package com.lottemart.epc.edi.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.NewProduct;


/**
 * @Class Name : NEDMPRO0060Service
 * @Description : 상품일괄등록 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.05.18		projectBOS32 	최초생성
 * </pre>
 */

public interface NEDMPRO0060Service {
	/**
	 * 상품기본정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProduct(DataMap paramMap) throws Exception;
	
	/**
	 * 상품등록여부
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public int selectImsiProductCnt(DataMap paramMap) throws Exception;
	
	/**
	 * 협력업체의 거래 중지여부 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public int selectCountVendorStopTrading(String vendorCode) throws Exception;
	
	/**
	 * 협력업체의 거래유형 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public NewProduct selectNewProductTradeType(String vendorCode) throws Exception;
	
	/**
	 * 온라인 대표상품코드 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectOnlineRepresentProdctInfo(DataMap paramMap) throws Exception;
	
	/**
	 * 이미지 업로드용 상품명 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProductNm(DataMap paramMap) throws Exception;
	
	/**
	 * 전상법 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchNorProdCode(DataMap paramMap) throws Exception;
	
	/**
	 * KC인증 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchKcProdCode(DataMap paramMap) throws Exception;
	
	/**
	 * 상품등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertProductInfo(DataMap paramMap) throws Exception;
	
	/**
	 * EC 카테고리 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertEcProductCategory(DataMap paramMap) throws Exception;
	
	/**
	 * 단품옵션 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertProductItemInfo(DataMap paramMap) throws Exception;
	
	/**
	 * 단품속성 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void insertEcProductAttribute(DataMap paramMap) throws Exception;
	
	/**
	 * 전상법 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveProdAddDetail(DataMap paramMap) throws Exception;
	
	/**
	 * KC인증 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveProdCertDetail(DataMap paramMap) throws Exception;
	
	/**
	 * 임시상품 테이블 update
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void updateNewProdRegDesc(DataMap paramMap) throws Exception;
	
	/**
	 * 추가설명 테이블 insert,update
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void saveNewProdDescr(DataMap paramMap) throws Exception;
	
	
	
	/* ************************** */
	//20181002 상품키워드 입력 기능 추가
	
	/**
	 * 전상법리스트
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectCodeInfo02() throws Exception;
	
	/**
	 * 제품안전인증리스트
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectCodeInfo03() throws Exception;	
	
	//20181002 상품키워드 입력 기능 추가
	/* ************************** */

	public int selectEcStdDispMappingCnt(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectEcStdDispMappingList(DataMap paramMap) throws Exception;
	
	public int selectEcStdAttrMappingCnt(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectEcStdAttrMappingList(DataMap paramMap) throws Exception;
	
}
