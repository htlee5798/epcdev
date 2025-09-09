package com.lottemart.epc.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.model.PSCMCOM0002VO;
import com.lottemart.epc.common.service.PSCMCOM0002Service;
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;
import com.lottemart.epc.common.util.SecureUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0002Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0002Controller.class);
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private PSCMCOM0002Service PSCMCOM0002Service;

//    /** XecureService 선언*/
//    @Resource(name = "xecuredb")
//    private XecuredbConn xecuredbConn;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/selectPartnerFirmsPopup.do")
	public String selectPartnerFirmsPopup(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model) throws Exception {
		
    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());		
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
		
		// 데이터 조회
		List<DataMap> list = PSCMCOM0002Service.selectVendorList(searchVO);
		model.addAttribute("list", list);
		
        int totalCount = 0;
        if(list.size() > 0) {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);		
        model.addAttribute("searchVO", searchVO);
		
		return "common/PSCMCOM0002";
	}
	
	@RequestMapping(value = "/common/selectPartnerSmallPopup.do")
	public String selectPartnerSmallPopup(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		 
    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());		
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		searchVO.setCono(epcLoginVO.getCono()[0]);
		
		// 데이터 조회
		List<DataMap> list = PSCMCOM0002Service.selectVendorsSmallPopupList(searchVO);
		model.addAttribute("list", list);
		
        int totalCount = 0;
        if(list.size() > 0) {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);		
        model.addAttribute("searchVO", searchVO);
		
		return "common/PSCMCOM00023";
	}
	
//	@RequestMapping(value = "/common/selectPartnerFirmsPopupSSL.do")
//	public String selectPartnerFirmsPopupSSL(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model) throws Exception {
//		
//    	/** paging setting */
//    	PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
//		paginationInfo.setPageSize(searchVO.getPageSize());		
//		
//		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
//				
//		searchVO.setGubn(SecureUtil.stripXSS(searchVO.getGubn()));		
//		searchVO.setVendorId(SecureUtil.stripXSS(searchVO.getVendorId()));
//		searchVO.setVendorNm(SecureUtil.stripXSS(searchVO.getVendorNm()));
//		searchVO.setCono(SecureUtil.stripXSS(searchVO.getCono()));
//		
//		if( searchVO.getGubn() != null && searchVO.getGubn().equals("CONO"))
//		{
//			logger.debug("loginid = ["+searchVO.getLoginId()+"]");
//			if( searchVO.getLoginId() == null || searchVO.getLoginId().equals("") )
//			{
//				model.addAttribute("msg", "관리자 ID를 입력하세요.");
//				return "common/popupResult";
//			}
//			
//			logger.debug("pwd = ["+searchVO.getPwd()+"]");
//			if( searchVO.getPwd() == null || searchVO.getPwd().equals("") )
//			{
//				model.addAttribute("msg", "관리자 패스워드를 입력하세요.");
//				return "common/popupResult";
//			} else {
//				if (searchVO.getPwd().length() < 30) {
////					searchVO.setPwd(xecuredbConn.hash(searchVO.getPwd())); 					
//				}
//			}
//			
//			//비밀 번호가 일치 하지 않을시
//			if( !PSCMCOM0002Service.checkAdminId(searchVO) )
//			{
//				int pwdErrorCnt = PSCMCOM0002Service.pwdErrorCnt(searchVO);
//				int pwdErrorCntBf = 0;
//				
//				//비밀번호 오류 횟수 5회 이상일시
//				if(pwdErrorCnt >4){
//					model.addAttribute("msg","비밀번호를 5회 이상 잘못 입력하여 로그인이 불가능 합니다.");
//				
//				//비밀번호 오류 횟수 카운트 증가
//				}else{
//					//비밀번호 4회 이상 오류시 ID 비활성화
//					if(pwdErrorCnt>=4){
//						searchVO.setActiveYn("N");
//					}
//					pwdErrorCntBf=pwdErrorCnt+1;
//					searchVO.setPwdErrorCnt(pwdErrorCntBf);
//				PSCMCOM0002Service.insetPwdErrorCnt(searchVO);
//				model.addAttribute("msg", "비밀번호 오류로 협력사 정보를 검색하실수 없습니다.("+pwdErrorCntBf+"회 오류 입니다)");
//				}
//				return "common/popupResult";
//			}
//		}
//		
//		// 데이터 조회		
//		List<DataMap> list ;
//
//		if(searchVO.getLoginId().equals("lottemart_1") || searchVO.getLoginId().equals("lottemart_2") || searchVO.getLoginId().equals("lottemart_3") || searchVO.getLoginId().equals("lottemart_4"))
//		{
//			list = PSCMCOM0002Service.selectVendorListGroupAtt(searchVO);
//		} else {
//			list = PSCMCOM0002Service.selectVendorList(searchVO);
//		}
//		
//		
//		model.addAttribute("list", list);
//		
//        int totalCount = 0;
//        if(list.size() > 0) {
//        	totalCount = list.get(0).getInt("TOTAL_COUNT");
//        }
//        paginationInfo.setTotalRecordCount(totalCount);
//        model.addAttribute("paginationInfo", paginationInfo);		
//        model.addAttribute("searchVO", searchVO);
//		
//		return "common/PSCMCOM00021";
//	}
	
	@RequestMapping(value = "/common/selectSSOPartnerFirmsPopupSSL.do")
	public String selectSSOPartnerFirmsPopupSSL(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model) throws Exception {
		
    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());		
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
		
		
		if( searchVO.getGubn() != null && searchVO.getGubn().equals("CONO") )
		{
			logger.debug("loginid = ["+searchVO.getLoginId()+"]");
			if( searchVO.getLoginId() == null || searchVO.getLoginId().equals("") )
			{
				model.addAttribute("msg", "관리자 ID를 입력하세요.");
				return "common/popupResult";
			}
			
			logger.debug("pwd = ["+searchVO.getPwd()+"]");
			//TODO. 어드민 계정 변경 체크로직 추가 예정 (epc; tad_admin -> srm; chmm_user_info)
			/*if( !PSCMCOM0002Service.ssoCountAdminId(searchVO) )
			{
				model.addAttribute("msg", "어드민계정이 올바르지않아 협력사 정보를 검색하실수 없습니다.");
				return "common/popupResult";
			}*/
		}
		
		List<DataMap> list;
		
		// 데이터 조회
		if(searchVO.getLoginId().equals("lottemart_1") || searchVO.getLoginId().equals("lottemart_2") || searchVO.getLoginId().equals("lottemart_3") || searchVO.getLoginId().equals("lottemart_4"))
		{
			list = PSCMCOM0002Service.selectVendorListGroupAtt(searchVO);
		} else {
			list = PSCMCOM0002Service.selectVendorList(searchVO);
		}

		model.addAttribute("list", list);
		
        int totalCount = 0;
        if(list.size() > 0) {
        	totalCount = list.get(0).getInt("TOTAL_COUNT");
        }
        paginationInfo.setTotalRecordCount(totalCount);
        model.addAttribute("paginationInfo", paginationInfo);		
        model.addAttribute("searchVO", searchVO);
		
		return "common/PSCMCOMSSO00021";
	}
	
	
//	public String selectPartnerFirmsPopupSSLADMIN_old250710(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
//		
//    	/** paging setting */
//    	PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
//		paginationInfo.setPageSize(searchVO.getPageSize());		
//		
//		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//
//		searchVO.setVendorId(SecureUtil.stripXSS(searchVO.getVendorId()));
//		searchVO.setVendorNm(SecureUtil.stripXSS(searchVO.getVendorNm()));
//		searchVO.setCono(SecureUtil.stripXSS(searchVO.getCono()));
//		
//		if( searchVO.getGubn() != null && searchVO.getGubn().equals("CONO") )
//		{
//			logger.debug("loginid = ["+searchVO.getLoginId()+"]");
//			if( searchVO.getLoginId() == null || searchVO.getLoginId().equals("") )
//			{
//				model.addAttribute("msg", "관리자 ID를 입력하세요.");
//				return "common/popupResult";
//			}
//			
//			logger.debug("pwd = ["+searchVO.getPwd()+"]");
//			if( searchVO.getPwd() == null || searchVO.getPwd().equals("") )
//			{
//				model.addAttribute("msg", "관리자 패스워드를 입력하세요.");
//				return "common/popupResult";
//			}else {
////				searchVO.setPwd(xecuredbConn.encrptBysha256(searchVO.getPwd()));
//			}			
//				
//			if( !PSCMCOM0002Service.checkAdminId(searchVO) )
//			{
//				model.addAttribute("msg", "어드민계정이 올바르지않아 협력사 정보를 검색하실수 없습니다.");
//				return "common/popupResult";
//			}
//		}
//		
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//		
//		List<DataMap> list ;
//			
//		if(epcLoginVO.getAdminId().equals("lottemart_1") || epcLoginVO.getAdminId().equals("lottemart_2") || epcLoginVO.getAdminId().equals("lottemart_3") || epcLoginVO.getAdminId().equals("lottemart_4") )
//		{
//			list = PSCMCOM0002Service.selectVendorListAdminGroupAtt(searchVO);
//		} else {
//			list = PSCMCOM0002Service.selectVendorListAdmin(searchVO);
//		}
//		
//		
//		// 데이터 조회
//		model.addAttribute("list", list);
//		
//        int totalCount = 0;
//        if(list.size() > 0) {
//        	totalCount = list.get(0).getInt("TOTAL_COUNT");
//        }
//        paginationInfo.setTotalRecordCount(totalCount);
//        model.addAttribute("paginationInfo", paginationInfo);		
//        model.addAttribute("searchVO", searchVO);
//		
//		return "common/PSCMCOM00022";
//	}	
	
	
	/**
	 * [관리자] 로그인이후 > 협력사조회 팝업 (로그인 변경 시)
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/selectPartnerFirmsPopupSSLADMIN.do")
	public String selectPartnerFirmsPopupSSLADMIN(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());		
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		/** 파트너사 정보 setting */
		searchVO.setVendorId(SecureUtil.stripXSS(searchVO.getVendorId()));		//파트너사코드
		searchVO.setVendorNm(SecureUtil.stripXSS(searchVO.getVendorNm()));		//파트너사명
		searchVO.setCono(SecureUtil.stripXSS(searchVO.getCono()));				//사업자번호
		
		
		/*
		 * 1. validation
		 * - 관리자 아이디, 비밀번호 입력한 상태에서 조회되도록 처리 (패스워드 유효성 체크는 하지않음. 단순히 입력했는지만 체크) 
		 */
		String loginId = StringUtils.defaultString(searchVO.getLoginId());		//로그인아이디
		String loginPwd = StringUtils.defaultString(searchVO.getPwd());			//로그인패스워드
		String gbn = StringUtils.defaultString(searchVO.getGubn());				//구분
		
		// 구분이 CONO 일 경우에만 체크
		if("CONO".equals(gbn)) {
			//로그인아이디 누락
			if("".equals(loginId)) {
				model.addAttribute("msg", "관리자 ID를 입력하세요.");
				return "common/popupResult";
			}
			
			//로그인패스워드 누락
			if("".equals(loginPwd)) {
				model.addAttribute("msg", "관리자 패스워드를 입력하세요.");
				return "common/popupResult";
			}
		}
		
		
		//TODO_JIA:::: 관리자 체크용 로직추가 필요??
		//로그인 이후...기 때문에... 적어도 아이디가 관리자 아이디인지는 체크하는 로직이 필요할 것 같음
		
		/*
		 * 2. 협력사 리스트 조회
		 */
		/** Session 정보 확인 */
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		String adminId = epcLoginVO.getAdminId();	//관리자아이디
		
		//협력사리스트 조회
		List<DataMap> list = PSCMCOM0002Service.selectVendorListAdmin(searchVO);
		
		//데이터 카운팅
		int totalCount = 0;
        if(list != null && list.size() > 0) totalCount = list.get(0).getInt("TOTAL_COUNT");
        
        paginationInfo.setTotalRecordCount(totalCount);		//페이징 전체행수 셋팅
        
        model.addAttribute("paginationInfo", paginationInfo);		//페이징객체
        model.addAttribute("searchVO", searchVO);					//검색조건
        model.addAttribute("list", list);							//데이터리스트					
        
        return "common/PSCMCOM00022";
	}
	
	/**
	 * [관리자] 로그인 > 협력사조회 팝업 (로그인 시)
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/selectPartnerFirmsPopupSSL.do")
	public String selectPartnerFirmsPopupSSL(@ModelAttribute("searchVO") PSCMCOM0002VO searchVO, ModelMap model) throws Exception {
		
    	/** paging setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());		
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
				
		/** 파트너사 정보 setting */
		searchVO.setGubn(SecureUtil.stripXSS(searchVO.getGubn()));				//구분코드
		searchVO.setVendorId(SecureUtil.stripXSS(searchVO.getVendorId()));		//파트너사코드
		searchVO.setVendorNm(SecureUtil.stripXSS(searchVO.getVendorNm()));		//파트너사명
		searchVO.setCono(SecureUtil.stripXSS(searchVO.getCono()));				//사업자번호
		
		/*
		 * 1. validation
		 * - 관리자 아이디, 비밀번호 입력한 상태에서 조회되도록 처리 (패스워드 유효성 체크는 하지않음. 단순히 입력했는지만 체크) 
		 */
		String loginId = StringUtils.defaultString(searchVO.getLoginId());		//로그인아이디
		String loginPwd = StringUtils.defaultString(searchVO.getPwd());			//로그인패스워드
		
		//로그인아이디 누락
		if("".equals(loginId)) {
			model.addAttribute("msg", "관리자 ID를 입력하세요.");
			return "common/popupResult";
		}
		
		//로그인패스워드 누락
		if("".equals(loginPwd)) {
			model.addAttribute("msg", "관리자 패스워드를 입력하세요.");
			return "common/popupResult";
		}
		
		//TODO_JIA:::: 관리자 체크용 로직추가 필요??
		//자체관리용.. SSO 관리자 아님.... 적어도 아이디 체크하는 로직은 추가되어야 할듯함
		
		/*
		 * 2. 협력사 리스트 조회
		 */
		// 데이터 조회		
		List<DataMap> list = PSCMCOM0002Service.selectVendorList(searchVO);
		
		//데이터 카운팅
		int totalCount = 0;
        if(list != null && list.size() > 0) totalCount = list.get(0).getInt("TOTAL_COUNT");
        
        paginationInfo.setTotalRecordCount(totalCount);		//페이징 전체행수 셋팅
        
        model.addAttribute("paginationInfo", paginationInfo);		//페이징객체
        model.addAttribute("searchVO", searchVO);					//검색조건
        model.addAttribute("list", list);							//데이터리스트					
		
		return "common/PSCMCOM00021";
	}
	
}
