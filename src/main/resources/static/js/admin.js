/**
 * Created by hasee on 2017/5/6.
 */
$(document).ready(function(){
    $.ajax({
        url:"/admin/getAllUser",
        type:"GET",
        data:{
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success:function(result){
            console.log(result);
            var page=result.data;
            var data=page.content;
            dynamic_table(data);
            check_all();
            ban();
        }
    });
})

function check_all(){
    $("#all").click(function(){
        if(this.checked){
            $(":checkbox").prop("checked", true);
        }
        else{
            $(":checkbox").prop("checked", false);
        }
    });
}
function ban(){
    $(".ban").on("click",function(){
        console.log("ban");
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
            console.log("id : "+$(this).val());
        });
        if(ids.length>0){
            $.get("/admin/banUser",
                {
                    id_array:ids.toString(),
                    page_index:1
                },
                function(data){
                    if(data.success){
                        sessionStorage.clear();
                        var total=data.data.totalPages;
                        // // var current=$(".active").text();
                        //
                        //
                        // if(current>total)
                        //     current=total;
                        // //dynamic_page(total,current);
                        // change_to_page(current-1>0?current-1:0);
                    }
                }
            );
        }
    })
}

function changeURL(index){
    history.pushState("","","/admin/allUser?pageIndex="+index);
}

function dynamic_table(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var box="<td><input type='checkbox' value='"+data[obj][0].id+"'/>"+"</td>";
        var id="<td>"+data[obj][0].id +"</td>";
        var nickName="<td>"+data[obj][0].nickName+"</td>";
        var ip="<td>" + data[obj][0].latestIP + "</td>";
        var tr=tr_head + box + id + nickName + ip + tr_foot;
        $("tbody").append(tr);
    }
}