package com.lottemart.epc.common.tag;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.lottemart.epc.common.model.OptionTagVO;


public class SelectOptionTagUtil
{
	public static String generateOptionsTag(List<OptionTagVO> list, String selectedCode, boolean showCode)
	{
		StringBuilder sb = new StringBuilder();
		
		if (list==null || list.size()==0)
		{
			return "";
		}
		
		for (OptionTagVO vo : list)
		{
			String selectedStr = (selectedCode!=null && selectedCode.equals(vo.code)) ? " selected " : "";
			String showStr = showCode ? StringEscapeUtils.escapeHtml("[" + vo.code + "] " + vo.name) : StringEscapeUtils.escapeHtml(vo.name);
			sb.append("<option value=\"").append(vo.code).append("\"").append(selectedStr)
			   .append(">").append(showStr).append("</option>");
		}
		
		return sb.toString();
	}
	
	public static String generateOptionsTag(List<OptionTagVO> list, String selectedCode)
	{
		return generateOptionsTag(list, selectedCode, false);
	}
}
