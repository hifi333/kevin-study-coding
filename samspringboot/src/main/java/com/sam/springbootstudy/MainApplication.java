package com.sam.springbootstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "com.sam" })

@PropertySource("classpath:application.properties")
public class MainApplication {

	@Value("${server.port}")
	private String serverport;
	

//  @RequestMapping("/")
//  public String home() {
//    return "Hello,I am Springboot from MainApplication @RequestMapping("/") ";
//  }

  @RequestMapping("/login")
  public String login() {
	  
    return "<h1> please input login name </h1>" + serverport;
  }
  
 
  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
    

  
    
  }
}