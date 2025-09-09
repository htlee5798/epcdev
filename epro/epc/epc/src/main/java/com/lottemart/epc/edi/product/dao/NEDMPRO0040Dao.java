package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0043VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0044VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0045VO;
import com.lottemart.epc.system.model.PSCMSYS0003VO;

/**
 * @Class Name : NEDMPRO0040Dao
 * @Description : 임시보관함 Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.27 	SONG MIN KYO	최초생성
 * </pre>
 */

@Repository("nEDMPRO0040Dao")
public class NEDMPRO0040Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 임시보관함 LIST
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0040VO> selectImsiProductList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectImsiProductList", paramMap);
	}
	
	/**
	 * 상품의 변형속성 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0042VO> selectNewSrcmkCd(Map<String,Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewSrcmkCd", paramMap);
	}
	
	/**
	 * 신상품의 확정된 상품의 변형속성 리스트 조회 추가 2016.03.17 by song min kyo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0042VO> selectNewSrcmkCdFix(Map<String,Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewSrcmkCdFix", paramMap);
	}
	
	/**
	 * 임시보관함 선택한 상품 변형속성 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProdVarAtt(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdVarAtt", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 선택한 상품 분석속성 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProdCatAtt(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdCatAtt", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 선택한 상품 오프라인 이미지 정보 삭제
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void deleteImsiNewProdOfflineImg(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdOfflineImg", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 선택한 상품 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProd(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProd", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 선택한 상품(전상거래법) 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProdAddInfoVal(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdAddInfoVal", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 선택한 상품(KC인증) 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProdCertInfoVal(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdCertInfoVal", nEDMPRO0040VO);
	}	
	
	/**
	 * 임시보관함 선택한 상품설명 삭제
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void deleteImsiNewProdDescr(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdDescr", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 확정처리 선택한 상품들의 상세정보 조회
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectNewTmpProductInfo(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewTmpProductInfo", nEDMPRO0040VO);
	}	
	
	/**
	 * 임시보관함 확정처리 선택한 상품들의 그룹분석속성 조회
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectRfcDataGrpAttr(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectRfcDataGrpAttr", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 확정처리 선택한 상품들의 영양성분속성 조회
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectRfcDataNutAtt(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectRfcDataNutAtt", nEDMPRO0040VO);
	}

	/**
	 * 임시보관함 확정처리 선택한 상품들의이미지 상세정보 조회
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectNewTmpProductImgInfo(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewTmpProductImgInfo", nEDMPRO0040VO);
	}

	/**
	 * 임시보관함 확정처리 전 이미지 등록 안된 상품 확인
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	public Integer selectNewProdNoImgCnt(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0040.selectNewProdNoImgCnt", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 확정처리 이후 상품 마스터 테이블 MD전송 구분 업데이트 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductListAdm(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductListAdm", nEDMPRO0040VO);
	}
	/**
	 * 임시보관함 확정처리 이후 상품 마스터 테이블 MD전송 구분 업데이트 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductList(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductList", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 확정처리 이후 관리자가 직매입 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductSts(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductSts", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 확정처리 이후 관리자가 직매입 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductStsVicOn(String pgmId) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductStsVicOn", pgmId);
	}
	/**
	 * 임시보관함 확정처리 이후 관리자가 직매입 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductStsVicOff(String pgmId) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductStsVicOff", pgmId);
	}		
	
	/**
	 * 임시보관함 확정처리 이후 관리자가 직매입 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductStsNorAdm(String pgmId) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductStsNorAdm", pgmId);
	}	
	/**
	 * 임시보관함 확정처리 이후 관리자가 직매입 처리
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductStsNor(String pgmId) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductStsNor", pgmId);
	}	
		
	/**
	 * 임시보관함 상품 확정처리 RFC 실패일 경우 MD_SEND_DIVN_CD 컬럼 미요청 상태['']로 다시 UPDATe
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateImsiProductClear(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateImsiProductClear", nEDMPRO0040VO);
	}
	
	/**
	 * 임시보관함 상품상세보기 변형속성 정보
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0043VO> selectNewProductDetailVarAttInfo(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewProductDetailVarAttInfo", paramMap);
	}
	
	/**
	 * 임시보관함 상품상세보기 온라인 전용 88코드, 이익률 조회
	 * @param entpCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0044VO> selectOnlineRepresentProdctList(String entpCode) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectOnlineRepresentProdctList", entpCode);
	}	
	
	/**
	 * 임시보관함 상세보기 온라인전용 [단품정보리스트]
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0045VO> selectOnlineItemListIntemp(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectOnlineItemListIntemp", pgmId);
	}	
	
	/**
	 * 상품 확정요청 이전 해당상품의 소분류에 해당하지 않는 그룹분석속성들은 삭제한다.
	 * 2016.03.14 추가 by song min kyo
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteNotGrpAtt(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteNotGrpAtt", pgmId);
	}
	
	/**
	 * 임직원몰판매가능 상품 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProduct(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProduct", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 전상법 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProdAddDetail(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProdAddDetail", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 KC인증 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProdCertDetail(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProdCertDetail", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 배송정책 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProdDelivery(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProdDelivery", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 배송정책 출고지,반품/교환지 주소 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProdVendorAddr(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProdVendorAddr", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 옵션단품 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProductItemInfo(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProductItemInfo", paramMap);
	}
	
	/**
	 * 임직원몰판매가능 추가설명 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellProductDescription(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellProductDescription", paramMap);
	}
	
	/**
	 * 임시보관함 선택한 배송정책 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteImsiNewProdDelivery(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteImsiNewProdDelivery", nEDMPRO0040VO);
	}
	
	//20180904 상품키워드 입력 기능 추가
	/**
	 * 온라인상품등록(일반) 상품키워드 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellTpcPrdKeyword(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertStaffSellTpcPrdKeyword", paramMap);
	}
	
	/**
	 * 온라인상품등록(일괄) 상품키워드 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteAllTpcPrdTotalKeyword(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteAllTpcPrdTotalKeyword", nEDMPRO0040VO);
	}
	//20180904 상품키워드 입력 기능 추가
	
	/**
	 * 대표 상품이미지 등록 여부
	 * @param pgmId
	 * @throws Exception
	 */
	public int selectProductDescChk(String pgmId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0040.selectProductDescChk", pgmId);
	}
	
	/**
	 * 기본출고지/반품지 등록여부 확인
	 * @param vendorIds
	 * @return
	 * @throws Exception
	 */
	public List<PSCMSYS0003VO>  selectAddressInfo(String[] vendorIds) throws Exception {
		return (List<PSCMSYS0003VO> ) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectAddressInfo", vendorIds);
	}

	/**
	 * EC전시카테고리 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteEcProductCategory(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteEcProductCategory", pgmId);
	}
	
	/**
	 * EC전시카테고리 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertEcProductCategory(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertEcProductCategory", paramMap);
	}

	/**
	 * EC상품(단품별)속성 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteEcProductAttribute(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteEcProductAttribute", pgmId);
	}

	/**
	 * EC상품(단품별)속성 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertEcProductAttribute(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0040.insertEcProductAttribute", paramMap);
	}
	
	/**
	 * 영양성분 속성 삭제
	 * @param String
	 * @throws Exception
	 */
	public void deleteNutAttWithPgmId(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteNutAttWithPgmId", pgmId);
	}

	/**
	 * 영양성분 속성 삭제
	 * @param String
	 * @throws Exception
	 */
	public void deleteOspDispCategory(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteOspDispCategory", pgmId);
	}

	/**
	 * 임시보관함 확정처리 선택한 상품들의 ESG인증정보 조회
	 * @param nEDMPRO0040VO
	 * @return List<HashMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectRfcDataEsgInfo(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectRfcDataEsgInfo", nEDMPRO0040VO);
	}

	/**
	 * ESG 인증항목 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteTpcNewProdEsg(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteTpcNewProdEsg", pgmId);
	}
	
	/**
	 * ESG 파일정보 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteTpcNewProdEsgFile(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0040.deleteTpcNewProdEsgFile", pgmId);
	}
	
	/**
	 * 확정 요청 대상별 상품LIST 구분
	 * @param nEDMPRO0040VO
	 * @return List<NEDMPRO0040VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0040VO> selectNewTmpProductInfoSendGbn(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectNewTmpProductInfoSendGbn", nEDMPRO0040VO);
	}
	
	/**
	 * BOS 전송일자 UPDATE
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateProdBosSendDy(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateProdBosSendDy", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 신상품마스터
	 * @param nEDMPRO0040VO
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> selectTpcNewProdRegBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (HashMap<String,Object>) getSqlMapClientTemplate().queryForObject("NEDMPRO0040.selectTpcNewProdRegBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 신상품등록상태
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcNewProdStsBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcNewProdStsBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 수입정보
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcImportAttBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcImportAttBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] KC인증
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTprProdCertInfoValBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTprProdCertInfoValBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 변형속성
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcVarAttBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcVarAttBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 상품추가설명
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcNewProdDescrBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcNewProdDescrBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 그룹분석속성
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcGrpAttBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcGrpAttBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 영양성분속성
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcNutAttBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcNutAttBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 상품키워드
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcProductKeywordBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcProductKeywordBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] EC 전시카테고리
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTecCatProdMappingBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTecCatProdMappingBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] EC 상품속성
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTecAttrProdMappingBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTecAttrProdMappingBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 전상법
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTprProdAddInfoValBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTprProdAddInfoValBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] ESG 항목
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcNewProdEsgBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcNewProdEsgBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] ESG 첨부파일
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcNewProdEsgFileBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcNewProdEsgFileBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] POG 이미지 정보
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTpcSaleImgBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcSaleImgBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * [BOS FIX] 오카도 전시 카테고리
	 * @param nEDMPRO0040VO
	 * @return List<HashMap<String,Object>>
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> selectTecOspCatProdMappingBosFix(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<HashMap<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTecOspCatProdMappingBosFix", nEDMPRO0040VO);
	}
	
	/**
	 * 항목삭제된 ESG정보 제거
	 * @param nEDMPRO0040VO
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateCtgDelEsgInfo(NEDMPRO0040VO nEDMPRO0040VO) throws DataAccessException {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0040.updateCtgDelEsgInfo", nEDMPRO0040VO);
	}
	
	/**
	 * BOS 전송일자 clear
	 * @param nEDMPRO0040VO
	 * @throws Exception
	 */
	public void updateProdBosSendDyClear(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0040.updateProdBosSendDyClear", nEDMPRO0040VO);
	}
	
	/**
	 * 등록된 ESG 파일정보 조회 (SFTP 전송용)
	 * @param nEDMPRO0040VO
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTpcProdEsgFileForSend(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0040.selectTpcProdEsgFileForSend", nEDMPRO0040VO);
	}
	
	/**
	 * ESG 파일 SFTP 전송 정보 UPDATE
	 * @param paramVo
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdEsgFileSendInfo(NEDMPRO0028VO paramVo) throws Exception{
		return (int) getSqlMapClientTemplate().update("NEDMPRO0040.updateTpcProdEsgFileSendInfo", paramVo);
	}
}
