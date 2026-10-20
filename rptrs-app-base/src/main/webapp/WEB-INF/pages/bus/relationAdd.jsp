<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>关联关系新增或编辑</title>
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

        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="fromPid" name="fromPid"/>
            <bf:hidden id="toPid" name="toPid"/>
            <bf:hidden id="unitCode" name="unitCode" value="${unitCode}"/>
            <div class="boxse">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="本层关联方名称" col="5" formControl="true"/>
                        <bf:text id="fromMemo" name="fromMemo" col="7" click="forNameSelect()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="本层关联方代码" col="5" formControl="true"/>
                        <bf:text id="fromIdentifyNumber" name="fromIdentifyNumber" col="7" disabled="true">
                            <bv:stringLength min="8" max="18" trim="true"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="本层关联方类型" col="5" formControl="true"/>
                        <bf:select id="fromType" name="fromType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OrgOrPsn" disabled="true">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="前一层关联方名称" col="5" formControl="true"/>
                        <bf:text id="toMemo" name="toMemo" col="7" click="forNameSelectAll()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="前一层关联方代码" col="5" formControl="true"/>
                        <bf:text id="toIdentifyNumber" name="toIdentifyNumber" col="7" disabled="true">
                            <bv:stringLength min="8" max="18" trim="true"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="前一层关联方类型" col="5" formControl="true"/>
                        <bf:select id="toType" name="toType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OrgOrPsn" disabled="true">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层的关联关系" col="5" formControl="true"/>
                        <bf:select id="relPsnOrg" name="relPsnOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelZZD">
                            <bf:option text="请选择" value=""/>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层之间是否有权益份额" col="5" formControl="true"/>
                        <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelequityShare">
                            <bf:option text="请选择" value=""/>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <div id="holdings" class="holdings">
                        <bc:col col="4">
                            <bc:label cls="required" text="持有比例（%）" col="5" formControl="true"/>
                            <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </div>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                         <bc:label cls="required" text="事项说明" col="5" formControl="true" />
                            <bf:select id="itemDescription" name="itemDescription" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-ItemDescription" >
                                <bf:option text="" value="新增"></bf:option>
                            </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="股权质押比例（%）" col="5" formControl="true"/>
                        <bf:text id="pledgeRatio" name="pledgeRatio" col="7">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="该关系是否仍然有效" col="5" formControl="true"/>
                        <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo">
                            <bf:option text="请选择" value=""/>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                        <bf:datebox name="activeTime" id="activeTime" col="7" format="date">
                            <bv:callback callback="isTransfered" />
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="invTime" text="失效日期" col="5" formControl="true"/>
                        <bf:datebox name="invalidTime" id="invalidTime" col="7" format="date">
                            <bv:callback callback="isTransfered" />
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="关系类型" col="5" formControl="true"/>
                        <bf:select id="relationType" name="relationType" col="7"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-RelationType">
                            <bf:option text="请选择" value="%"/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <div id="note" style="float: right;margin: 0.2% 0 !important;">
                            <p style="color: red;text-align: center;margin:auto;font-weight: 800;line-height: 2rem;font-size: smaller">失效时间请填写具体离职/关联关系不在有效日期的一年后，如2022年4月30日离职，请填写2023年4月30日。</p>
                        </div>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bf:formGroup col="12">
                        <bc:label cls="required" text="关联关系说明" col="2" formControl="true"/>
                        <bf:text id="relStatement" name="relStatement" col="10" rows="3" multiple="true"><bv:stringLength max="1000" message="请将字数控制在1000以内！"/><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            save();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relation/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <br:js pack="form"/>
    <be:processEvent/>
    <script type="text/javascript">
        function save() {
            var unitCode =  $('#unitCode').val();
            if (unitCode === "SX000") {
                if ($('#relationType').val() === '%') {
                    $.messager.alert('提示', '请填写关系类型', 'warning');
                    return;
                }
            }
            var isValidd = $('#isValid').val();
            if (isValidd !== "1"){
                var invalidTime = $('#invalidTime [name="invalidTime"]').val();
                if (!invalidTime.length>0){
                    $.messager.alert('提示', '请填写失效日期', 'warning');
                    return;
                }
            }
            var docInnerPids = getAllIdSelections();
            $('#editForm').data('formValidation').validate();
            var isValid = $('#editForm').data('formValidation').isValid();
            if(isValid)
            {
                var formData = $('#editForm').serializeAll();
                var docTableSelectItem = {
                    'name':'docTableSelectItem',
                    'value':docInnerPids
                };
                formData.push(docTableSelectItem);
                $.mask.post('${base}/bus/relation/save', formData, function(data){
                    if(data.success)
                    {
                        $.messager.confirm('提示', data.msg, function(r){
                            if(r)
                            {
                                window.location = '${base}/bus/relation/list';
                            }
                        });
                    }
                    else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                });
            }
        }
        //生效日期不得大于失效日期
        function isTransfered(value, validator) {
            var activeTime = $('#activeTime [name="activeTime"]').val();
            var invalidTime = $('#invalidTime [name="invalidTime"]').val();
            if (activeTime && invalidTime) {
                var start = new Date(activeTime.replace("-", "/").replace("-", "/"));
                var end = new Date(invalidTime.replace("-", "/").replace("-", "/"));
                if (start > end) {
                    return {valid: false, message: '生效日期不得大于失效日期。'};
                } else {
                    return {valid: true, message: '生效日期不得大于失效日期。'};
                }
            }
            return {valid: true, message: '生效日期不得大于失效日期。'};
        }


        $(function () {
            $('#equityShare').change(function () {
                let val = $('#equityShare').val();
                if (val != 0) {
                    $('#holdings').attr("style", "display:block;");
                } else {
                    $('#holdings').attr("style", "display:none;");
                    $('#holdings [name="shareholdingRatio"]').val('0')
                }
            });
        });

        $(function () {
            let val = $('#equityShare').val();
            if (val != 0) {
                $('#holdings').attr("style", "display:block;");
            } else {
                $('#holdings').attr("style", "display:none;");
            }
        });


        $(document).ready(function () {
            $(".required").each(function () {
                var $this = $(this);
                $(this).html("<font color='red'>✶</font>" + $this.html());
            });
            var unitCode =  $('#unitCode').val();
            if (unitCode === 'SX000') {
                $('.relationTyp').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
            }
        });
        /**
         * 名称弹框
         */
        function forNameSelect() {
            $.messager.modal({
                winId: 'forNameSelect',
                width: 700,
                height: 420,
                title: '本层关联方信息查询',
                url: '${base}/bus/relation/forNameSelect',
                args: {
                    // name    identifyType   identifyNumber  domesticOrForeign
                    result: function (obj) {
                        $("#fromPid").val(obj["pid"]);
                        $("#fromMemo").val(obj["fromMemo"]);
                        $("#fromIdentifyNumber").val(obj["identifyNumber"]);
                        $('#fromType').val(obj["fromType"]);
                        $('#fromType').select2({value: (obj["fromType"])});
                    }
                },
                onClose: function () {
                    setRelCategorySub();
                    validateRelInfo();
                }
            });
        }

        function forNameSelectAll() {
            $.messager.modal({
                winId: 'forNameSelect1',
                width: 700,
                height: 420,
                title: '前层关联方信息查询',
                url: '${base}/bus/relation/forNameSelectAll01',
                args: {
                    result: function (obj) {
                        $("#toPid").val(obj["pid"]);
                        $("#toMemo").val(obj["fromMemo"]);
                        $("#toIdentifyNumber").val(obj["identifyNumber"]);
                        $('#toType').val(obj["fromType"]);
                        $('#toType').select2({value: (obj["fromType"])});
                    }
                },
                onClose: function () {
                    setRelCategorySub();
                    validateRelInfo();
                }
            });
        }

        //与前一层的关系
        function setRelCategorySub() {
            var toType = $('#toType').val();
            var fromType = $('#fromType').val();
            if (toType && fromType) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/bus/relation/queryClassify?fromType=' + fromType + '&toType=' + toType,
                    dataType: 'json',
                    delay: 200,
                    success: function (data) {
                        $("#relPsnOrg").empty();
                        $.each(data, function (i, val) {
                            var str = "<option value=" + val.itemValue + ">" + val.itemLabel + "</option>";
                            $("#relPsnOrg").append(str);
                        });
                    }
                });
            }
        }


        //添加校验本层和上一层信息不能重复
        function validateRelInfo() {
            if ($("#toPid").val() !== '' && $("#fromPid").val() !== '') {
                if ($("#toPid").val() === $("#fromPid").val() && $("#fromMemo").val() === $("#toMemo").val()) {
                    $.messager.alert('提示', '不可以和自己建立关系', 'warning');
                    return;
                }
            }
        }


        function getAllIdSelections() {
            return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }

        $(function () {
            $('#isValid').change(function () {
                let val = $('#isValid').val();
                if (val == '0') {
                    $('.invTime').html("<font color='red'>✶</font>" +  $('.invTime').html());
                }
                else {
                    $('.invTime font').remove();
                }
            });
        });
    </script>
</body>
</html>