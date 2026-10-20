<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>自然人</title>
	<link rel="stylesheet" href="../../static/css/dialog.css">
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="自然人" subTitle="请输入自然人查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="关联方名称" col="5" formControl="true"/>
						<bf:text id="orignalOrgName" name="__LIKES_orignalOrgName" col="7" placeholder="源主体银行或保险机构名称" value="${orignalOrgName}"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:text name="__LIKES_identifyType" col="7" placeholder="证件类型"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="状态" col="5" formControl="true"/>
						<bf:text id="status" name="__LIKES_status" col="7" placeholder="状态" value="${status}"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="naturalPersonTable" idField="pid" url="${base}/bus/naturalPerson">
				<bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="confirmWithCustom" text="解除关联关系"  handler="dell" type="button" />
                <bc:toolbar id="addblackList" text="加入黑名单"  handler="addblackList" type="button"/>
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
                <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
				<bc:toolbar id="out" text="数据导出" handler="addblack" type="button"/>
                <bc:toolbar id="exportHandlerHis" text="下载导入模板" type="button" handler="exportHandler"/>
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100" />
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="sex" title="性別" width="100"  visible="false"/>
				<bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign" />
				<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"  />
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="reportVersion" title="报送版本号" width="100"  visible="false"/>
                <bc:column field="status" title="状态" width="100"  visible="false"/>
                <bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-ValidStatus"/>
				<bc:column field="isReported" title="是否被报送（0-未报送，1-已被报送）" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IsReported"  visible="false"/>
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

    $(document).ready(function(){ $('#inBtn').mouseup(function(){ $.messager.toastr('提示', '测试按钮导入'); }); });
    $(document).ready(function(){ $('#outBtn').mouseup(function(){ $.messager.toastr('提示', '测试按钮导出'); });
    });
	function exportHandler(){
		var data = "00"
		var url='${base}/sys/xlsie/NaturalPersonExportCallbackImpl/'+ data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}
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
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
        $.sendConfirm({
            title: '提交审批',
            content: '<div id="addIpBox">'
            + '<div class="frm-item">'
            + '<div class="frm-label"><span class="requireIcon">*</span> 部门:</div>'
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
    function importHandler(){
        $.messager.modal({
            winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
            width: 500,
            height: 230,
            args: {
                callback:loadCallback
            },
            title: '自然人信息导入',
            url: '${base}/sys/xlsie/import?bean=NaturalPersonImportCallbackImpl',
            onClose: function(){
                loadCallback();
            }
        });
    }
    function addblackList(){
        $.sendConfirm({
            hideHeader: true,
            withCenter: true,
            msg: '您确定将其加入黑名单？',
            button: {
                confirm: '确认',
                cancel: '取消'
            },
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
    function dell() {
        $.sendConfirm({
            title: '解除关联关系',
            content: '<div id="addIpBox">'
            + '<div class="frm-item">'
            + '<div class="frm-label"><span class="requireIcon">*</span> 解除关联关系原因:</div>'
            + '<textarea style="border:0;border-radius:5px;background-color:rgba(241,241,241,.98);width: 355px;height: 100px;padding: 10px;resize: none;" placeholder="请说明原因"></textarea>'
            + '</div>'
            + '<div>'
            +'<span class="requireIcon">*</span><span>x选择审批人</span>'
            + '</div>'
            + '<div class="frm-item">'
            + '<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 部门:</div>'
            + ' <select class="customerIds"  style="width:22em"><option >请选择</option><option >部门1</option><option >部门2</option></select>'
            + '</div>'
            + '<div class="frm-item">'
            + '<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 审批人:</div>'
            + ' <select class="customerIds" style="width:22em"><option >请选择</option><option >审批人1</option><option >审批人2</option></select>'
            + '</div>'
            + '</div>',
            button: {
                confirm: '提交审批',
                cancel: '取消'
            },
            width: 260,
            // onBeforeConfirm: function() {
            // onBeforeConfirm返回false，将阻止onConfirm的执行
            //     return false;
            //   },
            onConfirm: function() {
                console.log('点击确认！');
                $("body").on("blur","input[name='ip']",function(){
                    console.log(this.value);
                    //如果输入框为空，则替换为0
                    if(this.value == ""){
                        this.value = 0;
                    }
                });
                // var value = document.getElementsByName("ip")[0].value;
                // console.log(value);
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