const getItemImagePath = (obj) => {
    "use strict";
    if(!obj || !obj.mdSrcmkCd) {
        return '';
    }

    switch (obj.sizeCode) {
        case '400' :
            obj.sizeCode = '500';
            break;
        case '220' :
            obj.sizeCode = '250';
            break;
        case '154' :
            obj.sizeCode = '160';
            break;
        case '100' :
            obj.sizeCode = '100';
            break;
        case '90' :
            obj.sizeCode = '100';
            break;
    }

    let dirName = obj.mdSrcmkCd.substring(0, 5).trim();

    return obj.path + '/' + dirName + '/' + obj.mdSrcmkCd + '_' + obj.seq + '_' + obj.sizeCode + (obj.fileType || '.jpg');
};

export default getItemImagePath;