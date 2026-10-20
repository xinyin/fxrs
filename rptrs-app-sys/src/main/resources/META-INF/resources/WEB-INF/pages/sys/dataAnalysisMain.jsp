<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>数据分析器</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <div class="col-xs-12 ibox-content">
            <div class="panel-body">
                <bc:col col="2">
                    <div class="ibox-title">
                        <h5>元数据</h5>
                    </div>
                    <div class="row" style="height: 550px; overflow: scroll;">
                        <bc:tree id="metaRegionTree" url="${base}/sys/dataAnalysis" queryUrl="queryTableTree"
                                 edit="true" popupMenuEnable="true" popupMenuDefault="customRemove" autoParam="id=id">
                            <be:event type="onClick">onClick</be:event>
                            <be:event type="onRemove">onRemove</be:event>
                            <bc:popupMenu text="删除" id="customRemove" target="node" handler="customRemove"/>
                        </bc:tree>
                    </div>
                </bc:col>
                <bc:col col="10">
                    <bc:tabs id="tabContent">
                    </bc:tabs>
                </bc:col>
            </div>
        </div>
    </div>
</div>
<div style="display: none;">
    <iframe name="exportIframe" src=""></iframe>
    <form id="exportForm" action="${base}/sys/dataAnalysis/queryExport" target="exportIframe" method="post">
        <input type="hidden" id="tableId" name="tableId">
        <input type="hidden" id="script" name="script">
    </form>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var metaQuery;
    /*记录对应表一打开几个查询*/
    var tableNumber = [];

    function onClick(toggle, e, node) {
        /* 点击数据的类型 */
        var type = node.type;
        /*判断是否是子节点，如果是则进行查询*/
        if (type == 'T' || type == 'V') {
            //请求的URL
            var number = setTableNumber(node.id);
            var path = '${base}/sys/dataAnalysis/dataQuery?id=' + node.id + '&type=T';
            var title = node.name.substr(0, 14) + '-查询' + number;
            var tableId = node.id + '_' + number;
            openTab(title, path, tableId);
        } else if (type == 'Q' || type == 'P') {
            var path = '${base}/sys/dataAnalysis/dataQuery?id=' + node.id + '&type=Q';
            /*获取该节点的父节点*/
            var pNode = node.getParentNode();
            var title = pNode.name.substr(0, 14) + '(' + (node.name) + ')';
            openTab(title, path, node.id);
        }
    }

    /*记录table表对应打开的查询*/
    function setTableNumber(tableId) {
        var exists = false;
        var number = 1;
        $.each(tableNumber, function (index, tn) {
            /**/
            if (tn.tableId == tableId) {
                var obj = {
                    tableId: tn.tableId,
                    number: tn.number + 1
                };
                tableNumber.splice(index, 1, obj);
                exists = true;
                number = tn.number + 1;
            }
        });
        if (!exists) {
            var obj = {
                tableId: tableId,
                number: 1
            };
            tableNumber.push(obj);
        }

        return number;
    }


    //在右边center区域打开菜单，新增tab
    function openTab(title, path, tabId) {
        var tabCount = $('#tabContent').jTab('getTabSize');
        if (tabCount > 5) {
            $.messager.alert('系统提示', '您打开的页签过多,请关闭一些页签。', 'info');
            return;
        } else {
            var tab = {
                id: tabId,
                title: title,
                href: path,
                colse: true
            };
            $('#tabContent').jTab('addTab', tab);
        }
    }


    function customRemove(event, treeId, node) {
        if (!node) return;
        var type = node.type;
        if (type != 'Q' && type != 'P') {
            $.messager.toastr('系统提示', '只能删除查询，请重新选择。', 'info');
            return;
        } else {
            $.mask.post('${base}/sys/metaQuery/verify', {'queryId': node.id}, function (data) {
                if (data.success) {
                    $.messager.confirm('系统提示', '是否确认要删除该查询？', function (result) {
                        if (result) {
                            $.mask.post('${base}/sys/metaQuery/remove/' + node.id, {}, function (data) {
                                $.messager.alert('系统提示', data.msg, 'info', function () {
                                    if (data.success) {
                                        /*验证是否打开了tabs，存在就关闭*/
                                        $('#tabContent').jTab('colseTab', node.id);
                                        var treeObj = $.fn.zTree.getZTreeObj("_jTree_metaRegionTree");
                                        treeObj.removeNode(node);
                                    }
                                });
                            });
                        }
                    });
                } else {
                    $.messager.alert('系统提示', '只能删除自己保存的查询。', 'info');
                }
            });
        }
    }


    /*更新tabs title 更新tree*/
    function updateTitle(title, queryId, tableId, saveType, queryType) {
        /*保存更新tabs，另存在新打开一个tabs*/
        if (saveType == 'SAVE') {

            var $li = $('#tabContent').jTab('getTabTitle');
            var $t = $($li).children('a');
            tabId = $($t).attr('name');
            console.info(tabId);
            $('#tabContent').jTab('colseTab', tabId);
        }

        var treeObj = $.fn.zTree.getZTreeObj("_jTree_metaRegionTree");
        var node = treeObj.getNodeByParam('id', tableId);
        treeObj.expandNode(node, true, false);//指定选中ID节点展开
        var newNode = {
            id: queryId,
            name: title,
            type: queryType
        };
        treeObj.addNodes(node, newNode);
        /* 选中当前增加的节点 */
        var children = treeObj.getNodeByParam('id', queryId);
        treeObj.cancelSelectedNode();
        treeObj.selectNode(children, true);//指定选中ID的节点
        //treeObj.expandNode(children, true, false);//指定选中ID节点展开
        var path = '${base}/sys/dataAnalysis/dataQuery?id=' + queryId + '&type=Q';
        var showTitle = node.name + '(' + title + ')'
        openTab(showTitle, path, queryId);
    }


    /*更新tree*/
    function updateTableView(regionId, tableId, tableCode) {
        /*保存组装子节点的参数*/
        /*判断节点是否展开*/
        var treeObj = $.fn.zTree.getZTreeObj("_jTree_metaRegionTree");
        var node = treeObj.getNodeByParam('id', regionId);
        if (node) {
            treeObj.expandNode(node, true, false);//指定选中ID节点展开
            var newNode = {
                id: tableId,
                name: tableCode,
                type: 'V'
            };

            treeObj.addNodes(node, newNode);
            /* 选中当前增加的节点 */
            var children = treeObj.getNodeByParam('id', tableId);
            /*选中新增的视图*/
            treeObj.selectNode(children, true);//指定选中ID的节点
        } else {
            /*当前节点没有展开*/
            /* 先获取当前regionId的路径 */
            $.mask.post('${base}/sys/metaRegion/getMetaRegion', {'regionId': regionId}, function (data) {
                if (data.metaRegion) {
                    var path = data.metaRegion.pathCode;
                    var regionCodes = path.split('..');
                    openTree(regionCodes, tableId);
                }
            });
        }
    }

    /*新增视图，展开所有的父节点 ，并增加子节点*/
    function openTree(regionCodes, tableId) {
        var treeObj = $.fn.zTree.getZTreeObj("_jTree_metaRegionTree");
        $.each(regionCodes, function (idx, val) {
            var node = treeObj.getNodeByParam('regionCode', val);
            treeObj.expandNode(node, true, false);//指定选中ID节点展开
        });

        /* 选中当前增加的节点 */
        var children = treeObj.getNodeByParam('id', tableId);
        /*选中新增的视图*/
        treeObj.selectNode(children, true);//指定选中ID的节点
    }

    /*查询关联表数据*/
    function joinTable(masterTableId, masterColumnCode, joinTableId, joinColumnCode, dataRow, tableCode, tableName, type) {
        /*展开所有的父节点*/
        var treeObj = $.fn.zTree.getZTreeObj("_jTree_metaRegionTree");
        var selected = treeObj.getNodeByParam('id', masterTableId);
        if (selected) {
            debugger;

            var number = setTableNumber(masterTableId);
            var path = '${base}/sys/dataAnalysis/dataQuery?type=J&masterTableId=' + masterTableId + '&joinTableId=' + joinTableId + '&data=' + dataRow + '&relationType=' + type + '&masterColumnCode=' + masterColumnCode + '&joinColumnCode=' + joinColumnCode;
            var title = decodeURI(tableName).substr(0, 14) + '-查询' + number;
            treeObj.cancelSelectedNode();
            //treeObj.selectNode(selected,true);//指定选中ID的节点
            openTab(title, path, selected.id);
        } else {
            $.mask.post('${ctx}/nsa/dataAnalysis/getParents', {'relativeTableId': masterTableId}, function (data) {
                if (data.success) {
                    /*获取父节点上级所有节点*/
                    var joinTrees = data.joinTrees;
                    joinTrees = masterTableId + ',' + joinTrees;
                    openTree(joinTrees);

                    /*打开查询 */
                    var number = setTableNumber(masterTableId);

                    var path = '${ctx}/nsa/dataAnalysis/dataQuery?type=J&masterTableId=' + masterTableId + '&joinTableId=' + joinTableId + '&data=' + dataRow + '&relationType=' + type + '&masterColumnCode=' + masterColumnCode + '&joinColumnCode=' + joinColumnCode;

                    var _title = decodeURI(tableName).substr(0, 14) + '-查询' + number;
                    var title = decodeURI(tableName) + '(' + tableCode + ')' + '-查询' + number;
                    if ($("#tabs").tabs('exists', title)) {
                        $('#tabs').tabs('close', title);
                    }

                    openTab(title, _title, path, tableCode);
                }

            });
        }
    }
</script>
</body>
</html>