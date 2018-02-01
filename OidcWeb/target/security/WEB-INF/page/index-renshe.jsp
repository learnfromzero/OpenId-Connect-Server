<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%>
<html>
<head>
    <title>首页</title>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
    <style type="text/css">
        body{
            font-size: 12px;
            margin: 0 auto;
            background: url("./resources/images/top1.png") repeat-x top center;
        }
        .header {
            height: 175px;
            width: 1000px;
            margin: 0 auto;
            background: url("./resources/images/logo.png") no-repeat 30px 47px;
        }
        .header h1{
            list-style-type: none;
            width: 150px;
            float: right;
            height: 30px;
            font-size: 17px;
            line-height: 30px;
            margin-top: 40px;
        }
        .contain_div{
            width: 1000px;
            height: 230px;
            margin:0 auto;
        }
        .contain_div ul{
            list-style-type: none;
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }
        .contain_div ul li{
            float: left;
            list-style-type: none;
            width: 200px;
            height: 200px;
            margin: 25px 150px;
            border-radius: 200px;
            background-color: rgba(106, 181, 189, 0.47);
            cursor: pointer;
            line-height: 200px;
            text-align: center;
        }
        .contain_div ul li:hover{
            color: #5bc0de;
        }
        a {
            color: white;
            text-decoration: none;
            font-size: 30px;
        }
        a:hover{
            color: #5bc0de;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>欢迎登陆 <%=request.getSession().getAttribute("userName")%>!</h1>
</div>
<div class="buttonContainer">
    <div class="contain_div">
        <ul>
            <li onclick="queryMsg()" title="通过统一身份认证平台授权给公民系统，获取个人身份信息，查询个人社保缴纳情况">
                <a >
                    我的社保
                </a>
            </li>
            <li>
                <a href="#" title="我要就业">
                    我要就业
                </a>
            </li>
            <li>
                <a href="#" title="我要维权">
                    我要维权
                </a>
            </li>
            <li>
                <a href="#" title="申请管理">
                    申请管理
                </a>
            </li>
        </ul>
    </div>
</div>
</body>
<script type="text/javascript">

    var lt = "http://localhost:8080/openid-connect-server-webapp";
    function queryMsg() {
        var url = "http://localhost:8081/security/oidcTest.do";
//        var url = lt+"/authorize?response_type=code&client_id=client&scope=openid%20profile%20email%20address%20phone%20idcard&redirect_uri=http://localhost:9080/xz/xz/tongji/chaxunpeizhi/chaxunAction!oidcTest.do";
        window.location.href = url;
//        window.open(url,'addFileWindow','toolbar=no,location=no,resizable=no,location=no,resizable=no,height=500, width=680,,scrollbars=yes ,left=380,top=100');
    }
</script>
</html>
