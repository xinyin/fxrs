/*
 * 待办任务插件
 */
jQuery.todo = {
    render: function (target, url, e) {
        $(target).empty();
        $.get(url, {}, function (data) {
            var elms = [];
            $.each(data, function (idx, tasks) {
                elms.push('<ol id="' + idx + '">');
                if (tasks && tasks.length > 0) {
                    elms.push('<li class="tskttle">【' + tasks[0].categoryName + '】</li>');
                }
                $(tasks).each(function () {
                    elms.push('<li style="padding-left:20px;padding-top:2px;"><a href="#" url=' + this.url + '" id="' + this.code + '">' + this.topic + '</a><span>(' + this.count + ')</span></li>');
                });
                elms.push('</ol>');
            });
            $(target).html(elms.join(''));
            $(e).show();
        });
        $(target + ">ol>li>a").live('click', function () {
            openMainTabs($(this).attr('id'), $(this).html(), $(this).attr('url'), false);
        });
    }
};