<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关系自然人新增或编辑</title>
    <script>


        // window.onload=show;
        function show() {
            var val =${relPsnVo.isShareholder};
            // alert(val);
            if (val == '1') {
                $('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')
                localStorage.vals = val;
            }
        }

        function isShareholder(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }

        function isNoShareVote(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#voteProportion').css('display', 'block')
                $('#voteProportion').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#voteProportion').css('display', 'none')
                $('#voteProportion').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }
    </script>
    <x:jsLib/>

    <style>
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .MainGd {
            display: none;
        }

        .voteProportion {
            display: none;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
    <bf:hidden id="opt" name="opt" value="${opt}"/>
    <bf:hidden id="pid" name="pid" value="${pid}"/>
    <bf:hidden id="relId" name="relId" value="${relId}"/>
    <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
    <bf:hidden id="paFromPid" name="paFromPid" value="${pid}"/>

    <bf:formGroup>
        <%--				<div class="col-sm-4 col-sm-offset-2">--%>
        <%--					<bc:button type="info" text="基本信息">--%>
        <%--						<be:event>--%>
        <%--							basicInformation();--%>
        <%--						</be:event>--%>
        <%--					</bc:button>--%>
        <%--					<bc:button id="psnRelInformation" type="info" text="关系自然人信息">--%>
        <%--						<be:event>--%>
        <%--							affiliateInformation();--%>
        <%--						</be:event>--%>
        <%--					</bc:button>--%>
        <%--					<bc:button id="orgRelInformation" type="info" text="关系机构信息">--%>
        <%--						<be:event>--%>
        <%--							orgRelInformation();--%>
        <%--						</be:event>--%>
        <%--					</bc:button>--%>
        <%--				</div>--%>
    </bf:formGroup>
    <div class="boxse">

        <bf:formGroup type="success">
            <bc:col col="4">
                <bc:label text="关联方名称" col="5" formControl="true"/>
                <bf:text id="name" name="name" col="7" disabled="true" value="${relPsnVo.name}">
                </bf:text>
            </bc:col>
            <bc:col col="4">
                <bc:label text="证件类型" col="5" formControl="true"/>
                <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                           value="${relPsnVo.identifyType}" url="sys/dictItem/getDictItems/BUS-IdentifyType"
                           disabled="true">
                </bf:select>
            </bc:col>
            <bc:col col="4">
                <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                <bf:text id="identifyNumber" name="identifyNumber" disabled="true" col="7"
                         value="${relPsnVo.identifyNumber}">
                    <bv:stringLength min="8" max="16" trim="true"/>
                    <bv:notEmpty/>
                </bf:text>
            </bc:col>
        </bf:formGroup>
        <bf:formGroup type="success">
            <bc:col col="4">
                <bc:label cls="required" text="境内外标识" col="5" formControl="true"/>
                <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                           url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                           value="${relPsnVo.domesticOrForeign}" disabled="true">
                    <bv:notEmpty/>
                </bf:select>
            </bc:col>
            <bc:col col="4">
                <bc:label cls="required" text="关联关系类别" col="5" formControl="true"/>
                <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                           url="sys/dictItem/getDictItems/BUS-RelCategory"
                           includes="1" value="${relPsnVo.relCategory}">
<%--                    <be:event type="select">--%>
<%--                        setRelCategorySub(e.target.value);--%>
<%--                    </be:event>--%>
                </bf:select>
            </bc:col>
            <bc:col col="4">
                <bc:label cls="required" text="关系" col="5" formControl="true"/>
                <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                           url="sys/dictItem/getDictItems/BUS-Rel" includes="5" value="${relPsnVo.relCategorySub}">
                    <bv:notEmpty/>
                </bf:select>
            </bc:col>
        </bf:formGroup>
        <bf:formGroup type="success">
            <bc:col col="4">
                <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                           url="sys/dictItem/getDictItems/BUS-valid"
                           value="${relPsnVo.isValid}" disabled="false">
                    <bv:notEmpty/>
                </bf:select>
            </bc:col>
            <bc:col col="4">
                <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                <bf:datebox name="activeTimeString" id="activeTimeString" col="7" format="date"
                            value="${relPsnVo.activeTimeString}" disabled="ture">
                    <bv:notEmpty/>
                </bf:datebox>
            </bc:col>
            <bc:col col="4">
                <bc:label text="失效日期" col="5" formControl="true"/>
                <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                            value="${relPsnVo.invalidTimeString}" disabled="ture">
                </bf:datebox>
            </bc:col>
        </bf:formGroup>
        <bf:formGroup type="success">
            <bc:col col="4">
                <bc:label cls="required" text="与前一层是否有权益份额" col="5" formControl="true"/>
                <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                           url="sys/dictItem/getDictItems/WhetherToShare"
                           value="${relPsnVo.equityShare}" disabled="false">
                    <be:event type="select">
                        setShareholdingRatio(e.target.value);
                    </be:event>
                    <bv:notEmpty/>
                </bf:select>
            </bc:col>
            <div id="holdingRatio">
                <bc:col col="4">
                    <bc:label cls="required" text="持有比例(%)" col="5" formControl="true"/>
                    <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                             value="${relPsnVo.shareholdingRatio}" disabled="false">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </div>
            <bc:col col="4">
                <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                <bf:text id="pledgeRatio" name="pledgeRatio" col="7"
                         value="${relPsnVo.pledgeRatio}" disabled="fasle">
                </bf:text>
            </bc:col>
        </bf:formGroup>

        <bf:formGroup col="6">
            <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
            <bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true"
                     value="${relPsnVo.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
            <bv:notEmpty/>
        </bf:formGroup>
    </div>

</div>
<bf:formGroup>
    <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
        <bc:button type="success" outline="true" text="保存" icon="save" id="x">
            <be:event>

                $('#editForm').data('formValidation').validate();
                var isValid = $('#editForm').data('formValidation').isValid();
                if(isValid)
                {
                var formData = $('#editForm').serializeAll();
                var paFromPid=$('#paFromPid').val();
                $.mask.post('${base}/bus/relPsn/saveAffiliate/'+paFromPid+'/${fromPid}', formData, function(data){
                if(data.success)
                {
                $.messager.alert('提示', data.msg, 'info', function(){
                <%--var opt = $("#opt").val();--%>
                <%--window.location = '${base}/bus/relPsn/affiliateList/'+${fromPid};--%>
                window.location = '${base}/bus/relPsn/affiliateList/'+${fromPid}+'?op=0';
                });
                }
                else
                {
                $.messager.alert('错误', data.msg, 'error');
                }
                });
                }
            </be:event>
        </bc:button>
        <bc:button type="primary" text="返回" icon="arrow-circle-left">
            <be:event>
                window.location = '${base}/bus/relPsn/affiliateList/'+${fromPid}+'?op=0';
            </be:event>
        </bc:button>
    </div>
</bf:formGroup>
</bf:form>
</bc:ibox>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">

    var relCategory = '${relPsnVo.relCategory}';
    var relCategorySubVal = '${relPsnVo.relCategorySub}';
    var equityShareval = '${relPsnVo.equityShare}';
   /* $(document).ready(function () {
        if (relCategorySub != '' && relCategorySub != undefined) {
            setRelCategorySub(relCategory);
        }
        if (equityShareval!= '' && equityShareval != undefined ){
            setShareholdingRatio(equityShareval)
        }
    });
*/
    /*
   * 与前一层是否有权益份额联动持有比例
   * */
    function setShareholdingRatio(val) {
        if (val == '1' || val == '2') {
            //    shareholdingRatio
            $("#holdingRatio").show()
        }
        if (val == '0') {
            $("#holdingRatio").hide()
        }
    }


    function setRelCategorySub(value) {
        if (value == '1'){
            $("#equityShare").val('0');
            $('#equityShare').select2({value: '0'});
            setShareholdingRatio('0')
        }
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value + '&type=p',
            dataType: 'json',
            delay: 200,
            success: function (data) {

                var arr = new Array();
                $.each(data, function (i, val) {
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                $("#relCategorySub").empty();
                $("#relCategorySub").val(relCategorySubVal);
                $("#relCategorySub").select2({data: arr, width: "100%"});
                var select2 = $("#relCategorySub").select2();    //获取selectid

                select2.val(relCategorySubVal).trigger("change");
                select2.change();
            }
        });
    }


    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
    $(document).ready(function () {
        //查看页面标签禁用
        var op =${op};
        if (op == '1') {
            Array.prototype.slice.call(document.getElementsByTagName('input')).map(i => i.disabled = true);
            Array.prototype.slice.call(document.getElementsByTagName('select')).map(i => i.disabled = true);
            document.getElementById("relStatement").setAttribute("disabled", true);//关系说明禁用
            $('#x').hide()

        }
    })

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
        var formData = new FormData($('#uploadForm')[0]);
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

    <%--function affiliateInformation() {--%>
    <%--	var pid = $("#pid").val();--%>
    <%--	if (pid == '') {--%>
    <%--		alert("请保存基本信息")--%>
    <%--		return;--%>
    <%--	}--%>
    <%--	window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?';--%>
    <%--}--%>

    <%--function orgRelInformation() {--%>
    <%--	var pid = $("#pid").val();--%>
    <%--	if (pid == '') {--%>
    <%--		alert("请保存基本信息")--%>
    <%--		return;--%>
    <%--	}--%>
    <%--	window.location = '${base}/bus/relPsn/psnRelOrgList/' + pid + '?';--%>
    <%--}--%>

    // $(document).ready(function () {
    // 	var opt = $('#opt').val();
    // 	if (opt != 'edit') {
    // 		document.getElementById("psnRelInformation").setAttribute("disabled", true);
    // 		document.getElementById("orgRelInformation").setAttribute("disabled", true);
    // 	}
    // });
</script>
</body>
</html>
