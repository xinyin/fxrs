<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>披露季度</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="addBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="form1" name="form1" method="post" validation="true">
            <bc:row cls="m-b">
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="披露主体" col="4" formControl="true"/>
                    <bf:text id="pubSubject" name="pubSubject" col="8" value="大家保险集团本级" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="年度" col="4" formControl="true"/>
                    <bf:datebox id="pubYear" name="pubYear" col="6" format="date"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="季度" col="4" formControl="true"/>
                    <bf:select id="pubQuarter" name="pubQuarter" col="6" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Quarter" >
                        <bf:option text="ALL" value=""></bf:option>
                        <be:event type="select">
                            var input = $("#__LIKES_pubQuarter");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
                <bf:formGroup>
                    <div class="col-xs-8  col-xs-offset-4">
                        <bc:button type="primary"  text="生成披露文件" icon="save">
                            <be:event>
                                $('#form1').data('formValidation').validate();
                                var isValid = $('#form1').data('formValidation').isValid();
                                var pubYear = $("[name='pubYear']").val();
                                var pubQuarter = $("[name='pubQuarter']").val();
                                if(pubYear == '' || pubYear == null){
                                $.messager.alert('提示', '年度不能为空。', 'info');
                                isValid = false;
                                }
                                if(pubQuarter == '' || pubQuarter == null){
                                $.messager.alert('提示', '季度不能为空。', 'info');
                                isValid = false;
                                }
                                if(isValid) {
                                var formData = $('#form1').serializeAll();
                                var pubdata = pubYear +","+ pubQuarter;
                                $.mask.post('${base}/pub/publog/exportXlsQuarter/PubMenageQuarterOutCallbackImpl/' + pubdata , formData, function(data){
                                    if(data.success){
                                        $.messager.alert('披露文件生成成功',data.msg,'info',function(){
                                        window.location = '${base}/pub/publog/quarterList';
                                        });
                                    }else{
                                        $.messager.alert('披露文件生成失败',data.msg,'error');
                                    }
                                });
                                }
                            </be:event>
                        </bc:button>
                        <bc:button  type="primary" text="取消" icon="arrow-circle">
                            <be:event>
                                window.location = '${base}/pub/publog/quarterList';
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bc:row>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $("#pubYear").datetimepicker({
        format:"yyyy",
        autoclose: true,
        startView:4,
        minView:4,
        todayHighlight:true
    });

    /* 生成披露文件 */
    function addFile(){
  //      var pubSubject = $("#pubSubject").val();
        var pubYear = $("[name='pubYear']").val();
 //       var pubFileName = $("#pubFileName").val();
        if(pubYear == '' || pubYear == null){
            $.messager.alert('提示', '年度不能为空。', 'info');
        }
        var url = '${base}/sys/xlsie/PubMenageYearOutCallbackImpl/' + pubYear;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
</script>
</body>
</html>
