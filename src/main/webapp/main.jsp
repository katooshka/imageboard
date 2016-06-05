<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="boards" type="java.util.List"--%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/thread.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <link rel="import" href="bower_components/paper-card/paper-card.html">
    <title>Кочан</title>
</head>
<body>
<div class="main-title">
    <img src="cabbage.png">
    <h1>Кочан</h1>
</div>
<paper-card class="boards-list">
    <c:forEach var="board" items="${boards}">
        <div class="boards-names" id="${board.id}">
                ${board.name}
        </div>
    </c:forEach>
</paper-card>
</body>
</html>
