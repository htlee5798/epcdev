package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0014VO;
import com.lottemart.epc.product.model.PSCMPRD0040DTLVO;
import com.lottemart.epc.product.model.PSCMPRD0040VO;

/**
 * 
 * @author khKim
 * @Description : 
 * @Class : com.lottemart.epc.product.service
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *   
 *  -------    --------    ---------------------------
 * 2011.12.16  khKim
 * @version : 
 * </pre>
 */
public interface PSCMPRD0040Service {

	/**
	 * 
	 * @see selectSelPoint
	 * @Locaton    : com.lottemart.epc.product.service
	 * @MethodName  : selectRepProdCdList
	 * @author     :
	 * @Description : 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<PSCMPRD0040VO> selectSelPoint(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 셀링포인트 저장
	 * @Method Name : selSave
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selSave(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc : 셀링포인트 수정
	 * @Method Name : editSelPont
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int editSelPont(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc :  셀링포인트 디테일 삭제
	 * @Method Name : deleteDtlSelPoint
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteDtlSelPoint(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc : 게시판 총건수
	 * @Method Name : selectSelPointCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selectSelPointCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * Desc :  셀링포인트 
	 * @Method Name : selectSelPointView
	 * @param recommSeq
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMPRD0040VO selectSelPointView(String recommSeq) throws Exception;
	
	/**
	 * Desc : 셀링포인트 디테일 조회
	 * @Method Name : selectSelPontDtl
	 * @param 
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List< PSCMPRD0040DTLVO >selectSelSheet(DataMap paramMap ) throws Exception;

	/**
	 * Desc : 셀링포인트 시트에서 수정
	 * @Method Name : updateSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateSelPont(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc :  셀링포인트 상세 시트 삭제
	 * @Method Name : deleteSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteSelPont( String mdTalkSeq) throws Exception;
	
	/**
	 * Desc :  저장 수정
	 * @Method Name : insertAllPoint
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int insertAllPoint (DataMap paramMap) throws Exception;

}
