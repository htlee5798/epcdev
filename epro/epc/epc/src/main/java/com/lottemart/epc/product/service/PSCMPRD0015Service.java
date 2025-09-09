package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.product.model.PSCMPRD0015VO;
import com.lottemart.common.util.DataMap;

/**
 * 
 * @author     : wjChoi
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.service
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    --------------------------- 
 *   
 * @version : 
 * </pre>
 */
public interface PSCMPRD0015Service {

	/**
	 * 
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.service
	 * @MethodName  : selectRepProdCdList
	 * @author     : wjChoi
	 * @Description : 대표상품코드 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
public List<PSCMPRD0015VO> selectRepProdCdList(Map<Object, Object> paramMap) throws Exception;

/**
 * Desc : 상품단품 정보 중복 카운트를 조회하는 메소드
 * @Method Name : selectProductItemDupCount
 * @param paramMap
 * @return
 * @throws Exception
 * @param 
 * @return 
 * @exception Exception
 */
public List<DataMap> selectProductItemDupCount(DataMap paramMap) throws Exception;


/**
 * Desc : 상품단품 정보를 조회하는 메소드
 * @Method Name : selectProductItemList
 * @param paramMap
 * @return
 * @throws Exception
 * @param 
 * @return 
 * @exception Exception
 */
public List<PSCMPRD0015VO> selectProductItemList(DataMap paramMap) throws Exception;


/**
 * 
 * @see selectRepProdCdComboList
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : selectRepProdCdComboList
 * @author     : wjChoi
 * @Description : 협력사별 대표판매코드 목록 조회 
 * @param paramMap
 * @return
 * @throws Exception
 */
public List<Map<String, Object>> selectRepProdCdComboList(Map<Object, Object> paramMap) throws Exception;
/**
 * 
 * @see selectTprStoreItemPriceList
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : selectTprStoreItemPriceList
 * @author     : wjChoi
 * @Description : TPR_SOTRE_ITEM_PRICE 조회
 * @param paramMap
 * @return
 * @throws Exception
 */
public List<Map<String, Object>> selectTprStoreItemPriceList(Map<Object, Object> paramMap) throws Exception;

/**
 * 
 * @see insertRepProdCd
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : insertRepProdCd
 * @author     : wjChoi
 * @Description : 대표상품코드 등록
 * @param pbomprd0034vo
 * @throws Exception
 */
public void insertRepProdCd(Map<Object, Object> paramMap) throws Exception;

/**
 * 
 * @see updateRepProdCd
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : updateRepProdCd
 * @author     : wjChoi
 * @Description : 대표상품코드 수정
 * @param pbomprd0034vo
 * @throws Exception
 */
public void updateRepProdCd(Map<Object, Object> paramMap) throws Exception;

/**
 * 
 * @see deleteRepProdCd
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : deleteRepProdCd
 * @author     : wjChoi
 * @Description : 대표상품코드 삭제
 * @param gdReq
 * @param request
 * @throws Exception
 */
public int deleteRepProdCd(HttpServletRequest request) throws Exception;

/**
 * 
 * @see selectRepProdCdInfo
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : selectRepProdCdInfo
 * @author     : wjChoi
 * @Description : 대표상품코드 상세조회
 * @param paramMap
 * @return
 * @throws Exception
 */
public List<DataMap> selectRepProdCdInfoList(Map<String, String> paramMap) throws Exception;

/**
 * 
 * @see getBeforeChangePrc
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : getBeforeChangePrc
 * @author     : wjChoi
 * @Description : 변경 전 가격을 가져온다 
 * @param paramMap
 * @return List<DataMap>
 * @throws Exception
 */
public List<DataMap> getBeforeChangePrc(Map<String, String> paramMap) throws Exception;

/**
 * 
 * @see selectProdInfo
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : selectProdInfo
 * @author     : wjChoi
 * @Description : 그리드에서 상품코드리스트를 가져와 각각 상품코드마다 가격정보를 가져온다.
 * @param paramMap
 * @param 
 * @return List<DataMap>
 * @throws Exception
 */
public List<DataMap>selectProdInfo(Map<Object, Object> paramMap) throws Exception;

/**
 * 
 * @see insertRepProdCdList
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : insertRepProdCdList
 * @author     : wjChoi
 * @Description : 상품목록조회.
 * @param PSCMPRD0015VO
 * @param 
 * @return Map<String, String>
 * @throws Exception
 */
public Map<String, String> insertRepProdCdList(List<PSCMPRD0015VO> list) throws Exception;

/**
 * 
 * @see pscmprd0015List
 * @Locaton    : com.lottemart.epc.product.service
 * @MethodName  : pscmprd0015List
 * @author     : wjChoi
 * @Description : 상품목록조회.
 * @param paramMap
 * @param 
 * @return List<Map<String, Object>>
 * @throws Exception
 */
public List<Map<String, Object>> pscmprd0015List(Map<Object, Object> paramMap) throws Exception;
	
}
