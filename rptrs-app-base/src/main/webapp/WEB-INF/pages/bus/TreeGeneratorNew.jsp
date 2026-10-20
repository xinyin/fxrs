<%--
  Created by IntelliJ IDEA.
  User: MrZhang
  Date: 2022/4/13
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联关系图谱</title>
    <script src="${res}/js/d3.min.js"></script>
    <script src="${res}/js/jquery-3.3.1.min.js"></script>
    <script src="${res}/js/loadingoverlay.min.js"></script>
    <script src="${res}/js/screenfull.min.js"></script>
    <script src="${res}/js/tree-chart.js"></script>
    <link rel='stylesheet' href='${res}/css/epc.css'/>

</head>
<style>
    .hideClass {
      display: none;
    }
</style>
<body style="background-color: white;">

<div class="container" id="treecontainer">
    <div class="row">
        <bf:form id="queryForm" validation="true">
            <bc:row cls="m-b">
                <bf:formGroup col="4">
                    <bc:label text="关联方名称" col="5" formControl="true"/>
                    <bf:text name="relName" col="7" placeholder="关联方名称" value="${relName}"/>
                </bf:formGroup>

                <bf:formGroup col="4" cls="hideClass">
                    <bc:label text="关联方PID" col="5" formControl="true"/>
                    <bf:text name="relId" col="7" placeholder="关联方PID" value="${relId}"/>
                </bf:formGroup>

                <bf:formGroup col="4" cls="hideClass">
                    <bc:label text="关联方类型" col="5" formControl="true"/>
                    <bf:text name="relType" col="7" placeholder="关联方类型" value="${relType}"/>
                </bf:formGroup>

                <bf:formGroup col="4" cls="hideClass">
                    <bc:label text="关联方证件号码" col="5" formControl="true"/>
                    <bf:text name="identifyNumber" col="7" placeholder="关联方证件号码" value="${identifyNumber}"/>
                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bc:col col="12" colOffset="5">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search" click="search()"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
<%--                    <bc:button id="btnExport" type="primary" text="导出"  click="exportEexel()"/>--%>
                </bc:col>
            </bc:row>
        </bf:form>
    </div>
</div>
<div class="menu">
    <div id="product_tree" style="width: 100%;height:800px;margin: 0 auto; padding-left: 0"></div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    $(document).ready(function () {
        init()
        //getData()
     });

    /**
     * 初始化图谱
     * @param relId
     * @param relType
     */
    function init() {
        let relId = $('input[name = "relId"]').val()
        let relType = $('input[name = "relType"]').val()
        $.ajax({
            type:"POST",
            url:"${base}/design/equityFigure/selectAllJson?relId=" + relId + '&relType=' + relType,
            data: {"": ""},
            dataType:"JSON",
            traditional:true,
            success:function(result){
                //alert("数据查询成功")
                var d3GenerationChart = new TreeChart("#product_tree");
                d3GenerationChart.init(result, options);
            }
        })

        var options = {
            //节点的点击事件，e为节点数据
            /*onClick: function (result) {
                console.log(result)
            },*/
            onDoubleClick: function (result) {
                console.log(result)
                //点击切换节点
                let relId = result.relId
                let relType = result.relType
                let relName = result.name
                console.log(relId)
                console.log(relType)
                if (relId == undefined || relType == undefined) {
                    return
                }
                $('input[ name = "relName"]').val(relName)
                $('input[ name = "relId"]').val(relId)
                $('input[ name = "relType"]').val(relType)
                $('input[ name = "identifyNumber"]').val('')
                search()
            }
        }
    }

    /**
     * 获取关联方
     */
    function selectRel() {
        //弹出窗口选择关联方
        $.messager.modal({
            winId: 'selectRel',
            width: 700,
            height: 420,
            title: '选择关联方',
            url: '${base}/bus/rel/AllList/'+'ALL_AND_PRO',
            args: {
                result: function (obj) {
                    console.log(obj)
                    $("input[name='relId']").val(obj['pid'])
                    $("input[name='relName']").val(obj['relName'])
                    $("input[name='relType']").val(obj['type'])
                    $("input[name='identifyNumber']").val(obj['identifyNumber'])
                }
            },
            onClose: function () {
                //查询
                let identifyNumber = $("input[name='identifyNumber']").val()
                if (identifyNumber == null || identifyNumber == '') {
                    return
                }
                $('#btnQuery').click()
            }
        })
    }

    /**查询*/
    function search() {
        //图库不初始化？此处借助页面跳转实现初始化
        let relName = $('input[ name = "relName"]').val()
        let relId = $('input[ name = "relId"]').val()
        let relType = $('input[ name = "relType"]').val()
        let identifyNumber = $('input[ name = "identifyNumber"]').val()
        window.location.href = '${base}/design/equityFigure/listNew?relName=' + relName + '&relId=' + relId + '&relType=' + relType + '&identifyNumber=' + identifyNumber
    }

    /**重置*/
    function frmReset() {
        window.location.href = '${base}/design/equityFigure/listNew'
    }

    //导出
    function exportEexel() {
        let relId = $('input[name = "relId"]').val()
        let relType = $('input[name = "relType"]').val()
        let data = {
            relId: relId,
            relType: relType
        }
        var url = encodeURI('${base}/sys/xlsie/DesignExportCallbackImpl/1?params=' + JSON.stringify(data));
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
    /**测试数据*/
    function getData() {
        return {
            "rootName": "湖南三湘银行",
            "directionData": {
                "left": {
                    "children": [
                        {
                            "name": "法人股东",
                            "children": [
                                {
                                    "name": "三一重工股份有限公司",
                                    "ratio": "30%"
                                },
                                {
                                    "name": "长沙澄海实业有限公司",
                                    "ratio": "10%"
                                }
                            ]
                        },
                        {
                            "name": "自然人股东",
                            "children": [
                                {
                                    "name": "自然人股东AAA",
                                    "ratio": "14%"
                                },
                                {
                                    "name": "自然人股东234",
                                    "ratio": "23%"
                                }
                            ]
                        },
                        {
                            "name": "董监高",
                            "children": [
                                {
                                    "name": "潘亚康",
                                    "ratio": "董事"
                                },
                                {
                                    "name": "段立强",
                                    "ratio": "监事"
                                },
                                {
                                    "name": "段立强",
                                    "ratio": "监事"
                                },
                                {
                                    "name": "许贝",
                                    "ratio": "高级管理人员"
                                }
                            ]
                        }
                    ]
                },
                "right": {
                    "children": [
                        {
                            "name": "施加重大影响的法人或其他组织",
                            "children": [
                                {
                                    "name": "湖南安富投资有限公司",
                                    "ratio": "40%"
                                },
                                {
                                    "name": "长沙京源科技有限公司",
                                    "ratio": "6%"
                                },
                                {
                                    "name": "湖南三银商业管理有限公司",
                                    "ratio": "10%"
                                }
                            ]
                        }
                    ]
                }
            }
        }
    };
</script>

</body>
</html>
