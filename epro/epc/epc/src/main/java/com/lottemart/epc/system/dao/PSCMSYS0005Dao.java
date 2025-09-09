package com.lottemart.epc.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.model.PSCMSYS0005DtlVO;
import com.lottemart.epc.system.model.PSCMSYS0005VO;

/**
 * @Class Name : PSCMSYS0005Dao.java
 * @Description : 정산법 템플릿 관리 Repository
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 28. 오후 5:19:54 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscmsys0005Dao")
public class PSCMSYS0005Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * Desc : 전상법템플릿폼
	 * 
	 * @Method Name : searchEscTem
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMSYS0005VO> searchEscTem(Map<String, String> paramMap)
			throws Exception {
		return sqlMapClient.queryForList("pscmsys0005.searchEscTem", paramMap);
	}

	/**
	 * Desc : 전상법탬플릿 카운트
	 * 
	 * @Method Name : searchEscTemCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int searchEscTemCnt(Map<String, String> paramMap) throws Exception {
		return (Integer) sqlMapClient.queryForObject(
				"pscmsys0005.searchEscTemCnt", paramMap);
	}

	/**
	 * Desc : 전상법 탬플릿 삭제
	 * 
	 * @Method Name : deleteEscTem
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int deleteEscTem(PSCMSYS0005VO pscmsys0005vo) throws Exception {
		return sqlMapClient.update("pscmsys0005.deleteEscTem", pscmsys0005vo);
	}

	/**
	 * Desc : 전상법 탬플릿 저장
	 * 
	 * @Method Name : insertMasEscTem
	 * @param pscmsys0005vo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int insertMasEscTem(PSCMSYS0005VO pscmsys0005vo) throws Exception {
		return sqlMapClient
				.update("pscmsys0005.insertMasEscTem", pscmsys0005vo);
	}

	/**
	 * Desc : 전상법 탬플릿 수정
	 * 
	 * @Method Name : updateMasEscTem
	 * @param pscmsys0005vo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updateMasEscTem(PSCMSYS0005VO pscmsys0005vo) throws Exception {
		return sqlMapClient .update("pscmsys0005.updateMasEscTem", pscmsys0005vo);
	}
	
	/**
	 * Desc : 
	 * @Method Name : selectEscTemSql
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public String selectEscTemSql() throws Exception{
		return (String) sqlMapClient .queryForObject("pscmsys0005.selectEscTemSql");
	}

	/**
	 * Desc : 전상법 탬플릿 상세 저장
	 * 
	 * @Method Name : insertSubEscTem
	 * @param pscmsys0005DtlVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int insertSubEscTem(PSCMSYS0005DtlVO pscmsys0005DtlVO)
			throws Exception {
		return sqlMapClient.update("pscmsys0005.insertSubEscTem",
				pscmsys0005DtlVO);
	}

	/**
	 * Desc : 전상법 템플릿 상세 수정
	 * 
	 * @Method Name : updateSubEscTem
	 * @param pscmsys0005DtlVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updateSubEscTem(PSCMSYS0005DtlVO pscmsys0005DtlVO) throws Exception {
		return sqlMapClient.update("pscmsys0005.updateSubEscTem", pscmsys0005DtlVO);
	}

	/**
	 * Desc :
	 * 
	 * @Method Name : dtlEscTem
	 * @param norProdSeq
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMSYS0005VO dtlEscTem(String norProdSeq) throws Exception {
		return (PSCMSYS0005VO) sqlMapClient.queryForObject(
				"pscmsys0005.dtlEscTem", norProdSeq);
	}

	/**
	 * Desc :
	 * 
	 * @Method Name : dtlEscTemDtl
	 * @param norProdSeq
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMSYS0005DtlVO dtlEscTemDtl(String norProdSeq) throws Exception {
		return (PSCMSYS0005DtlVO) sqlMapClient.queryForObject(
				"pscmsys0005.dtlEscTemDtl", norProdSeq);
	}

	/**
	 * Desc : IBSHEET 세팅
	 * 
	 * @Method Name : selectSheet
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMSYS0005DtlVO> selectSheet(DataMap paramMap)
			throws Exception {
		return sqlMapClient.queryForList("pscmsys0005.selectSheet", paramMap);
	}
}
