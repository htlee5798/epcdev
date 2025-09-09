package com.lottemart.epc.edi.comm.taglibs.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagOldDao;


/**
 * select, input, checkbox, radio 생성 Tag
 */
@Service("customTagOldService")
public class CustomTagOldService extends TagSupport{

	private static final Logger logger = LoggerFactory.getLogger(CustomTagOldService.class);

	@Autowired
	private CustomTagOldDao customTagOldDao;


	private ConfigurationService config;

	/** 속성s */
	private String attr;

	/** Object Name */
	private String objName;

	/** Object ID */
	private String objId;

	/** Form name */
	private String formName;

	/** Componet Type */
	private String comType; // select , radio, checkbox

	/** selectbox의 optional 속성 */
	private String selSubComType; // select, option


	/** DB조회 시 parentCode value */
	private String parentCode;

	/** DB조회 시 childCode value */
	private String childCode;



	/** DB 조회시 subCode value	 */
	private String subCode;

	/** 선택되어 있는 값 표시**/
	private String selectParam;

	/** 첫번째 기본 옵션의 명칭**/
	private String defName;

	/** width 사이즈 **/
	private String width;

	/** Event */
	private String event;

	/** checkbox , radio 간격 */
	private String space;

	/** 바인드 자료 정보 (Default : CM_CODE table) CP  **/
	private String dataType;

	/** 첫번째 기본 옵션의 값**/
	private String defValue;


	public void setSpace(String space){
		if(space == null)
			this.space = "3";
		else
			this.space = space;
	}

	public void setEvent(String event){
		if(event == null)
			this.event = "";
		else
			this.event = event;
	}

	public void setSelSubComType(String selSubComType){
		if(selSubComType == null)
			this.selSubComType = "";
		else
			this.selSubComType = selSubComType;
	}

	public void setDefName(String defName){
			this.defName = defName;
	}

	public void setFormName(String formName){
		if(formName == null)
			this.formName = "";
		else
			this.formName = formName;
	}

	public void setSelectParam(String selectParam) {
		if(selectParam == null){
			this.selectParam = "";
		}else{
			this.selectParam = selectParam;
		}
	}

	public void setObjId(String objId) {
		if(objId == null)
			this.objId = "";
		else
			this.objId = objId;
	}

	public void setAttr(String attr) {
		if(attr == null)
			this.attr = "";
		else
			this.attr = attr;
	}


	public void setObjName(String objName) {
		if(objName == null)
			this.objName = "";
		else
			this.objName = objName;
	}


	public void setComType(String comType) {
		if(comType == null)
			this.comType = "";
		else
			this.comType = comType.toUpperCase();
	}




	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}


	public void setSubCode(String subCode){
		this.subCode = subCode;
	}

	public void setWidth(String width){
		this.width = width;
	}

	public void setDataType(String dataType){
		this.dataType = dataType;
	}


	public void release(){

	}


	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public int doStartTag()  {


		HttpServletRequest 					request = (HttpServletRequest)pageContext.getRequest();
		HttpServletResponse					response = (HttpServletResponse)pageContext.getResponse();
		HttpSession 						session = request.getSession();
		List 	rsList = null;

		HashBox								map = new HashBox();


		StringBuffer htm = new StringBuffer();
		try{

			//DAO Object
			customTagOldDao = 		(CustomTagOldDao) WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean("customTagOldDao");


			//DB 조회조건
			if(this.parentCode != null){
				map.put("parentCodeId", this.parentCode);
			}

			//DB 조회조건
			if(this.childCode != null){
				map.put("childCodeId", this.childCode);
			}

			//DB 조회조건
			if(this.subCode != null){
				map.put("subCodeId", this.subCode);
			}





			//DB조회하여 결과 List
			if(this.subCode == null || this.subCode.trim().length() > 0)
			{
				if(this.dataType != null && this.dataType.trim().length() > 0)
				{
					if(this.dataType.equals("CP"))
					{

						ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
						ConfigurationService config = (ConfigurationService)ctx.getBean("configurationService");

						EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

						if(epcLoginVO != null) {

							String[] ven_cd = epcLoginVO.getVendorId();
							HashMap hVenCD  = null;
							rsList 			= new ArrayList<HashBox>() ;

							for(int i=0; ven_cd.length >i; i++)
							{
								hVenCD = new HashBox();
								hVenCD.put("CODE_NAME", ven_cd[i]);
								hVenCD.put("CODE_ID", ven_cd[i]);

								rsList.add(hVenCD);
							}
						}
						//HashMap hBmanNos = new HashMap();
						//hBmanNos.put("bmanNos", bmanNos);
						//rsList = customTagOldDao.getCpCode(hBmanNos); //협력사 콤보
					}
					else if(this.dataType.equals("L1"))
					{
						rsList = customTagOldDao.getL1Code(); //L1 CODE
					}else if(this.dataType.equals("ORG"))
					{
						rsList = customTagOldDao.getOrdL1Code(); //ORG L1 CODE
					}
					else if(this.dataType.equals("BIZ"))
					{
						rsList = customTagOldDao.getBizCode(map);
					}
					else if(this.dataType.equals("YEAR"))
					{
						if(this.subCode == null || this.subCode.length() <= 0)
							this.subCode = "3" ;

						rsList = getYearData( Integer.parseInt(this.subCode));
					}
					else if(this.dataType.equals("MONTH"))
					{
						rsList = getMonthData();
					}
					else if(this.dataType.equals("TET")) {
						rsList = customTagOldDao.getTetCode(map);
					}
					else if(this.dataType.equals("AREA")) {
						rsList = customTagOldDao.getAreaCode(map);
					}
					else rsList = customTagOldDao.getCode(map);
				}
				else rsList = customTagOldDao.getCode(map);
			}

			JspWriter out = pageContext.getOut();


			String html = generatorComponet(comType, rsList);
			out.println(html);
			out.flush();

		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		release();
		return SKIP_BODY;
	}

	private List getYearData(int year)
	{
		List list =  new ArrayList();
		HashBox hData  =  null;


		String nowYear = DateUtil.getToday("yyyy");
		int fromYear = Integer.parseInt(nowYear)-year;

		for(int i=1; i<year+1; i++)
		{
			hData =  new HashBox();

			hData.put("CODE_ID", String.valueOf(fromYear+i));
			hData.put("CODE_NAME", String.valueOf(fromYear+i));

			list.add(hData);
		}

		return list;
	}

	private List getMonthData()
	{
		List list =  new ArrayList();
		HashBox hData  =  null;

		for(int i=1; i<13; i++)
		{

			hData =  new HashBox();


			if(i  < 10) {
				hData.put("CODE_ID",   ('0'+String.valueOf(i)));
				hData.put("CODE_NAME", ('0'+String.valueOf(i)));
			}
			else
			{
				hData.put("CODE_ID",   (String.valueOf(i)));
				hData.put("CODE_NAME", (String.valueOf(i)));
			}

			list.add(hData);
		}

		return list;
	}


	/**
	 * HTML생성 Method
	 * @param comType
	 * @return 문자열 - 생성된 HTML
	 */
	public String generatorComponet(String comType , List<HashBox> rsList)throws Exception{

		HashBox rsMap = new HashBox();

		StringBuffer htm = new StringBuffer();
		if(toS(comType).equals("SELECT") && (!toS(selSubComType).equals("OPTION"))){

			htm.append("<select id="+this.objId+" name=" +this.objName);


			if(this.width != null)
				htm.append(" style='width:"+this.width+"' ");
			if(this.event != null)
				htm.append(" " + this.event + " ");
			if(this.attr != null)
				htm.append(" "+ this.attr +" ");

			htm.append(">");


			if(this.defName != null)
				htm.append("<option value=''>"+this.defName+"</option>");

			if(rsList != null)
			{
				for(int i=0; i < rsList.size(); i++){
					rsMap = rsList.get(i);
					htm.append("<option value='" +rsMap.get("CODE_ID").toString().trim()+ "'");
					if(this.selectParam != null && this.selectParam.equals(rsMap.get("CODE_ID").toString().trim()))
						htm.append("selected");

					htm.append(" >"+rsMap.get("CODE_NAME")+"</option>");
				}
			}

			htm.append("</select>");


		}else if(toS(comType).equals("SELECT") && toS(selSubComType).equals("OPTION")){
			htm.delete(0, htm.length());

			for(int i=0; i < rsList.size(); i++){
				rsMap = rsList.get(i);
				htm.append("<option value='" +rsMap.get("CODE_ID").toString().trim()+ "'>"+rsMap.get("CODE_NAME")+"</option>");
			}

		//radio
		}else if(toS(comType).equals("RADIO")){

			for(int i=0; i < rsList.size(); i++){
				rsMap = rsList.get(i);
				htm.append("<input type='radio' id='"+this.objId+"' name='" +this.objName+"' ");
				htm.append(" style='border-style:none;' ");
				htm.append("value='"+rsMap.get("CODE_ID").toString().trim()+"' ");
				if(this.event != null)
					htm.append(" " + this.event + " ");
				if(this.attr != null)
					htm.append(" "+ this.attr +" ");

				if(this.selectParam.equals(rsMap.get("CODE_ID")) )
						htm.append(" checked ");
				else
				{
					if(this.selectParam.equals("0") && i==0 )
						htm.append(" checked ");
				}

				htm.append("> "+rsMap.get("CODE_NAME")+" &nbsp;");
			}

		//checkbox
		}else if(toS(comType).equals("CHECKBOX")){
			//htm.append("local.innerHTML = \"");

			/*
			if(Util.toS(this.fullCheck).equals("Y")){
				htm.append("<input type='checkbox'  name='"+this.objName+"ALL' onClick=\\\"fullCheck(this,'"+this.formName+"','"+this.objName+"');\\\"/> 전체");

				for(int roop=0 ; roop < Util.toII(this.space); roop++){
					htm.append("&nbsp;");
				}
			}
			*/
			String selArray[];

			for(int i=0; i < rsList.size(); i++){
				rsMap = rsList.get(i);

				htm.append("<input type='checkbox'  name='"+this.objName+"' value='"+rsMap.get("CODE_ID")+"' ");
				if(this.attr != null) {
					htm.append(" ");
					htm.append(this.attr);
				}
				if(this.event != null) {
					htm.append(" ");
					htm.append( this.event);
				}
				if(this.selectParam != null){
					selArray = strToArray(this.selectParam, ",");

					for(int j=0; j < selArray.length; j++)
						if(selArray[j].equals((String)rsMap.get("CODE_ID")))
							htm.append(" checked ");

				}

				htm.append("/>");
				htm.append("&nbsp;");
				htm.append(rsMap.get("CODE_NAME"));
				for(int roop=0 ; roop < toII(this.space); roop++)
					htm.append("&nbsp;");

			}

			//htm.append("\"; \n");
		}
		/*
		if((!Util.toS(selSubComType).equals("OPTION")))
			htm.append("</script>");
		*/
		return htm.toString();
	}


	public  String toS(final Object o) {
		if (o == null) {
			return "";
		} else {
			return o.toString().trim();
		}
	}

	public String[] strToArray(String strIn, String chrSeperator) {
		int curIndex = 0, endIndex = 0, arrSize;
		StringBuffer sbb = new StringBuffer();

		arrSize = cntStr(strIn, chrSeperator) + 1;

		String[] arrString = new String[arrSize];

		for (int i = 0; i < arrSize; i++) {
			curIndex = strIn.indexOf(chrSeperator, endIndex);
			if (curIndex == -1) {
				arrString[i] = strIn.substring(endIndex);
			} else {
				arrString[i] = strIn.substring(endIndex, curIndex);
			}
			endIndex = curIndex + 1;
		}
		return arrString;
	}


	/**
     * 입력String(strIn)에 구분자(chrSeprator)가 몇개들어 있는지를 Count함 (배열Size로 사용)
     *
     * @param strIn
     *            입력문자열
     * @param chrSeperator
     *            찾고자하는 구분자
     * @return
     */
	public  int cntStr(String strIn, String chrSeperator) {
		int curIndex = 0, endIndex = 0;
		int count = 0;


		if(strIn == null) return 0;

		if (strIn.trim().equals("")) {
			return 0;
		}

		for (int i = 0; endIndex < strIn.length();) {
			curIndex = strIn.indexOf(chrSeperator, endIndex);
			if (curIndex > -1) {
				count++;
				endIndex = curIndex + 1;
			} else {
				endIndex = strIn.length();
			}
		}
		return count;
	}

	/**
     * @param o
     * @return 0
     *
     * Null이거나 ""이면 0을 리턴시킨다.
     */
	public static int toII(String o) {
		String tmpO = o;
		int val = 0;
		if (tmpO == null) {
			return 3;
		} else if (tmpO.equals("")) {
			return 3;
		} else {
			tmpO = toN(tmpO);
			val = Integer.parseInt(tmpO);
			return val;
		}
	}

	/**
     * null일 경우 "&nbsp;"문자로 바꿈
     *
     * @param s
     *            입력문자열
     * @return
     */
	public String nullToNbsp(String s) {
		if ((s != null) && (s.trim().length() > 0)) {
			return s;
		}
		return "&nbsp;";
	}

	/**
     * @param o
     * @return 0
     *
     * Null이거나 ""이면 0을 리턴시킨다.
     */
	public static String toN(String o) {
		if (o == null) {
			return "0";
		} else if (o.equals("")) {
			return "0";
		} else {
			return o;
		}
	}



}
