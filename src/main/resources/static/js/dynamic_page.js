/**
 * Created by hasee on 2017/4/21.
 */

// $.fn.dynamic_page = function(total,current){
//     $(this).createPage({
//         pageTotal:total,
//         pageCurrent:current
//     });
// };

(function($){
    var ms = {
        init:function(totalsubpageTmep,args){
            return (function(){
                ms.fillHtml(totalsubpageTmep,args);
            })();
        },
        //填充html
        fillHtml:function(totalsubpageTmep,args){
            return (function(){
                var dp=totalsubpageTmep;
                var cn=$(dp).attr("class")
                totalsubpageTmep="";
                /************************START*********************/
                if(args.pageTotal>=1){
                    if(args.pageCurrent > 1){
                        totalsubpageTmep += "<li class='ali' id='head'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >head</a></li>";
                        totalsubpageTmep += "<li class='ali' id='prev'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >prev</a></li>";
                    }else{
                        totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >head</a></li>";
                        totalsubpageTmep += "<li class='ali disable' ><a href='javascript:void(0);' class='h_p_n_f' data-go='' >prev</a></li>";
                    }

                    // 页码大于等于4的时候，添加第一个页码元素
                    if(args.pageCurrent!=1 && args.pageCurrent>=4 && args.pageTotal!=4) {
                        totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+1+"</a></li>";
                    }
                    /* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
                    if(args.pageCurrent-2>2 && args.pageCurrent<=args.pageTotal && args.pageTotal>5) {
                        totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                    }
                    /* 当前页码的前两页 */
                    var start = args.pageCurrent-2;
                    /* 当前页码的后两页 */
                    var end = args.pageCurrent+2;

                    if((start>1 && args.pageCurrent<4) || args.pageCurrent==1) {
                        end++;
                    }
                    if(args.pageCurrent>args.pageTotal-4 && args.pageCurrent>=args.pageTotal) {
                        start--;
                    }

                    for(; start<=end; start++) {
                        if(start<=args.pageTotal && start>=1) {
                            if(start != args.pageCurrent) {
                                totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                            }else{
                                totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >"+start+"</a></li>";
                            }
                        }
                    }

                    if(args.pageCurrent+2<args.pageTotal-1 && args.pageCurrent>=1 && args.pageTotal>5) {
                        totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
                    }

                    if(args.pageCurrent!=args.pageTotal && args.pageCurrent<args.pageTotal-2 && args.pageTotal!=4) {
                        totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' id='total' >"+args.pageTotal+"</a></li>";
                    }

                    if(args.pageCurrent < args.pageTotal){
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
                    direct+='</span></div></div>';

                    $(".direct-wrapper").remove();
                    $(dp).html(totalsubpageTmep);
                    var page= $(".page-wrapper").html();
                    var page_wrapper=direct+page;
                    $(".page-wrapper").html(page_wrapper);

                    $(".page-wrapper li").not(".h_p_n_f").each(function(){
                        if($(this).text()==args.pageCurrent)
                            $(this).addClass("active");
                    });
                    var p=$("."+cn);
                    ms.bindEvent($(p),args);
                }
            })();
        },
        //绑定事件
        bindEvent:function(totalsubpageTmep,args){
            return (function(){
                var dp=totalsubpageTmep;
                //var cn=$(dp).attr("class");
                //删除操作
                // $("#delete").on("click",function(){
                //     var ids=[];
                //     $(":checked").not("#all").each(function(){
                //         ids.push($(this).val());
                //     });
                //     if(ids.length>0){
                //         console.log("before delete total: "+args.pageTotal+"current: "+args.pageCurrent);
                //         $.get("/delete",
                //             {
                //                 id_array:ids.toString(),
                //                 pageIndex:$(".active").text()
                //             },
                //             function(data){
                //                 if(data.success){
                //                     sessionStorage.clear();
                //                     args.pageTotal=data.data.pageTotals;
                //                     args.pageCurrent=$(".active").text();
                //                     if(args.pageCurrent>args.pageTotal)
                //                         args.pageCurrent=args.pageTotal;
                //                     var pageIndex=args.pageCurrent-1>0?args.pageCurrent-1:0;
                //                     ms.fillHtml(totalsubpageTmep,{"pageCurrent":args.pageCurrent,"pageTotal":args.pageTotal,"turndown":args.turndown});
                //                     changeToPage(pageIndex);
                //                 }
                //             }
                //         );
                //     }
                //
                // });
                //第几页
                $(totalsubpageTmep).on("click",".geraltTb_pager",function(event){
                    var current = parseInt($(this).text());
                    args.pageCurrent=current;
                    ms.fillHtml(totalsubpageTmep,args);
                    changeURL(current);
                    changeToPage(current-1);
                });
                //首页
                $(totalsubpageTmep).on("click","#head",function(event){
                    args.pageCurrent=1;
                    ms.fillHtml(totalsubpageTmep,args);
                    changeURL(1);
                    changeToPage(0);
                });
                //上一页
                $(totalsubpageTmep).on("click","#prev",function(event){
                    var current = parseInt($(".ali.active").eq(0).text());
                    args.pageCurrent=current-1;
                    ms.fillHtml(totalsubpageTmep,args);
                    changeURL(current-1);
                    changeToPage(current-2);
                });
                //下一页
                $(totalsubpageTmep).on("click","#next",function(){
                    var current = parseInt($(".ali.active").eq(0).text());
                    args.pageCurrent=current+1;
                    ms.fillHtml(totalsubpageTmep,args);
                    changeURL(current+1);
                    changeToPage(current);
                });
                //尾页
                $(totalsubpageTmep).on("click","#foot",function(event){
                    args.pageCurrent=args.pageTotal;
                    ms.fillHtml(totalsubpageTmep,args);
                    changeURL(args.pageTotal);
                    changeToPage(args.pageTotal-1);
                });
                //跳到xx页
                $(document).keydown(function(event){
                    if(event.keyCode==13){
                        $(".direct-button").click();
                    }
                });
                $(".direct-wrapper").on("click","#goto",function(event){
                    var to = parseInt($(".direct-input").val());
                    if(!isNaN(to)){
                        if(to>=1&&to<=args.pageTotal){
                            args.pageCurrent=to;
                            ms.fillHtml(totalsubpageTmep,args);
                            changeURL(args.pageCurrent);
                            changeToPage(to-1);
                        }
                        else if(to<1){
                            args.pageCurrent=1;
                            ms.fillHtml(totalsubpageTmep,args);
                            changeURL(1);
                            changeToPage(0);
                        }
                        else if(to>args.pageTotal){
                            args.pageCurrent=args.pageTotal;
                            ms.fillHtml(totalsubpageTmep,args);
                            changeURL(args.pageTotal);
                            changeToPage(args.pageTotal-1);
                        }
                    }
                });
            })();
        }
    };

    $.fn.createPage = function(options){
        ms.init(this,options);
        return ms;
    };
})(jQuery);