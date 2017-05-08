/**
 * Created by hasee on 2017/4/19.
 */
var mms;
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url : "/home/loginRecord",
        type : "post",
        data : {
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success : function(result) {
            console.log(result);
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            if(pageIndex<=pageTotal){
                var args={
                    pageTotal:pageTotal,
                    pageCurrent:pageIndex,
                };
                mms=$(".pagination").createPage(args);
                dynamic_table(data);
            }
            else{
                $(".img-wrapper").show();
                //alert("参数错误");
            }
        }
    });
});

function changeURL(pageIndex){
    history.pushState("","","/home/safe?pageIndex="+pageIndex);
}

//换页
function changeToPage(pageIndex){
    $.get("/home/loginRecord",
        {
            pageIndex:pageIndex
        },
        function(result){
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            dynamic_table(data);
            mms.init($(".pagination"),{
                totalPage:page_total,
                current:pageIndex+1,
                turndown:'true',
            });
    });
    $(":checkbox").prop("checked", false);
}

function dynamic_table(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var time="<td>"+new Date(data[obj].loginTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
        +"</td>";
        var ip="<td>"+data[obj].ip+"</td>";
        var position="<td>" + "登录地点" + data[obj].ip + "</td>";
        var tr=tr_head + time + ip + position + tr_foot;
        $("tbody").append(tr);
    }
}


