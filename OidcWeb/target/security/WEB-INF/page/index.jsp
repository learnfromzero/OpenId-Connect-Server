<%@ page import="com.shaun.authorize.entity.UserAccount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserAccount userAccount = (UserAccount) request.getSession().getAttribute("userAccountInfo");
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+
            request.getContextPath()+"/";
    String path = request.getContextPath();%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <title>控制台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="<%=basePath%>/resources/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=basePath%>/resources/assets/css/font-awesome.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/resources/assets/css/ace.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/resources/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/resources/assets/css/ace-skins.min.css" />
    <link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico"/>
    <script src="<%=basePath%>/resources/assets/js/ace-extra.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
    <style>
        #main_frame{
            width: 100%;
            border: none;
            height: 450px;
            display: inline;
        }
    </style>
</head>

<body>
<div class="navbar navbar-default" id="navbar" style="min-height: 45px">
    <script type="text/javascript">
        try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="icon-leaf"></i>
                    Oidc控制台
                </small>
            </a><!-- /.brand -->
        </div><!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="grey">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-tasks"></i>
                        <span class="badge badge-grey">4</span>
                    </a>

                </li>

                <li class="purple">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-bell-alt icon-animated-bell"></i>
                        <span class="badge badge-important">8</span>
                    </a>

                </li>
                <li class="green">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-envelope icon-animated-vertical"></i>
                        <span class="badge badge-success">5</span>
                    </a>
                </li>

                <li class="light-blue" onclick="showUserMenu()">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="<%=basePath%>/resources/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
                                     <div id="welcomUser"></div>
								</span>
                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                设置
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="icon-user"></i>
                                个人资料
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<%=path%>/logout">
                                <i class="icon-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul><!-- /.ace-nav -->
        </div><!-- /.navbar-header -->
    </div><!-- /.container -->
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
            </script>
            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div><!-- #sidebar-shortcuts -->
            <ul class="nav nav-list">
                <li class="active" value="">
                    <a href="<%=basePath%>indexAction.do">
                        <i class="icon-dashboard"></i>
                        <span class="menu-text"> 控制台 </span>
                    </a>
                </li>
                <li value="oidc/clientmng.do">
                    <a>
                        <i class="icon-laptop"></i>
                        <span class="menu-text">客户端管理</span>
                    </a>
                </li>
                <%--<li>--%>
                    <%--<a href="<%=basePath%>userAction.do">--%>
                        <%--<i class="icon-tag"></i>--%>
                        <%--<span class="menu-text"> 用户中心 </span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <li value="">
                    <a>
                        <i class="icon-quote-left"></i>
                        <span class="menu-text"> 社保查询 </span>
                    </a>
                </li>
            </ul><!-- /.nav-list -->
        </div>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">控制台</li>
                </ul><!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- #nav-search -->
            </div>
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        控制台
                        <small>
                            <i class="icon-double-angle-right"></i>
                            查看
                        </small>
                    </h1>
                </div><!-- /.page-header -->
                <div class="row">
                    <iframe id="main_frame" src="">
                    </iframe>
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#welcomUser").html('<%=userAccount.getUserName()%>');
        console.log(<%=request.getSession().getAttribute("_USER_MENUS_")%>);
    });
    $(".nav-list li a").css({"cursor":"pointer"});
    $(".nav-list li").click(function () {
        var url = $(this).attr("value");
        $("#main_frame").attr("src",url);
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        var menuName = $(this).find("span").text();
        $(".breadcrumb li.active").text(menuName);
    });

    function showUserMenu() {
        $("ul .user-menu").toggle();
    }
</script>
</html>
