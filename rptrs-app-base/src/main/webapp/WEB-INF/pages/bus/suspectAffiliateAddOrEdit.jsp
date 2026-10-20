<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>附属信息新增或编辑</title>
<x:jsLib/>
	<style>
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
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="pid" name="pid" value="${pid}"/>
				<bf:hidden id="relId" name="relId" value="${relId}"/>
				<bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
				<bf:hidden id="savefromPid" name="savefromPid" value="${savefromPid}"/>
				<%--<bf:hidden id="pidForPsnFrom" name="pidForPsnFrom" value="${fromPid}"/>--%>
				<%--<bf:hidden id="pidForPsnTo" name="pidForPsnTo" value="${pid}"/>--%>
				<%--<bf:hidden id="pidForRel" name="pidForRel" value="${relId}"/>--%>
				<div class="boxse">
					<bf:formGroup type="success" hrp="bottom">
						<bc:col col="4">
							<bc:label text="关联方名称" col="5" formControl="true"/>
							<bf:text id="name" name="name" col="7" value="${personRelationVo.name}">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="证件类型" col="5" formControl="true"/>
							<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
									   value="IDCARD" disabled="true" url="sys/dictItem/getDictItems/BUS-IdentifyType">
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="证件号码" col="5" formControl="true"/>
							<bf:text id="identifyNumber" name="identifyNumber" col="7" value="${personRelationVo.identifyNumber}">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="境内外标识" col="5" formControl="true"/>
							<bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
									   value="${personRelationVo.domesticOrForeign}" disabled="true">
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="出生日期" col="5" formControl="true"/>
							<bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${personRelationVo.birthdate}" disabled="true">
							</bf:datebox>
						</bc:col>
						<bc:col col="4">
							<bc:label text="居住地址" col="5" formControl="true"/>
							<bf:text id="residentialAddress" name="residentialAddress" col="7"
									 value="${personRelationVo.residentialAddress}" disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="关联关系判定" col="5" formControl="true"/>
							<bf:select id="Predicate" name="Predicate" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_Predicate">
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="关联关系类别" col="5" formControl="true"/>
							<bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelCategory"
									   includes="A,E" value="${personRelationVo.relCategory}">
								<be:event type="select">
									setRelCategorySub(e.target.value);
								</be:event>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="关系" col="5" formControl="true"/>
							<bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Rel" value="${personRelationVo.relCategorySub}">
							</bf:select>
						</bc:col>


						<bc:col col="4">
							<bc:label text="生效日期" col="5" formControl="true"/>
							<bf:datebox name="effectiveDate" id="effectiveDate" col="7" format="date" value="${personRelationVo.effectiveDate}">
							</bf:datebox>
						</bc:col>
						<bc:col col="4">
							<bc:label text="失效日期" col="5" formControl="true"/>
							<bf:datebox name="ineffectiveDate" id="ineffectiveDate" col="7" format="date" value="${personRelationVo.ineffectiveDate}">
							</bf:datebox>
						</bc:col>
						<bc:col col="4">
							<bc:label text="关联关系说明" col="5" formControl="true"/>
							<bf:text id="relStatement" name="relStatement" col="7" value="${personRelationVo.relStatement}"><bv:notEmpty/></bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="该关系是否有效" col="5" formControl="true"/>
							<bf:select id="isValid" name="isValid" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-valid"
									   value="${personRelationVo.isValid}" disabled="false">
							</bf:select>
						</bc:col>
						<%--<bc:col col="4">--%>
						<%--<bc:label text="持股占比" col="5" formControl="true"/>--%>
						<%--<bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"--%>
						<%--value="${personRelationVo.shareholdingProportion}" disabled="true">--%>
						<%--</bf:text>--%>
						<%--</bc:col>--%>
					</bf:formGroup>
				</div>
				<%--<div id="voteProportion"  class="voteProportion">--%>
					<%--<bc:col col="4">--%>
						<%--<bc:label text="表决权占比：" col="5" formControl="true"/>--%>
						<%--<bf:text id="voteProportion" name="voteProportion" col="7"--%>
								 <%--value="${personRelationVo.voteProportion}" disabled="true">--%>
						<%--</bf:text>--%>
					<%--</bc:col>--%>
				<%--</div>--%>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2" style="padding-top: 3%">
						<bc:button type="success" outline="true" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
								   var parentPid=$('#savefromPid').val();
									$.mask.post('${base}/bus/relPsnSuspect/saveSuspectAffiliate/'+parentPid+'/${fromPid}',formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												var opt = $("#opt").val();
												window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/'+${fromPid};
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
						<bc:button type="primary" text="取消" icon="arrow-circle-left">
							<be:event>
								var opt = $("#opt").val();
								window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/'+${fromPid};
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var relCategory = '${personRelationVo.relCategory}';
    $(document).ready(function(){
        //初始化内容脚本
        if(relCategory==''){
            relCategory='A';
        }
        setRelCategorySub(relCategory);
    });

    function setRelCategorySub(value){
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value+'&type=p',
            dataType: 'json',
            delay: 200,
            success: function(data){

                var arr = new Array();
                $.each(data, function(i, val){
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                $("#relCategorySub").empty();
                $("#relCategorySub").select2({data: arr, width: "100%"});
            }
        });
    }

    //输入身份证反显关联方
    $("#identifyNumber").blur(function () {
        var name = $("#name").val();
        var identifyType = $("#identifyType").val();
        var identifyNumber = $(this).val();
        if (identifyNumber != '' && identifyNumber != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelPsn',
                data: {
                    "name": name,
                    "identifyType": identifyType,
                    "identifyNumber": identifyNumber
                },
                dataType: 'json',
                success: function (data) {
                    $("#identifyNumber").empty();
                    if (data.length > 0) {
                        $("#savefromPid").val(data[0].pid);
                        $("#name").val(data[0].name);
                        $("#domesticOrForeign").val(data[0].domesticOrForeign);
                        $("[name='birthdate']").val(data[0].birthdate);
                        $("#residentialAddress").val(data[0].residentialAddress);
                        $("#shareholdingProportion").val(data[0].shareholdingProportion);
                        $("#voteProportion").val(data[0].voteProportion);
                    }else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#identifyNumber").empty();
                        });
                    }
                }
            });
        }

    });
</script>
</body>
</html>
