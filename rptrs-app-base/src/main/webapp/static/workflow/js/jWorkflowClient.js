/**
 * 该插件依赖于jQuery v3.2.0
 * 主要功能用于在自定义表单直接进行传输数据功能。 只需要配置一个参数：使用配置handler：on-form-data进行配置handler,该handler存在一个默认值：getFormData。该handler需要以JSON格式返回form表单数据。
 */


+function($){
	'use strict';
	

    var JWFClientTransferData = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
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
	
    JWFClientTransferData.DEFAULTS = {
    	onFormData: "getFormData"
    };
    
    /**
     * 初始化
     */
    JWFClientTransferData.prototype.init = function () {
    	
    	var $this = this, $opt = this.options;
    	/*初始化参数*/
    	window.addEventListener('message',function(e){    		
    	    //1. 判断是否是通过工作流传递过来的数据，如果是就进行处理，不是就不处理
    		if(e.data.type == 'workflow'){
    			var formData;
    			if(typeof $opt.onFormData === 'string')
				{
					var func = calculateFunc($opt.onFormData);
					if(func)
						formData = func.apply(this);
				}
    			else if (typeof $opt.onFormData === 'function') {
    		        return $opt.onFormData.apply(this);
    		    }
    			
    			if(formData)
    			{
    				if(typeof formData === 'object' ){
    					e.data.formData = JSON.stringify(formData);
    				}
    				else if(typeof formData === 'string')
    				{
            		    if(isJSON(formData)){
            		    	e.data.formData = formData;
            		    }
            		    else
        		    	{
            		    	e.data.formData = false;
        		    	}
    				}
    				else
    				{
    					e.data.formData = false;
    				}
    				
    				window.parent.postMessage(e.data, e.origin);
    			}
    			else if(formData === undefined || formData === null){
    				window.parent.postMessage(e.data, e.origin);
    			}
    			
    		}
    	},false);
    	
    	var cHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
	    var sHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
	    var height  = Math.max(cHeight, sHeight);
	    var data = {type : 'workflowFrameH', height :  height};
	    window.parent.postMessage(data, '*');
    };
    
	function isJSON(str)
	{
		if (typeof str == 'string') {
            try {
                var obj=JSON.parse(str);
                if(str.indexOf('{')>-1){
                    return true;
                }else{
                    return false;
                }

            } catch(e) {
               
                return false;
            }
        }
        return false;
	}

    
    
    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    
    $.fn.jWFClientTransferData = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);
        
        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jWFClientTransferData'),
                options = $.extend({}, JWFClientTransferData.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jWFClientTransferData');
                }
            }

            if (!data) {
                $this.data('bootstrap.jWFClientTransferData', (data = new JWFClientTransferData(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };

    $.fn.jWFClientTransferData.Constructor = JWFClientTransferData;
    $.fn.jWFClientTransferData.defaults = JWFClientTransferData.DEFAULTS;
    $.fn.jWFClientTransferData.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jWFClientTransferData"]').jWFClientTransferData();
    });
    
}(jQuery);

/**
 * 该插件依赖于jQuery v3.2.0
 * 工作流客户端，启动流程生成插件， 可以配置在button、a标签、div、....等标签上，点击该变成触发启动流程操作
 * 一共有三个参数：'data-client-root-url' ---客户端根目录（必须配置）； 'data-proc-def-code' ---业务流程编码（必须配置）
 *  'old-data' ---旧的业务数据（编辑的时候需要，也可以使用配置handler：onOldData进行配置） 
 */
+function($){
	'use strict';
//	var _CLIENT_URL_ = 'http://localhost:8080/jep-app'; //客户端的根url

    var JWFClientStartButton = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
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
	
    JWFClientStartButton.DEFAULTS = {
    	clientRootUrl: null,
    	oldData: null,
    	procDefCode: null
    };
    
    /**
     * 初始化
     */
    JWFClientStartButton.prototype.init = function () {
    	var $this = this, $opt = this.options;
    	if(!$opt.procDefCode){
    		$.messagerClient.toastr('系统提示', '业务编码不能为空。', 'info');
    		return ;//如果没有业务流程编码，就直接返回。不生成代码
    	}
    	
    	if(!$opt.clientRootUrl){
    		$.messagerClient.toastr('系统提示', '客户端根地址不能为空。', 'info');
    		return ;//如果没有客户端根地址，就直接返回。不生成代码
    	}
    	
    	$(this.$el).on('click', function(e){
    		var oldData = $opt.oldData;
    		if(typeof $opt.onOldData === 'string')
			{
				var func = calculateFunc($opt.onOldData);
				if(func)
					oldData = func.apply(this);
			}
    		if(oldData){
    			oldData = $.helper.base64Encode(oldData);
    		}
    		
    		$.maskClient.post($opt.clientRootUrl + '/workflow/client/getServiceUrl', {isValidate : true, procDefCode : $opt.procDefCode}, function(data){
    			$.maskClient.post(data.url, {token : data.token}, function(data){
    				if(data.success){
    					$.maskClient.post($opt.clientRootUrl + '/workflow/client/getServiceUrl', {isValidate : false, procDefCode : $opt.procDefCode}, function(data){
    		    			post(data.url, {token : data.token, procDefCode : $opt.procDefCode, oldData : oldData});
    		        	});
    				}
    				else
    				{
    					$.messagerClient.toastr('提示', data.msg);
    				}
    			});
    		});
    		
    		
    	});
    };
    
    /**
     * 通过post提交并跳转数据
     */
    function post(URL, PARAMS) { 
        var temp = document.createElement("form"); 
        temp.action = URL; 
        temp.method = "post"; 
        temp.style.display = "none"; 
        for (var x in PARAMS) { 
          var opt = document.createElement("textarea"); 
          opt.name = x; 
          opt.value = PARAMS[x]; // alert(opt.name) 
          temp.appendChild(opt); 
          } 
        document.body.appendChild(temp); 
        temp.submit(); return temp; 
      }
    
    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    
    $.fn.jWFClientStartButton = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);
        
        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jWFClientStartButton'),
                options = $.extend({}, JWFClientStartButton.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jWFClientStartButton');
                }
            }

            if (!data) {
                $this.data('bootstrap.jWFClientStartButton', (data = new JWFClientStartButton(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };

    /**
     * 遮罩层
     */
    jQuery.maskClient = {
    		
    	/**
    	 * post
    	 */
    	post: function(url, data, fn)
    	{
    		var _data, _fn;
    		if(data)
    		{
    			if($.isFunction(data))
    			{
    				_fn = data;
    			}
    			else
    			{
    				_data = data;
    				if(fn && $.isFunction(fn))
    				{
    					_fn = fn;
    				}
    			}
    		}
    		
    		//显示遮罩层
    		$.maskClient.show();
    		$.post(url, _data, function(data){
    			//移除遮罩层
    			$.maskClient.remove();
    			if(_fn) _fn.call(this, data);
    		});
    	},
    	
    	/**
    	 * 显示遮罩层
    	 */
    	show: function(msg, closeOnClick)
    	{
    		msg = msg || '正在处理，请稍后......';
    		var $doc = $(top.document.body);
    		var $backdrop = top.$('<div class="jep-backdrop fade in jep-loading-box"></div>').appendTo($doc);
    		var $container = top.$('<div class="jep-loading-container"></div>').appendTo($backdrop);
    		var $load = top.$('<div class="sk-spinner sk-spinner-circle jep-loading"></div>').appendTo($container);
    		for(var i = 1; i <= 12; i++)
    		{
    			top.$('<div class="sk-circle' + i + ' sk-circle"></div>').appendTo($load);
    		}
    		$('<span class="jep-loading-text">' + msg + '</span>').appendTo($container);
    		
    	},
    	
    	/**
    	 * 移除遮罩层
    	 */
    	remove: function()
    	{
    		var $doc = $(top.document.body);
    		var $box = $doc.find('.jep-loading-box');
    		$box.remove();
    	}
    };
    
    /**
     * 模态框
     */
    jQuery.messagerClient = {
    	/**
    	 * 显示 toastr 对话框
    	 */
    	toastr : function(title, content, type)
    	{
    		if(!type) type = 'info';
    		top.toastr.options = {
    			"closeButton" : true,
    			"debug" : false,
    			"progressBar" : false,
    			"preventDuplicates" : false,
    			"positionClass" : "toast-top-center",
    			"onclick" : null,
    			"showDuration" : "400",
    			"hideDuration" : "1000",
    			"timeOut" : "2000",
    			"extendedTimeOut" : "1000",
    			"showEasing" : "swing",
    			"hideEasing" : "linear",
    			"showMethod" : "fadeIn",
    			"hideMethod" : "fadeOut"
    		};
    		top.toastr[type](content, title);
    	}
    };
    
    $.fn.jWFClientStartButton.Constructor = JWFClientStartButton;
    $.fn.jWFClientStartButton.defaults = JWFClientStartButton.DEFAULTS;
    $.fn.jWFClientStartButton.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jWFClientStartButton"]').jWFClientStartButton();
    });
    
}(jQuery);