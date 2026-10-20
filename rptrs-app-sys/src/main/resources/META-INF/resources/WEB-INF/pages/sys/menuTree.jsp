<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>菜单</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="menuBox" title="菜单配置" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="menuTree" url="${base}/sys/menu" edit="true"
                         formId="menuForm" autoParam="id=parentId"
                         formUneditableField="menuCode" popupMenuDefault="" formNameField="menuName"
                         onDefaultData="setDefaultData">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="menuForm" name="menuFormNM" method="post" validation="true">
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="父菜单编码" col="4" formControl="true"/>
                            <bf:text name="parentId" col="8" disabled="true"/>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="父菜单名称" col="4" formControl="true"/>
                            <bf:text name="parentName" col="8" disabled="true"/>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="菜单编码" col="4" formControl="true"/>
                            <bf:text id="menuCode" name="menuCode" col="8" placeholder="如：sys.100">
                                <bv:notEmpty/>
                                <bv:regexp regexp="^[a-zA-Z_]{1}[a-zA-Z\d_]*" message="请输入正确的编码"/>
                                <bv:remote url="${base}/sys/menu/check" name="menuCode" message="该编码已被占用"/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="菜单名称" col="4" formControl="true"/>
                            <bf:text name="menuName" col="8">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="菜单级别" col="4" formControl="true"/>
                            <bf:select name="menuLevel" col="8" dataSource="fixedDict" url="MenuLevel" value="ADM">
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="URL" col="4" formControl="true"/>
                            <bf:text name="url" col="8" placeholder="如：sys/menu/list，或者 #">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="菜单图标" col="4" formControl="true"/>
                            <bf:text name="menuImage" col="6" placeholder="请输入图标样式">
                                <be:event type="keyup">
                                    {
                                    //TODO 点击树节点更新图标
                                    var iconStyle = $(this).val();
                                    $("#iconView").removeClass();
                                    $("#iconView").addClass(iconStyle);
                                    $("#iconView").addClass("fa-2x");
                                    }
                                </be:event>
                            </bf:text>
                            <i id="iconView" class="fa fa-align-justify fa-2x"
                               style="margin-top:5px;width:40px;height:32px;"></i>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="显示序号" col="4" formControl="true"/>
                            <bf:text name="displayOrder" col="8" placeholder="如：10、20、30……">
                                <bv:notEmpty/>
                                <bv:digits/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="公共模块" col="4" formControl="true"/>
                            <bf:checkGroup name="publicMenu" col="8">
                                <bf:check value="Y"/>
                            </bf:checkGroup>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="是否有效" col="4" formControl="true"/>
                            <bf:checkGroup name="enabled" col="8">
                                <bf:check value="Y" checked="true"/>
                            </bf:checkGroup>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="是否可见" col="4" formControl="true"/>
                            <bf:checkGroup name="visibled" col="8">
                                <bf:check value="Y" checked="true"/>
                            </bf:checkGroup>
                            <bf:hidden name="leafNode"></bf:hidden>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="备注" col="2" formControl="true"/>
                        <bf:text name="tips" col="10" multiple="true"/>
                        <bf:hidden id="isChrildrenUpdate" name="isChrildrenUpdate"></bf:hidden>
                        <bf:hidden id="btnSave" name="btnSave"></bf:hidden>
                    </bf:formGroup>
                    <bf:formGroup>
                        <div class="col-sm-4 col-sm-offset-4">
                            <bc:button id="btnSave1" type="primary" text="保存" icon="save">
                                <be:event type="click">
                                    var checked = $('input[name="publicMenu"]').is(':checked');
                                    var url = $('input[name="url"]').val();
                                    if(checked && url == '#'){
                                    $.messager.confirm('确认框', '是否更新所有子节点为公共菜单？', function(r){
                                    if(r){
                                    $('#isChrildrenUpdate').val('Y');
                                    setTimeout(function () { $('#btnSave').click(); }, 200);
                                    }
                                    else
                                    {
                                    $('#isChrildrenUpdate').val('N');
                                    $('#btnSave').click();
                                    }
                                    });
                                    }
                                    else
                                    {
                                    $('#btnSave').click();
                                    }

                                </be:event>
                            </bc:button>
                        </div>
                    </bf:formGroup>
                </bf:form>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    /**
     * 设置新增时节点的默认值
     */
    function setDefaultData(node) {
        console.log(node);
        var parent = node.getParentNode();
        console.log(parent);
        if (parent) {
            pId = parent.id;
            if (parent.children && parent.children.length > 0) {
                var l = parent.children.length;
                var order;
                if (l == 1 && parent.children[l - 1].id == '-1') {
                    order = 100;
                } else if (parent.children[l - 1].id == '-1') {
                    var order = Number(parent.children[l - 2]['displayOrder']) + 100;
                } else {
                    var order = Number(parent.children[l - 1]['displayOrder']) + 100;
                }
                node['displayOrder'] = node['displayOrder'] ? node['displayOrder'] : order;
                node['menuCode'] = node['menuCode'] ? node['menuCode'] : pId + '.' + order;

            } else {
                node['displayOrder'] = node['displayOrder'] ? node['displayOrder'] : '100';
                node['menuCode'] = node['menuCode'] ? node['menuCode'] : pId + '.100';
            }
        }

        /* 设置是否有效和是否可见的默认值 */
        node['enabled'] = node['enabled'] ? node['enabled'] : 'Y';
        node['visibled'] = node['visibled'] ? node['visibled'] : 'Y';
    }
</script>
</body>
</html>