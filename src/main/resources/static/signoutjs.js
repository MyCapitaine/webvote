/**
 * Created by hasee on 2017/4/12.
 */

function signout(){
    console.log(window.location.href);
    $.get("/signout",
        {
            url:window.location.href
        },
        function(data){
            console.log("data"+data);
        }
    );
}
$.ready(function(){
    $(".signout").click(function(){
        console.log(window.location.href);
        $.ajax({
            type:"post",
            url:"/signout",
            data:{
                url:window.location.href
            },
            dataType:"json",
            contentType:'application/json;charset=utf-8',
            success:function(data){
                for(user in data.data){
                    console.log(data.data[user]);
                }
            }
        });
    });
})