package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.common.util.CommonsMultipartFile;
import com.lottemart.epc.edi.comm.model.PagingVO;

public class ExcelTempUploadVO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2642933415726086661L;
	
	private String seqNo;
	private String excelSysKnd;		// 시스템구분(반품 , 원가변경 , 신상품제안 등등 )
	private String excelWorkKnd;	// 시스템별 업무구분
	private String entpCd;			// 파트너사 코드 
	private String nbPbGbn;			// nbPb 구분
	private String purDept;			// 구매조직(single)
	private String[] purDepts;		// 구매조직
	private String srcmkCd;			// 판매코드
	private String prodCd; 			// 상품코드
	
	//원가변경 엑셀 업로드 validation ----------------------------------------
	private String prStsCnt00;		//최신상태조회_임시저장상태건수
	private String prStsDept00;		//최신상태조회_임시저장상태구매조직명
	private String prStsCnt01;		//최신상태조회_승인대기상태건수
	private String prStsDept01;		//최신상태조회_승인대기상태구매조직명
	private String prStsCnt02;		//최신상태조회_승인상태건수
	private String prStsDept02;		//최신상태조회_승인상태구매조직명
	private String prStsCnt03;		//최신상태조회_반려상태건수
	private String prStsDept03;		//최신상태조회_반려상태구매조직명
	//-------------------------------------------------------------------
	
	/** 엑셀데이터 전송필드 */
	private String data0;
	private String data1; 
	private String data2; 
	private String data3; 
	private String data4; 
	private String data5; 
	private String data6; 
	private String data7; 
	private String data8; 
	private String data9; 
	private String data10; 
	private String data11; 
	private String data12; 
	private String data13; 
	private String data14; 
	private String data15; 
	private String data16; 
	private String data17; 
	private String data18; 
	private String data19; 
	private String data20; 	
	private String data21; 		
	private String data22; 		
	private String data23; 		
	private String data24; 		
	private String data25; 		
	private String data26; 		
	private String data27; 		
	private String data28; 		
	private String data29; 		
	private String data30; 		

	private String regId;			// 등록자(userId로 등록해주세요!!)

	CommonsMultipartFile file = null;
	
	private String[] venCds;		//소속회사리스트
	

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getPrStsCnt00() {
		return prStsCnt00;
	}

	public void setPrStsCnt00(String prStsCnt00) {
		this.prStsCnt00 = prStsCnt00;
	}

	public String getPrStsDept00() {
		return prStsDept00;
	}

	public void setPrStsDept00(String prStsDept00) {
		this.prStsDept00 = prStsDept00;
	}

	public String getPrStsCnt01() {
		return prStsCnt01;
	}

	public void setPrStsCnt01(String prStsCnt01) {
		this.prStsCnt01 = prStsCnt01;
	}

	public String getPrStsDept01() {
		return prStsDept01;
	}

	public void setPrStsDept01(String prStsDept01) {
		this.prStsDept01 = prStsDept01;
	}

	public String getPrStsCnt02() {
		return prStsCnt02;
	}

	public void setPrStsCnt02(String prStsCnt02) {
		this.prStsCnt02 = prStsCnt02;
	}

	public String getPrStsDept02() {
		return prStsDept02;
	}

	public void setPrStsDept02(String prStsDept02) {
		this.prStsDept02 = prStsDept02;
	}

	public String getPrStsCnt03() {
		return prStsCnt03;
	}

	public void setPrStsCnt03(String prStsCnt03) {
		this.prStsCnt03 = prStsCnt03;
	}

	public String getPrStsDept03() {
		return prStsDept03;
	}

	public void setPrStsDept03(String prStsDept03) {
		this.prStsDept03 = prStsDept03;
	}

	public String getNbPbGbn() {
		return nbPbGbn;
	}

	public void setNbPbGbn(String nbPbGbn) {
		this.nbPbGbn = nbPbGbn;
	}
	
	public String getPurDept() {
		return purDept;
	}

	public void setPurDept(String purDept) {
		this.purDept = purDept;
	}

	public String[] getPurDepts() {
		return purDepts;
	}

	public void setPurDepts(String[] purDepts) {
		this.purDepts = purDepts;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getExcelSysKnd() {
		return excelSysKnd;
	}

	public void setExcelSysKnd(String excelSysKnd) {
		this.excelSysKnd = excelSysKnd;
	}

	public String getExcelWorkKnd() {
		return excelWorkKnd;
	}

	public void setExcelWorkKnd(String excelWorkKnd) {
		this.excelWorkKnd = excelWorkKnd;
	}

	public String getEntpCd() {
		return entpCd;
	}

	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}

	public String getData0() {
		return data0;
	}

	public void setData0(String data0) {
		this.data0 = data0;
	}

	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public String getData6() {
		return data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public String getData7() {
		return data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData8() {
		return data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public String getData9() {
		return data9;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	public String getData10() {
		return data10;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}

	public String getData11() {
		return data11;
	}

	public void setData11(String data11) {
		this.data11 = data11;
	}

	public String getData12() {
		return data12;
	}

	public void setData12(String data12) {
		this.data12 = data12;
	}

	public String getData13() {
		return data13;
	}

	public void setData13(String data13) {
		this.data13 = data13;
	}

	public String getData14() {
		return data14;
	}

	public void setData14(String data14) {
		this.data14 = data14;
	}

	public String getData15() {
		return data15;
	}

	public void setData15(String data15) {
		this.data15 = data15;
	}

	public String getData16() {
		return data16;
	}

	public void setData16(String data16) {
		this.data16 = data16;
	}

	public String getData17() {
		return data17;
	}

	public void setData17(String data17) {
		this.data17 = data17;
	}

	public String getData18() {
		return data18;
	}

	public void setData18(String data18) {
		this.data18 = data18;
	}

	public String getData19() {
		return data19;
	}

	public void setData19(String data19) {
		this.data19 = data19;
	}

	public String getData20() {
		return data20;
	}

	public void setData20(String data20) {
		this.data20 = data20;
	}

	public String getData21() {
		return data21;
	}

	public void setData21(String data21) {
		this.data21 = data21;
	}

	public String getData22() {
		return data22;
	}

	public void setData22(String data22) {
		this.data22 = data22;
	}

	public String getData23() {
		return data23;
	}

	public void setData23(String data23) {
		this.data23 = data23;
	}

	public String getData24() {
		return data24;
	}

	public void setData24(String data24) {
		this.data24 = data24;
	}

	public String getData25() {
		return data25;
	}

	public void setData25(String data25) {
		this.data25 = data25;
	}

	public String getData26() {
		return data26;
	}

	public void setData26(String data26) {
		this.data26 = data26;
	}

	public String getData27() {
		return data27;
	}

	public void setData27(String data27) {
		this.data27 = data27;
	}

	public String getData28() {
		return data28;
	}

	public void setData28(String data28) {
		this.data28 = data28;
	}

	public String getData29() {
		return data29;
	}

	public void setData29(String data29) {
		this.data29 = data29;
	}

	public String getData30() {
		return data30;
	}

	public void setData30(String data30) {
		this.data30 = data30;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String[] getVenCds() {
		return venCds;
	}

	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}
	
	
	
	
}
