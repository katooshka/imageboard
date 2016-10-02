<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="board" type="java.util.servlets.BoardServlet"--%>
<%--@elvariable id="threads" type="java.util.List"--%>
<%--@elvariable id="postMessageFormatter" type="java.util.servlets.MessageFormatter"--%>

<html>
<body>
<head>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <title>${board.name}</title>
</head>
<div id="boards-links-surface">
    <a href="index.html"><img id="kochan-board-image" src="colored.png"></a>
    <c:forEach var="link" items="${links}">
        <a href="board?id=${link.id}">${link.address}</a>
    </c:forEach>
</div>
<div class="board-title">
    <a href="board?id=${board.id}"><h1>${board.name}</h1></a>
</div>
<div class="new-post-button">[Create new thread]</div>

<div class="submit-form-container">
<form id="submit-form" method="post"
      enctype="multipart/form-data" accept-charset="UTF-8">
    <input type="hidden" name="board-id" value="${board.id}"/>
    <table>
        <tr class="author">
            <td>
                <span>Author</span>
            </td>
            <td>
                <textarea type="text" value="Anonymous" name="author" maxlength="100" >Anonymous</textarea>
            </td>
        </tr>
        <tr class="subject">
            <td>
                <span>Subject</span>
            </td>
            <td>
                <textarea type="text" name="subject" maxlength="100"></textarea>
            </td>
        </tr>
        <tr class="message">
            <td>
                <span>Message</span>
            </td>
            <td>
                <textarea type="text" name="message" maxlength="5000"></textarea>
            </td>
        </tr>
    </table>
    <div class="picture-and-submit">
        <div class="picture-upload-surface">
            <input type="file" class="picture-upload" name="picture" accept="image/*"/>
            <img class="reset-file-button" src="cross.png"/>
        </div>
        <div class="thread-submit-form-actions">
            <button class="submit">Send</button>
        </div>
    </div>
</form>
</div>

<hr/>
<c:forEach var="thread" items="${threads}">
    <div class="op-post">
        <div class="post-heading">
            <span>${thread.opPost.author}</span>
            <span class="subject">${thread.opPost.subject}</span>
            <span><time timestamp="${thread.opPost.postTime.millis}"></time></span>
            <span>№<a id="${thread.opPost.id}">${thread.opPost.id}</a></span>
            <a href="thread?id=${thread.opPost.threadId}"><img id="thread-reply-icon" src="reply-icon.png"></a>
            <hr/>
        </div>
        <div class="post-content">
            <c:if test="${thread.opPost.imagePath != null}">
                <div class="picture-surface">
                    <img src="images/${thread.opPost.imagePath}" alt="Image not found">
                </div>
            </c:if>
            <blockquote>${postMessageFormatter.format(thread.opPost.message)}</blockquote>
        </div>
    </div>
    <c:forEach var="post" items="${thread.tailPosts}">
        <div class="post" id="post${post.id}">
            <div class="post-heading">
                <span>${post.author}</span>
                <span class="subject">${post.subject}</span>
                <span><time timestamp="${post.postTime.millis}"></time></span>
                <span>№ <a id="${post.id}">${post.id}</a></span>
                <hr>
            </div>
            <div class="post-content">
                <c:if test="${post.imagePath != null}">
                    <div class="picture-surface">
                        <img src="images/${post.imagePath}" alt="Image not found">
                    </div>
                </c:if>
                <blockquote>${postMessageFormatter.format(post.message)}</blockquote>
            </div>
        </div>
    </c:forEach>
    <hr/>
</c:forEach>
</body>
</html>
