package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMMNT0020VO;
import lcn.module.framework.base.AbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
@Repository("srmmnt002001Dao")
public class SRMMNT002001Dao extends AbstractDAO {

	/**
	 * 사업자 번호로 업체코드 조회
	 * @param String
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	public List<SRMMNT0020VO> selectCeoLoginVenCdLIst(String irsNo) throws Exception {
		return getSqlMapClientTemplate().queryForList("SRMMNT0020.selectCeoLoginVenCdLIst", irsNo);
	}

}




