<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>机构黑名单</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="机构黑名单" subTitle="请输入机构黑名单查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<%--<bf:formGroup col="4">--%>
						<%--<bc:label text="自增主键" col="5" formControl="true"/>--%>
						<%--<bf:text name="__LIKES_pid" col="7" placeholder="自增主键"/>--%>
					<%--</bf:formGroup>--%>
					<bf:formGroup col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_OrgName" col="7" placeholder="关联方名称"/>
					</bf:formGroup>
					<bf:formGroup col="4" >
						<bc:label text="统一社会信用代码" col="5" formControl="true"/>
						<bf:text name="__LIKES_uniscCode" col="7" placeholder="统一社会信用代码"/>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
				<bf:formGroup col="4" type="success">
					<bc:label text="加入黑名单日期" col="5" formControl="true"></bc:label>
					<bf:datebox name="beginDate" id="beginDate" col="6" format="date">
					</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="2" type="success">
						<bf:datebox id="endDate" name="endDate" col="12" format="date">
						</bf:datebox>
					</bf:formGroup>
					<%--状态  未加字段  --%>
					<%--<bf:formGroup col="4">--%>
						<%--<bc:label text="状态" col="3" formControl="true"/>--%>
						<%--<bf:select name="__LIKES_status" col="5" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Applystatus" >--%>
							<%--<bf:option text="ALL" value="%"></bf:option>--%>
							<%--<be:event type="select">--%>
								<%--var input = $("#__LIKES_status");--%>
								<%--input.focus();--%>
								<%--input.blur();--%>
							<%--</be:event>--%>
						<%--</bf:select>--%>
					<%--</bf:formGroup>--%>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="legalPersonBlackTable" idField="pid" url="${base}/bus/legalPersonBlack">
				<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
				<bc:toolbar id="confirmWithCustom" text="移出黑名单"  type="button" handler="remove"/>
				<bc:toolbar id="importSimple" text="数据导出" type="button" handler="importHandler"/>
				<bc:column field="pid" title="自增主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
				<bc:column field="orignalOrg" title="源主体银行" width="100" visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行名称" width="100" visible="false"/>
				<bc:column field="orgName" title="机构名称" width="100"/>
				<bc:column field="orgType" title="企业类型" width="100" visible="false"/>
				<bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="uniscCode" title="社会统一信用代码" width="100"  visible="false"/>
				<bc:column field="regCapital" title="注册资本(万元/人民币)" width="100" visible="false"/>
				<bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
				<bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
				<bc:column field="status" title="机构状态（1-正常，2-停业）" width="100" visible="false"/>
				<bc:column field="legalPerson" title="法定代表人" width="100" visible="false"/>
				<bc:column field="regAddress" title="注册地" width="100" visible="false"/>
				<bc:column field="reportVersion" title="报送版本号" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" visible="false"/>
				<bc:column field="isReported" title="是否被报送过（0-历史上从未报送过，1-已被报送过）" width="100" visible="false"/>
				<bc:column field="identifyType" title="证件类型" width="100" />
				<bc:column field="identifyNumber" title="证件代码" width="100" />
				<bc:column field="blackDate" title="计入黑名单日期" width="100" />
				<bc:column field="blackCause" title="计入黑名单原因" width="100" />
				<bc:column field="commitName" title="提交人" width="100" visible="false"/>
				<bc:column field="commitDutyCode" title="提交人职位" width="100" visible="false"/>
				<bc:column field="checkName" title="复核人" width="100" visible="false"/>
				<bc:column field="checkDutyCode" title="复核人职位" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
				<bc:column field="bodyType" title="机构类型" width="100" visible="false"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    /*
    * 移出黑名单的触发事件
    * */
    function remove(){
        var status=getIdSelections();
        if (status == 'run'){
            alert('正在审批中！')
        } else if (status == 'fail'){
            alert('提交成功！请耐心等候。。')
        }
    }
    function getIdSelections() {
        return $.map($('#legalPersonBlackTable').bootstrapTable('getSelections'), function (row) {
            return row.status//返回数据行中的id值
        });
    }
</script>
</body>
</html>