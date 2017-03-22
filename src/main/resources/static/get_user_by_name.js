/**
 * Created by hasee on 2017/3/6.
 */
$(document).ready(function(){
    $("#find_by_name").click(function(){
        $.post("/findName",
            {
                name:$("#name").val()
            },
            function(data,status){
                var objs=data.data;
                //var objs=JSON.parse(data);
                $("tbody").empty();
                for(var obj in objs){

                    var tr_head="<tr>";
                    var tr_foot="</tr>";
                    var name="<td>"+objs[obj].name+"</td>";
                    var id="<td>"+objs[obj].id+"</td>";
                    var height="<td>"+objs[obj].height+"</td>";
                    var sex="<td>"+objs[obj].sex+"</td>";
                    var birthday="<td>"+objs[obj].birthday+"</td>";
                    var sendtime="<td>"+new Date(objs[obj].sendtime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
                    var price="<td>"+objs[obj].price+"</td>";
                    var floatprice="<td>"+objs[obj].floatprice+"</td>";
                    var doubleprice="<td>"+objs[obj].doubleprice+"</td>";
                    var tr=tr_head+id+name+height+sex+birthday+sendtime+price+floatprice+doubleprice+tr_foot;
                    $("tbody").append(tr);
                }
            }
        )
    });
});



// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
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