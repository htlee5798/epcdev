package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON0044VO;
import org.springframework.stereotype.Repository;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 (해외)
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.11.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.11.16  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon0044Dao")
public class SRMJON0044Dao extends SRMDBConnDao {


    /**
     * 잠재업체 기본정보 조회
     * @param SRMJON0044VO
     * @throws Exception
     */
    public SRMJON0044VO selectGlobalHiddenCompInfo(SRMJON0044VO vo) throws Exception {
        return (SRMJON0044VO)queryForObject("SRMJON0044.selectGlobalHiddenCompInfo", vo);
    }

    /**
     * 기등록 잠재업체 기본정보 조회
     * @param SRMJON0044VO
     * @throws Exception
     */
    public SRMJON0044VO selectGlobalHiddenCompBasicInfo(SRMJON0044VO vo) throws Exception {
        return (SRMJON0044VO)queryForObject("SRMJON0044.selectGlobalHiddenCompBasicInfo", vo);
    }

    /**
     * 잠재업체 중복체크
     * @param SRMJON0040VO
     * @return SRMJON0040VO
     * @throws Exception
     */
    public int selectGlobalHiddenCompCheck(SRMJON0044VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0044.selectGlobalHiddenCompCheck", vo);
    }

    /**
     * 잠재업체 기본정보등록
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void insertGlobalHiddenCompInfo(SRMJON0044VO vo) throws Exception {
        insert("SRMJON0044.insertGlobalHiddenCompInfo", vo);
    }

    /**
     * 잠재업체 기본정보수정
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void updateGlobalHiddenCompInfo(SRMJON0044VO vo) throws Exception {
        update("SRMJON0044.updateGlobalHiddenCompInfo", vo);
    }


    /**
     * 잠재업체 담당잡정보 등록
     * @param SRMJON0044VO
     * @throws Exception
     */
    public void insertGlobalHiddenCompInfoSSUPI(SRMJON0044VO vo) throws Exception {
        insert("SRMJON0044.insertGlobalHiddenCompInfoSSUPI", vo);
    }

    /**
     * 잠재업체 담당잡정보 수정
     * @param SRMJON0044VO
     * @throws Exception
     */
    public void updateGlobalHiddenCompInfoSSUPI(SRMJON0044VO vo) throws Exception {
        update("SRMJON0044.updateGlobalHiddenCompInfoSSUPI", vo);
    }


    /**
     * 입점상담 신청 등록
     * @param SRMJON0044VO
     * @throws Exception
     */
    public void insertGlobalConsultReq(SRMJON0044VO vo) throws Exception {
        insert("SRMJON0044.insertGlobalConsultReq", vo);
    }

    /**
     * 입점상담 신청 수정
     * @param SRMJON0044VO
     * @throws Exception
     */
    public void updateGlobalConsultReq(SRMJON0044VO vo) throws Exception {
        update("SRMJON0044.updateGlobalConsultReq", vo);
    }

}




