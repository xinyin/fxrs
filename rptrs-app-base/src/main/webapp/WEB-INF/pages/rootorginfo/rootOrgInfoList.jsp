<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="list"/>
	<style type="text/css">

		#orgBaseDataTable_tb_add, #orgBaseDataTable_tb_edit,
		#orgBaseDataTable_tb_remove{
			display: none;
		}
	</style>
	<title>机构基础数据</title>
	<style>
		.form-group  select{
			width: 100% !important;
		}

		.form-group {
			margin: 1.0% 0 !important;
		}


		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}


		.form-group .input-group.date,
		.form-group .select2 {
			width: 100% !important;
		}

		.boxse .form-group .col-xs-4 {
			margin: 0.3% 0 !important;
		}

		.boxse .has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup hrp="bottom">
						<bf:formGroup col="1"></bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="年度" col="5" formControl="true"/>
							<bf:datebox id="years" name="__LIKES_years" col="7" format="date" />
						</bf:formGroup>

						<bf:formGroup col="4">
							<bc:label text="季度" col="5" formControl="true"/>
							<bf:select id="quarter" name="__LIKES_quarter" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-Quarter">
								<bf:option text="请选择" value=""></bf:option>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>

					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
						</div>
					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="orgBaseDataTable" idField="pid" url="${base}/bus/rootOrgInfo" selectFirst="true">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
				<%--<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>--%>
				<bc:column field="pid" title="主键自增" width="100" visible="false"/>
				<bc:column field="years" title="报送数据所属年份" width="150"/>
				<bc:column field="quarter" title="报送数据所属季度" width="150"
						   formatter="custom:sys/dictItem/getDictItems/BUS-Quarter"/>
				<bc:column field="totalCapital" title="总资产（万元）" width="150"/>
				<bc:column field="netCaptial" title="净资产（万元）" width="150"/>
		<%--		<bc:column field="approvalStatus" title="操作状态"
						   formatter="custom:sys/dictItem/getDictItems/BUS-ApplySingleStatus" width="100"/>--%>
				<bc:column field="equityUpperLimitScale" title="权益类资产投资上限比例（%）" width="150"/>
				<bc:operator id="view" icon="eye" title="详情"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script>
    //根据权限显示按钮
    $(function () {
        var code = "${code}";
        if (code.search("01,") != -1) {
            $('#orgBaseDataTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#orgBaseDataTable_tb_edit').show();
        }
        if (code.search("03") != -1) {
            $('#orgBaseDataTable_tb_remove').show();
        }
    });

    /**
     * 提交审批
     */
    function send() {

        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length!=1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }

        let statusArr = status.filter(v => { return '3' === v })
        console.log(statusArr)
        if (!statusArr || statusArr.length < pid.length) {
            $.messager.alert('提示', '请选择审批状态为"待提交"的数据。', 'info');
            return;
        }
        //查询出基本信息 修改状态 放到data中
        $.ajax({
            url: "${base}/bus/rootOrgInfo/send/" + pid,
            method: "GET",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                //ZZ_Org001
                if (data.success) {
                    //开始走工作流
                    var objData = data.data;
                    var myDate = new Date();
                    objData.id = myDate.getTime();
                    //获取备案号
                    var filNum = randomNumber();
                    objData.filNum = filNum;
                    data = JSON.stringify(objData);

                    var formData = $.helper.base64Encode(data);
                    //挂取工作流
                    var obj = {
                        procDefCode: 'ZZ_Org001',
                        oldData: formData,
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }

                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/bus/rootOrgInfo/list';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }

    /*
根据当前时间和随机数生成备案号
* */
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year + month + day + hour + minutes + seconds;
        return no + (Math.round(Math.random() * 89 + 100)).toString();
    }

    $("#years").datetimepicker({
        format:"yyyy",
        autoclose: true,
        startView:4,
        minView:4,
        todayHighlight:true
    });

    function getIdSelections() {
        return $.map($('#orgBaseDataTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function editHandler() {
        var pid = getIdSelections();
        var status = getStatusSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == '4') {
            $.messager.alert('提示', '审批中数据不可编辑', 'info');
            return false;
        }
        window.location = "${base}/bus/rootOrgInfo/edit/" + pid;
    }

    function removeHandler() {
        var pid = getIdSelections();
        var status = getStatusSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == '4') {
            $.messager.alert('提示', '审批中数据不可删除', 'info');
            return false;
        }
        // if (confirm('确认删除?')) {
        $.messager.confirm('提示', '确定要删除这些数据吗?', function(e){
            if (e) {
                $.ajax({
                    url: "${base}/bus/rootOrgInfo/remove/" + pid,
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert("成功", data.msg, 'info',function (r){
                                window.location = "${base}/bus/rootOrgInfo/list";
                            });
                        }
                    }
                });
            }
        } )}

    function getStatusSelections() {
        return $.map($('#orgBaseDataTable').bootstrapTable('getSelections'), function (row) {
            return row.approvalStatus;
        });
    }

    /*
  * 重置
  * */
    function frmReset() {
        window.location = '${base}/bus/rootOrgInfo/list';
    }

    function loadCallback() {
        $('#itraOtherTable').jTable('refresh');
    }

</script>
</body>
</html>