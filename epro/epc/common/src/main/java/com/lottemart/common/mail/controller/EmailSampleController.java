package com.lottemart.common.mail.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.lottemart.common.mail.model.LteMailVO;
import com.lottemart.common.mail.service.LteMailService;

/**
 * 
 * @Class Name : EmailSampleController.java
 * @Description : Email 발송 Controller 부분
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * 
 * @deprecated 해당 내용을 참조 할 뿐 사용하여 개발하는 것은 금지함(사용하기 위해 만든 controller 아님).
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("EmailSampleController")
public class EmailSampleController {
	
	@Autowired
	private LteMailService LteMailService;
	
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    /** Message*/
    @Resource(name = "messageSource")
    private MessageSource messageSource;
	
	/**
	 * Desc : Email 저장 폼
	 * @Method Name : emailInsertForm
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("sample/email/insertform.do")
	public String emailInsertForm(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("lteMailVO", new LteMailVO());
		
		return "common/tools/emailinsert";
	}
	
	/**
	 * Desc : Email 저장
	 * @Method Name : emailInsert
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("sample/email/insert.do")
	public String emailInsert(HttpServletRequest request,LteMailVO lteMailVO,
            BindingResult bindingResult, Model model, SessionStatus status) throws Exception {
		
		// Server-Side Validation
    	beanValidator.validate(lteMailVO, bindingResult);
    	
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("lteMailVO", lteMailVO);
    		request.setAttribute("message", "error");
			return "common/tools/smsinsert";
    	}
    	
    	LteMailService.inserteMailSend(lteMailVO);
        status.setComplete();
        
        String sucMessage 
        	= messageSource.getMessage("success.email.send", new Object[] {lteMailVO.getRMAIL()}, "", Locale.getDefault());
        request.setAttribute("message", sucMessage);
		return "common/tools/smsinsert";
	}
	
	
	/**
	 * @param request
	 * @param ltsmsVO
	 * @param bindingResult
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sample/email/emailSendList.do")
	public String smsSendList(HttpServletRequest request,LteMailVO lteMailVO,
            BindingResult bindingResult, Model model, SessionStatus status) throws Exception {
		
		int pageRow = 3;
		int start = 0;
		
		int total = LteMailService.selectMailInfsCnt(lteMailVO);
		
		int pageCnt = total/pageRow+1;
		
		int end = pageRow*1/*page number*/;
		
		lteMailVO.setSTART(String.valueOf(start));
		lteMailVO.setEND(String.valueOf(end));
		
		List<LteMailVO> emailList = LteMailService.selecteMailInfs(lteMailVO);
		
		model.addAttribute("emailList", emailList);
		
		return "common/tools/emailSendList";
	}
	
}
