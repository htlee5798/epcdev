self.onmessage = function (e) {

    switch (e.data.queryMethod) {
        case 'removeClassLinked' :
            postMessage('removeClassLinked');
            break;
    }
};