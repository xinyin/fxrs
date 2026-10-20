/**
 * 扩展jquery.risk.rcsa，增加整体控制有效性相关函数
 * @param $
 */
;(function ($) {

    jQuery.risk = $.extend({}, jQuery.risk, {});
    jQuery.risk.rcsa = $.extend({}, jQuery.risk.rcsa, {

        /*rcsa 相关工具方法*/
        controlGrade: {

            /*计算整体控制有效性结果, d = design, e = exec*/
            eval: function (d, e) {
                $.cache.cache('custom'
                    , contextPath + '/std/controlMesure/getControlMesure', 'controlMesure');

                var mesures = $.cache.getCache('controlMesure');

                var grade = {
                    gradeCode: null,
                    gradeName: null,
                    gradeValue: '未评估',
                    gradeColor: '#FFFFFF',
                    gradeCalcValue: 0
                };
                if (mesures) {
                    for (var i = 0; i < mesures.length; i++) {
                        var mesure = mesures[i];
                        if (d == mesure.controlDesignGradeCode && e == mesure.controlExecGradeCode) {
                            grade = {
                                gradeCode: mesure.grade.gradeCode,
                                gradeName: mesure.grade.gradeName,
                                gradeValue: mesure.grade.displayValue + '-' + mesure.grade.gradeName,
                                gradeColor: mesure.grade.gradeColor,
                                gradeCalcValue: mesure.grade.calcValue
                            };
                        }
                    }
                }
                return grade;
            },

            render: function (target, d, e) {
                var div = $('#' + target);
                var label = $('#' + target + "CalcValue");
                var grade = this.eval(d, e);
                if (!grade || !grade.gradeValue) {
                    div.html('<span></span>');
                    label.html('<label style="color:#FF0000; font-size:24px;font-weight:bold">' + 0 + '</label>');
                } else {
                    div.html('<span style="margin:0;padding:0;width:100%;height:100%;background-color:' + grade.gradeColor + ';text-align:center;border:solid 1px;">' + grade.gradeValue + '</span>');
                    label.html('<label style="color:#FF0000; font-size:24px;font-weight:bold">' + grade.gradeCalcValue + '</label>');
                }

                return grade;
            }
        }
    });

})(jQuery);