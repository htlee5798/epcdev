package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMJON0041VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;
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
 * 입점상담 / 입점상담신청  / 입찰상담 정보등록 [상세정보]
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0041Controller {

	private static final Logger logger = LoggerFactory.getLogger(SRMJON0041Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private SRMJON0041Service srmjon0041Service;

	@Autowired
	private SRMJON0043Service srmjon0043Service;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	public static final String[] INVALID_FILE_PATH = {"../", ".."};

	/**
	 * 입찰상담 정보등록 [상세정보]
	 * @param SRMJON0041VO
	 * @param Model
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0041.do")
	public String init(SRMJON0041VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		SRMJON0043VO searchVo = new SRMJON0043VO();
		searchVo.setHouseCode(session.getHouseCode());
		searchVo.setSellerCode(session.getSellerCode());
		searchVo.setReqSeq(vo.getReqSeq());
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(searchVo, request));
//		model.addAttribute("srmComp",srmjon0041Service.selectHiddenCompDetailInfo(vo));
		return "/edi/srm/SRMJON0041";
	}


	/**
	 * 입찰상담 정보등록 [상세정보] 저장
	 * @param Model
	 * @param SRMJON0041VO
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/updateHiddenCompDetailInfo.do")
	public String updateHiddenCompDetailInfo(Model model, SRMJON0041VO vo, HttpServletRequest request) throws Exception {
		//상세정보 UPDATE
		srmjon0041Service.updateHiddenCompDetailInfo(vo);

		model.addAttribute("url", StringUtil.null2str(vo.getUrl()));
//		model.addAttribute("srmComp",srmjon0041Service.selectHiddenCompDetailInfo(vo));
		SRMJON0043VO searchVo = new SRMJON0043VO();
		searchVo.setHouseCode(vo.getHouseCode());
		searchVo.setSellerCode(vo.getSellerCode());
		searchVo.setReqSeq(vo.getReqSeq());
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(searchVo, request));
		return "/edi/srm/SRMJON0041";
	}

	/**
	 * 파일 다운로드(파일 경로 및 이름을 통한 다운로드)
	 * @param HttpServletRequest
	 * @param Map<String, Object>
	 * @param FileVO
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/FileDown.do")
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

		if (StringUtil.isEmpty(filename)) {
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if (StringUtil.isEmpty(original)) {
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
