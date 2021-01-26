<%-- 
    Document   : error
    Created on : Jan 10, 2021, 7:59:04 AM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <link href="framework/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="framework/css/font-awesome.min.css">
        <style>
            body {
                background-image: url('images/error.png');
                background-size:cover;
                background-position-y: 5px;
                position:relative;
            }
            h1 {
                color:white;
            }
            div {
                margin-top:20%;
                text-align:center;
            }
        </style>
    </head>
    <body>
        <div>
        <h1>${requestScope.MESSAGE}</h1>
        <button class="btn btn-success" onClick="window.location.replace('/Assignment2_NguyenQuocVinh')">Back</button>
        </div>
        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
    </body>
</html>
