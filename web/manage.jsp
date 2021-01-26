<%-- 
    Document   : adminHome
    Created on : Jan 23, 2021, 4:59:49 PM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Management Page</title>
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
                    <h3 class="panel-title">Manage Option</h3>
                </div>
                <div class="panel-body" style="text-align: center">
                    <button class="btn btn-default" onClick="window.location.replace('create.jsp');" style="margin-bottom:2%">Create Question</button>
                    <br/>
                    <button class="btn btn-default" onClick="window.location.replace('update.jsp');" style="margin-bottom:2%">Update Question</button>
                    <br/>
                    <button class="btn btn-default" onClick="window.location.replace('delete.jsp');">Delete Question</button>
                </div>
            </div>
        </div>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>

