<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%>
<html>
<head>
    <title>管理客户端</title>
    <style>

        table{
            width: 100%;
            border-collapse: collapse;
            text-align: center;
        }
        table tr{
            height: 30px;
        }
        table th{
            font-size: 12px;
            color: #939393;
        }
        table td{
            font-size: 12px;
            color: #222222;
        }
        #clientDetailsTable-head tr{
            border-top: 1px solid #DEDEDE;
        }
        #clientDetailsTable tr{
            border-top: 1px solid #DEDEDE;
            border-bottom: 1px solid #DEDEDE;
            cursor: pointer;
        }
        #queryPanel .queryButton{
            width: 80px;
            height: 30px;
            text-decoration: none;
            cursor: pointer;
            margin: 5px 5px;
            line-height: 30px;
            background-color: #fff;
            font-size: 15px;
            border: none;
            border-radius: 10px;
            outline: none;
        }
        #queryPanel .on{
            color: #fff;
            background-color: #48BAC1;
        }
        #queryPanel ul{
            list-style-type: none;
            margin: 0;
        }
        .trMouseover{
            background-color:#F4F9F9;
        }
    </style>
</head>
<body>
    <input type="hidden" id="selectedClientId">
    <div style="width: 100%;height: 400px;">
        <div id="queryPanel">
            <ul>
                <li>
                    <input type="button" value="新增" class="queryButton on" id="add">
                    <input type="button" value="查看" class="queryButton" id="show">
                    <input type="button" value="测试" class="queryButton" id="test">
                    <input type="button" value="删除" class="queryButton" id="del">
                </li>
            </ul>
        </div>
        <div>
            <div>
                <table id="clientDetailsTable-head">
                    <tr>
                        <td style="width: 25%">客户端名称</td>
                        <td style="width: 25%">客户端Id</td>
                        <td style="width: 25%">授权模式</td>
                        <td style="width: 25%">重定向地址</td>
                    </tr>
                </table>
            </div>
            <div style="height: 203px;overflow: auto">
                <table id="clientDetailsTable">
                </table>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    $(function () {
        $(".queryButton").click(function () {
            $(this).addClass("on");
            $(this).siblings().removeClass("on");
        });

        $.ajax({
            url:'<%=basePath%>oidc/clientDetails.do',
            type:'get',
            dataType:'json',
            success:function (data) {
                data = JSON.parse(data);
                var html = "";
                for(var i=0;i<data.length;i++){
                    var clientDetails = data[i];
                    html += "<tr onclick='selectClient(this)'><td style=\"width: 25%\">";
                    html+=clientDetails.clientName;
                    html+="</td><td style=\"width: 25%\">";
                    html+=clientDetails.clientId;
                    html+="</td><td style=\"width: 25%\">";
                    html+=clientDetails.authorizedGrantTypes;
                    html+="</td><td style=\"width: 25%\">";
                    html+=clientDetails.webServerRedirectUri;
                    html+="</td></tr>";
                }
                $("#clientDetailsTable").append(html);
            }
        })
    });
    function selectClient(a) {
        var clientId = $(a).find("td").eq(1).text();
        console.log(clientId)
        $("#selectedClientId").val(clientId);
        $(this).addClass("trMouseover");
        $(this).siblings().removeClass("trMouseover");
    }
</script>
</html>
