package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON0042VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 인증/신용정보 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.19  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon0042Dao")
public class SRMJON0042Dao extends SRMDBConnDao {

    /**
     * 잠재업체 신용정보 조회
     * @param SRMJON0042VO
     * @return SRMJON0042VO
     * @throws Exception
     */
    public SRMJON0042VO selectHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception {
        return (SRMJON0042VO)queryForObject("SRMJON0042.selectHiddenCompCreditInfo", vo);
    }

    /**
     * 잠재업체 신용정보 수정
     * @param SRMJON0042VO
     * @throws Exception
     */
    public void updateHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception {
        update("SRMJON0042.updateHiddenCompCreditInfo", vo);
    }

    /**
     * 잠재업체 입점신청 신용등급정보 수정
     * @param SRMJON0042VO
     * @throws Exception
     */
    public void updateHiddenCompCreditInfoReq(SRMJON0042VO vo) throws Exception {
        update("SRMJON0042.updateHiddenCompCreditInfoReq", vo);
    }

    /**
     * 파일정보 조회(CREDIT_ATTACH_NO)
     * @param String
     * @throws Exception
     */
    public String selectHiddenCompCreditAttachNoFileId(SRMJON0042VO vo) throws Exception {
        return (String)queryForObject("SRMJON0042.selectHiddenCompCreditAttachNoFileId", vo);
    }
    
    /**
     * 신용평가기관 정보조회(나이스디앤비)
     * @param vo
     * @return
     * @throws Exception
     */
    public SRMJON0042VO selectNicednbCreditInfo(SRMJON0042VO vo) throws Exception {
    	return (SRMJON0042VO) queryForObject("SRMJON0042.selectNicednbCreditInfo", vo);
    }

    /**
     * 신용평가기관 정보조회(이크레더블)
     * @param vo
     * @return
     * @throws Exception
     */
    public SRMJON0042VO selectEcredibleCreditInfo(SRMJON0042VO vo) throws Exception {
    	return (SRMJON0042VO) queryForObject("SRMJON0042.selectEcredibleCreditInfo", vo);
    }

    /**
     * 신용평가기관 정보조회(한국기업데이터)
     * @param vo
     * @return
     * @throws Exception
     */
    public SRMJON0042VO selectIntrCreditInfo(SRMJON0042VO vo) throws Exception {
    	return (SRMJON0042VO) queryForObject("SRMJON0042.selectIntrCreditInfo", vo);
    }
    
    /**
     * 신용평가기관별 정보조회
     * @param SRMJON0042VO
     * @return List<SRMJON0042VO>
     * @throws Exception
     */
    public List<SRMJON0042VO> selectCreditAllInfo(SRMJON0042VO vo) throws Exception {
    	return (List<SRMJON0042VO>) queryForList("SRMJON0042.selectCreditAllInfo", vo);
    }

}




