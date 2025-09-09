package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0028Service;

import lcn.module.common.model.FileVO;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;


/**
 * @Class Name : NEDMPRO0028Controller
 * @Description : ESG 항목등록
 * @Modification Information
 * <pre>`
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025-03-21. 	PARK JONG GYU			최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0028Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0028Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;
	
	@Autowired
	private NEDMPRO0028Service nedmpro0028Service;
	
	public static final String[] INVALID_FILE_PATH = {"../", ".."};
	
	/**
	 * ESG 항목 등록 탭 페이지 (edi)
	 * @param paramVo
	 * @param model
	 * @param reuqest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0028.do")
	public String selectNutOpt(NEDMPRO0028VO paramVo, Model model, HttpServletRequest request) throws Exception {
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));			
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		paramMap.put("pgmId", paramVo.getPgmId());
		paramMap.put("cfmFg", StringUtils.trimToEmpty(paramVo.getCfmFg()));
//		paramMap.put("pgmId", "2021020500007332954");
//		paramMap.put("cfmFg", "3");
		NEDMPRO0020VO prodDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		
//		NEDMPRO0020VO prodDetailInfo = new NEDMPRO0020VO();
//		prodDetailInfo.setPgmId("2021020500007332954");
//		prodDetailInfo.setCfmFg("3");
		model.addAttribute("prodDetailInfo", prodDetailInfo);
		
		List<NEDMPRO0028VO> esgMstL = nedmpro0028Service.selectEsgMstlList (paramVo);
		List<NEDMPRO0028VO> esgMstM = nedmpro0028Service.selectEsgMstMList (paramVo);
		List<NEDMPRO0028VO> esgMstS = nedmpro0028Service.selectEsgMstSList (paramVo);
		
		ObjectMapper mapper = new ObjectMapper();
		String esgMstLJson  = mapper.writeValueAsString(esgMstL);
		String esgMstMJson  = mapper.writeValueAsString(esgMstM);
		String esgMstSJson  = mapper.writeValueAsString(esgMstS);
		
		model.addAttribute("esgMstL", esgMstLJson);
		model.addAttribute("esgMstM", esgMstMJson);
		model.addAttribute("esgMstS", esgMstSJson);
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));
		
		//ESG 첨부파일 확장자제한
		String fileExtLimitStr = config.getString("fileCheck.atchFile.ext.product.esg");
		model.addAttribute("extLimit", fileExtLimitStr);
				
		
		/*if (prodDetailInfo != null) {
			model.addAttribute("esgMstL", nedmpro0028Service.selectEsgMstlList (paramVo) );
			model.addAttribute("esgMstM", nedmpro0028Service.selectEsgMstMList (paramVo) );
			model.addAttribute("esgMstS", nedmpro0028Service.selectEsgMstSList (paramVo) );
		}*/
		
		return "edi/product/NEDMPRO0028";
	}
	
	
	/**
	 * ESG 항목 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectNewProdEsgList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectNewProdEsgList(@RequestBody NEDMPRO0028VO paramVo) throws Exception {
		/**** ESG 항목 수정가능여부에 따른 리스트 조회 분기용 파라미터셋팅 ****/		
		String editYn = "N";		//수정가능여부
		
		String pgmId = paramVo.getPgmId();
		String cfmFg = StringUtils.trimToEmpty(paramVo.getCfmFg());
		
		//신상품등록 상세정보 조회
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", pgmId);	//신상품 가등록아이디
		paramMap.put("cfmFg", cfmFg);	//확정여부
		NEDMPRO0020VO prodDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		if(prodDetailInfo != null) {
			String mdSendDivnCd = StringUtils.defaultString(prodDetailInfo.getMdSendDivnCd());	//MD 전송여부
			//MD 전송상태가 아닐경우에만 수정가능 (확정요청 이전까지 수정가능)
			if("".equals(mdSendDivnCd)) {
				editYn = "Y";
			}
		}
		paramVo.setEditYn(editYn);
		/*****************************************************/
		
		return nedmpro0028Service.selectNewProdEsgList(paramVo);
	}
	
	/**
	 * ESG 항목 등록( ESG 상품 - 적용 )
	 * @param vo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertNewProdEsg.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> insertNewProdEsg(MultipartHttpServletRequest multipartRequest, 
			NEDMPRO0028VO vo, HttpServletRequest request) throws Exception {
		return nedmpro0028Service.insertNewProdEsg(multipartRequest,vo, request);
	}
	
	
	/**
	 * ESG 항목 등록( ESG 상품 - 미적용 )
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/updateNewProdEsg.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> updateNewProdEsg( NEDMPRO0028VO vo, HttpServletRequest request) throws Exception {
		return nedmpro0028Service.updateNewProdEsg(vo, request);
	}
	
	/**
	 * ESG 항목 삭제
	 * @param vo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteNewProdEsg.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> deleteNewProdEsg(@RequestBody NEDMPRO0028VO vo, HttpServletRequest request) throws Exception {
		return nedmpro0028Service.deleteNewProdEsg(vo, request);
	}
	
	/**
	 * ESG 인증서 파일 다운로드
	 * @param request
	 * @param model
	 * @param filevo
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/esgFileDown.do")
	public ModelAndView download(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO filevo) throws Exception {
		System.out.println("asdfaasdfasdf : " +  filevo.getAtchFileId() );
		StringBuffer stordFilePath = new StringBuffer();

		CommonFileVO vo = new CommonFileVO();
		vo.setFileId(filevo.getAtchFileId());
		vo = nedmpro0028Service.selectProdEsgFile(vo);
		
		String filename = vo.getTempFileName();
		String original = vo.getFileNmae();
		String filestrecours = vo.getFilePath();
		
		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if ("".equals(original)) {
			original = filename;
		}
		System.out.println("sadfasdfsdafsad : "  + filename );
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
	
	/**
	 * ESG 인증정보 저장 (요청 시, 파일 생성)
	 * @param nEDMPRO0028VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateNewProdEsgInfoWithFile.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateNewProdEsgInfoWithFile(NEDMPRO0028VO nEDMPRO0028VO, HttpServletRequest request) throws Exception {
		return nedmpro0028Service.updateNewProdEsgInfoWithFile(nEDMPRO0028VO, request);
	}
	
	/**
	 * ESG 인증유형 조회
	 * @param paramVo
	 * @param request
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectEsgMstlList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<NEDMPRO0028VO> selectEsgMstlList(@RequestBody NEDMPRO0028VO paramVo,  HttpServletRequest request) throws Exception {
		return nedmpro0028Service.selectEsgMstMList(paramVo);
	}
	
	/**
	 * ESG 인증상세유형 조회
	 * @param paramVo
	 * @param request
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectEsgMstSList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<NEDMPRO0028VO> selectEsgMstSList(@RequestBody NEDMPRO0028VO paramVo,  HttpServletRequest request) throws Exception {
		return nedmpro0028Service.selectEsgMstSList(paramVo);
	}
	
}
