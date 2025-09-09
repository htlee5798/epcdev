/**
 * 그리드 초기화 함수
 * @param objName
 * @param width
 * @param height
 */
function initWiseGrid(objName, width, height)
{
	var WISEGRID_TAG = "<OBJECT ID='" + objName + "' codebase='/cab/WiseGridU_v5_3_1_1.cab#version=5,3,1,1'";
	WISEGRID_TAG = WISEGRID_TAG + " NAME='" + objName + "' WIDTH=" + width + " HEIGHT=" + height + " border=0";
	WISEGRID_TAG = WISEGRID_TAG + " CLASSID='CLSID:0CE50171-51F4-4b1e-992B-4ECC8E0BE537'>";
	WISEGRID_TAG = WISEGRID_TAG + " <PARAM NAME = 'strLicenseKeyList' VALUE='87A87D300FE5077905166C316364FA6F,CAEFB9908A24B127E12844BA80408EC3,AF10BA0901CE04216D55295EDC369310,445CB594C7F6D42FE1F7D17D497EF144'>";
	WISEGRID_TAG = WISEGRID_TAG + "</OBJECT>";
	
	document.write(WISEGRID_TAG);
}

/**
 * 그리드 디자인 및 기본설정 함수
 * @param GridObj
 */
function setWiseGridProperty(GridObj) {
	GridObj.strHDFontName = "돋움";
	GridObj.strCellFontName = "돋움";
	// Cell Font Setting
	GridObj.nCellFontSize = 10;

	// Header Font Setting
	GridObj.nHDFontSize = 10;
	GridObj.bHDFontBold = true;

	GridObj.bMultiRowMenuVisible = true;

	// Header Color
	GridObj.strHDBgColor="95|177|206";
	GridObj.strHDFgColor="255|255|255";

	// Cell Color
	GridObj.strGridBgColor="255|255|255";
	GridObj.strCellBgColor="255|255|255";
	GridObj.strCellFgColor="51|51|51";

	// Border Style
	GridObj.strGridBorderColor = "204|204|204";
	GridObj.strGridBorderStyle = "solidline";
	GridObj.strHDBorderStyle = "solidline";
	GridObj.strCellBorderStyle = "solidline";

	// ETC Color
//	GridObj.strActiveRowBgColor="214|228|236";
	GridObj.strSelectedCellBgColor = "241|231|221";
	GridObj.strSelectedCellFgColor = "51|51|51";
	GridObj.strStatusbarBgColor = "243|243|243";
	GridObj.strStatusbarFgColor = "101|101|101";
	GridObj.strProgressbarColor = "0|126|174"; 

	// ETC
	GridObj.bRowSelectorVisible = true;
	GridObj.bHDSwapping = true;
	GridObj.nAlphaLevel = 0;
	GridObj.nRowHeight = 22;
	GridObj.SetHelpInfo();
	GridObj.bAbortQueryVisible = true;
	GridObj.strRowScrollDragAction='syncscreen';

	//로우 셀렉터를 WiseGrid에서 숨긴다.
	GridObj.bRowSelectorVisible = false;

	//사용자가 헤더를 드래그해서 컬럼위치를 이동할수 없다.
	GridObj.bHDSwapping = false;
	
	//스테이터스 사용여부
	GridObj.bStatusbarVisible=false;
}

