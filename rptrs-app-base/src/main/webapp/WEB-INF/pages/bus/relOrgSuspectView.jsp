<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>疑似机构</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<%--<bf:hidden id="opt" name="opt" value="${opt}"/>--%>
			<%--隐藏标签  用于必须字段--%>
			<bf:hidden id="pid" name="pid" value="${relOrgSuspect.pid}"/>
			<bc:ibox id="relOrgSuspectBox" title="基本信息查看" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<%--<bf:hidden id="opt" name="opt" value="${opt}"/>--%>
				<bf:hidden id="pid" name="pid" value="${relOrgSuspect.pid}"/>
				<%--<bf:formGroup>--%>
					<%--<div class="col-sm-4 col-sm-offset-2">--%>
						<%--<bc:button type="info" text="基本信息">--%>
							<%--<be:event>--%>
							<%--</be:event>--%>
						<%--</bc:button>--%>
						<%--<bc:button type="info" text="自然人关系信息">--%>
							<%--<be:event>--%>
								<%--affiliateInformation();--%>
							<%--</be:event>--%>
						<%--</bc:button>--%>
						<%--<bc:button type="info" text="机构关系信息">--%>
							<%--<be:event>--%>
									<%--holderInformation();--%>
							<%--</be:event>--%>
						<%--</bc:button>--%>
					<%--</div>--%>
				<%--</bf:formGroup>--%>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="机构名称:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.orgName}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内外标识:" col="4" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relOrgSuspect.domesticOrForeign}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型:" col="4" formControl="true"/>
						<bc:label text="统一社会信用代码" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.uniscCode}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="注册地:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.regAddress}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称:" col="4" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relOrgSuspect.mgerOrg}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="企业类型:" col="4" formControl="true"/>
						<bf:select id="orgType" name="orgType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrgSuspect.orgType}">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="营业范围:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.businessScope}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="注册资本(万元/人民币):" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.regCapital}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="注册资本币种代码:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.regCapitalCurrency}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法定代表人:" col="4" formControl="true"/>
						<bc:label text="${relOrgSuspect.legalPerson}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构状态:" col="4" formControl="true"/>
						<bf:select id="status" name="status" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Status" value="${relOrgSuspect.status}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relOrgSuspect/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
				</bf:form>
			</bc:ibox>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    <%--function basicInformation() {--%>
        <%--var opt = $("#opt").val();--%>
        <%--if(opt == "edit"){--%>
            <%--var pid = $("#pid").val();--%>
            <%--window.location = '${base}/bus/relPsn/edit/'+ pid+'?';--%>
        <%--}else{--%>
            <%--window.location = '${base}/bus/relPsn/add';--%>
        <%--}--%>
    <%--}--%>
    function affiliateInformation() {
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/viewSub/'+pid;
		}
    }

    function holderInformation(){
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/viewHolder/'+pid;
        }
	}
</script>
</body>
</html>