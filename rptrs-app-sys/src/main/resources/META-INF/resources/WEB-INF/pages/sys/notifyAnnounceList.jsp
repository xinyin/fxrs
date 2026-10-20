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
                        <a href="${base}/sys/notify/announce" class="btn btn-block btn-primary compose-mail">发公告</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li><a onclick="outbox()"><i class="fa fa-envelope-o"></i> 发件箱</a></li>
                            <li>
                                <a onclick="draftbox()"><i class="fa fa-file-text-o"></i> 草稿箱
                                    <span class="label label-danger pull-right">${draftUnsend}</span>
                                </a>
                            </li>
                        </ul>
                        <a href="${base}/sys/notifyConfig/configDefault" class="btn btn-block btn-primary compose-mail">默认配置</a>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-10 animated fadeInRight">
            <div class="mail-box-header">
                <div class="input-group pull-right mail-search">
                    <bf:text id="searchText" name="search" placeholder="搜索"></bf:text>
                </div>
                <h2 id="h2"></h2>
                <div class="mail-tools m-t-md">
                    <div class="btn-group pull-right">
                        <bc:button cls="previous" icon="arrow-left" size="sm" click="previous()"/>
                        <bc:button cls="next" icon="arrow-right" size="sm" click="next()"/>
                    </div>
                    <div class="btn-group pull-left">
                        <div class="i-check-div">
                            <input type="checkbox" class="i-checks-all">
                        </div>
                        <bc:button cls="btnDeleteFlag" icon="trash-o" text="删除" size="xs" click="deleteFlag()"/>
                    </div>
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
                <div id="footer-mail-tools" class="mail-tools tooltip-demo m-t-md">
                    <div class="btn-group pull-right">
                        <bc:button cls="previous" icon="arrow-left" size="sm" click="previous()"/>
                        <bc:button cls="next" icon="arrow-right" size="sm" click="next()"/>
                    </div>
                    <div class="btn-group pull-left">
                        <div class="i-check-div">
                            <input type="checkbox" class="i-checks-all">
                        </div>
                        <bc:button cls="btnDeleteFlag" icon="trash-o" text="删除" size="xs" click="deleteFlag()"/>
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
    var folderType = '${folderType}'; //查看后返回list页面的文件夹类型。
    $(document).ready(function () {

        if (folderType == 'draftbox') {
            initOutbox(0, 'DRAFT', folderType, null); //初始化草稿箱数据
        } else {
            initOutbox(0, 'SEND', 'outbox', null); //初始化发件箱数据
        }

        /* checkbox样式初始化 */
        initCheck();

        /* 自动搜索 */
        $('#searchText').bind('input propertychange', function () {
            setTimeout(search, 500);
        });
    });

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

    /* 搜索 */
    function search() {
        var searchText = $('#searchText').val();

        var status = '';
        var folderType = $('#folderType').val();
        if (folderType == 'outbox') //发件箱列表
            status = 'SEND';
        else if (folderType == 'draftbox') //草稿箱列表
            status = 'DRAFT';

        initOutbox(0, status, folderType, searchText);
    }

    /* 表格工具条按钮 */
    function checkChange() {
        var flag = false;
        $.each($('.i-checks'), function (i, v) {

            if ($(this).is(":checked"))
                flag = true;
            else {
                flag = false;
                return false;
            }
        });
        if (flag) {
            $('.i-checks-all').prop("checked", "checked");
        } else {
            $('.i-checks-all').prop("checked", false);
        }
    }

    function deleteFlag() { //删除

        var folderType = $('#folderType').val();
        var ids = '';
        var checkedRow = $('.i-checks:checked');
        if (checkedRow.length > 0) {
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    $('.i-checks:checked').each(function () {
                        ids += $(this).val() + ',';
                    });

                    $.mask.post('${base}/sys/notify/remove', {ids: ids}, function (data) {
                        if (data.success) {
                            $.messager.toastr('提示', data.msg, 'info');

                            if (folderType == 'outbox') outbox();
                            else draftbox();
                        } else {
                            $.messager.toastr('错误', data.msg, 'error');
                        }
                    });
                }
            });

        } else {
            $.messager.toastr('提示', '没有选择记录');
        }
    }

    /* 发件箱 */
    function outbox() {
        $('#searchText').val(''); //清空搜索框
        initOutbox(0, 'SEND', 'outbox', null);

        $('.btnDeleteFlag').show();
    }

    /* 草稿箱 */
    function draftbox() {
        $('#searchText').val(''); //清空搜索框
        initOutbox(0, 'DRAFT', 'draftbox', null);

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
                __EQS_type: 'ANNOUNCE'
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

                        var viewUrl = '${base}/sys/notify/view/' + v.id + '?folderType=' + folderType + '&path=notify';
                        var html = '';
                        html += '<tr class="read">';
                        html += '<td class="check-mail">';
                        html += '<input type="checkbox" class="i-checks" value="' + v.id + '" onclick="checkChange()">';
                        html += '</td>';
                        if (v.status == 'SEND') {
                            html += '<td class="mail-contact"><a href="' + viewUrl + '">&nbsp;' + v.receiverName + '&nbsp;</a></td>';
                            html += '<td class="mail-subject"><a href="' + viewUrl + '">&nbsp;' + v.subject + '&nbsp;</a></td>';
                        } else {
                            var editAnnUrl = '${base}/sys/notify/announce?id=' + v.id + '&folderType=' + folderType;
                            html += '<td class="mail-contact"><a href="' + editAnnUrl + '">&nbsp;' + v.receiverName + '&nbsp;</a></td>';
                            html += '<td class="mail-subject"><a href="' + editAnnUrl + '">&nbsp;' + v.subject + '&nbsp;</a></td>';
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
        if (folderType == 'outbox') //发件箱列表
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