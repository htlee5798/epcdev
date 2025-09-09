package com.lottemart.epc.edi.srm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.srm.model.SRMRST0020VO;
import com.lottemart.epc.edi.srm.model.SRMVEN004001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0040VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0040Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;

/**
 * 품질경영평가조치
 * 
 * @author SHIN SE JIN
 * @since 2016.10.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMVEN0040Controller {

	@Autowired
	private SRMVEN0040Service srmven0040Service;
	
	@Autowired
	private ConfigurationService config;

	/**
	 * 품질경영평가조치 화면
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/edi/ven/SRMVEN0040.do"})
	public String init() throws Exception {
		return "/edi/srm/SRMVEN0040";
	}
	
	/**
	 * 품질경영평가조치목록 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectEvalInfoList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectEvalInfoList(@RequestBody SRMVEN0040VO vo, HttpServletRequest request) throws Exception {
		
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		vo.setVenCds(sessionVO.getVendorId());
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMVEN0040VO> list = null;
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(vo.getPageSize());
		
		resultMap.put("paginationInfo", paginationInfo);
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		int totalCnt = srmven0040Service.selectEvalInfoListCount(vo);
		paginationInfo.setTotalRecordCount(totalCnt);
		
		list = srmven0040Service.selectEvalInfoList(vo);
		resultMap.put("list", list);
		
		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		resultMap.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		
		return resultMap;
		
	}
	
	/**
	 * 품질경영평가조치 팝업
	 * @param Model
	 * @param SRMVEN004001VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCorrectiveActionPopup.do")
	public String selectCorrectiveActionPopup(Model model, SRMVEN004001VO vo, HttpServletRequest request) throws Exception {
		
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		model.addAttribute("correctiveAction",srmven0040Service.selectCorrectiveActionList(vo));

		return "/edi/srm/SRMVEN004001";
	}
	
	/**
	 * 품질경영평가조치 상세정보 조회
	 * @param SRMVEN004001VO
	 * @param HttpServletRequest
	 * @return SRMVEN004001VO
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCorrectiveActionDetail.json", method = RequestMethod.POST)
	public @ResponseBody SRMVEN004001VO selectCorrectiveActionDetail(HttpServletRequest request, @RequestBody SRMVEN004001VO vo) throws Exception {
		
		return srmven0040Service.selectCorrectiveActionDetail(vo);
	}
	
	/**
	 * 품질경영평가조치 정보 수정
	 * @param Model
	 * @param SRMRST002004VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/updateCorrectiveActionDetail.do")
	public String updateCompSiteVisitCover3Detail(Model model, SRMVEN004001VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setSellerCode(sessionVO.getRepVendorId());	//변경자 ID(로그인중인 협력코드)
		//----- Session 에서 생성된 코드 사용 End --------------------

		//저장
		srmven0040Service.updateCorrectiveActionDetail(vo);
		model.addAttribute("correctiveAction",srmven0040Service.selectCorrectiveActionList(vo));
		model.addAttribute("save","Y");
		model.addAttribute("evNo",vo.getEvNo());
		model.addAttribute("seq",vo.getSeq());
		return "/edi/srm/SRMVEN004001";
	}
	
	/**
	 * 품질경영평가조치 정보 삭제(초기화)
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/updateCorrectiveActionDetaildel.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateCompSiteVisitCover3Detaildel(HttpServletRequest request, @RequestBody SRMVEN004001VO vo) throws Exception {
		//----- Session 에서 생성된 코드 사용 -------------------
		EpcLoginVO sessionVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setSellerCode(sessionVO.getRepVendorId());	//변경자 ID(로그인중인 협력코드)
		//----- Session 에서 생성된 코드 사용 End --------------------

		return srmven0040Service.updateCorrectiveActionDetaildel(vo);
	}
}
