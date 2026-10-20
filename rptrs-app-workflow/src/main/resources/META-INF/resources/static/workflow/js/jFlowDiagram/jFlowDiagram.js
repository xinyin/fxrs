/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 * 
 */


+function($){
	'use strict';
	
	var CHECKED_NODE_KEY = 'noCheck';

    var JFlowDiagram = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.nodes = [];
        this.node = null;
        this.init();
    };
    
    var calculateFunc = function(name)
    {
    	var func = name;

        if (typeof name === 'string') {
            // support obj.func1.func2
            var names = name.split('.');

            if (names.length > 1) {
                func = window;
                $.each(names, function (i, f) {
                    func = func[f];
                });
            } else {
                func = window[name];
            }
        }
        
        if(typeof func === 'function') return func;
        return undefined;
    }
	
    /**
     * 默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    JFlowDiagram.DEFAULTS = {
    	code: '',
    	onNodeSelect : function(node){
    		
    	}
    };
        
    JFlowDiagram.EVENTS = [];
    
    JFlowDiagram.prototype.onNodeSelect = function(node){
    	
    }
    
    /**
     * 初始化
     */
    JFlowDiagram.prototype.init = function () {
    	
    	var $this = this, $opt = this.options, html = [];
    	this.disabled = $opt.disabled || false;
    	
    	$this.$el.css({
    		border: '1px solid'
    	});
    	var $imageUrl = $.helper.curl(base, '/workflow/diagram/pointNodeImage?code='+ $opt.code);
    	var $img = $('<img alt="流程图" id="imgWf99999wf" src="' + $imageUrl + '"/>').appendTo($this.$el);
    	
    	var $nodeUrl = $.helper.curl(base, '/workflow/diagram/nodes/', $opt.code);
    	$.ajax({
    		url : $nodeUrl,
    		type : 'post',
    		async : false,
    		data : {'code': $opt.code},
    		success: function(data)
    		{
    			$this.nodes = data;
    			
    		},
    		error : function(xhr, e)
    		{
    			
    		}
    	});
    	
    	$img.off('click').on('click', function(e){
    		var x = e.offsetX, y = e.offsetY;
    		
    		for(var i = 0; i < $this.nodes.length; i++)
    		{
    			var node = $this.nodes[i];
    			
    			if(node.type == 'STARTEVENT' || node.type == 'ENDEVENT')
    			{
    				var xd = node.startX + node.width/2 - x;
    				var yd = node.startY + node.width/2 - y;
    				var dis = Math.pow((xd * xd + yd * yd), 0.5) - node.width/2;
    				/*如果小于0，则点在圆形内*/
    				if(dis < 0){
    					/*不相等就点到了另一个node上*/
    					if(CHECKED_NODE_KEY != node.key){
    						document.getElementById("imgWf99999wf").src=$imageUrl + '&nodeKey=' + node.key + '&nodeType=USERTASK';
    						CHECKED_NODE_KEY = node.key;
    						/*回调选择节点函数*/
    						var func = calculateFunc($opt.onNodeSelect);
        					if(func) return func.apply($this, [node]);
    					}
    					break;
    				}
    				
    			}
    			else if(node.type == 'USERTASK'){
    				var xd = x - node.startX;
    				var yd = y - node.startY;
    				
    				if(xd > 0 && xd < node.width && yd > 0 && yd < node.height){
    					/*不相等就点到了另一个node上*/
    					if(CHECKED_NODE_KEY != node.key){
    						document.getElementById("imgWf99999wf").src=$imageUrl + '&nodeKey=' + node.key + '&nodeType=USERTASK';
    						CHECKED_NODE_KEY = node.key;
    						/*回调选择节点函数*/
    						var func = calculateFunc($opt.onNodeSelect);
        					if(func) return func.apply($this, [node]);
    					}
    					break;
    				}
    			}
    			else if(node.type == 'FLOW'){
    				/*获得这个FLOW的线路图*/
    				var wayPoints = node.wayPoints;
    				wayPoints = $.parseJSON(wayPoints);
    				/*判断是否点在线路图上*/
    				var inWay = pointInWay(wayPoints, x, y);
    				if(inWay){
						document.getElementById("imgWf99999wf").src=$imageUrl + '&nodeKey=' + node.key + '&nodeType=FLOW';
						CHECKED_NODE_KEY = node.key;
						/*回调选择节点函数*/
						var func = calculateFunc($opt.onNodeSelect);
    					if(func) return func.apply($this, [node]);
    					break;
					}
    			}
    		}
    	});
    	
    };
    
    /*判断点击的点是否在这个线路上*/
    function pointInWay(wayPoints, pointX, pointY){
    	
    	/*依次判断点是否在相邻的点的连线上*/
    	for(var i = 0; i < wayPoints.length - 1; i++){
    		if(pointInLine(wayPoints[i].X, wayPoints[i + 1].X, wayPoints[i].Y, wayPoints[i + 1].Y, pointX, pointY))
			{
    			return true;
    			break;
			}
    	}
    }
    
    function pointInLine(startX, endX, startY, endY, pointX, pointY){
    	/*如果是一条竖线*/
    	if(startX == endX && pointX == startX && pointY > Math.min(startY, endY) && pointY < Math.max(startY, endY)){
    		return true;
    	}
    	/*如果是一条横线*/
    	else if(startY == endY && pointY == startY && pointX > Math.min(startX, endX) && pointX < Math.max(startX, endX)){
        	return true;
        }
    	/*先判断点击的点是否在起点到终点的矩形框中*/
    	else if(!(pointX < Math.min(startX, endX)) && !(pointX > Math.max(startX, endX)) && !(pointY < Math.min(startY, endY)) && !(pointY > Math.max(startY, endY))){
    		/*获得线的倾斜度, 从起点开始*/
    		var angle1 = angle(startX, endX, startY, endY);
    		var angle2 = angle(startX, pointX, startY, pointY);
    		
    		/*获得线的倾斜度, 从终点开始*/
    		var angle3 = angle(endX, startX, endY, startY);
    		var angle4 = angle(endX, pointX, endY, pointY);
    		/*如果倾斜度相同就说明在一条直线上*/
    		if(Math.round(angle3*10) == Math.round(angle4*10) || Math.round(angle1*10) == Math.round(angle2*10)) {
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /*计算与水平面的倾斜率*/
    function angle(startX, endX, startY, endY){
        var diff_x = endX - startX,
            diff_y = endY - startY;
        //返回倾斜率
        return diff_y/diff_x;
    }
    
    /**
     * 可调用的方法
     */
    var allowedMethods = [];

    $.fn.jFlowDiagram = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);
        
        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jFlowDiagram'),
                options = $.extend({}, JFlowDiagram.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }
                
                if (!data) {
                    return;
                }
                
            	value = data[option].apply(data, args);
                if (option === 'destroy') {
                    $this.removeData('bootstrap.jFlowDiagram');
                }
            }

            if (!data) {
                $this.data('bootstrap.jFlowDiagram', (data = new JFlowDiagram(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };

    
    $.fn.jFlowDiagram.Constructor = JFlowDiagram;
    $.fn.jFlowDiagram.defaults = JFlowDiagram.DEFAULTS;
    $.fn.jFlowDiagram.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jFlowDiagram"]').jFlowDiagram();
    });
    
}(jQuery);