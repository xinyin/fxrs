/*
 * 生成附件上传下载功能页面；
 * 说明：
 * 有上传下载文件功能的页面引入js文件<script src="${ctx}/scripts/attachment.js" type="text/javascript"></script>
 * 新增、修改记录时（有上传功能）：调用js方法 initAttachment("attachmentDiv",2,'',true);
 * 保存记录成功回调中调用方法：attachmentSave(data.id)；
 * 查看记录时(下载功能)：调用js方法 initAttachment("attachmentDiv",2,'',false);
 * 具体示例请查看文件eventEffectEdit.jsp文件注释部分，类EventEffectController.java中save()方法注释部分返回记录ID方法。
 * 
 * divId附件功能所在页面div的ID；
 * attachTypeCode附件类型编码；
 * entityId业务ID，修改记录时提供；
 * event: E_UP | E_DOWN
 */
var E_NONE = 0;
var E_UP = 1;
var E_DOWN = 2;
var E_VIEW = 4;
var pageNo = 0;
var TYPE_OFFICE = "doc,docx,xls,xlsx,ppt,pptx,vsd";
var TYPE_IMAGE = "gif,jpg,jpeg,bmp,png";
var TYPE_TEXT = "txt,html,htm,ini";


function initAttachment(divId, attachTypeCode, entityId, event) {
    var strPath = window.top.location.pathname;
    var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
    var iframe = '<iframe id="ifr_attachment" name="ifr_attachment" src="' + contextPath + '/attach/toAttachment?attachTypeCode=' + attachTypeCode;
    if (entityId) {
        iframe += '&entityId=' + entityId;
    }
    if (event) {
        iframe += '&event=' + event;
    }
    iframe += '" frameborder="0" width="100%" height="97%"></iframe>';
    $(iframe).appendTo($("#" + divId));
}

function initAttachmentModal(divId, attachTypeCode, entityId, event) {
    var strPath = window.top.location.pathname;
    var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
    var iframe = '<iframe id="ifr_attachment" name="ifr_attachment" src="/' + postPath + '/attach/toAttachment?attachTypeCode=' + attachTypeCode;
    if (entityId) {
        iframe += '&entityId=' + entityId;
    }
    if (event == true || event == "true") {
        iframe += '&event=upload';
    }
    iframe += '" frameborder="0" style="width:100%;height:100%;"></iframe>';
    $(iframe).appendTo($("#" + divId));
}

/*
 * 保存记录成功后保存业务id到附件表中
 */
function attachmentSave(entityId) {
    window.frames["ifr_attachment"].btnSubmit(entityId);
}

function isOffice(ext) {
    return TYPE_OFFICE.indexOf(ext.toLowerCase()) >= 0;
}

function isImage(ext) {
    return TYPE_IMAGE.indexOf(ext.toLowerCase()) >= 0;
}

function isText(ext) {
    return TYPE_TEXT.indexOf(ext.toLowerCase()) >= 0;
}

/*
 * 浏览附件，根据类型选用不同的打开方式
 */
function viewAttach(id, name, ext, prefix) {
    if (isImage(ext)) {
        $.imageViewer.show({
            url: contextPath + '/attach/viewImage?id=' + id,
            title: '查看图片 - ' + name + '.' + ext
        });
    } else if (isText(ext)) {
        $.imageViewer.show({
            url: contextPath + '/attach/viewText?id=' + id,
            title: '查看文件  - ' + name + '.' + ext
        });
    } else if (isOffice(ext)) {
        if (!prefix) prefix = 'viewAttach';
        var flag = E_DOWN | E_VIEW;
        open(contextPath + '/attach/view?id=' + id + '&flag=' + flag, prefix + $.math.random(100000, 999999), 'width=' + (screen.availWidth - 10) + ',height=' + (screen.availHeight - 50) + ',resizable=yes');
    } else {
        $.messager.alert('提示', '无法查看的附件类型', 'info');
    }
}


function canView(ext) {
    return isImage(ext) || isText(ext) || isOffice(ext);
}


/**
 * 渲染某个div，显示附件查看及下载链接
 */
function renderAttachView(htmlId, entityId, attachTypeCode) {
    var fu = $('#' + htmlId);
    var params = {
        entityId: entityId,
        attachTypeCode: attachTypeCode
    };
    $.post(contextPath + '/attach/getAttachmentByEntityIdAndType', params, function (data) {
        if (data && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                if (canView(data[i].fileFormat)) {
                    $('<div style="padding:2px;"><a href="javascript:void(0);"  class="titlelink" onclick="viewAttach(\'' + data[i].id + '\', \'' + data[i].srcFileName + '\', \'' + data[i].fileFormat + '\')">' + data[i].srcFileName + '.' + data[i].fileFormat + '</a>&nbsp;&nbsp;&nbsp;<a class="titlelink"  href="' + contextPath + '/attach/download?id=' + data[i].id + '" style="color:blue;">下载</a></div>').appendTo(fu);
                } else {
                    $('<div style="padding:2px;">' + data[i].srcFileName + '.' + data[i].fileFormat + '&nbsp;&nbsp;&nbsp;<a class="titlelink"  href="' + contextPath + '/attach/download?id=' + data[i].id + '" style="color:blue;">下载</a></div>').appendTo(fu);
                }
            }
        } else {
            fu.html('<div class ="preview">无</div>');
        }
    });
}

/**
 * 渲染某个div，显示附件查看
 */
function renderAttachLook(htmlId, entityId, attachTypeCode) {
    var fu = $('#' + htmlId);
    var params = {
        entityId: entityId,
        attachTypeCode: attachTypeCode
    };
    $.post(contextPath + '/attach/getAttachmentByEntityIdAndType', params, function (data) {
        if (data && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                if (canView(data[i].fileFormat)) {
                    $('<div style="padding:2px;"><a href="javascript:void(0);"  class="titlelink" onclick="viewAttach(\'' + data[i].id + '\', \'' + data[i].srcFileName + '\', \'' + data[i].fileFormat + '\')">' + data[i].srcFileName + '.' + data[i].fileFormat + '</a></div>').appendTo(fu);
                } else {
                    $('<div style="padding:2px;">' + data[i].srcFileName + '.' + data[i].fileFormat + '&nbsp;&nbsp;&nbsp;<a class="titlelink"  href="' + contextPath + '/attach/download?id=' + data[i].id + '" style="color:blue;">下载</a></div>').appendTo(fu);
                }
            }
        } else {
            fu.html('<div class ="preview">无</div>');
        }
    });
}