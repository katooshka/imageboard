<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="posts" scope="request" type="java.util.List"/>
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
<c:forEach var="post" begin = "0" end="${posts.size() - 1}" step="1">
    <c:set var="class_type" value="posts"/>
    <c:if test="${post == 0}">
        <c:set var="class_type" value="op_post_in_thread"/>
    </c:if>
    <paper-card class="${class_type}" id="post${posts.get(post).number}">
        <h2>
                ${posts.get(post).name} ${posts.get(post).getDateAsString()} №<a id="${posts.get(post).number}">${posts.get(post).number}</a>#${post}
            <paper-icon-button class="button" noink icon="reply" title="reply" number="${posts.get(post).number}"></paper-icon-button>
        </h2>
        <div>${posts.get(post).message}</div>
    </paper-card>
</c:forEach>
<paper-card class="myform">
    <form is="iron-form" id="form" method="post" action="/thread" class="message_form" accept-charset="UTF-8">
        <div class="card-content">
            <p style="font-style: oblique">Новое сообщение</p>
            <paper-input value="Аноним" name="name" label="Имя"></paper-input>
            <paper-textarea value="" name="message_input" id="message_input" label="Сообщение"></paper-textarea>
        </div>
        <div class="card-actions">
            <paper-button id="submit">
                <iron-icon icon="icons:send"></iron-icon>Отправить</paper-button>
        </div>
    </form>
</paper-card>
</body>
</html>
