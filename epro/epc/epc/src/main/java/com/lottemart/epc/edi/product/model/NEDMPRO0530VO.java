package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

import com.lcnjf.util.DateUtil;

/**
 * 
 * @Class Name : NEDMPRO0530VO.java
 * @Description : 상품확장 VO
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.07.31		yun				최초생성
 *               </pre>
 */
public class NEDMPRO0530VO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698408934360336229L;

	private String rnum;		//행번호
	private String prgsSts;		//진행상태코드
	private String prgsStsNm;	//진행상태명
	private String prodCd;		//상품코드
	private String srcmkCd;		//판매코드
	private String prodNm;		//상품명
	private String venCd;		//협력사코드
	private String chanCd;		//채널코드
	private String chanNm;		//채널명
	
	private String pgmId;		//문서번호(프로그램아이디)
	private String extChanCd;	//확장채널코드
	private String newProdPromoFg;	//신상품장려금여부
	private String newProdStDy;		//신상품출시일자
	private String overPromoFg;		//성과초과장려금여부
	private String sNewProdPromoFg;	//(슈퍼) 신상품장려금여부
	private String sNewProdStDy;	//(슈퍼) 신상품출시일자
	private String sOverPromoFg;	//(슈퍼) 성과초과장려금여부
	
	private String norProdPcost;	//마트_원가
	private String norProdCurr;		//마트_원가화폐단위
	private String norProdSalePrc;	//마트_매가
	private String norProdSaleCurr;	//마트_매가화폐단위
	private String prftRate;		//마트_이익률
	private String wnorProdPcost;	//MAXX_원가
	private String wnorProdCurr;	//MAXX_원가화폐단위
	private String wnorProdSalePrc;	//MAXX_매가
	private String wnorProdSaleCurr;//MAXX_매가화폐단위
	private String wprftRate;		//MAXX_이익률
	private String snorProdPcost;	//슈퍼_원가
	private String snorProdCurr;	//슈퍼_원가화폐단위
	private String snorProdSalePrc;	//슈퍼_매가
	private String snorProdSaleCurr;//슈퍼_매가화폐단위
	private String sprftRate;		//슈퍼_이익률
	private String onorProdPcost;	//CFC_원가
	private String onorProdCurr;	//CFC_원가화폐단위
	private String onorProdSalePrc;	//CFC_매가
	private String onorProdSaleCurr;//CFC_매가화폐단위
	private String oprftRate;		//CFC_이익률
	
	private String srchVenCd;	//검색조건_업체코드
	private String srchPrgsSts;	//검색조건_진행상태
	private String srchSrcmkCd;	//검색조건_판매코드
	
	private String[] venCds;	//검색조건_업체코드(내 업체코드 전체)
	private String workId;		//작업아이디
	
	private int page;			//현재페이지
	private int rows;			//페이지별 행
	private int startRowNo;		//조회시작번호
	private int endRowNo;		//조회끝번호
	
	private String regDy;		//등록일자
	private String prodTypFg;	//NB,PB구분
	
	private List<NEDMPRO0530VO> prodArr;	//EPC 문서번호 LIST
	
	private String taxFg;		//과세구분코드
	
	private String[] chanCds;	//채널코드 array
	private String extAbleSts;	//확장가능상태 (체크용)
	private String cfmFg;		//확정상태코드
	
	private String newYn;		//신규추가여부
	
	private String srcmkCdAll;	//전체판매코드
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getPrgsSts() {
		return prgsSts;
	}
	public void setPrgsSts(String prgsSts) {
		this.prgsSts = prgsSts;
	}
	public String getPrgsStsNm() {
		return prgsStsNm;
	}
	public void setPrgsStsNm(String prgsStsNm) {
		this.prgsStsNm = prgsStsNm;
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
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getChanCd() {
		return chanCd;
	}
	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}
	public String getChanNm() {
		return chanNm;
	}
	public void setChanNm(String chanNm) {
		this.chanNm = chanNm;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getExtChanCd() {
		return extChanCd;
	}
	public void setExtChanCd(String extChanCd) {
		this.extChanCd = extChanCd;
	}
	public String getNewProdPromoFg() {
		return newProdPromoFg;
	}
	public void setNewProdPromoFg(String newProdPromoFg) {
		this.newProdPromoFg = newProdPromoFg;
	}
	public String getNewProdStDy() {
		return newProdStDy;
	}
	public void setNewProdStDy(String newProdStDy) {
		this.newProdStDy = newProdStDy;
	}
	public String getNewProdStDyFmt() {
		if(newProdStDy !=null && !"".equals(newProdStDy) && DateUtil.isDate(newProdStDy, "yyyyMMdd")) {
			return DateUtil.string2String(newProdStDy, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	public String getOverPromoFg() {
		return overPromoFg;
	}
	public void setOverPromoFg(String overPromoFg) {
		this.overPromoFg = overPromoFg;
	}
	public String getsNewProdPromoFg() {
		return sNewProdPromoFg;
	}
	public void setsNewProdPromoFg(String sNewProdPromoFg) {
		this.sNewProdPromoFg = sNewProdPromoFg;
	}
	public String getsNewProdStDy() {
		return sNewProdStDy;
	}
	public void setsNewProdStDy(String sNewProdStDy) {
		this.sNewProdStDy = sNewProdStDy;
	}
	public String getsNewProdStDyFmt() {
		if(sNewProdStDy !=null && !"".equals(sNewProdStDy) && DateUtil.isDate(sNewProdStDy, "yyyyMMdd")) {
			return DateUtil.string2String(sNewProdStDy, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	public String getsOverPromoFg() {
		return sOverPromoFg;
	}
	public void setsOverPromoFg(String sOverPromoFg) {
		this.sOverPromoFg = sOverPromoFg;
	}
	public String getNorProdPcost() {
		return norProdPcost;
	}
	public void setNorProdPcost(String norProdPcost) {
		this.norProdPcost = norProdPcost;
	}
	public String getNorProdCurr() {
		return norProdCurr;
	}
	public void setNorProdCurr(String norProdCurr) {
		this.norProdCurr = norProdCurr;
	}
	public String getSrchVenCd() {
		return srchVenCd;
	}
	public void setSrchVenCd(String srchVenCd) {
		this.srchVenCd = srchVenCd;
	}
	public String getSrchPrgsSts() {
		return srchPrgsSts;
	}
	public void setSrchPrgsSts(String srchPrgsSts) {
		this.srchPrgsSts = srchPrgsSts;
	}
	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}
	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}
	public String[] getVenCds() {
		return venCds;
	}
	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStartRowNo() {
		return startRowNo;
	}
	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}
	public int getEndRowNo() {
		return endRowNo;
	}
	public void setEndRowNo(int endRowNo) {
		this.endRowNo = endRowNo;
	}
	public List<NEDMPRO0530VO> getProdArr() {
		return prodArr;
	}
	public void setProdArr(List<NEDMPRO0530VO> prodArr) {
		this.prodArr = prodArr;
	}
	public String getRegDy() {
		return regDy;
	}
	public void setRegDy(String regDy) {
		this.regDy = regDy;
	}
	public String getRegDyFmt() {
		if(regDy !=null && !"".equals(regDy) && DateUtil.isDate(regDy, "yyyyMMdd")) {
			return DateUtil.string2String(regDy, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	public String getProdTypFg() {
		return prodTypFg;
	}
	public void setProdTypFg(String prodTypFg) {
		this.prodTypFg = prodTypFg;
	}
	public String getNorProdSalePrc() {
		return norProdSalePrc;
	}
	public void setNorProdSalePrc(String norProdSalePrc) {
		this.norProdSalePrc = norProdSalePrc;
	}
	public String getNorProdSaleCurr() {
		return norProdSaleCurr;
	}
	public void setNorProdSaleCurr(String norProdSaleCurr) {
		this.norProdSaleCurr = norProdSaleCurr;
	}
	public String getPrftRate() {
		return prftRate;
	}
	public void setPrftRate(String prftRate) {
		this.prftRate = prftRate;
	}
	public String getWnorProdPcost() {
		return wnorProdPcost;
	}
	public void setWnorProdPcost(String wnorProdPcost) {
		this.wnorProdPcost = wnorProdPcost;
	}
	public String getWnorProdCurr() {
		return wnorProdCurr;
	}
	public void setWnorProdCurr(String wnorProdCurr) {
		this.wnorProdCurr = wnorProdCurr;
	}
	public String getWnorProdSalePrc() {
		return wnorProdSalePrc;
	}
	public void setWnorProdSalePrc(String wnorProdSalePrc) {
		this.wnorProdSalePrc = wnorProdSalePrc;
	}
	public String getWnorProdSaleCurr() {
		return wnorProdSaleCurr;
	}
	public void setWnorProdSaleCurr(String wnorProdSaleCurr) {
		this.wnorProdSaleCurr = wnorProdSaleCurr;
	}
	public String getWprftRate() {
		return wprftRate;
	}
	public void setWprftRate(String wprftRate) {
		this.wprftRate = wprftRate;
	}
	public String getSnorProdPcost() {
		return snorProdPcost;
	}
	public void setSnorProdPcost(String snorProdPcost) {
		this.snorProdPcost = snorProdPcost;
	}
	public String getSnorProdCurr() {
		return snorProdCurr;
	}
	public void setSnorProdCurr(String snorProdCurr) {
		this.snorProdCurr = snorProdCurr;
	}
	public String getSnorProdSalePrc() {
		return snorProdSalePrc;
	}
	public void setSnorProdSalePrc(String snorProdSalePrc) {
		this.snorProdSalePrc = snorProdSalePrc;
	}
	public String getSnorProdSaleCurr() {
		return snorProdSaleCurr;
	}
	public void setSnorProdSaleCurr(String snorProdSaleCurr) {
		this.snorProdSaleCurr = snorProdSaleCurr;
	}
	public String getSprftRate() {
		return sprftRate;
	}
	public void setSprftRate(String sprftRate) {
		this.sprftRate = sprftRate;
	}
	public String getOnorProdPcost() {
		return onorProdPcost;
	}
	public void setOnorProdPcost(String onorProdPcost) {
		this.onorProdPcost = onorProdPcost;
	}
	public String getOnorProdCurr() {
		return onorProdCurr;
	}
	public void setOnorProdCurr(String onorProdCurr) {
		this.onorProdCurr = onorProdCurr;
	}
	public String getOnorProdSalePrc() {
		return onorProdSalePrc;
	}
	public void setOnorProdSalePrc(String onorProdSalePrc) {
		this.onorProdSalePrc = onorProdSalePrc;
	}
	public String getOnorProdSaleCurr() {
		return onorProdSaleCurr;
	}
	public void setOnorProdSaleCurr(String onorProdSaleCurr) {
		this.onorProdSaleCurr = onorProdSaleCurr;
	}
	public String getOprftRate() {
		return oprftRate;
	}
	public void setOprftRate(String oprftRate) {
		this.oprftRate = oprftRate;
	}
	public String getTaxFg() {
		return taxFg;
	}
	public void setTaxFg(String taxFg) {
		this.taxFg = taxFg;
	}
	public String[] getChanCds() {
		return chanCds;
	}
	public void setChanCds(String[] chanCds) {
		this.chanCds = chanCds;
	}
	public String getExtAbleSts() {
		return extAbleSts;
	}
	public void setExtAbleSts(String extAbleSts) {
		this.extAbleSts = extAbleSts;
	}
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	public String getNewYn() {
		return newYn;
	}
	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}
	public String getSrcmkCdAll() {
		return srcmkCdAll;
	}
	public void setSrcmkCdAll(String srcmkCdAll) {
		this.srcmkCdAll = srcmkCdAll;
	}
	
}
