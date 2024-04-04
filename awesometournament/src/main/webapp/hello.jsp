<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dynamically Generated Greeting</title>
</head>
<body>
    <%
          String greeting = "Hello";
    %>
    
    <h1><%= greeting %>, <%= request.getParameter("name") %>!</h1>
    
    <p>It's currently <%= new java.util.Date() %>.</p>
    <p><%= request.getAttribute("attribute")%></p>
</body>
</html>
