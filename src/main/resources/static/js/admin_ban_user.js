/**
 * Created by hasee on 2017/5/6.
 */
var dp;
// var page={
//     totalPage:0,
//     current:0,
// };
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    changeToRelease();
    $.ajax({
        url:"/admin/banUser/getAllUser",
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
                pageTotal:pageTotal,
                pageCurrent:pageIndex,
            };
            dp=$(".pagination").createPage(page);
            dynamic_table(data);
            checkAll();
            ban();
        }
    });
})

function changeToRelease(){
    $("#release").on("click",function () {
        location.href="/admin/releaseUser";
    })
}

function checkAll(){
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
    $("#banUser").on("click",function(){
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
        });
        if(ids.length>0){
            $.post("/admin/banUser/ban",
                {
                    idArray:ids.toString(),
                    pageIndex:pageIndex
                },
                function(result){
                    if(result.success){
                        var page=result.data;
                        var data=page.content;
                        var pageTotal=page.totalPages;
                        if(pageIndex>pageTotal)
                            pageIndex=pageTotal;
                        page={
                            pageTotal:pageTotal,
                            pageCurrent:pageIndex,
                        };
                        dp.init($(".pagination"),page);
                        changeURL(pageIndex);
                        changeToPage(page.pageCurrent-1>0?page.pageCurrent-1:0);
                    }
                }
            );
        }
    });

    $("#banIp").on("click",function(){
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).parent().siblings().eq(2).text());
        });
        if(ids.length>0){
            $.post("/admin/banIp",
                {
                    idArray:ids.toString(),
                    pageIndex:pageIndex
                },
                function(result){
                    if(result.success){
                        var page=result.data;
                        var data=page.content;
                        var pageTotal=page.totalPages;
                        if(pageIndex>pageTotal)
                            pageIndex=pageTotal;
                        page={
                            pageTotal:pageTotal,
                            pageCurrent:pageIndex,
                        };
                        dp.init($(".pagination"),page);
                        changeURL(pageIndex);
                        changeToPage(page.pageCurrent-1>0?page.pageCurrent-1:0);
                    }
                }
            );
        }
    })
}

function changeURL(index){
    pageIndex=index;
    history.pushState("","","/admin/banUser/allUser?pageIndex="+index);
}

function changeToPage(pageIndex){
    $(":checkbox").prop("checked", false);
    //pageIndex=pageIndex+1;
    $.ajax({
        url : "/admin/banUser/getAllUser",
        type : "post",
        data : {
            pageIndex:pageIndex,
        },
        dataType : "json",
        success : function(result) {
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            if(pageIndex<=pageTotal){
                dynamic_table(data);
            }
            else{
                $(".result").html("page index error");
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