/**
 * Created by hasee on 2017/5/9.
 */
$(document).ready(function(){
    $(".search-button-wrapper").on("click",function(){
        if($("#search-keyword").val()){
            search();
        }
    });
    $(".input-wrapper").keydown(function() {
        if (event.keyCode == 13 && $("#search-keyword").val()) {
            search();
        }
    });
});

function search(){
    $.ajax({
        url:"/search",
        type:"post",
        data:{
            keyword:$("#search-keyword").val()
        },
        success:function(result){
            changeURL();
            $("body").html(result);
        }
    });
}

function changeURL(){
    history.pushState("", "", "/search?keyword=" + $("#search-keyword").val());
}