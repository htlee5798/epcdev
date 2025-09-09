package com.lottemart.epc.edi.srm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import com.lottemart.epc.edi.srm.model.SRMRST0010VO;
import com.lottemart.epc.edi.srm.service.SRMJON0020Service;
import com.lottemart.epc.edi.srm.service.SRMRST0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;

/**
 * 입점상담 > 입점상담결과확인  > 로그인 Controller
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 	수정일				수정자				수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */

@Controller
public class SRMRST0010Controller {
	
	@Autowired
	private SRMRST0010Service srmrst0010Service;
	
	@Autowired
	private SRMJON0020Service srmjon0020Service;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 입점상담 결과확인 로그인 초기화
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMRST0010.do")
	public String init(Model model) throws Exception {
		model.addAttribute("serverType", serverType);
		return "/edi/srm/SRMRST0010";
	}
	
	/**
	 * 입점상담 결과 로그인
	 * @param SRMRST0010VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMRSTLogin.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> SRMRSTLogin(@RequestBody SRMRST0010VO vo, HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		SRMRST0010VO resultVO = srmrst0010Service.selectPasswdCheck(vo);// 기 등록된 내역 확인
		
		if (resultVO == null || resultVO.getIrsNo() == null || resultVO.getIrsNo().equals("")) {	// 등록된 정보가 없는 경우
			resultMap.put("msg", "NOT_EXIST");
			return resultMap;
		}
		
		if(StringUtil.isEmpty(vo.getTempPw())) return null;

		String tempPw = SRMCommonUtils.EncryptSHA256(vo.getTempPw());	//암호화
		
		if (resultVO.getPassCheckCnt() >= 15) {							// 비밀번호 오류 횟수 초과 로그인 금지
			resultMap.put("msg", "PASSCHECK_OUT");
			return resultMap;
		}
		
		/* SRMJON0020Service에 넘길 vo 생성 */
		SRMJON0020VO srmjon0020Vo = new SRMJON0020VO();
		srmjon0020Vo.setHouseCode(vo.getHouseCode());
		srmjon0020Vo.setShipperType(vo.getShipperType());
		srmjon0020Vo.setIrsNo(vo.getIrsNo());
		srmjon0020Vo.setSellerNameLoc(vo.getSellerNameLoc());
		srmjon0020Vo.setCountry(vo.getCountry());
		
		if (tempPw.equals(resultVO.getTempPw())) {						// 비밀번호가 맞을 경우
			srmjon0020Service.updatePassCheckCntReset(srmjon0020Vo);	// 비밀번호 오류 횟수 카운트 초기화
			
			srmjon0020Service.setSession(srmjon0020Vo, request);		// Session 생성
			resultMap.put("msg", "EXIST");
			
		} else {														// 비밀번호가 맞지 않을 경우
			srmjon0020Service.updatePassCheckCnt(srmjon0020Vo);			// 비밀번호 오류 횟수 카운트 증가
			resultMap.put("msg", "NOT_PASSWD");
			
			if (resultVO.getPassCheckCnt() >= 14) {						// 비밀번호 오류 횟수 초과 로그인 금지
				resultMap.put("msg", "PASSCHECK_OUT");
				return resultMap;
			}
		}
		return resultMap;
	}
	
}
