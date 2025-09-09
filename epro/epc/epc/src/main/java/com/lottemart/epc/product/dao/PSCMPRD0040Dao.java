package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.regexp.recompile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0040DTLVO;
import com.lottemart.epc.product.model.PSCMPRD0040VO;

/**
 * 
 * @author khkim
 * @Description :
 * @Class : com.lottemart.epc.product.dao
 * 
 *        <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  
 * @version :
 * </pre>
 */
@Repository("pscmprd0040Dao")
public class PSCMPRD0040Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 
	 * @see selectRepProdCdList
	 * @Locaton : com.lottemart.epc.product.dao
	 * @MethodName : selectRepProdCdList
	 * @author : khkim
	 * @Description :
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0040VO> selectSelPoint(DataMap paramMap)
			throws SQLException {
		return sqlMapClient
				.queryForList("pscmprd0040.selectSelPoint", paramMap);
	}

	/**
	 * Desc : 셀링포인트 저장
	 * 
	 * @Method Name : selSave
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int selSave(DataMap paramMap) throws Exception {
		int ret = sqlMapClient.update("pscmprd0040.selSave", paramMap);
		return ret;
	}

	/**
	 * Desc : 셀링포인트 수정
	 * 
	 * @Method Name : editSelPont
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int editSelPont(DataMap paramMap) throws Exception {
		int ret = sqlMapClient.update("pscmprd0040.editSelPont", paramMap);
		return ret;
	}
	
	/**
	 * Desc : 셀링포인트 디테일 삭제
	 * @Method Name : deleteDtlSelPoint
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteDtlSelPoint(DataMap paramMap) throws Exception {
		int ret = sqlMapClient.update("pscmprd0040.deleteDtlSelPoint", paramMap);
		return ret;
	}
	

	/**
	 * Desc : 게시핀 건수
	 * 
	 * @Method Name : selectSelPointCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int selectSelPointCnt(Map<String, String> paramMap) throws Exception {
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) sqlMapClient.queryForObject(
				"pscmprd0040.selectSelPointCnt", paramMap);
		return iTotCnt.intValue();
	}

	/**
	 * Desc : 상품톡 팝업
	 * 
	 * @Method Name : selectSelPointView
	 * @param recommSeq
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMPRD0040VO selectSelPointView(String recommSeq) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mdTalkSeq", recommSeq);
		return (PSCMPRD0040VO) sqlMapClient.queryForObject(
				"pscmprd0040.selectSelPointView", paramMap);

	}

	/**
	 * Desc : 상품 디테일 상세
	 * 
	 * @Method Name : selectSelPontDtl
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	/*
	 * public List<PSCMPRD0040DTLVO> selectSelPontDtl(DataMap paramMap) throws
	 * Exception { return
	 * sqlMapClient.queryForList("pscmprd0040.selectSelPontDtl",paramMap); }
	 */

	/**
	 * Desc : 사용여부 시트에서 수정
	 * 
	 * @Method Name : updateSelPont
	 * @param pscmprd0040vo
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updateSelPont(PSCMPRD0040VO pscmprd0040vo) throws Exception {
		return sqlMapClient.update("pscmprd0040.updateSelPont", pscmprd0040vo);
	}

	/**
	 * Desc : 셀링포인트 상세 시트에서 수정
	 * 
	 * @Method Name : deleteSelPont
	 * @param pscmprd0040vo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int deleteSelPont(String mdTalkSeq)
			throws Exception {
		return sqlMapClient.update("pscmprd0040.deleteSelPont",
				mdTalkSeq);
	}

	/**
	 * Desc : 셀링포인트 디테일 일괄 업로드
	 * 
	 * @Method Name : insertAllPoint
	 * @param pscmprd0040dtlvo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int insertAllPoint(DataMap paramMap)
			throws Exception {
		return sqlMapClient.update("pscmprd0040.insertAllPoint",
				paramMap);
	}

	/**
	 * Desc : IBSHEET 세팅
	 * 
	 * @Method Name : selectSelPontDtl
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0040DTLVO> selectSelSheet(DataMap paramMap)
			throws Exception {
		return sqlMapClient.queryForList("pscmprd0040.selectSelSheet", paramMap);
	}

}
