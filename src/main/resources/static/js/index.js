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
        url : "/votes",
        type : "post",
        data : {
            page_index:pageIndex-1,
        },
        dataType : "json",
        success : function(result) {
            console.log("callback");
            var page=result.data;
            //var data=page.content;
            //var pageTotal=page.totalPages;
            page={
                totalPage:10,
                currPage:pageIndex,
            };
            dp=$(".pagination").createPage(page);
            // if(pageIndex<=pageTotal){
            //     page={
            //         totalPage:pageTotal,
            //         currPage:pageIndex,
            //     };
            //     dp=$(".pagination").createPage(page);
            //     //dynamic_table(data);
            // }
            // else{
            //     // $(".img-wrapper").show();
            //     $(".result").html("page index error");
            // }
        }
    });
});

function changeURL(pageIndex){
    history.pushState("","","/votes?pageIndex="+pageIndex);
}

function change_to_page(pageIndex){

}