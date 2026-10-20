<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>MsgDeploy</title>
    <style>
        .fontp {
            color: red;
        }

        .msgimg {
            width: 100px;
            height: 100px;
            background: white;
            /*display:inline-block;*/
            float:left;
            margin: 0px 5px 5px 0px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bf:form id="editForm" method="post" validation="true">
        <%--        <bf:hidden id="opt" name="opt" value="${opt}"/>--%>
        <bf:formGroup>
            <bc:row>
                <bc:col col="6">
                    <bc:label cls="required" text="信息分类" col="3" formControl="true"/>
                    <bf:select id="busWay" name="busWay" col="4" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-MsgType" value="${msgDeploy.busWay}">
                    </bf:select>
                </bc:col>
            </bc:row>
            <bc:row>
                <bc:col col="6">
                    <bc:label cls="required" text="信息标题" col="3" formControl="true"/>
                    <bf:text id="busName" name="busName" col="9" value="${msgDeploy.busName}">
                    </bf:text>
                </bc:col>
            </bc:row>
            <bc:row>
                <bc:col col="6">
                    <bc:label cls="required" text="信息内容" col="3" formControl="true"/>
                    <bf:text id="busMessage" name="busMessage" col="9" rows="5" multiple="true" value="${msgDeploy.busMessage}"><bv:stringLength max="1000" message="请将字数控制在1000以内！"/><bv:notEmpty/></bf:text>
                </bc:col>
            </bc:row>
            <bc:row>
                <bc:col col="1">
                </bc:col>
<%--                <bc:button type="primary" text="增加图片" id="ButtonAdd" click="addImg()"/>--%>
                <bc:col col="1"></bc:col>
                <bc:button type="danger" text="删除图片" id="ButtonSubtract" click="delImg()"/>
            </bc:row>

            <bc:row>

                <bc:col col="6">
                    <label class='control-label col-xs-3 '>附件图片</label>
                    <div id="FileDiv" class="col-xs-9">
                    </div>
                </bc:col>
            </bc:row>
            <%--            <bc:row>--%>
            <%--                <bc:col col="6">--%>
            <%--                    <bc:label text="轮播图设置" col="3" formControl="true"/>--%>
            <%--                    <bf:select id="imgTop" name="imgTop" col="4" >--%>
            <%--                    </bf:select>--%>
            <%--                </bc:col>--%>
            <%--            </bc:row>--%>
            <bc:row>
                <bc:col col="1"></bc:col><p class="fontp">提示：设置的第一张图片为该条信息的轮播展示图片</p>

                <input type="hidden" id="img1" name="img1"  value="${msgDeploy.img1}" />
               <input type="hidden" id="fList" value='${fList}'>
                <input type="hidden" id="names" value='${names}'>
                <input type="hidden" id="pid" value="${pid}">

            </bc:row>
            <bc:row>
                <bc:col col="6">
                    <bc:label  text="是否为设为重要信息" col="3" formControl="true"/>
                    <bf:select id="importantMsg" name="importantMsg" col="4" dataSource="fixedDict" url="IsStatusDict"
                                value="${msgDeploy.importantMsg}">
                    </bf:select>
                </bc:col>
            </bc:row>
        </bf:formGroup>
        <div class="col-sm-4 col-sm-offset-2">
            <bc:button type="primary" text="保存并发布" icon="save" id="saveRelease">
            </bc:button>
            <bc:button type="primary" text="保存" icon="save" id="save">
            </bc:button>
            <bc:button type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/bus/msgDeploy/list';
                </be:event>
            </bc:button>

        </div>
    </bf:form>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        initFileDiv ();

        //读取图片，并显示
        if($('#FileDiv div').length>0){
            var namesArrlist=$("#names").val();
            var namesBe = namesArrlist.substr(1); //删除首字符
            var namesDe = namesBe.substring(0,namesBe.length-1);//删除尾字符
            var names=namesDe.split(", ");
            var fileUrl=$("#img1").val();
            for(var i=0;i<$('#FileDiv div').length;i++){
                var filePath=fileUrl+'/'+names[i];
                // var  filePath="8d583f9e-4df0-4278-b66e-4cea441ce8a2/0e67ab96fd34629bbdee6145e4df07e3.jpg";
                /*图片路径*/
                var imgUrl = '${base}/bus/msgDeploy/image?filePath='+filePath;
                /*加载流程图片  */
                $("#"+"showimg"+i).attr('src',imgUrl);
            }
        }

        //添加图片
        // $("#ButtonAdd").on("click", function () {
        //         var number = $('#FileDiv div').length;
        //         var content ='<div class="msgimg"  id="msgimg'+number+'" onclick="exceFile'+number+'.click()">';
        //             content +='<input type="file"  name="exceFile" class="exceFile" id="exceFile'+number+'" data-imgid="showimg'+number+'"  onchange="changeeimg(event)" style="display:none" >';//style="display:none"
        //             content +='<img class="showimg" src="../../static/img/add.jpg" id="showimg'+number+'" style="width: 100px;height: 100px">';
        //             content +="</div>";
        //         $("#FileDiv").append(content)
        // });

        //删除图片
        // $("#ButtonSubtract").on("click", function () {
        //     document.getElementById("FileDiv").lastChild.remove();
        // });

        $("#SubmitUpload").on('click', function () {
            $('#FormFile').attr('action', 'DynamicUploadFiles');
            $("#FormFile").submit();
        });
        //加上红点
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
        addImg();
    });
    //添加图片按钮
    function addImg(){
        var number = $('#FileDiv div').length;
        var content ='<div class="msgimg"  id="msgimg'+number+'" onclick="exceFile'+number+'.click()">';
        content +='<input type="file"  name="exceFile" class="exceFile" id="exceFile'+number+'" data-imgid="showimg'+number+'"  onchange="changeeimg(event)" style="display:none" >';//style="display:none"
        content +='<img class="showimg" src="../../static/img/add.jpg" alt="点击添加图片" id="showimg'+number+'" style="width: 100px;height: 100px">';
        content +="</div>";
        $("#FileDiv").append(content)
    };
    //删除图片按钮
    function delImg(){
        if($("#FileDiv div").length>1){
            $("#FileDiv div").eq(-2).remove();
        }else{
            $.messager.alert("提示","当前无添加图片","info");
            return;
        }
    }
    //保存操作
    $('#save').click(function () {
        if(($("#busWay").val()=="JYZX" )&& ($("#FileDiv").children().length==1)){
            // $.messager.alert("交易咨询必须添加图片");
            $.messager.alert("提示","交易资讯必须添加图片","info");
            return;
        }
        if($("#FileDiv").children().length>1){
            var fileData = new FormData();
            //循环 看先插入几个图片文件
            for (var i = 0; i < $("#FileDiv").children().length-1 ; i++) {
                    fileData.append("img" , $("#editForm")[0][i + 5].files[0]);
            }
            // fileData.append("img1",$("#editForm")[0][6].files[0]);
            // $.when(uploadajx(fileData)).then( );
            // $.when(uploadajx(fileData)).done(savemsg());
            uploadajx(fileData);
        }
        else {
            savemsg();
        }
    });
    //保存并发布操作
    $('#saveRelease').click(function () {
        if(($("#busWay").val()=="JYZX" )&& ($("#FileDiv").children().length==1)){
            $.messager.alert("提示","交易资讯必须添加图片","info");
            return;
        }
        if($("#FileDiv").children().length>1){
            var fileData = new FormData();
            //循环 看先插入几个图片文件
            for (var i = 0; i < $("#FileDiv").children().length-1 ; i++) {
                    fileData.append("img" , $("#editForm")[0][i + 5].files[0]);
            }
            // fileData.append("img1",$("#editForm")[0][6].files[0]);
            // $.when(uploadajx(fileData)).then( );
            // $.when(uploadajx(fileData)).done(savemsg());
            uploadajxRelease(fileData);
        }
        else {
            savemsgAndRelease();
        }
    });


    //添加图片文件并预览
   function  changeeimg(event) {
       var accept = ",.jpg,.jpeg,.png,";

       var number = $('#FileDiv div').length-1;
       var selectedFile = $('#exceFile'+number+'').val();
       if(selectedFile!=''){
           var hz = selectedFile.substring(selectedFile.indexOf('.'),
               selectedFile.length).toLowerCase();// 文件后缀
           var location = accept.indexOf("," + hz + ",");
           if (location > -1) {
               var file = event.target.files[0];
               var imgId = $('#exceFile'+number+'')[0].dataset.imgid;
               var reader = new FileReader();
               reader.onload = function(e) {
                   // 要显示的图片
                   $("#" +imgId).attr("src", e.target.result);

               };
               reader.readAsDataURL(file);
               addImg();
               return true;
           } else {
               $.messager.alert('仅支持上传图片文件');
               return;
           }
       }else {
           return ;
       }


   };
   //上传图片文件后进行保存
   function uploadajx(fileData){
           $.ajax({
               type: 'post',
               url: '${base}/bus/msgDeploy/upload', //上传文件的请求路径必须是绝对路劲
               data: fileData,
               processData: false,
               contentType: false,
               success: function (data) {
                   console.log(data);
                   if (data.success) {
                       $("#img1").val(data.fileUrl.path);
                       savemsg();
                   }
               },
               error: function () {
                   $.messager.alert("上传失败,请重试");
               }
           });
   };
   //上传图片文件后进行保存发布
    function uploadajxRelease(fileData){
        $.ajax({
            type: 'post',
            url: '${base}/bus/msgDeploy/upload', //上传文件的请求路径必须是绝对路劲
            data: fileData,
            processData: false,
            contentType: false,
            success: function (data) {
                console.log(data)
                if (data.success) {
                    $("#img1").val(data.fileUrl.path);
                    savemsgAndRelease();
                }
            },
            error: function () {
                $.messager.alert("上传失败,请重试");
            }
        });
    };

   //保存操作
   function savemsg(){
       $('#editForm').data('formValidation').validate();
       var isValid = $('#editForm').data('formValidation').isValid();
       if(isValid)
       {

           var formData = $('#editForm').serializeAll();
           $.mask.post('${base}/bus/msgDeploy/save', formData, function(data){
               if(data.success)
               {
                   $.messager.alert('提示', data.msg, 'info', function(){
                       window.location = '${base}/bus/msgDeploy/list';
                   });
               }
               else
               {
                   $.messager.alert('错误', data.msg, 'error');
               }
           });
       }
   }

   //保存并发布操作
    function savemsgAndRelease(){
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if(isValid)
        {
            var formData = $('#editForm').serializeAll();
            $.mask.post('${base}/bus/msgDeploy/saveAndRelease', formData, function(data){
                if(data.success)
                {
                    $.messager.alert('提示', data.msg, 'info', function(){
                        window.location = '${base}/bus/msgDeploy/list';
                    });
                }
                else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }


   //初始化FileDiv
    function initFileDiv () {
        var fList= $("#fList").val();
        for(var i=0;i<fList;i++){
            var content ='<div class="msgimg"  id="msgimg'+i+'" onclick="exceFile'+i+'.click()">';
            content +='<input type="file"  name="exceFile" class="exceFile" id="exceFile'+i+'" data-imgid="showimg'+i+'"  onchange="changeeimg(event)" style="display:none" >';//style="display:none"
            content +='<img class="showimg" src="" id="showimg'+i+'" alt="点击添加图片" style="width: 100px;height: 100px">';
            content +="</div>";
            $("#FileDiv").append(content)
        }
    }
</script>
</body>
</html>
