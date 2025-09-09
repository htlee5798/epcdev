package com.lottemart.epc.edi.consult.controller;

import java.awt.SystemColor;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

//import com.lottemart.common.file.util.FileMngUtil;
//import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.ConfigUtils;
//import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.comm.service.sendEmsSmsService;

import com.lottemart.epc.edi.comm.controller.BaseController;
//import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;

import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.AutionItem;
import com.lottemart.epc.edi.consult.model.VendorSession;
import com.lottemart.epc.edi.consult.service.PEDMSCT0002Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT051Service;

//XecureDB 추가부분 2014-02-04
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;

@Controller
public class PEDMSCT051Controller extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(PEDMSCT051Controller.class);

//	/** XecureService 선언 */
//	@Resource(name = "xecuredb")
//	private XecuredbConn xecuredbConn;

//	@Resource(name = "FileMngUtil")
//	private FileMngUtil fileMngUtil;

	private PEDMSCT051Service consultService;

	private PEDPCOM0001Service commService;

	@Autowired
	private PEDMSCT0002Service pedmsct0002Service;

	@Autowired
	private sendEmsSmsService sendemssmsService;

	@Autowired
	private ConfigurationService config;

	@Autowired
	public void setCommService(PEDPCOM0001Service commService) {
		this.commService = commService;
	}

	@Autowired
	public void setConsultService(PEDMSCT051Service consultService) {
		this.consultService = consultService;
	}

	@RequestMapping(value = "/epc/edi/consult/login", method = RequestMethod.GET)
	public String showConsultLoginForm() {

		String domin = config.getString("login.sdomain.url");
		return "redirect:" + domin + "/epc/edi/consult/loginHttps.do";
	}

	@RequestMapping(value = "/epc/edi/consult/loginHttps.do", method = RequestMethod.GET)
	public String showConsultLoginFormHttps() {

		// return "edi/consult/login"; //20150715 이동빈 입점개편

		return "edi/consult/PEDMCST056";

	}

	@RequestMapping(value = "/epc/edi/consult/loginHttpsNew.do", method = RequestMethod.GET)
	public String showConsultLoginFormHttpsNew() {

		// return "edi/consult/login"; //20150715 이동빈 입점개편
		String gubunBlock = "";
		return "edi/consult/login1";
		// return "edi/consult/PEDMCST056";

	}

	@RequestMapping(value = "/epc/edi/consult/loginHttpsNew2.do", method = RequestMethod.GET)
	public String showConsultLoginFormHttpsNew2() {

		// return "edi/consult/login"; //20150715 이동빈 입점개편
		String gubunBlock = "";
		return "edi/consult/login2";
		// return "edi/consult/PEDMCST056";

	}

	@RequestMapping(value = "/epc/edi/consult/loginHttpsNewResult.do", method = RequestMethod.GET)
	public String showConsultLoginFormHttpsNewResult() {

		// return "edi/consult/login"; //20150715 이동빈 입점개편
		String gubunBlock = "";
		return "edi/consult/loginResult";
		// return "edi/consult/PEDMCST056";

	}

	// 업체 비밀번호와 입력한 비밀번호가 맞는지 확인하는 과정, 사용자가 입력한 패스워드 암호화 처리 및 암호화된 패스워드와 대조시킴
	@RequestMapping(value = "/epc/edi/consult/checkVendorBusinessNo", method = RequestMethod.POST)
	public String getVendorBusinessNo(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		String returnView = "";
		String bmanNo = request.getParameter("businessNo");

		Vendor vendor = consultService.selectVendorInfo(bmanNo);
		VendorSession vendorSession = new VendorSession(vendor);
		request.getSession().setAttribute("vendorSession", vendorSession);

		String gubunBlock = "N";

		// HashBox hb =consultService.processCk(businessNo);
		HashBox hb2 = consultService.selectResultInfo(bmanNo);

		if (hb2 == null) {
			// if(hb2.get("CHG_STATUS_CD")==""){
			gubunBlock = "N";
		} else {
			if ("0".equals(hb2.get("CHG_STATUS_CD"))
					|| "N".equals(hb2.get("PAPE_JGM_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("CNSL_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("ENTSHP_RSLT_DIVN_CD"))) {
				gubunBlock = "N"; // 신청가능
			} else if ("1".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "Y"; // 심사중
			} else if ("2".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "C"; // 신청완료
			}
		}

		// 최초 사업자 번호 접속시
		if (StringUtils.isBlank(vendor.getBmanNo())) {
			model.addAttribute("resultMessage", "최초 혹은 반려 후 접속입니다.");
			vendor.setBmanNo(bmanNo);
			vendor.setRegDivnCd("V0");
			
			List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
			model.addAttribute("answerList", answerList);
			
			returnView = "edi/consult/applyNew";

		}
		// 기존 아이디가 존재할 시
		else {

			// 비밀번호가 없으면 -> 기존 신청페이지를 감
			if (vendor.getPasswd() == null) {
				model.addAttribute("resultMessage", "신청/재신청 하시기 바랍니다.");
				List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
				model.addAttribute("answerList", answerList);
				
				returnView = "edi/consult/applyNew";
			}
			// 비밀번호가 존재 -> 패스워드까지 받는 페이지를 감
			else {
				model.addAttribute("resultMessage", "신청하신 이력이 있어 비번을 넣어주세요");
				model.addAttribute("bmanNo", bmanNo);
				
				gubunBlock = "";
				returnView = "edi/consult/login2";				
			}
		}
		
		return returnView;
	}

	// 패스워드를 가진 업체가 아이디/패스워드 입력 했을때 접속하는 페이지 
	@RequestMapping(value = "/epc/edi/consult/checkVendorBusinessNo2", method = RequestMethod.POST)
	public String getVendorBusinessNo2(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		String returnView = "";
		String bmanNo = request.getParameter("businessNo");

		Vendor vendor = consultService.selectVendorInfo(bmanNo);	

		// 최초 사업자 번호 접속시
		if (StringUtils.isBlank(vendor.getBmanNo())) {					
			returnView = "edi/consult/login";
		}				
				
		VendorSession vendorSession = new VendorSession(vendor);
		request.getSession().setAttribute("vendorSession", vendorSession);

		String pwd = request.getParameter("password");
//		pwd = xecuredbConn.hash(pwd);
		searchParam.setPassword(pwd);
				
		model.addAttribute("gubunBlock", processCheck(request, model));
		
		String gubunBlock = "N";
		
		HashBox hb2 = consultService.selectResultInfo(bmanNo);
		
		// 비밀번호가 틀릴때
		if (!searchParam.getPassword().equals(vendor.getPasswd())) {
			
			model.addAttribute("resultMessage",	getText("msg.supply.consult.mismatch.password"));
			gubunBlock = "";
			return "edi/consult/login2";
		}
		
		/////////////////////////추가////////		
		List<VendorProduct> vendorProductList = consultService.selectVendorProduct(bmanNo);
		List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
		
		model.addAttribute("vendor", vendor);
		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("answerList", answerList);
		model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));		
		/////////////////////////추가끝////////		
		
		if (hb2 == null) {
			// if(hb2.get("CHG_STATUS_CD")==""){
			gubunBlock = "N";
		} else {
			if ("0".equals(hb2.get("CHG_STATUS_CD"))
					|| "N".equals(hb2.get("PAPE_JGM_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("CNSL_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("ENTSHP_RSLT_DIVN_CD"))) {
				gubunBlock = "N"; // 신청가능
			} else if ("1".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "Y"; // 심사중
			} else if ("2".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "C"; // 신청완료
			}
		}

		
			// 구분 블록 = "N" -> 업체가 작성완료 전이거나,MD가 평가 반려 했을때
			if (gubunBlock == "N") {
				model.addAttribute("resultMessage", "신청/재신청 하시기 바랍니다.");
				returnView = "edi/consult/applyNew";
			}
			// 구분 블록 = "Y" 혹은 "C"일때 -> MD 심사 진행중 일때,평가완료 되었을때
			else {
				model.addAttribute("resultMessage", "심사중이거나 심사완료된 상태입니다.");
				if ("1".equals(vendor.getKindCd())) {
					//returnView = "redirect:/epc/edi/consult/applyNewFinal.do";
					returnView = "edi/consult/applyNewProductFinal";
					//List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
					//model.addAttribute("answerList", answerList);

				} else if ("2".equals(vendor.getKindCd())) {
					//returnView = "redirect:/edi/consult/applyNewSupportFinal.do";
					returnView = "edi/consult/applyNewSupportFinal";
					//return "edi/consult/PEDMCST056";
					//List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
					//model.addAttribute("answerList", answerList);

				} else if ("3".equals(vendor.getKindCd())) {
					//returnView = "redirect:/epc/edi/consult/applyNewFinal.do";
					returnView = "edi/consult/applyNewTenantFinal";
					
				//	List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
				//	model.addAttribute("answerList", answerList);

				} else {
					returnView = "redirect:/epc/edi/consult/insertStep4past.do";
				}
			
		}

		return returnView;

	}

	@RequestMapping(value = "/epc/edi/consult/checkVendorBusinessNo3", method = RequestMethod.POST)
	public String getVendorBusinessNo3(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		String returnView = "";
		String bmanNo = request.getParameter("businessNo");

		Vendor vendor = consultService.selectVendorInfo(bmanNo);
		VendorSession vendorSession = new VendorSession(vendor);
		request.getSession().setAttribute("vendorSession", vendorSession);
		
		String pwd = request.getParameter("password");
		searchParam.setPassword(pwd);

		model.addAttribute("gubunBlock", processCheck(request, model));

		String gubunBlock = "N";

		HashBox hb2 = consultService.selectResultInfo(bmanNo);

		if (hb2 == null) {
			// if(hb2.get("CHG_STATUS_CD")==""){
			gubunBlock = "N";
		} else {
			if ("0".equals(hb2.get("CHG_STATUS_CD"))
					|| "N".equals(hb2.get("PAPE_JGM_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("CNSL_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("ENTSHP_RSLT_DIVN_CD"))) {
				gubunBlock = "N"; // 신청가능
			} else if ("1".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "Y"; // 심사중
			} else if ("2".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "C"; // 신청완료
			}
		}

		// 비밀번호가 틀릴때
		if (!searchParam.getPassword().equals(vendor.getPasswd())) {
			model.addAttribute("resultMessage",
					getText("msg.supply.consult.mismatch.password"));
			gubunBlock = "";
			return "edi/consult/loginResult";
		}
		// 비밀번호가 맞을때
		else {
			// 구분 블록 = "N" -> 업체가 작성완료 전이거나,MD가 평가 반려 했을때
			if (gubunBlock == "N") {
				model.addAttribute("resultMessage", "신청/재신청 하시기 바랍니다.");
				returnView = "edi/consult/applyNew";
			}
			// 구분 블록 = "Y" 혹은 "C"일때 -> MD 심사 진행중 일때,평가완료 되었을때
			else {
				model.addAttribute("resultMessage", "심사중이거나 심사완료된 상태입니다.");
				if ("1".equals(vendor.getKindCd())) {

					List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
					model.addAttribute("answerList", answerList);
					returnView = "redirect:/epc/edi/consult/applyNewFinal.do";
				} else if ("2".equals(vendor.getKindCd())) {

					List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
					model.addAttribute("answerList", answerList);
					returnView = "redirect:/edi/consult/applyNewSupportFinal.do";
				} else if ("3".equals(vendor.getKindCd())) {

					List<Vendor> answerList = consultService.selectVendorAnswer(bmanNo);
					model.addAttribute("answerList", answerList);
					returnView = "redirect:/epc/edi/consult/applyNewFinal.do";
				} else {
					returnView = "redirect:/epc/edi/consult/insertStep4past.do";
				}
			}
		}
		return returnView;

	}
	
	@RequestMapping(value = "/epc/edi/consult/insertStep4past", method = RequestMethod.GET)
	public String showConsultStep4PastPage(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectVendorInfo2(businessNo);
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
				"yyyyMM");

		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);

		List<AutionItem> autionItemListSum = consultService
				.selectAuItemSum(businessNo);
		// List<AutionItem> autionItemList =
		// consultService.selectAuItem(businessNo);
		model.addAttribute("autionItemListSum", autionItemListSum);

		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);

		model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/insertStep4past";
	}

	// 업체 비밀번호와 입력한 비밀번호가 맞는지 확인하는 과정, 사용자가 입력한 패스워드 암호화 처리 및 암호화된 패스워드와 대조시킴
	@RequestMapping(value = "/epc/edi/consult/checkVendorBusinessNoResult", method = RequestMethod.POST)
	public String getVendorBusinessNoResult(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		String gubunBlock;
		// 암호화 수정 부분 2014-02-04 Password를 입력받아서 암호화 처리
		String pwd = request.getParameter("password");
//		pwd = xecuredbConn.hash(pwd);
		searchParam.setPassword(pwd);

		Vendor vendor = consultService.selectVendorInfoResult(searchParam.getBusinessNo());
		// Vendor vendor =
		// consultService.selectVendorInfo(searchParam.getBusinessNo());
		// 사업자 번호가 없을 경우 새로운 사업자 번호의 패스워드(암호화)추가
		if (StringUtils.isBlank(vendor.getBmanNo())) {
			model.addAttribute("resultMessage",getText("msg.consult.applygo"));
					//getText("msg.supply.first.user"));
					
			gubunBlock = "";
			return "edi/consult/loginResult";

		} else if (!searchParam.getPassword().equals(vendor.getPasswd())) {
			model.addAttribute("resultMessage",
					getText("msg.supply.consult.mismatch.password"));
			gubunBlock = "";
			return "edi/consult/loginResult";
		}
		VendorSession vendorSession = new VendorSession(vendor);
		request.getSession().setAttribute("vendorSession", vendorSession);
		model.addAttribute("gubunBlock", processCheck(request, model));
		// return "edi/consult/PEDMCST056";

		return "redirect:/epc/edi/consult/PEDMCST057.do";

	}
	
	

	@RequestMapping(value = "/epc/edi/consult/findPassword", method = RequestMethod.GET)
	public String findVendorPasswordGet(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		return "edi/consult/findPassword";
	}

	@RequestMapping(value = "/epc/edi/consult/findPassword", method = RequestMethod.POST)
	public String findVendorPassword(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		Integer vendorPasswordCount = consultService
				.selectVendorPassword(searchParam);
		if (vendorPasswordCount == 0) {
			model.addAttribute("resultMessage",
					getText("msg.supply.consult.findpasswd.result"));
			return "edi/consult/findPassword";
		} else {
			return "edi/consult/initPassword";
		}
	}

	@RequestMapping(value = "/epc/edi/consult/initPassword", method = RequestMethod.GET)
	public String initVendorPasswordGet(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		return "edi/consult/initPassword";
	}

	@RequestMapping(value = "/epc/edi/consult/initPassword", method = RequestMethod.POST)
	public String updateVendorPassword(SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		// 로그인시 입력받은 값의 암호화 처리 , 향후 암호화 DB 패스워드와 비교를 위함
		String pwd = request.getParameter("password");
//		pwd = xecuredbConn.hash(pwd);
		searchParam.setPassword(pwd);

		Integer vendorPasswordCount = consultService
				.updateVendorPassword(searchParam);
		if (vendorPasswordCount == 1) {
			model.addAttribute("resultMessage",
					getText("msg.supply.consult.resetpassword"));
			return "edi/consult/initPasswordResult";
		} else {
			return "edi/consult/initPassword";
		}

	}

	@RequestMapping(value = "/epc/edi/consult/PEDMCST056")
	public String showConsultMain(HttpServletRequest request, Model model)
			throws Exception {

		model.addAttribute("gubunBlock", processCheck(request, model));
		return "edi/consult/PEDMCST056";
	}

	@RequestMapping(value = "/epc/edi/consult/PEDMCST057", method = RequestMethod.GET)
	public String showResultMain(HttpServletRequest request, Model model)
			throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		model.addAttribute("conList",
				pedmsct0002Service.consultAdminSelectDetail(businessNo));
		model.addAttribute("conList_past",
				pedmsct0002Service.consultAdminSelectDetailPast(businessNo));

		model.addAttribute("gubunBlock", processCheck(request, model));

		//return "edi/consult/PEDMCST057";//20150812 이동빈 과거
		return "edi/consult/PEDMCST0571";
	}

	@RequestMapping("/epc/edi/consult/PEDMCST058")
	public String showFaqMain(HttpServletRequest request, Model model)
			throws Exception {

		model.addAttribute("gubunBlock", processCheck(request, model));

		return "edi/consult/PEDMCST058";
	}

	@RequestMapping(value = "/epc/edi/consult/selectConsultCategory")
	public String showSelectConsultcategroyPage(Model model) throws Exception {
		// List<HashBox> consultcategroyList =
		// commService.selectConsultcategroyListNew();
		List<HashBox> consultcategroyList = commService
				.selectConsultcategroyListNew2();
		model.addAttribute("consultcategroyList", consultcategroyList);

		// return "edi/consult/apply"; //20150714 이동빈
		return "edi/consult/apply";

	}

	@RequestMapping(value = "/epc/edi/consult/selectConsultKind1")
	// 이동빈 입점혁신
	public String showSelectConsultkindPage1(Model model) throws Exception {
		return "edi/consult/applyKind1";
	}

	@RequestMapping(value = "/epc/edi/consult/selectDepartment")
	public String showSelectDepartmentPage(Model model) throws Exception {
		List<HashBox> departmentList = commService.selectDepartmentListNew();

		model.addAttribute("departmentList", departmentList);

		return "edi/consult/apply";
	}

	@RequestMapping(value = "/epc/edi/consult/selectDepartmentDetailPopup")
	public String showSelectDepartmentDetailPage(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		List<HashBox> departmentDetailList = commService
				.selectDepartmentDetailList(map);

		model.addAttribute("codeList", departmentDetailList);

		return "edi/consult/applyDetail";
	}

	@RequestMapping(value = "/epc/edi/consult/selectL1cdDetailPopup")
	public String showSelectL1cdDetailPage(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		List<HashBox> L1cdDetailList = commService.selectL1cdDetailList(map);

		model.addAttribute("codeList", L1cdDetailList);

		return "edi/consult/applyDetail";
	}

	@RequestMapping(value = "/epc/edi/consult/selectAllL1cdDetailPopup")
	public String showSelectAllL1cdDetailPage(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		List<HashBox> allL1cdDetailList = commService
				.selectAllL1cdDetailList(map);

		model.addAttribute("codeList", allL1cdDetailList);

		return "edi/consult/applyDetailpopUp";
	}

	@RequestMapping(value = "/epc/edi/consult/selectImgPopup")
	public String showPopUp0001(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		// List<HashBox> allL1cdDetailList =
		// commService.selectAllL1cdDetailList(map);

		// model.addAttribute("codeList", allL1cdDetailList);

		return "edi/consult/ImgPopup0001";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0001ImgPopup")
	public String showPopUpfront0001(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		// List<HashBox> allL1cdDetailList =
		// commService.selectAllL1cdDetailList(map);
		// model.addAttribute("codeList", allL1cdDetailList);
		return "edi/consult/ImgPopupFront0001";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0002ImgPopup")
	public String showPopUpfront0002(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0002";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0003ImgPopup")
	public String showPopUpfront0003(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0003";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0006ImgPopup")
	public String showPopUpfront0006(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0006";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0007ImgPopup")
	public String showPopUpfront0007(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0007";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0008ImgPopup")
	public String showPopUpfront0008(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0008";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0009ImgPopup")
	public String showPopUpfront0009(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0009";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0010ImgPopup")
	public String showPopUpfront0010(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0010";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0011ImgPopup")
	public String showPopUpfront0011(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0011";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0012ImgPopup.do")
	public String showPopUpfront0012(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0012";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0013ImgPopup.do")
	public String showPopUpfront0013(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0013";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0014ImgPopup.do")
	public String showPopUpfront0014(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0014";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0015ImgPopup.do")
	public String showPopUpfront0015(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0015";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0016ImgPopup.do")
	public String showPopUpfront0016(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0016";
	}

	@RequestMapping(value = "/epc/edi/consult/selectFront0017ImgPopup.do")
	public String showPopUpfront0017(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0017";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0018ImgPopup.do")
	public String showPopUpfront0018(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0018";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0019ImgPopup.do")
	public String showPopUpfront0019(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0019";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0020ImgPopup.do")
	public String showPopUpfront0020(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0020";
	}
	
	@RequestMapping(value = "/epc/edi/consult/selectFront0021ImgPopup.do")
	public String showPopUpfront0021(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		return "edi/consult/ImgPopupFront0021";
	}
	
	// 기본정보 , 최초 로그인 성공 이후 처리 로직
	@RequestMapping(value = "/epc/edi/consult/insertStep1", method = RequestMethod.GET)
	public String showConsultStep1Page(HttpServletRequest request,
			HttpServletResponse response, Model model, ModelMap modelMap,
			@RequestParam Map<String, Object> hmap) throws Exception {
		// public String showConsultStep1Page(HttpServletRequest request,
		// HttpServletResponse response, Model model, ModelMap
		// modelMap,@RequestParam Map<String,Object> hmap ) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor vendor = consultService.selectVendorInfo(businessNo);

		String teamCd = request.getParameter("teamCd");
		int teamCdInt = Integer.parseInt(teamCd);

		// modelMap.addAttribute("orgcdData",commService.selectOrg(hmap));
		modelMap.addAttribute("TeamcdData", commService.selectL1Team(hmap));
		if (teamCdInt == 17 || teamCdInt == 47) {
			modelMap.addAttribute("L1cdData", commService.selectL1CdEtc(hmap));
		} else {
			modelMap.addAttribute("L1cdData", commService.selectL1Cd(hmap));
		}

		Vendor dbRegisteredvendor = consultService
				.selectVendorInfo(vendorSession.getVendor().getBmanNo());
		Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor
				.getBmanNo()) ? 0 : 1;
		String pwd = vendorSession.getVendor().getPasswd();
		/* 대분류 -> 팀코드 변경 - ykson 2014.10.29 */
		// String l1Cd = request.getParameter("l1Cd");

		if (isNewVendor(vendorSession.getVendor(), vendorCount)) {
			// consultService.insertNewVendorInfoNew(pwd,businessNo,l1Cd);
			consultService.insertNewVendorInfoNew(pwd, businessNo, teamCd);
		} else {
			// consultService.updateVendorInfoNew(pwd,businessNo,l1Cd);
			consultService.updateVendorInfoNew(pwd, businessNo, teamCd);
		}

		model.addAttribute("vendor", vendor);

		model.addAttribute("gubunBlock", processCheck(request, model));
		return "edi/consult/insertStep1";
	}

	@RequestMapping(value = "/epc/edi/consult/insertStep1", method = RequestMethod.POST)
	public String submitConsultStep1Page(Vendor submittedVendorInfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		// submittedVendorInfo.mappingField();

		Vendor dbRegisteredvendor = consultService
				.selectVendorInfo(vendorSession.getVendor().getBmanNo());
		Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor
				.getBmanNo()) ? 0 : 1;

		logger.debug("필드값 보기 - "+submittedVendorInfo.toString());

		if (isNewVendor(vendorSession.getVendor(), vendorCount)) {
			consultService.updateVendorInfo(submittedVendorInfo);
		} else {
			consultService.insertNewVendorInfo(submittedVendorInfo);
		}

		model.addAttribute("gubunBlock", processCheck(request, model));

		return "redirect:/epc/edi/consult/insertStep2.do";
	}
	
	@RequestMapping(value = "/epc/edi/consult/insertApplyKind", method = RequestMethod.POST)
	public String submitConsultApplyKind(Vendor submittedVendorInfo,SearchParam searchParam,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");

		String bmanNo = request.getParameter("bmanNo");
		String kind = request.getParameter("kind");
		String colVal = request.getParameter("answer");

		HashMap<String, Object> hmap = new HashMap();

		hmap.put("bmanNo", bmanNo);
		if (kind.equals("1")) {
			hmap.put("seq", "10");
		} else if (kind.equals("2")) {
			hmap.put("seq", "20");
		} else if (kind.equals("3")) {
			hmap.put("seq", "30");
		}
		hmap.put("colVal", colVal);
		hmap.put("dispYn", "");
		// 업체 작성중 상태
		hmap.put("chgStatusCd", "0");
		hmap.put("kindCd", kind);
		//searchParam.setGroupCode(tmpProduct.getOfficialOrder().getTeamCode());
		
		Vendor dbRegisteredvendor = consultService.selectVendorInfo(vendorSession.getVendor().getBmanNo());
		Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo()) ? 0 : 1;
		consultService.deleteNewVendorAnswer(hmap);
		// 처음 사용하는 협력사의 경우 설문정보 insert / 업체정보 insert
		if (vendorCount == 0) {
			consultService.insertNewVendorAnswer(hmap);
		} else {

			// 기 사용업체중 설문 내용은 insert / 기존 업체정보는 업데이트
			if (dbRegisteredvendor.getKindCd() == null) {
				consultService.insertNewVendorAnswer2(hmap);
				consultService.updateNewVendorKindCd(hmap);
			}

			else {
				// 기 사용업체중 설문내용이 있던 업체는 delete후 insert // 기존 업체정보는 업데이트
			//	consultService.deleteNewVendorAnswer(hmap);
				consultService.insertNewVendorAnswer2(hmap);
				consultService.updateNewVendorKindCd(hmap);
			}

		}

		model.addAttribute("gubunBlock", processCheck(request, model));

		Vendor vendor = consultService.selectVendorInfo(bmanNo);
		model.addAttribute("vendor", vendor);		
		
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
		model.addAttribute("attachFileFolder", attachFileFolder);

		List<VendorProduct> vendorProductList = consultService.selectVendorProduct(bmanNo);
		model.addAttribute("vendorProductList", vendorProductList);
		// 팀 선택용
		// List<HashBox> consultcategroyList =
		// commService.selectConsultcategroyListNew2();
		// model.addAttribute("consultcategroyList", consultcategroyList);

		List<HashBox> consultTeamList = commService.selectConsultTeamList();
		model.addAttribute("consultTeamList", consultTeamList);
		
		searchParam.setGroupCode(vendor.getTeamCd());		
		model.addAttribute("l1GroupList", pedmsct0002Service.selectL1ListApply(searchParam));

		String returnView = "edi/consult/applyNewProducts";
		
		if ("1".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewProducts";
		} else if ("2".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewSupport";
		} else if ("3".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewTenant";
		} else {
			returnView = "edi/consult/applyNewProducts";
		}
		return returnView;

	}

	/*
	 * 
	 * @RequestMapping(value="/epc/edi/consult/insertApplyKind2", method =
	 * RequestMethod.GET) public String submitConsultApplyKind2(Vendor
	 * submittedVendorInfo, HttpServletRequest request, HttpServletResponse
	 * response, Model model) throws Exception { VendorSession vendorSession =
	 * (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");
	 * //submittedVendorInfo.mappingField();
	 * 
	 * Vendor dbRegisteredvendor =
	 * consultService.selectVendorInfo(vendorSession.getVendor().getBmanNo());
	 * Integer vendorCount = StringUtils.isBlank(dbRegisteredvendor.getBmanNo())
	 * ? 0 : 1; String businessNo = vendorSession.getVendor().getBmanNo();
	 * 
	 * logger.debug("필드값 보기 - "+submittedVendorInfo.toString());
	 * 
	 * if( isNewVendor(vendorSession.getVendor(), vendorCount)) {
	 * 
	 * //consultService.insertNewVendorInfo(submittedVendorInfo); //
	 * consultService.insertNewVendorInfoApply(submittedVendorInfo); } else {
	 * //consultService.updateVendorInfo(submittedVendorInfo); //
	 * consultService.updateVendorInfoApply(submittedVendorInfo); }
	 * 
	 * model.addAttribute("gubunBlock",processCheck(request, model));
	 * 
	 * 
	 * 
	 * 
	 * //VendorSession vendorSession = (VendorSession)
	 * WebUtils.getSessionAttribute(request, "vendorSession"); //String
	 * businessNo = "1111111111"; Vendor vendor =
	 * consultService.selectVendorInfo(businessNo); model.addAttribute("vendor",
	 * vendor);
	 * 
	 * String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
	 * "yyyyMM"); model.addAttribute("attachFileFolder", attachFileFolder);
	 * 
	 * List<VendorProduct> vendorProductList =
	 * consultService.selectVendorProduct(businessNo);
	 * model.addAttribute("vendorProductList", vendorProductList); //팀 선택용
	 * //List<HashBox> consultcategroyList =
	 * commService.selectConsultcategroyListNew2();
	 * //model.addAttribute("consultcategroyList", consultcategroyList);
	 * 
	 * List<HashBox> consultTeamList = commService.selectConsultTeamList();
	 * model.addAttribute("consultTeamList", consultTeamList);
	 * 
	 * //return "edi/consult/applyNewProduct"; return
	 * "edi/consult/applyNewProducts"; }
	 */

	public boolean isNewVendor(Vendor vendor, Integer vendorCount) {
		if ("0".equals(vendor.getChgStatusCd()) && vendorCount == 0) {
			return true;
		} else {
			return false;
		}

	}

	// 매출정보
	@RequestMapping(value = "/epc/edi/consult/insertStep2", method = RequestMethod.GET)
	public String showConsultStep2Page(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectVendorInfo(businessNo);
		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();

		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);

		model.addAttribute("gubunBlock", processCheck(request, model));

		return "edi/consult/insertStep2";
	}

	// 매출정보
	@RequestMapping(value = "/epc/edi/consult/insertStep2", method = RequestMethod.POST)
	public String submitConsultStep2Page(Vendor vendor,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		String st = vendor.getFoundationYear();
//		logger.debug("st!!! " + vendor.toString());
//		logger.debug("businessNo!!  " + businessNo);

		/*
		 * String[] otherStoreCode = request.getParameterValues("other_str_cd");
		 * String[] ent_str_cnt = request.getParameterValues("ent_str_cnt");
		 * String[] mg1 = request.getParameterValues("mg1");
		 * 
		 * String[] mg2 = request.getParameterValues("mg2"); String[] pcost =
		 * request.getParameterValues("pcost"); String[] subAmount =
		 * request.getParameterValues("subAmount"); String[] trd_typ_fg =
		 * request.getParameterValues("trd_typ_fg");
		 * 
		 * List<Sale> saleDataList = new ArrayList<Sale>(); int arrLength =
		 * otherStoreCode.length; for(int i=0; i < arrLength; i++) {
		 * if(!otherStoreCode[i].equals("")){
		 * 
		 * 
		 * Sale tmpSale = new Sale(); tmpSale.setBusinessNo(businessNo);
		 * tmpSale.setOtherStoreCode(otherStoreCode[i]);
		 * if("".equals(ent_str_cnt[i]) || ent_str_cnt[i] == null){
		 * tmpSale.setEnteredStoreCount(0); }else{
		 * tmpSale.setEnteredStoreCount(new Integer(ent_str_cnt[i])); }
		 * 
		 * if("".equals(mg1[i]) || mg1[i] == null){ tmpSale.setMarginRate1(0);
		 * }else{ tmpSale.setMarginRate1(new Integer(mg1[i])); }
		 * 
		 * if("".equals(mg2[i]) || mg2[i] == null){ tmpSale.setMarginRate2(0);
		 * }else{ tmpSale.setMarginRate2(new Integer(mg2[i])); }
		 * 
		 * if("".equals(pcost[i]) || pcost[i] == null){ tmpSale.setPcost(0);
		 * }else{ tmpSale.setPcost(new Integer(pcost[i])); }
		 * 
		 * if("".equals(subAmount[i]) || subAmount[i] == null){
		 * tmpSale.setSubAmount(0); }else{ tmpSale.setSubAmount(new
		 * Integer(subAmount[i])); }
		 * 
		 * tmpSale.setTradeTypeContent(trd_typ_fg[i]);
		 * 
		 * saleDataList.add(tmpSale);
		 * 
		 * 
		 * } }
		 */

		// consultService.updateVendorSaleInfo(vendor, saleDataList);
		consultService.updateVendorSaleInfo(vendor);

		model.addAttribute("gubunBlock", processCheck(request, model));

		return "redirect:/epc/edi/consult/insertStep3.do";
	}

	@RequestMapping(value = "/edi/consult/step3ImagePopup", method = RequestMethod.GET)
	public String showConsultStep3ImagePopup(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/imagePopup";
	}
	@RequestMapping(value = "/edi/consult/step3ImagePopupNew", method = RequestMethod.GET)
	public String showConsultStep3ImagePopupNew(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/imagePopupNew";
	}
	

	@RequestMapping(value = "/edi/consult/step3DocumnetPopup", method = RequestMethod.GET)
	public ModelAndView showConsultStep3DocumentPopup(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		String businessNo = request.getParameter("vendorBusinessNo");
		Vendor vendor = consultService.selectVendorInfo(businessNo);
		
		String upFolleder = vendor.getUploadFolder();
		
		
		String systemFileLocation = ConfigUtils.getString("edi.consult.image.path")
				+ "/"
			//	+ DateFormatUtils.format(vendor.getRegDate(), "yyyyMM")
				+ upFolleder				
				+ "/"
				+ vendor.getBmanNo() + "_doc";
		// fileMngUtil.downFileUTF8(response, systemFileLocation,
		// vendor.getAttachFileCode());
		model.put("file", new File(systemFileLocation));
		model.put("fileName", vendor.getAttachFileCode());

		return new ModelAndView("downloadView", model);

	}
	
	@RequestMapping(value = "/edi/consult/step3DocumnetPopupNew", method = RequestMethod.GET)
	public ModelAndView showConsultStep3DocumentPopupNew(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		String businessNo = request.getParameter("vendorBusinessNo");
		Vendor vendor = consultService.selectVendorInfo(businessNo);
		String systemFileLocation = ConfigUtils
				.getString("edi.consult.image.path")
				+ "/"
			//	+ DateFormatUtils.format(vendor.getRegDate(), "yyyyMM")
			//	+ "/"
				+ vendor.getBmanNo() + "_doc";
		// fileMngUtil.downFileUTF8(response, systemFileLocation,
		// vendor.getAttachFileCode());
		model.put("file", new File(systemFileLocation));
		model.put("fileName", vendor.getAttachFileCode());

		return new ModelAndView("downloadView", model);

	}
	

	@RequestMapping(value = "/epc/edi/consult/insertStep3", method = RequestMethod.GET)
	public String showConsultStep3Page(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor vendor = consultService.selectVendorInfo(businessNo);

		// if(vendor.getRegDate() != null) {
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
				"yyyyMM");
		model.addAttribute("attachFileFolder", attachFileFolder);
		// }
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);
		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("vendor", vendor);

		// 이동빈 추가
		String L1_CD = vendor.getL1Cd();

		List<AutionItem> autionItemList = consultService.selectAuItem(
				businessNo, L1_CD);
		// List<AutionItem> autionItemList = consultService.selectAuItem(L1_CD);
		// List<AutionItem> autionItemList =
		// consultService.selectAuItem(businessNo);

		model.addAttribute("autionItemList", autionItemList);

		model.addAttribute("gubunBlock", processCheck(request, model));
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/insertStep3";
	}

	// 상품정보
	@RequestMapping(value = "/epc/edi/consult/insertStep3", method = RequestMethod.POST)
	public String submitConsultStep3Page(VendorProduct vendorProduct,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor tmpVendor = consultService.selectVendorInfo(businessNo);

		String currentTimeString = DateFormatUtils.format(
				Calendar.getInstance(), "yyyyMMddHHmmss");
		String currentYearMonth = DateFormatUtils.format(
				Calendar.getInstance(), "yyyyMM");
		// String imageUploadPath =
		// ConfigUtils.getString("edi.consult.image.path")+"/"+currentYearMonth;
		// //getServletContext().getRealPath("/lim/static_root")+"/consult";getServletContext().getRealPath("/lim/static_root")+"/consult";
		// if(tmpVendor.getRegDate() != null) {
		String imageUploadPath = ConfigUtils
				.getString("edi.consult.image.path")
				+ "/"
				+ DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");

		// }
		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory())
			folderDir.mkdirs();

		String attachFile1 = "";
		String attachFile2 = "";
		String attachFile3 = "";

		if (!vendorProduct.getAttachFile1().isEmpty()) {
			String detailImageExt = FilenameUtils.getExtension(vendorProduct
					.getAttachFile1().getOriginalFilename());
			String attachImageFile1Name = businessNo + "_1." + detailImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath
					+ "/" + attachImageFile1Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile1().getInputStream(),
					listImageOs);
			vendorProduct.setAttachFileName1(attachImageFile1Name);

		} else {
			vendorProduct.setAttachFileName1(vendorProduct.getUploadFile1());
		}

		if (!vendorProduct.getAttachFile2().isEmpty()) {
			String detailImageExt = FilenameUtils.getExtension(vendorProduct
					.getAttachFile2().getOriginalFilename());
			String attachImageFile2Name = businessNo + "_2." + detailImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath
					+ "/" + attachImageFile2Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile2().getInputStream(),
					listImageOs);
			vendorProduct.setAttachFileName2(attachImageFile2Name);
		} else {
			vendorProduct.setAttachFileName2(vendorProduct.getUploadFile2());
		}

		if (!vendorProduct.getAttachFile3().isEmpty()) {
			String zoomImageExt = FilenameUtils.getExtension(vendorProduct
					.getAttachFile3().getOriginalFilename());
			String attachImageFile3Name = businessNo + "_3." + zoomImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath
					+ "/" + attachImageFile3Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile3().getInputStream(),
					listImageOs);
			vendorProduct.setAttachFileName3(attachImageFile3Name);
		} else {
			vendorProduct.setAttachFileName3(vendorProduct.getUploadFile3());
		}

		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
			String zoomImageExt = FilenameUtils.getExtension(vendorProduct
					.getVendorAttachFile().getOriginalFilename());
			String attachImageFileName = businessNo + "_doc";
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath
					+ "/" + attachImageFileName);
			FileCopyUtils.copy(vendorProduct.getVendorAttachFile()
					.getInputStream(), listImageOs);
			vendorProduct.setVendorAttachFileName(vendorProduct
					.getVendorAttachFile().getOriginalFilename());
		} else {
			vendorProduct
					.setVendorAttachFileName(vendorProduct.getVendorFile());
		}

		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());
		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String[] productName = multipartRequest
				.getParameterValues("productName");
		String[] productCost = multipartRequest
				.getParameterValues("productCost");
		String[] productSalePrice = multipartRequest
				.getParameterValues("productSalePrice");

		String[] monthlyAverage = multipartRequest
				.getParameterValues("monthlyAverage");
		String[] productDescription = multipartRequest
				.getParameterValues("productDescription");
		String[] subAmount = multipartRequest.getParameterValues("subAmount");
		String[] seq = request.getParameterValues("seq");

		String[] prodArray = multipartRequest.getParameterValues("prodArray");


		 
		consultService.deleteAuctionProdItem(businessNo);

		String[] prodArraySeq = prodArray[0].split("/");

		if (!"".equals(StringUtils.defaultIfEmpty(prodArray[0], ""))) {

			for (int i = 0; i < prodArraySeq.length; i++) {
				consultService.insertAuctionProdItem(prodArraySeq[i],businessNo);
			}
		}

		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
		int arrLength = productName.length;
		for (int i = 0; i < arrLength; i++) {
			VendorProduct tmpVendorProduct = new VendorProduct();
			tmpVendorProduct.setBusinessNo(businessNo);
			tmpVendorProduct.setProductName(productName[i]);
			tmpVendorProduct.setProductCost(productCost[i]);
			tmpVendorProduct.setProductSalePrice(productSalePrice[i]);
			tmpVendorProduct.setMonthlyAverage(monthlyAverage[i]);
			tmpVendorProduct.setProductDescription(productDescription[i]);
			tmpVendorProduct.setSeq(new Integer(seq[i]));
			tmpVendorProduct.setSellCode("notUsed");
			if (i == 0) {
				tmpVendorProduct.setAttachFileCode(vendorProduct
						.getAttachFileName1());
			} else if (i == 1) {
				tmpVendorProduct.setAttachFileCode(vendorProduct
						.getAttachFileName2());
			} else {
				tmpVendorProduct.setAttachFileCode(vendorProduct
						.getAttachFileName3());
			}

			vendorProductList.add(tmpVendorProduct);
		}

		consultService.updateVendorProductInfo(tmpVendor, vendorProductList);

		model.addAttribute("gubunBlock", processCheck(request, model));

		return "redirect:/epc/edi/consult/insertStep4.do";
	}

	// 상품정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
	@RequestMapping(value = "/epc/edi/consult/insertApplyConfirm", method = RequestMethod.POST)
	public String submitConsultApplyConfirm(Vendor submittedVendorInfo,
			VendorProduct vendorProduct, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils.getSessionAttribute(request, "vendorSession");

		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor tmpVendor = consultService.selectVendorInfo(businessNo);

		String currentTimeString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");

		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");

		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요

		consultService.updateVendorInfo(submittedVendorInfo);

		// /////////////////////////////상품시작
		// /////////////////////////////////////////////////
		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");

		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory())
			folderDir.mkdirs();

		String attachFile1 = "";
		String attachFile2 = "";
		String attachFile3 = "";

		if (!vendorProduct.getAttachFile1().isEmpty()) {
			String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile1().getOriginalFilename());
			String attachImageFile1Name = businessNo + "_1." + detailImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile1Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile1().getInputStream(),	listImageOs);				
		//	attachImageFile1Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile1Name;
			vendorProduct.setAttachFileName1(attachImageFile1Name);
			vendorProduct.setUploadfolder1(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
			
		} else {
			vendorProduct.setAttachFileName1(vendorProduct.getUploadFile1());
		}

		if (!vendorProduct.getAttachFile2().isEmpty()) {
			String detailImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile2().getOriginalFilename());
			String attachImageFile2Name = businessNo + "_2." + detailImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile2Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile2().getInputStream(),	listImageOs);
		//	attachImageFile2Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile2Name;
			vendorProduct.setAttachFileName2(attachImageFile2Name);
			vendorProduct.setUploadfolder2(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));			
		} else {
			vendorProduct.setAttachFileName2(vendorProduct.getUploadFile2());
		}

		if (!vendorProduct.getAttachFile3().isEmpty()) {
			String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getAttachFile3().getOriginalFilename());
			String attachImageFile3Name = businessNo + "_3." + zoomImageExt;
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFile3Name);
			FileCopyUtils.copy(vendorProduct.getAttachFile3().getInputStream(),	listImageOs);
		//	attachImageFile3Name=DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM")+"/"+attachImageFile3Name;
			vendorProduct.setUploadfolder3(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));			
			vendorProduct.setAttachFileName3(attachImageFile3Name);
		} else {
			vendorProduct.setAttachFileName3(vendorProduct.getUploadFile3());
		}

		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
			String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
			String attachImageFileName = businessNo + "_doc";
			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
			FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
			vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
			tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
			
		} else {
			vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
		}
		                                             
		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());		
		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());		 
		 
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String[] productName = multipartRequest.getParameterValues("productName");
		// String[] productCost =
		// multipartRequest.getParameterValues("productCost");
		// String[] productSalePrice =
		// multipartRequest.getParameterValues("productSalePrice");
		//
		// String[] monthlyAverage =
		// multipartRequest.getParameterValues("monthlyAverage");
		// String[] productDescription =
		// multipartRequest.getParameterValues("productDescription");
		// String[] subAmount =
		// multipartRequest.getParameterValues("subAmount");
		String[] seq = request.getParameterValues("seq");
	//	String[] atchFileFolder = request.getParameterValues("atchFileFolder");

		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
		int arrLength = productName.length;
		for (int i = 0; i < arrLength; i++) {
			VendorProduct tmpVendorProduct = new VendorProduct();
			tmpVendorProduct.setBusinessNo(businessNo);
			tmpVendorProduct.setProductName(productName[i]);
			tmpVendorProduct.setProductCost("notUsed");
			tmpVendorProduct.setProductSalePrice("notUsed");
			tmpVendorProduct.setMonthlyAverage("notUsed");
			tmpVendorProduct.setProductDescription("notUsed");
			tmpVendorProduct.setSeq(new Integer(seq[i]));
			tmpVendorProduct.setSellCode("notUsed");
		//	tmpVendorProduct.setAtchFileFolder(atchFileFolder[i]);
			
			

			if (i == 0) {
				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName1());
				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder1());
			} else if (i == 1) {
				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName2());
				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder2());
			} else {
				tmpVendorProduct.setAttachFileCode(vendorProduct.getAttachFileName3());
				tmpVendorProduct.setAtchFileFolder(vendorProduct.getUploadfolder3());
				
			}

			vendorProductList.add(tmpVendorProduct);
		}
		// consultService.updateVendorProductInfo(tmpVendor, vendorProductList);
		consultService.updateVendorProductInfoApply(tmpVendor,vendorProductList);
		// ///////////////////////상품끝///////////////////////////////////////////////

		model.addAttribute("gubunBlock", processCheck(request, model));
		return "redirect:/epc/edi/consult/applyNewFinal.do";

	}

	// 지원정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
	@RequestMapping(value = "/epc/edi/consult/insertApplyConfirmSupport", method = RequestMethod.POST)
	public String submitConsultApplyConfirmSupport(Vendor submittedVendorInfo,
			VendorProduct vendorProduct, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");

		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor tmpVendor = consultService.selectVendorInfo(businessNo);

		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요
		consultService.updateVendorInfo(submittedVendorInfo);

		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());

		
		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
		
		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory())
			folderDir.mkdirs();

		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
					String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
					String attachImageFileName = businessNo + "_doc";
					FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
					FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
					vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
					tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
					
				} else {
					vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
				}
		
		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());	

		consultService.updateVendorProductInfoApply(tmpVendor,vendorProductList);

		model.addAttribute("gubunBlock", processCheck(request, model));
		return "redirect:/epc/edi/consult/applyNewSupportFinal.do";

	}

	// 테넌트 정보 VendorProduct vendorProduct Vendor submittedVendorInfo,
	@RequestMapping(value = "/epc/edi/consult/insertApplyConfirmTenant", method = RequestMethod.POST)
	public String submitConsultApplyConfirmTenant(Vendor submittedVendorInfo,
			VendorProduct vendorProduct, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");

		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor tmpVendor = consultService.selectVendorInfo(businessNo);
		
		submittedVendorInfo.setBmanNo(businessNo);// 지워주세요
		consultService.updateVendorInfo(submittedVendorInfo);
		
		
		List<VendorProduct> vendorProductList = new ArrayList<VendorProduct>();
		tmpVendor.setPecuSubjectContent(vendorProduct.getContent());
		

		String currentYearMonth = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
		String imageUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM");
		
		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory())
			folderDir.mkdirs();

		if (!vendorProduct.getVendorAttachFile().isEmpty()) {
					String zoomImageExt = FilenameUtils.getExtension(vendorProduct.getVendorAttachFile().getOriginalFilename());
					String attachImageFileName = businessNo + "_doc";
					FileOutputStream listImageOs = new FileOutputStream(imageUploadPath	+ "/" + attachImageFileName);
					FileCopyUtils.copy(vendorProduct.getVendorAttachFile().getInputStream(), listImageOs);
					vendorProduct.setVendorAttachFileName(vendorProduct.getVendorAttachFile().getOriginalFilename());
					tmpVendor.setUploadFolder(DateFormatUtils.format(tmpVendor.getRegDate(), "yyyyMM"));
					
				} else {
					vendorProduct.setVendorAttachFileName(vendorProduct.getVendorFile());
				}
		
		tmpVendor.setAttachFileCode(vendorProduct.getVendorAttachFileName());	
		consultService.updateVendorProductInfoApply(tmpVendor,vendorProductList);

		model.addAttribute("gubunBlock", processCheck(request, model));
		return "redirect:/epc/edi/consult/applyNewTenantFinal.do";

	}

	@RequestMapping(value = "/epc/edi/consult/applyNewFinal", method = RequestMethod.GET)
	public String showConsultapplyNewFinal(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectNewVendorInfoApply(businessNo);
		// Vendor vendor = consultService.selectVendorInfo2(businessNo);
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),"yyyyMM");
		List<VendorProduct> vendorProductList = consultService.selectVendorProduct(businessNo);
		List<Vendor> answerList = consultService.selectVendorAnswer(businessNo);
		List<HashBox> consultTeamList = commService.selectConsultTeamList();
		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("vendor", vendor);
		model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		model.addAttribute("answerList", answerList);
		model.addAttribute("consultTeamList", consultTeamList);

		String returnView = "edi/consult/applyNewProductFinal";
		
		if ("1".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewProductFinal";
		} else if ("2".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewSupportFinal";
		} else if ("3".equals(vendor.getKindCd())) {
			returnView = "edi/consult/applyNewTenantFinal";
		} else {
			returnView = "edi/consult/applyNewProductFinal";
		}
		return returnView;
	}

	// 지원
	@RequestMapping(value = "/epc/edi/consult/applyNewSupportFinal", method = RequestMethod.GET)
	public String showConsultapplyNewSupportFinal(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();
		Vendor vendor = consultService.selectNewVendorInfoApply(businessNo);
		// Vendor vendor = consultService.selectVendorInfo2(businessNo);
		 String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(), "yyyyMM");
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);
		List<Vendor> answerList = consultService.selectVendorAnswer(businessNo);
		// List<HashBox> consultTeamList = commService.selectConsultTeamList();
		// model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("vendor", vendor);
		 model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		 model.addAttribute("imagePath" , ConfigUtils.getString("edi.image.url"));
		model.addAttribute("answerList", answerList);
		// model.addAttribute("consultTeamList", consultTeamList);

		return "edi/consult/applyNewSupportFinal";
	}

	// 테넌트
	@RequestMapping(value = "/epc/edi/consult/applyNewTenantFinal", method = RequestMethod.GET)
	public String showConsultapplyNewTenantFinal(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectNewVendorInfoApply(businessNo);
		// Vendor vendor = consultService.selectVendorInfo2(businessNo);
		// String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
		// "yyyyMM");
		// List<VendorProduct> vendorProductList =
		// consultService.selectVendorProduct(businessNo);
		List<Vendor> answerList = consultService.selectVendorAnswer(businessNo);
		// List<HashBox> consultTeamList = commService.selectConsultTeamList();
		// model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("vendor", vendor);
		// model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		// model.addAttribute("imagePath" ,
		// ConfigUtils.getString("edi.image.url"));
		model.addAttribute("answerList", answerList);
		// model.addAttribute("consultTeamList", consultTeamList);

		return "edi/consult/applyNewTenantFinal";
	}

	@RequestMapping(value = "/epc/edi/consult/insertStep4", method = RequestMethod.GET)
	public String showConsultStep4Page(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectVendorInfo2(businessNo);
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
				"yyyyMM");

		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);

		List<AutionItem> autionItemListSum = consultService
				.selectAuItemSum(businessNo);
		// List<AutionItem> autionItemList =
		// consultService.selectAuItem(businessNo);
		model.addAttribute("autionItemListSum", autionItemListSum);

		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);

		model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/insertStep4";
	}

	@RequestMapping(value = "/epc/edi/consult/insertStepEtc", method = RequestMethod.GET)
	public String showConsultStep4PageEtc(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		Vendor vendor = consultService.selectVendorInfo2(businessNo);
		// Vendor vendor = consultService.selectVendorInfo2Etc(businessNo);
		String attachFileFolder = DateFormatUtils.format(vendor.getRegDate(),
				"yyyyMM");

		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);

		List<AutionItem> autionItemListSum = consultService
				.selectAuItemSum(businessNo);
		// List<AutionItem> autionItemList =
		// consultService.selectAuItem(businessNo);
		model.addAttribute("autionItemListSum", autionItemListSum);

		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);

		model.addAttribute("attachFileFolder", attachFileFolder);
		model.addAttribute("gubunBlock", processCheck(request, model));
		model.addAttribute("imagePath", ConfigUtils.getString("edi.image.url"));
		return "edi/consult/insertStep4past";
	}

	@RequestMapping(value = "/epc/edi/consult/insertStep4", method = RequestMethod.POST)
	public String submitConsultStep4Final(HttpServletRequest request,
			Model model) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		consultService.updateVendorStatus(vendorSession.getVendor());

		Vendor vendor = consultService.selectVendorInfo2(businessNo);
		List<Sale> saleList = consultService.selectSaleInfoByVendor(businessNo);
		List<EdiCommonCode> otherStoreList = commService.selectOtherStoreList();
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);

		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("otherStoreList", otherStoreList);
		model.addAttribute("vendor", vendor);

		model.addAttribute("gubunBlock", processCheck(request, model));

		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO", businessNo);
		hmap.put("V_ID", "");
		hmap.put("V_AUTHO_FG", "");
		hmap.put("V_TEL_NO", "");
		hmap.put("V_EMAIL", "");
		hmap.put("V_SUB_INFO_ID", "");
		hmap.put("V_EMS_CD", "");
		hmap.put("V_SVC_SEQ", "");

		hmap.put("V_VENDOR_ID", "");
		hmap.put("V_ANX_INFO_CD", "");
		hmap.put("V_DEST_CALL_NO", "");
		hmap.put("V_MSGS", "");
		hmap.put("V_SMS_CD", "20");

		hmap.put("V_MSG", "");
		hmap.put("V_ERR", "");
		hmap.put("V_LOG", "");

		sendemssmsService.sendSMS(hmap);

		return "edi/consult/insertStep4";
	}

	@RequestMapping(value = "/epc/edi/consult/insertStepApplyFinal", method = RequestMethod.POST)
	public String submitConsultApplyFinal(HttpServletRequest request,
			Model model, SearchParam searchParam) throws Exception {

		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");
		String businessNo = vendorSession.getVendor().getBmanNo();

		// 패스워드 업데이트 추가
		String pwd = request.getParameter("password");
//		pwd = xecuredbConn.hash(pwd);
		searchParam.setPassword(pwd);
		searchParam.setBusinessNo(businessNo);
		Integer vendorPasswordCount = consultService
				.updateVendorPassword(searchParam);

		consultService.updateVendorStatus(vendorSession.getVendor());

		Vendor vendor = consultService.selectVendorInfoApply(businessNo);
		
		List<VendorProduct> vendorProductList = consultService
				.selectVendorProduct(businessNo);

		model.addAttribute("vendorProductList", vendorProductList);
		model.addAttribute("vendor", vendor);
		model.addAttribute("gubunBlock", processCheck(request, model));

		HashMap<String, Object> hmap = new HashMap();
		hmap.put("V_BMAN_NO",businessNo);
		hmap.put("V_ID","");
		hmap.put("V_AUTHO_FG","");
		hmap.put("V_TEL_NO","");
		hmap.put("V_EMAIL","");
		hmap.put("V_SUB_INFO_ID","");
	//	hmap.put("V_EMS_CD","42");
		hmap.put("V_SVC_SEQ","");
		
		hmap.put("V_VENDOR_ID","");
		hmap.put("V_ANX_INFO_CD","");
		hmap.put("V_DEST_CALL_NO","");
		hmap.put("V_MSGS","");
		hmap.put("V_SMS_CD","20");
		
		hmap.put("V_MSG","");
		hmap.put("V_ERR","");
		hmap.put("V_LOG","");

		if ("1".equals(vendor.getKindCd())) {
			
			hmap.put("V_EMS_CD","42");			
		    sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
		
		} else if ("2".equals(vendor.getKindCd())) {
			hmap.put("V_EMS_CD","43");	
			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
			//sendemssmsService.sendSMSConsultSupport(hmap); 잠시만 개발기에서 끔
		} else if ("3".equals(vendor.getKindCd())) {
			hmap.put("V_EMS_CD","44");	
			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
			//sendemssmsService.sendSMSConsultTenant(hmap); 잠시만 개발기에서 끔
		} else {
			sendemssmsService.sendEMS(hmap); // 잠시만 개발기에서 끔
		}
		// sendemssmsService.sendSMS(hmap); 잠시만 개발기에서 끔

		// return "edi/consult/applyNewProductFinal";
		return "redirect:/epc/edi/consult/applyNewFinal.do";

	}

	@RequestMapping(value = "/epc/edi/consult/messagePopup")
	public String messagePopup(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		model.addAttribute("strMsg", getText(request.getParameter("strMsg")));
		model.addAttribute("objFocus", request.getParameter("objFocus"));
		return "edi/popup/messagePopup";
	}

	@RequestMapping(value = "/epc/edi/consult/PEDMCST057Process", method = RequestMethod.GET)
	public String processCheck(HttpServletRequest request, Model model)
			throws Exception {
		VendorSession vendorSession = (VendorSession) WebUtils
				.getSessionAttribute(request, "vendorSession");

		String businessNo = "";

		if (vendorSession != null) {
			businessNo = vendorSession.getVendor().getBmanNo();
		}

		String gubunBlock = "N";

		// HashBox hb =consultService.processCk(businessNo);
		HashBox hb2 = consultService.selectResultInfo(businessNo);

		if (hb2 == null) {
			// if(hb2.get("CHG_STATUS_CD")==""){
			gubunBlock = "N";
		} else {
			if ("0".equals(hb2.get("CHG_STATUS_CD"))
					|| "N".equals(hb2.get("PAPE_JGM_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("CNSL_RSLT_DIVN_CD"))
					|| "N".equals(hb2.get("ENTSHP_RSLT_DIVN_CD"))) {
				gubunBlock = "N"; // 신청가능
			} else if ("1".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "Y"; // 심사중
			} else if ("2".equals(hb2.get("CHG_STATUS_CD"))) {
				gubunBlock = "C"; // 신청완료
			}
		}
		return gubunBlock;
	}

}
