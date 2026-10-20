<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>疑似关联方规则配置</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="疑似关联方规则-自然人" subTitle="" col="12" collapsible="true">
		<bc:table id="relPsnRuleTable" idField="pid" url="${base}/bus/relRule" queryUrl="querySusPsn">
			<bc:toolbar id="open" text="启用" type="button" handler="openPsnRule"/>
			<bc:toolbar id="close" text="停用" type="button" handler="closePsnRule"/>
			<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
			<bc:column field="ruleName" title="规则名称" width="100"/>
			<%--<bc:column field="ruleType" title="规则所属类型  SUSORG或SUSPSN" width="100"/>--%>
			<bc:column field="ruleStatus" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RuleStatus"/>
			<%--<bc:operator id="view" icon="search" title="查看"/>--%>
			<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
			<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
		</bc:table>
	</bc:ibox>

		<bc:ibox title="疑似关联方规则-机构" subTitle="" col="12" collapsible="true">
		<bc:table id="relOrgRuleTable" idField="pid" url="${base}/bus/relRule" queryUrl="querySusOrg">
			<bc:toolbar id="open" text="启用" type="button" handler="openOrgRule"/>
			<bc:toolbar id="close" text="停用" type="button" handler="closeOrgRule"/>
			<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
			<bc:column field="ruleName" title="规则名称" width="100"/>
			<%--<bc:column field="ruleType" title="规则所属类型  SUSORG或SUSPSN" width="100"/>--%>
			<bc:column field="ruleStatus" title="启用状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RuleStatus"/>
			<%--<bc:operator id="view" icon="search" title="查看"/>--%>
			<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
			<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
		</bc:table>
	</bc:ibox>

		<bc:ibox title="疑似关联机构-关键字列表" subTitle="" col="12" collapsible="true">
			<bc:table id="relKeywordsTable" idField="pid" url="${base}/bus/relKeywords">
				<bc:toolbar id="add" text="新增" type="button" handler="add"/>
				<%--<bc:toolbar id="edit" text="编辑" type="button" handler="edit"/>--%>
				<%--<bc:toolbar id="remove" text="删除" type="button"  handler="remove"/>--%>
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="keyNo" title="编号" width="100"/>
				<%--<bc:column field="ruleType" title="规则所属类型  SUSORG或SUSPSN" width="100"/>--%>
				<bc:column field="keyOrgName" title="企业名称关键字段" width="100"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>

	</div>

	<%--<div class='buttonPanel'  style="text-align: right;border:1px solid lightgrey;position: absolute;right: 20px; top: 100px;width: 200px;height: 200px">--%>
		<%--<input value="Close" id="btn1" onclick="hideDiv('pop-div');" type="button" />--%>
	<%--</div>--%>
</div>
<br:js pack="list"/>
<script>
	function remove(){
        var pid=getIdSelections();
            $.ajax({
                url:"${base}/bus/relKeywords/remove/"+pid,
                <%--url:"${base}/bus/relPsn/remove/"+pid,--%>
                method:"GET",
                data:{"":""},
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        // $.messager.alert('成功', data.msg, 'success');
                        $.messager.alert('提示', '删除成功。', 'info', function () {
                            window.location="${base}/bus/relRule/list";
                        });
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
	}


	function openPsnRule(){
	    //开启自然人规则
		var pid=getPsnIdSelections();
		var ruleStatus=getPsnStatusSelections();
        if (ruleStatus == '0'){
            $.ajax({
                url:"${base}/bus/relRule/edit/"+pid+"/1",
                <%--url:"${base}/bus/relPsn/remove/"+pid,--%>
                method:"GET",
                data:{"":""},
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        // $.messager.alert('成功', data.msg, 'success');
                        window.location="${base}/bus/relRule/list"
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        } else {
			$.messager.alert("已是启用状态");
        }
	}

    function closePsnRule(){
        //关闭自然人规则
        var pid=getPsnIdSelections();
        var ruleStatus=getPsnStatusSelections();
        if (ruleStatus == '1'){
            $.ajax({
                url:"${base}/bus/relRule/edit/"+pid+"/0",
                <%--url:"${base}/bus/relPsn/remove/"+pid,--%>
                method:"GET",
                data:{"":""},
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        // $.messager.alert('成功', data.msg, 'success');
                        window.location="${base}/bus/relRule/list"
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        } else {
			$.messager.alert("已是停用状态");
        }
    }

	function openOrgRule(){
		//开启机构规则
		var pid=getOrgIdSelections();
		var ruleStatus=getOrgStatusSelections();
		if (ruleStatus == '0'){
			$.ajax({
				url:"${base}/bus/relRule/edit/"+pid+"/1",
				<%--url:"${base}/bus/relPsn/remove/"+pid,--%>
				method:"GET",
				data:{"":""},
				dataType:"JSON",
				traditional:true,
				success:function(data){
					if(data.success){
						// $.messager.alert('成功', data.msg, 'success');
						window.location="${base}/bus/relRule/list"
					}else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			})
		} else {
			$.messager.alert("已是启用状态");
		}
	}

    function closeOrgRule(){
        //关闭机构规则
        var pid=getOrgIdSelections();
        var ruleStatus=getOrgStatusSelections();
        if (ruleStatus == '1'){
            $.ajax({
                url:"${base}/bus/relRule/edit/"+pid+"/0",
                <%--url:"${base}/bus/relPsn/remove/"+pid,--%>
                method:"GET",
                data:{"":""},
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        // $.messager.alert('成功', data.msg, 'success');
                        window.location="${base}/bus/relRule/list"
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
		} else {
			$.messager.alert("已是停用状态");
		}
    }


    function getIdSelections() {
        return $.map($('#relKeywordsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getPsnIdSelections() {
        return $.map($('#relPsnRuleTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function getPsnStatusSelections() {
        return $.map($('#relPsnRuleTable').bootstrapTable('getSelections'), function (row) {
            return row.ruleStatus;
        });
    }

    function getOrgIdSelections() {
        return $.map($('#relOrgRuleTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function getOrgStatusSelections() {
        return $.map($('#relOrgRuleTable').bootstrapTable('getSelections'), function (row) {
            return row.ruleStatus;
        });
    }


</script>

<be:processEvent/>
</body>
</html>