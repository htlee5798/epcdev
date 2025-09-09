package com.lottemart.epc.common.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.common.tag.dao.TagLibraryDao;

//import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * @Description : select box에 들어가는 option 태그들을 만들어주는 tag library.
 * [사용예]
 *    <%@ taglib prefix="ccutl" uri="/ccutl" %>
 *    <ws:option codeGroupId="BND02" selectedCd="02" showCode="true"/>
 *    여기서 codeGroupId(필수) : MAJOR_CD 값
 *    selectedCd : selected될 MINOR_CD 값
 *    showCode : 코드도 같이 보여줄지의 여부 (true일 경우에는 보여줌.)
 * @Modification Information
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 11. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

@SuppressWarnings("serial")
public class SelectOptionTag extends TagSupport 
{

	private static final Logger logger = LoggerFactory.getLogger(SelectOptionTag.class);
	
    private String codeGroupId;
	private String selectedCd;
	private String showCode;
	
	@Override
	public int doStartTag() throws JspException
	{
		// TODO Auto-generated method stub
		return Tag.SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		
		try
		{
			String outputStr = SelectOptionTagUtil.generateOptionsTag(getOptionList(), selectedCd, "true".equals(showCode));
			out.println(outputStr);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		
		return Tag.EVAL_PAGE;
	}
	
	List<OptionTagVO> getOptionList()
	{
		TagLibraryDao dao = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean(TagLibraryDao.class);
		List<OptionTagVO> list = dao.selectCodeList(codeGroupId);
		
		return list;
	}
	
	@Override
	public void release()
	{		
		super.release();
		this.codeGroupId = null;
		this.selectedCd  = null;
		this.showCode    = null;
	}
	
	public String getCodeGroupId()
	{
		return codeGroupId;
	}

	public void setCodeGroupId(String codeGroupId)
	{
		this.codeGroupId = codeGroupId;
	}

	public String getSelectedCd()
	{
		return selectedCd;
	}

	public void setSelectedCd(String selectedCd)
	{
		this.selectedCd = selectedCd;
	}

	public String getShowCode()
	{
		return showCode;
	}

	public void setShowCode(String showCode)
	{
		this.showCode = showCode;
	}

}
