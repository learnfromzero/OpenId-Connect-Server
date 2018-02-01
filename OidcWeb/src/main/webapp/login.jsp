<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%><!--静态包含-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <title>登录</title>
    <link rel="stylesheet" href="<%=basePath%>/resources/login/css/login.css">
    <link rel="stylesheet" href="<%=basePath%>resources/css/init.css"/>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
    <%Exception exception = (Exception)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    String errorMsg;
    if(exception!=null){
        String excMsg = exception.getMessage();
        if("CheckCode is Invalid".equals(excMsg)){
            errorMsg = "验证码错误！";
        }else if("Bad credentials".equals(excMsg)){
            errorMsg = "用户名或密码错误！";
        }else {
            errorMsg = excMsg;
        }
    }else {
        errorMsg = "";
    }%>
</head>
<body>
    <div id="maiDiv" class="bgd_container">
        <form action ="j_spring_security_check" method="POST">
            <ul class="logContainer">
                <li>
                    <input class="inputBox" name='username' placeholder="用户名"">
                </li>
                <li>
                    <input class="inputBox" type ='password' name='password' placeholder="密码">
                </li>
                <li>
                    <input class="checkCodeBox" name='checkCode' placeholder="验证码">
                    <img class="checkCodeImg" src="" id="simpleCapt" onclick="fnRefreshCode()">
                </li>
                <li>
                    <input class="logBtn" name ="submit" type="submit" value="登录">
                </li>
                <li>
                    <span class="error_msg"><%=errorMsg%></span>
                </li>
                <li class="line">
                    <span>使用下面方式登录：</span>
                </li>
                <li class="thirdLogin">
                </li>
            </ul>
        </form>
    </div>
</body>
<script type="text/javascript">
    var simpleCaptUrl = "<%=path%>/simplecapt?j="+Math.random();
    function checkUser(a) {
        var userName = $(a).val();
        $.ajax({
            url:'<%=path%>/login/checkUser.do',
            type:'POST',
            data:{"userName":userName},
            headers:{
                "Authorization":"fdlafjald"
            },
            dataType:'JSON',
            success:function (data) {
                var result = JSON.parse(data);
                if(!result.success){
                    alert(result.msg);
                    $("#username").focus();
                    return;
                }
            }
        })
    }

    //验证码
    $(function () {
        $("#simpleCapt").attr("src",simpleCaptUrl);
    })
    function fnRefreshCode() {
        $("#simpleCapt").attr("src",simpleCaptUrl);
    }
</script>
</html>
