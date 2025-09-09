package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.srm.model.SRMVEN002001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0020VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0020Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SRM정보 / 인증정보 등록 Controller
 * 
 * @author SHIN SE JIN
 * @since 2016.07.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.26  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMVEN0020Controller {
	
	@Autowired
	private SRMVEN0020Service srmven0020Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	public static final String[] INVALID_FILE_PATH = {"../", ".."};
	
	/**
	 * 인증정보 등록 화면 init
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/SRMVEN0020.do")
	public String init() throws Exception {
		return "/edi/srm/SRMVEN0020";
	}
	
	/**
	 * 인증등록 정보 내역
	 * @param SRMVEN0020VO
	 * @param HttpServletRequest
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCertiInfoAddList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectCertiInfoAddList(@RequestBody SRMVEN0020VO vo, HttpServletRequest request) throws Exception {
		
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		vo.setVendorCode(sessionVO.getCono()[0]);		//사업자 등록 번호
		
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMVEN0020VO> list = null;
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(vo.getPageSize());
		
		resultMap.put("paginationInfo", paginationInfo);
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		// List Total Count
		int totCnt = srmven0020Service.selectCertiInfoAddListCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		list = srmven0020Service.selectCertiInfoAddList(vo);
		resultMap.put("list", list);

		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		resultMap.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		
		return resultMap;
	}
	
	/**
	 * 인증정보 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/ven/certiInfoPopup.do")
	public String certiInfoPopup() throws Exception {
		return "/edi/srm/SRMVEN002002";
	}
	
	/**
	 * 인증정보 내역
	 * @param SRMVEN0020VO
	 * @param HttpServletRequest
	 * @return List<SRMVEN0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCertiInfoList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMVEN0020VO> selectCertiInfoList(@RequestBody SRMVEN0020VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		vo.setVendorCode(sessionVO.getCono()[0]);		//사업자 등록 번호
		
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		
		return srmven0020Service.selectCertiInfoList(vo);
	}
	
	/**
	 * 상품검색 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/ven/prodCdsearchPopup.do")
	public String prodCdsearchPopup() throws Exception {
		return "/edi/srm/SRMVEN002001";
	}
	
	/**
	 * 상품검색
	 * @param SRMVEN002001VO
	 * @param HttpServletRequest
	 * @return List<SRMVEN002001VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectProdInfo.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectProdInfo(@RequestBody SRMVEN002001VO vo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMVEN002001VO> list = null;
		
		if (StringUtil.isEmpty(vo.getProdCd())) {
			resultMap.put("list", "");
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());
		
		resultMap.put("paginationInfo", paginationInfo);
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		if (epcLoginVO != null) {
			vo.setVenCds(epcLoginVO.getVendorId()); 
		}
		
		if (vo.getVenCds() != null && vo.getVenCds().length > 0) {
			
			// List Total Count
			int totCnt = srmven0020Service.selectProdInfoCount(vo);
			paginationInfo.setTotalRecordCount(totCnt);

			// List 가져오기
			list = srmven0020Service.selectProdInfo(vo);
			resultMap.put("list", list);
			
		}

		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		resultMap.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		
		return resultMap;
	}
	
	/**
	 * 인증정보 등록/수정
	 * @param SRMVEN0020VO
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/updateCertiInfo.do")
	public String updateCertiInfo(SRMVEN0020VO vo, HttpServletRequest request, Model model) throws Exception {
		String msg = "";
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		vo.setVendorCode(sessionVO.getCono()[0]);
		vo.setChangeUserId(sessionVO.getRepVendorId());		// 변경자ID(로그인중인 협력코드)
		
		msg = srmven0020Service.updateCertiInfo(vo);
		
		model.addAttribute("message", msg);
		
		return "/edi/srm/SRMVEN0020";
	}
	
	/**
	 * 파트너사 인증정보 삭제
	 * @param SRMVEN0020VO
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/deleteCertiInfo.json", method = RequestMethod.POST)
	public @ResponseBody String deleteCertiInfo(@RequestBody SRMVEN0020VO vo, HttpServletRequest request) throws Exception {
		
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
				
		vo.setVendorCode(sessionVO.getCono()[0]);
		
		return srmven0020Service.deleteCertiInfo(vo);
	}
	
	/**
	 * 파일 다운로드(파일 경로 및 이름을 통한 다운로드)
	 * @param HttpServletRequest
	 * @param Map<String, Object>
	 * @param FileVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/fileDown.do")
	public ModelAndView download(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO filevo) throws Exception {
		StringBuffer stordFilePath = new StringBuffer();

		String imageUploadPath = config.getString("edi.srm.file.path");	//파일 업로드 경로

		CommonFileVO vo = new CommonFileVO();
		vo.setFileId(filevo.getAtchFileId());	//첨부파일 아이디
		vo.setFileSeq(filevo.getFileSn());		//파일순번
		vo = srmven0020Service.selectFileInfo(vo);

		String filename = vo.getTempFileName();	//서버저장 파일명
		String original = vo.getFileNmae();		//원본파일명
		String filestrecours = imageUploadPath;	//파일 업로드 경로

		if ("".equals(filename)) {	//서버저장 파일이 없을 경우
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if ("".equals(original)) {	//원본 파일이 없을 경우
			original = filename;
		}

		stordFilePath.append(filestrecours).append("/").append(filename);


		//--------------------------------------------------
		// 2012.10.29 jaeyulim 파일 다운로드 취약점 보완
		String file = stordFilePath.toString();
		String fileName = original;

		for(int i=0; i<INVALID_FILE_PATH.length; i++){
			if(file.indexOf(INVALID_FILE_PATH[i]) >=0 || fileName.indexOf(INVALID_FILE_PATH[i]) >= 0){
				request.setAttribute("message", "invalid file name");
				return new ModelAndView("/common/tools/FileDown");
			}
		}

		model.put("file", new File(file));
		model.put("fileName", original);
		//--------------------------------------------------

		return new ModelAndView("downloadView", model);
	}
	
	/**
	 * 인증정보 정보 중복체크
	 * @param SRMVEN0020VO
	 * @param HttpServletRequest
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCertiInfoCheck.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> selectCertiInfoCheck(@RequestBody SRMVEN0020VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
				
		vo.setVendorCode(sessionVO.getCono()[0]);	// 사업자번호
		
		int cnt = srmven0020Service.selectCertiInfoCheck(vo);	// 중복체크 조회
		
		if (cnt > 0) {	// 중복일 경우
			resultMap.put("message", "OVERLAP_INFO");
		} else {
			resultMap.put("message", "OK_INFO");
		}
		return resultMap;
	}
	
}
