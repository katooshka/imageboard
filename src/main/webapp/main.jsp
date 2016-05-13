<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="boards" scope="request" type="java.util.List"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/thread.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <link rel="import" href="bower_components/paper-card/paper-card.html">
    <title>Кочан</title>
    <div class="main_title">
        <img src="kapust09.png">
        <h1>Кочан</h1>
    </div>
    <paper-card class="boards_list">
        <c:forEach var="board" begin="0" end="${boards.size() - 1}">
            <div class="boards_names" id="${boards.get(board)}">
                    ${boards.get(board)}
            </div>
        </c:forEach>

    </paper-card>
</head>
<body>

</body>
</html>
