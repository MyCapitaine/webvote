package com.example;

import com.example.dao.RegisterDao;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.entity.UserRegister;
import com.example.service.SendEmail;
import com.example.serviceInterface.RegisterService;
import com.example.serviceInterface.UserService;
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
	private RegisterDao registerDao;

	@Autowired
	private RegisterService registerService;

	@Test
	public void test9(){
		registerService.release(1);
	}
	@Test
	public void test8(){
		registerService.ban(1);
	}

	@Test
	public void test7(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);

		UserRegister uu=registerDao.findOne(10);
		uu.setRegistertime(currentTime);
		registerDao.save(uu);
	}

	@Test
	public void test6(){
//		String to="578776370@qq.com";
//		String to="lzs105@sina.com";
		String to="lzs105@163.com";
		StringBuffer  content=new StringBuffer ("您好：");
		content.append(to+"!<br>");
		content.append("请点击下面的链接来激活您的账号（如果不能跳转，请复制粘贴到浏览器地址栏）<br>");
		//content.append("http://localhost:8080/validate?token=pass");
		content.append("http://www.qq.com");
		SendEmail.send(to,content.toString());
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
