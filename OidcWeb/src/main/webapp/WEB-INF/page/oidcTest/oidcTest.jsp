<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/resources/inc.jsp"%>
<html>
<head>
    <title>数据</title>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/socketio/socket.io.js"></script>
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
        .content{
            width: 1000px;
            margin: 0 auto;
        }
        table.altrowstable {
            font-family: verdana,arial,sans-serif;
            font-size:11px;
            color:#333333;
            width: 100%;
            border-width: 1px;
            border-color: #a9c6c9;
            border-collapse: collapse;
        }
        table.altrowstable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #a9c6c9;
        }
        table.altrowstable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #a9c6c9;
            text-align: center;
        }
        .oddrowcolor{
            background-color:#d4e3e5;
        }
        .evenrowcolor{
            background-color:#c3dde0;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>欢迎登陆 <%=request.getSession().getAttribute("userName")%>!</h1>
</div>
<div class="content">
    <table class="altrowstable" id="alternatecolor">
        <tr>
            <th>身份证</th><th>姓名</th><th>性别</th><th>民族</th><th>出生日期</th>
            <th>社保编码</th><th>参缴基数</th><th>欠费金额</th>
        </tr>
        <tr>
            <td id="jaa001"></td>
            <td id="jaa002"></td>
            <td id="jaa003"></td>
            <td id="jaa004"></td>
            <td id="jaa054"></td>
            <td id="jab002"></td>
            <td id="jab005"></td>
            <td id="jab010"></td>
        </tr>
    </table>
    <%--<ul>--%>
        <%--<li><span>身份证：<input value="" id="jaa001"></span></li>--%>
        <%--<li><span>姓名：<input value="" id="jaa002"></span></li>--%>
        <%--<li><span>性别：<input value="" id="jaa003"></span></li>--%>
        <%--<li><span>民族：<input value="" id="jaa004"></span></li>--%>
        <%--<li><span>出生日期：<input value="" id="jaa006"></span></li>--%>
        <%--<li><span>社保编码：<input value="" id="jab002"></span></li>--%>
        <%--<li><span>参缴基数：<input value="" id="jab005"></span></li>--%>
        <%--<li><span>欠费金额：<input value="" id="jab010"></span></li>--%>
    <%--</ul>--%>
</div>
</body>
<script type="text/javascript">
    var lt = "http://localhost:8080/openid-connect-server-webapp";
    $(document).ready(function () {
        var socketUrl = "<%=path%>/startServer.do";
        $.ajax({
            url:socketUrl,
            type:'get',
            success:function(){

            }
        })
        var url = lt+"/authorize?response_type=code&client_id=client&scope=openid%20profile%20email%20address%20phone%20idcard&redirect_uri=http://localhost:9080/xz/xz/tongji/chaxunpeizhi/chaxunAction!oidcTest.do";
//        window.location.href = url;
        window.open(url,'addFileWindow','toolbar=no,location=no,resizable=no,location=no,resizable=no,height=500, width=680,,scrollbars=yes ,left=380,top=100');
    })

    var socket = io.connect('http://192.168.31.214:9092');
    socket.on('advert_data',function(data){
        var personInfo = JSON.parse(data);
        console.log(personInfo);
        $("#jaa001").text(personInfo[0].jaa001);
        $("#jaa002").text(personInfo[0].jaa002);
        $("#jaa003").text(personInfo[0].jaa003);
        $("#jaa004").text(personInfo[0].jaa004);
        $("#jaa006").text(personInfo[0].jaa006);
        $("#jaa054").text(personInfo[0].jaa054);
        $("#jab002").text(personInfo[0].jab002);
        $("#jab005").text(personInfo[0].jab005);
        $("#jab010").text(personInfo[0].jab010);
    });
</script>
</html>
