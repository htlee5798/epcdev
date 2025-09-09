package com.lottemart.epc.etc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.epc.etc.model.PSCMETC0001VO;
import com.lottemart.epc.etc.service.PSCPETC0002Service;

@Controller
public class PSCPETC0002Controller {

	@Autowired
	private PSCPETC0002Service pscpetc0002Service;


	@RequestMapping(value = "etc/selectCodePopup.do")
	public String selectCodeDetailPopup(@ModelAttribute("vo") PSCMETC0001VO vo, Model model) throws Exception {
		model.addAttribute("pscmetc0001VO", pscpetc0002Service.selectCodePopup(vo));
		return "etc/PSCPETC000201";
	}

	@RequestMapping(value = "etc/insertCodePopupForm.do")
	public String insertCodePopupForm() throws Exception {
		return "etc/PSCPETC000202";
	}

	@RequestMapping(value = "etc/insertCodePopup.do")
	public void insertCodePopup(@ModelAttribute("vo") PSCMETC0001VO vo) throws Exception {
		vo.setOrderSeq("1");
		vo.setUseYn("Y");
		vo.setRegDivnCd("Y");
		vo.setValiStartDate("20110101000000");
		vo.setValiEndDate("99991231235959");
		pscpetc0002Service.insertCodePopup(vo);
	}

	@RequestMapping(value = "etc/updateCodePopup.do")
	public void updateCodePopup(@ModelAttribute("vo") PSCMETC0001VO vo) throws Exception {
		vo.setOrderSeq("1");
		vo.setUseYn("Y");
		vo.setRegDivnCd("Y");
		vo.setValiStartDate("20110101000000");
		vo.setValiEndDate("99991231235959");
		pscpetc0002Service.updateCodePopup(vo);
	}

	@RequestMapping(value = "etc/deleteCodePopup.do")
	public void deleteCodePopup(@ModelAttribute("vo") PSCMETC0001VO vo) throws Exception {
		pscpetc0002Service.deleteCodePopup(vo);
	}
}
