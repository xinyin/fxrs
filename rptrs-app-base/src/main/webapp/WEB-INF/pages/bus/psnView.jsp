<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>自然人新增或编辑</title>
    <x:jsLib/>

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
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
            <bf:hidden id="status" name="status" value="${relPsn.status}"/>
            <bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            <%--basicInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button id = "psnRelInformation" type="info" text="自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id ="orgRelInformation" type="info" text="机构关系信息">
                        <be:event>
                            orgRelInformation();
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <div class="boxse">
                <bf:formGroup type="success" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="姓名" col="5" formControl="true"/>
                        <bf:text id="name" name="name" col="7" value="${relPsn.name}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="性別" col="5" formControl="true"/>
                        <bf:select id="sex" name="sex" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsn.sex}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsn.identifyNumber}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <%--<bc:col col="5">--%>
                    <%--<bc:label text="黑名单标识" col="4" formControl="true"/>--%>
                    <%--<bf:select id="isBlack" name="isBlack" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-isNo" value="${relPsn.isBlack}">--%>
                    <%--</bf:select>--%>
                    <%--</bc:col>--%>
                    <%--<bc:col col="5">--%>
                    <%--<bc:label text="加入黑名单原因" col="4" formControl="true"/>--%>
                    <%--<bf:text id="blackReason" name="blackReason" col="8" value="${relPsn.blackReason}">--%>
                    <%--</bf:text>--%>
                    <%--</bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="政治面貌" col="5" formControl="true"/>
                        <bf:select id="politicsStatus" name="politicsStatus" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS_politically" value="${relPsn.politicsStatus}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="学历" col="5" formControl="true"/>
                        <bf:select id="educationBackground" name="educationBackground" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Education" value="${relPsn.educationBackground}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="民族" col="5" formControl="true"/>
                        <bf:select id="race" name="race" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="境内外标识" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                   value="${relPsn.domesticOrForeign}">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="出生日期" col="5" formControl="true"/>
                        <bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsn.birthdate}">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="婚姻状况" col="5" formControl="true"/>
                        <bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="移动电话" col="5" formControl="true"/>
                        <bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="邮箱" col="5" formControl="true"/>
                        <bf:text id="email" name="email" col="7" value="${relPsn.email}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}" disabled="true">
                        </bf:select>
                    </bc:col>
<%--                    <bc:col col="4">--%>
<%--                        <bc:label text="统计口径" col="5" formControl="true"/>--%>
<%--                        <bf:select id="regulatorOth1" name="regulatorOth1" col="7" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/BUS-regulator" value="${relPsn.regulatorOth1}" disabled="true">--%>
<%--                            <bf:option text="ALL" value=""></bf:option>--%>
<%--                        </bf:select>--%>
<%--                    </bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="居住地址" col="5" formControl="true"/>
                        <bf:text id="residentialAddress" name="residentialAddress" col="7"
                                 value="${relPsn.residentialAddress}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="工作单位" col="5" formControl="true"/>
                        <bf:text id="workUnits" name="workUnits" col="7" value="${relPsn.workUnits}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="单位地址" col="5" formControl="true"/>
                        <bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsn.unitAddress}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="是否为本行主要股东" col="5" formControl="true"/>
                        <bf:checkGroup name="isMainGd" col="7" value="${relPsn.isMainGd}">
                            <bf:radio value="1" label="是"/>
                            <bf:radio value="0" label="否" checked="${opt != 'edit'}"/>
                        </bf:checkGroup>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股权证编号" col="5" formControl="true"/>
                        <bf:text id="capitalNumber" name="capitalNumber" col="7" value="${relPsn.capitalNumber}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东账号" col="5" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="7"
                                 value="${relPsn.shareholdersCode}">
                            <bv:notEmpty/>
                            <bv:remote url="${base}/bus/gdInfo/check/PSN/${relPsn.pid}" name="shareholdersCode" message="该股东编号已存在"/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="交易账户开户行" col="5" formControl="true"/>
                        <bf:text id="openBank" name="openBank" col="7" value="${relPsn.openBank}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="交易账户账号" col="5" formControl="true"/>
                        <bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsn.tradeAccount}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="入股时间" col="5" formControl="true"/>
                        <bf:datebox name="inTime" id="inTime" col="7" format="date" value="${relPsn.inTime}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="入股价格(元/股)" col="5" formControl="true"/>
                        <bf:text id="inPrice" name="inPrice" col="7" value="${relPsn.inPrice}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股数量（股）" col="5" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relPsn.holeShareAmount}"
                                 change="getPorportion()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股占比" col="5" formControl="true"/>
                        <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                                 value="${relPsn.shareholdingProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总冻结股数（股）" col="5" formControl="true"/>
                        <bf:text id="frozenShareAmount" name="frozenShareAmount" col="7"
                                 value="${relPsn.frozenShareAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总质押股数（股）" col="5" formControl="true"/>
                        <bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7"
                                 value="${relPsn.pledgedShareAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="relFileTable" idField="pid" url="${base}/bus/relFile" multiple="true"
                          selectFirst="false" queryUrl="queryList?relSerial=${relPsn.relSerial}">
<%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
<%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="fileName" title="文件名称" width="100"/>
                    <bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
                    <bc:column field="approveStatus" title="操作状态" width="100" visible="false"/>
                    <bc:column field="fileSize" title="文件大小" width="100"/>
                    <bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
                    <bc:column field="uploadAgency" title="上传机构" width="100"/>
                    <bc:column field="uploadDepartment" title="上传部门" width="100"/>
                    <bc:column field="modifiedTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0 ">
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/gdInfo/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
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

    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
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

    function getPorportion() {
        var holeShareAmount = $('#holeShareAmount').val();
        $.ajax({
            type: 'get',
            url: '${base}/bus/gdInfo/getPorportion/' + holeShareAmount,
            dataType: 'json',
            success:function(data){
                if (data.success){
                    var proportion = data.proportion;
                    $('#shareholdingProportion').val(proportion);
                }
            }
        });
    }

    function affiliateInformation() {
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        window.location = '${base}/bus/gdInfo/psnRelPsnView/' + pid;
    }

    function orgRelInformation() {
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        window.location = '${base}/bus/gdInfo/psnRelOrgView/' + pid;
    }
    // $(document).ready(function () {
    //     var opt = $('#opt').val();
    //     if (opt != 'edit' && opt != 'view') {
    //         document.getElementById("psnRelInformation").setAttribute("disabled", true);
    //         document.getElementById("orgRelInformation").setAttribute("disabled", true);
    //     }
    // });
</script>
</body>
</html>
