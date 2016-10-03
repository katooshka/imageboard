var openNewThread = function () {
    window.open('http://localhost:8080/thread?id=' + $(this).attr('id'), '_blank');
};

var expandPicture = function () {
    var img = $(this);
    var src = img.attr('src');
    $('body').append(`
        <div class="popup">
            <div class="popup-bg">
                <img src="${src}" class="popup-img" />
            </div>
        </div>
    `);
    $('.popup').fadeIn(400);
    $('.popup-bg, .popup-img').click(function () {
        $('.popup').fadeOut(400);
        setTimeout(function () {
            $('.popup').remove();
        }, 400);
    });
};

var revealOrHideNewForm = function () {
    $('#submit-form').slideToggle();
};

var reset = function () {
    event.preventDefault();
    $('.picture-upload').wrap('<form>').closest('form').get(0).reset().unwrap();
};

var fillInDates = function () {
    $('time[timestamp]').each(function () {
        var $this = $(this);
        var millis = parseInt($this.attr('timestamp'));
        var timeText = new Date(millis).toLocaleString();
        $this.text(timeText);
    });
};

var previousPostId = null;

var openLocalSubmitForm = function () {
    var $post = $(this).closest('.post,.op-post');
    var currentPostId = $post.attr('id');
    var $form = $('#inner-post-submit-form');

    if (previousPostId === currentPostId) {
        $form.slideToggle(500, function () {
            $('html,body').animate({scrollTop: $post.offset().top});
        });
    } else {
        $post.after($('.inner-post-submit-form-container'));
        $form.slideDown(500, function () {
            $('html,body').animate({scrollTop: $post.offset().top});
        });

        var $textarea = $('#inner-post-submit-form-text');
        var text = $textarea.val();
        text += '>>' +  $post.attr('post-id') + '\r\n';
        $textarea.scrollTop($textarea.prop('scrollHeight') - $textarea.height());
        $textarea.val(text);
    }
    previousPostId = currentPostId;
};

$(document).ready(function () {
    fillInDates();
    $('.thread-button').click(openNewThread);
    $('div.picture img').click(expandPicture);
    $('.picture-surface img').click(expandPicture);
    $('.new-post-button').click(revealOrHideNewForm);
    $('#reset-file-button').click(reset);
    $('.post-reply-icon').click(openLocalSubmitForm);
});