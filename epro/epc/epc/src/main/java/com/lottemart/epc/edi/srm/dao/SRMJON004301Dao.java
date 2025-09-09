package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import org.springframework.stereotype.Repository;

/**
 * 입점상담 / 입점상담신청  / 입점상담 신청 EMAIL 전송 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.08.23  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon004301Dao")
public class SRMJON004301Dao extends SRMEMSDBConnDao {

    /**
     * 입점상담 신청 email 등록
     * @param SRMJON0043VO
     * @throws Exception
     */
    public void insertHiddenCompReqEMS(SRMJON0043VO vo) throws Exception {
        getSqlMapClientTemplate().insert("SRMJON0043.insertHiddenCompReqEMS", vo);
    }



}




