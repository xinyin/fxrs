<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #fundsInvestmentTable_tb_add, #fundsInvestmentTable_tb_edit,
        #fundsInvestmentTable_tb_remove{
            display: none;
        }
    </style>
    <title>MsgDeploy</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="资金运用类投资余额管理" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="investmentBalanceTimes" name="investmentBalanceTime" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="月度" col="5" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Monthes">
                            <bf:option selected="selected" text="本年度" value="0"></bf:option>
                            <be:event type="select">
                                var input = $("#quarter");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bc:col col="1"></bc:col>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frReset()"  />
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="fundsInvestmentTable" idField="pid" url="${base}/bus/fundsInvestment"
                      queryUrl="query1">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="investmentBalanceTimeString" title="投资余额统计时间" width="100"/>
                <bc:column field="realEstateBalance" title="不动产类资产投资余额(万元)" width="100"/>
<%--                <bc:column field="unlistedBalance" title="未上市权益类资产投资余额" width="100"/>--%>
                <bc:column field="foreignBalance" title="境外投资余额(万元)" width="100"/>
<%--                <bc:column field="otherFinanceBalance" title="其他类金融资产投资余额" width="100"/>--%>
                <bc:column field="fixedIncomeBalance" title="固定收益类资产投资余额(万元)" width="100"/>
<%--                <bc:column field="listedBalance" title="上市权益类资产投资余额" width="100"/>--%>
                <bc:column field="balanceInvestment" title="权益类资产投资余额(万元)" width="100"/>
                <bc:column field="insuranceManagementBalance" title="保险资产管理产品投资余额(万元)" width="100"/>
                <bc:column field="bankFinancialBalance" title="商业银行理财产品投资余额(万元)" width="100"/>
                <bc:column field="bankNegotiableSecuritiesBalance" title="银行业金融机构信资产支持证券投资余额(万元)" width="100"/>
                <bc:column field="trustSchemeBalance" title="信托公司集合资金信托计划投资余额(万元)" width="100"/>
                <bc:column field="negotiableSecuritiesSpecialPlanBalance" title="证券公司专项资产管理计划投资余额(万元)" width="100"/>
                <bc:column field="insuranceFundsSupportPlanBalance" title="保险资管公司发行资产支持计划投资余额(万元)" width="100"/>
                <bc:column field="otherInvestmentBalance" title="其他投资余额(万元)" width="100"/>
                <bc:column field="allInvestmentBalance" title="全部投资余额(万元)" width="100"/>
                <bc:column field="mobilityBalance" title="流动性资产投资余额(万元)" width="100"/>
                <bc:column field="status" title="状态" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS_FundsStatus"/>
                <bc:operator id="view" icon="eye" title="操作"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //根据权限显示按钮
    $(function () {
        var code = "${code}";
        if (code.search("01,") != -1) {
            $('#fundsInvestmentTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#fundsInvestmentTable_tb_edit').show();
        }
        if (code.search("03") != -1) {
            $('#fundsInvestmentTable_tb_remove').show();
        }
    });

    $(document).ready(function () {
        // $("input[name='__LIKES_years']").val(new Date().getUTCFullYear());
        $("#investmentBalanceTimes").datetimepicker("setDate", new Date());
        changestyle();

    });
    $("#investmentBalanceTimes").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#investmentBalanceTimes").change(function () {
        if (
            $("#investmentBalanceTimes").datetimepicker("getDate") == "" ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == null ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == undefined
        ) {
            $("#investmentBalanceTimes").datetimepicker("setDate", new Date());
        }
    });
    /*获取pid*/
    function getIdSelections() {
        return $.map($('#fundsInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    //获取状态
    function getStausSelections() {
        return $.map($('#fundsInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }
    //编辑
    function editHandler() {

        var pid = getIdSelections();

        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }else{
            window.location ='${base}/bus/fundsInvestment/edit/'+pid;
        }
    }
    //删除
    function removeHandler() {
        var pid = getIdSelections();
        var status=getStausSelections();
      /*  if(status=='1'){
            $.messager.alert('错误', "只能删除待补录数据", 'error');
            return;
        }*/
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }else{
            $.ajax({
                url: '${base}/bus/fundsInvestment/remove/'+pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/fundsInvestment/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });

        }

        <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
    }
    //重置
    function frReset() {
        window.location = '${base}/bus/fundsInvestment/list';
    }

    // $('#fundsInvestmentTable').on("click-row.bs.table",function(e, row, $element) {
    //     //获取当前选中行号
    //     console.log(e)
    //     console.log(row)
    //     console.log($element)
    //     $element.css.backgroundColor='#FFFF00'
    // })
    function changestyle() {
        // let tr = document.querySelectorAll("tr");
        // for (let i = 0; i < tr.length; i++) {
        //     // 找到各自行里的列
        //     let td = tr[i].querySelectorAll("td");
        //     console.log(td)
        //     // 找每行的单数列
        //     for (let j = 0; j < td.length; j += 2) {
        //         // 换背景颜色
        //         console.log(j)
        //         td[j].style.backgroundColor = 'lightblue';
        //     }
        // }
    }

</script>

</body>
</html>
