package com.lottemart.epc.edi.comm.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

@Repository(value="bosOpenApiDao")
public class BosOpenApiDao extends AbstractDAO {
	
	/**
	 * BOS API 구분 코드 조회
	 * @param ifCd
	 * @return String
	 * @throws Exception
	 */
	public String selectBosApiUrl(String ifCd) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("BosOpenAPiQuery.selectBosApiUrl", ifCd);
	}	
	
	/**
	 * 전상법템플릿 마스터 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpProdAddInfoMst() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpProdAddInfoMst");
	}
	
	/**
	 * 전상법템플릿 마스터 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpProdAddInfoMst(Map<String,Object> paramMap) throws Exception {
//		return (int) getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpProdAddInfoMst", paramMap);
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpProdAddInfoMst", paramMap);
	}
	
	/**
	 * 전상법템플릿 마스터 Temp To Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTprProdAddInfoMst(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTprProdAddInfoMst", paramMap);
	}
	
	/**
	 * 전상법템플릿 상세 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpProdAddInfoDet() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpProdAddInfoDet");
	}
	
	/**
	 * 전상법템플릿 상세 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpProdAddInfoDet(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpProdAddInfoDet", paramMap);
	}
	
	/**
	 * 전상법템플릿 상세 Temp To Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTprProdAddInfoDet(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTprProdAddInfoDet", paramMap);
	}
	
	
	/**
	 * KC인증품목 마스터 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpProdCertInfoMst() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpProdCertInfoMst");
	}
	
	/**
	 * KC인증품목 마스터 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpProdCertInfoMst(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpProdCertInfoMst", paramMap);
	}
	
	/**
	 * KC인증품목 마스터 Temp To Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTprProdCertInfoMst(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTprProdCertInfoMst", paramMap);
	}
	
	/**
	 * KC인증품목 상세 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpProdCertInfoDet() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpProdCertInfoDet");
	}
	
	/**
	 * KC인증품목 상세 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpProdCertInfoDet(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpProdCertInfoDet", paramMap);
	}
	
	/**
	 * KC인증품목 상세 Temp To Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTprProdCertInfoDet(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTprProdCertInfoDet", paramMap);
	}
	
	/**
	 * EC표준카테고리 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpStdCategory() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpStdCategory");
	}
	
	/**
	 * EC표준카테고리 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpStdCategory(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpStdCategory", paramMap);
	}
	
	/**
	 * EC표준카테고리 Temp To Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTecStdCategory(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecStdCategory", paramMap);
	}
	
	/**
	 * EC전시카테고리 임시테이블 데이터 삭제
	 * @return deleteTmpProdAddInfoMst
	 * @throws Exception
	 */
	public int deleteTmpDispCategory() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpDispCategory");
	}
	
	/**
	 * EC전시카테고리 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpDispCategory(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpDispCategory", paramMap);
	}
	
	/**
	 * EC전시카테고리 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecDispCategory(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecDispCategory", paramMap);
	}
	
	/**
	 * EC 표준/전시 카테고리 맵핑 임시테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpStdDispCategoryMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpStdDispCategoryMapping");
	}
	
	/**
	 * EC 표준/전시 카테고리 맵핑 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpStdDispCategoryMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpStdDispCategoryMapping", paramMap);
	}
	
	/**
	 * EC 표준/전시 카테고리 맵핑 Temp to Real 이관
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecStdDispCategoryMapping(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecStdDispCategoryMapping", paramMap);
	}

	
	/**
	 * EC 표준/마트 카테고리 맵핑 임시테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpStdCatCategoryMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpStdCatCategoryMapping");
	}
	
	/**
	 * EC 표준/마트 카테고리 맵핑 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpStdCatCategoryMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpStdCatCategoryMapping", paramMap);
	}
	
	/**
	 * EC 표준/마트 카테고리 맵핑 Temp to Real 이관
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecStdCatCategoryMapping(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecStdCatCategoryMapping", paramMap);
	}
	
	
	
	
	/**
	 * EC 표준카테고리 / EC 상품속성 매핑정보 임시테이블 데이터 삭제
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpAttrCatCategoryMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpAttrCatCategoryMapping");
	}
	
	/**
	 * EC 표준카테고리 / EC 상품속성 매핑정보 임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpAttrCatCategoryMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpAttrCatCategoryMapping", paramMap);
	}
	
	/**
	 * EC 표준카테고리 / EC 상품속성 매핑 Temp to Real 이관
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecAttrCatCategoryMapping(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecAttrCatCategoryMapping", paramMap);
	}
	
	/**
	 * EC 상품속성 코드(마스터) 임시테이블 데이터  삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpAttrCd() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpAttrCd");
	}
	
	/**
	 * EC 상품 속성 코드 (마스터) 임시테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpAttrCd(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpAttrCd", paramMap);
	}
	
	/**
	 * EC 상품 속성 코드 (마스터) Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecAttrCd(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecAttrCd", paramMap);
	}

	/**
	 * EC 상품속성 코드(아이템) 임시테이블 데이터  삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpAttrVal() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpAttrVal");
	}
	
	/**
	 * EC 상품 속성 코드 (아이템) 임시테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpAttrVal(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpAttrVal", paramMap);
	}
	
	/**
	 * EC 상품 속성 코드 (아이템) Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTecAttrVal(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTecAttrVal", paramMap);
	}

	/**
	 * 온라인 공통 코드 임시 테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpTetCode() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTetCode");
	}
	
	/**
	 * 온라인 공통 코드 임시테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTetCode(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTetCode", paramMap);
	}
	
	/**
	 * 온라인 공통 코드 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTetCode(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTetCode", paramMap);
	}

	/**
	 * 온라인 전시 카테고리 임시테이블 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpCategory() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpCategory");
	}
	
	/**
	 * 온라인 전시 카테고리 임시테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpCategory(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpCategory", paramMap);
	}
	
	/**
	 * 온라인 전시 카테고리 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeCategory(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeCategory", paramMap);
	}
	
	/**
	 * 마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 임시테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpCategoryMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpCategoryMapping");
	}
	
	/**
	 * 마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 임시테이블 데이터 저장  
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpCategoryMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpCategoryMapping", paramMap);
	}
	
	/**
	 * 마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 Temp to Real 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeCategoryMapping(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeCategoryMapping", paramMap);
	}
	
	
	/**
	 * 팀 카테고리 맵핑정보 임시 테이블 데이터 삭제 
	 * @param paramMap
	 * @throws Exception
	 */
	public int deleteTmpL1Cd() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpL1Cd");
	}
	
	/**
	 * 팀 카테고리 맵핑정보 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpL1Cd(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpL1Cd", paramMap);
	}
	
	/**
	 * 팀 카테고리 맵핑정보 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeL1Cd(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeL1Cd", paramMap);
	}
	
	
	
	/**
	 * 신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 삭제
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpOspCatProdMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpOspCatProdMapping");
	}
	
	/**
	 * 신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 저장
	 * @return
	 * @throws Exception
	 */
	public void insertTmpOspCatProdMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpOspCatProdMapping", paramMap);
	}
	
	/**
	 * 신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 Temp to Real 이관 
	 * @return
	 * @throws Exception
	 */
	public int mergeOspCatProdMapping(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeOspCatProdMapping", paramMap);
	}
	
	/**
	 * 오카도 전시 카테고리 임시 테이블 데이터 삭제 
	 * @param paramMap
	 * @throws Exception
	 */
	public int deleteTmpPdDcat() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpPdDcat");
	}
	
	/**
	 * 오카도 전시 카테고리 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpPdDcat(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpPdDcat", paramMap);
	}
	
	/**
	 * 오카도 전시 카테고리 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergePdDcat(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergePdDcat", paramMap);
	}
	
	
	
	/**
	 * 오카도 전시 카테고리 / 마트 소분류 코드 매핑정보  임시테이블 데이터 삭제
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpPdDcatScatMpng() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpPdDcatScatMpng");
	}
	
	/**
	 * 오카도 전시 카테고리 / 마트 소분류 코드 매핑정보  임시테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpPdDcatScatMpng(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpPdDcatScatMpng", paramMap);
	}
		
	/**
	 * 오카도 전시 카테고리 / 마트 소분류 코드 매핑정보  Temp to Real 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergePdDcatScatMpng(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergePdDcatScatMpng", paramMap);
	}

	
	
	/**
	 * 브랜드 임시 테이블 데이터 삭제 
	 * @param paramMap
	 * @throws Exception
	 */
	public int deleteTmpTprBrand() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTprBrand");
	}
	
	/**
	 * 브랜드 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTprBrand(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTprBrand", paramMap);
	}
	/**
	 * 브랜드 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTprBrand(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTprBrand", paramMap);
	}
	
	
	
	/**
	 * EC 패션 속성값 페이지 전시 유무 전송 임시 테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpEcAttDisplay() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpEcAttDisplay");
	}
	
	/**
	 * EC 패션 속성값 페이지 전시 유무 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpEcAttDisplay(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpEcAttDisplay", paramMap);
	}
	
	/**
	 * EC 패션 속성값 페이지 전시 유무 전송 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeEcAttDisplay(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeEcAttDisplay", paramMap);
	}
	
	
	/**
	 * EDI 코드 임시 테이블 데이터 삭제 
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpTpcCode() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTpcCode");
	}
	
	/**
	 * EDI 코드 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTpcCode(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTpcCode", paramMap);
	}
	
	/**
	 * EDI 코드 Temp to Real 이관 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int mergeTpcCode(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTpcCode", paramMap);
	}
	
	/**
	 * 영양성분 마스터 임시 테이블 데이터 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteTmpTpcNutMst() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTpcNutMst");
	}
	
	/**
	 * 영양성분 마스터 임시 테이블 데이터 저장
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTpcNutMst(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTpcNutMst", paramMap);
	}
	
	/**
	 * 영양성분 마스터 Temp to Real 이관 
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTpcNutMst(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTpcNutMst", paramMap);
	}
	
	/**
	 * 영양성분 소분류 매핑 정보 임시 테이블 데이터 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteTmpTpcNutL3Cd() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTpcNutL3Cd");
	}
	
	/**
	 * 영양성분 소분류 매핑 정보 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTpcNutL3Cd(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTpcNutL3Cd", paramMap);
	}
	
	/**
	 * 영양성분 소분류 매핑 정보 Temp to Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTpcNutL3Cd(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTpcNutL3Cd", paramMap);
	}
	
	/**
	 * 오프라인 카테고리분류 임시 테이블 데이터 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteTmpTcaMdCategory() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteTmpTcaMdCategory");
	}
	
	/**
	 * 오프라인 카테고리분류 임시 테이블 데이터 저장 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTmpTcaMdCategory(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertTmpTcaMdCategory", paramMap);
	}
	
	/**
	 * 오프라인 카테고리분류 Temp to Real 이관
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int mergeTcaMdCategory(Map<String,Object> paramMap) throws Exception {
		return (int) getSqlMapClientTemplate().update("BosOpenAPiQuery.mergeTcaMdCategory", paramMap);
	}
	
	/**
	 * 온라인 전시 카테고리 운영 테이블 데이터 전체 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteRealTcaCategory() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteRealTcaCategory");
	}
	
	/**
	 * 온라인 전시 카테고리 운영 테이블 데이터 BULK INSERT
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertRealTcaCategory(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertRealTcaCategory", paramMap);
	}
	
	/**
	 * 마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 운영 테이블 데이터 전체 삭제 
	 * @return int
	 * @throws Exception
	 */
	public int deleteRealTcaCategoryMapping() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteRealTcaCategoryMapping");
	}
	
	/**
	 * 마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 운영 테이블 데이터 BULK INSERT
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertRealTcaCategoryMapping(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertRealTcaCategoryMapping", paramMap);
	}
	
	/**
	 * 팀 카테고리 매핑정보 운영 테이블 데이터 전체 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteRealTpcL1Cd() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteRealTpcL1Cd");
	}
	
	/**
	 * 팀 카테고리 매핑정보 운영 테이블 데이터 BULK INSERT
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertRealTpcL1Cd(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertRealTpcL1Cd", paramMap);
	}
	
	/**
	 * 오카도 전시 카테고리 운영 테이블 데이터 전체 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteRealPdDcat() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteRealPdDcat");
	}
	
	/**
	 * 오카도 전시 카테고리 운영 테이블 데이터 BULK INSERT
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertRealPdDcat(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertRealPdDcat", paramMap);
	}
	
	/**
	 * 오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 운영 테이블 데이터 전체 삭제
	 * @return int
	 * @throws Exception
	 */
	public int deleteRealPdDcatScatMpng() throws Exception {
		return (int) getSqlMapClientTemplate().delete("BosOpenAPiQuery.deleteRealPdDcatScatMpng");
	}
	
	/**
	 * 오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 운영 테이블 데이터 BULK INSERT
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertRealPdDcatScatMpng(Map<String,Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("BosOpenAPiQuery.insertRealPdDcatScatMpng", paramMap);
	}
	
}
