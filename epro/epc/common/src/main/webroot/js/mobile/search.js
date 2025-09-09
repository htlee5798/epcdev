
//----- 상품개수 변경시 -----
function DQproduct_count(id, num, min, max){
	var cnt = document.getElementById(id).value*1;
	var rcnt;
	if((cnt + (num*1)) > 0) {
		rcnt = cnt + (num*1);
		if(rcnt < min || rcnt > max) {
			alert(fnJsMsg(msg_productOrderMaxQty, max));	//최대 {0}개까지 수량 변경이 가능합니다...
			document.getElementById(id).value = min;
		} else {
			document.getElementById(id).value = cnt + (num*1);
		}
	} else {
		document.getElementById(id).value = 1;
	}
}

