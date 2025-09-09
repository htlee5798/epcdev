package com.lottemart.epc.edi.weborder.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import lcn.module.common.views.AjaxJsonModelHelper;


import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0020Service;


/**
 * @Class Name : PEDMPRO0020Controller
 * @Description : MDer 협력업체 설정 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 22. 오후 1:33:50 dada
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0020Controller extends BaseController{



	private PEDMWEB0020Service pEDMWEB0020Service;

	//공통코드 조회 서비스
	//private PEDPCOM0001Service commService;

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0020Controller.class);

	@Autowired
	public void setpEDMWEB0020Service(PEDMWEB0020Service pEDMWEB0020Service) {
		this.pEDMWEB0020Service = pEDMWEB0020Service;
	}

//	@Autowired
//	public void setCommService(PEDPCOM0001Service commService) {
//		this.commService = commService;
//	}

	@Resource(name = "configurationService")
	private ConfigurationService config;


	/**
	 * Desc : MDer 협력업체 설정 init
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0020")
    public String initView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {



		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);



		return "edi/weborder/PEDMWEB0020";
	}

	@RequestMapping(value="/edi/weborder/PEDMWEB0020ModEmpl")
    public String initPopupView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		return "edi/weborder/PEDMWEB0020Popup";
	}



	/**
    * 담당자 조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0020SearchEmpl.do")
	public ModelAndView searchEmpPoolList(@RequestBody SearchWebOrder vo) throws Throwable
	{
		/**
		 * State : -1:Exception, 0:정상조회
		 */
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{

			List<EdiPoEmpPoolVO> list = pEDMWEB0020Service.selectEmpPoolList(vo);

			rtnData.put("empList", list);
			rtnData.put("state","0");
			rtnData.put("message","SUCCESS");
		}
		catch (Exception e) {
			rtnData.put("empList", null);
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}


	/**
    * 담당자별 협력업체 조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0020SearchEmplVen.do")
	public ModelAndView selectEmpVenList(@RequestBody SearchWebOrder vo) throws Throwable
	{
		/**
		 * State : -1:Exception, 0:정상조회
		 */
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{

			List<EdiPoEmpPoolVO> list = pEDMWEB0020Service.selectEmpVenList(vo);

			rtnData.put("venList", list);
			rtnData.put("state","0");
			rtnData.put("message","SUCCESS");
		}
		catch (Exception e) {
			rtnData.put("venList", null);
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
    * 담당자별 협력업체 목록 삭제
	* @param EdiPoEmpPoolVO
	* @return  AjaxJsonModelHelper[ HashMap<String, Object> ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0020DeleteEmplVenList.do")
	public ModelAndView deleteEmpVenList(@RequestBody SearchWebOrder vo) throws Throwable{
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");
		try{

			pEDMWEB0020Service.deleteEmpVenList(vo);								//선택된 협력사 삭제
			List<EdiPoEmpPoolVO> list = pEDMWEB0020Service.selectEmpVenList(vo);	//협력사 목록 제조회

			rtnData.put("venList", list);
			rtnData.put("state","0");
			rtnData.put("message","SUCCESS");

		}catch (Exception e) {
				rtnData.put("venList", null);
				rtnData.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
    * 담당자별 협력업체 목록 저장
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ HashMap<String, Object> ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0020AddEmplVen.do")
	public ModelAndView addEmpVenData(@RequestBody SearchWebOrder vo) throws Throwable{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{

			rtnData = pEDMWEB0020Service.insertEmpVenData(vo);	//선택된 협력사 저장

		}catch (Exception e) {

			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}



	/**
	    * 담당자 복사 / 이동
		* @param EdiPoEmpPoolVO
		* @return  AjaxJsonModelHelper[ HashMap<String, String> ]
		*/
		@RequestMapping("edi/weborder/PEDMWEB0020MoveAndCopyEmplPool.do")
		public ModelAndView copyEmpPoolData(@RequestBody EdiPoEmpPoolVO vo) throws Throwable{
			HashMap<String, String> rtnData = new HashMap<String, String>();
			rtnData.put("state", "-1");
			try{

				rtnData = pEDMWEB0020Service.insertEmpVenCopAndMove(vo);

			}catch (Exception e) {
				rtnData.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
			}
			return AjaxJsonModelHelper.create(rtnData);
		}


}
