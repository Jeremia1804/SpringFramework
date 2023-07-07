<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" import="koursa.*"%>
<%
Course nom =(Course) request.getAttribute("Course");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>BONJOUR <%out.print(String.valueOf(nom.getNom()));%></h1>  
</body>
</html>