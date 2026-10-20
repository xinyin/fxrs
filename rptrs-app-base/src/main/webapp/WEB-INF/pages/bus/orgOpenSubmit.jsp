<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>RelOrg</title>
    <style>
        .boxse .form-group .col-xs-5 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .form-group .col-xs-4 {
            margin-top: 0.5%;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            <%--basicInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button id="psnRelInformation" type="info" text="关系自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="orgRelInformation" type="info" text="关系机构信息">
                        <be:event>
                            orgRelInformation();
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <div class="boxse">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="企业名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="企业类型" col="5" formControl="true"/>
                        <bf:select id="orgType" name="orgType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-CompanyType" value="${relOrg.orgType}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrg.uniscCode}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                        <bf:text id="regCapital" name="regCapital" col="7" value="${relOrg.regCapital}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                        <bf:select id="regCapitalCurrency" name="regCapitalCurrency" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Currency"
                                   col="7" value="${relOrg.regCapitalCurrency}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="营业范围" col="5" formControl="true"/>
                        <bf:text id="businessScope" name="businessScope" col="7" value="${relOrg.businessScope}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册地" col="5" formControl="true"/>
                        <bf:text id="regAddress" name="regAddress" col="7" value="${relOrg.regAddress}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构状态" col="5" formControl="true"/>
                        <bf:select id="status" name="status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relOrg.mgerOrg}" disabled="true">
                        </bf:select>
                    </bc:col>
<%--                    <bc:col col="4">--%>
<%--                        <bc:label text="统计口径" col="5" formControl="true"/>--%>
<%--                        <bf:select id="regulatorOth1" name="regulatorOth1" col="7" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/BUS-regulator" value="${relOrg.regulatorOth1}"--%>
<%--                                   disabled="true">--%>
<%--                            <bf:option text="ALL" value=""></bf:option>--%>
<%--                        </bf:select>--%>
<%--                    </bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="法定代表人" col="5" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrg.legalPerson}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="legalIdentifyType" name="legalIdentifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.legalIdentifyType}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="7"
                                 value="${relOrg.legalIdentifyNumber}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="境内外标识" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                   value="${relOrg.domesticOrForeign}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="是否为本行主要股东" col="5" formControl="true"/>
                        <bf:checkGroup name="isMainGd" col="7" value="${relOrg.isMainGd}">
                            <bf:radio value="1" label="是"/>
                            <bf:radio value="0" label="否" checked="${opt != 'edit'}"/>
                        </bf:checkGroup>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="是否国有企业" col="5" formControl="true"/>
                        <bf:select id="stateOwned" name="stateOwned" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.stateOwned}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东账号" col="5" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="7"
                                 value="${relOrg.shareholdersCode}">
                            <bv:notEmpty/>
                            <bv:remote url="${base}/bus/gdInfo/check/ORG/${relOrg.pid}" name="shareholdersCode"
                                       message="该股东账号已存在"/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联银行账户开户行" col="5" formControl="true"/>
                        <bf:text id="openBank" name="openBank" col="7" value="${relOrg.openBank}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联银行户账号" col="5" formControl="true"/>
                        <bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relOrg.tradeAccount}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股数量" col="5" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relOrg.holeShareAmount}"
                                 change="getPorportion()">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股占比" col="5" formControl="true"/>
                        <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                                 value="${relOrg.shareholdingProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总冻结股数（股）" col="5" formControl="true"/>
                        <bf:text id="frozenShare" name="frozenShare" col="7"
                                 value="${relOrg.frozenShare}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总质押股数（股）" col="5" formControl="true"/>
                        <bf:text id="pledgedShare" name="pledgedShare" col="7"
                                 value="${relOrg.pledgedShare}" disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <div id="tableDiv" style="width: 95%;margin:0 auto">
                <bc:table id="relFileTable" idField="pid" url="${base}/bus/relFile" multiple="true"
                          selectFirst="false" queryUrl="queryList?relSerial=${relOrg.relSerial}">
                    <%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
                    <%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="fileName" title="文件名称" width="100"/>
                    <bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
                    <bc:column field="approveStatus" title="审批状态" width="100" visible="false"/>
                    <bc:column field="fileSize" title="文件大小" width="100"/>
                    <bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
                    <bc:column field="uploadAgency" title="上传机构" width="100"/>
                    <bc:column field="uploadDepartment" title="上传部门" width="100"/>
                    <bc:column field="modifiedTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bc:col col="12">
                <bc:label text="提交备注" col="1" formControl="true"/>
                <bf:text id="statusComment" name="statusComment" col="7" multiple="true">
                </bf:text>
            </bc:col>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0 ">
                    <bc:button type="success" text="确认提交" icon="arrow-circle-right" click="sub()">
                        <be:event>
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            <%--var uniscCode = $('#uniscCode').value();--%>
                            window.location = '${base}/bus/gdInfo/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    function exportIn() {
        $("#hiddenBtn").click();
    }

    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/relFile/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#relFileTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/relFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }


    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/relFile/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.relFile;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#relFileTable').bootstrapTable('load', showData);
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
            }
        })
    });
    // $(document).ready(function () {
    //     var opt = $('#opt').val();
    //     if (opt != 'edit') {
    //         document.getElementById("psnRelInformation").setAttribute("disabled", true);
    //         document.getElementById("orgRelInformation").setAttribute("disabled", true);
    //     }
    // });

    //表单提交数据
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeAll();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    //文件表数据
    function getData() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function basicInformation() {
        var pid = $('#pid').val();
        window.location.href = "${base}/bus/gdInfo/edit/" + pid;
    }

    function affiliateInformation() {
        var pid = $('#pid').val();
        if (pid == null || pid == "") {
            alert("请先保存基本信息!")
        } else {
            window.location = '${base}/bus/gdInfo/orgRelPsnListSubmit/' + pid;
        }

    }

    function orgRelInformation() {
        var pid = $('#pid').val();
        if (pid == null || pid == "") {
            alert("请先保存基本信息!")
        } else {
            window.location = '${base}/bus/gdInfo/orgRelOrgListSubmit/' + pid;
        }

    }

    // $(document).ready(function () {
    //     var opt = $('#opt').val();
    //     if (opt != 'edit') {
    //         document.getElementById("psnRelInformation").setAttribute("disabled", true);
    //         document.getElementById("orgRelInformation").setAttribute("disabled", true);
    //     }
    // });

    function getPorportion() {
        var holeShareAmount = $('#holeShareAmount').val();
        $.ajax({
            type: 'get',
            url: '${base}/bus/gdInfo/getPorportion/' + holeShareAmount,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var proportion = data.proportion;
                    $('#shareholdingProportion').val(proportion);
                }
            }
        });
    }

    function sub() {
        var pid = $('#pid').val();
        var statusComment = $('#statusComment').val();
        var obj = {
            pid: pid,
            type: 'ORG',
            statusComment: statusComment
        };
        $.ajax({
            type: 'get',
            url: '${base}/bus/gdInfo/changeApplyStatus',
            dataType: 'json',
            data: obj,
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "open";
                    formData.gdType = "ORG";
                    var data = JSON.stringify(formData);
                    formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'gd',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeAll();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
</script>
</body>
</html>
