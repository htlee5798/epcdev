package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMKOR0010VO;
import com.lottemart.epc.edi.product.service.NEDMKOR0010Service;

/**
 * @Class Name : NEDMKOR0010Controller
 * @Description : 코리안넷 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21 	SONG MIN KYO	최초생성
 * </pre>
 */

@Controller
public class NEDMKOR0010Controller extends BaseController {

	@Resource(name="nEDMKOR0010Service")
	private NEDMKOR0010Service nEDMKOR0010Service;

	/**
	 * 초기화
	 * @param vo
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMKOR0010.do")
	public String NEDMKOR0010Init(NEDMKOR0010VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		return "edi/koreanNetTest";
	}

	/**
	 * 코리안넷 이미지 가져오기(JSON 호출방식)
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMKOR0010CallJson.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> NEDMKOR0010CallJson(@RequestBody NEDMKOR0010VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (vo == null || request == null) {
			throw new IllegalArgumentException();
		}

		//---------- 선택한 협력업체코드의 사업자번호 가져오기
		String bmanNo = nEDMKOR0010Service.selectBmanNo(vo.getEntpCd());
		if (bmanNo == null || bmanNo.equals("")) {
			// 해당하는 사업자번호가 없으면 return
			resultMap.put("retCd", "-1");
			resultMap.put("retMsg", "협력업체의 사업자번호를 가져올 수 없습니다.");

			return resultMap;
		}

		vo.setBmanNo(bmanNo);
		//--------------------------------------------------

		//---------- 인증조회 ------------------------------
		String authToken = nEDMKOR0010Service.selectAuthToken();

		if (authToken != null && !authToken.equals("")) {	// 발급된 인증토큰이 있으면
			vo.setAuthToken(authToken);
		} else {	// 발급된 인증토큰이 없으면
			//---------- 코리안넷 인증 ------------------------------
			Map<String, Object> authMap = new HashMap<String, Object>();
			authMap = nEDMKOR0010Service.getAuthToken();

			//logger.debug("authMap retCd----->" + authMap.get("retCd"));
			//logger.debug("authMap retMsg----->" + authMap.get("retMsg"));
			if (authMap.get("retCd").equals("200")) {
				vo.setAuthToken((String) authMap.get("authToken"));

				//---------- 인증토큰정보 저장 ----------
				HashMap authHm = new HashMap();
				authHm.put("authStartDt", authMap.get("authStartDt"));
				authHm.put("authEndDt", authMap.get("authEndDt"));
				authHm.put("authToken", authMap.get("authToken"));

				nEDMKOR0010Service.insertAuthToken(authHm);

				// 코리안넷에서 인증토큰 적용이 되게끔 2초 대기
				Thread.sleep(2000);
				//------------------------------
			} else {
				return authMap;
			}
			/*String authToken = nEDMKOR0010Service.getAuthToken();
			if (authToken == null || authToken.equals("")) {
				// 인증오류 이므로 return
				resultMap.put("retCd", "-1");
				resultMap.put("retMsg", "인증실패");

				return resultMap;
			} else {
				vo.setAuthToken(authToken);
			}*/
			//--------------------------------------------------
		}
		//--------------------------------------------------


		//---------- 상품이미지 압축파일 정보 ------------------------------
		Map<String, Object> imgMap = new HashMap<String, Object>();
		imgMap = nEDMKOR0010Service.getDownloadZipFileInfo(vo);

		//logger.debug("imgMap retCd----->" + imgMap.get("retCd"));
		//logger.debug("imgMap retMsg----->" + imgMap.get("retMsg"));

		if (imgMap.get("retCd").equals("200")) {
			vo.setRealImgPath((String) imgMap.get("retMsg"));
		} else {
			return imgMap;
		}
		/*String downloadURL = nEDMKOR0010Service.getDownloadZipFileInfo(vo);
		if (downloadURL == null || downloadURL.equals("")) {
			// 다운로드할 파일이 없으므로 return
			resultMap.put("retCd", "-1");
			resultMap.put("retMsg", "상품이미지 정보가져오기 실패");

			return resultMap;
		} else {
			vo.setRealImgPath(downloadURL);
		}*/
		//--------------------------------------------------

		//---------- 상품이미지 압축파일 다운로드 ------------------------------
		String retZip = nEDMKOR0010Service.getDownloadZipFile(vo);
		if (!retZip.equals("success")) {
			resultMap.put("retCd", "-1");
			resultMap.put("retMsg", "상품이미지 가져오기 실패");

			return resultMap;
		}
		//--------------------------------------------------

		//---------- 다운받은 압축파일 압축해제 ------------------------------
		String retUnZip = nEDMKOR0010Service.unZipFile(vo);
		if (!retUnZip.equals("success")) {
			resultMap.put("retCd", "-1");
			resultMap.put("retMsg", "상품이미지 압축해제 실패");

			return resultMap;
		}
		//--------------------------------------------------

		//---------- 다운받은 압축파일 이동 ------------------------------
		String retMove = nEDMKOR0010Service.moveFile(vo);
		if (!retUnZip.equals("success")) {
			resultMap.put("retCd", "-1");
			resultMap.put("retMsg", "상품이미지 복사 실패");

			return resultMap;
		}
		//--------------------------------------------------

		resultMap.put("retCd", "200");
		resultMap.put("retMsg", "success");

		return resultMap;
	}

	/**
	 * 코리안넷 이미지 가져오기
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/NEDMKOR0010Call.do")
	public String NEDMKOR0010Call(NEDMKOR0010VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}

		//---------- 선택한 협력업체코드의 사업자번호 가져오기
		String bmanNo = nEDMKOR0010Service.selectBmanNo(vo.getEntpCd());
		if (bmanNo == null || bmanNo.equals("")) {
			// 해당하는 사업자번호가 없으면 return
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "협력업체의 사업자번호를 가져올 수 없습니다.");
			return "edi/koreanNetTest";
		}

		vo.setBmanNo(bmanNo);
		//--------------------------------------------------

		//---------- 코리안넷 인증 ------------------------------
		Map<String, Object> authMap = new HashMap<String, Object>();
		authMap = nEDMKOR0010Service.getAuthToken();

		if (authMap.get("retCd").equals("200")) {
			vo.setAuthToken((String) authMap.get("retMsg"));
		} else {
			// 인증오류 이므로 return
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "인증실패");
			return "edi/koreanNetTest";
		}
		/*String authToken = nEDMKOR0010Service.getAuthToken();
		if (authToken == null || authToken.equals("")) {
			// 인증오류 이므로 return
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "인증실패");
			return "edi/koreanNetTest";
		} else {
			vo.setAuthToken(authToken);
		}*/
		//--------------------------------------------------

		// 코리안넷에서 인증토큰 적용이 되게끔 2초 대기
		Thread.sleep(2000);

		//---------- 상품이미지 압축파일 정보 ------------------------------
		Map<String, Object> imgMap = new HashMap<String, Object>();
		imgMap = nEDMKOR0010Service.getDownloadZipFileInfo(vo);

		if (authMap.get("retCd").equals("200")) {
			vo.setRealImgPath((String) authMap.get("retMsg"));
		} else {
			// 인증오류 이므로 return
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "상품이미지 정보가져오기 실패");
			return "edi/koreanNetTest";
		}

		/*String downloadURL = nEDMKOR0010Service.getDownloadZipFileInfo(vo);
		if (downloadURL == null || downloadURL.equals("")) {
			// 다운로드할 파일이 없으므로 return
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "상품이미지 정보가져오기 실패");
			return "edi/koreanNetTest";
		} else {
			vo.setRealImgPath(downloadURL);
		}*/
		//--------------------------------------------------

		//---------- 상품이미지 압축파일 정보 ------------------------------
		String retZip = nEDMKOR0010Service.getDownloadZipFile(vo);
		if (!retZip.equals("success")) {
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "상품이미지 가져오기 실패");
			return "edi/koreanNetTest";
		}
		//--------------------------------------------------

		//---------- 다운받은 압축파일 압축해제 ------------------------------
		String retUnZip = nEDMKOR0010Service.unZipFile(vo);
		if (!retUnZip.equals("success")) {
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "상품이미지 압축해제 실패");
			return "edi/koreanNetTest";
		}
		//--------------------------------------------------

		//---------- 다운받은 압축파일 이동 ------------------------------
		String retMove = nEDMKOR0010Service.moveFile(vo);
		if (!retUnZip.equals("success")) {
			model.addAttribute("retCd", "-1");
			model.addAttribute("retMsg", "상품이미지 복사 실패");
			return "edi/koreanNetTest";
		}
		//--------------------------------------------------

		model.addAttribute("retCd", "0");
		model.addAttribute("retMsg", "success");

		return "edi/koreanNetTest";
	}

}
