/**
 * Created by hasee on 2017/5/6.
 */
var dp;
// var page={
//     totalPage:0,
//     currPage:0,
// };
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
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
            var pageTotal=page.totalPages;
            page={
                totalPage:pageTotal,
                currPage:pageIndex,
            };
            dp=$(".pagination").createPage(page);
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
        console.log("before ban ,index is : "+pageIndex);
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
        });
        if(ids.length>0){
            $.get("/admin/banUser",
                {
                    id_array:ids.toString(),
                    page_index:pageIndex
                },
                function(result){
                    if(result.success){
                        console.log(result);
                        var page=result.data;
                        var data=page.content;
                        var pageTotal=page.totalPages;
                        if(pageIndex>pageTotal)
                            pageIndex=pageTotal;
                        page={
                            totalPage:pageTotal,
                            currPage:pageIndex,
                        };
                        console.log("after ban ,index is : "+pageIndex);
                        dp.init($(".pagination"),page);
                        changeURL(pageIndex);
                        changeToPage(page.currPage-1>0?page.currPage-1:0);
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
    pageIndex=index;
    history.pushState("","","/admin/allUser?pageIndex="+index);
}

function changeToPage(page_index){
    $(":checkbox").prop("checked", false);
    //pageIndex=page_index+1;
    $.ajax({
        url : "/admin/getAllUser",
        type : "post",
        data : {
            pageIndex:page_index,
        },
        dataType : "json",
        success : function(result) {
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            if(pageIndex<=pageTotal){
                // page={
                //     totalPage:pageTotal,
                //     currPage:pageIndex,
                // };
                // dp.init($(".pagination"),page);
                dynamic_table(data);
                //dynamic_table(data);
            }
            else{
                // $(".img-wrapper").show();
                $(".result").html("page index error");
                //alert("参数错误");
            }
        }
    });
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