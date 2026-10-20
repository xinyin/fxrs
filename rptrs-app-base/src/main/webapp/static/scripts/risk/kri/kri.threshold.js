/**
 * 扩展jquery.kri，增加指标处理
 * 1. 增加处理指标预警范围确定
 * @param $
 */
;(function ($) {

    jQuery.risk = $.extend({}, jQuery.risk, {

        /*kri 相关工具方法*/
        kri: {

            evalThreshold: function (indicatorId, value) {
                $.cache.cache('custom'
                    , contextPath + '/kri/indicatorThreshold/getThresholds?indicatorId=' + indicatorId
                    , indicatorId);

                var thresholds = $.cache.getCache(indicatorId);

                var grade = {gradeCode: null, gradeName: null, gradeValue: '指标值超出预警范围', gradeColor: '#FFFFFF'};
                if (thresholds) {
                    for (var i = 0; i < thresholds.length; i++) {
                        var threshold = thresholds[i];
                        if (value >= threshold.minValue && value < threshold.maxValue) {
                            grade = {
                                gradeCode: threshold.kriWarnStatus.gradeCode,
                                gradeName: threshold.kriWarnStatus.gradeName,
                                gradeValue: threshold.kriWarnStatus.displayValue,
                                gradeColor: threshold.kriWarnStatus.gradeColor
                            };
                        }
                    }
                }
                return grade;
            }
        }
    });

})(jQuery);