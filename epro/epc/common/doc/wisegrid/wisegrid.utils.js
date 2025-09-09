(function($){
	$.loadWiseGridActivex = function(objName, width, height, license) {
		var WISEGRID_TAG = "<OBJECT ID='" + objName + "' codebase='/WiseGrid/WiseGrid_v5212.cab#version=5,2,1,2'";
		WISEGRID_TAG = WISEGRID_TAG + " NAME='" + objName + "' WIDTH=" + width + " HEIGHT=" + height + " border=0";
		WISEGRID_TAG = WISEGRID_TAG + " CLASSID='CLSID:E8AA1760-8BE5-4891-B433-C53F7333710F'>";
		WISEGRID_TAG = WISEGRID_TAG + " <PARAM NAME = 'strLicenseKeyList' VALUE='"+license+"'>";
		WISEGRID_TAG = WISEGRID_TAG + "</OBJECT>";
	
		document.write(WISEGRID_TAG);
	};
	
	/**
	 * 그리드 header 생성
	 *
	 * id : 그리드 id 값
	 * header : header정보
	 */
	$.initGrid = function(id, param) {
	
		var grid = $.getGrid(id);
		var checkbox = param.props.checkbox;
		var checkbox_fix = param.props.checkbox_fix;
		var radio = param.props.radio;
		var crud_hidden = param.props.crud_hidden;
		var row_resize = param.props.row_resize;
		var combo_callback = param.props.combo_callback;
		var summary = param.props.summary;
		
		$.setProperty(grid);
		grid.cols = param.header;

		/*
		param.utils = param.utils||{};
		$.each(param.utils, function(name, util) {
			//alert(name);
			param.utils[name] = $.extend(false, WiseGrid.defaultUtils[name], util||{});	//기본util 확장
		});
		
		grid.utils = param.utils;
		*/
		
		if (param.headerGroups){
			grid.bHDMoving(false);
			grid.bHDSwapping(false);
		}
		
		$(param.header).each(function(){
			var cell = this;
			grid.AddHeader(	cell.key	, (cell.empty == false ? cell.name + "*" : cell.name) //col.empty == false : 빈값 허용 안함(필수입력항목은 * 추가)
					, cell.type
					, (cell.length == null? 40 : cell.length)
					, (cell.width == null ? 10 : cell.width)
					, (cell.editable == null ? true : cell.editable));		
		});
		
		//  헤더 그룹을 생성한다. 
		if (param.headerGroups){
			grid.bHDMoving(false);
			grid.bHDSwapping(false);
			grid.nRowSpacing(0);
			grid.nHDLineSize(30);
			//1.그룹정보를 셋팅한다.
			$.each(param.headerGroups, function(index, colgroup){
				grid.AddGroup(colgroup.key, colgroup.name);
				if ( colgroup.members ) {
					$.each(colgroup.members, function(index, member){
						grid.AppendHeader(colgroup.key, member);
					});
				};
			});
			//2.parent 셋팅
			$.each(param.headerGroups, function(index, colgroup){
				if (colgroup.parent) {
					grid.AppendGroup(colgroup.parent, colgroup.key);
				};
			});
		}
		
		grid.BoundHeader();
		
		if(this.summary) {
			grid.bHDMoving(false);
			grid.bHDSwapping(false);
			grid.bRowSelectorVisible(false);
			grid.strRowBorderStyle("none");
			grid.nRowSpacing(0);
			grid.strHDClickAction("select");
		}
		
		
		
		//컬럼별 속성 적용
		$(param.header).each(function(){
			var cell = this;	
			
			grid.SetColCellAlign(cell.key, (cell.align == null ? "center":cell.align));
			grid.SetColCellFontULine(cell.key, cell.underline);
			
			if (cell.fgColor){
				grid.SetColCellFgColor(cell.key, cell.fgColor);
			}
			
			if (cell.bgColor){
				grid.SetColCellBgColor(cell.key, cell.bgColor);
			}

			//편집컬럼 헤더 색상 변경
			if (cell.editable && cell.key != "SELECTED") {
				//grid.SetColHDBgColor(cell.key, '214|209|184');
				grid.SetColHDFgColor(cell.key, '0|0|0');
			}
			
			if (cell.visible) {
				grid.SetColHide(cell.key, false);	
			} else {
				grid.SetColHide(cell.key, true);	//컬럼을 숨긴다.
			}
			
			if (cell.key == "SELECTED") {
				grid.SetColCellSortEnable("SELECTED", false);	//체크박스는 정렬하지 않는다.
				if (checkbox_fix == null || (checkbox_fix != null && checkbox_fix != false)) {
					if (cell.visible) {
						grid.SetColFix('SELECTED');	// 체크박스 컬럼을 고정.
					}
				}
				if ( radio ) {	//checkbox를 radio 버튼처럼 동작
					grid.SetColCellRadio("SELECTED", radio);
				} else {
					grid.SetColHDCheckBoxVisible("SELECTED", true, false);
				}
				if(checkbox) {
					grid.SetColHDCheckBoxValue("SELECTED", true);
				}
			} else if (cell.key == "CRUD") {
				grid.SetColCellSortEnable("CRUD", false);
				grid.SetColCellAlign("CRUD", 'center');			
				grid.SetCRUDMode(cell.key, "추가", "수정", "삭제");
				grid.SetColHide(cell.key, crud_hidden);
			}
			if (cell.type == WiseGrid.TYPE_NUMBER) {
				if (!cell.format){
					grid.SetNumberFormat(cell.key, WiseGridFormat.PRICE);
				}
				else {
					grid.SetNumberFormat(cell.key, cell.format);
				}								
			} else if ( cell.type == WiseGrid.TYPE_IMAGETEXT ) {
				grid.SetImagetextAlign(cell.key, cell.imagealign);	//이미지텍스트의 경우 이미지 정렬
				if (cell.imgurls) {
					$.each(cell.imgurls, function(index, imgurl){
						grid.AddImageList(cell.key, imgurl);
					});
				}
			}
			else if (cell.type == WiseGrid.TYPE_COMBO) {	//combo
				if (cell.options) {	//사용자가 지정한 콤보 사용
					$.each(cell.options, function(code, obj){
						grid.AddComboListValue(cell.key,  obj.text, obj.key);
					});
				}
			} 
			else if (cell.type == WiseGrid.TYPE_DATE) {	//date
				if (cell.format){
					grid.SetDateFormat(cell.key, cell.format);
				} else {
					grid.SetDateFormat(cell.key, "yyyy-MM-dd");
				}
			}
		});
		
		// 그리드 공통기능 추가 (2011.09.02 Sophie)
		// 현재 그리드에 대한 사용자별 화면설정 정보를 조회한다.
		var userGridSet;
		var params = "gridId=" + id;
		ajaxCallSync("post", '/common/grid/baseGridScreenSetInfo.ldpm', params, "json", callback = function(result) {
			if (result != null) {
				if (result.columnSetArrayContent != null && result.columnSetArrayContent != "") {
					//alert("그리드 항목을 기본 설정된 화면으로 변경합니다.\n" + result.columnSetArrayContent);
					var colSetContent = result.columnSetArrayContent.split('&quot;').join('\"');
					userGridSet = eval(colSetContent);
					
					$.each(userGridSet, function(index, col) {
						try {
							grid.SetColHide(col.key, col.hide);
						} catch (e) {}
					});
				}
			}
		});
		
		//  초기 데이터를 그리드에 추가한다. 
		if (param.initialData){
			$.each(param.initialData, function(index, row){
				//alert(index+":"+row);
				grid.AddRow();
				
				grid.SetCellValue( "CRUD",index, ""); //  추가된  Row 를 "추가"로 처리하지 않기 위해 지정
				$.each(row, function(key, value){
					//alert(key+":"+value);
					for(var i=0;i<grid.GetColCount();i++){
											
						
						var colKey = grid.GetColHDKey(i);
						
						if (colKey == key){
							colType = grid.GetColType(key);
							if ( colType == "t_combo") {
								grid.SetComboSelectedHiddenValue( key,index, value);
							}
							else if ( colType == "t_checkbox") {
								grid.SetCellValue( key,index, value ? "1" : "0");
							}
							else {
								grid.SetCellValue( key,index, value);	
							}
						}
					}
					
										
					
				});
			});
		}
		
		grid.attachEvent("ChangeCombo", function(strColumnKey, nRow, vtOldValue, vtNewValue) {	//콤보 변경 시
			try {
				grid.SetCellValue("SELECTED", nRow, "1");
				grid.SetCellBgColor(strColumnKey, nRow, "220|238|203");
			} catch (e) {}	//체크박스 없을경우
			
			//콤보 변경 시 콜백 함수가 선언된 경우 함수 호출
			if(param.event.changeCombo)
				param.event.changeCombo(grid, strColumnKey, nRow, vtOldValue, vtNewValue);
			
		});
		
		grid.attachEvent("ChangeCell", function(strColumnKey, nRow, vtOldValue, vtNewValue) {	//셀 변경 시
			if (strColumnKey != "SELECTED") {
				try {
					grid.SetCellValue("SELECTED", nRow, "1");
					grid.SetCellBgColor(strColumnKey, nRow, "220|238|203");
				} catch (e) {}	//체크박스 없을경우
			} else {
				if (grid.GetCellValue("SELECTED", nRow) == "0") {
					try {
						if (grid.GetCellHiddenValue("CRUD", nRow) == "D") {	//취소선 제거
							for ( var i = 0; i < grid.GetColCount(); i++) {	
								if (grid.GetColHDKey(i) != 'CRUD' && grid.GetColHDKey(i) != 'SELECTED') {
									grid.SetCellFontCLine(grid.GetColHDKey(i), nRow, false);
								}
							}
						}	
						if (grid.GetCellHiddenValue("CRUD", nRow) != "C") {
							grid.CancelCRUDRow(nRow);									
						}
					} catch (e) {}
				}
				
				// 체크박스('SELECTED')인경우 개별적으로 선택되도록.
				if(param.props.checkbox == true){
					if (vtOldValue === "1") {
						grid.SetCellValue("SELECTED", nRow, "0");
					}
					else {
						grid.SetCellValue("SELECTED", nRow, "1");
					}
				}
			}
			
			//셀 변경 시 콜백 함수가 선언된 경우 함수 호출
			if(grid.changeCell)
				grid.changeCell(grid, strColumnKey, nRow, vtOldValue, vtNewValue);
		});
		
		
		
		grid.attachEvent("CellClick", function(strColumnKey,nRow) {	//셀 클릭 시
			//셀 클릭 시  콜백 함수가 선언된 경우 함수 호출
			if(grid.cellClick)
				grid.cellClick(grid, strColumnKey, nRow);
		});
		
		var eventCallback = function(eventName) {
			return function(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9) {
				if (param.event && param.event[eventName]){
					if ( grid === arg1 ){
						param.event[eventName](grid, arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
					}
					else {
						param.event[eventName](grid, arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
					}
				}
			};
		};
		
		var gridEventAttacher = function(eventName){
			grid.attachEvent(eventName, eventCallback("on"+eventName));
		};
					
		gridEventAttacher("ErrorQuery");	// 서버와 통신이 실패했을때
		gridEventAttacher("BlockPasteError");	// 엑셀 붙여넣기 중 에러발생시 이벤트
		gridEventAttacher("BeforeBlockPaste");	// 엑셀 붙여넣기 전 발생 이벤트
		gridEventAttacher("BlockPaste");	// 엑셀 붙여넣기 성공시 발생 이벤트
		gridEventAttacher("ChangeCell");	// Cell이 변경되었을때 발생 이벤트
		gridEventAttacher("ChangeCombo");	// Combo가 변경되었을 경우 발생 이벤트
		gridEventAttacher("ClickHDCheckBox");	//  Header의 CheckBox 클릭시 발생 이벤트
		gridEventAttacher("Collapse");	//  Tree가 접혔을때 발생 이벤트
		gridEventAttacher("Expand");	// Tree가 펼쳐졌을 때 발생 이벤트
		gridEventAttacher("MouseOver");	//  Mouse Over시 발생 이벤트
		gridEventAttacher("MouseOut");	// Mouse Out시 발생 이벤트
		gridEventAttacher("RowScroll");	// Scroll 이동시 발생이벤트
		gridEventAttacher("RowActivate");	//  Row 활성화시 발생이벤트
		gridEventAttacher("BeforeShowUserContextMenu");	//  Context메뉴가 보이기전 발생 이벤트
		gridEventAttacher("UserContextMenuClick");	//  Context메뉴를 클릭했을시 발생 이벤트
		gridEventAttacher("HeaderClick");	// Header 클릭시 발생이벤트
		gridEventAttacher("RegExp");	// 정규표현식이 True를 반환할 경우 발생
		gridEventAttacher("TreeNodeClick");	// TreeNode 클릭시 발생 이벤트
		gridEventAttacher("CellDblClick");	// Cell 더블클릭시 발생 이벤트
		gridEventAttacher("CellRClick");	// Cell Right Click시 발생이벤트
		gridEventAttacher("CellClick");	// Cell 클릭시 발생 이벤트
		gridEventAttacher("AbortQuery");	// 통신 취소시 발생 이벤트
		gridEventAttacher("EndQuery");	// 서버와 통신이 완료 시
		
		
		
		grid.AddRegExp("WISEREGEXP_ISEMPTY", "WISEREGEXP_ISEMPTY", true); // 빈값 체크(주의 : 콤보타입일 경우 HiddenValue가 아니라 Value로 체크함)
		
		// 페이징이 존재하는 경우 초기화
		if(param.paging) {
			$.setPagingInfo(id, param.paging.perPage, param.paging.currentPage, param.paging.form, param.paging.url);
		}
	
		if (param.event && param.event.setupGrid) {
			param.event.gridInitialized(grid);
		}
		
		
		
		if(param.combo) {
			grid.attachEvent("ChangeCombo", function(trColumnKey, nRow, nOldIndex, nNewIndex) {	//셀 클릭 시
				parentKey = grid.GetCellHiddenValue(trColumnKey, nRow);
				//alert("trColumnKey:"+trColumnKey+" nRow:"+nRow+" nOldIndex:"+nOldIndex+" nNewIndex:"+nNewIndex +" hiddenValue:"+parentKey);
				
				if (param.combo[trColumnKey]){
					childHeader = param.combo[trColumnKey];
					//alert(childHeader);
					
					grid.SetComboSelectedIndex(childHeader, nRow,0, grid.GetCellHiddenValue(trColumnKey, nRow));
				}
			});
		}
		
	};

	/**
	 * 그리드 object를 찾는다.
	 *
	 * id : 그리드 id 값
	 */
	$.getGrid = function(id) {
		var grid = null;
		$("#"+id).each(function(){
			grid = this;
		});
		return grid;
	};

	/**
	 * 체크박스 선택 취소
	 *
	 * id : 그리드 id 값
	 */
	$.unChkSelected = function(id) {
		
		var grid = $.getGrid(id);
		for(i = 0 ; i < grid.GetRowCount() ; i++ ){
			if(grid.GetCellValue("SELECTED",i) == "1")				
				grid.setCellValue("SELECTED", i, "0");
		}
	};

	/**
	 * 체크박스 toggle
	 * id : 그리드 id 값
	 * nRow : row number
	 */
	$.toggleSelected = function(id, nRow) {
		
		var grid = $.getGrid(id);
		if(nRow) {
			if(grid.GetCellValue("SELECTED",nRow) == "1")				
				grid.setCellValue("SELECTED", nRow, "0");
			else
				grid.setCellValue("SELECTED", nRow, "1");
		} else {
			for(i = 0 ; i < GridObj.GetRowCount() ; i++ ){
				if(grid.GetCellValue("SELECTED",i) == "1")				
					grid.setCellValue("SELECTED", i, "0");
				else
					grid.setCellValue("SELECTED", i, "1");
			}
		}
	};

	/**
	 * 콤보 박스 셋팅
	 * id : 그리드 id 값
	 * url : 콤보 박스 생성 url
	 */
	$.setComboList = function(id, url){

		var grid = $.getGrid(id);
		grid.DoQuery(url);
	};

	/**
	 * 로우가 체크되었는지 확인.
	 * id : 그리드 id 값
	 */
	$.chkSelected = function(id) {
		
		var chkCount = 0;	
		var grid = $.getGrid(id);
		for(i = 0; i < grid.GetRowCount(); i++) { //그리드 데이터의 로우수를 반환한다. 
			if(grid.GetCellValue("SELECTED", i) == "1") //지정한 셀의 값을 가져온다. 
				chkCount++;
		}
		if(chkCount <= 0) {
			return false;	
		}
		return true;
	};

	/**
	 * Row 삭제
	 * param = {condition:{id:"WiseGrid", nRow:'check', disp:false},
		      doQuery:{url:'/goods/saveGrid.do', rows:'SELECTED', async:true, validation:true}};
	 * id : 그리드 id
	 * nRow|n,'check',true   : n -> 삭제할 row number 
	 *  				   	   'check' -> checked row 삭제
	 *  					   true -> 현재 활성화된 (선택된) row 삭제  
	 * disp|true,false 		 : true -> CRUD 모드에서 그리드 로우 삭제 
	 *		             	   false-> CRUD 모드에서 그리드 로우 삭제 : 바로 삭제
	 */
	$.deleteRow = function(param, msg) {
		
		var id = param.condition.id;
		var nRow = param.condition.nRow;
		var disp = param.condition.disp;
		
		var grid = $.getGrid(id);
		var row = 0;
		var message = "선택된 건을 삭제하시겠습니까?";
		if(msg !=null && msg != "undefined") {
			message = msg;
		}
		if(confirm(message)) {
			
			if(nRow == "check") {
				if($.chkSelected(id)) 
					row++;
			} else if(nRow) {
				row = grid.GetActiveRowIndex();
			} else if(nRow >= 0){
				row = nRow;
			}
			
			if(row <= 0) {
				alert("삭제할 row를 선택해 주세요.");
				return false;
			}
			
			if (nRow != "check") {
				try {
					grid.SetCellValue("SELECTED", row, "1");
				} catch (e) {
				} // 체크박스 없을경우
				$.deleteRowfromGrid(grid, row, disp);
			} else {
				for ( var i = (grid.GetRowCount() - 1); i >= 0; i--) {
					if (grid.GetCellValue("SELECTED", i) == "1") {
						if(disp)
							$.deleteRowfromGrid(grid, i, disp);
						else
							grid.DeleteRow(i, true);
					}
				}
			}
			
			if(!disp && param.doQuery) {
				grid.DoQuery(param.doQuery.url, param.doQuery.rows
						, (param.doQuery.async == null ? true : param.doQuery.async)
						, (param.doQuery.validation == null ? true : param.doQuery.validation));
			} 
			
		} else {
			return false;
		}
	};

	$.deleteRowfromGrid = function(grid, nRow, disp) {

		if (grid.GetCellHiddenValue('CRUD', nRow) != 'C') {
			for ( var i = 0; i < grid.GetColCount(); i++) {
				if (grid.GetColHDKey(i) != 'CRUD' && grid.GetColHDKey(i) != 'SELECTED') {
					grid.SetCellFontCLine(grid.GetColHDKey(i), nRow, true);
				}
			}
		}
		grid.DeleteRow(nRow, disp);
	};

	/**
	 * 하단 합계 Bar
	 *
	 * id : 그리드 id 값
	 */

	$.AddTotalBar = function(id, pin) {
		var defs = {
			text : "총계"
		};
		pin = $.extend(defs, pin || {});
		var grid = $.getGrid(id);
		grid.AddSummaryBar(pin.barkey, pin.text, 'summaryall', 'sum', pin.cols.replace(/ /g, ""));
		// 색상처리
		grid.SetSummaryBarColor(pin.barkey, '0|0|0', '211|211|211');
		return grid;
	};
	
	/**
	 * 행추가
	 *
	 * id : 그리드 id 값
	 * strColumnKey : selected columnKey
	 * nRow : 선택한 row index
	 * vtOldValue : 예전 value
	 * vtNewValue : 변경 된 value
	 */
	$.addRow = function(id, nRow) {
		var grid = $.getGrid(id);
		if (nRow) {
			grid.InsertRow(nRow);
		} else {
			if (grid.GetRowCount() <= 0) {
				grid.InsertRow(-1);
			} else {
				grid.InsertRow(0);
			}
		}

		var active_row = grid.GetActiveRowIndex();
		
		try {
			grid.SetCellValue("SELECTED", active_row, "1");
		} catch (e) {
		} // 체크박스 없을경우
		
		return active_row;
	};

	/**
	 * 선택한 Cell의 hiddenValue 수정
	 *
	 * id : 그리드 id 값
	 * hiddenvalue : hiddenValue
	 * strColumnKey : strColumnKey (combo type 제외)
	 * nRow : row number
	 */
	$.changeCellHiddenValue = function(id, hiddenValue, strColumnKey, nRow) {

		var grid = $.getGrid(id);
		
		if(!nRow) {
			for(var i = 0; i < grid.GetRowCount(); i++) { 
				if(grid.GetCellValue("SELECTED", i) == "1") {
					if(grid.GetColType(strColumnKey) != WiseGrid.TYPE_COMBO)
						grid.SetCellHiddenValue(strColumnKey, i, hiddenValue);
				}
			}
		} else {
			grid.SetCellHiddenValue(strColumnKey, nRow, hiddenValue);
		}
		
		return true;
	};

	/**
	 * 변경 취소
	 * id : 그리드 id		 
	 */
	$.cancelCRUD = function(id) {
		
		var grid = $.getGrid(id);
	
		for(var i = 0; i < grid.GetRowCount(); i++) {
			
			if(grid.GetCellValue('CRUD', i) != 'R') {
				for ( var j = 0; j < grid.GetColCount(); j++) {
					if (grid.GetColHDKey(j) != 'CRUD' && grid.GetColHDKey(j) != 'SELECTED') 
						grid.SetCellFontCLine(grid.GetColHDKey(j), i, false);
				}
			}
		}
		
		grid.CancelCRUD();
	};

	/**
	 * 지정한 셀의 값을 가져온다.
	 * 콤보타입일 경우 hidden value를 리턴한다.
	 * id : 그리드 id		 
	 * strColumnKey:'COLUMN_KEY'	//조회하고자 하는 컬럼키
	 * nRow:n						//조회하고자 하는 row 번호
	 */
	$.getCellValue = function(id, strColumnKey, nRow) {
		var grid = $.getGrid(id);
		var value = "";

		if (grid.GetColType(strColumnKey) == WiseGrid.TYPE_COMBO) { // 콤보일 경우
			value = grid.GetCellHiddenValue(strColumnKey, nRow);
		} else if (strColumnKey == "CRUD") {
			value = grid.GetCellHiddenValue(strColumnKey, nRow);
		} else {
			value = grid.GetCellValue(strColumnKey, nRow);
		}
		return value;
	};
	
	/**
	 * 목록 조회
	 * grid : 그리드 객체 
	 * (서버 사이드에 조회 조건 외에 grid param으로 넘길 데이터가 있는 경우 호출전에 저장)	 
	 * formId : 조회 조건 formId
	 * url : 조회 url
	 */
	$.searchList = function(grid, formId, url) {
		WiseGridHelper.setGridParam(grid, formId);
		if ( grid.pagingInfo){
			grid.SetParam("currentPage", grid.pagingInfo.currentPage);
			grid.SetParam("perPage", grid.pagingInfo.perPage);
		}
		grid.DoQuery(url);
	};
	
	/**
	 * multi combo 셋팅(부모콤보값에 따라 자식 콤보 박스 생성)
	 * grid : 그리드
	 * parent : parent combo strColumnKey
	 * child : child combo strColumnKey
	 * nRow : parent combo의 row number
	 * index : 자식 콤보의 초기 선택 값 인덱스 (-1 : 선택안함)
	 */
	$.setMultiCombo = function(grid, parent, child, nRow, index) {
		
		var parentKey = grid.GetComboSelectedListKey(parent, nRow);  
		var combokey = grid.GetComboHiddenValue(parent,grid.GetComboSelectedIndex(parent,nRow), parentKey); 	 
		grid.SetComboSelectedIndex(child, nRow, index, combokey);
		
	};
	
	/**
	 * grid : 그리드
	 * title : 엑셀 타이틀
	 * 그리드 목록에서 보이는 필드를 엑셀 파일로 export
	 */
	$.excelExport = function(grid, title) {
		
		grid.ClearExcelInfo();
		grid.SetExcelHeader(title, 20, 15, 'center', '0|0|0', '240|240|240');

		var header = new Array;
		for ( var col = 0; col < grid.GetColCount(); col++){
			var key = grid.GetColHDKey(col);
			
			if(!grid.IsColHide(key) && key != 'SELECTED' && key != 'CRUD'){
				header[header.length] = key;
			}
		}
		//grid.SetExcelFooter('Copyright 2011 LDPM', 20, 10, 'right', '100|100|100', '220|220|220');
		grid.ExcelExport('', header.join(','), true, true);
	};
	
	/**
	 * grid : 그리드
	 * strColumnKey : 그리드 키값
	 * value : 셋팅할 그리드 값
	 * 특정 그리드에 해당 값을 셋팅
	 */
	$.setCellValue = function(grid, strColumnKey, value) {
		
		var active_row = grid.GetActiveRowIndex();
		if (grid.GetColType(strColumnKey) == WiseGrid.TYPE_COMBO) { // 콤보일 경우			
			grid.SetComboSelectedHiddenValue(strColumnKey, active_row, value);
		} else {
			grid.SetCellValue(strColumnKey, active_row, value);
		}
		
	};
	
	/**
	 * 선택한 row카운트
	 */
	$.getSelectedRowCount = function(grid) {
		var count = 0;
		
		for(var row=0; row<grid.GetRowCount() ; row++) {
			if(grid.GetCellValue("SELECTED", row) == "1") {
				count++;
			}
		}
		return count;
	};
	
	/**
	 * 선택한 row를 JSON으로 변환
	 * grid : 그리드
	 */
	$.getJsonListSelectedRow = function(grid) {
		
		var json;
		
		var gridCount = $.getSelectedRowCount(grid);
		
		for(var row = 0; row < grid.GetRowCount(); row++) {
			
			if(row == 0 && gridCount > 0)
				json = "[";
			
			if(grid.GetCellValue("SELECTED", row) == "1") {
				
				for ( var col = 0; col < grid.GetColCount(); col++){
					var key = grid.GetColHDKey(col);
					var value = "";
					colType = grid.GetColType(key);
					if ( colType == "t_combo") 
						value = grid.GetCellHiddenValue(key, row);
					else 
						value = grid.GetCellValue(key, row);
					if(col == 0)
						json += "{";
					json += "\""+key+"\":"+"\""+value+"\"";
					if(col < grid.GetColCount() - 1) 
						json += ",";
					if(col == grid.GetColCount() - 1)
						json +="},";
				}				
			}
			
			if(row == (grid.GetRowCount()-1) && gridCount > 0) {
				json = json.substring(0, json.length-1);
				json += "]";
			}
		}
		
		return json;
	};

	/**
	 * JSON Data를 grid row로 변환(2011.08.22 - lhj)
	 */
	$.setGridToJsonStringList = function(gridId, jsonData, chainKey) {
		
		if(jsonData == null || jsonData == undefined || jsonData == '') return;
		
		var grid = $.getGrid(gridId);
		var active_row;
		
		var dataList = $.parseJSON(jsonData);
        $.each(dataList, function (index, data) {
    		active_row = $.addRow(gridId, index-1);
    		var chainValue = '';
        	$.each(data, function (key, value){
        		if(key == chainKey) 	chainValue = value;
        		
    			for ( var col = 0; col < grid.GetColCount(); col++){
    				if(key == grid.GetColHDKey(col)) {
    					colType = grid.GetColType(key);
    					if ( colType == "t_combo") {
    						try{
    							grid.SetComboSelectedHiddenValue( key, active_row, value, key);
    						}catch(e){
    							grid.SetComboSelectedHiddenValue( key, active_row, value, chainValue);
    						}
    					}
    					else if ( colType == "t_checkbox") {
    						grid.SetCellValue( key, active_row, value ? "1" : "0");
    					}
    					else {
    						grid.SetCellValue( key, active_row, value);	
    					}
    				}
    			}	
        	});
        });
	};
	
	/**
	 * 결과 내 검색
	 * (검색어가 비었을 경우에는 전체row가 보이도록 수정-2010.12.03장선화)
	 * grid : 그리드
	 * searchWord : 검색키워드
	 * startColumnKey : 검색 시작 컬럼 키값
	 * endColumnKey : 검색 종료 컬럼 키값
	 * compareOption : 비교조건(equal : 같은값-Default, like : like, regularexpression : 정규표현식)
	 * 
	 */
	$.findResult = function(grid, searchWord, startColumnKey, endColumnKey, compareOption) {
		if (!grid || startColumnKey == null || startColumnKey == '' || endColumnKey == null || endColumnKey == '') {
			return;
		}
		
		if (grid.GetRowCount() == 0) {
			return;
		}
		
		if (compareOption == null || compareOption == '') {
			compareOption = 'like';
		}
		
		if (searchWord == null || searchWord == '') {	// 검색어가 보이지 않을 경우에는 전체 row 보임
			for ( var row = 0; row < grid.GetRowCount(); row++) {
				grid.SetRowHide(row, false);
			}
			return;
		}
		
		var results = grid.FindArea(searchWord, startColumnKey, 0, endColumnKey, grid.GetRowCount() - 1, 'visible', 'value', compareOption);
		var area = results.split(',');

		if (area != null && area.length > 0) {
			for ( var row = 0, srchRow = 1; row < grid.GetRowCount(); row++) {
				if (srchRow < area.length && row == area[srchRow]) {
					grid.SetRowHide(row, false);
					while ((srchRow = srchRow + 2) < area.length) {
						if (area[srchRow] > row) {
							break;
						}
					}
				} else {
					grid.SetRowHide(row, true);
					grid.SetCellValue('SELECTED', row, '0');
					grid.SetCellHiddenValue('SELECTED', row, '');
				}
			}
		}
	};
	
	/**
	 * 삭제할 row카운트
	 */
	$.getDeleteRowCount = function(grid) {
		var count = 0;
		
		for(var row=0; row<grid.GetRowCount() ; row++) {
			if(grid.GetCellHiddenValue('CRUD', row) == "D") {
				count++;
			}
		}
		return count;
	};
	
})(jQuery);	
	

WiseGridHelper = {
	setGridParam : function(GridObj, _id){
		try{
			var f = document.getElementById(_id);
			var inputs = f.getElementsByTagName('input');			
			var selects = f.getElementsByTagName('select');
			var textareas = f.getElementsByTagName("textarea");
			var chkNm = "";
			if (inputs) {
				if (inputs.length  == null ){
					inputs = [inputs];
				}
				for(var i=0; i<inputs.length; i++){
					if(inputs[i].type =='text' || inputs[i].type =='hidden'){
						try {
							GridObj.SetParam(inputs[i].name, inputs[i].value);
						}
						catch (e) {
							alert("text:"+i+" "+e.message);
						}
					} 
					else if(inputs[i].type =='radio'){
						if(inputs[i].checked) {
							try {
								GridObj.SetParam(inputs[i].name, inputs[i].value);
							}
							catch(e) {
								alert("radio:"+i+"  "+e.message);
							}
						}
					}
					else if(inputs[i].type =='checkbox'){
						try {
							if(inputs[i].checked) {		
								if(inputs[i].name == chkNm) {
									GridObj.SetParam(inputs[i].name, GridObj.GetParam(inputs[i].name) + "," + inputs[i].value);
								}
								else {
									chkNm = inputs[i].name;
									GridObj.SetParam(inputs[i].name, inputs[i].value);
								}
							}
						} 
						catch (e) {
							alert("checkbox:" + i + " " + e.message);
						}
					}
				}
			}
			if (selects) {
				if (selects.length  == null) {
					selects = [selects];
				}
				for(var s=0; s<selects.length; s++){
					try {
						GridObj.SetParam(selects[s].name, selects[s].value);
					}
					catch(e) {
						alert("select:" + i + "  " + e.message);
					}	
				}
			}
			if (textareas) {
				if (textareas.length == null ){
					textareas = [textareas];
				}
				for (var t = 0; t < textareas.length; t++) {
					try {
						if (textareas[t].value.indexOf("\n") < 0)
							textareas[t].value += "\n";
						
						GridObj.SetParam(textareas[t].name, textareas[t].value);
					}
					catch (e) {
						alert("textarea:" + i + "  " + e.message);
					}
				}
			}
		}catch(e){alert("setGridParam:"+e.message);}			
	},
	paramToJson : function(){
		try{
			var jsonObj = {};
			var keyCount = GridObj.GetParamCount();
			for(var i=0; i<keyCount; i++){
				var key = GridObj.GetParamKey(i);
				jsonObj[key] = GridObj.GetParam(key);
			}
			return jsonObj;
		}catch(e){alert(e.message);}
	},
	paramToJsonArray : function(){
		try{
			var jsonArr = new Array();
			var keyCount = GridObj.GetParamCount();
			for(var i=0; i<keyCount; i++){
				var key = GridObj.GetParamKey(i);
				jsonArr[i] = {key :key,val:GridObj.GetParam(key)};
			}
			return jsonArr;
		}catch(e){alert(e.message);}
	},
	
	toJSON: function(grid, withParam, asString) {
		if (! withParam ) {
			withParam = true;
		}
		if (! asString ) {
			asString = false;
		}
		var gridListData = {};
		
		for(var row = 0; row < grid.GetRowCount(); row++) {
			
			var holder = {};
			for ( var col = 0; col < grid.GetColCount(); col++){
				var key = grid.GetColHDKey(col);
				var value = grid.GetCellValue(key, row);
				holder[key] = value;
			}
			
			gridListData[row] = holder;
		}
		
		var gridParamData = {};
		if ( withParam == true){
			for(var count = 0; count < grid.GetParamCount(); count++){
				var key = grid.GetParamKey(count);
				var value = grid.GetParam(key);
				gridParamData[key] = value;
			}
		}
		
		var res = {};
		res["data"]  = gridListData;
		res["param"]  = gridParamData;
		
		if (asString == true){
			return JSON.stringify(res);	
		}
		return res;
	},
	selectedCellltoJSON: function(grid) {
		
		var gridListData = {};
		
		var rowIndex = 0;
		var colType;
		
		for(var row = 0; row < grid.GetRowCount(); row++) {
			
			if(grid.GetCellValue("SELECTED", row) == "1") {
				var holder = {};
				for ( var col = 0; col < grid.GetColCount(); col++){
					var key = grid.GetColHDKey(col);
					var value = "";
					colType = grid.GetColType(key);
					if ( colType == "t_combo") {
						value = grid.GetCellHiddenValue(key, row);
					} else {
						value = grid.GetCellValue(key, row);
					}
					holder[key] = value;
				}
				gridListData[rowIndex] = holder;
				rowIndex++;
			}
		}
		return JSON.stringify(gridListData);	
	},
	toForm: function(grid, formId, withParam){
		if (! withParam ) {
			withParam = true;
		}
		form = $("#"+formId);
		for(var row = 0; row < grid.GetRowCount(); row++) {
			for ( var col = 0; col < grid.GetColCount(); col++){
				var key = grid.GetColHDKey(col);
				var value = grid.GetCellValue(key, row);
				$("<input/>").attr("name", key).attr("value", value).hide().appendTo("#"+formId);
			}
		}
		if ( withParam ){
			for(var count = 0; count < grid.GetParamCount(); count++){
				var key = grid.GetParamKey(count);
				var value = grid.GetParam(key);
				$("<input/>").attr("name", key).attr("value", value).hide().appendTo("#"+formId);
			}
		}
	},
	clearGrid: function(grid) {
		var rows =grid.GetRowCount();
		while(rows > 0){
			grid.DeleteRow(0, false);
			rows = rows-1;
		}
	}
};
	/**
	 * 그리드의 내용을 Excel 파일로 저장.
	 * <ldpm:gridFunction> 에서 사용하는 엑셀저장.
	 * 
	 * @param gridId 그리드ID
	 * @param title 엑셀헤더에 사용될 이름
	 * 
	 */
	function clickExcelExportGridContents(gridId, title) {
		if (!title || title==null || title=="") {
			// 다중 그리드 일때를 대비하여 여러개의 이름을 사용
			//title = $("h2.page_title").text();
			title = $("#"+gridId).parent().parent().parent().find("div.headerStyle03>h3").text() ;
			
			if (!title)	{
				alert("엑셀 제목을 찾을 수 없습니다.\ngridFunction 태그에 excelExport의 \ntitle 속성을 직접 지정해 주셔야 합니다.\n관리자에게 요청 바랍니다.");
				return ; 
			}
		}
		var gridObj = $.getGrid(gridId);
		$.excelExport(gridObj, title);
	}

	/**
	 * 그리드의 특정 칼럼을 선택하여 해당 칼럼을 고정
	 * 
	 * @param gridId 그리드ID
	 */
	function clickSetGridColFix(gridId) {
		var gridObj = $.getGrid(gridId);
		var cellinfos = gridObj.GetSelectedCells();
		
		if (gridObj.fixed) {
			// 틀고정이 되어있는 경우 해제
			gridObj.ResetColFix();
			gridObj.fixed = false;
		} else {
			// 틀고정이 안된 경우 적용
			if (cellinfos === null || cellinfos === "") {
				alert("먼저 고정하고자 하시는 컬럼의 셀을 클릭하세요");
				return;
			}
			var colkey = cellinfos.substring(0, cellinfos.indexOf(","));
			gridObj.SetColFix(colkey);
			gridObj.fixed = true;
		}
	}


	getVisibleColHDKeys = function(gridId) {
		var gridObj = $.getGrid(gridId);
		var visibleColKeys = new Array();
		var colCnt = gridObj.GetColCount();
		var j=0;
		for (var i = 0; i < colCnt; i++) {
			var colKey = gridObj.GetColHDVisibleKey(i);
			if ("CRUD" === colKey || "SELECTED" === colKey) {
				continue;
			}
			else {
				visibleColKeys[j++] = colKey;
			}
		}
		return visibleColKeys;
	};

	getVisibleColHDTexts = function(gridId, visibleColHDKeys) {
		var gridObj = $.getGrid(gridId);
		var visibleColTexts = new Array();
		for (var i = 0; i < visibleColHDKeys.length; i++) {
			visibleColTexts[i] = gridObj.GetColHDText(visibleColHDKeys[i]);
		}
		return visibleColTexts;
	};

	setVisibleColumns = function(gridId, hiddenColHDKeys, visibleColHDKeys) {
		var gridObj = $.getGrid(gridId);
		for (var i in hiddenColHDKeys) {
			gridObj.SetColHide(hiddenColHDKeys[i], true);
		}
		for (var i in visibleColHDKeys) {
			gridObj.SetColHide(visibleColHDKeys[i], false);
		}
	};

	/**
	 * 그리드의 모든 칼럼항목이 출력되도록 하는 기능
	 * 
	 * @param gridId 그리드 ID
	 */
	function clickVisibleAllGridCol(gridId) {
		var gridObj = $.getGrid(gridId);
		var colKeys = getVisibleColHDKeys(gridId);
		for (var i in colKeys) {
			gridObj.SetColHide(colKeys[i], false);
		}
	}
	

(function($){
		
	/**
	 * 그리드의 칼럼에 대해 관리자가 체크한 항목만 선택하여 출력하는 기능
	 * 
	 * @param gridId 그리드 ID
	 */
	$.changeGridColVisible = function(gridId) {
		// 그리드 항목선택노출

		$("#btnSelectCol" + gridId).click(function() {
			var gridObj = $.getGrid(gridId);
			var colKeys = getVisibleColHDKeys(gridId);
			var colTexts = getVisibleColHDTexts(gridId, colKeys);
			var html='';
			
			for (var i in colKeys) {
				html += '<tr class="last">';
				if (gridObj.IsColHide(colKeys[i])) {
					html += '<td class="chk"><input type="checkbox" name="btnSelVisibleCol" value="' + colKeys[i] + '" /></td>';
				}
				else {
					html += '<td class="chk"><input type="checkbox" name="btnSelVisibleCol" checked=checked value="' + colKeys[i] + '" /></td>';
				}
				html += '<td class="title">' + colTexts[i] + '</td>';
				html += '</tr>';
			}
			
			//alert(html);
			if ($("#divSelectCol"+gridId).css("display") == "none") {
				$("#divSelectCol_items" + gridId).html(html);
				$("#iframeSelectCol"    + gridId).show();
				$("#divSelectCol"       + gridId).show();
			} else {
				$("#iframeSelectCol"    + gridId).hide();
				$("#divSelectCol"       + gridId).hide();
			}
			
			// 레이어 닫기버튼 클릭 시
			$(".closeLayer").click(function() {
				$("#iframeSelectCol"    + gridId).hide();
				$("#divSelectCol"       + gridId).hide();
			});
		});

		$("#divSelectCol_close"  + gridId).click(function() {
			$("#divSelectCol"    + gridId).hide();
    		$("#iframeSelectCol" + gridId).hide();
		});

		$("#divSelectCol_confirm" + gridId).click(function() {
			var hiddenColKeys = new Array();
			var visibleColKeys = new Array();
			var i = 0;
			var j = 0;

			// 20110530 그리드가 여러개 일때 아래 방법으로 겹침이 발생하지 않도록 수정 
			$("#divSelectCol_items" + gridId).find("input[type=checkbox][name=btnSelVisibleCol]").each(function() {
				if ($(this).attr("checked") === false) {
					hiddenColKeys[i++] = $(this).val();
				}
				else {
					visibleColKeys[j++] = $(this).val();
				}
			});
			//alert(hiddenColKeys);
			//alert(visibleColKeys);
			setVisibleColumns(gridId, hiddenColKeys, visibleColKeys);
			$("#divSelectCol" + gridId).hide();
			$("#iframeSelectCol" + gridId).hide();
		});

	};
	
	/**
	 * 그리드 화면 설정 기능
	 * @param gridId
	 */
	$.setGridScreen = function(gridId) {
		$("#btnSetGridScreen_" + gridId).click(function() {
			
			var gridObj = $.getGrid(gridId);
			var colKeys = getVisibleColHDKeys(gridId);
			var colTexts = getVisibleColHDTexts(gridId, colKeys);
			var html = '';
			
			html += '<table style="text-align:left;">';
			html += '  <tr><td>화면설정추가</td></tr>'
					+ '<tr><td><input type="text" id="saveName">&nbsp;&nbsp;&nbsp;&nbsp;<img id="btnAddScreenSet_' + gridId + '" src="/image/ly/btn_calenderCommit.gif"></td></tr>'
					+ '<tr><td>사용자화면선택</td></tr>'
					+ '<tr>'
					+ '  <td>'
					+ '    <table style="width:100%;" border="1" id="table_gridScreenSet"></table>'
					+ '  </td>'
					+ '</tr>'
				;
			html += '</table>';
			
			if ($("#divSetGridScreen_"+gridId).css("display") == "none") {
				$("#content_" + gridId).html(html);
				$("#divSetGridScreen_" + gridId).show();
			} else {
				$("#divSetGridScreen_" + gridId).hide();
			}
			
			// 레이어 닫기버튼 클릭 시
			$(".closeLayer").click(function() {
				$("#divSetGridScreen_" + gridId).hide();
			});
			
			
			// 선택버튼 클릭
			var clickSelectBtn = function(param) {
				alert("click select btn\n" + param);
				
				// 틀고정을 모두 초기화 한다.
				grid.ResetColFix();
				var colSetContent = eval(param);
				$.each(colSetContent, function(index, col) {
					try {	//컬럼이 삭제되었을 경우를 대비
						grid.SetColIndex(col.key, index);
						grid.SetColHide(col.key, col.hide);	
					} catch (e) {}
				});
				if (grid.props.checkbox) {	//체크박스 있을경우 체크박스 컬럼 디폴트 틀고정
					try {
						grid.SetColFix("SELECTED");				
					} catch (e) {}
				}
			};
			
			var makeTR = function(gridScreenSetNo, saveName, screenUrlPath, colSetContent) {
				var tr = $('<tr/>').append(
					$('<td/>').append(saveName)
				).append(
					$('<td/>').append(
						$('<a/>').append("선택").click(function() {
							// 틀고정 초기화
							gridObj.ResetColFix();
							
							// 선택한 화면으로 그리드 항목을 변경한다.
							colSetContent = colSetContent.split('&quot;').join('\"');
							colSetContent = eval(colSetContent);
							
							$.each(colSetContent, function(index, col) {
								try {
									//컬럼이 삭제되었을 경우를 대비
									gridObj.SetColIndex(col.key, index);
									gridObj.SetColHide(col.key, col.hide);	
								} catch (e) {}
							});
							
							// 기본 설정화면 저장 Ajax 호출 (현재 선택한 화면을 기본 화면으로 저장한다.)
							var params = "gridScreenSetNo=" + gridScreenSetNo
										+ "&gridId=" + gridId
										+ "&screenUrlPath=" + screenUrlPath 
										+ "&columnSetArrayContent= " + colSetContent;
							
							ajaxCall("post", '/common/grid/updateBaseGridScreenSet.ldpm', params, "json", callback = function(result) {
								if (result != null && result == 'SUCCESS') {
									//alert(result);
									alert("그리드 기본 설정화면이 변경되었습니다.");
								} else {
									alert("그리드 기본 설정화면 변경에 실패하였습니다.");
								}
							});
						})
					)
				).append(
					$('<td/>').append(
						$('<a/>').append("삭제").click(function() {
							if (!confirm("삭제하시겠습니까?")) {
								return;
							}

							// Ajax 호출
							var deleteParam = "gridScreenSetNo=" + gridScreenSetNo;
							ajaxCall("post", '/common/grid/deleteGridScreenSet.ldpm', deleteParam, "json", callback = function(data) {
								if (data != null && data.message) {
									alert(data.message);
									tr.remove();
								} else {
									alert("화면설정 삭제 중 오류가 발생했습니다.");
								}
							});
						})
					)
				);
				
				return tr;
			};
			
			// 그리드 화면설정 목록 조회
			var params = "gridId=" + gridId 
						+ "&screenUrlPath=" + _request_uri;
			
			ajaxCall("post", '/common/grid/gridScreenSetList.ldpm', params, "json", callback = function(datas) {
				if (datas != null) {
					var trHtml = '';
					$.each(datas, function(index, data) {
						$("#table_gridScreenSet").append(
							makeTR(data.gridScreenSetNo
									, data.screenSetSaveName
									, data.screenUrlPath
									, data.columnSetArrayContent)
						);
					});
				} else {
					alert("화면설정 목록 조회 실패");
				}
			});
			
			
			$("#btnAddScreenSet_" + gridId).click(function() {
				if (confirm("현재 화면 설정을 저장하시겠습니까?")) {
					
					// 입력값 체크
					var sav_nm = $('#saveName');
					if (sav_nm.val() == "") {
						alert("화면설정명을 입력하세요.");
						sav_nm.focus();
						return false;
					};
					
					// 그리드 컬럼을 json 형태의  문자열로 생성한다.
					var grid = $.getGrid(gridId);
					var json_string = "[";
					var cols = [];
					$.each(grid.cols, function(index, col) {
						cols[grid.GetColHDVisibleIndex(col.key)] = col;
					});
					$.each(cols, function(index, col) {
						if (index != 0) {
							json_string += ", ";
						};
						json_string += "{key:\"" + col.key + "\", hide:" + grid.IsColHide(col.key) + "}";
					});
					json_string += "]";
					
					// Ajax 파라미터 세팅
					var params = "gridId=" + gridId
								+ "&screenUrlPath=" + _request_uri
								+ "&screenSetSaveName=" + sav_nm.val() 
								+ "&columnSetArrayContent= " + json_string
								;
					
					// Ajax 호출
					ajaxCall("post", '/common/grid/createGridScreenSet.ldpm', params, "json", callback = function(result) {
						if (result != null && result.message) {
							alert(result.message);
							$("#table_gridScreenSet").append(
								makeTR(result.data.gridScreenSetNo
										, result.data.screenSetSaveName
										, result.data.screenUrlPath
										, result.data.columnSetArrayContent)
							);
							$("#saveName").val("");
						} else {
							alert("화면설정 저장 중 오류가 발생했습니다.");
						}
					});
				}
			});
			
		});
		
	};

})(jQuery);	

