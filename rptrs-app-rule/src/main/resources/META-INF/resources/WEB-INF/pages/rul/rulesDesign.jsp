<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="../include/common.jsp"%>
	<br:css pack="form"/>
	<title>规则内容编辑</title>
	<style>
		.item{
			color: #000;
			display: block;
			padding: 5px;
		}
		.item:hover{
			background-color: lightskyblue;
			border-radius: 4px;
		}
		.obj {
			display: inline-block;
			background-color: lightskyblue;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.function{
			display: inline-block;
			background-color: white;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.SetParam{
			display: inline-block;
			background-color: lightskyblue;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.memOperate{
			display: inline-block;
			background-color: lightskyblue;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.setValue{
			display: inline-block;
			background-color: lightskyblue;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.addList {
			display: inline-block;
			background-color: darkcyan;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.logic {
			display: inline-block;
			background-color: cornsilk;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.actions{
			display: inline-block;
			background-color: cornsilk;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.chooseObjValue{
			display: inline-block;
			background-color: orange;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}


		.vals {
			display: inline-block;
			background-color: orange;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}

		.equalOperate{
			display: inline-block;
			background-color: lightpink;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}
		.operate {
			display: inline-block;
			background-color: lightpink;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}
		.remove{
			display: inline-block;
			margin-left: 10px;
			padding: 5px 10px 5px 10px;
			margin-top: 5px;
			text-align: center;
			font-weight: bold;
			color: black;
			border-radius: 4px;
		}
		.left{
			text-align: right;
		}
	</style>
</head>
<body class="white-bg">
<div class="row">
	<bc:ibox id="inOutObj" title="" subTitle="" col="12" collapsible="false">
		<div class="col-md-8">
			<h2>编辑规则——${rules.name}</h2>
		</div>
		<div class="col-md-4">
			<bc:buttonGroup cls="">
				<bc:button  text="保存" icon="save" type="primary">
					<be:event>
						var formData = new Array();
						var content = window.editor.getValue();
						var lhsHtml= $("#lhs").html();
						var rhsHtml= $("#rhs").html();

						formData.push({"name":"content","value":content});
						formData.push({"name":"id","value":'${rulesContent.id}'});
						formData.push({"name":"ruleId","value":'${ruleId}'});
						formData.push({"name":"htmlLhs","value":lhsHtml});
						formData.push({"name":"htmlRhs","value":rhsHtml});
						formData.push({"name":"lhsIndex","value":rowIndex});
						formData.push({"name":"rhsIndex","value":rhsRowIndex});

						$.mask.post('${base}/rul/rulesContent/save', formData, function(data){
						if(data.success)
						{
						$.messager.alert('提示', data.msg, 'info');
						}
						else
						{
						$.messager.alert('错误', data.msg, 'error');
						}
						});
					</be:event>
				</bc:button>
				<bc:button  text="编译" icon="resove" >
					<be:event>
						<%--window.location = '${base}/rul/rules/build';--%>
						var content = window.editor.getValue();
						$.mask.post('${base}/rul/rules/compile', {"ruleId":"${ruleId}","content":content}, function(data){
						if(data.length > 0)
						{
						$.messager.toastr("提示","编译失败，请查看消息提示！",'warning');
						$("#compileMessage").html("");
						var html = "";
						$.each(data,function(k,v){
						var codeLine = Number(v.codeLine);
						codeLine = codeLine > 17 ? codeLine - 17 : codeLine;
						var tr = "<tr>";
						tr += "<td>"+v.number+"</td><td>"+v.message+"</td><td>" + codeLine +"</td>";
						tr += "</tr>";
						html += tr;
						})
						$("#compileMessage").append(html);
						$('body').animate( {scrollTop: 400}, 800);
						}
						else
						{
						$.messager.toastr("提示","编译成功！",'success');
						$("#compileMessage").html("<tr><td colspan='3'>编译成功！</td></tr>");
						}
						});
					</be:event>
				</bc:button>
				<bc:button  text="测试" icon="clear" tooltip="测试规则" tooltipPos="bottom">
					<be:event>
						$.messager.modal({
						winId: 'winObjsAdd',
						width: 800,
						height: 400,
						title: '规则测试',
						url: "${base}/rul/rules/testView?ruleId=" + '${ruleId}',
						onClose: function(){
						<%--$("#inOutObjTable").bootstrapTable('refresh');--%>
						}
						});
					</be:event>
				</bc:button>
				<bc:button  text="返回" icon="arrow-circle-left">
					<be:event>
						window.location = '${base}/rul/rules/list';
					</be:event>
				</bc:button>
			</bc:buttonGroup>
		</div>
	</bc:ibox>
</div>
<div class="row">
	<div class="col-md-12">
		<bc:tabs id="tabContent">
			<bc:tab id="tab1" title="对象库" visible="false">
				<div id="wrapper" class="animated fadeInRight">
					<div class="row">
						<bc:ibox id="inOutObj" title="传入对象" subTitle="" col="12" collapsible="false">
							<bc:buttonGroup cls="right">
								<bc:button id="addOjb" text="添加">
									<be:event>
										$.messager.modal({
										winId: 'winObjsAdd',
										width: 800,
										height: 450,
										title: '选择对象库',
										url: "${base}/rul/objects/select?ruleId=" + '${ruleId}',
										onClose: function(){
											$("#inOutObjTable").bootstrapTable('refresh');
											getObjects();
										}
										});
									</be:event>
								</bc:button>
								<bc:button id="removeObj" text="删除">
									<be:event>
										var objData = $('#inOutObjTable').jTable('getSelections');
										if(!objData[0])
										{
										$.messager.toastr("提示","请选择对象！","info");
										return;
										}
										$.mask.post("${base}/rul/ruleObjectMap/remove/" + objData[0].id,function(data){
										if(data.success)
										{
										$.messager.toastr("提示","删除成功！","success");
										$("#inOutObjTable").bootstrapTable('refresh');
										getObjects();
										}
										else{
										$.messager.toastr("提示","删除失败！","error");
										}
										});
									</be:event>
								</bc:button>
							</bc:buttonGroup>
							<bc:table id="inOutObjTable" idField="code" url="${base}/rul/ruleObjectMap" queryUrl="findObjectsByRuleId?ruleId=${ruleId}" showRefresh="false" showColumns="false"  multiple="true">
								<%--<bc:toolbar id="add" text="增加" type="button"/>--%>
								<bc:column field="code" title="英文名称" width="100"/>
								<bc:column field="id" title="主键" visible="false"/>
								<bc:column field="name" title="中文名称" width="100"/>
								<bc:column field="dataType" title="数据类型" width="100" formatter="fixedDict:RuleDataType"/>
								<bc:column field="group" title="分组" width="100"/>
								<bc:column field="remark" title="说明" width="100"/>
							</bc:table>
						</bc:ibox>
						<bc:ibox id="baseColumnBox" title="函数" subTitle="" col="12" collapsible="false">
							<%--<bc:buttonGroup cls="right">
                                <bc:button id="addOjb" text="添加"></bc:button>
                                <bc:button id="addOjb" text="删除"></bc:button>
                            </bc:buttonGroup>--%>
							<bc:table id="dashLayoutTable" idField="code" url="${base}/rul/functions" showRefresh="false" showColumns="false">
								<bc:column field="code" title="编码" width="200"/>
								<bc:column field="name" title="名称" width="200"/>
								<bc:column field="remark" title="说明" width="500"/>
							</bc:table>
						</bc:ibox>
						<bc:ibox id="constantBox" title="常量" subTitle="" col="12" collapsible="false">
							<bc:table id="constantTable" idField="code" url="${base}/rul/function" showRefresh="false" showColumns="false">
								<bc:column field="code" title="英文名称" width="100"/>
								<bc:column field="name" title="中文名称" width="100"/>
							</bc:table>
						</bc:ibox>
					</div>
				</div>
			</bc:tab>
			<bc:tab id="tab2" title="编辑器" visible="true">
				<bc:ibox id="contentBox" title="" subTitle="" col="12" collapsible="false">
					<%-- 					<div id="contentId" data-toggle="jRuleEditor" data-rule-code="${rules.code}"></div> --%>
					<div id="content" style="overflow:auto;background-color: #eeeeee;height: 300px">
						<div style="margin: 10px;"><h2>如果：</h2></div>
						<div id="lhs">
								${rulesContent.htmlLhs}
						</div>
						<div id="lhsMore">
							<span class="obj" onclick="addConditions(this)">增加一个条件</span>
						</div>
						<div style="margin: 10px;"><h2>那么：</h2></div>
						<div id="rhs">
								${rulesContent.htmlRhs}
						</div>
						<div id="rhsMore">
							<span class="obj" onclick="addActions(this)">插入新动作</span>
						</div>
					</div>
				</bc:ibox>
				<bc:ibox id="messageBox" title="消息提示" subTitle="" col="12" collapsible="false">
					<div id="messageTable">
						<table class="table table-bordered">
							<thead>
							<tr>
								<th>编号</th>
								<th>错误信息</th>
								<th>行数</th>
							</tr>
							</thead>
							<tbody id="compileMessage">
							<tr>
								<td colspan="3">没有消息。</td>
							</tr>
							</tbody>
						</table>
						<div class="left">
							<bc:button type="default" id="clearMessage" text="清除">
								<be:event>
									$("#compileMessage").html("");
								</be:event>
							</bc:button>
						</div>
					</div>
				</bc:ibox>
			</bc:tab>
			<bc:tab id="tab3" title="源码">
				<div style="overflow:auto;">
					<bc:col col="12">
						<bf:text id="ruleContent" name="ruleContent" col="12" rows="100"  value="${rulesContent.content}" multiple="true"></bf:text>
					</bc:col>
				</div>
			</bc:tab>
		</bc:tabs>
	</div>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<link href="${res}/css/plugins/codemirror/codemirror.css" rel="stylesheet" />
<script src="${res}/js/plugins/codemirror/codemirror.js"></script>
<script src="${res}/js/plugins/codemirror/mode/clike/clike.js"></script>
<script type="text/javascript">
    var whenIndex = 2;

    var objs = new Array(); //对象库
    var functions = new Array();
    var operates = {"==":"等于",">":"大于",">=":"大于等于","<":"小于","<=":"小于等于","!=":"不等于","memberOf":"属于","not memberOf":"不属于"}; //操作符
    var strOperates = {"==":"等于","!=":"不等于"}; //对象数据类型是字符串的操作符
    var logics = {"":"空","and":"并且","or":"或者","and (":"并且(","or (":"或者(",") and":") 并且",") or":") 或者","(":"(",")":")"};//逻辑操作
    var memOperate = {"insert":"添加对象","retract":"删除对象","update":"更新对象"};//内存操作
    var actions = {"memOperate":"内存操作","setValue":"对象赋值","addList":"加入返回列表","functions":"方法调用"};

    //结尾的逻辑操作符
    var endLogics = {";":"结束符",")":")"};

    //条件对象索引
    var objIndex = 1; //对象索引
    var operateIndex = 1;//操作符索引
    var valueIndex = 1;//值索引
    var rowIndex = ${rhsIndex == null ? 1 : rhsIndex};//行数索引
    var logicIndex = 1;//逻辑操作符索引

    //执行对象索引
    var rhsRowIndex = '${rhsIndex == null ? 1 : rhsIndex}';
    var actionIndex = 1;
    var functionIndex = 1;

    /**
     * 查询对象库
     */
	function getObjects()
    {
        $.ajax({
            type: "get",
            url: "${base}/rul/ruleObjectMap/findObjectsByRuleId?ruleId=${ruleId}",
            cache:false,
            async:false,
            dataType: "json",
            success: function(data){
                objs = data.rows;
            }
        });
    }


    /**
     * 查询函数库
     */
    function getFunctions()
    {
        $.ajax({
            type: "get",
            url: "${base}/rul/functions/query",
            cache:false,
            async:false,
            dataType: "json",
            success: function(data){
                functions = data.rows;
            }
        });
    }


	$(document).ready(function(){
        var defaultCode = 'rule "${rules.code}"\nwhen\n\t$map:Map();\n\n\n\nthen\n\n\n\n\n\n\nend';
        if(!$("#ruleContent").text())
        {
            $("#ruleContent").text(defaultCode);
        }

        window.editor = CodeMirror.fromTextArea(document.getElementById("ruleContent"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java"
        });

        //加载对象库
        getObjects();

        //加载函数库
        getFunctions();
    });




    function addActions()
    {
        var html = "<div id=\"rhsRow_"+rhsRowIndex+"\" rowIndex=\""+actionIndex+"\">";
        html += "<a id=\"action_"+actionIndex+"\"  class=\"actions\" code=\"\" onclick=\"showActions(this)\">请选择</a>";
        html += "</div>";
        $("#rhs").append(html);

        actionIndex++;
        rhsRowIndex++;
    }



    /**
     * 显示逻辑操作符
     */
    function showLogic($el)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        for(k in logics)
        {
            html += '<a href="javaScript: void(0);" code="'+k+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+logics[k]+'</a>';
        }
        html += '</div>';

        showPoper($el,html);
    }


    /**
     * 显示对象
     * @param $el
     */
    function showObjects($el)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        $.each(objs,function(k,v){
            html += '<a href="javaScript: void(0);" code="'+v.code+'" dataType="'+v.dataType+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+v.name+'</a>';
        })
        html += '</div>';

        showPoper($el,html);
    }


    /**
     * 显示对象
     * @param $el
     */
    function showFunctions($el)
    {
        var popId = $el.id;
        var html = '<div style="width:150px;">';
        $.each(functions,function(k,v){
            html += '<a href="javaScript: void(0);" code="'+v.code+'" data-el-id="'+popId+'" class="item" onClick="setParam(this);" title="'+v.remark+'">'+v.name+'</a>';
        })
        html += '</div>';

        showPoper($el,html);
    }

    var paramToral;
    var params;

    function setParam(el){//选择方法的时候，根据方法的编码去后台查方法的参数
        debugger;
        var id = $(el).attr("code");
        var popid = $(el).attr("data-el-id");
        var rhs = $("#"+popid).parent();
        var chids = $(rhs).children();
        var paramNode = chids[3];
        if(paramNode){//如果有后续节点
            var ids = $(paramNode).attr("id");
            if(ids.indexOf(id)>-1){//点击的是同一个函数
                var cod = $(el).attr("code");
                $("a[code="+cod+"]").popover("hide");//不执行
                return;
            }else{//点击的不是同一个函数，就说明是修改(修改参数的值)
                var text = $(el).text();
                $("#"+popid).text(text);
                var cod = $(el).attr("code");
                $("a[code="+cod+"]").popover("hide");
                $("#"+popid).nextAll().remove();
                $.mask.post('${base}/rul/functions/selectFuns',{code : id}, function(data){
                    if(data.success)
                    {
                        paramToral = data.paramTotal;
                        var arr =  data.params;
                        var html = "";
                        for (var i=0;i<arr.length;i++){
                            var paramObj = arr[i];
                            var index = i+1;
                            var ind = index + id;
                            html += '<a id=\'setParam'+ind+'\' index="'+index+'" type="'+paramObj.paramType+'" code  class="SetParam" onClick="showMethodValue(this);">参数+"'+index+'"</a>';
                        }
                        rhs.append(html);

                        var row = Number($("#"+popid).parent().attr("rowIndex"));//获取修改的数据的行数
                        row = window.editor.lineCount() - 8 + row;
                        window.editor.replaceRange("\n",{line:row-1},{line:row});//删除之前编辑框存在的源码

                        insertValue(el);
                    }
                    else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                });
            }
        }else{//没有后续节点，就表示是新增(参数设置值)
            $.mask.post('${base}/rul/functions/selectFuns',{code : id}, function(data){
                if(data.success)
                {
                    paramToral = data.paramTotal;
                    var arr =  data.params;
                    var html = "";
                    for (var i=0;i<arr.length;i++){
                        var paramObj = arr[i];
                        var index = i+1;
                        var ind = index + id;
                        html += '<a id=\'setParam'+ind+'\' index="'+index+'" type="'+paramObj.paramType+'" code  class="SetParam" onClick="showMethodValue(this);">参数'+index+'</a>';
                    }
                    rhs.append(html);
                    insertValue(el);
                }
                else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }

    }

    function showMethodValue(el){
        var popId = el.id;
        var type = $(el).attr("type");
        type = type.toUpperCase();
        var newObj = new Array();
        for(var i=0;i<objs.length;i++){
            var objType = objs[i].dataType;
            if(objType === type){
                newObj.push(objs[i]);
            }
        }
        var html = '<div style="width:100px;">';
        html += "<input id='input_"+valueIndex+"'  placeholder=\"请输入值\" data-el-id='"+popId+"' class='form-control' style='width: 80px;height: 25px;margin-right:5px;display: inline'><i class='fa fa-check' onclick='insertValue(input_"+valueIndex+",1)'/>"
        $.each(newObj,function(k,v){
            html += '<a href="javaScript: void(0);" code="'+v.code+'" dataType ="'+v.dataType+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this,2);">'+v.name+'</a>';
        })
        html += '</div>';
        showPoper(el,html);
    }



    /**
     * 显示操作符
     *
     */
    function showOperate($el,dataType)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        for(k in operates)
        {
            html += '<a href="javaScript: void(0);" code="'+k+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+operates[k]+'</a>';
        }
        html += '</div>';
        showPoper($el,html);
    }

    //显示结尾的逻辑操作符
    function showEndLogic($el){
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        for(k in endLogics)
        {
            html += '<a href="javaScript: void(0);" code="'+k+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+endLogics[k]+'</a>';
        }
        html += '</div>';

        showPoper($el,html);
    }


    function equal($el){//对象赋值选择等号
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        html += '<a href="javaScript: void(0);" code="=" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">等于</a>';
        html += '</div>';
        showPoper($el,html);
    }

    /**
     * 插入值
     * @param $ael
     */

    //绑定值到标签的code属性上
    function bindValToDom(el){
        debugger
        var dataType = $(el).attr("dataType");//对象库中对象的数据类型

        var cod = $(el).attr("code");

        var id = $(el).attr("data-el-id");

        var popid = "#" + $(el).attr("data-el-id");

        var type = $("#"+id).attr("class");//选择的是对象库

        if(type === "obj"){//选择的是对象库
            if(dataType){//对象库中对象的数据类型
                if(dataType && dataType === "DOUBLE"){
                    var code = " eval(toDouble($map.get(\""+cod+"\"))";;
                    $("#"+id).attr("code",code);
                    $("#"+id).attr("dataType",dataType);
                }else if(dataType && dataType === "STRING"){
                    var code = " eval(toStr($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                    $("#"+id).attr("dataType",dataType);
                }else if(dataType && dataType === "INTEGER"){
                    var code = " eval(toInt($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                    $("#"+id).attr("dataType",dataType);
                }
            }
        }else if(type === "vals"){//如果选择的是值上面的对象库
            if(dataType){//对象库中对象的数据类型
                if(dataType && dataType === "DOUBLE"){
                    var code = "toDouble($map.get(\""+cod+"\"))";;
                    $("#"+id).attr("code",code);
                }else if(dataType && dataType === "STRING"){
                    var code = "toStr($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                }else if(dataType && dataType === "INTEGER"){
                    var code = "toInt($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                }
            }
        }else if(type === "memOperate"){//如果是内存操作
            var cod = $(el).attr("code");
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code",cod);

        }else if(type === "setValue"){//对象赋值，选择对象
            var cod = $(el).attr("code");
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code","$map.put('"+cod+"',");
        }else if(type == "addList"){//对象列表
            var cod = $(el).attr("code");
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code","resultList.add(new ScoreResult(\"${rules.code}\",toStr($map.get('"+cod+"')),\"0\"));");
        }else if(type === "equalOperate"){//对象赋值，操作符选择等号
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code","");
        }else if(type === "chooseObjValue"){//对象赋值，选择的是对象，不是输入框输入
            var cod = $(el).attr("code");
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code","$map.get(\""+cod+"\"));");
        }else if(type === "SetParam"){//方法调用，设置参数
            if(dataType){//对象库中对象的数据类型
                if(dataType && dataType === "DOUBLE"){
                    var code = "toDouble($map.get(\""+cod+"\"))";;
                    $("#"+id).attr("code",code);
                }else if(dataType && dataType === "STRING"){
                    var code = "toStr($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                }else if(dataType && dataType === "INTEGER"){
                    var code = "toInt($map.get(\""+cod+"\"))";
                    $("#"+id).attr("code",code);
                }
            }
        }else{
            var cod = $(el).attr("code");
            var id = $(el).attr("data-el-id");
            $("#"+id).attr("code",cod);
        }

    }


    function insertValue(el,num)
    {
        debugger
        var id = $(el).attr("data-el-id");
        var isExit = $("#"+id).attr("code");//获取标签上的code属性
        if(isExit && id.indexOf("action") == -1 && id.indexOf("function") == -1){//标签上的code属性存在，就表示是修改，否则是新增
            //插入值
            var popid = "#" + $(el).attr("data-el-id");
            var value = "";
            if($(el).is('input')) //输入框
            {
                value = $(el).val();
            }else{ //其他对象
                value = $(el).text();
            }

            $(popid).text(value);
            $(popid).popover("hide");

            if(num ==1){//输入的是值
                var val = $(el).val();
                var id = $(el).attr("data-el-id");
                if(id.indexOf("chooseObjValue") > -1){//如果是对象赋值，input框输入的值
                    $("#"+id).attr("code",val+");");
                }else{
                    if(isNaN(value)){
                        $("#"+id).attr("code","\""+val+"\"");//字符串类型
                    }else{
                        $("#"+id).attr("code",val);//数字类型
                    }
                }
            }else{//修改的是其他的
                //绑定值到标签的code属性上
                bindValToDom(el);
            }

            var parentId =  $(popid).parent().attr("id");
            var content;//编辑器内容
            var row;//编辑器的行数

            if(parentId.indexOf("rhs") > -1)//"那么"下面的操作
            {
                if(id.indexOf("memOperate") > -1){//内存操作
                    var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                    var obj = $("#rhsRow_"+row).children();
                    var memOrapt = $(obj[1]).attr("code");
                    row = window.editor.lineCount() - 8 + row;
                    content = "\r "+memOrapt + "($map);";
                    window.editor.replaceRange(content,{line:row-1},{line:row});
                }else if(id.indexOf("setValue") > -1 || id.indexOf("chooseObjValue") > -1){//对象赋值
                    var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                    var obj = $("#rhsRow_"+row).children();
                    var obj1 = $(obj[1]).attr("code");
                    var val = $(obj[3]).attr("code");
                    row = window.editor.lineCount() - 8 + row;
                    content = "\r " + obj1 + val;
                    window.editor.replaceRange(content,{line:row-1},{line:row});
                }else if(id.indexOf("addList") > -1)
				{
				    var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                    var obj = $("#rhsRow_"+row).children();
                    var obj1 = $(obj[1]).attr("code");

                    row = window.editor.lineCount() - 8 + row;
                    content = "\r " + obj1;
                    window.editor.replaceRange(content,{line:row-1},{line:row});
				}
                else{//方法调用
                    var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                    var obj = $("#rhsRow_"+row).children();
                    row = window.editor.lineCount() - 8 + row;
                    var html = "\r ";
                    for(var i=0;i<obj.length;i++){
                        if(i==0){
                            continue;
                        }else if(i==1){
                            var el = obj[i];
                            var val = $(el).attr("code");
                            html += val+"(";
                        }else{
                            var el = obj[i];
                            var val = $(el).attr("code");
                            html += val+",";
                        }
                    }
                    html += ");"
                    var len = html.length;
                    var n = html.lastIndexOf(",");
                    var j = html.substring(n,len);
                    html = html.replace(j,");");

                    window.editor.replaceRange(html,{line:row-1},{line:row});
                }

            }else {//"如果"下面的操作

                var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                var obj = $("#row_"+row).children();
                var logicStr1 = $(obj[0]).attr("code");
                var obj1 = $(obj[1]).attr("code");
                var dataType = $(obj[1]).attr("dataType");
                var operate = $(obj[2]).attr("code");
                var val = $(obj[3]).attr("code");
                if(dataType === "STRING"){
                    val = "\""+val+"\"";
                }
                var logicStr2 = $(obj[4]).attr("code");
                row = whenIndex + row;
                content = "\r\t"+logicStr1+obj1+operate+val+")"+logicStr2;
                window.editor.replaceRange(content, {line:row-1},{line:row});
            }

        }else{//新增

            var popid = "#" + $(el).attr("data-el-id");
            var index = $(popid).attr("index");
            var value = "";
            if($(el).is('input')) //输入框
            {
                value = $(el).val();
            }else{ //其他对象
                value = $(el).text();
            }

            $(popid).text(value);
            $(popid).popover("hide");

            if(num ==1){//输入的是值
                var val = $(el).val();
                var id = $(el).attr("data-el-id");
                if(id.indexOf("chooseObjValue") > -1){//如果是对象赋值，input框输入的值
                    $("#"+id).attr("code",val+");");
                }else{
                    var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                    var obj = $("#row_"+row).children();
                    var dataType = $(obj[1]).attr("dataType");
                    if(dataType === "STRING"){
                        val = '"' + val + '"';
                        value = '"' + value + '"';
                    }
                    $("#"+id).attr("code",val);
                }
            }else{//选择的是对象
                //绑定值到标签的code属性上
                bindValToDom(el);
            }

            //创建后续节点
            var id = $(popid).attr("id");
            var html = "";
            var next = $(popid).next().length;
            if((value == '内存操作' || value == '对象赋值' || value=='方法调用' || value=='加入返回列表') && next > 0)
            {
                $(popid).nextAll().remove();
                var row = Number($(popid).parent().attr("rowIndex"));//获取修改的数据的行数
                row = window.editor.lineCount() - 8 + row;
                window.editor.replaceRange("\n", {line:row-1},{line:row});
                next = 0;
            }


            if(id.indexOf("logic") > -1 && next == 0 )
            {
                getObjects();
                html = "<a id=\"obj_"+objIndex+"\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\">请选择</a>";
                objIndex++;
            }
            else if(id.indexOf("obj") > -1) //如果是对象节点，后续跟上操作符
            {
                var dataType = $(el).attr("dataType");
                if(next == 0){
                    html = "<a id=\"operate"+operateIndex+"\" class=\"operate\" code=\"\" onclick=\"showOperate(this,'"+dataType+"')\">请选择</a>";
                    operateIndex ++;
                }else{
                    $(".operate").remove();//如果是修改选择的对象，先移除已经存在的操作符按钮，再进行新增
                    html = "<a id=\"operate"+operateIndex+"\" class=\"operate\" code=\"\" onclick=\"showOperate(this,'"+dataType+"')\">请选择</a>";
                    operateIndex ++;
                }
            }else if(id.indexOf("setValue") > -1){//如果是对象赋值，选择对象的操作，后面跟上算数操作符
                if(next == 0){
                    html = "<a id=\"equalOperate"+operateIndex+"\" class=\"equalOperate\" code=\"\" onclick=\"equal(this)\">请选择</a>";
                    operateIndex ++;
                }
            }else if(id.indexOf("equalOperate") > -1 && next == 0){//对象赋值等号后面的请选择
                html = "<a id=\"chooseObjValue"+valueIndex+"\" class=\"chooseObjValue\" code=\"\" onclick=\"showValue(this)\">请选择</a>";
                valueIndex ++;
            }
            else if(id.indexOf("operate") > -1 && next == 0)//如果是算数运算符节点，后续跟上值
            {
                var code = $(el).attr("code");
                html = "<a id=\"value"+valueIndex+"\" class=\"vals\" code=\"\" onclick=\"showValue(this,'"+code+"')\">请选择</a>";
                valueIndex ++;
            }
            else if(id.indexOf("value") > -1 && next == 0)//如果是录入的是值
            {
                var parentId =  $(popid).parent().attr("id");
                var divId = "";
                if(parentId.indexOf("rhs") > -1) //是RHS部分
                {
                    divId = "rhs";
                }

                html = "<a id=\"logic_"+logicIndex+"\"  class=\"logic\" code=\"\" onclick=\"showEndLogic(this)\">请选择</a>";
                logicIndex++;
                html += "<a class=\"remove\" code=\"\" onclick=\"deleteRow('"+divId+"',this)\"><i class='fa fa-times' title='删除这个条件'></a>";
            }
            else if(id.indexOf("action") > -1 && next == 0)//录入动作
            {
                //先删除后续的

                var actionCode = $(el).attr("code");
                if(actionCode == "memOperate") //内存操作
                {
                    html = '<a id="memOperate'+rhsRowIndex+'" class="memOperate" code=\"\" onClick="showMemOperate(this);">请选择</a>'
                }else if(actionCode == "setValue"){
                    html = "<a id=\"setValue_"+objIndex+"\" class=\"setValue\" code=\"\" onclick=\"showObjects(this)\">请选择</a>";
                    objIndex++;
                }else if(actionCode == "functions"){
                    html = "<a id=\"function_"+functionIndex+"\" class=\"function\" code=\"\" onclick=\"showFunctions(this)\">请选择</a>";
                    functionIndex++;
                }else if(actionCode == "addList"){
                    html = "<a id=\"addList_" + rowIndex+"\" class=\"addList\" code=\"\" onclick=\"showObjects(this)\">请选择</a>";
				}
            }
            $(popid).parent().append(html);

            var code =  $(el).attr("code");
            if( id.indexOf('addList') > -1)
			{
			    code = $("#" + id).attr("code");
			}

            var dataType =  $(el).attr("dataType");

            if(id.indexOf("action") > -1 && next == 0){
                return;
            }else{
                insertCode(popid,code,dataType,id,value,num,index);
            }

        }


    }

    function insertCode(popid,code,dataType,id,value,num,index)
    {
        var parentId =  $(popid).parent().attr("id");
        var rowIndex =  Number($(popid).parent().attr("rowIndex"));
        var r = window.editor.getLine(rowIndex);
        if(parentId.indexOf("rhs") > -1)
        {
            rowIndex = window.editor.lineCount() - 8 + rowIndex;
        }else {
            rowIndex = whenIndex + rowIndex;
        }

        var endStr = ")";//在选择对象库的时候，eval(toDouble($map.get(\""+code+"\"))后面要加操作符，少写了一个扩号，在结尾要加上

        if(id.indexOf("obj") > -1){
            //如果是对象操作
            if(dataType === "DOUBLE"){
                r = " eval(toDouble($map.get(\""+code+"\"))";
            }else if(dataType === "STRING"){
                r = " eval(toStr($map.get(\""+code+"\"))";
            }else if(dataType === "INTEGER"){
                r = " eval(toInt($map.get(\""+code+"\"))";
            }
        }else if(id.indexOf("value") > -1){//如果录入的是值
            if(num == 1){//为1表示是输入值
                r = " "+value+endStr;//在结尾加括号
            }else{
                //如果是对象操作
                if(dataType === "DOUBLE"){
                    r = " toDouble($map.get(\""+code+"\")))";
                }else if(dataType === "STRING"){
                    r = " toStr($map.get(\""+code+"\")))";
                }else if(dataType === "INTEGER"){
                    r = " toInt($map.get(\""+code+"\")))";
                }
            }
        }else if(id.indexOf("operate") > -1){//如果是算数运算符
            r = " " + code;
        }else if(id.indexOf("memOperate") > -1){//如果是内存操作
            r = " "+code + "($map);";
        }else if(id.indexOf("setValue") > -1){//如果是对象赋值
            r = '  $map.put("'+code+'",';
        }else if(id.indexOf("equalOperate") > -1){//对象赋值，等号操作
            r = "";
        }else if(id.indexOf("chooseObjValue") > -1){//对象赋值，input框输入的值
            if(num == 1){//为1表示是输入值
                r = value + ");";
            }else{
                r = "$map.get(\""+code+"\"));";
            }
        }else if(id.indexOf("setParam") > -1){//方法设置参数
            if(num == 1){
                var paramIndex = Number(index);
                if(paramIndex == paramToral){//如果填写的参数值得索引和参数的个数相等，就表示是最后一个参数，进行特殊处理
                    if(isNaN(value)){
                        r = "\""+value+"\"" + ");";//字符串类型
                    }else{
                        r = value + ");";//数字类型
                    }
                }else{
                    if(isNaN(value)){
                        r = "\""+value+"\"" + ",";//字符串类型
                    }else{
                        r = value+",";//数字类型
                    }
                }
            }else{
                //如果是对象操作
                var paramIndex = Number(index);
                if(paramIndex == paramToral){//如果填写的参数值得索引和参数的个数相等，就表示是最后一个参数，进行特殊处理
                    if(dataType === "DOUBLE"){
                        r = "toDouble($map.get(\""+code+"\")));";
                    }else if(dataType === "STRING"){
                        r = "toStr($map.get(\""+code+"\")));";
                    }else if(dataType === "INTEGER"){
                        r = "toInt($map.get(\""+code+"\")));";
                    }
                }else{
                    if(dataType === "DOUBLE"){
                        r = "toDouble($map.get(\""+code+"\")),";
                    }else if(dataType === "STRING"){
                        r = "toStr($map.get(\""+code+"\")),";
                    }else if(dataType === "INTEGER"){
                        r = "toInt($map.get(\""+code+"\")),";
                    }
                }

            }

        }else if(id.indexOf("function") > -1){
            if(paramToral == 0){
                r = "\t"+code +"();";
            }else{
                r = "\t"+code+"(";
            }
        }else{
            r = "\t" + code;
        }
        window.editor.replaceRange(r, {line:rowIndex});

        var row =  Number($(popid).parent().attr("rowIndex"));
        if(row ==3 || row ==8 || row ==13 || row ==18 || row ==23 || row ==28 || row ==33 || row==38 || row==43 || row==48){
            window.editor.replaceRange("\r\n", {line:rowIndex});
        }
    }

    /**
     * 增加条件
     */
    function addConditions() {
        var html = "<div id=\"row_"+rowIndex+"\" rowIndex=\""+rowIndex+"\">" ;
        html += "<a id=\"logic_"+logicIndex+"\"  class=\"logic\" code=\"\" onclick=\"showLogic(this)\">请选择</a>";
        html += "</div>";
        $("#lhs").append(html);
        rowIndex++;
        logicIndex++;
    }

    /**
     * 显示动作
     * @param $el
     */
    function showActions($el)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';

        for(k in actions)
        {
            html += '<a href="javaScript: void(0);" code="'+k+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+actions[k]+'</a>';
        }
        html += '</div></div>';

        showPoper($el,html);
    }

    function showMemOperate($el)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';

        for(k in memOperate)
        {
            html += '<a href="javaScript: void(0);" code="'+k+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this);">'+memOperate[k]+'</a>';
        }
        html += '</div></div>';

        showPoper($el,html);
    }

    function showValue($el,code)
    {
        var popId = $el.id;
        var html = '<div style="width:100px;">';
        if(code === "memberOf" || code ==="not memberOf"){
            //如果选择了属于或者不属于，从对象库数组中获取类型不是数字类型的对象
            var arr = new Array();//排除数据类型是数字的对象
            $.each(objs,function(k,v){
                if(v.dataType == "STRING"){
                    arr.push(v);
                }
            });

            $.each(arr,function(k,v){
                html += '<a href="javaScript: void(0);" code="'+v.code+'" dataType ="'+v.dataType+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this,2);">'+v.name+'</a>';
            })

        }else{
            html += "<input id='input_"+valueIndex+"'  placeholder=\"请输入值\" data-el-id='"+popId+"' class='form-control' style='width: 80px;height: 25px;margin-right:5px;display: inline'><i class='fa fa-check' onclick='insertValue(input_"+valueIndex+",1)'/>"
            $.each(objs,function(k,v){
                html += '<a href="javaScript: void(0);" code="'+v.code+'" dataType ="'+v.dataType+'" data-el-id="'+popId+'" class="item" onClick="insertValue(this,2);">'+v.name+'</a>';
            })
        }

        html += '</div>';

        showPoper($el,html);
    }

    function deleteRow(div,el){
        if(div == 'rhs')
        {
            rhsRowIndex--;
            $("#rhsRow_" + rhsRowIndex).remove();
        }else{
            var delRowIndex = Number($(el).parent().attr("rowindex"));
//         rowIndex--;
            $("#row_" + delRowIndex).remove();
            delRowIndex = delRowIndex + whenIndex;
            window.editor.replaceRange("\n", {line:delRowIndex-1},{line:delRowIndex});
        }

    }

    function showPoper(el,html)
    {
        $(el).popover({
            trigger : 'manual',
            placement : 'right',
            html : 'true',
            content : html,
            animation : false
        }).on("mouseleave", function(){
            var _this = this;
            setTimeout(function() {
                if (!$(".popover:hover").length) {
                    $(_this).popover("hide")
                }
            }, 100);
        }).popover("show");
    }

</script>
</body>
</html>
