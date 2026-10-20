<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>定义系统中的附件上传类型。</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="approveMapBox" title="多部门配置化管理新增" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${approveMap.pid}"/>
            <bf:hidden id="staffName" name="staffName" value="${approveMap.staffName_}"/>
            <bf:hidden id="roleName" name="roleName" value="${approveMap.roleName_}"/>
            <bf:hidden id="roleStdCode" name="roleStdCode" value="${approveMap.roleStdCode_}"/>
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:col col="6">
                <bc:label text="用户编码" col="4" formControl="true"/>
                <bf:lookup url="${base}/sys/staff/staffDlg1" id="staffCode_"
                           name="staffCode_" onSelect="receiverChange" winId="winmainSales" propText="staffCode"
                           propValue="stN" col="8" callback="result" width="700" height="600"  disabled="${opt == 'view'}"
                           value="${approveMap.staffCode_}" text="${approveMap.staffCode_}" >
                </bf:lookup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="staffName_" name="staffName_" col="8" value="${approveMap.staffName_}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
        </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="部门" col="4" formControl="true"/>
                    <bf:unitLookup id="deptCode_" name="deptCode_" col="8"
                                   propText="unitCode" propValue="unitCode" value="${approveMap.deptCode_}" disabled="${opt == 'view'}"
                                   callback="result"  onSelect="selectDdep">
                        <bv:notEmpty/>
                    </bf:unitLookup>
                </bc:col>
               <bc:col col="6">
                    <bc:label text="部门名称" col="4" formControl="true"/>
                    <bf:text id="deptName_" name="deptName_" col="8" value="${approveMap.deptName_}"  disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>

            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="角色编码" col="4" formControl="true"/>
                    <bf:lookup url="${base}/sys/role/roleDlgs" id="roleCode_"
                               name="roleCode_" onSelect="receiverChangeR" winId="winmainSales" propText="roleCode"
                               propValue="roleS" col="8" callback="result" width="700" height="600" disabled="${opt == 'view'}"
                               value="${approveMap.roleCode_}" text="${approveMap.roleCode_}"  >
                    </bf:lookup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="角色名称" col="4" formControl="true"/>
                    <bf:text id="roleName_" name="roleName_" col="8" value="${approveMap.roleName_}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>

            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="标准角色编码" col="4" formControl="true"/>
                    <bf:text id="roleStdCode_" name="roleStdCode_" col="8" value="${approveMap.roleStdCode_}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button type="primary" text="保存" icon="save" id="save2">
                        <be:event>
                            debugger
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/approveMap/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/approveMap/list';
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
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/approveMap/list';
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


    var opt = $("#opt").val();
    if (opt == 'view') {
        $("#save2").css("display", "none");
    }

    function selectDdep(){
        debugger
        var unitCode = $("div#deptCode_ div.input-group input.lookup-value").val();
        //var unitName = $("div#deptCode_ div.input-group input.lookup-text").val();

        //$("#deptCode_").val(unitCode);


        $.ajax({

            type:"POST",

            url:"${base}/sys/unit/queryDep/" + unitCode,

            dataType:"html",

            success:function(result){

                var ajaxobj=eval("("+result+")");

                for(var o in ajaxobj){

                    $("#deptName_").val(ajaxobj[o]);
                }




            }

        });

    }

    /**
     * 数据回显
     */
    function receiverChange() {
        debugger
    let valueList = $("[name='staffCode_']").val();
        if (valueList !== null && valueList !== '' && valueList.length > 0) {
            let list = valueList.split(',');
             let staffCode = list[0];
            let staffName_ = list[1];
             $('#staffCode_').val(null == staffCode_? '' : staffCode_);
            $('#staffName_').val(null == staffName_ ? '' : staffName_);
        }
    }

    /**
     * 数据回显
     */
    function receiverChangeR() {
        debugger
        let valueList = $("[name='roleCode_']").val();
        if (valueList !== null && valueList !== '' && valueList.length > 0) {
            let list = valueList.split(',');
            let roleCode = list[0];
            let roleName_ = list[1];
            let roleStdCode_ = list[2];
            $('#roleCode_').val(null == roleCode_? '' : roleCode_);
            $('#roleName_').val(null == roleName_ ? '' : roleName_);
            $('#roleStdCode_').val(null == roleStdCode_ ? '' : roleStdCode_);
        }
    }
</script>
</body>
</html>
