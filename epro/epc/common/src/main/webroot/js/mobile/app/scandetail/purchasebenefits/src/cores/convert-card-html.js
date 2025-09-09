const convertCardHtml = (cDataFul) => {
    let cInfo = "";

    try {

        let aDataFul = cDataFul.split("</li>");
        for ( let i in aDataFul ) {
            let len = aDataFul[i].length;
            let aData = "";
            let aTitle = "";
            let bInfo = "";
            let idx = 0;
            let bPrice = "";
            let bCard = "";

            if ( len > 0 ) {
                aData = aDataFul[i].split("<br>");

                if ( aData.length > 0) {
                    aTitle = aData[0].split("</p>");
                    bInfo = aData[1] + " " + aData[2];

                    if ( aTitle.length > 0 ) {
                        idx = aTitle[0].indexOf("<em");
                        bPrice = aTitle[0].substring(idx);
                        bCard = aTitle[1];
                    }
                }
                cInfo = cInfo + "<li><span class='cardname'>" + bCard + "</span> " + bPrice + "<br>" + bInfo + "</li>";
            }
        }
        cInfo = "<ul>" + cInfo + "</ul>";

    } catch(e) {}

    return cInfo;
};

export default convertCardHtml;