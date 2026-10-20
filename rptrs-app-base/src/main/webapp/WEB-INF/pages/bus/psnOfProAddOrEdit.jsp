<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
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

        .labels .control-label {
            display: inline-block;
            width: 10.5%;
            float: left;
            margin-top: 0.4%;
            text-align: center;
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
            <bf:hidden id="savefromPid" name="savefromPid" value="${savefromPid}"/>
            <div class="boxse">
                <bf:formGroup type="success" hrp="bottom">
                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text id="name" name="name" col="7" disabled="true" value="${relPsnVo.name}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                       value="IDCARD" disabled="true" url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                            <bf:text id="identifyNumber" name="identifyNumber" col="7"
                                     value="${relPsnVo.identifyNumber}">
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
                            </bf:select>
                        </bc:col>

                        <bc:col col="4">
                            <bc:label cls="required" text="关联关系类型" col="5" formControl="true"/>
                            <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-RelCategory"
                                       includes="4" value="${relPsnVo.relCategory}">
                                <bf:option text="请选择"></bf:option>
                                <bv:notEmpty/>
                                <be:event type="select">
                                    setRelCategorySub(e.target.value);
                                </be:event>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="关系" col="5" formControl="true"/>
                            <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Rel" value="${relPsnVo.relCategorySub}">
                                <bf:option text="请选择"></bf:option>
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
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                            <bf:datebox name="activeTimeString" id="activeTimeString" col="7" format="date"
                                        value="${relPsnVo.activeTimeString}">
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="失效日期" col="5" formControl="true"/>
                            <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                                        value="${relPsnVo.invalidTimeString}">
                                <bv:callback message="请填写必填字段" callback="setInvalidTime"/>
                            </bf:datebox>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label  cls="required" text="与前一层是否有份额" col="5" formControl="true"/>
                            <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/WhetherToShare"
                                       value="${relPsnVo.equityShare}" disabled="false">
                                <bv:notEmpty/>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="持有比例(%)" col="5" formControl="true"/>
                            <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                     value="${relPsnVo.shareholdingRatio}" disabled="false">
                            </bf:text>
                        </bc:col></bf:formGroup>
                    <bf:formGroup type="success">

                        <div class="labels">
                            <bc:col col="12">
                                <bc:label cls="required" text="关联关系说明" col="5" formControl="true"/>
                                <bf:text id="relStatement" name="relStatement" col="10" value="${relPsnVo.relStatement}" disabled="false" multiple="true"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                            </bc:col>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
            </div>
            <%--<div id="voteProportion"  class="voteProportion">
                <bc:col col="4">
                    <bc:label text="表决权占比：" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7"
                             value="${relPsnVo.voteProportion}" disabled="true">
                    </bf:text>
                </bc:col>
            </div>--%>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button type="success" outline="true" text="保存" icon="save">
                        <be:event>
                            var docInnerPids = getAllIdSelections();
                            <%--if(localStorage.vals =='1'){--%>
                            <%--if (docInnerPids == "" || docInnerPids == undefined) {--%>
                            <%--alert("文件不能为空,请选中或上传文件");--%>
                            <%--return;--%>
                            <%--}--%>
                            <%--}--%>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var parentPid=$('#savefromPid').val();
                            $.mask.post('${base}/bus/relPsnOfPro/savePsnOfPro/'+parentPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/bus/relPro/psnOfProList/'+${fromPid}+'?op=0';
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
                            window.history.go(-1);
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter" id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="z-index:1;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">


    // setInvalidTime
    function setInvalidTime(value, validator, $field) {
        debugger
        //  isValid invalidTimeString
        var isValid = $("#isValid").val();
        var fieId1 = $field[0].id;
        var value1 = value;
        var investmentType = $('#invalidTimeString').val();
        if (fieId1 == "") {
            if ((isValid == "0") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });

    var relCategory = '${relPsnVo.relCategory}';
    var relCategorySubVal = '${relPsnVo.relCategorySub}';

    function setRelCategorySub(value) {
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value + '&type=s',
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

    //输入身份证反显关联方
    $("#identifyNumber").blur(function () {
        var name = $("#name").val();
        var identifyType = $("#identifyType").val();
        var identifyNumber = $(this).val();
        if (identifyNumber != '' && identifyNumber != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelPsn',
                data: {
                    "name": name,
                    "identifyType": identifyType,
                    "identifyNumber": identifyNumber
                },
                dataType: 'json',
                success: function (data) {
                    $("#identifyNumber").empty();
                    if (data.length > 0) {
                        $("#savefromPid").val(data[0].pid);
                        $("#name").val(data[0].name);
                        $("#domesticOrForeign").val(data[0].domesticOrForeign);
                        $("[name='birthdate']").val(data[0].birthdate);
                        $("#residentialAddress").val(data[0].residentialAddress);
                        $("#shareholdingProportion").val(data[0].shareholdingProportion);
                        $("#voteProportion").val(data[0].voteProportion);
                    } else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#identifyNumber").empty();
                        });
                    }
                }
            });
        }

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
                // alert(data.msg);
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
