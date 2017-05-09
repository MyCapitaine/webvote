/**
 * Created by hasee on 2017/5/4.
 */
var dp;
var page={
    totalPage:0,
    current:0,
};
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url : "/getVotes",
        type : "GET",
        data : {
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success : function(result) {
            console.log(result);
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            if(pageTotal==0){
                $(".result").html("空空如也");
            }
            else if(pageIndex<=pageTotal){
                var pages={
                    pageTotal:pageTotal,
                    pageCurrent:pageIndex,
                };
                dp=$(".pagination").createPage(pages);
                dynamic_result(data);
            }
            else{
                $(".result").html("page index error");
            }
        }
    });
});

function changeURL(pageIndex){
    history.pushState("","","/votes?pageIndex="+pageIndex);
}

function changeToPage(pageIndex){

}

function dynamic_result(data){
    var template=$(".template");
    $(".result").empty();
    for(var index in data){
        var beginTime = new Date(data[index].beginTime).Format("yyyy-MM-dd hh:mm:ss");
        var deadline = new Date(data[index].deadLine).Format("yyyy-MM-dd hh:mm:ss");
        var row = template.clone();
        $(row).find("a").attr("href","/votepage/"+data[index].id);
        $(row).find("a").attr("target","_blank");
        $(row).find("a").text(data[index].vname);
        $(row).find(".row-middle").text(data[index].vinfo);
        $(row).find(".beginTime").text(beginTime);
        $(row).find(".deadline").text(deadline);
        $(".result").append(row);
    }
}