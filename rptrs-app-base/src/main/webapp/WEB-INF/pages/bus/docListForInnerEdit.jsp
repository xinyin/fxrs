<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Doc</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="行内制度" subTitle="" col="12" collapsible="true">
			<%--<bf:form id="queryForm" validation="true">
                <bf:hidden name="systemType" id="systemType" value="I"></bf:hidden>
                <bc:row cls="m-b">
					<bf:formGroup col="3">
						<bc:label text="制度名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_sysName" col="6" placeholder="制度名称"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="文号" col="5" formControl="true"/>
						<bf:text name="__LIKES_articleNumber" col="6" placeholder="文号"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="发文部门" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dispatchDep" col="6" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_DispatchDep">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="时效性" col="5" formControl="true"/>
                        <bf:select name="__LIKES_isVal" col="6" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_IsValid">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="3" type="success">
						<bc:label text="生效时间" col="3" formControl="true"></bc:label>
						<bf:datebox name="__LIKES_beginDate" id="__LIKES_endDate" col="8" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="3" type="success">
						<bc:label text="失效时间" col="3" formControl="true"></bc:label>
						<bf:datebox id="__LIKES_endDate" name="__LIKES_endDate" col="8" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>--%>


			<bf:formGroup hr="dashed" hrp="bottom">
				<bc:col col="6">
					<bc:label text="制度名称" col="4" formControl="true"/>
					<bf:text id="sysName" name="sysName" col="8" value="${suspectPersonEquityCompany.pid}">
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="文号" col="4" formControl="true"/>
					<bf:text id="articleNumber" name="articleNumber" col="8" value="${suspectPersonEquityCompany.companyName}">
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="时效性" col="4" formControl="true"/>
					<bf:text id="isVal" name="isVal" col="8" value="${suspectPersonEquityCompany.companyName}">
					</bf:text>
				</bc:col>
			</bf:formGroup>

			<bf:formGroup hr="dashed" hrp="bottom">
				<bc:col col="6">
					<bc:label text="发文部门" col="4" formControl="true"/>
					<bf:text id="dispatchDep" name="dispatchDep" col="8" value="${suspectPersonEquityCompany.pid}">
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="生效日期" col="4" formControl="true"/>
					<bf:datebox name="beginDate" id="endDate" col="8" format="date">
					</bf:datebox>
				</bc:col>

				<bf:formGroup col="3" type="success">
					<bc:label text="失效时间" col="3" formControl="true"></bc:label>
					<bf:datebox id="endDate" name="endDate" col="8" format="date">
					</bf:datebox>
				</bf:formGroup>
			</bf:formGroup>

			<bc:table id="docTable" idField="pid" url="${base}/bus/docForInner">
				<bc:toolbar id="uploadFile" text="上传文件" type="button" handler="exportIn"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="sysName" title="文件名" width="100"/>
				<bc:column field="articleNumber" title="文件类型" width="100"/>
				<bc:column field="dispatchDep" title="文件大小" width="100"/>
				<bc:column field="isVal" title="上传机构" width="100"/>
				<bc:column field="effectiveTime" title="上传部门" width="100"/>
				<bc:column field="expiryTime" title="上传时间" width="100"/>
				<bc:column field="expiryTime" title="操作" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter" id="hiddenBtn" style="display: none">
		文件上传按钮
	</button>
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Modal title</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label for="file">上传文件</label>
							<a href="javascript:;" class="file">选择文件
								<input type="file" class="form-control" id="file" name="file" multiple="multiple" style="padding: 15px !important; height: 59px !important;">
							</a>

						</div>
						<div class="form-group"  style="margin-bottom: 15px;height: 90px !important;">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-primary">上传</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function exportIn() {
        alert(666);
        $("#hiddenBtn").click();
    }
</script>
</body>
</html>