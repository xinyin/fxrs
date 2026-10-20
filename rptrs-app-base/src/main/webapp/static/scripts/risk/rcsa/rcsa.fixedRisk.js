/**
 * 扩展jquery.risk.rcsa，增加固有风险评估标准相关函数
 * @param $
 */
;(function ($) {

    jQuery.risk = $.extend({}, jQuery.risk, {});

    jQuery.risk.rcsa = $.extend({}, jQuery.risk.rcsa, {

        /*rcsa 相关工具方法*/
        fixedRisk: {

            /*计算固有风险评估结果, p = possibility, e = effect*/
            eval: function (p, e) {
                $.cache.cache('custom'
                    , contextPath + '/std/fixedRiskMesure/getFixedRiskMesure', 'fixedRiskMesure');

                var mesures = $.cache.getCache('fixedRiskMesure');

                var grade = {gradeCode: null, gradeName: null, gradeValue: '未评估', gradeColor: '#FFFFFF'};
                if (mesures) {
                    for (var i = 0; i < mesures.length; i++) {
                        var mesure = mesures[i];
                        if (p == mesure.possibilityCode && e == mesure.effectCode) {
                            grade = {
                                gradeCode: mesure.grade.gradeCode,
                                gradeName: mesure.grade.gradeName,
                                gradeValue: mesure.grade.displayValue + '-' + mesure.grade.gradeName,
                                gradeColor: mesure.grade.gradeColor
                            };
                        }
                    }
                }
                return grade;
            },

            render: function (target, p, e) {
                var div = $('#' + target);
                var grade = this.eval(p, e);
                if (!grade || !grade.gradeValue) {
                    div.html('<span></span>');
                } else {
                    div.html('<span style="margin:0;padding:0;width:100%;height:100%;background-color:' + grade.gradeColor + ';text-align:center;border:solid 1px;">' + grade.gradeValue + '</span>');
                }
                return grade;
            }
        }
    });

})(jQuery);