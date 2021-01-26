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
        <title>History Page</title>
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
                let subject = document.getElementById('selectSubject').value;

                historyId = historyId.trim().replace(/\s\s+/g, ' ');
                window.location.replace("SearchHistoryServlet?btnAction="+ "&txtHistoryId=" + historyId + "&txtSubject=" + subject + "&txtIndex=" + index);
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
                    <a>History</a>
                </li>
                <li>
                    <a href="LogOutServlet" >Log Out</a>
                </li>
            </ul>
        </nav>
        <div class="main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"></h3>
                </div>
                <div class="panel-body" style="text-align: center;">
                    <form action="SearchHistoryServlet" method="POST" class="form-inline">

                        <div class="form-group">
                            <div style="margin-right:30px;display:inline-block">
                                <label for="input1" class="col-sm-0 control-label">History ID:</label>
                                <input type="hidden" name="txtIndex" value="1">
                                <input type="text" name="txtHistoryId" id="txtHistoryId" value="${empty param.txtHistoryId ? '' : param.txtHistoryId}" class="form-control"  placeholder="Input ID"/>
                            </div>
                            <div style="margin-right:30px;display:inline-block">
                                <label for="input2" class="col-sm-0 control-label">Subject:</label>
                                <input type="hidden" id="selectSubject" value="${param.txtSubject}">
                                <select name="txtSubject" class="form-control">
                                    <option value="0">-- Select One --</option>
                                    <c:if test="${not empty applicationScope.SUBJECT}" >
                                        <c:forEach var="subject" items="${applicationScope.SUBJECT}">
                                            <option value="${subject.name}" ${param.txtSubject.equals(subject.name) ? 'selected' : ''}>${subject.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>

                        <button type="submit" name="btnAction" class="btn btn-success" style="margin-left:4%">Search</button>
                    </form>
                    <hr/>
                    <c:if test="${not empty requestScope.LIST}">
                        <c:set var="list" value="${requestScope.LIST}"></c:set>
                            <div class="row" style="margin-top:3%;overflow-y:auto;height:58vh;max-height:58vh">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>No.</th>
                                                <th>History ID</th>
                                                <th>Email</th>
                                                <th>Subject ID</th>
                                                <th>Start Time</th>
                                                <th>End Time</th>
                                                <th>Number Of Correct</th>
                                                <th>Number Of InCorrect</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item" items="${list}" varStatus="counter">
                                            <tr>
                                                <td>${counter.count}</td>
                                                <td>${item.historyId}</td>
                                                <td>${item.email}</td>
                                                <td>${item.subjectId}</td>
                                                <td>${item.passStartTime()}</td>
                                                <td>${item.passEndTime()}</td>
                                                <td>${item.numOfCorrect}</td>
                                                <td>${item.numOfInCorrect}</td>
                                                <td>${item.total}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${empty requestScope.LIST &&  param.btnAction != null}">
                        <font color="red">No record found !!!</font>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${not empty requestScope.PAGE && requestScope.PAGE > 1}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center">

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

