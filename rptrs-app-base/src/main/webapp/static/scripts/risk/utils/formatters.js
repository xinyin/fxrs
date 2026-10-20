function formatLongText(value) {
    return $.toolTip.formatLongText(value);
//	if(!value) return '';
//	return '<span style="width:100%;height:100%;" title="' + value + '">' + value + '</span>';
};

;(function ($) {
    jQuery.toolTip = $.extend({}, jQuery.toolTip, {
        formatLongText: function (value) {
            var _init = function ($) {
                var tooltip;
                if ($('body').find('.tip-help-wrap').length > 0) {
                    tooltip = $('.tip-help-wrap');
                } else {
                    var html = "<div class='tip-help-wrap'>"
                        + "<div class='tip-help-content'></div>"
                        + "<div class='tip-direction'>"
                        + "<span class='tip-corner-inner'>◆</span>"
                        + "<span class='tip-corner-outer'>◆</span>"
                        + "</div>"
                        + "</div>";
                    tooltip = $(html).appendTo('body');
                }
                return tooltip;
            }(jQuery);

            var _mouseFaction = function (e, that) {
                var pos = that.getBoundingClientRect();
                var $tip = $('.tip-help-wrap');
                if ($tip.css('display') != 'none') return false;
                var $direction = $('.tip-direction');
                var className = 'tip-direction-left-top';
                var boxWidth = parseInt($tip.css('width'));
                var windowWidth = window.innerWidth || document.body.clientWidth;
                var windowHeight = window.innerHeight || document.body.clientHeight;
                $tip.css({
                    left: 'auto',
                    right: 'auto',
                    top: 'auto',
                    bottom: 'auto'
                });
                var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
                var scroolTop = document.documentElement.scrollTop || document.body.scrollTop;
                var clientX = e.pageX || e.clientX + scrollLeft;
                var clientY = e.pageY || e.clientY + scrollTop;
                var offsetY = windowHeight - clientY;
                if (offsetY > 50) {
                    if (windowWidth - clientX < boxWidth) {
                        $tip.css({
                            left: (clientX - boxWidth - 20) + 'px',
                            top: (clientY - 25) + 'px'
                        });
                        className = 'tip-direction-right-top';
                    } else {
                        $tip.css({
                            left: (clientX + 15) + 'px',
                            top: (clientY - 25) + 'px'
                        });
                    }
                } else {
                    $tip.css('top', (clientY - 50) + 'px');

                    if (windowWidth - clientX < boxWidth) {
                        $tip.css('left', (clientX - boxWidth - 20) + 'px');
                        if (offsetY < 25) {
                            className = 'tip-direction-right-bottom';
                        } else {
                            className = 'tip-direction-right-top';
                        }
                    } else {
                        $tip.css('left', (clientX + 20) + 'px');
                        if (offsetY < 25) {
                            className = "tip-direction-left-bottom";
                        } else {
                            className = "tip-direction-left-top";
                        }
                    }

                }
//	            var textLen = (that.innerHtml ? that.innerHtml.length : 0)*10;
//	            if(textLen < boxWidth && textLen > (boxWidth/2)) {
//	            	$tip.css('width', textLen);
//	            }
//	            else if(textLen < (boxWidth/2)) {
//	            	$tip.css('width', (boxWidth/2));
//	            }

                $('.tip-help-content')[0].innerHTML = that.innerHTML;
                $tip.css('display', 'block');
                if (!$direction.hasClass(className)) {
                    $direction.removeClass('tip-direction-right-top');
                    $direction.removeClass('tip-direction-right-bottom');
                    $direction.removeClass('tip-direction-left-top');
                    $direction.removeClass('tip-direction-left-bottom');
                    $direction.addClass(className);
                }
            };

            var _bindTip = function (tipclassName) {
                $('.datagrid-body').delegate(tipclassName, {
                    mouseover: function (e) {
                        _mouseFaction(e, this);
                    },
                    mouseout: function (e) {
                        var $tip = $('.tip-help-wrap');
                        var $direction = $(".tip-direction");
                        $tip.css('display', "none");
                        $direction.addClass('tip-direction');
                    },
                    mousemove: function (e) {
                        _mouseFaction(e, this);
                    }
                });
            };

            var _service = function (value) {
                if (!value) return '';
                var html = "<span class='cell-tooltip' style='width:100%;height:100%;display:block;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;'>" + value + "</span>";
                _bindTip('span');
                return html;
            };

            return _service(value);
        }
    });
})(jQuery);

