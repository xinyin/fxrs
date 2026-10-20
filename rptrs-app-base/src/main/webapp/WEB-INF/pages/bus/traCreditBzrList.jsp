<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>关联交易表-授信-保证人信息表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联交易表-授信-保证人信息表" subTitle="请输入关联交易表-授信-保证人信息表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="自增主键" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="自增主键"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="traCreditBzrTable" idField="pid" url="${base}/bus/traCreditBzr">
				<%--<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>--%>
				<bc:column field="creditId" title="授信类ID" width="100"/>
				<bc:column field="guarantorName" title="保证人名称" width="100"/>
				<bc:column field="identlfyType" title="证件类型" width="100"/>
				<bc:column field="identNumber" title="证件号码" width="100"/>
				<bc:column field="sex" title="性别" width="100"/>
				<bc:column field="relation" title="关系" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">
    function send(){
        $.ajax({
            type: "get",
            url: "/test/initData",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if(data.statusCode==100) {
                    var options = "<option>--请选择--</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        options+="<option>"+data.data[i].companyName+"</option>";
                    }
                    $(".customerIds").html(options);
                    //活动名称下拉选改变事件：
                    $(".customerIds").change(function () {
                        $(this).removeClass("redFrame");
                        console.log("活动名称下拉选改变事件："+$(this).children('option:selected').val());
                        console.log("选择的具体是哪一个："+$(this).get(0).selectedIndex);
                        var selectedIndex = $(this).get(0).selectedIndex;
                        if(selectedIndex==0){
                            console.log("没有进行选择");
                            $("#customerName").val("");
                            $("#customerMobile").val("");
                            $("#customerAddress").val("");
                            $("#customerWeb").val("");
                        }
                        else{
                            $("#customerName").val(data.data[selectedIndex-1].customer.customerName);
                            $("#customerMobile").val(data.data[selectedIndex-1].customer.customerMobile);
                            $("#customerAddress").val(data.data[selectedIndex-1].customer.customerAddress);
                            $("#customerWeb").val(data.data[selectedIndex-1].customer.customerWeb);
                        }
                    });
                }
                else{
                    console.log("获取数据失败");
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
        $.sendConfirm({
            title: '提交审批',
            content: '<div id="addIpBox" >'
                + '<div class="frm-item">'
                + '<div class="frm-label" >'
                + '<br>'
                +  '选择审批人：'
                + '<br>'
                + '<span class="requireIcon">*</span  > 部门:</div>'
                + ' <select class="customerIds"><option >请选择</option><option >合规部</option><option >审计部</option></select>'
                + '</div>'
                + '<div class="frm-item">'
                + '<div class="frm-label"><span class="requireIcon">*</span> 审批人:</div>'
                + ' <select class="customerIds"><option >请选择</option><option >审批人1</option><option >审批人2</option></select>'
                + '</div>'
                + '</div>',
            button: {
                confirm: '确认',
                cancel: '取消'
            },
            width: 260,
            // onBeforeConfirm: function() {},
            onConfirm: function() {
                console.log('点击确认！');
            },
            onCancel: function() {
                console.log('点击取消！');
            },
            onClose: function() {
                console.log('点击关闭！');
            }
        });
    }

    //刷新表格
    function loadCallback(){
        $('#naturalPersonTable').jTable('refresh');
    }
</script>

</body>
</html>