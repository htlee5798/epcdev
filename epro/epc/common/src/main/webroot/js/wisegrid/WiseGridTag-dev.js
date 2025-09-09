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
  WISEGRID_TAG = WISEGRID_TAG + " <PARAM NAME = 'strLicenseKeyList' VALUE='M+UMiwRSibpQ9Ynz4hRLqJByNH8oRVTD/eg3UO+UnHZ6OeGZ1C6BszyQsMoM3fWVm7TLf0IQE97n2NdwcviTyw=='>";
  
  /*
   	기간제 라이선스: VDI 내에서는 와이즈그리드 홈페이지에 가서 개발라이선스 초기화를 하지 못하니, 기간제 라이선스를 사용하세요.
   */  
  //   개발 라이선스 : 8BA4276E9CF2E8B2BBA5F636F384FD4A
  // 기간제 라이선스 : M+UMiwRSibpQ9Ynz4hRLqJByNH8oRVTD/eg3UO+UnHZ6OeGZ1C6Bs8K5Gu3uE4erOoio/GmDw7jn2NdwcviTyw== : 2014년 08월 27일 ~ 2014년 12월 30일까지
  // 기간제 라이선스 : M+UMiwRSibpQ9Ynz4hRLqJByNH8oRVTD/eg3UO+UnHZ6OeGZ1C6Bsx8+USd4fr8j+yFpgBOPj5bn2NdwcviTyw== : 2015년 01월 01일 ~ 2015년 05월 30일까지
  // 기간제 라이선스 : M+UMiwRSibpQ9Ynz4hRLqJByNH8oRVTD/eg3UO+UnHZ6OeGZ1C6Bs3r2ZHW8LiekTUCxoeTmiRfn2NdwcviTyw== : 2015년 06월 01일 ~ 2015년 12월 30일까지
  // 기간제 라이선스 : M+UMiwRSibpQ9Ynz4hRLqJByNH8oRVTD/eg3UO+UnHZ6OeGZ1C6BszyQsMoM3fWVm7TLf0IQE97n2NdwcviTyw== : 2016년 01월 01일 ~ 2016년 ??월 ??일까지
  
  
 
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
	GridObj.nCellFontSize = 8;

	// Header Font Setting
	GridObj.nHDFontSize = 8;
	GridObj.bHDFontBold = false;

	GridObj.bMultiRowMenuVisible = true;

	// Header Color
	GridObj.strHDBgColor="95|177|206";
	GridObj.strHDFgColor="255|255|255";
	
	//GridObj.strHDBgColor="230|230|230";
	//GridObj.strHDFgColor="85|85|85";

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
	
	//상태바 표시
	GridObj.bStatusbarVisible = false;
	
	/*
    //우클릭메뉴에서 엑셀버튼 제거
	GridObj.bUserContextMenu = true;
	GridObj.bUseDefaultContextMenu = false;
	GridObj.RemoveAllContextMenuItem("MENU_CELL");
	GridObj.AddDefaultContextMenuItem("MENUITEM_CELL_FONTUP");
	GridObj.AddDefaultContextMenuItem("MENUITEM_CELL_FONTDOWN");
	GridObj.AddDefaultContextMenuItem("MENUITEM_CELL_FIND");
	*/
	
}

