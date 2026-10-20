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
            display: flex;
            justify-content: space-around;
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

        .detail textarea  ,.del textarea{
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
            visibility: hidden;
        }

        .cz {
            width: 40%;
            margin: 0 auto;
            text-align: center;
            display: flex;
            justify-content: space-around;
        }
        .h4s{
            background: #F2F8FB;
            line-height: 35px;
            height: 35px;
            padding: 0 1%;
        }
        #gltype{
            display: inline-block;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }

        .sendto,.sendcheck,.backof{
            border: 1px solid #1c84c6;
            color: #1c84c6;
            padding: 2%;
            line-height: 24px;
            width: 47%;
            border-radius: 7px;
        }
        .sendto:hover ,.sendcheck:hover,.backof:hover{
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
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <div class="right">
        <div class="roots">
            <div class="title">
                <span>机构名称：</span><span>成都市农村商业银行股份有限公司</span>
                <input type="button" value="test新增" onclick="sub()">
                <input type="button" value="test删除" onclick="del()">
            </div>

            <div class="contents">
                <div class="lefts">
                    <p>
                        <span>股东个数</span><span id="gdnum">55</span>
                    </p>
                    <p>
                        <span>冻结股数(股)</span><span id="djnum">5235</span>
                    </p>
                    <p>
                        <span>质押股数(股)</span><span id="zynum">5543</span>
                    </p>
                    <p>
                        <span>总资产(万元/人民币)</span><span id="totalnum">55545</span>
                    </p>
                </div>
                <div class="rigths">
                    <p>
                        <span>总股本</span><span id="total">55</span>
                    </p>
                    <p>
                        <span>占总股本(%)</span><span id="rate">5235</span>
                    </p>
                    <p>
                        <span>占总股本(%)</span><span id="gbrate">5543</span>
                    </p>
                    <p>
                        <span>净资产(万元/人民币)</span><span id="jzc">55545</span>
                    </p>
                </div>
            </div>
        </div>
        <div class="child">
            <div class="ups">
                <h4 class="h4s">关联方</h4>
                <div class="contents">
                    <div class="lefts">
                        <p>
                            <span>关联方名称</span><span id="names">55</span>
                        </p>
                        <p>
                            <span>证件类型</span><span id="typename">5235</span>
                        </p>

                    </div>
                    <div class="rigths">
                        <p>
                            <span>股东代码</span><span id="codes">55</span>
                        </p>
                        <p>
                            <span>证件号码</span><span id="codenb">5235</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="downs">
                <h4 class="h4s">关联关系</h4>
                <div class="contents">
                    <div class="lefts">
                        <p>
                            <span>关联关系类型</span><span id="gltype">55</span>
                        </p>
                        <p>
                            <span>持股数量(股)</span><span id="cgnum">5235</span>
                        </p>

                    </div>
                    <div class="rigths">
                        <p class="qs">
                            <span>关系</span><span id="cantion">55</span>
                        </p>
                        <p>
                            <span>持股比例(%)</span><span id="cgbl">5235</span>
                        </p>
                    </div>
                </div>
                <%--<div class="details">--%>
                    <%--<p>--%>
                        <%--<span>情况说明：</span>--%>
                    <%--<div class="texts" id="texts"></div>--%>
                    <%--</p>--%>
                <%--</div>--%>
                <div class="bottoms">
                    <h4 class="h4s">删除关联关系</h4>
                    <div class="del">
                        <p>
                            <span>删除原因：</span>
                            <textarea id="texta" class="textes"></textarea>
                        </p>
                    </div>
                    <div class="cz">
                        <div class="sendto">保存</div>
                        <div class="backof">取消</div>
                    </div>
                </div>
            </div>
        </div>
        <div id="editors" class="editors">
            <div class="ups">
                <h4 class="h4s">关联方</h4>
                <div class="contents">
                    <div class="lefts">
                        <div>
                            <span>关联方名称</span>
                            <input type="text" id="glfname" placeholder="请输入关联方名称"/>
                        </div>
                        <div class="choose">
                            <label>证件类型</label>
                            <select id="selectit">
                                <option value=0>请选择</option>
                                <option value="IDCARD">居民身份证</option>
                                <option value="PERMANENTRESIDENT">居民户口簿</option>
                                <option value="MILITARYID">军官证</option>
                                <option value="PASSPORT">护照</option>
                                <option value="UNIFIEDSOCIALCREDITCODE">统一社会信用代码</option>
                                <option value="OTHER">其他证件</option>
                            </select>
                        </div>

                    </div>
                    <div class="rigths">
                        <div>
                            <span>股东代码</span>
                            <input type="text" class="codegd" id="codegd" placeholder=""/>
                        </div>
                        <div>
                            <span>证件号码</span>
                            <input type="text" id="idcode" placeholder=""/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="downs">
                <h4 class="h4s">关联关系</h4>
                <div class="contentes">
                    <div class="leftof">
                        <div class="choosese">
                            <label>关联关系类型</label>
                            <select id="isshows" onchange="showit()">
                                <option value=''>请选择</option>
                                <option value="0">股东</option>
                                <option value="02">控股股东</option>
                                <option value="03">控股股东(母公司)</option>
                                <option value="04">控股子公司</option>
                                <option value="05">控股自然人股东</option>
                                <option value="06">5%以上控股权的机构股东</option>
                                <option value="07">5%以上控股权的自然人股东</option>
                                <option value="08">5%以下控股权的机构股东</option>
                                <option value="09">5%以下控股权的自然人股东</option>
                                <option value="10">直接、间接控股5%以上的机构股东</option>
                                <option value="11">直接、间接控股5%以上的自然人股东</option>
                                <option value="12">直接、间接控股5%以下的机构股东</option>
                                <option value="13">直接、间接控股5%以下的自然人股东</option>
                                <option value="14">一致行动人（机构）</option>
                                <option value="15">一致行动人（自然人）</option>
                                <option value="16">控制的法人或其他组织</option>
                                <option value="17">控制或施加重大影响的机构</option>
                                <option value="18">董事</option>
                                <option value="19">监事</option>
                                <option value="20">高管</option>
                                <option value="21">其他管理人员</option>
                                <option value="22">亲属</option>
                                <option value="23">其他关联机构</option>
                                <option value="24">其他关联自然人</option>
                            </select>
                        </div>
                        <div class="choosese noshow" id="isshow">
                            <label>关系</label>
                            <select id="qinshu">
                                <option value=''>请选择</option>
                                <option value=0>父亲</option>
                                <option value=1>儿子</option>
                                <option value=2>女儿</option>
                            </select>
                        </div>

                    </div>
                    <div class="rigthof">
                        <p>
                            <label>是否持有上一级关联方股份<input name="" type="checkbox" id="checkboxse" value="true"
                                                      onchange="getcode()"/> </label>
                        </p>
                        <div id="isshowof" class="noshows">
                            <div>
                                <span>持股数量(股)</span>
                                <input type="text" id="nums" placeholder=""/>
                            </div>
                            <div>
                                <span>持股比例(%)</span>
                                <input type="text" id="rats" placeholder=""/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="detail">
                    <p>
                        <h4 class="h4s">情况说明：</h4>
                        <textarea id="textareas"></textarea>
                    </p>
                </div>

            </div>
            <div class="cz">
                <div class="sendcheck">提交审批</div>
                <div class="backof">返回</div>
            </div>
        </div>
    </div>
</div>
<div id="rMenu">
    <ul>
        <li id="m_check" onclick="checkTreeNode(true);">查看基本信息</li>
        <li id="m_add" onclick="addTreeNode();">增加下一级关联方</li>
        <li id="m_unCheck" onclick="checkTreeNode(false);">编辑关联关系</li>
        <li id="m_del" onclick="removeTreeNode();">删除关联关系</li>


    </ul>
</div>

<script src="../../static/js/jquery-3.2.0.js"></script>
<script src="../../static/js/jquery.dialog.js"></script>
<script src="../../static/js/jquery.ztree.core-3.5.js"></script>
<script src="../../static/js/jquery.ztree.exedit-3.5.js"></script>
<%--<script src="../../static/js/jquery.ztree.excheck-3.5.js"></script>--%>
<script src="../../static/jep/js/jep.jQuery.js"></script>
<script type="text/javascript">
    function sub() {
        var pid = $("#pid").val();
        var saveTreeMsgVo = {
            'fromPid': '7779',
            'fromType': 'ORG',
            'toPid': '7783',
            'toType': 'ORG',
            'fromRelCategory': '03',
            'toRelCategory': '03',
            'relCategorySub': '10',
            'shareholdingStrand': '50',
            'shareholdingRatio': '12.1',
            'cognizaReason': 'test'
        }
        $.ajax({
            url: "${base}/bus/treeSuspect/saveRelMsg",
            type: "get",
            data: saveTreeMsgVo,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data.relPsnSuspect)
                if (data.success == true) {
                    //保存成功
                    alert('保存成功！')
                }
            }
        })
    }

    function del() {
        var pid = $("#pid").val();
        var saveTreeMsgVo = {
            'fromPid': '7779',
            'fromType': 'ORG',
            'toPid': '7783',
            'toType': 'ORG',
            'fromRelCategory': '03',
            'toRelCategory': '03',
            'relCategorySub': '10',
            'shareholdingStrand': '50',
            'shareholdingRatio': '12.1',
            'cognizaReason': 'test'
        }
        //保存成功
        //ajax请求数据
        //必须传入id 值
        var objData = saveTreeMsgVo;
        //判断是否设置了关联管理
        var myDate = new Date();
        objData.id = myDate.getTime();
        objData.name = 'test04集团';
        objData.shareholdersCode = '20';
        objData.identifyType = '身份证';
        objData.identifyNumber = '1524896';
        // 欠缺是否是关联方
        //objData.cognizaReason=$("#cognizaReason").val().trim();
        //objData.isSuspect=$("#isSuspect").val().trim();
        data = JSON.stringify(objData);
        var formData = $.helper.base64Encode(data);
        var obj = {
            procDefCode: 'YZJ_REL_MANA_DEL',
            oldData: '',
            newData: formData,
            uploadSessions: '',
            procInstId: ''

        }
        $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', '提交成功。', 'info', function () {
                    window.location = '${base}/workflow/process/runingList';
                });
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }








    $(document).ready(function () {
        var zTree, rMenu;
        var setting = {
            view: {
                dblClickExpand: false
            },
            check: {
                enable: true
            },
            callback: {
                onRightClick: OnRightClick,
                onClick: zTreeOnClick
            }
        };
        $.ajax({
            type: 'GET',
            url: '${base}/bus/treeSuspect/allNode',
            data: {},
            dataType: 'json',
            success: function (data) {
                console.log(data)
                var zNodes = data


                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu = $("#rMenu");


                <%--window.location = '${base}/sys/notify/announceList';--%>
            }
        });


        function OnRightClick(event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                zTree.cancelSelectedNode();
                showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && !treeNode.noR) {
                zTree.selectNode(treeNode);
                showRMenu("node", event.clientX, event.clientY);
            }
        }

        function showRMenu(type, x, y) {
            $("#rMenu ul").show();
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

        function zTreeOnClick(event, treeId, treeNode) {
            console.log(treeNode.relPid + "," + treeNode.pid + "," + treeNode.name);
            console.log(treeNode.target);
            $('.bottoms').css('display','none')
            if (treeNode.target == 'main') {
                $(".blocks").addClass('child')
                $(".roots").removeClass('hiddenit')
                $('#editors').addClass('editors').removeClass("editorblock")
            } else if (treeNode.target == 'child') {
                $(".roots").addClass('hiddenit')
                $(".child").addClass('blocks').removeClass("child")
                $('#editors').addClass('editors').removeClass("editorblock")
                if (treeNode.name != null && treeNode.name != undefined) {
                    $("#names").html(treeNode.name)
                    $("#names").attr('title', treeNode.name)
                } else {
                    $("#names").html('')
                }
                if (treeNode.shareholdersCode != null && treeNode.shareholdersCode != undefined) {
                    $("#codes").html(treeNode.shareholdersCode)
                } else {
                    $("#codes").html('')
                }
                if (treeNode.identifyType != null && treeNode.identifyType != undefined) {
                    if (treeNode.identifyType == 'MILITARYID') {
                        $("#typename").html('军官证')
                    } else if (treeNode.identifyType == 'PASSPORT') {
                        $("#typename").html('护照')
                    } else if (treeNode.identifyType == 'OTHER') {
                        $("#typename").html('其他证件')
                    } else if (treeNode.identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                        $("#typename").html('统一社会信用代码')
                    } else if (treeNode.identifyType == 'IDCARD') {
                        $("#typename").html('居民身份证')
                    } else if (treeNode.identifyType == 'PERMANENTRESIDENT') {
                        $("#typename").html('居民户口簿')
                    }

                } else {
                    $("#typename").html('')
                }
                if (treeNode.identifyNumber != null && treeNode.identifyNumber != undefined) {
                    $("#codenb").html(treeNode.identifyNumber)
                } else {
                    $("#codenb").html('')
                }
                if (treeNode.relCategory != null && treeNode.relCategory != undefined) {
                    if (treeNode.relCategory == '01') {
                        $("#gltype").html('股东')
                    } else if (treeNode.relCategory == '02') {
                        $("#gltype").html('控股股东')
                    } else if (treeNode.relCategory == '03') {
                        $("#gltype").html('控股股东(母公司)')
                    } else if (treeNode.relCategory == '04') {
                        $("#gltype").html('控股子公司')
                    } else if (treeNode.relCategory == '05') {
                        $("#gltype").html('控股自然人股东')
                    } else if (treeNode.relCategory == '06') {
                        $("#gltype").html('5%以上控股权的机构股东')
                    }
                    if (treeNode.relCategory == '07') {
                        $("#gltype").html('5%以上控股权的自然人股东')
                    } else if (treeNode.relCategory == '08') {
                        $("#gltype").html('5%以下控股权的机构股东')
                    } else if (treeNode.relCategory == '09') {
                        $("#gltype").html('5%以下控股权的自然人股东')
                    } else if (treeNode.relCategory == '10') {
                        $("#gltype").html('直接、间接控股5%以上的机构股东')
                    } else if (treeNode.relCategory == '11') {
                        $("#gltype").html('直接、间接控股5%以上的自然人股东')
                    } else if (treeNode.relCategory == '12') {
                        $("#gltype").html('直接、间接控股5%以下的机构股东')
                    }
                    if (treeNode.relCategory == '13') {
                        $("#gltype").html('直接、间接控股5%以下的自然人股东')
                    } else if (treeNode.relCategory == '14') {
                        $("#gltype").html('一致行动人（机构）')
                    } else if (treeNode.relCategory == '15') {
                        $("#gltype").html('一致行动人（自然人）')
                    } else if (treeNode.relCategory == '16') {
                        $("#gltype").html('控制的法人或其他组织')
                    } else if (treeNode.relCategory == '17') {
                        $("#gltype").html('控制或施加重大影响的机构')
                    } else if (treeNode.relCategory == '18') {
                        $("#gltype").html('董事')
                    }
                    if (treeNode.relCategory == '19') {
                        $("#gltype").html('监事')
                    } else if (treeNode.relCategory == '20') {
                        $("#gltype").html('高管')
                    } else if (treeNode.relCategory == '21') {
                        $("#gltype").html('其他管理人员')
                    } else if (treeNode.relCategory == '22') {

                        $("#gltype").html('亲属')
                        if (treeNode.relCateorySub == '01') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('父亲')
                        }
                        if (treeNode.relCateorySub == '02') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('儿子')
                        }
                        if (treeNode.relCateorySub == '03') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('女儿')
                        } else {
                            $(".qs").css('visibility', 'hidden')
                        }

                    } else if (treeNode.relCategory == '23') {
                        $("#gltype").html('其他关联机构')
                    } else if (treeNode.relCategory == '24') {
                        $("#gltype").html('其他关联自然人')
                    }
                } else {
                    $("#gltype").html('')
                }

                if (treeNode.shareholdingStrand != null && treeNode.shareholdingStrand != undefined) {
                    $("#cgnum").html(treeNode.shareholdingStrand)
                } else {
                    $("#cgnum").html('')
                }
                if (treeNode.shareholdingRatio != null && treeNode.shareholdingRatio != undefined) {
                    $("#cgbl").html(treeNode.shareholdingRatio)
                } else {
                    $("#cgbl").html('')
                }

            }

        };

        window.addTreeNode = function () {
            hideRMenu();
            $(".roots").addClass('hiddenit')
            $('#editors').addClass('editorblock')
            $(".blocks").addClass('child')
            // $(".child").addClass('blocks').removeClass("child")
            var selectedNodes = zTree.getSelectedNodes()[0];
            console.log(selectedNodes)
            localStorage.fromPid = selectedNodes.pid;
            localStorage.fromType = selectedNodes.type;
            localStorage.fromRelCategory = selectedNodes.relCategory;
            console.log(localStorage.fromPid)

        }

        window.removeTreeNode = function () {
            var selectedNodes = zTree.getSelectedNodes()[0];
            if (selectedNodes.target == 'main') {
                $(".blocks").addClass('child')
                $(".roots").removeClass('hiddenit')
                $('#editors').addClass('editors').removeClass("editorblock")
            } else if (selectedNodes.target == 'child') {
                $(".roots").addClass('hiddenit')
                $(".child").addClass('blocks').removeClass("child")
                $('#editors').addClass('editors').removeClass("editorblock")
                $('.bottoms').css('display', 'block')
                if (selectedNodes.name != null && selectedNodes.name != undefined) {
                    $("#names").html(selectedNodes.name)
                    $("#names").attr('title', selectedNodes.name)
                } else {
                    $("#names").html('')
                }
                if (selectedNodes.shareholdersCode != null && selectedNodes.shareholdersCode != undefined) {
                    $("#codes").html(selectedNodes.shareholdersCode)
                } else {
                    $("#codes").html('')
                }
                if (selectedNodes.identifyType != null && selectedNodes.identifyType != undefined) {
                    if (selectedNodes.identifyType == 'MILITARYID') {
                        $("#typename").html('军官证')
                    } else if (selectedNodes.identifyType == 'PASSPORT') {
                        $("#typename").html('护照')
                    } else if (selectedNodes.identifyType == 'OTHER') {
                        $("#typename").html('其他证件')
                    } else if (selectedNodes.identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                        $("#typename").html('统一社会信用代码')
                    } else if (selectedNodes.identifyType == 'IDCARD') {
                        $("#typename").html('居民身份证')
                    } else if (selectedNodes.identifyType == 'PERMANENTRESIDENT') {
                        $("#typename").html('居民户口簿')
                    }

                } else {
                    $("#typename").html('')
                }
                if (selectedNodes.identifyNumber != null && selectedNodes.identifyNumber != undefined) {
                    $("#codenb").html(selectedNodes.identifyNumber)
                } else {
                    $("#codenb").html('')
                }
                if (selectedNodes.relCategory != null && selectedNodes.relCategory != undefined) {
                    if (selectedNodes.relCategory == '01') {
                        $("#gltype").html('股东')
                    } else if (selectedNodes.relCategory == '02') {
                        $("#gltype").html('控股股东')
                    } else if (selectedNodes.relCategory == '03') {
                        $("#gltype").html('控股股东(母公司)')
                    } else if (selectedNodes.relCategory == '04') {
                        $("#gltype").html('控股子公司')
                    } else if (selectedNodes.relCategory == '05') {
                        $("#gltype").html('控股自然人股东')
                    } else if (selectedNodes.relCategory == '06') {
                        $("#gltype").html('5%以上控股权的机构股东')
                    }
                    if (selectedNodes.relCategory == '07') {
                        $("#gltype").html('5%以上控股权的自然人股东')
                    } else if (selectedNodes.relCategory == '08') {
                        $("#gltype").html('5%以下控股权的机构股东')
                    } else if (selectedNodes.relCategory == '09') {
                        $("#gltype").html('5%以下控股权的自然人股东')
                    } else if (selectedNodes.relCategory == '10') {
                        $("#gltype").html('直接、间接控股5%以上的机构股东')
                    } else if (selectedNodes.relCategory == '11') {
                        $("#gltype").html('直接、间接控股5%以上的自然人股东')
                    } else if (selectedNodes.relCategory == '12') {
                        $("#gltype").html('直接、间接控股5%以下的机构股东')
                    }
                    if (selectedNodes.relCategory == '13') {
                        $("#gltype").html('直接、间接控股5%以下的自然人股东')
                    } else if (selectedNodes.relCategory == '14') {
                        $("#gltype").html('一致行动人（机构）')
                    } else if (selectedNodes.relCategory == '15') {
                        $("#gltype").html('一致行动人（自然人）')
                    } else if (selectedNodes.relCategory == '16') {
                        $("#gltype").html('控制的法人或其他组织')
                    } else if (selectedNodes.relCategory == '17') {
                        $("#gltype").html('控制或施加重大影响的机构')
                    } else if (selectedNodes.relCategory == '18') {
                        $("#gltype").html('董事')
                    }
                    if (selectedNodes.relCategory == '19') {
                        $("#gltype").html('监事')
                    } else if (selectedNodes.relCategory == '20') {
                        $("#gltype").html('高管')
                    } else if (selectedNodes.relCategory == '21') {
                        $("#gltype").html('其他管理人员')
                    } else if (selectedNodes.relCategory == '22') {

                        $("#gltype").html('亲属')
                        if (selectedNodes.relCateorySub == '01') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('父亲')
                        }
                        if (selectedNodes.relCateorySub == '02') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('儿子')
                        }
                        if (selectedNodes.relCateorySub == '03') {
                            $(".qs").css('visibility', 'visible')
                            $("#cantion").html('女儿')
                        } else {
                            $(".qs").css('visibility', 'hidden')
                        }

                    } else if (selectedNodes.relCategory == '23') {
                        $("#gltype").html('其他关联机构')
                    } else if (selectedNodes.relCategory == '24') {
                        $("#gltype").html('其他关联自然人')
                    }
                } else {
                    $("#gltype").html('')
                }

                if (selectedNodes.shareholdingStrand != null && selectedNodes.shareholdingStrand != undefined) {
                    $("#cgnum").html(selectedNodes.shareholdingStrand)
                } else {
                    $("#cgnum").html('')
                }
                if (selectedNodes.shareholdingRatio != null && selectedNodes.shareholdingRatio != undefined) {
                    $("#cgbl").html(selectedNodes.shareholdingRatio)
                } else {
                    $("#cgbl").html('')
                }
                console.log(selectedNodes)
                localStorage.fromPid = selectedNodes.pid;
                localStorage.fromType = selectedNodes.type;
                localStorage.fromRelCategory = selectedNodes.relCategory;
                console.log(localStorage.fromPid)
                localStorage.frompid = selectedNodes.pid;
                localStorage.fromtype = selectedNodes.type;
                localStorage.fromrelcategory = selectedNodes.relCategory;
                localStorage.identifytype =selectedNodes.identifyType
                localStorage.identifynumber =selectedNodes.identifyNumber
                localStorage.relpid =selectedNodes.relPid
                localStorage.relCategorysub = selectedNodes.relCategorySub
                if(selectedNodes.relCategory == 22){
                    localStorage.relCateorysub= selectedNodes.relCateorySub
                }else {
                    localStorage.relCateorysub =''
                }
                if(selectedNodes.shareholdersCode!=''&& selectedNodes.shareholdersCode!=undefined){
                    localStorage.shareholderscode =selectedNodes.shareholdersCode
                }else {
                    localStorage.shareholderscode = ''
                }
                if(selectedNodes.shareholdingRatio!=''&& selectedNodes.shareholdingRatio!=undefined){
                    localStorage.shareholdingratio =selectedNodes.shareholdingRatio
                }else {
                    localStorage.shareholdingratio = ''
                }
                if(selectedNodes.identifyNumber!=''&& selectedNodes.identifyNumber!=undefined){
                    localStorage.identifynumber =selectedNodes.identifyNumber
                }else {
                    localStorage.identifyNumber = ''
                }
                if(selectedNodes.shareholdingStrand!=''&& selectedNodes.shareholdingStrand!=undefined){
                    localStorage.shareholdingstrand =selectedNodes.shareholdingStrand
                }else {
                    localStorage.shareholdingstrand = ''
                }

                localStorage.names = selectedNodes.name
            }
        }

        window.checkTreeNode = function () {
            hideRMenu();
            var selectedNodes = zTree.getSelectedNodes()[0];
            var fromType = selectedNodes.type;
            var fromPid = selectedNodes.pid;
            if(fromType=='PSN' || fromType=='psn'){
                window.location.href='${base}/bus/relPsn/view/'+fromPid
            }else if(fromType=='ORG' || fromType=='org'){
                window.location.href='${base}/bus/relOrg/view/'+fromPid
            }else if(fromType=='SUSORG'){
                window.location.href='${base}/bus/relOrgSuspect/view/'+fromPid
            }else if(fromType=='SUSPSN'){
                window.location.href='${base}/bus/relPsnSuspect/view/'+fromPid
            }
        }

        function resetTree() {
            hideRMenu();
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }

        $("#glfname").blur(function () {
            var username = $(this).val();
            if (username != '' && username != undefined) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/bus/treeSuspect/selectForAffiliateMessage',
                    data: {
                        "name": username,
                        "shareCode": '',
                        "identifyType": '',
                        "identifyNumber": ''
                    },
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        $("#selectit").empty();
                        if (data.length > 0) {
                            if (data[0].name != '' && data[0].name != undefined) {
                                localStorage.name = data[0].name;
                            } else {
                                localStorage.name = ''
                            }
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                localStorage.shareholdersCode = data[0].shareholdersCode;
                            } else {
                                localStorage.shareholdersCode = ''
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                localStorage.identifyType = data[0].identifyType;
                            } else {
                                localStorage.identifyType = ''
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                localStorage.identifyNumber = data[0].identifyNumber;
                            } else {
                                localStorage.identifyNumber = ''
                            }
                            localStorage.toPid = data[0].pid
                            localStorage.toType = data[0].type
                            $("#glfname").val(data[0].name)
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                $("#codegd").val(data[0].shareholdersCode)
                            } else {
                                $("#codegd").val('')
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                $("#idcode").val(data[0].identifyNumber)
                            } else {
                                $("#idcode").val('')
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                if (data[0].identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                                    var str = "<option value=" + data[0].identifyType + ">统一社会信用代码</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'MILITARYID') {
                                    var str = "<option value=" + data[0].identifyType + ">军官证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PASSPORT') {
                                    var str = "<option value=" + data[0].identifyType + ">护照</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'IDCARD') {
                                    var str = "<option value=" + data[0].identifyType + ">居民身份证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PERMANENTRESIDENT') {
                                    var str = "<option value=" + data[0].identifyType + ">居民户口簿</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'OTHER') {
                                    var str = "<option value=" + data[0].identifyType + ">其他证件</option>";
                                    $("#selectit").append(str);
                                }
                            } else {
                                var str = "<option value='0'>无</option>";
                                $("#selectit").append(str);
                            }
                            $("#glfname").val(data[0].name)
                        } else {
                            alert('请添加关联方')
                            $("#idcode").val('')
                            $("#codegd").val('')
                            $("#glfname").val('')
                            // $("#selectit option[value='0']").attr("selected", true);
                            var str = "<option value='0'>无</option>";
                            $("#selectit").append(str);
                        }

                    }
                });
            }

        })
        $("#codegd").blur(function () {
            var codegd = $(this).val();
            if (codegd != '' && codegd != undefined) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/bus/treeSuspect/selectForAffiliateMessage',
                    data: {
                        "name": '',
                        "shareCode": codegd,
                        "identifyType": '',
                        "identifyNumber": ''
                    },
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        $("#selectit").empty();
                        if (data.length > 0) {
                            if (data[0].name != '' && data[0].name != undefined) {
                                localStorage.name = data[0].name;
                            } else {
                                localStorage.name = ''
                            }
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                localStorage.shareholdersCode = data[0].shareholdersCode;
                            } else {
                                localStorage.shareholdersCode = ''
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                localStorage.identifyType = data[0].identifyType;
                            } else {
                                localStorage.identifyType = ''
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                localStorage.identifyNumber = data[0].identifyNumber;
                            } else {
                                localStorage.identifyNumber = ''
                            }
                            localStorage.toPid = data[0].pid
                            localStorage.toType = data[0].type
                            $("#glfname").val(data[0].name)
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                $("#codegd").val(data[0].shareholdersCode)
                            } else {
                                $("#codegd").val('')
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                $("#idcode").val(data[0].identifyNumber)
                            } else {
                                $("#idcode").val('')
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                if (data[0].identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                                    var str = "<option value=" + data[0].identifyType + ">统一社会信用代码</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'MILITARYID') {
                                    var str = "<option value=" + data[0].identifyType + ">军官证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PASSPORT') {
                                    var str = "<option value=" + data[0].identifyType + ">护照</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'IDCARD') {
                                    var str = "<option value=" + data[0].identifyType + ">居民身份证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PERMANENTRESIDENT') {
                                    var str = "<option value=" + data[0].identifyType + ">居民户口簿</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'OTHER') {
                                    var str = "<option value=" + data[0].identifyType + ">其他证件</option>";
                                    $("#selectit").append(str);
                                }
                            } else {
                                var str = "<option value='0'>无</option>";
                                $("#selectit").append(str);
                            }
                            $("#glfname").val(data[0].name)
                        } else {
                            alert('请添加关联方')
                            $("#idcode").val('')
                            $("#codegd").val('')
                            $("#glfname").val('')
                            // $("#selectit option[value='0']").attr("selected", true);
                            var str = "<option value='0'>无</option>";
                            $("#selectit").append(str);
                        }
                    }
                });
            }

        })
        window.showit = function () {
            var idtype = document.getElementById("isshows");
            var value = idtype.value;
            console.log(value)
            if (value == 22) {
                $('#isshow').addClass('isshow').removeClass('noshow')
            } else {
                $('#isshow').removeClass('isshow').addClass('noshow')
            }

        }
        window.getcode = function () {
            var idtypes = document.getElementById("checkboxse");

            if (idtypes.checked) {
                $('#isshowof').removeClass('noshows')
            } else {
                $('#isshowof').addClass('noshows')
            }

        }
        $("#idcode").blur(function () {
            var idcode = $(this).val();

            if (idcode != '' && idcode != undefined) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/bus/treeSuspect/selectForAffiliateMessage',
                    data: {
                        "name": "",
                        "shareCode": '',
                        "identifyType": '',
                        "identifyNumber": idcode
                    },
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        $("#selectit").empty();
                        if (data.length > 0) {
                            if (data[0].name != '' && data[0].name != undefined) {
                                localStorage.name = data[0].name;
                            } else {
                                localStorage.name = ''
                            }
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                localStorage.shareholdersCode = data[0].shareholdersCode;
                            } else {
                                localStorage.shareholdersCode = ''
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                localStorage.identifyType = data[0].identifyType;
                            } else {
                                localStorage.identifyType = ''
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                localStorage.identifyNumber = data[0].identifyNumber;
                            } else {
                                localStorage.identifyNumber = ''
                            }
                            localStorage.toPid = data[0].pid
                            localStorage.toType = data[0].type
                            $("#glfname").val(data[0].name)
                            if (data[0].shareholdersCode != '' && data[0].shareholdersCode != undefined) {
                                $("#codegd").val(data[0].shareholdersCode)
                            } else {
                                $("#codegd").val('')
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                $("#idcode").val(data[0].identifyNumber)
                            } else {
                                $("#idcode").val('')
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                if (data[0].identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                                    var str = "<option value=" + data[0].identifyType + ">统一社会信用代码</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'MILITARYID') {
                                    var str = "<option value=" + data[0].identifyType + ">军官证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PASSPORT') {
                                    var str = "<option value=" + data[0].identifyType + ">护照</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'IDCARD') {
                                    var str = "<option value=" + data[0].identifyType + ">居民身份证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PERMANENTRESIDENT') {
                                    var str = "<option value=" + data[0].identifyType + ">居民户口簿</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'OTHER') {
                                    var str = "<option value=" + data[0].identifyType + ">其他证件</option>";
                                    $("#selectit").append(str);
                                }
                                // $("#selectit option[value='"+data[0].identifyType+"']").attr("selected", true);
                            } else {
                                var str = "<option value='0'>无</option>";
                                $("#selectit").append(str);
                            }
                            $("#glfname").val(data[0].name)
                        } else {
                            alert('请添加关联方')
                            $("#idcode").val('')
                            $("#codegd").val('')
                            $("#glfname").val('')
                            var str = "<option value='0'>无</option>";
                            $("#selectit").append(str);
                        }
                    }
                });
            }

        })
        //审批
        $(".sendcheck").on('click', function () {
            var fromPid = localStorage.fromPid;
            var fromType = localStorage.fromType;
            var toPid = localStorage.toPid
            var toType = localStorage.toType
            var fromRelCategory = localStorage.fromRelCategory
            var name = localStorage.name;
            var shareholdersCode = localStorage.shareholdersCode;
            var identifyType = localStorage.identifyType;
            var identifyNumber = localStorage.identifyNumber
            var idtype = document.getElementById("isshows");
            var toRelCategory = idtype.value;
            // console.log(toRelCategory)
            var qinshu = document.getElementById("qinshu");
            if (qinshu.value != undefined && qinshu.value != '') {
                var relCategorySub = qinshu.value;
            } else {
                var relCategorySub = ''
            }
            var checkboxse = document.getElementById("checkboxse");
            if (checkboxse.checked) {
                var shareholdingStrand = $('#nums').val()
                var shareholdingRatio = $('#rats').val()
            } else {
                var shareholdingStrand = ''
                var shareholdingRatio = ''
            }
            var cognizaReason = $('#textareas').val()
            var saveTreeMsgVo = {
                "fromPid": fromPid,
                "fromType": fromType,
                "toPid": toPid,
                "toType": toType,
                "fromRelCategory": fromRelCategory,
                "relCategorySub": relCategorySub,
                "shareholdingStrand": shareholdingStrand,
                "shareholdingRatio": shareholdingRatio,
                "toRelCategory": toRelCategory,
                "cognizaReason": cognizaReason
            }
            $.ajax({
                url: "${base}/bus/treeSuspect/saveRelMsg",
                type: "get",
                data: saveTreeMsgVo,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    // console.log(data.relPsnSuspect)
                    if (data.success == true) {
                        //保存成功
                        alert('保存成功！')
                    }else {
                        alert('保存失败！')
                    }
                }
            })
        })
        //删除
        $(".sendto").on('click', function () {
            var fromPid = localStorage.frompid;
            var fromType = localStorage.fromtype;
            var fromRelCategory = localStorage.fromrelcategory
            var shareholdersCode = localStorage.shareholderscode;
            var shareholdingRatio = localStorage.shareholdingratio;
            var identifyType = localStorage.identifytype;
            var identifyNumber = localStorage.identifynumber
            var relPid = localStorage.relpid
            var name = localStorage.names;
            var shareholdingStrand = localStorage.shareholdingstrand
            var relCategorySub = localStorage.relCategorysub
            var cognizaReason = $('#texta').val()
            var saveTreeMsgVo = {
                "fromPid": fromPid,
                "fromType": fromType,
                "toPid": '',
                "toType": '',
                "relPid":relPid,
                "fromRelCategory": fromRelCategory,
                "relCategorySub": relCategorySub,
                "shareholdingStrand": shareholdingStrand,
                "shareholdingRatio": shareholdingRatio,
                "toRelCategory": '',
                "cognizaReason": cognizaReason
            }
            $.ajax({
                url: "${base}/bus/treeSuspect/delRelMsg",
                type: "get",
                data: saveTreeMsgVo,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                    success: function (data) {
                    console.log(data.relPsnSuspect)
                    if (data.success == true) {
                        alert('删除成功')
                    }else {
                        alert('删除失败')
                    }
                }
            })

        })
    })


</script>
</body>
</html>