<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .header-wrapper {
        display: flex;
        flex-direction: row;
        justify-content: center;
        height: 4rem;
        gap: 3rem;
        align-items: center;
        background-color: #13730a;
    }
    .img-wrapper {
        height: 74%;
    }
    h1{
        justify-content: center;
        color: black;
    }
    img {
        height: 100%;
    }
</style>
<header class="header-wrapper">
    <div>
        <h1>Awesome Tournament</h1>
    </div>
    <div class="img-wrapper">
        <a href="/">
            <img src="<c:url value="/media/tournament_logo.png"/>"
                 alt="logo of Awesome Tournament">
        </a>
    </div>
</header>