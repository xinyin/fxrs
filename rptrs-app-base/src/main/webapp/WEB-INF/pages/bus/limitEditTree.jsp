<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="../../static/css/demo.css">
    <link rel="stylesheet" href="../../static/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

    <br:css pack="form"/>
    <title>菜单</title>
    <style>
        div#rMenu {
            position: absolute;
            visibility: hidden;
            top: 0;
            background-color: #555;
            text-align: left;
            padding: 2px;
        }

        div#rMenu ul li {
            margin: 1px 0;
            padding: 0 5px;
            cursor: pointer;
            list-style: none outside none;
            background-color: #DFDFDF;
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

        .lefts, .rigths {
            line-height: 28px;
            width: 50%;
        }

        .lefts p, .rigths p {
            display: flex;
            justify-content: space-around;
            margin: 3% 0;
        }

        .lefts p span:nth-child(1), .rigths p span:nth-child(1) {
            width: 39%;
            text-align: right;
        }

        .lefts p span:nth-child(2), .rigths p span:nth-child(2) {
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

        .detail textarea, .del textarea {
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

        .sendto, .sendcheck, .backof {
            border: 1px solid #1c84c6;
            color: #1c84c6;
            padding: 2%;
            line-height: 24px;
            width: 47%;
            border-radius: 7px;
        }

        .sendto:hover, .sendcheck:hover, .backof:hover {
            border: 1px solid #1c84c6;
            color: #ffffff;
            background: #1c84c6;
            padding: 2%;
            line-height: 24px;
            width: 47%;
            border-radius: 7px;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">

<div class="content_wrap">
    <bc:ibox id="iboxUnitDlg" title="" col="12" collapsible="false">
    <div>
        <ul id="treeDemo" class="ztree"></ul>
        <div style="text-align:center">
            <button type="button" class="btn btn-primary" onclick="submitId();">确认</button>
            <button type="button" class="btn btn-default" onclick="$.messager.hide('productViewId');">取消</button>
        </div>
        </bc:ibox>
    </div>

    <script src="../../static/js/jquery-3.2.0.js"></script>
    <script src="../../static/js/jquery.dialog.js"></script>
    <script src="../../static/js/jquery.ztree.core-3.5.js"></script>
    <script src="../../static/js/jquery.ztree.exedit-3.5.js"></script>
    <script src="../../static/js/jquery.ztree.excheck-3.5.js"></script>
    <script src="../../static/jep/js/jep.jQuery.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            /*查看节点级别*/
            function closee() {
                window.close();
            };
            var setting = {
                view: {
                    dblClickExpand: false
                },
                check: {
                    enable: false
                },
                callback: {
                    // onRightClick: OnRightClick,
                    onClick: zTreeOnClick
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
                }
            });
        });

        var nodeId;
        var nodeLevel;
        var nodeName;
        var nodeString;
        var nodePaProId;

        //单击获取当前节点id
        function zTreeOnClick(event, treeId, treeNode) {
            nodePaProId = treeNode.getParentNode().id
            nodeId = treeNode.id;
            nodeLevel = treeNode.level;
            nodeName = treeNode.name;
            nodeString = nodeLevel + '-' + nodeId + '-' + nodeName + '-' + nodePaProId;
            if (nodeLevel == '0' || nodeLevel == '1') {
                nodeName = '无';
            }
        }

        function submitId() {
            if (nodeId == null) {
                $.messager.toastr('提示', '请选择一个数据区域。', 'info');
            } else {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var sNodes = treeObj.getSelectedNodes();
                var level = sNodes[0].level;
                if (level == '0') {
                    alert('不可选择，请选择业务类型或产品！')
                    return false
                }
                $.messager.args('productViewId').result([{
                    'pid': nodeId,
                    'proName': nodeName,
                    'level': nodeString
                }]);
                var checkObj = {
                    expr: nodeString
                };
                $.messager.hide('productViewId');
                //关闭窗口

            }
        }


    </script>
</body>
</html>