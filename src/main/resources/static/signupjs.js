/**
 * Created by hasee on 2017/3/22.
 */
$(document).ready(function(){
    var users=[];
    for(var i=0;i<10;i++){
        var user={
            loginname:"name"+i,
            loginpwd:"pwd"+i,
            bindemail:"email"+i,
            registertime:new Date()
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

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}