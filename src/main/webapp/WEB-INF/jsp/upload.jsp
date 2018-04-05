<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>班课文件上传</title>
    <%String contextPath = request.getContextPath();%>
    <link rel="stylesheet" href="<%=contextPath%>/css/bootstrap.min.css">
</head>
<body>

<div class="page-header">
    <center><h1>班课文件上传</h1></center>
</div>
<form method="post" action="UploadServlet" enctype="multipart/form-data" class="form-inline" role="form">
    <label class="sr-only" for="inputfile">选择文件</label>
    <input type="file"id="inputfile" name="uploadFile" />
    <br/><br/>
    <input type="submit" class="btn btn-default" value="上传" />
    <script src="<%=contextPath%>/js/bootstrap.min.js" type="text/javascript"></script>
</form>

</body>
</html>