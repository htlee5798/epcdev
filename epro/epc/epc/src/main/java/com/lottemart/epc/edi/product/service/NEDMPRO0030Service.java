package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;


/**
 * @Class Name : NEDMPRO0030Service
 * @Description : 온라인신상품등록(배송정보) Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */

public interface NEDMPRO0030Service {
	/**
	 * 배송정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNewOnlineDeliveryList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 업체 주소정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectAddrMgrList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 배송정보 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void newOnlineDeliverySave(DataMap paramMap) throws Exception;
	
	/**
	 * 공통배송비정보1 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 공통배송비정보2 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectVendorDeliInfo(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 전상법 템플릿 selectBox 데이터
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempList(DataMap paramMap) throws Exception;
	
	/**
	 * 전상법 템플릿 VALUES
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempValList(DataMap paramMap) throws Exception;
	
	
	/**
	 * 온라인전용 상품 복사
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public DataMap newMakeCopycatProduct(HttpServletRequest request) throws Exception;

	/**
	 * 업체 거래형태 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selecttrdTypFgSel(DataMap paramMap) throws Exception;
	
	/**
	 * EC 표준 카테고리 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcStandardCategory(DataMap paramMap) throws Exception;
	
	/**
	 * EC 표준 카테고리 매핑
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception;

	/**
	 * EC 전시 카테고리 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcDisplayCategory(DataMap paramMap) throws Exception;

	/**
	 * EC 속성 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception;
	
	/**
	 * EC 전시 카테고리 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception;
	
	/**
	 * EC 롯데ON 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountDispLton(DataMap paramMap) throws Exception;
	
	/**
	 * EC 롯데마트몰 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountDispMart(DataMap paramMap) throws Exception;
	
	/**
	 * EC 표준카테고리 - 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountEcProductAttrId(DataMap paramMap) throws Exception;
	
	/**
	 * EC 표준카테고리 - 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectCountEcProductAttrValId(DataMap paramMap) throws Exception;
	
}
