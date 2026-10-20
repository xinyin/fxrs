<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联关系金融新增</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relProBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:button type="primary" text="基础数据">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <%-- <bc:button id="psnRelInformation" type="light" text="关系自然人信息">
                         <be:event>
                             affiliateInformation();
                         </be:event>
                     </bc:button>--%>
                    <%--<bc:button id="orgRelInformation" type="light" text="关联法人或非法人组织">
                        <be:event>
                            orgRelInformation();
                        </be:event>
                    </bc:button>--%>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <%--<bc:col col="3">
                    <font color="red">*</font>
                </bc:col>--%>
                <bc:col col="6">
                    <bc:label cls="required" text="产品名称" col="4" formControl="true"/>
                    <bf:text id="proName" name="proName" col="8" value="${relPro.proName}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="产品分类" col="4" formControl="true"/>
                    <bf:select id="proType" name="proType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-pro" value="${relPro.proType}">
                        <bv:notEmpty/>
                        <bf:option text="请选择"></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品批准/注册/备案号" col="4" formControl="true"/>
                    <bf:text id="proProvider" name="proProvider" col="8" value="${relPro.proProvider}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="产品代码" col="4" formControl="true"/>
                    <bf:text id="proCode" name="proCode" col="8" value="${relPro.proCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品登记机构" col="4" formControl="true"/>
                    <bf:text id="proRegOrg" name="proRegOrg" col="8" value="${relPro.proRegOrg}">
                    </bf:text>
                </bc:col>
                <%-- <bc:col col="6">
                     <bc:label cls="required" text="机构名称" col="4" formControl="true"/>
                     <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
                                url="sys/unit/selectUnit" excludes="ZZ_Delegation" value="${relPro.mgerOrg}">
                         <bv:notEmpty/>
                     </bf:select>
                 </bc:col>--%>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:button type="primary" text="管理信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品管理人法定名称" col="4" formControl="true"/>
                    <bf:text id="adName" name="adName" col="8" value="${relPro.adName}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="产品管理人证件号码" col="4" formControl="true"/>
                    <bf:text id="adCreditCode" name="adCreditCode" col="8" value="${relPro.adCreditCode}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品托管人法定名称" col="4" formControl="true"/>
                    <bf:text id="careName" name="careName" col="8" value="${relPro.careName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="产品托管人证件号码" col="4" formControl="true"/>
                    <bf:text id="careCreditCode" name="careCreditCode" col="8" value="${relPro.careCreditCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="10">
                    <bc:label text="底层资产" cls="required" col="3" formControl="true"/>
                    <bf:text id="assetDesc" name="assetDesc" col="7" rows="3" multiple="true" value="${relPro.assetDesc}"><bv:notEmpty/></bf:text>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup>
                <bc:col col="10">
                    <bc:label text="投资者" col="3" formControl="true"/>
                    <bf:text id="investors" name="investors" col="7" rows="3" multiple="true" value="${relPro.investors}"></bf:text>
                </bc:col>
            </bf:formGroup>
            <%--
                        <bf:formGroup>
                           &lt;%&ndash; <bc:col col="6">
                                <bc:label text="持有比例(%)" col="4" formControl="true"/>
                                <bf:text id="careName" name="careName" col="8" value="${relPro.careName}">
                                </bf:text>
                            </bc:col>&ndash;%&gt;
                            &lt;%&ndash;<bc:col col="6">
                                <bc:label cls="required" text="与前一层是否有份额" col="4" formControl="true"/>
                                <bf:select id="equityShare" name="equityShare" col="8" dataSource="custom"
                                           url="sys/dictItem/getDictItems/WhetherToShare"
                                           value="${relPro.equityShare}" disabled="false">
                                    <bv:notEmpty/>
                                </bf:select>
                            </bc:col>&ndash;%&gt;
                        </bf:formGroup>
                        <bf:formGroup>

                        </bf:formGroup>--%>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            save();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relPro/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">

    $("#proProvider").blur(function () {
        debugger

        var proProvider = $("#proProvider").val();
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPro/selectProProvider',
            data: {
                "proProvider": proProvider,

            },
            dataType: 'json',
            success: function (data) {
                if (data.success){
                    $.messager.alert('提示', '该产品批准/注册/备案号已经存在！', 'warning')
                    $("#proProvider").val('');
                }
            }
        });

    });


    $(document).ready(function () {

        $('#orgRelInformation').attr("disabled",true);

        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
    clear()

    function exportIn() {
        // alert(localStorage.vals);
        if (localStorage.vals == "1") {
            $("#hiddenBtn").click();
        }
    }

    function clear() {
        localStorage.clear()
        // console.log('111')
    }

    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var mgerOrg = $('#mgerOrg').val();
        var formData = new FormData($('#uploadForm')[0]);
        formData.append("uploadAgency", mgerOrg);
        $.ajax({
            type: 'post',
            url: '${base}/bus/relFile/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.relFile;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#relFileTable').bootstrapTable('load', showData);
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
            }
        })
    });

    //表单提交数据
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeAll();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    //文件表数据
    function getData() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/relFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }
    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/relFile/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#relFileTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }


    function affiliateInformation() {
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?';
    }

    function orgRelInformation() {
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        window.location = '${base}/bus/relPsn/psnRelOrgList/' + pid + '?';
    }

    /* $(document).ready(function () {
         var opt = $('#opt').val();
         if (opt != 'edit') {
             document.getElementById("psnRelInformation").setAttribute("disabled", true);
             document.getElementById("orgRelInformation").setAttribute("disabled", true);
         }
     });*/

    //反显
    function addRelPsn() {
        <%--var identifyNumber = ${relPsn.identifyNumber}.val();--%>
        <%--var  parms = {--%>
        <%--    'identifyNumber' :identifyNumber--%>
        <%--}--%>
        $.ajax({
            url: "${base}/bus/relPsn/viewRelPsn",
            type: "get",
            data: parms,
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            success: function (data) {
                console.log(data)
                if (data.size() > 0) {
                    $("#name").val(data[0].name)
                    <%--$("#residentialAddress").value="${relPsn.residentialAddress};--%>
                }
            }
        })
    }

    //反显持股占比
    $("#holeShareAmount").blur(function () {
        var holeShareAmount = $('#holeShareAmount').val();
        // alert(holeShareAmount);
        if (holeShareAmount != '' && holeShareAmount != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/rootOrgInfo/count/' + holeShareAmount,
                data: {
                    "": ''
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    // $("#identifyNumber").empty();
                    if (data.success) {
                        $("#shareholdingProportion").val(data.shareholdingProportion)
                        $("#frozenShareAmount").val(0)
                        $("#pledgedShareAmount").val(0)
                    } else {
                        $.messager.alert('提示', '计算失败', 'info');
                    }
                }
            });
        }
    })

    //查询反显
    $("#identifyNumber").blur(function () {
        var identifyNumber = $(this).val();
        if (identifyNumber != '' && identifyNumber != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelPsn',
                data: {
                    "name": '',
                    // "shareCode": '',
                    "identifyType": '',
                    "identifyNumber": identifyNumber
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    $("#identifyNumber").empty();
                    if (data.length > 0) {
                        $.messager.alert('提示', '关联方已存在！', 'warning', function () {
                            <%--window.location = '${base}/workflow/process/runingList';--%>
                        });
                        /*if (data[0].name != '' && data[0].name != undefined) {
                            localStorage.name = data[0].name;
                        } else {
                            localStorage.name = ''
                        }
                        if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                            localStorage.identifyType = data[0].identifyType;
                        } else {
                            localStorage.identifyType = ''
                        }
                        if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                            localStorage.identifyNumber = data[0].identifyNumber;
                        } else {
                            localStorage.identifyNumber = ''
                        }
                        // localStorage.toPid = data[0].pid
                        // localStorage.toType = data[0].type
                        $("#name").val(data[0].name)
                        if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                            $("#identifyNumber").val(data[0].identifyNumber)
                        } else {
                            $("#identifyNumber").val('')
                        }
                        if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                            if (data[0].identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                                var str = "<option value=" + data[0].identifyType + ">统一社会信用代码</option>";
                                $("#selectit").append(str);
                            } else if (data[0].identifyType == 'MILITARYID') {
                                var str = "<option value=" + data[0].identifyType + ">军官证</option>";
                                $("#selectit").append(str);
                            } else if (data[0].identifyType == 'PASSPORT') {
                                var str = "<option value=" + data[0].identifyType + ">护照</option>";
                                $("#selectit").append(str);
                            } else if (data[0].identifyType == 'IDCARD') {
                                var str = "<option value=" + data[0].identifyType + ">居民身份证</option>";
                                $("#selectit").append(str);
                            } else if (data[0].identifyType == 'PERMANENTRESIDENT') {
                                var str = "<option value=" + data[0].identifyType + ">居民户口簿</option>";
                                $("#selectit").append(str);
                            } else if (data[0].identifyType == 'OTHER') {
                                var str = "<option value=" + data[0].identifyType + ">其他证件</option>";
                                $("#selectit").append(str);
                            }
                        } else {
                            var str = "<option value='0'>无</option>";
                            $("#identifyType").append(str);
                        }
                        $("#identifyNumber").val(data[0].identifyNumber)*/
                    } /*else {
                        $.messager.alert('提示', '请添加关联方。', 'warning', function () {






                    <%--window.location = '${base}/workflow/process/runingList';--%>
                        });

                        $("#identifyNumber").val('')
                        $("#identifyType").val('')
                        // $("#selectit option[value='0']").attr("selected", true);
                        var str = "<option value='0'>无</option>";
                        $("#identifyNumber").append(str);
                    }*/

                }
            });
        }

    })


    /**
     * 数据保存
     */

    function save() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var formData = $('#editForm').serializeAll();
            $.mask.post('${base}/bus/relPro/save', formData, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/bus/relPro/list';
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });

        }
    }
</script>
</body>
</html>
