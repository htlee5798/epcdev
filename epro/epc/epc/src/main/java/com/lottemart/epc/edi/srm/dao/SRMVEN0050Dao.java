package com.lottemart.epc.edi.srm.dao;


import com.lottemart.epc.edi.srm.model.SRMVEN005001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0050VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SRM 성과평가 Dao
 * 
 * @author LEE SANG GU
 * @since 2018.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2018.11.05  	LEE SANG GU		 	최초 생성
 *
 * </pre>
 */
@Repository("srmven0050Dao")
public class SRMVEN0050Dao extends SRMDBConnDao {
	/**
	 * SRM 성과평가 결과 LIST count
	 * @param SRMVEN0050VO
	 * @return int
	 * @throws Exception
	 */	
	public int selectSrmEvalResCount(SRMVEN0050VO vo) throws Exception {
		return (Integer) queryForInteger("SRMVEN0050.selectSrmEvalResCount", vo);
	}
	
	/**
	 * SRM 성과평가 결과 LIST 
	 * @param SRMVEN0050VO
	 * @return int
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public List<SRMVEN0050VO> selectSrmEvalRes(SRMVEN0050VO vo) throws Exception {
		return queryForList("SRMVEN0050.selectSrmEvalRes", vo);
	}
	
	/**
	 * SRM 성과평가 상세조회 팝업 LIST 
	 * @param SRMVEN005001VO
	 * @return int
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public List<SRMVEN005001VO> selectSrmEvalResDetailPopup(SRMVEN005001VO vo) throws Exception {
		return queryForList("SRMVEN0050.selectSrmEvalResDetailPopup", vo);		
	}
	
	
}




