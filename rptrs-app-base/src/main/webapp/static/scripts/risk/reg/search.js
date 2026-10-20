var PAGE_SIZE = 20;
var PAGE_SHOW = 10;
var KEY_MAX = 20;
var SUB_MAX = 3;
var keywords;
var baseurl = "";

function query() {
    keywords = $('#keywords').val();
    queryResult(1);
}

function queryResult(page) {
    if (keywords == undefined || keywords == null || keywords == '') {
        return;
    }

    var subed = null;
    if (keywords.length > KEY_MAX) {
        subed = keywords.substring(KEY_MAX, keywords.length);
        keywords = keywords.substring(0, KEY_MAX);
    }

    /*请求前先将关键字编码以保留空格*/
    var encodeKeywords = encodeURIComponent(keywords);
    $.ajax({
        async: true,
        cache: false,
        type: "POST",
        url: contextPath + '/reg/publicLawSearch/fullTextQuery',
        data: {'keywords': encodeKeywords, 'page': page, 'rows': PAGE_SIZE},
        dataType: "json",
        /*在发送请求之前显示遮罩层*/
        beforeSend: function (jqXHR, settings) {
            $.mask.show('正在查询，请稍后。。。');
        },
        /*请求成功之后，移除遮罩层*/
        success: function (data) {
            $.mask.remove();
            if (subed != null) {
                var index = (subed.length >= SUB_MAX ? SUB_MAX : subed.length);
                var str = subed.substring(0, index);
                var html = "提示: \"" + str + "\"及其后面的字词均被忽略，因为全文检索限制在" + KEY_MAX + "个汉字以内";
                displayRemind(html);
                $('#keywords').val(keywords);
            } else {
                displayRemind(null);
            }
            displayResult(data);
        },
        /*发生错误之后也要移除*/
        error: function (jqXHR, status) {
            $.mask.remove();
        }
    });
}

/*显示提示消息*/
function displayRemind(msg) {
    $('#remind').empty();
    if (msg == null || msg == undefined || msg == '') return;
    var html = "<strong>" + msg + "</strong>";
    $(html).appendTo($('#remind'));
}

/*显示结果*/
function displayResult(data) {
    $('#result').empty();

    if (data.rows == null || data.rows == undefined || data.rows == '') {
        var msg = "抱歉，没有找到合适的结果，请修改关键字后，再尝试查询。";
        displayRemind(msg);
        return;
    }

    /*显示内容*/
    for (var i = 0; i < data.rows.length; i++) {
        var publicLawContent = data.rows[i];
        var html = '<div class="resultItem">';
        html += '<div class="resultTitle"><a href="javascript:void(0)" onclick="showLawDetail(\'' + publicLawContent.lawCode + '\');">《' + publicLawContent.parentLaw.lawName + '》</a></div>';
        if (publicLawContent.children != null && publicLawContent.children != undefined) {
            html += '<div>';
            for (var j = 0; j < publicLawContent.children.length; j++) {
                var publicLawItem = publicLawContent.children[j];
                //html += '<div class="resultBodyTitle"><a href="#">' + publicLawItem.itemSeq + '</a></div>';
                html += '<div class="resultBody">' + publicLawItem.itemContent + ' </div>';//&nbsp;<a href="javascript:void(0)" onclick="showLawDetail(\'' + publicLawItem.lawCode + '\', \'' + publicLawItem.lawItemId + '\');">查看</a></div>';
            }
            html += '</div>';
        }
        html += '<div>';
        $(html).appendTo($('#result'));
    }

    displayStat(data.total, data.time);
    displayPages(data.pages, data.pageno);

    /*将主DIV卷动到顶端*/
    document.getElementById('main').scrollTop = 0;

    /*更新自动更新内容(已取消，改为后台保存)*/
    /*请求前先将关键字编码以保留空格*/
    var encodeKeywords = encodeURIComponent(keywords);
    $.ajax({
        async: true,
        cache: false,
        type: "POST",
        url: baseurl + '/bas/autocomplete/updateAutocomplete/PUBLIC_LAW',
        data: {'keywords': encodeKeywords, 'totalCount': data.total},
        dataType: "json"
    });

}

/*显示分页*/
function displayPages(pages, pageno) {
    $('#page').empty();
    //var half = PAGE_SHOW / 2;
    var min = $.math.max(pageno - PAGE_SHOW + 1, 1);
    var max = $.math.min(pageno + PAGE_SHOW - 1, pages);
    //if(max - min < 10 && min > 1) min = $.math.max((pages - pageno) - min, 1);

    var html = '';
    if (pageno > 1) {
        html += '<a href="#" onclick="queryResult(' + (pageno - 1) + ');" class="n">上一页</a>';
    }
    for (var i = min; i <= max; i++) {
        if (i == pageno) {
            html += '<strong>' + i + '</strong>';
        } else {
            html += '<a href="#" onclick="queryResult(' + i + ')">' + i + '</a>';
        }
    }
    if (pageno < pages) {
        html += '<a href="#" onclick="queryResult(' + (pageno + 1) + ');" class="n">下一页</a>';
    }
    $(html).appendTo($('#page'));
}

/*显示统计*/
function displayStat(total, time) {
    var stat = '<span>找到相关法律法规' + total + '部, 用时' + time + '秒</span>';
    $('#stat').empty();
    $(stat).appendTo($('#stat'));
}

/*查看法规内容*/
function showLawDetail(lawCode, itemId) {
    if (window.top.$) {
        var path = baseurl + '/reg/regFrames/getPublicLawFrames?saved=yes&optype=view&closable=false&lawCode=' + lawCode + '&rand=' + Math.random(100000);
        if (itemId != undefined) {
            path += '&lawItemId=' + itemId;
        }

        var key = $.encoder.encode(keywords);
        path += '&flag=mark&queryKey=' + key;
        var tabId = '__publicLaw_' + lawCode;
        window.top.$('#tt').tabs('add', {
            id: tabId,
            title: '法律法规查看',
            content: '<iframe id="' + tabId + '" frameborder="0" scrolling="auto" style="z-index: 1;width:100%;height:100%; overflow: hidden;" src="' + path + '"/>',
            closable: true
        });
    }
}