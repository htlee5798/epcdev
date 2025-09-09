package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMJON0030ListVO;
import com.lottemart.epc.edi.srm.model.SRMJON0030VO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * 입점상담 / 입점상담신청  / 1차 스크리닝 Service
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.06  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
public interface SRMJON0030Service {
    /**
     * 1차 스크리닝 LIST 조회
     * @param vo
     * @return
     * @throws Exception
     */
    public List<SRMJON0030VO> selectScreeningList(SRMJON0030VO vo) throws Exception;

    /**
     * 1차 스크리닝 결과 등록
     * @param SRMJON0030VO
     * @return Map<String,Object>
     * @throws Exception
     */
    public Map<String,Object> insertScreeningList(SRMJON0030ListVO listVo, HttpServletRequest request) throws Exception;


    /**
     * 대분류 코드 조회
     * @param SRMJON0030VO
     * @return List<SRMJON0030VO>
     * @throws Exception
     */
    public List<SRMJON0030VO> selectCatLv1CodeList(SRMJON0030VO vo) throws Exception;
    
    /**
	 * 채널, 대분류로 동일한 신청내역 체크
	 * @param SRMJON0040VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
    public Map<String, Object> consultStatusCheck(SRMJON0040VO srmjon0040VO) throws Exception;
}
