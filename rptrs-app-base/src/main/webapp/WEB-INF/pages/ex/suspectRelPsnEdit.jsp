<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>自然人新增或编辑</title>
	<script>
        function isShareholder(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }
	</script>
	<script>
        function isNoShareVote(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#voteProportion').css('display', 'block')
                $('#voteProportion').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#voteProportion').css('display', 'none')
                $('#voteProportion').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }
	</script>
	<x:jsLib/>
	<style>
		.boxse .form-group .col-xs-4{
			margin: 0.3% 0!important;
		}
		.boxse .has-success .form-control,.boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
		.has-success .select2-dropdown, .has-success .select2-selection {
			border-color: #CCCCCC !important;
		}
		.gray-bg {
			background: white !important;
		}
		.MainGd {
			display: none;
		}
		.voteProportion {
			display: none;
		}
	</style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
	<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
	<bf:form id="editForm" method="post" validation="true">
	<bf:hidden id="opt" name="opt" value="${opt}"/>
	<bf:hidden id="pid" name="pid" value="${relPsnSuspect.pid}"/>
	<bf:formGroup>
		<div class="col-sm-4 col-sm-offset-2">
			<bc:button type="info" text="基本信息">
				<be:event>
					<%--basicInformation();--%>
				</be:event>
			</bc:button>
			<bc:button id = "psnRelInformation" type="info" text="自然人关系信息">
				<be:event>
					affiliateInformation();
				</be:event>
			</bc:button>
			<bc:button id ="orgRelInformation" type="info" text="机构关系信息">
				<be:event>
					orgRelInformation();
				</be:event>
			</bc:button>
		</div>
	</bf:formGroup>
	<div class="boxse">
		<bf:formGroup type="success" hr="dashed" hrp="bottom">
		<bc:col col="4">
			<bc:label text="姓名" col="5" formControl="true"/>
			<bf:text id="name" name="name" col="7" value="${relPsnSuspect.name}">
				<bv:notEmpty/>
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="性別" col="5" formControl="true"/>
			<bf:select id="sex" name="sex" col="7" dataSource="custom" url="sys/dictItem/getDictItems/BUS-Sex"
					   value="${relPsnSuspect.sex}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="证件类型" col="5" formControl="true"/>
			<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsnSuspect.identifyType}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="证件号码" col="5" formControl="true"/>
			<bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsnSuspect.identifyNumber}">
				<bv:notEmpty/>
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="政治面貌" col="5" formControl="true"/>
			<bf:select id="politicsStatus" name="politicsStatus" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS_politically" value="${relPsnSuspect.politicsStatus}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="学历" col="5" formControl="true"/>
			<bf:select id="educationBackground" name="educationBackground" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-Education" value="${relPsnSuspect.educationBackground}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="民族" col="5" formControl="true"/>
			<bf:select id="race" name="race" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-Race" value="${relPsnSuspect.race}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="境内外标识" col="5" formControl="true"/>
			<bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
					   value="${relPsnSuspect.domesticOrForeign}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="出生日期" col="5" formControl="true"/>
			<bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsnSuspect.birthdate}">
			</bf:datebox>
		</bc:col>
		<bc:col col="4">
			<bc:label text="婚姻状况" col="5" formControl="true"/>
			<bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsnSuspect.maritalStatus}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="移动电话" col="5" formControl="true"/>
			<bf:text id="mobile" name="mobile" col="7" value="${relPsnSuspect.mobile}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="邮箱" col="5" formControl="true"/>
			<bf:text id="email" name="email" col="7" value="${relPsnSuspect.email}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="机构名称" col="5" formControl="true"/>
			<bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
					   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsnSuspect.mgerOrg}">
			</bf:select>
		</bc:col>
		<bc:col col="4">
			<bc:label text="居住地址" col="5" formControl="true"/>
			<bf:text id="residentialAddress" name="residentialAddress" col="7"
					 value="${relPsnSuspect.residentialAddress}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="工作单位" col="5" formControl="true"/>
			<bf:text id="workUnits" name="workUnits" col="7" value="${relPsnSuspect.workUnits}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="单位地址" col="5" formControl="true"/>
			<bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsnSuspect.unitAddress}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="是否为股东" col="5" formControl="true"/>
			<bf:checkGroup name="isShareholder"  col="7" value="${relPsnSuspect.isShareholder}">
				<bf:radio value="1" label="是" click="isShareholder(e.target.value)"/>
				<bf:radio value="0" label="否" checked="true" click="isShareholder(e.target.value)"/>
			</bf:checkGroup>
		</bc:col>
	</div>
	<div id="MainGd" class="MainGd">
			<%--                    新添的--%>
		<bc:col col="4">
			<bc:label text="是否为本行主要股东" col="5" formControl="true"/>
			<bf:checkGroup name="isMainGd"  col="7" value="${relPsnSuspect.isMainGd}">
				<bf:radio value="1" label="是" />
				<bf:radio value="0" label="否" checked="true" />
			</bf:checkGroup>
		</bc:col>
		<bc:col col="4">
			<bc:label text="股权证编号" col="5" formControl="true"/>
			<bf:text id="shareholdersCode" name="shareholdersCode" col="7" value="${relPsnSuspect.shareholdersCode}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="股东代码（账户号）" col="5" formControl="true"/>
			<bf:text id="capitalNumber" name="capitalNumber" col="7"
					 value="${relPsnSuspect.capitalNumber}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="交易账户开户行" col="5" formControl="true"/>
			<bf:text id="openBank" name="openBank" col="7" value="${relPsnSuspect.openBank}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="交易账户账号" col="5" formControl="true"/>
			<bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsnSuspect.tradeAccount}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="入股时间" col="5" formControl="true"/>
			<bf:datebox id="inTime" name="inTime" col="7" format="date"  value="${relPsnSuspect.inTime}">
			</bf:datebox>
		</bc:col>
		<bc:col col="4">
			<bc:label text="入股价格" col="5" formControl="true"/>
			<bf:text id="inPrice" name="inPrice" col="7" value="${relPsnSuspect.inPrice}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="持股数量（股）" col="5" formControl="true"/>
			<bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relPsnSuspect.holeShareAmount}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="持股占比" col="5" formControl="true"/>
			<bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
					 value="${relPsnSuspect.shareholdingProportion}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="总冻结股数（股）" col="5" formControl="true"/>
			<bf:text id="frozenShareAmount" name="frozenShareAmount" col="7"
					 value="${relPsnSuspect.frozenShareAmount}">
			</bf:text>
		</bc:col>
		<bc:col col="4">
			<bc:label text="总质押股数（股）" col="5" formControl="true"/>
			<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7"
					 value="${relPsnSuspect.pledgedShareAmount}">
			</bf:text>
		</bc:col>
		</bf:formGroup>
	</div>
	<div id="tableDiv" class="MainGd" style="width:95%;margin:0 auto">
		<bc:table id="relFileTable" idField="pid" url="${base}/bus/relFile" multiple="true"
				  selectFirst="false" queryUrl="queryList?relSerial=${relPsnSuspect.relSerial}">
			<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
			<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
			<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
			<bc:column field="pid" title="主键" width="100" visible="false"/>
			<bc:column field="fileName" title="文件名称" width="100"/>
			<bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
			<bc:column field="fileType" title="文件类型" width="100"/>
			<bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
			<bc:column field="approveStatus" title="操作状态" width="100" visible="false"/>
			<bc:column field="fileSize" title="文件大小" width="100"/>
			<bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
			<bc:column field="uploadAgency" title="上传机构" width="100"/>
			<bc:column field="uploadDepartment" title="上传部门" width="100"/>
			<bc:column field="modifiedTime" title="上传时间" width="100"/>
		</bc:table>
	</div>

</div>

<bc:col col="4">
	<bc:label text="是否具有非持股表决权" col="5" formControl="true"/>
	<bf:checkGroup name="isNoShareVote"  col="7" value="${relPsnSuspect.isNoShareVote}">
		<bf:radio value="1" label="是" click="isNoShareVote(e.target.value)"/>
		<bf:radio value="0" label="否" checked="true" click="isNoShareVote(e.target.value)"/>
	</bf:checkGroup>
</bc:col>
<div id="voteProportion"  class="voteProportion">
	<bc:col col="4">
		<bc:label text="表决权占比：" col="5" formControl="true"/>
		<bf:text id="voteProportion" name="voteProportion" col="7"
				 value="${relPsnSuspect.voteProportion}">
		</bf:text>
	</bc:col>
</div>

<bc:col col="4">
	<bc:label text="限制表决权情况：" col="5" formControl="true"/>
	<bf:text id="limitVoteSituation" name="limitVoteSituation" col="7"
			 value="${relPsnSuspect.limitVoteSituation}">
	</bf:text>
</bc:col>
<bc:col col="6">
	<bc:label text="认定原因" col="4" formControl="true"/>
	<bf:text id="cognizaReason" name="cognizaReason" col="8"  value="${cognizaReason}">
	</bf:text>
</bc:col>
<bf:formGroup>
	<div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0 ">
		<bc:button type="success" outline="true" text="保存" icon="save">
			<be:event>
				var tradeAccount = $('#tradeAccount').val();
				var inPrice = $('#inPrice').val();
				<%--                            alert(tradeAccount);--%>
				<%--                            alert(inPrice);--%>
				if(localStorage.vals =="1" && localStorage.vals !="" && localStorage.vals !="undefined"){
				if(tradeAccount == ""||tradeAccount == undefined){
				alert("请填写交易账户账号");
				return;
				}
				if(inPrice == ""||inPrice == undefined){
				alert("请填写入股价格");
				return;
				}
				}
				var docInnerPids = getAllIdSelections();
				if(localStorage.vals =='1'){
				if (docInnerPids == "" || docInnerPids == undefined) {
				alert("文件不能为空,请选中或上传文件");
				return;
				}
				}
				$('#editForm').data('formValidation').validate();
				var isValid = $('#editForm').data('formValidation').isValid();
				if(isValid)
				{
				var formData = $('#editForm').serializeAll();
				var docTableSelectItem = {
				'name':'docTableSelectItem',
				'value':docInnerPids
				};
				formData.push(docTableSelectItem);
				$.mask.post('${base}/bus/relPsnSuspect/save', formData, function(data){
				if(data.success)
				{
				$.messager.alert('提示', data.msg, 'info', function(){
				<%--var opt = $("#opt").val();--%>
				<%--var pidForForm = $("#pid").val();--%>
				<%--window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/'+data.formPid;--%>
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
		<%--<bc:button type="primary" text="返回" icon="arrow-circle-left">--%>
			<%--<be:event>--%>
				<%--window.location = '${base}/bus/relPsnSuspect/list';--%>
			<%--</be:event>--%>
		<%--</bc:button>--%>
	</div>
</bf:formGroup>
</bf:form>
</bc:ibox>
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
							<img src="../../static/img/sendfile.png"/>
							<a href="javascript:;" class="file">选择文件
								<input type="file" class="form-control" id="file" name="file" multiple
									   style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    function exportIn() {
        $("#hiddenBtn").click();
    }
    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/relFile/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#relFileTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/relFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }

    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/relFile/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.relFile;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#relFileTable').bootstrapTable('load', showData);
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
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
    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }



    //文件表数据
    function getData() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }



    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }
    function basicInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relOrgSuspect/view/'+ pid+'?';
    }


    function holderInformation(){
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/viewHolder/'+pid;
        }
    }

    <%--function basicInformation() {--%>
    <%--var opt = $("#opt").val();--%>
    <%--if(opt == "edit"){--%>
    <%--var pid = $("#pid").val();--%>
    <%--window.location = '${base}/bus/relPsnSuspect/edit/'+ pid+'?';--%>
    <%--}else{--%>
    <%--window.location = '${base}/bus/relPsnSuspect/addForRelPsnAdd';--%>
    <%--}--%>
    <%--}--%>
    function affiliateInformation() {
        var opt = $("#opt").val();
        var pid = $("#pid").val();
        if(pid == ""){
            pid = "%"
        }
        // if(opt == "edit"){
        window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/'+pid + '? ';
        // }else{
        <%--window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/'+ opt + "/" +pid +'?';--%>
        <%--}--%>
    }
    function rightsInformation() {
        var opt = $("#opt").val();
        var pid = $("#pid").val();
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' +pid +'? ';
    }

    function orgRelInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/'+ pid +'?';
    }
    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
        }
    });
</script>
</body>
</html>
