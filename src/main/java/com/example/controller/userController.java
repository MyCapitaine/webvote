package com.example.controller;

/**
 * Created by hasee on 2017/3/6.
 */
        import com.example.dao.UserDao;
        import com.example.entity.JsonResult;
        import com.example.entity.Result;
        import com.example.entity.User;
        import com.google.gson.Gson;
        import net.sf.json.JSONArray;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
        import java.math.BigDecimal;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;
@Controller
public class userController {
    @Autowired
    private UserDao userDao;
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

    @RequestMapping("/getSendtime")
    @ResponseBody
    public String findBySendtime(String sendtime) {
        System.out.println("sendtime:"+sendtime);
        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<User> userList = null;
        try {
            userList = userDao.findBySendtime(formate.parse(sendtime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + sendtime + " is not exist.";
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
