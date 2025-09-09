package com.lottemart.epc.board.controller;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.service.LtsmsService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.board.model.PSCPBRD0011SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0011Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.util.Utils;
import com.lottemart.epc.common.util.SecureUtil;

/**
 * @Class Name : PSCPBRD0011Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 18. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCPBRD0011Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0011Controller.class);
	
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private LtsmsService LtsmsService;
	@Autowired
	private PSCPBRD0011Service pscpbrd0011Service;
	@Resource(name = "propertiesService")
	protected PropertyService propertyService;
    // 첨부파일 관련 
	@Resource(name="FileMngService")
	private FileMngService fileMngService;
	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * 건의사항관리 등록 폼
	 * @Description : 건의사항관리 등록 초기 화면 로딩. 
	 * @Method Name : insertCcQnaPopupForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertCcQnaPopupForm.do")
	public String insertCcQnaPopupForm(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		// 문의유형(대) 공통코드 조회
		List<DataMap> codeList1 = commonCodeService.getCodeList("QA001");
		
		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("codeList1", codeList1);
		
		return "board/PSCPBRD0011";
	}
	
	/**
	 * 문의사항 등록
	 * @Description : 문의사항을 등록한다.
	 * @Method Name : insertCcQnaPopup
	 * @param VO
	 * @param Model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertCcQnaPopup.do")
	public String insertCcQnaPopup(@ModelAttribute("saveVO") PSCPBRD0011SaveVO saveVO, Model model, HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		
		String fileId = saveVO.getAtchFileId();
		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(fileId);

		// 현재 게시물의 첨부파일 번호를 얻어온다. 없으면 1 리턴
		int maxFileSN = fileMngService.getMaxFileSN(fileVO);
		
		List<FileVO> fileList = null;
		final Map<String, MultipartFile> files = mptRequest.getFileMap();
		logger.debug("files isEmpty ==>" + files.isEmpty() + "<==");
		logger.debug("files size    ==>" + files.size()    + "<==");
		
		fileList = fileMngUtil.parseFileInf(files, "MTG_", maxFileSN, fileId, "");
		logger.debug("fileList ==>" + fileList + "<==");
		fileId = fileMngService.insertFileInfs(fileList);
		logger.debug("fileId ==>" + fileId + "<==");
		
		if ( !fileId.isEmpty() )
		{
			saveVO.setAtchFileYn("Y");
		} 
		else 
		{
			saveVO.setAtchFileYn("N");
		}

		setCellNo(saveVO); //휴대폰번호 접합		
		
		// 2019.04.11 ISMS 모의해킹 결과 조치 작업		
		saveVO.setAtchFileId(SecureUtil.stripXSS(fileId));		
		saveVO.setListSeq(SecureUtil.stripXSS(saveVO.getListSeq()));
		saveVO.setTitle(SecureUtil.stripXSS(saveVO.getTitle()));
		saveVO.setContent(SecureUtil.stripXSS(saveVO.getContent()));
		saveVO.setMemberNo(SecureUtil.stripXSS(saveVO.getMemberNo()));
		saveVO.setMemberNm(SecureUtil.stripXSS(saveVO.getMemberNm()));
		saveVO.setAtchFileYn(SecureUtil.stripXSS(saveVO.getAtchFileYn()));
		saveVO.setOrderId(SecureUtil.stripXSS(saveVO.getOrderId()));
		saveVO.setClmLgrpCd(SecureUtil.stripXSS(saveVO.getClmLgrpCd()));
		saveVO.setCellNo(SecureUtil.stripXSS(saveVO.getCellNo()));
		saveVO.setCellNo1(SecureUtil.stripXSS(saveVO.getCellNo1()));
		saveVO.setCellNo2(SecureUtil.stripXSS(saveVO.getCellNo2()));
		saveVO.setCellNo3(SecureUtil.stripXSS(saveVO.getCellNo3()));
		saveVO.setEmail(SecureUtil.stripXSS(saveVO.getEmail()));
		saveVO.setAnsSmsRecvYn(SecureUtil.stripXSS(saveVO.getAnsSmsRecvYn()));
		saveVO.setAcceptId(SecureUtil.stripXSS(saveVO.getAcceptId()));
		saveVO.setLet1Ref(SecureUtil.stripXSS(saveVO.getLet1Ref()));
		saveVO.setLet2Ref(SecureUtil.stripXSS(saveVO.getLet2Ref()));
		saveVO.setLet3Ref(SecureUtil.stripXSS(saveVO.getLet3Ref()));
		saveVO.setLet4Ref(SecureUtil.stripXSS(saveVO.getLet4Ref()));
		saveVO.setVendorId(SecureUtil.stripXSS(saveVO.getVendorId()));
		
		try
		{
			pscpbrd0011Service.insertCcQnaPopup(saveVO);
			
			List<DataMap> cellNoList = pscpbrd0011Service.selectBoardAuthCellNo();
			
			for(int i=0; i<cellNoList.size(); i++){
				DataMap info = cellNoList.get(i);
				
				if(!"".equals(info.getString("CELL_NO"))){ //고객센터 담당자 연락처, 업체 연락처 유무 체크
					String tranMsg = "업체명 : "+epcLoginVO.getLoginNm() + "\n"
						           + "업체 문의글이 등록 되었습니다.";
					
					LtnewsmsVO ltnewsmsvo = new LtnewsmsVO();
			    	
			    	ltnewsmsvo.setTRAN_PHONE(info.getString("CELL_NO"));
			    	ltnewsmsvo.setTRAN_CALLBACK("1577-2500");
			    	ltnewsmsvo.setTRAN_STATUS("1");
			    	ltnewsmsvo.setTRAN_MSG(tranMsg);
			    	ltnewsmsvo.setTRAN_TYPE("4");
			    	ltnewsmsvo.setTRAN_ETC2("O04");
			    	ltnewsmsvo.setTRAN_ETC3(epcLoginVO.getAdminId());
			 
			    	LtsmsService.insertNewSmsSend(ltnewsmsvo);
				}
			}
			
			model.addAttribute("msg", "저장되었습니다.");		
		} 
		catch(Exception e) 
		{
			model.addAttribute("msg", e.getMessage());
		}
		
		return "common/messageResult";
	}
		
	/**
	 * 주문번호 목록
	 * @Description : 주문번호 조회
	 * @Method Name : selectOrderIdList
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/selectOrderIdList.do")
	public String selectPopupOrderIdList(ModelMap model, HttpServletRequest request) throws Exception {
		HashMap map = new HashMap();
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		String from_date = request.getParameter("startDate");     // 기간(시작)
		String to_date 	 = request.getParameter("endDate");     // 기간(끝)
		
		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -21);
		
		String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
		
		// 초최 오픈시 Default 값 세팅
		if(from_date == null || from_date.equals("")) from_date = today_date2;
		if(to_date   == null || to_date.equals(""))   to_date   = today_date;					
		
		map.put("fromDate", from_date.replaceAll("-", ""));
		map.put("toDate", to_date.replaceAll("-", ""));
		map.put("vendorId", epcLoginVO.getVendorId());

		List<DataMap> list = pscpbrd0011Service.selectOrderIdList(map);
		model.addAttribute("list", list);
		model.addAttribute("startDate",from_date);
		model.addAttribute("endDate",to_date);
		
		return "common/PSCMCOM0011";
	}
	
	/**
	 * 상품 폼 페이지
	 * @Description : 상품 초기페이지 로딩 (팝업)
	 * @Method Name : selectPopupProductForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("/board/selectProductForm.do")
	public String selectPopupProductForm(HttpServletRequest request) throws Exception 
	{
		return "common/PSCMCOM0012";
	}
	
	/**
	 * 상품 목록
	 * @Description : 상품 조회
	 * @Method Name : selectOrderIdList
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/selectProductSearch.do")
	public @ResponseBody Map selectPopupProductSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		Map rtnMap = new HashMap<String, Object>();
		
		try 
		{
			DataMap paramMap = new DataMap(request);
			
			String currentPage = paramMap.getString("currentPage");
			// 페이징 
			String rowsPerPage = StringUtil.null2str(paramMap.getString("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			paramMap.put("vendorId", epcLoginVO.getVendorId());
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
			
			// 데이터 조회
			List<DataMap> list = pscpbrd0011Service.selectProductList(paramMap);
			
			rtnMap = JsonUtils.convertList2Json((List)list, -1, currentPage);

			rtnMap.put("result", true);
		
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}
		
		
		return rtnMap;
	}
	
	/**
	 * 상담원 폼 페이지
	 * @Description : 상담원 초기페이지 로딩 (팝업)
	 * @Method Name : selectPopupCCagentForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("/board/selectCCagentForm.do")
	public String selectPopupCCagentForm(HttpServletRequest request) throws Exception 
	{
		return "common/PSCMCOM0013";
	}
	
	/**
	 * 상담원 목록
	 * @Description : 상담원 조회
	 * @Method Name : selectPopupCCagentSearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/selectCCagentSearch.do")
	public @ResponseBody Map selectPopupCCagentSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		
		try 
		{
			DataMap paramMap = new DataMap(request);
			
			String currentPage = paramMap.getString("currentPage");
			// 페이징 
			String rowsPerPage = StringUtil.null2str(paramMap.getString("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
			
			List<DataMap> list = pscpbrd0011Service.selectCCagentList(paramMap);
			
			rtnMap = JsonUtils.convertList2Json((List)list, -1, currentPage);

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
	
	/**
	 * 
	 * @description : 휴대전화번호 접합후 VO에 저장 
	 * @Method Name : setCellNo
	 * @param vo
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	private void setCellNo(PSCPBRD0011SaveVO vo) {
		if (!StringUtils.isEmpty(vo.getCellNo1()) && !StringUtils.isEmpty(vo.getCellNo2()) && !StringUtils.isEmpty(vo.getCellNo3())) {
			// 휴대전화번호 문자열 접합
			StringBuffer strBuffer= new StringBuffer();
			String cellNo1 = vo.getCellNo1().trim();
			String cellNo2 = vo.getCellNo2().trim();
			String cellNo3 = vo.getCellNo3().trim();
			strPlus(strBuffer, cellNo1);
			strPlus(strBuffer, cellNo2);
			strPlus(strBuffer, cellNo3);
			
			vo.setCellNo(strBuffer.toString());
		}
	}

	/**
	 * 
	 * @description : 휴대전화번호 문자열 접합
	 * @Method Name : strPlus
	 * @param strBuffer
	 * @param cellNo
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	private void strPlus(StringBuffer strBuffer, String cellNo) {
		strBuffer.append(cellNo);
		
		int length = cellNo.length();
		
		for (int i = 0; i < 4 - length; i++) {
			strBuffer.append(" ");
		}
	}
}
