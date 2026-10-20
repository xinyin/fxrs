<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>调度任务管理</title>
<style type="text/css">
.btnCluster{
	background: white;
}
.btnCluster.active{
	background: #e7eaec;
}


.spinner {  
  width: 100%;  
}  
.spinner input {  
  text-align: right;  
}  
.input-group-btn-vertical {  
  position: relative;  
  white-space: nowrap;  
  width: 1%;  
  vertical-align: middle;  
  display: table-cell;  
}  
.input-group-btn-vertical > .btn {  
  display: block;  
  float: none;  
  width: 100%;  
  max-width: 100%;  
  padding: 8px;  
  margin-left: -1px;  
  position: relative;  
  border-radius: 0;  
}  
.input-group-btn-vertical > .btn:first-child {  
  border-top-right-radius: 4px;  
}  
.input-group-btn-vertical > .btn:last-child {  
  margin-top: -2px;  
  border-bottom-right-radius: 4px;  
}  
.input-group-btn-vertical i{  
  position: absolute;  
  top: 0;  
  left: 4px;  
}  

</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="任务编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${jobEntity.code}">
							<bv:remote url="${base}/sch/jobEntity/check" name="code" message="任务编码"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="类别编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${jobEntity.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="日历" col="4" formControl="true"/>
						<bf:select id="calendarCode" name="calendarCode" col="8" value="${jobEntity.calendarCode}" dataSource="custom" url="/sch/calendarDetail/queryAll">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型" col="4" formControl="true"/>
						<bf:select id="groupCode" name="groupCode" col="8" value="${jobEntity.groupCode}" dataSource="custom" url="/sch/jobGroup/queryAll">
							<be:event type="select">initTableData();</be:event>
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="任务类型1" col="4" formControl="true"/>
						<bf:checkGroup id="jobType1" name="jobType1" col="8">
							<bf:radio dataSource="fixedDict" url="JobType1Dict" checked="true" value="${jobEntity.jobType1}"></bf:radio>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型2" col="4" formControl="true"/>
						<bf:checkGroup id="jobType2" name="jobType2" col="8">
							<bf:radio dataSource="fixedDict" url="JobType2Dict" checked="true" value="${jobEntity.jobType2}"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="12">
						<bc:label text="范围类型" col="2" formControl="true"/>
						<bf:checkGroup id="nodeScopeType" name="nodeScopeType" col="8">
							<bf:radio dataSource="fixedDict" url="NodeScopeTypeDict" checked="true" value="${jobEntity.nodeScopeType}" click="nodeScopeTypeClick()"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<div class="col-sm-10 col-sm-offset-2"  id="nodeScopeDiv">
						
					</div>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="12">
						<bc:label text="节点算法" col="2" formControl="true"/>
						<bf:checkGroup id="nodeStrategy" name="nodeStrategy" col="8">
							<bf:radio dataSource="fixedDict" url="NodeStrategyDict" checked="true" value="${jobEntity.nodeStrategy}"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="开始日期" col="4" formControl="true"/>
						<bf:datebox id="startDate" name="startDate" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="结束日期" col="4" formControl="true"/>
						<bf:datebox id="endDate" name="endDate" col="8" />
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="超时时间" col="4" formControl="true"/>
						<bf:select id="timeout" name="timeout" col="5" value="${jobEntity.timeout}">
						</bf:select>
						<bf:select id="timeoutUnit" name="timeoutUnit" col="3" value="${jobEntity.timeoutUnit}" dataSource="fixedDict" url="TimeoutUnitDict">
							<be:event type="select" >timeoutUnitClick(this)</be:event>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="超时处理" col="4" formControl="true"/>
						<bf:checkGroup id="timeoutInstruction" name="timeoutInstruction" col="8">
							<bf:radio dataSource="fixedDict" url="TimeoutInstructionDict" checked="true" value="${jobEntity.timeoutInstruction}"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="失败处理" col="4" formControl="true"/>
						<bf:checkGroup id="failInstruction" name="failInstruction" col="8">
							<bf:radio dataSource="fixedDict" url="FailInstructionDict" checked="true" value="${jobEntity.failInstruction}"></bf:radio>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="6">
						<bc:label text="优先级" col="4" formControl="true"/>
						<bf:select name="priority" id="priority" col="8" value="${jobEntity.priority}">
							<bf:option text="1" value="1"/>
							<bf:option text="2" value="2"/>
							<bf:option text="3" value="3"/>
							<bf:option text="4" value="4"/>
							<bf:option text="5" value="5" selected="true"/>
							<bf:option text="6" value="6"/>
							<bf:option text="7" value="7"/>
							<bf:option text="8" value="8"/>
							<bf:option text="9" value="9"/>
							<bf:option text="10" value="10"/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="2">
						<bc:label text="参数" col="12" formControl="true"/>
					</bc:col>
					<bc:col col="10">
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
						<bc:label text="任务表达式" col="2" formControl="true"/>
						<div class=" col-xs-10">
							<div id="corn" data-toggle="jTab" class="tabs-container ">
								<ul class="nav nav-tabs" >
									<li class="tab-list active"><a name="tab-1" href="#tab-1">秒</a></li>
									<li class="tab-list "><a name="tab-2" href="#tab-2">分钟</a></li>
									<li class="tab-list "><a name="tab-3" href="#tab-3">小时</a></li>
									<li class="tab-list "><a name="tab-4" href="#tab-4">日</a></li>
									<li class="tab-list "><a name="tab-5" href="#tab-5">月</a></li>
									<li class="tab-list "><a name="tab-6" href="#tab-6">周</a></li>
									<li class="tab-list "><a name="tab-7" href="#tab-7">年</a></li>
								</ul>
								<div class="tab-content">
									<div id="tab-1" class="tab-pane active">
										<div class="wrapper wrapper-content">
											<div class="row m-b">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="second" checked="checked" class="form-control" onclick="everyTime(this)"/>
													<label>每秒 允许的通配符[, - * /]</label>
												</div>
											</div>
											<div class="row m-b">
												<div class="col-xs-1" style="height: 35px;padding: 0;">
													<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
														<input type="radio" name="second" class="form-control" onclick="cycle(this)"/>
														<label>周期从</label>
													</div>
												</div>
												<div class="col-xs-1" style="height: 35px;padding: 0;">
													<div class="input-group spinner second1">  
													    <input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,'')" id="secondStart_0" value="1" style="border: 1px solid #e7eaec;">  
													    <div class="input-group-btn-vertical">  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
													    </div>  
													 </div>  
												</div>
												<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
													<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
												</div>
												<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
													<div class="input-group spinner second2">  
													    <input type="text" class="form-control numberspinner" id="secondEnd_0" onkeyup="value=value.replace(/\D/g,'')" value="2" style="border: 1px solid #e7eaec;">  
													    <div class="input-group-btn-vertical">  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
													    </div>  
													 </div>  
												</div>
												<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
													<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒</label>
												</div>
											</div>
											<div class="row m-b">
												<div class="col-xs-1" style="height: 35px;padding: 0;">
													<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
														<input type="radio" name="second" onclick="startOn(this)" class="form-control"/>
														<label>从</label>
													</div>
												</div>
												<div class="col-xs-1" style="height: 35px;padding: 0;">
													<div class="input-group spinner second3">  
													    <input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,'')" id="secondStart_1" value="1" style="border: 1px solid #e7eaec;">  
													    <div class="input-group-btn-vertical">  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
													    </div>  
													 </div>  
												</div>
												<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
													<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒开始, 每</label>
												</div>
												<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
													<div class="input-group spinner second4">  
													    <input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,'')" id="secondEnd_1" value="1" style="border: 1px solid #e7eaec;">  
													    <div class="input-group-btn-vertical">  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
													      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
													    </div>  
													 </div>  
												</div>
												<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
													<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒执行一次</label>
												</div>
											</div>
											<div class="row m-b">
												<div class="col-xs-1" style="height: 35px;padding: 0;">
													<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
														<input type="radio" name="second" id="sencond_appoint" class="form-control"/>
														<label>指定</label>
													</div>
												</div>
											</div>
											<div class="row m-b">
												<div class="col-xs-0 secondList">
													<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="0" class="form-control "><label >00</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >02</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >03</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >04</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >05</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >06</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >07</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="8" class="form-control "><label >08</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="9" class="form-control "><label >09</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="10" class="form-control "><label >10</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="11" class="form-control "><label >11</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="12" class="form-control "><label >12</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="13" class="form-control "><label >13</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="14" class="form-control "><label >14</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="15" class="form-control "><label >15</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="16" class="form-control "><label >16</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="17" class="form-control "><label >17</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="18" class="form-control "><label >18</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="19" class="form-control "><label >19</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="20" class="form-control "><label >20</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="21" class="form-control "><label >21</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="22" class="form-control "><label >22</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="23" class="form-control "><label >23</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="24" class="form-control "><label >24</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="25" class="form-control "><label >25</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="26" class="form-control "><label >26</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="27" class="form-control "><label >27</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="28" class="form-control "><label >28</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="29" class="form-control "><label >29</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="30" class="form-control "><label >30</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="31" class="form-control "><label >31</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="32" class="form-control "><label >32</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="33" class="form-control "><label >33</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="34" class="form-control "><label >34</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="35" class="form-control "><label >35</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="36" class="form-control "><label >36</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="37" class="form-control "><label >37</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="38" class="form-control "><label >38</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="39" class="form-control "><label >39</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="40" class="form-control "><label >40</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="41" class="form-control "><label >41</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="42" class="form-control "><label >42</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="43" class="form-control "><label >43</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="44" class="form-control "><label >44</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="45" class="form-control "><label >45</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="46" class="form-control "><label >46</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="47" class="form-control "><label >47</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="48" class="form-control "><label >48</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="49" class="form-control "><label >49</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="50" class="form-control "><label >50</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="51" class="form-control "><label >51</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="52" class="form-control "><label >52</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="53" class="form-control "><label >53</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="54" class="form-control "><label >54</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="55" class="form-control "><label >55</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="56" class="form-control "><label >56</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="57" class="form-control "><label >57</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="58" class="form-control "><label >58</label></div>
													<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="59" class="form-control "><label >59</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-2" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="min" checked="checked" class="form-control" onclick="everyTime(this)"/>
												<label>分钟 允许的通配符[, - * /]</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="min" class="form-control" onclick="cycle(this)"/>
													<label>周期从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner min1">  
												    <input type="text" class="form-control numberspinner" value="1" onkeyup="value=value.replace(/\D/g,'')" id="minStart_0" style="border: 1px solid #e7eaec;"/>  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner min2">  
												    <input type="text" class="form-control numberspinner" value="2" onkeyup="value=value.replace(/\D/g,'')" id="minEnd_0" style="border: 1px solid #e7eaec;"/>  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="min" class="form-control" onclick="startOn(this);"/>
													<label>从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner min3">  
												    <input type="text" class="form-control numberspinner" value="0" onkeyup="value=value.replace(/\D/g,'')" id="minStart_1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟开始, 每</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner min4">  
												    <input type="text" class="form-control numberspinner" value="1" onkeyup="value=value.replace(/\D/g,'')" id="minEnd_1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟执行一次</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="min" class="form-control" id="min_appoint"/>
													<label>指定</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-0 minList">
												<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="0" class="form-control "><label >00</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >02</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >03</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >04</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >05</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >06</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >07</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="8" class="form-control "><label >08</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="9" class="form-control "><label >09</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="10" class="form-control "><label >10</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="11" class="form-control "><label >11</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="12" class="form-control "><label >12</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="13" class="form-control "><label >13</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="14" class="form-control "><label >14</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="15" class="form-control "><label >15</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="16" class="form-control "><label >16</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="17" class="form-control "><label >17</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="18" class="form-control "><label >18</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="19" class="form-control "><label >19</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="20" class="form-control "><label >20</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="21" class="form-control "><label >21</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="22" class="form-control "><label >22</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="23" class="form-control "><label >23</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="24" class="form-control "><label >24</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="25" class="form-control "><label >25</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="26" class="form-control "><label >26</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="27" class="form-control "><label >27</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="28" class="form-control "><label >28</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="29" class="form-control "><label >29</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="30" class="form-control "><label >30</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="31" class="form-control "><label >31</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="32" class="form-control "><label >32</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="33" class="form-control "><label >33</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="34" class="form-control "><label >34</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="35" class="form-control "><label >35</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="36" class="form-control "><label >36</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="37" class="form-control "><label >37</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="38" class="form-control "><label >38</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="39" class="form-control "><label >39</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="40" class="form-control "><label >40</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="41" class="form-control "><label >41</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="42" class="form-control "><label >42</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="43" class="form-control "><label >43</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="44" class="form-control "><label >44</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="45" class="form-control "><label >45</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="46" class="form-control "><label >46</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="47" class="form-control "><label >47</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="48" class="form-control "><label >48</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="49" class="form-control "><label >49</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="50" class="form-control "><label >50</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="51" class="form-control "><label >51</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="52" class="form-control "><label >52</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="53" class="form-control "><label >53</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="54" class="form-control "><label >54</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="55" class="form-control "><label >55</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="56" class="form-control "><label >56</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="57" class="form-control "><label >57</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="58" class="form-control "><label >58</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="59" class="form-control "><label >59</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-3" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="hour" checked="checked" class="form-control" onclick="everyTime(this)"/>
												<label>小时 允许的通配符[, - * /]</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="hour" class="form-control" onclick="cycle(this)"/>
													<label>周期从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner hour1">  
												    <input type="text" class="form-control numberspinner" id="hourStart_0" value="0" onkeyup="value=value.replace(/\D/g,'')" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner hour2">  
												    <input type="text" class="form-control numberspinner" value="2" id="hourEnd_0" onkeyup="value=value.replace(/\D/g,'')" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="hour" class="form-control" onclick="startOn(this)"/>
													<label>从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner hour3">  
												    <input type="text" class="form-control numberspinner" id="hourStart_1" value="0" onkeyup="value=value.replace(/\D/g,'')" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时开始, 每</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner hour4">  
												    <input type="text" class="form-control numberspinner" id="hourEnd_1" value="1" onkeyup="value=value.replace(/\D/g,'')" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时执行一次</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="hour" id="hour_appoint" class="form-control"/>
													<label>指定</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-0 hourList">
												<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="0" class="form-control "><label >00</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >02</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >03</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >04</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >05</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >06</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >07</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="8" class="form-control "><label >08</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="9" class="form-control "><label >09</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="10" class="form-control "><label >10</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="11" class="form-control "><label >11</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="12" class="form-control "><label >12</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="13" class="form-control "><label >13</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="14" class="form-control "><label >14</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="15" class="form-control "><label >15</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="16" class="form-control "><label >16</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="17" class="form-control "><label >17</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="18" class="form-control "><label >18</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="19" class="form-control "><label >19</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="20" class="form-control "><label >20</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="21" class="form-control "><label >21</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="22" class="form-control "><label >22</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="23" class="form-control "><label >23</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-4" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="day" checked="checked" class="form-control" onclick="everyTime(this)"/>
												<label>日 允许的通配符[, - * / L W]</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="day" class="form-control" onclick="unAppoint(this)"/>
												<label>不指定</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="day" class="form-control" onclick="cycle(this)"/>
													<label>周期从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner day1">  
												    <input type="text" class="form-control numberspinner" id="dayStart_0" onkeyup="value=value.replace(/\D/g,'')" value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner day2">  
												    <input type="text" class="form-control numberspinner" id="dayEnd_0" onkeyup="value=value.replace(/\D/g,'')" value="2" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="day" class="form-control" onclick="startOn(this)"/>
													<label>从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner day3">  
												    <input type="text" class="form-control numberspinner"  id="dayStart_1" onkeyup="value=value.replace(/\D/g,'')" value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日开始, 每</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner day4">  
												    <input type="text" class="form-control numberspinner" id="dayEnd_1" onkeyup="value=value.replace(/\D/g,'')" value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">天执行一次</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="day" class="form-control" onclick="workDay(this)"/>
													<label>每月</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner day5">  
												    <input type="text" class="form-control numberspinner" id="dayStart_2" onkeyup="value=value.replace(/\D/g,'')" value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-2" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">号最近的那个工作日</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-2" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="day" class="form-control" onclick="lastDay(this)"/>
													<label>本月最后一天</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="day" class="form-control" id="day_appoint"/>
													<label>指定</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-0 dayList">
												<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >02</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >03</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >04</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >05</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >06</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >07</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="8" class="form-control "><label >08</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="9" class="form-control "><label >09</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="10" class="form-control "><label >10</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="11" class="form-control "><label >11</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="12" class="form-control "><label >12</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="13" class="form-control "><label >13</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="14" class="form-control "><label >14</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="15" class="form-control "><label >15</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="16" class="form-control "><label >16</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="17" class="form-control "><label >17</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="18" class="form-control "><label >18</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="19" class="form-control "><label >19</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="20" class="form-control "><label >20</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="21" class="form-control "><label >21</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="22" class="form-control "><label >22</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="23" class="form-control "><label >23</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="23" class="form-control "><label >23</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="24" class="form-control "><label >24</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="25" class="form-control "><label >25</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="26" class="form-control "><label >26</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="27" class="form-control "><label >27</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="28" class="form-control "><label >28</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="29" class="form-control "><label >29</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="30" class="form-control "><label >30</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="31" class="form-control "><label >31</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-5" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="month" checked="checked" class="form-control" onclick="everyTime(this)"/>
												<label>月 允许的通配符[, - * /]</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="month" class="form-control" onclick="unAppoint(this)"/>
												<label>不指定</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="month" class="form-control" onclick="cycle(this)"/>
													<label>周期从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner month1">  
												    <input type="text" class="form-control numberspinner" id="monthStart_0" onkeyup="value=value.replace(/\D/g,'')"  value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner month2">  
												    <input type="text" class="form-control numberspinner" id="monthEnd_0" onkeyup="value=value.replace(/\D/g,'')"  value="2" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">月</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="month" class="form-control" onclick="startOn(this)"/>
													<label>从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner month3">  
												    <input type="text" class="form-control numberspinner" id="monthStart_1" onkeyup="value=value.replace(/\D/g,'')"  value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日开始, 每</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner month4">  
												    <input type="text" class="form-control numberspinner" id="monthEnd_1" onkeyup="value=value.replace(/\D/g,'')"  value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">月执行一次</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="month" class="form-control" id="month_appoint"/>
													<label>指定</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-0 monthList">
												<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >02</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >03</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >04</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >05</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >06</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >07</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="8" class="form-control "><label >08</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="9" class="form-control "><label >09</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="10" class="form-control "><label >10</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="11" class="form-control "><label >11</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="12" class="form-control "><label >12</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-6" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="week" checked="checked" class="form-control" onclick="everyTime(this)"/>
												<label>周 允许的通配符[, - * / L #]</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="week" class="form-control" onclick="unAppoint(this)"/>
												<label>不指定</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-2" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="week" class="form-control" onclick="startOn(this)"/>
													<label>周期从星期</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner week1">  
												    <input type="text" class="form-control numberspinner" id="weekStart_0" value="1" onkeyup="value=value.replace(/\D/g,'')"  style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner week2">  
												    <input type="text" class="form-control numberspinner" id="weekEnd_0" value="2" onkeyup="value=value.replace(/\D/g,'')"  style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="week" class="form-control" onclick="weekOfDay(this)"/>
													<label>第</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner week3">  
												    <input type="text" class="form-control numberspinner" id="weekStart_1" value="1" onkeyup="value=value.replace(/\D/g,'')"  style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">周的星期</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner week4">  
												    <input type="text" class="form-control numberspinner" id="weekEnd_1" onkeyup="value=value.replace(/\D/g,'')"  value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-2" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="week" class="form-control" onclick="lastWeek(this)"/>
													<label>本月最后一个星期</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner week5">  
												    <input type="text" class="form-control numberspinner" id="weekStart_2" onkeyup="value=value.replace(/\D/g,'')"  value="1" style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">
													<input type="radio" name="week" class="form-control" id="week_appoint"/>
													<label>指定</label>
												</div>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-0 weekList">
												<div class="checkbox  checkbox  checkbox-default checkbox-inline" style="margin-left: 10px;"><input type="checkbox" value="1" class="form-control "><label >01</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="2" class="form-control "><label >2</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="3" class="form-control "><label >3</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="4" class="form-control "><label >4</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="5" class="form-control "><label >5</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="6" class="form-control "><label >6</label></div>
												<div class="checkbox  checkbox  checkbox-default checkbox-inline"><input type="checkbox" value="7" class="form-control "><label >7</label></div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab-7" class="tab-pane">
									<div class="wrapper wrapper-content">
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="year" checked="checked" class="form-control" onclick="unAppoint(this)"/>
												<label>不指定 允许的通配符[, - * /] 非必填</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
												<input type="radio" name="year" id="yearStart_0" onkeyup="value=value.replace(/\D/g,'')"  class="form-control" onclick="everyTime(this)"/>
												<label>每年</label>
											</div>
										</div>
										<div class="row m-b">
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">
													<input type="radio" name="year" id="yearEnd_0" class="form-control" onclick="cycle(this)"/>
													<label>周期从</label>
												</div>
											</div>
											<div class="col-xs-1" style="height: 35px;padding: 0;">
												<div class="input-group spinner year1">  
												    <input type="text" class="form-control numberspinner" value="2017" onkeyup="value=value.replace(/\D/g,'')"  style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
											<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">
												<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>
											</div>
											<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">
												<div class="input-group spinner year2">  
												    <input type="text" class="form-control numberspinner" value="2018" onkeyup="value=value.replace(/\D/g,'')"  style="border: 1px solid #e7eaec;">  
												    <div class="input-group-btn-vertical">  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
												      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
												    </div>  
												 </div>  
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="12">
						   <input type="text" name="v_second" class="col" value="*" readonly="readonly" />
                            <input type="text" name="v_min" class="col" value="*" readonly="readonly" />
                            <input type="text" name="v_hour" class="col" value="*" readonly="readonly" />
                            <input type="text" name="v_day" class="col" value="*" readonly="readonly" />
                            <input type="text" name="v_month" class="col" value="*" readonly="readonly" />
                            <input type="text" name="v_week" class="col" value="?" readonly="readonly" />
                            <input type="text" name="v_year" class="col" readonly="readonly"
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="12">
						<input type="text" name="cron" style="width: 100%;" value="* * * * * ?" id="cron"  />
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" value="${jobEntity.remark}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button  type="primary" text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/jobEntity/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/sch/jobEntity/list';
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
						<bc:button text="返回">
							<be:event>
								window.location = '${base}/sch/jobEntity/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
					</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<link href="${res}/js/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${res}/js/bootstrap3-editable/js/bootstrap-editable.js"></script>
<script src="${res}/js/bootstrap3-editable/js/bootstrap-table-editable.js"></script>
<script type="text/javascript">
 
</script>
                    
<script type="text/javascript">
//节点范围， 以逗号隔开
var nodeScope = '';
var opt = '${opt}';
var paramJosn = '${param}';
$(document).ready(function(){
	//禁用表单提交事件 btnSave 参考155行
	$("#form1").attr("onsubmit", "return false");
	//初始化超时时间
	timeoutUnitClick();
	//初始化类型选择
	nodeScopeTypeClick();
	/* 初始化参数表格*/
	initTable();
	//初始化TouchSpin
	var ids = new Array();
	ids.push({id: 'second1', name: 'second', type: '-', max: 58, min: 1});
	ids.push({id: 'second2', name: 'second', type: '-', max: 59, min: 2});
	ids.push({id: 'second3', name: 'second', type: '/', max: 59, min: 0});
	ids.push({id: 'second4', name: 'second', type: '/', max: 59, min: 1});
	ids.push({id: 'min1', name: 'min', type: '-', max: 58, min: 1});
	ids.push({id: 'min2', name: 'min', type: '-', max: 59, min: 2});
	ids.push({id: 'min3', name: 'min', type: '/', max: 59, min: 0});
	ids.push({id: 'min4', name: 'min', type: '/', max: 59, min: 1});
	ids.push({id: 'hour1', name: 'hour', type: '-', max: 23, min: 0});
	ids.push({id: 'hour2', name: 'hour', type: '-', max: 23, min: 2});
	ids.push({id: 'hour3', name: 'hour', type: '/', max: 23, min: 0});
	ids.push({id: 'hour4', name: 'hour', type: '/', max: 23, min: 1});
	ids.push({id: 'day1', name: 'day', type: '-', max: 31, min: 1});
	ids.push({id: 'day2', name: 'day', type: '-', max: 31, min: 2});
	ids.push({id: 'day3', name: 'day', type: '/', max: 31, min: 1});
	ids.push({id: 'day4', name: 'day', type: '/', max: 31, min: 1});
	ids.push({id: 'day5', name: 'day', type: 'W', max: 31, min: 1});
	ids.push({id: 'month1', name: 'month', type: '-', max: 12, min: 1});
	ids.push({id: 'month2', name: 'month', type: '-', max: 12, min: 2});
	ids.push({id: 'month3', name: 'month', type: '/', max: 12, min: 1});
	ids.push({id: 'month4', name: 'month', type: '/', max: 12, min: 1});
	ids.push({id: 'week1', name: 'week', type: '/', max: 7, min: 1});
	ids.push({id: 'week2', name: 'week', type: '/', max: 7, min: 2});
	ids.push({id: 'week3', name: 'week', type: '#', max: 4, min: 1});
	ids.push({id: 'week4', name: 'week', type: '#', max: 7, min: 1});
	ids.push({id: 'week5', name: 'week', type: 'L', max: 7, min: 1});
	ids.push({id: 'year1', name: 'year', type: '-', max: 3000, min: 2017});
	ids.push({id: 'year2', name: 'year', type: '-', max: 3000, min: 2018});
	initNumber(ids);
	
	var vals = $("input[name^='v_']");
	var cron = $("#cron");
	vals.change(function() {
		var item = [];
		vals.each(function() {
			item.push(this.value);
		});
	    //修复表达式错误BUG，如果后一项不为* 那么前一项肯定不为为*，要不然就成了每秒执行了
	    //获取当前选中tab
		var currentIndex = 0;
		$('.nav-tabs>li').each(function (i, item) {
		    if($(item).hasClass('active'))
		    {
		        currentIndex =i;
		        return false;
		    }

		});
        //当前选中项之前的如果为*，则都设置成0
		for (var i = currentIndex; i >= 1; i--)
		{
		    if (item[i] != '*' && item[i - 1] == '*')
		    {
		        item[i - 1] = '0';
		    }
		}
	    //当前选中项之后的如果不为*则都设置成*
		if (item[currentIndex] == '*')
		{
		    for (var i = currentIndex + 1; i < item.length; i++)
		    {
		        if (i == 5) 
		        {
		            item[i] = '?';
		        }
		        else
		        {
		            item[i] = '*';
		        }
		    }
		}
		cron.val(item.join(' ')).change();
	});
	
	cron.change(function () {
		cronChange();
	});
	
	var secondList = $('.secondList').find('input');
	$('#sencond_appoint').click(function(){
	    if (this.checked)
	    {
	        if ($(secondList).filter(':checked').length == 0)
	        {
	            $(secondList.eq(0)).prop('checked', true);
	        }
			secondList.eq(0).change();
		}
	});
	secondList.change(function() {
		var sencond_appoint = $('#sencond_appoint').prop('checked');
		if (sencond_appoint) {
			var vals = [];
			secondList.each(function(){
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 59)
			{
				val = vals.join(','); 
			}
			else if(vals.length == 59)
			{
				val = '*';
			}
			var item = $('input[name=v_second]');
			item.val(val);
			item.change();
		}
	});
	
	var minList = $('.minList').find('input');
	$('#min_appoint').click(function(){
	    if (this.checked)
	    {
	        if ($(minList).filter(':checked').length == 0)
	        {
	            $(minList.eq(0)).prop('checked', true);
	        }
			minList.eq(0).change();
		}
	});
	minList.change(function() {
		var min_appoint = $('#min_appoint').prop('checked');
		if (min_appoint) 
		{
			var vals = [];
			minList.each(function() {
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 59)
			{
				val = vals.join(",");
			}
			else if(vals.length == 59)
			{
				val = '*';
			}
			var item = $('input[name=v_min]');
			item.val(val);
			item.change();
		}
	});
	
	var hourList = $('.hourList').find('input');
	$('#hour_appoint').click(function(){
	    if (this.checked)
	    {
	        if ($(hourList).filter(':checked').length == 0)
	        {
	            $(hourList.eq(0)).prop('checked', true);
	        }
			hourList.eq(0).change();
		}
	});
	
	hourList.change(function() {
		var hour_appoint = $('#hour_appoint').prop('checked');
		if (hour_appoint) {
			var vals = [];
			hourList.each(function() {
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 24)
			{
				val = vals.join(',');
			}
			else if(vals.length == 24)
			{
				val = '*';
			}
			var item = $('input[name=v_hour]');
			item.val(val);
			item.change();
		}
	});
	
	var dayList = $('.dayList').find('input');
	$('#day_appoint').click(function(){
	    if (this.checked) {
	        if ($(dayList).filter(':checked').length == 0)
	        {
	            $(dayList.eq(0)).prop('checked', true);
	        }
			dayList.eq(0).change();
		}
	});
	dayList.change(function() {
		var day_appoint = $('#day_appoint').prop('checked');
		if (day_appoint) 
		{
			var vals = [];
			dayList.each(function(){
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 31)
			{
				val = vals.join(',');
			}
			else if(vals.length == 31)
			{
				val = '*';
			}
			var item = $('input[name=v_day]');
			item.val(val);
			item.change();
		}
	});
	
	var monthList = $('.monthList').find('input');
	$('#month_appoint').click(function(){
	    if (this.checked) {
	        if ($(monthList).filter(':checked').length == 0)
	        {
	            $(monthList.eq(0)).prop('checked', true);
	        }
	        monthList.eq(0).change();
		}
	});
	monthList.change(function() {
		var month_appoint = $('#month_appoint').prop('checked');
		if (month_appoint) {
			var vals = [];
			monthList.each(function(){
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 12) val = vals.join(',');
			else if(vals.length == 12) val = '*';
			var item = $("input[name=v_month]");
			item.val(val);
			item.change();
		}
	});
	
	var weekList = $('.weekList').find('input');
	$('#week_appoint').click(function(){
	    if (this.checked) {
	        if ($(weekList).filter(':checked').length == 0)
	        {
	            $(weekList.eq(0)).prop('checked', true);
	        }
			weekList.eq(0).change();
		}
	});
	weekList.change(function() {
		var week_appoint = $('#week_appoint').prop('checked');
		if (week_appoint) {
			var vals = [];
			weekList.each(function(){
				if (this.checked)
				{
					vals.push(this.value);
				}
			});
			var val = '?';
			if (vals.length > 0 && vals.length < 7) val = vals.join(",");
			else if(vals.length == 7) val = '*';
			var item = $('input[name=v_week]');
			item.val(val);
			item.change();
		}
	});
	
})

function initNumber(ids){
	$.each(ids, function(i, v){
		$('.' + v.id + ' .btn:first-of-type').on('click', function() {
			var val = parseInt($('.' + v.id + ' input').val(), 10) + 1; 
			if(val >= v.min &&  val <= v.max)
			{
				$('.' + v.id + ' input').val(val);  
				var $radio = $(this).parent().parent().parent().parent().find('input[type=radio]');
				$($radio).prop('checked', true); 
				var ns = $(this).parent().parent().parent().parent().find('.numberspinner');
				var start = $(ns.eq(0)).val();
				var end = $(ns.eq(1)).val();
				var item = $("input[name=v_" + v.name + "]");
				if(v.type == 'W') item.val(start + v.type);
				else if(v.type == 'L') item.val(start + v.type);
				else item.val(start + v.type + end);
				item.change();				
			}
		});  
		
		$('.' + v.id + ' .btn:last-of-type').on('click', function() {
			var val = parseInt($('.' + v.id + ' input').val(), 10) - 1;
			if(val >= v.min &&  val <= v.max)
			{
				$('.' + v.id + ' input').val(val);  
				var $radio = $(this).parent().parent().parent().parent().find('input[type=radio]');
				$($radio).prop('checked', true);
				var ns = $(this).parent().parent().parent().parent().find('.numberspinner');
				var start = $(ns.eq(0)).val();
				var end = $(ns.eq(1)).val();
				var item = $("input[name=v_" + v.name + "]");
				if(v.type == 'W') item.val(start + v.type);
				else if(v.type == 'L') item.val(start + v.type);
				else item.val(start + v.type + end);
				item.change();	
			}
		}); 
		
		$('.' + v.id + ' input').on('blur', function() {
			var val = v.min;
			var ival = parseInt($('.' + v.id + ' input').val());
			if(!isNaN(ival))
			{
				if(ival < v.min) $('.' + v.id + ' input').val(v.min);
				if(ival > v.max) $('.' + v.id + ' input').val(v.max);
				var $radio = $(this).parent().parent().parent().find('input[type=radio]');
				$($radio).prop('checked', true);
				var ns = $(this).parent().parent().parent().find('.numberspinner');
				var start = $(ns.eq(0)).val();
				var end = $(ns.eq(1)).val();
				var item = $("input[name=v_" + v.name + "]");
				if(v.type == 'W') item.val(start + v.type);
				else if(v.type == 'L') item.val(start + v.type);
				else item.val(start + v.type + end);
				item.change();	
			}
			else 
			{
				$('.' + v.id + ' input').val(val); 
				var $radio = $(this).parent().parent().parent().find('input[type=radio]');
				$($radio).prop('checked', true);
				var ns = $(this).parent().parent().parent().find(".numberspinner");
				var start = $(ns.eq(0)).val();
				var end = $(ns.eq(1)).val();
				var item = $("input[name=v_" + v.name + "]");
				if(v.type == 'W') item.val(start + v.type);
				else if(v.type == 'L') item.val(start + v.type);
				else item.val(start + v.type + end);
				item.change();	
			}
		});
	});
}

//选择超时时间单位后处理
function timeoutUnitClick(){
	var value = $('#timeoutUnit').val();
	$('#timeout').empty();
	//小时 1 -23
	if(value == 'H')
	{
		for(var i = 1; i < 24; i++)
		{
			 $('#timeout').append('<option value="' + i + '">' + (i.toString().length < 2 ? '0' + i : i) + '</option>');
		}
	}//分钟 1 - 59
	else
	{
		for(var i = 1; i < 60; i++)
		{
			$('#timeout').append('<option value="' + i + '">' + (i.toString().length < 2 ? '0' + i : i) + '</option>');
		}
	}
}

//初始化类型选择
function nodeScopeTypeClick(){
	//清空
	nodeScope = '';
	$('#nodeScopeDiv').empty();
	var nodeScopeType = $('input[name="nodeScopeType"]:checked').val();
	if(nodeScopeType == 'CLUSTER')
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data, function(i, v){
					var html = '<button class="btn btn-white btn-xs btnCluster" id="' + v.value + 'Cluster" onclick="clickScopeView(this, \'' + v.value + '\')" data-placement="top">' + v.name + '</button>&nbsp;';
					$('#nodeScopeDiv').append(html);
				});
			}
		});
	}
	else
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryClusterAndNodeAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data.clusters, function(i, v){
					var html = '<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC;clear: inherit;margin-left: 5px;border-radius:5px;">';
					html += '<div class="ibox-title" style="height: 10px;padding: 5px 5px 5px 5px;min-height: 20px;"><h5><small>' + v.name + '</small></h5></div>';
					html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
					//循环节点
					$.each(data.nodes, function(j, n){
						if(v.code == n.clusterCode)
						{
							html += '<button class="btn btn-white btn-xs btnCluster" id="' + n.code + 'Node" onclick="clickScopeView(this, \'' + n.code + '\')" data-placement="top">' + n.name + '</button>';
						}
					});
					html += '</div></div>';
					
					$('#nodeScopeDiv').append(html);
				});
			}
		});
	}
}

//点击处理
function clickScopeView(el, val){
	
	//如果当前选中包含，需要清除
	if($(el).hasClass('active'))
	{
		nodeScope = nodeScope.replace(val + ',', '');
	}
	else
	{
		nodeScope += val + ',';
	}
	$(el).toggleClass('active');
}

/* 初始化参数表格*/
function initTable(){
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "code",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: [
        	{ field: "code", title: "编码", align: "center", width: 100},
        	{field: "type", title: "类型", align: "center", width: 100, editable: { type: 'select',  disabled: true,  mode: "type", source: [{ value: "string", text: "字符串" }, { value: "number", text: "数字" }, {value:"date", text:"日期"}], title: '参数类型'}},
        	{ field: "value", title: "值", width: 100, formatter: valFormatter}]
    });
	
	//加载数据，新增加载类型数据， 编辑加载原始数据， 选择类型后加载累心数据
	initTableData();
}

//格式化值
function valFormatter(value, row, index){
	if(row.type == 'date')
	{
		return '<a href="javascript:void(0);" id="' + row.code + 'date">' + value + '</a>';
	}
	else
	{
		return '<a href="javascript: void(0);" id="' + row.code + 'input">' + value + '</a>';
	}
}

//加载数据，新增加载类型数据， 编辑加载原始数据， 选择类型后加载累心数据
function initTableData(){
	
	//判断是否是编辑， 编辑加载数据
	if(opt == 'edit')//加载表中数据
	{
		var rows = JSON.parse(paramJosn);
		$('#optionTable').bootstrapTable('load', rows);
	}
	else//新增加载类型数据
	{
		var code = $('#groupCode').val();
		$.ajax({
			type: 'GET',
			url: '${base}/sch/jobGroup/getJobGroup/' + code,
			dataType: 'json',
			success: function(data){
				var rows = JSON.parse(data.param);
				$('#optionTable').bootstrapTable('load', rows);
				
				$.each(rows, function(i, v){
					if(v.type == 'date')
					{
						$('#' + v.code + 'date').editable({
					        type: "date",                //编辑框的类型。支持text|textarea|select|date|checklist等
					        title: "值",              //编辑框的标题
					        mode: "popup"              //编辑框的模式：支持popup和inline两种模式，默认是popup
					    });
					}
					else
					{
						$('#' + v.code + 'input').editable({
					        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
					        title: "值",              //编辑框的标题
					        emptytext: "空文本",          //空值的默认文本
					        mode: "popup",             //编辑框的模式：支持popup和inline两种模式，默认是popup
					    });
					}
				});
			}
		});
	}
}


/********表达式**********/
/**
 * 每周期
 */
function everyTime(dom) {
	var item = $("input[name=v_" + dom.name + "]");
	item.val("*");
	item.change();
}

/**
 * 周期
 */
function cycle(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().parent().find(".numberspinner");
	var start = $(ns.eq(0)).val();
	var end = $(ns.eq(1)).val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + "-" + end);
	item.change();
}

/**
 * 从开始
 */
function startOn(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().parent().find('.numberspinner');
	var start = $(ns.eq(0)).val();
	var end = $(ns.eq(1)).val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + "/" + end);
	item.change();
}

/**
 * 不指定
 */
function unAppoint(dom) {
	var name = dom.name;
	var val = '?';
	if (name == 'year')
		val = '';
	var item = $("input[name=v_" + name + "]");
	item.val(val);
	item.change();
}

//本月的最后一天
function lastDay(dom){
	var item = $("input[name=v_" + dom.name + "]");
	item.val('L');
	item.change();
}
//每月最近的那个工作日
function workDay(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().parent().find('.numberspinner');
	var start = $(ns.eq(0)).val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + "W");
	item.change();
}

//第几周的星期几
function weekOfDay(dom){
	var name = dom.name;
	var ns = $(dom).parent().parent().parent().find('.numberspinner');
	var start = $(ns.eq(0)).val();
	var end = $(ns.eq(1)).val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + '#' + end);
	item.change();
}
//本月最后一个星期
function lastWeek(dom){
	var item = $("input[name=v_" + dom.name + "]");
	var ns = $(dom).parent().parent().parent().find('.numberspinner');
	var start = $(ns.eq(0)).val();
	item.val(start + 'L');
	item.change();
}


function cronChange(){
    var txt = $('#cron').val();
    if (txt)
    {
        var regs = txt.split(' ');
        $('input[name=v_second]').val(regs[0]);
        $('input[name=v_min]').val(regs[1]);
        $('input[name=v_hour]').val(regs[2]);
        $('input[name=v_day]').val(regs[3]);
        $('input[name=v_month]').val(regs[4]);
        $('input[name=v_week]').val(regs[5]);

        initObj(regs[0], 'second');
        initObj(regs[1], 'min');
        initObj(regs[2], 'hour');
        initDay(regs[3]);
        initMonth(regs[4]);
        initWeek(regs[5]);
        if (regs.length > 6)
        {
            $('input[name=v_year]').val(regs[6]);
            initYear(regs[6]);
        }
    }
}

function initObj(strVal, strid) {
    var ary = null;
    var objRadio = $("input[name='" + strid + "'");
    if (strVal == '*')
    {
        objRadio.eq(0).prop('checked', 'checked');
    }
    else if(strVal.split('-').length > 1)
    {
        ary = strVal.split('-');
        objRadio.eq(1).prop('checked', 'checked');
        $('#' + strid + 'Start_0').numberspinner('setValue', ary[0]);
        $('#' + strid + 'End_0').numberspinner('setValue', ary[1]);
    }
    else if(strVal.split('/').length > 1)
    {
        ary = strVal.split('/');
        objRadio.eq(2).prop('checked', 'checked');
        $('#' + strid + 'Start_1').numberspinner('setValue', ary[0]);
        $('#' + strid + 'End_1').numberspinner('setValue', ary[1]);
    }
    else
    {
        objRadio.eq(3).prop('checked', 'checked');
        if (strVal != '?')
        {
            ary = strVal.split(',');
            for (var i = 0; i < ary.length; i++)
            {
                $('.' + strid + 'List input[value="' + ary[i] + '"]').prop('checked', 'checked');
            }
        }
    }
}

function initDay(strVal){
    var ary = null;
    var objRadio = $('input[name="day"');
    if (strVal == '*')   objRadio.eq(0).prop('checked', 'checked');
    else if(strVal == '?') objRadio.eq(1).prop('checked', 'checked');
    else if(strVal.split('-').length > 1) 
    {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#dayStart_0').numberspinner('setValue', ary[0]);
        $('#dayEnd_0').numberspinner('setValue', ary[1]);
    } else if(strVal.split('/').length > 1)
    {
        ary = strVal.split('/');
        objRadio.eq(3).prop('checked', 'checked');
        $('#dayStart_1').numberspinner('setValue', ary[0]);
        $('#dayEnd_1').numberspinner('setValue', ary[1]);
    }
    else if(strVal.split('W').length > 1)
    {
        ary = strVal.split('W');
        objRadio.eq(4).prop('checked', 'checked');
        $('#dayStart_2').numberspinner('setValue', ary[0]);
    }
    else if
    (strVal == 'L')
    {
        objRadio.eq(5).prop('checked', 'checked');
    }
    else
    {
        objRadio.eq(6).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++)
        {
            $('.dayList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initMonth(strVal){
    var ary = null;
    var objRadio = $('input[name="month"');
    if (strVal == '*')
    {
        objRadio.eq(0).prop('checked', 'checked');
    }
    else if(strVal == '?')
    {
        objRadio.eq(1).prop('checked', 'checked');
    }
    else if(strVal.split('-').length > 1)
    {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#monthStart_0').numberspinner('setValue', ary[0]);
        $('#monthEnd_0').numberspinner('setValue', ary[1]);
    }
    else if(strVal.split('/').length > 1)
    {
        ary = strVal.split('/');
        objRadio.eq(3).prop('checked', 'checked');
        $('#monthStart_1').numberspinner('setValue', ary[0]);
        $('#monthEnd_1').numberspinner('setValue', ary[1]);
    }
    else
    {
        objRadio.eq(4).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++)
        {
            $('.monthList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initWeek(strVal) {
    var ary = null;
    var objRadio = $('input[name="week"');
    if (strVal == '*')
    {
        objRadio.eq(0).prop('checked', 'checked');
    }
    else if(strVal == '?')
    {
        objRadio.eq(1).prop('checked', 'checked');
    }
    else if(strVal.split('/').length > 1)
    {
        ary = strVal.split('/');
        objRadio.eq(2).prop('checked', 'checked');
        $('#weekStart_0').numberspinner('setValue', ary[0]);
        $('#weekEnd_0').numberspinner('setValue', ary[1]);
    }
    else if(strVal.split('-').length > 1)
    {
        ary = strVal.split('-');
        objRadio.eq(3).prop('checked', 'checked');
        $('#weekStart_1').numberspinner('setValue', ary[0]);
        $('#weekEnd_1').numberspinner('setValue', ary[1]);
    }
    else if(strVal.split('L').length > 1)
    {
        ary = strVal.split('L');
        objRadio.eq(4).prop('checked', 'checked');
        $('#weekStart_2').numberspinner('setValue', ary[0]);
    }
    else
    {
        objRadio.eq(5).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++)
        {
            $('.weekList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initYear(strVal) {
    var ary = null;
    var objRadio = $('input[name="year"');
    if(strVal == '*')
    {
        objRadio.eq(1).prop('checked', 'checked');
    }
    else if(strVal.split('-').length > 1)
    {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#yearStart_0').numberspinner('setValue', ary[0]);
        $('#yearEnd_0').numberspinner('setValue', ary[1]);
    }
}

</script>
</body>
</html>
