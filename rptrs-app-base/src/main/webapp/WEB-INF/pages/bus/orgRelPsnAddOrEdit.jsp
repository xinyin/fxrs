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
            <bf:hidden id="savefromPid" name="savefromPid" value="${savefromPid}"/>
            <div class="boxse">
                <bf:formGroup type="success" hrp="bottom">

                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text id="name" name="name" click="forNameSelect()" disabled="${opt == 'edit'}" col="7" value="${relPsnVo.name}" >
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                       value="${relPsnVo.identifyType}" disabled="true" url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                            <bf:text id="identifyNumber" name="identifyNumber" col="7" disabled="true"
                                     value="${relPsnVo.identifyNumber}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
                            <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                       value="${relPsnVo.domesticOrForeign}" disabled="true">
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="关联关系类别" col="5" formControl="true"/>
                            <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-RelCategory"
                                       includes="2,4,6" value="${relPsnVo.relCategory}">
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
                                        value="${relPsnVo.activeTimeString}" disabled="ture">
                                <bv:notEmpty/>
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="失效日期" col="5" formControl="true"/>
                            <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                                        value="${relPsnVo.invalidTimeString}" disabled="ture">
                                <bv:callback message="请填写必填字段" callback="setInvalidTime"/>
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
                                    <bv:between min="0" max="100" inclusive="true" message="必须为0-100的数字"/>
                                    <bv:notEmpty/>
                                </bf:text>
                            </bc:col>
                        </div>
                        <bc:col col="4">
                            <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                            <bf:text id="pledgeRatio" name="pledgeRatio" col="7"
                                     value="${relPsnVo.pledgeRatio}" disabled="fasle">
                                <bv:between min="0" max="100" inclusive="true" message="必须为0-100的数字"/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
                    <bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true" value="${relPsnVo.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup type="success" hidden="true">
                    <bc:col col="4">
                        <bc:label text="居住地址" col="5" formControl="true"/>
                        <bf:text id="residentialAddress" name="residentialAddress" col="7"
                                 value="${relPsnVo.residentialAddress}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联关系判定" col="5" formControl="true"/>
                        <bf:select id="Predicate" name="Predicate" col="7" dataSource="custom"
                        ><%-- url="sys/dictItem/getDictItems/BUS_Predicate"--%>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
            </div>
            <div id="voteProportion" class="voteProportion">
                <bc:col col="4">
                    <bc:label text="表决权占比：" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7"
                             value="${relPsnVo.voteProportion}" disabled="true">
                    </bf:text>
                </bc:col>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button type="success" outline="true" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            debugger
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var parentPid=$('#savefromPid').val();
                            $.mask.post('${base}/bus/relOrg/saveAffiliate/'+parentPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/bus/relOrg/affiliateList/'+${fromPid}+'?op=0';
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
                            window.location = '${base}/bus/relOrg/affiliateList/'+"/"+${fromPid}+'?op=0';
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


    var relCategory = '${relPsnVo.relCategory}';
    var relCategorySubVal = '${relPsnVo.relCategorySub}';
    $(document).ready(function () {
        if (relCategorySub != '' && relCategorySub != undefined) {
            setRelCategorySub(relCategory);
        }
    });
    var equityShareval = '${relPsnVo.equityShare}';
    $(document).ready(function () {
        if (equityShareval != '' && equityShareval != undefined) {
            setShareholdingRatio(equityShareval)
        }
    });
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

    /*
    * 关联关系类别联动
    * */
    function setRelCategorySub(value) {
        if (value == '' || value == undefined) {
            $("#relCategorySub").empty();
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relOrg/queryRelCategorySub?relCategory=' + value + '&type=p',
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

    /**
     * 名称弹框
     */
    //click="forNameSelect()"
    function forNameSelect() {
        var type = 'psn';
        $.messager.modal({
            winId: 'forNameSelect',
            width: 700,
            height: 420,
            title: '关联方信息查询',
            url: '${base}/bus/rel/forNameSelect/' + type,
            args: {
                // name    identifyType   identifyNumber  domesticOrForeign
                result: function (obj) {
                    $("#savefromPid").val(obj["pid"]);
                    $("#name").val(obj["name"]);
                    $("#identifyType").val(obj["psnType"]);
                    $('#identifyType').select2({value:(obj["psnType"])});
                    $("#identifyNumber").val(obj["number"]);
                    $("#domesticOrForeign").val(obj["domesticOrForeign"]);
                    $('#domesticOrForeign').select2({value:(obj["domesticOrForeign"])});
                }
            },
            onClose: function () {
            }
        });
    }


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
</script>


<script type="text/javascript">

    //加上红点
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
</script>
</body>
</html>
