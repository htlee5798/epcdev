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
import com.lottemart.epc.edi.weborder.model.NEDMWEB0240VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0240Service;


/**
 * @Class Name : NEDMWEB0240Controller
 * @Description : 협력업체설정 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0240Controller extends BaseController{



	private NEDMWEB0240Service nEDMWEB0240Service;

	//공통코드 조회 서비스
//	private PEDPCOM0001Service commService;

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0240Controller.class);

	@Autowired
	public void setnEDMWEB0240Service(NEDMWEB0240Service nEDMWEB0240Service) {
		this.nEDMWEB0240Service = nEDMWEB0240Service;
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
	@RequestMapping(value="/edi/weborder/NEDMWEB0240.do")
    public String initView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {



		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);



		return "edi/weborder/NEDMWEB0240";
	}

	@RequestMapping(value="/edi/weborder/NEDMWEB0240ModEmpl.do")
    public String initPopupView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		return "edi/weborder/NEDMWEB0240Popup";
	}



	/**
    * 담당자 조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/NEDMWEB0240SearchEmpl.json")
	public ModelAndView searchEmpPoolList(@RequestBody NEDMWEB0240VO vo) throws Throwable
	{
		/**
		 * State : -1:Exception, 0:정상조회
		 */
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{

			List<NEDMWEB0240VO> list = nEDMWEB0240Service.selectEmpPoolList(vo);

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
	@RequestMapping("edi/weborder/NEDMWEB0240SearchEmplVen.json")
	public ModelAndView selectEmpVenList(@RequestBody NEDMWEB0240VO vo) throws Throwable
	{
		/**
		 * State : -1:Exception, 0:정상조회
		 */
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{

			List<NEDMWEB0240VO> list = nEDMWEB0240Service.selectEmpVenList(vo);

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
	@RequestMapping("edi/weborder/NEDMWEB0240DeleteEmplVenList.json")
	public ModelAndView deleteEmpVenList(@RequestBody NEDMWEB0240VO vo) throws Throwable{
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");
		try{

			nEDMWEB0240Service.deleteEmpVenList(vo);								//선택된 협력사 삭제
			List<NEDMWEB0240VO> list = nEDMWEB0240Service.selectEmpVenList(vo);	//협력사 목록 제조회

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
	@RequestMapping("edi/weborder/NEDMWEB0240AddEmplVen.json")
	public ModelAndView addEmpVenData(@RequestBody NEDMWEB0240VO vo) throws Throwable{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{

			rtnData = nEDMWEB0240Service.insertEmpVenData(vo);	//선택된 협력사 저장

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
	@RequestMapping("edi/weborder/NEDMWEB0240MoveAndCopyEmplPool.json")
	public ModelAndView copyEmpPoolData(@RequestBody NEDMWEB0240VO vo) throws Throwable{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{

			rtnData = nEDMWEB0240Service.insertEmpVenCopAndMove(vo);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}


}
