/**
 * Created by hasee on 2017/5/9.
 */
$(document).ready(function(){
    $(".loading").hide();
    var validate = $("#signupForm").validate({

        submitHandler: function(form) {
            console.log("this:"+this);
            //resetPassword(this)
        },
        rules : {
            old_password : {
                required : true,
                minlength : 4,
                maxlength : 16,
                remote : {
                    url : "/home/oldPassword",
                    type : "POST",
                    data : {
                        id:id,
                        password:function () {
                            return $("#old_password").val();
                        }
                    }
                },
            },
            new_password : {
                required : true,
                minlength : 4,
                maxlength : 16
            },
            password_confirm : {
                required : true,
                minlength : 4,
                maxlength : 16,
                equalTo : "#new_password"
            }
        },
        messages : {
            old_password : {
                required : "请输入密码",
                minlength : "密码不少于4位",
                maxlength : "密码不多于16位",
                remote : "当前密码不正确"
            },
            new_password : {
                required : "请输入密码",
                minlength : "密码不少于4位",
                maxlength : "密码不多于16位"
            },
            password_confirm : {
                required : "请输入密码",
                minlength : "密码不少于4位",
                maxlength : "密码不多于16位",
                equalTo : "两次密码输入不一致"
            }
        }
    });
    if ($("#old_password").val()) {
        $("#old_password").prev().fadeOut();
    }
    if ($("#new_password").val()) {
        $("#new_password").prev().fadeOut();
    }
    if ($("#password_confirm").val()) {
        $("#password_confirm").prev().fadeOut();
    }

    $("#old_password").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#old_password").blur(function() {
        if (!$("#old_password").val()) {
            $(this).prev().fadeIn();
        }
    });
    $("#new_password").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#new_password").blur(function() {
        if (!$("#new_password").val()) {
            $(this).prev().fadeIn();
        }
    });

    $("#password_confirm").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#password_confirm").blur(function() {
        if (!$("#password_confirm").val()) {
            $(this).prev().fadeIn();
        }
    });

    $("#submit").bind('click',function(){
        setPassword(validate);
    });
});

function setPassword(validate){

    if(validate.form()){
//    	$.ajax({
//			url : "/resetPassword",
//			type : "post",
//			data : {
//				id : id,
//				validateCode : validateCode,
//				password : $("#password").val(),
//			},
//			dataType : "json",
//			beforeSend : function() {
//				$('.loading').show();
//			},
//			success : function(result){
//				$('.loading').hide();
//                location.href="message";
//            }
//		});


        $.ajax({
            url : "/home/resetPassword",
            type : "post",
            data : {
                id : id,
                password : $("#new_password").val(),
            },
            dataType : "json",
            beforeSend : function() {
                $('.loading').show();
            },
            success : function(result){
                $('.loading').hide();
                $.cookie("currentUser",null,{expires:0,path:'\/'});
                location.href="/message";
            }
        });
    }
}
