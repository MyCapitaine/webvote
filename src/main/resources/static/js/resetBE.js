/**
 * Created by hasee on 2017/5/2.
 */
$(document).ready(function(){
    $(".loading").hide();

    $("#submit").bind('click',function(){
        clickSlow();
    });
});

function clickSlow(){
    $(this).unbind('click');
    sendEmail();
    setTimeout(function(){
        $(this).bind('click',clickSlow);
    },1500);
}

function sendEmail(){
    $.ajax({
        url:"/home/sendEmailForResetBE",
        type:"post",
        data:{
            id:id,
        },
        beforeSend : function() {
            $('.loading').show();
        },
        success:function(result){
            $('.loading').hide();
            console.log(result);
            if(result.success){
                location.href="/message";
            }
            else{
                var label=$("label[for='email']");
                label.html(result.message);
                label.show();
            }
        }
    })
}