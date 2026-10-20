<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        .filter-table .quick {
            margin-left: 0.5em;
            font-size: 0.8em;
            text-decoration: none;
        }

        .fitler-table .quick:hover {
            text-decoration: underline;
        }

        td.alt {
            background-color: #ffc;
            background-color: rgba(255, 255, 0, 0.2);
        }

        .btnAnnounce {
            background: white;
        }

        .btnAnnounce.active {
            background: #e7eaec;
        }

        .btn-group-view {
            padding-left: 10px;
        }

        #footerBtn {
            padding-bottom: 20px;
            padding-top: 20px;
            border: 1px solid #e7eaec;
            border-top: none;
        }

        .mail-box {
            margin-bottom: 0px;
        }

        .mail-box-header {
            padding-bottom: 15px;
        }

        .i-check-div {
            float: left;
            padding-top: 5px;
            padding-right: 10px;
        }

        .mail-text {
            border-top: none;
        }

        #footer-mail-tools {
            margin-top: 0px;
        }

        .view-btn-style {
            padding-right: 0px;
        }

        #searchText {
            height: 32px;
            border-radius: 3px;
        }

        #mailToolDiv {
            padding-top: 1px;
            border-top-width: 0px;
        }

        #folderUl a:HOVER {
            background-color: rgb(249, 249, 249);
        }
    </style>
    <title>消息中心</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-xs-2">
            <div class="ibox float-e-margins">
                <div class="ibox-content mailbox-content">
                    <div class="file-manager">
                        <a href="${base}/sys/notify/msg" class="btn btn-block btn-primary">发私信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul id="folderUl" class="folder-list m-b-md" style="padding: 0">
                            <li><a onclick="inbox()"><i class="fa fa-inbox "></i> 收件箱
                                <span id="inboxSpan" class="label label-warning pull-right">${inboxUnread}</span>
                            </a>
                            </li>
                            <li><a onclick="outbox()"><i class="fa fa-envelope-o"></i> 发件箱</a></li>
                            <li><a onclick="draftbox()"><i class="fa fa-file-text-o"></i> 草稿箱
                                <span id="draftboxSpan" class="label label-danger pull-right">${draftUnsend}</span>
                            </a>
                            </li>
                            <li><a onclick="trashbox()"><i class="fa fa-trash-o"></i> 垃圾箱</a></li>
                        </ul>
                        <h5>分类</h5>
                        <ul class="category-list" style="padding: 0">
                            <li><a href="#"> <i class="fa fa-bullhorn text-navy"></i> 系统公告 </a></li>
                            <li><a href="#"> <i class="fa fa-list text-danger"></i> 代办任务 </a></li>
                            <li><a href="#"> <i class="fa fa-envelope text-info"></i> 私信消息 </a></li>
                            <li><a href="#"> <i class="fa fa-bell text-warning"></i> 提醒信息 </a></li>
                        </ul>
                        <a href="${base}/sys/notifyConfig/config" class="btn btn-block btn-primary">配置</a>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-10 animated fadeInRight">
            <div class="mail-box-header">
                <bc:col col="6"><h2 id="h2"></h2></bc:col>
                <bc:col col="4" cls="view-btn-style">
                    <bc:buttonGroup cls="btn-group-view pull-right">
                        <bc:button cls="btnAnnounce" icon="bullhorn" tooltip="公告" size="xs" click="announceView()"/>
                        <bc:button cls="btnTodo" icon="list" tooltip="待办" size="xs" click="todoView()"/>
                        <bc:button cls="btnMessage" icon="envelope" tooltip="私信" size="xs" click="msgView()"/>
                        <bc:button cls="btnRemind" icon="bell" tooltip="提醒" size="xs" click="remindView()"/>
                        <bc:button cls="btnAll" icon="th-list" tooltip="全部" size="xs" click="allView()"/>
                    </bc:buttonGroup>
                </bc:col>
                <bf:text col="2" id="searchText" name="search" placeholder="搜索"/>
                <div class="clearfix"></div>
            </div>
            <div id="mailToolDiv" class="mail-box-header">
                <div class="btn-group pull-right">
                    <bc:button cls="previous" icon="arrow-left" size="xs" click="previous()"/>
                    <bc:button cls="next" icon="arrow-right" size="xs" click="next()"/>
                </div>
                <div class="btn-group pull-left">
                    <div class="i-check-div">
                        <input id="icheckUp" type="checkbox" class="i-checks-all">
                    </div>
                    <bc:button cls="btnRefresh" icon="refresh" text="刷新" size="xs" click="refresh()"/>
                    <bc:button cls="btnMarkAsRead" icon="eye" text="标记已读" size="xs" click="markAsRead()"/>
                    <bc:button cls="btnMarkAllAsRead" icon="check" text="全部标记已读" size="xs" click="markAllAsRead()"/>
                    <bc:button cls="btnMarkAsUnread" icon="eye-slash" text="标记未读" size="xs" click="markAsUnread()"/>
                    <bc:button cls="btnDeleteFlag" icon="trash-o" text="删除" size="xs" click="deleteFlag()"/>
                    <bc:button cls="btnDelCompletely" icon="trash" text="彻底删除" size="xs" click="deleteCompletely()"/>
                    <bc:button cls="btnRecovery" icon="undo" text="恢复" size="xs" click="recovery()"/>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="mail-box">
                <div class="mail-text h-300">
                    <table id="staffNotifyTable" class="table table-hover table-mail">
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <div id="footerBtn" class="mail-box-header">
                <div id="footer-mail-tools" class="mail-tools tooltip-demo m-t-xs">
                    <div class="btn-group pull-right">
                        <bc:button cls="previous" icon="arrow-left" size="xs" click="previous()"/>
                        <bc:button cls="next" icon="arrow-right" size="xs" click="next()"/>
                    </div>
                    <div class="btn-group pull-left">
                        <div class="i-check-div">
                            <input id="icheckDown" type="checkbox" class="i-checks-all">
                        </div>
                        <bc:button cls="btnRefresh" icon="refresh" text="刷新" size="xs" click="refresh()"/>
                        <bc:button cls="btnMarkAsRead" icon="eye" text="标记已读" size="xs" click="markAsRead()"/>
                        <bc:button cls="btnMarkAllAsRead" icon="check" text="全部标记已读" size="xs" click="markAllAsRead()"/>
                        <bc:button cls="btnMarkAsUnread" icon="eye-slash" text="标记未读" size="xs" click="markAsUnread()"/>
                        <bc:button cls="btnDeleteFlag" icon="trash-o" text="删除" size="xs" click="deleteFlag()"/>
                        <bc:button cls="btnDelCompletely" icon="trash" text="彻底删除" size="xs"
                                   click="deleteCompletely()"/>
                        <bc:button cls="btnRecovery" icon="undo" text="恢复" size="xs" click="recovery()"/>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var pageNumber = 0; //当前第几页
    var pages = 0; //总页数
    var initType = '${type}'; //搜索按钮
    var viewFolderType = '${folderType}'; //发件箱查看后返回标记
    var isRecord = false; //是否有匹配的数据

    $(document).ready(function () {

        /* 初始化消息中心数据 */
        if (viewFolderType == 'outbox') {
            outbox();
        } else if (viewFolderType == 'draftbox') {
            draftbox();
        } else if (viewFolderType == 'trashbox') {
            initTable(0, 'TRASH', 'trashbox', initType, null);
        } else {
            initTable(0, 'INBOX', 'inbox', initType, null);
        }

        initBtnActive(); //初始化按钮状态
        $('.btnRecovery').hide(); //隐藏垃圾箱的恢复工具按钮

        /* 自动搜索 */
        $('#searchText').bind('input propertychange', function () {
            setTimeout(search, 500);
        });

        $('#searchText').parent('div').attr('style', 'padding-right: 0px;');

        /* checkbox样式加载 */
        initCheck();
    });

    /* 搜索 */
    function search() {
        var searchText = $('#searchText').val();

        var type = '';
        if ($('.btnAnnounce').hasClass('active'))
            type = 'ANNOUNCE,';
        if ($('.btnTodo').hasClass('active'))
            type += 'TODO,';
        if ($('.btnMessage').hasClass('active'))
            type += 'MESSAGE,';
        if ($('.btnRemind').hasClass('active'))
            type += 'REMIND,';
        type = type.substring(0, type.length - 1);

        var status = '';
        var folderType = $('#folderType').val();
        if (folderType == 'inbox') //收件箱列表
            status = 'INBOX';
        else if (folderType == 'trashbox') //垃圾箱列表
            status = 'TRASH';
        else if (folderType == 'outbox') //发件箱列表
            status = 'SEND';
        else if (folderType == 'draftbox') //草稿箱列表
            status = 'DRAFT';

        if (folderType == 'inbox' || folderType == 'trashbox') {
            initTable(0, status, folderType, type, searchText);
        } else if (folderType == 'outbox' || folderType == 'draftbox') {
            initOutbox(0, status, folderType, searchText);
        }
    }

    function initCheck() {
        $('.i-checks-all').iCheck({ //checkbox样式初始化
            checkboxClass: 'icheckbox_square-green'
        });

        $('.i-checks-all').on('ifChecked', function (event) {
            $('.i-checks').iCheck('check');
            $('.i-checks-all').iCheck('check');
        });
        $('.i-checks-all').on('ifUnchecked', function (event) {
            $('.i-checks').iCheck('uncheck');
            $('.i-checks-all').iCheck('uncheck');
        });
    }

    /* 表格工具条按钮 */
    function checkChange() {

        var flag = false;
        $.each($('.i-checks'), function (i, v) {
            if ($(this).is(":checked")) {
                flag = true;
            } else {
                flag = false;
                return false;
            }
        });
        if (flag) $('.i-checks-all').iCheck('check');
        else $('.i-checks-all').iCheck('uncheck');
    }

    function refresh() { //刷新
        initByfolderType(0);
    }

    function markAsRead() { //标记为已读
        //update('markAsRead');

        var checkedRow = $('.i-checks:checked');
        if (checkedRow.length > 0) {
            var ids = '';
            $('.i-checks:checked').each(function () {
                console.log($(this));
                ids += $(this).val() + ',';
            });

            $.mask.post('${base}/sys/staffNotify/markAsRead', {ids: ids}, function (data) {
                if (data.success) {
                    $.messager.toastr('提示', data.msg, 'info');
                    initByfolderType(0);
                    synchBadges(); //更新【收件箱】【草稿箱】的数字角标
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });

        } else {
            $.messager.toastr('提示', '没有选择记录', 'info');
        }

    }

    function markAllAsRead() { //全部标记为已读

        if (isRecord) {
            $.mask.post('${base}/sys/staffNotify/markAllAsRead', function (data) {
                if (data.success) {
                    $.messager.toastr('提示', data.msg, 'info');
                    initByfolderType(0);
                    synchBadges(); //更新【收件箱】【草稿箱】的数字角标
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });

        } else {
            $.messager.toastr('提示', '没有匹配的记录', 'info');
        }
    }

    function markAsUnread() { //标记为未读
        update('markAsUnread');
    }

    function deleteFlag() { //删除
        var checkedRow = $('.i-checks:checked');
        if (checkedRow.length > 0) {
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    var folderType = $('#folderType').val();
                    var ids = '';
                    if (folderType == 'outbox') //发件箱中数据的删除
                    {
                        $('.i-checks:checked').each(function () {
                            ids += $(this).val() + ',';
                        });

                        $.mask.post('${base}/sys/notify/deleteFlag', {ids: ids}, function (data) {
                            if (data.success) {
                                $.messager.toastr('提示', data.msg, 'info');
                                outbox(); //重新加载发件箱页面
                            } else {
                                $.messager.toastr('错误', data.msg, 'error');
                            }
                        });
                    } else if (folderType == 'draftbox') //草稿箱中数据的删除
                    {
                        $('.i-checks:checked').each(function () {
                            ids += $(this).val() + ',';
                        });

                        $.mask.post('${base}/sys/notify/remove', {ids: ids}, function (data) {
                            if (data.success) {

                                $.messager.toastr('提示', data.msg, 'info');
                                draftbox(); //重新加载草稿箱页面
                                synchBadges(); //更新【收件箱】【草稿箱】的数字角标

                            } else {
                                $.messager.toastr('错误', data.msg, 'error');
                            }
                        });

                    } else {
                        update('deleteFlag'); //垃圾箱和收件箱数据的删除
                    }
                }
            });
        } else {
            $.messager.toastr('提示', '没有选择记录');
        }
    }

    function deleteCompletely() { //彻底删除
        var checkedRow = $('.i-checks:checked');
        if (checkedRow.length > 0) {
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    update('deleteCompletely');
                }
            });
        } else {
            $.messager.toastr('提示', '没有选择记录');
        }
    }

    function recovery() { //恢复
        update('recovery');
    }

    function update(method) {
        var checkedRow = $('.i-checks:checked');
        if (checkedRow.length > 0) {
            var ids = '';
            $('.i-checks:checked').each(function () {
                ids += $(this).val() + ',';
            });

            $.mask.post('${base}/sys/staffNotify/' + method, {ids: ids}, function (data) {
                if (data.success) {
                    $.messager.toastr('提示', data.msg, 'info');
                    initByfolderType(0);
                    synchBadges(); //更新【收件箱】【草稿箱】的数字角标
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });

        } else {
            $.messager.toastr('提示', '没有选择记录', 'info');
        }
    }

    function announceView() { //查看公告
        $('.btnAnnounce').toggleClass('active');
        queryDataByType();
    }

    function todoView() { //查看代办
        $('.btnTodo').toggleClass('active');
        queryDataByType();
    }

    function msgView() { //查看私信
        $('.btnMessage').toggleClass('active');
        queryDataByType();
    }

    function remindView() { //查看提醒
        $('.btnRemind').toggleClass('active');
        queryDataByType();
    }

    function allView() { //查看全部
        $('.btnAll').removeClass('active');
        $('.btnAnnounce').removeClass('active');
        $('.btnTodo').removeClass('active');
        $('.btnMessage').removeClass('active');
        $('.btnRemind').removeClass('active');

        $('#searchText').val(''); //清空搜索框
        var folderType = $('#folderType').val();
        if (folderType == 'inbox') //收件箱列表
        {
            initTable(0, 'INBOX', folderType, null, null);
        } else if (folderType == 'trashbox') //垃圾箱列表
        {
            initTable(0, 'TRASH', folderType, null, null);
        }
    }

    /* 改变【全部】按钮状态 */
    function isActive() {
        if (!$('.btnAnnounce').hasClass('active') ||
            !$('.btnTodo').hasClass('active') ||
            !$('.btnMessage').hasClass('active') ||
            !$('.btnRemind').hasClass('active')) {
            $('.btnAll').removeClass('active');
        } else {
            $('.btnAll').addClass('active');
        }
    }

    /* 初始化按钮状态 */
    function initBtnActive() {
        if ('MESSAGE' == initType)
            $('.btnMessage').addClass('active');
        else if ('TODO' == initType)
            $('.btnTodo').addClass('active');
        else if ('ANNOUNCE' == initType)
            $('.btnAnnounce').addClass('active');
        else if ('REMIND' == initType)
            $('.btnRemind').addClass('active');
    }

    /* 根据不同消息类型获取消息*/
    function queryDataByType() {

        isActive();

        var type = '';
        if ($('.btnAnnounce').hasClass('active'))
            type = 'ANNOUNCE,';
        if ($('.btnTodo').hasClass('active'))
            type += 'TODO,';
        if ($('.btnMessage').hasClass('active'))
            type += 'MESSAGE,';
        if ($('.btnRemind').hasClass('active'))
            type += 'REMIND,';

        type = type.substring(0, type.length - 1);

        $('#searchText').val(''); //清空搜索框
        var folderType = $('#folderType').val();
        if (folderType == 'inbox') //收件箱列表
        {
            initTable(0, 'INBOX', folderType, type, null);
        } else if (folderType == 'trashbox') //垃圾箱列表
        {
            initTable(0, 'TRASH', folderType, type, null);
        }
    }

    /* 清除消息类型查看按钮的选择状态 */
    function clearBtn() {
        $('.btnAnnounce').removeClass('active');
        $('.btnTodo').removeClass('active');
        $('.btnMessage').removeClass('active');
        $('.btnRemind').removeClass('active');
        $('.btnAll').removeClass('active');
    }

    /* 收件箱 */
    function inbox() {
        $('#searchText').val(''); //清空搜索框
        initTable(0, 'INBOX', 'inbox', null, null);
        $('.btnRefresh').show();
        $('.btnMarkAsRead').show();
        $('.btnMarkAllAsRead').show();
        $('.btnMarkAsUnread').show();
        $('.btnDeleteFlag').show();
        $('.btnDelCompletely').show();
        $('.btnAnnounce').show();
        $('.btnTodo').show();
        $('.btnMessage').show();
        $('.btnRemind').show();
        $('.btnAll').show();
        $('.btnRecovery').hide();
        clearBtn();
    }

    /* 垃圾箱 */
    function trashbox() {
        $('#searchText').val(''); //清空搜索框
        initTable(0, 'TRASH', 'trashbox', null, null);

        $('.btnRefresh').hide();
        $('.btnMarkAsRead').hide();
        $('.btnMarkAllAsRead').hide();
        $('.btnMarkAsUnread').hide();
        $('.btnDeleteFlag').hide();

        $('.btnDelCompletely').show();
        $('.btnAnnounce').show();
        $('.btnTodo').show();
        $('.btnMessage').show();
        $('.btnRemind').show();
        $('.btnAll').show();
        $('.btnRecovery').show();
        clearBtn();
    }

    /* 初始化表格列表
     * pageNo: 当前第几页
     * status: 状态：inbox、trash
     * folderType：当前列表为：收件箱(inbox)、或垃圾箱(trash)
     */
    function initTable(pageNo, status, folderType, type, searchText) {
        $("#staffNotifyTable tbody").empty(); //清空表格列表
        if (folderType == 'inbox') $('#h2').text('收件箱');
        else $('#h2').text('垃圾箱');
        $('.i-checks-all').iCheck('uncheck'); //清除选中的checkbox

        $.ajax({
            type: 'GET',
            url: '${base}/sys/staffNotify/queryNotifyByStaffCode',
            data: {
                pageNumber: pageNo,
                __EQS_status: status,
                __INS_type: type,
                __EQS_searchText: searchText
            },
            dataType: 'json',
            success: function (data) {

                pageNumber = data.pageNumber;
                pages = data.pages;
                paginationChange(pages, pageNo);

                var html = '';
                html += '<tr><input id="folderType" value="' + folderType + '" type="hidden"></tr>';
                $("#staffNotifyTable tbody").append(html);

                var rows = data.rows;
                if (rows.length == 0) {
                    var html = '';
                    html += '<tr class="no-records-found">';
                    html += '<td class="text-center">没有找到匹配的记录</td>';
                    html += '<td class="check-mail">';
                    html += '</tr>';
                    $("#staffNotifyTable tbody").append(html);
                    isRecord = false;
                } else {
                    $.each(rows, function (i, v) {
                        var viewUrl = '${base}/sys/staffNotify/view/' + v.staffNotifyId + '?folderType=' + folderType;

                        var html = '';
                        if (v.isRead == 'Y') {
                            html += '<tr class="read">';
                        } else {
                            html += '<tr class="unread">';
                        }

                        html += '<td class="check-mail">';
                        html += '<input type="checkbox" class="i-checks" value="' + v.staffNotifyId + '">';
                        html += '</td>';
                        html += '<td class="mail-contact"><a href="' + viewUrl + '">' + v.senderName + '</a>';

                        if (v.type == 'ANNOUNCE') {
                            html += '<i class="fa fa-bullhorn text-navy pull-right"></i>';
                        } else if (v.type == 'TODO') {
                            html += '<i class="fa fa-list text-danger pull-right"></i>';
                        } else if (v.type == 'REMIND') {
                            html += '<i class="fa fa-bell text-warning pull-right"></i>';
                        } else if (v.type == 'MESSAGE') {
                            html += '<i class="fa fa-envelope text-info pull-right"></i>';
                        }

                        html += '</td>';
                        html += '<td class="mail-subject"><a href="' + viewUrl + '">' + v.subject + '</a></td>';
                        html += '<td class="">';

                        if (v.isUpload == 'Y') {
                            html += '<i class="fa fa-paperclip">';
                        } else {
                            html += '';
                        }

                        html += '</i></td>';
                        html += '<td class="text-right mail-date">' + computeTimeDiff(v.createTime, v.createTimeString) + '</td>';
                        html += '</tr>';

                        $("#staffNotifyTable tbody").append(html);
                    });

                    $('.i-checks').iCheck({ //checkbox样式初始化
                        checkboxClass: 'icheckbox_square-green'
                    });

                    isRecord = true;
                }
            }
        });
    }

    /* 同步收件箱、草稿箱上的角标数字 */
    function synchBadges() {
        $.ajax({
            type: 'GET',
            url: '${base}/sys/staffNotify/synch',
            dataType: 'json',
            success: function (data) {
                $('#inboxSpan').text(data.inboxUnread);
                $('#draftboxSpan').text(data.draftUnsend);
            }
        });
    }

    /* 发件箱 */
    function outbox() {
        $('#searchText').val(''); //清空搜索框
        initOutbox(0, 'SEND', 'outbox', null);

        $('.btnRefresh').hide();
        $('.btnMarkAsRead').hide();
        $('.btnMarkAllAsRead').hide();
        $('.btnMarkAsUnread').hide();
        $('.btnDelCompletely').hide();
        $('.btnAnnounce').hide();
        $('.btnTodo').hide();
        $('.btnMessage').hide();
        $('.btnRemind').hide();
        $('.btnAll').hide();
        $('.btnRecovery').hide();

        $('.btnDeleteFlag').show();
    }

    /* 草稿箱 */
    function draftbox() {
        $('#searchText').val(''); //清空搜索框
        initOutbox(0, 'DRAFT', 'draftbox', null);

        $('.btnRefresh').hide();
        $('.btnMarkAsRead').hide();
        $('.btnMarkAllAsRead').hide();
        $('.btnMarkAsUnread').hide();
        $('.btnDelCompletely').hide();
        $('.btnAnnounce').hide();
        $('.btnTodo').hide();
        $('.btnMessage').hide();
        $('.btnRemind').hide();
        $('.btnAll').hide();
        $('.btnRecovery').hide();

        $('.btnDeleteFlag').show();
    }

    /* 初始化表格列表
     * pageNo: 当前第几页
     * status: 状态：SEND、DRAFT
     * folderType：当前列表为：发件箱(outbox)、草稿箱(draft)
     */
    function initOutbox(pageNo, status, folderType, searchText) {
        $("#staffNotifyTable tbody").empty();
        if (folderType == 'outbox') $('#h2').text('发件箱');
        else $('#h2').text('草稿箱');
        $('.i-checks-all').iCheck('uncheck'); //清除选中的checkbox

        $.ajax({
            type: 'GET',
            url: '${base}/sys/notify/queryNotify',
            data: {
                pageNumber: pageNo,
                __EQS_status: status,
                __EQS_searchText: searchText,
                __EQS_type: 'MESSAGE'
            },
            dataType: 'json',
            success: function (data) {

                pageNumber = data.pageNumber;
                pages = data.pages;
                paginationChange(pages, pageNo)

                var html = '';
                html += '<tr><input id="folderType" value="' + folderType + '" type="hidden"></tr>';
                $("#staffNotifyTable tbody").append(html);

                var rows = data.rows;
                if (rows.length == 0) {
                    var html = '';
                    html += '<tr class="no-records-found">';
                    html += '<td class="text-center">没有找到匹配的记录</td>';
                    html += '<td class="check-mail">';
                    html += '</tr>';
                    $("#staffNotifyTable tbody").append(html);
                } else {
                    $.each(rows, function (i, v) {

                        var html = '';
                        html += '<tr class="read">';
                        html += '<td class="check-mail">';
                        html += '<input type="checkbox" class="i-checks" value="' + v.id + '">';
                        html += '</td>';

                        if (v.status == 'SEND') {
                            var viewUrl = '${base}/sys/notify/view/' + v.id + '?folderType=' + folderType + '&path=staffNotify';
                            html += '<td class="mail-contact"><a href="' + viewUrl + '">' + v.receiverName + '</a></td>';
                            html += '<td class="mail-subject"><a href="' + viewUrl + '">' + v.subject + '</a></td>';
                        } else {
                            var editMsgUrl = '${base}/sys/notify/msg?id=' + v.id + '&folderType=' + folderType;
                            html += '<td class="mail-contact"><a href="' + editMsgUrl + '">&nbsp;' + v.receiverName + '&nbsp;</a></td>';
                            html += '<td class="mail-subject"><a href="' + editMsgUrl + '">&nbsp;' + v.subject + '&nbsp;</a></td>';
                        }

                        html += '<td class="">';

                        if (v.isUpload == 'Y') {
                            html += '<i class="fa fa-paperclip">';
                        } else {
                            html += '';
                        }

                        html += '</i></td>';
                        html += '<td class="text-right mail-date">' + computeTimeDiff(v.sendTime, v.sendTimeString) + '</td>';
                        html += '</tr>';

                        $("#staffNotifyTable tbody").append(html);
                    });
                    $('.i-checks').iCheck({ //checkbox样式初始化
                        checkboxClass: 'icheckbox_square-green'
                    });
                }
            }
        });
    }

    /* 上一页 */
    function previous() {
        initByfolderType(pageNumber - 1);
    }

    /* 下一页 */
    function next() {
        initByfolderType(pageNumber + 1);
    }

    /* 翻页按钮禁用启用的改变，pages: 列表总页数，pageNo: 当前第几 页  */
    function paginationChange(pages, pageNo) {

        if (pages <= 1) //没有数据，或有且只有一页
        {
            $('.previous').attr('disabled', true);
            $('.next').attr('disabled', true);
        } else if (pages == 2) //只有两页
        {
            if (pageNo <= 1) {
                $('.previous').attr('disabled', true);
                $('.next').removeAttr('disabled');
            } else {
                $('.previous').removeAttr('disabled');
                $('.next').attr('disabled', true);
            }
        } else if (pages >= 3) //3页及以上
        {
            if (pageNo <= 1) {
                $('.previous').attr('disabled', true);
                $('.next').removeAttr('disabled');
            } else if (1 < pageNo && pageNo < pages) {
                $('.previous').removeAttr('disabled');
                $('.next').removeAttr('disabled', true);
            } else {
                $('.previous').removeAttr('disabled');
                $('.next').attr('disabled', true);
            }
        }
    }

    function initByfolderType(pageNo) {

        $('#searchText').val(''); //清空搜索框
        var folderType = $('#folderType').val();
        if (folderType == 'inbox') //收件箱列表
        {
            initTable(pageNo, 'INBOX', folderType, null, null);
        } else if (folderType == 'trashbox') //垃圾箱列表
        {
            initTable(pageNo, 'TRASH', folderType, null, null);
        } else if (folderType == 'outbox') //发件箱列表
        {
            initOutbox(pageNo, 'SEND', folderType, null);
        } else if (folderType == 'draftbox') //草稿箱列表
        {
            initOutbox(pageNo, 'DRAFT', folderType, null);
        }
    }

    /* 计算时间差 */
    function computeTimeDiff(createTime, createTimeString) {
        var date = new Date();
        var time = date.getTime() - createTime; //时间差的毫秒数（时间戳）

        //计算相差天数
        var days = Math.floor(time / (24 * 3600 * 1000));

        //计算相差小时
        var modulus = time % (24 * 3600 * 1000);  //计算天数后剩余的时间戳
        var hours = Math.floor(modulus / (3600 * 1000));

        //计算相差分钟数
        var modulusMin = modulus % (3600 * 1000); //计算小时数后剩余的时间戳
        var minutes = Math.floor(modulusMin / (60 * 1000));

        //计算相差秒数
        var modulusSec = modulusMin % (60 * 1000);      //计算分钟数后剩余的时间戳
        var seconds = Math.round(modulusSec / 1000);

        var timeString = '';
        if (days != 0 && days < 2) {
            timeString = '昨天';
        } else if (days != 0 && days == 2) {
            timeString = days + '天前';
        } else if (days == 0 && hours != 0) {
            timeString = hours + '小时前';
        } else if (days == 0 && minutes > 1) {
            timeString = minutes + '分钟前';
        } else if (days == 0 && minutes <= 1) {
            timeString = '刚刚';
        } else {
            timeString = createTimeString;
        }

        return timeString
    }

</script>
</body>
</html>