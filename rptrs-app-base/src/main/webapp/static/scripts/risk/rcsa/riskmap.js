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

            console.info('risk:', riskEval);

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

                    var _f = function (scope, pre) {
                        return function () {
                            scope._setSeverity(pre);
                        };
                    }(this, pre);

                    console.info(_ids);
                    $(_ids).combobox({onChange: _f});
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
                console.info('pre:', pre);
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


            return riskEval;
        }
    });

})(jQuery);