package com.lottemart.common.menu.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.lottemart.common.api.BaseError;
import com.lottemart.common.api.BaseResponse;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.menu.model.CommonMenuSearchVO;
import com.lottemart.common.menu.model.MenuVO;
import com.lottemart.common.menu.service.CommonMenuService;
import com.lottemart.common.util.JsonUtils;

@Controller("CommonMenuRestController")
public class CommonMenuRestController {
private static final Logger logger = LoggerFactory.getLogger(CommonMenuRestController.class);

	@Autowired
	private CommonMenuService commonMenuService;

	@RequestMapping(value="/common/menu/search.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchUsers(@ModelAttribute("search") CommonMenuSearchVO search, HttpServletRequest request) {
		LoginSession loginSession = LoginSession.getLoginSession(request);

		String adminId 		= loginSession.getAdminId();
		String sysDivnCd	= loginSession.getSysDivnCd();

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			search.setAdminId(adminId);
			search.setSysDivnCd(sysDivnCd);

			List<MenuVO> menus = commonMenuService.selectByName(search);
			int count = CollectionUtils.isEmpty (menus) ? 0 : menus.size();
			
			if(search.hasPaginationParameters()) {
				count = commonMenuService.countByName(search);
			}
			if(menus.size() == 1) {
				menus = null;
			}else {
				if(sysDivnCd.equals("02")) {
					for(int i=0; i < menus.size(); i++) {
						String menuId = menus.get(i).getMenuId();
						String menuNm = menus.get(i).getMenuNm();
						
						if( menuId.equals("---")) {
							menus.get(i).setMenuNm("---------------------");
						}
					}
				}
			}
			logger.debug("============CommonMenuRestController=============");
			logger.debug("============menus============="+menus);
			logger.debug("============CommonMenuRestController=============");
			result = JsonUtils.convertList2Json(menus, count, String.valueOf(search.getCurrentPage()));
			result.put("result", true);
		} catch (Exception e) {
			result.put("result", false);
			result.put("Message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/common/menu/search.do", method=RequestMethod.GET)
	public String search(@ModelAttribute("search") CommonMenuSearchVO search, HttpServletRequest request, ModelMap modelMap) {
		BaseResponse response = new BaseResponse();
		LoginSession loginSession = LoginSession.getLoginSession(request);

		String adminId		= loginSession.getAdminId();
		String sysDivnCd	= loginSession.getSysDivnCd();
		String searchWord	= "";

		// 숫자, 한/영 문자열 검색어 허용 (특수문자 제외)
		String pattern = "^[a-zA-Zㄱ-ㅎ가-힣0-9 ]*$";

		try {
			search.setAdminId(adminId);
			search.setSysDivnCd(sysDivnCd);
			searchWord = search.getQ().trim().replaceAll(" ", "|");
			search.setQ(searchWord);

			if (!Pattern.matches(pattern, search.getQ())) {
				throw new SQLException("정규 표현식에 일치하지 않는 특수문자 있음");
			} else {
				List<MenuVO> menus = commonMenuService.selectByName(search);
				if(menus.size() == 1) {
					menus = null;
				}else {
					if(sysDivnCd.equals("02")) {
						for(int i=0; i < menus.size(); i++) {
							String menuId = menus.get(i).getMenuId();
							String menuNm = menus.get(i).getMenuNm();
							
							if( menuId.equals("---")) {
								menus.get(i).setMenuNm("-----------------------");
							}
						}
					}
				}
				logger.debug("============CommonMenuRestController GET=============");
				logger.debug("============menus GET============="+menus);
				logger.debug("============CommonMenuRestController GET=============");
				
				response.putData("menus", menus);
				response.putData("count", CollectionUtils.isEmpty(menus) ? 0 : menus.size());
				
				ObjectMapper om = new ObjectMapper();
				modelMap.addAttribute("json", om.writeValueAsString(response));
			}
		} catch (Exception e) {
			response.setError(new BaseError(BaseError.ErrorCode.SERVER_ERROR, e.getLocalizedMessage()));
		}
		return "common/printJson";
	}

}