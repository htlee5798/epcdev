package com.lottemart.epc.edi.comm.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.comm.model.ScheduledVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;

import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : CommonDao.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		  	  수정자           		수정내용
 *  -------    		---------------    ---------------------------
 * 2025. 04. 09. 	 park jong gyu				최초생성
 * 
 * </pre>
 */
@Repository("scheduledDao")
public class ScheduledDao extends AbstractDAO {
	
	/**
	 * ECS <> EPC IF 진행 상태 조회(계약완료)
	 * @param paramVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<ScheduledVO> selectProEventIfStsList(ScheduledVO scheduledVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ScheduledVO>)getSqlMapClientTemplate().queryForList("Scheduled.selectProEventIfStsList", scheduledVO);
	}
	
	/**
	 * ECS <> EPC IF 진행 상태 조회(삭제 or 폐기)
	 * @param paramVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<ScheduledVO> selectProEventIfDelStsList(ScheduledVO scheduledVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ScheduledVO>)getSqlMapClientTemplate().queryForList("Scheduled.selectProEventIfDelStsList", scheduledVO);
	}
	
	/**
	 * 행사 header 정보 수정
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateProdEvnt(ScheduledVO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Scheduled.updateProdEvnt", paramVo);
	}
	
	/**
	 * if_return 상태값 update
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateIfReturn(ScheduledVO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Scheduled.updateIfReturn", paramVo);
	}
	
	/**
	 * RFC Call Data 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<LinkedHashMap> selectEvntRfcCallData(ScheduledVO vo) throws Exception {
		return this.list("Scheduled.selectEvntRfcCallData", vo);
	}
	
	/**
	 * 원가변경요청 계약번호 업데이트
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public int updateProdChgCostDcNum(ScheduledVO paramVo) throws Exception {
		return (int) getSqlMapClientTemplate().update("Scheduled.updateProdChgCostDcNum", paramVo);
	}
	
	/**
	 * 신상품확정요청_자동발송 플래그 업데이트
	 * @param paramVo
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcNewProdRegAutoSendFg(NEDMPRO0040VO nEDMPRO0040VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("Scheduled.updateTpcNewProdRegAutoSendFg", nEDMPRO0040VO);
	}
	
	/**
	 * main 파트너사 실적 MERGE
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateMainVenPrfr(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Scheduled.updateMainVenPrfr", map);
	}
	
	/**
	 * main 파트너사 매입액 MERGE
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateMainVenBuy(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Scheduled.updateMainVenBuy", map);
	}
	
	/**
	 * main 신상품 실적 MERGE
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateMainNewProdPrfr(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Scheduled.updateMainNewProdPrfr", map);
	}
	
	/**
	 * 특정 연도의 휴일정보 삭제
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int deleteTpcHolidayYear(Map<String, Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().delete("Scheduled.deleteTpcHolidayYear", paramMap);
	}
	
	/**
	 * 휴일 및 공휴일 등록
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertTpcHoliday(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().insert("Scheduled.insertTpcHoliday", paramMap);
	}
	
	/**
	 * 오늘이 휴일인지 체크
	 * @return String
	 * @throws Exception
	 */
	public String selectTodayIsHolidayYn() throws Exception{
		return (String) getSqlMapClientTemplate().queryForObject("Scheduled.selectTodayIsHolidayYn");
	}
	
}
