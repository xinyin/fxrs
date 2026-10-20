<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="list"/>
<title>规则集</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="规则模型" subTitle="这是被外部系统调用的模型" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_id" col="7" placeholder="中文名称"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
						<bc:button id="btnReset" type="default" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="packagesTable" idField="id" url="${base}/rul/packages">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="addRules" text="管理规则" type="button" handler="setRules" />
				<bc:toolbar id="deploy" text="部署" type="button" handler="deploy" />
				<bc:toolbar id="unDeploy" text="反部署" type="button" handler="unDeploy"/>
				<bc:toolbar id="test" text="测试" type="button" handler="test" />

				<bc:column field="id" title="主键" width="100" visible="false"/>
				<bc:column field="code" title="英文名称" width="100"/>
				<bc:column field="name" title="中文名称" width="100"/>
				<bc:column field="type" title="是否评分" width="100" formatter="fixedDict:YesNo"/>
				<bc:column field="state" title="状态" width="100"/>

				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function setRules(){
        // 维护规则内容
		var rows= $("#packagesTable").jTable("getSelections");
		if(!rows[0]){
			$.messager.toastr('请选择要编辑的模型');
		}else{
			var title=rows[0].name+"编辑";
			var packageId=rows[0].id;
			window.location = "${base}/rul/packages/rules?packagesId="+packageId;
		}
	}

    /**
     * 部署规则模型
     */
    function test() {
        var rows= $("#packagesTable").jTable("getSelections");
        if(!rows[0]){
            $.messager.toastr('请选择要部署的模型');
        }else{
			$.messager.modal({
				winId: 'winObjsAdd',
				width: 800,
				height: 400,
				title: '规则测试',
				url: "${base}/rul/packages/testView?pkgId=" + rows[0].id,
				onClose: function(){
					<%--$("#inOutObjTable").bootstrapTable('refresh');--%>
				}
			});
		}
    }

    /**
	 * 部署规则模型
     */
	function deploy() {
        var rows= $("#packagesTable").jTable("getSelections");
        if(!rows[0]){
            $.messager.toastr('请选择要部署的模型');
        }else{
           $.mask.post("${base}/rul/packages/deploy",{pkgId:rows[0].id},function(data){
				if(data.success)
				{
                    $.messager.toastr('提示','部署成功','success');
				}
				else
				{
                    $.messager.toastr('提示','部署失败','error');
				}
		   });
        }
    }

    /**
     * 反部署规则模型
     */
    function unDeploy() {
        var rows= $("#packagesTable").jTable("getSelections");
        if(!rows[0]){
            $.messager.toastr('请选择要部署的模型');
        }else{
            $.mask.post("${base}/rul/packages/unDeploy",{pkgId:rows[0].id},function(data){
                if(data.success)
                {
                    $.messager.toastr('提示','反部署成功','success');
                }
                else
                {
                    $.messager.toastr('提示','反部署失败','error');
                }
            });
        }
    }
</script>
</body>
</html>