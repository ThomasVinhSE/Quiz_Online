<%-- 
    Document   : adminHome
    Created on : Jan 23, 2021, 4:59:49 PM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Page</title>
        <link href="framework/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="framework/css/font-awesome.min.css">
        <style>
            body {
                background-image : url('images/bg.png');
            }
            div.main {
                margin-top:5%;
                margin-left: 15%;
                margin-right: 15%;
            }

        </style>
        <script>
            var index = 1;
            var size = 0;
            function makeSearch()
            {
                let historyId = document.getElementById('txtHistoryId').value;

                historyId = historyId.trim().replace(/\s\s+/g, ' ');
                window.location.replace("ViewDetailServlet?btnAction=" + "&txtHistoryId=" + historyId + "&txtIndex=" + index+"&txtIndex2=${param.txtIndex2}"+"&txtSubject=${param.txtSubject}");
            }
            function focusTag()
            {
                let a = document.getElementById(index);
                if (a !== null)
                    a.style.backgroundColor = "rgb(243, 217, 104)";
            }
            function clickTag(param)
            {
                index = param;
                size = document.getElementsByName("paging").length;
                if (index > size)
                {
                    index = 1;
                }
                focusTag();
            }
            function changeIndex(param)
            {
                if (index === param)
                {
                    return;
                }
                if (param === -1)
                {
                    index = index - 1;
                    if (index < 1)
                    {
                        index++;
                        return;
                    }
                } else if (param === -2)
                {
                    index = index + 1;
                    if (index > size)
                    {
                        index--;
                        return;
                    }
                } else
                    index = param;
                makeSearch();
            }
        </script>
    </head>
    <body onLoad="clickTag(${param.txtIndex})">
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <a class="navbar-brand" style="color:blue"><font style="font-size:20px;color:black">Welcome</font> ${sessionScope.ACCOUNT.name} 
                <label class="label label-success" style="font-size:10px;vertical-align:10px;">student</label>
            </a>
            <ul class="nav navbar-nav" style="float:right;">
                <li>
                    <a href="home.jsp">Home</a>
                </li>
                <li class="active">
                    <a>Detail</a>
                </li>
                <li>
                    <a href="LogOutServlet" >Log Out</a>
                </li>
            </ul>
        </nav>
        <div class="main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Detail Of History : ${param.txtHistoryId}</h3>
                </div>
                <div class="panel-body" style="text-align: center;">
                    <c:if test="${not empty requestScope.DETAIL}">
                        <c:set var="detail" value="${requestScope.DETAIL}"></c:set>
                            <div class="row" style="overflow-y:auto;height:58vh;max-height:58vh;text-align:left">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>No.</th>
                                                <th>Question ID</th>
                                                <th>Question Content</th>
                                                <th>Choice ID</th>
                                                <th>Choice Content</th>
                                                <th>Correct</th>
                                                <th>Point</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item" items="${detail}" varStatus="counter">
                                            <tr>
                                                <td>${counter.count}<input type="hidden" name="txtHistoryId" id="txtHistoryId" value="${item.dto.historyId}"></td>
                                                <td>${item.dto.questionId}</td>
                                                <td>${item.questionContent}</td>
                                                <td>${item.dto.choiceId == 78 ? '' : item.dto.choiceId}</td>
                                                <td>${item.choiceContent}</td>
                                                <td>${item.isCorrect ? '<span class=\'label label-success\'>Success</span>' : '<span class=\'label label-danger\'>Fail</span>'}</td>
                                                <td>${item.point}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${empty requestScope.DETAIL &&  param.btnAction != null}">
                        <font color="red">No record found !!!</font>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${not empty requestScope.PAGE && requestScope.PAGE > 1}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;position:relative">
                <form action="SearchHistoryServlet" method="POST">
                    <input type="hidden" name="txtHistoryId" value="${param.txtHistoryId}"/>
                    <input type="hidden" name="txtSubject" value="${param.txtSubject}"/>
                    <input type="hidden" name="txtIndex" value="${param.txtIndex2}"/>
                    <button name="btnAction" class="btn btn-default">Back</button>
                </form>
                <ul class="pagination">
                    <li><a onClick="changeIndex(-1)" >&laquo;</a></li>
                        <c:forEach begin="1" end="${requestScope.PAGE}" step="1" varStatus="counter">
                        <li><a onClick="changeIndex(${counter.count})" id="${counter.count}" name="paging" >${counter.count}</a></li>
                        </c:forEach>
                    <li><a onClick="changeIndex(-2)" >&raquo;</a></li>
                </ul>
            </div>
        </c:if>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>

    </body>
</html>

