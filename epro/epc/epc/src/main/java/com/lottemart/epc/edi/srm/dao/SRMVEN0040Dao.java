package com.lottemart.epc.edi.srm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.model.SRMVEN004001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0040VO;

/**
 * 품질경영평가조치
 * 
 * @author SHIN SE JIN
 * @since 2016.10.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmven0040Dao")
public class SRMVEN0040Dao extends SRMDBConnDao {
	
	/**
	 * 품질경영평가조치리스트 카운트
	 * @param SRMVEN0040VO
	 * @return int
	 * @throws Exception
	 */
	public int selectEvalInfoListCount(SRMVEN0040VO vo) throws Exception {
		return (Integer) queryForInteger("SRMVEN0040.selectEvalInfoListCount", vo);
	}
	
	/**
	 * 품질경영평가조치리스트 조회
	 * @param SRMVEN0040VO
	 * @return List<SRMVEN0040VO>
	 * @throws Exception
	 */
	public List<SRMVEN0040VO> selectEvalInfoList(SRMVEN0040VO vo) throws Exception {
		return queryForList("SRMVEN0040.selectEvalInfoList", vo);
	}

	/**
	 * 품질경영평가조치내역 조회
	 * @param SRMVEN004001VO
	 * @return List<SRMVEN004001VO>
	 * @throws Exception
	 */
	public List<SRMVEN004001VO> selectCorrectiveActionList(SRMVEN004001VO vo) throws Exception {
		return queryForList("SRMVEN0040.selectCorrectiveActionList", vo);
	}
	
	/**
	 * 품질경영평가조치 상세정보 조회
	 * @param SRMVEN004001VO
	 * @return SRMVEN004001VO
	 * @throws Exception
	 */
	public SRMVEN004001VO selectCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception {
		return (SRMVEN004001VO)queryForObject("SRMVEN0040.selectCorrectiveActionDetail", vo);
	}

	/**
	 * 품질경영평가조치 정보 수정
	 * @param SRMVEN004001VOo
	 * @throws Exception
	 */
	public void updateCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception {
		update("SRMVEN0040.updateCorrectiveActionDetail", vo);
	}

	/**
	 * 품질경영평가조치 정보 삭제
	 * @param SRMVEN004001VO
	 * @throws Exception
	 */
	public void updateCorrectiveActionDetaildel(SRMVEN004001VO vo) throws Exception {
		update("SRMVEN0040.updateCorrectiveActionDetaildel", vo);
	}
	
	
}




