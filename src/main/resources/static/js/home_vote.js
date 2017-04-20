/**
 * Created by hasee on 2017/4/20.
 */
$(document).ready(function(){
    $("#join").on("click",function(){
        rightShift();
    });

    $("#publish").on("click",function(){
        leftShift()
    });
});

function rightShift(){
    $("#publish").off("click");
    $("#publish").css("cursor","default");
    $(".tab-border").addClass("right-shift");
    $("#publish").removeClass("tab-active");
    setTimeout(function(){
        $("#publish").on("click",function(){
            leftShift();
        });
        $("#publish").css("cursor","pointer");
        $("#join").addClass("tab-active");
        $(".tab-border").css("left","95px");
        $(".tab-border").removeClass("right-shift");
    },500);
}

function leftShift(){
    $("#join").off("click");
    $("#join").css("cursor","default");
    $(".tab-border").addClass("left-shift");
    $("#join").removeClass("tab-active");
    setTimeout(function(){
        $("#join").on("click",function(){
            rightShift();
        });
        $("#join").css("cursor","pointer");
        $("#publish").addClass("tab-active");
        $(".tab-border").css("left","29px");
        $(".tab-border").removeClass("left-shift");
    },500);
}