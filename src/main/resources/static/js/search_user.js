/**
 * Created by hasee on 2017/5/4.
 */
$(document).ready(function(){
    $("#vote").on("click",function(){
        location.href="/search?searchType=Vote&keyword="+keyword;
    });
});

