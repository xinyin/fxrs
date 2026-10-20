<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联关系机构</title>
    <link rel="stylesheet" href="/static/css/dialog.css">
    <style>
        .BOX{
            display: flex;
            justify-content: space-around;
            width:100%;
            margin: 1% auto;
        }
        .dialog-confirm{
            width:100% !important;
        }
        .boxchild{
            width: 33%;
            display: flex;
        }
        .BOX  .frm-label{
            width: 7em !important;
        }
        .dialog-section{
            display: block;
            left: 50%;
            position: fixed;
            margin-left: -438px !important;
            margin-top: -144px !important;
            opacity: 1;
            width: 80%  !important;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
        <bc:ibox title="关联关系机构" subTitle="机构关系信息" col="12" collapsible="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:formGroup>
                <bc:col col="6" colOffset="2">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="机构关系信息">
                        <be:event>
                            rightsInformation();
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable" idField="pid" url="${base}/bus/relPsnSuspect"
                          queryUrl="suspectPsnRelOrgSelect/${fromPid}" addUrl="suspectAddPsnRelOrg/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="delPsnRelOrg"/>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>
                    <bc:column field="relCategory" title="关系类型" width="100" visible="false"/>
                    <bc:column field="relCategorySub" title="关系子类型" width="100" visible="false"/>
                    <bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>
                    <bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>
                    <bc:column field="relSort" title="关系类别" width="100" visible="false"/>
                    <bc:column field="position" title="职位" width="100" visible="false"/>
                    <bc:column field="relation" title="亲属关系" width="100" visible="false"/>
                    <bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>
                    <bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>
                    <bc:column field="isShow" title="是否显示" width="100" visible="false"/>
                    <bc:column field="level" title="关系层级" width="100" visible="false"/>
                    <bc:column field="liftReason" title="解除原因" width="100" visible="false"/>
                    <bc:column field="orgName" title="股东名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <%--<bc:column field="relStatus" title="处理状态" formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" width="100"/>--%>
                </bc:table>
            </bc:ibox>
        </bc:ibox>
    </div>


</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">

    function basicInformation() {
        var opt = $("#opt").val();
        // if(opt == "edit"){
        var pid = $("#fromPid").val();
        window.location = '${base}/bus/relPsnSuspect/edit/' + pid + '?';
        <%--}else{--%>
        <%--window.location = '${base}/bus/relPsnSuspect/addForRelPsnAdd';--%>
        <%--}--%>
    }

    function affiliateInformation() {
        var opt = $("#opt").val();
        // if(opt == "edit"){
        var pid = $("#fromPid").val();
        window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/' + pid + '? ';
        <%--}else{--%>
        <%--window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/'+ opt + "/" +pid+'?';--%>
        <%--}--%>
    }

    function rightsInformation() {
        var opt = $("#opt").val();
        var pid = $("#fromPid").val();
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' + pid + '? ';
    }

    function editHandler() {
        var pid = getIdSelections();
        var relId = getPidSelections();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            window.location = '${base}/bus/relPsnSuspect/suspectEditPsnRelOrg/' + pid + "/" + fromPid + "/" + relId;
        } else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }
    }

    function getIdSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.pidForOrgTo;
        });
    }

    function getPidSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.pidForRel;
        });
    }

    function addit() {
        $.sendConfirm({
            title: '解除关联关系',
            content: ' <div>'
            +' <div class="BOX">'
                +' <div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件类型:</div>'
                +'<select class="customerIds" style="width:22em"> <option>请选择</option> <option>审批人1</option> <option>审批人2</option>'
                +'</select>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件号码:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 机构名称:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'</div>'
                +'<div class="BOX">'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件类型:</div>'
                +' <select class="customerIds" style="width:22em"> <option>请选择</option> <option>审批人1</option> <option>审批人2</option>'
                +'</select>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件号码:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 机构名称:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'</div>'
                +'<div class="BOX">'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件类型:</div>'
                +'<select class="customerIds" style="width:22em"> <option>请选择</option> <option>审批人1</option> <option>审批人2</option>'
                +'</select>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 证件号码:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'<div class="boxchild">'
                +'<div class="frm-label" style="width:4em"><span class="requireIcon">*</span> 机构名称:</div>'
                +'<input type="number"  placeholder="请输入证件号码"/>'
                +'</div>'
                +'</div>'
                +'</div>',
            button: {
                confirm: '提交审批',
                cancel: '取消'
            },
            width: 260,
            // onBeforeConfirm: function() {
            // onBeforeConfirm返回false，将阻止onConfirm的执行
            //     return false;
            //   },
            onConfirm: function () {
                console.log('点击确认！');
                $("body").on("blur", "input[name='ip']", function () {
                    console.log(this.value);
                    //如果输入框为空，则替换为0
                    if (this.value == "") {
                        this.value = 0;
                    }
                });
                // var value = document.getElementsByName("ip")[0].value;
                // console.log(value);
            },
            onCancel: function () {
                console.log('点击取消！');
            },
            onClose: function () {
                console.log('点击关闭！');
            }
        });
    }

    //删除
    function delPsnRelOrg() {
        var pidForRel = getPidSelections();
        if (pidForRel == "" || pidForRel == undefined) {
            $.messager.alert("请选中数据")
        } else {
            var r = confirm("确定要删除数据？");
            if (r) {
                $.ajax({
                    url: '${base}/bus/relPsnSuspect/delPsnRelOrg/' + pidForRel,
                    type: 'get',
                    dataType: "json",
                    success: function (res) {
                        $.messager.alert(res.msg);
                        if (res.success) {
                            window.location.reload();
                        }
                    }
                })
            }
        }
    }
</script>
</body>
</html>