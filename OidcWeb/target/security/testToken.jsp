<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/oidc/oauth/token?client_id=71add01ce2d54742805dc641225d3bb5&client_secret=IXZ0u3cK5VYhCm3izPuqscBuL9ZmQSC9&grant_type=authorization_code&code=&redirect_uri=http://localhost:8080/oidc/testToken.jsp"
    method="post">
        <button type="submit" value="tijiao"></button>
    </form>
</body>
<script type="text/javascript">
    //    var lt = "http://localhost:8080/oauth2";
    var lt = "http://localhost:8080/oidc";
    $(document).ready(function () {
        var a = window.location.search;
        var returnCode = a.substr(1);
        var returns = returnCode.split("&");
        for(var i=0;i<returns.length;i++) {
            var entry = returns[i];
            var key = entry.substr(0, entry.indexOf("="));
            if (key == "code") {//获取code
                var code = entry.substr(entry.indexOf("=") + 1);
                var url = lt+"/oauth/token?client_id=71add01ce2d54742805dc641225d3bb5&client_secret=IXZ0u3cK5VYhCm3izPuqscBuL9ZmQSC9&grant_type=authorization_code&code="+code+"&redirect_uri=http://localhost:8080/oidc/testToken.jsp" ;
                $.ajax({
                    url: url,
                    type: 'post',
                    success: function (data) {
                        console.log(data)
                        data = JSON.parse(data);
                        var access_token = data.value;
//                        window.open(lt+"/user/userinfo.do?access_token="+access_token);
//                        $.ajax({
//                            url:lt+'/user/userinfo.do?access_token='+access_token,
//                            type:'POST',
//                            success:function (data) {
//                                console.log(data);
//                            }
//                        })
//                        var access_token = data.access_token;
//                        $.ajax({
//                            url:lt+'/m/user_info?access_token='+access_token,
//                            type:'POST',
//                            success:function (data) {
//                                console.log(data)
//                            }
//                        })
                    }
                })
            }
        }

    })
</script>
</html>
