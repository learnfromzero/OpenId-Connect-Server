<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE HTML>

<html>
<head>
    <title>Oauth Approval</title>
</head>
<body><h1>OAuth Approval</h1>

<p>你确定要授权给'${authorizationRequest.clientId}' 获取的你资源吗？</p>

<form id='confirmationForm' name='confirmationForm' action='${pageContext.request.contextPath}/oauth/authorize'
      method='post'>
    <input name='user_oauth_approval' value='true' type='hidden'/>
    <label> <input name='authorize' value='确认' type='submit' class="btn btn-success"/></label>
</form>
<form id='denialForm' name='denialForm' action='${pageContext.request.contextPath}/oauth/authorize' method='post'>
    <input name='user_oauth_approval' value='false' type='hidden'/>
    <label><input name='deny' value='取消' type='submit' class="btn btn-warning"/></label>
</form>
</body>
</html>