<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="threads" scope="request" type="java.util.List"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/thread.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <script src="bower_components/webcomponentsjs/webcomponents-lite.min.js"></script>
    <link rel="import" href="bower_components/paper-button/paper-button.html">
    <link rel="import" href="bower_components/paper-input/paper-input.html">
    <link rel="import" href="bower_components/paper-input/paper-textarea.html">
    <link rel="import" href="bower_components/paper-card/paper-card.html">
    <link rel="import" href="bower_components/paper-styles/color.html">
    <link rel="import" href="bower_components/iron-icons/iron-icons.html">
    <link rel="import" href="bower_components/paper-dialog/paper-dialog.html">
    <link rel="import" href="bower_components/iron-collapse/iron-collapse.html">
    <link rel="import" href="bower_components/paper-icon-button/paper-icon-button.html">
    <title>/b/</title>
</head>
<body>
<h1>Досканейм</h1>
<c:forEach var="thread" items="${threads}">
    <paper-card class=op_post id="post${thread.getOpPost().number}">
        <h2>
                ${thread.getOpPost().name} ${thread.getOpPost().getDateAsString()} №<a
                id="${thread.getOpPost().number}">${thread.getOpPost().number}</a>
            <paper-icon-button class="thread_button" noink icon="content-copy" title="content-copy"
                               number="${thread.getOpPost().number}"></paper-icon-button>
        </h2>
        <div>${thread.getOpPost().message}</div>
    </paper-card>
    <c:forEach var="post" items="${thread.getTailPosts()}">
        <paper-card class=posts id="post${post.number}">
            <h2>
                    ${post.name} ${post.getDateAsString()} №<a
                    id="${post.number}">${post.number}</a>
            </h2>
            <div>${post.message}</div>
        </paper-card>
    </c:forEach>
</c:forEach>
</body>
</html>

<%--<meta charset="UTF-8">--%>