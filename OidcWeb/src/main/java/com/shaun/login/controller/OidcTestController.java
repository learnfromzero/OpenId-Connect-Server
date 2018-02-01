package com.shaun.login.controller;

import com.shaun.commons.controller.BaseController;
import com.shaun.commons.util.SocketIoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/10/9.
 */
@Controller
public class OidcTestController extends BaseController {

    private Log logger = LogFactory.getLog(OidcTestController.class);

    @Autowired
    private SocketIoService serviceIo;

    //启动socket 服务
    @RequestMapping(value = "startServer.do")
    public void startServer(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            if (serviceIo.getServer() == null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            serviceIo.startServer();
                        } catch (InterruptedException e) {
                            logger.error(e);
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "pushData.do",method = RequestMethod.POST)
    public void pushData(String res){
        serviceIo.sendMessageToAllClient("advert_data",res);
    }

    @RequestMapping("oidcTest.do")
    public String oidcTest(){
        return "oidcTest/oidcTest";
    }

}
