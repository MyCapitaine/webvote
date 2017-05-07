/**
 * Created by hasee on 2017/4/20.
 */
var type="publish";
var jms;
var pms;
var jtotal;
var ptotal;

$(document).ready(function(){
    //initJoinVote();
    initPublishVote();

    //joinVote();
    //publishVote();
    // if(type=="publish"){
    //     $("#join-vote").hide();
    // }
    // else{
    //     $("#publish").removeClass("tab-active")
    //     $("#join").addClass("tab-active");
    //     $(".tab-border").css("left","95px");
    //     $("#publish-vote").hide();
    // }
});

function joinVote(){
    $("#join").on("click",function(){
        rightShift();
        type="join";
        //getJoinedVote();
        $("#publish-vote").hide();
        $("#join-vote").show();
        changeURLPublish($(".join-page .ali.active").text());
    });
}

function publishVote(){
    $("#publish").on("click",function(){
        leftShift();
        type="publish";
        //getPublishedVote();
        $("#join-vote").hide();
        $("#publish-vote").show();
        changeURLPublish($(".publish-page .ali.active").text());
    });
}

function initJoinVote(){
    $.ajax({
        url : "/home/joinVote",
        type : "post",
        data : {
            page_index:page_index-1,
        },
        dataType : "json",
        success : function(result) {
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            jtotal=page_total;
            var join={
                totalPage:page_total,
                currPage:page_index,
                changeURL:(function(){
                    return changeURLJoin;
                })(),
                change_to_page:(function(){
                    return change_to_page_join;
                })()
            };
            if(page_index<=page_total){
                jms=$(".join-page").createPage(join);
                dynamicTableJoin(data);
            }
            else{
                $(".img-wrapper").show();
            }
        }
    });
}
function initPublishVote(){
    $.ajax({
        url : "/home/publishVote",
        type : "post",
        data : {
            page_index:page_index-1,
        },
        dataType : "json",
        success : function(result) {
            console.log(result);
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            ptotal=page_total;
            var publish={
                totalPage:page_total,
                currPage:page_index,
                changeURL:(function(){
                    return changeURLPublish;
                })(),
                change_to_page:(function(){
                    return change_to_page_publish;
                })()
            };
            if(page_index<=page_total){
                pms=$(".publish-page").createPage(publish);
                dynamicTablePublish(data);
            }
            else{
                $(".img-wrapper").show();
            }
        }
    });
}

function changeURLJoin(page_index){
    history.pushState("", "", "/home/vote?type="+type+"&page_index="+page_index);
}
function change_to_page_join(page_index){
    $.get("/home/joinVote",
        {
            page_index:page_index
        },
        function(result){
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            var join={
                totalPage:page_total,
                currPage:page_index+1,
                changeURL:(function(){
                    return changeURLJoin;
                })(),
                change_to_page:(function(){
                    return change_to_page_join;
                })()
            };
            dynamicTableJoin(data);
            if(jtotal!=page_total){
                console.log("total plus");
                jms.init($(".join-page"),join);
            }
        }
    );
}
function dynamicTableJoin(data){
    $("#join-vote tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var time="<td>"+new Date(data[obj].loginTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
        +"</td>";
        var ip="<td>"+data[obj].ip+"</td>";
        var position="<td>" + "登录地点" + data[obj].ip + "</td>";
        var tr=tr_head + time + ip + position + tr_foot;
        $("#join-vote tbody").append(tr);
    }
}
function changeURLPublish(page_index){
    history.pushState("", "", "/home/vote?type="+type+"&page_index="+page_index);
}
function change_to_page_publish(page_index){
    $.get("/home/publishVote",
        {
            page_index:page_index
        },
        function(result){
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            var publish={
                totalPage:page_total,
                currPage:page_index+1,
                changeURL:(function(){
                    return changeURLPublish;
                })(),
                change_to_page:(function(){
                    return change_to_page_publish;
                })()
            };
            dynamicTablePublish(data);
            if(ptotal!=page_total){
                console.log("total plus");
                pms.init($(".publish-page"),publish);
            }
        }
    );
}
function dynamicTablePublish(data){
    $("#publish-vote tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var time="<td>"+new Date(data[obj].createTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
        +"</td>";
        var ip="<td>"+data[obj].vinfo+"</td>";
        var position="<td>" + data[obj].vname + "</td>";
        var tr=tr_head + time + ip + position + tr_foot;
        $("#publish-vote tbody").append(tr);
    }
}
function rightShift(){
    $("#publish").off("click");
    $("#publish").css("cursor","default");
    $(".tab-border").addClass("right-shift");
    $("#publish").removeClass("tab-active");
    setTimeout(function(){
        publishVote();
        $("#publish").css("cursor","pointer");
        $("#join").addClass("tab-active");
        $(".tab-border").css("left","95px");
        $(".tab-border").removeClass("right-shift");
    },500);
}

function leftShift(){
    $("#join").off("click");
    $("#join").css("cursor","default");
    $(".tab-border").addClass("left-shift");
    $("#join").removeClass("tab-active");
    setTimeout(function(){
        joinVote();
        $("#join").css("cursor","pointer");
        $("#publish").addClass("tab-active");
        $(".tab-border").css("left","29px");
        $(".tab-border").removeClass("left-shift");
    },500);
}