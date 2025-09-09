package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMJON0041VO;
import org.springframework.stereotype.Repository;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 상세정보 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.18  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon0041Dao")
public class SRMJON0041Dao extends SRMDBConnDao {

    /**
     * 잠재업체 상세정보 조회
     * @param SRMJON0041VO
     * @return SRMJON0041VO
     * @throws Exception
     */
    public SRMJON0041VO selectHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception {
        return (SRMJON0041VO)queryForObject("SRMJON0041.selectHiddenCompDetailInfo", vo);
    }

    /**
     * 잠재업체 상세정보 수정
     * @param SRMJON0041VO
     * @throws Exception
     */
    public void updateHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception {
        update("SRMJON0041.updateHiddenCompDetailInfo", vo);
    }

    /**
     * 잠재업체 상세정보 수정
     * @param SRMJON0041VO
     * @throws Exception
     */
    public void updateHiddenCompDetailInfoSSUPI(SRMJON0041VO vo) throws Exception {
        update("SRMJON0041.updateHiddenCompDetailInfoSSUPI", vo);
    }


    /**
     * 파일등록
     * @param CommonFileVO
     * @throws Exception
     */
    public void insertHiddenCompFile(CommonFileVO vo) throws Exception {
        insert("SRMJON0041.insertHiddenCompFile", vo);
    }

    /**
     * 파일삭제
     * @param CommonFileVO
     * @throws Exception
     */
    public void deleteHiddenCompFile(CommonFileVO vo) throws Exception {
        insert("SRMJON0041.deleteHiddenCompFile", vo);
    }

    /**
     * 파일정보 조회
     * @param String
     * @throws Exception
     */
    public CommonFileVO selectHiddenCompFile(CommonFileVO vo) throws Exception {
        return (CommonFileVO)queryForObject("SRMJON0041.selectHiddenCompFile", vo);
    }

    /**
     * 파일정보 조회(PRODUCT_IMG_PATH)
     * @param String
     * @throws Exception
     */
    public String selectHiddenCompProductImgPathFileId(SRMJON0041VO vo) throws Exception {
        return (String)queryForString("SRMJON0041.selectHiddenCompProductImgPathFileId", vo);
    }

    /**
     * 파일정보 조회(PRODUCT_INTRO_ATTACH_NO)
     * @param String
     * @throws Exception
     */
    public String selectHiddenCompProductIntroAttachNoFileId(SRMJON0041VO vo) throws Exception {
        return (String)queryForString("SRMJON0041.selectHiddenCompProductIntroAttachNoFileId", vo);
    }

    /**
     * 파일정보 조회(COMPANY_INTRO_ATTACH_NO)
     * @param String
     * @throws Exception
     */
    public String selectHiddenCompCompanyIntroAttachNoFileId(SRMJON0041VO vo) throws Exception {
        return (String)queryForString("SRMJON0041.selectHiddenCompCompanyIntroAttachNoFileId", vo);
    }
    
    /**
     * 파일정보 조회(SM_ATTACH_NO)
     * @param String
     * @throws Exception
     */
    public String selectHiddenCompSmAttachNoFileId(SRMJON0041VO vo) throws Exception {
        return (String)queryForString("SRMJON0041.selectHiddenCompSmAttachNoFileId", vo);
    }

}




