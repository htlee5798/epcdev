package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.model.SRMEVL0010VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMEVL0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
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
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 로그인 Controller
 * 
 * @author SHIN SE JIN
 * @since 2016.07.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.08  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMEVL0010Controller {
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private SRMEVL0010Service srmevl0010Service;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;
	
	/**
	 * 품질경영평가 로그인 화면 초기화
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/SRMEVL0010.do")
	public String SRMEVL0010(Model model) throws Exception {
		model.addAttribute("serverType", serverType);
		return "/edi/srm/SRMEVL0010";
	}
	
	/**
	 * 품질경영평가 로그인
	 * @param SRMEVL0010VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/SRMEVLLogin.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> SRMEVLLogin(@RequestBody SRMEVL0010VO vo,HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 평가자로그인 정보조회
		SRMEVL0010VO resultVO = srmevl0010Service.selectSRMEVLLogin(vo);

		if(StringUtil.isEmpty(vo.getPassword())){
			return null;
		}
		String password = SRMCommonUtils.EncryptSHA256(vo.getPassword());
		
		if (resultVO == null || resultVO.getSellerCode() == null) {	// 등록된 정보가 없을경우
			resultMap.put("msg", "NOT_DATA");
			
		} else {
			
			if (resultVO.getPassCheckCnt() >= 15) {	// 비밀번호 오류 횟수 초과 로그인 금지
				resultMap.put("msg", "PASSCHECK_OUT");
				return resultMap;
			}
			
			if (password.equals(StringUtil.null2str(resultVO.getPassword()))) {	// 비밀번호가 맞는 경우
				srmevl0010Service.updatePassCheckCntReset(vo);					// 비밀번호 오류 횟수 카운트 초기화
				
				SRMSessionVO evlSession = new SRMSessionVO();
				
				evlSession.setHouseCode(StringUtil.null2str(resultVO.getHouseCode()));			//하우스코드
				evlSession.setEvalSellerCode(StringUtil.null2str(resultVO.getSellerCode()));	//업체코드
				evlSession.setSellerNameLoc(StringUtil.null2str(resultVO.getSellerNameLoc()));	//업체명
				evlSession.setEvUserId(StringUtil.null2str(resultVO.getUserId()));				//평가업체id
				
				request.getSession().setAttribute(config.getString("lottemart.srm.evl.session.key"), evlSession);	//세션 생성
				
				resultMap.put("msg", "OK");
				
			} else {										// 비밀번호가 맞지 않을 경우
				srmevl0010Service.updatePassCheckCnt(vo);	// 비밀번호 오류 횟수 카운트 증가
				resultMap.put("msg", "NOT_PASSWORD");
				
				if (resultVO.getPassCheckCnt() >= 14) {	// 비밀번호 오류 횟수 초과 로그인 금지
					resultMap.put("msg", "PASSCHECK_OUT");
					return resultMap;
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 품질경영평가 로그아웃
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/SRMEVLLogout.do")
	public String SRMSLogout(HttpServletRequest request) throws Exception {
		
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
		
		if (session != null) {
			//세션 삭제(로그아웃)
			request.getSession().removeAttribute(config.getString("lottemart.srm.evl.session.key"));
		}
		
		return "/edi/srm/SRMEVL0010";
	}
	
}
