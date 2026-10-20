<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<x:jsLib/>
    <br:css pack="form"/>
    <title>金融产品新增或编辑</title>
    <script>
        $(document).ready(function(){
            show();
        });
        $(document).ready(function(){
            x();
        });
        // window.onload=show;
        // window.onload=x;
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
            // alert("进入");
            var value='${op}';
            // alert(value);
            if(value != "0"){
                // $('#y').css('display', 'none');//返回按钮
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
                Array.prototype.slice.call(document.getElementsByTagName('input')).map(i => i.disabled = true)
                Array.prototype.slice.call(document.getElementsByTagName('select')).map(i => i.disabled = true)
            }
            if (value == '2'){
                $('#y').hide()
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

    <style>
        .form-group .input-group.date,
        .form-group .select2{
            width: 100% !important;
        }
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }
        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
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
    <bc:ibox id="productBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <bf:hidden id="pid" name="pid" value="${relPro.pid}"/>
            <bf:hidden id="status" name="status" value="${relPro.approvalStatus}"/>
            <%--<bf:hidden id="relSerial" name="relSerial" value="${relPro.relSerial}"/>--%>
        <bf:formGroup>
             <bc:col col="6">
                <bc:button type="primary" text="基本信息">
                    <be:event>
                        basicInformation();
                    </be:event>
                </bc:button>
               <%-- <bc:button id="psnRelInformation" type="light" text="关系自然人信息">
                    <be:event>
                        affiliateInformation();
                    </be:event>
                </bc:button>--%>
                <%--<bc:button id="orgRelInformation" type="light" text="关联法人或其他组织">
                    <be:event>
                        orgRelInformation();
                    </be:event>
                </bc:button>--%>
                <%--<bc:button id="proRelInformation" type="info" text="关系金融产品">
                    <be:event>
                        proRelInformation();
                    </be:event>
                </bc:button>--%>
            </bc:col>
        </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品名称" col="4" formControl="true"/>
                    <bf:text id="proName" name="proName" col="8" value="${relPro.proName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="产品分类" col="4" formControl="true"/>
                    <bf:select id="proType" name="proType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-pro" value="${relPro.proType}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品批准/注册/备案号" col="4" formControl="true"/>
                    <bf:text id="proProvider" name="proProvider" col="8" value="${relPro.proProvider}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="产品代码" col="4" formControl="true"/>
                    <bf:text id="proCode" name="proCode" col="8" value="${relPro.proCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品管理人全称" col="4" formControl="true"/>
                    <bf:text id="adName" name="adName" col="8" value="${relPro.adName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="管理人统一社会信用代码" col="4" formControl="true"/>
                    <bf:text id="adCreditCode" name="adCreditCode" col="8" value="${relPro.adCreditCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品托管人全称" col="4" formControl="true"/>
                    <bf:text id="careName" name="careName" col="8" value="${relPro.careName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="托管人统一社会信用代码" col="4" formControl="true"/>
                    <bf:text id="careCreditCode" name="careCreditCode" col="8" value="${relPro.careCreditCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品登记机构" col="4" formControl="true"/>
                    <bf:text id="proRegOrg" name="proRegOrg" col="8" value="${relPro.proRegOrg}">
                    </bf:text>
                </bc:col>
                <%--<bc:col col="6">
                    <bc:label cls="required" text="机构名称" col="4" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
                               url="sys/unit/selectUnit" excludes="ZZ_Delegation" value="${relPro.mgerOrg}">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>--%>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    $("#proProvider").blur(function () {p
        var proProvider = $("#proProvider").val();
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPro/selectProProvider',
            data: {
                "proProvider": proProvider,
            },
            dataType: 'json',
            success: function (data) {
                if (data.success){
                    $.messager.alert('提示', '该金融产品已经存在！', 'warning', function () {
                        $("#proProvider").empty();
                    });
                }
            }
        });

    });

    clear();
    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });
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

    function affiliateInformation() {
        var value='${op}';
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }

        if (value == "0"){
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=0';
        }else {
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=1';
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
            window.location = '${base}/bus/relPro/orgOfProList/' + pid + '?op=0';
        }else {
            window.location = '${base}/bus/relPro/orgOfProList/' + pid + '?op='+value;
        }
    }

    function proRelInformation() {
        var value='${op}';
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        if (value == "0"){
            window.location = '${base}/bus/relPsn/proOfPsnList/' + pid + '?op=0';
        }else {
            window.location = '${base}/bus/relPsn/proOfPsnList/' + pid + '?op=1';
        }
    }

    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
            document.getElementById("proRelInformation").setAttribute("disabled", true);
        }
    });
</script>
</body>
</html>
