<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
    <title>
        用户登录
    </title>
</head>
<body bgcolor="#e3e3e3">
    <form action="weblogin" method="post">
        <table>
            <caption>用户登录</caption>
            <tr><td>用户名:</td><td><input type="text" name="account" size="20"/></td></tr>
            <tr><td>密码:</td><td><input type="text" name="password" size="20"/></td></tr>
            <tr><td><input type="submit" value="登录"/><td><input type="reset" value="重置"/>
        </table>
    </form>
</body>
</center>
</html>