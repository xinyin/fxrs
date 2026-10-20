<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>MeetingManage</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle=" "   col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="会议名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_meetName" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="会议发起人" col="5" formControl="true"/>
						<bf:text name="__LIKES_meetInitiatorName" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="会议发起日期" col="5" formControl="true"/>
						<bf:datebox id="meetDate" name="__LIKES_meetDate" col="7" value="${meetingManage.meetDate}"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="会议内容" col="5" formControl="true"/>
						<bf:text name="__LIKES_meetDetails" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="参会人员" col="5" formControl="true"/>
						<bf:text name="__LIKES_conferee" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="会议发起部门" col="5" formControl="true"/>
						<bf:select id="meetDepart" name="__LIKES_meetDepart"  col="7" dataSource="custom"
								   url="sys/unit/selecttoDept" value="${meetingManage.meetDepart}">
						</bf:select>
					</bf:formGroup>

					<%--<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</bc:col>--%>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="meetingManageTable" idField="pid" url="${base}/bus/meetingManage">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="edit"/>
				<bc:toolbar id="remove" text="删除" type="button"  handler="removeHandler"/>
				<bc:column field="pid" title="主键" width="100" visible="false"/>
				<bc:column field="meetName" title="会议名称" width="100" />
				<bc:column field="meetDepart" title="会议发起部门" width="100" formatter="custom:sys/unit/selectAllUnit"/>
				<bc:column field="meetInitiatorName" title="会议发起人" width="100" />
				<bc:column field="meetDate" title="会议发起日期" width="100" />
				<bc:column field="conferee" title="参会人员" width="100"/>
				<bc:column field="meetDetails" title="会议内容" width="100"/>
				<bc:operator id="view" icon="search" title="详情"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
	//编辑
	function edit() {
        //获取pid
        var pid=getIdSelections();
        if(pid==''||pid==undefined){
            alert("请选择一条数据");
            return;
        }
        if(pid.indexOf(",")!=-1){
            alert("最多选择一条数据");
            return;
        }else{
            window.location='${base}/bus/meetingManage/edit/'+pid;
        }
    }

    //跳出选择外规页面

    function getIdSelections() {
        return $.map($('#meetingManageTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function removeHandler() {
        var pid = getIdSelections();
        if (confirm('您确定要删除该数据吗？')){
        $.ajax({
            url:"${base}/bus/meetingManage/remove/"+pid,
            method:"POST",
            data:{"":""},
            dataType:"JSON",
            traditional:true,
            success:function(data){
                if(data.success){
                    alert(data.msg);
                    window.location = '${base}/bus/meetingManage/list';
                }else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
		}
    }

/*  重置*/
	function frmReset(){
		window.location = '${base}/bus/meetingManage/list';
	}
</script>
</body>
</html>