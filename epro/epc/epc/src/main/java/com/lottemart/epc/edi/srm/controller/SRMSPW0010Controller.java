package com.lottemart.epc.edi.srm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import com.lottemart.epc.edi.srm.service.SRMJON0020Service;
import lcn.module.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.edi.srm.model.SRMSPW0010VO;
import com.lottemart.epc.edi.srm.service.SRMSPW0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;

/**
 * 비밀번호 변경 Controller
 *
 * @author SHIN SE JIN
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMSPW0010Controller {

	@Autowired
	SRMSPW0010Service srmspw0010Service;

	@Autowired
	SRMJON0020Service srmjon0020Service;

	/**
	 * 비밀번호 변경 init
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMSPW0010.do")
	public String init() throws Exception {
		return "/edi/srm/SRMSPW0010";
	}

	/**
	 * 비밀번호 변경전 확인
	 * @param SRMSPW0010VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/passwdChangeCheck.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> passwdChangeCheck(@RequestBody SRMSPW0010VO vo) throws Exception {
		if (vo == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기 등록된 내역 확인
		SRMSPW0010VO resultVO = srmspw0010Service.passwdChangeCheck(vo);

		if (resultVO == null || resultVO.getSellerCode() == null || resultVO.getSellerCode().equals("")) {
			resultMap.put("msg", "fail");
		} else {
			resultMap.put("msg", "success");
			resultMap.put("resultVO", resultVO);
		}
		return resultMap;
	}

	/**
	 * 비밀번호 변경 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/passwdChangPopup.do")
	public String passwdChangPopup(SRMSPW0010VO vo, Model model, HttpServletRequest request) throws Exception {
		if (request == null) {
			return null;
		}

		// 담당자 이메일 리스트
		model.addAttribute("vEmailList", srmspw0010Service.selectVEmailList(vo));

		return "/edi/srm/SRMSPW001001";
	}

	/**
	 * 비밀번호 변경
	 * @param SRMSPW0010VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updatePasswdChange.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updatePasswdChange(@RequestBody SRMSPW0010VO vo) throws Exception {
		if (vo == null) return null;
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if ("Y".equals(vo.getIsKeepPassword90())) {
			srmspw0010Service.updatePwdChgDateToday(vo);
			resultMap.put("msg", "KEEP_PASSWORD_90");
			return resultMap;
		}

		//----- 비밀번호 유효성 체크 --------------------
		if (!StringUtil.isEmpty(vo.getTempPw()) && vo.getTempPw().length() < 8) {
			resultMap.put("msg", "NOT_LENGTH");
			return resultMap;
		}

		// 공백체크
		if (!StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isSpaceCheck(vo.getTempPw())) {
			resultMap.put("msg", "EXIST_SPACE");
			return resultMap;
		}

		// 허용된 문자열인지 체크
		if (!StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isPermitPasswordCharCheck(vo.getTempPw())) {
			resultMap.put("msg", "NOT_PERMIT");
			return resultMap;
		}

		// 중복된 3자 이상의 문자 또는 숫자 사용불가
		/*if (!SRMCommonUtils.isDuplicate3Character(vo.getTempPw())) {
			resultMap.put("msg", "EXIST_DUPL");
			return resultMap;
		}*/

		// 문자, 숫자, 특수문자 혼용
		if (!StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isPasswordCharCheck(vo.getTempPw())) {
			resultMap.put("msg", "NOT_MATCH");
			return resultMap;
		}
		//---------------------------------------------

		SRMSPW0010VO resultVO = srmspw0010Service.passwdChangeCheck(vo);		// 이전 비밀번호 조회
		// 비밀번호 변경 일 경우
		if (vo.getPwdGbn().equals("0")) {
			if(!StringUtil.isEmpty(vo.getOldTempPw())){
				vo.setOldTempPw(SRMCommonUtils.EncryptSHA256(vo.getOldTempPw()));	// 이전 비밀번호 암호화
			}
			if(!StringUtil.isEmpty(vo.getTempPw())){
				vo.setTempPw(SRMCommonUtils.EncryptSHA256(vo.getTempPw()));			// 변경할 비밀번호 암호화
			}

			if (!resultVO.getTempPw().equals(vo.getOldTempPw())) {					// 이전 비밀번호 체크
				resultMap.put("msg", "NOT_EQUALS");
				return resultMap;
			}

			// 이전 비밀번호와 변경할 비밀번호와 같을 경우
			if (vo.getTempPw().equals(resultVO.getTempPw()) ||
					vo.getTempPw().equals(resultVO.getOldTempPw1()) ||
					vo.getTempPw().equals(resultVO.getOldTempPw2())) {
				resultMap.put("msg", "OLD_PW_EQUALS");
				return resultMap;
			}
		}

		if (resultVO != null) {
			vo.setOldTempPw2(resultVO.getOldTempPw1()); // 기존 등록비밀번호1 > 등록비밀번호2
			vo.setOldTempPw1(resultVO.getTempPw()); // 기존 등록비밀번호 > 기존등록비밀번호1
		}

		// 비밀번호 변경/임시비밀번호 발송
		String retString = srmspw0010Service.updatePasswdChange(vo);

		if (retString.equals("success")) {
			resultMap.put("msg", "success");
		} else {
			resultMap.put("msg", "fail");
		}

		return resultMap;
	}

	/**
	 * 임시 비밀번호 변경 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/tempPasswdChangPopup.do",  method = {RequestMethod.GET, RequestMethod.POST})
	public String tempPasswdChangPopup(SRMSPW0010VO vo, Model model, HttpServletRequest request) throws Exception {
		if (request == null) {
			return null;
		}

		if (vo.getCountry() == null || vo.getCountry().isEmpty()) {
			vo.setCountry("KR");
		}

		SRMSPW0010VO resultVO = srmspw0010Service.passwdChangeCheck(vo);
		if (resultVO != null) {
			vo.setSellerCode(resultVO.getSellerCode());
		}

		model.addAttribute("vendorInfo", vo);

		return "/edi/srm/SRMJON002004";
	}

	/**
	 * 비밀번호 변경 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/pwChangeOver90Popup.do",  method = {RequestMethod.GET, RequestMethod.POST})
	public String pwChangeOver90Popup(SRMSPW0010VO vo, Model model, HttpServletRequest request) throws Exception {
		if (request == null) {
			return null;
		}

		if (vo.getCountry() == null || vo.getCountry().isEmpty()) {
			vo.setCountry("KR");
		}

		SRMSPW0010VO resultVO = srmspw0010Service.passwdChangeCheck(vo);
		if (resultVO != null) {
			vo.setSellerCode(resultVO.getSellerCode());
		}

		model.addAttribute("vendorInfo", vo);

		return "/edi/srm/SRMJON002005";
	}


	/**
	 * 임시비밀번호 변경
	 * @param SRMSPW0010VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateTempPasswdChange.json", method = {RequestMethod.POST})
	public @ResponseBody Map<String, Object> updateTempPasswdChange(@RequestBody SRMSPW0010VO vo, HttpServletRequest request) throws Exception {
		if (vo == null) return null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*
			1. 비밀번호 유효 체크
			2. 변경 비밀번호 갱신 및 세션 생성
		 */

		// 1. 비밀번호 유효체크
		if (vo.getIrsNo() == null || vo.getHouseCode() == null) {
			resultMap.put("msg", "GET_SUBMIT");
			return resultMap;
		}

		// 길이 체크
		if (!lcn.module.common.util.StringUtil.isEmpty(vo.getTempPw()) && vo.getTempPw().length() < 8) {
			resultMap.put("msg", "NOT_LENGTH");
			return resultMap;
		}

		// 공백 체크
		if (!lcn.module.common.util.StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isSpaceCheck(vo.getTempPw())) {
			resultMap.put("msg", "EXIST_SPACE");
			return resultMap;
		}

		// 허용된 문자열인지 체크
		if (!lcn.module.common.util.StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isPermitPasswordCharCheck(vo.getTempPw())) {
			resultMap.put("msg", "NOT_PERMIT");
			return resultMap;
		}

		// 중복된 3자 이상의 문자 또는 숫자 사용불가
		/*if (!SRMCommonUtils.isDuplicate3Character(vo.getTempPw())) {
			resultMap.put("msg", "EXIST_DUPL");
			return resultMap;
		}*/

		// 문자, 숫자, 특수문자 혼용
		if (!lcn.module.common.util.StringUtil.isEmpty(vo.getTempPw()) && !SRMCommonUtils.isPasswordCharCheck(vo.getTempPw())) {
			resultMap.put("msg", "NOT_MATCH");
			return resultMap;
		}
		//---------------------------------------------

		// 비밀번호 변경 일 경우
		SRMSPW0010VO resultVO = srmspw0010Service.passwdChangeCheck(vo);		// 이전 비밀번호 조회

		if(!lcn.module.common.util.StringUtil.isEmpty(vo.getTempPw())){
			vo.setTempPw(SRMCommonUtils.EncryptSHA256(vo.getTempPw()));			// 변경할 비밀번호 암호화
		}

		// 이전 비밀번호와 변경할 비밀번호와 같을 경우
		if (vo.getTempPw().equals(resultVO.getTempPw()) ||
				vo.getTempPw().equals(resultVO.getOldTempPw1()) ||
				vo.getTempPw().equals(resultVO.getOldTempPw2())) {
			resultMap.put("msg", "OLD_PW_EQUALS");
			return resultMap;
		}

		vo.setOldTempPw2(resultVO.getOldTempPw1()); // 기존 등록비밀번호1 > 등록비밀번호2
		vo.setOldTempPw1(resultVO.getTempPw()); // 기존 등록비밀번호 > 기존등록비밀번호1

		// 2. 변경 비밀번호 갱신 및 세션 생성
		SRMJON0020VO srmjon0020vo = new SRMJON0020VO();
		srmjon0020vo.setHouseCode(vo.getHouseCode());
		srmjon0020vo.setSellerCode(vo.getSellerCode());
		srmjon0020vo.setIrsNo(vo.getIrsNo());
		srmjon0020vo.setTempPw(vo.getTempPw());


		srmspw0010Service.updateTempPasswdChange(vo);
		srmjon0020Service.setSession(srmjon0020vo, request);						// Session 생성

		resultMap.put("msg", "success");
		return resultMap;
	}
}
