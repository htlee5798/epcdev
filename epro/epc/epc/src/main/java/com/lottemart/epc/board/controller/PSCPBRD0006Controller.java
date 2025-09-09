package com.lottemart.epc.board.controller;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0006SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0006Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;

/**
 * @Class Name : PSCPBRD0006Controller.java
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
@Controller
public class PSCPBRD0006Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0006Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCPBRD0006Service pscpbrd0006Service;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	// 첨부파일 관련
	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "FileMngUtil")
	private FileMngUtil fileMngUtil;

	private final static String[] whiteExt = { "doc", "docx", "rtf", "txt", "csv", "xls", "xlsx", "ppt", "pptx", "pdf",
			"bmp", "gif", "jpg", "jpeg", "mp4", "png", "tif", "wmv", "zip", "7z", "rar", "hwp" };

	/**
	 * 건의사항관리 등록 폼
	 * @Description : 건의사항관리 등록 초기 화면 로딩. 
	 * @Method Name : insertSuggestionPopupForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertSuggestionPopupForm.do")
	public String insertSuggestionPopupForm(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		return "board/PSCPBRD0006";
	}

	/**
	 * 건의사항 등록
	 * @Description : 건의사항을 등록한다.
	 * @Method Name : insertSuggestionPopup
	 * @param VO
	 * @param Model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertSuggestionPopup.do")
	public String insertSuggestionPopup(@ModelAttribute("saveVO") PSCPBRD0006SaveVO saveVO, Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		DataMap param = new DataMap(request);
		//logger.debug("contents = " + param.getString("contents"));
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동

		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		//String saveUrl = config.getString("board.image.mime.save.url") + DateUtil.getToday("yyyyMM");
		//String savePath = config.getString("board.image.mime.save.path") + DateUtil.getToday("yyyyMM");
		String noticeYn = mptRequest.getParameter("noticeYn");

		// Mime타입 파일 저장
//		NamoMime mime = MimeUtil.save(mptRequest.getParameter("contents"), saveUrl, savePath);

		String fileId = saveVO.getAtchFileId();
		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(fileId);

		// 현재 게시물의 첨부파일 번호를 얻어온다. 없으면 1 리턴
		int maxFileSN = fileMngService.getMaxFileSN(fileVO);

		List<FileVO> fileList = null;
		final Map<String, MultipartFile> files = mptRequest.getFileMap();
		//logger.debug("files isEmpty ==>" + files.isEmpty() + "<==");
		//logger.debug("files size    ==>" + files.size() + "<==");

		try {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				MultipartFile file = entry.getValue();
				String orginFileName = file.getOriginalFilename();

				if ("".equals(orginFileName)) {
					continue;
				}
				int index = orginFileName.lastIndexOf(".");
				String fileExt = orginFileName.substring(index + 1);

				if (fileExt != null && !"".equals(fileExt)) {
					String extDiff = fileExt.toLowerCase();

					if (!Arrays.asList(whiteExt).contains(extDiff)) {
						throw new AlertException("업로드 불가한 파일형식 입니다.");
					}
				}
			}

			fileList = fileMngUtil.parseFileInf(files, "MTG_", maxFileSN, fileId, "");
			// logger.debug("fileList ==>" + fileList + "<==");
			fileId = fileMngService.insertFileInfs(fileList);
			// logger.debug("fileId ==>" + fileId + "<==");

			if (!fileId.isEmpty()) {
				saveVO.setAtchFileYn("Y");
			} else {
				saveVO.setAtchFileYn("N");
			}

			if ("Y".equals(noticeYn)) {
				saveVO.setScrpKindCd("01");
			} else {
				saveVO.setScrpKindCd("");
			}

			saveVO.setAtchFileId(fileId);
			// saveVO.setContent(mime.getBodyContent());
			saveVO.setTitle(SecureUtil.stripXSS(param.getString("title")));
			saveVO.setContent(SecureUtil.stripXSS(param.getString("contents")));

			pscpbrd0006Service.insertSuggestionPopup(saveVO);
			model.addAttribute("msg", "저장되었습니다.");
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			model.addAttribute("msg", e.getMessage());
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
		}

		return "common/messageResult";
	}

}
