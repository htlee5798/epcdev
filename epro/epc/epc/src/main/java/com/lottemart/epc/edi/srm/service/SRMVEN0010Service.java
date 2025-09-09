package com.lottemart.epc.edi.srm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.srm.model.SRMVEN0010VO;

/**
 * SRM > SRM정보 > 파트너사정보변경 Service
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
public interface SRMVEN0010Service {
	
	/**
	 * 협력업체정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectVendorList(SRMVEN0010VO vo) throws Exception;
	
	/**
	 * 파트너사 수정정보 Insert/Update
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String insertVenInfo(SRMVEN0010VO vo, HttpServletRequest request) throws Exception;
	
	/**
	 * 파트너사 수정정보 확정요청
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateVenInfoConfirm(SRMVEN0010VO vo, HttpServletRequest request) throws Exception;
	
	/**
	 * 파트너사 수정정보 Delete
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String deleteVenInfo(SRMVEN0010VO vo) throws Exception;
	
	/**
	 * 선택한 파트너사 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectSrmVenList(SRMVEN0010VO vo) throws Exception;
	
}
