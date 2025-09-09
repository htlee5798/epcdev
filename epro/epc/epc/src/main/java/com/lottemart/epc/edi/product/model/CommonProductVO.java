package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lottemart.epc.edi.comm.model.PagingVO;

/**
 * @Class Name : CommonProductVO
 * @Description : 상품관련 공통 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 */
public class CommonProductVO extends PagingVO implements Serializable {

	private static final long serialVersionUID = -2418365301224702974L;
	
	private String teamCd;		//팀코드
	private String teamNm;		//팀명
	private String l1Cd;		//대분류코드
	private String l1Nm;		//대분류명
	private String l2Cd;		//증분류코드
	private String l2Nm;		//중분류명
	private String l3Cd;		//소분류코드
	private String l3Nm;		//소분류명
	private String grpL3Cd;		//그룹분석코드
	private String grpL3Nm;		//그룹분석명
	private String styleCd;		//계절코드
	private String styleNm;		//계절명
	private String yearCd;		//계절년도
	private String yearNm;		//걔절년도
	
	/* 전상법, KC인증 콤보박스 변경시 조회되는 전상법 리스트  */
	private String orgNm2;		//정보그룹명
	private String orgCd2;		//정보그룹코드
	private String orgCd;		//정보컬럼코드
	private String orgNm;		//정보컬럼명
	private String orgDesc;		//정보컬럼 설명
	private String orderSeq;	//정렬순번
	private String val;			//입력값
	
	/* 코리안넷 협력사 리스트 */
	private String venCd;		//협력사코드
	private String venNm;		//협력사명
	
	/*판매코드 리스트*/
	private String rnum;
	private String srcmkCd;		//판매코드
	private String prodCd;		//상품코드
	private String prodNm;		//상품명
	private String prodPatFg;	//신선구분
	private String prodTypFg;	//상품유형
	private String freshOrdFg;	//신선발주여부
	private String freshMeatFg; //신선육류구분
	private String srchEntpCd; //신선육류구분
	private String srchFresh; //신선육류구분
	private String srchOrderPossible; //신선육류구분
	private String srchProdCd; //신선육류구분
	private String srchSellCd; //신선육류구분
	private String srchProdNm;	//상품명
	private String srchTeamCd;	//팀코드
	private String srchL1Cd;	//대분류 코드
	private String srchL2Cd;	//중분류 코드
	private String srchL3Cd;	//소분류 코드
	private String oldL3Cd;		//소분류 코드
	
	//점포코드 리스트
	private String strCd;
	private String strNm;
	private String bmanNo;
	private String comNm;
	
	//공통 코드 리스트
	private String cdNm;
	private String dtlNm;
	private String majorCd;
	private String minorCd;
	private String useYn;
	private String regId;
	private String regDate;
	private String modId;
	private String modDate;
	private String cdDesc;
	
	private String mstComCd;			// 마스터코드 
	private String mstComCdOld;			// 마스터 코드 original
	private String mstComNm;			// 마스터 코드 명
	private String mstComNmOld;			// 마스터 코드 명 original
	private String mstSubDtlCd;			// 마스터코드의 세부코드
	private String mstSubDtlCdOld;		// 마스터코드의 세부코드 original
	private String mstSubDtlNm;			// 마스터코드의 세부코드 명
	
	private String subComCd;			// 세부코드의 마스터 코드
	private String subComNm;			// 세부코드의 마스터 코드 명
	private String subDtlCd;			// 세부코드
	private String subDtlCdOld;			// 세부코드 original
	private String subDtlNm;			// 세부코드 명
	private String subDtlNmOld;			// 세부코드 명 original
	private String subExtent01;			// 세부코드 명 확장
	private String subExtent02;			// 세부코드 명 확장
	private String subExtent03;			// 세부코드 명 확장
	private String subExtent04;			// 세부코드 명 확장
	private int subExtentCnt01;		// 세부코드 숫자 확장 
	private int subExtentCnt02;		// 세부코드 숫자 확장 
	private int subExtentCnt03;		// 세부코드 숫자 확장 
	private int subExtentCnt04;		// 세부코드 숫자 확장 
	private int subSortNo;			// 세부코드 정렬 순서
	private String subUseYn;			//세부코드 사용여부
	
	private String orgCost;	//기존원가
	
	private String srchProdPatFg;	//상품유형구분
	private String srchTaxFg;		//상품과세구분
	private String srchPurDept;		//구매조직
	
	private String[] venCds;		//해당 업체 조회조건
	
	private String purDept;			//구매조직
	
	private List<CommonProductVO> teamL1CdList;	// 팀- 대분류 리스트
	
	private String groupCode;		//팀 코드 
	
	private String workId;	//작성자,수정자
	
	private String freshStdYn;		//신선규격상품여부
	private String srchFreshStdYn;	//조회_신선규격상품여부
	private String dispUnit;		//표시단위
	private String ordUnit;			//기본단위
	private String taxFg;			//면과세구분
	private String taxFgNm;			//면과세구분명
	
	private String orgCostTit;		//상품원가 title 표시용 data
	private String orgCostKr02;		//정상원가-마트
	private String orgCostKr03;		//정상원가-MAXX
	private String orgCostKr04;		//정상원가-슈퍼
	private String orgCostKr09;		//정상원가-오카도
	
	private String srchChgOrgCostYn;	//원가변경가능대상상품
	
	//ECS 수신 담당자 팝업
	private String tenantCd;		//계열사코드
	private String tenantNm;		//계열사명
	private String fullGroupId;		//전체조직코드
	private String fullGroupNm;		//전체조직명
	private String empNo;			//ecs 수신 담당자 사번 
	private String managerNm;		//ecs 수신 담당자 명
	private String srchManagerNm;	//ecs 수신 담당자명 (검색)
	private String srchTeamNm;		//ecs 수신 담당자 부서명 (검색)
	
	private String zzorg;			//거래조직
	private String[] zzorgArr;		//거래조직 array
	private String zzorgNm;			//거래조직명
	
	private String[] purDeptArr;		//구매조직 array
	
	private String[] subExtentList01;	//확장코드1 리스트
	private String[] subExtentList02;	//확장코드2 리스트
	private String notIn;				//확장코드 조회조건 notIn 조건
	
	private CommonProductVO codeSrchInfo;	//코드검색용 vo
	
	private String subNmUseYn;			//확장이름 사용여부
	
	
	private String srchProdMart;		//구매조직별상품조회_마트
	private String srchProdMaxx;		//구매조직별상품조회_MAXX
	private String srchProdSuper;		//구매조직별상품조회_슈퍼
	private String srchProdCfc;			//구매조직별상품조회_CFC
	private String srchProdMtAll;		//구매조직별상품조회_마트전체
	
	private String ecsRecvCompNum;		//ECS 수신 계열사 사업자번호
	private String ecsRecvCompName;		//ECS 수신 계열사명
	
	
	public String getSrchManagerNm() {
		return srchManagerNm;
	}
	public void setSrchManagerNm(String srchManagerNm) {
		this.srchManagerNm = srchManagerNm;
	}
	public String getSrchTeamNm() {
		return srchTeamNm;
	}
	public void setSrchTeamNm(String srchTeamNm) {
		this.srchTeamNm = srchTeamNm;
	}
	public String getTenantCd() {
		return tenantCd;
	}
	public void setTenantCd(String tenantCd) {
		this.tenantCd = tenantCd;
	}
	public String getTenantNm() {
		return tenantNm;
	}
	public void setTenantNm(String tenantNm) {
		this.tenantNm = tenantNm;
	}
	public String getFullGroupId() {
		return fullGroupId;
	}
	public void setFullGroupId(String fullGroupId) {
		this.fullGroupId = fullGroupId;
	}
	public String getFullGroupNm() {
		return fullGroupNm;
	}
	public void setFullGroupNm(String fullGroupNm) {
		this.fullGroupNm = fullGroupNm;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getManagerNm() {
		return managerNm;
	}
	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
	}
	public String getProdTypFg() {
		return prodTypFg;
	}
	public void setProdTypFg(String prodTypFg) {
		this.prodTypFg = prodTypFg;
	}
	public String getMstComNmOld() {
		return mstComNmOld;
	}
	public void setMstComNmOld(String mstComNmOld) {
		this.mstComNmOld = mstComNmOld;
	}
	public String getSubDtlNmOld() {
		return subDtlNmOld;
	}
	public void setSubDtlNmOld(String subDtlNmOld) {
		this.subDtlNmOld = subDtlNmOld;
	}
	public String getMstSubDtlCd() {
		return mstSubDtlCd;
	}
	public void setMstSubDtlCd(String mstSubDtlCd) {
		this.mstSubDtlCd = mstSubDtlCd;
	}
	public String getMstSubDtlCdOld() {
		return mstSubDtlCdOld;
	}
	public void setMstSubDtlCdOld(String mstSubDtlCdOld) {
		this.mstSubDtlCdOld = mstSubDtlCdOld;
	}
	public String getMstSubDtlNm() {
		return mstSubDtlNm;
	}
	public void setMstSubDtlNm(String mstSubDtlNm) {
		this.mstSubDtlNm = mstSubDtlNm;
	}
	public String getSubComCd() {
		return subComCd;
	}
	public void setSubComCd(String subComCd) {
		this.subComCd = subComCd;
	}
	public String getSubComNm() {
		return subComNm;
	}
	public void setSubComNm(String subComNm) {
		this.subComNm = subComNm;
	}
	public String getSubDtlCd() {
		return subDtlCd;
	}
	public void setSubDtlCd(String subDtlCd) {
		this.subDtlCd = subDtlCd;
	}
	public String getSubDtlCdOld() {
		return subDtlCdOld;
	}
	public void setSubDtlCdOld(String subDtlCdOld) {
		this.subDtlCdOld = subDtlCdOld;
	}
	public String getSubDtlNm() {
		return subDtlNm;
	}
	public void setSubDtlNm(String subDtlNm) {
		this.subDtlNm = subDtlNm;
	}
	public String getSubExtent01() {
		return subExtent01;
	}
	public void setSubExtent01(String subExtent01) {
		this.subExtent01 = subExtent01;
	}
	public String getSubExtent02() {
		return subExtent02;
	}
	public void setSubExtent02(String subExtent02) {
		this.subExtent02 = subExtent02;
	}
	public String getSubExtent03() {
		return subExtent03;
	}
	public void setSubExtent03(String subExtent03) {
		this.subExtent03 = subExtent03;
	}
	public String getSubExtent04() {
		return subExtent04;
	}
	public void setSubExtent04(String subExtent04) {
		this.subExtent04 = subExtent04;
	}
	public int getSubExtentCnt01() {
		return subExtentCnt01;
	}
	public void setSubExtentCnt01(int subExtentCnt01) {
		this.subExtentCnt01 = subExtentCnt01;
	}
	public int getSubExtentCnt02() {
		return subExtentCnt02;
	}
	public void setSubExtentCnt02(int subExtentCnt02) {
		this.subExtentCnt02 = subExtentCnt02;
	}
	public int getSubExtentCnt03() {
		return subExtentCnt03;
	}
	public void setSubExtentCnt03(int subExtentCnt03) {
		this.subExtentCnt03 = subExtentCnt03;
	}
	public int getSubExtentCnt04() {
		return subExtentCnt04;
	}
	public void setSubExtentCnt04(int subExtentCnt04) {
		this.subExtentCnt04 = subExtentCnt04;
	}
	public int getSubSortNo() {
		return subSortNo;
	}
	public void setSubSortNo(int subSortNo) {
		this.subSortNo = subSortNo;
	}
	public String getSubUseYn() {
		return subUseYn;
	}
	public void setSubUseYn(String subUseYn) {
		this.subUseYn = subUseYn;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getMstComCd() {
		return mstComCd;
	}
	public void setMstComCd(String mstComCd) {
		this.mstComCd = mstComCd;
	}
	public String getMstComCdOld() {
		return mstComCdOld;
	}
	public void setMstComCdOld(String mstComCdOld) {
		this.mstComCdOld = mstComCdOld;
	}
	public String getMstComNm() {
		return mstComNm;
	}
	public void setMstComNm(String mstComNm) {
		this.mstComNm = mstComNm;
	}
	public String getCdDesc() {
		return cdDesc;
	}
	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public List<CommonProductVO> getTeamL1CdList() {
		return teamL1CdList;
	}
	public void setTeamL1CdList(List<CommonProductVO> teamL1CdList) {
		this.teamL1CdList = teamL1CdList;
	}
	public String getSrchProdNm() {
		return srchProdNm;
	}
	public void setSrchProdNm(String srchProdNm) {
		this.srchProdNm = srchProdNm;
	}
	public String getSrchTeamCd() {
		return srchTeamCd;
	}
	public void setSrchTeamCd(String srchTeamCd) {
		this.srchTeamCd = srchTeamCd;
	}
	public String getSrchL1Cd() {
		return srchL1Cd;
	}
	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}
	public String getSrchL2Cd() {
		return srchL2Cd;
	}
	public void setSrchL2Cd(String srchL2Cd) {
		this.srchL2Cd = srchL2Cd;
	}
	public String getSrchL3Cd() {
		return srchL3Cd;
	}
	public void setSrchL3Cd(String srchL3Cd) {
		this.srchL3Cd = srchL3Cd;
	}
	public String getOldL3Cd() {
		return oldL3Cd;
	}
	public void setOldL3Cd(String oldL3Cd) {
		this.oldL3Cd = oldL3Cd;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMajorCd() {
		return majorCd;
	}
	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}
	public String getMinorCd() {
		return minorCd;
	}
	public void setMinorCd(String minorCd) {
		this.minorCd = minorCd;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	public String getDtlNm() {
		return dtlNm;
	}
	public void setDtlNm(String dtlNm) {
		this.dtlNm = dtlNm;
	}
	public String getSrchEntpCd() {
		return srchEntpCd;
	}
	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}
	public String getSrchFresh() {
		return srchFresh;
	}
	public void setSrchFresh(String srchFresh) {
		this.srchFresh = srchFresh;
	}
	public String getSrchOrderPossible() {
		return srchOrderPossible;
	}
	public void setSrchOrderPossible(String srchOrderPossible) {
		this.srchOrderPossible = srchOrderPossible;
	}
	public String getSrchProdCd() {
		return srchProdCd;
	}
	public void setSrchProdCd(String srchProdCd) {
		this.srchProdCd = srchProdCd;
	}
	public String getSrchSellCd() {
		return srchSellCd;
	}
	public void setSrchSellCd(String srchSellCd) {
		this.srchSellCd = srchSellCd;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getComNm() {
		return comNm;
	}
	public void setComNm(String comNm) {
		this.comNm = comNm;
	}
	public String getFreshMeatFg() {
		return freshMeatFg;
	}
	public void setFreshMeatFg(String freshMeatFg) {
		this.freshMeatFg = freshMeatFg;
	}
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
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getFreshOrdFg() {
		return freshOrdFg;
	}
	public void setFreshOrdFg(String freshOrdFg) {
		this.freshOrdFg = freshOrdFg;
	}
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	public String getTeamNm() {
		return teamNm;
	}
	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	public String getStyleCd() {
		return styleCd;
	}
	public void setStyleCd(String styleCd) {
		this.styleCd = styleCd;
	}
	public String getStyleNm() {
		return styleNm;
	}
	public void setStyleNm(String styleNm) {
		this.styleNm = styleNm;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getL2Nm() {
		return l2Nm;
	}
	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getL3Nm() {
		return l3Nm;
	}
	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}
	public String getGrpL3Cd() {
		return grpL3Cd;
	}
	public void setGrpL3Cd(String grpL3Cd) {
		this.grpL3Cd = grpL3Cd;
	}
	public String getGrpL3Nm() {
		return grpL3Nm;
	}
	public void setGrpL3Nm(String grpL3Nm) {
		this.grpL3Nm = grpL3Nm;
	}	
	public String getOrgNm2() {
		return orgNm2;
	}
	public void setOrgNm2(String orgNm2) {
		this.orgNm2 = orgNm2;
	}
	public String getOrgCd2() {
		return orgCd2;
	}
	public void setOrgCd2(String orgCd2) {
		this.orgCd2 = orgCd2;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getYearCd() {
		return yearCd;
	}
	public void setYearCd(String yearCd) {
		this.yearCd = yearCd;
	}
	public String getYearNm() {
		return yearNm;
	}
	public void setYearNm(String yearNm) {
		this.yearNm = yearNm;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getOrgCost() {
		return orgCost;
	}
	public void setOrgCost(String orgCost) {
		this.orgCost = orgCost;
	}
	public String getSrchProdPatFg() {
		return srchProdPatFg;
	}
	public void setSrchProdPatFg(String srchProdPatFg) {
		this.srchProdPatFg = srchProdPatFg;
	}
	public String getSrchTaxFg() {
		return srchTaxFg;
	}
	public void setSrchTaxFg(String srchTaxFg) {
		this.srchTaxFg = srchTaxFg;
	}
	public String[] getVenCds() {
		return venCds;
	}
	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}
	public String getSrchPurDept() {
		return srchPurDept;
	}
	public void setSrchPurDept(String srchPurDept) {
		this.srchPurDept = srchPurDept;
	}
	public String getPurDept() {
		return purDept;
	}
	public void setPurDept(String purDept) {
		this.purDept = purDept;
	}
	public String getFreshStdYn() {
		return freshStdYn;
	}
	public void setFreshStdYn(String freshStdYn) {
		this.freshStdYn = freshStdYn;
	}
	public String getSrchFreshStdYn() {
		return srchFreshStdYn;
	}
	public void setSrchFreshStdYn(String srchFreshStdYn) {
		this.srchFreshStdYn = srchFreshStdYn;
	}
	public String getDispUnit() {
		return dispUnit;
	}
	public void setDispUnit(String dispUnit) {
		this.dispUnit = dispUnit;
	}
	public String getTaxFg() {
		return taxFg;
	}
	public void setTaxFg(String taxFg) {
		this.taxFg = taxFg;
	}
	public String getTaxFgNm() {
		return taxFgNm;
	}
	public void setTaxFgNm(String taxFgNm) {
		this.taxFgNm = taxFgNm;
	}
	public String getOrgCostTit() {
		return orgCostTit;
	}
	public void setOrgCostTit(String orgCostTit) {
		this.orgCostTit = orgCostTit;
	}
	public String getOrgCostKr02() {
		return orgCostKr02;
	}
	public void setOrgCostKr02(String orgCostKr02) {
		this.orgCostKr02 = orgCostKr02;
	}
	public String getOrgCostKr03() {
		return orgCostKr03;
	}
	public void setOrgCostKr03(String orgCostKr03) {
		this.orgCostKr03 = orgCostKr03;
	}
	public String getOrgCostKr04() {
		return orgCostKr04;
	}
	public void setOrgCostKr04(String orgCostKr04) {
		this.orgCostKr04 = orgCostKr04;
	}
	public String getOrgCostKr09() {
		return orgCostKr09;
	}
	public void setOrgCostKr09(String orgCostKr09) {
		this.orgCostKr09 = orgCostKr09;
	}
	public String getSrchChgOrgCostYn() {
		return srchChgOrgCostYn;
	}
	public void setSrchChgOrgCostYn(String srchChgOrgCostYn) {
		this.srchChgOrgCostYn = srchChgOrgCostYn;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getZzorg() {
		return zzorg;
	}
	public void setZzorg(String zzorg) {
		this.zzorg = zzorg;
	}
	public String[] getZzorgArr() {
		return zzorgArr;
	}
	public void setZzorgArr(String[] zzorgArr) {
		this.zzorgArr = zzorgArr;
	}
	public String getZzorgNm() {
		return zzorgNm;
	}
	public void setZzorgNm(String zzorgNm) {
		this.zzorgNm = zzorgNm;
	}
	public String[] getPurDeptArr() {
		return purDeptArr;
	}
	public void setPurDeptArr(String[] purDeptArr) {
		this.purDeptArr = purDeptArr;
	}
	public String[] getSubExtentList01() {
		return subExtentList01;
	}
	public void setSubExtentList01(String[] subExtentList01) {
		this.subExtentList01 = subExtentList01;
	}
	public String[] getSubExtentList02() {
		return subExtentList02;
	}
	public void setSubExtentList02(String[] subExtentList02) {
		this.subExtentList02 = subExtentList02;
	}
	public String getNotIn() {
		return notIn;
	}
	public void setNotIn(String notIn) {
		this.notIn = notIn;
	}
	public CommonProductVO getCodeSrchInfo() {
		return codeSrchInfo;
	}
	public void setCodeSrchInfo(CommonProductVO codeSrchInfo) {
		this.codeSrchInfo = codeSrchInfo;
	}
	public String getSubNmUseYn() {
		return subNmUseYn;
	}
	public void setSubNmUseYn(String subNmUseYn) {
		this.subNmUseYn = subNmUseYn;
	}
	public String getSrchProdMart() {
		return srchProdMart;
	}
	public void setSrchProdMart(String srchProdMart) {
		this.srchProdMart = srchProdMart;
	}
	public String getSrchProdMaxx() {
		return srchProdMaxx;
	}
	public void setSrchProdMaxx(String srchProdMaxx) {
		this.srchProdMaxx = srchProdMaxx;
	}
	public String getSrchProdSuper() {
		return srchProdSuper;
	}
	public void setSrchProdSuper(String srchProdSuper) {
		this.srchProdSuper = srchProdSuper;
	}
	public String getSrchProdCfc() {
		return srchProdCfc;
	}
	public void setSrchProdCfc(String srchProdCfc) {
		this.srchProdCfc = srchProdCfc;
	}
	public String getSrchProdMtAll() {
		return srchProdMtAll;
	}
	public void setSrchProdMtAll(String srchProdMtAll) {
		this.srchProdMtAll = srchProdMtAll;
	}
	public String getEcsRecvCompNum() {
		return ecsRecvCompNum;
	}
	public void setEcsRecvCompNum(String ecsRecvCompNum) {
		this.ecsRecvCompNum = ecsRecvCompNum;
	}
	public String getEcsRecvCompName() {
		return ecsRecvCompName;
	}
	public void setEcsRecvCompName(String ecsRecvCompName) {
		this.ecsRecvCompName = ecsRecvCompName;
	}
	
	
}
