package com.shaun;

import com.shaun.login.service.LoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class Test {
        public static void main(String[] args) {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
            LoginService loginService = applicationContext.getBean("loginService", LoginService.class);
            Map map = loginService.queryByUserName("admin");
            System.out.println(map);
        }
}
