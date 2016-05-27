const onReferenceMouseIn = function () { // add class for <a> tag
    var selector = '#' + $(this).attr('ref_id');
    $(selector).addClass('selected');
};

const onReferenceMouseOut = function () {
    var selector = '#' + $(this).attr('ref_id');
    $(selector).removeClass('selected');
};

const onPostSubmitButtonClick = function () {
    $('#submit-form').submit();
};

const onThreadSubmitButtonClick = function () {
    $('#submit-form').submit();
};

const openNewThread = function () {
    window.open("http://localhost:8080/thread?id=" + $(this).attr('id'), "_blank");
};

const onButtonClick = function () {
    $('#form').toggleClass('form-invoked');
    $('#message-input').attr('value', '>>' + $(this).attr('number'));
};

const openNewBoard = function () {
    window.open("http://localhost:8080/board?id=" + $(this).attr('id'), "_blank");
};

const onBoardsMouseIn = function () {
    $(this).addClass('board-highlighted');
};

const onBoardsMouseOut = function () {
    $(this).removeClass('board-highlighted');
};


$(document).ready(function () {
    $('.button').click(onButtonClick);
    $('.postLink').hover(onReferenceMouseIn, onReferenceMouseOut);
    $('#thread-submit').click(onPostSubmitButtonClick);
    $('#submit').click(onThreadSubmitButtonClick);
    $('.thread-button').click(openNewThread);
    $('.boards-names').hover(onBoardsMouseIn, onBoardsMouseOut);
    $('.boards-names').click(openNewBoard);
});
