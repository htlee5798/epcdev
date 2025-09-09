/**
 * WiseGrid 파일경로 및 라이센스 키
 */
WiseGridConfig = {
	path:'/WiseGrid/WiseGrid_v5_3_1_1.cab',
	version:'5,3,1,1',
	classid:'0CE50171-51F4-4b1e-992B-4ECC8E0BE537',
	licenseKey : '87A87D300FE5077905166C316364FA6F,CAEFB9908A24B127E12844BA80408EC3,AF10BA0901CE04216D55295EDC369310,445CB594C7F6D42FE1F7D17D497EF144'
}

/**
 * 기본 Property.
 */
defaultProperty = {
	strGridBgColor:"255|255|255",			//Grid 배경색상 변경
	strGridBorderColor:"204|204|204",		//Grid 테두리선 색상 변경
	strGridBorderStyle:"solidline",			//Grid 테두리선 모양 변경
	strHDFontName : "돋움",					//Header Font 변경
	nHDFontSize : 10,						//Header Font 사이즈 변경
	bHDFontBold : false,					//Header Font의 Bold 적영여부
	bHDFontCLine : false,					//Header Font의 Center(Strike) 적용여부
	bHDFontItalic : false,					//Header Font의 Italic 적용여부
	bHDFontULine : false,					//Header Font의 Underline 적용여부
	bHDMoving : true,						//Header 이동여부
	bHDSwapping : false,					//Header Swap기능 사용여부 설정
	bHDVisible : true,						//Header Visible 설정
	nHDLines:1,								//Header의 Line수 설정
	strHDBgColor:"242|242|242",				//Header 배경색상 설정
	strHDFgColor:"90|90|90",				//Header 폰트색상 설정
	strHDBorderStyle:"raisedsoft",			//Header 테두리선 모양 변경
	strCellFontName : "돋움",				//Cell 폰트 변경
	nCellFontSize : 10,						//Cell 폰트 사이즈 변경
	nCellPadding : 0,						//Cell의 Padding값 설정
	strCellBgColor:"255|255|255",			//Cell 배경색상 변경
	strCellFgColor:"90|90|90",				//Cell 폰트색상 변경
	strCellBorderStyle:"solidline",			//Cell 테두리선 모양 변경
	strSelectedCellBgColor:"241|231|221",	//선택된 Cell 배경색상 변경
	strSelectedCellFgColor:"51|51|51",		//선택된 Cell Font 색상 변경
	strStatusbarBgColor:"243|243|243",		//StatusBar 배경색상 설정
	strStatusbarFgColor:"101|101|101",		//StatusBar 폰트색상 설정
	strProgressbarColor:"0|126|174",		//PregressBar 색상
	strActiveRowBgColor:"230|255|255",		//Active된 Row 배경색상 설정
	strActiveRowFgColor:"101|101|101",		//Acvive된 Row 폰트색상 설정
	strRowSelectorFgColor:"101|101|101",	//RowSelector 폰트색상 설정
	bRowSelectorVisible:true,				//RowSelector 숨김설정
	nAlphaLevel:0,							//투명도 변경
	nRowHeight:22,							//Row의 높이 변경
	nRowSpacing:0,							//Row의 간격값 설정
	bAbortQueryVisible:true,				//통신중 취소버튼 활성화
	strBlockPaste:'gridareabase',			//엑셀 붙여넣기
	bMultiRowMenuVisible : true,			//다중열 Visible 설정
	bTooltip:false							//툴팁 사용여부
}
