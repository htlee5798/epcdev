$(function () {
    $('body').on('touchstart', '.ripple', function (event) {
        var $this = $(this);
        var offset = $this.offset();

        var touches = event.touches[0];

        var posX = touches.pageX - offset.left;
        var posY = touches.pageY - offset.top;

        if ($this.find('.mat-layer').length > 0) {
            return;
        }

        $this.append('<i class="mat-layer"/>');

        setTimeout(function () {
            $this.find('.mat-layer')
                .css({'top': posY, 'left': posX})
                .addClass('active');
        }, 10);
    }).on('transitionend -webkit-transitionend', '.mat-layer', function () {
        $(this).remove();
    });
});

