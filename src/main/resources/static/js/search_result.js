/**
 * Created by hasee on 2017/4/21.
 */
var dp;
$(document).ready(function(){
    if(searchType=="vote"){
        vote();
    }
    else{
        user();
        rightShift();
        changeURL(pageIndex);
        searchVote();
    }

    var page={
        totalPage:10,
        currPage:pageIndex,
    };
    mms=$(".pagination").createPage(page);
});

function vote(){
    $("#user").on("click",function(){
        rightShift();
        searchType="user";
        changeURL(pageIndex);
        searchUser();
    });
}

function user(){
    $("#vote").on("click",function(){
        leftShift();
        searchType="vote";
        changeURL(pageIndex);
        searchVote();
    });
}

function searchVote(){
    $(".result").html("votes result");
}
function searchUser(){
    $(".result").html("users result");
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

function changeURL(page_index){
    history.pushState("","","/search?searchType="+searchType+"&keyword="+keyword+"&pageIndex="+page_index);
}
function changeToPage(page_index){

}