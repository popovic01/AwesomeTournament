<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <c:import url="/jsp/common/head.jsp"/>
    </head>
    <body>
    <!-- header -->
    <c:import url="/jsp/common/header.jsp"/>

    <h1>Awesome Tournament</h1>
    <ul>
        <c:if test="${logged}">
            <h5>Welcome ${email}</h5>
        </c:if>
        <li>
            <a href="<c:url value="/home"/>">Homepage</a>
        </li>
        <c:if test="${!logged}">
            <li>
                <a href="<c:url value="/auth/login"/>">Login</a>
            </li>
            <li>
                <a href="<c:url value="/auth/signup"/>">Signup</a>
            </li>
        </c:if>
        <c:if test="${logged}">
            <li>
                <a href="<c:url value="/auth/logout"/>">Logout</a>
            </li>
        </c:if>
    </ul>

    <!-- footer -->
    <c:import url="/jsp/common/footer.jsp"/>
    </body>
</html>
