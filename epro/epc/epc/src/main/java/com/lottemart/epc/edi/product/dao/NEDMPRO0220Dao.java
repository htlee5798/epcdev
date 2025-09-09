package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.common.util.HashBox;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0220VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0221VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0222VO;

/**
 * @Class Name : NEDMPRO0220Dao
 * @Description : 물류바코드 등록 DAO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.30 	SONG MIN KYO	최초생성
 * </pre>
 */

@Repository("nEDMPRO0220Dao")
public class NEDMPRO0220Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 임시보관함에서 바코드 등록을 위한 기본 정보 조회
	 * @param paramMap
	 * @return
	 */
	public HashMap selectNewBarcodeRegistTmp(Map<String, Object> paramMap) throws Exception {
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectNewBarcodeRegistTmp", paramMap);
	}
	
	/**
	 * 임시보관함에서 바코드 등록을 위한  등록된 상품의 속성 리스트 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectImsiProdClassListCnt(String pgmId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectImsiProdClassListCnt", pgmId);
	}
	
	
	/**
	 * [임시보관함 바코드 등록 팝업] 해당상품의 등록된 속성이 있을경우 등록된 속성의 물류바코드 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0220VO selectImsiProdClassToLogiBcdInfo(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0220VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectImsiProdClassToLogiBcdInfo", paramMap);
	}
	
	/**
	 * 물류바코드 상품별 SEQ 구한다.
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public String selectLogiBcdSeqInfo(String pgmId) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectLogiBcdSeqInfo", pgmId);
	}
	
		
	/**
	 * 임시보관함 물류바코드 저장
	 * @param nEDMPRO0220VO
	 * @throws Exception
	 */
	public void insertImsiProdLogiBcd(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0220.insertImsiProdLogiBcd", nEDMPRO0220VO);
	}
	
	/**
	 * RFC 넘길 물류바코드 조회
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public HashMap selectImsiProdInfoToRFC(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		return (HashMap) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectImsiProdInfoToRFC", nEDMPRO0220VO);		
	}
	
	/**
	 * 물류바코드 중복 카운트[TPC_LOGI_BCD TABLE 활용]
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public int selectLogiBcdDuplChkCnt(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectLogiBcdDuplChkCnt", nEDMPRO0220VO);
	}
	
	/**
	 * 물류바코드 등록전 삭제 [신상풍, 임시보관함]
	 * @param nEDMPRO0220VO
	 * @throws Exception
	 */
	public void deleteImsiProdLogiBcd(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0220.deleteImsiProdLogiBcd", nEDMPRO0220VO);
	}
	
	/**
	 * 물류바코드 수정[신상품, 임시보관함]
	 * @param nEDMPRO0220VO
	 * @throws Exception
	 */
	public void updateImsiProdLogiBcd(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0220.updateImsiProdLogiBcd", nEDMPRO0220VO);
	}
	
	/**
	 * 물류바코드 수정일 경우 LOGI_BCD_SAP 테이블의 확정상태를 심사중으로 UPDATE
	 * @param nEDMPRO0220VO
	 * @throws Exception
	 */
	public void updateImsiProdLogiBcdSAP(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0220.updateImsiProdLogiBcdSAP", nEDMPRO0220VO);
	}
	
	/**
	 * 물류바코드 등록 팝업 화면 상품기본정보 조회 _1
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0220VO selectLogiProdBasicInfo_1(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0220VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectLogiProdBasicInfo_1", paramMap);
	}
	
	/**
	 * 물류바코드 등록 팝업 화면 상품기본정보 조회 _2
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0220VO selectLogiProdBasicInfo_2(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0220VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectLogiProdBasicInfo_2", paramMap);
	}
	
	
	
	
	/////////////////////////

	public HashMap selectBarCodeProductInfo(NEDMPRO0221VO searchParam) {
		// TODO Auto-generated method stub
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectBarcodeProductInfo", searchParam);
	}
	
	
	/**
	 * 바코드 정보 조회
	 * @param searchParam
	 * @return
	 */
	public List selectBarcodeList(NEDMPRO0221VO searchParam) {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMPRO0220.selectBarcodeList", searchParam);
	}
	
	/**
	 * 바코드 RFC 호출정보 조회
	 * @param searchParam
	 * @return
	 */
	/*public List selectProdInfoToRFC(NEDMPRO0221VO searchParam) {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMPRO0220.selectProdInfoToRFC", searchParam);
	}*/
	public HashMap selectProdInfoToRFC(NEDMPRO0220VO vo) {
		return (HashBox)getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectProdInfoToRFC", vo);
	}
	
	
	public String selectBarcodeList() throws DataAccessException  {
		// TODO Auto-generated method stub
		return (String)getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectPgmId");
	}
	
	
	/*new_logi_bcd_edi INSERT or UPDATE*/
	
	/**
	 * 물류바코드 Insert
	 * @param pedmpro00061vo
	 * @throws DataAccessException
	 */
	/*public void insertNewBarcode(NEDMPRO0222VO pedmpro00061vo) throws DataAccessException  {*/
	public void insertNewBarcode(NEDMPRO0220VO vo) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0220.insertNewBarcode", vo);
	}
	
	/**
	 * 물류바코드 Update
	 * @param pedmpro00061vo
	 * @throws DataAccessException
	 */
	/*public void updateNewBarcode(NEDMPRO0222VO pedmpro00061vo) throws DataAccessException  {*/
	public void updateNewBarcode(NEDMPRO0220VO vo) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0220.updateNewBarcode", vo);
	}
	
	/**
	 * 수정요청정보 Update
	 * @param pedmpro00061vo
	 * @throws DataAccessException
	 */
	/*public void updateNewBarcodeSap(NEDMPRO0222VO pedmpro00061vo) throws DataAccessException  {*/
	public void updateNewBarcodeSap(NEDMPRO0220VO vo) throws Exception  {
		getSqlMapClientTemplate().insert("NEDMPRO0220.updateNewBarcodeSap", vo);
	}
	
	/**
	 * 신상품등록현황 조회에서 해당 변형속성이 있는 상품의 물류바코드 등록시 해당 변형순번의 변형명칭 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectVarAttNmInfo(Map<String, Object> paramMap) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0220.selectVarAttNmInfo", paramMap);
	}

	
}
