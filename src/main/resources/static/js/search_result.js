/**
 * Created by hasee on 2017/4/21.
 */

//var keyword;

$(document).ready(function(){
    //keyword=$("#search-keyword").val();
    if(searchType=="vote"){
        vote();
        //$("#join-vote").hide();
    }
    else{
        user();
        rightShift();
        changeURLForSearchUser(0);
    }
});

function vote(){
    $("#user").on("click",function(){
        rightShift();
        searchType="user";
        changeURLForSearchUser(0);
        //getJoinedVote();
    });
}

function user(){
    $("#vote").on("click",function(){
        leftShift();
        searchType="vote";
        changeURLForSearchVote(0);
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

function changeURLForSearchUser(page_index){
    history.pushState("","","/search?searchType="+searchType+"&keyword="+keyword+"&pageIndex="+page_index);
}

function changeURLForSearchVote(page_index){
    history.pushState("","","/search?searchType="+searchType+"&keyword="+keyword+"&pageIndex="+page_index);
}