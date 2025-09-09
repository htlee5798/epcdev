package com.lottemart.common.product.video.service;

import java.util.List;
import java.util.Map;

/**
 *  
 * @Class Name : TPRMPICService.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일          수정자	     수정내용
 *  -------         --------    ---------------------------
 * 2016. 6. 15. 오후 3:22:50   hyunjin
 * 
 * @Copyright (C) 2016 ~ 2016 lottemart All right reserved.
 */
public interface TPRMPICService {

	/**
	 * Desc : 상품 동영상 목록 조회
	 * @Method Name : selectTPRMPICList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<Object, Object>> selectTPRMPICList(Map<Object, Object> paramMap) throws Exception;
	
	
	/**
	 * Desc : 상품 동영상 삭제
	 * @Method Name : updateTPRMPICinfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateTPRMPICinfo(Map<Object, Object> paramMap) throws Exception;
	
	/**
	 * Desc : 상세화면에서 수정
	 * @Method Name : updateTPRMPICinfo2
	 * @param paramMap
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateTPRMPICinfo2(Map<Object, Object> paramMap) throws Exception;
	
	
	/**
	 * Desc : 상품 동영상 등록, 수정
	 * @Method Name : updateTPRMPICinfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int insertTPRMPICinfo(Map<Object, Object> paramMap) throws Exception;
	
	/**
	 * Desc : 목록에서 수정
	 * @Method Name : updateTPRMPICList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateTPRMPICList(Map<Object, Object> paramMap) throws Exception;
	
	/**
	 * Desc : 상품 동영상 상세조회
	 * @Method Name : selectTPRMPICInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public Map<Object, Object> selectTPRMPICInfo(Map<Object, Object> paramMap) throws Exception;
	
	/**
	 * Desc : 콜백시 데이터 조회
	 * @Method Name : selectTPRMPICInfo2
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public Map<Object, Object> selectTPRMPICInfo2(Map<Object, Object> paramMap) throws Exception;
	
}
