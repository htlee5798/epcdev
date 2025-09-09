/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 9. 7. 오전 10:48:38
 * @author      : yhs8462, 윤해성(yhs8462@lotte.net)
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package com.lottemart.epc.common.service;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;

import com.lottemart.epc.common.model.CommonCodeModel;
import com.lottemart.epc.common.model.OptionTagVO;

/**
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7. 오전 10:48:38 yhs8462, 윤해성(yhs8462@lotte.net)
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface CommonCodeHelperService
{

	/**
	 * Desc : 
	 * @Method Name : selectCommonCodeList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> selectCommonCodeList(String selectedCode);

	/**
	 * Desc : 
	 * @Method Name : getCommonCodeColumn
	 * @param param
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	String getCommonCodeColumn(CommonCodeModel param);

	/**
	 * Desc : 
	 * @Method Name : storeSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> storeSelectList(String selectedCode);

	/**
	 * Desc : 
	 * @Method Name : excelUploadInfoList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> excelUploadInfoList(String selectedCode);
	
	/**
	 * Desc : 
	 * @Method Name : excelRtnUploadInfoList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> excelRtnUploadInfoList(String selectedCode);
	
	/**
	 * Desc : AS-IS
	 * @Method Name : venStoreSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> venStoreSelectList(HashBox vo) throws Exception;
	
	/** AS-IS
	 * Desc : 
	 * @Method Name : venOrdStoreSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	List<OptionTagVO> venOrdStoreSelectList(HashBox vo) throws Exception;
}
