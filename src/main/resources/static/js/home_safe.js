/**
 * Created by hasee on 2017/4/19.
 */

(function($){
    var ms = {
        init:function(totalsubpageTmep,args){
            return (function(){
                ms.fillHtml(totalsubpageTmep,args);
                //ms.bindEvent(totalsubpageTmep,args);
            })();
        },
        //填充html
        fillHtml:function(totalsubpageTmep,args){
            return (function(){
                var dp=totalsubpageTmep;
                var cn=$(dp).attr("class");
                totalsubpageTmep="";
                /************************START*********************/
                if(args.currPage > 1){
                    totalsubpageTmep += "<li class='ali' id='head'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >head</a></li>";
                    totalsubpageTmep += "<li class='ali' id='prev'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >prev</a></li>";
                }else{
                    totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >head</a></li>";
                    totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >prev</a></li>";
                }

                // 页码大于等于4的时候，添加第一个页码元素
                if(args.currPage!=1 && args.currPage>=4 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+1+"</a></li>";
                }
                /* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
                if(args.currPage-2>2 && args.currPage<=args.totalPage && args.totalPage>5) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                }
                /* 当前页码的前两页 */
                var start = args.currPage-2;
                /* 当前页码的后两页 */
                var end = args.currPage+2;

                if((start>1 && args.currPage<4) || args.currPage==1) {
                    end++;
                }
                if(args.currPage>args.totalPage-4 && args.currPage>=args.totalPage) {
                    start--;
                }

                for(; start<=end; start++) {
                    if(start<=args.totalPage && start>=1) {
                        if(start != args.currPage) {
                            totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                        }else{
                            totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                        }
                    }
                }

                if(args.currPage+2<args.totalPage-1 && args.currPage>=1 && args.totalPage>5) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                }

                if(args.currPage!=args.totalPage && args.currPage<args.totalPage-2 && args.totalPage!=4) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' id='total' >"+args.totalPage+"</a></li>";
                }

                if(args.currPage < args.totalPage){
                    totalsubpageTmep += "<li class='ali' id='next'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >next</a></li>";
                    totalsubpageTmep += "<li class='ali' id='foot'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >foot</a></li>";
                }
                else{
                    totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >next</a></li>";
                    totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >foot</a></li>";
                }
                var direct='<div class="direct-wrapper">';
                direct+='<div class="direct">';
                direct+='<input class="direct-input" type="text"  placeholder="page"/>';
                direct+='<span >';
                direct+='<button class="direct-button" type="button" id="goto">GoTo</button>';
                //totalsubpageTmep+='<button class="btn btn-default" type="button" id="change">Change_page</button>';
                direct+='</span></div></div>';
                $(".direct-wrapper").remove();
                $(dp).html(totalsubpageTmep);
                var page= $(".page-wrapper").html();
                var page_wrapper=page+direct;
                $(".page-wrapper").html(page_wrapper);
                $(".page-wrapper li").not(".h_p_n_f").each(function(){
                    if($(this).text()==args.currPage)
                        $(this).addClass("active");
                });
                var p=document.getElementsByClassName(cn);
                p=$("."+cn);
                ms.bindEvent($(p),args);
            })();
        },
        //绑定事件
        bindEvent:function(totalsubpageTmep,args){
            return (function(){
                //删除操作
                // $("#delete").on("click",function(){
                //     var ids=[];
                //     $(":checked").not("#all").each(function(){
                //         ids.push($(this).val());
                //     });
                //     if(ids.length>0){
                //         console.log("before delete total: "+args.totalPage+"current: "+args.currPage);
                //         $.get("/delete",
                //             {
                //                 id_array:ids.toString(),
                //                 page_index:$(".active").text()
                //             },
                //             function(data){
                //                 if(data.success){
                //                     sessionStorage.clear();
                //                     args.totalPage=data.data.totalPages;
                //                     args.currPage=$(".active").text();
                //                     if(args.currPage>args.totalPage)
                //                         args.currPage=args.totalPage;
                //                     var page_index=args.currPage-1>0?args.currPage-1:0;
                //                     ms.fillHtml(totalsubpageTmep,{"currPage":args.currPage,"totalPage":args.totalPage,"turndown":args.turndown});
                //                     change_to_page(page_index);
                //                 }
                //             }
                //         );
                //     }
                //
                // });
                //第几页
                $(totalsubpageTmep).on("click",".geraltTb_pager",function(event){
                    var current = parseInt($(this).text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
                    // ms.bindEvent(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL(current);
                    change_to_page(current-1);
                });
                //首页
                $(totalsubpageTmep).on("click","#head",function(event){
                    //var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":1,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL(1);
                    change_to_page(0);
                });
                //上一页
                $(totalsubpageTmep).on("click","#prev",function(event){
                    var current = parseInt($(".ali.active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current-1,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL(current-1);
                    change_to_page(current-2);
                });
                //下一页
                $(totalsubpageTmep).on("click","#next",function(){
                    var current = parseInt($(".ali.active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current+1,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL(current+1);
                    change_to_page(current);
                });
                //尾页
                $(totalsubpageTmep).on("click","#foot",function(event){
                    //var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":args.totalPage,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL(args.totalPage);
                    change_to_page(args.totalPage-1);
                });
                //跳到xx页
                $(document).keydown(function(event){
                    if(event.keyCode==13){
                        $(".btn").click();
                    }
                });
                $(totalsubpageTmep).on("click","#goto",function(event){
                    var current = parseInt($(".form-control").val());
                    if(!isNaN(current)){
                        if(current>=1&&current<=args.totalPage){
                            ms.fillHtml(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
                            change_to_page($(".active").text()-1);
                        }
                        else if(current<1){
                            ms.fillHtml(totalsubpageTmep,{"currPage":1,"totalPage":args.totalPage,"turndown":args.turndown});
                            change_to_page($(".active").text()-1);
                        }
                        else if(current>args.totalPage){
                            ms.fillHtml(totalsubpageTmep,{"currPage":args.totalPage,"totalPage":args.totalPage,"turndown":args.turndown});
                            change_to_page($(".active").text()-1);
                        }
                    }
                });
            })();
        }
    };
    $.fn.getMS=function(){
        return ms;
    };

    $.fn.createPage = function(options){
        ms.init(this,options);
        return ms;
    }
})(jQuery);

var ms = $(document).getMS();

var mms;
$(document).ready(function(){
    $.ajax({
        url : "/home/record",
        type : "post",
        data : {
            page_index:page_index-1,
        },
        dataType : "json",
        success : function(result) {
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            if(page_index<=page_total){
                mms=$(".pagination").createPage({
                    totalPage:page_total,
                    currPage:page_index,
                    turndown:'true',
                });
                console.log(mms);
                dynamic_table(data);
            }
            else{
                $(".img-wrapper").show();
                //alert("参数错误");
            }
        }
    });
});

function changeURL(page_index){
    history.pushState("","","/home/safe?page_index="+page_index);
}

//换页
function change_to_page(page_index){
    $.get("/home/record",
        {
            page_index:page_index
        },
        function(result){
            var page=result.data;
            var data=page.content;
            var page_total=page.totalPages;
            dynamic_table(data);
            ms.init($(".pagination"),{
                totalPage:page_total,
                currPage:page_index+1,
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


