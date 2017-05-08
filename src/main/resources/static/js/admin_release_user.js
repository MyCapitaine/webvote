/**
 * Created by hasee on 2017/5/8.
 */
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
    changeToRelease();
    $.ajax({
        url:"/admin/releaseUser/getAllUser",
        type:"GET",
        data:{
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success:function(result){
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            page={
                pageTotal:pageTotal,
                pageCurrent:pageIndex,
            };
            dp=$(".pagination").createPage(page);
            console.log(dp);
            dynamic_table(data);
            checkAll();
            release();
        }
    });
})

function changeToRelease(){
    $("#ban").on("click",function () {
        location.href="/admin/banUser";
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
function release(){
    $(".ban").on("click",function(){
        console.log("before ban ,index is : "+pageIndex);
        var ids=[];
        $(":checked").not("#all").each(function(){
            ids.push($(this).val());
        });
        if(ids.length>0){
            $.post("/admin/releaseUser/release",
                {
                    idArray:ids.toString(),
                    pageIndex:pageIndex
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
                            pageTotal:pageTotal,
                            pageCurrent:pageIndex,
                        };
                        console.log("after ban ,index is : "+pageIndex);
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
    history.pushState("","","/admin/releaseUser?pageIndex="+index);
}

function changeToPage(page_index){
    console.log(page_index);
    $(":checkbox").prop("checked", false);
    //pageIndex=page_index+1;
    $.ajax({
        url : "/admin/releaseUser/getAllUser",
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
                dynamic_table(data);
            }
            else{
                // $(".img-wrapper").show();
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