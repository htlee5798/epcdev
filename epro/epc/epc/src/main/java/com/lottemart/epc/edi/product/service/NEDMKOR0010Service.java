package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.Map;

import com.lottemart.epc.edi.product.model.NEDMKOR0010VO;


/**
 * @Class Name : NEDMKOR0010Service
 * @Description : 코리안넷 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21		SONG MIN KYO	최초생성
 * </pre>
 */

public interface NEDMKOR0010Service {
	
	/**
	 * 선택한 협력업체코드의 사업자번호 가져오기
	 * @param entpCd
	 * @return
	 * @throws Exception
	 */
	public String selectBmanNo(String entpCd) throws Exception;
	
	/**
	 * 해당일 최근 토큰정보 조회
	 * @return
	 * @throws Exception
	 */
	public String selectAuthToken() throws Exception;
	
	/**
	 * 인증토큰 저장
	 * @param paramHm
	 * @return
	 * @throws Exception
	 */
	public void insertAuthToken(HashMap authHm) throws Exception;
	
	/**
	 * 코리안넷 인증
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAuthToken() throws Exception;
	
	/**
	 * 다운로드할 이미지 정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDownloadZipFileInfo(NEDMKOR0010VO vo) throws Exception;
	
	/**
	 * 코리안넷 이미지 다운로드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String getDownloadZipFile(NEDMKOR0010VO vo) throws Exception;
	
	/**
	 * ZIP 파일 압축풀기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String unZipFile(NEDMKOR0010VO vo) throws Exception;
	
	/**
	 * 파일이동
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String moveFile(NEDMKOR0010VO vo) throws Exception;
	
}
