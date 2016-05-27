<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="board" type="java.util.servlets.BoardServlet"--%>
<%--@elvariable id="threads" type="java.util.List"--%>
<%--@elvariable id="postMessageFormatter" type="java.util.servlets.MessageFormatter"--%>


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
    <title>${board.name}</title>
</head>
<body>
<h1>${board.name}</h1>
<c:forEach var="thread" items="${threads}">
    <paper-card class="op-post" id="post${thread.opPost.id}">
        <h2 class="post-heading">
            <span>${thread.opPost.author} ${thread.opPost.postTime.toString()}</span>
            № <a id="${thread.opPost.id}">${thread.opPost.id}</a>
            <paper-icon-button class="thread-button" noink icon="content-copy" title="content-copy"
                               id="${thread.opPost.threadId}"></paper-icon-button>
        </h2>
        <div>${postMessageFormatter.format(thread.opPost.message)}</div>
    </paper-card>
    <c:forEach var="post" items="${thread.tailPosts}">
        <paper-card class="posts" id="post${post.id}">
            <h2 class="post-heading">
                <span>${post.author} ${post.postTime.toString()}</span>
                № <a id="${post.id}">${post.id}</a>
            </h2>
            <div>${postMessageFormatter.format(post.message)}</div>
        </paper-card>
    </c:forEach>
</c:forEach>
<paper-card class="submit-form">
    <form is="iron-form" id="submit-form" method="post" action="/board" class="message-form" accept-charset="UTF-8">
        <div class="card-content">
            <p style="font-style: oblique">Новое сообщение</p>
            <input type="hidden" name="board-id" value="${board.id}"/>
            <paper-input value="Аноним" name="author" label="Имя"></paper-input>
            <paper-textarea value="" name="message" id="message" label="Сообщение"></paper-textarea>
        </div>
        <div class="card-actions">
            <paper-button id="submit">
                <iron-icon icon="icons:send"></iron-icon>
                Отправить
            </paper-button>
        </div>
    </form>
</paper-card>
</body>
</html>