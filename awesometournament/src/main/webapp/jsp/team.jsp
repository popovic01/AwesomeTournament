<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/common/head.jsp"/>

        <style>
            .main-wrapper {
                max-width: 800px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .title-logo-wrapper {
                display: flex;
                flex-direction: row;
                justify-content: center;
                gap: 1rem;
                height: 3rem;
                margin-bottom: 1rem;
            }

        </style>
    </head>

    <body>
        <!-- header -->
        <c:import url="/jsp/common/header.jsp"/>

        <div class="main-wrapper">
            <div class="title-logo-wrapper">
                <p class="fs-1 text-dark">
                    <c:out value="${team.name}"/>
                </p>
                <img src="data:image/jpg;base64,${team.base64Logo}"/>
            </div>

<%--            file upload--%>
<%--            <form method="POST" action="upload" enctype="multipart/form-data" >--%>
<%--                <input type="hidden" name="teamId" value="${team.getId()}">--%>
<%--                File:--%>
<%--                <input type="file" name="file" id="file" /> <br/>--%>
<%--                <input type="submit" value="Upload" name="upload" id="upload" /> <br/>--%>
<%--            </form>--%>

            <c:if test="${tournamentOwner}">
                <div>
                    You are the admin of the tournament this team belongs to
                </div>
            </c:if>
            <c:if test="${teamOwner}">
                <div>
                    You are the admin of this team
                </div>
            </c:if>

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">Position</th>
                        <th scope="col">Medical Certificate</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${players}" var="player" varStatus="i">
                        <tr>
                            <th scope="row">${i.count}</th>
                            <td>
                                <c:out value="${player.name}"/>
                                <c:out value="${player.surname}"/>
                            </td>
                            <td>
                                <c:out value="${player.dateOfBirth}"/>
                            </td>
                            <td>
                                <c:out value="${player.position}"/>
                            </td>
                            <td>
                                <button onclick="showMedCertificate()" class="hyperlink">
                                    Show
                                </button>
<%--                                <c:out value="${player.medicalCertificate}"/>--%>
                            </td>
                            <td>
                                <i class="fa fa-pencil-square-o"></i>
                                <i class="fa fa-trash-o"></i>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- footer -->
        <c:import url="/jsp/common/footer.jsp"/>
    </body>

</html>
