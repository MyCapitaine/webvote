package com.example;

import com.example.dao.LoginRecordDao;
import com.example.dao.UserRegisterDao;
import com.example.entity.*;
import com.example.exception.UserInformationServiceException;
import com.example.exception.UserRegisterServiceException;
import com.example.serviceInterface.BindingEmailValidateService;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
import com.example.util.Encrypt;
import com.example.vo.ModifyLoginPasswordVO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserRegisterDao userRegisterDao;

	@Autowired
	private UserRegisterService userRegisterService;

	@Autowired
	private UserInformationService userInformationService;
	@Autowired
	private BindingEmailValidateService activeBindingEmailValidateService;

	@Autowired
	private LoginRecordDao loginRecordDao;


	@Test
	public void test15(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					int i=0;
					while(i<10){
						LoginRecord lr =new LoginRecord();
						lr.setIp("192.168.64.91");
						lr.setLoginTime(new Date());
						lr.setUserId(1);
						loginRecordDao.save(lr);
						i++;
						int time = (int) (Math.random() * 10);
						Thread.sleep(1000*time);
					}

				}
				catch (InterruptedException e)
				{
				}
			}
		});
		t.start();
	}

	@Test
	public void test14(){
		System.out.println(Encrypt.encryptEmailPrefix("5@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("57@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("578@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("5787@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("57877@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("578776@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("5787763@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("57877637@qq.com"));
        System.out.println(Encrypt.encryptEmailPrefix("578776370@qq.com"));
	}
	@Test
	public void test13(){
		for(int i=0;i<10;i++){
			LoginRecord lr =new LoginRecord();
			lr.setIp("192.168.64.91");
			lr.setLoginTime(new Date());
			lr.setUserId(1);
			loginRecordDao.save(lr);
		}
	}
	@Test
    public void test12(){
        System.out.println(userRegisterDao.findByLoginName("name0"));
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
		ur.setLoginName("aaa");
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

//			ServiceResult avsr = activeBindingEmailValidateService.add(ur);
//            BindingEmailValidate av = (BindingEmailValidate)avsr.getData();
//			String validator = av.getValidator() ;
//			js.setMessage(avsr.getMessage());
//			js.setSuccess(avsr.isSuccess());
//
//			SendEmail se = SendEmailFactory.getInstance(SendValidateEmailForBindingEmail.class);
//			se.send(ur.getBindingEmail(),validator);

			js.setData(ui);

		}
		catch (UserRegisterServiceException e) {
			System.out.println(js) ;
		}
		catch (UserInformationServiceException e){
			userRegisterService.delete(ur);
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


}
