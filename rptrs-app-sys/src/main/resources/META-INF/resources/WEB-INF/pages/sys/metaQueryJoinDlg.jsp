<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>增加关联表</title>
    <style type="text/css">
        #joinIbox {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
        }

        .panel-body {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg" style="overflow: hidden">
<div class="row">
    <bc:ibox id="joinIbox" title="" col="12" collapsible="false">
        <bf:form id="joinForm" method="post" validation="true">
            <bf:formGroup>
                <bc:label text="主表" col="3" formControl="true"/>
                <bc:label text="" col="6" formControl="true" style="text-align:left" cls="masterTableId"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="关联表" col="3" formControl="true"/>
                <bf:lookup id="joinTableId" name="joinTableId"
                           url="${base}/sys/metaQuery/tableDlg" col="6" winId="winJoinTable"
                           propText="code" propValue="name"
                           value="" text=""
                           width="800" height="440" onSelect="selectJoinTable"
                           callback="result" title="选择关联表" beforeOpen="beforeOpen">
                    <bv:notEmpty/>
                </bf:lookup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="关联条件" col="3" formControl="true"/>
                <bf:text id="joinCons" name="joinCons" multiple="true" col="6" disabled="true"
                         rows="3"><bv:notEmpty/></bf:text>
                <bc:button id="joinConsBtn" icon="search-plus" tooltip="关联条件" size="xs" click="addJoinCondition()"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="关联方式" col="3" formControl="true"/>
                <bf:select id="joinType" name="joinType" col="6" dataSource="fixedDict" url="QueryJoinType">
                    <bv:notEmpty/>
                </bf:select>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button id="btnSave" type="primary" text="确定" click="btnSave()"/>
                    <bc:button id="btnBack" text="关闭">
                        <be:event>
                            $.messager.hide('winDataAnalysisJoin');
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
    var masterTable = ''; //主表
    var joinAlias = ''; //关联表别名
    var joinTable = ''; //关联表
    var joinCons = []; //关联条件
    var type = ''; //操作类型

    $(document).ready(function () {
        var args = $.messager.args('winDataAnalysisJoin');
        masterTable = args.masterTable;
        joinAlias = args.joinAlias;
        type = args.type;

        /*显示主表信息*/
        var masterTableShow = masterTable.name + '( ' + masterTable.code + ' )';
        $('.masterTableId').text(masterTableShow);

        if (type == 'edit') {
            /*关联表*/
            joinTable = args.joinTable;
            joinAlias = joinTable.alias;

            /*关联条件*/
            var joinTableType = args.joinTableType;
            joinCons = joinTableType.joinCondition;

            /*显示关联表*/
            $('#joinTableId').jLookup('setData', {code: joinTable.code, name: joinTable.name});

            /*关联条件显示*/
            setJoinCondition(joinCons);

            /*显示关联信息*/
            $('#joinType').val(joinTableType.joinType).trigger('change');
        }
    });

    /* 添加参数 */
    function beforeOpen() {
        var options = {toggle: "jLookup"};
        options['url'] = $.helper.curl(base, '/sys/metaQuery/tableDlg?joinAlias=' + joinAlias);
        $('#joinTableId').jLookup('reload', options);
    }

    /* lookup 选择关联表回调方法 */
    function selectJoinTable(table) {

        /*判断选择的是否和已选择的一样*/
        if ('' == joinTable || joinTable.id != table.id) {
            joinTable = table; //获取关联表
            joinCons = []; //清空关联条件
            $('#joinCons').val('');
        }
    }

    /*选择关联条件*/
    function addJoinCondition() {

        /* 判断是否已经选择关联表 */
        if (!joinTable) {
            $.messager.toastr('提示', '请先选择关联表!', 'warning');
            return;
        }

        $.messager.modal({
            winId: 'winMetaQueryJoinCons',
            width: 950,
            height: 560,
            title: '关联条件',
            url: '${base}/sys/metaQuery/joinCons',
            args: {callback: joinConCallBack, masterTable: [masterTable], joinTable: [joinTable], joinCons: joinCons}
        });
    }

    /*选择关联条件回调函数*/
    function joinConCallBack(data) {
        joinCons = data;
        setJoinCondition(data);
    }

    function setJoinCondition(data) {
        var displayJoinCons = '';
        $.each(data, function (i, v) {
            var tmp = masterTable.alias + '.' + v.masterColumn + ' = ' + joinAlias + '.' + v.joinColumn;

            if (displayJoinCons == '')
                displayJoinCons = tmp;
            else
                displayJoinCons = displayJoinCons + ', ' + tmp;
        });
        $('#joinCons').val(displayJoinCons);
    }

    /* 保存 */
    function btnSave() {
        $('#joinForm').data('formValidation').validate();
        var isValid = $('#joinForm').data('formValidation').isValid();
        if (isValid) {
            var joinType = $('#joinType').val();
            var joinTableType = {
                masterTable: masterTable.alias,
                joinTable: joinAlias,
                joinType: joinType,
                joinCondition: joinCons
            };

            $.messager.args('winDataAnalysisJoin').callback(joinTable, joinTableType);
            $.messager.hide('winDataAnalysisJoin');
        }
    }
</script>
</body>
</html>