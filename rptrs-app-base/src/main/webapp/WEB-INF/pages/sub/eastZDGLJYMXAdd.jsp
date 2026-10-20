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
		<bc:ibox title="待生成数据列表" subTitle="" col="12" collapsible="true">
			<bc:table id="zdgljymxTable" idField="id" multipleVisible="false" url="${base}/east/zdgljymx" queryUrl="/querySubFile">
				<bc:toolbar id="addEastGlfxView" text="生成报送文件" type="button" handler="frmSubmitFile"/>
                <bc:column field="id" title="id" width="100" visible="false"/>
<%--                <bc:column field="lsh" title="流水号" width="100" visible="false"/>--%>
<%--                <bc:column field="bxjgdm" title="保险机构代码" width="100" visible="false"/>--%>
<%--                <bc:column field="bxjgmc" title="保险机构名称" width="100" visible="false"/>--%>
<%--                <bc:column field="khbh" title="客户编号" width="100"/>--%>

                <bc:column field="glfbh" title="关联方编号" width="100"/>
                <bc:column field="jybh" title="交易编号" width="100"/>
                <bc:column field="glfmc" title="关联方名称" width="100"/>
<%--                <bc:column field="glfsbm" title="关联方识别码 " width="100"/>--%>
                <bc:column field="glflb" title="关联方类别" width="100"/>
<%--                <bc:column field="jybd" title="交易标的  " width="100"/>--%>
<%--                <bc:column field="tyjyxybz" title="统一交易协议标志" width="100"/>--%>
<%--                <bc:column field="htbh" title="合同编号  " width="100"/>--%>
                <bc:column field="htqdrq" title="关联交易日期 " width="100"/>
<%--                <bc:column field="ywqsrq" title="业务起始日期 " width="100"/>--%>
<%--                <bc:column field="ywjsrq" title="业务结束日期 " width="100"/>--%>
                <bc:column field="gljyywlx" title="关联交易类型" width="100"/>
                <bc:column field="gljyje" title="关联交易金额 " width="100"/>
<%--                <bc:column field="ddzdgljydqx" title="达到重大关联交易的情形" width="100"/>--%>
<%--                <bc:column field="bndygglffsgljyljje" title="本年度与该关联方发生关联交易累计金额 " width="100"/>--%>
<%--                <bc:column field="gljyjg" title="关联交易价格 " width="100"/>--%>
<%--                <bc:column field="djzc" title="定价政策  " width="100"/>--%>
<%--                <bc:column field="dcwzkjjycgdyx" title="对财务状况及经营成果的影响  " width="100"/>--%>
<%--                <bc:column field="zqlx" title="证券类型  " width="100"/>--%>
<%--                <bc:column field="syl" title="收益率 " width="100"/>--%>
<%--                <bc:column field="jysl" title="交易数量  " width="100"/>--%>
<%--                <bc:column field="bdgbhfezb" title="变动股比或份额占比 " width="100"/>--%>
<%--                <bc:column field="bdhgbhfezb" title="变动后股比或份额占比  " width="100"/>--%>
<%--                <bc:column field="sxrq" title="生效日期  " width="100"/>--%>
<%--                <bc:column field="xzspwh" title="行政审批文号 " width="100"/>--%>
<%--                <bc:column field="btzqyqk" title="被投资企业情况  " width="100"/>--%>
<%--                <bc:column field="qcjyjg" title="前次交易价格 " width="100"/>--%>
<%--                <bc:column field="qcjyrq" title="前次交易日期 " width="100"/>--%>
<%--                <bc:column field="tzfs" title="投资方式  " width="100"/>--%>
<%--                <bc:column field="gqbl" title="股权比例  " width="100"/>--%>
<%--                <bc:column field="jrcpmc" title="金融产品名称 " width="100"/>--%>
<%--                <bc:column field="cppz_zc_bah" title="产品批准/注册/备案号  " width="100"/>--%>
<%--                <bc:column field="jrcpfxgm" title="金融产品发行规模" width="100"/>--%>
<%--                <bc:column field="tzjrcpfezb" title="投资金融产品份额占比  " width="100"/>--%>
<%--                <bc:column field="dczcsjglfbz" title="底层资产涉及关联方标志" width="100"/>--%>
<%--                <bc:column field="dcjczcsjkggdhkggddglfbz" title="底层基础资产涉及控股股东或控股股东的关联方标志" width="100"/>--%>
<%--                <bc:column field="dczcjtms" title="底层资产具体描述" width="100"/>--%>
<%--                <bc:column field="dczcsjdglfmc" title="底层资产涉及的关联方名称 " width="100"/>--%>
<%--                <bc:column field="btqymc" title="被投企业名称 " width="100"/>--%>
<%--                <bc:column field="ywms" title="业务模式  " width="100"/>--%>
<%--                <bc:column field="ylyq" title="盈利预期  " width="100"/>--%>
<%--                <bc:column field="tzbl" title="投资比例  " width="100"/>--%>
<%--                <bc:column field="tzsyfpqk" title="投资收益分配情况" width="100"/>--%>
<%--                <bc:column field="ssfdqk" title="损失分担情况 " width="100"/>--%>
<%--                <bc:column field="bdzcqk" title="标的资产情况 " width="100"/>--%>
<%--                <bc:column field="bdqk" title="标的情况  " width="100"/>--%>
<%--                <bc:column field="dbtj" title="担保条件  " width="100"/>--%>
<%--                <bc:column field="kgzgsjybz" title="控股子公司交易标志 " width="100"/>--%>
<%--                <bc:column field="kgzgsqc" title="控股子公司全称  " width="100"/>--%>
<%--                <bc:column field="kgzgszjhm" title="控股子公司证件号码 " width="100"/>--%>
<%--                <bc:column field="qtsmsx" title="其他说明事项" width="100"/>--%>

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
	//生成报送文件
	function frmSubmitFile() {
		var data = "E4";
		var url = '${base}/east/zdgljymx/createSubRelParFile/' + data;
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			dataType: 'json',
			success: function (data) {
                if (data.success==false)
                {
                    if (data.msg == "暂无报送数据生成") {
                        $.messager.alert('提示', data.msg, 'info');
                    } else {
                        $.messager.alert('失败', data.msg, 'error');
                    }
                }
                else{
                    $.messager.alert('成功', data.msg, 'info', function () {
                        window.location = '${base}/east/zdgljymx/list';
                    });
                }
			}
		});
	}
</script>
</body>
</html>