package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class DemoApplication {
//	@Bean  // 文件上传大小限制【如果文件大小超过了规定大小，那么后台不会走到controller层，也不会返回任何信息，所以前台也没有任何反应】
//	public MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setMaxFileSize("600MB");
//		factory.setMaxRequestSize("600MB");
//		return factory.createMultipartConfig();
//	}

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}
}
