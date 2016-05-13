const onReferenceMouseIn = function () { // add class for <a> tag
    var selector = '#' + $(this).attr('ref_id');
    $(selector).addClass('selected');
    //document.getElementById(this.getAttribute('ref_id')).style.backgroundColor = 'B9CCB4';
};

const onReferenceMouseOut = function () {
    var selector = '#' + $(this).attr('ref_id');
    $(selector).removeClass('selected');
};

const onSubmitButtonClick = function () {
    $('#form').submit();
};

const openNewThread = function () {
    window.open("http://localhost:8080/thread?id=" + $(this).attr('number'), "_blank");
};

const onButtonClick = function () {
    $('#form').toggleClass('form-invoked');
    $('#message_input').attr('value', '>>' + $(this).attr('number'));
};

const openNewBoard = function () {
    window.open("http://localhost:8080/board?name=" + $(this).attr('id'), "_blank");
};

const onBoardsMouseIn = function () {
    $(this).addClass('board_highlighted');
};

const onBoardsMouseOut = function () {
    $(this).removeClass('board_highlighted');
};


$(document).ready(function () {
    $('.button').click(onButtonClick);
    $('.postLink').hover(onReferenceMouseIn, onReferenceMouseOut);
    $('#submit').click(onSubmitButtonClick);
    $('.thread_button').click(openNewThread);
    $('.boards_names').hover(onBoardsMouseIn, onBoardsMouseOut);
    $('.boards_names').click(openNewBoard);
});
