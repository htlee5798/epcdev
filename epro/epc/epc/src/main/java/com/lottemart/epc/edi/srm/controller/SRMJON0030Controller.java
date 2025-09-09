package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMJON0030ListVO;
import com.lottemart.epc.edi.srm.model.SRMJON0030VO;
import com.lottemart.epc.edi.srm.service.SRMJON0030Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 입점상담 / 입점상담신청  / 1차 스크리닝 Controller
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.06  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0030Controller {
	
	@Autowired
	private SRMJON0030Service srmjon0030Service;

	/**
	 * 1차 스크리닝 화면
	 * @param HttpServletRequest
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/SRMJON0030.do")
	public String init() throws Exception {
		return "/edi/srm/SRMJON0030";
	}

	/**
	 * 1차 스크리닝 LIST 조회
	 * @param SRMJON0030VO
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/srm/selectScreeningList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMJON0030VO> selectScreeningList(@RequestBody SRMJON0030VO vo) throws Exception {
		List<SRMJON0030VO> list = srmjon0030Service.selectScreeningList(vo);
		return list;
	}

	/**
	 * 1차 스크리닝 등록
	 * @param SRMJON0030VO
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/insertScreeningList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> insertScreeningList(@RequestBody SRMJON0030ListVO listVo, HttpServletRequest request) throws Exception {
		return srmjon0030Service.insertScreeningList(listVo, request);
	}


	/**
	 * 1차 스크리닝 부적격판정 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/srm/screeningUnqualifiedPopup.do")
	public String screeningUnqualifiedPopup() throws Exception {
		return "/edi/srm/SRMJON003001";
	}
	
	/**
	 * 1차 스크리닝 적합판정 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/srm/screeningQualifiedPopup.do")
	public String screeningQualifiedPopup() throws Exception {
		return "/edi/srm/SRMJON003002";
	}



	/**
	 * 대분류 선택 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/srm/selectCatLv1CodePopup.do")
	public String selectCatLv1CodePopup() throws Exception {
		return "/edi/srm/SRMJON003003";
	}


	/**
	 * 대분류 코드 조회
	 * @param SRMJON0030VO
	 * @return List<SRMJON0030VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCatLv1CodeList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMJON0030VO> selectCatLv1CodeList(@RequestBody SRMJON0030VO vo) throws Exception {
		return srmjon0030Service.selectCatLv1CodeList(vo);
	}
}
