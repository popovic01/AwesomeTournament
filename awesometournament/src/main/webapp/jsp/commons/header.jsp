<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .header-wrapper {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        height: 4rem;
        padding: 0 1rem;
        background-color: #13730a;
    }

    .img-wrapper {
        height: 74%;
    }

    h1 {
        color: #e3e0e0;
    }

    img {
        height: 100%;
    }

    .menu-btn {
        background: none;
        border: none;
        color: #e3e0e0;
        font-size: 1.5rem;
        cursor: pointer;
    }

    .navbar {
        display: none; /* Hide the navbar by default */
        position: absolute;
        top: 4rem; /* Under the header */
        right: 0;
        background-color: #13730a;
        width: 200px;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        z-index: 1;
    }

    .navbar a {
        display: block;
        color: white;
        padding: 1rem;
        text-decoration: none;
    }

    .navbar a:hover {
        background-color: #0a4d06;
    }
</style>

<header class="header-wrapper">
    <div class="img-wrapper">
        <a href="/">
            <img src="<c:url value="/media/tournament_logo.png"/>" alt="logo of Awesome Tournament">
        </a>
    </div>
    <h1>Awesome Tournament</h1>
    <button class="menu-btn" onclick="toggleNavbar()">☰</button>
    <nav class="navbar" id="navbar">
        <c:choose>
            <c:when test="${logged}">
                <a href="<c:url value='/auth/logout'/>">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/auth/login'/>">Login</a>
                <a href="<c:url value='/auth/signup'/>">Sign up</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<script>
    function toggleNavbar() {
        var navbar = document.getElementById('navbar');
        if (navbar.style.display === 'block') {
            navbar.style.display = 'none';
        } else {
            navbar.style.display = 'block';
        }
    }
</script>
