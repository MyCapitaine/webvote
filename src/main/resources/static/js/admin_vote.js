/**
 * Created by hasee on 2017/5/8.
 */
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url:"/admin/vote/getAllVotes",
        type:"post",
        data:{
            pageIndex:pageIndex-1,
        },
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
            dynamicList(data);
            checkAll();
            deleteVotes();
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
function deleteVotes(){
    $(".delete").on("click",function(){
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
        });
        if(ids.length>0){
            $.post("/admin/vote/delete",
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
    history.pushState("","","/admin/vote?pageIndex="+index);
}
function changeToPage(index){

    $(":checkbox").prop("checked", false);
    $.ajax({
        url:"/admin/vote/getAllVotes",
        type:"post",
        data:{
            pageIndex:index,
        },
        success:function(result){
            console.log(result);
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            page={
                pageTotal:pageTotal,
                pageCurrent:pageIndex,
            };
            dp.init($(".pagination"),page);
            dynamicList(data);
        }
    });
}
function dynamicList(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var box="<td><input type='checkbox' value='"+data[obj].id+"'/>"+"</td>";
        var id="<td>"+data[obj].uid +"</td>";
        var title="<td>" + "<a href='/votepage/" + data[obj].id + "' target='_blank'>" + data[obj].vname + "</a>"+ "</td>";
        var summary="<td>" + data[obj].vinfo + "</td>";
        var tr=tr_head + box + id + title + summary + tr_foot;
        $("tbody").append(tr);
    }
}