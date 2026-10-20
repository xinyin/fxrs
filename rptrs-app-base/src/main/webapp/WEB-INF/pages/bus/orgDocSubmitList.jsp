<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>报送管理表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="报送管理表" subTitle="请输入报送管理表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="文件名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" />
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="报送机构" col="3" formControl="true"/>
						<bf:select id="submitOrg" name="__LIKES_submitOrg" col="8"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-org" value="${docSubmit.submitOrg}">
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="下载次数" col="3" formControl="true"/>
						<bf:text name="__LIKES_downloadCount" col="7" />
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:text name="__LIKES_year" col="7" placeholder="2020"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="季度" col="3" formControl="true"/>
						<bf:select id="quarter" name="__LIKES_quarter" col="8"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-quarter">
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="月份" col="3" formControl="true"/>
						<bf:select id="month" name="__LIKES_month" col="8"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-month">
						</bf:select>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4" type="success">
						<bc:label text="生成报送时间" col="5" formControl="true"></bc:label>
						<bf:datebox name="__LIKES_beginDate" id="beginDate" col="6" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="2" type="success">
						<bf:datebox id="endDate" name="__LIKES_endDate" col="12" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="6" type="success">
						<bc:label text="数据校验结果" col="6" formControl="true"></bc:label>
						<bf:select id="isSuccess" name="__LIKES_isSuccess" col="4"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-checkResult">
						</bf:select>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="docSubmitTable" idField="pid" url="${base}/bus/orgDocSubmit">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="remove1" text="删除" type="button" handler="del"/>
				<bc:toolbar id="download" text="下载文件" type="button" handler="downloadFile"/>
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="fileName" title="文件名称" width="100"/>
				<bc:column field="submitOrg" title="报送机构" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-org" />
				<bc:column field="year" title="年度" width="100"/>
				<bc:column field="quarter" title="季度" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-quarter" />
				<bc:column field="month" title="月份" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-month"/>
				<bc:column field="generateTime" title="生成报送文件时间" width="100"/>
				<bc:column field="isSuccess" title="数据校验结果" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-checkResult"/>
				<bc:column field="downloadCount" title="下载次数" width="100"/>
				<bc:column field="submitType" title="报送类型" width="100" visible="false"/>
				<bc:column field="filePath" title="报送文件路径" width="100" visible="false"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function del() {
        var pid = getIdSelections();
        if(pid != ""){
            window.location = '${base}/bus/orgDocSubmit/remove/'+ pid+'?';
            $.messager.alert('删除成功');
        }else {
            $.messager.alert('id不可为空');
            return "";
        }
    }

    function  downloadFile() {
        var pid = getIdSelections();
        if(pid != ""){
            window.location = '${base}/bus/orgDocSubmit/download/'+ pid+'?';
            $.messager.alert('下载成功');
        }else {
            $.messager.alert('id不可为空');
            return "";
        }
    }
    function getIdSelections() {
        return $.map($('#docSubmitTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
</script>
</body>
</html>