<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="posts" type="java.util.List"--%>
<%--@elvariable id="threadId" type="java.util.Integer"--%>
<%--@elvariable id="formatter" type="java.util.MessageFormatter"--%>
<html>
<body>
<head>
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
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
<div class="new-post-button">[Create new post]</div>

<div class="submit-form-container">
    <form id="submit-form" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <input type="hidden" name="thread-id" value="${threadId}"/>
        <table>
            <tr class="author">
                <td>
                    <span>Author</span>
                </td>
                <td>
                    <textarea type="text" value="Anonymous" name="author" maxlength="100">Anonymous</textarea>
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
<div class="inner-post-submit-form-container">
    <form id="inner-post-submit-form" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <hr/>
        <input type="hidden" name="thread-id" value="${threadId}"/>
        <table>
            <tr class="author">
                <td>
                    <span>Author</span>
                </td>
                <td>
                    <textarea type="text" value="Anonymous" name="author" maxlength="100">Anonymous</textarea>
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
                    <textarea id="inner-post-submit-form-text" type="text" name="message" maxlength="5000"></textarea>
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
        <hr/>
    </form>
</div>

<c:forEach var="post" items="${posts}">
    <c:set var="classType" value="post"/>
    <c:if test="${post.id == posts.get(0).id}">
        <c:set var="classType" value="op-post"/>
    </c:if>
    <div class="${classType}" id="post${post.id}" post-id="${post.id}">
        <div class="post-heading">
            <span>${post.author}</span>
            <span class="subject">${post.subject}</span>
            <span><time timestamp="${post.postTime.millis}"></time></span>
            <span>№<a id="${post.id}">${post.id}</a></span>
            <img class="post-reply-icon" src="reply-icon.png">
            <hr/>
        </div>
        <div class="post-content">
            <c:if test="${post.imagePath != null}">
                <div class="picture-surface">
                    <img src="images/${post.imagePath}" alt="Image not found">
                </div>
            </c:if>
            <blockquote>${formatter.format(post.message)}</blockquote>
        </div>
    </div>
</c:forEach>
<hr/>
</body>
</html>
