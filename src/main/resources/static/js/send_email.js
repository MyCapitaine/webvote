/**
 * Created by hasee on 2017/4/14.
 */
$(document).ready(function(){

    if ($("#email").val()) {
        $("#email").prev().fadeOut();
    }
    $("#email").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#email").blur(function() {
        if (!$("#email").val()) {
            $(this).prev().fadeIn();
        }
    });

    var validate = $("#signupForm").validate({
        rules : {
            email : {
                required : true,
                email : true,
                remote : {
                    url : "/isEmailBinding",
                    type : "POST",
                    data : {
                        bindingEmail:function () {
                            return $("#email").val();
                        }
                    }
                },
            }
        },
        messages : {
            email : {
                required : "请输入邮箱",
                email : "邮箱格式不正确",
                remote : "邮箱未绑定"
            }
        }
    });

    $("#submit").bind("click", function() {
        login(validate);
    });
});

function login(validate){

    if(validate.form()){
        $.post(
            "/sendResetPasswordEmail",
            {
                email:$("#email").val()
            },
            function(result){
                if(result.success){
                    location.href="message";
                }
                else{
                    var label=$("label[for='email']");
                    label.html(data.message);
                    label.show();
                }
            }
        )
    }
}