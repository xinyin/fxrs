/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 * 
 */


+function($){
	'use strict';
	
	var RENDER_TYPE_EDIT = 'EDIT';
	var RENDER_TYPE_VIEW = 'VIEW';

    var JFlowForm = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.$form = null;
        this.$parent = this.$el.parent();
        this.$props = [];
        this.$events = [];
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
    JFlowForm.DEFAULTS = {
    	formId: null,
    	formDefCode: null,
    	procDefCode: null,
    	procInstId: null,
    	renderType: 'EDIT',
    	url: $.helper.curl(base, '/workflow/form/metadata'),
    	formData: null
    };
        
    JFlowForm.EVENTS = [];
    
    /**
     * 初始化
     */
    JFlowForm.prototype.init = function () {
    	
    	var $this = this, $opt = this.options;
    	this.$form = $('#' + $opt.formId);
    	if($opt.formData)
    	{
    		$this.oldData = $opt.formData;
    	}
    	
    	var data = {};
    	/*使用表单编码获取表单字段，进行后续处理*/
    	if($opt.formDefCode)
    	{
    		data.formDefCode = $opt.formDefCode;
    	}
    	/*使用业务流程编码获取表单字段*/
    	else if($opt.procDefCode)
    	{
    		data.procDefCode = $opt.procDefCode;
    		if($this.oldData)
    		{
    			data.oldData = $this.oldData;
    		}
    	}
    	/*使用业务流程ID获取表单字段*/
    	else if($opt.procInstId)
    	{
    		data.procInstId = $opt.procInstId;
    		if($opt.taskId)
    		{
    			data.taskId = $opt.taskId;
    		}
    	}
    	
    	$.ajax({
    		url : $opt.url,
    		type : 'post',
    		async : false,
    		data : data,
    		success: function(data)
    		{
    			$this.initData(data);
    		},
    		error : function(xhr, e)
    		{
    			
    		}
    	});
    };
    
    /**
     * 初始化数据
     */
    JFlowForm.prototype.initData = function (data) {
    	var $this = this, $opt = this.options;
    	
    	if(data.success)
		{
			/*获得属性定义，根据inputType 不同，可能需要对默认值进行处理*/
			$this.$props = {};
			if(data.props){
				for(var i = 0; i < data.props.length; i++)
				{
					var prop = data.props[i];
					$this.$props[prop.propCode] = prop;
				}
			}
			
			
			/*获得事件定义*/
			$this.$events = data.events;
			
			/*生成脚本代码*/
			var script = [];
			script.push('<script type="text/javascript">');
			if($this.$events){
				for(var i = 0; i < $this.$events.length; i++)
				{
					var event = $this.$events[i];
					/*属性可见可编辑，才增加事件*/
					if(event.type == 'PROP' && $this.$props[event.propCode].config.visible
							&& $this.$props[event.propCode].config.writable)
					{
						var func = [];
						func.push('$(document).ready(function($){');
						func.push('$("#' + event.propCode + '").on("' + event.name + '", function(event){');
						func.push(event.script);
						func.push('});');
						func.push('});');
						script.push(func.join('\n'));
					}
					else if(event.name == 'submit')
					{
						var funcName = '_form_submit_' + Math.uuid('10', '8');
						var func = [];
						func.push('function ' + funcName + '(event){');
						func.push(event.script);
						func.push('};');
						script.push(func.join('\n'));
						
						/*生成用户定义的表单提交事件*/
						$this['onFormSubmit'] = function(event)
						{
							//动态执行用户定义的 submit 事件
							var submitFunc = calculateFunc(funcName);
							submitFunc.apply(this, [event]);
						}
						
					}
				}
			}
			
			//判断表单提交事件不存在， 赋空方法
			if(typeof($this['onFormSubmit']) == 'undefined')
			{
				$this['onFormSubmit'] = function(){};
			}
			
			script.push('</script>');
			$(script.join('\n')).appendTo($this.$parent);
		}
    	else
    	{
    		
    	}
    };

    
    /**
     * 返回数据
     */
    JFlowForm.prototype.getFormData = function () {
    	
    	var $this = this, $opt = this.options;
    	/*查看模式下，不返回数据*/
    	if($opt.renderType == RENDER_TYPE_VIEW) return {};
    	
    	var data = {};
    	
    	/*返回旧数据*/
    	if($this.oldData)
		{
			data['oldData'] = $this.oldData;
		}
    	else
    	{
    		data['oldData'] = {};
    	}
    	
    	data['formData'] = {};
    	data['uploadSessions'] = [];
    	$.each($this.$props, function(){
    		/*隐藏域，直接取值*/
    		if(!this.config.visible || !this.config.writable)
    		{
    			
    			//data['formData'][this.propCode] = $('#' + this.propCode).val();
    			data['formData'][this.propCode] = this.value;
    		}
    		else
    		{
    			/*单行文本、多行文本、数字输入，直接取值*/
    			if(this.inputType == 'TEXT' || this.inputType == 'MTEXT' ||
    					this.inputType == 'NTEXT')
    			{
    				data['formData'][this.propCode] = $('#' + this.propCode).val();
    			}
    			/*radio、checkbox输入*/
    			else if(this.inputType == 'RADIO' || this.inputType == 'CHECK')
    			{
    				data['formData'][this.propCode] = $('input[name="' + this.propCode + '"]:checked').val();
    			}
    			/*下拉选择*/
    			else if(this.inputType == 'SELECT')
    			{
    				data['formData'][this.propCode] = $('#' + this.propCode).val();
    			}
    			/*日期选择*/
    			else if(this.inputType == 'DATE')
    			{
    				data['formData'][this.propCode] = $('#' + this.propCode).val();
    			}
    			/*机构选择*/
    			else if(this.inputType == 'UNIT')
    			{
    				data['formData'][this.propCode] = $('#' + this.propCode).jUnitLookup('getValue');
    			}
    			/*人员选择*/
    			else if(this.inputType == 'STAFF')
    			{
    				data['formData'][this.propCode] = $('#' + this.propCode).jStaffLookup('getValue');
    			}
    			/*附件*/
    			else if(this.inputType == 'ATTACH')
    			{
    				var session = $('#' + this.propCode).jUpload('getSession');
    				var files = $('#' + this.propCode).jUpload('getFiles');
    				var fileName = [];
    				$.each(files, function(){
    					fileName.push(this.srcFileName + '.' + this.fileFormat);
    				});
    				data['formData'][this.propCode] = fileName.join(',');
    				/*当前有文件上传时，将uploadSession返回*/
    				if(files.length > 0)
    				{
        				data['uploadSessions'].push(this.propCode + ":" + session);
    				}
    			}
    		}
    	});
    	
    	/*将表单数据和 uploadSessions 进行base64编码*/
    	data['formData'] = $.helper.base64Encode(JSON.stringify(data['formData']));
    	data['uploadSessions'] = $.helper.base64Encode(data['uploadSessions'].join(','));
    	return data;
    }
    
    /**
     * 可调用的方法
     */
    var allowedMethods = ['onFormSubmit', 'getFormData'];

    $.fn.jFlowForm = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);
        
        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jFlowForm'),
                options = $.extend({}, JFlowForm.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jFlowForm');
                }
            }

            if (!data) {
                $this.data('bootstrap.jFlowForm', (data = new JFlowForm(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };

    
    $.fn.jFlowForm.Constructor = JFlowForm;
    $.fn.jFlowForm.defaults = JFlowForm.DEFAULTS;
    $.fn.jFlowForm.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jFlowForm"]').jFlowForm();
    });
    
}(jQuery);