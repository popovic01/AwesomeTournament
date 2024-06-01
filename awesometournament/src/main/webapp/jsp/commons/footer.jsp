<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .footer-wrapper {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        height: 4rem;
        align-items: center;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #13730a;
    }
    .img-wrapper {
        height: 74%;
    }
    img.footer {
        height: 100%;
    }
    p.footer {
        color: #e3e0e0;
        margin: 0;
    }
</style>
<footer class="footer-wrapper">

    <div class="img-wrapper">
        <a href="http://www.unipd.it/" target="_blank">
            <img src="<c:url value="/media/unipd_logo.jpg"/>"
                 alt="logo University of Padua" class="footer">
        </a>
    </div>
    <div>
        <p class="footer">&copy; 2024 Awesome Tournament. All rights reserved.</p>
    </div>
    <div class="img-wrapper">
        <a href="/">
            <img src="<c:url value="/media/tournament_logo.png"/>"
                 alt="logo of Awesome Tournament" class="footer">
        </a>
    </div>
</footer>