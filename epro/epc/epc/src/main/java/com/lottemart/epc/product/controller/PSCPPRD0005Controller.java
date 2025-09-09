package com.lottemart.epc.product.controller;


import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

//import lcn.module.common.namo.NamoMime;
//import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.MimeUtil;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.service.PSCPPRD0005Service;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCPPRD0005Controller.java
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
@Controller("pscpprd0005Controller")
public class PSCPPRD0005Controller implements ServletContextAware {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0005Controller.class);

	@Autowired
	private PSCPPRD0005Service pscpprd0005Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * 추가설명 정보 폼 페이지
	 * @Description : 추가설명 정보 목록 초기페이지 로딩
	 * @Method Name : prdDescriptionForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductDescriptionForm.do")
	public String selectProductDescriptionForm(HttpServletRequest request) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("typeCd", "002");
		PSCPPRD0005VO resultDescr = pscpprd0005Service.selectPrdDescription(paramMap);

		String Seq = (String) resultDescr.getSeq();
		// String AddDesc = resultDescr.getAddDesc();

		/*if (resultDescr != null && AddDesc != null) {
			resultDescr.setAddDesc(MimeUtil.getHTMLCode(resultDescr.getAddDesc()));
		}*/
		
		if (resultDescr != null && Seq != null) {
			request.setAttribute("resultBeDescr", "1");
		} else {
			request.setAttribute("resultBeDescr", "2");
		}

		request.setAttribute("resultDescr", resultDescr);

		return "product/internet/PSCPPRD000501";
	}

	/**
	 * 추가설명 정보 입력,수정 폼 페이지
	 * @Description : 추가설명 정보 입력,수정 초기페이지 로딩 (팝업)
	 * @Method Name : insertProduectDescriptionForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/insertProduectDescriptionForm.do")
	public String insertProduectDescriptionForm(HttpServletRequest request) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("typeCd", "002");
		PSCPPRD0005VO resultDescr = pscpprd0005Service.selectPrdDescription(paramMap);
		PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(paramMap);

		String AddDesc = resultDescr.getAddDesc();

		if (resultDescr != null && AddDesc != null) {
			resultDescr.setAddDesc(MimeUtil.getHTMLCode(resultDescr.getAddDesc()));
		}

		request.setAttribute("resultDescr", resultDescr);
		request.setAttribute("prdMdAprInfo", prdMdAprInfo);

		return "product/internet/PSCPPRD000502";
	}

	public String makeSubFolderForOnline(String imgName) {
		String subFolderNames = subFolderName(imgName);
		String uploadDir = config.getString("online.product.detail.image.path") + "/" + subFolderNames;

		// logger.debug("real dir ==>" + uploadDir + "<==");
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		return uploadDir;
	}

	public String subFolderName(String imgName) {
		return imgName.substring(0, 5);
	}

	/**
	 * 추가설명 정보 입력,수정 처리
	 * @Description : 팝업창의 추가설명 정보 입력 버튼 클릭시 추가설명 처리후 그리드를 리로드한다. 
	 * @Method Name : saveProductDescription
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/saveProductDescription.do")
	public String saveProductDescription(HttpServletRequest request) throws Exception {
		// String sessionKey = config.getString("lottemart.epc.session.key");
		// EpcLoginVO epcLoginVO = null;
		// epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		/*if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			// logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}*/

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		// String mode = (String) mptRequest.getParameter("mode");

		PSCPPRD0005VO bean = new PSCPPRD0005VO();
		bean.setProdCd((String) mptRequest.getParameter("prodCd"));
		bean.setSeq((String) mptRequest.getParameter("seq"));
		bean.setImgSubPath((String) mptRequest.getParameter("imgSubPath"));
		bean.setRegId((String) mptRequest.getParameter("vendorId"));
		bean.setAprvCd("001");
		bean.setTypeCd("002");
		bean.setVendorId((String) mptRequest.getParameter("vendorId"));
		bean.setDispTypeCd(StringUtils.defaultIfEmpty(mptRequest.getParameter("dispTypeCd"), "H"));
		// bean.setDispTypeCd(sDispTypeCd == null || "".equals(sDispTypeCd) ? "H" : sDispTypeCd);
		// String mdSrcmkCd = (String) mptRequest.getParameter("mdSrcmkCd");
		// String subFolderNames = subFolderName(mdSrcmkCd);

		/*
		//NaMo
        String saveUrl = config.getString("online.product.detail.image.url") + "/" + subFolderNames + "/" + DateUtil.formatDate(DateUtil.getToday(),"");//System.currentTimeMillis();//호출시 
        String uploadDir = makeSubFolderForOnline(subFolderNames);
        String savePath = uploadDir + "/" + DateUtil.formatDate(DateUtil.getToday(),"");//System.currentTimeMillis();//저장시 
		// logger.debug("uploadPath ==>" + savePath + "<==");

        NamoMime mime = MimeUtil.save(mptRequest.getParameter("addDesc"), saveUrl, savePath);*/
        bean.setAddDesc(mptRequest.getParameter("addDesc"));

        // 값이 있을경우 에러
        String resultStr = validate(bean);
		
		DataMap map = new DataMap();
		map.put("prodCd", request.getParameter("prodCd"));
		map.put("typeCd", "002");

		try {
			int resultCnt = 0;
			int iCnt = pscpprd0005Service.selectPrdMdAprvMstCnt(map);
			if (iCnt > 0) {
				resultCnt = pscpprd0005Service.updatePrdDescriptionHist(bean);
			} else {
				bean.setTitle("상품정보");
				resultCnt = pscpprd0005Service.insertPrdDescriptionHist(bean);
			}

			/*if ("insert".equals(mode)) {
				bean.setTitle("상품정보");
				resultCnt = pscpprd0005Service.insertPrdDescription(bean);
			} else if (mode.equals("update")) {
				resultCnt = pscpprd0005Service.updatePrdDescription(bean);
			}*/

			if (resultCnt > 0) {
				resultStr = messageSource.getMessage("msg.common.success.insert", null, Locale.getDefault());
			} else {
				resultStr = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
			}
		} catch (Exception e) {
			// 오류
			// logger.debug("[[[ Exception ]]]");
			logger.error("", e.getMessage());
		}

		request.setAttribute("result", "success");
		request.setAttribute("message", resultStr);

		return "product/internet/PSCPPRD000502";
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0005VO bean) throws Exception {
		// --체크
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}

		return "";
	}

}
