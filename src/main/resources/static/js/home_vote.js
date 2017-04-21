/**
 * Created by hasee on 2017/4/20.
 */
var type="publish";

$(document).ready(function(){
    joinVote();
    $("#join-vote").hide();

});



function joinVote(){
    $("#join").on("click",function(){
        rightShift();
        type="join";
        getJoinedVote();
    });
}

function publishVote(){
    $("#publish").on("click",function(){
        leftShift();
        type="publish";
    });
}

function getJoinedVote(){

}

function rightShift(){
    $("#publish").off("click");
    $("#publish").css("cursor","default");
    $(".tab-border").addClass("right-shift");
    $("#publish").removeClass("tab-active");
    setTimeout(function(){
        publishVote();
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
        joinVote();
        $("#join").css("cursor","pointer");
        $("#publish").addClass("tab-active");
        $(".tab-border").css("left","29px");
        $(".tab-border").removeClass("left-shift");
    },500);
}