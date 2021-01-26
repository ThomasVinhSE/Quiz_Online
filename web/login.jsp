<%-- 
    Document   : login
    Created on : Jan 23, 2021, 2:57:21 PM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
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
        <div class="main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Login System</h3>
                </div>
                <div class="panel-body">
                    <form action="LoginServlet" method="POST" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="input" class="col-sm-2 control-label">Email:</label>
                            <div class="col-sm-10">
                                <input type="email" name="txtEmail" id="input" class="form-control" value="" required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input" class="col-sm-2 control-label">Password:</label>
                            <div class="col-sm-10">
                                <input type="password" name="txtPassword" id="input" class="form-control" value="" required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <a href="register.jsp" style="text-decoration: underline;margin-left:2%">Register</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>
