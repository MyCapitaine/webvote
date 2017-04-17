$(document).ready(function(){
	$(".loading").hide();
	
	var validate = $("#signupForm").validate({
        
		submitHandler: function(form) {   
    		console.log("this:"+this);
    		//resetPassword(this)    
    	},
		rules : {
            password : {
                required : true,
                minlength : 4,
                maxlength : 16
            },
            password_confirm : {
                required : true,
                minlength : 4,
                maxlength : 16,
                equalTo : "#password"
            }
        },
        messages : {
            password : {
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
	
	if ($("#password").val()) {
        $("#password").prev().fadeOut();
    }
    if ($("#password_confirm").val()) {
        $("#password_confirm").prev().fadeOut();
    }
    
    $("#password").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#password").blur(function() {
        if (!$("#password").val()) {
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
			url : "/resetPassword",
			type : "post",
			data : {
				id : id,
				validateCode : validateCode,
				password : $("#password").val(),
			},
			dataType : "json",
			beforeSend : function() {
				$('.loading').show();	
			},
			success : function(result){
				console.log("result :"+result.success);
				$('.loading').hide();
                location.href="message";

            }
		});
    	
    }
}
