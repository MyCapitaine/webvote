/**
 * Created by hasee on 2017/5/8.
 */
$(document).ready(function(){
    $.ajax({
        url:"/model",
        type:"post",
        data:{
            id:100
        },
        success:function(result){
            console.log(result)
        }
    })
})