/**
 * Created by hasee on 2017/4/20.
 */
var type="publish";
var pms;
var ptotal;

$(document).ready(function(){
    initPublishVote();
});

function initPublishVote(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url : "/home/publishVote",
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
            ptotal=pageTotal;
            var publish={
                pageTotal:pageTotal,
                pageCurrent:pageIndex,
            };
            if(pageIndex<=pageTotal){
                pms=$(".pagination").createPage(publish);
                dynamicInformation(data);
            }
            else{
                $(".img-wrapper").show();
            }
        }
    });
}


function changeURL(index){
    history.pushState("", "", "/home/vote?type="+type+"&pageIndex="+index);
}
function changeToPage(index){
    $.get("/home/publishVote",
        {
            pageIndex:index
        },
        function(result){
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            var publish={
                totalPage:page_total,
                current:pageIndex+1,
            };
            dynamicInformation(data);
            if(ptotal!=page_total){
                console.log("total plus");
                pms.init($(".publish-page"),publish);
            }
        }
    );
}
function dynamicInformation(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var time="<td>"+new Date(data[obj].createTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
        +"</td>";
        var ip="<td>"+data[obj].vinfo+"</td>";
        var position="<td>" + data[obj].vname + "</td>";
        var tr=tr_head + time + ip + position + tr_foot;
        $("tbody").append(tr);
    }
}