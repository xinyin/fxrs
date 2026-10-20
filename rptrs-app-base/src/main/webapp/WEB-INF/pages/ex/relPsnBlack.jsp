<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>自然人</title>
	<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
	<script>
        $(document).ready(function(){
            show();
        });
        $(document).ready(function(){
            x();
        });
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
        function x() {
            var value='${op}';
            if(value != "0"){
                if(value != "0"){
                    $('#regulatorOth1').attr('disabled','true');//统计口径
                    $('#mgerOrg').attr('disabled','true');//统计口径
                    $('#x').css('display', 'none');//保存按钮
                    $('#name').attr('disabled','true');//姓名
                    $('#sex').attr('disabled','true');//性别
                    $('#identifyType').attr('disabled','true');//证件类型
                    $('#identifyNumber').attr('disabled','true');//证件号码
                    $('#politicsStatus').attr('disabled','true');//政治面貌
                    $('#educationBackground').attr('disabled','true');//学历
                    $('#race').attr('disabled','true');//民族
                    $('#domesticOrForeign').attr('disabled','true');//境内外标识
                    $('#maritalStatus').attr('disabled','true');//婚姻状况
                    $('#mobile').attr('disabled','true');//移动电话
                    $('#email').attr('disabled','true');//邮箱
                    $('#residentialAddress').attr('disabled','true');//居住地址
                    $('#unitAddress').attr('disabled','true');//单位地址
                    $('#shareholdersCode').attr('disabled','true');//股权证编号
                    $('#capitalNumber').attr('disabled','true');//股东账号
                    $('#openBank').attr('disabled','true');
                    $('#tradeAccount').attr('disabled','true');
                    $('#inPrice').attr('disabled','true');
                    $('#holeShareAmount').attr('disabled','true');
                    $('#shareholdingProportion').attr('disabled','true');
                    $('#frozenShareAmount').attr('disabled','true');
                    $('#pledgedShareAmount').attr('disabled','true');
                    $('#isNoShareVote').attr('disabled','true');
                    $('#voteProportion').attr('disabled','true');
                    $('#limitVoteSituation').attr('disabled','true');
                    $('#workUnits').attr('disabled','true');
                    // $('#birthdate').css('disabled','true');//出生日期
                    // $('#inTime').css('disabled','true');  //$('#date').datebox('disable');
                }
            }
        }
        function show() {
            // alert("进入")
            var val='${relPsn.isShareholder}';
            if (val == '1') {
                // alert("进入1")
                $('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')
                document.getElementById('chk1').checked =false
                document.getElementById('chk').checked=true
                localStorage.vals = val;
            }
            if (val == '0') {
                // alert("进入0")
                $('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')
                localStorage.vals = val;
                document.getElementById('chk1').checked =true
                document.getElementById('chk').checked=false
            }

            var val1='${relPsn.isNoShareVote}';
            if (val1 == '1') {
                $('#voteProportion').css('display', 'block')
                $('#voteProportion').css('display', 'block')
                document.getElementById('isv').checked =true
                document.getElementById('isv1').checked=false
                localStorage.vals = val;
            }
            if (val1 == '0') {
                $('#voteProportion').css('display', 'none')
                $('#voteProportion').css('display', 'none')
                document.getElementById('isv').checked =false
                document.getElementById('isv1').checked=true
                localStorage.vals = val;
            }
            //是否为主要股东
            var val2='${relPsn.isMainGd}';
            if (val2 == '1') {
                // $('#voteProportion').css('display', 'block')
                // $('#voteProportion').css('display', 'block')
                document.getElementById('ma').checked =true
                document.getElementById('ma1').checked=false
                // localStorage.vals = val;
            }
            if (val2 == '0') {
                document.getElementById('ma').checked =false
                document.getElementById('ma1').checked=true
            }

        }

	</script>
	<x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
	<bc:ibox id="provideServicesBox" title="编辑信息" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
			<bf:hidden id="status" name="status" value="${relPsn.status}"/>
			<bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
			<bf:formGroup>
				<div class="col-sm-6 col-sm-offset-2">
					<bc:button type="info" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button id="psnRelInformation" type="info" text="关系自然人信息">
						<be:event>
							affiliateInformation();
						</be:event>
					</bc:button>
					<bc:button id="orgRelInformation" type="info" text="关系机构信息">
						<be:event>
							orgRelInformation();
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
			<div class="boxse">
				<bf:formGroup type="success" hrp="bottom">
				<bc:col col="4">
					<bc:label text="姓名" col="5" formControl="true"/>
					<bf:text id="name" name="name" col="7" value="${relPsn.name}">
						<bv:notEmpty/>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="性別" col="5" formControl="true"/>
					<bf:select id="sex" name="sex" col="7" dataSource="custom" url="sys/dictItem/getDictItems/BUS-Sex"
							   value="${relPsn.sex}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="证件类型" col="5" formControl="true"/>
					<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="证件号码" col="5" formControl="true" />
					<bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsn.identifyNumber}">
						<%--                        click="addRelPsn()"--%>
						<bv:notEmpty/>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="政治面貌" col="5" formControl="true"/>
					<bf:select id="politicsStatus" name="politicsStatus" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS_politically" value="${relPsn.politicsStatus}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="学历" col="5" formControl="true"/>
					<bf:select id="educationBackground" name="educationBackground" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-Education" value="${relPsn.educationBackground}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="民族" col="5" formControl="true"/>
					<bf:select id="race" name="race" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="境内外标识" col="5" formControl="true"/>
					<bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
							   value="${relPsn.domesticOrForeign}">
					</bf:select>
				</bc:col>

				<bc:col col="4">
					<bc:label text="出生日期" col="5" formControl="true"/>
					<bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsn.birthdate}">
					</bf:datebox>
				</bc:col>
				<bc:col col="4">
					<bc:label text="婚姻状况" col="5" formControl="true"/>
					<bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="移动电话" col="5" formControl="true"/>
					<bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}">
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="邮箱" col="5" formControl="true"/>
					<bf:text id="email" name="email" col="7" value="${relPsn.email}">
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="机构名称" col="5" formControl="true"/>
					<bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}">
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="居住地址" col="5" formControl="true"/>
					<bf:text id="residentialAddress" name="residentialAddress" col="7"
							 value="${relPsn.residentialAddress}">
						<bv:notEmpty/>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="工作单位" col="5" formControl="true"/>
					<bf:text id="workUnits" name="workUnits" col="7" value="${relPsn.workUnits}">
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="单位地址" col="5" formControl="true"/>
					<bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsn.unitAddress}">
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="是否为股东" col="5" formControl="true"/>
					<bf:checkGroup name="isShareholder"  col="7" value="${relPsn.isShareholder}">
						<bf:radio id="chk" value="1" label="是" click="isShareholder(e.target.value)"/>
						<bf:radio  id="chk1"  value="0" label="否" checked="true" click="isShareholder(e.target.value)"/>
					</bf:checkGroup>
				</bc:col>
				<div id="MainGd" class="MainGd">
						<%--                    新添的--%>
					<bc:col col="4">
						<bc:label text="是否为本行主要股东" col="5" formControl="true"/>
						<bf:checkGroup name="isMainGd"  col="7" value="${relPsn.isMainGd}">
							<bf:radio id="ma" value="1" label="是" />
							<bf:radio id="ma1" value="0" label="否" checked="true" />
						</bf:checkGroup>
					</bc:col>
					<bc:col col="4">
						<bc:label text="股权证编号" col="5" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="7" value="${relPsn.shareholdersCode}">
						</bf:text>
					</bc:col>

					<bc:col col="4">
						<bc:label text="股东代码（账户号）" col="5" formControl="true"/>
						<bf:text id="capitalNumber" name="capitalNumber" col="7"
								 value="${relPsn.capitalNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="交易账户开户行" col="5" formControl="true"/>
						<bf:text id="openBank" name="openBank" col="7" value="${relPsn.openBank}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="交易账户账号" col="5" formControl="true"/>
						<bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsn.tradeAccount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="入股时间" col="5" formControl="true"/>
						<bf:text id="inTime" name="inTime" col="7" value="${relPsn.inTime}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="入股价格" col="5" formControl="true"/>
						<bf:text id="inPrice" name="inPrice" col="7" value="${relPsn.inPrice}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="持股数量（股）" col="5" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relPsn.holeShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="持股占比" col="5" formControl="true"/>
						<bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
								 value="${relPsn.shareholdingProportion}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="总冻结股数（股）" col="5" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="7"
								 value="${relPsn.frozenShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="总质押股数（股）" col="5" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7"
								 value="${relPsn.pledgedShareAmount}">
						</bf:text>
					</bc:col>
					</bf:formGroup>
				</div>
				<div id="tableDiv" class="MainGd" style="width: 95%;margin:0 auto">
					<bc:table id="relFileTable" idField="pid" url="${base}/bus/relFile" multiple="true"
							  selectFirst="false" queryUrl="queryList?relSerial=${relPsn.relSerial}">
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
			<%--            新添字段--%>
			<bc:col col="4">
				<bc:label text="是否具有非持股表决权" col="5" formControl="true"/>
				<bf:checkGroup name="isNoShareVote"  col="7" value="${relPsn.isNoShareVote}">
					<bf:radio id="isv" value="1" label="是" click="isNoShareVote(e.target.value)"/>
					<bf:radio id="isv1" value="0" label="否" checked="true" click="isNoShareVote(e.target.value)"/>
				</bf:checkGroup>
			</bc:col>
			<div id="voteProportion"  class="voteProportion">
				<bc:col col="4">
					<bc:label text="表决权占比：" col="5" formControl="true"/>
					<bf:text id="voteProportion" name="voteProportion" col="7"
							 value="${relPsn.voteProportion}">
					</bf:text>
				</bc:col>
			</div>
			<bc:col col="4">
				<bc:label text="限制表决权情况：" col="5" formControl="true"/>
				<bf:text id="limitVoteSituation" name="limitVoteSituation" col="7"
						 value="${relPsn.limitVoteSituation}">
				</bf:text>
			</bc:col>
			<bf:formGroup type="success" hr="dashed" hrp="bottom">
				<bc:col col="8">
					<bc:label text="${addOrRemoveBlack}" col="4" formControl="true"/>
					<bf:text id="blackReason" name="blackReason" col="8" value="${blackReason}" multiple="true" placeholder="此项为必填项" disabled="true">
						<bv:notEmpty message="请输入加入/移出黑名单原因"/>
					</bf:text>
				</bc:col>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    clear()
    function exportIn() {
        // alert(localStorage.vals);
        if (localStorage.vals == "1") {
            $("#hiddenBtn").click();
        }
    }
    function clear() {
        localStorage.clear()
        // console.log('111')
    }
    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
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
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row
        });
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


    function affiliateInformation() {
        var value='${op}';
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        if (value == "0"){
            // alert(value);
            window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?op=0';
        }else {
            // alert(value);
            window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?op=1';
        }

    }

    function orgRelInformation() {
        var value='${op}';
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        if (value == "0"){
            alert(value);
            window.location = '${base}/bus/relPsn/psnRelOrgList/' + pid + '?op=0';
        }else {
            alert(value);
            window.location = '${base}/bus/relPsn/psnRelOrgList/' + pid + '?op=1';
        }
    }

    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
        }
    });
</script>
<script type="text/javascript">

</script>
</body>
</html>
