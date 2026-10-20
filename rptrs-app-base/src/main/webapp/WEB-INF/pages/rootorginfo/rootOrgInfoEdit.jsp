<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>机构基础数据编辑</title>
    <style>
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
    <bc:ibox id="orgBaseDataBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${edit}"/>
            <bf:hidden id="pid" name="pid" value="${rootOrgInfo.pid}"/>
            <bf:hidden id="editYear" name="editYear" value="${rootOrgInfo.years}"/>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom" hr="dashed">
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="报送数据所属年份" col="4" formControl="true"/>
                        <%--<bf:datebox id="years" name="years" col="9" format="date" value="${dateTime}"/>--%>
                        <bf:select id="years" name="years" col="8" disabled="${opt == 'view'}" value="${dateTime}" >
                            <be:event type="select">
                                yearSelected();
                            </be:event>
                            <bv:notEmpty/>
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                        <bv:notEmpty/>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="报送数据所属季度" col="4" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter" disabled="${opt == 'view'}"
                                   value="${rootOrgInfo.quarter}">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom" hr="dashed" >
                    <bf:formGroup col="6">
                        <bc:label text="总资产(万元，请保留4位小数)" cls="required" col="4" formControl="true"/>
                        <bf:text id="totalCapital" name="totalCapital" col="8" value="${rootOrgInfo.totalCapital}">
                            <bv:regexp regexp="^([1-9]([0-9]*)|([0]))(\\.[0-9]{0,4})?$" message="请输入正确的数值,限4位小数"/>
                            <bv:stringLength max="20" />
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label text="净资产(万元，请保留4位小数)" cls="required" col="4" formControl="true"/>
                        <bf:text id="netCaptial" name="netCaptial" col="8" value="${rootOrgInfo.netCaptial}">
                            <bv:regexp regexp="^([1-9]([0-9]*)|([0]))(\\.[0-9]{0,4})?$" message="请输入正确的数值,限4位小数"/>
                            <bv:stringLength max="20" />
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom" hr="dashed" >
                   <%-- <bf:formGroup col="6">
                        <bc:label text="季度综合偿付能力充足率（%）" cls="required" col="4" formControl="true"/>
                        <bf:text id="quarterCompreSolvencyRate" name="quarterCompreSolvencyRate" col="8" value="${rootOrgInfo.quarterCompreSolvencyRate}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>--%>
                    <bf:formGroup col="6">
                        <bc:label text="权益类资产投资上限比例（%）" cls="required" col="4" formControl="true"/>
                        <bf:text id="equityUpperLimitScale" name="equityUpperLimitScale" col="8" value="${rootOrgInfo.equityUpperLimitScale}"  placeholder="请按百分比填写">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>

            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/rootOrgInfo/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/rootOrgInfo/list';
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
                            window.location = '${base}/bus/rootOrgInfo/list';
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
    /*start*/
    $(document).ready(function () {
        /*向下拉框中填入数据*/
        $(function(){
            var dd = new Date();
            var currentYear = dd.getFullYear();
            var years = [];
            for(var i = 1; i <= 10; i++){
                years.push(currentYear--);
            }
            // console.log(years);

            // var size = currentYear - 10 + 1;
            //年份回显
            var year = parseInt($("#editYear").val());
            if(year != undefined && year != ''){
                for( var j=0; j<years.length; j++ ){
                    var yearOld = currentYear + j + 1;
                    if(yearOld == year){
                        $("#years").append($("<option value="+year+" selected>"+year+"年"+"</option>"));
                        continue;
                    }
                    $("#years").append($("<option value="+yearOld+">"+yearOld+"年"+"</option>"));
                }
            }else {
                for( var i=0; i<years.length; i++ ){
                    var yearOld = currentYear + i + 1;
                    $("#years").append($("<option value="+yearOld+">"+yearOld+"年"+"</option>"));
                }
            }
        });

        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });


    /* 获取选中的下拉框的值 */
    function yearSelected(){
        var valueSel = $("#years").find("option:selected").val();
        console.info("选中的option的value值为："+valueSel);/* value值，如2017 */
        equityUpperLimitScaleRequired();
    }

    function equityUpperLimitScaleRequired(){
        var selectYear = $('#years').find("option:selected").val();
        console.log(selectYear)
        var date = new Date();
        var year = date.getFullYear();
        if(selectYear == year) {
            $(".control-label").each(function (index,item) {
                if(index == 5) {
                    var $this = $(this);
                    console.log($this)
                    console.log(item)
                    $(this).attr('className', 'control-label col-xs-4 ')
                    $(this).html("权益类资产投资上限比例（%）");
                }
            });
        }else {
            $(".control-label").each(function (index,item) {
                if(index == 5) {
                    var $this = $(this);
                    console.log($this)
                    console.log(item)
                    $(this).attr('className', 'control-label col-xs-4 required ')
                    $(this).html("<font color='red'>✶</font>" + "权益类资产投资上限比例（%）");
                }
            });
        }
    }

    /*
        $("#years").datetimepicker({
            format:"yyyy",
            autoclose: true,
            startView:4,
            minView:4,
            todayHighlight:true
        });
    */

    /*end*/

</script>
</body>
</html>
