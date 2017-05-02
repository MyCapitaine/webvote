/**
 * Created by hasee on 2017/4/18.
 */
$(document).ready(function(){
    $("#datetimepicker1").datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd ", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose:true //选择日期后自动关闭
    });

    $(".a-upload").on("change","input[type='file']",function(){
        var filePath=$(this).val();
        if(filePath.indexOf("jpg")!=-1 || filePath.indexOf("png")!=-1){
            var arr=filePath.split('\\');
            var fileName=arr[arr.length-1];
            $(".showFileName").html(fileName);
            //$(".upload").submit();
            upload();
        }else{
            // $(".showFileName").html("");
            $(".showFileName").html("您未上传文件，或者您上传文件类型有误！").show();
            return false
        }
    })

    var validate = $(".information-form").validate({
        submitHandler: function(form) {
        },
        rules : {
            nickName : {
                required : true,
                remote:{
                    url:"/home/isNickNameUsed",
                    type:"POST",
                    data: {
                        nickName:function () {
                            return $("#nickName").val();
                        },
                        id : id
                    }
                },
            },
            birthday : {
                required : true,
            }
        },
        messages : {
            nickName : {
                required : "请输入昵称",
                remote : "昵称已被占用"
            },
            birthday : {
                required : "请选择出生日期",
            }
        }

    });

    $(".mask").hide();

    $(".portrait-img").on("click",function(){
        $("html").attr("style","height:100%");
        $("body").attr("style","height:100%");
        $(".mask").show();
        $(".mask").on("click",function(){
           // $(".mask").hide();
            $(".mask").addClass("hidden");
            $(".mask").off("click");
            setTimeout(function(){
                $(".mask").removeClass("hidden");
                $(".mask").hide();
                $("html").attr("style","");
                $("body").attr("style","");
            },1500);
        });
    });

    $(".submit-btn").bind("click",function(){
        modifyInformation(validate);
    });

    $(".textValue .canHover").on("click",function(){
        sex(this);
    });

})

function upload(){
    var form=new FormData($(".upload")[0]);
    form.append("id",id);
    $.ajax({
        url:"/home/modifyPortrait",
        type:"post",
        data:form,
        processData:false,
        contentType:false,
        success:function(data){
            //window.clearInterval(timer);
            console.log("upload over..");
            alert("上传成功");
        },
        error:function(e){
            alert("错误！！");
            //window.clearInterval(timer);
        }
    });
}

function sex(li){
    console.log($(li).text());
    $(li).off("click");
    $(li).removeClass("canHover");
    $(li).addClass("selected");
    // $(li).css({"background-color": "#00a0d8","color": "#fff","cursor": "default"});
    // $(li).attr("style","background-color: #00a0d8 !important;color: #fff;cursor: default");
    $(li).siblings().off("click");
    $(li).siblings().on("click",function(){
        sex(this);
    });
    $(li).siblings().removeClass("selected");
    $(li).siblings().addClass("canHover");
}

function modifyInformation(validate){
    if (validate.form()) {
        var time=$("#birthday").val();
        var date=(time=="")?null:(new Date(time).Format("yyyy-MM-dd "));
        console.log(date);
        $.ajax({
            url : "/home/modify",
            type : "POST",
            data : {
                id : id,
                nickName : $("#nickName").val(),
                sex :　$(".selected").text(),
                sign : $("#sign").val(),
                birthday : date
            },
            dataType : "json",
            // beforeSend : function() {
            //     $('.loading').show();
            // },
            success : function(data) {
                $('.loading').hide();
                console.log(data);
                if (data.success) {
                    alert("修改已保存");
                    location.href="home";
                }
                else {
                    if(data.message=="Email has been binding"){
                        var label=$("label[for='email']");
                        label.html(data.message);
                        label.show();
                    }
                }
            }
        });
    }
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}