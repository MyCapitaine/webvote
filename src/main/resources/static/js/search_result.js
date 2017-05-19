/**
 * Created by hasee on 2017/4/21.
 */
var dp;
var page={
    totalPage:0,
    current:0,
};
var template;
$(document).ready(function(){
    template=$(".template");
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    search(keyword,pageIndex);
    $(".search-button-wrapper").on("click",function(){
        if($("#search-keyword").val()){
            keyword = $("#search-keyword").val();
            search(keyword,pageIndex);
        }
    });
    $(".input-wrapper").keydown(function() {
        if (event.keyCode == 13 && $("#search-keyword").val()) {
            keyword = $("#search-keyword").val();
            search(keyword,pageIndex);
        }
    });
    // if(searchType=="Vote"){
    //     vote();
    //     search();
    // }
    // else{
    //     user();
    //     $("#vote").removeClass("tab-active")
    //     $("#user").addClass("tab-active");
    //     $(".tab-border").css("margin-left","430px");
    //     changeURL(pageIndex);
    //     search();
    // }
});

// function vote(){
//     $("#user").on("click",function(){
//         rightShift();
//         searchType="User";
//         pageIndex=1;
//         page.current=1;
//         changeURL(pageIndex);
//         search();
//     });
// }

// function user(){
//     $("#vote").on("click",function(){
//         leftShift();
//         searchType="Vote";
//         pageIndex=1;
//         page.current=1;
//         changeURL(pageIndex);
//         search();
//     });
// }

function search(keyword,index){
    changeURL(keyword,index);
    $.ajax({
        // url : "/search"+searchType,
        url : "/searchVote",
        type : "post",
        data : {
            keyword:$("#search-keyword").val(),
            pageIndex:pageIndex-1,
        },
        dataType : "json",
        success : function(result) {

            var page=result.data;
            var data=page.content;
            console.log(data);
            var pageTotal=page.totalPages;
            if(pageIndex<=pageTotal){
                page={
                    pageTotal:pageTotal,
                    pageCurrent:pageIndex,
                };
                dp=$(".pagination").createPage(page);
                dynamic_result(data);
            }
            else{
                $(".result").html("没找到_(:зゝ∠)_");
            }
        }
    });
}
//
// function rightShift(){
//     $("#vote").off("click");
//     $("#vote").css("cursor","default");
//     $(".tab-border").addClass("right-shift");
//     $("#vote").removeClass("tab-active");
//     setTimeout(function(){
//         user();
//         $("#vote").css("cursor","pointer");
//         $("#user").addClass("tab-active");
//         $(".tab-border").css("margin-left","430px");
//         $(".tab-border").removeClass("right-shift");
//     },500);
// }
//
// function leftShift(){
//     $("#user").off("click");
//     $("#user").css("cursor","default");
//     $(".tab-border").addClass("left-shift");
//     $("#user").removeClass("tab-active");
//     setTimeout(function(){
//         vote();
//         $("#user").css("cursor","pointer");
//         $("#vote").addClass("tab-active");
//         $(".tab-border").css("margin-left","150px");
//         $(".tab-border").removeClass("left-shift");
//     },500);
// }

function changeURL(keyword,index){
    pageIndex=index;
    // history.pushState("","","/search?searchType="+searchType+"&keyword="+keyword+"&pageIndex="+pageIndex);
    history.pushState("","","/search?keyword="+keyword+"&pageIndex="+index);
}
function changeToPage(index){
    pageIndex=index;
    $.ajax({
        url : "/searchVote",
        type : "post",
        data : {
            pageIndex:index,
        },
        dataType : "json",
        success : function(result) {
            var page=result.data;
            var data=page.content;
            var pageTotal=page.totalPages;
            if(pageIndex<=pageTotal){
                page={
                    pageTotal:pageTotal,
                    pageCurrent:pageIndex,
                };
                dp=$(".pagination").init(page);
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

function dynamic_result(data){
    $(".result").empty();
    for(var index in data){
        console.log(index);
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