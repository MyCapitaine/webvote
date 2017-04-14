package com.example;

import com.example.dao.UserRegisterDao;
import com.example.dao.UserDao;
import com.example.entity.*;
import com.example.exception.ActiveValidateServiceException;
import com.example.exception.SendEmailException;
import com.example.exception.UserInformationServiceException;
import com.example.exception.UserRegisterServiceException;
import com.example.service.SendEmail;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
import com.example.serviceInterface.ValidateService;
import com.example.vo.ModifyLoginPasswordVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRegisterDao userRegisterDao;

	@Autowired
	private UserRegisterService userRegisterService;

	@Autowired
	private UserInformationService userInformationService;
	@Autowired
	private ValidateService activeValidateService;

	@Test
    public void test12(){
        System.out.println(userRegisterDao.findByLoginName("name0").size());
    }
	@Test
	public void test11(){

        ModifyLoginPasswordVO form = new ModifyLoginPasswordVO();
        form.setId(1);
        form.setOldLoginPassword("111");
        form.setNewLoginPassword("2333");
        form.setSecondNewLoginPassword("2333");
        ServiceResult sr=userRegisterService.modifyLoginPassword(form);
        System.out.println(sr.getMessage());
	}
	@Test
	public void test10(){
		UserRegister ur=new UserRegister();
		ur.setLogin_name("aaa");
		ur.setBindingEmail("578776370@qq.com");
		ur.setLoginPassword("111");
		ur.setRegisterTime(new Date());
		JsonResult js = new JsonResult();
		js.setData(null);
		js.setMessage("register failed");
		js.setSuccess(false);

		UserInformation ui=null;
		try{
			ServiceResult ursr = userRegisterService.register(ur);
			ur = (UserRegister) ursr.getData();
			js.setMessage(ursr.getMessage());
			js.setSuccess(ursr.isSuccess());

			ui = new UserInformation(ur);
			ServiceResult uisr = userInformationService.register(ui);
			js.setMessage(uisr.getMessage());
			js.setSuccess(uisr.isSuccess());

			ServiceResult avsr = activeValidateService.getValidator(ur);
            ActiveValidate av = (ActiveValidate)avsr.getData();
			String validator = av.getValidator() ;
			js.setMessage(avsr.getMessage());
			js.setSuccess(avsr.isSuccess());

			SendEmail.sendValidateEmail(ur.getBindingEmail(),validator);

			js.setData(ui);

		}
		catch (UserRegisterServiceException e) {
			System.out.println(js) ;
		}
		catch (UserInformationServiceException e){
			userRegisterService.delete(ur);
		}
		catch (ActiveValidateServiceException e){
			userRegisterService.delete(ur);
			userInformationService.delete(ur);
		}
		catch(SendEmailException e){
			userRegisterService.delete(ur);
			userInformationService.delete(ur);
			activeValidateService.deleteValidator(ur);
			js.setMessage(e.getMessage());
		}

		System.out.println(js) ;
	}
	@Test
	public void test9(){
		userRegisterService.release(1);
	}
	@Test
	public void test8(){
		userRegisterService.ban(1);
	}

	@Test
	public void test7(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);

		UserRegister uu= userRegisterDao.findOne(10);
		uu.setRegisterTime(currentTime);
		userRegisterDao.save(uu);
	}

	@Test
	public void test6(){
//		String to="578776370@qq.com";
//		String to="lzs105@sina.com";
//		String to="lzs105@163.com";
		String to="1@qq.com";
		StringBuffer  content=new StringBuffer ("您好：");
		content.append(to+"!<br>");
		content.append("请点击下面的链接来激活您的账号（如果不能跳转，请复制粘贴到浏览器地址栏）<br>");
		//content.append("http://localhost:8080/validate?token=pass");
		content.append("http://www.qq.com");
		try {
			SendEmail.sendValidateEmail(to,content.toString());
		} catch (SendEmailException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test1() {

		Pageable pageable =new PageRequest(1, 10);

		Page<User> datas = userDao.findAll(pageable);

		System.out.println("总条数："+datas.getTotalElements());
		System.out.println("一页大小："+datas.getSize());
		System.out.println("总页数："+datas.getTotalPages());
		for(User u : datas) {
			System.out.println(u.getId()+"===="+u.getName());
		}
	}

	@Test
	public void test2(){

		for(int i=0;i<20;i++){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			ParsePosition pos = new ParsePosition(0);
			Date currentTime_2 = formatter.parse(dateString, pos);

			User user=new User();
			user.setBirthday(currentTime_2);
			user.setDoubleprice(i);
			user.setFloatprice(i);
			user.setHeight(170+i);
			user.setName("name"+i);
			user.setPrice(new BigDecimal(i));
			user.setSendtime(currentTime_2);
			char f='f';
			char m='m';
			user.setSex(i%2==0? f:m);
			userDao.save(user);
		}
	}

	@Test
	public void test3(){

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		ParsePosition pos3 = new ParsePosition(2);
		Date currentTime_3 = formatter.parse(dateString, pos3);
		System.out.println(dateString);
		System.out.println(currentTime_2);
		System.out.println(currentTime_3);
	}

	@Test
	public void test5(){
		for(int i=26;i<86;i++){

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			ParsePosition pos = new ParsePosition(0);
			Date currentTime_2 = formatter.parse(dateString, pos);

			User user=userDao.findOne(i);
			user.setBirthday(currentTime_2);
			user.setSendtime(currentTime_2);
//			user.setDoubleprice(i);
//			user.setFloatprice(i);
//			user.setHeight(170+i);
//			user.setName("name"+i);
//			user.setPrice(new BigDecimal(i));
//
//			char f='f';
//			user.setSex(f);
//			user.setId(5);
			userDao.save(user);
		}
	}

	@Test
	public void test4(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		System.out.println(dateString);
		System.out.println(currentTime_2);

		int i=25;
		User user=new User();
		user.setBirthday(currentTime_2);
		user.setDoubleprice(i);
		user.setFloatprice(i);
		user.setHeight(170+i);
		user.setName("name"+i);
		user.setPrice(new BigDecimal(i));
		user.setSendtime(currentTime_2);
		char f='f';
		char m='m';
		user.setSex(i%2==0? f:m);
		userDao.save(user);
	}

}
