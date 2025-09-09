package com.lottemart.epc.edi.consult.controller;

import com.lottemart.common.file.util.FileMngUtil;
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.NEDPCOM0010Service;
import com.lottemart.epc.edi.comm.service.sendEmsSmsService;
import com.lottemart.epc.edi.consult.model.*;
import com.lottemart.epc.edi.consult.service.NEDMCST0310Service;
import lcn.module.common.util.HashBox;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

//XecureDB 추가부분 2014-02-04

@Controller
public class NEDMCST0310Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(NEDMCST0310Controller.class);

//	/** XecureService 선언 */
//	@Resource(name = "xecuredb")
//	private XecuredbConn xecuredbConn;

	private NEDMCST0310Service nEDMCST0310Service;

	private NEDPCOM0010Service nEDPCOM0010Service;

	@Autowired
	private sendEmsSmsService sendemssmsService;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;


	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	@Autowired
	public void setNEDPCOM0010Service(NEDPCOM0010Service nEDPCOM0010Service) {
		this.nEDPCOM0010Service = nEDPCOM0010Service;
	}

	@Autowired
	public void setNEDMCST0310Service(NEDMCST0310Service nEDMCST0310Service) {
		this.nEDMCST0310Service = nEDMCST0310Service;
	}

//	/**
//	 * 입점상담 신청 팝업 화면 URL 호출
//	 * @return
//	 */
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310login.do", method = RequestMethod.GET)
//	public String showConsultLoginForm() {
//
//		String domin = config.getString("login.sdomain.url");
//		return "redirect:" + domin + "/epc/edi/consult/NEDMCST0310loginHttps.do";
//	}
//
//	/**
//	 * 입점상당신청 화면 Form Init
//	 * @return
//	 */
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310loginHttps.do", method = RequestMethod.GET)
//	public String showConsultLoginFormHttps() {
//		return "edi/consult/NEDMCST0311";
//
//	}
//
//	/**
//	 * 상담신청 화면
//	 * @return
//	 */
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310loginHttpsNew.do", method = RequestMethod.GET)
//	public String showConsultLoginFormHttpsNew(Model model) {
//		model.addAttribute("serverType", serverType);
//		return "edi/consult/NEDMCST0310login1";
//	}
//
//	/**
//	 * 상담신청 결과화면 Form Init
//	 * @return
//	 */
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do", method = RequestMethod.GET)
//	public String showConsultLoginFormHttpsNewResult(Model model) {
//		model.addAttribute("serverType", serverType);
//		return "edi/consult/NEDMCST0310loginResult";
//	}
//
//	/**
//	 * 사업자 번호 입력후 약관 동의 페이지
//	 * @param nEDMCST0310VO
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/epc/edi/consult/NEDMCST0310Agree.do")
//	public String NEDMCST0310Agree(NEDMCST0310VO nEDMCST0310VO, HttpServletRequest request, Model model) {
//		return "edi/consult/NEDMCST0310Agree";
//	}
//
//	/**
//	 * 약관 동의 후 체크 페이지
//	 * @param nEDMCST0310VO
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	// 업체 비밀번호와 입력한 비밀번호가 맞는지 확인하는 과정, 사용자가 입력한 패스워드 암호화 처리 및 암호화된 패스워드와 대조시킴
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310checkVendorBusinessNo.do", method = RequestMethod.POST)
//	public String getVendorBusinessNo(NEDMCST0310VO nEDMCST0310VO,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//
//		String returnView = "";
//		String bmanNo = (nEDMCST0310VO.getBusinessNo() != null && !"".equals(nEDMCST0310VO.getBusinessNo())) ?  nEDMCST0310VO.getBusinessNo() : "";
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(bmanNo);
//		VendorSession vendorSession = new VendorSession(vendor);
//		request.getSession().setAttribute("vendorSession", vendorSession);
//
//		//System.out.println("vendor.getPepeJgmRsltDivnCd() : ----------------"+ vendor.getPepeJgmRsltDivnCd());
//		//model.addAttribute("gubunBlock", processCheck(request, model));
//
//		String gubunBlock = "N";
//
//		// HashBox hb =nEDMCST0310Service.processCk(businessNo);
//		NEDMCST0310VO resultInfo = nEDMCST0310Service.selectResultInfo(bmanNo);
//
//		if (resultInfo == null) {
//			// if(hb2.get("CHG_STATUS_CD")==""){
//			gubunBlock = "N";
//		} else {
//			if ("0".equals(resultInfo.getChgStatusCd())
//					|| "N".equals(resultInfo.getPapeJgmRsltDivnCd())
//					|| "N".equals(resultInfo.getCnslRsltDivnCd())
//					|| "N".equals(resultInfo.getEntshpRsltDivnCd())) {
//				gubunBlock = "N"; // 신청가능
//			} else if ("1".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "Y"; // 심사중
//			} else if ("2".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "C"; // 신청완료
//			}
//		}
//
//		// 최초 사업자 번호 접속시
//		if (StringUtils.isBlank(vendor.getBmanNo())) {
//			model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType1", null, Locale.getDefault()));
//
//			vendor.setBmanNo(bmanNo);
//			vendor.setRegDivnCd("V0");
//
//			List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//			model.addAttribute("answerList", answerList);
//
//			returnView = "edi/consult/NEDMCST0310applyNew";		//신청페이지
//
//		}
//		// 기존 아이디가 존재할 시
//		else {
//
//			// 비밀번호가 없으면 -> 기존 신청페이지를 감
//			if (vendor.getPasswd() == null) {
//				model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType2", null, Locale.getDefault()));
//
//				List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//				model.addAttribute("answerList", answerList);
//				model.addAttribute("vendor", vendor);
//
//				returnView = "edi/consult/NEDMCST0310applyNew";	//신청페이지
//			}
//			// 비밀번호가 존재 -> 패스워드까지 받는 페이지를 감
//			else {
//			//	System.out.println("leedb2222");
//				model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType3", null, Locale.getDefault()));
//				model.addAttribute("bmanNo", bmanNo);
//
//				gubunBlock = "";
//				returnView = "edi/consult/NEDMCST0310login2";		//패스워드 입력 받는 페이지
//				//returnView = "edi/consult/NEDMCST0310Agree";		//약관 동의 페이지
//
//			}
//
//		}
//		model.addAttribute("serverType", serverType);
//		return returnView;
//
//	}
//
//	// 패스워드를 가진 업체가 아이디/패스워드 입력 했을때 접속하는 페이지
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310checkVendorBusinessNo2.do", method = RequestMethod.POST)
//	public String getVendorBusinessNo2(SearchParam searchParam,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//
//		String returnView = "";
//		String bmanNo = (searchParam.getBusinessNo() != null && !"".equals(searchParam.getBusinessNo())) ?  searchParam.getBusinessNo() : "";
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(bmanNo);
//
//
//
//		// 최초 사업자 번호 접속시
//		if (StringUtils.isBlank(vendor.getBmanNo())) {
//			returnView = "edi/consult/NEDMCST0310login1";
//		}
//
//
//		VendorSession vendorSession = new VendorSession(vendor);
//		request.getSession().setAttribute("vendorSession", vendorSession);
//
//
//		String pwd = (searchParam.getPassword() != null && !"".equals(searchParam.getPassword())) ?  searchParam.getPassword() : "";
//
//		pwd = xecuredbConn.hash(pwd);
//
//		searchParam.setPassword(pwd);
//
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
//
//		String gubunBlock = "N";
//
//		NEDMCST0310VO resultInfo = nEDMCST0310Service.selectResultInfo(bmanNo);
//
//
//
//		// 비밀번호가 틀릴때
//		if (!searchParam.getPassword().equals(vendor.getPasswd())) {
//			model.addAttribute("resultMessage",	getText("msg.supply.consult.mismatch.password"));
//			model.addAttribute("bmanNo",bmanNo);
//			gubunBlock = "";
//			return "edi/consult/NEDMCST0310login2";
//		}
//
//		/////////////////////////추가////////
//
//		List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(bmanNo);
//		List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
//
//		model.addAttribute("vendor", vendor);
//		model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("answerList", answerList);
//		model.addAttribute("attachFileFolder", attachFileFolder);
//		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//
///////////////////////////추가끝////////
//
//
//		if (resultInfo == null) {
//			// if(hb2.get("CHG_STATUS_CD")==""){
//			gubunBlock = "N";
//		} else {
//			if ("0".equals(resultInfo.getChgStatusCd())
//					|| "N".equals(resultInfo.getPapeJgmRsltDivnCd())
//					|| "N".equals(resultInfo.getCnslRsltDivnCd())
//					|| "N".equals(resultInfo.getEntshpRsltDivnCd())) {
//				gubunBlock = "N"; // 신청가능
//			} else if ("1".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "Y"; // 심사중
//			} else if ("2".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "C"; // 신청완료
//			}
//		}
//
//
//		// 구분 블록 = "N" -> 업체가 작성완료 전이거나,MD가 평가 반려 했을때
//		if (gubunBlock == "N") {
//			model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType2", null, Locale.getDefault()));
//			returnView = "edi/consult/NEDMCST0310applyNew";
//		}
//		// 구분 블록 = "Y" 혹은 "C"일때 -> MD 심사 진행중 일때,평가완료 되었을때
//		else {
//			model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType4", null, Locale.getDefault()));
//			if ("1".equals(vendor.getKindCd())) {
//				//returnView = "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//				returnView = "edi/consult/NEDMCST0310applyNewProductFinal";
//	//			System.out.println("leedb20150724_3333333333");
//				//List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//				//model.addAttribute("answerList", answerList);
//
//			} else if ("2".equals(vendor.getKindCd())) {
//				//returnView = "redirect:/edi/consult/NEDMCST0310applyNewSupportFinal.do";
//				returnView = "edi/consult/NEDMCST0310applyNewSupportFinal";
//				//return "edi/consult/NEDMCST0311";
//	//			System.out.println("leedb20150724_444444444444111");
//				//List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//				//model.addAttribute("answerList", answerList);
//
//			} else if ("3".equals(vendor.getKindCd())) {
//				//returnView = "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//				returnView = "edi/consult/NEDMCST0310applyNewTenantFinal";
//
//	//			System.out.println("leedb20150724_55555555555");
//			//	List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//			//	model.addAttribute("answerList", answerList);
//
//			} else if ("4".equals(vendor.getKindCd())) {
//				//returnView = "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//				returnView = "edi/consult/NEDMCST0310applyNewMobileFinal";
//				//			System.out.println("leedb20150724_55555555555");
//				//	List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//				//	model.addAttribute("answerList", answerList);
//
//			} else {
//				returnView = "redirect:/epc/edi/consult/NEDMCST0310insertStep4past.do";
//	//			System.out.println("leedb20150724_666666666");
//			}
//
//		}
//
//	//	System.out.println("leedb20150724_returnView"+returnView);
//		return returnView;
//
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310checkVendorBusinessNo3", method = RequestMethod.POST)
//	public String getVendorBusinessNo3(SearchParam searchParam,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//
//		String returnView = "";
//		String bmanNo = request.getParameter("businessNo");
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(bmanNo);
//		VendorSession vendorSession = new VendorSession(vendor);
//		request.getSession().setAttribute("vendorSession", vendorSession);
//
//		String pwd = request.getParameter("password");
//		searchParam.setPassword(pwd);
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
//		String gubunBlock = "N";
//
//		NEDMCST0310VO resultInfo = nEDMCST0310Service.selectResultInfo(bmanNo);
//
//		if (resultInfo == null) {
//			// if(hb2.get("CHG_STATUS_CD")==""){
//			gubunBlock = "N";
//		} else {
//			if ("0".equals(resultInfo.getChgStatusCd())
//					|| "N".equals(resultInfo.getPapeJgmRsltDivnCd())
//					|| "N".equals(resultInfo.getCnslRsltDivnCd())
//					|| "N".equals(resultInfo.getEntshpRsltDivnCd())) {
//				gubunBlock = "N"; // 신청가능
//			} else if ("1".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "Y"; // 심사중
//			} else if ("2".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "C"; // 신청완료
//			}
//		}
//
//		// 비밀번호가 틀릴때
//		if (!searchParam.getPassword().equals(vendor.getPasswd())) {
//			model.addAttribute("resultMessage",
//					getText("msg.supply.consult.mismatch.password"));
//			gubunBlock = "";
//	//		System.out.println("여기오나요???");
//	//		System.out.println("여기오나요???------------gubunBlock"+gubunBlock);
//			return "edi/consult/NEDMCST0310loginResult";
//		}
//		// 비밀번호가 맞을때
//		else {
//			// 구분 블록 = "N" -> 업체가 작성완료 전이거나,MD가 평가 반려 했을때
//			if (gubunBlock == "N") {
//				model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType2", null, Locale.getDefault()));
//				returnView = "edi/consult/NEDMCST0310applyNew";
//			}
//			// 구분 블록 = "Y" 혹은 "C"일때 -> MD 심사 진행중 일때,평가완료 되었을때
//			else {
//				model.addAttribute("resultMessage", messageSource.getMessage("msg.consult.require.msgType4", null, Locale.getDefault()));
//				if ("1".equals(vendor.getKindCd())) {
//
//					List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//					model.addAttribute("answerList", answerList);
//					returnView = "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//				} else if ("2".equals(vendor.getKindCd())) {
//
//					List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//					model.addAttribute("answerList", answerList);
//					returnView = "redirect:/edi/consult/NEDMCST0310applyNewSupportFinal.do";
//				} else if ("3".equals(vendor.getKindCd())) {
//
//					List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(bmanNo);
//					model.addAttribute("answerList", answerList);
//					returnView = "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//				} else {
//					returnView = "redirect:/epc/edi/consult/NEDMCST0310insertStep4past.do";
//				}
//			}
//		}
//	//	 System.out.println("leedb------------gubunBlock"+gubunBlock);
//		return returnView;
//
//	}
//
//
//
//	// 업체 비밀번호와 입력한 비밀번호가 맞는지 확인하는 과정, 사용자가 입력한 패스워드 암호화 처리 및 암호화된 패스워드와 대조시킴
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310checkVendorBusinessNoResult.do", method = RequestMethod.POST)
//	public String getVendorBusinessNoResult(SearchParam searchParam,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//		String gubunBlock;
//		// 암호화 수정 부분 2014-02-04 Password를 입력받아서 암호화 처리
//		String pwd = (searchParam.getPassword() != null && !"".equals(searchParam.getPassword())) ?  searchParam.getPassword() : "";
//		// System.out.println("Password------------------------------------------------------"+pwd);
//		pwd = xecuredbConn.hash(pwd);
//		searchParam.setPassword(pwd);
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfoResult(searchParam.getBusinessNo());
//		// Vendor vendor =
//		// nEDMCST0310Service.selectVendorInfo(searchParam.getBusinessNo());
//		// 사업자 번호가 없을 경우 새로운 사업자 번호의 패스워드(암호화)추가
//		if (StringUtils.isBlank(vendor.getBmanNo())) {
//			model.addAttribute("resultMessage",getText("msg.consult.applygo"));
//
//			gubunBlock = "";
//
//			return "edi/consult/NEDMCST0310loginResult";
//
//		} else if (!searchParam.getPassword().equals(vendor.getPasswd())) {
//			model.addAttribute("resultMessage",
//					getText("msg.supply.consult.mismatch.password"));
//			gubunBlock = "";
//			return "edi/consult/NEDMCST0310loginResult";
//		}
//
//
//		VendorSession vendorSession = new VendorSession(vendor);
//		request.getSession().setAttribute("vendorSession", vendorSession);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		// return "edi/consult/NEDMCST0311";
//
//		return "redirect:/epc/edi/consult/NEDMCST0312.do";
//
//	}
//
//
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310findPassword.do", method = RequestMethod.GET)
//	public String findVendorPasswordGet(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		return "edi/consult/NEDMCST0310findPassword";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310findPassword.do", method = RequestMethod.POST)
//	public String findVendorPassword(SearchParam searchParam,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//
//		Integer vendorPasswordCount = nEDMCST0310Service.selectVendorPassword(searchParam);
//		if (vendorPasswordCount == 0) {
//			model.addAttribute("resultMessage",getText("msg.supply.consult.findpasswd.result"));
//			return "edi/consult/NEDMCST0310findPassword";
//		} else {
//			return "edi/consult/NEDMCST0310initPassword";
//		}
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310initPassword.do", method = RequestMethod.GET)
//	public String initVendorPasswordGet(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		return "edi/consult/NEDMCST0310findPassword";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310initPassword.do", method = RequestMethod.POST)
//	public String updateVendorPassword(SearchParam searchParam,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//
//		// 로그인시 입력받은 값의 암호화 처리 , 향후 암호화 DB 패스워드와 비교를 위함
//		String pwd = (searchParam.getPassword() != null && !"".equals(searchParam.getPassword())) ?  searchParam.getPassword() : "";
//		pwd = xecuredbConn.hash(pwd);
//		searchParam.setPassword(pwd);
//
//		Integer vendorPasswordCount = nEDMCST0310Service.updateVendorPassword(searchParam);
//		if (vendorPasswordCount == 1) {
//			model.addAttribute("resultMessage", getText("msg.supply.consult.resetpassword"));
//			return "edi/consult/NEDMCST0310initPasswordResult";
//		} else {
//			return "edi/consult/NEDMCST0310initPassword";
//		}
//
//	}
//
//	/**
//	 * 상담절차 화면 Form Init
//	 * @param request
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0311.do")
//	public String showConsultMain(HttpServletRequest request, Model model)
//			throws Exception {
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		return "edi/consult/NEDMCST0311";
//	}
//
//
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0312.do", method = RequestMethod.GET)
//	public String showResultMain(HttpServletRequest request, Model model)
//			throws Exception {
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		String businessNo = vendorSession.getVendor().getBmanNo();
//
//		model.addAttribute("conList",	nEDMCST0310Service.consultAdminSelectDetail(businessNo));
//		model.addAttribute("conList_past",	nEDMCST0310Service.consultAdminSelectDetailPast(businessNo));
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
//		//return "edi/consult/NEDMCST0312";//20150812 이동빈 과거
//		return "edi/consult/NEDMCST0312";
//	}
//
//	@RequestMapping("/epc/edi/consult/NEDMCST0313.do")
//	public String showFaqMain(HttpServletRequest request, Model model)
//			throws Exception {
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
//		return "edi/consult/NEDMCST0313";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectConsultCategory")
//	public String showSelectConsultcategroyPage(Model model) throws Exception {
//		// List<HashBox> consultcategroyList = nEDPCOM0010Service.selectConsultcategroyListNew();
//		List<NEDMCST0310VO> consultcategroyList = nEDPCOM0010Service.selectConsultcategroyListNew2();
//		model.addAttribute("consultcategroyList", consultcategroyList);
//
//		// return "edi/consult/apply"; //20150714 이동빈
//		return "edi/consult/NEDMCST0310apply";
//
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectConsultKind1")
//	// 이동빈 입점혁신
//	public String showSelectConsultkindPage1(Model model) throws Exception {
//		return "edi/consult/NEDMCST0310applyKind1";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectDepartment")
//	public String showSelectDepartmentPage(Model model) throws Exception {
//		List<HashBox> departmentList = nEDPCOM0010Service.selectDepartmentListNew();
//
//		model.addAttribute("departmentList", departmentList);
//
//		return "edi/consult/NEDMCST0310apply";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectDepartmentDetailPopup")
//	public String showSelectDepartmentDetailPage(
//			@RequestParam Map<String, Object> map, Model model)
//			throws Exception {
//		List<HashBox> departmentDetailList = nEDPCOM0010Service
//				.selectDepartmentDetailList(map);
//
//		model.addAttribute("codeList", departmentDetailList);
//
//		return "edi/consult/NEDMCST0310applyDetail";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectL1cdDetailPopup")
//	public String showSelectL1cdDetailPage(
//			@RequestParam Map<String, Object> map, Model model)
//			throws Exception {
//		List<HashBox> L1cdDetailList = nEDPCOM0010Service.selectL1cdDetailList(map);
//
//		model.addAttribute("codeList", L1cdDetailList);
//
//		return "edi/consult/NEDMCST0310applyDetail";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectAllL1cdDetailPopup")
//	public String showSelectAllL1cdDetailPage(
//			@RequestParam Map<String, Object> map, Model model)
//			throws Exception {
//		List<HashBox> allL1cdDetailList = nEDPCOM0010Service
//				.selectAllL1cdDetailList(map);
//
//		model.addAttribute("codeList", allL1cdDetailList);
//
//		return "edi/consult/NEDMCST0310applyDetailpopUp";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectImgPopup")
//	public String showPopUp0001(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		// List<HashBox> allL1cdDetailList =
//		// nEDPCOM0010Service.selectAllL1cdDetailList(map);
//
//		// model.addAttribute("codeList", allL1cdDetailList);
//
//		return "edi/consult/ImgPopup0001";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0001ImgPopup")
//	public String showPopUpfront0001(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		// List<HashBox> allL1cdDetailList =
//		// nEDPCOM0010Service.selectAllL1cdDetailList(map);
//		// model.addAttribute("codeList", allL1cdDetailList);
//		return "edi/consult/ImgPopupFront0001";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0002ImgPopup")
//	public String showPopUpfront0002(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0002";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0003ImgPopup")
//	public String showPopUpfront0003(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0003";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0006ImgPopup")
//	public String showPopUpfront0006(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0006";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0007ImgPopup")
//	public String showPopUpfront0007(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0007";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0008ImgPopup")
//	public String showPopUpfront0008(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0008";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0009ImgPopup")
//	public String showPopUpfront0009(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0009";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0010ImgPopup")
//	public String showPopUpfront0010(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0010";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310selectFront0011ImgPopup")
//	public String showPopUpfront0011(@RequestParam Map<String, Object> map,
//			Model model) throws Exception {
//		return "edi/consult/ImgPopupFront0011";
//	}
//
//
//
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertApplyKind.do", method = RequestMethod.POST)
//	public String submitConsultApplyKind(Vendor submittedVendorInfo,NEDMCST0310VO nEDMCST0310VO,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model) throws Exception {
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//
//		long fileSize = 0;
//
//
//		if (!submittedVendorInfo.getKindAttachFile1().isEmpty()) {
//			fileSize = submittedVendorInfo.getKindAttachFile1().getSize();
//		}
//
//		if (!submittedVendorInfo.getKindAttachFile2().isEmpty()) {
//			fileSize = submittedVendorInfo.getKindAttachFile2().getSize();
//		}
//
//		if (!submittedVendorInfo.getKindAttachFile3().isEmpty()) {
//			fileSize = submittedVendorInfo.getKindAttachFile3().getSize();
//		}
//
//		if (!submittedVendorInfo.getKindAttachFile4().isEmpty()) {
//			fileSize = submittedVendorInfo.getKindAttachFile4().getSize();
//		}
//
//		if(fileSize > 1024000 * 5){ //5M
//			model.addAttribute("errMsg", 		messageSource.getMessage("msg.consult.require.maxSize5m", null, Locale.getDefault()));
//			model.addAttribute("resultMessage", "not empty");
//			return "edi/consult/NEDMCST0310applyNew";
//		}
//
//
//		String bmanNo = (submittedVendorInfo.getBmanNo() != null && !"".equals(submittedVendorInfo.getBmanNo())) ?  submittedVendorInfo.getBmanNo() : "";
//		String kind =   (submittedVendorInfo.getKindCd() != null && !"".equals(submittedVendorInfo.getKindCd())) ?  submittedVendorInfo.getKindCd() : "";
//		String colVal = (submittedVendorInfo.getAnswer() != null && !"".equals(submittedVendorInfo.getAnswer())) ?  submittedVendorInfo.getAnswer() : "";
//
//
//		NEDMCST0310VO paramVO = new NEDMCST0310VO();
//
//		paramVO.setBmanNo(bmanNo);
//
//		if (kind.equals("1")) {
//			paramVO.setSeq("10");
//		} else if (kind.equals("2")) {
//			paramVO.setSeq("20");
//		} else if (kind.equals("3")) {
//			paramVO.setSeq("30");
//		} else if (kind.equals("4")) {
//			paramVO.setSeq("40");
//		}
//
//
//		paramVO.setColVal(colVal);
//		paramVO.setDispYn("");
//		paramVO.setChgStatusCd("0");
//		paramVO.setKindCd(kind);
//
//		//searchParam.setGroupCode(tmpProduct.getOfficialOrder().getTeamCode());
//
//
//		Vendor dbRegisteredvendor = nEDMCST0310Service.selectVendorInfo(vendorSession.getVendor().getBmanNo());
//		Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo()) ? 0 : 1;
////		System.out.println("vendorCount -------------------- " + vendorCount);
//		nEDMCST0310Service.deleteNewVendorAnswer(paramVO);
//		// 처음 사용하는 협력사의 경우 설문정보 insert / 업체정보 insert
//		if (vendorCount == 0) {
//			nEDMCST0310Service.insertNewVendorAnswer(paramVO);
//		} else {
//
//			// 기 사용업체중 설문 내용은 insert / 기존 업체정보는 업데이트
//			if (dbRegisteredvendor.getKindCd() == null) {
//				nEDMCST0310Service.insertNewVendorAnswer2(paramVO);
//				nEDMCST0310Service.updateNewVendorKindCd(paramVO);
//			}
//
//			else {
//				// 기 사용업체중 설문내용이 있던 업체는 delete후 insert // 기존 업체정보는 업데이트
//			//	nEDMCST0310Service.deleteNewVendorAnswer(hmap);
//				nEDMCST0310Service.insertNewVendorAnswer2(paramVO);
//				nEDMCST0310Service.updateNewVendorKindCd(paramVO);
//			}
//
//		}
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(bmanNo);
//		model.addAttribute("vendor", vendor);
//
//		// TODO koy1983 파일업로드 path 임시 변경
//		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
//		//String fileUploadPath = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult" + "/" + DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
//
//		File folderDir = new File(fileUploadPath);
//		if (!folderDir.isDirectory())	folderDir.mkdirs();
//
//		if (kind.equals("1")) {
//			if (!submittedVendorInfo.getKindAttachFile1().isEmpty()) {
//
//				fileSize = submittedVendorInfo.getKindAttachFile1().getSize();
//
//				if(fileSize > 1024000 * 5){ //5M
//					model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize5m", null, Locale.getDefault()));
//				}else{
//					String zoomImageExt = FilenameUtils.getExtension(submittedVendorInfo.getKindAttachFile1().getOriginalFilename());
//					String attachImageFileName = bmanNo + "_kind_doc";
//					FileOutputStream listImageOs = new FileOutputStream(fileUploadPath	+ "/" + attachImageFileName);
//					FileCopyUtils.copy(submittedVendorInfo.getKindAttachFile1().getInputStream(), listImageOs);
//					submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindAttachFile1().getOriginalFilename());
//					submittedVendorInfo.setUploadFolder(DateFormatUtils.format(vendor.getRegDate(), "yyyyMM"));
//				}
//			} else {
//				submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindFile1());
//			}
//		}else if (kind.equals("2")) {
//			if (!submittedVendorInfo.getKindAttachFile2().isEmpty()) {
//
//				fileSize = submittedVendorInfo.getKindAttachFile2().getSize();
//
//				if(fileSize > 1024000 * 5){ //5M
//					model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize5m", null, Locale.getDefault()));
//				}else{
//					String zoomImageExt = FilenameUtils.getExtension(submittedVendorInfo.getKindAttachFile2().getOriginalFilename());
//					String attachImageFileName = bmanNo + "_kind_doc";
//					FileOutputStream listImageOs = new FileOutputStream(fileUploadPath	+ "/" + attachImageFileName);
//					FileCopyUtils.copy(submittedVendorInfo.getKindAttachFile2().getInputStream(), listImageOs);
//					submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindAttachFile2().getOriginalFilename());
//					submittedVendorInfo.setUploadFolder(DateFormatUtils.format(vendor.getRegDate(), "yyyyMM"));
//				}
//			} else {
//				submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindFile2());
//			}
//		}else if (kind.equals("3")) {
//			if (!submittedVendorInfo.getKindAttachFile3().isEmpty()) {
//				fileSize = submittedVendorInfo.getKindAttachFile3().getSize();
//
//				if(fileSize > 1024000 * 5){ //5M
//					model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize5m", null, Locale.getDefault()));
//				}else{
//					String zoomImageExt = FilenameUtils.getExtension(submittedVendorInfo.getKindAttachFile3().getOriginalFilename());
//					String attachImageFileName = bmanNo + "_kind_doc";
//					FileOutputStream listImageOs = new FileOutputStream(fileUploadPath	+ "/" + attachImageFileName);
//					FileCopyUtils.copy(submittedVendorInfo.getKindAttachFile3().getInputStream(), listImageOs);
//					submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindAttachFile3().getOriginalFilename());
//					submittedVendorInfo.setUploadFolder(DateFormatUtils.format(vendor.getRegDate(), "yyyyMM"));
//				}
//			} else {
//				submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindFile3());
//			}
//		}else if (kind.equals("4")) {
//			if (!submittedVendorInfo.getKindAttachFile4().isEmpty()) {
//				fileSize = submittedVendorInfo.getKindAttachFile4().getSize();
//
//				if(fileSize > 1024000 * 5){ //5M
//					model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize5m", null, Locale.getDefault()));
//				}else{
//					String zoomImageExt = FilenameUtils.getExtension(submittedVendorInfo.getKindAttachFile4().getOriginalFilename());
//					String attachImageFileName = bmanNo + "_kind_doc";
//					FileOutputStream listImageOs = new FileOutputStream(fileUploadPath	+ "/" + attachImageFileName);
//					FileCopyUtils.copy(submittedVendorInfo.getKindAttachFile4().getInputStream(), listImageOs);
//					submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindAttachFile4().getOriginalFilename());
//					submittedVendorInfo.setUploadFolder(DateFormatUtils.format(vendor.getRegDate(), "yyyyMM"));
//				}
//			} else {
//				submittedVendorInfo.setAtchFileKindCd(submittedVendorInfo.getKindFile4());
//			}
//		}
//
//		nEDMCST0310Service.updateKindProductInfoApply(submittedVendorInfo);
//
//
//
//		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
//		model.addAttribute("attachFileFolder", attachFileFolder);
//
//		List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(bmanNo);
//		model.addAttribute("vendorProductList", vendorProductList);
//		// 팀 선택용
//		// List<HashBox> consultcategroyList =
//		// nEDPCOM0010Service.selectConsultcategroyListNew2();
//		// model.addAttribute("consultcategroyList", consultcategroyList);
//
//		//희망부서
//		//List<HashBox> consultTeamList = nEDPCOM0010Service.selectConsultTeamList();
//		model.addAttribute("consultTeamList", nEDMCST0310Service.selectTeamCdListApply(nEDMCST0310VO));
//
//		//대분류
//		nEDMCST0310VO.setGroupCode(vendor.getTeamCd());
//		model.addAttribute("l1GroupList", nEDMCST0310Service.selectL1ListApply(nEDMCST0310VO));
//
//		// System.out.println("leedbkindCd -------------------- "
//		// +vendor.getKindCd());
//
//		String returnView = "edi/consult/NEDMCST0310applyNewProducts";
//		if ("1".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewProducts";
//		} else if ("2".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewSupport";
//		} else if ("3".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewTenant";
//		} else if ("4".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewMobile";
//		} else {
//			returnView = "edi/consult/NEDMCST0310applyNewProducts";
//		}
//
//
//		return returnView;
//
//	}
//
//	/*
//	 *
//	 * @RequestMapping(value="/epc/edi/consult/insertApplyKind2", method =
//	 * RequestMethod.GET) public String submitConsultApplyKind2(Vendor
//	 * submittedVendorInfo, HttpServletRequest request, HttpServletResponse
//	 * response, Model model) throws Exception { VendorSession vendorSession =
//	 * (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//	 * //submittedVendorInfo.mappingField();
//	 *
//	 * Vendor dbRegisteredvendor =
//	 * nEDMCST0310Service.selectVendorInfo(vendorSession.getVendor().getBmanNo());
//	 * Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo())
//	 * ? 0 : 1; String businessNo = vendorSession.getVendor().getBmanNo();
//	 *
//	 * logger.debug("필드값 보기 - "+submittedVendorInfo.toString());
//	 *
//	 * if( isNewVendor(vendorSession.getVendor(), vendorCount)) {
//	 *
//	 * //nEDMCST0310Service.insertNewVendorInfo(submittedVendorInfo); //
//	 * nEDMCST0310Service.insertNewVendorInfoApply(submittedVendorInfo); } else {
//	 * //nEDMCST0310Service.updateVendorInfo(submittedVendorInfo); //
//	 * nEDMCST0310Service.updateVendorInfoApply(submittedVendorInfo); }
//	 *
//	 * model.addAttribute("gubunBlock",processCheck(request, model));
//	 *
//	 *
//	 *
//	 *
//	 * //VendorSession vendorSession = (VendorSession)
//	 * WebUtils.getSessionAttribute(request, "vendorSession"); //String
//	 * businessNo = "1111111111"; Vendor vendor =
//	 * nEDMCST0310Service.selectVendorInfo(businessNo); model.addAttribute("vendor",
//	 * vendor);
//	 *
//	 * String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
//	 * "yyyyMM"); model.addAttribute("attachFileFolder", attachFileFolder);
//	 *
//	 * List<VendorProduct> vendorProductList =
//	 * nEDMCST0310Service.selectVendorProduct(businessNo);
//	 * model.addAttribute("vendorProductList", vendorProductList); //팀 선택용
//	 * //List<HashBox> consultcategroyList =
//	 * nEDPCOM0010Service.selectConsultcategroyListNew2();
//	 * //model.addAttribute("consultcategroyList", consultcategroyList);
//	 *
//	 * List<HashBox> consultTeamList = nEDPCOM0010Service.selectConsultTeamList();
//	 * model.addAttribute("consultTeamList", consultTeamList);
//	 *
//	 * //return "edi/consult/applyNewProduct"; return
//	 * "edi/consult/NEDMCST0310applyNewProducts"; }
//	 */
//
//	public boolean isNewVendor(Vendor vendor, Integer vendorCount) {
//		if ("0".equals(vendor.getChgStatusCd()) && vendorCount == 0) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
//
//
//
//	@RequestMapping(value = "/edi/consult/NEDMCST0310step3ImagePopup.do", method = RequestMethod.GET)
//	public String showConsultStep3ImagePopup(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		model.addAttribute("imagePath" ,			ConfigUtils.getString("system.cdn.static.path"));
//		model.addAttribute("imagePathSub" ,			ConfigUtils.getString("system.cdn.static.path.sub"));
//
//		return "edi/consult/NEDMCST0310imagePopup";
//	}
//	@RequestMapping(value = "/edi/consult/NEDMCST0310step3ImagePopupNew.do", method = RequestMethod.GET)
//	public String showConsultStep3ImagePopupNew(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		model.addAttribute("imagePath" ,			ConfigUtils.getString("system.cdn.static.path"));
//		model.addAttribute("imagePathSub" ,			ConfigUtils.getString("system.cdn.static.path.sub"));
//
//		return "edi/consult/NEDMCST0310imagePopupNew";
//	}
//
//
//	@RequestMapping(value = "/edi/consult/NEDMCST0310step3DocumnetPopup.do", method = RequestMethod.GET)
//	public ModelAndView showConsultStep3DocumentPopup(
//			HttpServletRequest request, HttpServletResponse response,
//			NEDMCST0310VO nEDMCST0310VO,
//			Map<String, Object> model) throws Exception {
//		String businessNo = nEDMCST0310VO.getVendorBusinessNo();
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//		String upFolleder = vendor.getUploadFolder();
//
//
//		String systemFileLocation = ConfigUtils.getString("edi.consult.image.path")
//		//String systemFileLocation = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult"
//				+ "/"
//			//	+ DateFormatUtils.format(vendor.getRegDate(), "yyyyMM")
//				+ upFolleder
//				+ "/"
//				+ vendor.getBmanNo() + "_doc";
//		// fileMngUtil.downFileUTF8(response, systemFileLocation,
//		// vendor.getAttachFileCode());
//		model.put("file", new File(systemFileLocation));
//		model.put("fileName", vendor.getAttachFileCode());
//
//		return new ModelAndView("downloadView", model);
//
//	}
//
//
//	@RequestMapping(value = "/edi/consult/NEDMCST0310step3DocumnetPopupNew", method = RequestMethod.GET)
//	public ModelAndView showConsultStep3DocumentPopupNew(
//			HttpServletRequest request, HttpServletResponse response,
//			NEDMCST0310VO nEDMCST0310VO,
//			Map<String, Object> model) throws Exception {
//		String businessNo = nEDMCST0310VO.getVendorBusinessNo();
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//		String systemFileLocation = ConfigUtils
//				.getString("edi.consult.image.path")
//				+ "/"
//			//	+ DateFormatUtils.format(vendor.getRegDate(), "yyyyMM")
//			//	+ "/"
//				+ vendor.getBmanNo() + "_doc";
//		// fileMngUtil.downFileUTF8(response, systemFileLocation,
//		// vendor.getAttachFileCode());
//		model.put("file", new File(systemFileLocation));
//		model.put("fileName", vendor.getAttachFileCode());
//
//		return new ModelAndView("downloadView", model);
//
//	}
//
//	@RequestMapping(value = "/edi/consult/NEDMCST0310step3DocumnetPopupKindCd.do", method = RequestMethod.GET)
//	public ModelAndView NEDMCST0310step3DocumnetPopupKindCd(
//			HttpServletRequest request, HttpServletResponse response,
//			NEDMCST0310VO nEDMCST0310VO,
//			Map<String, Object> model) throws Exception {
//		String businessNo = nEDMCST0310VO.getVendorBusinessNo();
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//		String upFolleder = vendor.getUploadFolder();
//
//
//		String systemFileLocation = ConfigUtils.getString("edi.consult.image.path")
//		//String systemFileLocation = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult"
//				+ "/"
//			//	+ DateFormatUtils.format(vendor.getRegDate(), "yyyyMM")
//				+ upFolleder
//				+ "/"
//				+ vendor.getBmanNo() + "_kind_doc";
//		// fileMngUtil.downFileUTF8(response, systemFileLocation,
//		// vendor.getAttachFileCode());
//		model.put("file", new File(systemFileLocation));
//		model.put("fileName", vendor.getAtchFileKindCd());
//
//		return new ModelAndView("downloadView", model);
//
//	}
//
//
//
//
//	// 상품정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertApplyConfirm.do", method = RequestMethod.POST)
//	public String submitConsultApplyConfirm(Vendor submittedVendorInfo,
//			VendorProduct vendorProduct, HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//
//		String businessNo = vendorSession.getVendor().getBmanNo();
//		Vendor tmpVendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//		String currentTimeString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
//
//		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
//
//		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요
//
//		nEDMCST0310Service.updateVendorInfo(submittedVendorInfo);
//
//		// /////////////////////////////상품시작
//		// /////////////////////////////////////////////////
//
//		// TODO koy1983 파일업로드 path 임시 변경
//		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//		//String imageUploadPath = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult" + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//
//		File folderDir = new File(imageUploadPath);
//		if (!folderDir.isDirectory())
//			folderDir.mkdirs();
//
//		String attachFile1 = "";
//		String attachFile2 = "";
//		String attachFile3 = "";
//
//
//
//		if (!vendorProduct.getAttachFile1().isEmpty()) {
//
//			long fileSize = vendorProduct.getAttachFile1().getSize();
//
//			if(fileSize > 1024000 * 1){ //1M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize1m", null, Locale.getDefault()));
//			}else{
//
//				String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile1().getOriginalFilename());
//				String attachImageFile1Name = businessNo + "_1." + detailImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile1Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile1().getInputStream(),	listImageOs);
//			//	attachImageFile1Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile1Name;
//				vendorProduct.setAttachFileName1(attachImageFile1Name);
//				vendorProduct.setUploadfolder1(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//			}
//
//		} else {
//			vendorProduct.setAttachFileName1(vendorProduct.getUploadFile1());
//		}
//
//		if (!vendorProduct.getAttachFile2().isEmpty()) {
//
//			long fileSize = vendorProduct.getAttachFile2().getSize();
//
//			if(fileSize > 1024000 * 1){ //1M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize1m", null, Locale.getDefault()));
//			}else{
//
//				String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile2().getOriginalFilename());
//				String attachImageFile2Name = businessNo + "_2." + detailImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile2Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile2().getInputStream(),	listImageOs);
//			//	attachImageFile2Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile2Name;
//				vendorProduct.setAttachFileName2(attachImageFile2Name);
//				vendorProduct.setUploadfolder2(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//			}
//
//
//		} else {
//			vendorProduct.setAttachFileName2(vendorProduct.getUploadFile2());
//		}
//
//		if (!vendorProduct.getAttachFile3().isEmpty()) {
//
//			long fileSize = vendorProduct.getAttachFile3().getSize();
//
//			if(fileSize > 1024000 * 1){ //1M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize1m", null, Locale.getDefault()));
//			}else{
//
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile3().getOriginalFilename());
//				String attachImageFile3Name = businessNo + "_3." + zoomImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile3Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile3().getInputStream(),	listImageOs);
//			//	attachImageFile3Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile3Name;
//				vendorProduct.setUploadfolder3(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//				vendorProduct.setAttachFileName3(attachImageFile3Name);
//			}
//
//		} else {
//			vendorProduct.setAttachFileName3(vendorProduct.getUploadFile3());
//		}
//
//		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
//
//			long fileSize = vendorProduct.getVendorAttachFile().getSize();
//
//			if(fileSize > 1024000 * 2){ //2M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize2m", null, Locale.getDefault()));
//			}else{
//
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				String attachImageFileName = businessNo + "_doc";
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
//				FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
//				vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//
//			}
//
//
//		} else {
//			vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
//		}
//
//		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());
//		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());
//
//
//
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//
//		String[] productName = vendorProduct.getAttachProductName();
//		//String[] seq = request.getParameterValues("seq");
//		String[] seq = vendorProduct.getAttachSeq();
//		// String[] productCost =
//		// multipartRequest.getParameterValues("productCost");
//		// String[] productSalePrice =
//		// multipartRequest.getParameterValues("productSalePrice");
//		//
//		// String[] monthlyAverage =
//		// multipartRequest.getParameterValues("monthlyAverage");
//		// String[] productDescription =
//		// multipartRequest.getParameterValues("productDescription");
//		// String[] subAmount =
//		// multipartRequest.getParameterValues("subAmount");
//	//	String[] atchFileFolder = request.getParameterValues("atchFileFolder");
//
//		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
//
//		int arrLength = 0 ;
//		if(productName != null) arrLength = productName.length;
//
//		for (int i = 0; i < arrLength; i++) {
//			VendorProduct tmpVendorProduct = new VendorProduct();
//			tmpVendorProduct.setBusinessNo(businessNo);
//			tmpVendorProduct.setProductName(productName[i]);
//			tmpVendorProduct.setProductCost("notUsed");
//			tmpVendorProduct.setProductSalePrice("notUsed");
//			tmpVendorProduct.setMonthlyAverage("notUsed");
//			tmpVendorProduct.setProductDescription("notUsed");
//			tmpVendorProduct.setSeq(new Integer(seq[i]));
//			tmpVendorProduct.setSellCode("notUsed");
//		//	tmpVendorProduct.setAtchFileFolder(atchFileFolder[i]);
//
//
//
//			if (i == 0) {
//				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName1());
//				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder1());
//			} else if (i == 1) {
//				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName2());
//				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder2());
//			} else {
//				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName3());
//				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder3());
//
//			}
//
//			vendorProductList.add(tmpVendorProduct);
//		}
//
//		//atchFileFolder
//
//		//System.out.println("leedbsetAttachFileCode:"+vendorProductList);
//
//
//		// nEDMCST0310Service.updateVendorProductInfo(tmpVendor, vendorProductList);
//		nEDMCST0310Service.updateVendorProductInfoApply(tmpVendor,vendorProductList);
//		// ///////////////////////상품끝///////////////////////////////////////////////
//
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		return "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//
//	}
//
//	// 지원정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertApplyConfirmSupport.do", method = RequestMethod.POST)
//	public String submitConsultApplyConfirmSupport(Vendor submittedVendorInfo,
//			VendorProduct vendorProduct, HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//
//		String businessNo = vendorSession.getVendor().getBmanNo();
//		Vendor tmpVendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요
//		nEDMCST0310Service.updateVendorInfo(submittedVendorInfo);
//
//		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
//		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());
//
//
//		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
//		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//		//String imageUploadPath = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult" + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//
//		File folderDir = new File(imageUploadPath);
//		if (!folderDir.isDirectory())
//			folderDir.mkdirs();
//
//
//
//		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
//
//
//			long fileSize = vendorProduct.getVendorAttachFile().getSize();
//
//			if(fileSize > 1024000 * 2){ //2M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize2m", null, Locale.getDefault()));
//			}else{
//
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				String attachImageFileName = businessNo + "_doc";
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
//				FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
//				vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//			}
//
//
//		} else {
//			vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
//		}
//
//		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());
//
//		nEDMCST0310Service.updateVendorProductInfoApply(tmpVendor,vendorProductList);
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		return "redirect:/epc/edi/consult/NEDMCST0310applyNewSupportFinal.do";
//
//	}
//
//	// 테넌트 정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertApplyConfirmTenant.do", method = RequestMethod.POST)
//	public String submitConsultApplyConfirmTenant(Vendor submittedVendorInfo,
//			VendorProduct vendorProduct, HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//
//		String businessNo = vendorSession.getVendor().getBmanNo();
//		Vendor tmpVendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요
//		nEDMCST0310Service.updateVendorInfo(submittedVendorInfo);
//
//
//		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
//		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());
//
//
//		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
//		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//		//String imageUploadPath = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult" + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//
//		File folderDir = new File(imageUploadPath);
//		if (!folderDir.isDirectory())
//			folderDir.mkdirs();
//
//		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
//
//			long fileSize = vendorProduct.getVendorAttachFile().getSize();
//
//			if(fileSize > 1024000 * 2){ //2M
//				model.addAttribute("errMsg", messageSource.getMessage("msg.consult.require.maxSize2m", null, Locale.getDefault()));
//			}else{
//
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				String attachImageFileName = businessNo + "_doc";
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
//				FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
//				vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
//			}
//
//
//		} else {
//			vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
//		}
//
//		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());
//		nEDMCST0310Service.updateVendorProductInfoApply(tmpVendor,vendorProductList);
//
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		return "redirect:/epc/edi/consult/NEDMCST0310applyNewTenantFinal.do";
//
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310applyNewFinal.do", method = RequestMethod.GET)
//	public String showConsultapplyNewFinal(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		String businessNo = vendorSession.getVendor().getBmanNo();
//
//		Vendor vendor = nEDMCST0310Service.selectNewVendorInfoApply(businessNo);
//		// Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
//		List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//		List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(businessNo);
//		List<HashBox> consultTeamList = nEDPCOM0010Service.selectConsultTeamList();
//		model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("vendor", vendor);
//		model.addAttribute("attachFileFolder", attachFileFolder);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//		model.addAttribute("answerList", answerList);
//		model.addAttribute("consultTeamList", consultTeamList);
//
//		String returnView = "edi/consult/NEDMCST0310applyNewProductFinal";
//		if ("1".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewProductFinal";
//		} else if ("2".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewSupportFinal";
//		} else if ("3".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewTenantFinal";
//		} else if ("4".equals(vendor.getKindCd())) {
//			returnView = "edi/consult/NEDMCST0310applyNewMobileFinal";
//		} else {
//			returnView = "edi/consult/NEDMCST0310applyNewProductFinal";
//		}
//		return returnView;
//	}
//
//	// 지원
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310applyNewSupportFinal.do", method = RequestMethod.GET)
//	public String showConsultapplyNewSupportFinal(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
////		System.out.println("leedb20150728_000");
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		// System.out.println("leedb====>vendorSession:"+vendorSession);
//		String businessNo = vendorSession.getVendor().getBmanNo();
////		System.out.println("leedb20150728_111");
//		Vendor vendor = nEDMCST0310Service.selectNewVendorInfoApply(businessNo);
//		// Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//		 String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
//		List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//		List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(businessNo);
//		// List<HashBox> consultTeamList = nEDPCOM0010Service.selectConsultTeamList();
//		// model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("vendor", vendor);
//		 model.addAttribute("attachFileFolder", attachFileFolder);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		 model.addAttribute("imagePath" , ConfigUtils.getString("edi.image.url"));
//		model.addAttribute("answerList", answerList);
//		// model.addAttribute("consultTeamList", consultTeamList);
//
//		return "edi/consult/NEDMCST0310applyNewSupportFinal";
//	}
//
//	// 테넌트
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310applyNewTenantFinal.do", method = RequestMethod.GET)
//	public String showConsultapplyNewTenantFinal(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		String businessNo = vendorSession.getVendor().getBmanNo();
//
//		Vendor vendor = nEDMCST0310Service.selectNewVendorInfoApply(businessNo);
//		// Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//		// String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
//		// "yyyyMM");
//		// List<VendorProduct> vendorProductList =
//		// nEDMCST0310Service.selectVendorProduct(businessNo);
//		List<Vendor> answerList = nEDMCST0310Service.selectVendorAnswer(businessNo);
//		// List<HashBox> consultTeamList = nEDPCOM0010Service.selectConsultTeamList();
//		// model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("vendor", vendor);
//		// model.addAttribute("attachFileFolder", attachFileFolder);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		// model.addAttribute("imagePath" ,
//		// ConfigUtils.getString("edi.image.url"));
//		model.addAttribute("answerList", answerList);
//		// model.addAttribute("consultTeamList", consultTeamList);
//
//		return "edi/consult/NEDMCST0310applyNewTenantFinal";
//	}
//
//
//
//
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStepApplyFinal.do", method = RequestMethod.POST)
//	public String submitConsultApplyFinal(HttpServletRequest request,
//			Model model, SearchParam searchParam) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		String businessNo = vendorSession.getVendor().getBmanNo();
//
//		// 패스워드 업데이트 추가
//		String pwd = (searchParam.getPassword() != null && !"".equals(searchParam.getPassword())) ?  searchParam.getPassword() : "";
//		pwd = xecuredbConn.hash(pwd);
//		searchParam.setPassword(pwd);
//		searchParam.setBusinessNo(businessNo);
//		Integer vendorPasswordCount = nEDMCST0310Service.updateVendorPassword(searchParam);
//
//		nEDMCST0310Service.updateVendorStatus(vendorSession.getVendor());
////		System.out.println("leedb" + "상담신청 후");
//		Vendor vendor = nEDMCST0310Service.selectVendorInfoApply(businessNo);
////		System.out.println("leedbbbb7777777777 ");
//		List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//
//		model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("vendor", vendor);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//
////		System.out.println("leedbbbb8888888888 ");
//		HashMap<String, Object> hmap = new HashMap();
//		hmap.put("V_BMAN_NO",businessNo);
//		hmap.put("V_ID","");
//		hmap.put("V_AUTHO_FG","");
//		hmap.put("V_TEL_NO","");
//		hmap.put("V_EMAIL","");
//		hmap.put("V_SUB_INFO_ID","");
//	//	hmap.put("V_EMS_CD","42");
//		hmap.put("V_SVC_SEQ","");
//
//		hmap.put("V_VENDOR_ID","");
//		hmap.put("V_ANX_INFO_CD","");
//		hmap.put("V_DEST_CALL_NO","");
//		hmap.put("V_MSGS","");
//		hmap.put("V_SMS_CD","20");
//
//		hmap.put("V_MSG","");
//		hmap.put("V_ERR","");
//		hmap.put("V_LOG","");
//	//	System.out.println("leedbbbb99999999999 ");
//		if ("1".equals(vendor.getKindCd())) {
//
//			hmap.put("V_EMS_CD","42");
//		    sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
////			System.out.println("leedbbbb442222 ");
//
//		} else if ("2".equals(vendor.getKindCd())) {
//			hmap.put("V_EMS_CD","43");
////			System.out.println("leedbbbb44433333 ");
//			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
//			//sendemssmsService.sendSMSConsultSupport(hmap); 잠시만 개발기에서 끔
//		} else if ("3".equals(vendor.getKindCd())) {
////			System.out.println("leedbbbb4444444 ");
//			hmap.put("V_EMS_CD","44");
//			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
//			//sendemssmsService.sendSMSConsultTenant(hmap); 잠시만 개발기에서 끔
//		} else if ("4".equals(vendor.getKindCd())) {
////			System.out.println("leedbbbb4444444 ");
//			hmap.put("V_EMS_CD","42");
//			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
//			//sendemssmsService.sendSMSConsultTenant(hmap); 잠시만 개발기에서 끔
//		} else {
////			System.out.println("leedbbbb442222 ");
//			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
//		}
//		// sendemssmsService.sendSMS(hmap); 잠시만 개발기에서 끔
//
//		// return "edi/consult/NEDMCST0310applyNewProductFinal";
//		return "redirect:/epc/edi/consult/NEDMCST0310applyNewFinal.do";
//
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310messagePopup")
//	public String messagePopup(HttpServletRequest request, NEDMCST0310VO nEDMCST0310VO,
//			HttpServletResponse response, Model model) throws Exception {
//
//		model.addAttribute("strMsg", getText(nEDMCST0310VO.getStrMsg()));
//		model.addAttribute("objFocus", nEDMCST0310VO.getObjFocus());
//		return "edi/popup/messagePopup";
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0311Process", method = RequestMethod.GET)
//	public String processCheck(HttpServletRequest request, Model model)
//			throws Exception {
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//
//		String businessNo = "";
//
//		if (vendorSession != null) {
//			businessNo = vendorSession.getVendor().getBmanNo();
//		}
//
//		String gubunBlock = "N";
//
//		// HashBox hb =nEDMCST0310Service.processCk(businessNo);
//		NEDMCST0310VO resultInfo = nEDMCST0310Service.selectResultInfo(businessNo);
//
//		if (resultInfo == null) {
//			// if(hb2.get("CHG_STATUS_CD")==""){
//			gubunBlock = "N";
//		} else {
//			if ("0".equals(resultInfo.getChgStatusCd())
//					|| "N".equals(resultInfo.getPapeJgmRsltDivnCd())
//					|| "N".equals(resultInfo.getCnslRsltDivnCd())
//					|| "N".equals(resultInfo.getEntshpRsltDivnCd())) {
//				gubunBlock = "N"; // 신청가능
//			} else if ("1".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "Y"; // 심사중
//			} else if ("2".equals(resultInfo.getChgStatusCd())) {
//				gubunBlock = "C"; // 신청완료
//			}
//		}
//
//
//		return gubunBlock;
//	}
//	/*
//	//입점상담관리 분류 AJAX
//	@RequestMapping("/edi/consult/selectL1List")
//    public ModelAndView getL1List(SearchParam searchParam,
//    		HttpServletRequest request) {
//
//		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
//			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
//		}
//
//		List<EdiCommonCode> resultL1List = nEDMCST0310Service.selectL1List(searchParam);
//		return AjaxJsonModelHelper.create(resultL1List);
//	}
//	*/
//	//입점상담관리 분류 AJAX
//	@RequestMapping("/edi/consult/NEDMCST0310selectL1ListApply.do")
//    public ModelAndView getL1ListApply(NEDMCST0310VO nEDMCST0310VO,
//    		HttpServletRequest request) {
//
//		if(StringUtils.isEmpty(nEDMCST0310VO.getGroupCode())) {
//			nEDMCST0310VO.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
//		}
//
//		List<EdiCommonCode> resultL1List = nEDMCST0310Service.selectL1ListApply(nEDMCST0310VO);
//		return AjaxJsonModelHelper.create(resultL1List);
//	}
//
//	@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep4past", method = RequestMethod.GET)
//	public String showConsultStep4PastPage(HttpServletRequest request,
//			HttpServletResponse response, Model model) throws Exception {
//
//		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//		String businessNo = vendorSession.getVendor().getBmanNo();
//
//		Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
//				"yyyyMM");
//
//		List<Sale> saleList = nEDMCST0310Service.selectSaleInfoByVendor(businessNo);
//		List<EdiCommonCode> otherStoreList = nEDPCOM0010Service.selectOtherStoreList();
//		List<VendorProduct> vendorProductList = nEDMCST0310Service
//				.selectVendorProduct(businessNo);
//
//		List<AutionItem> autionItemListSum = nEDMCST0310Service
//				.selectAuItemSum(businessNo);
//		// List<AutionItem> autionItemList =
//		// nEDMCST0310Service.selectAuItem(businessNo);
//		model.addAttribute("autionItemListSum", autionItemListSum);
//
//		model.addAttribute("vendorProductList", vendorProductList);
//		model.addAttribute("saleList", saleList);
//		model.addAttribute("otherStoreList", otherStoreList);
//		model.addAttribute("vendor", vendor);
//
//		model.addAttribute("attachFileFolder", attachFileFolder);
//		model.addAttribute("gubunBlock", processCheck(request, model));
//		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//		return "edi/consult/NEDMCST0310insertStep4past";
//	}
//
//	// 기본정보 , 최초 로그인 성공 이후 처리 로직
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep1", method = RequestMethod.GET)
//		public String showConsultStep1Page(HttpServletRequest request,
//				HttpServletResponse response, Model model, ModelMap modelMap,
//				NEDMCST0310VO nEDMCST0310VO) throws Exception {
//			// public String showConsultStep1Page(HttpServletRequest request,
//			// HttpServletResponse response, Model model, ModelMap
//			// modelMap,@RequestParam Map<String,Object> hmap ) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//			String teamCd = nEDMCST0310VO.getTeamCd();
//
//			int teamCdInt = Integer.parseInt(teamCd);
//
//			// modelMap.addAttribute("orgcdData",nEDPCOM0010Service.selectOrg(hmap));
//			modelMap.addAttribute("TeamcdData", nEDPCOM0010Service.selectL1Team(teamCd));
//			if (teamCdInt == 17 || teamCdInt == 47) {
//				modelMap.addAttribute("L1cdData", nEDPCOM0010Service.selectL1CdEtc(teamCd));
//			} else {
//				modelMap.addAttribute("L1cdData", nEDPCOM0010Service.selectL1Cd(teamCd));
//			}
//
//			Vendor dbRegisteredvendor = nEDMCST0310Service.selectVendorInfo(vendorSession.getVendor().getBmanNo());
//			Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo()) ? 0 : 1;
//			String pwd = vendorSession.getVendor().getPasswd();
//			/* 대분류 -> 팀코드 변경 - ykson 2014.10.29 */
//			// String l1Cd = request.getParameter("l1Cd");
//			// System.out.println("ykson : "+l1Cd);
//
//			if (isNewVendor(vendorSession.getVendor(), vendorCount)) {
//				// nEDMCST0310Service.insertNewVendorInfoNew(pwd,businessNo,l1Cd);
//				nEDMCST0310Service.insertNewVendorInfoNew(pwd, businessNo, teamCd);
//			} else {
//				// nEDMCST0310Service.updateVendorInfoNew(pwd,businessNo,l1Cd);
//				nEDMCST0310Service.updateVendorInfoNew(pwd, businessNo, teamCd);
//			}
//
//			model.addAttribute("vendor", vendor);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//			return "edi/consult/NEDMCST0310insertStep1";
//		}
//
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep1", method = RequestMethod.POST)
//		public String submitConsultStep1Page(Vendor submittedVendorInfo,
//				HttpServletRequest request, HttpServletResponse response,
//				Model model) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			// submittedVendorInfo.mappingField();
//
//			Vendor dbRegisteredvendor = nEDMCST0310Service
//					.selectVendorInfo(vendorSession.getVendor().getBmanNo());
//			Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo()) ? 0 : 1;
//
//			// logger.debug("필드값 보기 - "+submittedVendorInfo.toString());
//
//			if (isNewVendor(vendorSession.getVendor(), vendorCount)) {
//				nEDMCST0310Service.updateVendorInfo(submittedVendorInfo);
//			} else {
//				nEDMCST0310Service.insertNewVendorInfo(submittedVendorInfo);
//
//			}
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//
//			return "redirect:/epc/edi/consult/NEDMCST0310insertStep2.do";
//		}
//
//		// 매출정보
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep2", method = RequestMethod.GET)
//		public String showConsultStep2Page(HttpServletRequest request,
//				HttpServletResponse response, Model model) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//			List<Sale> saleList = nEDMCST0310Service.selectSaleInfoByVendor(businessNo);
//			List<EdiCommonCode> otherStoreList = nEDPCOM0010Service.selectOtherStoreList();
//
//			model.addAttribute("saleList", saleList);
//			model.addAttribute("otherStoreList", otherStoreList);
//			model.addAttribute("vendor", vendor);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//
//			return "edi/consult/NEDMCST0310insertStep2";
//		}
//
//		// 매출정보
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep2", method = RequestMethod.POST)
//		public String submitConsultStep2Page(Vendor vendor,
//				HttpServletRequest request, HttpServletResponse response,
//				Model model) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//
//			String st = vendor.getFoundationYear();
//			logger.debug("st!!! " + vendor.toString());
//			logger.debug("businessNo!!  " + businessNo);
//
//			/*
//			 * String[] otherStoreCode = request.getParameterValues("other_str_cd");
//			 * String[] ent_str_cnt = request.getParameterValues("ent_str_cnt");
//			 * String[] mg1 = request.getParameterValues("mg1");
//			 *
//			 * String[] mg2 = request.getParameterValues("mg2");
//			 * String[] pcost =request.getParameterValues("pcost");
//			 * String[] subAmount =  request.getParameterValues("subAmount");
//			 * String[] trd_typ_fg = request.getParameterValues("trd_typ_fg");
//			 *
//			 * List<Sale> saleDataList = new ArrayList<Sale>();
//			 * int arrLength = otherStoreCode.length; for(int i=0; i < arrLength; i++) {
//			 * if(!otherStoreCode[i].equals("")){
//			 *
//			 *
//			 * Sale tmpSale = new Sale();
//			 * tmpSale.setBusinessNo(businessNo);
//			 * tmpSale.setOtherStoreCode(otherStoreCode[i]);
//			 * if("".equals(ent_str_cnt[i]) || ent_str_cnt[i] == null){
//			 * 	tmpSale.setEnteredStoreCount(0);
//			 * }else{
//			 * 	tmpSale.setEnteredStoreCount(new Integer(ent_str_cnt[i]));
//			 * }
//			 *
//			 * if("".equals(mg1[i]) || mg1[i] == null){ tmpSale.setMarginRate1(0);
//			 * }else{ tmpSale.setMarginRate1(new Integer(mg1[i])); }
//			 *
//			 * if("".equals(mg2[i]) || mg2[i] == null){ tmpSale.setMarginRate2(0);
//			 * }else{ tmpSale.setMarginRate2(new Integer(mg2[i])); }
//			 *
//			 * if("".equals(pcost[i]) || pcost[i] == null){ tmpSale.setPcost(0);
//			 * }else{ tmpSale.setPcost(new Integer(pcost[i])); }
//			 *
//			 * if("".equals(subAmount[i]) || subAmount[i] == null){
//			 * tmpSale.setSubAmount(0); }else{ tmpSale.setSubAmount(new
//			 * Integer(subAmount[i])); }
//			 *
//			 * tmpSale.setTradeTypeContent(trd_typ_fg[i]);
//			 *
//			 * saleDataList.add(tmpSale);
//			 *
//			 *
//			 * } }
//			 */
//
//			// nEDMCST0310Service.updateVendorSaleInfo(vendor, saleDataList);
//			nEDMCST0310Service.updateVendorSaleInfo(vendor);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//
//			return "redirect:/epc/edi/consult/NEDMCST0310insertStep3.do";
//		}
//
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep3", method = RequestMethod.GET)
//		public String showConsultStep3Page(HttpServletRequest request,
//				HttpServletResponse response, Model model) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//			// if(vendor.getRegDate() != null) {
//			String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
//			model.addAttribute("attachFileFolder", attachFileFolder);
//			// }
//			List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//			model.addAttribute("vendorProductList", vendorProductList);
//			model.addAttribute("vendor", vendor);
//
//			// 이동빈 추가
//			// System.out.println("이동빈businessNo:");
//			// System.out.println(businessNo);
//			// System.out.println("이동빈vendor:");
//			// System.out.println(vendor);
//			// vendor.getL1Cd();
//			// System.out.println(vendor.getL1Cd());
//			String L1_CD = vendor.getL1Cd();
//
//			List<AutionItem> autionItemList = nEDMCST0310Service.selectAuItem(businessNo, L1_CD);
//			// List<AutionItem> autionItemList = nEDMCST0310Service.selectAuItem(L1_CD);
//			// List<AutionItem> autionItemList =
//			// nEDMCST0310Service.selectAuItem(businessNo);
//
//			model.addAttribute("autionItemList", autionItemList);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//			model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//			return "edi/consult/NEDMCST0310insertStep3";
//		}
//
//		// 상품정보
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep3", method = RequestMethod.POST)
//		public String submitConsultStep3Page(VendorProduct vendorProduct,
//				HttpServletRequest request, HttpServletResponse response,
//				Model model) throws Exception {
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//			Vendor tmpVendor = nEDMCST0310Service.selectVendorInfo(businessNo);
//
//			String currentTimeString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
//			String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
//			// String imageUploadPath =
//			// ConfigUtils.getString("edi.consult.image.path")+"/"+currentYearMonth;
//			// //getServletContext().getRealPath("/lim/static_root")+"/consult";getServletContext().getRealPath("/lim/static_root")+"/consult";
//			// if(tmpVendor.getRegDate() != null) {
//
//			String imageUploadPath = ConfigUtils.getString("edi.consult.image.path")+ "/"+ DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//			//String imageUploadPath = "\\\\10.52.51.200\\01.ltm\\83.EPC\\100.UploadFiles\\consult" + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
//
//			// }
//			File folderDir = new File(imageUploadPath);
//			if (!folderDir.isDirectory())	folderDir.mkdirs();
//
//			String attachFile1 = "";
//			String attachFile2 = "";
//			String attachFile3 = "";
//
//			if (!vendorProduct.getAttachFile1().isEmpty()) {
//				String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile1().getOriginalFilename());
//				String attachImageFile1Name = businessNo + "_1." + detailImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + attachImageFile1Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile1().getInputStream(),	listImageOs);
//				vendorProduct.setAttachFileName1(attachImageFile1Name);
//			} else {
//				vendorProduct.setAttachFileName1(vendorProduct.getUploadFile1());
//			}
//
//			if (!vendorProduct.getAttachFile2().isEmpty()) {
//				String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile2().getOriginalFilename());
//				String attachImageFile2Name = businessNo + "_2." + detailImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile2Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile2().getInputStream(),	listImageOs);
//				vendorProduct.setAttachFileName2(attachImageFile2Name);
//			} else {
//				vendorProduct.setAttachFileName2(vendorProduct.getUploadFile2());
//			}
//
//			if (!vendorProduct.getAttachFile3().isEmpty()) {
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile3().getOriginalFilename());
//				String attachImageFile3Name = businessNo + "_3." + zoomImageExt;
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile3Name);
//				FileCopyUtils.copy(vendorProduct.getAttachFile3().getInputStream(),	listImageOs);
//				vendorProduct.setAttachFileName3(attachImageFile3Name);
//			} else {
//				vendorProduct.setAttachFileName3(vendorProduct.getUploadFile3());
//			}
//
//			if (!vendorProduct.getVendorAttachFile().isEmpty()) {
//				String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
//				String attachImageFileName = businessNo + "_doc";
//				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
//				FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
//				vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
//			} else {
//				vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
//			}
//
//			tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());
//			tmpVendor.setPecuSubjectContent(vendorProduct.getContent());
//
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//
//			String[] productName = multipartRequest.getParameterValues("productName");
//			String[] productCost = multipartRequest.getParameterValues("productCost");
//			String[] productSalePrice = multipartRequest.getParameterValues("productSalePrice");
//
//			String[] monthlyAverage = multipartRequest.getParameterValues("monthlyAverage");
//			String[] productDescription = multipartRequest.getParameterValues("productDescription");
//			String[] subAmount = multipartRequest.getParameterValues("subAmount");
//			String[] seq = request.getParameterValues("seq");
//
//			String[] prodArray = multipartRequest.getParameterValues("prodArray");
//
//
//
//			nEDMCST0310Service.deleteAuctionProdItem(businessNo);
//
//			String[] prodArraySeq = prodArray[0].split("/");
//
//
//			if (!"".equals(StringUtils.defaultIfEmpty(prodArray[0], ""))) {
//
//				for (int i = 0; i < prodArraySeq.length; i++) {
//
//					nEDMCST0310Service.insertAuctionProdItem(prodArraySeq[i],businessNo);
//				}
//			}
//
//
//
//			List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
//			int arrLength = productName.length;
//			for (int i = 0; i < arrLength; i++) {
//				VendorProduct tmpVendorProduct = new VendorProduct();
//				tmpVendorProduct.setBusinessNo(businessNo);
//				tmpVendorProduct.setProductName(productName[i]);
//				tmpVendorProduct.setProductCost(productCost[i]);
//				tmpVendorProduct.setProductSalePrice(productSalePrice[i]);
//				tmpVendorProduct.setMonthlyAverage(monthlyAverage[i]);
//				tmpVendorProduct.setProductDescription(productDescription[i]);
//				tmpVendorProduct.setSeq(new Integer(seq[i]));
//				tmpVendorProduct.setSellCode("notUsed");
//				if (i == 0) {
//					tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName1());
//				} else if (i == 1) {
//					tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName2());
//				} else {
//					tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName3());
//				}
//
//				vendorProductList.add(tmpVendorProduct);
//			}
//
//			nEDMCST0310Service.updateVendorProductInfo(tmpVendor, vendorProductList);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//
//			return "redirect:/epc/edi/consult/NEDMCST0310insertStep4.do";
//		}
//
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep4", method = RequestMethod.GET)
//		public String showConsultStep4Page(HttpServletRequest request,
//				HttpServletResponse response, Model model) throws Exception {
//
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//			String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
//
//			List<Sale> saleList = nEDMCST0310Service.selectSaleInfoByVendor(businessNo);
//			List<EdiCommonCode> otherStoreList = nEDPCOM0010Service.selectOtherStoreList();
//			List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//
//			List<AutionItem> autionItemListSum = nEDMCST0310Service.selectAuItemSum(businessNo);
//			// List<AutionItem> autionItemList =
//			// nEDMCST0310Service.selectAuItem(businessNo);
//			model.addAttribute("autionItemListSum", autionItemListSum);
//
//			model.addAttribute("vendorProductList", vendorProductList);
//			model.addAttribute("saleList", saleList);
//			model.addAttribute("otherStoreList", otherStoreList);
//			model.addAttribute("vendor", vendor);
//
//			model.addAttribute("attachFileFolder", attachFileFolder);
//			model.addAttribute("gubunBlock", processCheck(request, model));
//			model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//
//			return "edi/consult/NEDMCST0310insertStep4";
//		}
//
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStepEtc", method = RequestMethod.GET)
//		public String showConsultStep4PageEtc(HttpServletRequest request,
//				HttpServletResponse response, Model model) throws Exception {
//
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//			// Vendor vendor = nEDMCST0310Service.selectVendorInfo2Etc(businessNo);
//			String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
//
//			List<Sale> saleList = nEDMCST0310Service.selectSaleInfoByVendor(businessNo);
//			List<EdiCommonCode> otherStoreList = nEDPCOM0010Service.selectOtherStoreList();
//			List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//
//			List<AutionItem> autionItemListSum = nEDMCST0310Service.selectAuItemSum(businessNo);
//			// List<AutionItem> autionItemList =
//			// nEDMCST0310Service.selectAuItem(businessNo);
//			model.addAttribute("autionItemListSum", autionItemListSum);
//
//			model.addAttribute("vendorProductList", vendorProductList);
//			model.addAttribute("saleList", saleList);
//			model.addAttribute("otherStoreList", otherStoreList);
//			model.addAttribute("vendor", vendor);
//
//			model.addAttribute("attachFileFolder", attachFileFolder);
//			model.addAttribute("gubunBlock", processCheck(request, model));
//			model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
//
//			return "edi/consult/NEDMCST0310insertStep4past";
//		}
//
//		@RequestMapping(value = "/epc/edi/consult/NEDMCST0310insertStep4", method = RequestMethod.POST)
//		public String submitConsultStep4Final(HttpServletRequest request,
//				Model model) throws Exception {
//
//			VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
//			String businessNo = vendorSession.getVendor().getBmanNo();
//
//			nEDMCST0310Service.updateVendorStatus(vendorSession.getVendor());
//			Vendor vendor = nEDMCST0310Service.selectVendorInfo2(businessNo);
//			List<Sale> saleList = nEDMCST0310Service.selectSaleInfoByVendor(businessNo);
//			List<EdiCommonCode> otherStoreList = nEDPCOM0010Service.selectOtherStoreList();
//			List<VendorProduct> vendorProductList = nEDMCST0310Service.selectVendorProduct(businessNo);
//
//			model.addAttribute("vendorProductList", vendorProductList);
//			model.addAttribute("saleList", saleList);
//			model.addAttribute("otherStoreList", otherStoreList);
//			model.addAttribute("vendor", vendor);
//
//			model.addAttribute("gubunBlock", processCheck(request, model));
//
//			HashMap<String, Object> hmap = new HashMap();
//			hmap.put("V_BMAN_NO", businessNo);
//			hmap.put("V_ID", "");
//			hmap.put("V_AUTHO_FG", "");
//			hmap.put("V_TEL_NO", "");
//			hmap.put("V_EMAIL", "");
//			hmap.put("V_SUB_INFO_ID", "");
//			hmap.put("V_EMS_CD", "");
//			hmap.put("V_SVC_SEQ", "");
//
//			hmap.put("V_VENDOR_ID", "");
//			hmap.put("V_ANX_INFO_CD", "");
//			hmap.put("V_DEST_CALL_NO", "");
//			hmap.put("V_MSGS", "");
//			hmap.put("V_SMS_CD", "20");
//
//			hmap.put("V_MSG", "");
//			hmap.put("V_ERR", "");
//			hmap.put("V_LOG", "");
//
//			sendemssmsService.sendSMS(hmap);
//
//			return "edi/consult/NEDMCST0310insertStep4";
//		}
//
//
//		//결격사유 삭제
//		@RequestMapping(value="/edi/consult/NEDMCST0310step3DocumnetDeletePopupKindCd.do", method=RequestMethod.POST, headers="Accept=application/json")
//		public @ResponseBody int NEDMCST0310step3DocumnetDeletePopupKindCd(@RequestBody Vendor vendor, HttpServletRequest request) throws Exception {
//			return nEDMCST0310Service.updateAtchFileKindCd(vendor);
//		}
//
//		//사업소개서 삭제
//		@RequestMapping(value="/edi/consult/NEDMCST0310deleteBusinessIntroduction.do", method=RequestMethod.POST, headers="Accept=application/json")
//		public @ResponseBody int NEDMCST0310deleteBusinessIntroduction(@RequestBody Vendor vendor, HttpServletRequest request) throws Exception {
//			return nEDMCST0310Service.updateAttachFileCode(vendor);
//		}
//
//		//이미지 삭제
//		@RequestMapping(value="/edi/consult/NEDMCST0310deleteImg.do", method=RequestMethod.POST, headers="Accept=application/json")
//		public @ResponseBody int NEDMCST0310deleteImg(@RequestBody Vendor vendor, HttpServletRequest request) throws Exception {
//			return nEDMCST0310Service.NEDMCST0310deleteImg(vendor);
//		}
}
