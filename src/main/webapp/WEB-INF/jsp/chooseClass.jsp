<%--
  Created by IntelliJ IDEA.
  User: n551
  Date: 2018/3/10
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
    <title>chooseClass</title>
    <script type="text/javascript">
        $(document).ready(function() {
            var classid;
            $.ajax({
                async: false,
                url: "http://localhost" + "/chooseClass",
                type: "post",
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    var jsonStr = JSON.stringify(data);
                    var jsonData1 = JSON.parse(jsonStr);
                    var jsonData = [];
                    jsonData1.forEach(function(item){
                        jsonData.push({class_id: item.class_id, name: item.name,course:item.course})
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
                        var table = document.createElement("table");
                        var thead = document.createElement("tr");
                        for (var count = 0; count < headArray.length; count++) {
                            var td = document.createElement("th");
                            td.innerHTML = headArray[count];
                            thead.appendChild(td);
                        }
                        table.appendChild(thead);
                        for (var tableRowNo = 0; tableRowNo < jsonData.length; tableRowNo++) {
                            var tr = document.createElement("tr");
                            for (var headCount = 0; headCount < headArray.length; headCount++) {
                                var cell = document.createElement("td");
                                cell.innerHTML = jsonData[tableRowNo][headArray[headCount]];
                                classid=jsonData[tableRowNo][headArray[0]];
                                tr.appendChild(cell);
                            }
                           tr.innerHTML +="<input name=\"按钮\" id="+classid+"  class=\"btn\" type=\"button\" value=\"upload\"><\/input>";
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


            /*  for (var i = 0; i < jsonData.length; i++) {
                  var class_id=jsonData[i].class_id;
                  var name=jsonData[i].course;
                  var course=jsonData[i].name;
                  //alert(class_id+"\n"+name);
              }*/


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
<div id="div1"></div>

</body>
</html>
