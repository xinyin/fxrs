<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>MeetingManage</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="meetingManageBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="pid" name="pid" value="${meetingManage.pid}"/>
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="会议名称" col="4" formControl="true"/>
						<bf:text id="meetName" name="meetName" col="8" value="${meetingManage.meetName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="会议发起人" col="4" formControl="true"/>
						<bf:text id="meetInitiatorName"  name="meetInitiatorName" col="8" value="${meetingManage.meetInitiatorName}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
					<bc:label text="会议发起部门" col="4" formControl="true"/>
						<bf:select id="meetDepart" name="meetDepart"  col="8" dataSource="custom"
							   url="sys/unit/selecttoDept" value="${meetingManage.meetDepart}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="会议发起日期" col="4" formControl="true"/>
						<bf:datebox id="meetDate" name="meetDate" col="8" value="${meetingManage.meetDate}">
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>

				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="参会人员" col="4" formControl="true"/>
						<bf:text id="conferee" name="conferee" col="8" value="${meetingManage.conferee}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="会议内容" col="4" formControl="true"/>
						<bf:text id="meetDetails" name="meetDetails" col="8" value="${meetingManage.meetDetails}" multiple="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bc:table id="meetingFileTable" idField="pid" url="${base}/bus/meetingFile"
						      multiple="true" selectFirst="false" queryUrl="queryList?meetingPid=${meetingFile.meetingPid}">
					<bc:toolbar id="uploadFile" text="上传文件" type="button" handler="exportIn"/>
					<bc:toolbar id="remove" text="删除" type="button"  handler="removeHandler"/>
					<bc:column field="fileName" title="文件名" width="100"/>
					<bc:column field="fileType" title="文件类型" width="100"/>
					<bc:column field="fileSize" title="文件大小" width="100"/>
					<bc:column field="uploadAgency" title="上传机构" width="100"/>
					<bc:column field="uploadDepartment" title="上传部门" width="100"/>
					<bc:column field="modifiedTime" title="上传时间" width="100"/>
				</bc:table>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
						<bc:button type="success" text="保存" icon="arrow-circle-right">
							<be:event>

								var docInnerPids = getAllIdSelections();

								$('#editForm').data('formValidation').validate();

								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
								var formData = $('#editForm').serializeAll();
								var filepid = {
								'name':'filepid',
								'value':docInnerPids
								};
								formData.push(filepid);
								$.mask.post('${base}/bus/meetingManage/save', formData, function(data){
								if(data.success)
								{
								if (data.orgid !=null &&data.orgid !=undefined){
								$('#pid').val(data.orgid);
								}
								<%--$.messager.alert('提示', data.msg, 'info', function(){--%>
								<%--window.location = '${base}/bus/relOrg/list';--%>
								<%--});--%>
								var orgid = $('#pid').val();
								<%--window.location = '${base}/bus/relOrg/listAffi/' + orgid;--%>
								$.messager.confirm('提示', data.msg, function(r){
								if(r)
								{
								window.location = '${base}/bus/meetingManage/list';
								}
								});
								}
								else
								{
								$.messager.alert('错误', data.msg, 'error');
								}
								});
								}
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								<%--var uniscCode = $('#uniscCode').value();--%>
								window.location = '${base}/bus/meetingManage/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
	</div>
	<div>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
				id="hiddenBtn"
				style="display: none">
			文件上传按钮
		</button>

		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
			 aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form method="post" enctype="multipart/form-data" id="uploadForm"
							  style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
							<div class="form-group" style="text-align: center;vertical-align: middle;">
								<label for="file"></label>
								<img src="../../static/img/sendfile.png" style="top:28px;position:relative;z-index :0;font-size: 20px;" />
								<a href="javascript:;" class="file" style="top:28px;position:relative;z-index :0;">选择文件
									<input type="file" class="form-control" id="file" name="file" multiple
										   style="top:-58px;position:relative;z-index :1;opacity:0;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
								</a >
							</div>

						</form>
						<div class="form-group"
							 style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
							<button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
							<button type="submit" class="btn btn-primary" id="upload">上传</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
     /*上传文件的删除按钮方法*/
	function removeHandler() {
		var pid = getIdSelections();
		if (confirm('您确定要删除该数据吗？')){
			$.ajax({
				url:"${base}/bus/meetingFile/remove/"+pid,
				method:"POST",
				data:{"":""},
				dataType:"JSON",
				traditional:true,
				success:function(data){
					if(data.success){
						$.messager.alert(data.msg);
						window.location = '${base}/bus/meetingManage/add';
					}else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			})
		}
	}


	function exportIn() {
        $("#hiddenBtn").click();

    }

$("#upload").click(function () {
    var obj = document.getElementById("file");
    for (var i = 0; i < obj.files.length; i++) {
        var temp = obj.files[i].name;
        var temArr = temp.split('.');
        var fileLast = temArr[temArr.length - 1];

    }
    var formData = new FormData($('#uploadForm')[0]);
    $.ajax({
        type: 'post',
        url: '${base}/bus/meetingFile/uploadFile', //上传文件的请求路径必须是绝对路劲
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            $("#close").click();
			$.messager.alert(data.msg);
            if (data.success) {
                //处理数据
                var files = data.meetingFile;
                var count = files.length;
                //获取原有表单的数据
                var rows = getData();
                rows = rows.concat(files);
                //构造表格显示数据
                var total = rows.length;
                var showData = {'total': total, 'rows': rows}
                $('#meetingFileTable').bootstrapTable('load', showData);
            }
        },
        error: function () {
            $("#close").click();
			$.messager.alert("上传失败,请重试");
        }
    })
});
// $(document).ready(function () {
//     var opt = $('#opt').val();
//     if (opt != 'edit') {
//         document.getElementById("psnRelInformation").setAttribute("disabled", true);
//         document.getElementById("orgRelInformation").setAttribute("disabled", true);
//     }
// });

//表单提交数据
$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeAll();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};

//文件表数据
function getData() {
    return $.map($('#meetingFileTable').bootstrapTable('getData'), function (row) {
        return row
    });
}

function getAllIdSelections() {
    return $.map($('#meetingFileTable').bootstrapTable('getData'), function (row) {
        return row.pid//返回数据行中的id值
    });
}

function getIdSelections() {
    return $.map($('#meetingFileTable').bootstrapTable('getSelections'), function (row) {
        return row.pid//返回数据行中的id值
    });
}

</script>
</body>
</html>
