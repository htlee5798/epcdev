package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.dao.SRMSPW0010Dao;
import com.lottemart.epc.edi.srm.model.SRMEVL0050VO;
import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import com.lottemart.epc.edi.srm.model.SRMSPW0010VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0020Service;
import com.lottemart.epc.edi.srm.service.SRMSPW0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 입점상담 / 입점상담신청  / 입점상당신청 로그인 Controller
 *
 * @author SHIN SE JIN
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0020Controller {

	@Autowired
	private SRMJON0020Service srmjon0020Service;

	@Autowired
	private SRMSPW0010Service srmspw0010Service;

	@Autowired
	private ConfigurationService config;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 입점상당신청 로그인화면 init
	 *
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJON0020.do")
	public String init(Model model, HttpServletRequest request) throws Exception {

		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		if (session != null) {    // 세션이 있는 경우 입점상담신청 내역 리스트 화면으로 분기
			return "/edi/srm/SRMJON002001";
		}
		model.addAttribute("serverType", serverType);
		return "/edi/srm/SRMJON0020";
	}

	/**
	 * 입점상담 신청 로그인
	 *
	 * @param SRMJON0020VO
	 * @param HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJONLogin.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> SRMJONLogin(@RequestBody SRMJON0020VO vo, HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기 등록된 내역 확인
		SRMJON0020VO resultVO = srmjon0020Service.selectSRMJONLogin(vo);

		if (resultVO == null || resultVO.getIrsNo() == null || resultVO.getIrsNo().equals("")) {    //사업자등록번호가 없으면 팝업x 신청페이지로 이동
			resultMap.put("msg", "NOT_EXIST");

			//----- Session 생성 --------------------
			srmjon0020Service.setSession(vo, request);

		} else {
			if (!resultVO.getSellerNameLoc().equals(vo.getSellerNameLoc()) || !resultVO.getCountry().equals(vo.getCountry())) {    // 사업자등록번호는 일치하며 사업자명이나 국가가 다른경우
				resultMap.put("msg", "NOT_NAME");
			} else {    // 정보일치
				resultMap.put("msg", "EXIST");
			}
		}

		return resultMap;
	}

	/**
	 * 입점상담 신청 로그인(비밀번호 포함)
	 *
	 * @param SRMJON0020VO
	 * @param HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJONReLogin.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> SRMJONReLogin(@RequestBody SRMJON0020VO vo, HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		SRMJON0020VO resultVO = srmjon0020Service.selectSRMJONReLogin(vo);    // 기 등록된 내역 확인

		if (StringUtil.isEmpty(vo.getTempPw())) return null;

		String tempPw = SRMCommonUtils.EncryptSHA256(vo.getTempPw());        // 암호화

		if (resultVO.getPassCheckCnt() >= 15) {                                // 비밀번호 오류 횟수 초과 로그인 금지
			resultMap.put("msg", "PASSCHECK_OUT");
			return resultMap;
		}

		if (tempPw.equals(resultVO.getTempPw())) {                            // 비밀번호가 맞을 경우

			// 임시비밀번호 발급여부 확인 로직
			SRMSPW0010VO srmspw0010vo = new SRMSPW0010VO();
			srmspw0010vo.setSellerCode(resultVO.getSellerCode());
			srmspw0010vo.setHouseCode(resultVO.getHouseCode());
			srmspw0010vo.setIrsNo(resultVO.getIrsNo());
			String isTempPw = srmspw0010Service.selectTempPwFlag(srmspw0010vo);
			if (isTempPw != null && isTempPw.equals("Y")) {
				resultMap.put("msg", "TEMP_PW");
				return resultMap;
			}

//			int isPwChgOver90 =  srmspw0010Service.selectIsNotChangePasswordOver90(srmspw0010vo);
//			if (isPwChgOver90 > 0) {
//				resultMap.put("msg", "PW_CHG_OVER90");
//				return resultMap;
//			}

			int isNotAccessBefore90 =  srmspw0010Service.selectIsNotAccessBefore90(srmspw0010vo);
			if (isNotAccessBefore90 > 0) {
				resultMap.put("msg", "NOT_ACCESS_BEFORE90");
				return resultMap;
			}

			srmjon0020Service.updatePassCheckCntReset(vo);                    // 비밀번호 오류 횟수 카운트 초기화

			srmjon0020Service.setSession(vo, request);                        // Session 생성
			resultMap.put("msg", "EXIST");

		} else {                                                            // 비밀번호가 맞지 않을 경우
			srmjon0020Service.updatePassCheckCnt(vo);                        // 비밀번호 오류 횟수 카운트 증가
			resultMap.put("msg", "NOT_PASSWD");

			if (resultVO.getPassCheckCnt() >= 14) {                            // 비밀번호 오류 횟수 초과 로그인 금지
				resultMap.put("msg", "PASSCHECK_OUT");
				return resultMap;
			}
		}

		return resultMap;
	}

	/**
	 * 기존입점상당신청 내역이 존재 할 경우 상담신청 리스트 확인창
	 *
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJON002001.do")
	public String counselCheckPopup() throws Exception {


		return "/edi/srm/SRMJON002001";
	}


	/**
	 * 기존입점상당신청 내역이 존재 할 경우 상담신청 리스트 확인창
	 *
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/searchCompanyPopup.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String searchCompanyPopup(Model model, HttpServletRequest request) throws Exception {


		return "/edi/srm/SRMJON002002";
	}


	/**
	 * 입점상담 신청 로그인
	 *
	 * @param SRMJON0020VO
	 * @param HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJONSearch.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> SRMJONSearch(@RequestBody SRMJON0020VO vo, HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기 등록된 내역 확인
		SRMJON0020VO resultVO = srmjon0020Service.selectSRMJONSearch(vo);
		resultMap.put("result", resultVO);

		if (resultVO == null || resultVO.getIrsNo() == null || resultVO.getIrsNo().equals("")) {    //사업자등록번호가 없으면 팝업x 신청페이지로 이동
			resultMap.put("msg", "NOT_EXIST");
		} else {
			resultMap.put("msg", "EXIST");

		}

		return resultMap;
	}


	/**
	 * 상담신청 리스트 조회
	 *
	 * @param SRMJON0020VO
	 * @param HttpServletRequest
	 * @return List<SRMJON0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCounselList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectCounselList(@RequestBody SRMJON0020VO vo, HttpServletRequest request) throws Exception {

		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		vo.setHouseCode(session.getHouseCode());
		vo.setIrsNo(session.getIrsNo());

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMJON0020VO> list = null;

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(vo.getPageSize());

		resultMap.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		int totalCnt = srmjon0020Service.selectCounselListCount(vo);
		paginationInfo.setTotalRecordCount(totalCnt);

		list = srmjon0020Service.selectCounselList(vo);
		resultMap.put("list", list);

		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		resultMap.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return resultMap;
	}
}

