package com.lottemart.epc.edi.product.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.product.model.NEDMPRO0180VO;

public interface NEDMPRO0180Service {
	
	/**
	 * PB상품 리스트 조회
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0180VO> selectPbProdList(NEDMPRO0180VO vo) throws Exception;
	
	/**
	 * 특정 PB상품 성적서 정보 조회
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0180VO selectPbProdReportInfo(NEDMPRO0180VO vo) throws Exception;
	
	/**
	 * 성적서 정보 업데이트
	 * @return
	 * @throws Exception
	 */
	public boolean updateReportFile(NEDMPRO0180VO reportVO) throws Exception;
	
	/**
	 * 특정 성적서 정보 삭제
	 * @return
	 * @throws Exception
	 */
	public boolean deleteReportFile(NEDMPRO0180VO reportVO) throws Exception;
	
	
	/**
	 * 성적서 업로드 필요 개수 확인
	 * @return
	 * @throws Exception
	 */
	public Integer countNotValidPbProdReport(NEDMPRO0180VO reportVO) throws Exception;
	
	
	/**
	 * PB 상품에 등록된 성적서 정보 데이터 개수
	 * @return
	 * @throws Exception
	 */
	public Integer countReportFileForAProd(NEDMPRO0180VO reportVO) throws Exception;

	/**
	 * 성적서 관리부서 조회
	 * @return
	 * @throws Exception
	 */
	public List<String> selectAdminDeptPbReport(String adminId) throws Exception;
	
	/**
	 * 성적서 다운로드
	 * @return
	 * @throws Exception
	 */
	public boolean downloadReportFile(NEDMPRO0180VO reportVO, HttpServletResponse response) throws Exception;
	
	public String getTodayFormatYymmdd() throws Exception;
}
