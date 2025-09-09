package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMVEN0010VO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * SRM > SRM정보 > 파트너사정보변경 Dao
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           			수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.29  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
@Repository("srmven0010Dao")
public class SRMVEN0010Dao extends SRMDBConnDao {
	
	/**
	 * 협력업체정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectVendorList(SRMVEN0010VO vo) throws Exception {
		return queryForList("SRMVEN0010.selectVendorList", vo);
	}

	/**
	 * 파트너사 수정정보 확정대상 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectVenInfoConfirmList(SRMVEN0010VO vo) throws Exception {
		return queryForListHashMap("SRMVEN0010.selectVenInfoConfirmList", vo);
	}
	
	/**
	 * 파트너사 수정정보 확정요청
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void updateVenInfoConfirm(SRMVEN0010VO vo) throws Exception {
		update("SRMVEN0010.updateVenInfoConfirm", vo);
	}
	
	/**
	 * 파트너사 수정정보 Insert
	 * @param vo
	 * @throws Exception
	 */
	public void insertVenInfo(SRMVEN0010VO vo) throws Exception {
		insert("SRMVEN0010.insertVenInfo", vo);
	}
	
	/**
	 * 파트너사 수정정보 Update
	 * @param vo
	 * @throws Exception
	 */
	public void updateVenInfo(SRMVEN0010VO vo) throws Exception {
		update("SRMVEN0010.updateVenInfo", vo);
	}

	/**
	 * 파트너사 수정정보 Delete
	 * @param vo
	 * @throws Exception
	 */
	public void deleteVenInfo(SRMVEN0010VO vo) throws Exception {
		delete("SRMVEN0010.deleteVenInfo", vo);
	}
	
	/**
	 * 선택한 파트너사 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectSrmVenList(SRMVEN0010VO vo) throws Exception {
		return queryForList("SRMVEN0010.selectSrmVenList", vo);
	}

	/**
	 * 파트너사 정보변경 MD EMAIL LIST
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectVenInfoConfirmMDEmailList(SRMVEN0010VO vo) throws Exception {
		return queryForList("SRMVEN0010.selectVenInfoConfirmMDEmailList", vo);
	}

}




