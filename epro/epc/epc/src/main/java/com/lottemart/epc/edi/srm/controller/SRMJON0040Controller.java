package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMJON004001VO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0040Service;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 입점상담 / 입점상담신청  / 입찰상담 정보등록 [기본정보]
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
public class SRMJON0040Controller {

	@Autowired
	private SRMJON0040Service srmjon0040Service;

	@Autowired
	private SRMJON0043Service srmjon0043Service;

	@Autowired
	private ConfigurationService config;

	/**
	 * 입찰상담 정보등록 [기본정보]
	 * @param HttpServletRequest
	 * @param Model
	 * @param SRMJON0040VO
	 * @return String
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0040.do")
	public String init(HttpServletRequest request, Model model, SRMJON0040VO vo) throws Exception {
		
		//잠재업체 기본정보 조회
		if (!StringUtil.isEmpty(vo.getReqSeq())){
			String sessionKey = config.getString("lottemart.srm.session.key");
			SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

			SRMJON0043VO searchVo = new SRMJON0043VO();
			searchVo.setHouseCode(session.getHouseCode());
			searchVo.setSellerCode(session.getSellerCode());
			searchVo.setReqSeq(vo.getReqSeq());
			
			vo.setSellerCode(session.getSellerCode());
			vo.setHouseCode(session.getHouseCode());
			
			model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(searchVo, request));
		} else {
			model.addAttribute("srmComp", srmjon0040Service.selectHiddenCompInfo(request, vo));
		}

		// 개인정보동의 유형 구분자
		model.addAttribute("agreeType", srmjon0040Service.selecthiddenCompAgreeType(vo, request));
//		model.addAttribute("catLv1Code", srmjon0040Service.selectCatLv1CodeList());

		return "/edi/srm/SRMJON0040";
	}

	/**
	 * 잠재업체 기본정보 임시저장
	 * @param SRMJON0040VO
	 * @return Map<String, Object>
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/updateHiddenCompInfo.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateHiddenCompInfo(@RequestBody SRMJON0040VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = srmjon0040Service.updateHiddenCompInfo(vo, request);
		return resultMap;
	}

	/**
	 * 주소찾기
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectJusoPopup.do")
	public String selectJuso() throws Exception {
		return "/edi/srm/SRMJON004001";
	}


	/**
	 * 주소 검색
	 * @param paramVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectZipList.do")
	public @ResponseBody Map<String, Object> selectZipList(@RequestBody SRMJON004001VO paramVO, HttpServletRequest request) throws Exception {
		if(paramVO == null) return null;

		Map<String,Object> resultMap = srmjon0040Service.selectZipList(paramVO, request);

		return resultMap;
	}


}
