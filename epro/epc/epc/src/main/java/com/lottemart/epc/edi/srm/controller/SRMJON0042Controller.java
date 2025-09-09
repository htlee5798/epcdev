package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.model.SRMJON0042VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0042Service;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 입점상담 / 입점상담신청  / 입찰상담 정보등록 [인증/신용평가 정보]
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0042Controller {

	@Autowired
	private SRMJON0042Service srmjon0042Service;

	@Autowired
	private SRMJON0043Service srmjon0043Service;

	@Autowired
	private ConfigurationService config;

	/**
	 * 입찰상담 정보등록 [인증/신용평가 정보]
	 * @param Model
	 * @param SRMJON0042VO
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0042.do")
	public String init(SRMJON0042VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		SRMJON0043VO searchVo = new SRMJON0043VO();
		searchVo.setHouseCode(session.getHouseCode());
		searchVo.setSellerCode(session.getSellerCode());
		searchVo.setReqSeq(vo.getReqSeq());
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(searchVo, request));
//		model.addAttribute("srmComp", srmjon0042Service.selectHiddenCompCreditInfo(vo));
		return "/edi/srm/SRMJON0042";
	}

	/**
	 * 입찰상담 정보등록 [신용정보] 저장
	 * @param Model
	 * @param SRMJON0042VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateHiddenCompCreditInfo.do")
	public String updateHiddenCompCreditInfo(Model model, SRMJON0042VO vo, HttpServletRequest request) throws Exception {
		//상세정보 UPDATE
		srmjon0042Service.updateHiddenCompCreditInfo(vo);

		model.addAttribute("url", StringUtil.null2str(vo.getUrl()));
//		model.addAttribute("srmComp", srmjon0042Service.selectHiddenCompCreditInfo(vo));
		SRMJON0043VO searchVo = new SRMJON0043VO();
		searchVo.setHouseCode(vo.getHouseCode());
		searchVo.setSellerCode(vo.getSellerCode());
		searchVo.setReqSeq(vo.getReqSeq());
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(searchVo, request));
		return "/edi/srm/SRMJON0042";
	}
	
	/**
	 * 신용평가기관 정보 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCreditInfo.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectCreditInfo(@RequestBody SRMJON0042VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String retMsg = srmjon0042Service.selectCreditInfo(vo, request);

		resultMap.put("retMsg", StringUtil.null2str(retMsg));

		return resultMap;
	}
	
	/**
	 * 신용평가정보 찾기
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCreditAllInfoPopup.do")
	public String selectCreditAllInfo(Model model, SRMJON0042VO vo) throws Exception {
		model.addAttribute("companyType", vo.getCompanyType());
		model.addAttribute("companyRegNo", vo.getCompanyRegNo());
		return "/edi/srm/SRMJON004201";
	}
	
	/**
	 * 신용평가기관 정보 조회
	 * @param SRMJON0042VO
	 * @param HttpServletRequest
	 * @return List<SRMJON0042VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCreditAllInfo.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMJON0042VO> selectCreditAllInfo(@RequestBody SRMJON0042VO vo, HttpServletRequest request) throws Exception {
				
		return srmjon0042Service.selectCreditAllInfo(vo, request);
	}
	
}
