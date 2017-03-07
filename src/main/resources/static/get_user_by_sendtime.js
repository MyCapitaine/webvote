/**
 * Created by hasee on 2017/3/7.
 */
$(".form_datetime").datetimepicker({
    minView: "month", //选择日期后，不会再跳转去选择时分秒
    format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
    language: 'zh-CN', //汉化
    autoclose:true //选择日期后自动关闭
});

$("#get_user_by_sendtime").click(function(){
    $.post("/getSendtime",
        {
            name:$("#sendtime").val()
        },
        function(data,status){
            var objs=data.data;
            //var objs=JSON.parse(data);
            $("tbody").empty();
            for(var obj in objs){

                var tr="<tr>";
                var name="<td>"+objs[obj].name+"</td>";
                var id="<td>"+objs[obj].id+"</td>";
                var height="<td>"+objs[obj].height+"</td>";
                var sex="<td>"+objs[obj].sex+"</td>";
                var birthday="<td>"+objs[obj].birthday+"</td>";
                var sendtime="<td>"+new Date(objs[obj].sendtime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
                var price="<td>"+objs[obj].price+"</td>";
                var floatprice="<td>"+objs[obj].floatprice+"</td>";
                var doubleprice="<td>"+objs[obj].doubleprice+"</td>";
                tr=tr+name+id+height+sex+birthday+sendtime+price+floatprice+doubleprice;
                $("tbody").append(tr);
            }
        }
    )
});