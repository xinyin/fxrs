<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>法人</title>
    <style>
        .container {width: 600px; margin: 20px auto;}
        button { padding: 0 10px; }
        .text-info { color: #ff7300;}

        .mt10{
            margin-top: 10px;
        }

        .mt20{
            margin-top: 20px;
        }

        .hide {
            display: none;
        }

        #formBox {
            width: 550px;
            border: 1px solid #ddd;
            padding: 20px;
            margin: 20px auto;
        }

        .frm-item {
            line-height: 32px;
        }

        .frm-item + .frm-item {
            margin-top: 10px;
        }

        .frm-label {
            display: inline-block;
            width:9em;
            font-size: 14px;
			text-align: right;
        }

        .frm-input {
            width: 120px;
            height: 30px;
            border: 1px solid #ddd;
            padding: 0 10px;
        }

        .frm-submit {
            display: inline-block;
            min-width: 50px;
            height: 32px;
            padding: 0 10px;
            font-size: 14px;
            line-height: 32px;
            text-align: center;
            color: #fff;
            background-color: #0D76B4;
            text-decoration: none;
        }

        .frm-submit.disabled,
        .frm-submit.disabled:hover {
            background-color: #BCB7B7;
            cursor: not-allowed;
        }

        .frm-submit + .frm-reset {
            margin-left: 10px;
        }

        .frm-reset {
            display: inline-block;
            width: 60px;
            height: 32px;
            font-size: 14px;
            line-height: 32px;
            text-align: center;
            color: #fff;
            background-color: #B9A5CE;
            text-decoration: none;
        }

        .frm-submit:hover {
            background-color: #0F5883;
        }

        i.required {
            padding-right: 5px;
            color: #f00;
        }

        #addIpBox .msg-box {
            padding-left: 98px;
            color: #c33;
        }
        .dialog-confirm .dialog-submit {
            border: none;
            background-color: #6f55c0;
            color: #fff;
            text-align: center;
            text-decoration: none; }
        .dialog-confirm .dialog-submit:hover {
            text-decoration: none;
            background-color: #8032f5; }
        .dialog-confirm .disabled.dialog-submit, .dialog-confirm .disabled.dialog-submit:hover {
            border: 1px solid #ddd;
            color: #999;
            background-color: #f8f8f8;
            cursor: not-allowed; }

        .dialog-confirm .dialog-cancel {
            border: 1px solid #ddd;
            color: #666;
            text-align: center;
            text-decoration: none; }
        .dialog-confirm .dialog-cancel:hover {
            color: #6f55c0;
            border-color: #6f55c0;
            text-decoration: none; }
        .dialog-confirm .disabled.dialog-cancel, .dialog-confirm .disabled.dialog-cancel:hover {
            border: 1px solid #ddd;
            color: #999;
            background-color: #f8f8f8;
            cursor: not-allowed; }

        .dialog-background {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            z-index: 990;
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
            background-color: rgba(0, 0, 0, 0.5);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#88000000,endColorstr=#88000000); }

        .dialog-section {
            opacity: 0;
            filter: alpha(opacity=0);
            display: none;
            position: absolute;
            top: 50%;
            left: 50%;
            z-index: 999;
            padding: 0;
            margin: 0;
            background-color: #FFF;
            box-shadow: 0 0 8px rgba(0, 0, 0, 0.5); }

        .dialog-body,
        .dialog-imagelist {
            box-sizing: content-box;
            z-index: 1;
            min-width: 516px;
            height: 100%;
            padding: 32px;
            padding-top: 48px;
            margin: 0;
            overflow: hidden; }
        .dialog-body.withNoPadding,
        .dialog-imagelist.withNoPadding {
            padding: 0; }
        .dialog-body.withNoMinWidth,
        .dialog-imagelist.withNoMinWidth {
            min-width: auto;
            min-width: 0; }

        .dialog-body img {
            display: block;
            vertical-align: top; }

        .dialog-btnPrev,
        .dialog-btnNext {
            position: absolute; }

        .dialog-btnPrev,
        .dialog-btnNext {
            top: 50%;
            z-index: 9;
            margin-top: -18px;
            width: 36px;
            height: 36px;
            overflow: hidden;
            font: 16px/36px 'Simsun';
            text-align: center;
            color: #fff !important;
            cursor: pointer;
            background-color: #7F7F7F;
            background-color: rgba(0, 0, 0, 0.5); }

        .dialog-btnPrev {
            left: 0; }

        .dialog-btnNext {
            right: 0; }

        .dialog-disabled {
            cursor: not-allowed;
            background-color: rgba(0, 0, 0, 0.3);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#44000000,endColorstr=#44000000); }

        .dialog-header {
            position: relative;
            width: auto;
            height: 39px;
            padding: 0 46px 0 16px;
            margin: 0;
            border-bottom: 1px solid #d9d9d9;
            font-size: 16px;
            line-height: 39px;
            white-space: nowrap;
            background-color: #e6e6e6; }
        .dialog-header + .dialog-body {
            padding-top: 32px; }
        .dialog-header + .dialog-body.withNoPadding {
            padding: 0; }

        .dialog-close {
            display: inline-block;
            vertical-align: middle;
            background-image: url("images/icons-dialog.png");
            background-repeat: no-repeat;
            position: absolute;
            right: 16px;
            top: 12px;
            width: 14px;
            height: 14px;
            padding: 0;
            margin: 0;
            background-position: 0 -135px;
            cursor: pointer; }

        #dialogTipBox .dialog-body {
            padding-top: 48px;
            padding-bottom: 48px; }
        #dialogTipBox .dialog-msg {
            padding: 0;
            margin: 0;
            text-align: center; }
        #dialogTipBox .dialog-msg-text {
            padding: 0;
            margin: 0;
            font-size: 16px; }
        #dialogTipBox .i-success,
        #dialogTipBox .i-warning,
        #dialogTipBox .i-error {
            position: relative;
            display: inline-block;
            top: -2px;
            margin-right: 20px;
            width: 36px;
            height: 36px;
            vertical-align: middle;
            background-image: url("images/icons-dialog.png");
            background-repeat: no-repeat; }
        #dialogTipBox .i-success {
            background-position: 0 0; }
        #dialogTipBox .i-warning {
            background-position: 0 -45px; }
        #dialogTipBox .i-error {
            background-position: 0 -90px; }

        .dialog-confirm {
            box-sizing: content-box;
            position: relative;
            width: 235px;
            min-width: 235px;
            font-size: 0;
            text-align: left;
            margin: 0 auto; }
        .dialog-confirm.withCenter {
            text-align: center; }
        .dialog-confirm.withIcon, .dialog-confirm.withIconSuccess, .dialog-confirm.withIconWarning, .dialog-confirm.withIconError {
            padding-left: 54px; }
        .dialog-confirm.withIcon:before, .dialog-confirm.withIconSuccess:before, .dialog-confirm.withIconWarning:before, .dialog-confirm.withIconError:before {
            content: '';
            display: inline-block;
            vertical-align: middle;
            background-image: url("images/icons-dialog.png");
            background-repeat: no-repeat;
            position: absolute;
            left: 0;
            top: 0;
            width: 36px;
            height: 36px;
            background-position: 0 -45px; }
        .dialog-confirm.withIconSuccess:before {
            background-position: 0 0; }
        .dialog-confirm.withIconWarning:before {
            background-position: 0 -45px; }
        .dialog-confirm.withIconError:before {
            background-position: 0 -90px; }
        .dialog-confirm .dialog-msg {
            padding: 0;
            margin: 0;
            font-size: 16px;
            line-height: 32px; }
        .dialog-confirm .dialog-msg + .dialog-desc {
            margin-top: 10px; }
        .dialog-confirm .dialog-desc {
            padding: 0;
            margin: 0;
            font-size: 12px;
            line-height: 24px;
            margin-bottom: 20px; }
        .dialog-confirm .dialog-content {
            padding: 0;
            margin: 0;
            font-size: 12px;
            line-height: 24px;
            text-align: left; }
        .dialog-confirm .dialog-content + .dialog-buttonBox {
            text-align: center; }
        .dialog-confirm .dialog-buttonBox {
            overflow: hidden;
            padding: 0;
            margin: 24px 0 0 0; }
        .dialog-confirm .dialog-buttonBox a {
            margin-left: 15px; }
        .dialog-confirm .dialog-buttonBox a:first-child {
            margin-left: 0; }
        .dialog-confirm .dialog-submit,
        .dialog-confirm .dialog-cancel {
            display: inline-block; }
        .dialog-confirm .dialog-submit {
            width: 98px;
            height: 29px;
            font-size: 12px;
            line-height: 29px; }
        .dialog-confirm .dialog-cancel {
            box-sizing: content-box;
            width: 96px;
            height: 27px;
            font-size: 12px;
            line-height: 27px; }

        .dialog-success-top {
            position: fixed;
            left: 50%;
            top: 0;
            z-index: 999;
            min-width: 400px;
            padding: 0;
            margin: 0;
            border-radius: 5px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            font-size: 16px;
            line-height: 46px;
            text-align: center;
            color: #fff;
            background-color: #4aac42; }
        .dialog-success-top .i-icon {
            position: relative;
            display: inline-block;
            vertical-align: middle;
            width: 26px;
            height: 18px;
            margin-right: 15px;
            background: url("images/icons-dialog.png") no-repeat 0 -180px; }

        .dialog-warning-top {
            position: fixed;
            left: 50%;
            top: 0;
            z-index: 999;
            min-width: 400px;
            padding: 0;
            margin: 0;
            border-radius: 5px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            font-size: 16px;
            line-height: 46px;
            text-align: center;
            color: #fff;
            background-color: #ff4400; }

        /*# sourceMappingURL=dialog.css.map */
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="法人" subTitle="请输入法人查询信息" col="12" collapsible="true">
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
                    <bc:col col="6" colOffset="1">
                        <%--<bc:button id="inBtn" type="info" outline="true" text="导入" > <be:event> </be:event> </bc:button>--%>
                        <%--<bc:button id="outBtn" type="info" outline="true" text="导出" > <be:event> </be:event> </bc:button>--%>

						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
						<%--<bc:button id="exportsalary" type="success" text="下载导入模板">--%>
							<%--<be:event>--%>
								<%--exportHandler();--%>
							<%--</be:event>--%>
						<%--</bc:button>--%>
					</bc:col>

				</bc:row>
			</bf:form>
			<bc:table id="legalPersonTable" idField="pid" url="${base}/bus/legalPerson">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="confirmWithCustom" text="解除关联关系" handler="dell" type="button"/>
				<bc:toolbar id="addblackList" text="加入黑名单" handler="addblackList" type="button"/>
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
				<bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
				<bc:toolbar id="out" text="数据导出" handler="addblack" type="button"/>
                <bc:toolbar id="exportHandlerHis" text="下载导入模板" type="button" handler="exportHandler"/>
				<bc:column field="pid" title="自增主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100"/>
				<bc:column field="orgName" title="企业名称" width="100"/>
				<bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="uniscCode" title="社会统一信用代码" width="100"/>
				<bc:column field="status" title="机构状态（1-正常，2-停业）" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Status"/>
				<bc:column field="reportVersion" title="报送版本号" width="100"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-ValidStatus"/>
				<bc:column field="isReported" title="是否被报送（0-未报送，1-已被报送）" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IsReported"/>
				<bc:operator id="views" icon="eye" title="查看" handler="views"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script>

    $(document).ready(function(){ $('#inBtn').mouseup(function(){ $.messager.toastr('提示', '测试按钮导入'); }); });
    $(document).ready(function(){ $('#outBtn').mouseup(function(){ $.messager.toastr('提示', '测试按钮导出'); });

    });
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
    function exportHandler(){
        var data = "00"
        var url='${base}/sys/xlsie/LegalPersonEportCallbackImpl/'+ data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
	function views(e, value, row, index){
		//获取当前页码
		var pageNumber = $('#legalPersonTable').bootstrapTable('getOptions').pageNumber;
		var pageSize = $('#legalPersonTable').bootstrapTable('getOptions').pageSize;

		$('#legalPersonTable').on("click-row.bs.table",function(e, row, $element) {
			//获取当前选中行号
			var pageRow= $element.data('index');
			console.log(pageRow)
			window.location = '${base}/bus/legalPerson/view/' + row.pid+ '/' +'?pageNumber='+pageNumber+'&pageRow='+pageRow+'&pageSize='+pageSize;
		})
	}

</script>
</body>
</html>