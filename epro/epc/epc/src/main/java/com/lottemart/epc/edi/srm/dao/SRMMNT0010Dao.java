package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMMNT0010VO;
import org.springframework.stereotype.Repository;

/**
 * 대표자 SRM 모니터링 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Repository("SRMMNT0010Dao")
public class SRMMNT0010Dao extends SRMDBConnDao {



	/**
	 * CEO로그인 및 MAX로그인수 조회
	 * @param  vo
	 * @return SRMMNT0010VO
	 * @throws Exception
	 */
	public SRMMNT0010VO selectCEOSRMmoniteringLogin(SRMMNT0010VO vo) throws Exception {
		return (SRMMNT0010VO)queryForObject("SRMMNT0010.selectCEOSRMmoniteringLogin", vo);
	}


	/**
	 * 로그인수 update
	 * @throws Exception
	 */
	public void updateCEOSRMmoniteringLogin(SRMMNT0010VO vo) throws Exception {
		update("SRMMNT0010.updateCEOSRMmoniteringLogin", vo);
	}


	/**
	 * 대표자 로그인 로그 insert
	 * @throws Exception
	 */
	public void insertCEOSRMmoniteringLoginLog(SRMMNT0010VO vo) throws Exception {
		insert("SRMMNT0010.insertCEOSRMmoniteringLoginLog", vo);
	}
}




