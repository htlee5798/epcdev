package com.lottemart.epc.edi.srm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMJON004001VO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 기본정보
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.15  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
public interface SRMJON0040Service {
    /**
     * 잠재업체 기본정보 조회
     * @param HttpServletRequest
     * @param SRMJON0040VO
     * @return SRMJON0040VO
     * @throws Exception
     */
    public SRMJON0040VO selectHiddenCompInfo(HttpServletRequest request, SRMJON0040VO vo) throws Exception;

    /**
     * 잠재업체 기본정보 수정
     * @param SRMJON0040VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public Map<String, Object> updateHiddenCompInfo(SRMJON0040VO vo, HttpServletRequest request) throws Exception;

    /**
     * 대분류 코드 LIST 조회
     * @throws Exception
     */
    public List<OptionTagVO> selectCatLv1CodeList() throws Exception;


    /**
     * 주소 검색
     */
    public Map<String,Object> selectZipList(SRMJON004001VO paramVO, HttpServletRequest request) throws Exception;
    
    /**
     * 개인정보선택 유형 가져오기
     */
    public String selecthiddenCompAgreeType(SRMJON0040VO paramVO, HttpServletRequest request) throws Exception;
}
