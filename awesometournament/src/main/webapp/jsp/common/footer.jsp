<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .footer-wrapper {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        height: 3rem;
        align-items: center;
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #135dc9;
    }
    .img-wrapper {
        height: 74%;
    }
    img {
        height: 100%;
    }
    p {
        color: #e3e0e0;
        margin: 0;
    }
</style>
<footer class="footer-wrapper">

    <div class="img-wrapper">
        <a href="http://www.unipd.it/" target="_blank">
            <img src="<c:url value="/media/unipd_logo.jpg"/>"
                 alt="logo University of Padua">
        </a>
    </div>
    <div>
        <p>&copy; 2024 Awesome Tournament. All rights reserved.</p>
    </div>
    <div class="img-wrapper">
        <a>
            <img src="<c:url value="/media/tournament_logo.png"/>"
                 alt="logo of Awesome Tournament">
        </a>
    </div>
</footer>