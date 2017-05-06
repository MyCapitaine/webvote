/**
 * Created by hasee on 2017/5/4.
 */
var dp;
var page={
    totalPage:0,
    currPage:0,
};
$(document).ready(function(){
    $.ajax({
        url : "/getVotes",
        type : "GET",
        data : {
            page_index:pageIndex-1,
        },
        dataType : "json",
        success : function(result) {
            console.log("callback");
            var page=result.data;
            //var data=page.content;
            //var pageTotal=page.totalPages;
            var pageTotal=10;
            // page={
            //     totalPage:10,
            //     currPage:pageIndex,
            // };
            //dp=$(".pagination").createPage(page);
            if(pageIndex<=pageTotal){
                var pages={
                    totalPage:pageTotal,
                    currPage:pageIndex,
                };
                dp=$(".pagination").createPage(pages);
                //dynamic_table(data);
            }
            else{
                var pages={
                    totalPage:pageTotal,
                    currPage:pageIndex,
                };
                dp=$(".pagination").createPage(pages);
                // $(".img-wrapper").show();
                $(".result").html("page index error");
            }
        }
    });
});

function changeURL(pageIndex){
    history.pushState("","","/votes?pageIndex="+pageIndex);
}

function change_to_page(pageIndex){

}