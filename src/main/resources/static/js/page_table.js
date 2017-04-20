/**
 * Created by hasee on 2017/3/7.
 */
$(document).ready(function(){

    $.get("/initPage",
        {
            page_index:page_index-1
        },
        function(data){
            sessionStorage.setItem("page_index="+0,JSON.stringify(data.data.content));
            var total=data.data.totalPages;
            dynamic_page(total,page_index);
            dynamic_table(data.data.content);
            check_all();
            del();
        }
    );

})

function del(total,current){


}
// function del(){
//
//     $("#delete").click(function(){
//         var ids=[];
//         $(":checked").not("#all").each(function(){
//             ids.push($(this).val());
//         });
//         if(ids.length>0){
//
//
//             $.get("/delete",
//                 {
//                     id_array:ids.toString(),
//                     page_index:$(".active").text()
//                 },
//                 function(data){
//                     if(data.success){
//                         sessionStorage.clear();
//                         var total=data.data.totalPages;
//                         var current=$(".active").text();
//
//
//                         if(current>total)
//                             current=total;
//                         dynamic_page(total,current);
//                         change_to_page(current-1>0?current-1:0);
//                     }
//                 }
//             );
//         }
//     });
// }

function check_all(){
    $("#all").click(function(){
        if(this.checked){
            $(":checkbox").prop("checked", true);
        }
        else{
            $(":checkbox").prop("checked", false);
        }
    });
}



function dynamic_table(data){
    $("tbody").empty();
    for(var obj in data){
        var tr_head="<tr>";
        var tr_foot="</tr>";
        var name="<td>"+data[obj].name+"</td>";
        //var id="<td>"+data[obj].id+"</td>";
        var id="<td><input type='checkbox' value='"+data[obj].id+"'/>"+"</td>";
        var height="<td>"+data[obj].height+"</td>";
        var sex="<td>"+data[obj].sex+"</td>";
        var birthday="<td>"+data[obj].birthday+"</td>";
        var sendtime="<td>"+new Date(data[obj].sendtime).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
        var price="<td>"+data[obj].price+"</td>";
        var floatprice="<td>"+data[obj].floatprice+"</td>";
        var doubleprice="<td>"+data[obj].doubleprice+"</td>";
        var tr=tr_head+id+name+height+sex+birthday+sendtime+price+floatprice+doubleprice+tr_foot;
        $("tbody").append(tr);
    }
}

function dynamic_page(page_total,page_current){
    $(".pagination").createPage({
        totalPage:page_total,
        currPage:page_current,
        turndown:'true',
    });
}
//动态页签
(function($){
    var ms = {
        init:function(totalsubpageTmep,args){
            return (function(){
                ms.fillHtml(totalsubpageTmep,args);
                ms.bindEvent(totalsubpageTmep,args);
            })();
        },
        //填充html
        fillHtml:function(totalsubpageTmep,args){
              return (function(){
                  console.log("index:"+args.currPage);
                totalsubpageTmep="";
                /************************START*********************/
                if(args.currPage > 1){
                    totalsubpageTmep += "<li class='ali' id='head'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >head</a></li>";
                    totalsubpageTmep += "<li class='ali' id='prev'><a href='javascript:void(0);' class='h_p_n_f' data-go='' >prev</a></li>";
                }else{

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
                // totalsubpageTmep+='<div class="col-sm-2">';
                // totalsubpageTmep+='<div class="input-group">';
                // totalsubpageTmep+='<input type="text" class="form-control" placeholder="page"/>';
                // totalsubpageTmep+='<span class="input-group-btn">';
                // totalsubpageTmep+='<button class="btn btn-default" type="button" id="goto">GoTo</button>';
                // //totalsubpageTmep+='<button class="btn btn-default" type="button" id="change">Change_page</button>';
                // totalsubpageTmep+='</span></div></div>';
                  console.log(totalsubpageTmep);
                $(".pagination").html(totalsubpageTmep);
                $("li").not(".h_p_n_f").each(function(){
                    if($(this).text()==args.currPage)
                        $(this).addClass("active");
                });
            })();
        },
        //绑定事件
        bindEvent:function(totalsubpageTmep,args){
            return (function(){
                //删除操作
                console.log("bind:"+$(totalsubpageTmep).html());
                $("#delete").on("click",function(){
                    var ids=[];
                    $(":checked").not("#all").each(function(){
                        ids.push($(this).val());
                    });
                    if(ids.length>0){
                        console.log("before delete total: "+args.totalPage+"current: "+args.currPage);
                        $.get("/delete",
                            {
                                id_array:ids.toString(),
                                page_index:$(".active").text()
                            },
                            function(data){
                                if(data.success){
                                    sessionStorage.clear();
                                    args.totalPage=data.data.totalPages;
                                    args.currPage=$(".active").text();
                                    if(args.currPage>args.totalPage)
                                        args.currPage=args.totalPage;
                                    var page_index=args.currPage-1>0?args.currPage-1:0;
                                    ms.fillHtml(totalsubpageTmep,{"currPage":args.currPage,"totalPage":args.totalPage,"turndown":args.turndown});
                                    change_to_page(page_index);
                                }
                            }
                        );
                    }

                });
                //第几页
                totalsubpageTmep.on("click","a.geraltTb_pager",function(event){
                    var current = parseInt($(this).text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current,"totalPage":args.totalPage,"turndown":args.turndown});
                    changeURL($(".active").text());
                    change_to_page($(".active").text()-1);
                });
                //首页
                totalsubpageTmep.on("click","#head",function(event){
                    //var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":1,"totalPage":args.totalPage,"turndown":args.turndown});
                    change_to_page($(".active").text()-1);
                });
                //上一页
                totalsubpageTmep.on("click","#prev",function(event){
                    var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current-1,"totalPage":args.totalPage,"turndown":args.turndown});
                    change_to_page($(".active").text()-1);
                });
                //下一页
                totalsubpageTmep.on("click","#next",function(){
                    var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":current+1,"totalPage":args.totalPage,"turndown":args.turndown});
                    change_to_page($(".active").text()-1);
                });
                //尾页
                totalsubpageTmep.on("click","#foot",function(event){
                    //var current = parseInt($(".active").text());
                    ms.fillHtml(totalsubpageTmep,{"currPage":args.totalPage,"totalPage":args.totalPage,"turndown":args.turndown});
                    change_to_page($(".active").text()-1);
                });
                //跳到xx页

                $(document).keydown(function(event){
                    if(event.keyCode==13){
                        $(".btn").click();
                    }
                });
                totalsubpageTmep.on("click","#goto",function(event){
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
    }
    $.fn.createPage = function(options){
        ms.init(this,options);
    }
})(jQuery);


// function dynamic_page(page_total){
//     //动态生成分页标签开始
//     var head="<li " + "id='head'" + "class='h_p_n_f'"+"><a href=" +"#" +">首页</a></li>";
//     var prev="<li " + "id='prev'" + "class='h_p_n_f'"+"><a href=" +"#" +">&laquo;</a></li>";
//     var next="<li " + "id='next'" + "class='h_p_n_f'"+"><a href=" +"#" +">&raquo;</a></li>";
//     var foot="<li " + "id='foot'" + "class='h_p_n_f'"+"><a href=" +"#" +">尾页</a></li>";
//     var a1="<a href="+"'"+"#"+"'"+">";
//     var a2="</a>";
//     // $("ul").append(head);
//     // $("ul").append(prev);
//     for(var i=1;i<=page_total;i++){
//         var li="<li ";
//         if(i==1)
//             li+="class="+ "'" +"active"+ "'" +">"+a1+i+a2+"</li>";
//         else
//             li+=">"+a1+i+a2+"</li>";
//         $("ul").append(li);
//     }
//     if(page_total>1){
//         $("ul").append(next);
//         $("ul").append(foot);
//     }
//     //为下一页和尾页标签添加点击事件
//     if($('#next').length && $('#next').length>0){
//         n_f_click(page_total);
//     }
//     //动态生成分页标签结束
//
//     //遍历每一个分页标签开始
//     $("li").not(".h_p_n_f").each(function(){
//         //给分页标签绑定点击事件开始
//         $(this).click(function(){
//             //判断点击标签是否是当前活动标签开始
//             if($(".active").text()!=$(this).text()){
//                 $(".active").removeClass("active");
//                 $(this).attr("class","active");
//                 //添加上一页和首页标签
//                 if(!$('#prev').length && !$('#prev').length>0){
//                     $("ul").prepend(prev);
//                     $("ul").prepend(head);
//                     p_h_click();
//                 }
//                 //添加下一页和尾页标签
//                 if(!$('#next').length && !$('#next').length>0){
//                     $("ul").append(next);
//                     $("ul").append(foot);
//                     n_f_click(page_total);
//                 }
//                 //删除上首下尾
//                 if($(".active").text()=="1"){
//                     $("li").remove("#head");
//                     $("li").remove("#prev");
//                 }
//                 else if($(".active").text()==""+page_total){
//                     $("li").remove("#next");
//                     $("li").remove("#foot");
//                 }
//                 change_to_page($(this).text()-1);
//             }
//             //判断点击标签是否是当前活动标签结束
//         });
//         //给分页标签绑定点击事件结束
//     })
//     //遍历每一个分页标签结束
//
//
//     function p_h_click(){
//         //上一页点击事件
//         $("#prev").click(function(){
//             var index=$(".active").index();
//             $(".active").removeClass("active");
//             $($("li").get(index-1)).addClass("active");
//
//             if($(".active").text()=="1"){
//                 $("li").remove("#head");
//                 $("li").remove("#prev");
//             }
//             if(!$('#next').length && !$('#next').length>0){
//                 $("ul").append(next);
//                 $("ul").append(foot);
//                 n_f_click();
//             }
//             change_to_page($(".active").text()-1);
//         });
//         //首页点击事件
//         $("#head").click(function(){
//             $("li").remove("#head");
//             $("li").remove("#prev");
//             $(".active").removeClass("active");
//             //$($("li").get(0)).addClass("active");
//             $("li:first").addClass("active");
//             if(!$('#next').length && !$('#next').length>0){
//                 $("ul").append(next);
//                 $("ul").append(foot);
//                 n_f_click();
//             }
//             change_to_page(0);
//         });
//     }
//
//     function n_f_click(){
//         //下一页点击事件
//         $("#next").click(function(){
//             var index=$(".active").index();
//             $(".active").removeClass("active");
//             $($("li").get(index+1)).addClass("active");
//
//             if($(".active").text()==""+page_total){
//                 $("li").remove("#next");
//                 $("li").remove("#foot");
//             }
//             if(!$('#prev').length && !$('#prev').length>0){
//                 $("ul").prepend(prev);
//                 $("ul").prepend(head);
//                 p_h_click();
//             }
//             change_to_page($(".active").text()-1);
//         });
//         //尾页点击事件
//         $("#foot").click(function(){
//             $("li").remove("#next");
//             $("li").remove("#foot");
//             $(".active").removeClass("active");
//             //$($("li").get(0)).addClass("active");
//             $("li:last").addClass("active");
//             if(!$('#prev').length && !$('#prev').length>0){
//                 $("ul").prepend(prev);
//                 $("ul").prepend(head);
//                 p_h_click();
//             }
//             change_to_page($(".active").text()-1);
//         });
//     }
// }


function changeURL(page_index){
    var stateObj = { foo: "bar" };
    history.pushState(stateObj, "page2", "page?page_index="+page_index);
}

//换页
function change_to_page(page_index){
    $(":checkbox").prop("checked", false);
    if(sessionStorage.getItem("page_index="+page_index)){
        var data=sessionStorage.getItem("page_index="+page_index);
        dynamic_table(JSON.parse(data));
    }
    else{
        $.get("/initPage",
            {
                page_index:page_index
            },
            function(data){
                sessionStorage.setItem("page_index="+page_index,JSON.stringify(data.data.content));
                dynamic_table(data.data.content);
            });
    }
}
//日期格式化
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}