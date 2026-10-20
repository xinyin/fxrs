<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>列元数据表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaColumnBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="元数据ID" col="4" formControl="true"/>
                    <bf:text id="columnId" name="columnId" col="8" value="${metaColumn.columnId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaColumn/check" name="columnId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="表元数据ID" col="4" formControl="true"/>
                    <bf:text id="tableId" name="tableId" col="8" value="${metaColumn.tableId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="tableCode" col="4" formControl="true"/>
                    <bf:text id="tableCode" name="tableCode" col="8" value="${metaColumn.tableCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="字段名" col="4" formControl="true"/>
                    <bf:text id="columnCode" name="columnCode" col="8" value="${metaColumn.columnCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="中文名" col="4" formControl="true"/>
                    <bf:text id="columnName" name="columnName" col="8" value="${metaColumn.columnName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="数据类型" col="4" formControl="true"/>
                    <bf:text id="dataType" name="dataType" col="8" value="${metaColumn.dataType}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="字段长度" col="4" formControl="true"/>
                    <bf:text id="length" name="length" col="8" value="${metaColumn.length}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="字段精度" col="4" formControl="true"/>
                    <bf:text id="scale" name="scale" col="8" value="${metaColumn.scale}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="是否主键" col="4" formControl="true"/>
                    <bf:text id="isKey" name="isKey" col="8" value="${metaColumn.isKey}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="是否敏感字段" col="4" formControl="true"/>
                    <bf:text id="isSensitive" name="isSensitive" col="8" value="${metaColumn.isSensitive}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="是否外键" col="4" formControl="true"/>
                    <bf:text id="isForeignKey" name="isForeignKey" col="8" value="${metaColumn.isForeignKey}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="字段顺序" col="4" formControl="true"/>
                    <bf:text id="order" name="order" col="8" value="${metaColumn.order}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="字段属性：             O - 机构字段             D - 日期字段             N - 普通字段" col="4"
                              formControl="true"/>
                    <bf:text id="prop" name="prop" col="8" value="${metaColumn.prop}">
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
                            $.mask.post('${base}/sys/metaColumn/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaColumn/list';
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
                            window.location = '${base}/sys/metaColumn/list';
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
