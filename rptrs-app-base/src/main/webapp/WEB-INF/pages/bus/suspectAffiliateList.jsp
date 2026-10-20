<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>关联关系自然人</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<bc:ibox title="关联关系自然人" subTitle="关联关系自然人附属信息" col="12" collapsible="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
			<bf:formGroup>
				<bc:col col="6" colOffset="2">
					<bc:button type="light" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button type="primary" text="自然人信息">
						<be:event>
							affiliateInformation();
						</be:event>
					</bc:button>
					<bc:button type="light" text="机构关系信息">
						<be:event>
							rightsInformation();
						</be:event>
					</bc:button>
				</bc:col>
			</bf:formGroup>
			<bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
				<bc:table id="affiliateTable" idField="pid" url="${base}/bus/relPsnSuspect" queryUrl="suspectAffiliateSelect/${fromPid}" addUrl="suspectAffiliateAdd/${fromPid}">
					<bc:toolbar id="add" text="新增" type="button"/>
					<bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>
					<bc:toolbar id="remove" text="删除" type="button" handler="delAffiliate"/>
					<bc:column field="pidForRel" title="关联关系表自增长主键" width="100"  visible="false"/>
					<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
					<bc:column field="name" title="姓名" width="100"/>
					<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
					<bc:column field="identifyNumber" title="证件号码" width="100"/>
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>



					<%--<bc:column field="relStatus" title="处理状态" formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" width="100"/>--%>
				</bc:table>
			</bc:ibox>

			<%--<bc:ibox title="下一级关联自然人" subTitle="" col="12" collapsible="true">--%>
				<%--<bc:table id="affiliateTable" idField="pid" url="${base}/bus/relPsnSuspect" queryUrl="suspectAffiliateSelect/${fromPid}" addUrl="suspectAffiliateAdd/${fromPid}">--%>
					<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
					<%--&lt;%&ndash;<bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>&ndash;%&gt;--%>
					<%--<bc:toolbar id="remove" text="删除" type="button" handler="delAffiliate"/>--%>
					<%--<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>--%>
					<%--<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>--%>
					<%--<bc:column field="name" title="姓名" width="100"/>--%>
					<%--<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>--%>
					<%--<bc:column field="identifyNumber" title="证件号码" width="100"/>--%>
					<%--<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>--%>
					<%--<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>--%>
					<%--<bc:column field="relStatus" title="处理状态" formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" width="100"/>--%>
				<%--</bc:table>--%>
			<%--</bc:ibox>--%>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	function basicInformation() {
		var opt = $("#opt").val();
		if(opt == "edit"){
			var pid = $("#fromPid").val();
			window.location = '${base}/bus/relPsnSuspect/edit/'+ pid+'?';
		}else{
			window.location = '${base}/bus/relPsnSuspect/add';
		}
	}
	function affiliateInformation() {
		var opt = $("#opt").val();
		if(opt == "edit"){
			var pid = $("#fromPid").val();
			window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/' +pid +'? ';
		}else{
			window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/' +pid+'?';
		}
	}
	function rightsInformation() {
		var opt = $("#opt").val();
		if (opt == "edit"){
		var pid = $("#fromPid").val();
		window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' +pid +'? ';
		}else {
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/'+pid +'? ';
		}
	}
	function  editAffiliate() {
		var id = getIdSelections();
		var pidForPsnFrom = $("#fromPid").val();
		if(id != ""){
			window.location = '${base}/bus/relPsnSuspect/editAffiliate/' + id+"/"+ pidForPsnFrom;
		}else {
			$.messager.alert('提示', '请选择自然人。', 'info');
			return "";
		}
	}
	function getIdSelections() {
		return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
			return row.pidForPsnTo +"/"+ row.pidForRel;
		});
	}

    //删除
    function delAffiliate() {
        var pidForRel=getPidSelections();
        if(pidForRel==""||pidForRel==undefined){
            alert("请选中数据")
        }else{
            var r=confirm("确定要删除数据？");
            if(r){
                $.ajax({
                    url:'${base}/bus/relPsnSuspect/delAffiliate/'+pidForRel,
                    type: 'get',
                    dataType: "json",
                    success:function (res) {
                        alert(res.msg);
                        if(res.success){
                            window.location.reload();
                        }
                    }
                })
            }
        }
    }

    function getPidSelections() {
        return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
            return row.pidForRel;
        });
    }

</script>
</body>
</html>