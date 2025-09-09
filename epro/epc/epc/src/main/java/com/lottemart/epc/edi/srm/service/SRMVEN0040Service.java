
package com.lottemart.epc.edi.srm.service;

import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.srm.model.SRMVEN004001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0040VO;


/**
 * 품질경영평가조치
 * 
 * @author SHIN SE JIN
 * @since 2016.10.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMVEN0040Service {
	
	/**
	 * 품질경영평가조치리스트 카운트
	 * @param SRMVEN0040VO
	 * @return int
	 * @throws Exception
	 */
	public int selectEvalInfoListCount(SRMVEN0040VO vo) throws Exception;
	
	/**
	 * 품질경영평가조치리스트 조회
	 * @param SRMVEN0040VO
	 * @return List<SRMVEN0040VO>
	 * @throws Exception
	 */
	public List<SRMVEN0040VO> selectEvalInfoList(SRMVEN0040VO vo) throws Exception ;
	
	/**
	 * 품질경영평가조치내역 조회
	 * @param SRMVEN004001VO
	 * @return List<SRMVEN004001VO>
	 * @throws Exception
	 */
	public List<SRMVEN004001VO> selectCorrectiveActionList(SRMVEN004001VO vo) throws Exception;
	
	/**
	 * 품질경영평가조치 상세정보 조회
	 * @param SRMVEN004001VO
	 * @return SRMVEN004001VO
	 * @throws Exception
	 */
	public SRMVEN004001VO selectCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception;
	
	/**
	 * 품질경영평가조치 정보 수정
	 * @param SRMVEN004001VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception;

	/**
	 * 품질경영평가조치 정보 삭제
	 * @param SRMVEN004001VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateCorrectiveActionDetaildel(SRMVEN004001VO vo) throws Exception;
	
}
