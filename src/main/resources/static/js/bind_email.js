/**
 * Created by hasee on 2017/4/22.
 */
/**
 * Created by hasee on 2017/4/14.
 */
$(document).ready(function(){
    $(".loading").hide();

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


        submitHandler: function(form) {
        },
        rules : {
            email : {
                required : true,
                email : true,
                remote : {
                    url : "/isBindingEmailUsed",
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
                remote : "邮箱已被占用"
            }
        }
    });


    $("#submit").bind('click',function(){
        clickSlow(validate);
    });

    $("body").each(function() {
        $(this).keydown(function() {
            if (event.keyCode == 13) {
                resetPassword(validate);
            }
        });
    });

});

function clickSlow(validate){
    $(this).unbind('click');
    resetPassword(validate);
    setTimeout(function(){
        $(this).bind('click',clickSlow);
    },1500);
}

function resetPassword(validate){

    if(validate.form()){
        $.ajax({
            url : "/sendEmailForBindingEmail",
            type : "post",
            data : {
                id:id,
                email:$("#email").val()
            },
            dataType : "json",
            beforeSend : function() {
                $('.loading').show();
            },
            success : function(result){
                $('.loading').hide();
                if(result.success){
                    location.href="/message";
                }
                else{
                    var label=$("label[for='email']");
                    label.html(result.message);
                    label.show();
                }
            }
        });
    }
}