<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>描述元数据表之间的关联</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaRelationBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="表关系ID" col="4" formControl="true"/>
                    <bf:text id="relationId" name="relationId" col="8" value="${metaRelation.relationId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaRelation/check" name="relationId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="主表ID" col="4" formControl="true"/>
                    <bf:text id="masterTableId" name="masterTableId" col="8" value="${metaRelation.masterTableId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="主表模式" col="4" formControl="true"/>
                    <bf:text id="masterTableSchema" name="masterTableSchema" col="8"
                             value="${metaRelation.masterTableSchema}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="主表编码" col="4" formControl="true"/>
                    <bf:text id="masterTableCode" name="masterTableCode" col="8"
                             value="${metaRelation.masterTableCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="主表列编码" col="4" formControl="true"/>
                    <bf:text id="masterColumnCode" name="masterColumnCode" col="8"
                             value="${metaRelation.masterColumnCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="joinTableId" col="4" formControl="true"/>
                    <bf:text id="joinTableId" name="joinTableId" col="8" value="${metaRelation.joinTableId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="关联表模式" col="4" formControl="true"/>
                    <bf:text id="joinTableSchema" name="joinTableSchema" col="8"
                             value="${metaRelation.joinTableSchema}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="关联表编码" col="4" formControl="true"/>
                    <bf:text id="joinTableCode" name="joinTableCode" col="8" value="${metaRelation.joinTableCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="关联表列编码" col="4" formControl="true"/>
                    <bf:text id="joinColumnCode" name="joinColumnCode" col="8" value="${metaRelation.joinColumnCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="关联关系" col="4" formControl="true"/>
                    <bf:text id="joinDetails" name="joinDetails" col="8" value="${metaRelation.joinDetails}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/metaRelation/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaRelation/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/metaRelation/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>
</body>
</html>
