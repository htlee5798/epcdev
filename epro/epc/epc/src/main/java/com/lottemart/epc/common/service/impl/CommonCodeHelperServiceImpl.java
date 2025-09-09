/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 9. 7. 오전 10:48:57
 * @author      : yhs8462, 윤해성(yhs8462@lotte.net)
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package com.lottemart.epc.common.service.impl;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.dao.CommonCodeHelperDao;
import com.lottemart.epc.common.model.CommonCodeModel;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.common.service.CommonCodeHelperService;
/**
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7. 오전 10:48:57 yhs8462, 윤해성(yhs8462@lotte.net)
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service
public class CommonCodeHelperServiceImpl implements CommonCodeHelperService
{
	
	@Autowired
	CommonCodeHelperDao commonCodeHelperDao;

	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#selectCommonCodeList(java.lang.String)
	 */
	@Override
	public List<OptionTagVO> selectCommonCodeList(String selectedCode)
	{
		return (List<OptionTagVO>)commonCodeHelperDao.selectCommonCodeList(selectedCode);		
	}

	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#getCommonCodeColumn(com.lottemart.epc.common.model.CommonCodeModel)
	 */
	@Override
	public String getCommonCodeColumn(CommonCodeModel param)
	{
		return (String)commonCodeHelperDao.getCommonCodeColumn(param);
	}
	
	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#selectCommonCodeList(java.lang.String)
	 */
	@Override
	public List<OptionTagVO> storeSelectList(String selectedCode)
	{
		return (List<OptionTagVO>)commonCodeHelperDao.storeSelectList(selectedCode);		
	}
	
	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#selectCommonCodeList(java.lang.String)
	 */
	@Override
	public List<OptionTagVO> excelUploadInfoList(String selectedCode)
	{
		return (List<OptionTagVO>)commonCodeHelperDao.excelUploadInfoList(selectedCode);		
	}
	
	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#selectCommonCodeList(java.lang.String)
	 */
	@Override
	public List<OptionTagVO> excelRtnUploadInfoList(String selectedCode)
	{
		return (List<OptionTagVO>)commonCodeHelperDao.excelRtnUploadInfoList(selectedCode);		
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#venStoreSelectList(java.lang.String)
	 * AS-IS
	 */
	@Override
	public List<OptionTagVO> venStoreSelectList( HashBox vo) throws Exception{
		return (List<OptionTagVO>)commonCodeHelperDao.venStoreSelectList(vo);	
	}
	
	/* (non-Javadoc)
	 * @see com.lottemart.epc.common.service.CommonCodeHelperService#venStoreSelectList(java.lang.String)
	 * AS-IS
	 */
	@Override
	public List<OptionTagVO> venOrdStoreSelectList( HashBox vo) throws Exception{
		return (List<OptionTagVO>)commonCodeHelperDao.venOrdStoreSelectList(vo);	
	}
}
