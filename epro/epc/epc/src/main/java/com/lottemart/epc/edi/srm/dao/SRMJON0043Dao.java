package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 정보확인 Dao
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
@Repository("srmjon0043Dao")
public class SRMJON0043Dao extends SRMDBConnDao {


    /**
     * 잠재업체 정보 조회
     * @param SRMJON0043VO
     * @return SRMJON0043VO
     * @throws Exception
     */
    public SRMJON0043VO selectHiddenComp(SRMJON0043VO vo) throws Exception {
        return (SRMJON0043VO)queryForObject("SRMJON0043.selectHiddenComp", vo);
    }

    /**
     * 잠재업체 입점상담신청 수정
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void updateHiddenCompReq(SRMJON0043VO vo) throws Exception {
        update("SRMJON0043.updateHiddenCompReq", vo);
    }

    /**
     * 입점상담 신청 삭제
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void deleteHiddenCompReq(SRMJON0043VO vo) throws Exception {
        delete("SRMJON0043.deleteHiddenCompReq", vo);
    }

    /**
     * 입점상담 신청 삭제
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void deleteHiddenCompReqSSUPI(SRMJON0043VO vo) throws Exception {
        delete("SRMJON0043.deleteHiddenCompReqSSUPI", vo);
    }

    /**
     * 입점상담 신청 정보 존재여부 check
     * @param SRMJON0043VO
     * @return SRMJON0043VO
     * @throws Exception
     */
    public int selectHiddenCompReqCheck(SRMJON0043VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0043.selectHiddenCompReqCheck", vo);
    }

    /**
     * 잠재업체 정보 삭제
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void deleteHiddenComp(SRMJON0043VO vo) throws Exception {
        delete("SRMJON0043.deleteHiddenComp", vo);
    }

    /**
     * 입점상담 신청 접수 MD메일 LIST 조회
     * @param SRMJON0043VO
     * @return List<SRMJON0043VO>
     * @throws Exception
     */
    public List<SRMJON0043VO> selectHiddenCompReqEMSList(SRMJON0043VO vo) throws Exception {
        return queryForList("SRMJON0043.selectHiddenCompReqEMSList", vo);
    }


    /**
     * 삭제된 대분류인지 체크
     * @param SRMJON0043VO
     * @return List<SRMJON0043VO>
     * @throws Exception
     */
    public int selectCatLv1CodeCheck(SRMJON0043VO vo) throws Exception {
        return (Integer)queryForInteger("SRMJON0043.selectCatLv1CodeCheck", vo);
    }
}




