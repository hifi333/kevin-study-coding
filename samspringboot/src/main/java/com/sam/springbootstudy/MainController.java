package com.sam.springbootstudy;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class MainController {
	
	
	@Autowired
	private UserMapper userMapper;

  @RequestMapping("/test")
  public String test(@RequestParam("callback") String jsonpcallback, ModelMap model) {
	  System.out.println("model:" + model.toString());
      return  jsonpcallback + "({'username':'jack','age':21,'gender':'male'})";
}

  
  @RequestMapping("/getsamdata")
  public String getsamdata() {
	  
	  
    return "catta get response!";
  }

  
  
  @OnMessage
  public void onMessage(String message, Session session) 
      throws IOException, InterruptedException {
      System.out.println("客户端说：" + message);
      
      while(true){
          session.getBasicRemote().sendText("world");
          Thread.sleep(2000);
      }
  }
  
}
