<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${base}/static/css/custom.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/static/js/editable-select/jquery-editable-select.min.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/static/js/jquery-ui-1.12.1.custom/jquery-ui.css">
</head>
<style scoped>
    body {
        background-image: url(${res}/img/background.png);
        background-origin: content-box;
        background-position: 50% 0%;
        background-size: contain;
        background-repeat: no-repeat;
        background-size: cover;

    }

    .title-text {
        font-size: 23px;
        font-weight: bold;
        color: #404040;
    }

</style>
<body>
<div>
    <div class="middle-box text-left loginscreen animated fadeInDown login-box">
        <div class="ibox-content" style="background-color: rgba(0,0,0,0); border-color: rgba(0,0,0,0);">
            <div style="text-align: center;margin: 10px 0" class="title-text">关联交易信息系统</div>
            <form class="m-t" role="form" id="loginForm" method="post">
                <input type="hidden" id="source" name="source" value="JEP_LOCAL_LOGIN">
                <%--租户列表--%>
              <%--  <div class="form-group">
                    <select id="tenantId" name="tenantId" class="form-control">
                    </select>
                </div>--%>
                <div class="input-group form-group">
                    <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
                    <input id="userName" name="userName" type="text" class="form-control" placeholder="请输入用户名"
                           value="">
                </div>
                <div class="input-group form-group">
                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                    <input id="passWord" name="passWord" type="password" class="form-control" placeholder="请输入密码"
                           value="">
                </div>
                <div class="form-group" id="secondedStaffDiv" style="display: none;">
                    <select id="secondedStaff" name="secondedStaff" class="form-control">
                    </select>
                </div>
                <button type="button" id="submit" class="btn btn-success block full-width m-b">登录</button>
                <div class="form-group">
                    <p id="msg" class="text-left text-danger"></p>
                </div>
            </form>
            <%--<p class="m-t text-center"> <small>杰智融. 2018&copy;版权所有</small> </p>--%>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript" src="${base}/static/js/particles.min.js"></script>
<script type="text/javascript" src="${base}/static/js/app.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery-cookie-v1.4.1-0/jquery.cookie.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery-cookie-v1.4.1-0/jep.cookie.js"></script>
<script type="text/javascript" src="${base}/static/js/editable-select/jquery-editable-select.min.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
<script type="text/javascript" src="${base}/static/js/des/crypto-js.js"></script>
<script type="text/javascript" src="${base}/static/js/jsencrypt.js"></script>
<script type="text/javascript">
    if (self != top) {
        window.top.location = window.location;
    }
    $(document).ready(function () {
        //获取cookie 缓存的用户名
        var jepCookie = new JepCookie();
        var cookies = jepCookie.getCookie();
        var userNames = cookies.split(',');

        //数据添加到插件中
        $("#userName").autocomplete({
            source: userNames,
            minChars: -1,
            autoFill: true,
            mustMatch: true,
            matchContains: true
        });

        /* //把缓存数据放到用户名输入框中
        $.each(userNames, function(i, val){
            $('#userName').prepend("<option value='" + val + "'>" + val + "</option>");
        })

        //设置select可编辑
        $('#userName').editableSelect({ effects: 'default' }); */

        //当前登录人是否被借调
       /* $('#userName').blur(function () {
            querySecondedStaff(this);
        });*/

        //绑定回车提交
        document.onkeydown = function (e) {
            var theEvent = window.event || e;
            var code = theEvent.keyCode || theEvent.which;
            if (code == 13) {
                login();
            }
        }

        //绑定提交事件
        $('#submit').click(function () {
            login();
        });

        //获取租户
        initTenant();
    });

    /*
     * 登录方法
     */
    function login() {
        var userName = $('#userName').val();
        if (null == userName || '' == userName) {
            $('#msg').html('用户名不能为空。');
            return;
        }

        var passWord = $('#passWord').val();
        if (null == passWord || '' == passWord) {
            $('#msg').html('密码不能为空。');
            return;
        }
        let encodePass = encrypt(passWord)
        $("input[name='passWord']").val(encodePass);
        // pwd(passWord);

        var formData = $('#loginForm').serializeAll();
        var url = '';

        /*
        if (serr == 'ot'){
            url = '/login/verification';
        }*/
        url = '/login/verification';
        $.mask.post('${base }' + url, formData, function (data) {
            if (data.success) {
                //把用户名写入cookie里， 方便下次读取
                var jepCookie = new JepCookie();
                jepCookie.addCookie(userName);
                window.location = '${base}/index/';
            } else {
                $('#msg').html(data.msg);
            }
        });
    }

    function pwd(passwordOld){
        var passwordNew = encryptByDES(passwordOld, "yinzhijieguanlianjiaoyiguanlixitong");
        $("input[name='passWord']").val(passwordNew);
    }


    // DES加密
    function encryptByDES(message, key) {
        var keyHex = CryptoJS.enc.Utf8.parse(key);
        var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
            mode : CryptoJS.mode.ECB,
            padding : CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }

    //查询当前登录人员是否被借调
    function querySecondedStaff(el) {
        //请求数据
        $.ajax({
            type: 'GET',
            url: '${base}/login/queryStaff?proxyStaffCode=' + el.value,
            dataType: 'json',
            success: function (data) {
                $("#secondedStaff").empty();

                //验证是否被借调
                if (data.length <= 1) {
                    $('#secondedStaffDiv').hide();
                }

                //如果被借调
                if (data.length > 1) {
                    $.each(data, function (i, val) {
                        $("#secondedStaff").prepend("<option value='" + val.staffCode + "'>" + val.displayName + "</option>");
                    })

                    $('#secondedStaffDiv').show();
                }
            }
        });
    }

    //获取租户
    function initTenant() {
        $('#tenantId').hide();
        //请求数据
        $.ajax({
            type: 'GET',
            url: '${base}/sys/tenant/queryList',
            dataType: 'json',
            success: function (data) {
                $("#tenantId").empty();
                //查询当前系统是不是只有一个租户 ，如果一个就不显示
                if (data.length <= 1) {
                    $('#tenantId').hide();
                } else {
                    $.each(data, function (i, val) {
                        if (val.code == null) $("#tenantId").prepend("<option value='" + val.code + "' selected=\"selected\">" + val.name + "</option>");
                        else $("#tenantId").prepend("<option value='" + val.code + "'>" + val.name + "</option>");
                    })

                    $('#tenantId').show();
                }
            }
        });
    }
    //公钥
    const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC8l9UJ+sb+os5wcAnc1S0dgpdzAtxfsBJwFvbFMy1vPsfwQPmLXKwrX84f8Zy5W2ghS4CQbGVB5+bDl+K8zFhRywsq0DnRw85J8nHOoG5aSk8jfzeIuWK6XZ4/h17akHMKYObjrPs63SbGAnSQ/9DhfEPd/3GPZeITKwDq6MHDQIDAQAB'
    // 加密
    function encrypt(txt) {
        const encryptor = new JSEncrypt()
        encryptor.setPublicKey(publicKey) // 设置公钥
        return encryptor.encrypt(txt) // 对数据进行加密
    }
</script>
</body>
</html>