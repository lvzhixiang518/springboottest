/**
 * @description 描述：初始化js方法
 * @author lzx
 * @since 2020年1月10日16:30:13
 */
$(document).ready(function(){
    //初始化图片上传 参数1:控件id、参数2:上传地址
    initFileInput("reportFile", "report/uploadReport");
});

/**
 * @description 描述：初始化fileinput控件（第一次初始化）
 * @author lzx
 * @since 2020年1月9日15:10:40
 */
function initFileInput(ctrlName, uploadUrl) {
    var control = $("#reportFile");
    //初始化上传控件的样式
    control.fileinput({
        language: 'zh',                                         //设置语言
        uploadUrl: uploadUrl,                                   //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png', 'pdf'],    //接收的文件后缀
        showUpload: true,                                       //是否显示上传按钮
        showRemove : true,                                      //显示移除按钮
        showPreview : true,                                     //是否显示预览
        showCaption: false,                                     //是否显示标题
        browseClass: "btn btn-primary",                         //按钮样式
        uploadAsync: true,                                      //默认异步上传
        //dropZoneEnabled: false,                               //是否显示拖拽区域
        //minImageWidth: 50,                                    //图片的最小宽度
        //minImageHeight: 50,                                   //图片的最小高度
        //maxImageWidth: 1000,                                  //图片的最大宽度
        //maxImageHeight: 1000,                                 //图片的最大高度
        //maxFileSize: 0,                                       //单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount: 15,                                       //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        uploadExtraData:function (previewId, index) {           //传参
            var data = {
                "reportGroupId": $('#lbl_groupId').html(),      //此处自定义传参
            };
            return data;
        }
    });

    //导入文件上传完成之后的事件
    control.on('fileuploaded', function(event, data, previewId, index){
        var response = data.response;
        $.each(response,function(id,path){//上传完毕，将文件名返回
            console.log("path:"+path.pathUrl);
            $("#form").append("<input class='imgClass' name='filePath' type='hidden' value='"+path.pathUrl+"'>");
        });

    });
}




/**
 * @description 描述：打开客户经理备注弹出框进行修改
 * @author lzx
 * @since 2020年1月9日15:10:40
 */
function loan311_openPersonRemarkInfo(){
    $('#loanId_H').val($("#loanID").val());
    $("#loan311_cancelRemark").val($("#managerRemark").val());
    AWFCommon.unlock('loan311_cancelRemark_removeRed');
    $('#UpPersonRemarkInfo').modal();
}

/**
 * @description 描述：保存或取消修改的原因
 * @author lzx
 * @since 2020年1月9日15:10:40
 */
function loan311_SavePersonRemarkInfo() {
    var loanId = $("#loanId_H").val();
    var remark = $("#loan311_cancelRemark").val();//修改后的客户经理备注
    if (remark.trim() == "") {
        swal({
            title: "保存失败！",
            text: "备注不能为空！",
            type: "error",
            confirmButtonText: "确定"
        });
        return false;
    }
    var serverWS = 'list.SavePersonRemarkInfo.reason';
    var detail = {"parameters.common": p_submitCommPara};
    detail[serverWS] = {'loanId': loanId, 'remark': remark};
    $.ajax({
        url: __SERVICE_URL_PREFIX__ + __SELECTDETAIL_URL__,
        type: "post",
        async: false,
        dataType: "json",
        data: 'detail=' + JSON.stringify(detail) + '&target=',
        success: function (v) {
            var data = v.data[0];
            var brand = eval(data[serverWS]);
            if (brand.flag == "Y") {
                swal({
                    title: "成功!",
                    text: "保存成功！",
                    type: "success",
                    confirmButtonText: "确定"
                });
                $("#managerRemark").val("");
                $("#managerRemark").val(remark);
            } else {
                swal({
                    title: "失败!",
                    text: "保存失败！",
                    type: "error",
                    confirmButtonText: "确定"
                });
            }
        }
    });
}
