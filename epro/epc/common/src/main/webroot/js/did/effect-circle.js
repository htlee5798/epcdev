
var OPTS = {
    fill: 'none',
    radius: 30,
    strokeWidth: {25: 0},
    scale: {0: 1},
    angle: {'rand(-35, -70)': 0},
    duration: 500,
    left: 0, top: 0,
    easing: 'quad.out'
};

var circle1 = new mojs.Shape($.extend(true, {}, OPTS, {
    stroke: 'red',
    opacity : 0.3,
    isShowEnd : false
}));

var circle2 = new mojs.Shape($.extend(true, {}, OPTS, {
    radius : 25,
    stroke: 'red',
    opacity : 0.5,
    isShowEnd : false
}));

var circle3 = new mojs.Shape($.extend(true, {}, OPTS, {
    radius : 20,
    stroke: 'red',
    opacity : 0.7,
    isShowEnd : false
}));

circle1.el.style.zIndex = 55;

document.addEventListener('click', function (e) {
    if(!e.target.classList.contains('ignore-circle')) {
        circle1
            .tune({ x: e.pageX, y: e.pageY  })
            .replay();

        circle2
            .tune({ x: e.pageX, y: e.pageY  })
            .replay();

        circle3
            .tune({ x: e.pageX, y: e.pageY  })
            .replay();
    }
});