package com.example.controller;

import com.example.dao.UserRegisterDao;
import com.example.entity.JsonResult;
import com.example.entity.LoginRecord;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.serviceInterface.LoginRecordService;
import com.example.serviceInterface.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage","UserInformation"})//
public class SimpleController {
    @Autowired
    LoginRecordService loginRecordService;
    @Autowired
    VoteService voteService;

    @RequestMapping("/")
    public String index(ModelMap model){
        /*返回页码，首页默认为第一页。由ajax获取具体数据*/
        ServiceResult sr = voteService.findHotVotes();
        model.addAttribute("pageIndex",1);
        model.addAttribute("hotVotes",sr.getData());
        return "/index";
    }
    @RequestMapping("/index")
    public String index2(ModelMap model){
        /*返回页码，首页默认为第一页。由ajax获取具体数据*/
        ServiceResult sr = voteService.findHotVotes();
        model.addAttribute("pageIndex",1);
        model.addAttribute("hotVotes",sr.getData());
        return "/index";
    }
    /*刷新浏览器和地址栏URL访问首页第几页*/
    @RequestMapping(value = "/votes",method = RequestMethod.GET)
    public String getVotes(@RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex,
                                      ModelMap model){

        /*返回页码，由ajax获取具体数据*/
        model.addAttribute("pageIndex",pageIndex);
        return "/index";
    }
    /*热门投票*/
    @RequestMapping(value = "/hotVotes")
    @ResponseBody
    public JsonResult hotVotes(ModelMap model){
        ServiceResult sr = voteService.findHotVotes();
        return new JsonResult(sr.getData());
    }
    /*ajax访问首页第几页*/
    @RequestMapping(value = "/votes",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult postVotes(@RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex,
                                      ModelMap model){
        /*获取投票列表分页*/
        return new JsonResult();
    }
    /*ajax访问首页第几页*/
    @RequestMapping(value = "/getVotes",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult ajaxGetVotes(@RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex,
                                 ModelMap model){
        /*获取投票列表分页*/
        int page_size=5;
        System.out.println("getVotes index:"+pageIndex);
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, page_size);
        ServiceResult sr = voteService.findAllVote(page);

        return new JsonResult(sr.getData());
    }


    /*用于根据投票id获取投票信息*/
    @RequestMapping("/{vid}")
    public String vote(@PathVariable("vid") int id, ModelMap model){
        model.addAttribute("pageIndex",id);
        return "/index";
    }

//    @RequestMapping("/search")
//    public String search(){
//        return "search_index";
//    }
//    @RequestMapping("/searchVote")
//    public String searchVote(ModelMap model,String keyword){
//        //没有输入关键字，返回搜索首页
//        if(keyword.equals("")){
//            return "redirect:search";
//        }
//        //有关键字。默认搜索投票
//        else{
//            model.addAttribute("searchType","vote");
//            return "search_result";
//        }
//    }
//    @RequestMapping("/searchUser")
//    public String searchUser(ModelMap model,String keyword){
//        //没有输入关键字，返回搜索首页
//        if(keyword.equals("")){
//            return "redirect:search";
//        }
//        //有关键字,搜索用户并返回结果
//        else{
//            model.addAttribute("searchType","user");
//            return "search_result";
//        }
//    }
    /*刷新浏览器、地址栏URL和搜索框搜索*/
    @RequestMapping("/search")
    public String search(@RequestParam(name = "searchType",defaultValue = "Vote")String searchType,
                         @RequestParam(name = "keyword",defaultValue = "")String keyword,
                         @RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex,
                         ModelMap model){
        if(keyword.equals("")){
            return "/search_index";
        }
        model.addAttribute("keyword",keyword);
        model.addAttribute("pageIndex",pageIndex);
        if(searchType.equals("User")){
            model.addAttribute("searchType",searchType);
            model.addAttribute("searchResult","result");
            //return "/search_result";
            return "search_user";
        }
        model.addAttribute("searchType",searchType);
        model.addAttribute("searchResult","result");
        return "/search_result";
        //return "search_vote";
    }
    /*ajax获取搜索结果*/
    @RequestMapping("/searchVote")
    @ResponseBody
    public JsonResult searchVote(ModelMap model,String keyword,
                                 @RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex){
        int pageSize = 10;
        Pageable pageable =new PageRequest(pageIndex, pageSize);
        ServiceResult vs = voteService.researchVotes(pageable,keyword);
//        ServiceResult lrsr = loginRecordService.find(1,pageable);
        return new JsonResult(vs.getData());
    }
    /*ajax获取搜索结果*/
    @RequestMapping("/searchUser")
    @ResponseBody
    public JsonResult searchUser(ModelMap model,String keyword,
                                 @RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex){
        int page_size=5;
        Pageable pageable =new PageRequest(pageIndex, page_size);
        ServiceResult lrsr = loginRecordService.find(1,pageable);
        return new JsonResult(lrsr.getData());
    }

    @RequestMapping("/find")
    public String find(@ModelAttribute(value = "currentUser") UserRegister ur){

        return "find";
    }

    @RequestMapping("/model")
    @ResponseBody
    public String model(int id, HttpServletRequest request){
        System.out.println(id);
        UserRegister ur = (UserRegister) request.getSession().getAttribute("currentUser");
        return ur.getId()+"";
    }

    //message界面
    @RequestMapping("/message")
    public String message(ModelMap model,HttpServletRequest httpServletRequest){
        return "message";
    }


    @RequestMapping("/page")
    public String page(ModelMap model, @RequestParam(value = "pageIndex",defaultValue = "1")int index){
        model.addAttribute("pageIndex",index);
        return "page";
    }
    @Autowired
    UserRegisterDao userRegisterDao;
    @RequestMapping("/initPage")
    @ResponseBody
    public JsonResult initPage(int pageIndex){
        int page_size=1;
        Pageable pageable =new PageRequest(pageIndex, page_size);
        Page<UserRegister> datas = userRegisterDao.findAll(pageable);
        if(datas!=null){
            return new JsonResult(datas);
        }
        return null;
    }
}
