<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="boards" type="java.util.List"--%>

<html id="main-html">
<head>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <title>Kochan</title>
</head>
<body class="main-body">
<div id="base-surface">
    <div id="main-headline">
        <a href="/index.html"><img id="kochan-picture" src="colored.png"></a>
        <a href="/index.html"><h1 id="imageboard-name">Kochan</h1></a>
    </div>
    <div id="boards-cards-surface">
        <c:forEach var="board" items="${boards}">
            <a href="board?id=${board.id}">
                <div class="board-card" id="${board.id}">
                    <div class="board-name">
                        <h2>${board.name}</h2>
                    </div>
                    <div class="board-image">
                        <img src="images/${board.imagePath}" alt="Картинка не найдена">
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>
