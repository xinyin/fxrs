/* =================================================
// jQuery Tabs Plugins 1.0
// author:chenyg@5173.com
// URL:http://stylechen.com/jquery-tabs.html
// 4th Dec 2010
// =================================================*/

;(function($){
	$.fn.extend({
		Tabs:function(options){
			// 处理参数
			options = $.extend({
				event : 'mouseover',
				timeout : 0,
				auto : 0,
				callback : null
			}, options);
			
			var self = $(this),
				tabBox = self.children( 'div.tab_box' ).children( 'div' ),
				menu = self.children( 'ul.tab_menu' ),
				items = menu.find( 'li' ),
				timer;
				
			var tabHandle = function( elem ){
					elem.siblings( 'li' )
						.removeClass( 'current' )
						.end()
						.addClass( 'current' );
						
					tabBox.siblings( 'div' )
						.addClass( 'hide' )
						.end()
						.eq( elem.index() )
						.removeClass( 'hide' );
				},
					
				delay = function( elem, time ){
					time ? setTimeout(function(){ tabHandle( elem ); }, time) : tabHandle( elem );
				},
				
				start = function(){
					if( !options.auto ) return;
					timer = setInterval( autoRun, options.auto );
				},
				
				autoRun = function(){
					var current = menu.find( 'li.current' ),
						firstItem = items.eq(0),
						len = items.length,
						index = current.index() + 1,
						item = index === len ? firstItem : current.next( 'li' ),
						i = index === len ? 0 : index;
					
					current.removeClass( 'current' );
					item.addClass( 'current' );
					
					tabBox.siblings( 'div' )
						.addClass( 'hide' )
						.end()
						.eq(i)
						.removeClass( 'hide' );
				};
							
			items.bind( options.event, function(){
				delay( $(this), options.timeout );
				if( options.callback ){
					options.callback( self );
				}
			});
			
			if( options.auto ){
				start();
				self.hover(function(){
					clearInterval( timer );
					timer = undefined;
				},function(){
					start();
				});
			}
			
			return this;
		}
	});
})(jQuery);

/*
 * write by waitingbar
 * time 2011.03.04
 *
 */
 
(function($){
    $.fn.extend({
		newsList:function(options){
	        var defaults ={
		       actName:'li',          //显示条数名；
			   maxShowNum:'7',       //最多的显示条数；
			   actNameH:'23',       //一次移动的距离；
			   ulClass:'.ul_news_list',           //被复制层的class
			   copyUlClass:'.ul_news_list2',     //复制层的class
			   autoTime:'1500',  //自动运行时间；
			   clickGoUpC:'.go_uplist',        //点击向上class; 
			   clickDownUpC:'.go_downlist',   //点击向下class; 
			   goStart:'go_tart',
			   autoCloss:'flase'    //自动运行开关,当为'flase'时为开，其它则关；
			   
			}

			options = $.extend(defaults, options);
			
			return this.each(function(){
		       var o = options;
			   var counts =1;
			   var linum = $($(this).find(o.actName),$(this)).size();
			   var ul_class = $($(this).find(o.ulClass),$(this));
			   var copy_ul_class = $($(this).find(o.copyUlClass),$(this));
			   var click_go_up_c = $($(this).find(o.clickGoUpC),$(this));
			   var click_go_down_C = $($(this).find(o.clickDownUpC),$(this));
			   var go_start = $($(this).find(o.goStart),$(this));
			   
			   	if(linum > o.maxShowNum){
				  $(copy_ul_class).html($(ul_class).html());
				  goStartList();
				}
				
				var thiswrap = $($(ul_class).parent().parent(),$(this));
				
				//自动运行函数
				function auto_function(){
					if(counts <= linum){
					$(ul_class).animate({top:'-=' + o.actNameH},o.autoTime);
					$(copy_ul_class).animate({top:'-=' + o.actNameH},o.autoTime);
					counts++;
					}else{
					$(ul_class).animate({top:0},0);
					$(copy_ul_class).animate({top:0},0);
					counts = 1;	
					}
				}
				
				//点击向上移动时；
				if(linum > o.maxShowNum){
					$(click_go_up_c).click(function(){
						if(counts <= linum){
							$(ul_class).animate({top:'-=' + o.actNameH},0);
							$(copy_ul_class).animate({top:'-=' + o.actNameH},0);
							counts++;
						}else{
							$(ul_class).animate({top:0},0);
							$(copy_ul_class).animate({top:0},0);
							counts = 1;
						}
					});
				}
				
				//点击向下移动时；
				if(linum > o.maxShowNum){
					$(click_go_down_C).click(function(){
						if(counts > 1){
							counts--;
							$(ul_class).animate({top:'-'+ counts*o.actNameH},0);
							$(copy_ul_class).animate({top:'-'+ counts*o.actNameH},0);
						}else{
							$(ul_class).animate({top:0},0);
							$(copy_ul_class).animate({top:0},0);
							counts = linum+1;
						}
				    });
				}
				
				//鼠标经过所发生的开始停止；
				if(linum > o.maxShowNum){
					$(thiswrap).hover(function(){
					   goStopList();
					},function(){
					   goStartList();
					});
				}
				
				
				function goStartList(){
					if(o.autoCloss === 'flase'){
					   go_start = setInterval(auto_function,o.autoTime);
					}
				}
				
				function goStopList(){
				    clearInterval(go_start);
				}
			});
		}
    });
}(jQuery));