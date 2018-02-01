package com.shaun.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shaun.commons.controller.BaseController;
import com.shaun.authorize.entity.MyUser;
import com.shaun.commons.util.AppException;
import com.shaun.commons.util.HttpRequestor;
import com.shaun.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/5.
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

    //    LoginService loginService = (LoginService) super.getService("loginService");
    @Autowired
    LoginService loginService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService MyUserDetailsService;

    @RequestMapping(value = "login.do")
    public String login() {
        return "/login.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "checkUser.do",method = RequestMethod.POST)
    public String checkUser(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String header = request.getHeader("Authorization");
        Map map = loginService.queryByUserName(userName);
        Map resMap = new HashMap();
        if (map == null) {
            resMap.put("success", false);
            resMap.put("msg", "用户名不存在");
        } else {
            resMap.put("success", true);
        }
        return JSON.toJSONString(resMap);
    }

    @RequestMapping(value = "bindUserCheck.do",method = RequestMethod.POST)
    @ResponseBody
    public String dkffdajfl(String thirdUserName){
        Map dtoMap = new HashMap();
        dtoMap.put("thirdUserName",thirdUserName);
        Map resMap = loginService.queryBindUser(dtoMap);
        if(resMap==null||resMap.isEmpty()){
            resMap = new HashMap();
            resMap.put("success",false);
        }else {
            resMap.put("success",true);
        }
        return JSON.toJSONString(resMap);
    }

    @RequestMapping("autoLogin.do")
    public void autoLogin(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        List<GrantedAuthority> grantedAuthorities =new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USERS");
        grantedAuthorities.add(grantedAuthority);
        UserDetails principle = MyUserDetailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principle,pwd);
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) SecurityContextHolder.getContext();
        securityContextImpl.setAuthentication(usernamePasswordAuthenticationToken);
        request.getSession().putValue("SPRING_SECURITY_CONTEXT",securityContextImpl);
        response.sendRedirect(request.getContextPath()+"/indexAction.do");
    }

    @RequestMapping(value = "bindLogin.do")
    public String checkBind(HttpServletRequest request){
        String bindUserName = request.getParameter("bindUserName");
        request.setAttribute("bindUserName",bindUserName);
        return "login/loginBind";
    }

    @RequestMapping(value = "oidcLogin.do")
    public String oidcLogin(HttpServletRequest request){
        String code = request.getParameter("code");
        String url = "http://localhost:8080/openid-connect-server-webapp/token";
        Map map = new HashMap();
        map.put("client_id","client");
        map.put("client_secret","secret");
        map.put("grant_type","authorization_code");
        map.put("code",code);
        map.put("redirect_uri","http://localhost:8081/security/login/oidcLogin.do");
        HttpRequestor httpRequestor = new HttpRequestor();
        try {
            String str = httpRequestor.doPost(url,map);
            JSONObject jsonObject = JSON.parseObject(str);
            String access_token = jsonObject.getString("access_token");
            System.out.println(access_token);
            String url2 = "http://localhost:8080/openid-connect-server-webapp/userinfo";
            String reStr =  httpRequestor.doPostSetHeader(url2,"Authorization","bearer "+access_token);
            System.out.println(reStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "saveBind.do")
    @ResponseBody
    public String saveBind(String xzUser,String username)throws AppException{
        Map map = loginService.queryByUserName(username);
        Map resMap = new HashMap();
        if (map == null) {
            resMap.put("success", false);
            resMap.put("msg", "用户名不存在");
        } else {
            Map dtoMap = new HashMap();
            dtoMap.put("xzUser",xzUser);
            dtoMap.put("username",username);
            int n = loginService.saveBind(dtoMap);
            if(n>1){
                throw new AppException("更新超过1条");
            }
            resMap.put("success", true);
            resMap.put("msg","绑定成功");
        }
        return JSON.toJSONString(resMap);
    }

    @RequestMapping(value = "loginAction.do")
    @ResponseBody
    public String loginAction(HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        userName = userName.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
            session.setAttribute("userName",userName);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        Map map = new HashMap();
        map.put("success",true);
        return JSON.toJSONString(map);
    }


    /**
     * @return
     */
    @RequestMapping("loginSuccessAction.do")
    public String loginSuccessAction() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(o instanceof UserDetails)) {

        } else {
            MyUser user = (MyUser) o;
            this.request.setAttribute("userName", user.getUsername());
        }
        return null;
    }

}
