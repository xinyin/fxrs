<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>自然人关联解除名单</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="关联方名称" col="5" formControl="true"/>
						<bf:text name="" col="7" placeholder="关联方名称"/>
					</bf:formGroup>

					<bf:formGroup col="4" >
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:text name="" col="7" placeholder="请输入证件类型"/>
					</bf:formGroup>
					<bf:formGroup col="4" >
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text name="" col="7" placeholder="请输入证件号码"/>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4" type="success">
						<bc:label text="解除日期" col="5" formControl="true"></bc:label>
						<bf:datebox name="beginDate" id="beginDate" col="7" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="4" type="success">
						<bc:label text="" col="5" formControl="true"></bc:label>
						<bf:datebox id="endDate" name="endDate" col="7" format="date">
						</bf:datebox>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup>
						<bc:col col="12" colOffset="5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
						</bc:col>
					</bf:formGroup>

				</bc:row>
			</bf:form>
			<bc:table id="naturalPersonRemoveTable" idField="pid" url="${base}/bus/naturalPersonRemove">
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100"/>
				<bc:column field="orignalOrg" title="源主体机构" width="100" visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行名称" width="100" visible="false"/>
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="sex" title="性別" width="100" visible="false"/>
				<bc:column field="nationality" title="国别/地区" width="100" visible="false"/>
				<bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="race" title="民族" width="100" visible="false"/>
				<bc:column field="identifyType" title="证件类型" width="100"/>
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
				<bc:column field="mobile" title="移动电话" width="100" visible="false"/>
				<bc:column field="email" title="邮箱" width="100" visible="false"/>
				<bc:column field="reportVersion" title="报送版本号" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-ValidStatus"/>
				<bc:column field="dutyCode" title="职务编码" width="100" visible="false"/>
				<bc:column field="maritalStatus" title="婚姻状况" width="100" visible="false"/>
				<bc:column field="status" title="状态    未提交/已提交/未审批" width="100" visible="false"/>
				<bc:column field="isReported" title="是否被报送过（0-历史上从未报送过，1-已被报送过）" width="100" visible="false"/>
				<bc:column field="removeDate" title="解除时间" width="100"/>
				<bc:column field="removeCause" title="关联解除原因" width="100"/>
				<bc:column field="commitName" title="提交人" width="100"/>
				<bc:column field="commitDutyCode" title="提交人职位" width="100" visible="false" />
				<bc:column field="checkName" title="复核人" width="100"/>
				<bc:column field="checkDutyCode" title="复核人职位" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false" />
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false" />
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>