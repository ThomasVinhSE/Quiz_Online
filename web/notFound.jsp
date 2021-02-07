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
        <title>Not Found Page</title>
        <style>
            body {
                
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
        <c:set var="ACCOUNT" value="${null}" scope="session"/>
        <button class="btn btn-success" onClick="window.location.replace('/Assignment2_NguyenQuocVinh')">Back</button>
    </body>
</html>
