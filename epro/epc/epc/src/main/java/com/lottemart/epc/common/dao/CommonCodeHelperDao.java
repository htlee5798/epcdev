/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 9. 7. 오전 10:50:31
 * @author      : yhs8462, 윤해성(yhs8462@lotte.net)
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package com.lottemart.epc.common.dao;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

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
 * 2011. 9. 7. 오전 10:50:31 yhs8462, 윤해성(yhs8462@lotte.net)
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository
public class CommonCodeHelperDao extends AbstractDAO
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
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> selectCommonCodeList(String selectedCode)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.selectCommonCodeList", selectedCode);
	}

	/**
	 * Desc : 
	 * @Method Name : getCommonCodeColumn
	 * @param param
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public String getCommonCodeColumn(CommonCodeModel param)
	{
		return (String)this.selectByPk("CommonCodeHelperDao.getCommonCodeColumn", param);
	}
	
	
	/**
	 * Desc : 
	 * @Method Name : storeSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> storeSelectList(String selectedCode)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.storeSelectList", selectedCode);
	}
	
	/**
	 * Desc : 
	 * @Method Name : storeSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> excelUploadInfoList(String selectedCode)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.excelUploadInfoList", selectedCode);
	}
	
	/**
	 * Desc : 
	 * @Method Name : storeSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> excelRtnUploadInfoList(String selectedCode)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.excelRtnUploadInfoList", selectedCode);
	}
	
	/**
	 * Desc : AS-IS
	 * @Method Name : venStoreSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> venStoreSelectList(HashBox vo)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.venStoreSelectList", vo);
	}
	

	/**
	 * Desc : AS-IS
	 * @Method Name : venStoreSelectList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<OptionTagVO> venOrdStoreSelectList(HashBox vo)
	{
		return (List<OptionTagVO>)this.list("CommonCodeHelperDao.venOrdStoreSelectList", vo);
	}
}
