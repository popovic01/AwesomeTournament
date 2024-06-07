<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<head>
    <title>Header</title>
    <link rel="stylesheet" type="text/css" href="/css/header.css" />
</head>

<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand custom-color-bold" href="/">
            Awesome Tournaments
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/tournaments">Tournaments</a>
                </li>
                <c:choose>
                    <c:when test="${logged}">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/auth/logout">Logout</a>
                        </li>
                    </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/auth/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/auth/signup">Signup</a>
                    </li>
                </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
