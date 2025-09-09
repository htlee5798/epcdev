package com.lottemart.epc.edi.comm.taglibs.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;



/**
 * select, input, checkbox, radio 생성 Tag
 */
@Service("customTagService")
public class CustomTagService extends TagSupport{

	private static final Logger logger = LoggerFactory.getLogger(CustomTagService.class);

	@Autowired
	private CustomTagDao customTagDao;


	/** 속성s */
	private String attr;

	/** Object Name */
	private String objName;

	/** Object ID */
	private String objId;

	/** Form name */
	@SuppressWarnings("unused")
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
	private String subCode;			//let_4_ref
	private String subCode02;		//let_2_ref
	private String subCode03;		//let_3_ref
	
	private String childCodeList;	//childCode N개 (, 로 구분)
	private String subCodeList02;	//let_2_ref N개 (, 로 구분)
	
	private String notIn;			//code list 조회 시, notIn 조건 사용여부

	/** 선택되어 있는 값 표시**/
	private String selectParam;

	/** 첫번째 기본 옵션의 명칭**/
	private String defName;

	/** width 사이즈 **/
	private String width;
	
	/** display 조건 **/
	private String display;

	/** Event */
	private String event;

	/** checkbox , radio 간격 */
	private String space;

	/** 바인드 자료 정보 (Default : CM_CODE table) CP  **/
	private String dataType;

	/** 첫번째 기본 옵션의 값**/
	private String defValue;

	/** 정렬기준 FLAG**/
	private String orderSeqYn;

	/** disabled */
	private String disabled;
	
	private String subNmUseYn;
	

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

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
		// TODO Auto-generated method stub
	}


	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	public String getSubCode02() {
		return subCode02;
	}

	public void setSubCode02(String subCode02) {
		this.subCode02 = subCode02;
	}

	public String getSubNmUseYn() {
		return subNmUseYn;
	}

	public void setSubNmUseYn(String subNmUseYn) {
		this.subNmUseYn = subNmUseYn;
	}

	public String getSubCode03() {
		return subCode03;
	}

	public void setSubCode03(String subCode03) {
		if(subCode03 == null)
			this.subCode03 = "";
		else
			this.subCode03 = subCode03;
	}
	
	public String getNotIn() {
		return notIn;
	}

	public void setNotIn(String notIn) {
		if(notIn == null)
			this.notIn = "";
		else
			this.notIn = notIn;
	}
	
	public String getChildCodeList() {
		return childCodeList;
	}

	public void setChildCodeList(String childCodeList) {
		if(childCodeList == null)
			this.childCodeList = "";
		else
			this.childCodeList = childCodeList;
	}

	public String getSubCodeList02() {
		return subCodeList02;
	}

	public void setSubCodeList02(String subCodeList02) {
		if(subCodeList02 == null)
			this.childCodeList = "";
		else
			this.subCodeList02 = subCodeList02;
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
			customTagDao =
				 		(CustomTagDao)WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean("customTagDao");


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
			
			//DB 조회조건
			if(this.subCode02 != null){
				map.put("subCodeId02", this.subCode02);
			}
			
			//DB 조회조건
			if(this.subCode03 != null){
				map.put("subCodeId03", this.subCode03);
			}
			
			//DB 조회조건
			if(this.notIn != null){
				map.put("notIn", this.notIn.trim().toUpperCase());
			}
			
			//DB 조회조건
			if(this.childCodeList != null && !"".equals(this.childCodeList)){
				String[] childCodeArr = strToArray(this.childCodeList, ",");
				map.put("childCodeList", childCodeArr);
			}
			
			//DB 조회조건
			if(this.subCodeList02 != null && !"".equals(this.subCodeList02)){
				String[] subCodeArr02 = strToArray(this.subCodeList02, ",");
				map.put("subCodeList02", subCodeArr02);
			}

			//DB 조회조건
			if(this.orderSeqYn != null){
				map.put("orderSeqYn", this.orderSeqYn);
			}
			
			if(this.subNmUseYn != null) {
				map.put("subNmUseYn", this.subNmUseYn);
			}else {
				map.put("subNmUseYn", "N");
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
						//rsList = customTagDao.getCpCode(hBmanNos); //협력사 콤보
					}else if(this.dataType.equals("NTCPCD")) {
						rsList = customTagDao.getNewTcpCode(map);
					}
					else if(this.dataType.equals("L1"))
					{
						rsList = customTagDao.getL1Code(); //L1 CODE
					}else if(this.dataType.equals("ORG"))
					{
						rsList = customTagDao.getOrdL1Code(); //ORG L1 CODE
					}
					else if(this.dataType.equals("BIZ"))
					{
						rsList = customTagDao.getBizCode(map);
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
						rsList = customTagDao.getTetCode(map);
					}
					else if(this.dataType.equals("AREA")) {
						rsList = customTagDao.getAreaCode(map);
					}
					else rsList = customTagDao.getCode(map);
				}
				else rsList = customTagDao.getCode(map);
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


			if(this.width != null && this.display != null)
				htm.append(" style='width:"+this.width+"display:none' ");
			if(this.width != null)
				htm.append(" style='width:"+this.width+"' ");
			if(this.event != null)
				htm.append(" " + this.event + " ");
			if(this.attr != null)
				htm.append(" "+ this.attr +" ");
			if(this.display != null)
				htm.append(" style='display:none' ");
			if(this.disabled != null && this.disabled != "false")
				htm.append(" disabled");
			htm.append(">");


			if(this.defName != null) {
				if(this.defValue != null) {
					htm.append("<option value="+this.defValue+">"+this.defName+"</option>");
				}else {
					htm.append("<option value=''>"+this.defName+"</option>");
				}
			}

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
				htm.append("<input type='radio' id='"+this.objId+i+"' name='" +this.objName+"' ");
				htm.append(" style='border-style:none;' ");
				htm.append("value='"+rsMap.get("CODE_ID").toString().trim()+"' ");
				if(this.event != null)
					htm.append(" " + this.event + " ");
				if(this.attr != null)
					htm.append(" "+ this.attr +" ");

				if(this.selectParam != null) {
					if(this.selectParam.equals(rsMap.get("CODE_ID")) )
						htm.append(" checked ");
					else
					{
						if(this.selectParam.equals("0") && i==0 )
							htm.append(" checked ");
					}
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

				htm.append("<input type='checkbox'  name='"+this.objName+"'id='"+ this.objId + "' value='"+rsMap.get("CODE_ID")+"' ");
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
				if(this.disabled != null && this.disabled != "false") {
					htm.append(" disabled");
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

		int str;
		if ((s != null) && (s.length() > 0)) {
			str = s.length();
			for (int i = 0; i < str; i++) {
				if (Character.isWhitespace(s.charAt(i))) {
					return s;
				}
			}
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

	public String getOrderSeqYn() {
		return orderSeqYn;
	}

	public void setOrderSeqYn(String orderSeqYn) {
		this.orderSeqYn = orderSeqYn;
	}



}
