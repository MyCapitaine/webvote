/**
 * Created by hasee on 2017/5/8.
 */
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url:"/admin/ip/getAllIp",
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
            dynamicTable(data);
            checkAll();
            release();
        }
    });
});
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
function release(){
    $(".release").on("click",function(){
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
        });
        if(ids.length>0){
            $.post("/admin/ip/release",
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
    history.pushState("","","/admin/ip?pageIndex="+index);
}

function changeToPage(index){
    $(":checkbox").prop("checked", false);
    $.ajax({
        url:"/admin/ip/getAllIp",
        type:"GET",
        data:{
            pageIndex:index,
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
            dynamicTable(data);
        }
    });
}

function dynamicTable(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var box="<td><input type='checkbox' value='"+data[obj].id+"'/>"+"</td>";
        var id="<td>"+data[obj].id +"</td>";
        var ip="<td>" + data[obj].ip + "</td>";
        var tr=tr_head + box + id + ip + tr_foot;
        $("tbody").append(tr);
    }
}