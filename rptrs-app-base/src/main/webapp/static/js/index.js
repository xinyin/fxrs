function initMenu(data) {
    //循环获取导航菜单
    $.each(data, function (i, v) {
        //判断 parentId 为空的则是顶级
        if (!v.parentId) {
            menuCode = v.menuCode.replace(/\./g, '_');
            var menuImage = v.menuImage ? v.menuImage : "fa fa-align-justify";
            var html = '<li><a href="#"><i class="' + menuImage + '" style="margin-right:10px;"></i><span class="nav-label">' + v.menuName + '</span></a><ul id="sys_menu_' + menuCode + '" class="nav nav-second-level collapse"></ul></li>';
            $('#side-menu').append(html);

            generateMenu(data, v.menuCode);
        }
    });

    initMetisMenu();
}

/* 生成目录结构 */
function generateMenu(data, parentId) {
    //循环获取导航菜单
    $.each(data, function (i, v) {
        if (v.parentId) {
            var parent = v.parentId.replace(/\./g, '_');
            var menuCode = v.menuCode.replace(/\./g, '_');
            parentId = parentId.replace(/\./g, '_');
            var menuImage = v.menuImage ? v.menuImage : "fa fa-align-justify";

            //是否是当前传入对象的下一级
            if (parent == parentId) {
                //获取二级菜单及倒数第二层菜单
                if (v.url == '#') {
                    var html = '<li><a href="#"><i class="' + menuImage + '" style="margin-right:10px;"></i>' + v.menuName + '</a><ul id="sys_menu_' + menuCode + '" class="nav nav-third-level"></ul></li>';
                    $('#sys_menu_' + parentId).append(html);
                    generateMenu(data, v.menuCode);
                } else {
                    var pathTitle = getPathTitle(data, v.menuCode);
                    var html = '<li><a href="javaScript: void(0);" onClick="onClick(\'' + base + '/' + v.url + '\',\'' + v.menuCode + '\',\'' + pathTitle + '\',\'' + v.menuName + '\');"><i class="' + menuImage + '" style="margin-right:10px;"></i>' + v.menuName + '</a></li>';
                    $('#sys_menu_' + parentId).append(html);
                }
            }
        }
    });
}

//循环获取当前级别以上的所以级别名称
function getPathTitle(data, parentId) {
    var pathTitle = '';
    $.each(data, function (i, v) {
        var menuCode = v.menuCode.replace(/\./g, '_');
        parentId = parentId.replace(/\./g, '_');
        if (menuCode == parentId) {
            pathTitle += v.menuCode + '_' + v.menuName + ',';
            if (v.parentId) {
                pathTitle += getPathTitle(data, v.parentId)
            }
        }
    });

    //去掉最后一个逗号
    //pathTitle = pathTitle.substring(0, pathTitle.length-1);
    return pathTitle;
}

//点击事件
function onClick(url, code, pathTitle, title) {
    if (isIndexTab) {
        if (code) {
            code = code.replace(/\./g, '_');
        } else {
            code = "default";
        }
        openTab(code, url, title, false);
    } else {
        //改变iframe的src
        $('#content').attr('src', url);
        pathTitles = pathTitle;
        var data = pathTitle.split(',');
        $('#bread').empty();
        //改变面包屑导航
        $('#bread').append('<li><a href="javaScript: void(0);" onClick="navIndexMenuClick(\'' + base + '/welcome\')">首页</a></li>');
        for (var i = data.length - 1; i > -1; i--) {
            if (data[i]) {
                var node = data[i].split('_');
                var html = '<li><a href="javaScript: void(0);" onClick="navMenuClick(\'' + data[i] + '\')">' + node[1] + '</a></li>';
                //i == 0 循环的是最底层
                if (i == 0) {
                    html = '<li class="active">' + node[1] + '</li>';
                }
                $('#bread').append(html);
            }
        }
    }
}

//打开首页
function navIndexMenuClick(url) {

    //改变iframe的src
    $('#content').attr('src', url);
    $('#bread').empty();
    $('#bread').append('<li><a href="javaScript: void(0);" onClick="navIndexMenuClick(\'' + base + '/welcome\')">首页</a></li>');
}

//面包屑导航点击事件
function navMenuClick(node) {
    var title = node.split('_');
    var url = base + '/sys/menu/navMenu?menuCode=' + title[0];

    //改变iframe的src
    $('#content').attr('src', url);

    //去掉当前点击节点的下级节点
    var index = pathTitles.indexOf(title[0] + '_');
    pathTitles = pathTitles.substr(index);

    //改变面包屑导航
    var data = pathTitles.split(',');
    $('#bread').empty();
    $('#bread').append('<li><a href="javaScript: void(0);" onClick="navIndexMenuClick(\'' + base + '/welcome\')">首页</a></li>');
    for (var i = data.length - 1; i > -1; i--) {
        if (data[i]) {
            var node = data[i].split('_');
            var html = '<li><a href="javaScript: void(0);" onClick="navMenuClick(\'' + data[i] + '\')">' + node[1] + '</a></li>';
            //i == 0 循环的是最底层
            if (i == 0) {
                html = '<li class="active"><a href="javaScript: void(0);" onClick="navMenuClick(\'' + data[i] + '\')">' + node[1] + '</a></li>';
            }

            $('#bread').append(html);
        }
    }
}

//提供子页面点击事件
function setNavMenu(code, name, url) {
    //增加当前节点到面包屑
    var node = code + '_' + name;
    pathTitles = node + ',' + pathTitles;

    //判断当前url 为 '#', 则还有子节点
    if (url == '#') {
        var html = '<li><a href="javaScript: void(0);" onClick="navMenuClick(\'' + node + '\')">' + name + '</a></li>';
        $('#bread').append(html);

        var url = base + '/sys/menu/navMenu?menuCode=' + code;
        //改变iframe的src
        $('#content').attr('src', url);
    } else {
        var $url = $.helper.curl(base + '/', url);
        //改变iframe的src
        $('#content').attr('src', $url);

        var html = '<li class="active">' + name + '</li>';
        $('#bread').append(html);
    }
}

/* 初始化获取通知 */
function initNotify() {

    $('#notifYUL').html('');

    $.ajax({
        type: 'GET',
        url: base + '/sys/notify/countNotifyByType',
        dataType: 'json',
        success: function (data) {
            if (data.login == 'Y') {
                var style = 'style="border-bottom: 1px solid #e8e8e8; padding-bottom: 10px;"'
                var envelopeNum = '';
                var html = '';
                if (data.todo != 0) {
                    html += '<li><div class="dropdown-messages-box" ' + style + '><div class="text-left media-body pointer">';
                    html += '<small class="text-muted" onclick="winLocation(\'TODO\')">';
                    html += '<i class="fa fa-list" style="padding-right: 10px;"></i> 您有 ' + data.todo + ' 封代办未处理。 </small>';
                    html += '</div></div></li>';
                }
                if (data.message != 0) {
                    html += '<li><div class="dropdown-messages-box" ' + style + '><div class="text-left media-body pointer">';
                    html += '<small class="text-muted" onclick="winLocation(\'MESSAGE\')">';
                    html += '<i class="fa fa-envelope" style="padding-right: 10px;"></i> 您有 ' + data.message + ' 封新私信消息。';
                    html += '</small></div></div></li>';
                }
                if (data.announce != 0) {
                    html += '<li><div class="dropdown-messages-box" ' + style + '><div class="text-left media-body pointer">';
                    html += '<small class="text-muted" onclick="winLocation(\'ANNOUNCE\')">';
                    html += '<i class="fa fa-bullhorn" style="padding-right: 10px;"></i> 您有 ' + data.announce + ' 封新公告消息。';
                    html += '</small></div></div></li>';
                }
                if (data.remind != 0) {
                    html += '<li><div class="dropdown-messages-box" ' + style + '><div class="text-left media-body pointer">';
                    html += '<small class="text-muted" onclick="winLocation(\'REMIND\')">';
                    html += '<i class="fa fa-bell" style="padding-right: 10px;"></i> 您有 ' + data.remind + ' 条提醒信息。 </small>';
                    html += '</div></div></li>';
                }

                html += '<li><div class="text-center link-block">';
                html += '<small class="text-muted pointer" onclick="winLocation(\'\')">';
                html += '<i class="fa fa-th-list"></i> 查看全部</small>';
                html += '</div></li>';
                //$('#notifYUL').append(html);

                $('#envelopeNum').text(data.todo + data.message + data.announce + data.remind);

                $("[rel=bell]").popover({
                    trigger: 'manual',
                    placement: 'bottom',
                    html: 'true',
                    content: html,
                    animation: false
                }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });
            } else //停止定时更新角标提示消息
            {
                $('#envelopeNum').text(0);
                clearInterval(timer);
                console.log('停止');
            }

        }
    });
}

function winLocation(type) {
    var url = base + '/sys/staffNotify/list?type=' + type;
    $('#content').attr('src', url);

    /* 面包屑导航 */
    var title = 'notify.100_消息中心,notify_通知中心,sys_系统管理,';
    onClick(url, 'notify.100', title, '消息中心');
}
