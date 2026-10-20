/**
 * @author yinke.hou
 * version: 1.0
 *
 */

var JepCookie = (function ($) {
    //默认变量
    var _COOKIE_USER_NAME = 'cookie.user.name';

    function JepCookie(args) {
        if (null != args && '' != args) _COOKIE_USER_NAME = args;
        return this;
    }

    JepCookie.prototype = {
        addCookie: function (name, args) {

            if (null != args && '' != args) _COOKIE_USER_NAME = args;

            //获取cookie
            var cookies = $.cookie(_COOKIE_USER_NAME);

            //删除已有的cookie
            //$.cookie(_COOKIE_USER_NAME, '');

            //判断当前是否存在cookie
            if (null != cookies && '' != cookies) {
                if (cookies.indexOf(name) < 0) {
                    cookies += ',' + name;
                    $.cookie(_COOKIE_USER_NAME, cookies);
                }
            } else {
                $.cookie(_COOKIE_USER_NAME, name);
            }
        },
        getCookie: function (args) {
            if (null != args && '' != args) _COOKIE_USER_NAME = args;

            //获取当前cookie
            var cookies = $.cookie(_COOKIE_USER_NAME);

            //如果cookie为空， 返回''
            if (null == cookies || '' == cookies) {
                cookies = '';
            }
            return cookies;
        }
    }
    return JepCookie;
})(jQuery);