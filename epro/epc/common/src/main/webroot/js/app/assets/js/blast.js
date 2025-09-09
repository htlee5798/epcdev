import mojs from 'mo-js';

const shape = new mojs.Shape({
    left: 0, top: 0,
    radius: 10,
    isShowEnd: false,
    stroke: 'magenta',
    fill: 'none',
    strokeWidth: {20: 0, easing: 'cubic.out'},
    strokeLinecap: 'round',
    scale: {0: 2},
    duration: 300,
    easing: 'quad.out'
});

shape.el.style['z-index'] = 1;

const shape2 = new mojs.Shape({
    left: 0, top: 0,
    radius: 5,
    isShowEnd: false,
    stroke: 'yellow',
    fill: 'none',
    strokeWidth: {20: 0, easing: 'cubic.out'},
    strokeLinecap: 'round',
    scale: {0: 2},
    duration: 300,
    easing: 'quad.out'
});

shape2.el.style['z-index'] = 1;

export default function (e) {
    shape
        .tune({ x: e.pageX, y: e.pageY })
        .replay();

    shape2
        .tune({ x: e.pageX, y: e.pageY })
        .replay();
}