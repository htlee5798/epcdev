package com.lottemart.epc.edi.srm.controller;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.srm.model.SRMQNA0000VO;
import com.lottemart.epc.edi.srm.model.SRMQNA0010VO;
import com.lottemart.epc.edi.srm.service.SRMQNA0010Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 입점상담 FAQ Controller
 * 
 * @author PARK IL YOUNG
 * @since 2024.07.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.20  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMQNA0010Controller {

	private static final Logger logger = LoggerFactory.getLogger(SRMQNA0010Controller.class);

	@Autowired
	private SRMQNA0010Service srmqna0010Service;

	@RequestMapping(value = "/edi/srm/SRMQNA0010.do")
	public String loadTenantQnaWritePage(HttpServletRequest request,  ModelMap model) throws Exception {

		model.addAttribute("namoPath", ConfigUtils.getString("namo.link.path")); //
		return "/edi/srm/SRMQNA0010";
	}

	@ResponseBody
	@RequestMapping (value="/edi/srm/fetchQnaStoreArea.json", method= RequestMethod.POST)
	public List<SRMQNA0000VO> fetchQnaStoreArea(HttpServletRequest request, @RequestBody SRMQNA0000VO srmqna0000VO) throws Exception {

		List<SRMQNA0000VO> qnaStoreAreaList = null;

		try {
			String foodYnFlag = srmqna0000VO.getMartSuperFg();
			if (foodYnFlag != null) {
				qnaStoreAreaList = srmqna0010Service.selectQnaStoreAreaList(srmqna0000VO);
			}
		} catch(Exception e) {
			logger.debug("SRMQNA0010>fetchQnaStoreArea : " + e.getMessage());
		}

		return qnaStoreAreaList;
	}

	@ResponseBody
	@RequestMapping (value="/edi/srm/fetchQnaStore.json", method= RequestMethod.POST)
	public List<SRMQNA0000VO> fetchQnaStore(HttpServletRequest request, @RequestBody SRMQNA0000VO srmqna0000VO) throws Exception {

		List<SRMQNA0000VO> qnaStoreList = null;

		try {
			String areaCd = srmqna0000VO.getAreaCd();
			if (areaCd != null) {
				qnaStoreList = srmqna0010Service.selectQnaStoreList(srmqna0000VO);
			}
		} catch(Exception e) {
			logger.debug("SRMQNA0010>fetchQnaStore : " + e.getMessage());
		}

		return qnaStoreList;
	}

	@ResponseBody
	@RequestMapping (value="/edi/srm/fetchQnaCategory.json", method= RequestMethod.POST)
	public List<SRMQNA0000VO>fetchQnaCategory(HttpServletRequest request, @RequestBody SRMQNA0000VO srmqna0000VO) throws Exception {

		List<SRMQNA0000VO> qnaCategoryList = null;

		try {
			String foodYnFg = srmqna0000VO.getFoodYnFg();
			if (foodYnFg != null) {
				qnaCategoryList = srmqna0010Service.selectQnaCategoryList(srmqna0000VO);
			}
		} catch(Exception e) {
			logger.debug("SRMQNA0010>fetchQnaCategory : " + e.getMessage());
		}

		return qnaCategoryList;
	}

	@ResponseBody
	@RequestMapping (value="/edi/srm/insertQnaInfo.do", method= RequestMethod.POST)
	public HashMap<String, String> insertQnaInfo(HttpServletRequest request, @ModelAttribute SRMQNA0010VO srmqna0010VO) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String boardAttachFileNo = "";
		String compInfoAttachFileNo = "";
		String bizPlanAttachFileNo = "";
		String bizRegCertAttachFileNo = "";

		try {
			// 테넌트 입점문의 파일 업로드
			boardAttachFileNo = srmqna0010Service.uploadQnaFile(srmqna0010VO.getBoardAttachFile(), srmqna0010VO.getSellerCode());
			srmqna0010VO.setBoardAttachNo(boardAttachFileNo);
			compInfoAttachFileNo = srmqna0010Service.uploadQnaFile(srmqna0010VO.getCompInfoAttachFile(), srmqna0010VO.getSellerCode());
			srmqna0010VO.setCompInfoAttachNo(compInfoAttachFileNo);
			bizPlanAttachFileNo = srmqna0010Service.uploadQnaFile(srmqna0010VO.getBizPlanAttachFile(), srmqna0010VO.getSellerCode());
			srmqna0010VO.setBizPlanAttachNo(bizPlanAttachFileNo);
			bizRegCertAttachFileNo = srmqna0010Service.uploadQnaFile(srmqna0010VO.getBizRegCertAttachFile(), srmqna0010VO.getSellerCode());
			srmqna0010VO.setBizRegCertAttachNo(bizRegCertAttachFileNo);

			// 테넌트 입점문의 정보 저장
			srmqna0010Service.insertQnaInfo(srmqna0010VO);

			// 이메일 전송
			srmqna0010Service.sendEmailToSeller(srmqna0010VO);

			resultMap.put("rtnStatus", "S");
		} catch(Exception e) {
			logger.debug("SRMQNA0010>insertQnaInfo : " + e.getMessage());
			resultMap.put("rtnStatus", "F");
		}

		return resultMap;
	}

}
