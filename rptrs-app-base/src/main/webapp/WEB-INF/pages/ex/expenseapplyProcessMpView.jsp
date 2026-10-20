<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Expenseapply</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="expenseapplyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
			<bf:hidden name="traId" id="traId"></bf:hidden>
			<bf:hidden name="divId" id="divId"></bf:hidden>
			<bf:hidden name="applyStatus" id="applyStatus" value="${expenseapply.applyStatus }"></bf:hidden>
			<bf:hidden name="staffId" id="staffId" value="${expenseapply.staffId }"></bf:hidden>
			<bf:hidden name="ano" id="ano" value="${expenseapply.ano }"></bf:hidden>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="申请名称" col="4" formControl="true"/>
						<bf:text id="appTitle" name="appTitle" col="8" value="${expenseapply.appTitle}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="申请类型" col="4" formControl="true"/>
						<bf:text id="appType" name="appType" col="8" value="${expenseapply.appType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="开始日期" col="4" formControl="true"/>
						<bf:datebox id="beginDate" name="beginDate" col="8" value="${expenseapply.beginDate}"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="结束日期" col="4" formControl="true"/>
						<bf:datebox id="endDate" name="endDate" col="8" value="${expenseapply.endDate}"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否跨区" col="4" formControl="true"/>
						<bf:select id="isOr" name="isOr" col="8" dataSource="custom" url="sys/dictItem/getDictItems/EX-Is_O_R" value="${expenseapply.isOr}" >
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="申请目的" col="4" formControl="true"/>
						<bf:select id="purpose" name="purpose" col="8" dataSource="custom" url="sys/dictItem/getDictItems/EX-Purpose" value="${expenseapply.purpose}" >
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="项目编号" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${expenseapply.pid}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机会号" col="4" formControl="true"/>
						<bf:text id="oppId" name="oppId" col="8" value="${expenseapply.oppId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="ccc" col="4" formControl="true"/>
						<bf:text id="ccc" name="ccc" col="8" value="${expenseapply.ccc}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="项目实体" col="4" formControl="true"/>
						<bf:text id="entityId" name="entityId" col="8" value="${expenseapply.entityId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="预算总金额" col="4" formControl="true"/>
						<bf:text id="budgetAmt" name="budgetAmt" col="8" value="${expenseapply.budgetAmt}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否借款" col="4" formControl="true"/>
						<bf:select id="isBorrow" name="isBorrow" col="8" dataSource="custom" url="sys/dictItem/getDictItems/SYS-IsNo" value="${expenseapply.isBorrow}" >
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="申请人联系电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${expenseapply.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="计划支付日期" col="4" formControl="true"/>
						<bf:datebox id="planPayDate" name="planPayDate" col="8" value="${expenseapply.planPayDate}"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="备注" col="4" formControl="true"/>
						<bf:text id="applyDesc" name="applyDesc" col="8" value="${expenseapply.applyDesc}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<div class="ys_tlist" id="travelSegmentDiv">
					<table class="table table-hover" width="95%">
						<thead>
							<tr>
								<th style="width:100px">差旅段类型</th>
								<th style="width:70px">是否往返</th>
								<th style="width:70px">是否公司办理</th>
								<th>出发地点</th>
								<th>到达地点</th>
								<th>开始日期</th>
								<th>结束日期</th>
								<th>公司协议酒店</th>
								<th>币种</th>
								<th>预算金额</th>
								<th style="width:50px">操作</th>
							</tr>
						</thead>
						<tbody class="travelSegmentBoxs">
							<c:forEach items="${travelsegments }" var="t" varStatus="s">
								<tr>
									<td><bf:select id="tstype${s.index+1 }" name="tstype${s.index+1 }" dataSource="custom" url="sys/dictItem/getDictItems/EX-TSType" value="${t.tstype }"></bf:select></td>
									<td><bf:select id="isGb${s.index+1 }" name="isGb${s.index+1 }" dataSource="custom" url="sys/dictItem/getDictItems/SYS-IsNo" value="${t.isGb }"></bf:select></td>
									<td><bf:select id="isCp${s.index+1 }" name="isCp${s.index+1 }" dataSource="custom" url="sys/dictItem/getDictItems/SYS-IsNo" value="${t.isCp }"></bf:select></td>
									<td><input name="startLoc${s.index+1 }" style="width:80px" value="${t.startLoc }"/></td>
									<td><input name="endLoc${s.index+1 }" style="width:80px" value="${t.endLoc }"/></td>
									<td><bf:datebox id="traBeginDate${s.index+1 }" name="traBeginDate${s.index+1 }" value="${t.traBeginDate }"/></td>
									<td><bf:datebox id="traEndDate${s.index+1 }" name="traEndDate${s.index+1 }" value="${t.traEndDate }"/></td>
									<td><input name="agmtHotel${s.index+1 }" style="width:100px" value="${t.agmtHotel }"/></td>
									<td><input name="currencyId${s.index+1 }" value="CNY" style="width:50px"/></td>
									<td><input name="traBudgetAmt${s.index+1 }" id="traBudgetAmt${s.index+1 }" style="width:50px" value="${t.budgetAmt }"/></td>
									<bf:hidden name="tsno${s.index+1 }" id="tsno${s.index+1 }" value="${t.tsno }"></bf:hidden>
									<td><em class="online delTraBtn">x</em></td>
								</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
				<div class="ys_tlist">
					<table class="table table-hover" width="95%">
						<thead>
							<tr>
								<th>预算日期</th>
								<th style="width: 200px;">费用科目</th>
								<th>事项说明</th>
								<th>币种</th>
								<th>预算金额</th>
								<th style="width:50px">操作</th>
							</tr>
						</thead>
						<tbody class="dateboxs">
							<c:forEach items="${budgetdetails }" var="b" varStatus="s">
								<tr>
									<td><bf:datebox id="budgeDate${s.index+1 }" name="budgeDate${s.index+1 }" format="date" value="${b.budgeDate }"/></td>
									<td><bf:select name="exSubject${s.index+1 }" dataSource="custom" url="ex/feesubject/queryAllFeesubjects?exCategory=${expenseapply.appType }" value="${b.exSubject }"></bf:select></td>
									<td><input name="budgetDesc${s.index+1 }" value="${b.budgetDesc }"/></td>
									<td><input name="currencyId${s.index+1 }" value="CNY"/></td>
									<td><input name="budgetDetailAmt${s.index+1 }" id="budgetDetailAmt${s.index+1 }" value="${b.budgetAmt }"/></td>
									<td><em class="online delbtn">x</em></td>
									<bf:hidden name="seqNo${s.index+1 }" id="seqNo${s.index+1 }" value="${b.seqNo }"></bf:hidden>
								</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<div class="mail-body">
						<bc:upload id="upload" entityType="ExpenseAccount" entityId="${expenseapply.eid}${expenseapply.ano}" uploadEnable="true" removeEnable="true"/>
					</div>
				</bf:formGroup>
				<%-- <bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								saveExpenseapply();
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/ex/expenseapply/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup> --%>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData"></div>
<script type="text/javascript">
$(document).ready(function(){
	$("input").attr("readonly","readonly");
	judgeTravle();
});

function judgeTravle(){
	var appType = $("#appType").val();
	if(appType=="Business_Travel"){
		$("#travelSegmentDiv").show();
	}else{
		$("#travelSegmentDiv").hide();
	}
	$.ajax({  

        type:"POST", 

        url:"${base}/ex/feesubject/queryAll/" + appType, 
        
        async:false,

        dataType:"html", 

        success:function(result){  
      	 $(".exSubject bf:option").remove();
      	 var ajaxobj=eval("("+result+")");
      	 var option = "<option value='N/A'>请选择</option>";
      	 for(var o in ajaxobj){
      	      option+="<option value='"+ajaxobj[o]+"'>"+o+"</option>";
      	    }
      	 
   	      $(".exSubject").html(option);
       }
 
	});
}

function queryEntity(){
	var pid = $("#pid").val();
	$.ajax({  

        type:"POST", 

        url:"${base}/pm/project/queryEntity/" + pid, 

        dataType:"html", 

        success:function(result){  
      		$("#entityId bf:option").remove();
      		 var ajaxobj=eval("("+result+")");
         	 var option = "<option value='N/A'>请选择</option>";
         	 for(var o in ajaxobj){
         	     option+="<option value='"+ajaxobj[o]+"'>"+o+"</option>";
         	 }
         	 
       	    $("#entityId").html(option);
      		
        }
	});
	queryOppId(pid);
	queryCcc(pid);
}	

function queryOppId(pid){
	$.ajax({  

        type:"POST", 

        url:"${base}/pm/project/queryOpp/" + pid, 

        dataType:"html", 

        success:function(result){  
      		var ajaxobj = eval("("+result+")");
      		var oppId = ajaxobj.oppId;
      		$("#oppId").val(oppId);
        }
	});
}

function queryCcc(pid){
	$.ajax({  

        type:"POST", 

        url:"${base}/pm/project/queryCcc/" + pid, 

        dataType:"html", 

        success:function(result){  
      		var ajaxobj = eval("("+result+")");
      		var ccc = ajaxobj.ccc;
      		$("#ccc").val(ccc);
        }
	});
}

function queryDetailExSubject(divId){
	var appType = $("#appType").val();
	$.ajax({  

        type:"POST", 

        url:"${base}/ex/feesubject/queryAll/" + appType, 

        dataType:"html", 

        success:function(result){  
      	 $(".budgetDetail"+divId+" bf:option").remove();
      	 var ajaxobj=eval("("+result+")");
      	 var option = "<option value='N/A'>请选择</option>";
      	 for(var o in ajaxobj){
      	      option+="<option value='"+ajaxobj[o]+"'>"+o+"</option>";
      	    }
      	 
   	      $(".budgetDetail"+divId+"").html(option);
       }
 
	});
}

function queryTraDetailExSubject(traId){
	console.log(traId);
	$.ajax({  

        type:"POST", 

        url:"${base}/ex/feesubject/queryAllAppType", 

        dataType:"html", 

        success:function(result){  
      	 $(".tstype"+traId+" bf:option").remove();
      	 var ajaxobj=eval("("+result+")");
      	 var option = "<option value='N/A'>请选择</option>";
      	 for(var o in ajaxobj){
      	      option+="<option value='"+ajaxobj[o]+"'>"+o+"</option>";
      	    }
      	 
   	      $(".tstype"+traId+"").html(option);
       }
 
	});
}

$("#addTravelSegment").click(function(){
	var traId = $("#traId").val();
	traId++;
	var table = $(".travelSegmentBoxs");
	var tr = "<tr><td><select class='tstype"+traId+"' name='tstype'></select></td>"+
	"<td><select name='isGb"+traId+"'><option value='Y'>是</option><option value='N'>否</option></select></td>"+
	"<td><select name='isCp"+traId+"'><option value='Y'>是</option><option value='N'>否</option></select></td>"+
	"<td><input name='startLoc"+traId+"' style='width:80px'/></td>"+
	"<td><input name='endLoc"+traId+"' style='width:80px'/></td>"+
	"<td><div><div class='input-group date' id='"+traId+"traBegin'><input type='text' class='form-control' name='traBeginDate"+traId+"' id='"+traId+"traInput'><span class='input-group-addon'><i class='fa fa-calendar'></i></span></div></div></td>"+
	"<td><div><div class='input-group date' id='"+traId+"traEnd'><input type='text' class='form-control' name='traEndDate"+traId+"' id='"+traId+"traInput'><span class='input-group-addon'><i class='fa fa-calendar'></i></span></div></div></td>"+
	"<td><input name='agmtHotel"+traId+"' style='width:100px'/></td>"+
	"<td><input name='currencyId"+traId+"' value='CNY' style='width:50px'/></td>"+
	"<td><input name='traBudgetAmt"+traId+"' id='traBudgetAmt"+traId+"' style='width:50px'/></td>"+
	"<td><em class='online delTraBtn'>x</em></td></tr>";
	table.append(tr);
	$("#traId").attr("value",traId);
	$(".delTraBtn").bind("click",function(){
    	$(this).parents("tr").empty();
    	traId--;
    	$("#traId").attr("value",traId);
    });
	
	$("#"+traId+"traBegin").datetimepicker({
		format:"yyyy-mm-dd",
		language:"zh-CN",
		autoclose:1,
		todayBtn:"linked",
		minView: "month"
	});
	$("#"+traId+"traEnd").datetimepicker({
		format:"yyyy-mm-dd",
		language:"zh-CN",
		autoclose:1,
		todayBtn:"linked",
		minView: "month"
	});
	queryTraDetailExSubject(traId);	
});

var divId = ${maxBudIndex};
$("#divId").attr("value",divId);
$("#addBudgetDetail").click(function(){
	divId++;
	$("#divId").attr("value",divId);
	var table = $(".dateboxs");
	var tr = "<tr><td><div><div class='input-group date' id='"+divId+"div'><input type='text' class='form-control' name='budgeDate"+divId+"' id='"+divId+"input'><span class='input-group-addon'><i class='fa fa-calendar'></i></span></div></div></td>"+
			"<td><select name='exSubject' class='budgetDetail"+divId+"'></select></td>"+
			"<td><input name='budgetDesc"+divId+"'/></td>"+
			"<td><input name='currencyId' value='CNY'/></td>"+
			"<td><input name='budgetDetailAmt"+divId+"' id='budgetDetailAmt"+divId+"'/></td><td><em class='online delbtn'>x</em></td></tr>";
			table.append(tr);
			
	$(".delbtn").bind("click",function(){
    	$(this).parents("tr").empty();
    	divId--;
    	$("#divId").attr("value",divId);
    });
	
	$("#"+divId+"div").datetimepicker({
		format:"yyyy-mm-dd",
		language:"zh-CN",
		autoclose:1,
		todayBtn:"linked",
		minView: "month"
	});
	queryDetailExSubject(divId);	
});

function countAmt(){
	var budgetAmt = 0;
	for(var i=1;i<=divId;i++){
		var amt = $("#budgetDetailAmt"+i+"").val();
		budgetAmt = budgetAmt + Number(amt);
	}
	var traId = $("#traId").val();
	for(var i=1;i<=traId;i++){
		var amt = $("#traBudgetAmt"+i+"").val();
		budgetAmt = budgetAmt + Number(amt);
		$("#budgetAmt").attr("value",budgetAmt);
	}
}

function saveExpenseapply(){
	countAmt();
	$('#editForm').data('formValidation').validate();
	var isValid = $('#editForm').data('formValidation').isValid();
	if(isValid)
	{
		var formData = $('#editForm').serializeAll();
		$.mask.post('${base}/ex/expenseapply/saveAll', formData, function(data){
			if(data.success)
			{
				$.messager.alert('提示', data.msg, 'info', function(){
					window.location = '${base}/ex/expenseapply/list';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
}

</script>
</body>
</html>
