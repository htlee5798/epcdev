/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.util;

import org.apache.commons.beanutils.BeanUtils;

import xlib.cmc.GridData;
import xlib.cmc.GridHeader;

/**
 * @Class Name : WiseGridUtil
 * @Description : WiseGrid에 대한 유틸리티 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:06:41 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class WiseGridUtil {

	/**
	 * Desc : GridData 헤더 data를 VO객체에 담는다. 
	 * @Method Name : getWiseGridParamToDTO
	 * @param
	 * @param dto
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public static Object getWiseGridHeaderDataToObject(int index, GridData gdReq, Object bean) throws Exception{

		String name = "";
		String obj = "";
		String type = "";
		
		 GridHeader[] gh = gdReq.getHeaders();
		
		for(int j = 0; j < gh.length; j++){
			name = (gdReq.getHeaders())[j].getID();
			type = gdReq.getHeader(name).getDataType().toString();
			
			if("T".equals(type)){			// text
				obj = gdReq.getHeader(name).getValue(index);
			}else if("L".equals(type)){		// combo
				obj = gdReq.getHeader(name).getComboHiddenValues()[gdReq.getHeader(name).getSelectedIndex(index)];
			}else if("D".equals(type)){
				obj = gdReq.getHeader(name).getValue(index);
			}else if("N".equals(type)) {
				obj = gdReq.getHeader(name).getValue(index);
			}
			
			BeanUtils.setProperty(bean, name, obj);
		}
		
		return bean;
	}	

	/**
	 * Desc : WiseGrid 메시지 결과 처리 메소드
	 * @Method Name : getSendData
	 * @param size
	 * @param mode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public static String getSendData(int size, String mode) {
		StringBuffer sbData = new StringBuffer();
		if("save".equals(mode)){
			sbData.append(size);
			sbData.append(" 건의 데이터가 저장되었습니다.\n\n");
		} else if("delete".equals(mode)){
			sbData.append(size); 
			sbData.append(" 건의 데이터가 삭제되었습니다.\n\n");
		}
		return sbData.toString();
	}
}
