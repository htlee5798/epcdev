package com.lottemart.common.sms.controller;

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

import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.sms.service.LtsmsService;

/**
 * 
 * @Class Name : SMSSampleController.java
 * @Description : SMS 발송 Controller 부분
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 5. 오전 11:45:49 shko sms발송 예제 controller
 * 
 * @deprecated 해당 내용을 참조 할 뿐 사용하여 개발하는 것은 금지함(사용하기 위해 만든 controller 아님).
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("SMSSampleController")
public class SMSSampleController {

	@Autowired
	private LtsmsService LtsmsService;
	
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    /** Message*/
    @Resource(name = "messageSource")
    private MessageSource messageSource;
	
	/**
	 * Desc : SMS 저장 폼
	 * @Method Name : smsInsert
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("sample/sms/insertform.do")
	public String smsInsertForm(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("ltsmsVO", new LtsmsVO());
		
		return "common/tools/smsinsert";
	}
	
	/**
	 * Desc : SMS 저장
	 * @Method Name : smsInsert
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("sample/sms/insert.do")
	public String smsInsert(HttpServletRequest request,LtsmsVO ltsmsVO,
            BindingResult bindingResult, Model model, SessionStatus status) throws Exception {
		
		// Server-Side Validation
    	beanValidator.validate(ltsmsVO, bindingResult);
    	
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("ltsmsVO", ltsmsVO);
    		request.setAttribute("message", "error");
			return "common/tools/smsinsert";
    	}
    	
    	LtsmsService.insertSmsSend(ltsmsVO);
        status.setComplete();
        
        String sucMessage 
        	= messageSource.getMessage("success.sms.send", new Object[] {ltsmsVO.getDESTCALLNO()}, "", Locale.getDefault());
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
	@RequestMapping("sample/sms/smsSendList.do")
	public String smsSendList(HttpServletRequest request,LtsmsVO ltsmsVO,
            BindingResult bindingResult, Model model, SessionStatus status) throws Exception {
		
		int pageRow = 3;
		int start = 0;
		
		int total = LtsmsService.selectSmsInfsCnt(ltsmsVO);
		
		int pageCnt = total/pageRow+1;
		
		int end = pageRow*1/*page number*/;
		
		ltsmsVO.setSTART(String.valueOf(start));
		ltsmsVO.setEND(String.valueOf(end));
		
		List<LtsmsVO> smsList = LtsmsService.selectSmsInfs(ltsmsVO);
		
		model.addAttribute("smsList", smsList);
		
		return "common/tools/smsSendList";
	}
	
}
