package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMMNT002001VO;
import com.lottemart.epc.edi.srm.model.SRMMNT0020VO;
import com.lottemart.epc.edi.srm.service.SRMMNT0020Service;
import lcn.module.common.util.StringUtil;
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
 * 대표자 SRM 모니터링
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMMNT0020Controller {

	@Autowired
	private SRMMNT0020Service srmmnt0020Service;

	/**
	 * 대표자 SRM 모니터링 로그인 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/edi/mnt/SRMMNT0020.do"})
	public String init(Model model, SRMMNT0020VO vo) throws Exception {
		if(StringUtil.isEmpty(vo.getIrsNo())){
			return "redirect:/edi/mnt/SRMMNT0010.do";
		}
		model.addAttribute("venCds",srmmnt0020Service.selectCeoLoginVenCdLIst(vo.getIrsNo()));
		model.addAttribute("irsNo",vo.getIrsNo());
		return "/edi/srm/SRMMNT0020";
	}


	/**
	 * 대분류/중분류 코드
	 * @param SRMMNT0020VO
	 * @return List<OptionTagVO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectCEOCatLv1CodeList.json", method = RequestMethod.POST)
	public @ResponseBody List<OptionTagVO> selectCatLv1CodeList(@RequestBody SRMMNT0020VO vo) throws Exception {
		return srmmnt0020Service.selectCatLv1CodeList(vo);
	}

	/**
	 * SRM모니터링 LIST
	 * @param SRMMNT0020VO
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectCEOSRMmoniteringList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectSRMmoniteringList(@RequestBody SRMMNT0020VO vo, HttpServletRequest request) throws Exception {
		return srmmnt0020Service.selectSRMmoniteringList(vo, request);
	}


	/**
	 * SRM 모니터링 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectCEOSRMmoniteringDetailPopup.do")
	public String selectSRMmoniteringDetailPopup(Model model, SRMMNT002001VO vo, HttpServletRequest request) throws Exception {

//		Calendar cal = Calendar.getInstance();
//		cal.setTime(Date.valueOf(vo.getCountYear()+"-"+vo.getCountMonth()+"-01"));
//		String lastDay = String.valueOf(cal.getActualMaximum(cal.DAY_OF_MONTH));

		model.addAttribute("vo", vo);
//		model.addAttribute("lastDate", vo.getCountYear()+"."+vo.getCountMonth()+"."+lastDay);

		model.addAttribute("countYear", vo.getCountYear());
		model.addAttribute("countMonth", vo.getCountMonth());

		model.addAttribute("list", srmmnt0020Service.selectSRMmoniteringDetail(vo, request));							//상세LIST
		model.addAttribute("claimCount", srmmnt0020Service.selectSRMmoniteringDetailClaim(vo));					//고객컴플레인수
		model.addAttribute("grade", srmmnt0020Service.selectSRMmoniteringDetailGrade(vo));					//등급조회

		model.addAttribute("defective", srmmnt0020Service.selectSRMmoniteringDetailDefective(vo));					//불량등록건수

		return "/edi/srm/SRMMNT002001";
	}

	/**
	 * SRM모니터링 차트
	 * @param SRMMNT0020VO
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectCEOSRMmoniteringDetailChart.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMMNT002001VO> selectSRMmoniteringDetailChart(@RequestBody SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Service.selectSRMmoniteringDetailChart(vo);
	}


	/**
	 * 현재까지 평가점수
	 * @param SRMMNT0020VO
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailCurrentValue.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, List<SRMMNT002001VO>> selectSRMmoniteringDetailCurrentValue(@RequestBody SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Service.selectSRMmoniteringDetailCurrentValue(vo);
	}

	/**
	 * SRM 모니터링 > 비고팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailRemarkPopup.do")
	public String selectSRMmoniteringDetailRemarkPopup(Model model, SRMMNT002001VO vo) throws Exception {
		model.addAttribute("data",srmmnt0020Service.selectSRMmoniteringDetailRemarkEtc(vo));
		return "/edi/srm/SRMMNT002002";
	}

	/**
	 * SRM 모니터링 > 특이사항 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailEtcPopup.do")
	public String selectSRMmoniteringDetailEtcPopup(Model model, SRMMNT002001VO vo) throws Exception {
		model.addAttribute("data",srmmnt0020Service.selectSRMmoniteringDetailRemarkEtc(vo));
		return "/edi/srm/SRMMNT002003";
	}

	/**
	 * SRM 모니터링 > 등급예제 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailGradeExemplePopup.do")
	public String selectSRMmoniteringDetailGradeExemplePopup(Model model, SRMMNT002001VO vo) throws Exception {
		model.addAttribute("grade",vo.getGrade());
		model.addAttribute("data",srmmnt0020Service.selectSRMmoniteringDetailGradeExemple(vo));
		return "/edi/srm/SRMMNT002004";
	}


	/**
	 * SRM 모니터링 > PLC등급 리스트 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailPlcPopup.do")
	public String selectSRMmoniteringDetailPlcPopup(Model model, SRMMNT002001VO vo) throws Exception {
		model.addAttribute("list",srmmnt0020Service.selectSRMmoniteringDetailPlc(vo));
		return "/edi/srm/SRMMNT002005";
	}

	/**
	 * PLC등급 리스트
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailPlcList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMMNT002001VO> selectCatLv1CodeList(@RequestBody SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Service.selectSRMmoniteringDetailPlc(vo);
	}


	/**
	 * SRM 모니터링 > 불량등록건수 리스트 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailDefectivePopup.do")
	public String selectSRMmoniteringDetailDefectivePopup(Model model, SRMMNT002001VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("list",srmmnt0020Service.selectSRMmoniteringDetailDefectiveList(vo, request));
		return "/edi/srm/SRMMNT002006";
	}

	/**
	 * SRM 모니터링 > 개선요청 팝업
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectSRMmoniteringDetailImpReqPopup.do")
	public String selectSRMmoniteringDetailImpReqPopup(Model model, SRMMNT002001VO vo, HttpServletRequest request) throws Exception {
		SRMMNT002001VO data = srmmnt0020Service.selectSRMmoniteringDetailImpReq(vo, request);
		model.addAttribute("data",data);
		model.addAttribute("fileList",srmmnt0020Service.selectSRMmoniteringDetailImpReqFileList(data));

		return "/edi/srm/SRMMNT002007";
	}
}
