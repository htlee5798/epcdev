package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0050VO;
import com.lottemart.epc.edi.srm.service.SRMEVL0050Service;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;
import lcn.module.common.model.FileVO;
import lcn.module.framework.property.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * 품질경영평가 / 품질평가 / 평가총평
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.11  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMEVL0050Controller {
	private static final Logger logger = LoggerFactory.getLogger(SRMEVL0050Controller.class);

	@Autowired
	SRMEVL0050Service srmevl0050Service;

	@Autowired
	private SRMJON0041Service srmjon0041Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	public static final String[] INVALID_FILE_PATH = {"../", ".."};

	/**
	 * 최종점검 내용 등록
	 * @param Model
	 * @param SRMEVL0050VO
	 * @param HashMap
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/edi/evl/SRMEVL0050.do")
	public String init(Model model, SRMEVL0050VO vo, HttpServletRequest request) throws Exception {

		//점검요약
		model.addAttribute("siteVisit1", srmevl0050Service.selectQualityEvaluationSiteVisit1(vo, request));
		//참석자 LIST
		model.addAttribute("siteVisit2List", srmevl0050Service.selectQualityEvaluationSiteVisit2(vo));
		//조치내용 LSIT
		model.addAttribute("siteVisit3List", srmevl0050Service.selectQualityEvaluationSiteVisit3(vo, request));
		//첨부파일 LIST
		model.addAttribute("attachFileList", srmevl0050Service.selectQualityEvaluationAttachFileList(vo));
		//조치사항 대분류 코드
		model.addAttribute("evItemType1CodeList", srmevl0050Service.selectEvlTabList(vo, request));

		model.addAttribute("vo", vo);
		return "/edi/srm/SRMEVL0050";
	}


	/**
	 * 평가완료
	 * @param HttpServletRequest
	 * @param SRMEVL0050VO
	 * @param RedirectAttributes
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/edi/evl/SRMEVLupdateQualityEvaluationComplete.do")
	public String updateQualityEvaluationComplete(HttpServletRequest request, SRMEVL0050VO vo, Model model) throws Exception {
		srmevl0050Service.updateQualityEvaluationComplete(vo, request);
		
			model.addAttribute("tempYn", vo.getTempYn());
			//점검요약
			model.addAttribute("siteVisit1", srmevl0050Service.selectQualityEvaluationSiteVisit1(vo, request));
			//참석자 LIST
			model.addAttribute("siteVisit2List", srmevl0050Service.selectQualityEvaluationSiteVisit2(vo));
			//조치내용 LSIT
			model.addAttribute("siteVisit3List", srmevl0050Service.selectQualityEvaluationSiteVisit3(vo, request));
			//첨부파일 LIST
			model.addAttribute("attachFileList", srmevl0050Service.selectQualityEvaluationAttachFileList(vo));

			model.addAttribute("vo", vo);
			
		return "/edi/srm/SRMEVL0050";
	}

	/**
	 * 상세보기 팝업
	 * @param Model
	 * @param SRMEVL0050VO
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationSiteVisitDetailPopup.do")
	public String selectQualityEvaluationSiteVisitDetailPopup(Model model, SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		//점검요약
		//2019-01-04 오승현 
		//100분위 비율 점수를 환산하기 위해 (중대위반괸리 점수 계산)
		model.addAttribute("siteVisit1", srmevl0050Service.selectQualityEvaluationSiteVisit1(vo, request));
		
		model.addAttribute("list", srmevl0050Service.selectQualityEvaluationSiteVisitDetailPopup(vo, request));
		return "/edi/srm/SRMEVL005001";
	}



	/**
	 * 결과보고서 팝업
	 * @param Model
	 * @param SRMEVL0050VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationSiteVisitReportPopup.do")
	public String selectQualityEvaluationSiteVisitReportPopup(Model model, SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("vo", vo);
		//점검요약
		model.addAttribute("siteVisit1", srmevl0050Service.selectQualityEvaluationSiteVisit1(vo, request));
		//참석자 LIST
		model.addAttribute("siteVisit2List", srmevl0050Service.selectQualityEvaluationSiteVisit2(vo));
		//조치내용 LSIT
		model.addAttribute("siteVisit3List", srmevl0050Service.selectQualityEvaluationSiteVisit3(vo, request));
		//평가결과 list
		model.addAttribute("siteVisitResultList", srmevl0050Service.selectQualityEvaluationSiteVisitResult(vo, request));

		return "/edi/srm/SRMEVL005002";
	}

	/**
	 * 파일 다운로드(파일 경로 및 이름을 통한 다운로드)
	 * @param HttpServletRequest
	 * @param Map<String, Object>
	 * @param FileVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/FileDown.do")
	public ModelAndView download(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO filevo) throws Exception {

		StringBuffer stordFilePath = new StringBuffer();
		String imageUploadPath = config.getString("edi.srm.file.path");

		CommonFileVO vo = new CommonFileVO();
		vo.setFileId(filevo.getAtchFileId());
		vo.setFileSeq(filevo.getFileSn());
		vo = srmjon0041Service.selectHiddenCompFile(vo);

		String filename = vo.getTempFileName();
		String original = vo.getFileNmae();
		String filestrecours = imageUploadPath;

		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if ("".equals(original)) {
			original = filename;
		}

		stordFilePath.append(filestrecours).append("/").append(filename);


		//--------------------------------------------------
		// 2012.10.29 jaeyulim 파일 다운로드 취약점 보완
		String file = stordFilePath.toString();
		String fileName = original;

		for(int i=0; i<INVALID_FILE_PATH.length; i++){
			if(file.indexOf(INVALID_FILE_PATH[i]) >=0 || fileName.indexOf(INVALID_FILE_PATH[i]) >= 0){
				logger.debug("[invalid file path or name]");
				request.setAttribute("message", "invalid file name");
				return new ModelAndView("/common/tools/FileDown");
			}
		}

		model.put("file", new File(file));
		model.put("fileName", original);
		//--------------------------------------------------

		return new ModelAndView("downloadView", model);
	}
}
