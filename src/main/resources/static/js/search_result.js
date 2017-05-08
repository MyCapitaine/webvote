/**
 * Created by hasee on 2017/4/21.
 */
var dp;
var page={
    totalPage:0,
    current:0,
};
$(document).ready(function(){
    if(searchType=="Vote"){
        vote();
        search();
    }
    else{
        user();
        $("#vote").removeClass("tab-active")
        $("#user").addClass("tab-active");
        $(".tab-border").css("margin-left","430px");
        changeURL(pageIndex);
        search();
    }
});

function vote(){
    $("#user").on("click",function(){
        rightShift();
        searchType="User";
        pageIndex=1;
        page.current=1;
        changeURL(pageIndex);
        search();
    });
}

function user(){
    $("#vote").on("click",function(){
        leftShift();
        searchType="Vote";
        pageIndex=1;
        page.current=1;
        changeURL(pageIndex);
        search();
    });
}

function search(){
    $.ajax({
        url : "/search"+searchType,
        type : "post",
        data : {
            pageIndex:pageIndex-1,
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
                dp=$(".pagination").createPage(page);
                var index=pageIndex-1>data.length-1?data.length-1:pageIndex-1;
                var time=new Date(data[index].loginTime).Format("yyyy-MM-dd hh:mm:ss");
                $(".result").html("login time is : "+time);
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

function rightShift(){
    $("#vote").off("click");
    $("#vote").css("cursor","default");
    $(".tab-border").addClass("right-shift");
    $("#vote").removeClass("tab-active");
    setTimeout(function(){
        user();
        $("#vote").css("cursor","pointer");
        $("#user").addClass("tab-active");
        $(".tab-border").css("margin-left","430px");
        $(".tab-border").removeClass("right-shift");
    },500);
}

function leftShift(){
    $("#user").off("click");
    $("#user").css("cursor","default");
    $(".tab-border").addClass("left-shift");
    $("#user").removeClass("tab-active");
    setTimeout(function(){
        vote();
        $("#user").css("cursor","pointer");
        $("#vote").addClass("tab-active");
        $(".tab-border").css("margin-left","150px");
        $(".tab-border").removeClass("left-shift");
    },500);
}

function changeURL(pageIndex){
    history.pushState("","","/search?searchType="+searchType+"&keyword="+keyword+"&pageIndex="+pageIndex);
}
function changeToPage(pageIndex){
    $.ajax({
        url : "/search"+searchType,
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
                page={
                    pageTotal:pageTotal,
                    pageCurrent:pageIndex,
                };
                dp=$(".pagination").init(page);
                var index=pageIndex>data.length-1?data.length-1:pageIndex;
                var time=new Date(data[index].loginTime).Format("yyyy-MM-dd hh:mm:ss");
                $(".result").html("login time is : "+time);
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