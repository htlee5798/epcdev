
(function($){
	
	WiseGrid = {
			TYPE_TEXT:"t_text",
			TYPE_NUMBER:"t_number",
			TYPE_DATE:"t_date",
			TYPE_COMBO:"t_combo",
			TYPE_CHECKBOX:"t_checkbox",
			TYPE_IMAGETEXT:"t_imagetext",
			TYPE_DEFAULT:"t_default"
		};

	WiseGridColor = {
			RED:'255|0|0',
			BLUE:'0|0|255',
			LIGHTBLUE:'173|216|230',
			GREEN:'0|255|0',
			YELLOW:'255|255|0',
			ORANGE:'255|192|0',
			GRAY:'221|221|221',
			BLACK:'255|255|255',
			WHITE:'0|0|0',
			SUMMARY:'241|241|241',
			TOTAL:'211|211|211'
		};

	WiseGridFormat = {
			ORD_NO:"####-##-##-#######",
			BREG_NO:"###-##-#####",
			SDATE:"####-##-##",
			YM:"####-##",
			PRICE:"#,##"
		};	


	/* 그리드 property 정의 */
	$.setProperty = function(GridObj) {
		
		GridObj.bAbortQueryVisible(true);				//통신중 취소버튼 활성화
		GridObj.bExcelImportAllColumn(true);				//ExcelImport시 t_checkbox, t_combo 타입의 포함 여부 지정
		GridObj.bHDDblClickAction(true);					//t_checkbox 타입 header를 Double 클릭했을때 CheckBox 전체 체크 사용 여부
		GridObj.bHDFontBold(false);						//Header Font의 Bold 적영여부
		GridObj.bHDFontCLine(false);						//Header Font의 Center(Strike) 적용여부
		GridObj.bHDFontItalic(false);					//Header Font의 Italic 적용여부
		GridObj.bHDFontULine(false);						//Header Font의 Underline 적용여부
		GridObj.bHDMoving(true);							//Header 이동여부
		GridObj.bHDSwapping(false);						//Header Swap기능 사용여부 설정
		GridObj.bHDVisible(true);						//Header Visible 설정
		GridObj.bMultiRowMenuVisible(true);				//다중열 Visible 설정
		GridObj.bTooltip(false);							//툴팁 사용여부
		GridObj.bRowSelectorIndex(true);					//Row Selector 영역에 Row Index 표시 여부
		GridObj.bRowSelectorVisible(true);				//RowSelector 숨김설정
		GridObj.bStatusbarVisible(true);					//Statusbar 표시 여부
		GridObj.nAlphaLevel(0);							//투명도 변경
		GridObj.nCellFontSize(10);						//Cell 폰트 사이즈 변경
		//nCellPadding:3,						//Cell의 Padding값 설정
		GridObj.nHDLineSize(15);							//Header 1 라인의 Height Size
		GridObj.nHDFontSize(10);							//Header Font 사이즈 변경
		GridObj.nRowHeight(20);							//Row의 높이 변경
		GridObj.nRowSpacing(0);							//Row의 간격값 설정
		GridObj.nHDLines(1);								//Header의 Line수 설정
		//strActiveRowBgColor("185|221|255");		//Active된 Row 배경색상 설정
		//strActiveRowFgColor("101|101|101");		//Acvive된 Row 폰트색상 설정
		GridObj.strBlockPaste('gridareabase');			//엑셀 붙여넣기
		GridObj.strCellFontName("돋움");					//Cell 폰트 변경
		GridObj.strCellBgColor("255|255|255");			//Cell 배경색상 변경
		GridObj.strCellBorderStyle("solidline");			//Cell 테두리선 모양 변경
		GridObj.strCellFgColor("0|0|0");					//Cell 폰트색상 변경
		GridObj.strGridBgColor("255|255|255");			//Grid 배경색상 변경
		GridObj.strGridBorderColor("222|222|222");		//Grid 테두리선 색상 변경
		GridObj.strGridBorderStyle("solidline");			//Grid 테두리선 모양 변경
		GridObj.strHDBgColor("242|242|242");				//Header 배경색상 설정
		GridObj.strHDBorderStyle("solidline");			//Header 테두리선 모양 변경
		GridObj.strHDFgColor("158|158|158");					//Header 폰트색상 설정
		GridObj.strHDFontName("돋움");					//Header Font 변경
		GridObj.strMouseWheelAction('page');				//마우스 스크롤시 동작
		GridObj.strProgressbarColor("0|126|174");		//PregressBar 색상
		GridObj.strRowSelectorFgColor("101|101|101");	//RowSelector 폰트색상 설정
		GridObj.strRowSizing("fixed");					//사용자가 로우의 사이즈를 변경하는 동작을 설정한다(fixed); free); sychronized); autofree); autofixed)
		GridObj.strSelectedCellBgColor("228|242|255");	//선택된 Cell 배경색상 변경
		GridObj.strSelectedCellFgColor("51|51|51");		//선택된 Cell Font 색상 변경
		GridObj.strStatusbarBgColor("243|243|243");		//StatusBar 배경색상 설정
		GridObj.strStatusbarFgColor("101|101|101");		//StatusBar 폰트색상 설정
	};
	
})(jQuery);




