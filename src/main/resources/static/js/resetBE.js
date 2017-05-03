/**
 * Created by hasee on 2017/5/2.
 */
$(document).ready(function(){
    $(".loading").hide();

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