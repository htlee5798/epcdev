package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0043VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0044VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0045VO;


/**
 * @Class Name : NEDMPRO0040Service
 * @Description : 임시보관함 SERVICE
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.27		SONG MIN KYO	최초생성
 * </pre>
 */

public interface NEDMPRO0040Service {	
	
	/**
	 * 임시보관함 LIST
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0040VO> selectImsiProductList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 상품의 변형속성 리스트 조회
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0042VO> selectNewSrcmkCd(Map<String,Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 임시보관함 선택상품 삭제
	 * @param nEDMPRO0040VO
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteImsiProductList(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 임시보관함 상품 확정
	 * @param nEDMPRO0040VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateImsiProductList(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 임시보관함 상품 변형속성 정보
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0043VO> selectNewProductDetailVarAttInfo(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 임시보관함 상품상세보기 온라인전용 88코드, 이익률 조회
	 * @param entpCode
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0044VO> selectOnlineRepresentProdctList(String entpCode) throws Exception;
	
	/**
	 * 임시보관함 상품상세보기 [온라인전용] 단품정보 리스트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0045VO> selectOnlineItemListIntemp(String pgmId) throws Exception;
	
	/**
	 * 출고지/반품지 등록 여부 확인
	 * @param strings
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkAddressInfo(String[] vendorIds) throws Exception;
	
	/**
	 * 임시보관함 신규상품 확정요청 > BOS API 전송
	 * @param nEDMPRO0040VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateImsiProductListFixSendBos(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 임시보관함 신규상품 확정요청 > ERP Proxy 전송
	 * @param nEDMPRO0040VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateImsiProductListFixSendErp(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 임시보관함 신규상품 확정요청 > 전송대상별 신상품 LIST 추출
	 * @param nEDMPRO0040VO
	 * @return List<NEDMPRO0040VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0040VO> selectNewTmpProductInfoSendGbn(NEDMPRO0040VO nEDMPRO0040VO) throws Exception;
	
	/**
	 * ESG 파일 STFP 전송 (EPC to PO)
	 * @param paramVo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateSendSftpEsgFile(NEDMPRO0040VO paramVo) throws Exception;
}
