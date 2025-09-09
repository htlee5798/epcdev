package com.lottemart.epc.edi.product.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

import lcn.module.framework.base.AbstractDAO;


/**
 * @Class Name : NEDMPRO0060Dao
 * @Description : 상품일괄등록 Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.05.18		projectBOS32 	최초생성
 * </pre>
 */

@Repository("nEDMPRO0060Dao")
public class NEDMPRO0060Dao extends AbstractDAO {
	
	/**
	 * 상품기본정보 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProduct(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectImsiProduct", paramMap);
	}
	
	/**
	 * 상품등록여부
	 * @param paramMap
	 * @throws Exception
	 */
	public Integer selectImsiProductCnt(DataMap paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0060.selectImsiProductCnt", paramMap);
	}
	
	/**
	 * 온라인 대표상품코드 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public DataMap selectOnlineRepresentProdctInfo(DataMap paramMap) throws Exception {
		return (DataMap) getSqlMapClientTemplate().queryForObject("NEDMPRO0060.selectOnlineRepresentProdctInfo", paramMap);
	}
	
	/**
	 * 이미지 업로드용 상품명 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProductNm(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectImsiProductNm", paramMap);
	}
	
	/**
	 * 전상법 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchNorProdCode(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectBatchNorProdCode", paramMap);
	}
	
	/**
	 * KC인증 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchKcProdCode(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectBatchKcProdCode", paramMap);
	}
	
	/**
	 * 상품 기본정보 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertProductInfo(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.insertProductInfo", paramMap);
	}
	
	/**
	 * 상품 단품옵션 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertProductItemInfo(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.insertProductItemInfo", paramMap);
	}
	
	/**
	 * 상품 EC 카테고리 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertEcProductCategory(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.insertEcProductCategory", paramMap);
	}
	
	
	/**
	 * 상품 단품속성 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertEcProductAttribute(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.insertEcProductAttribute", paramMap);
	}
	
	/**
	 * 전상법 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveProdAddDetail(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.saveProdAddDetail", paramMap);
	}
	
	/**
	 * 전상법 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveProdCertDetail(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.saveProdCertDetail", paramMap);
	}
	
	/**
	 * 임시상품 테이블 update
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void updateNewProdRegDesc(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.updateNewProdRegDesc", paramMap);
	}
	
	/**
	 * 추가설명 테이블 insert,update
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveNewProdDescr(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0060.saveNewProdDescr", paramMap);
	}
	
	/* ************************** */
	//20181002 상품키워드 입력 기능 추가
	/**
	 * 전상법리스트
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectCodeInfo02() throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectCodeInfo02");
	}
	
	/**
	 * 전상법리스트
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectCodeInfo03() throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectCodeInfo03");
	}
	//20181002 상품키워드 입력 기능 추가
	/* ************************** */

	public Integer selectEcStdDispMappingCnt(DataMap paramMap) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0060.selectEcStdDispMappingCnt", paramMap);
	}
	
	public List<DataMap> selectEcStdDispMappingList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectEcStdDispMappingList", paramMap);
	}

	public Integer selectEcStdAttrMappingCnt(DataMap paramMap) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0060.selectEcStdAttrMappingCnt", paramMap);
	}
	
	public List<DataMap> selectEcStdAttrMappingList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0060.selectEcStdAttrMappingList", paramMap);
	}
	
}
