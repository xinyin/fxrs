<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraPf</title>
    <style>
        .form-group .col-xs-4 {
            margin-top: 0.5%;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="gdTraPfBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="pid" name="pid" value="${gdTraPf.pid}"/>
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label cls="required" text="派发方案名称" col="5" formControl="true"/>
                    <bf:text id="name" name="name" col="7" value="${gdTraPf.name}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="派发年份" col="5" formControl="true"/>
                    <bf:text id="year" name="year" col="7" value="${gdTraPf.year}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="股利分配比例" col="5" formControl="true"/>
                    <bf:text id="allocationProportion" name="allocationProportion" col="7"
                             value="${gdTraPf.allocationProportion}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="现金分红" col="5" formControl="true"/>
                    <bf:text id="cashBonus" name="cashBonus" col="7" value="${gdTraPf.cashBonus}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="派送红股" col="5" formControl="true"/>
                    <bf:text id="bonusIssue" name="bonusIssue" col="7" value="${gdTraPf.bonusIssue}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12" colOffset="5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/gdTraPf/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/gdTraPf/edit/' + data.pid;
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
                            window.location = '${base}/bus/gdTraPf/list';
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
        </bf:form>
        <div class="row">
            <bc:ibox title="股东分配调整" subTitle="" col="12" collapsible="true">
                <bf:form id="queryForm" validation="true">
                    <bc:row cls="m-b">
                        <bf:formGroup col="4">
                            <bc:label text="股东账号" col="5" formControl="true"/>
                            <bf:text name="__LIKES_shareholdCode" col="7" placeholder="股东账号"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_name" col="7" placeholder="股东名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="境内/境外" col="5" formControl="true"/>
                            <bf:select id="domesticOrForeign" name="__LIKES_domesticOrForeign" col="7"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_domesticOrForeign");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <%--<bc:col col="3" colOffset="1">--%>
                            <%--<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
                            <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
                        <%--</bc:col>--%>
                    </bc:row>
                    <bc:row cls="m-b">
                        <bc:col col="12" colOffset="5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                        </bc:col>
                    </bc:row>
                </bf:form>
                <bc:table id="gdPfInfoTable" idField="pid" url="${base}/bus/gdPfInfo"
                          queryUrl="queryGdPfInfo?pfPid=${gdTraPf.pid}">
                    <bc:toolbar id="save" text="保存修改" type="button" handler="saveHandler"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="pfPid" title="方案主键" width="100" visible="false"/>
                    <bc:column field="gdPid" title="股东主键" width="100" visible="false"/>
                    <bc:column field="shareholdCode" title="股东账号" width="100"/>
                    <bc:column field="name" title="股东名称" width="100"/>
                    <bc:column field="gdType" title="股东类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                    <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="identifyNumber" title="证件号码" width="100"/>
                    <bc:column field="gdDj" title="账户冻结" width="100"/>
                    <bc:column field="tradeAccount" title="交易账户账号" width="100"/>
                    <bc:column field="holeShareAmount" title="持股数量" width="100"/>
                    <bc:column field="shareholdingProportion" title="持股占比" width="100"/>
                    <bc:column field="frozenShareAmount" title="已冻结股数" width="100"/>
                    <bc:column field="pledgedShareAmount" title="已质押股数" width="100"/>
                    <bc:column field="cashBonus" title="奖金分红" width="100" formatter="editFormatter"/>
                    <bc:column field="bonusIssue" title="派送红利" width="100" formatter="edit2Formatter"/>
<%--                    <bc:operator id="save" icon="save" title="保存" handler="saveHandler"/>--%>
                    <%--                            <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--                            <bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
        </div>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>

<script type="text/javascript">
    $("#approvalTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#applicaSubTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });


    $("#tranDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#validityContract").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    function getIdSelections() {
        return $.map($('#gdPfInfoTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function editFormatter(value, row, index) {
        return [
            '<input type="text" id="1plan' + row.pid + '" class="form-control edit1" data=' + value + ' value=' + value + ' onblur="addTarget(' + row.pid + ',1)"/>'
        ].join("");
    }

    function edit2Formatter(value, row, index) {
        return [
            '<input type="text"   id="2plan' + row.pid + '" class="form-control edit2" data=' + value + ' value=' + value + '  onblur="addTarget(' + row.pid + ',2)" />'
        ].join("");
    }

    var jsontarget = [];

    //id:这条数据的id  type :1,2,3:代表的是第几个目标值
    function addTarget(id, type) {
        var writevalue = $("#" + type + "plan" + id).val(); //获取改变后的输入框的值
        var oldvalue = $("#" + type + "plan" + id).attr("data"); //获取输入框原本的值
        if (!(writevalue == oldvalue)) {//通过判断输入框的值是否改变，是否写入jsontarget改变的json数据
            oldvalue = writevalue;
            $("#" + type + "plan" + id).attr("data", oldvalue);
            writeJson(id, type, writevalue);
            console.log(jsontarget);
        }
    }

    //写入改变的目标值的json数据
    function writeJson(id, type, writeValue) {
        for (var i = 0; i < jsontarget.length; i++) {
            if (jsontarget[i].pid == id)//说明现在的json中已经存在这个值
            {
                if (type == 1) {
                    jsontarget[i].cashBonus = writeValue;
                } else if (type == 2) {
                    jsontarget[i].bonusIssue = writeValue;
                }
                return;
            }
        }
        var obj = {pid: id};
        if (type == 1) {
            obj.cashBonus = writeValue;
        } else if (type == 2) {
            obj.bonusIssue = writeValue;
        }
        jsontarget.push(obj);
    }

    function saveHandler() {
        var objJson = JSON.stringify(jsontarget);
        var data = {
            data:objJson
        };
        $.ajax({
            url:'${base}/bus/gdPfInfo/editPf',
            data:data,
            type:'post',
            dataType:'json',
            success:function(data){
                if (data.success){
                    alert(data.msg);
                }
            }
        });
    }

    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });

</script>

</body>
</html>
