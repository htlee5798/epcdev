/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import lcn.module.common.util.DateUtil;
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
import com.lottemart.epc.board.model.PSCPBRD0007VO;
import com.lottemart.epc.board.service.PSCPBRD0007Service;
import com.lottemart.epc.common.model.CommentVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommentService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.common.util.WebUtil;
import com.lottemart.epc.common.util.SecureUtil;

/**
 * @Class Name : PSCPBRD0007Controller
 * @Description : 건의사항 상세 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 2:33:50 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

@Controller
public class PSCPBRD0007Controller {

	private static final String BOARD_ATCH_FILE_SAVE_PATH = "";

	private static final Logger logger = LoggerFactory.getLogger(PSCPBRD0007Controller.class);

	@Autowired
	private ConfigurationService config;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	// 첨부파일 관련
	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "FileMngUtil")
	private FileMngUtil fileMngUtil;

	// 댓글 관련
	@Resource(name = "CommentService")
	private CommentService commentService;

	@Autowired
	private PSCPBRD0007Service pscpbrd0007Service;

	private final static String[] whiteExt = { "doc", "docx", "rtf", "txt", "csv", "xls", "xlsx", "ppt", "pptx", "pdf",
			"bmp", "gif", "jpg", "jpeg", "mp4", "png", "tif", "wmv", "zip", "7z", "rar", "hwp" };

	/**
	 * Desc : 업체문의사항 상세 내용을 조회하는 메소드
	 * @Method Name : selectSuggestionDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectSuggestionDetailPopup.do")
	public String selectSuggestionDetailPopup(@ModelAttribute("pscpbrd0007VO") PSCPBRD0007VO vo, Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 조회수 증가
		pscpbrd0007Service.updateSuggestionReadCount(vo);

		PSCPBRD0007VO paramVO = pscpbrd0007Service.selectSuggestionDetailPopup(vo);

		List<FileVO> fileList = null;
		if (paramVO.getAtchFileId() != null && !"".equals(paramVO.getAtchFileId())) {
			FileVO fileVO = new FileVO();
			fileVO.setAtchFileId(paramVO.getAtchFileId());
			fileList = fileMngService.selectFileInfs(fileVO);
		}

		CommentVO commentVO = new CommentVO();
		commentVO.setBoardSeq(paramVO.getBoardSeq());
		List<DataMap> commentList = commentService.selectCommentList(commentVO);

		model.addAttribute("commentList", commentList);
		model.addAttribute("fileList", fileList);
//		paramVO.setContent(MimeUtil.getHTMLCode(paramVO.getContent()));
		model.addAttribute("detail", paramVO);

		List<String> vendorList = LoginUtil.getVendorList(epcLoginVO);
		model.addAttribute("isVendor", vendorList.contains(paramVO.getRegId()));

		return "board/PSCPBRD0007";
	}

	/**
	 * Desc : 업체문의사항 상세 정보를 저장하는 메소드
	 * @Method Name : updateSuggestionDetailPopup
	 * @param vo
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/updateSuggestionDetailPopup.do")
	public String updateSuggestionDetailPopup(@ModelAttribute("pscpbrd0007VO") PSCPBRD0007VO vo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//String sessionKey = config.getString("lottemart.epc.session.key");
		//EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		DataMap param = new DataMap(request);

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		//String saveUrl = config.getString("board.image.mime.save.url") + DateUtil.getToday("yyyyMM");
		//String savePath = config.getString("board.image.mime.save.path") + DateUtil.getToday("yyyyMM");

		//Mime타입 파일 저장
//		NamoMime mime = MimeUtil.save(mptRequest.getParameter("contents"), saveUrl, savePath);

		String fileId = vo.getAtchFileId();
		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(fileId);

		try {
			// 현재 게시물의 다음 첨부파일 번호를 얻어온다. 없으면 1 리턴
			int maxFileSN = fileMngService.getMaxFileSN(fileVO);

			List<FileVO> fileList = null;
			final Map<String, MultipartFile> files = mptRequest.getFileMap();

			if (!files.isEmpty()) {

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

				fileList = fileMngUtil.parseFileInf(files, "MTG_", maxFileSN, fileId, BOARD_ATCH_FILE_SAVE_PATH);

				if (maxFileSN > 1) {
					// 기존 첨부파일이 존재할 경우 추가로 첨부, ATCH_FILE_ID 유지
					fileMngService.insertAppendFileInfs(fileList);
				} else {
					// 기존 첨부파일이 존재하지 않을 경우 새로운 ATCH_FILE_ID 생성, 추후 게시물 데이터에 UPDATE
					fileId = fileMngService.insertFileInfs(fileList);
				}
			}

			vo.setAtchFileId(fileId);
			vo.setContent(param.getString("contents"));
//			vo.setContent(mime.getBodyContent());

			pscpbrd0007Service.updateSuggestionDetailPopup(vo);
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			request.setAttribute("msg", e.getMessage());
		} catch(Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return selectSuggestionDetailPopup(vo, model, request);
	}
	
	/**
	 * Desc : COMMENT정보를 저장하는 메소드
	 * @Method Name : insertCommentPopup
	 * @param pscpbrd0007VO
	 * @param commentVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/insertCommentPopup.do")
	public String insertCommentPopup(@ModelAttribute("pscpbrd0007VO") PSCPBRD0007VO pscpbrd0007VO, @ModelAttribute("commentVO") CommentVO commentVO, Model model, HttpServletRequest request) throws Exception {

		//String sessionKey = config.getString("lottemart.epc.session.key");
		//EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		commentVO.setUserIp(WebUtil.getRemoteAddr(request));
		commentVO.setModId(commentVO.getRegId());
		commentVO.setMemberNo(commentVO.getRegId());

		commentVO.setLineComment(SecureUtil.stripXSS(commentVO.getLineComment()));

		commentService.insertComment(commentVO);

		return selectSuggestionDetailPopup(pscpbrd0007VO, model, request);
	}

	/**
	 * Desc : COMMENT정보를 삭제하는 메소드
	 * @Method Name : deleteCommentPopup
	 * @param pscpbrd0007VO
	 * @param commentVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/deleteCommentPopup.do")
	public String deleteCommentPopup(@ModelAttribute("pscpbrd0007VO") PSCPBRD0007VO pscpbrd0007VO, @ModelAttribute("commentVO") CommentVO commentVO, Model model, HttpServletRequest request) throws Exception {

		//String sessionKey = config.getString("lottemart.epc.session.key");
		//EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		commentService.deleteComment(commentVO);

		return selectSuggestionDetailPopup(pscpbrd0007VO, model, request);
	}

	/**
	 * Desc : 파일 정보 및 실제 파일을 삭제하는 메소드
	 * @Method Name : deleteFile
	 * @param pscpbrd0007VO
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/deleteFile.do")
	public String deleteFile(@ModelAttribute("pscpbrd0007VO") PSCPBRD0007VO pscpbrd0007VO, @ModelAttribute("fileVO") FileVO fileVO, Model model, HttpServletRequest request) throws Exception {

		//String sessionKey = config.getString("lottemart.epc.session.key");
		//EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// 파일 데이터 삭제
		fileMngService.deleteFileInf(fileVO);

		// 첨부파일이 모두 지워졌을 경우는 BOARD 테이블의 ATCH_FILE_ID 함께 삭제
		int maxFileSN = fileMngService.getMaxFileSN(fileVO);
		if (maxFileSN <= 1) {
			pscpbrd0007Service.deleteAtchFileId(pscpbrd0007VO);
		}

		// 실제 파일 삭제
		fileMngUtil.deleteRealFile(fileVO, BOARD_ATCH_FILE_SAVE_PATH);

		return selectSuggestionDetailPopup(pscpbrd0007VO, model, request);
	}
}