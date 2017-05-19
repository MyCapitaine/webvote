/**
 * Created by hasee on 2017/5/4.
 */
$(document).ready(function(){



});

function searchUser(){
    $("#user").on("click",function(){
        location.href="/search?searchType=User&keyword="+keyword;
    });
}

function changeURL(index){
    history.pushState("", "", "/search?keyword=" + keyword + "&pageIndex=" +index);
}

function changeToPage(index){

}

function dynamicResult(data){

}