package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMVEN003001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0030VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0030Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SRM 모니터링
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.17  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMVEN0030Controller {

	@Autowired
	private SRMVEN0030Service srmven0030Service;

	/**
	 * SRM 모니터링 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/edi/ven/SRMVEN0030.do"})
	public String init() throws Exception {
		return "/edi/srm/SRMVEN0030";
	}

	/**
	 * 대분류/중분류 코드
	 * @param SRMVEN0030VO
	 * @return List<OptionTagVO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectCatLv1CodeList.json", method = RequestMethod.POST)
	public @ResponseBody List<OptionTagVO> selectCatLv1CodeList(@RequestBody SRMVEN0030VO vo) throws Exception {
		return srmven0030Service.selectCatLv1CodeList(vo);
	}

	/**
	 * SRM모니터링 LIST
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN0030VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectSRMmoniteringList(@RequestBody SRMVEN0030VO vo, HttpServletRequest request) throws Exception {
		return srmven0030Service.selectSRMmoniteringList(vo, request);
	}


	/**
	 * SRM 모니터링 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailPopup.do")
	public String selectSRMmoniteringDetailPopup(Model model, SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Date.valueOf(vo.getCountYear()+"-"+vo.getCountMonth()+"-01"));
		String lastDay = String.valueOf(cal.getActualMaximum(cal.DAY_OF_MONTH));

		model.addAttribute("vo", vo);
		model.addAttribute("lastDate", vo.getCountYear()+"."+vo.getCountMonth()+"."+lastDay);

		model.addAttribute("countYear", vo.getCountYear());
		model.addAttribute("countMonth", vo.getCountMonth());

		model.addAttribute("list", srmven0030Service.selectSRMmoniteringDetail(vo, request));							//상세LIST
		model.addAttribute("claimCount", srmven0030Service.selectSRMmoniteringDetailClaim(vo));					//고객컴플레인수
		model.addAttribute("grade", srmven0030Service.selectSRMmoniteringDetailGrade(vo));					//등급조회

		model.addAttribute("defective", srmven0030Service.selectSRMmoniteringDetailDefective(vo));					//불량등록건수

		return "/edi/srm/SRMVEN003001";
	}

	/**
	 * SRM모니터링 차트
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN0030VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailChart.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMVEN003001VO> selectSRMmoniteringDetailChart(@RequestBody SRMVEN003001VO vo) throws Exception {
		return srmven0030Service.selectSRMmoniteringDetailChart(vo);
	}


	/**
	 * 현재까지 평가점수
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN0030VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailCurrentValue.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, List<SRMVEN003001VO>> selectSRMmoniteringDetailCurrentValue(@RequestBody SRMVEN003001VO vo) throws Exception {
		return srmven0030Service.selectSRMmoniteringDetailCurrentValue(vo);
	}


	/**
	 * SRM 모니터링 > 비고팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailRemarkPopup.do")
	public String selectSRMmoniteringDetailRemarkPopup(Model model, SRMVEN003001VO vo) throws Exception {
		model.addAttribute("data",srmven0030Service.selectSRMmoniteringDetailRemarkEtc(vo));
		return "/edi/srm/SRMVEN003002";
	}

	/**
	 * SRM 모니터링 > 특이사항 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailEtcPopup.do")
	public String selectSRMmoniteringDetailEtcPopup(Model model, SRMVEN003001VO vo) throws Exception {
		model.addAttribute("data",srmven0030Service.selectSRMmoniteringDetailRemarkEtc(vo));
		return "/edi/srm/SRMVEN003003";
	}

	/**
	 * SRM 모니터링 > 등급예제 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailGradeExemplePopup.do")
	public String selectSRMmoniteringDetailGradeExemplePopup(Model model, SRMVEN003001VO vo) throws Exception {
		model.addAttribute("grade",vo.getGrade());
		model.addAttribute("data",srmven0030Service.selectSRMmoniteringDetailGradeExemple(vo));
		return "/edi/srm/SRMVEN003004";
	}


	/**
	 * SRM 모니터링 > PLC등급 리스트 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailPlcPopup.do")
	public String selectSRMmoniteringDetailPlcPopup(Model model, SRMVEN003001VO vo) throws Exception {
		model.addAttribute("vo",vo);
		model.addAttribute("list",srmven0030Service.selectSRMmoniteringDetailPlc(vo));
		return "/edi/srm/SRMVEN003005";
	}


	/**
	 * PLC등급 리스트
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailPlcList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMVEN003001VO> selectCatLv1CodeList(@RequestBody SRMVEN003001VO vo) throws Exception {
		return srmven0030Service.selectSRMmoniteringDetailPlc(vo);
	}


	/**
	 * SRM 모니터링 > 불량등록건수 리스트 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailDefectivePopup.do")
	public String selectSRMmoniteringDetailDefectivePopup(Model model, SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("list",srmven0030Service.selectSRMmoniteringDetailDefectiveList(vo, request));
		return "/edi/srm/SRMVEN003006";
	}

	/**
	 * SRM 모니터링 > 개선요청 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSRMmoniteringDetailImpReqPopup.do")
	public String selectSRMmoniteringDetailImpReqPopup(Model model, SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		SRMVEN003001VO data = srmven0030Service.selectSRMmoniteringDetailImpReq(vo, request);
		model.addAttribute("data",data);
		model.addAttribute("fileList",srmven0030Service.selectSRMmoniteringDetailImpReqFileList(data));

		return "/edi/srm/SRMVEN003007";
	}
	/**
	 * SRM 모니터링 > 개선요청 저장
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/updateSRMmoniteringDetailImpReq.do")
	public String updateSRMmoniteringDetailImpReq(Model model, SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("result",srmven0030Service.updateSRMmoniteringDetailImpReq(vo));
		SRMVEN003001VO data = srmven0030Service.selectSRMmoniteringDetailImpReq(vo, request);
		model.addAttribute("data",data);

		return "/edi/srm/SRMVEN003007";
	}

}
