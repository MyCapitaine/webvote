/**
 * Created by hasee on 2017/5/4.
 */
$(document).ready(function(){
    $("#user").on("click",function(){
        location.href="/search?searchType=user&keyword="+keyword;
    });
});