<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>AwesomeTournaments - Tournament</title>
    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="/css/tournament.css" />
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />
    <c:import url="/jsp/components/edit-tournament-form.jsp" />

    <div class="container-fluid fh" id="main-container">
        <div class="tournament-info d-flex justify-content-center align-items-center my-4">
            <h1 class="tournament-name m-4">
                <strong>${tournament.getName()}</strong>
            </h1>
            <c:choose>
                <c:when test="${not empty tournament.getBase64Logo()}">
                    <img src="data:image/jpg;base64,${tournament.getBase64Logo()}"
                        class="img-fluid rounded-circle ml-3 main-logo" alt="tournament logo" />
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/media/AT_logo.png' />"
                        class="img-fluid rounded-circle ml-3 main-logo" alt="default logo">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="d-flex justify-content-center mb-4">
            <c:if test="${owner}">
                <button id="btnEditTournament" class="btn btn-primary">Edit Tournament</button>
                <c:if test="${empty matches}">
                    <button id="generateMatches" class="btn btn-success button-separator">Close Subscriptions and<br>Generate Matches</button>
                </c:if>
            </c:if>
            <c:if test="${logged && !deadlinePassed}">
                <button id="btnAdd" class="btn btn-primary button-separator">Add Team</button>
            </c:if>
        </div>

        <div class="custom-container">
            <div class="tabs" id="rads">
                <input type="radio" id="radio-1" name="tabs" checked />
                <label class="tab" for="radio-1">Ranking</label>
                <input type="radio" id="radio-2" name="tabs" />
                <label class="tab" for="radio-2">Scorers Ranking</label>
                <input type="radio" id="radio-3" name="tabs" />
                <label class="tab" for="radio-3">Matches</label>
                <span class="glider"></span>
            </div>
        </div>

        <div class="table-container padding-top">
            <div class="table-responsive">
                <c:import url="/jsp/components/tournament-ranking.jsp" />
                <c:import url="/jsp/components/scorers-ranking.jsp" />
                <c:import url="/jsp/components/matches-list.jsp" />
            </div>
        </div>
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
    <script>
        var userId = "${userId}";
        var tournamentId = "${tournament.getId()}";
        var matches_ = "${matches}";
        var owner = "${owner}";
    </script>
    <script type="text/javascript" src="/js/tournament.js"></script>
</body>

<script type="text/javascript">
    var userId = "${userId}";
    var tournamentId = "${tournament.getId()}";
    var matches_ = "${matches}";

    var rad = document.getElementById('rads').children;

    for (var i = 0; i < rad.length; i++) {
        let local_i = i;

        if (i === 0 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'table';
            document.getElementById('rankingTable').style.display = 'none';
            document.getElementById('matches').style.display = 'none';
        } else if (i === 2 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'none';
            document.getElementById('rankingTable').style.display = 'table';
            document.getElementById('matches').style.display = 'none';
        } else if (i === 4 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'none';
            document.getElementById('rankingTable').style.display = 'none';
            document.getElementById('matches').style.display = 'block';
        }

        rad[i].addEventListener('change', function () {
            if (local_i === 0) {
                document.getElementById('tournamentTable').style.display = 'table';
                document.getElementById('rankingTable').style.display = 'none';
                document.getElementById('matches').style.display = 'none';
            } else if (local_i === 2) {
                document.getElementById('tournamentTable').style.display = 'none';
                document.getElementById('rankingTable').style.display = 'table';
                document.getElementById('matches').style.display = 'none';
            } else if (local_i === 4) {
                document.getElementById('tournamentTable').style.display = 'none';
                document.getElementById('rankingTable').style.display = 'none';
                document.getElementById('matches').style.display = 'block';
            }
        });
    }
</script>
<script type="text/javascript" src="/js/tournament.js"></script>

</html>
