/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 9. 8. 오후 1:40:52
 * @author      : yhs8462, 윤해성(yhs8462@lotte.net)
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package com.lottemart.epc.common.model;

/**
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 8. 오후 1:40:52 yhs8462, 윤해성(yhs8462@lotte.net)
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@SuppressWarnings("serial")
public class CommonCodeModel extends BaseModel
{


	private String majorCD;
	private String minorCD;
	private String columnNM;
	/**
	 * @return the majorCD
	 */
	public String getMajorCD()
	{
		return majorCD;
	}
	/**
	 * @param majorCD the majorCD to set
	 */
	public void setMajorCD(String majorCD)
	{
		this.majorCD = majorCD;
	}
	/**
	 * @return the minorCD
	 */
	public String getMinorCD()
	{
		return minorCD;
	}
	/**
	 * @param minorCD the minorCD to set
	 */
	public void setMinorCD(String minorCD)
	{
		this.minorCD = minorCD;
	}
	/**
	 * @return the column
	 */
	public String getColumnNM()
	{
		return columnNM;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumnNM(String columnNM)
	{
		this.columnNM = columnNM;
	}
	

}
