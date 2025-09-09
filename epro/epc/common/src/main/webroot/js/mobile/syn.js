	/* [1] 동기화 서비스 목록  **************************************************************************/		
	/*********************************************************
	 * 동기화 서비스 글 목록 조회
	 ******************************************************** */
	function fn_synList() {
		var uri = contextPath + "/mobile/syn/selectMobileSyncList.mdo"; 
	
	    $.ajax({
	        type       : "POST",
	        cache      : false,                   
	        url        : uri,
	        data       : {fetchRow:fetchRowIdx, deviceType:deviceType},          
	        dataType   : "json",                 
	        success :function(json) {
	            var synList = eval(json.synList);
	            var html = "";
	            localStorage.clear();
	            var seperator = "|";
	            for(var k=0; k<synList.length; k++) {
	                var data = "S" + seperator + synList[k].sn + seperator + synList[k].syncSj + seperator + synList[k].syncCn + seperator + synList[k].creatDt;
	                localStorage.setItem("localSn" + (synList.length - k), data);
	            }
	
	            fn_showList();
	        }
	    });
	};
	
	/*********************************************************
	 * 동기화 서비스 글 목록 생성
	 ******************************************************** */        
	function fn_showList() {
		localStorage.removeItem("localSn");
	    var dataLength = localStorage.length;
	    var listData = "";
	    var rowData = "";
	    
	    for(var i = dataLength; i > 0; i--) {
	        rowData = localStorage.getItem("localSn" + i).split("|");
	        listData += "<li>";
	        if (rowData[0] == "S") {
	            listData += "<a href=javascript:fn_goSynView('localSn" + i +"')><h3>"+rowData[2]+"</h3><p>"+rowData[4]+"</p></a>";
	        } else if (rowData[0] == "D") {
	            listData += "<a href='#'><font color='red'><h3>"+rowData[2]+"</h3><p>"+rowData[4]+"</p></font></a>";
	        } else if (rowData[0] == "I") {
	           	listData += "<a href=javascript:fn_goSynView('localSn" + i +"')><font color='blue'><h3>"+rowData[2]+"</h3><p>"+rowData[4]+"</p></font></a>";
	        } else if (rowData[0] == "U") {
	          	listData += "<a href=javascript:fn_goSynView('localSn" + i +"')><font color='green'><h3>"+rowData[2]+"</h3><p>"+rowData[4]+"</p></font></a>";                	
	        }
	        
	        listData += "<input type='hidden' id='syncCn' value='"+rowData[3]+"'>";
	        listData += "</li>";            
	    }
	
	    $('#syncList').html(listData).listview('refresh');
	    
	}   
	
	/*********************************************************
	 * 동기화 서비스 더 보기 조회
	 ******************************************************** */			
    function fn_moreFetch() {
	    if(window.navigator.onLine == true) {
	        ++fetchRowIdx;
	        fn_synList();
	    } else {
	        jAlert('오프라인 모드 입니다.', ':: 동기화 서비스 ::', 'a');
	    }
	}
	
	/*********************************************************
	 * 동기화 서비스 글 목록 조회 초기화
	 ******************************************************** */				
	 $('#list').live('pageshow',function(event, ui){
		if(window.navigator.onLine == true) {
			fn_synList();
		} else {
	        fn_showList();
		}
	});
	    
    /*********************************************************
	 * 동기화 서비스 글 상세 조회
	 ******************************************************** */                
	function fn_goSynView(localSn) {
		localStorage.setItem("localSn", localSn);
	    location.href = contextPath + "/mobile/syn/goMobileSync.mdo";              
	}
	        
    /* [2] 동기화 서비스 상세  **************************************************************************/		
    /*********************************************************
	 * 동기화 서비스 상세 조회  
	 ******************************************************** */
    function fn_view() {
    	if(window.navigator.onLine == true) {
    		syncSn = rowData[1];
			var uri = contextPath + "/mobile/syn/selectMobileSync.mdo"; 					
				$.ajax({
					type       : "POST",
					cache      : false,				      
					url        : uri,
					data       : {sn:syncSn},			 
					dataType   : "json",			     
					success :function(json) {
						var resultView = eval(json.syncView);
						$("#syncSj").text(resultView.syncSj);
						$("#syncCn").text(resultView.syncCn);
						$("#creatDt").text(resultView.creatDt);
					} 
				});
    	} else {
    		$("#syncSj").text(rowData[2]);
			$("#syncCn").text(rowData[3]);
			$("#creatDt").text(rowData[4]);
    	}
    };	   	     
	
    /*********************************************************
     * 동기화 서비스 글 삭제
     ******************************************************** */			
    function fn_delete() {
    	jConfirm('', '삭제 하시겠습니까?', 'b', function(r) {
    		if(r == true) {
    			if(window.navigator.onLine == true) {
    				// 1. 온라인
    				location.href = contextPath + "/mobile/syn/deleteMobileSync.mdo?sn="+syncSn
    			} else {
    				// 2. 오프라인
    				var data = "D" + seperator + rowData[1] + seperator + rowData[2] + seperator + rowData[3] + seperator + rowData[4];
					localStorage.setItem(localStorage.getItem("localSn"), data);						
					location.href = contextPath +"/mobile/syn/goMobileSyncList.mdo";
    			}	
    		}
    	});
    };
	  
    /* [3] 동기화 서비스 등록  **************************************************************************/		
    /*********************************************************
     * 동기화 서비스 등록 Validation Check
     ******************************************************** */
    function fn_validation() {
    	var syncSj = $("#syncSj").val();
    	var syncCn = $("#syncCn").val();
		var rtnArr = new Array();
		
		if(syncSj.length == 0) {
			rtnArr[0] = false;
			rtnArr[1] = "제목을 입력하여 주십시오.";
		} else if(syncSj.length > 30) {
			rtnArr[0] = false;
			rtnArr[1] = "제목의 길이를 확인하여 주십시오.";
		} else if(syncCn.length == 0) {
			rtnArr[0] = false;
			rtnArr[1] = "내용을 입력하여 주십시오.";
		} else if(syncCn.length > 100) {		
			rtnArr[0] = false;
			rtnArr[1] = "내용의 길이를 확인하여 주십시오.";
		} else {
			rtnArr[0] = true;
			rtnArr[1] = "";
		}
		return rtnArr;
    }
	
    /*********************************************************
	 * 동기화 서비스 등록
	 ******************************************************** */
    function fn_insert() {
		var rtnArr = fn_validation();
		var uri = contextPath + "/mobile/syn/insertMobileSync.mdo"; 	
		
		if(rtnArr[0] == true) {
	        var syncSj = $("#syncSj").val();
	        var syncCn = $("#syncCn").val();
	        					
		    if(window.navigator.onLine == true) {
				$.ajax({
					type       : "POST",
					cache      : false,				      
					url        : uri,
					data       : {syncSj:syncSj, syncCn:syncCn},			 
					dataType   : "json",			     
					success :function(json) {
						location.href = contextPath + "/mobile/syn/goMobileSyncList.mdo";        
					}
				});
				
			} else {
	            var dataLength = localStorage.length + 1;
	            var data = "I" + seperator + "0" + seperator + syncSj + seperator + syncCn + seperator + getTimeStamp();
				localStorage.setItem("localSn" + dataLength, data);	
	            location.href = contextPath + "/mobile/syn/goMobileSyncList.mdo";    
	            
			}
		    
		} else {
			jAlert(rtnArr[1],'동기화 서비스 등록', 'a');
		}
	};
	
	/*********************************************************
	 * 동기화 서비스 글 등록 취소
	 ******************************************************** */
	function fn_cancel() {
		$("#syncSj").val("");
		$("#syncCn").val("");
	};
	
	/*********************************************************
	 * 동기화 서비스 현재시간 STEP-1 : 오프라인 
	 ******************************************************** */        
	function getTimeStamp() {
	  var d = new Date();
	
	  var s =
	    leadingZeros(d.getFullYear(), 4) + '-' +
	    leadingZeros(d.getMonth() + 1, 2) + '-' +
	    leadingZeros(d.getDate(), 2);
	
	  return s;
	}
	
	/*********************************************************
	 * 동기화 서비스 현재시간 STEP-2 : 오프라인 
	 ******************************************************** */        
	function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();
	
	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
	}	
	
	/* [4] 동기화 서비스 수정  **************************************************************************/
	/*********************************************************
	 * 동기화 서비스 수정 상세 조회  
	 ******************************************************** */
	function fn_modify_view() {
		if(window.navigator.onLine == true) {
			syncSn = rowData[1];
			var uri = contextPath + "/mobile/syn/selectMobileSync.mdo"; 	
			$.ajax({
				type       : "POST",
				cache      : false,				      
				url        : uri,
				data       : {sn:syncSn},			 
				dataType   : "json",			     
				success :function(json) {
	
					var resultView = eval(json.syncView);
					
					$("#syncSj").val(resultView.syncSj);
					$("#syncCn").text(resultView.syncCn);
				} 
			});
		} else {
			$("#syncSj").val(rowData[2]);
			$("#syncCn").text(rowData[3]);
		}
	}	        	        
	
	
	/*********************************************************
	 * 동기화 서비스 수정
	 ******************************************************** */
	function fn_save() {
	
	    var rtnArr = fn_validation();
	    var syncSj = $("#syncSj").val();
	    var syncCn = $("#syncCn").val();
	    var syncSn = rowData[1];
	    
		if(window.navigator.onLine == true) {
			var uri = contextPath + "/mobile/syn/updateMobileSync.mdo"; 
			if(rtnArr[0] == true) {
	
				$.ajax({
					type       : "POST",
					cache      : false,				      
					url        : uri,
					data       : {sn:syncSn, syncSj:syncSj, syncCn:syncCn},			 
					dataType   : "json",			     
					success :function(json) {
						location.href = contextPath + "/mobile/syn/goMobileSyncList.mdo"; 
					}
				});
				
			} else {
				jAlert(rtnArr[1],'동기화 서비스 수정', 'a');
			}
	
		} else {
	
			var syncStatus = "U";
			if (rowData[0] == "I") {
				syncStatus = "I";
			}
			var data = syncStatus + seperator + rowData[1] + seperator + syncSj + seperator + syncCn + seperator + rowData[4];
			localStorage.setItem(localStorage.getItem("localSn"), data);						
			location.href = contextPath + "/mobile/syn/goMobileSyncList.mdo";
		}
	};