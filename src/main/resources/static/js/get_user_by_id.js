/**
 * Created by hasee on 2017/3/7.
 */
$(document).ready(function(){
    $("#find_by_id").click(function(){
        $.post("/getUser",
            {
                id:$("#id").val()
            },
            function(data,status){
                var obj=data.data;
                //var objs=JSON.parse(data);
                $("tbody").empty();
                var tr_head="<tr>";
                var tr_foot="</tr>";
                var id="<td>"+obj.id+"</td>";
                var name="<td>"+obj.name+"</td>";

                var height="<td>"+obj.height+"</td>";
                var sex="<td>"+obj.sex+"</td>";
                var birthday="<td>"+obj.birthday+"</td>";
                var sendtime="<td>"+new Date(obj.sendtime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
                var price="<td>"+obj.price+"</td>";
                var floatprice="<td>"+obj.floatprice+"</td>";
                var doubleprice="<td>"+obj.doubleprice+"</td>";
                var tr=tr_head+id+name+height+sex+birthday+sendtime+price+floatprice+doubleprice+tr_foot;
                $("tbody").append(tr);

            }
        )
    });
});