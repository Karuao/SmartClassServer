<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
    <title>
        用户登录
    </title>
    <%String contextPath = request.getContextPath();%>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="<%=contextPath%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=contextPath%>/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=contextPath%>/css/form-elements.css">
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
</head>
<body background="<%=contextPath%>/img/1.jpg">

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>学堂小助手</h3>
                            <p>请在此输入账号密码登录</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <%--<form role="form" &lt;%&ndash;action="weblogin"&ndash;%&gt; class="login-form">--%>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户名:</label>
                                <input type="text" name="account" placeholder="Username..." class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">密码:</label>
                                <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
                            </div>
                            <button type="submit" class="btn">登录</button>
                        <%--</form>--%>
                    </div>
                </div>
            </div>
            <div class="row"></div>
        </div>
    </div>

</div>
<%--
    <form action="weblogin" method="post">
        <table>
            <caption>用户登录</caption>
            <tr><td>用户名:</td><td><input type="text" name="account" size="20"/></td></tr>
            <tr><td>密码:</td><td><input type="text" name="password" size="20"/></td></tr>
            <tr><td><input type="submit" value="登录"/>
        </table>
    </form>--%>

    <script src="<%=contextPath%>/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="<%=contextPath%>/js/jquery.backstretch.js" type="text/javascript"></script>
    <script src="<%=contextPath%>/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=contextPath%>/js/scripts.js"></script>
    <script type="text/javascript">
        $(".btn").click(function(){
            var account=document.getElementById("form-username").value;
            var password=document.getElementById("form-password").value;
            $.ajax({
                async: false,
                url: "http://localhost" + "/weblogin",
                data:{
                    account:account,
                    password:password
                },
                type: "post",
                success: function (data) {
                    if(data=="0"){
                        window.location.href = "/chooseClass"
                    }
                     else if(data=="1"){
                        alert("密码错误")
                    }
                    else if(data=="2"){
                        alert("用户不存在")
                    }

                }
                })
    });
    </script>

</body>
</center>
</html>