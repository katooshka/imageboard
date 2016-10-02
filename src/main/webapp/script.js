const onReferenceMouseIn = function () {
    const selector = '#' + $(this).attr('ref_id');
    $(selector).addClass('selected');
};

const onReferenceMouseOut = function () {
    const selector = '#' + $(this).attr('ref_id');
    $(selector).removeClass('selected');
};

const onPostSubmitButtonClick = function () {
    $('#submit-form').submit();
};

const onThreadSubmitButtonClick = function () {
    $('#submit-form').submit();
};

const openNewThread = function () {
    window.open('http://localhost:8080/thread?id=' + $(this).attr('id'), '_blank');
};

const onBoardsMouseIn = function () {
    $(this).addClass('board-highlighted');
};

const onBoardsMouseOut = function () {
    $(this).removeClass('board-highlighted');
};

const expandPicture = function () {
    const img = $(this);
    const src = img.attr('src');
    $('body').append('<div class="popup">' +
        '<div class="popup_bg"></div>' +
        '<img src="' + src + '" class="popup_img" />' +
        '</div>');
    $('.popup').fadeIn(400);
    $('.popup_bg, .popup_img').click(function () {
        $('.popup').fadeOut(400);
        setTimeout(function () {
            $('.popup').remove();
        }, 400);
    });
};

const revealOrHideNewForm = function () {
    $('#submit-form').slideToggle();
};

const reset = function () {
    event.preventDefault();
    $('.picture-upload').wrap('<form>').closest('form').get(0).reset().unwrap();
};

const fillInDates = function () {
    $('time[timestamp]').each(function () {
        const $this = $(this);
        const millis = parseInt($this.attr('timestamp'));
        const timeText = new Date(millis).toLocaleString();
        $this.text(timeText);
    });
};

let previousPostId = null;

const openLocalSubmitForm = function () {
    const $post = $(this).closest('.post,.op-post');
    const currentPostId = $post.attr('id');
    const $form = $('#inner-post-submit-form');

    if (previousPostId === currentPostId) {
        $form.slideToggle(500, function () {
            $('html,body').animate({scrollTop: $post.offset().top});
        });
    } else {
        $post.after($('.inner-post-submit-form-container'));
        $form.slideDown(500, function () {
            $('html,body').animate({scrollTop: $post.offset().top});
        });

        const $textarea = $('#inner-post-submit-form-text');
        let text = $textarea.val();
        text += '>>' +  $post.attr('post-id') + '\n';
        $textarea.val(text);
    }
    previousPostId = currentPostId;
};

$(document).ready(function () {
    fillInDates();
    $('.postLink').hover(onReferenceMouseIn, onReferenceMouseOut);
    $('#thread-submit').click(onPostSubmitButtonClick);
    $('.submit').click(onThreadSubmitButtonClick);
    $('.thread-button').click(openNewThread);
    $('.board-card').hover(onBoardsMouseIn, onBoardsMouseOut);
    $('div.picture img').click(expandPicture);
    $('.picture-surface img').click(expandPicture);
    $('.new-post-button').click(revealOrHideNewForm);
    $('#reset-file-button').click(reset);
    $('.post-reply-icon').click(openLocalSubmitForm);
});