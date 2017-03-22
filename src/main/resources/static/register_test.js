/**
 * Created by hasee on 2017/3/22.
 */
$(document).ready(function(){
    var users=[];
    for(var i=0;i<10;i++){
        var user={
            login_name:"name"+i,
            login_pwd:"pwd"+i,
            bind_email:"email"+i
        };
        users.push(user);
    }
    console.log(users);
    $.ajax({
        type:"post",
        url:"/save",
        data:JSON.stringify(users),
        dataType:"json",
        contentType:'application/json;charset=utf-8',
        success:function(data){
            for(user in data.data){
                console.log(data.data[user]);
            }
        }
    });
});