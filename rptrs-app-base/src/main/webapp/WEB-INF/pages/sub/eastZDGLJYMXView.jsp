<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelPar</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="已报送数据" subTitle="" col="12" collapsible="true">
            <%--<div class="fixed-table-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th style="text-align: center; width: 100px; " data-field="tranDate" tabindex="0">
                            <div class="th-inner" style="font-size:25px">重大关联交易</div>
                            <div class="fht-cell"></div>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-index="0">
                        <td colspan="2" width="100%">
                            <span style="float:left; border:1px;" id="traSubName" >推送日期:${time}</span>
                        </td>

                    </tr>
                    </tbody>

                </table></div>--%>
			<bc:table id="zdgljymxTable" idField="id" multipleVisible="false" url="${base}/east/zdgljymx" queryUrl="/viewDetail?id=${id}">
                <bc:column field="id" title="id" width="100" visible="false"/>
<%--                <bc:column field="lsh" title="流水号" width="100" visible="false"/>--%>
<%--                <bc:column field="bxjgdm" title="保险机构代码" width="100" visible="false"/>--%>
<%--                <bc:column field="bxjgmc" title="保险机构名称" width="100" visible="false"/>--%>
<%--                <bc:column field="khbh" title="客户编号" width="100"/>--%>

                <bc:column field="glfbh" title="关联方编号" width="100"/>
                <bc:column field="jybh" title="交易编号" width="100"/>
                <bc:column field="glfmc" title="关联方名称" width="100"/>
                <bc:column field="glflb" title="关联方类别" width="100"/>
                <bc:column field="htqdrq" title="关联交易日期 " width="100"/>
                <bc:column field="gljyywlx" title="关联交易类型" width="100"/>
                <bc:column field="gljyje" title="关联交易金额 " width="100"/>

			</bc:table>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/east/zdgljymx/list';
                    </be:event>
                </bc:button>
            </div>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>