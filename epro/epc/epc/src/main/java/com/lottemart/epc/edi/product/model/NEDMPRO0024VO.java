package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Class Name : NEDMPRO0024VO
 * @Description : 상품변형속성  VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.10 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0024VO implements Serializable {
	
	static final long serialVersionUID = 8817461414775383997L;
	
	/** 신규상품코드 */
	private String pgmId;
	/** 소분류코드(Argument) */
	private String l3Cd;
	/** 상품범주(단일상품, 묶음상품) */
	private String prodAttTypFg;
	
	/** 변형번호 */
	private String variant;
	/** 속성그룹코드 */
	private String attCd;
	/** 속성분류값코드 */
	private String attValue;
	/** 업체코드 */
	private String entpCd;
	/** 판매코드 */
	private String sellCd;
	/** 상태구분코드 */
	private String stsDivnCd;
	/** 삭제유무 */
	private String delTf;
	/** 상품이미지아이디 */
	private String prodImgId;
	/** 옵션설명 */
	private String optnDesc;
	/** 재고관리여부 */
	private String stkMgrYn;
	/** 예약재고수량 */
	private String rservStkQty;
	/** 단품코드 */
	private String itemCd;
	/** 등록일자 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일자 */
	private String modDate;
	/** 수정자 */
	private String modId;

	/** 그룹분석속성코드 */
	private String grpCd;	
	/** 속성그룹코드 */
	private String attGrpCd;
	/** 속성상세코드 */
	private String attDetailCd;
	/** 상품범주_소분류 */
	private String prodRange;
	/** 특성프로파일_클래스 */
	private String classCd;
	/** 속성상세코드명 */
	private String attGrpNm;
	/** 속성그룹코드명 */
	private String attDetailNm;
	/** 상품범주_소분류명 */
	private String prodRangeNm;
	/** 특성프로파일클래스명 */
	private String classNm;
	/** 등록일시 */
	private String regDt;
	/** 등록사번 */
	private String regEmpNo;
	/** 변경일시 */
	private String lstChgDt;
	/** 변경사번 */
	private String lstChgEmpNo;
	/** 수정구분 */
	private String chgFg;
	/** 인터페이스일시 */
	private String ifDt;
	
	/** ROW SEQ */
	private String rowSeq;
	/** ROW SPAN COUNT **/
	private String rowSpan;
	
	/** 그룹속성ID */
	private String attId;
	/** 그룹속성명 */
	private String attNm;
	/** 그룹속성값 */
	private String attValId;
	/** 그룹속성값명 */
	private String attValNm;
	/** 입력된 그룹속성값 */
	private String selAttValId;
	
	/** 상품변형속성 ArrayList */
	private ArrayList attInfoAl;
	
	/** 이미지, 상품 확정여부 */
	private String cfmFg;
	/** 이미지 반려 코드?? */
	private String cfmReasonFg;
	/** 상품코드 */
	private String prodCd;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품확정구분 */
	
	
	/** 변형속성의 판매코드  배열 [입력된 판매코드의 수 만큼 들어옴]*/
	private String[] arrSrcmkCd;
	
	
	public String[] getArrSrcmkCd() {
		if (this.arrSrcmkCd != null) {
			String[] ret = new String[arrSrcmkCd.length];
			for (int i = 0; i < arrSrcmkCd.length; i++) { 
				ret[i] = this.arrSrcmkCd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrSrcmkCd(String[] arrSrcmkCd) {
		 if (arrSrcmkCd != null) {
			 this.arrSrcmkCd = new String[arrSrcmkCd.length];			 
			 for (int i = 0; i < arrSrcmkCd.length; ++i) {
				 this.arrSrcmkCd[i] = arrSrcmkCd[i];
			 }
		 } else {
			 this.arrSrcmkCd = null;
		 }

	}
	
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getProdAttTypFg() {
		return prodAttTypFg;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	public String getAttValue() {
		return attValue;
	}
	public void setAttValue(String attValue) {
		this.attValue = attValue;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getStsDivnCd() {
		return stsDivnCd;
	}
	public void setStsDivnCd(String stsDivnCd) {
		this.stsDivnCd = stsDivnCd;
	}
	public String getDelTf() {
		return delTf;
	}
	public void setDelTf(String delTf) {
		this.delTf = delTf;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
	public String getStkMgrYn() {
		return stkMgrYn;
	}
	public void setStkMgrYn(String stkMgrYn) {
		this.stkMgrYn = stkMgrYn;
	}
	public String getRservStkQty() {
		return rservStkQty;
	}
	public void setRservStkQty(String rservStkQty) {
		this.rservStkQty = rservStkQty;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public void setProdAttTypFg(String prodAttTypFg) {
		this.prodAttTypFg = prodAttTypFg;
	}
	public String getAttGrpCd() {
		return attGrpCd;
	}
	public void setAttGrpCd(String attGrpCd) {
		this.attGrpCd = attGrpCd;
	}
	public String getAttDetailCd() {
		return attDetailCd;
	}
	public void setAttDetailCd(String attDetailCd) {
		this.attDetailCd = attDetailCd;
	}
	public String getProdRange() {
		return prodRange;
	}
	public void setProdRange(String prodRange) {
		this.prodRange = prodRange;
	}
	public String getClassCd() {
		return classCd;
	}
	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}
	public String getAttGrpNm() {
		return attGrpNm;
	}
	public void setAttGrpNm(String attGrpNm) {
		this.attGrpNm = attGrpNm;
	}
	public String getAttDetailNm() {
		return attDetailNm;
	}
	public void setAttDetailNm(String attDetailNm) {
		this.attDetailNm = attDetailNm;
	}
	public String getProdRangeNm() {
		return prodRangeNm;
	}
	public void setProdRangeNm(String prodRangeNm) {
		this.prodRangeNm = prodRangeNm;
	}
	public String getClassNm() {
		return classNm;
	}
	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegEmpNo() {
		return regEmpNo;
	}
	public void setRegEmpNo(String regEmpNo) {
		this.regEmpNo = regEmpNo;
	}
	public String getLstChgDt() {
		return lstChgDt;
	}
	public void setLstChgDt(String lstChgDt) {
		this.lstChgDt = lstChgDt;
	}
	public String getLstChgEmpNo() {
		return lstChgEmpNo;
	}
	public void setLstChgEmpNo(String lstChgEmpNo) {
		this.lstChgEmpNo = lstChgEmpNo;
	}
	public String getChgFg() {
		return chgFg;
	}
	public void setChgFg(String chgFg) {
		this.chgFg = chgFg;
	}
	public String getIfDt() {
		return ifDt;
	}
	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}
	public String getRowSeq() {
		return rowSeq;
	}
	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}
	public String getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}
	public ArrayList getAttInfoAl() {
		if (this.attInfoAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < attInfoAl.size(); i++) {
				ret.add(i, this.attInfoAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setAttInfoAl(ArrayList attInfoAl) {
		if (attInfoAl != null) {
			this.attInfoAl = new ArrayList();
			for (int i = 0; i < attInfoAl.size();i++) {
				this.attInfoAl.add(i, attInfoAl.get(i));
			}
		} else {
			this.attInfoAl = null;
		}
	}
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}
	public String getAttNm() {
		return attNm;
	}
	public void setAttNm(String attNm) {
		this.attNm = attNm;
	}
	public String getAttValId() {
		return attValId;
	}
	public void setAttValId(String attValId) {
		this.attValId = attValId;
	}
	public String getAttValNm() {
		return attValNm;
	}
	public void setAttValNm(String attValNm) {
		this.attValNm = attValNm;
	}
	public String getSelAttValId() {
		return selAttValId;
	}
	public void setSelAttValId(String selAttValId) {
		this.selAttValId = selAttValId;
	}
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	public String getCfmReasonFg() {
		return cfmReasonFg;
	}
	public void setCfmReasonFg(String cfmReasonFg) {
		this.cfmReasonFg = cfmReasonFg;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	
}
