<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/resources/inc.jsp"%><!--静态包含-->
<html>
<head>
    <title>登录绑定</title>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
</head>
<body>
    <div>
        <span>公民系统账号:<span id="xzUser"><%=request.getAttribute("bindUserName")%><span></span>
    </div>
    <div>
        <span>本系统账号：</span>
        <input id="username" placeholder="输入用户名"/>
        <input type="password" id="pwd" placeholder="输入密码"/>
        <input type="submit" value="确认绑定" onclick="bindUser()">
    </div>
</body>
<script type="text/javascript">
    function bindUser() {
        var xzUser = $("#xzUser").text().trim();
        var username = $("#username").val();
        var password = $("#pwd").val();
        $.ajax({
            url:"<%=path%>/login/saveBind.do",
            data:{xzUser:xzUser,username:username,passwrod:password},
            dataType:'JSON',
            type:'POST',
            success:function (data) {
                //自动登录
                $.ajax({
                    url:'<%=path%>/login/loginAction.do',
                    data:{'userName':username,'pwd':password},
                    dataType:'JSON',
                    type:'post',
                    success:function (data) {
                        window.location.href = "<%=basePath%>indexAction.do";
                    }
                })
            },error:function (data) {
                console.log(data)
                alert("错误")
            }
        })
    }
</script>
</html>
