/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var jCron = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.$parent = this.$el.parent();
        if (this.options) {
            $.extend(this.options, {'id': this.$el.attr('id')});
            $.extend(this.options, {'data': this.$el.attr('data')});
        }
        this.init();
    };

    var calculateFunc = function (name) {
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

        if (typeof func === 'function') return func;
        return undefined;
    }

    /**
     * 默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    jCron.DEFAULTS = {
        multiple: null
    };

    jCron.EVENTS = [];

    /**
     * 初始化
     */
    jCron.prototype.init = function () {
        var $this = this, $opt = $this.options;
        var html = '<div class=" col-xs-10">';
        html += '<div id="corn" data-toggle="jTab" class="tabs-container ">';
        html += '<ul class="nav nav-tabs" >';
        html += '<li class="tab-list active"><a name="tab-1" href="#tab-1">秒</a></li>';
        html += '<li class="tab-list "><a name="tab-2" href="#tab-2">分钟</a></li>';
        html += '<li class="tab-list "><a name="tab-3" href="#tab-3">小时</a></li>';
        html += '<li class="tab-list "><a name="tab-4" href="#tab-4">日</a></li>';
        html += '<li class="tab-list "><a name="tab-5" href="#tab-5">月</a></li>';
        html += '<li class="tab-list "><a name="tab-6" href="#tab-6">周</a></li>';
        html += '<li class="tab-list "><a name="tab-7" href="#tab-7">年</a></li>';
        html += '</ul>';
        html += '<div class="tab-content">';

        /***************秒 start********************/
        html += '<div id="tab-1" class="tab-pane active">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="second" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>每秒 允许的通配符[, - * /]</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="second" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner second1">';
        html += '<input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,\'\')" id="secondStart_0" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label>';
        html += '</div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner second2">';
        html += '<input type="text" class="form-control numberspinner" id="secondEnd_0" onkeyup="value=value.replace(/\D/g,\'\')" value="2" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="second" onclick="startOn(this)" class="form-control"/>';
        html += '<label>从</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner second3">';
        html += '<input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,\'\')" id="secondStart_1" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒开始, 每</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner second4">';
        html += '<input type="text" class="form-control numberspinner" onkeyup="value=value.replace(/\D/g,\'\')" id="secondEnd_1" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">秒执行一次</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="second" id="sencond_appoint" class="form-control"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 secondList">';
        for (var i = 0; i < 60; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 0) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + (i.toString().length < 2 ? '0' + i : i) + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************秒 end********************/
        /***************分钟 start********************/
        html += '<div id="tab-2" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="min" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>分钟 允许的通配符[, - * /]</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="min" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner min1">';
        html += '<input type="text" class="form-control numberspinner" value="1" onkeyup="value=value.replace(/\D/g,\'\')" id="minStart_0" style="border: 1px solid #e7eaec;"/>';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner min2">';
        html += '<input type="text" class="form-control numberspinner" value="2" onkeyup="value=value.replace(/\D/g,\'\')" id="minEnd_0" style="border: 1px solid #e7eaec;"/>';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟</label>';
        html += '</div></div><div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="min" class="form-control" onclick="startOn(this);"/>';
        html += '<label>从</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner min3">';
        html += '<input type="text" class="form-control numberspinner" value="0" onkeyup="value=value.replace(/\D/g,\'\')" id="minStart_1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟开始, 每</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner min4">';
        html += '<input type="text" class="form-control numberspinner" value="1" onkeyup="value=value.replace(/\D/g,\'\')" id="minEnd_1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">分钟执行一次</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="min" class="form-control" id="min_appoint"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 minList">';
        for (var i = 0; i < 60; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 0) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + (i.toString().length < 2 ? '0' + i : i) + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************分钟 end********************/
        /***************小时 start********************/
        html += '<div id="tab-3" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="hour" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>小时 允许的通配符[, - * /]</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="hour" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner hour1">';
        html += '<input type="text" class="form-control numberspinner" id="hourStart_0" value="0" onkeyup="value=value.replace(/\D/g,\'\')" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical"> ';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner hour2">';
        html += '<input type="text" class="form-control numberspinner" value="2" id="hourEnd_0" onkeyup="value=value.replace(/\D/g,\'\')" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="hour" class="form-control" onclick="startOn(this)"/>';
        html += '<label>从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner hour3">';
        html += '<input type="text" class="form-control numberspinner" id="hourStart_1" value="0" onkeyup="value=value.replace(/\D/g,\'\')" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时开始, 每</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner hour4">';
        html += '<input type="text" class="form-control numberspinner" id="hourEnd_1" value="1" onkeyup="value=value.replace(/\D/g,\'\')" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button> ';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">小时执行一次</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="hour" id="hour_appoint" class="form-control"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 hourList">';
        for (var i = 0; i < 24; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 0) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + (i.toString().length < 2 ? '0' + i : i) + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************小时 end********************/
        /***************天 start********************/
        html += '<div id="tab-4" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="day" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>日 允许的通配符[, - * / L W]</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="day" class="form-control" onclick="unAppoint(this)"/>';
        html += '<label>不指定</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="day" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner day1">';
        html += '<input type="text" class="form-control numberspinner" id="dayStart_0" onkeyup="value=value.replace(/\D/g,\'\')" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner day2">';
        html += '<input type="text" class="form-control numberspinner" id="dayEnd_0" onkeyup="value=value.replace(/\D/g,\'\')" value="2" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="day" class="form-control" onclick="startOn(this)"/>';
        html += '<label>从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner day3">';
        html += '<input type="text" class="form-control numberspinner" id="dayStart_1" onkeyup="value=value.replace(/\D/g,\'\')" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日开始, 每</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner day4">';
        html += '<input type="text" class="form-control numberspinner" id="dayEnd_1" onkeyup="value=value.replace(/\D/g,\'\')" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">天执行一次</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="day" class="form-control" onclick="workDay(this)"/>';
        html += '<label>每月</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner day5">';
        html += '<input type="text" class="form-control numberspinner" id="dayStart_2" onkeyup="value=value.replace(/\D/g,\'\')" value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-2" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">号最近的那个工作日</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-2" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="day" class="form-control" onclick="lastDay(this)"/>';
        html += '<label>本月最后一天</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="day" class="form-control" id="day_appoint"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 dayList">';
        for (var i = 1; i < 32; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 1) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + (i.toString().length < 2 ? '0' + i : i) + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************天 end********************/
        /***************月 start********************/
        html += '<div id="tab-5" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="month" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>月 允许的通配符[, - * /]</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="month" class="form-control" onclick="unAppoint(this)"/>';
        html += '<label>不指定</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="month" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner month1">';
        html += '<input type="text" class="form-control numberspinner" id="monthStart_0" onkeyup="value=value.replace(/\D/g,\'\')"  value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner month2">';
        html += '<input type="text" class="form-control numberspinner" id="monthEnd_0" onkeyup="value=value.replace(/\D/g,\'\')"  value="2" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">月</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="month" class="form-control" onclick="startOn(this)"/>';
        html += '<label>从</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner month3">';
        html += '<input type="text" class="form-control numberspinner" id="monthStart_1" onkeyup="value=value.replace(/\D/g,\'\')"  value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">日开始, 每</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner month4">';
        html += '<input type="text" class="form-control numberspinner" id="monthEnd_1" onkeyup="value=value.replace(/\D/g,\'\')"  value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-2" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">月执行一次</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="month" class="form-control" id="month_appoint"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 monthList">';
        for (var i = 1; i < 13; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 1) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + i + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************月 end********************/
        /***************周 start********************/
        html += '<div id="tab-6" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="week" checked="checked" class="form-control" onclick="everyTime(this)"/>';
        html += '<label>周 允许的通配符[, - * / L #]</label>';
        html += '</div></div>';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="week" class="form-control" onclick="unAppoint(this)"/>';
        html += '<label>不指定</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-2" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="week" class="form-control" onclick="startOn(this)"/>';
        html += '<label>周期从星期</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner week1">';
        html += '<input type="text" class="form-control numberspinner" id="weekStart_0" value="1" onkeyup="value=value.replace(/\D/g,\'\')"  style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner week2">';
        html += '<input type="text" class="form-control numberspinner" id="weekEnd_0" value="2" onkeyup="value=value.replace(/\D/g,\'\')"  style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="week" class="form-control" onclick="weekOfDay(this)"/>';
        html += '<label>第</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner week3">';
        html += '<input type="text" class="form-control numberspinner" id="weekStart_1" value="1" onkeyup="value=value.replace(/\D/g,\'\')"  style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">周的星期</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 30px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner week4">';
        html += '<input type="text" class="form-control numberspinner" id="weekEnd_1" onkeyup="value=value.replace(/\D/g,\'\')"  value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-2" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="week" class="form-control" onclick="lastWeek(this)"/>';
        html += '<label>本月最后一个星期</label>';
        html += '</div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner week5">';
        html += '<input type="text" class="form-control numberspinner" id="weekStart_2" onkeyup="value=value.replace(/\D/g,\'\')"  value="1" style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;cursor: pointer;">';
        html += '<input type="radio" name="week" class="form-control" id="week_appoint"/>';
        html += '<label>指定</label>';
        html += '</div></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-0 weekList">';
        for (var i = 1; i < 8; i++) {
            html += '<div class="checkbox  checkbox  checkbox-default checkbox-inline"';
            if (i == 1) html += ' style="margin-left: 10px;"';
            html += '><input type="checkbox" value="' + i + '" class="form-control "><label >' + i + '</label></div>';
        }
        html += '</div></div></div></div>';
        /***************周 end********************/
        /***************年 start********************/
        html += '<div id="tab-7" class="tab-pane">';
        html += '<div class="wrapper wrapper-content">';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="year" checked="checked" class="form-control" onclick="unAppoint(this)"/>';
        html += '<label>不指定 允许的通配符[, - * /] 非必填</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="year" id="yearStart_0" onkeyup="value=value.replace(/\D/g,\'\')"  class="form-control" onclick="everyTime(this)"/>';
        html += '<label>每年</label></div></div>';
        html += '<div class="row m-b">';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="radio  radio-default radio-inline" style="padding: 0;margin: 5px 0px 0px 20px;">';
        html += '<input type="radio" name="year" id="yearEnd_0" class="form-control" onclick="cycle(this)"/>';
        html += '<label>周期从</label></div></div>';
        html += '<div class="col-xs-1" style="height: 35px;padding: 0;">';
        html += '<div class="input-group spinner year1">';
        html += '<input type="text" class="form-control numberspinner" value="2017" onkeyup="value=value.replace(/\D/g,\'\')"  style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div>';
        html += '<div style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;width: 10px;text-align: center;float: left;">';
        html += '<label style="color: #3c763d;font-size: 13px;font-weight: normal;">-</label></div>';
        html += '<div class="col-xs-1" style="height: 35px;line-height: 35px;padding: 0;margin-left: 5px;">';
        html += '<div class="input-group spinner year2">';
        html += '<input type="text" class="form-control numberspinner" value="2018" onkeyup="value=value.replace(/\D/g,\'\')"  style="border: 1px solid #e7eaec;">';
        html += '<div class="input-group-btn-vertical">';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>';
        html += '<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>';
        html += '</div></div></div></div>';
        /***************年 end********************/
        html += '</div></div></div>';
        /***************显示每项选择的参数 start********************/
        html += '<input type="hidden" name="v_second" class="col" value="*"/>';
        html += '<input type="hidden" name="v_min" class="col" value="*"/>';
        html += '<input type="hidden" name="v_hour" class="col" value="*"/>';
        html += '<input type="hidden" name="v_day" class="col" value="*"/>';
        html += '<input type="hidden" name="v_month" class="col" value="*"/>';
        html += ' <input type="hidden" name="v_week" class="col" value="?"/>';
        html += '<input type="hidden" name="v_year" class="col"/>';
        /***************显示每项选择的参数 end********************/

        html += '<div class="row m-b col-xs-4" >';
        html += '<input type="text" id="' + $opt.id + 'Id" name="' + $opt.id + 'Id" style="width: 100%;"';
        if ($opt.data) {
            html += ' value="' + $opt.data + '"';
        } else {
            html += ' value="* * * * * ?"';
        }
        html += ' readonly="readonly" /></div>';
        $('#' + $opt.id).html(html);

        //重新初始化jTab
        $('[data-toggle="jTab"]').jTab();
        //初始化加载js脚本
        $this.initCron();
    };

    jCron.prototype.initCron = function () {
        var $this = this, $opt = $this.options;
        /*生成脚本代码*/
        var script = [];
        script.push('<script type="text/javascript">');
        var func = [];
        func.push('$(document).ready(function(){');
        func.push("//调用js脚本中初始化查询需要的脚本");
        func.push("initCron('" + $opt.id + "', '" + $opt.data + "');");
        func.push('});');
        script.push(func.join('\n'));
        script.push('</script>');
        $(script.join('\n')).appendTo($this.$parent);

    };

    /**
     * 修改tab 子集中的内容
     */
    jCron.prototype.getCron = function () {
        var $this = this, $opt = $this.options;

        return $('#' + $opt.id + 'Id').val();
    }

    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    allowedMethods.push("getCron");

    $.fn.jCron = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jCron'),
                options = $.extend({}, jCron.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jCron');
                }
            }

            if (!data) {
                $this.data('bootstrap.jCron', (data = new jCron(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jCron.Constructor = jCron;
    $.fn.jCron.defaults = jCron.DEFAULTS;
    $.fn.jCron.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jCron"]').jCron();
    });
}(jQuery);