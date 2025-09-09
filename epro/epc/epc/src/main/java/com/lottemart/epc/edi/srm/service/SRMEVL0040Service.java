package com.lottemart.epc.edi.srm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.srm.model.SRMEVL0040ListVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0040VO;

import javax.servlet.http.HttpServletRequest;

/**
 * 품질경영평가  > 품질경영평가  Service
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일 				수정자				수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
public interface SRMEVL0040Service {
	
	/**
	 * 품질경영평가 Tab List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlTabList(SRMEVL0040VO vo, HttpServletRequest request) throws Exception;
	
	/**
	 * 품질경영평가 Item
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlItem(SRMEVL0040VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 품질경영평가 등록
	 * @param SRMEVL0040ListVO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertQualityEvaluation(SRMEVL0040ListVO listVo, HttpServletRequest request) throws Exception;

	/**
	 * 품질평가 모두 입력 여부 Check
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public int selectQualityEvaluationEvalCheck(SRMEVL0040VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 엑셀파일 업로드
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public HashMap<String,Object> selectQualityEvaluationItemListExcelUpload(SRMEVL0040VO vo, HttpServletRequest request) throws Exception;
	
}
