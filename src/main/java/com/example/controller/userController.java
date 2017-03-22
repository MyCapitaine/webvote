package com.example.controller;

/**
 * Created by hasee on 2017/3/6.
 */
        import com.example.dao.UserDao;
        import com.example.entity.JsonResult;
        import com.example.entity.User;
        import com.example.entity.UserRegister;
        import com.example.service.UserServiceImp;
        import com.example.serviceInterface.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
        import java.math.BigDecimal;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.List;
@Controller
public class userController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    private int page_size=10;

    @RequestMapping("/save")
    @ResponseBody
    public JsonResult<Object[]> save(@RequestBody List<UserRegister> userList){
        System.out.println("ids.size is "+userList.size());
        //JsonResult delete = userService.delete(id_array,page_index,page_size);
        JsonResult users=new JsonResult(userList);
        return users;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult<Object[]> delete(int[] id_array,int page_index){
        System.out.println("ids.size is"+id_array);
        JsonResult delete = userService.delete(id_array,page_index,page_size);
        if (delete != null ) {
            return  delete;
        }
        return null;
    }
    @RequestMapping("/initPage")
    @ResponseBody
    public JsonResult<Object[]> initPage(int  page_index){
        JsonResult page = userService.initPage(page_index,page_size);
        if (page != null ) {
            return  page;
        }
        return null;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public JsonResult<Object[]> findById(String id){
        JsonResult user = userService.findById(Integer.parseInt(id));
        if (user != null ) {
            return  user;
        }
        return null;
    }
    @RequestMapping("/getName")
    @ResponseBody
    public JsonResult<Object[]> getByName(String name) {
        List<User> userList = userDao.findByName(name);
        if (userList != null && userList.size()!=0) {
            //Gson gson = new Gson();
            //String jsonArray = gson.toJson(userList);
            //return userList;
           // return  userList;
            return  new JsonResult(userList);
           // return jsonArray;
            //return "The user length is: " + userList.size();
        }
        return null;
        //return new Result("");
        //return "user " + name + " is not exist.";
    }
    @RequestMapping("/findName")
    @ResponseBody
    public JsonResult<Object[]> findByName(String name) {
        JsonResult<Object[]> userList = userService.findByName(name);
        if (userList != null ) {
            return  userList;
        }
        return null;
    }

    @RequestMapping("/getSex")
    @ResponseBody
    public String getBySex(char sex) {
        List<User> userList = userDao.findBySex(sex);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + sex + " is not exist.";
    }

    @RequestMapping("/getBirthday")
    @ResponseBody
    public String findByBirthday(String birthday) {
        System.out.println("birthday:"+birthday);
        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
        List<User> userList = null;
        try {
            userList = userDao.findByBirthday(formate.parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + birthday + " is not exist.";
    }

    @RequestMapping("/getSendTime")
    @ResponseBody
    public JsonResult<Object[]> findBySendtime(String sendTime) {
        System.out.println("sendTime:"+sendTime);

        if(sendTime!=null&&sendTime!=""){
            SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<User> userList = null;
            try {
                userList = userDao.findBySendtime(formate.parse(sendTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (userList != null && userList.size()!=0) {
                return  new JsonResult(userList);
            }
        }
        return null;
    }

    @RequestMapping("/getPrice")
    @ResponseBody
    public String findByPrice(BigDecimal price) {
        List<User> userList = null;
        userList = userDao.findByPrice(price);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + price + " is not exist.";
    }

    @RequestMapping("/getFloatprice")
    @ResponseBody
    public String findFloatprice(float floatprice) {
        List<User> userList = null;
        userList = userDao.findByFloatprice(floatprice);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + floatprice + " is not exist.";
    }

    @RequestMapping("/getDoubleprice")
    @ResponseBody
    public String findByPrice(double doubleprice) {
        List<User> userList = null;
        userList = userDao.findByDoubleprice(doubleprice);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + doubleprice + " is not exist.";
    }
}
