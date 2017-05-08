/**
 * Created by hasee on 2017/5/8.
 */
$(document).ready(function(){
    if(pageIndex-1<0){
        pageIndex=1;
        changeURL(1);
    }
    $.ajax({
        url:"/admin/ip/getAllIp",
        type:"GET",
        data:{
            pageIndex:pageIndex-1,
        },
        dataType : "json",
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
            // dynamic_table(data);
            // checkAll();
            // ban();
        }
    });
});

function changeURL(index){


}

function changeToPage(index){

}

function dynamicTable(data){

}