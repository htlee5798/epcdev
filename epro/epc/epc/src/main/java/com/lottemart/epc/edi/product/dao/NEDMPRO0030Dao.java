package com.lottemart.epc.edi.product.dao;


import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;


/**
 * @Class Name : NEDMPRO0030Dao
 * @Description : 온라인신상품등록(배송정보) Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */

@Repository("nEDMPRO0030Dao")
public class NEDMPRO0030Dao extends AbstractDAO {
	
	/**
	 * 배송정보 조회 
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectNewOnlineDeliveryList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectNewOnlineDeliveryList", paramMap);
	}
	
	/**
	 * 업체 주소정보 조회 
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectAddrMgrList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectAddrMgrList", paramMap);
	}
	
	/**
	 * 공통배송비정보1 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(Map<String, Object> paramMap) throws Exception {
		return (DataMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectVendorDlvInfo", paramMap);
	}
	
	/**
	 * 공통배송비정보2 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectVendorDeliInfo(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectVendorDeliInfo", paramMap);
	}
	
	/**
	 * 업체 주소정보 삭제 (저장전)
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteAddrMgr(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0030.deleteAddrMgr", paramMap);
	}
	
	/**
	 * 배송비 삭제 (저장전)
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDeliveryAmt(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0030.deleteDeliveryAmt", paramMap);
	}
	
	/**
	 * 배송가능지역, 묶음배송여부 등록
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdReg(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0030.updateNewProdReg", paramMap);
	}
	
	/**
	 * 배송비 등록
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertDeliveryAmt(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0030.insertDeliveryAmt", paramMap);
	}
	
	/**
	 * 업체 주소정보 등록
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertAddrMgr(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0030.insertAddrMgr", paramMap);
	}
	
	/**
	 * 전상법 템플릿 selectBox 데이터 
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectNorProdTempList", paramMap);
	}
	
	/**
	 * 전상법 템플릿 VALUES
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempValList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectNorProdTempValList", paramMap);
	}
	
	/**
	 * 온라인전용 상품 복사
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertCopyProduct(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0030.insertCopyProduct", paramMap);
	}
	
	/**
	 * 업체 거래형태 정보
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selecttrdTypFgSel(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selecttrdTypFgSel", paramMap);
	}

	/**
	 * EC 표준 카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcStandardCategory(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectEcStandardCategory", paramMap);
	}

	/**
	 * EC 표준 카테고리 매핑
	 * @param paramMap
	 * @throws Exception
	 */
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception {
		return (DataMap) getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectEcStandardCategoryMapping", martCatCd);
	}
	
	/**
	 * EC 전시 카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcDisplayCategory(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectEcDisplayCategory", paramMap);
	}
	
	/**
	 * EC 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectEcProductAttr", paramMap);
	}
	
	/**
	 * EC 상품 표준/전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0030.selectEcCategoryByProduct", pgmId);
	}
	
	/**
	 * EC 롯데ON 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountDispLton(DataMap paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectCountDispLton", paramMap);
	}
	
	/**
	 * EC 롯데마트몰 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountDispMart(DataMap paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectCountDispMart", paramMap);
	}

	/**
	 * EC 표준카테고리 - 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountEcProductAttrId(DataMap paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectCountEcProductAttrId", paramMap);
	}
	
	/**
	 * EC 표준카테고리 - 상품속성값 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountEcProductAttrValId(DataMap paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0030.selectCountEcProductAttrValId", paramMap);
	}
}
