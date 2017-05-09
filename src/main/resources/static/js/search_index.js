/**
 * Created by hasee on 2017/5/9.
 */
$(document).ready(function(){
    $(".search-button-wrapper").on("click",function(){
        if($("#search-keyword").val()){
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
            $("body").html(result);
        }
    });
}