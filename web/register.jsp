<%-- 
    Document   : login
    Created on : Jan 23, 2021, 2:57:21 PM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
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
        <c:if test="${empty requestScope.ERROR}">

            <div class="main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Register System</h3>
                    </div>
                    <div class="panel-body">
                        <form action="RegisterServlet" method="POST" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Email:</label>
                                <div class="col-sm-10">
                                    <input type="email" name="txtEmail" id="input" class="form-control" value="${param.txtEmail}" required="required">

                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Name:</label>
                                <div class="col-sm-10">
                                    <input type="text" name="txtName" id="input" class="form-control" value="${param.txtName}" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Password:</label>
                                <div class="col-sm-10">
                                    <input type="password" name="txtPassword" id="input" class="form-control" value="" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Confirm Password:</label>
                                <div class="col-sm-10">
                                    <input type="password" name="txtConfirmPassword" id="input" class="form-control" value="" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-2">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <a href="/Assignment2_NguyenQuocVinh" style="text-decoration: underline;margin-left:2%">Back</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.ERROR}">
            <c:set var="error" value="${requestScope.ERROR}"></c:set>
                <div class="main">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Register System</h3>
                        </div>
                        <div class="panel-body">
                            <form action="RegisterServlet" method="POST" class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="input" class="col-sm-2 control-label">Email:</label>
                                    <div class="col-sm-10">
                                        <input type="email" name="txtEmail" id="input" class="form-control" value="${param.txtEmail}" required="required">
                                    <c:if test="${not empty error.errorEmail}">
                                        <br/><font color="red">${error.errorEmail}</font>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Name:</label>
                                <div class="col-sm-10">
                                    <input type="text" name="txtName" id="input" class="form-control" value="${param.txtName}" required="required">
                                    <c:if test="${not empty error.errorName}">
                                        <br/><font color="red">${error.errorName}</font>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Password:</label>
                                <div class="col-sm-10">
                                    <input type="password" name="txtPassword" id="input" class="form-control" value="" required="required">
                                    <c:if test="${not empty error.errorPassword}">
                                        <br/><font color="red">${error.errorPassword}</font>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="input" class="col-sm-2 control-label">Confirm:</label>
                                <div class="col-sm-10">
                                    <input type="password" name="txtConfirmPassword" id="input" class="form-control" value="" required="required">
                                    <c:if test="${not empty error.errorConfirmPassword}">
                                        <br/><font color="red">${error.errorConfirmPassword}</font>
                                    </c:if>
                                    <c:if test="${not empty error.errorMatching}">
                                        <br/><font color="red">${error.errorMatching}</font>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <c:if test="${not empty error.isDuplicate}">
                                    <br/><font color="red">${error.isDuplicate}</font>
                                </c:if>
                                <div class="col-sm-10 col-sm-offset-2">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <a href="/Assignment2_NguyenQuocVinh" style="text-decoration: underline;margin-left:2%">Back</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>
