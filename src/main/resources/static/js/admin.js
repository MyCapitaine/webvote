/**
 * Created by hasee on 2017/5/6.
 */
$(document).ready(function(){
    console.log(pageIndex);
    $.ajax({
        url:"/admin/getAllUser",
        type:"GET",
        data:{
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success:function(result){
            var page=result.data;
            var data=page.content;
            dynamic_table(data);
        }
    });
})

function changeURL(index){
    history.pushState("","","/admin/allUser?pageIndex="+index);
}

function dynamic_table(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var box="<td><input type='checkbox' value='"+data[obj].id+"'/>"+"</td>";
        var id="<td>"+data[obj].id +"</td>";
        var nickName="<td>"+data[obj].nickName+"</td>";
        var ip="<td>" + data[obj].latestIP + "</td>";
        var tr=tr_head + box + id + nickName + ip + tr_foot;
        $("tbody").append(tr);
    }
}