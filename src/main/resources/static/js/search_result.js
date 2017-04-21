/**
 * Created by hasee on 2017/4/21.
 */
var searchType="vote";

$(document).ready(function(){
    vote();
    $("#join-vote").hide();

});

function vote(){
    $("#user").on("click",function(){
        rightShift();
        searchType="user";
        getJoinedVote();
    });
}

function user(){
    $("#vote").on("click",function(){
        leftShift();
        type="vote";
    });
}

function getJoinedVote(){

}

function rightShift(){
    $("#vote").off("click");
    $("#vote").css("cursor","default");
    $(".tab-border").addClass("right-shift");
    $("#vote").removeClass("tab-active");
    setTimeout(function(){
        user();
        $("#vote").css("cursor","pointer");
        $("#user").addClass("tab-active");
        $(".tab-border").css("margin-left","430px");
        $(".tab-border").removeClass("right-shift");
    },500);
}

function leftShift(){
    $("#user").off("click");
    $("#user").css("cursor","default");
    $(".tab-border").addClass("left-shift");
    $("#user").removeClass("tab-active");
    setTimeout(function(){
        vote();
        $("#user").css("cursor","pointer");
        $("#vote").addClass("tab-active");
        $(".tab-border").css("margin-left","150px");
        $(".tab-border").removeClass("left-shift");
    },500);
}