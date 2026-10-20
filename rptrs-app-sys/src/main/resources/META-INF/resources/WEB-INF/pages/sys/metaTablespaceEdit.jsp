<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表空间</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaTablespaceBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="表空间ID" col="4" formControl="true"/>
                    <bf:text id="tablespaceId" name="tablespaceId" col="8" value="${metaTablespace.tablespaceId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaTablespace/check" name="tablespaceId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="表空间编码" col="4" formControl="true"/>
                    <bf:text id="tablespaceCode" name="tablespaceCode" col="8" value="${metaTablespace.tablespaceCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="表空间名称" col="4" formControl="true"/>
                    <bf:text id="tablespaceName" name="tablespaceName" col="8" value="${metaTablespace.tablespaceName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="表空间描述" col="4" formControl="true"/>
                    <bf:text id="tablespaceDesc" name="tablespaceDesc" col="8" value="${metaTablespace.tablespaceDesc}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="数据文件" col="4" formControl="true"/>
                    <bf:text id="dataFileLoc" name="dataFileLoc" col="8" value="${metaTablespace.dataFileLoc}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="初始大小(M)" col="4" formControl="true"/>
                    <bf:text id="initSize" name="initSize" col="8" value="${metaTablespace.initSize}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="页面大小(K)" col="4" formControl="true"/>
                    <bf:text id="pageSize" name="pageSize" col="8" value="${metaTablespace.pageSize}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="是否扩展: Y/N" col="4" formControl="true"/>
                    <bf:text id="autoExtend" name="autoExtend" col="8" value="${metaTablespace.autoExtend}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="扩展大小(M)" col="4" formControl="true"/>
                    <bf:text id="extendSize" name="extendSize" col="8" value="${metaTablespace.extendSize}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="最大容量(M)" col="4" formControl="true"/>
                    <bf:text id="maxSize" name="maxSize" col="8" value="${metaTablespace.maxSize}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="表空间状态:             10 - 草稿             30 - 启用             50 - 停用" col="4"
                              formControl="true"/>
                    <bf:text id="status" name="status" col="8" value="${metaTablespace.status}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="备注" col="4" formControl="true"/>
                    <bf:text id="remark" name="remark" col="8" value="${metaTablespace.remark}">
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
                            $.mask.post('${base}/sys/metaTablespace/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaTablespace/list';
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
                            window.location = '${base}/sys/metaTablespace/list';
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
