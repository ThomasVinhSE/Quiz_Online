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
        <title>Admin Home</title>
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

            .table tr {
                cursor: pointer;
            }
            .table tr.accordion-toggle td {
                text-align: left;
            }
            .hedding h1{
                color:#fff;
                font-size:25px;
            }
            .main-section{
                margin-top: 120px;
            }
            .hiddenRow {
                padding: 0 4px !important;
                background-color: #eeeeee;
                font-size: 13px;
            }
            .accordian-body span{
                color:black !important;
            }

        </style>
        <script>
            var index = 1;
            var size = 0;
            function makeSearch()
            {
                let questionContent = document.getElementById('txtQuestionContent').value;
                let subject = document.getElementById('selectSubject').value;
                let isActive = document.getElementById('selectActive').value;

                questionContent = questionContent.trim().replace(/\s\s+/g, ' ');
                window.location.replace("SearchServlet?txtQuestionName=" + questionContent + "&txtSubject=" + subject + "&txtStatus=" + isActive + "&txtIndex=" + index);
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
                <label class="label label-danger" style="font-size:10px;vertical-align:10px;">admin</label>
            </a>
            <ul class="nav navbar-nav" style="float:right;">
                <li class="active">
                    <a>Home</a>
                </li>
                <li>
                    <a href="manage.jsp">Management</a>
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
                    <form action="SearchServlet" method="POST" class="form-inline">

                        <div class="form-group">
                            <div style="margin-right:30px;display:inline-block">
                                <label for="input1" class="col-sm-0 control-label">Question Name:</label>
                                <input type="hidden" name="txtQuestionContent" id="txtQuestionContent" value="${param.txtQuestionName}">
                                <input type="hidden" name="txtIndex" value="1">
                                <input type="text" name="txtQuestionName" value="${empty param.txtQuestionName ? '' : param.txtQuestionName}" class="form-control"  placeholder="Input name"/>
                            </div>
                            <div style="margin-right:30px;display:inline-block">
                                <label for="input2" class="col-sm-0 control-label">Subject:</label>
                                <input type="hidden" id="selectSubject" value="${param.txtSubject}">
                                <select name="txtSubject" class="form-control">
                                    <c:if test="${not empty applicationScope.SUBJECT}" >
                                        <c:forEach var="subject" items="${applicationScope.SUBJECT}">
                                            <option value="${subject.name}" ${param.txtSubject.equals(subject.name) ? 'selected' : ''}>${subject.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <div style="margin-right:30px;display:inline-block">
                                <label for="input3" class="col-sm-0 control-label">Status:</label>
                                <input type="hidden" id="selectActive" value="${param.txtStatus}">
                                <select name="txtStatus" class="form-control">
                                    <option value="1" ${param.txtStatus.equals('1') ? 'selected' : ''}>Active</option>
                                    <option value="0" ${param.txtStatus.equals('0') ? 'selected' : ''}>DeActive</option>
                                </select>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success" style="margin-left:4%">Search</button>
                    </form>
                    <hr/>
                    <c:if test="${not empty requestScope.MAP}">
                        <c:set var="map" value="${requestScope.MAP}"></c:set>
                            <div class="row" style="margin-top:3%;overflow-y:auto;height:58vh;max-height:58vh">
                                <div class="table-responsive">
                                    <table class="table table-hover" aria-label="test" style="border-collapse:collapse;">
                                        <thead>
                                            <tr>
                                                <th>No.</th>
                                                <th>Question ID</th>
                                                <th>Create Date</th>
                                                <th>Subject ID</th>
                                                <th>Point</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="pair" items="${map}" varStatus="counter">
                                            <tr data-toggle="collapse" data-target="#demo${counter.count}" class="accordion-toggle">
                                                <td>${counter.count}</td>
                                                <td>${pair.key.questionId}</td>
                                                <td>${pair.key.passDate()}</td>
                                                <td>${pair.key.subjectId}</td>
                                                <td>${pair.key.point}</td>
                                                <td>${pair.key.status ? '<label class=\'label label-success\'>Active</label>' : '<label class=\'label label-danger\'>InActive</label>'}</td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" class="hiddenRow" style="position:relative">
                                                    <div class="accordian-body collapse p-3" id="demo${counter.count}">
                                                        <p><font style="font-weight: bold">Question :</font> <span>${pair.key.content}</span></p>
                                                        <p ${pair.value.get(0).isCorrect ? 'style=\'text-decoration: underline solid ;\'': ''}><font style="font-weight: bold">A.</font> <span>${pair.value.get(0).content}</span> </p>
                                                        <p ${pair.value.get(1).isCorrect ? 'style=\'text-decoration: underline solid ;\'': ''}><font style="font-weight: bold">B.</font> <span>${pair.value.get(1).content}</span> </p>
                                                        <p ${pair.value.get(2).isCorrect ? 'style=\'text-decoration: underline solid ;\'': ''}><font style="font-weight: bold">C.</font> <span>${pair.value.get(2).content}</span> </p>
                                                        <p ${pair.value.get(3).isCorrect ? 'style=\'text-decoration: underline solid ;\'': ''}><font style="font-weight: bold">D.</font> <span>${pair.value.get(3).content}</span></p>
                                                        <p>
                                                        <form action="SearchServlet" method="POST">
                                                            <input type="hidden" name="txtQuestionName" value="${param.txtQuestionName}">
                                                            <input type="hidden" name="txtSubject" value="${param.txtSubject}">
                                                            <input type="hidden" name="txtStatus" value="${param.txtStatus}">
                                                            <input type="hidden" name="txtIndex" value="${param.txtIndex}">
                                                            <input type="hidden" name="isUpdate" value="" />
                                                            <input type="hidden" name="isSearch" value="" />
                                                            <input type="hidden" name="txtQuestionId" value="${pair.key.questionId}" />
                                                            <button class="btn btn-success" type="submit" style="position:absolute;bottom:0;right:75px;width:75px;">Update</button>
                                                        </form>
                                                        <form action="SearchServlet" method="POST">
                                                            <input type="hidden" name="txtQuestionName" value="${param.txtQuestionName}">
                                                            <input type="hidden" name="txtSubject" value="${param.txtSubject}">
                                                            <input type="hidden" name="txtStatus" value="${param.txtStatus}">
                                                            <input type="hidden" name="txtIndex" value="${param.txtIndex}">
                                                            <input type="hidden" name="isDelete" value="" />
                                                            <input type="hidden" name="txtQuestionId" value="${pair.key.questionId}" />
                                                            <input type="hidden" name="isSearch" value="" />
                                                            <button class="btn btn-danger" type="submit" style="position:absolute;bottom:0;right:0;width:75px;">Delete</button>
                                                        </form>
                                                        </p>
                                                    </div>

                                                </td> 
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${empty requestScope.MAP && not empty param.txtQuestionName}">
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
        <script>
                        $('.accordion-toggle').click(function () {
                            $('.hiddenRow').hide();
                            $(this).next('tr').find('.hiddenRow').show();
                        });
        </script>
    </body>
</html>

