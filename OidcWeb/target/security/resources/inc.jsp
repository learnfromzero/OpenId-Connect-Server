<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+
                  request.getContextPath()+"/";
String path = request.getContextPath();%>
<html>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico"/>
<script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>