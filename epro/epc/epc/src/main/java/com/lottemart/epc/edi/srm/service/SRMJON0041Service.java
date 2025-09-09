package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMJON0041VO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 상세정보
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
public interface SRMJON0041Service {
    /**
     * 잠재업체 상세정보 조회
     * @param SRMJON0041VO
     * @return SRMJON0041VO
     * @throws Exception
     */
    public SRMJON0041VO selectHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception;

    /**
     * 잠재업체 상세정보 수정
     * @param SRMJON0041VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception;

    /**
     * 파일정보 조회
     * @param String
     * @throws Exception
     */
    public CommonFileVO selectHiddenCompFile(CommonFileVO vo) throws Exception;

    /**
     * 파일저장/수정/삭제
     * @param String
     * @param MultipartFile
     * @param String
     * @param String
     * @return
     * @throws Exception
     */
    public String fileSave(String fileId, MultipartFile uploadfile, String SellerCode, String deleteFileNo)throws Exception;
}
