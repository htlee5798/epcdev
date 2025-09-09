package com.lottemart.epc.product.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.service.impl.CommonServiceImpl;
import com.lottemart.epc.product.model.PSCMPRD0001VO;
import com.lottemart.epc.product.service.PSCMPRD0001Service;

/**
 * @Class Name : PSCMPRD0001Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("pscmprd0001Controller")
public class PSCMPRD0001Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0001Controller.class);
	
	@Autowired
	private PSCMPRD0001Service pscmprd0001Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private CommonService commonService;
	@Autowired
	private FileMngService fileMngService;
	
	/**
	 * 상품 폼 페이지
	 * Desc : 상품 목록 초기페이지 로딩
	 * @Method Name : selectProduct
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProduct.do")
	public String selectProduct(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
	
		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);
		
		// 대분류콤보
		List<DataMap> daeCdList = commonService.selectDaeCdList();
		request.setAttribute("daeCdList", daeCdList);
		
		// 상품구분콤보
		List<DataMap> prodDivnCdList = pscmprd0001Service.selectProdDivNCdList();
		request.setAttribute("prodDivnCdList", prodDivnCdList);
		
		String gubun = StringUtil.null2str(request.getParameter("gubun"));
		
		if(gubun.equals("M")) {
			if(request.getParameter("asKeywordSelect").equals("05")) {

				DataMap param = new DataMap(request);
				
				String asKeywordValue = StringUtil.null2str(request.getParameter("asKeywordValue"));
				
				// 기획전번호로 해당 상품 가져오기
				PSCMPRD0001VO prodCdInfo = pscmprd0001Service.selectProdCdInfo(asKeywordValue);

				request.setAttribute("asKeywordValue",  prodCdInfo.getProdCd());
			} else {
				request.setAttribute("asKeywordValue",  request.getParameter("asKeywordValue"));
			}
			request.setAttribute("gubun",  gubun);
			request.setAttribute("asKeywordSelect", "01");
		}
		
		return "product/internet/PSCMPRD0001";
	}
	
	/**
	 * 상품 목록
	 * @Description : 상품 목록을 페이지별로 로딩하여 그리드에 리턴
	 * @Method Name : selectProductList
	 * @param request
	 * @return String
	 * @throws Exception
	 */
//	@RequestMapping("product/selectProductList.do")
//	public String selectProductList(HttpServletRequest request) throws Exception 
//	{
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
//		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
//		{
//			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
//		}
//		
//		GridData gdRes = new GridData();
//		DataMap paramMap = null;
//		List<PSCMPRD0001VO> list = null;
//		ArrayList<String> aryList = null;
//
//		try 
//		{
//			// 요청객체
//            String 	 wiseGridData = request.getParameter("WISEGRID_DATA");   
//            GridData gdReq 	      = OperateGridData.parse(wiseGridData);
//			
//			// 결과객체
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//			
//			// 페이징 
//			String rowsPerPage = StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
//			int startRow       = ((Integer.parseInt(gdReq.getParam("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
//			int endRow         = startRow + Integer.parseInt(rowsPerPage) -1;
//			
//			// 파라미터
//			paramMap = new DataMap();
//
//			paramMap.put("currentPage",          gdReq.getParam("currentPage")				  );			
//			paramMap.put("startRow", 	         Integer.toString(startRow)					  );
//			paramMap.put("endRow", 		         Integer.toString(endRow)					  );
//			paramMap.put("asCmbnMallSellPsbtYn", gdReq.getParam("asCmbnMallSellPsbtYn")       );
//			paramMap.put("asProdDivnCd", 		 gdReq.getParam("asProdDivnCd")               );
//			paramMap.put("asAprvYn",             gdReq.getParam("asAprvYn")                   );
//			paramMap.put("asDispYn",             gdReq.getParam("asDispYn")                   );
//			paramMap.put("asImgYn", 			 gdReq.getParam("asImgYn")                    );
//			paramMap.put("asDescYn",             gdReq.getParam("asDescYn")                   );
//			paramMap.put("asCategoryId", 		 gdReq.getParam("asCategoryId")               );
//			paramMap.put("asDateSelect",         gdReq.getParam("asDateSelect")        		  );
//			paramMap.put("chkVal",               gdReq.getParam("chkVal")                     );
//			paramMap.put("startDt", 			 gdReq.getParam("startDt").replaceAll("-", ""));
//			paramMap.put("endDt", 				 gdReq.getParam("endDt").replaceAll("-", "")  );
//			paramMap.put("asKeywordSelect",      gdReq.getParam("asKeywordSelect")            );
//			paramMap.put("asKeywordValue",       gdReq.getParam("asKeywordValue")             );
//			
//			paramMap.put("prodCommerce",         gdReq.getParam("prodCommerce")                 );
//			paramMap.put("prodCommerceApprove",  gdReq.getParam("prodCommerceApprove")   );
//			
//            logger.debug("asCmbnMallSellPsbtYn ==>" + gdReq.getParam("asCmbnMallSellPsbtYn") + "<==");
//            logger.debug("asProdDivnCd         ==>" + gdReq.getParam("asProdDivnCd")         + "<==");
//            logger.debug("asAprvYn             ==>" + gdReq.getParam("asAprvYn")             + "<==");
//            logger.debug("asDispYn             ==>" + gdReq.getParam("asDispYn")             + "<==");
//            logger.debug("asImgYn              ==>" + gdReq.getParam("asImgYn")              + "<==");
//            logger.debug("asDescYn             ==>" + gdReq.getParam("asDescYn")             + "<==");
//            logger.debug("asCategoryId         ==>" + gdReq.getParam("asCategoryId")         + "<==");
//            logger.debug("asDateSelect         ==>" + gdReq.getParam("asDateSelect")         + "<==");
//            logger.debug("chkVal               ==>" + gdReq.getParam("chkVal")               + "<==");
//            logger.debug("startDt              ==>" + gdReq.getParam("startDt")              + "<==");
//            logger.debug("endDt                ==>" + gdReq.getParam("endDt")                + "<==");
//            logger.debug("asKeywordSelect      ==>" + gdReq.getParam("asKeywordSelect")      + "<==");
//            logger.debug("asKeywordValue       ==>" + gdReq.getParam("asKeywordValue")       + "<==");
//			
//			//-------------------------------------------------------------------
//			//paramMap.put("asVendorId", gdReq.getParam("asVendorId"));
//			//-------------------------------------------------------------------
//			if ( ( gdReq.getParam("asVendorId") == null || "".equals(gdReq.getParam("asVendorId")) ) && epcLoginVO != null ) 
//			{
//				aryList = new ArrayList<String>();
//				
//				for (int i = 0; i < epcLoginVO.getVendorId().length; i++)
//				{
//					aryList.add(epcLoginVO.getVendorId()[i]);
//				}
//	            
//				paramMap.put("asVendorId", aryList);
//			}
//			else 
//			{
//				aryList = new ArrayList<String>();
//				aryList.add(gdReq.getParam("asVendorId"));
//				
//				paramMap.put("asVendorId", aryList);
//			}
//			//-------------------------------------------------------------------
//			
//			// 전체 조회 건수
//			int totalCnt = pscmprd0001Service.selectPrdTotalCnt(paramMap);
//			logger.debug("selectPrdTotalCnt ==>" + totalCnt + "<==");
//
//			// 페이징 변수
//			gdRes.addParam("totalCount",  Integer.toString(totalCnt)   ); 
//			gdRes.addParam("rowsPerPage", rowsPerPage                  );
//			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//			
//			if ( totalCnt >= 0 )
//			{
//				// 데이터 조회
//				list = pscmprd0001Service.selectPrdList(paramMap);
//				logger.debug("selectPrdList ==>" + list.size() + "<==");
//			}
//
//			// 데이터 없음
//			if (list == null || list.size() == 0)
//			{
//				logger.debug("::: data not exists..! :::");
//				gdRes.setStatus("false");
//				request.setAttribute("wizeGridResult", gdRes);
//				
//				return "common/wiseGridResult";
//			}
//	   
//			PSCMPRD0001VO bean           = null;
//			String        MdRecentSellDy = null;
//			int           listSize       = list.size();
//			
//			// GridData 셋팅
//			logger.debug("::: data grid setting :::");
//			for (int i = 0; i < listSize; i++) 
//			{
//				bean = (PSCMPRD0001VO)list.get(i);
//
//				gdRes.getHeader("num"		    ).addValue(bean.getNum()									,"");
//				gdRes.getHeader("prodCd"	    ).addValue(bean.getProdCd()									,"");
//				gdRes.getHeader("prodNm"	    ).addValue(bean.getProdNm()									,"");
//				gdRes.getHeader("mdProdCd"	    ).addValue(bean.getMdProdCd()==null?"":bean.getMdProdCd()	,"");
//				gdRes.getHeader("mdSrcmkCd"	    ).addValue(bean.getMdSrcmkCd()==null?"":bean.getMdSrcmkCd()	,"");
//				gdRes.getHeader("catNm"		    ).addValue(bean.getCatNm()									,"");
//				gdRes.getHeader("aprvYn"	    ).addValue(bean.getAprvYn()==null?"":bean.getAprvYn()		,"");
//				gdRes.getHeader("aprvDate"	    ).addValue(bean.getAprvDate()								,"");
//				gdRes.getHeader("regDate"	    ).addValue(bean.getRegDate()								,"");
//				gdRes.getHeader("absenceYn"	    ).addValue(bean.getAbsenceYn()==null?"":bean.getAbsenceYn()	,"");
//				gdRes.getHeader("dispYn"	    ).addValue(bean.getDispYn()==null?"":bean.getDispYn()		,"");
//				gdRes.getHeader("haveImg"		).addValue(bean.getHaveImg()==null?"":bean.getHaveImg()		,"");
//				gdRes.getHeader("buyPrc"		).addValue(bean.getBuyPrc()							,"");
//				gdRes.getHeader("currSellPrc"	).addValue(bean.getCurrSellPrc()							,"");
//				gdRes.getHeader("approvalChk"	).addValue(bean.getApprovalChk()							,"");
//				gdRes.getHeader("prodDivnCd"	).addValue(bean.getProdDivnCd()								,"");
//				gdRes.getHeader("mallDivnCd"	).addValue(bean.getMallDivnCd()								,"");
//				
//				
//				gdRes.getHeader("prodCommerce").addValue(bean.getProdCommerce()==null?"":bean.getProdCommerce()	,"");
//				gdRes.getHeader("prodCommerceApprove").addValue(bean.getProdCommerceApprove()==null?"":bean.getProdCommerceApprove()		,"");
//
//				gdRes.getHeader("profitRate").addValue(bean.getProfitRate()==null?"":bean.getProfitRate(), "");
//			
//				if (bean.getMdRecentSellDy() == null)
//				{
//					MdRecentSellDy = "-";
//				}
//				else
//				{
//					MdRecentSellDy = bean.getMdRecentSellDy().substring(0, 4) + "-" 
//					               + bean.getMdRecentSellDy().substring(4, 6) + "-" 
//					               + bean.getMdRecentSellDy().substring(6, 8);
//				}
//				
//				gdRes.getHeader("mdRecentSellDy").addValue(MdRecentSellDy,"");
//
//				// tmp
//				gdRes.getHeader("CHK"			).addValue("0"	,"");
//				gdRes.getHeader("tmps1"			).addValue(""	,"");
//				gdRes.getHeader("tmps2"			).addValue(""	,"");
//			}
//		
//			// 성공
//			gdRes.setStatus("true");
//			logger.debug("[[[ success ]]]");
//
//		} 
//		catch (Exception e) 
//		{
//			// 오류
//			gdRes.setMessage(e.getMessage());
//			gdRes.setStatus("false");
//			logger.debug("[[[ Exception ]]]", e);
//		}
//		
//		request.setAttribute("wizeGridResult", gdRes);
//		logger.debug("::: wiseGridResult :::");
//
//		return "common/wiseGridResult";
//	}
	
	
	@RequestMapping(value = "/product/selectProductUploadList.do")
	public void selectOutOfStockUploadList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		
		Map rtnMap = new HashMap<String, Object>();
		List<DataMap> mapList = new ArrayList<DataMap>();
		String prodCd = "";
		boolean expChk = false;
		
		try {
			String[] colNms = {"PROD_CD"};
			
			mapList = fileMngService.readUploadExcelFile(mptRequest, colNms, 1);
			
			for(int i=0; i<mapList.size(); i++){
				DataMap map = mapList.get(i);
				
				prodCd += ","+map.getString("PROD_CD").trim();
			}
			
			prodCd = prodCd.substring(1,prodCd.length());
		} catch(Exception e) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script type=\'text/javascript'>");
			out.println("alert('업로드 양식이 다릅니다.');");
			out.println("</script>");
			
			expChk = true;
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
		} finally {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter(); 
			
			try {
				out.println("<script type=\'text/javascript'>");
				out.println("parent.document.dataForm.createFile.value = ''");
				
				if(mapList.size() < 1000 && mapList.size() > 0 && prodCd.length() > 0 && !"false".equals(prodCd)){
					out.println("parent.document.dataForm.prodInVal.value = '"+prodCd+"'");
					out.println("parent.doSearch();");
				}else if (mapList.size() >= 1000) {
					out.println("alert('조회 가능한 상품코드를 초과하였습니다. \\n상품코드는 999개 까지만 조회 가능합니다.');");
				}
				else{
					if(!expChk){
						out.println("alert('업로드 된 데이터가 없습니다.');");
					}
				}
				out.println("	parent.hideLoadingMask();");
				out.println("</script>");
			}catch(Exception e){
		        logger.error(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 상품 목록
	 * @Description : 상품 목록을 페이지별로 로딩하여 그리드에 리턴
	 * @Method Name : selectProductList
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductList.do")
	public @ResponseBody Map selectProductList(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
//		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
//		{
//			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
//		}
		
		Map rtnMap = new HashMap<String, Object>();
		
//		GridData gdRes = new GridData();
//		DataMap paramMap = null;
		List<PSCMPRD0001VO> list = null;
		ArrayList<String> aryList = null;

		try 
		{
			DataMap param = new DataMap(request);

			String rowPerPage  = param.getString("rowsPerPage");   
			String currentPage = param.getString("currentPage");   
			logger.debug("rowPerPage ==============> " + rowPerPage);
			
			// 페이징 
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDt", param.getString("startDt").replaceAll("-", ""));
			param.put("endDt", param.getString("endDt").replaceAll("-", ""));
			
			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if ( ( param.getString("asVendorId") == null || param.getString("asVendorId").equals(epcLoginVO.getRepVendorId())  ||  "".equals(param.getString("asVendorId")) ) && epcLoginVO != null ) {
				aryList = new ArrayList<String>();
				
				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}
	            
				param.put("asVendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("asVendorId"));
				param.put("asVendorId", aryList);
			}
			
			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for(int l=0; openappiVendorId.size()>l; l++ ){		 
				if(openappiVendorId.get(l).getRepVendorId().equals(param.getString("asVendorId").replace("[", "").replace("]", "").trim())){
					aryList = new ArrayList<String>();
					
					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("asVendorId", aryList);
					}
				}
			}
			
			if(!"".equals(param.getString("prodInVal"))){
				String prodInVal[] = param.getString("prodInVal").split(",");
				logger.debug("prodInValprodInValprodInVal"+prodInVal.toString());
				param.put("prodInVal", prodInVal);
			}
			
			// 상품코드 여러건 조회
			if(param.getString("asKeywordSelect").equals("01")){
	        	String[] prodCdArr = param.getString("asKeywordValue").split(",");
				param.put("prodCdArr", prodCdArr);
			} 
			
			// 전체 조회 건수
			int totalCnt = pscmprd0001Service.selectPrdTotalCnt(param);

			
			if ( totalCnt >= 0 )
			{
				// 데이터 조회
				list = pscmprd0001Service.selectPrdList(param);
				
				logger.debug("selectPrdList ==>" + list.size() + "<==");
				
			}
			
			rtnMap = JsonUtils.convertList2Json((List)list, totalCnt, currentPage);
			
			// 성공
	        rtnMap.put("result", true);

		} 
		catch (Exception e) 
		{
			// 오류
			logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;
	}
	
	
	@RequestMapping(value = "/product/selectProductExcel.do")
	public void selectProductExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") PSCMPRD0001VO searchVO, ModelMap model) throws Exception {
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
	
	
		List<PSCMPRD0001VO> list = null;
		ArrayList<String> aryList = null;
		
		DataMap param = new DataMap(request);
		
		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if ( ( param.getString("asVendorId") == null || param.getString("asVendorId").equals(epcLoginVO.getRepVendorId())  ||"".equals(param.getString("asVendorId")) ) && epcLoginVO != null ) {
			aryList = new ArrayList<String>();
			
			for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
				aryList.add(epcLoginVO.getVendorId()[i]);
			}

			param.put("asVendorId", aryList);
		} else {
			aryList = new ArrayList<String>();
			aryList.add(param.getString("asVendorId"));
			param.put("asVendorId", aryList);
		}
		
		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(param.getString("asVendorId").replace("[", "").replace("]", "").trim())){
				aryList = new ArrayList<String>();
				
				for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
					aryList.add(epcLoginVO.getVendorId()[a]);
					param.put("asVendorId", aryList);
				}
			}
		}

		// 상품코드 여러건 조회
		if(param.getString("asKeywordSelect").equals("01")){
			String[] prodCdArr = param.getString("asKeywordValue").split(",");
			param.put("prodCdArr", prodCdArr);
		} 
		
		// 데이터 조회
		List<PSCMPRD0001VO> prdList = pscmprd0001Service.selectProductExcel(param);
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		JsonUtils.IbsExcelDownload((List)prdList, request, response);
	}
	

}
