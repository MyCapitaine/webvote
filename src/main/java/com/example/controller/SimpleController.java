package com.example.controller;

import com.example.dao.UserRegisterDao;
import com.example.entity.JsonResult;
import com.example.entity.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})//
public class SimpleController {
    @RequestMapping("/")
    public String index(ModelMap model){
        return "index";
    }
    @RequestMapping("/index")
    public String index2(ModelMap model){
        return "index";
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

    @RequestMapping("/search")
    public String search(@RequestParam(name = "searchType",defaultValue = "vote")String searchType,
                         @RequestParam(name = "keyword",defaultValue = "")String keyword,
                         @RequestParam(name = "pageIndex",defaultValue = "1")int pageIndex,
                         ModelMap model){
        if(keyword.equals("")){
            return "search_index";
        }
        model.addAttribute("keyword",keyword);
        if(searchType.equals("user")){
            //todo
            model.addAttribute("searchType","user");
            model.addAttribute("searchResult","result");
            return "search_result";
        }
        //todo
        model.addAttribute("searchType","vote");
        model.addAttribute("searchResult","result");
        return "search_result";
    }

    @RequestMapping("/searchVote")
    public String searchVote(ModelMap model,String keyword){
        //没有输入关键字，返回搜索首页
        if(keyword.equals("")){
            return "redirect:search";
        }
        //有关键字。默认搜索投票
        else{
            model.addAttribute("searchType","vote");
            return "search_result";
        }
    }

    @RequestMapping("/searchUser")
    public String searchUser(ModelMap model,String keyword){
        //没有输入关键字，返回搜索首页
        if(keyword.equals("")){
            return "redirect:search";
        }
        //有关键字,搜索用户并返回结果
        else{
            model.addAttribute("searchType","user");
            return "search_result";
        }
    }

    @RequestMapping("/page")
    public String page(ModelMap model, @RequestParam(value = "page_index",defaultValue = "1")int index){
        model.addAttribute("page_index",index);
        return "page";
    }

    @RequestMapping("/find")
    public String find(){
        return "find";
    }


    @RequestMapping("/random")
    public String random( ModelMap model){
        return "random";
    }

    //message界面
    @RequestMapping("/message")
    public String message(ModelMap model,HttpServletRequest httpServletRequest){
        return "message";
    }

    @Autowired
    UserRegisterDao userRegisterDao;
    @RequestMapping("/initPage")
    @ResponseBody
    public JsonResult initPage(int page_index){
        int page_size=1;
        Pageable pageable =new PageRequest(page_index, page_size);
        Page<UserRegister> datas = userRegisterDao.findAll(pageable);
        if(datas!=null){
            return new JsonResult(datas);
        }
        return null;
    }
}
