package com.lottemart.epc.edi.srm.controller;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.edi.srm.model.SRMJON0010VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0010Service;

/**
 * 입점상담 / 입점상담신청  / 개인정보 수집 동의 Controller
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
public class SRMJON0010Controller {
	
	@Autowired
	private SRMJON0010Service srmjon0010Service;
	
	@Autowired
	private ConfigurationService config;
	
	/**
	 * 개인정보 수집 동의 init
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJON0010.do")
	public String init() throws Exception {
		return "/edi/srm/SRMJON0010";
	}
	
	
	/**
	 * 약관동의 시 정보동의 INSERT
	 * @param SRMJON0010VO
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/insertCounselInfo.json", method = RequestMethod.POST)
	public @ResponseBody String insertCounselInfo(@RequestBody SRMJON0010VO vo, HttpServletRequest request) throws Exception {
		
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
		
		// ip주소
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null){
        	vo.setIpAddress(request.getRemoteAddr());// IPv6형식(0:0:0:0:0:0:0:1)
        }
		
		if (session != null) {
			vo.setHouseCode(StringUtil.null2str(session.getHouseCode()));			// 하우스코드
			vo.setShipperType(StringUtil.null2str(session.getShipperType()));		// 업체 소싱 국가 구분
			vo.setIrsNo(StringUtil.null2str(session.getIrsNo()));					// 사업자등록번호
			vo.setSellerNameLoc(StringUtil.null2str(session.getSellerNameLoc()));	// 사업자명
		}
		
		srmjon0010Service.insertCounselInfo(vo);
		
		return "success";
	}
	
	
}
