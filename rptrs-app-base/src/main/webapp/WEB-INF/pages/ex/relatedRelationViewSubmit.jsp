<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>投资入股类信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="投资入股类信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="4">
                <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                <bc:label text="报送机构：${subOrg}" col="5" formControl="true"/>
                <bc:label text="操作人：${operator}" col="5" formControl="true"/>
            </bf:formGroup>
            <bc:table id="relatedRelationTable" idField="pid" url="${base}/sub/relatedRelation" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="relationSeq" title="关联关系图谱流水号" width="100" visible="false"/>
                <bc:column field="entityName" title="关联方名称/关联方中间节点名称" width="100" />
                <bc:column field="entityUniscUode" title="关联方代码/关联方中间节点代码" width="100" />
                <bc:column field="entityType" title="关联方/关联方中间节点类型" width="100" />
                <bc:column field="superEntityName" title="前一层关联方名称/关联方中间节点名称" width="100" />
                <bc:column field="superEntityUniscCode" title="前一层关联方代码/关联方中间节点代码" width="100" />
                <bc:column field="superEntityType" title="前一层关联方/关联方中间节点类型" width="100" />
                <bc:column field="relationType" title="与前一层的关联关系" width="100" />
                <bc:column field="isShareHolding" title="与前一层之间是否有权益份额" width="100" />
                <bc:column field="holdingPercent" title="持有比例" width="100" />
                <bc:column field="relationInfo" title="关联关系说明" width="100" />
                <bc:column field="validStatus" title="该关系是否仍然有效" width="100" />
                <bc:column field="activeTime" title="生效日期" width="100" />
                <bc:column field="invalidTime" title="失效日期" width="100" />
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/relatedRelation/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>