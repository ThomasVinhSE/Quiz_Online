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
        <title>Create Page</title>
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
                    <h3 class="panel-title">Create Question</h3>
                </div>
                <div class="panel-body" style="text-align: center;position:relative">


                    <form action="CreateServlet" method="POST" class="form-horizontal" >

                        <div class="form-group" style="padding-left:23%;position:relative">

                            <div style="margin-right:30px;display:inline-block">
                                <label for="input2" class="control-label">Subject</label>
                                <select name="txtSubject" id="input2" class="form-control" required="required">
                                    <option value="">-- Select One --</option>
                                    <c:if test="${not empty applicationScope.SUBJECT}" >
                                        <c:forEach var="subject" items="${applicationScope.SUBJECT}">
                                            <option value="${subject.name}" ${param.txtSubject.equals(subject.name) ? 'selected' : ''}>${subject.name}</option>
                                        </c:forEach>
                                    </c:if>

                                </select>
                            </div>
                            <div style="margin-right:30px;display:inline-block;text-align: center">
                                <label for="input3" class="control-label">Point</label>
                                <input type="number" name="txtPoint" value="${not empty param.txtPoint ? String.format("%.2f",pamra.txtPoint) : 0}"  step="0.01"min="0" id="input3" class="form-control"  placeholder="Input point">
                            </div>
                        </div>

                        <div class="form-group" style="margin-top:4%">
                            <label for="input" class="col-sm-3 control-label" style="margin-top:3%">Question Content</label>
                            <div class="col-sm-9">
                                <textarea name="txtContent" id="textarea" class="form-control" rows="3" required="required" placeholder="Input content"></textarea>
                            </div>
                        </div>

                        <div class="form-group" style="text-align:left;">
                            <div style="width:28%;float:left">
                                <img src="images/pencil.jpg" style="width:100%;height: inherit;" />
                            </div>
                            <div style="width:72%;float:right;padding-left:0">
                                <div class="radio">
                                    <label style="width:97%">
                                        <input type="radio" name="rdOption" ${param.rdOption == 1 ? 'checked=\'checked\'' : ''} required="required" id="input" value="1" style="margin-top:12px">
                                        <input type="text"  required="required" name="txtAnswer1" value="${param.txtAnswer1}" class="form-control"  placeholder="Input Answer">
                                    </label>
                                </div>
                                <div class="radio" >
                                    <label style="width:97%">
                                        <input type="radio" name="rdOption" ${param.rdOption == 2 ? 'checked=\'checked\'' : ''} required="required" id="input" value="2" style="margin-top:12px">
                                        <input type="text" required="required" name="txtAnswer2" value="${param.txtAnswer2}" class="form-control"  placeholder="Input Answer">
                                    </label>
                                </div>
                                <div class="radio" >
                                    <label style="width:97%">
                                        <input type="radio" name="rdOption" ${param.rdOption == 3 ? 'checked=\'checked\'' : ''} required="required" id="input" value="3" style="margin-top:12px">
                                        <input type="text" required="required" name="txtAnswer3" value="${param.txtAnswer3}" class="form-control"  placeholder="Input Answer">
                                    </label>
                                </div>
                                <div class="radio" >
                                    <label style="width:97%">
                                        <input type="radio" name="rdOption" ${param.rdOption == 4 ? 'checked=\'checked\'' : ''} required="required" id="input" value="4" style="margin-top:12px">
                                        <input type="text" required="required" name="txtAnswer4" value="${param.txtAnswer4}" class="form-control"  placeholder="Input Answer">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div style="position:absolute;bottom:0;right:0">
                            <button type="submit" class="btn btn-success">Create</button>
                        </div>
                    </form>



                    <div style="position:absolute;bottom:0;left:0">
                        <button class="btn btn-default" style="width:68px; max-width: 68px;"onClick="window.location.replace('manage.jsp')">Back</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>

