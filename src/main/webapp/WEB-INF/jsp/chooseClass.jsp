<%--
  Created by IntelliJ IDEA.
  User: n551
  Date: 2018/3/10
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html>
<head>
    <title>chooseClass</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/bootstrap.min.css">
    <script type="text/javascript">
        $(document).ready(function() {
            var classid;
            $.ajax({
                async: false,
                url: "http://localhost" + "/chooseClassRequest",
                type: "post",
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    var jsonStr = JSON.stringify(data);
                    var jsonData1 = JSON.parse(jsonStr);
                    var jsonData = [];
                    jsonData1.forEach(function(item){
                        jsonData.push({邀请码: item.class_id, 班级名: item.name,班课名:item.course})
                    })
                    var headArray = [];
                    appendTable(jsonData);
                    function parseHead(oneRow) {
                        for (var i in oneRow) {
                            headArray[headArray.length] = i;
                        }
                    }

                    function appendTable() {
                        parseHead(jsonData[0]);
                        var div = document.getElementById("div1");
                        var table = document.getElementById("table1");

                        //var thead = document.createElement("tr");
/*                        for (var count = 0; count < headArray3; count++) {
                            var td = document.createElement("th");
                            td.innerHTML = headArray[count];
                            thead.appendChild(td);
                        }*/
                        //table.appendChild(thead);
                        for (var tableRowNo = 0; tableRowNo < jsonData.length; tableRowNo++) {
                            var tr = document.createElement("tr");
                            for (var headCount = 0; headCount < headArray.length; headCount++) {
                                var cell = document.createElement("td");
                                cell.innerHTML = jsonData[tableRowNo][headArray[headCount]];
                                classid=jsonData[tableRowNo][headArray[0]];
                                tr.appendChild(cell);
                            }
                           tr.innerHTML +="<input name=\"按钮\" id="+classid+"  class=\"btn\" type=\"button\" value=\"上传\"><\/input>";
                            table.appendChild(tr);
                        }
                        div.appendChild(table);
                    }

                }
            })
            $(".btn").click(function(){
                var id=$(this).attr("id");
                window.location.href = "upload?class_id="+id;
            });

        })




    </script>
    <style>
        table {
            width: 1500px;
            padding: 0 ;
            border-collapse: collapse;
        }
        td,th {
            border: 1px solid #ddd;
            padding: 6px 6px 6px 12px;
            color: #4f6b72;
            text-align: center;
        }
        th {
            background: #d3d3d3;

        }
    </style>
</head>


<body>
<div class="page-header">
<center><h1>请选择已创建的班课（如果没有班课请创建后再来本页面）</h1></center>
</div>
<div id="div1"></div>
<table id="table1"class="table table-bordered">
    <tr>
        <th>邀请码</th><th>班级名</th><th>班课名</th>
    </tr>
</table>
<script src="<%=contextPath%>/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
