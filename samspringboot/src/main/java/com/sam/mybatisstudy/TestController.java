package com.sam.mybatisstudy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sam.springbootstudy.User;
import com.sam.springbootstudy.UserMapper;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class TestController {
	
	
	@Autowired
	private UserMapper userMapper;
	

  @RequestMapping("/test123")
  public String test(@RequestParam("callback") String jsonpcallback, ModelMap model) {
	  System.out.println("model:" + model.toString());
      return  jsonpcallback + "({'username':'jack','age':21,'gender':'male'})";
}

  
  @RequestMapping("/getsamdata123")
  public String getsamdata() {
	  
	  User user = userMapper.findByName("11");
	  
    return "catta get response!";
  }

}
