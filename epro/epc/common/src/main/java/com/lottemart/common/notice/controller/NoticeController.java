package com.lottemart.common.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.notice.service.NoticeService;
import com.lottemart.common.util.DataMap;
/**
 * 공통 공지사항 컨트롤러
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 6. 오후 2:24:30 yhchoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	@Resource(name="FileMngService")
	private FileMngService fileMngService;
	/**
	 * Desc :메인 공지사항 리스트
	 * @Method Name : mainNoticeList
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("/notice/commNoticeList.do")
	public String commNoticeList(HttpServletRequest request) throws Exception {
		LoginSession loginSession = LoginSession.getLoginSession(request);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("strCd", loginSession.getStrCd());
		
		paramMap.put("sysDivnCd", loginSession.getSysDivnCd());
		
		List<DataMap> noticeList = noticeService.mainNoticeList(paramMap);
		request.setAttribute("noticeList", noticeList);
		return "/common/commNotice";
	}
	
	/**
	 * Desc : 공지사항 게시판 상세보기
	 * @Method Name : commNoticeView
	 * @param request,response
	 * @throws Exception
	 * @return common/commNoticeView
	 */
	@RequestMapping("notice/commNoticeView.do")
	public String commNoticeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardSeq = request.getParameter("boardSeq");
		String atchFileId =request.getParameter("atchFileId");
		
		NoticeVO noticeViewInfo = noticeService.selectNoticeView(boardSeq);//공지사항정보
		//트리 리스트
		List<NoticeVO> allStoreList = noticeService.selectAllStoreList(boardSeq);
		FileVO filevo = new FileVO();
		if(!"".equals(atchFileId)){
			filevo.setAtchFileId(atchFileId);
			//파일정보
			List<FileVO> fileList = this.fileMngService.selectFileList(filevo);
			filevo.setAtchFileId(fileList.get(0).getAtchFileId());
			filevo.setStreFileNm(fileList.get(0).getStreFileNm());
			filevo.setOrignlFileNm(fileList.get(0).getOrignlFileNm());
			filevo.setFileStreCours(fileList.get(0).getFileStreCours());
			filevo.setFileSn(fileList.get(0).getFileSn());
		}
		request.setAttribute("noticeViewInfo", noticeViewInfo);
		request.setAttribute("allStoreList", allStoreList);
		request.setAttribute("fileVO", filevo);
		
		return "/common/commNoticeView";
	}
	
	/**
	 * 
	 * @see popupNoticeView
	 * @Locaton    : com.lottemart.common.notice.controller
	 * @MethodName  : popupNoticeView
	 * @author     : hjKim
	 * @Description : 팝업공지 상세보기 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("notice/popupNoticeView.do")
	public String popupNoticeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String title = request.getParameter("title");
		String content =request.getParameter("content");
	
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		
		NoticeVO noticeInfo = noticeService.selectNoticeView(request.getParameter("boardSeq"));
		request.setAttribute("noticeInfo", noticeInfo);
		
		return "/common/popupNoticeView";
	}
}
