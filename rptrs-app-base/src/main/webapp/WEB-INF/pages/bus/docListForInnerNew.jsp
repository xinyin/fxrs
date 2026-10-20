<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>内部制度</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2{
            width: 100% !important;
        }
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }
        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }



        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">

    <div class="row">
        <div style="margin-left:20px; ">
            <button type="button" class="btn btn-primary" onclick="changeDiv1()" id="bt1">制度信息</button>
            <button type="button" class="btn btn-light" onclick="changeDiv2()" id="bt2">关联外规</button>
        </div>
        <bf:form id="editForm" method="post" validation="true">
            <%--制度信息--%>
            <div id="d1">
                <bc:ibox title="内部制度" subTitle="" col="12" collapsible="true">

                    <bf:hidden id="opt" name="opt" value="${doc.pid}"/>
                    <bf:formGroup >
                        <bc:col col="4">
                            <bc:label text="制度名称" col="4" formControl="true"/>
                            <bf:text id="sysName" name="sysName" col="7"
                                     value="${doc.sysName}">
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="文号" col="4" formControl="true"/>
                            <bf:text id="articleNumber" name="articleNumber" col="7"
                                     value="${doc.articleNumber}">
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="时效性" col="4" formControl="true"/>
                            <bf:select id="isVal" name="isVal" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS_IsValid" value="${doc.isVal}">
                                <bf:option text="请选择"></bf:option>
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup >
                        <bc:col col="4">
                            <bc:label text="发文部门" col="4" formControl="true"/>
                            <bf:select id="dispatchDep" name="dispatchDep" col="7"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS_DispatchDep" value="${doc.dispatchDep}">
                                <bf:option text="请选择"></bf:option>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="生效日期" col="4" formControl="true"/>
                            <bf:datebox name="beginDate" id="beginDate" col="7" format="date"
                                        value="${doc.effectiveTime}">
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="失效日期" col="4" formControl="true"/>
                            <bf:datebox name="endDate" id="endDate" col="7" format="date" value="${doc.expiryTime}">
                            </bf:datebox>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup >
                        <bc:col col="4">
                            <bc:label text="制度文件" col="4" formControl="true"/>
                        </bc:col>
                    </bf:formGroup>

                    <div id="tableDiv" style="">
                        <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" multiple="true"
                                  selectFirst="false">
                            <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                            <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                            <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                            <bc:column field="pid" title="主键" width="100" visible="false"/>
                            <bc:column field="fileName" title="文件名" width="100"/>
                            <bc:column field="fileType" title="文件类型" width="100"/>
                            <bc:column field="fileSize" title="大小" width="100"/>
                            <bc:column field="uploadOrg" title="上传机构" width="100"/>
                            <bc:column field="uploadDep" title="上传部门" width="100"/>
                            <bc:column field="createTime" title="上传时间" width="100"/>
                        </bc:table>
                    </div>
                </bc:ibox>
            </div>
            <%--关联外规1--%>
            <div id="d2" style="display: none">
                <div id="outsideList">
                    <bc:ibox title="" subTitle="" col="12" collapsible="true">

                        <bc:row cls="m-b">
                            <bc:col col="3">
                                <bc:label text="" col="4" formControl="true"/>
                            </bc:col>
                        </bc:row>
                        <div id="tableDiv" style="">
                            <bc:table id="docTable1" idField="pid" url="" selectFirst="false"
                                      multiple="true">
                                <bc:toolbar id="selectDoc" text="选择" type="button" handler="selectDoc"/>
                                <bc:toolbar id="removeOutsideDoc" text="删除" type="button" handler="removeOutsideDoc"/>
                                <bc:toolbar id="view" text="查看详情" type="button" handler="viewOutsideDoc"/>
                                <bc:column field="pid" title="主键" width="100" visible="false"/>
                                <bc:column field="sysName" title="外规名称" width="300"/>
                            </bc:table>
                        </div>
                    </bc:ibox>
                </div>
                    <%--外规详情页面--%>
                <div id="outsideDetial">

                </div>
            </div>
        </bf:form>
        <%--选择外规2--%>
        <div id="d3" style="display: none">
            <bc:ibox title="" subTitle="" col="12" collapsible="true">
                <bf:form id="queryForm" validation="true">
                    <bf:hidden name="systemType" id="systemType" value="O"></bf:hidden>
                    <bc:row cls="m-b">
                        <bf:formGroup col="4">
                            <bc:label text="外规名称" col="4" formControl="true"/>
                            <bf:text name="__LIKES_sysName" col="8" placeholder="外规名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="文号" col="4" formControl="true"/>
                            <bf:text name="__LIKES_articleNumber" col="8" placeholder="文号"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="时效性" col="4" formControl="true"/>
                            <bf:select name="__LIKES_isVal" col="8" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS_IsValid">
                                <bf:option text="ALL" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                    </bc:row>
                    <bc:row cls="m-b">
                        <bf:formGroup col="4">
                            <bc:label text="颁布机构" col="4" formControl="true"/>
                            <bf:select name="__LIKES_issueOrg" col="8" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS_IssueOrg">
                                <bf:option text="ALL" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <%--<bc:col col="3" colOffset="1">--%>
                            <%--<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
                            <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
                        <%--</bc:col>--%>
                    </bc:row>
                    <bc:row cls="m-b">
                        <div class="col-sm-4 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                        </div>
                    </bc:row>
                    </bf:form>
                    <bc:table id="docTable2" idField="pid" url="${base}/bus/docForOutside" selectFirst="false"
                              multiple="true">
    <%--                    <bc:toolbar id="viewDocOutside" text="查看" type="button" handler="viewDocOutside"/>--%>
                    <bc:toolbar id="commitSelect" text="确定选择" type="button" handler="commitSelect"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="sysName" title="外规名称" width="100"/>
                    <bc:column field="articleNumber" title="文号" width="100"/>
                    <bc:column field="dispatchDep" title="颁布机构" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS_DispatchDep"/>
                    <%--<bc:column field="uploadOrg" title="上传机构" width="100"/>--%>
                    <%--<bc:column field="uploadDep" title="上传部门" width="100"/>--%>
                    <bc:column field="isVal" title="时效性" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS_IsVal"/>
                    <bc:column field="effectiveTime" title="颁布日期" width="100"/>
                    <%--<bc:column field="issueOrg" title="颁布机构" width="100"/>--%>
                    <%--<bc:column field="systemType" title="制度类型" width="100"/>--%>
                    <bc:operator id="view" icon="search" title="查看"/>
                    <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
        </div>
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:formGroup  >
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="danger" text="保存" click="save()">
                        <%-- <be:event>
                             $('#editForm').data('formValidation').validate();
                             var isValid = $('#editForm').data('formValidation').isValid();
                             if(isValid)
                             {
                             var formData = $('#editForm').serializeAll();
                             $.mask.post('${base}/bus/docForInner/save',formData,function(data){
                             if(data.success)
                             {
                             $.messager.alert('提示', data.msg, 'info', function(){

                             window.location = '${base}/bus/docForInner/list';
                             });
                             }
                             else
                             {
                             $.messager.alert('错误', data.msg, 'error');
                             }
                             });
                             }
                         </be:event>--%>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/docForInner/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
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
                    <div style="text-align: center" id="loading"></div>
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="${base}/static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">
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
<br:js pack="list"/>
<be:processEvent/>
<script>


    function exportIn() {
        $("#hiddenBtn").click();
    }

    //查看外规
    viewOutsideDoc = function () {
        var pid = getIdSelections1();
        if (pid == '' || pid == undefined) {
            alert("选择一条数据");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选择一条数据")
            return;
        }
        window.location='${base}/bus/docForOutside/view/'+pid;
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
                url: '${base}/bus/docFile/removeDoc/' + pids,
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
                        $('#docTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }


    //文件上传
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
            url: '${base}/bus/docFile/uploadDocFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend:function(XMLHttpRequest){
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.fileData;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#docTable').bootstrapTable('load', showData);
                    $("#loading").empty();
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });
    changeDiv1 = function () {
        $("#bt1").attr("class", "btn btn-primary");
        $("#bt2").attr("class", "btn btn-light");
        $("#d1").attr("style", "display:");
        $("#d2").attr("style", "display:none");
        $("#d3").attr("style", "display:none");
    }
    changeDiv2 = function () {
        $("#bt1").attr("class", "btn btn-light");
        $("#bt2").attr("class", "btn btn-primary");
        $("#d1").attr("style", "display:none");
        $("#d2").attr("style", "display:");
        $("#d3").attr("style", "display:none");
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
            + encodeURIComponent('${base}/bus/docFile/preview?pid='+pid+'&fileName='+fileName+'.pdf'));
    }


    //删除外规
    removeOutsideDoc = function () {
        var pid = $("#opt").val();
        if (pid == undefined || pid == '') {
            alert("暂无关联外规")
            return;
        } else {
            if (!confirm("确认删除?")) {
                return;
            }
            //获取表格中选中数据
            //删除前表的数据
            var selectData = getselectDataId1();
            var outsidePids = getIdSelections1();
            var allData = getData1()//所有数据
            //选中数据数组的下标集合
            var selectIndex = [];
            for (var i = 0; i < selectData.length; i++) {
                selectIndex[i] = allData.indexOf(selectData[i]);
            }
            $.ajax({
                url: '${base}/bus/docForInner/delRelForSysOutside/' + pid + '/' + outsidePids,
                type: 'get',
                dataType: "json",
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //直接移除数据，构造数据显示参数
                        for (var j = 0; j < selectIndex.length; j++) {
                            allData.splice(selectIndex[j], 1);
                        }

                        //构造显示数据
                        var data = {'total': allData.length, 'rows': allData};
                        $('#docTable1').bootstrapTable('load', data);
                    }
                }
            })
        }
    }
    //保存
    save = function () {
        var obj1 = $('#editForm').serializeJson()
        var opt = $('#opt').val();
        var sysName = $('#sysName').val();
        var articleNumber = $('#articleNumber').val();
        var isVal = $('#isVal').find("option:selected").val();
        var dispatchDep = $('#dispatchDep').find("option:selected").val();
        var beginDate = obj1['beginDate'];
        var endDate = obj1['endDate']
        if(beginDate > endDate){
            alert("请正确填写日期");
            return;
        }
        //获取表格1数据
        var docInnerPids = getIdSelections();
        if (docInnerPids == '' || docInnerPids == undefined) {
            alert("文件不能为空,请选中或上传文件");
            return;
        }
        var docOutsidePids = getIdSelections2();
        var obj = {
            'opt': opt,
            'sysName': sysName,
            'articleNumber': articleNumber,
            'isVal': isVal,
            'dispatchDep': dispatchDep,
            'beginDate': beginDate,
            'endDate': endDate,
            'docTableSelectItem': docInnerPids,
            'docTable1SelectItem': docOutsidePids
        };
        var objJson = JSON.stringify(obj);
        /* var formData=$("#editForm").serializeJson();
         var docTableSelectItem=[];
         var docTable1SelectItem=[];
         var str =getIdSelections().splice(',');
         var str1=getIdSelections1().splice(',');
         if(str.indexOf(','==-1)){
             docTableSelectItem[0]=str;
         }else{
             docTableSelectItem=getIdSelections().splice(',')
         }
         if(str1.indexOf(','==-1)){
             docTableSelectItem1[0]=str1;
         }else{
             docTable1SelectItem=getIdSelections1().splice(',')
         }
         var arr=objToArr(formData);
         arr.push({'docTableSelectItem':docTableSelectItem})
         arr.push({'docTable1SelectItem':docTable1SelectItem});
         var data=arrToObJson(arr);*/
        $.ajax({
            contentType: 'application/json',
            url: '${base}/bus/docForInner/save',
            type: 'post',
            dataType: 'json',
            data: objJson,
            success: function (res) {
                alert(res.msg);
                if (res.success) {
                    window.location = '${base}/bus/docForInner/list';
                }
            }
        })
    }

    /*$('#docTable').bootstrapTable({
        columns: columns, //列对象
        data: data, //要显示的数据对象
        onClickCell:function(field, value, row, $element)
        {
            alert(JSON.stringify(field));
        }
    });*/

    selectDoc = function () {
        $('#d3').attr('style', 'display:');
        $('#d2').attr('style', 'display:none');
    }
    //点击确认选择
    commitSelect = function () {
        $('#d2').attr('style', 'display:');
        $('#d3').attr('style', 'display:none');
        //获取原有数据
        var selectData1 = getData1();
        //获取选择的数据
        var selectData2 = getselectDataId2();
        //数据相加
        var selectData = selectData1.concat(selectData2);
        for (var i = 0; i < selectData1.length; i++) {
            if (selectData2.indexOf(selectData1[i]) == -1) {
                selectData2.concat(selectData1[i]);
            }
        }
        var showData = {'total': selectData2.length, 'rows': selectData2};
        //展示到table1
        $('#docTable1').bootstrapTable('load', showData);

    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getIdSelections1() {
        return $.map($('#docTable1').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getIdSelections2() {
        return $.map($('#docTable2').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //删除按键选择主键
    function editHandler1() {
        var $ids = [];    //定义一个空数组
        var $chkBoxes = $('#relTable2').find('input:checked');
        if ($chkBoxes.length == 0) {
            alert('请至少选择一个数据集');
            return false;
        }
-
        $($chkBoxes).each(function () {
            $ids.push($(this).attr('value'));
        });
        var $ids_str = $ids.join(',');
        window.location = "${base}/bus/rel/editOrg/" + $ids_str;
    }

    //页面初始化时展示文件列表数据和关联外规的数据
    $(function () {

        var url = window.location.href;
        if (url.indexOf('add') != -1) {
            return;
        }

        //编辑操作
        var arr = url.split('/');
        var docPid = arr[arr.length - 1];
        //查询文件表数据
        $.ajax({
            type: 'get',
            url: '${base}/bus/docFile/query?docPid=' + docPid,
            dataType: 'json',
            success: function (res) {
                $('#docTable').bootstrapTable('load', res);
            }
        })
        //查询中间关联表数据
        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '${base}/bus/docForInner/selectDocForInnerPid/' + docPid,
            success: function (res) {
                if (res.success) {
                    //设置表格显示数据
                    var total = res.fileData.length;
                    var data = {'total': total, 'rows': res.fileData};
                    $('#docTable1').bootstrapTable('load', data);
                }
            }
        })
        //table2外规doc数据自动加载并全量展示
    })
    //上传文件后文件表格数据展示

    //展示数据方法
    function showTableData(data) {

        $('#docTable').bootstrapTable('load', data);
    }

    //获取表格中所有的数据主键
    function getAllDataId() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    // function getAllDataId2() {
    //     return $.map($('#docTable2').bootstrapTable('getSelections'), function (row) {
    //         return row.pid//返回数据行中的id值
    //     });
    // }


    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    function getselectDataId1() {
        return $.map($('#docTable1').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    function getselectDataId2() {
        return $.map($('#docTable2').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //外规1所有数据
    function getData1() {
        return $.map($('#docTable1').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //外规2所有数据
    function getData2() {
        return $.map($('#docTable2').bootstrapTable('getData'), function (row) {
            return row
        });
    }

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

    //将表单格式的数组转换成key:value数组
    function getArr(arr) {
        var obj = {}
        for (var item in arr) {
            obj[arr[item].name] = arr[item].value;
        }
        ;
        return obj;
    }

    function editHandler1() {
        var $ids = [];    //定义一个空数组
        var $chkBoxes = $('#relTable2').find('input:checked');
        if ($chkBoxes.length == 0) {
            alert('请至少选择一个数据集');
            return false;
        }

        $($chkBoxes).each(function () {
            $ids.push($(this).attr('value'));
        });
        var $ids_str = $ids.join(',');
        window.location = "${base}/bus/rel/editOrg/" + $ids_str;
    }

    //查看
    <%--viewDocOutside = function (){--%>
    <%--    var pid=getAllDataId2();--%>
    <%--    alert(pid)--%>
    <%--    window.location = '${base}/bus/docForOutside/view/'+pid;--%>
    <%--    $.ajax({--%>
    <%--        url: '${base}/bus/docForOutside/view/'+pid ,--%>
    <%--        type: 'get',--%>
    <%--        dataType: "json",--%>
    <%--        success: function (res) {--%>
    <%--            alert(res.msg);--%>
    <%--            &lt;%&ndash;if (res.success) {&ndash;%&gt;--%>
    <%--            &lt;%&ndash;    window.location = '${base}/bus/docForInner/view';&ndash;%&gt;--%>
    <%--            &lt;%&ndash;}&ndash;%&gt;--%>
    <%--        }--%>
    <%--    })--%>

    <%--}--%>


</script>
</body>
</html>