/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.namo.NamoMime;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.service.LtsmsService;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0012VO;
import com.lottemart.epc.board.service.PSCPBRD0011Service;
import com.lottemart.epc.board.service.PSCPBRD0012Service;
import com.lottemart.epc.common.model.CommentVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommentService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.common.util.MimeUtil;
import com.lottemart.epc.common.util.WebUtil;

/**
 * @Class Name : PSCPBRD0012Controller
 * @Description : 고객센터문의사항 상세 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 19. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

@Controller
public class PSCPBRD0012Controller {

	private static final String BOARD_ATCH_FILE_SAVE_PATH = "";
	
	private final static Logger logger = LoggerFactory.getLogger(PSCPBRD0012Controller.class);
	
	@Autowired
	private ConfigurationService config;

	@Autowired
	private CommonCodeService commonCodeService;
	
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
	
	// 댓글 관련 
	@Resource(name="CommentService")
	private CommentService commentService;	

	@Autowired
	private PSCPBRD0012Service pscpbrd0012Service;
	
	/**
	 * Desc : 고객센터문의사항 상세 내용을 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectCcQnaDetailPopup.do")
	public String selectCcQnaDetailPopup(@ModelAttribute("pscpbrd0012VO") PSCPBRD0012VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		request.setAttribute("epcLoginVO", epcLoginVO);
			
		PSCPBRD0012VO paramVO =  pscpbrd0012Service.selectCcQnaDetailPopup(vo, epcLoginVO);
		
		List<FileVO> fileList = null;
		if(paramVO.getAtchFileId() != null && !"".equals(paramVO.getAtchFileId())) {
			FileVO fileVO = new FileVO();
			fileVO.setAtchFileId(paramVO.getAtchFileId());
			fileList = fileMngService.selectFileInfs(fileVO);
		}
		
		CommentVO commentVO = new CommentVO();
		commentVO.setBoardSeq(paramVO.getBoardSeq());
		List<DataMap> commentList = commentService.selectCommentList(commentVO);
		
		model.addAttribute("commentList", commentList);
		model.addAttribute("fileList", fileList);
		
		List<PSCPBRD0012VO> relationList = pscpbrd0012Service.selectCcQnaDetailList(vo);
		
		request.setAttribute("detail", paramVO);
		request.setAttribute("relationList", relationList);
		
		// 진행상태 공통코드 조회
		List<DataMap> codeList3 = commonCodeService.getCodeList("QA004");
		request.setAttribute("codeList3", codeList3);
		
		List<String> vendorList = LoginUtil.getVendorList(epcLoginVO);
		model.addAttribute("isVendor", vendorList.contains(paramVO.getRegId()));
		
		return "board/PSCPBRD0012";
	}
	
	/**
	 * Desc : 고객센터문의사항 답변 상세 내용을 등록하는 메소드
	 * @Method Name : selectReCcQnaDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectReCcQnaDetailPopup.do")
	public String selectReCcQnaDetailPopup(@ModelAttribute("pscpbrd0012VO") PSCPBRD0012VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		
		PSCPBRD0012VO paramVO =  pscpbrd0012Service.selectCcQnaDetailPopup(vo,null);
		
		request.setAttribute("detail", paramVO);
		
		return "board/PSCPBRD0013";
	}
	
	/**
	 * 고객센터문의사항 답변 등록
	 * @Description : 고객센터문의사항 답변을 등록한다.
	 * @Method Name : insertCcQnaPopup
	 * @param VO
	 * @param Model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/insertReCcQnaDetailPopup.do")
	public String insertReCcQnaDetailPopup(@ModelAttribute("pscpbrd0012VO") PSCPBRD0012VO saveVO, Model model, HttpServletRequest request) throws Exception 
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
		
		String boardSeq = saveVO.getBoardSeq();
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

		saveVO.setAtchFileId(fileId);
		//saveVO.setAdminId(epcLoginVO.getAdminId());
		saveVO.setAdminId(epcLoginVO.getRepVendorId());
		try
		{
			pscpbrd0012Service.updateCcQnaDetailPopup(saveVO);
			
			pscpbrd0012Service.insertReCcQnaDetailPopup(saveVO);
			
			Boolean chkVendor = false;
			String[] vendorId = epcLoginVO.getVendorId();
			saveVO.setBoardSeq(boardSeq);
			PSCPBRD0012VO paramVO =  pscpbrd0012Service.selectCcQnaDetail(saveVO);
			
			for(int i=0; i<vendorId.length; i++){
				
				if(vendorId[i].equals(paramVO.getAcceptId())){ 
					chkVendor = true;
					break;
				}
			}
			
			if(chkVendor){ //고객센터에 대한 답변등록인지 체크			
				List<DataMap> cellNoList = pscpbrd0011Service.selectBoardAuthCellNo();
				
				for(int i=0; i<cellNoList.size(); i++){
					DataMap info = cellNoList.get(i);
					
					if(!"".equals(info.getString("CELL_NO"))){ //고객센터 담당자 연락처, 업체 연락처 유무 체크
						String tranMsg = "업체명 : "+epcLoginVO.getLoginNm() + "\n"
				           			   + "업체 답변글이 등록 되었습니다.";
						
						LtnewsmsVO ltnewsmsvo = new LtnewsmsVO();
				    	
				    	ltnewsmsvo.setTRAN_PHONE(info.getString("CELL_NO"));
				    	ltnewsmsvo.setTRAN_CALLBACK("1577-2500"); //임시
				    	ltnewsmsvo.setTRAN_STATUS("1");
				    	ltnewsmsvo.setTRAN_MSG(tranMsg);
				    	ltnewsmsvo.setTRAN_TYPE("4");
				    	ltnewsmsvo.setTRAN_ETC2("O04");
				    	ltnewsmsvo.setTRAN_ETC3(epcLoginVO.getAdminId());
				 
				    	LtsmsService.insertNewSmsSend(ltnewsmsvo);
					}
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
	 * Desc : 고객센터문의사항 상세 정보를 저장하는 메소드
	 * @Method Name : updateCcQnaDetailPopup
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
	@RequestMapping(value = "/board/updateCcQnaDetailPopup.do")
	public String updateCcQnaDetailPopup(@ModelAttribute("pscpbrd0012VO") PSCPBRD0012VO vo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		if(epcLoginVO.getAdminId() == null || epcLoginVO.getAdminId().equals("")) {
			vo.setAdminId(epcLoginVO.getRepVendorId());
		}else {
			vo.setAdminId(epcLoginVO.getAdminId());			
		}

		pscpbrd0012Service.updateCcQnaDetailPopup(vo);
		
		model.addAttribute("msg", "저장되었습니다.");
		
		return "common/messageResult";
	}
	

	/**
	 * Desc : 파일 정보 및 실제 파일을 삭제하는 메소드
	 * @Method Name : deleteFile
	 * @param pscpbrd0012VO
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/deleteCcQnaFile.do")
	public String deleteFile(@ModelAttribute("pscpbrd0012VO") PSCPBRD0012VO pscpbrd0012VO, @ModelAttribute("fileVO") FileVO fileVO, Model model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// 파일 데이터 삭제
		fileMngService.deleteFileInf(fileVO);
		
		// 첨부파일이 모두 지워졌을 경우는 BOARD 테이블의 ATCH_FILE_ID 함께 삭제
		int maxFileSN = fileMngService.getMaxFileSN(fileVO);
		if(maxFileSN <= 1) {
			pscpbrd0012Service.deleteAtchFileId(pscpbrd0012VO);
		}
		
		// 실제 파일 삭제
		fileMngUtil.deleteRealFile(fileVO, BOARD_ATCH_FILE_SAVE_PATH);
		
		return selectCcQnaDetailPopup(pscpbrd0012VO, model, request);
	}
}