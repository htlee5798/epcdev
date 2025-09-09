package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 기본정보 Dao
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
@Repository("srmjon0040Dao")
public class SRMJON0040Dao extends SRMDBConnDao {


    /**
     * 잠재업체 기본정보 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public SRMJON0040VO selectHiddenCompInfo(SRMJON0040VO vo) throws Exception {
        return (SRMJON0040VO)queryForObject("SRMJON0040.selectHiddenCompInfo", vo);
    }

    /**
     * 기등록 잠재업체 기본정보 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public SRMJON0040VO selectHiddenCompBasicInfo(SRMJON0040VO vo) throws Exception {
        return (SRMJON0040VO)queryForObject("SRMJON0040.selectHiddenCompBasicInfo", vo);
    }

    /**
     * 파트너사 기본정보 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public SRMJON0040VO selectHiddenCompPartnerInfo(SRMJON0040VO vo) throws Exception {
        return (SRMJON0040VO)queryForObject("SRMJON0040.selectHiddenCompPartnerInfo", vo);
    }



    /**
     * 잠재업체 중복체크
     * @param SRMJON0040VO
     * @return SRMJON0040VO
     * @throws Exception
     */
    public int selectHiddenCompCheck(SRMJON0040VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0040.selectHiddenCompCheck", vo);
    }

    /**
     * 잠재업체 기본정보등록
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void insertHiddenCompInfo(SRMJON0040VO vo) throws Exception {
        insert("SRMJON0040.insertHiddenCompInfo", vo);
    }

    /**
     * 잠재업체 기본정보 수정
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void updateHiddenCompInfo(SRMJON0040VO vo) throws Exception {
        update("SRMJON0040.updateHiddenCompInfo", vo);
    }

    /**
     * 신청 순번
     * @param SRMJON0040VO
     * @throws Exception
     */
    public String selectConsultReqSeq(SRMJON0040VO vo) throws Exception {
        return (String)queryForString("SRMJON0040.selectConsultReqSeq", vo);
    }

    /**
     * 입점상담 신청 등록
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void insertConsultReqRequest(SRMJON0040VO vo) throws Exception {
        insert("SRMJON0040.insertConsultReq", vo);
    }

    /**
     * 입점상담 신청 수정
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void updateConsultReqRequest(SRMJON0040VO vo) throws Exception {
        update("SRMJON0040.updateConsultReq", vo);
    }

    /**
     * 대분류 코드 LIST 조회
     * @throws Exception
     */
    public List<OptionTagVO> selectCatLv1CodeList() throws Exception {
        return queryForList("SRMJON0040.selectCatLv1CodeList", null);
    }

    /**
     * 입점 거절 조회
     * @param SRMJON0040VO
     * @return List<String>
     * @throws Exception
     */
    public List<String> selectConsultStatusList(SRMJON0040VO vo) throws Exception {
        return queryForListString("SRMJON0040.selectConsultStatusList", vo);
    }

    /**
     * 입점 승인 상태 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public int selectConsultStatus(SRMJON0040VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0040.selectConsultStatus", vo);
    }

    /**
     * 입점 승인 상태 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public int selectConsultIngStatus(SRMJON0040VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0040.selectConsultIngStatus", vo);
    }


    /**
     * 잠재업체 담당잡정보 등록
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void insertHiddenCompInfoSSUPI(SRMJON0040VO vo) throws Exception {
        insert("SRMJON0040.insertHiddenCompInfoSSUPI", vo);
    }

    /**
     * 잠재업체 담당잡정보 등록
     * @param SRMJON0040VO
     * @throws Exception
     */
    public void updateHiddenCompInfoSSUPI(SRMJON0040VO vo) throws Exception {
        update("SRMJON0040.updateHiddenCompInfoSSUPI", vo);
    }

    /**
     * 입점 승인 상태 조회
     * @param SRMJON0040VO
     * @throws Exception
     */
    public int selecthiddenCompSSUPICount(SRMJON0040VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0040.selecthiddenCompSSUPICount", vo);
    }
    
    /**
     * 입점 업체 계약 동의 구분
     * @param SRMJON0040VO
     * @throws Exception
     */
    public String selecthiddenCompAgreeType(SRMJON0040VO vo) throws Exception {
        return (String)queryForString("SRMJON0040.selecthiddenCompAgreeType", vo);
    }

}




