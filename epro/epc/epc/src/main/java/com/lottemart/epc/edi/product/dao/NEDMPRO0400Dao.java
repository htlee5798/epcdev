package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;

import signgate.provider.ec.arithmetic.curves.exceptions.ECException;

@Component("nedmpro0400Dao")
public class NEDMPRO0400Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 입점제안 등록 총 갯수
	 * @param vo
	 * @return
	 * @throws ECException
	 */
	public int selectProposeStoreListCount(NEDMPRO0400VO vo) throws ECException {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0400.selectProposeStoreListCount", vo); 
	}

	/**
	 * 입점제안 등록 리스트 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0400VO> selectProposeStoreList(NEDMPRO0400VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectProposeStoreList", vo);
	}	
	
	/**
	 * 반려건 재등록시 기존건은 delYn = Y 로변경 
	 * @param vo
	 * @throws Exception
	 */
	public void updateNewPropDelYn (NEDMPRO0400VO vo) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0400.updateNewPropDelYn", vo);
	}
	
	/**
	 * 신규 입점 제안번호 구하기 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String selectNewPropRegNo(String seq) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0400.selectNewPropRegNo", seq);
	}
	
	/**
	 * 이미지 아이디 구하기
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String selectNewImgId(int seq) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0400.selectNewImgId", seq);
	}
	
	public String selectOrgFileNm(String imgId) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0400.selectOrgFileNm", imgId);
	}
	
	/**
	 * 신규 입점 제안 정보 저장 
	 * @param paramVo
	 * @throws Exception
	 */
	public void insertNewPropStore(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().insert("NEDMPRO0400.insertNewPropStore", paramVo);
	}
	
	/**
	 * 신규 입점 제안 정보 수정 
	 * @param paramVo
	 * @throws Exception
	 */
	public void updateNewPropStore(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().update("NEDMPRO0400.updateNewPropStore", paramVo);
	}
	
	
	/**
	 * 신규 입점 제안 이미지 저장 
	 * @param paramVo
	 * @throws Exception
	 */
	public void insertNewPropStoreImg(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().insert("NEDMPRO0400.insertNewPropStoreImg", paramVo);
	}
	
	/**
	 * 신규 입점 제안 이미지 수정 
	 * @param paramVo
	 * @throws Exception
	 */
	public void updateNewPropStoreImg(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().update("NEDMPRO0400.updateNewPropStoreImg", paramVo);
	}
	
	/**
	 * 등록된 이미지 상세조회 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0400VO selectDetailImgInfo(String param) throws Exception{
		return (NEDMPRO0400VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0400.selectDetailImgInfo", param);
	}
	
	/**
	 * 신규 입정 상품 삭제 
	 * @param paramVo
	 * @throws Exception
	 */
	public void deleteNewPropStore(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().update("NEDMPRO0400.deleteNewPropStore", paramVo);
	}
	
	/**
	 * 신규 입점 상품 이미지 삭제 
	 * @param paramVo
	 * @throws Exception
	 */
	public void deleteImgNewPropStore(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().update("NEDMPRO0400.deleteImgNewPropStore", paramVo);
	}
	
	/**
	 * 신규 입점 제안 상품 제안요청 상태로 변경 
	 * @param paramVo
	 * @throws Exception
	 */
	public void updateNewPropStoreRequest(NEDMPRO0400VO paramVo) throws Exception{
		getSqlMapClientTemplate().update("NEDMPRO0400.updateNewPropStoreRequest", paramVo);
	}
	
	/**
	 * RFC 넘길 데이터 조회 
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectTmpPropStoreInfo(NEDMPRO0400VO paramVo) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectTmpPropStoreInfo", paramVo);
	}
	
	/**
	 * 요청유무  변경 
	 * @param totalRfcPropStoreList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateNewPropStoreReqYn(NEDMPRO0400VO paramVo) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0400.updateNewPropStoreReqYn", paramVo);
	}
	
	/**
	 * 해당팀의 대분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectTempL1List(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectTempL1List", paramMap);
	}
	
	public List<CommonProductVO> selectTempL1List2(CommonProductVO paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectTempL1List2", paramMap);
	}

	/**
	 * 신상품입점제안 엑셀 다운로드 
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectNewPropStoreExcelInfo(NEDMPRO0400VO paramVo) throws Exception {
		return (List<HashMap<String, String>>) getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectNewPropStoreExcelInfo", paramVo);
	}
	
	/**
	 * 상품제안 채널 조회 (selectbox 구성용)
	 * @param paramMap
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTpcNewPropChanCodes(Map<String, Object> paramMap) throws Exception {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectTpcNewPropChanCodes", paramMap);
	}
	
	/**
	 * 등록된 제안 파일정보 조회 (SFTP 전송용)
	 * @param paramMap
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTpcProdNewPropFileForSend(Map<String, Object> paramMap) throws Exception{
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0400.selectTpcProdNewPropFileForSend", paramMap);
	}
	
	/**
	 * 제안 파일 SFTP 전송 정보 UPDATE
	 * @param paramVo
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdNewProdImgSendInfo(NEDMPRO0400VO paramVo) throws Exception{
		return (int) getSqlMapClientTemplate().update("NEDMPRO0400.updateTpcProdNewProdImgSendInfo", paramVo);
	}
}
