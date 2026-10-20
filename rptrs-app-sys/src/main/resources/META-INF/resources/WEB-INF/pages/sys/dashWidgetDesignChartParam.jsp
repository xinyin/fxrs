<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true"
     style="overflow-y: auto; overflow-x: hidden; height: 550px;">
    <div class="panel panel-default" style="margin-top: 5px;">
        <div class="panel-heading" role="tab" id="headingOne">
            <h4 class="panel-title">
                <a role="button" data-toggle="collapse" data-parent="#accordion"
                   href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    基础
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body tabbable body-div">
                <ul class='nav nav-tabs param-tab' id="baseTabs">
                    <li class='base-tab-list active'><a href='#baseTab' data-toggle='tab'>通用</a></li>
                    <li class='base-tab-list'><a href='#baseTab0' data-toggle='tab'>统计项</a></li>
                </ul>
                <div class='tab-content param-content' id="tabContent">
                    <div class='tab-pane active' id='baseTab'>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="animationCheck">
                                <bf:check change="animationCheckChange(this, 'animation')"/>
                            </bf:checkGroup>
                            <bc:label text="动画(建议IE8关闭)" col="4" formControl="true"/>
                            <bf:checkGroup col="7" name="animation" value="Y" disabled="true">
                                <bf:radio dataSource="fixedDict" url="YesNo" change="animationChange(this)"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="chartsBgColorCheck">
                                <bf:check change="chartsBgColorCheckChange(this, 'chartsBgClrpkr')"/>
                            </bf:checkGroup>
                            <bc:label text="图整体背景颜色" col="4" formControl="true"/>
                            <bf:text id="chartsBgClrpkr" name="chartsBgColor" col="7" disabled="true"
                                     value="rgb(255, 255, 255)"/>
                        </bf:formGroup>

                        <!-- 	直系图通用设置       -->
                        <div class="div-line-fg">
                            <bf:formGroup hr="dashed" hrp="top">
                                <bf:checkGroup col="1" name="seriesChartsTypeNormalCheck" disabled="true">
                                    <bf:check checked="true"/>
                                </bf:checkGroup>
                                <bc:label text="系列的图形类型" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="line2SmoothCurveN" value="line">
                                    <bf:radio label="柱" value="bar" change="seriesChartsTypeNormal(this)"/>
                                    <bf:radio label="折线" value="line" change="seriesChartsTypeNormal(this)"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="line2SmoothCurveCheck">
                                    <bf:check change="line2SmoothCurveChange(this, 'line2SmoothCurve')"/>
                                </bf:checkGroup>
                                <bc:label text="折线转为平滑曲线" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="line2SmoothCurve" value="N" disabled="true">
                                    <bf:radio dataSource="fixedDict" url="YesNo" change="line2SmoothChange(this)"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                        </div>
                        <!-- 	直系图、雷达通用设置       -->
                        <div class="div-line-fg div-radar-fg">
                            <bf:formGroup hr="dashed" hrp="top">
                                <bf:checkGroup col="1" name="symbolSizeCheck">
                                    <bf:check change="symbolSizeCheckChange(this, 'symbolSize')"/>
                                </bf:checkGroup>
                                <bc:label text="小标志大小" col="4" formControl="true"/>
                                <bf:touchpin id="symbolSize" name="symbolSize" col="7" change="symbolSizeChange(this)"
                                             disabled="true" min="2" max="20" step="1" value="2"/>
                            </bf:formGroup>
                        </div>
                    </div>
                    <div class='tab-pane' id='baseTab0'>
                        <bf:formGroup id="seriesBtnFg">
                            <bc:col col="12" cls="margin-top-Fg"></bc:col>
                        </bf:formGroup>

                        <!-- 	直系图   （系列n）    -->
                        <div class="div-line-fg">
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="seriesChartsTypeCheck" disabled="true">
                                    <bf:check checked="true"/>
                                </bf:checkGroup>
                                <bc:label text="系列的图形类型" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="seriesTypeValue" value="line">
                                    <bf:radio label="折线" value="line" change="seriesChartsType(this,'type')"/>
                                    <bf:radio label="柱" value="bar" change="seriesChartsType(this,'type')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup hr="solid" cls="bar-div">
                                <bf:checkGroup col="1" name="smoothCurveCheck">
                                    <bf:check change="seriesFirstPCheckChange(this, 'smoothCurve', 'smooth')"/>
                                </bf:checkGroup>
                                <bc:label text="折线转为平滑曲线" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="smoothCurve" value="N" disabled="true">
                                    <bf:radio dataSource="fixedDict" url="YesNo"
                                              change="seriesFirstPChange(this,'smooth')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                        </div>

                        <!-- 	饼图设置       -->
                        <div class="div-pie-fg">
                            <bf:formGroup cls="title-tag-grp">
                                <bc:label text="饼图设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="pieRoseTypeCheck">
                                    <bf:check change="seriesFirstPCheckChange(this, 'pieRoseType', 'roseType')"/>
                                </bf:checkGroup>
                                <bc:label text="南丁格尔玫瑰图模式" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="pieRoseType" value="radius" disabled="true">
                                    <bf:radio label="半径模式" value="radius"
                                              change="seriesFirstPChange(this, 'roseType')"/>
                                    <bf:radio label="面积模式" value="area" change="seriesFirstPChange(this, 'roseType')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup cls="margin-small">
                                <bf:checkGroup col="1" name="pieCenterCheck">
                                    <bf:check change="seriesSliderXYChange(this,'pieCenterX','pieCenterY','center')"/>
                                </bf:checkGroup>
                                <bc:label text="饼图圆心坐标" col="4" formControl="true" cls="slider-label-x"/>
                                <span>x</span><span>:</span>
                                <input id="pieCenterX" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                                <span id="pieCenterXVal">50</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup cls="slider-margin-large">
                                <bc:label text="" col="5" formControl="true" cls="slider-label-y"/>
                                <span>y</span><span>:</span>
                                <input id="pieCenterY" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                                <span id="pieCenterYVal">50</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="pieRadiusCheck">
                                    <bf:check change="seriesSingleSliderChange(this,'pieRadius','radius')"/>
                                </bf:checkGroup>
                                <bc:label text="饼图半径" col="4" formControl="true"/>
                                <input id="pieRadius" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="60" data-slider-enabled="false"/>
                                <span id="pieRadiusXVal">60</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="startAngleCheck">
                                    <bf:check change="seriesSingleSliderChange(this,'startAngle','startAngle')"/>
                                </bf:checkGroup>
                                <bc:label text="饼图起始角度" col="4" formControl="true"/>
                                <input id="startAngle" type="text" data-slider-min="0" data-slider-max="180"
                                       data-slider-step="6" data-slider-value="90" data-slider-enabled="false"/>
                                <span id="startAngleVal">90</span><span> °</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="minAngleCheck">
                                    <bf:check change="seriesSingleSliderChange(this,'minAngle','minAngle')"/>
                                </bf:checkGroup>
                                <bc:label text="饼图最小角度" col="4" formControl="true"/>
                                <input id="minAngle" type="text" data-slider-min="0" data-slider-max="180"
                                       data-slider-step="6" data-slider-value="50" data-slider-enabled="false"/>
                                <span id="minAngleVal">50</span><span> °</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="selectedModeCheck">
                                    <bf:check change="seriesFirstPCheckChange(this, 'selectedMode', 'selectedMode')"/>
                                </bf:checkGroup>
                                <bc:label text="鼠标点选模式" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="selectedMode" value="none" disabled="true">
                                    <bf:radio label="不可选" value="none"
                                              change="seriesFirstPChange(this, 'selectedMode')"/>
                                    <bf:radio label="单选" value="single"
                                              change="seriesFirstPChange(this, 'selectedMode')"/>
                                    <bf:radio label="多选" value="multiple"
                                              change="seriesFirstPChange(this, 'selectedMode')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup hr="solid">
                                <bf:checkGroup col="1" name="selectedOffsetCheck">
                                    <bf:check
                                            change="seriesSingleSliderChange(this,'selectedOffset','selectedOffset')"/>
                                </bf:checkGroup>
                                <bc:label text="选中时扇区偏移量" col="4" formControl="true"/>
                                <input id="selectedOffset" type="text" data-slider-min="0" data-slider-max="180"
                                       data-slider-step="6" data-slider-value="10" data-slider-enabled="false"/>
                                <span id="selectedOffsetVal">10</span><span> °</span>
                            </bf:formGroup>
                        </div>

                        <!-- 	系列设置:普通、高亮       -->
                        <div class="tabbable div-line-fg div-pie-fg div-radar-fg"
                             style="margin-left: 15px; margin-right: 15px;">
                            <ul class='nav nav-tabs param-tab'>
                                <li class='active'><a href='#normalTab' data-toggle='tab'>普通状态（normal）</a></li>
                                <li><a href='#emphasisTab' data-toggle='tab'>高亮状态（emphasis）</a></li>
                            </ul>
                        </div>
                        <div class='tab-content param-content div-line-fg div-pie-fg div-radar-fg'>
                            <div class='tab-pane active' id='normalTab'>

                                <!-- 	直系图、饼图、雷达图  	  -->
                                <bf:formGroup cls="div-line-fg div-pie-fg div-radar-fg">
                                    <bf:checkGroup col="1" name="color4ASeries">
                                        <bf:check
                                                change="seriesColorChange(this,'aSeriesClrpkr','itemStyle','normal','color')"/>
                                    </bf:checkGroup>
                                    <bc:label text="系列特定颜色" col="4" formControl="true"/>
                                    <bf:text id="aSeriesClrpkr" name="" col="7" disabled="true"
                                             value="rgb(183, 51, 51)"/>
                                </bf:formGroup>

                                <!-- 	直系图  	  -->
                                <div class="div-line-fg bar-div">
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="lineColor">
                                            <bf:check
                                                    change="lineColorChange(this,'lineClrpkr','normal','lineStyle','color')"/>
                                        </bf:checkGroup>
                                        <bc:label text="线条颜色" col="4" formControl="true"/>
                                        <bf:text id="lineClrpkr" name="" col="7" disabled="true"
                                                 value="rgb(183, 51, 51)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="lineWidthCheck">
                                            <bf:check
                                                    change="lineWidthCheckChange(this,'lineWidth','normal','lineStyle','width')"/>
                                        </bf:checkGroup>
                                        <bc:label text="线条宽度" col="4" formControl="true"/>
                                        <bf:touchpin id="lineWidth" name="lineWidth" col="7" disabled="true" min="0"
                                                     max="20" step="1" value="2"
                                                     change="lineWidthChange(this,'normal','lineStyle','width')"/>
                                    </bf:formGroup>
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                        <bc:label text="小标志边框属性" col="4" formControl="true"/>
                                    </bf:formGroup>
                                </div>

                                <!-- 	饼图  	  -->
                                <div class="div-pie-fg bar-div-show">
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                        <bc:label text="边框属性" col="4" formControl="true"/>
                                    </bf:formGroup>
                                </div>

                                <!-- 	直系图、饼图  	  -->
                                <div class="div-line-fg div-pie-fg">
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="borderColor">
                                            <bf:check
                                                    change="seriesColorChange(this,'borderClrpkr','itemStyle','normal','borderColor')"/>
                                        </bf:checkGroup>
                                        <bc:label text="边框颜色" col="4" formControl="true"/>
                                        <bf:text id="borderClrpkr" name="" col="7" disabled="true"
                                                 value="rgb(183, 51, 51)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="borderWidthCheck">
                                            <bf:check
                                                    change="seriesBorderWthCheckChange(this,'borderWidth','itemStyle','normal','borderWidth')"/>
                                        </bf:checkGroup>
                                        <bc:label text="边框宽度" col="4" formControl="true"/>
                                        <bf:touchpin id="borderWidth" name="borderWidth" col="7" disabled="true" min="0"
                                                     max="20"
                                                     step="1" value="2"
                                                     change="seriesBorderWthChange(this,'itemStyle','normal','borderWidth')"/>
                                    </bf:formGroup>
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                        <bc:label text="数据项标签" col="4" formControl="true"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="showLabelCheck">
                                            <bf:check
                                                    change="labelForthCheckChange(this,'showLabel','normal','label','show')"/>
                                        </bf:checkGroup>
                                        <bc:label text="是否显示数据项标签" col="4" formControl="true"/>
                                        <bf:checkGroup col="7" name="showLabel" value="N" disabled="true">
                                            <bf:radio dataSource="fixedDict" url="YesNo"
                                                      change="labelForthChange(this,'normal','label','show')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelPositionCheck">
                                            <bf:check
                                                    change="labelForthCheckChange(this,'labelPosition','normal','label','position')"/>
                                        </bf:checkGroup>
                                        <bc:label text="项标签显示位置" col="4" formControl="true"/>
                                        <bf:checkGroup col="5" name="labelPosition" value="top" disabled="true">
                                            <bf:radio label="居下" value="bottom"
                                                      change="labelForthChange(this,'normal','label','position')"/>
                                            <bf:radio label="居上" value="top"
                                                      change="labelForthChange(this,'normal','label','position')"/>
                                            <bf:radio label="居左" value="left"
                                                      change="labelForthChange(this,'normal','label','position')"/>
                                            <bf:radio label="居右" value="right"
                                                      change="labelForthChange(this,'normal','label','position')"
                                                      cls="align-radio"/>
                                            <bf:radio label="内部" value="inside"
                                                      change="labelForthChange(this,'normal','label','position')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelColor">
                                            <bf:check change="labelColorChange(this,'labelClrpkr','normal','color')"/>
                                        </bf:checkGroup>
                                        <bc:label text="文本颜色" col="4" formControl="true"/>
                                        <bf:text id="labelClrpkr" name="" col="7" disabled="true"
                                                 value="rgb(255, 255, 255)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelSizeCheck">
                                            <bf:check
                                                    change="labelSizeCheckChange(this,'labelSize','normal','fontSize')"/>
                                        </bf:checkGroup>
                                        <bc:label text="字体大小" col="4" formControl="true"/>
                                        <bf:touchpin id="labelSize" name="labelSize" col="7" disabled="true"
                                                     min="10" max="25" step="1" value="12"
                                                     change="labelSizeChange(this,'normal','fontSize')"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelStyleCheck">
                                            <bf:check
                                                    change="labelStyleCheckChange(this,'labelStyle','normal','fontStyle')"/>
                                        </bf:checkGroup>
                                        <bc:label text="字体样式" col="4" formControl="true"/>
                                        <bf:checkGroup col="2" name="labelStyleBold" disabled="true">
                                            <bf:check label="加粗" value="bold"
                                                      change="labelStyleBoldChange(this,'normal','fontWeight')"/>
                                        </bf:checkGroup>
                                        <bf:checkGroup col="4" name="labelStyle" value="normal" disabled="true">
                                            <bf:radio label="普通" value="normal"
                                                      change="labelStyleChange(this,'normal','fontStyle')"/>
                                            <bf:radio label="斜体" value="italic"
                                                      change="labelStyleChange(this,'normal','fontStyle')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                </div>
                            </div>
                            <div class='tab-pane' id='emphasisTab'>

                                <!-- 	直系图、饼图、雷达图  	  -->
                                <bf:formGroup cls="div-line-fg div-pie-fg div-radar-fg">
                                    <bf:checkGroup col="1" name="color4ASeriesEmph">
                                        <bf:check
                                                change="seriesColorChange(this,'aSeriesClrpkrEmph','itemStyle','emphasis','color')"/>
                                    </bf:checkGroup>
                                    <bc:label text="系列特定颜色" col="4" formControl="true"/>
                                    <bf:text id="aSeriesClrpkrEmph" name="" col="7" disabled="true"
                                             value="rgb(183, 51, 51)"/>
                                </bf:formGroup>

                                <!-- 	直系图  	  -->
                                <div class="div-line-fg bar-div">
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="lineColorEmph">
                                            <bf:check
                                                    change="lineColorChange(this,'lineClrpkrEmph','emphasis','lineStyle','color')"/>
                                        </bf:checkGroup>
                                        <bc:label text="线条颜色" col="4" formControl="true"/>
                                        <bf:text id="lineClrpkrEmph" name="" col="7" disabled="true"
                                                 value="rgb(183, 51, 51)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="lineWidthCheckEmph">
                                            <bf:check
                                                    change="lineWidthCheckChange(this,'lineWidthEmph','emphasis','lineStyle','width')"/>
                                        </bf:checkGroup>
                                        <bc:label text="线条宽度" col="4" formControl="true"/>
                                        <bf:touchpin id="lineWidthEmph" name="lineWidthEmph" col="7" disabled="true"
                                                     min="0"
                                                     max="20" step="1" value="2"
                                                     change="lineWidthChange(this,'emphasis','lineStyle','width')"/>
                                    </bf:formGroup>
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp div-line-fg">
                                        <bc:label text="小标志边框属性" col="4" formControl="true"/>
                                    </bf:formGroup>
                                </div>

                                <!-- 	饼图  	  -->
                                <div class="div-pie-fg bar-div-show">
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                        <bc:label text="边框属性" col="4" formControl="true"/>
                                    </bf:formGroup>
                                </div>

                                <!-- 	直系图、饼图  	  -->
                                <div class="div-line-fg div-pie-fg">
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="borderColorEmph">
                                            <bf:check
                                                    change="seriesColorChange(this,'borderClrpkrEmph','itemStyle','emphasis','borderColor')"/>
                                        </bf:checkGroup>
                                        <bc:label text="边框颜色" col="4" formControl="true"/>
                                        <bf:text id="borderClrpkrEmph" name="" col="7" disabled="true"
                                                 value="rgb(183, 51, 51)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="borderWidthCheckEmph">
                                            <bf:check
                                                    change="seriesBorderWthCheckChange(this,'borderWidthEmph','itemStyle','emphasis','borderWidth')"/>
                                        </bf:checkGroup>
                                        <bc:label text="边框宽度" col="4" formControl="true"/>
                                        <bf:touchpin id="borderWidthEmph" name="borderWidthEmph" col="7" disabled="true"
                                                     min="0" max="20"
                                                     step="1" value="2"
                                                     change="seriesBorderWthChange(this,'itemStyle','emphasis','borderWidth')"/>
                                    </bf:formGroup>
                                    <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                        <bc:label text="数据项标签" col="4" formControl="true"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="showLabelCheckEmph">
                                            <bf:check
                                                    change="labelForthCheckChange(this,'showLabelEmph','emphasis','label','show')"/>
                                        </bf:checkGroup>
                                        <bc:label text="是否显示数据项标签" col="4" formControl="true"/>
                                        <bf:checkGroup col="7" name="showLabelEmph" value="N" disabled="true">
                                            <bf:radio dataSource="fixedDict" url="YesNo"
                                                      change="labelForthChange(this,'emphasis','label','show')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelPositionCheckEmph">
                                            <bf:check
                                                    change="labelForthCheckChange(this,'labelPositionEmph','emphasis','label','position')"/>
                                        </bf:checkGroup>
                                        <bc:label text="项标签显示位置" col="4" formControl="true"/>
                                        <bf:checkGroup col="5" name="labelPositionEmph" value="top" disabled="true">
                                            <bf:radio label="居下" value="bottom"
                                                      change="labelForthChange(this,'emphasis','label','position')"/>
                                            <bf:radio label="居上" value="top"
                                                      change="labelForthChange(this,'emphasis','label','position')"/>
                                            <bf:radio label="居左" value="left"
                                                      change="labelForthChange(this,'emphasis','label','position')"/>
                                            <bf:radio label="居右" value="right"
                                                      change="labelForthChange(this,'emphasis','label','position')"
                                                      cls="align-radio"/>
                                            <bf:radio label="内部" value="inside"
                                                      change="labelForthChange(this,'emphasis','label','position')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelColorEmph">
                                            <bf:check
                                                    change="labelColorChange(this,'labelClrpkrEmph','emphasis','color')"/>
                                        </bf:checkGroup>
                                        <bc:label text="文本颜色" col="4" formControl="true"/>
                                        <bf:text id="labelClrpkrEmph" name="" col="7" disabled="true"
                                                 value="rgb(255, 255, 255)"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelSizeCheckEmph">
                                            <bf:check
                                                    change="labelSizeCheckChange(this,'labelSizeEmph','emphasis','fontSize')"/>
                                        </bf:checkGroup>
                                        <bc:label text="字体大小" col="4" formControl="true"/>
                                        <bf:touchpin id="labelSizeEmph" name="labelSizeEmph" col="7" disabled="true"
                                                     min="10" max="25" step="1" value="12"
                                                     change="labelSizeChange(this,'emphasis','fontSize')"/>
                                    </bf:formGroup>
                                    <bf:formGroup>
                                        <bf:checkGroup col="1" name="labelStyleCheckEmph">
                                            <bf:check
                                                    change="labelStyleCheckChange(this,'labelStyleEmph','emphasis','fontStyle')"/>
                                        </bf:checkGroup>
                                        <bc:label text="字体样式" col="4" formControl="true"/>
                                        <bf:checkGroup col="2" name="labelStyleEmphBold" disabled="true">
                                            <bf:check label="加粗" value="bold"
                                                      change="labelStyleBoldChange(this,'emphasis','fontWeight')"/>
                                        </bf:checkGroup>
                                        <bf:checkGroup col="4" name="labelStyleEmph" value="normal" disabled="true">
                                            <bf:radio label="普通" value="normal"
                                                      change="labelStyleChange(this,'emphasis','fontStyle')"/>
                                            <bf:radio label="斜体" value="italic"
                                                      change="labelStyleChange(this,'emphasis','fontStyle')"/>
                                        </bf:checkGroup>
                                    </bf:formGroup>
                                </div>
                            </div>
                        </div>

                        <!-- 	直系图、雷达       -->
                        <div class="div-line-fg div-radar-fg">
                            <div class="hr-line-solid div-line-fg div-radar-fg"></div>
                            <bf:formGroup cls="div-line-fg">
                                <bf:checkGroup col="1" name="stackCheck">
                                    <bf:check change="stackCheckChange(this, 'stack', 'stack')"/>
                                </bf:checkGroup>
                                <bc:label text="堆积名称" col="4" formControl="true"/>
                                <bf:text id="stack" name="stack" value="总量" col="7" disabled="true"
                                         helpText="同名系列会被堆积"/>
                            </bf:formGroup>
                            <div class="bar-div">
                                <div class="hr-line-dashed div-line-fg"></div>
                                <bf:formGroup cls="title-tag-grp">
                                    <bc:label text="小标志设置" col="4" formControl="true"/>
                                </bf:formGroup>
                                <bf:formGroup>
                                    <bf:checkGroup col="1" name="symbolCheck">
                                        <bf:check change="seriesFirstPCheckChange(this, 'symbol', 'symbol')"/>
                                    </bf:checkGroup>
                                    <bc:label text="小标志图形" col="4" formControl="true"/>
                                    <bf:checkGroup col="7" name="symbol" value="circle" disabled="true">
                                        <bf:radio label="标注" value="pin" change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="箭头" value="arrow" change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="菱形" value="diamond"
                                                  change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="圆形" value="circle" change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="矩形" value="rect" change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="圆角矩形" value="roundRect"
                                                  change="seriesFirstPChange(this,'symbol')" cls="align-radio"/>
                                        <bf:radio label="三角形" value="triangle"
                                                  change="seriesFirstPChange(this,'symbol')" cls="align-radio"/>
                                        <bf:radio label="空心圆" value="emptyCircle"
                                                  change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="空心矩形" value="emptyRectangle"
                                                  change="seriesFirstPChange(this,'symbol')"/>
                                        <bf:radio label="空心三角" value="emptyTriangle"
                                                  change="seriesFirstPChange(this,'symbol')" cls="align-radio"/>
                                        <bf:radio label="空心菱形" value="emptyDiamond"
                                                  change="seriesFirstPChange(this,'symbol')"/>
                                    </bf:checkGroup>
                                </bf:formGroup>
                                <bf:formGroup hr="dashed" hrp="top">
                                    <bf:checkGroup col="1" name="aSymbolSizeCheck">
                                        <bf:check change="aSymbolSizeCheckChange(this, 'aSymbolSize', 'symbolSize')"/>
                                    </bf:checkGroup>
                                    <bc:label text="小标志大小" col="4" formControl="true"/>
                                    <bf:touchpin id="aSymbolSize" name="aSymbolSize" col="7" disabled="true"
                                                 min="2" max="20" step="1" value="2"
                                                 change="aSymbolSizeChange(this,'symbolSize')"/>
                                </bf:formGroup>
                                <bf:formGroup>
                                    <bf:checkGroup col="1" name="symbolRotateCheck">
                                        <bf:check change="symbolRotateCheck(this, 'symbolRotate', 'symbolRotate')"/>
                                    </bf:checkGroup>
                                    <bc:label text="旋转角度" col="4" formControl="true"/>
                                    <input id="symbolRotate" type="text" data-slider-min="-180" data-slider-max="180"
                                           data-slider-step="2" data-slider-value="0" data-slider-enabled="false"/>
                                    <span id="symbolRotateVal">0</span><span> °</span>
                                </bf:formGroup>
                            </div>
                        </div>

                        <!-- 	油量图（仪表盘）设置       -->
                        <div class="div-gauge-fg">
                            <bf:formGroup cls="title-tag-grp">
                                <bc:label text="油量图数值设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeRangeCheck">
                                    <bf:check change="gaugeRangeCheckChange(this,'gaugeRange','min','max')"/>
                                </bf:checkGroup>
                                <bc:label text="值域范围" col="4" formControl="true"/>
                                <input id="gaugeRange" type="text" data-slider-min="0" data-slider-max="150"
                                       data-slider-step="2" data-slider-value="[0,100]" data-slider-enabled="false"/>
                                <span id="gaugeRangeVal">0,100</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="splitNumberCheck">
                                    <bf:check change="seriesSingleSliderChange(this, 'splitNumber', 'splitNumber')"/>
                                </bf:checkGroup>
                                <bc:label text="分割段数" col="4" formControl="true"/>
                                <input id="splitNumber" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="10" data-slider-enabled="false"/>
                                <span id="splitNumberVal">10</span>
                            </bf:formGroup>
                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图基础设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup cls="margin-small">
                                <bf:checkGroup col="1" name="gaugeColorCheck">
                                    <bf:check
                                            change="gaugeColorChange(this,'gaugeColorVal','gaugeColorClrpkr','axisLine','lineStyle','color')"/>
                                </bf:checkGroup>
                                <bc:label text="油量图颜色" col="4" formControl="true"/>
                                <bc:col col="7">
                                    <bc:button id="addGaugeColor" icon="plus" size="xs" click="addGaugeColor()"/>
                                    <bc:button id="delGaugeColor" icon="minus" size="xs" click="delGaugeColor()"/>
                                </bc:col>
                            </bf:formGroup>
                            <bf:formGroup cls="margin-small" id="gaugeColorFg">
                                <bc:col col="7" colOffset="5">
                                    <bc:label text="输入框中可输入0到1之间的值，表示百分比" formControl="true"/>
                                </bc:col>
                            </bf:formGroup>
                            <div id="gaugeColorDiv">
                                <bf:formGroup cls="gauge-color-fg">
                                    <bc:col col="7" colOffset="5" cls="gauge-color-css">
                                        <bf:text id="gaugeColorVal0" name="" col="8" value="0.2" disabled="true"/>
                                        <bc:col col="4">
                                            <div id="gaugeColorClrpkr0" class="gauge-color-div"
                                                 style="background-color: rgb(34, 139, 34)"></div>
                                        </bc:col>
                                    </bc:col>
                                </bf:formGroup>
                                <bf:formGroup cls="gauge-color-fg">
                                    <bc:col col="7" colOffset="5" cls="gauge-color-css">
                                        <bf:text id="gaugeColorVal1" name="" col="8" value="0.8" disabled="true"/>
                                        <bc:col col="4">
                                            <div id="gaugeColorClrpkr1" class="gauge-color-div"
                                                 style="background-color: rgb(68, 136, 187)"></div>
                                        </bc:col>
                                    </bc:col>
                                </bf:formGroup>
                                <bf:formGroup cls="gauge-color-fg">
                                    <bc:col col="7" colOffset="5" cls="gauge-color-css">
                                        <bf:text id="gaugeColorVal2" name="" col="8" value="1" disabled="true"/>
                                        <bc:col col="4">
                                            <div id="gaugeColorClrpkr2" class="gauge-color-div"
                                                 style="background-color: rgb(255, 69, 0)"></div>
                                        </bc:col>
                                    </bc:col>
                                </bf:formGroup>
                            </div>
                            <bf:formGroup cls="margin-small">
                                <bf:checkGroup col="1" name="gaugeCenterCheck">
                                    <bf:check
                                            change="seriesSliderXYChange(this,'gaugeCenterX','gaugeCenterY','center')"/>
                                </bf:checkGroup>
                                <bc:label text="圆心坐标" col="4" formControl="true" cls="slider-label-x"/>
                                <span>x</span><span>:</span>
                                <input id="gaugeCenterX" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                                <span id="gaugeCenterXVal">50</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup cls="slider-margin-large">
                                <bc:label text="" col="5" formControl="true" cls="slider-label-y"/>
                                <span>y</span><span>:</span>
                                <input id="gaugeCenterY" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                                <span id="gaugeCenterYVal">50</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeRadiusCheck">
                                    <bf:check change="seriesSingleSliderChange(this, 'gaugeRadius', 'radius')"/>
                                </bf:checkGroup>
                                <bc:label text="半径" col="4" formControl="true"/>
                                <input id="gaugeRadius" type="text" data-slider-min="0" data-slider-max="100"
                                       data-slider-step="1" data-slider-value="75" data-slider-enabled="false"/>
                                <span id="gaugeRadiusVal">75</span><span> %</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeStartAngleCheck">
                                    <bf:check change="seriesSingleSliderChange(this, 'gaugeStartAngle', 'startAngle')"/>
                                </bf:checkGroup>
                                <bc:label text="起始角度" col="4" formControl="true"/>
                                <input id="gaugeStartAngle" type="text" data-slider-min="-90" data-slider-max="270"
                                       data-slider-step="6" data-slider-value="230" data-slider-enabled="false"/>
                                <span id="gaugeStartAngleVal">230</span><span> °</span>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeEndAngleCheck">
                                    <bf:check change="seriesSingleSliderChange(this, 'gaugeEndAngle', 'endAngle')"/>
                                </bf:checkGroup>
                                <bc:label text="结束角度" col="4" formControl="true"/>
                                <input id="gaugeEndAngle" type="text" data-slider-min="-90" data-slider-max="270"
                                       data-slider-step="6" data-slider-value="-45" data-slider-enabled="false"/>
                                <span id="gaugeEndAngleVal">-45</span><span> °</span>
                            </bf:formGroup>
                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图内标题" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleCheck">
                                    <bf:check change="gaugeShowCheckChange(this,'gaugeTitle','title','show')"/>
                                </bf:checkGroup>
                                <bc:label text="是否显示内标题" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="gaugeTitle" value="Y" disabled="true">
                                    <bf:radio dataSource="fixedDict" url="YesNo"
                                              change="gaugeShowChange(this,'title','show')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleSizeCheck">
                                    <bf:check
                                            change="seriesBorderWthCheckChange(this,'gaugeTitleSizeTchPin','title','textStyle','fontSize')"/>
                                </bf:checkGroup>
                                <bc:label text="字体大小" col="4" formControl="true"/>
                                <bf:touchpin id="gaugeTitleSizeTchPin" name="gaugeTitleSizeTchPin" col="7"
                                             disabled="true"
                                             min="10" max="30" step="1" value="10"
                                             change="seriesBorderWthChange(this,'title','textStyle','fontSize')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleStyleCheck">
                                    <bf:check
                                            change="gaugeStyleCheckChange(this,'gaugeTitleStyle','title','textStyle','fontStyle','gaugeTitleStyleBold')"/>
                                </bf:checkGroup>
                                <bc:label text="字体样式" col="4" formControl="true"/>
                                <bf:checkGroup col="2" name="gaugeTitleStyleBold" value="" disabled="true">
                                    <bf:check label="加粗" value="bold" id="gaugeTitleStyleBold"
                                              change="gaugeStyleBoldChange(this, 'title','textStyle','fontWeight')"/>
                                </bf:checkGroup>
                                <bf:checkGroup col="4" name="gaugeTitleStyle" value="normal" disabled="true">
                                    <bf:radio label="普通" value="normal"
                                              change="gaugeStyleChange(this, 'title','textStyle','fontStyle')"/>
                                    <bf:radio label="斜体" value="italic"
                                              change="gaugeStyleChange(this, 'title','textStyle','fontStyle')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图内数据描述设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeDataCheck">
                                    <bf:check change="gaugeShowCheckChange(this,'gaugeData','detail','show')"/>
                                </bf:checkGroup>
                                <bc:label text="是否显示数据描述" col="4" formControl="true"/>
                                <bf:checkGroup col="7" name="gaugeData" value="Y" disabled="true">
                                    <bf:radio dataSource="fixedDict" url="YesNo"
                                              change="gaugeShowChange(this,'detail','show')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleBgColor">
                                    <bf:check
                                            change="seriesGaugeColorChange(this,'gaugeTitleBgClrpkr','detail','backgroundColor')"/>
                                </bf:checkGroup>
                                <bc:label text="数据描述背景颜色" col="4" formControl="true"/>
                                <bf:text id="gaugeTitleBgClrpkr" name="" col="7" disabled="true"
                                         value="rgb(255, 255, 255)"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleBorderWidthCheck">
                                    <bf:check
                                            change="gaugeFormatterChange(this,'remindTmpNormal','detail','formatter')"/>
                                </bf:checkGroup>
                                <bc:label text="数据描述内容模板" col="4" formControl="true"/>
                                <bf:text id="remindTmpNormal" name="remindTmpNormal" col="7" value="{value}%"
                                         disabled="true"
                                         helpText="模板中可以使用“{value}”获得当前量值"></bf:text>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleBorderWidthCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'gaugeTitleBorderWidth','detail','borderWidth')"/>
                                </bf:checkGroup>
                                <bc:label text="数据描述边框线宽" col="4" formControl="true"/>
                                <bf:touchpin id="gaugeTitleBorderWidth" name="gaugeTitleBorderWidth" col="7" min="0"
                                             max="3" disabled="true"
                                             step="1" value="0"
                                             change="seriesGaugeChange(this,'detail','borderWidth')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeTitleBorderColorCheck">
                                    <bf:check
                                            change="seriesGaugeColorChange(this,'gaugeTitleBorderColorClrpkr','detail','borderColor')"/>
                                </bf:checkGroup>
                                <bc:label text="数据描述边框颜色" col="4" formControl="true"/>
                                <bf:text id="gaugeTitleBorderColorClrpkr" name="gaugeTitleBorderColorClrpkr" col="7"
                                         disabled="true" value="rgb(255, 255, 255)"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeDataSizeCheck">
                                    <bf:check
                                            change="seriesBorderWthCheckChange(this,'gaugeDataSizeTchPin','detail','textStyle','fontSize')"/>
                                </bf:checkGroup>
                                <bc:label text="字体大小" col="4" formControl="true"/>
                                <bf:touchpin id="gaugeDataSizeTchPin" name="gaugeDataSizeTchPin" col="7" disabled="true"
                                             min="10" max="30" step="1" value="10"
                                             change="seriesBorderWthChange(this,'detail','textStyle','fontSize')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeDataStyleCheck">
                                    <bf:check
                                            change="gaugeStyleCheckChange(this,'gaugeDataStyle','detail','textStyle','fontStyle','gaugeDataStyleBold')"/>
                                </bf:checkGroup>
                                <bc:label text="字体样式" col="4" formControl="true"/>
                                <bf:checkGroup col="2" name="gaugeDataStyleBold" value="" disabled="true">
                                    <bf:check label="加粗" value="bold" id="gaugeDataStyleBold"
                                              change="gaugeStyleBoldChange(this, 'detail','textStyle','fontWeight')"/>
                                </bf:checkGroup>
                                <bf:checkGroup col="4" name="gaugeDataStyle" value="normal" disabled="true">
                                    <bf:radio label="普通" value="normal"
                                              change="gaugeStyleChange(this, 'detail','textStyle','fontStyle')"/>
                                    <bf:radio label="斜体" value="italic"
                                              change="gaugeStyleChange(this, 'detail','textStyle','fontStyle')"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图轮廓线设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeAxisWidthCheck">
                                    <bf:check
                                            change="seriesBorderWthCheckChange(this,'gaugeAxisWidthTchPin','axisLine','lineStyle','width')"/>
                                </bf:checkGroup>
                                <bc:label text="轮廓宽度" col="4" formControl="true"/>
                                <bf:touchpin id="gaugeAxisWidthTchPin" name="gaugeAxisWidthTchPin" col="7"
                                             disabled="true"
                                             min="5" max="30" step="1" value="30"
                                             change="seriesBorderWthChange(this,'axisLine','lineStyle','width')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="lineSizeCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'lineSizeTchPin','splitLine','length')"/>
                                </bf:checkGroup>
                                <bc:label text="主分隔线长度" col="4" formControl="true"/>
                                <bf:touchpin id="lineSizeTchPin" name="lineSizeTchPin" col="7" disabled="true"
                                             min="0" max="100" step="1" value="30"
                                             change="seriesGaugeChange(this,'splitLine','length')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="subLineSizeCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'subLineSizeTchPin','axisTick','length')"/>
                                </bf:checkGroup>
                                <bc:label text="刻度线长度" col="4" formControl="true"/>
                                <bf:touchpin id="subLineSizeTchPin" name="subLineSizeTchPin" col="7" disabled="true"
                                             min="0" max="20" step="1" value="10"
                                             change="seriesGaugeChange(this,'axisTick','length')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="splitNumCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'splitNumTchPin','axisTick','splitNumber')"/>
                                </bf:checkGroup>
                                <bc:label text="刻度线分隔段数" col="4" formControl="true"/>
                                <bf:touchpin id="splitNumTchPin" name="splitNumTchPin" col="7" disabled="true"
                                             min="0" max="20" step="1" value="10"
                                             change="seriesGaugeChange(this,'axisTick','splitNumber')"/>
                            </bf:formGroup>
                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图刻度文字设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeFontContentCheck">
                                    <bf:check
                                            change="gaugeFormatterChange(this,'gaugeFontTmpNormal','axisLabel','formatter')"/>
                                </bf:checkGroup>
                                <bc:label text="刻度文字内容模板" col="4" formControl="true"/>
                                <bf:text id="gaugeFontTmpNormal" name="gaugeFontTmpNormal" col="7" disabled="true"
                                         helpText="模板中可以使用“{value}”获得当前量值"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeFontSizeCheck">
                                    <bf:check
                                            change="seriesBorderWthCheckChange(this,'gaugeFontSizeTchPin','axisLabel','textStyle','fontSize')"/>
                                </bf:checkGroup>
                                <bc:label text="字体大小" col="4" formControl="true"/>
                                <bf:touchpin id="gaugeFontSizeTchPin" name="gaugeFontSizeTchPin" col="7" disabled="true"
                                             min="10" max="20" step="1" value="12"
                                             change="seriesBorderWthChange(this,'axisLabel','textStyle','fontSize')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugeFontStyleCheck">
                                    <bf:check
                                            change="gaugeStyleCheckChange(this,'gaugeFontStyle','axisLabel','textStyle','fontStyle','gaugeStyleBold')"/>
                                </bf:checkGroup>
                                <bc:label text="字体样式" col="4" formControl="true"/>
                                <bf:checkGroup col="2" name="gaugeFontStyleBold" value="" disabled="true">
                                    <bf:check label="加粗" value="bold" id="gaugeStyleBold"
                                              change="gaugeStyleBoldChange(this, 'axisLabel','textStyle','fontWeight')"/>
                                </bf:checkGroup>
                                <bf:checkGroup col="4" name="gaugeFontStyle" value="normal" disabled="true">
                                    <bf:radio label="普通" value="normal"
                                              change="gaugeStyleChange(this, 'axisLabel','textStyle','fontStyle')"/>
                                    <bf:radio label="斜体" value="italic"
                                              change="gaugeStyleChange(this, 'axisLabel','textStyle','fontStyle')"/>
                                </bf:checkGroup>
                            </bf:formGroup>

                            <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                                <bc:label text="油量图指针设置" col="4" formControl="true"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugePointerLengthCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'gaugePointerLength','pointer','length')"/>
                                </bf:checkGroup>
                                <bc:label text="指针长度" col="4" formControl="true"/>
                                <bf:touchpin id="gaugePointerLength" name="gaugePointerLengthTchPin" col="7"
                                             disabled="true"
                                             min="0" max="100" step="1" value="80"
                                             change="seriesGaugeChange(this,'pointer','length')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bf:checkGroup col="1" name="gaugePointerWithCheck">
                                    <bf:check
                                            change="seriesGaugeCheckChange(this,'gaugePointerWith','pointer','width')"/>
                                </bf:checkGroup>
                                <bc:label text="指针宽度" col="4" formControl="true"/>
                                <bf:touchpin id="gaugePointerWith" name="gaugePointerWithTchPin" col="7" disabled="true"
                                             min="3" max="30" step="1" value="10"
                                             change="seriesGaugeChange(this,'pointer','width')"/>
                            </bf:formGroup>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingTwo">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                   aria-expanded="false" aria-controls="collapseTwo">
                    标题
                </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
            <div class="panel-body tabbable">
                <ul class='nav nav-tabs param-tab' id="">
                    <li class='active'><a href='#titleTab1' data-toggle='tab'>内容</a></li>
                    <li><a href='#titleTab2' data-toggle='tab'>样式</a></li>
                </ul>
                <div class='tab-content param-content'>
                    <div class='tab-pane active' id='titleTab1'>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleCheck">
                                <bf:check id="titleCheck" change="titleCheckChange(this, 'titleInput')"/>
                            </bf:checkGroup>
                            <bc:label text="主标题文本" col="4" formControl="true"/>
                            <bf:text id="titleInput" name="" value="" col="7" disabled="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleLinkCheck">
                                <bf:check id="titleLinkCheck" change="titleLinkCheckChange(this, 'titleLinkInput')"/>
                            </bf:checkGroup>
                            <bc:label text="主标题文本超链接" col="4" formControl="true"/>
                            <bf:text id="titleLinkInput" name="" value="" col="7" disabled="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="subTitleCheck">
                                <bf:check id="subTitleCheck" change="subTitleCheckChange(this, 'subTitleInput')"/>
                            </bf:checkGroup>
                            <bc:label text="副标题文本" col="4" formControl="true"/>
                            <bf:text id="subTitleInput" name="" value="" col="7" disabled="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="subTitleLinkCheck">
                                <bf:check id="subTitleLinkCheck"
                                          change="subTitleLinkCheckChange(this, 'subTitleLinkInput')"/>
                            </bf:checkGroup>
                            <bc:label text="副标题文本超链接" col="4" formControl="true"/>
                            <bf:text id="subTitleLinkInput" name="" value="" col="7" disabled="true"/>
                        </bf:formGroup>
                    </div>
                    <div class='tab-pane' id='titleTab2'>
                        <bf:formGroup cls="title-tag-grp">
                            <bc:label text="标题块位置" col="4" formControl="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="positionHCheck">
                                <bf:check id="positionHCheck" change="positionHCheckChange(this, 'positionH')"/>
                            </bf:checkGroup>
                            <bc:label text="水平安放位置" col="4" formControl="true"/>
                            <bf:checkGroup col="7" name="positionH" value="center" disabled="true">
                                <bf:radio label="居左" value="left" id="positionHLeft" change="positionHChange(this)"/>
                                <bf:radio label="居中" value="center" id="positionHCenter"
                                          change="positionHChange(this)"/>
                                <bf:radio label="居右" value="right" id="positionHRight" change="positionHChange(this)"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="positionVCheck">
                                <bf:check id="positionVCheck" change="positionVCheckChange(this, 'positionV')"/>
                            </bf:checkGroup>
                            <bc:label text="垂直安放位置" col="4" formControl="true"/>
                            <bf:checkGroup col="7" name="positionV" value="top" disabled="true">
                                <bf:radio label="居上" value="top" id="positionVUp" change="positionVChange(this)"/>
                                <bf:radio label="居中" value="center" id="positionVCenter"
                                          change="positionVChange(this)"/>
                                <bf:radio label="居下" value="bottom" id="positionVDown" change="positionVChange(this)"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleAlignTypeCheck">
                                <bf:check id="titleAlignTypeCheck"
                                          change="titleAlignTypeCheckChange(this, 'titleAlignType')"/>
                            </bf:checkGroup>
                            <bc:label text="标题对齐方式" col="4" formControl="true"/>
                            <bf:checkGroup col="7" name="titleAlignType" value="center" disabled="true">
                                <bf:radio label="居左" value="left" id="titleAlignLeft"
                                          change="titleAlignTypeChange(this)"/>
                                <bf:radio label="居中" value="center" id="titleAlignCenter"
                                          change="titleAlignTypeChange(this)"/>
                                <bf:radio label="居右" value="right" id="titleAlignRight"
                                          change="titleAlignTypeChange(this)"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                        <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                            <bc:label text="标题块样式" col="4" formControl="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleBgColor">
                                <bf:check id="titleBgColor" change="bgColorChange(this, 'titleBgClrpkr', 'title')"/>
                            </bf:checkGroup>
                            <bc:label text="标题背景颜色" col="4" formControl="true"/>
                            <bf:text id="titleBgClrpkr" name="" col="7" disabled="true" value="rgb(255, 255, 255)"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleBorderWidthCheck">
                                <bf:check id="titleBorderWidthCheck"
                                          change="borderWidthCheckChange(this, 'titleBorderWidth', 'title')"/>
                            </bf:checkGroup>
                            <bc:label text="标题边框线宽" col="4" formControl="true"/>
                            <bf:touchpin id="titleBorderWidth" name="" col="7" disabled="true"
                                         change="borderWidthChange(this, 'title')"
                                         min="0" max="10" step="1" value="0"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleBorderColorCheck">
                                <bf:check id="titleBorderColorCheck"
                                          change="borderColorChange(this, 'titleBorderColorClrpkr', 'title')"/>
                            </bf:checkGroup>
                            <bc:label text="标题边框颜色" col="4" formControl="true"/>
                            <bf:text id="titleBorderColorClrpkr" name="" col="7" disabled="true"
                                     value="rgb(255, 255, 255)"/>
                        </bf:formGroup>
                        <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                            <bc:label text="主标题文字样式" col="4" formControl="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleTextColor">
                                <bf:check id="titleTextColor"
                                          change="fontColorChange(this, 'titleTextClrpkr', 'title')"/>
                            </bf:checkGroup>
                            <bc:label text="文本颜色" col="4" formControl="true"/>
                            <bf:text id="titleTextClrpkr" name="" col="7" disabled="true" value="rgb(51, 51, 51)"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleSizeCheck">
                                <bf:check id="titleSizeCheck"
                                          change="fontSizeCheckChange(this, 'titleSizeTchPin', 'title')"/>
                            </bf:checkGroup>
                            <bc:label text="字体大小" col="4" formControl="true"/>
                            <bf:touchpin id="titleSizeTchPin" name="" col="7" disabled="true"
                                         change="fontSizeChange(this, 'title')"
                                         min="10" max="25" step="1" value="15"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="titleStyleCheck">
                                <bf:check change="fontStyleCheckChange(this, 'titleStyle', 'title', 'titleStyleBold')"/>
                            </bf:checkGroup>
                            <bc:label text="字体样式" col="4" formControl="true"/>
                            <bf:checkGroup col="2" name="titleStyleBold" value="bold" disabled="true">
                                <bf:check label="加粗" value="bold" id="titleStyleBold"
                                          change="fontStyleBoldChange(this, 'title')"/>
                            </bf:checkGroup>
                            <bf:checkGroup col="4" name="titleStyle" value="normal" disabled="true">
                                <bf:radio label="普通" value="normal" id="titleStyleNormal"
                                          change="fontStyleChange(this, 'title')"/>
                                <bf:radio label="斜体" value="italic" id="titleStyleItalic"
                                          change="fontStyleChange(this, 'title')"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                        <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                            <bc:label text="副标题文字样式" col="4" formControl="true"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="subTitleTextCheck">
                                <bf:check id="subTitleTextCheck"
                                          change="subTitleTextCheckChange(this, 'subTitleTextClrpkr')"/>
                            </bf:checkGroup>
                            <bc:label text="文本颜色" col="4" formControl="true"/>
                            <bf:text id="subTitleTextClrpkr" name="" col="7" disabled="true"
                                     value="rgb(167, 177, 194)"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="subTitleSizeCheck">
                                <bf:check id="subTitleSizeCheck"
                                          change="subTitleSizeCheckChange(this, 'subTitleSizeTchPin')"/>
                            </bf:checkGroup>
                            <bc:label text="字体大小" col="4" formControl="true"/>
                            <bf:touchpin id="subTitleSizeTchPin" name="" disabled="true"
                                         change="subTitleSizeTchPinChange(this)"
                                         col="7" min="10" max="25" step="1" value="12"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bf:checkGroup col="1" name="subTitleStyleCheck">
                                <bf:check id="subTitleStyleCheck"
                                          change="subTitleStyleCheckChange(this, 'subTitleStyle')"/>
                            </bf:checkGroup>
                            <bc:label text="字体样式" col="4" formControl="true"/>
                            <bf:checkGroup col="2" name="subTitleStyle" value="" disabled="true">
                                <bf:check label="加粗" value="bold" id="subTitleStyleBold"
                                          change="subTitleStyleBoldChange(this)"/>
                            </bf:checkGroup>
                            <bf:checkGroup col="4" name="subTitleStyle" value="normal" disabled="true">
                                <bf:radio label="普通" value="normal" id="subTitleStyleNormal"
                                          change="subTitleStyleChange(this)"/>
                                <bf:radio label="斜体" value="italic" id="subTitleStyleItalic"
                                          change="subTitleStyleChange(this)"/>
                            </bf:checkGroup>
                        </bf:formGroup>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingThree">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                    图例
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body param-content">
                <bf:formGroup>
                    <bf:checkGroup col="1" name="isShowLegendCheck">
                        <bf:check id="isShowLegendCheck" change="isShowLegendCheckChange(this, 'showLegend')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示图例" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="showLegend" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" id="showLegend" change="showLegendChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendChoiceTypeCheck">
                        <bf:check id="legendChoiceTypeCheck" change="selectedModeChange(this, 'legendChoiceType')"/>
                    </bf:checkGroup>
                    <bc:label text="图例选择模式" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="legendChoiceType" value="multiple" disabled="true">
                        <bf:radio label="不可选" value="false" id="legendChoiceTypeNone" change="legendChoiceType(this)"/>
                        <bf:radio label="单选" value="single" id="legendChoiceTypeSingle"
                                  change="legendChoiceType(this)"/>
                        <bf:radio label="多选" value="multiple" id="legendChoiceTypeMulti"
                                  change="legendChoiceType(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="图例位置" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendPositionHCheck">
                        <bf:check id="legendPositionHCheck"
                                  change="putHCheckChange(this, 'legendPositionH', 'legend')"/>
                    </bf:checkGroup>
                    <bc:label text="水平安放位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="legendPositionH" value="center" disabled="true">
                        <bf:radio label="居左" value="left" id="legendPositionHLeft" change="putH(this, 'legend')"/>
                        <bf:radio label="居中" value="center" id="legendPositionHCenter" change="putH(this, 'legend')"/>
                        <bf:radio label="居右" value="right" id="legendPositionHRight" change="putH(this, 'legend')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendPositionVCheck">
                        <bf:check id="legendPositionVCheck"
                                  change="putVCheckChange(this, 'legendPositionV', 'legend')"/>
                    </bf:checkGroup>
                    <bc:label text="垂直安放位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="legendPositionV" value="bottom" disabled="true">
                        <bf:radio label="居上" value="top" id="legendPositionVUp" change="putV(this, 'legend')"/>
                        <bf:radio label="居中" value="center" id="legendPositionVCenter" change="putV(this, 'legend')"/>
                        <bf:radio label="居下" value="bottom" id="legendPositionVDown" change="putV(this, 'legend')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendPositionTypeCheck">
                        <bf:check id="legendPositionTypeCheck"
                                  change="putTypeCheckChange(this, 'legendPstType', 'legend')"/>
                    </bf:checkGroup>
                    <bc:label text="图例放置方式" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="legendPstType" value="horizontal" disabled="true">
                        <bf:radio label="水平放置" value="horizontal" id="legendTypeH" change="putType(this, 'legend')"/>
                        <bf:radio label="垂直放置" value="vertical" id="legendTypeV" change="putType(this, 'legend')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="图例文字样式" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendTextColor">
                        <bf:check id="legendTextColor" change="fontColorChange(this, 'legendTextClrpkr', 'legend')"/>
                    </bf:checkGroup>
                    <bc:label text="文本颜色" col="4" formControl="true"/>
                    <bf:text id="legendTextClrpkr" name="legendTextClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendSizeCheck">
                        <bf:check id="legendSizeCheck"
                                  change="fontSizeCheckChange(this, 'legendSizeTchPin', 'legend')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="legendSizeTchPin" name="legendSizeTchPin" disabled="true"
                                 change="fontSizeChange(this, 'legend')"
                                 col="7" min="6" max="30" step="1" value="12"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="legendStyleCheck">
                        <bf:check change="fontStyleCheckChange(this, 'legendStyle', 'legend', 'legendStyleBold')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="legendStyleBold" disabled="true">
                        <bf:check label="加粗" value="bold" id="legendStyleBold"
                                  change="fontStyleBoldChange(this, 'legend')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="legendStyle" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal" id="legendStyleNormal"
                                  change="fontStyleChange(this, 'legend')"/>
                        <bf:radio label="斜体" value="italic" id="legendStyleItalic"
                                  change="fontStyleChange(this, 'legend')"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFour">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour"
                   aria-expanded="false" aria-controls="collapseFour">
                    提示
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
            <div class="panel-body tabbable param-content">
                <bf:formGroup>
                    <bf:checkGroup col="1" name="usingRemindCheck">
                        <bf:check id="usingRemindCheck" change="toolCheckChange(this, 'usingRemind', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="是否使用提示" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="usingRemind" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" id="usingRemind"
                                  change="toolChange(this, 'tooltip')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="格式模板" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="tooltipFormatterCheck">
                        <bf:check id="tooltipFormatterCheck" change="tooltipFormatterChange(this, 'tooltipFormatter')"/>
                    </bf:checkGroup>
                    <bc:label text="提示模板" col="4" formControl="true"/>
                    <bf:text id="tooltipFormatter" name="tooltipFormatter" col="7" disabled="true"
                             helpText="模板中可以试试看“{a} {b} {c} {d} {e}”这些变量" value="{a} &lt;br&gt;{b}:{c}"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="提示外观" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindBgColor">
                        <bf:check id="remindBgColor" change="bgColorChange(this, 'remindBgClrpkr', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="背景颜色" col="4" formControl="true"/>
                    <bf:text id="remindBgClrpkr" name="remindBgClrpkr" col="7" disabled="true" value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindBorderWidthCheck">
                        <bf:check id="remindBorderWidthCheck"
                                  change="borderWidthCheckChange(this, 'remindBorderWidth', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="边框粗细" col="4" formControl="true"/>
                    <bf:touchpin id="remindBorderWidth" name="remindBorderWidth"
                                 change="borderWidthChange(this, 'tooltip')"
                                 disabled="true" col="7" min="0" max="20" step="1" value="0"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindBorderColor">
                        <bf:check id="remindBorderColor"
                                  change="borderColorChange(this, 'remindBorderClrpkr', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="边框颜色" col="4" formControl="true"/>
                    <bf:text id="remindBorderClrpkr" name="remindBorderClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="提示文本样式" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindTextColor">
                        <bf:check id="remindTextColor" change="fontColorChange(this, 'remindTextClrpkr', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="字体颜色" col="4" formControl="true"/>
                    <bf:text id="remindTextClrpkr" name="" col="7" disabled="true" value="rgb(255, 255, 255)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindTextSizeCheck">
                        <bf:check id="remindTextSizeCheck"
                                  change="fontSizeCheckChange(this, 'remindTextSize', 'tooltip')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="remindTextSize" name="remindTextSize" change="fontSizeChange(this, 'tooltip')"
                                 disabled="true" col="7" min="5" max="20" step="1" value="13"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindTextStyleCheck">
                        <bf:check id="remindTextStyleCheck"
                                  change="fontStyleCheckChange(this, 'remindTextStyle', 'tooltip', 'remindTextStyleB')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="remindTextStyleBold" disabled="true">
                        <bf:check label="加粗" value="bold" id="remindTextStyleB"
                                  change="fontStyleBoldChange(this, 'tooltip')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="remindTextStyle" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal" id="remindTextStyleNormal"
                                  change="fontStyleChange(this, 'tooltip')"/>
                        <bf:radio label="斜体" value="italic" id="remindTextStyleItalic"
                                  change="fontStyleChange(this, 'tooltip')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="rmdTextAlignHCheck">
                        <bf:check id="rmdTextAlignHCheck" change="rmdTextAlignHCheckChange(this, 'rmdTextAlignH')"/>
                    </bf:checkGroup>
                    <bc:label text="文本水平对齐方式" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="rmdTextAlignH" value="left" disabled="true">
                        <bf:radio label="居左" value="left" id="rmdTextAlignHLeft" change="rmdTextAlignHChange(this)"/>
                        <bf:radio label="居中" value="center" id="rmdTextAlignHCenter"
                                  change="rmdTextAlignHChange(this)"/>
                        <bf:radio label="居右" value="right" id="rmdTextAlignHRight" change="rmdTextAlignHChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="提示的指示器设置" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindTiggerCheck">
                        <bf:check id="remindTiggerCheck" change="remindTiggerCheckChange(this, 'remindTigger')"/>
                    </bf:checkGroup>
                    <bc:label text="提示触发点" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="remindTigger" value="axis" disabled="true">
                        <bf:radio label="项上触发(item)" value="item" id="remindTiggerItem"
                                  change="remindTiggerChange(this)"/>
                        <bf:radio label="轴上触发(axis)" value="axis" id="remindTiggerAxis"
                                  change="remindTiggerChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="remindPointerTypeCheck">
                        <bf:check id="remindPointerTypeCheck"
                                  change="pointerTypeCheckChange(this, 'remindPointerType')"/>
                    </bf:checkGroup>
                    <bc:label text="提示指示器类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="remindPointerType" value="line" disabled="true">
                        <bf:radio label="提示线" value="line" id="pointerTypeLine" change="pointerTypeChange(this)"/>
                        <bf:radio label="交叉线" value="cross" id="pointerTypeLine" change="pointerTypeChange(this)"/>
                        <bf:radio label="提示阴影" value="shadow" id="pointerTypeShadow" change="pointerTypeChange(this)"/>
                        <bf:radio label="无" value="none" id="pointerTypeNone" change="pointerTypeChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="pointerLineColor">
                        <bf:check id="pointerLineColor" change="pointerLineColorChange(this, 'pointerLineClrpkr')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="pointerLineClrpkr" name="" col="7" disabled="true" value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="pointerLineWidthCheck">
                        <bf:check id="pointerLineWidthCheck" change="pointerLineWidthChange(this, 'pointerLineWidth')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="pointerLineWidth" name="" disabled="true" change="pLineWidthChange(this)"
                                 col="7" min="2" max="10" step="1" value="2"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="pointerLineTypeCheck">
                        <bf:check id="pointerLineTypeCheck"
                                  change="pointerLineTypeCheckChange(this, 'pointerLineType')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="pointerLineType" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid" id="pointerLineSolid" change="pointerLineTypeChange(this)"/>
                        <bf:radio label="点状线" value="dotted" id="pointerLineDotted"
                                  change="pointerLineTypeChange(this)"/>
                        <bf:radio label="虚线" value="dashed" id="pointerLineDashed"
                                  change="pointerLineTypeChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFive">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                    工具
                </a>
            </h4>
        </div>
        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
            <div class="panel-body param-content">
                <bf:formGroup>
                    <bf:checkGroup col="1" name="isShowToolCheck">
                        <bf:check id="isShowToolCheck" change="toolCheckChange(this, 'isShowTool', 'toolbox')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示工具箱" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="isShowTool" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" id="showTool"
                                  change="toolChange(this, 'toolbox')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="工具箱位置" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="toolPositionHCheck">
                        <bf:check id="toolPositionHCheck" change="putHCheckChange(this, 'toolPositionH', 'toolbox')"/>
                    </bf:checkGroup>
                    <bc:label text="水平安放位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="toolPositionH" value="right" disabled="true">
                        <bf:radio label="居左" value="left" id="toolPositionHLeft" change="putH(this, 'toolbox')"/>
                        <bf:radio label="居中" value="center" id="toolPositionHCenter" change="putH(this, 'toolbox')"/>
                        <bf:radio label="居右" value="right" id="toolPositionHRight" change="putH(this, 'toolbox')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="toolPositionVCheck">
                        <bf:check id="toolPositionVCheck" change="putVCheckChange(this, 'toolPositionV', 'toolbox')"/>
                    </bf:checkGroup>
                    <bc:label text="垂直安放位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="toolPositionV" value="top" disabled="true">
                        <bf:radio label="居上" value="top" id="toolPositionVUp" change="putV(this, 'toolbox')"/>
                        <bf:radio label="居中" value="center" id="toolPositionVCenter" change="putV(this, 'toolbox')"/>
                        <bf:radio label="居下" value="bottom" id="toolPositionVDown" change="putV(this, 'toolbox')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="toolPositionTypeCheck">
                        <bf:check id="toolPositionTypeCheck"
                                  change="putTypeCheckChange(this, 'toolPstType', 'toolbox')"/>
                    </bf:checkGroup>
                    <bc:label text="工具箱放置方式" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="toolPstType" value="horizontal" disabled="true">
                        <bf:radio label="水平放置" value="horizontal" id="typeH" change="putType(this, 'toolbox')"/>
                        <bf:radio label="垂直放置" value="vertical" id="typeV" change="putType(this, 'toolbox')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="功能按钮设定" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="choiceToolCheck">
                        <bf:check id="choiceToolCheck" change="choiceToolCheckChange(this, 'choiceTool')"/>
                    </bf:checkGroup>
                    <bc:label text="选择显示的工具" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="choiceTool" disabled="true">
                        <bf:check label="区域框" value="brush" id="brush" change="choiceToolChange(this)"/>
                        <bf:check label="数据视图" value="dataView" id="dataView" checked="true"
                                  change="choiceToolChange(this)"/>
                        <bf:check label="缩放" value="dataZoom" id="resizable" change="choiceToolChange(this)"/>
                        <bf:check label="还原" value="restore" id="restore" checked="true"
                                  change="choiceToolChange(this)"/>
                        <bf:check label="另存为" value="saveAsImage" cls="align-radio" checked="true"
                                  change="choiceToolChange(this)"/>
                        <bf:check label="折柱切换" value="magicType" id="lineStack" change="choiceToolChange(this)"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>
    <div class="panel panel-default div-line-fg">
        <div class="panel-heading" role="tab" id="headingSix">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                    视区
                </a>
            </h4>
        </div>
        <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
            <div class="panel-body param-content">
                <bf:formGroup cls="title-tag-grp">
                    <bc:label text="绘图区域位置" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridX1Check">
                        <bf:check change="gridPositionCheck(this, 'gridX1', 'x')"/>
                    </bf:checkGroup>
                    <bc:label text="横坐标距图左侧边缘宽度" col="4" formControl="true"/>
                    <input id="gridX1" type="text" data-slider-min="0" data-slider-max="150"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="gridX1Val">50</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridX2Check">
                        <bf:check change="gridPositionCheck(this, 'gridX2', 'x2')"/>
                    </bf:checkGroup>
                    <bc:label text="横坐标距图右侧边缘宽度" col="4" formControl="true"/>
                    <input id="gridX2" type="text" data-slider-min="0" data-slider-max="150"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="gridX2Val">50</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridY1Check">
                        <bf:check change="gridPositionCheck(this, 'gridY1', 'y')"/>
                    </bf:checkGroup>
                    <bc:label text="纵坐标距图左侧边缘宽度" col="4" formControl="true"/>
                    <input id="gridY1" type="text" data-slider-min="0" data-slider-max="100"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="gridY1Val">50</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridY2Check">
                        <bf:check change="gridPositionCheck(this, 'gridY2', 'y2')"/>
                    </bf:checkGroup>
                    <bc:label text="纵坐标距图右侧边缘宽度" col="4" formControl="true"/>
                    <input id="gridY2" type="text" data-slider-min="0" data-slider-max="100"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="gridY2Val">50</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="绘图区域外观" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridBgColor">
                        <bf:check change="gridColorCheck(this, 'gridBgClrpkr', 'backgroundColor')"/>
                    </bf:checkGroup>
                    <bc:label text="背景颜色" col="4" formControl="true"/>
                    <bf:text id="gridBgClrpkr" name="gridBgClrpkr" col="7" disabled="true" value="rgb(255, 255, 255)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridBorderWidthCheck">
                        <bf:check change="gridBorderWidthCheck(this, 'gridBorderWidth')"/>
                    </bf:checkGroup>
                    <bc:label text="边框线宽" col="4" formControl="true"/>
                    <bf:touchpin id="gridBorderWidth" name="gridBorderWidth" change="gridBorderWidthChange(this)"
                                 disabled="true" col="7" min="0" max="10" step="1" value="0"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="gridBorderColor">
                        <bf:check change="gridColorCheck(this, 'gridBorderClrpkr', 'borderColor')"/>
                    </bf:checkGroup>
                    <bc:label text="边框颜色" col="4" formControl="true"/>
                    <bf:text id="gridBorderClrpkr" name="gridBorderClrpkr" col="7" disabled="true"
                             value="rgb(255, 255, 255)"/>
                </bf:formGroup>
            </div>
        </div>
    </div>
    <div class="panel panel-default div-line-fg">
        <div class="panel-heading" role="tab" id="headingSeven">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
                    X轴
                </a>
            </h4>
        </div>
        <div id="collapseSeven" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSeven">
            <div class="panel-body param-content">
                <div id="xAxisDiv" style="display: none">
                    <bf:formGroup>
                        <bc:col col="12" cls="margin-top-Fg"></bc:col>
                    </bf:formGroup>
                </div>
                <bf:formGroup cls="title-tag-grp">
                    <bc:label text="基础" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisPositionCheck">
                        <bf:check change="axisCheckChange(this, 'axisPosition', 'xAxis', 'position')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisPosition" value="bottom" disabled="true">
                        <bf:radio label="居上" value="top" change="axisChange(this, 'xAxis', 'position')"/>
                        <bf:radio label="居下" value="bottom" change="axisChange(this, 'xAxis', 'position')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameCheck">
                        <bf:check change="axisNameCheckChange(this,'axisName', 'xAxis', 'name')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴名称" col="4" formControl="true"/>
                    <bf:text id="axisName" name="axisName" col="7" disabled="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNamePositionCheck">
                        <bf:check change="axisCheckChange(this, 'axisNamePosition', 'xAxis', 'nameLocation')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴名称位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisNamePosition" value="end" disabled="true">
                        <bf:radio label="位于开端" value="start" change="axisChange(this, 'xAxis', 'nameLocation')"/>
                        <bf:radio label="位于末端" value="end" change="axisChange(this, 'xAxis', 'nameLocation')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameColor">
                        <bf:check change="axisNameColorChange(this,'axisNameClrpkr','xAxis','nameTextStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="文本颜色" col="4" formControl="true"/>
                    <bf:text id="axisNameClrpkr" name="" col="7" disabled="true" value="rgb(0, 0, 0)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameSizeCheck">
                        <bf:check
                                change="axisFontSizeCheckChange(this,'axisNameSize','xAxis','nameTextStyle','fontSize')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="axisNameSize" name="axisNameSize" col="7" disabled="true" min="10" max="25"
                                 step="1" value="12"
                                 change="axisFontSizeChange(this,'xAxis','nameTextStyle','fontSize')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameStyleCheck">
                        <bf:check
                                change="axisNameStyleCheckChange(this,'axisNameStyle','xAxis','nameTextStyle','fontStyle','axisNameStylebold')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="axisNameStyleBold" disabled="true">
                        <bf:check label="加粗" value="bold" id="axisNameStylebold"
                                  change="axisNameStyleBoldChange(this,'xAxis','nameTextStyle')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="axisNameStyle" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal"
                                  change="axisNameStyleChange(this,'xAxis','nameTextStyle','fontStyle')"/>
                        <bf:radio label="斜体" value="italic"
                                  change="axisNameStyleChange(this,'xAxis','nameTextStyle','fontStyle')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="boundaryGapCheck">
                        <bf:check change="axisCheckChange(this, 'boundaryGap', 'xAxis', 'boundaryGap')"/>
                    </bf:checkGroup>
                    <bc:label text="类目两端空白策略" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="boundaryGap" value="N" disabled="true">
                        <bf:radio label="留空" value="Y" change="axisChange(this, 'xAxis', 'boundaryGap')"/>
                        <bf:radio label="顶头" value="N" change="axisChange(this, 'xAxis', 'boundaryGap')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="轴线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineShowCheck">
                        <bf:check change="axisShowCheckChange(this,'axisLineShow','xAxis','axisLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示轴线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLineShow" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this,'xAxis','axisLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineColor">
                        <bf:check
                                change="axisColorChange(this,'axisLineClrpkr','xAxis','axisLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="axisLineClrpkr" name="axisLineClrpkr" col="7" disabled="true" value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineWidthCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'axisLineWidth','xAxis','axisLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="axisLineWidth" name="axisLineWidth" disabled="true" col="7" min="2" max="20"
                                 step="1" value="2" change="axisPXChange(this,'xAxis','axisLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineTypeCheck">
                        <bf:check
                                change="axisLineTypeCheckChange(this,'axisLineType','xAxis','axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLineType" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid"
                                  change="axisLineTypeChange(this,'xAxis','axisLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted"
                                  change="axisLineTypeChange(this,'xAxis','axisLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed"
                                  change="axisLineTypeChange(this,'xAxis','axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="轴标签" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelShowCheck">
                        <bf:check change="axisShowCheckChange(this,'axisLabelShow','xAxis','axisLabel','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示文本标签" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLabelShow" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'xAxis','axisLabel','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisRotateCheck">
                        <bf:check change="axisSliderCheckChange(this,'axisRotate','xAxis','axisLabel','rotate')"/>
                    </bf:checkGroup>
                    <bc:label text="文本标签旋转角度" col="4" formControl="true"/>
                    <input id="axisRotate" type="text" data-slider-min="-180" data-slider-max="180"
                           data-slider-step="2" data-slider-value="0" data-slider-enabled="false"/>
                    <span id="axisRotateVal">0</span><span> °</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelIntervalCheck">
                        <bf:check
                                change="axisSliderCheckChange(this,'axisLabelInterval','xAxis','axisLabel','interval')"/>
                    </bf:checkGroup>
                    <bc:label text="文本标签与坐标轴间距" col="4" formControl="true"/>
                    <input id="axisLabelInterval" type="text" data-slider-min="0" data-slider-max="50"
                           data-slider-step="2" data-slider-value="0" data-slider-enabled="false"/>
                    <span id="axisLabelIntervalVal">0</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelFormatCheck">
                        <bf:check
                                change="axisLabelFormatterChange(this, 'axisLabelFormat','xAxis','axisLabel','formatter')"/>
                    </bf:checkGroup>
                    <bc:label text="标签内容模板" col="4" formControl="true"/>
                    <bf:text id="axisLabelFormat" name="axisLabelFormat" col="7" value="{value}" disabled="true"
                             helpText="模板中可以使用“{value}”获得当前量值"></bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelColor">
                        <bf:check
                                change="axisColorChange(this,'axisLabelClrpkr','xAxis','axisLabel','textStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="文本颜色" col="4" formControl="true"/>
                    <bf:text id="axisLabelClrpkr" name="" col="7" disabled="true" value="rgb(0, 0, 0)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelSizeCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'axisLabelSize','xAxis','axisLabel','textStyle','fontSize')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="axisLabelSize" name="axisLabelSize" col="7" disabled="true" min="10" max="25"
                                 step="1" value="12"
                                 change="axisPXChange(this,'xAxis','axisLabel','textStyle','fontSize')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelStyleCheck">
                        <bf:check
                                change="axisLabelCheckChange(this,'axisLabelStyle','xAxis','axisLabel','textStyle','fontStyle','axisLabelStyleBold')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="axisLabelStyleBold" disabled="true">
                        <bf:check label="加粗" value="bold" id="axisLabelStyleBold"
                                  change="axisLabelBoldChange(this,'xAxis','axisLabel','textStyle')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="axisLabelStyle" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal"
                                  change="axisLabelChange(this,'xAxis','axisLabel','textStyle','fontStyle')"/>
                        <bf:radio label="斜体" value="italic"
                                  change="axisLabelChange(this,'xAxis','axisLabel','textStyle','fontStyle')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="隔线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineShow">
                        <bf:check change="axisShowCheckChange(this,'splitLine','xAxis','splitLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示隔线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitLine" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'xAxis','splitLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineColor">
                        <bf:check
                                change="axisColorChange(this,'splitLineClrpkr','xAxis','splitLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="splitLineClrpkr" name="splitLineClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineWidthCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'splitLineWidth','xAxis','splitLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="splitLineWidth" name="splitLineWidth" disabled="true" col="7"
                                 min="2" max="20" step="1" value="2"
                                 change="axisPXChange(this,'xAxis','splitLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineTypeCheck">
                        <bf:check
                                change="axisLineTypeCheckChange(this,'splitLineType','xAxis','splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitLineType" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid"
                                  change="axisLineTypeChange(this,'xAxis','splitLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted"
                                  change="axisLineTypeChange(this,'xAxis','splitLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed"
                                  change="axisLineTypeChange(this,'xAxis','splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="隔区" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitAreaShow">
                        <bf:check change="axisShowCheckChange(this,'splitArea','xAxis','splitArea','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示隔区" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitArea" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'xAxis','splitArea','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>

    <div class="panel panel-default div-line-fg">
        <div class="panel-heading" role="tab" id="headingEight">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseEight" aria-expanded="false" aria-controls="collapseEight">
                    Y轴
                </a>
            </h4>
        </div>
        <div id="collapseEight" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEight">
            <div class="panel-body param-content">
                <div id="yAxisDiv" style="display: none">
                    <bf:formGroup>
                        <bc:col col="12" cls="margin-top-Fg"></bc:col>
                    </bf:formGroup>
                </div>
                <bf:formGroup cls="title-tag-grp">
                    <bc:label text="基础" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisPositionYCheck">
                        <bf:check change="axisCheckChange(this, 'axisPositionY', 'yAxis', 'position')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisPositionY" value="bottom" disabled="true">
                        <bf:radio label="居左" value="left" change="axisChange(this, 'yAxis', 'position')"/>
                        <bf:radio label="居右" value="right" change="axisChange(this, 'yAxis', 'position')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameYCheck">
                        <bf:check change="axisNameCheckChange(this,'axisNameY', 'yAxis', 'name')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴名称" col="4" formControl="true"/>
                    <bf:text id="axisNameY" name="axisNameY" col="7" disabled="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNamePositionYCheck">
                        <bf:check change="axisCheckChange(this, 'axisNamePositionY', 'yAxis', 'nameLocation')"/>
                    </bf:checkGroup>
                    <bc:label text="坐标轴名称位置" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisNamePositionY" value="end" disabled="true">
                        <bf:radio label="位于开端" value="start" change="axisChange(this, 'yAxis', 'nameLocation')"/>
                        <bf:radio label="位于末端" value="end" change="axisChange(this, 'yAxis', 'nameLocation')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameYColor">
                        <bf:check change="axisNameColorChange(this,'axisNameYClrpkr','yAxis','nameTextStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="文本颜色" col="4" formControl="true"/>
                    <bf:text id="axisNameYClrpkr" name="" col="7" disabled="true" value="rgb(0, 0, 0)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameSizeYCheck">
                        <bf:check
                                change="axisFontSizeCheckChange(this,'axisNameSizeY','yAxis','nameTextStyle','fontSize')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="axisNameSizeY" name="axisNameSizeY" col="7" disabled="true" min="10" max="25"
                                 step="1" value="12"
                                 change="axisFontSizeChange(this,'yAxis','nameTextStyle','fontSize')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisNameStyleYCheck">
                        <bf:check
                                change="axisNameStyleCheckChange(this,'axisNameStyleY','yAxis','nameTextStyle','fontStyle','axisNameStyleYbold')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="axisNameStyleY" disabled="true">
                        <bf:check label="加粗" value="bold" id="axisNameStyleYbold"
                                  change="axisNameStyleBoldChange(this,'yAxis','nameTextStyle')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="axisNameStyleY" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal"
                                  change="axisNameStyleChange(this,'yAxis','nameTextStyle','fontStyle')"/>
                        <bf:radio label="斜体" value="italic"
                                  change="axisNameStyleChange(this,'yAxis','nameTextStyle','fontStyle')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="轴线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineShowYCheck">
                        <bf:check change="axisShowCheckChange(this,'axisLineShowY','yAxis','axisLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示轴线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLineShowY" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this,'yAxis','axisLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineYColor">
                        <bf:check
                                change="axisColorChange(this,'axisLineYClrpkr','yAxis','axisLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="axisLineYClrpkr" name="axisLineYClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineWidthYCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'axisLineWidthY','yAxis','axisLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="axisLineWidthY" name="axisLineWidthY" disabled="true" col="7" min="2" max="20"
                                 step="1" value="2" change="axisPXChange(this,'yAxis','axisLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLineTypeYCheck">
                        <bf:check
                                change="axisLineTypeCheckChange(this,'axisLineTypeY','yAxis','axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLineTypeY" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid"
                                  change="axisLineTypeChange(this,'yAxis','axisLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted"
                                  change="axisLineTypeChange(this,'yAxis','axisLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed"
                                  change="axisLineTypeChange(this,'yAxis','axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="轴标签" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelShowYCheck">
                        <bf:check change="axisShowCheckChange(this,'axisLabelShowY','yAxis','axisLabel','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示文本标签" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="axisLabelShowY" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'yAxis','axisLabel','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisRotateYCheck">
                        <bf:check change="axisSliderCheckChange(this,'axisRotateY','yAxis','axisLabel','rotate')"/>
                    </bf:checkGroup>
                    <bc:label text="文本标签旋转角度" col="4" formControl="true"/>
                    <input id="axisRotateY" type="text" data-slider-min="-180" data-slider-max="180"
                           data-slider-step="2" data-slider-value="0" data-slider-enabled="false"/>
                    <span id="axisRotateYVal">0</span><span> °</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelIntervalYCheck">
                        <bf:check
                                change="axisSliderCheckChange(this,'axisLabelIntervalY','yAxis','axisLabel','interval')"/>
                    </bf:checkGroup>
                    <bc:label text="文本标签与坐标轴间距" col="4" formControl="true"/>
                    <input id="axisLabelIntervalY" type="text" data-slider-min="0" data-slider-max="50"
                           data-slider-step="2" data-slider-value="0" data-slider-enabled="false"/>
                    <span id="axisLabelIntervalYVal">0</span><span> px</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelFormatYCheck">
                        <bf:check
                                change="axisLabelFormatterChange(this, 'axisLabelFormatY','yAxis','axisLabel','formatter')"/>
                    </bf:checkGroup>
                    <bc:label text="标签内容模板" col="4" formControl="true"/>
                    <bf:text id="axisLabelFormatY" name="axisLabelFormatY" col="7" value="{value}" disabled="true"
                             helpText="模板中可以使用“{value}”获得当前量值"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelYColor">
                        <bf:check
                                change="axisColorChange(this,'axisLabelYClrpkr','yAxis','axisLabel','textStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="文本颜色" col="4" formControl="true"/>
                    <bf:text id="axisLabelYClrpkr" name="" col="7" disabled="true" value="rgb(0, 0, 0)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelSizeYCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'axisLabelSizeY','yAxis','axisLabel','textStyle','fontSize')"/>
                    </bf:checkGroup>
                    <bc:label text="字体大小" col="4" formControl="true"/>
                    <bf:touchpin id="axisLabelSizeY" name="axisLabelSizeY" col="7" disabled="true" min="10" max="25"
                                 step="1" value="12"
                                 change="axisPXChange(this,'yAxis','axisLabel','textStyle','fontSize')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="axisLabelStyleYCheck">
                        <bf:check
                                change="axisLabelCheckChange(this,'axisLabelStyleY','yAxis','axisLabel','textStyle','fontStyle','axisLabelStyleBoldY')"/>
                    </bf:checkGroup>
                    <bc:label text="字体样式" col="4" formControl="true"/>
                    <bf:checkGroup col="2" name="axisLabelStyleYBold" disabled="true">
                        <bf:check label="加粗" value="bold" id="axisLabelStyleBoldY"
                                  change="axisLabelBoldChange(this,'yAxis','axisLabel','textStyle')"/>
                    </bf:checkGroup>
                    <bf:checkGroup col="4" name="axisLabelStyleY" value="normal" disabled="true">
                        <bf:radio label="普通" value="normal"
                                  change="axisLabelChange(this,'yAxis','axisLabel','textStyle','fontStyle')"/>
                        <bf:radio label="斜体" value="italic"
                                  change="axisLabelChange(this,'yAxis','axisLabel','textStyle','fontStyle')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="隔线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineYShow">
                        <bf:check change="axisShowCheckChange(this,'splitLineY','yAxis','splitLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示隔线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitLineY" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'yAxis','splitLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineYColor">
                        <bf:check
                                change="axisColorChange(this,'splitLineYClrpkr','yAxis','splitLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="splitLineYClrpkr" name="splitLineYClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineWidthYCheck">
                        <bf:check
                                change="axisPXCheckChange(this,'splitLineWidthY','yAxis','splitLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="splitLineWidthY" name="splitLineWidthY" disabled="true" col="7"
                                 min="2" max="20" step="1" value="2"
                                 change="axisPXChange(this,'yAxis','splitLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitLineTypeYCheck">
                        <bf:check
                                change="axisLineTypeCheckChange(this,'splitLineTypeY','yAxis','splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitLineTypeY" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid"
                                  change="axisLineTypeChange(this,'yAxis','splitLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted"
                                  change="axisLineTypeChange(this,'yAxis','splitLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed"
                                  change="axisLineTypeChange(this,'yAxis','splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="隔区" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="splitAreaYShow">
                        <bf:check change="axisShowCheckChange(this,'splitAreaY','yAxis','splitArea','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示隔区" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="splitAreaY" value="N" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo"
                                  change="axisShowChange(this, 'yAxis','splitArea','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>

    <div class="panel panel-default div-radar-fg">
        <div class="panel-heading" role="tab" id="headingNight">
            <h4 class="panel-title">
                <a class="collapsed" role="button" data-toggle="collapse"
                   data-parent="#accordion" href="#collapseNight" aria-expanded="false" aria-controls="collapseNight">
                    雷达
                </a>
            </h4>
        </div>
        <div id="collapseNight" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingNight">
            <div class="panel-body tabbable param-content">
                <bf:formGroup cls="title-tag-grp">
                    <bc:label text="基础" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="shapeCheck">
                        <bf:check change="shapeCheckChange(this, 'shape', 'shape')"/>
                    </bf:checkGroup>
                    <bc:label text="底盘形状" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="shape" value="circle" disabled="true">
                        <bf:radio label="棱角型" value="polygon" change="shapeChange(this, 'shape')"/>
                        <bf:radio label="圆环形" value="circle" change="shapeChange(this, 'shape')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup cls="margin-small">
                    <bf:checkGroup col="1" name="radarCenterCheck">
                        <bf:check change="radarCenterChange(this,'radarCenterX','radarCenterY')"/>
                    </bf:checkGroup>
                    <bc:label text="圆心位置" col="4" formControl="true" cls="slider-label-x"/>
                    <span>x</span><span>:</span>
                    <input id="radarCenterX" type="text" data-slider-min="0" data-slider-max="100"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="radarCenterXVal">50</span><span> %</span>
                </bf:formGroup>
                <bf:formGroup cls="slider-margin-large">
                    <bc:label text="" col="5" formControl="true" cls="slider-label-y"/>
                    <span>y</span><span>:</span>
                    <input id="radarCenterY" type="text" data-slider-min="0" data-slider-max="100"
                           data-slider-step="1" data-slider-value="50" data-slider-enabled="false"/>
                    <span id="radarCenterYVal">50</span><span> %</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarRadiusCheck">
                        <bf:check change="radarFirstChange(this, 'radarRadius', 'radius')"/>
                    </bf:checkGroup>
                    <bc:label text="半径长度" col="4" formControl="true" cls="slider-label-x"/>
                    <span>x</span><span>:</span>
                    <input id="radarRadius" type="text" data-slider-min="0" data-slider-max="100"
                           data-slider-step="1" data-slider-value="75" data-slider-enabled="false"/>
                    <span id="radarRadiusVal">75</span><span> %</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarStartAngleCheck">
                        <bf:check change="radarFirstChange(this, 'radarStartAngle', 'startAngle')"/>
                    </bf:checkGroup>
                    <bc:label text="起始角度" col="4" formControl="true" cls="slider-label-x"/>
                    <span>x</span><span>:</span>
                    <input id="radarStartAngle" type="text" data-slider-min="-180" data-slider-max="180"
                           data-slider-step="1" data-slider-value="90" data-slider-enabled="false"/>
                    <span id="radarStartAngleVal">90</span><span> °</span>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarSplitNumberCheck">
                        <bf:check change="radarSplitNumberCheckChange(this,'radarSplitNumber', 'splitNumber')"/>
                    </bf:checkGroup>
                    <bc:label text="环形分割数" col="4" formControl="true"/>
                    <bf:touchpin id="radarSplitNumber" name="radarSplitNumber" disabled="true"
                                 col="7" min="1" max="30" step="1" value="5"
                                 change="radarSplitNumberChange(this, 'splitNumber')"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="轴线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarAxisLineShow">
                        <bf:check change="radarShowCheckChange(this,'radarAxisLine','axisLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示轴线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarAxisLine" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" change="radarShowChange(this,'axisLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarAxisLineColor">
                        <bf:check
                                change="radarColorChange(this, 'radarAxisLineClrpkr','axisLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="radarAxisLineClrpkr" name="radarAxisLineClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarAxisLineWidthCheck">
                        <bf:check
                                change="radarPXCheckChange(this, 'radarAxisLineWidth','axisLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="radarAxisLineWidth" name="radarAxisLineWidth" disabled="true"
                                 col="7" min="2" max="20" step="1" value="2"
                                 change="radarChange(this,'axisLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarAxisLineTypeCheck">
                        <bf:check
                                change="radarLineTypeCheckChange(this, 'radarAxisLineType','axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarAxisLineType" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid" change="radarChange(this,'axisLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted" change="radarChange(this,'axisLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed" change="radarChange(this,'axisLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top" cls="title-tag-grp">
                    <bc:label text="隔线" col="4" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarSplitLineShow">
                        <bf:check change="radarShowCheckChange(this,'radarSplitLine','splitLine','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示网格线" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarSplitLine" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" change="radarShowChange(this,'splitLine','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarSplitLineColor">
                        <bf:check
                                change="radarColorChange(this, 'radarSplitLineClrpkr','splitLine','lineStyle','color')"/>
                    </bf:checkGroup>
                    <bc:label text="线条颜色" col="4" formControl="true"/>
                    <bf:text id="radarSplitLineClrpkr" name="radarSplitLineClrpkr" col="7" disabled="true"
                             value="rgb(68, 67, 67)"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarSTWidthCheck">
                        <bf:check change="radarPXCheckChange(this, 'radarSTWidth','splitLine','lineStyle','width')"/>
                    </bf:checkGroup>
                    <bc:label text="线条宽度" col="4" formControl="true"/>
                    <bf:touchpin id="radarSTWidth" name="radarSTWidth" disabled="true"
                                 col="7" min="2" max="20" step="1" value="2"
                                 change="radarChange(this,'splitLine','lineStyle','width')"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarSTTypeCheck">
                        <bf:check
                                change="radarLineTypeCheckChange(this, 'radarSTType','splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                    <bc:label text="线条类型" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarSTType" value="solid" disabled="true">
                        <bf:radio label="实线" value="solid" change="radarChange(this,'splitLine','lineStyle','type')"/>
                        <bf:radio label="点状线" value="dotted" change="radarChange(this,'splitLine','lineStyle','type')"/>
                        <bf:radio label="虚线" value="dashed" change="radarChange(this,'splitLine','lineStyle','type')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="top">
                    <bf:checkGroup col="1" name="radarSplitAreaShow">
                        <bf:check change="radarShowCheckChange(this,'radarSplitArea','splitArea','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示网格区域" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarSplitArea" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" change="radarShowChange(this,'splitArea','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bf:checkGroup col="1" name="radarAxisLabelShow">
                        <bf:check change="radarShowCheckChange(this,'radarAxisLabel','axisLabel','show')"/>
                    </bf:checkGroup>
                    <bc:label text="是否显示坐标轴文本标签" col="4" formControl="true"/>
                    <bf:checkGroup col="7" name="radarAxisLabel" value="Y" disabled="true">
                        <bf:radio dataSource="fixedDict" url="YesNo" change="radarShowChange(this,'axisLabel','show')"/>
                    </bf:checkGroup>
                </bf:formGroup>
            </div>
        </div>
    </div>
</div>