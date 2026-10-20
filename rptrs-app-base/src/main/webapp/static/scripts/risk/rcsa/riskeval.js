/**
 * 扩展jquery.risk，增加风险评估处理
 * 避免在选择时频繁请求风险地图数据。
 * @param $
 */
(function ($) {

    jQuery.risk = $.extend({}, jQuery.risk, {

        createRiskEval: function () {

            var riskEval = function ($) {
                var _m = null;
                $.ajax({
                    url: contextPath + '/rcsa/riskMap/getUnitRiskMapDto.json',
                    type: 'post',
                    async: false,
                    data: {unitCode: ''},
                    success: function (data) {
                        if (data) _m = data;
                    }
                });
                this.map = _m;
                return this;

            }(jQuery);


            riskEval.bindFixed = function (fixedFreq, fixedFin, fixedCus, fixedRen, fixedSup, fixedEmp, fixedSev) {
                this._bind('fixed', fixedFreq, fixedFin, fixedCus, fixedRen, fixedSup, fixedEmp, fixedSev);
            };


            riskEval.bindRest = function (restFreq, restFin, restCus, restRen, restSup, restEmp, restSev) {
                this._bind('rest', restFreq, restFin, restCus, restRen, restSup, restEmp, restSev);
            };


            riskEval._bind = function (pre, freq, fin, cus, ren, sup, emp, sev) {
                this[pre + 'Freq'] = freq;
                this[pre + 'Fin'] = fin;
                this[pre + 'Cus'] = cus;
                this[pre + 'Ren'] = ren;
                this[pre + 'Sup'] = sup;
                this[pre + 'Emp'] = emp;
                this[pre + 'Sev'] = sev;

                try {
                    var _ids = '#' + this[pre + 'Freq']
                        + ',#' + this[pre + 'Fin']
                        + ',#' + this[pre + 'Cus']
                        + ',#' + this[pre + 'Ren']
                        + ',#' + this[pre + 'Sup']
                        + ',#' + this[pre + 'Emp'];

//					var _f = function(scope, pre)
//					{
//						return function(){
//							scope._setSeverity(pre);
//						};
//					}(this, pre);

                    var _that = this;
                    $(_ids).combobox({
                        onChange: function () {

                            /*
                             * 添加固有风险与剩余风险的约束，当固有风险的财务影响等级、客户服务影响等级、声誉影响等级、
                             * 法律/监管影响等级、员工影响等级存在不适用的情况，则剩余风险的对应项也为不适用；
                             * 并且剩余风险的对应项的等级要低于固有风险的项值
                             * modified at 2014-03-03
                             */
                            var _this = this;
                            if (!$(_this).combobox('getValue')) {
                                return false;
                            }

                            var flag = _that._limitFactor(pre, freq, fin, cus, ren, sup, emp, _this);
                            if (flag) {
                                _that._setSeverity(pre);
                            }
                        }
                    });
                    $('#' + this[pre + 'Sev']).combobox('disable');

                } catch (e) {
                }

            };

            riskEval.setFixedSeverity = function () {
                this._setSeverity('fixed');
            };

            riskEval.setRestSeverity = function () {
                this._setSeverity('rest');
            };


            riskEval._setSeverity = function (pre) {
                var _freq = this._getComboValue(this[pre + 'Freq']);
                var _fin = this._getComboValue(this[pre + 'Fin']);
                var _cus = this._getComboValue(this[pre + 'Cus']);
                var _ren = this._getComboValue(this[pre + 'Ren']);
                var _sup = this._getComboValue(this[pre + 'Sup']);
                var _emp = this._getComboValue(this[pre + 'Emp']);
                var _sev = this[pre + 'Sev'];

                if (this.map == null || !_freq || (!_fin && !_cus && !_ren && !_sup && !_emp)) {
                    $("#" + _sev).combobox("setValue", '');
                    var textbox = $("#" + _sev).combobox("textbox");
                    $(textbox).css("background", '#FFFFFF');
                    return;
                }

                var _arr = [];
                if (_fin) _arr.push(_fin);
                if (_cus) _arr.push(_cus);
                if (_ren) _arr.push(_ren);
                if (_sup) _arr.push(_sup);
                if (_emp) _arr.push(_emp);
                $.sort.insertSort(_arr);

                var _val = _arr.pop();

                for (var i = 0; i < this.map.length; i++) {
                    var _m = this.map[i];
                    if (_m.riskFreq == _freq && _m.riskFinance == _val) {
                        $("#" + _sev).combobox("setValue", _m.riskSeverity);
                        var textbox = $("#" + _sev).combobox("textbox");
                        $(textbox).css("background", _m.rgb == null ? '#FFFFFF' : _m.rgb);
                        return;
                    }
                }

                $("#" + _sev).combobox("setValue", '');
                var textbox = $("#" + _sev).combobox("textbox");
                $(textbox).css("background", '#FFFFFF');
                return;
            };


            riskEval._getComboValue = function (comboId) {
                if (comboId) {
                    try {
                        return $('#' + comboId).combobox('getValue');
                    } catch (e) {
                    }
                }
            };

            /**
             * 固有风险与剩余风险约束关系
             * modified at 2014-03-03
             */
            riskEval._limitFactor = function (pre, freq, fin, cus, ren, sup, emp, that) {
                var flag = true;
                var _value = $(that).combobox('getValue');
                var _id = $(that).attr('id');
                var preTemp = 'fixed';
                if (pre == 'fixed') {
                    preTemp = 'rest';
                    switch (_id) {
                        case freq: {
                            var _rFreq = this._getComboValue(this[preTemp + 'Freq']);
                            flag = this._message(_value, _rFreq, '不低于剩余风险', that);
                            break;
                        }
                        case fin: {
                            var _rfin = this._getComboValue(this[preTemp + 'Fin']);
                            flag = this._setDefaultValue(_value, pre, preTemp, 'Fin');
                            if (flag) {
                                flag = this._message(_value, _rfin, '不小于剩余风险', that);
                            }
                            break;
                        }
                        case cus: {
                            var _rcus = this._getComboValue(this[preTemp + 'Cus']);
                            flag = this._setDefaultValue(_value, pre, preTemp, 'Cus');
                            if (flag) {
                                flag = this._message(_value, _rcus, '不小于剩余风险', that);
                            }
                            break;
                        }
                        case ren: {
                            var _rren = this._getComboValue(this[preTemp + 'Ren']);
                            flag = this._setDefaultValue(_value, pre, preTemp, 'Ren');
                            if (flag) {
                                flag = this._message(_value, _rren, '不小于剩余风险', that);
                            }
                            break;
                        }
                        case sup: {
                            var _rsup = this._getComboValue(this[preTemp + 'Sup']);
                            flag = this._setDefaultValue(_value, pre, preTemp, 'Sup');
                            if (flag) {
                                flag = this._message(_value, _rsup, '不小于剩余风险', that);
                            }
                            break;
                        }
                        case emp: {
                            var _remp = this._getComboValue(this[preTemp + 'Emp']);
                            flag = this._setDefaultValue(_value, pre, preTemp, 'Emp');
                            if (flag) {
                                flag = this._message(_value, _remp, '不小于剩余风险', that);
                            }
                            break;
                        }
                    }
                } else {
                    switch (_id) {
                        case freq: {
                            var _freq = this._getComboValue(this[preTemp + 'Freq']);
                            flag = this._message(_freq, _value, '不高于固有风险', that, pre);
                            break;
                        }
                        case fin: {
                            var _ffin = this._getComboValue(this[preTemp + 'Fin']);
                            flag = this._message(_ffin, _value, '不大于固有风险', that, pre);
                            break;
                        }
                        case cus: {
                            var _fcus = this._getComboValue(this[preTemp + 'Cus']);
                            flag = this._message(_fcus, _value, '不大于固有风险', that, pre);
                            break;
                        }
                        case ren: {
                            var _fren = this._getComboValue(this[preTemp + 'Ren']);
                            flag = this._message(_fren, _value, '不大于固有风险', that, pre);
                            break;
                        }
                        case sup: {
                            var _fsup = this._getComboValue(this[preTemp + 'Sup']);
                            flag = this._message(_fsup, _value, '不大于固有风险', that, pre);
                            break;
                        }
                        case emp: {
                            var _femp = this._getComboValue(this[preTemp + 'Emp']);
                            flag = this._message(_femp, _value, '不大于固有风险', that, pre);
                            break;
                        }
                    }
                }
                return flag;
            };

            riskEval._setDefaultValue = function (_value, pre, preTemp, subfix) {
                var _flag = true;
                if (_value == '0') {
                    $('#' + this[preTemp + subfix]).combobox('setValue', '0');
                    _flag = false;
                } else {
                    var value = $('#' + this[preTemp + subfix]).combobox('getValue');
                    if (value == '0') {
                        $('#' + this[preTemp + subfix]).combobox('setValue', '');
                        _flag = false;
                    }
                }
                return _flag;
            };

            riskEval._message = function (_value, _other, msg, that, flag) {
                var _flag = true;
                if (flag && flag == 'rest') {
                    if (!_value) {
                        $.messager.alert('提示', '尚未评估固定风险', 'info', function () {
                            $(that).combobox('setValue', '');
                        });
                        _flag = false;
                        return _flag;
                    }
                    //如果固定风险为”不适用“，则剩余风险也为”不适用“
                    else if (_value == '0' && _other != '0') {
                        $.messager.alert('提示', '剩余风险项只能为【不适用】', 'info', function () {
                            $(that).combobox('setValue', '0');
                        });

                        _flag = false;
                        return _flag;
                    }
                    //固定风险如果不为”不适用“，则剩余风险也不能选择”不适用“
                    else if (_value != '0' && _other == '0') {
                        $.messager.alert('提示', '剩余风险应与固定风险保持一致，不应选择【不适用】', 'info', function () {
                            $(that).combobox('setValue', '');
                        });

                        _flag = false;
                        return _flag;
                    }
                }

                if (_other && _value) {
                    if (_value < _other) {
                        $.messager.alert('提示', '选择值应该' + msg + '对应项值', 'info', function () {
                            $(that).combobox('setValue', '');
                        });

                        _flag = false;
                        return _flag;
                    }
                }

                return _flag;
            };

            riskEval.validateEval = function (fixedFreq, fixedFin, fixedCus, fixedRen, fixedSup, fixedEmp, fixedSev,
                                              restFreq, restFin, restCus, restRen, restSup, restEmp, restSev, restResave) {
                var msg = "";
                var message = new Array();
                if (!this._getComboValue(fixedFreq)) {
                    message.push('【风险发生频率】');
                }
                if (!this._getComboValue(fixedFin)) {
                    message.push('【财务影响等级】');
                }
                if (!this._getComboValue(fixedCus)) {
                    message.push('【客户服务影响等级】');
                }
                if (!this._getComboValue(fixedRen)) {
                    message.push('【声誉影响等级】');
                }
                if (!this._getComboValue(fixedSup)) {
                    message.push('【法律/监管影响等级】');
                }
                if (!this._getComboValue(fixedEmp)) {
                    message.push('【员工影响等级】');
                }
//				if(!this._getComboValue(fixedSev)) {
//					message.push('');
//				}
                if (message.length > 0) {
                    msg += '固有风险' + message.join(',') + '不能为空；<br/>';
                }
                var mss = new Array();
                if (!this._getComboValue(restFreq)) {
                    mss.push('【风险发生频率】');
                }
                if (!this._getComboValue(restFin)) {
                    mss.push('【财务影响等级】');
                }
                if (!this._getComboValue(restCus)) {
                    mss.push('【客户服务影响等级】');
                }
                if (!this._getComboValue(restRen)) {
                    mss.push('【声誉影响等级】');
                }
                if (!this._getComboValue(restSup)) {
                    mss.push('【法律/监管影响等级】');
                }
                if (!this._getComboValue(restEmp)) {
                    mss.push('【员工影响等级】');
                }
                if (!this._getComboValue(restResave)) {
                    mss.push('【风险可接受程度】');
                }
                if (mss.length > 0) {
                    msg += '剩余风险' + mss.join(',') + '不能为空';
                }
//				if(!this._getComboValue(restSev)) {
//					message.push('');
//				}
                return msg;
            };

            riskEval.validate = function (fixedFreq, fixedFin, fixedCus, fixedRen, fixedSup, fixedEmp, fixedSev,
                                          restFreq, restFin, restCus, restRen, restSup, restEmp, restSev, restResave) {
                var msg = this.validateEval(fixedFreq, fixedFin, fixedCus, fixedRen, fixedSup, fixedEmp, fixedSev,
                    restFreq, restFin, restCus, restRen, restSup, restEmp, restSev, restResave);
                if (msg) {
                    $.messager.alert('提示', msg, 'warning');
                    return false;
                }

                //控制综合评级说明判断：如果控制综合评级不是“控制有效”，需要填写控制综合评级说明
                var ctrlOverAllGrade = $('#ctrlOverAllGrade').combobox('getValue');
                if (!ctrlOverAllGrade) {
                    $.messager.alert('提示', '请选择控制综合评级', 'info');
                    return false;
                }

                var ctrlOverAllGradeDesc = $('#ctrlOverAllGradeDesc').val();
                if (ctrlOverAllGrade != '1' && !ctrlOverAllGradeDesc) {
                    $.messager.alert('提示', '请填写控制综合评级说明', 'info');
                    return false;
                }

                //判断剩余风险严重度和风险可接受程度是否匹配
                var restResave = $('#restRiskBeResave').combobox('getValue');
                var restSeverity = $('#restRiskOrderOfSeverity').combobox('getValue');
                if (restResave == '3' && (restSeverity == '4' || restSeverity == '5')) {
                    $.messager.alert('提示', '请选择合适的风险可接受程度', 'info');
                    return false;
                }

                return true;
            };

            return riskEval;
        }
    });

})(jQuery);