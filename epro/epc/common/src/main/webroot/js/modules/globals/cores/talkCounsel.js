/**
 * 
 */
define(function() {
	function _talkCounsel() {
		if (confirm("톡 상담을 시작하시겠습니까?")) {
			var newWindow = window.open("/mymart/popup/selectMyTalkCommunication.do", "_TALK_COUNSEL", "width=900; height=720; scrollbars=yes; resizable=yes");
		}
	}
	
	return _talkCounsel;
});