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
        <title>Update Page</title>
        <link href="framework/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="framework/css/font-awesome.min.css">
        <style>
            body {
                background-image : url('images/bg.png');
            }
            div.main {
                margin-top:5%;
                margin-left: 30%;
                margin-right: 30%;
            }

        </style>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <a class="navbar-brand" style="color:blue"><font style="font-size:20px;color:black">Welcome</font> ${sessionScope.ACCOUNT.name} 
                <label class="label label-danger" style="font-size:10px;vertical-align:10px;">admin</label>
            </a>
            <ul class="nav navbar-nav" style="float:right;">
                <li>
                    <a href="adminHome.jsp">Home</a>
                </li>
                <li class="active">
                    <a>Management</a>
                </li>
                <li>
                    <a href="LogOutServlet" >Log Out</a>
                </li>
            </ul>
        </nav>
        <div class="main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <c:if test="${not empty requestScope.PAIR}">
                        <h3 class="panel-title">Update Question --> ${param.txtQuestionId}</h3>
                    </c:if>
                    <c:if test="${empty requestScope.PAIR}">
                        <h3 class="panel-title">Update Question</h3>
                    </c:if>
                </div>
                <div class="panel-body" style="text-align: center;position:relative">

                    <c:if test="${empty param.txtQuestionId || empty requestScope.PAIR}">
                        <form action="SearchServlet" method="POST" class="form-horizontal" style="margin-bottom:8%;margin-top:2%">
                            <div class="form-group">
                                <label for="input" class="col-sm-3 control-label">Question Id:</label>
                                <div class="col-sm-7">
                                    <input type="text" name="txtQuestionId" id="input" class="form-control" value="" required="required">
                                </div>
                                <input type="hidden" name="isUpdate" value="" />
                            </div>
                            <c:if test="${not empty param.txtQuestionId}">
                                <font color="red">Not found question</font>
                            </c:if>
                            <div style="position:absolute;bottom:0;right:0">
                                <button type="submit" class="btn btn-success">Search</button>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${not empty param.txtQuestionId && not empty requestScope.PAIR}">
                        <c:set var="question" value="${requestScope.PAIR.getKey()}"></c:set>
                        <c:set var="list" value="${requestScope.PAIR.getValue()}"></c:set>
                            <form action="UpdateServlet" method="POST" class="form-horizontal" >
                                <input type="hidden" name="txtQuestionName" value="${param.txtQuestionName}" />
                            <input type="hidden" name="txtSubject" value="${param.txtSubject}" />
                            <input type="hidden" name="txtStatus" value="${param.txtStatus}" />
                            <input type="hidden" name="txtIndex" value="${param.txtIndex}" />
                            <c:if test="${param.isSearch != null}">
                                <input type="hidden" name="isSearch" value="${param.isSearch}" />
                            </c:if>
                            <input type="hidden" name="txtQuestionId" value="${question.questionId}" />
                            <div class="form-group" style="padding-left:23%;position:relative">

                                <div style="margin-right:30px;display:inline-block">
                                    <label for="input2" class="control-label">Subject</label>
                                    <select name="txtSubject1" id="input2" class="form-control" required="required">
                                        <option value="">-- Select One --</option>
                                        <c:if test="${not empty applicationScope.SUBJECT}" >
                                            <c:forEach var="subject" items="${applicationScope.SUBJECT}">
                                                <option value="${subject.name}" ${question.subjectName.equals(subject.name) ? 'selected' : ''}>${subject.name}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </div>
                                <div style="margin-right:30px;display:inline-block;text-align: center">
                                    <label for="input3" class="control-label">Point</label>
                                    <input type="number" name="txtPoint" value="${not empty question.point ? String.format("%.2f",question.point) : 0}"  step="0.01"min="0" id="input3" class="form-control"  placeholder="Input point">
                                </div>
                            </div>

                            <div class="form-group" style="margin-top:4%">
                                <label for="input" class="col-sm-3 control-label" style="margin-top:3%">Question Content</label>
                                <div class="col-sm-9">
                                    <textarea name="txtContent" id="textarea" class="form-control" rows="3" required="required" placeholder="Input content">${question.content}</textarea>
                                </div>
                            </div>

                            <div class="form-group" style="text-align:left;">
                                <div style="width:28%;float:left">
                                    <img src="images/pencil.jpg" style="width:100%;height: inherit;" />
                                </div>
                                <div style="width:72%;float:right;padding-left:0">
                                    <div class="radio">
                                        <label style="width:97%">
                                            <input type="radio" name="rdOption" ${list.get(0).isCorrect ? 'checked=\'checked\'' : ''} value="1" id="input" style="margin-top:12px">
                                            <input type="text"  required="required" name="txtAnswer1" value="${list.get(0).content}" class="form-control"  placeholder="Input Answer">
                                            <input type="hidden" name="answer1_Id" value="${list.get(0).choiceId}">
                                        </label>
                                    </div>
                                    <div class="radio" >
                                        <label style="width:97%">
                                            <input type="radio" name="rdOption" ${list.get(1).isCorrect ? 'checked=\'checked\'' : ''} id="input" value="2" style="margin-top:12px">
                                            <input type="text" required="required" name="txtAnswer2" class="form-control" value="${list.get(1).content}" placeholder="Input Answer">
                                            <input type="hidden" name="answer2_Id" value="${list.get(1).choiceId}">
                                        </label>
                                    </div>
                                    <div class="radio" >
                                        <label style="width:97%">
                                            <input type="radio" name="rdOption" ${list.get(2).isCorrect ? 'checked=\'checked\'' : ''} id="input" value="3" style="margin-top:12px">
                                            <input type="text" required="required" name="txtAnswer3" class="form-control" value="${list.get(2).content}" placeholder="Input Answer">
                                            <input type="hidden" name="answer3_Id" value="${list.get(2).choiceId}">
                                        </label>
                                    </div>
                                    <div class="radio" >
                                        <label style="width:97%">
                                            <input type="radio" name="rdOption" ${list.get(3).isCorrect ? 'checked=\'checked\'' : ''} id="input" value="4" style="margin-top:12px">
                                            <input type="text" required="required" name="txtAnswer4" value="${list.get(3).content}" class="form-control"  placeholder="Input Answer">
                                            <input type="hidden" name="answer4_Id" value="${list.get(3).choiceId}">
                                        </label>
                                    </div>
                                    <div class="radio" style="text-align:right">
                                        <label>
                                            <input type="checkbox" value="" name="chkActive" ${question.status ? 'checked=\'checked\'' : ''} style="margin-top:12px">
                                            &nbsp;<span>Active</span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div style="position:absolute;bottom:0;right:0">
                                <button type="submit" class="btn btn-warning">Update</button>
                            </div>
                        </form>

                    </c:if>

                    <c:if test="${param.isSearch == null}">
                        <div style="position:absolute;bottom:0;left:0">
                            <button class="btn btn-default" style="width:71px; max-width: 71px;" onClick="window.location.replace('manage.jsp')">Back</button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>

