package com.lottemart.epc.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0041DtlVO;
import com.lottemart.epc.product.model.PSCMPRD0041VO;

@Repository("pscmprd0041Dao")
public class PSCMPRD0041Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * Desc : 협력사 공지관리 검색
	 * @Method Name : selectVendorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0041VO> selectVendorMgr(DataMap paramMap) throws Exception{
		return sqlMapClient.queryForList("pscmprd0041.selectVendorMgr",paramMap);
	}
	
	/**
	 * Desc : 협력사 공지관리 게시판 총건수
	 * @Method Name : selectVendorMgrCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selectVendorMgrCnt( Map<String, String>paramMap)throws Exception{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) sqlMapClient.queryForObject( "pscmprd0041.selectVendorMgrCnt", paramMap);
		return iTotCnt.intValue();
	}
	
	/**
	 * Desc : 협력사 공지관리 사용ㅇ
	 * @Method Name : updateVendorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateVendorMgr(PSCMPRD0041VO pscmprd0041vo) throws Exception{
		return sqlMapClient.update("pscmprd0041.updateVendorMgr", pscmprd0041vo);
	}
	
	
	/**
	 * Desc : 협력사 공지 등록
	 * @Method Name : saveVondorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int saveVondorMgr(DataMap paramMap) throws Exception{
		return sqlMapClient.update("pscmprd0041.saveVondorMgr", paramMap);
	}
	
	/**
	 * Desc : 협력사 공지 수정
	 * @Method Name : updateVondorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateVondorMgr(DataMap paramMap) throws Exception{
		return sqlMapClient.update("pscmprd0041.updateVondorMgr", paramMap);
	}
	
	/**
	 * Desc : 협력사 공지 디테일 삭제
	 * @Method Name : deleteVondorMgrDtl
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteVondorMgrDtl(DataMap paramMap) throws Exception{
		return sqlMapClient.update("pscmprd0041.deleteVondorMgrDtl", paramMap);
	}
	/**
	 * Desc : 협력사 게시판 상세 등록
	 * @Method Name : saveVondorMgrDtl
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int saveVondorMgrDtl(DataMap paramMap) throws Exception{
		return sqlMapClient.update("pscmprd0041.saveVondorMgrDtl", paramMap);
	}
	
	
	/**
	 * Desc :  
	 * @Method Name : VondorMgrCnt
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public String VondorMgrCnt()throws Exception{
		return (String) sqlMapClient.queryForObject( "pscmprd0041.VondorMgrCnt");
	}
	/**
	 * Desc : 협력사 공지 게시판 디테일
	 * @Method Name : selectVendorMgrView
	 * @param recommSeq
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMPRD0041VO selectVendorMgrView(String recommSeq) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("announSeq", recommSeq);
		return (PSCMPRD0041VO) sqlMapClient.queryForObject( "pscmprd0041.selectVendorMgrView", paramMap);
	}
	
	/**
	 * Desc : IBSHEET 세팅
	 * @Method Name : selectSheet
	 * @param seq
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0041DtlVO> selectSheet(DataMap paramMap) throws Exception{
		return sqlMapClient.queryForList("pscmprd0041.selectSheet", paramMap);
	}
	
	/**
	 * Desc : 
	 * @Method Name : selectPscmprd0041Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> selectPscmprd0041Export(Map<Object, Object> paramMap) throws Exception {
		return sqlMapClient.queryForList("pscmprd0041.selectPscmbrd0014Export",paramMap);
	}
	
	/**
	 * 공지사항 ec연동여부 확인 
	 * Desc : 
	 * @Method Name : selectNotiEcInfo
	 * @param pscmprd0041Vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMPRD0041VO selectNotiEcInfo(PSCMPRD0041VO vo) throws Exception {
		return (PSCMPRD0041VO) sqlMapClient.queryForObject("pscmprd0041.selectNotiEcInfo", vo);
	}
	
	/**
	 * 같은 공지기간에 상품 중복 여부 체크 
	 * Desc : 
	 * @Method Name : selectNotiEcProductCheck
	 * @param pscmprd0041Vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selectNotiEcProductCheck(Map<String, Object> dataMap) throws Exception {
		Integer resultCnt = new Integer(0);
		resultCnt = (Integer) sqlMapClient.queryForObject( "pscmprd0041.selectNotiEcProductCheck", dataMap);
		return resultCnt;
	}

}
