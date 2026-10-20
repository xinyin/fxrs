<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">

<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="../../static/css/demo.css">
    <link rel="stylesheet" href="../../static/css/zTreeStyle/zTreeStyle.css">

    <br:css pack="form"/>
    <title>菜单</title>
    <style>
        div#rMenu > ul > li {
            background-color: #ffffff;
        }

        .title {
            margin: 1% auto;
            width: 55%;
            line-height: 35px;
        }

        .contents {
            display: flex;
            justify-content: space-around;
        }

        .lefts,
        .rigths {
            line-height: 28px;
            width: 50%;
        }

        .lefts p,
        .rigths p {
            display: flex;
            justify-content: space-around;
            margin: 3% 0;
        }

        .lefts p span:nth-child(1),
        .rigths p span:nth-child(1) {
            width: 39%;
            text-align: right;
        }

        .lefts p span:nth-child(2),
        .rigths p span:nth-child(2) {
            width: 46%;
            text-align: center;
            padding: 1%;
            border: 1px solid #cccccc;
        }

        .blocks {
            display: block;
            padding: 3%;
        }

        .child {
            display: none;
        }

        .hiddenit {
            display: none;
        }

        .details {
            margin: 0 0 0 7%;
            display: flex;
            justify-content: space-around;
        }

        .details p {
            width: 22%;
            text-align: left
        }

        .details p span {
            display: inline-block;
            width: 90%;
            text-align: right
        }

        .texts {
            width: 80%;
            height: 96px;
            border: 1px solid #cccccc;
        }

        #names {
            display: block;
            background-color: aquamarine;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }

        .qs {
            visibility: hidden
        }

        .editors {
            padding: 2%;
            display: none;
        }

        .editorblock {
            padding: 2%;
            display: block;
        }

        .ico_close {
            width: 0;
            height: 0;
            display: none !important;
        }

        .ico_open {
            width: 0;
            height: 0;
            display: none !important;
        }

        .botton {
            display: none !important
        }

        .choose select {
            display: inline-block;
            width: 172px;
            line-height: 34px;
            height: 34px;
            margin: 3% 0 0 5%;
        }

        .codegd {
            display: inline-block;
            margin: 0 0 3% 0;
        }

        .leftof {
            display: flex;
            justify-content: space-between;
        }

        .choosese {
            width: 50%;
        }

        .choosese select {
            display: inline-block;
            width: 70%;
            height: 34px;
            margin: 0 0 0 3.5%;
        }

        .rigthof p {
            margin: 10px 0 10px;
            position: relative;
        }

        .rigthof input[type="checkbox"] {
            margin: 4px 10px 0;
            line-height: normal;
        }

        .rigthof div {
            float: left;
            margin: 0.5% 2% 1% 0.5%;
        }

        .detail p {
            margin: 10px 0 10px;
            position: relative;
        }

        .detail p span {
            display: inline-block;
            position: absolute;
            top: 50%;
            left: 0;
        }

        bottoms {
            display: none;
        }

        .detail textarea,
        .del textarea {
            display: inline-block;
            width: 86%;
            height: 88px;
            margin: 2% 0 0 12%;
        }

        .noshow {
            visibility: hidden;
        }

        .isshow {
            visibility: visible;
        }

        .noshows {
            display: none;
        }

        .shows {
            display: block;
            width: 100%;
        }

        .cz {
            width: 40%;
            margin: 0 auto;
            text-align: center;
            display: flex;
            justify-content: space-around;
        }

        .h4s {
            background: #F2F8FB;
            line-height: 35px;
            height: 35px;
            padding: 0 1%;
        }

        #gltype {
            display: inline-block;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }

        .sendto,
        .sendcheck,
        .backof {
            border: 1px solid #1c84c6;
            color: #1c84c6;
            padding: 2%;
            line-height: 24px;
            width: 47%;
            border-radius: 7px;
        }

        .sendto:hover,
        .sendcheck:hover,
        .backof:hover {
            border: 1px solid #1c84c6;
            color: #ffffff;
            background: #1c84c6;
            padding: 2%;
            line-height: 24px;
            width: 47%;
            border-radius: 7px;
        }

        .div-tree {
            margin-top: 10px;
        }

        .div-search {
            height: 40px;
        }

        .left-title {
            text-align: left;
            font-size: 16px;
        }

    </style>
</head>

<body class="gray-bg" style="background: white">


<div class="content_wrap">

    <div class="ibox-title">
        <h5>产品树</h5>
    </div>

    <div class="ibox-content">

        <div class="panel-body">
            <div class="col-xs-5">
                <div class="div-search">
                    <form id="proNamePuzzyForm">
                        <div class="form-group">
                            <div class="col-xs-6">
                                <input class="form-control" type="text" id="proNamePuzzy" name="proNamePuzzy"
                                       placeholder="请输入产品名称"/>
                            </div>
                            <div class="col-xs-6">
                                <input class="btn btn-primary" type="button" id="searchTreeByName"
                                       value="查询"/>
                                <input class="btn btn-primary" type="button" id="reset" value="重置"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="div-tree">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
            <div class="col-xs-5" style="border-left: 1px solid">
                <input type="hidden" name="zhongzhuan" id="zhongzhuan" value=""/>
                <h5 class="left-title">产品/目录</h5>
                <div>
                    <form id="upload" class="form-horizontal fv-form fv-form-bootstrap">
                        <div class="form-group">
                            <label class="control-label col-xs-3">上级编号</label>
                            <div class="col-xs-8">
                                <input class="form-control" name="paProId" id="paProId" type="text" disabled="disabled"
                                       value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">上级名称</label>
                            <div class="col-xs-8"><input class="form-control" name="paPraName" id="paPraName"
                                                         type="text" disabled="disabled" value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">编号</label>
                            <div class="col-xs-8"><input class="form-control" name="proId" id="proId" type="text"
                                                         disabled="disabled" value=""/></div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">名称</label>
                            <div class="col-xs-8"><input class="form-control" name="praName" id="praName"
                                                         disabled="disabled" type="text" value=""/></div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">最高交易价格</label>
                            <div class="col-xs-8"><input class="form-control" name="maxAmount" id="maxAmount"
                                                         disabled="disabled" type="text" value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">最低交易价格</label>
                            <div class="col-xs-8"><input class="form-control" name="minAmount" id="minAmount"
                                                         disabled="disabled" type="text" value=""/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">状态</label>
                            <div class="col-xs-8">
                                <select class="form-control" id="status" name="status" disabled="disabled">
                                    <option value="0">启用</option>
                                    <option value="1">未启用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">业务级别</label>
                            <div class="col-xs-8">
                                <select class="form-control" id="isPro" name="isPro" disabled="disabled">
                                    <option value="0">交易类型</option>
                                    <option value="1">业务类型</option>
                                    <option value="2">产品</option>
                                    <option value="3">无</option>
                                    <option value="4">无</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3">描述</label>
                            <div class="col-xs-8">
                                <input class="form-control" name="description" id="description" disabled="disabled"
                                       value=""/>
                            </div>
                        </div>

                        <div class="form-group" style="text-align: center;">
                            <input class="btn btn-primary" id="btn_save" type="button" style="display: none;"
                                   value="保存"/>
                            <input class="btn btn-primary" id="btn_cancel" type="button" style="display: none;"
                                   value="取消"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<%--右击菜单选项--%>
<div id="rMenu" class="popupMenu"
     style="position: absolute; width: 150px; top: 89px; left: 119px; visibility: visible; display: none;">
    <ul>
        <li class="popupMenuItem list-group-item" id="m_check" onclick="addHomeTreeNode();">
            <span class="fa fa-plus-square"></span> 增加同级节点
        </li>
        <li class="popupMenuItem list-group-item" id="m_add" onclick="addNextTreeNode();">
            <span class="fa fa-plus-square-o"></span> 增加下级节点
        </li>
        <li class="popupMenuItem list-group-item" id="m_unCheck" onclick="checkTreeNode();">
            <span class="fa fa-credit-card"></span> 编辑节点
        </li>
        <li class="popupMenuItem list-group-item" id="m_del" onclick="removeTreeNode();">
            <span class="fa fa-window-close"></span> 删除节点
        </li>
    </ul>
</div>

<script src="../../static/js/jquery-3.2.0.js"></script>
<script src="../../static/js/jquery.dialog.js"></script>
<script src="../../static/js/jquery.ztree.core-3.5.js"></script>
<script src="../../static/js/jquery.ztree.exedit-3.5.js"></script>
<script src="../../static/js/jquery.ztree.exhide-3.5.js"></script>
<%--<script src="../../static/js/jquery.ztree.excheck-3.5.js"></script>--%>
<script src="../../static/jep/js/jep.jQuery.js"></script>
<script type="text/javascript">

    /*查询展开树节点*/
    $(document).ready(function () {



        /*查看节点级别*/
        function queryLevel() {
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var sNodes = treeObj.getSelectedNodes();
            var level = sNodes[0].level;
            return level;

        }

        $("#searchTreeByName").bind("click", function () {
            var proNamePuzzy = $("#proNamePuzzy").val();
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getNodes();
            nodes = treeObj.transformToArray(nodes);
            var searchNode = null;
            $.each(nodes, function (i, node) {
                if (node.name == proNamePuzzy) {
                    searchNode = node;
                    return false;
                }
            });
            if (searchNode != null) {
                $("#btn_save").hide();
                $("#btn_cancel").hide();
                setAllDisabled();
                treeObj.cancelSelectedNode();
                treeObj.selectNode(searchNode, true, true);
                fuzzyQueryProduct();
            } else {
                $.messager.toastr('提示', '没有找到匹配数据！');
            }
        });

        /*查询展开树节点之后右侧显示查询信息*/
        function fuzzyQueryProduct() {
            var proNamePuzzy = $("#proNamePuzzy").val();
            // alert(proNamePuzzy)
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${base}/bus/product/queryProductByProName",
                data: {
                    proNamePuzzy: proNamePuzzy
                },
                success: function (result) {
                    $("#paProId").val(result["paProId"]);
                    $("#paPraName").val(result["paProName"]);
                    $("#proId").val(result["pid"]);
                    $("#praName").val(result["proName"]);
                    $("#maxAmount").val(result["maxAmount"]);
                    $("#minAmount").val(result["minAmount"]);
                    /* $("#displayOrder").val(result["displayOrder"]),*/
                    $("#status").val(result["status"]);
                    $("#isPro").val(result["isPro"]);
                    $("#description").val(result["description"]); //动态给页面input 赋值
                }

            });
        }

        $("#reset").bind("click", function () {
            $("#proNamePuzzy").val("");
            $("#paProId").val("");
            $("#paPraName").val("");
            $("#proId").val("");
            $("#praName").val("");
            $("#maxAmount").val("");
            $("#minAmount").val("");
            $("#status").val("");
            $("#isPro").val("");
            $("#description").val("");
            setAllDisabled();
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(false);
        });
        /*重置按钮。合起树*/
        $("#btn_cancel").bind("click", function () {
            var node = $("#zhongzhuan").data("treeNode");
            $.ajax({
                type: "get",
                url: "${base}/bus/product/queryProductByPid",
                dataType: "json",
                data: {
                    "productPid": node.pid
                },
                success: function (result) {
                    $("#paProId").val(result["paProId"]);
                    $("#paPraName").val(result["paProName"]);
                    $("#proId").val(result["pid"]);
                    $("#praName").val(result["proName"]).attr("disabled", "disabled");
                    $("#status").val(result["status"]).attr("disabled", "disabled");
                    $("#isPro").val(result["isPro"]).attr("disabled", "disabled");
                    $("#description").val(result["description"]).attr("disabled", "disabled");
                    $("#btn_save").hide();
                    $("#btn_cancel").hide();
                }
            });
        });
        /*点击保存按钮，执行不同的函数，产生增删改*/
        $("#btn_save").bind("click", function () {
            var praName = $('#praName').val();
            //TODO 其他检查
            if ($("#praName").val() == "") {
                alert("名称不能为空");
                return false;
            }
            if (form_type == "edit") {
                editSubmint();
            } else if (form_type == "addHome") {
                addVisSubmint();
            } else if (form_type == "addNext") {
                addNextSubmint();
            }
        });

        $("#btn_cancel").bind("click", function () {
            setAllDisabled();
            setAllEmpty();
            if (form_type == "edit") {
            } else if (form_type == "addHome") {
                var newNode = $("#zhongzhuan").data("newNode");
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.removeNode(newNode);
            } else if (form_type == "addNext") {
                var newNode = $("#zhongzhuan").data("newNode");
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.removeNode(newNode);
            }
        });


        function setAllDisabled() {
            $("#paProId").attr("disabled", "disabled");
            $("#paPraName").attr("disabled", "disabled");
            $("#proId").attr("disabled", "disabled");
            $("#praName").attr("disabled", "disabled");
            $("#maxAmount").attr("disabled", "disabled");
            $("#minAmount").attr("disabled", "disabled");
            $("#status").attr("disabled", "disabled");
            $("#isPro").attr("disabled", "disabled");
            $("#description").attr("disabled", "disabled");
        }

        function setAllEmpty() {
            $("#paProId").val("");
            $("#paPraName").val("");
            $("#proId").val("");
            $("#praName").val("");
            $("#maxAmount").val("");
            $("#minAmount").val("");
            $("#status").val("0");
            $("#isPro").val("1");
            $("#description").val("");
        }

        var form_type = "edit";

        /*编辑节点*/
        function editSubmint() {
            var pid = $("#zhongzhuan").val();
            var praName = $('#praName').val();
            /*select状态*/
            var status = $("#status option:selected").val();
            var isPro = $("#isPro option:selected").val();
            /*描述*/
            var description = $('#description').val();
            var maxAmount = $('#maxAmount').val();
            var minAmount = $('#minAmount').val();
            // alert(pid+"---"+ status+"-----"+description)

            $.ajax({
                type: "POST",
                dataType: "text",
                url: "${base}/bus/product/editNode",
                data: {
                    pid: pid,
                    proName: praName,
                    maxAmount: maxAmount,
                    minAmount: minAmount,
                    status: status,
                    isPro: isPro,
                    description: description
                },
                success: function (result) {
                    console.log(result);
                    // alert(msg);
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    var node = $("#zhongzhuan").data("treeNode");
                    //var node = treeObj.getNodeByTId(node.pid);
                    node.name = praName;
                    treeObj.updateNode(node);
                    $("#btn_save").hide();
                    $("#btn_cancel").hide();
                    $("#praName").attr("disabled", "disabled");
                    $("#maxAmount").attr("disabled", "disabled");
                    $("#minAmount").attr("disabled", "disabled");
                    $("#status").attr("disabled", "disabled");
                    $("#isPro").attr("disabled", "disabled");
                    $("#description").attr("disabled", "disabled");
                    // alert(result.msg);
                }
            });
        }

        /*增加同级节点*/
        function addVisSubmint() {
            var pid = $("#zhongzhuan").val();
            var proName = $('#praName').val();
            var maxAmount = $('#maxAmount').val();
            var minAmount = $('#minAmount').val();
            var status = $("#status option:selected").val();
            var isPro = $("#isPro option:selected").val();
            var description = $('#description').val();
            // alert(pid+"---"+ status+"-----"+description);

            //TODO param + 需要得参数
            $.ajax({
                type: "POST",
                dataType: "text",
                url: "${base}/bus/product/addVisNode",
                data: {
                    pid: pid,
                    proName: proName,
                    maxAmount: maxAmount,
                    minAmount: minAmount,
                    status: status,
                    isPro: isPro,
                    description: description
                },
                success: function (result) {
                    // alert(JSON.stringify(result));
                    var newNode = $("#zhongzhuan").data("newNode");
                    newNode.name = proName;
                    // newNode.Pid = result.pId; //TODO
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.updateNode(newNode);

                    setAllDisabled();
                    setAllEmpty();
                    $("#btn_save").hide();
                    $("#btn_cancel").hide();
                }
            });
        }

        /*增加下级节点*/
        function addNextSubmint() {
            // var param = $('#upload').serialize();
            var pid = $("#zhongzhuan").val();
            var praName = $('#praName').val();
            var maxAmount = $('#maxAmount').val();
            var minAmount = $('#minAmount').val();
            var status = $("#status option:selected").val();
            var isPro = $("#isPro option:selected").val();
            var description = $('#description').val();
            // alert(pid + "---" + status + "-----" + description)
            //TODO param + 需要得参数
            $.ajax({
                type: "POST",
                dataType: "text",
                url: "${base}/bus/product/addNextNode",
                data: {
                    pid: pid,
                    proName: praName,
                    maxAmount: maxAmount,
                    minAmount: minAmount,
                    status: status,
                    isPro: isPro,
                    description: description
                },
                success: function (result) {
                    var newNode = $("#zhongzhuan").data("newNode");
                    newNode.name = praName;
                    // newNode.Pid = result.pId; //TODO
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.updateNode(newNode);

                    setAllDisabled();
                    setAllEmpty();
                    $("#btn_save").hide();
                    $("#btn_cancel").hide();
                }
            });
        }

        /*树展示以及右击弹出菜单、单击触发查询树节点基本信息*/
        var zTree, rMenu;
        var setting = {
            view: {
                dblClickExpand: false
            },
            check: {
                enable: true
            },
            callback: {
                onRightClick: OnRightClick,//右击弹出菜单-
                onClick: zTreeOnClick//单击查询产品或目录信息
            }

        };
        $.ajax({
            type: 'GET',
            url: '${base}/bus/product/treeNode',
            data: {},
            dataType: 'json',
            success: function (data) {
                console.log(data)
                var zNodes = data
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu = $("#rMenu");
            }
        });

        /*右击回调函数-显示菜单*/
        function OnRightClick(event, treeId, treeNode) {
            if (treeNode) {
                zTree.selectNode(treeNode);
                showRMenu("node", event.clientX, event.clientY);
                $("#zhongzhuan").val(treeNode.id)
                $("#zhongzhuan").data("treeNode", treeNode);
            }
        }

        /*菜单定位显示*/
        function showRMenu(type, x, y) {
            $("#rMenu").show();
            if (type == "root") {
                $("#m_del").hide();
                $("#m_check").hide();
                $("#m_unCheck").hide();
            } else {
                $("#m_del").show();
                $("#m_check").show();
                $("#m_unCheck").show();
            }
            rMenu.css({
                "top": y + "px",
                "left": x + "px",
                "visibility": "visible"
            });

            $("body").bind("mousedown", onBodyMouseDown);

        }

        /*单击body隐藏菜单*/
        function hideRMenu() {
            if (rMenu) rMenu.css({
                "visibility": "hidden"
            });
            $("body").unbind("mousedown", onBodyMouseDown);
        }

        function onBodyMouseDown(event) {
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
                rMenu.css({
                    "visibility": "hidden"
                });
            }
        }

        var addCount = 1;

        //获取节点属性值等
        function onCheck(e, treeId, treeNode) {
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = treeObj.getCheckedNodes(true),
                v = "";
            for (var i = 0; i < nodes.length; i++) {
                v += nodes[i].name + ",";
                console.log("节点id0:" + nodes[i].relPid + "节点名称" + v); //获取选中节点的值
                console.log("节点id1:" + nodes[i].pid + "节点名称" + v); //获取选中节点的值
                console.log(nodes[i].target); //获取选中节点的值
                // $("body").bind("mousedown", onBodyMouseDown);
            }
        }

        /*单击查询节点基本信息*/
        function zTreeOnClick(event, treeId, treeNode) {

            // alert(queryLevel())
            from_type = "edit";
            //获取当前节点的pid
            var pid = treeNode.id;
            // alert(pid)

            $.ajax({
                type: "get",
                url: "${base}/bus/product/queryProductByPid",
                dataType: "json",
                data: {
                    "productPid": pid
                },
                success: function (result) {

                    // data为返回值
                    $("#paProId").val(result["paProId"]);
                    $("#paPraName").val(result["paProName"]);
                    $("#proId").val(result["pid"]);
                    $("#praName").val(result["proName"]);
                    $("#maxAmount").val(result["maxAmount"]);
                    $("#minAmount").val(result["minAmount"]);
                    /* $("#displayOrder").val(result["displayOrder"]),*/
                    $("#status").val(result["status"]);
                    $("#isPro").val(queryLevel());
                    $("#description").val(result["description"]); //动态给页面input 赋值
                    // 成功后的回调方法
                    // alert(result);
                    $("#btn_save").hide();
                    $("#btn_cancel").hide();
                    setAllDisabled();
                    console.log(result)
                }
            });
        };

        //新增同级节点
        window.addHomeTreeNode = function () {
            form_type = "addHome";
            hideRMenu();
            setAllEmpty();

            $("#btn_save").show();
            $("#btn_cancel").show();
            $("#praName").removeAttr("disabled");
            $("#maxAmount").removeAttr("disabled");
            $("#minAmount").removeAttr("disabled");
            $("#status").removeAttr("disabled");
            // $("#isPro").removeAttr("disabled");
            $("#description").removeAttr("disabled");

            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var node = $("#zhongzhuan").data("treeNode");
            var pNode = node.getParentNode();
            var newNode = {name: "新节点"};
            newNode = treeObj.addNodes(pNode, newNode)[0];
            treeObj.cancelSelectedNode();
            treeObj.selectNode(newNode, true, true);
            $("#zhongzhuan").data("newNode", newNode);

            $("#paProId").val(pNode ? pNode.pId : "");
            $("#paPraName").val(pNode ? pNode.name : "");
        };

        //新增下级节点
        window.addNextTreeNode = function () {
            form_type = "addNext";
            hideRMenu();
            setAllEmpty();

            $("#btn_save").show();
            $("#btn_cancel").show();
            $("#praName").removeAttr("disabled");
            $("#maxAmount").removeAttr("disabled");
            $("#minAmount").removeAttr("disabled");
            $("#status").removeAttr("disabled");
            // $("#isPro").removeAttr("disabled");
            $("#description").removeAttr("disabled");

            debugger
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var pNode = $("#zhongzhuan").data("treeNode");
            var newNode = {name: "新节点"};
            newNode = treeObj.addNodes(pNode, newNode)[0];
            treeObj.cancelSelectedNode();
            treeObj.selectNode(newNode, true, true);
            $("#zhongzhuan").data("newNode", newNode);

            $("#paProId").val(pNode ? pNode.pId : "");
            $("#paPraName").val(pNode ? pNode.name : "");
        };


        /*编辑节点*/
        window.checkTreeNode = function (checked) {
            var pid = $('#zhongzhuan').val();

            $.ajax({
                type: "get",
                url: "${base}/bus/product/queryProductByPid",
                dataType: "json",
                data: { // 提交数据
                    "productPid": pid
                },
                success: function (result) {

                    //TODO:
                    //ajax 返回的数据需要判断成功失败，增加代码的健壮性
                    form_type = "edit";
                    // data为返回值
                    $("#paProId").val(result["paProId"]);
                    $("#paPraName").val(result["paProName"]);
                    $("#proId").val(result["pid"]);
                    $("#praName").val(result["proName"]);
                    $("#maxAmount").val(result["maxAmount"]);
                    $("#minAmount").val(result["minAmount"]);
                    $("#status").val(result["status"]);
                    $("#isPro").val(queryLevel());
                    $("#description").val(result["description"]);
                    // $.messager.toastr('提示', result.msg);
                    $("#btn_save").show();
                    $("#btn_cancel").show();
                    $("#praName").removeAttr("disabled");
                    $("#maxAmount").removeAttr("disabled");
                    $("#minAmount").removeAttr("disabled");
                    $("#status").removeAttr("disabled");
                    // $("#isPro").removeAttr("disabled");
                    $("#description").removeAttr("disabled");
                }
            });

            hideRMenu();
            //editAndSubmit
            // $('#editAndSubmit').style("submit")
        }

        /*删除节点*/
        window.removeTreeNode = function () {
            hideRMenu();
            var pid = $('#zhongzhuan').val();
            $.messager.confirm('提示', '是否删除此节点和它的所有子节点？', function (result) {
                if (result) {
                    $.ajax({
                        type: "get",
                        url: "${base}/bus/product/removeNode",
                        data: {
                            "productPid": pid
                        },
                        success: function (result) {
                            //alert(result.msg)
                            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                            var node = $("#zhongzhuan").data("treeNode");
                            treeObj.removeNode(node);
                        }
                    });
                }
            });
        };

    });


</script>
</body>

</html>