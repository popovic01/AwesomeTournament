<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/common/head.jsp"/>

        <style>
            .input-wrapper {
                display: flex;
                flex-direction: row;
                justify-content: start;
                gap: 1rem;
                width: 50%;

                p {
                    width: 40%;
                }
            }
        </style>
    </head>

    <body>

        <form method="POST" action="" enctype="multipart/form-data" >
            <input type="hidden" name="teamId" value="${team.getId()}">
            <div class="input-wrapper">
                <p class="text-dark">Team Name:</p>
                <input type="text" name="name" placeholder="Name" class="form-control">
            </div>
            <div class="input-wrapper">
                <p class="text-dark">Team Logo:</p>
                <input type="file" name="file" id="file" class="btn btn-outline-secondary"/>
            </div>
            <div class="d-flex justify-content-start">
                <input type="submit" value="Edit" name="upload" class="btn btn-secondary"/>
            </div>
        </form>

    </body>
</html>
