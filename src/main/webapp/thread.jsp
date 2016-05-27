<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="posts" type="java.util.List"--%>
<%--@elvariable id="threadId" type="java.util.Integer"--%>
<%--@elvariable id="formatter" type="java.util.MessageFormatter"--%>
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
<c:forEach var="post" items="${posts}">
    <c:set var="classType" value="posts"/>
    <c:if test="${post.id == posts.get(0).id}">
        <c:set var="classType" value="op_post_in_thread"/>
    </c:if>
    <paper-card class="${classType}" id="post${post.id}">
        <h2 class="post-heading">
            <span>${post.author} ${post.postTime.toString()}</span>
            № <a id="${post.id}">${post.id}</a>
            <span>#${post.id}</span>
            <paper-icon-button class="button" noink icon="reply" title="reply" number="${post.id}"></paper-icon-button>
        </h2>
        <div>${formatter.format(post.message)}</div>
    </paper-card>
</c:forEach>
<paper-card class="submit-form">
    <form is="iron-form" id="submit-form" method="post" action="/thread" class="message-form" accept-charset="UTF-8">
        <div class="card-content">
            <p style="font-style: oblique">Новое сообщение</p>
            <input type="hidden" name="thread-id" value="${threadId}"/>
            <paper-input value="Аноним" name="author" label="Имя"></paper-input>
            <paper-textarea value="" name="message" id="message-input" label="Сообщение"></paper-textarea>
        </div>
        <div class="card-actions">
            <paper-button id="thread-submit">
                <iron-icon icon="icons:send"></iron-icon>
                Отправить
            </paper-button>
        </div>
    </form>
</paper-card>
</body>
</html>
