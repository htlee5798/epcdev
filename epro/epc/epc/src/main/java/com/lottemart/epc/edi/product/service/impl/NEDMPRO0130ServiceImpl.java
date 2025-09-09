package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0130Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0130VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0130Service;

@Service("nedmpro0130Service")
public class NEDMPRO0130ServiceImpl implements NEDMPRO0130Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0130ServiceImpl.class);

	@Autowired
	private RFCCommonService rfcCommonService;

	@Autowired
	private NEDMPRO0130Dao nedmpro0130Dao;

	@Resource(name="baseController")
	private BaseController baseController;

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectProductImageCount(NEDMPRO0130VO vo) throws Exception {
		if (vo == null) {
			throw new IllegalArgumentException();
		}

		int resultCnt = 0;

		/*if (StringUtils.trimToEmpty(vo.getSrchProdDivnCode()).equals("1")) {	//규격
			resultCnt = nedmpro0130Dao.selectStandardProductImageCount(vo);
		}else {																	//패션
			resultCnt = nedmpro0130Dao.selectFashionProductImageCount(vo);
		}*/


		resultCnt = nedmpro0130Dao.selectFashionProductImageCount(vo);

		return resultCnt;
	}

	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0130VO> selectProductImageList(NEDMPRO0130VO vo) throws Exception {
		if (vo == null) {
			throw new IllegalArgumentException();
		}

		/*List<NEDMPRO0130VO>	resultList	=	null;
		if (StringUtils.trimToEmpty(vo.getSrchProdDivnCode()).equals("1")) {
			resultList = nedmpro0130Dao.selectStandardProductImageList(vo);		//규격
		}else {
			resultList = nedmpro0130Dao.selectFashionProductImageList(vo);		//패션
		}*/

		List<NEDMPRO0130VO> resultList = nedmpro0130Dao.selectFashionProductImageList(vo);

		return resultList;
	}

	/**
	 * 이미지 사이즈 변경 예약
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertMDSizeReserv(NEDMPRO0130VO vo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		vo.setRegId(workId);
		vo.setModId(workId);
		
		int iChk = nedmpro0130Dao.selectMDSellCodeinReserv(vo);
		if (iChk > 0) {
			nedmpro0130Dao.updateMDSizeInReserv(vo);
		} else {
			nedmpro0130Dao.insertMDSizeReserv(vo);
		}

		//----- RFC Call 처리
		List<LinkedHashMap> rfcList = nedmpro0130Dao.selectRfcCallData(vo);

		JSONObject jo = new JSONObject();

		jo.put("TPC_SALE_MST_RSERV", rfcList);

		LinkedHashMap reqCommonLhm = new LinkedHashMap();
		reqCommonLhm.put("ZPOSOURCE", "");
		reqCommonLhm.put("ZPOTARGET", "");
		reqCommonLhm.put("ZPONUMS", "");
		reqCommonLhm.put("ZPOROWS", "");
		reqCommonLhm.put("ZPODATE", "");
		reqCommonLhm.put("ZPOTIME", "");

		jo.put("REQCOMMON", reqCommonLhm);

		logger.debug("----->" + jo.toString());

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		if (rfcList.size() > 0) {
			resultMap = rfcCommonService.rfcCall(vo.getProxyNm(), jo.toString(), "");
		}


		//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj 			= new JSONObject(resultMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
		String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		//성공이 아니면 실패로 간주한다.
		if (!rtnResult.equals("S")) {
			//result = "FAIL";
			//return result;
			throw new IllegalArgumentException();
		}

		resultMap.put("msg", "SUCCESS");

		return resultMap;

	}

	/**
	 * 이미지 저장
	 */
	public String insertSaleImgAllApply(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new IllegalArgumentException();
		}
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		
		
		String result 		= 	"FAIL";
		String uploadDir	=	baseController.makeSubFolderForOffline(StringUtils.trimToEmpty((String) paramMap.get("pgmId")));

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		Iterator fileIter = mptRequest.getFileNames();

		FTPClient ftp = new FTPClient();

		String currentTime=DateFormatUtils.format(new Date(), "yyyyMMddHHmmss", Locale.KOREA);

		try{
			//----- 1.이미지 업로드 & MD FTP 전송 [2016.02.25 이후로 POG서버로 이미지 전송은 하지 않는다.]
			/* ftp.connect( ConfigUtils.getString("edi.md.ftp.url") );  //AS-IS:10.52.1.163,	to-be:10.52.11.133
			 ftp.login( ConfigUtils.getString("edi.md.ftp.userid"), ConfigUtils.getString("edi.md.ftp.passwd") );
			 ftp.setFileType(FTP.BINARY_FILE_TYPE); */

			FileOutputStream frontImageStream =null;

			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

				if(!mFile.isEmpty()) {
					//String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
					String postfix = mFile.getName();

					// 이미지아이디가 없는경우----------------------------
					if(StringUtils.trimToEmpty((String)paramMap.get("imgNm")).equals("")  || StringUtils.trimToEmpty((String)paramMap.get("imgNm")).length() <= 0) {
						postfix =  currentTime+postfix;
					}
					//------------------------------------------------

					if(postfix.endsWith("front")) {
						postfix = postfix.replaceAll("_front", ".1");
					}
					if(postfix.endsWith("side")) {
						postfix = postfix.replaceAll("_side", ".2");
					}
					if(postfix.endsWith("top")) {
						postfix = postfix.replaceAll("_top", ".3");
					}
					if(postfix.endsWith("back")) {
						postfix = postfix.replaceAll("_back", ".4");
					}

					String newFileSource = uploadDir+"/"+postfix+".jpg";//+ext;

					frontImageStream = new FileOutputStream(newFileSource);
					FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					//ftp.storeFile(postfix+".jpg", FileUtils.openInputStream(new File(newFileSource)));

					frontImageStream.close();

				}

			}
		} catch(Exception e) {
			//ftp.disconnect();
			logger.error("이미지사이즈 관리 POG이미지 등록 Exception ::::::::::::::::::::::" + e.toString());
		} finally {
			ftp.disconnect();
		}

		if(StringUtils.trimToEmpty((String)paramMap.get("imgNm")).equals("") || StringUtils.trimToEmpty((String)paramMap.get("imgNm")).length() <=0) {
			paramMap.put("imgNm", currentTime);
		}


		//----- 2.이미지 삭제 이후 저장(DB)
		nedmpro0130Dao.deleteProdPogImgInfo(paramMap);

		NEDMPRO0024VO nEDMPRO0024VO	=	new NEDMPRO0024VO();

		nEDMPRO0024VO.setPgmId(StringUtils.trimToEmpty((String)paramMap.get("pgmId")));
		nEDMPRO0024VO.setVariant(StringUtils.trimToEmpty((String)paramMap.get("variant")));
		nEDMPRO0024VO.setProdImgId(StringUtils.trimToEmpty((String)paramMap.get("imgNm")));
		nEDMPRO0024VO.setEntpCd(StringUtils.trimToEmpty((String)paramMap.get("venCd")));
		nEDMPRO0024VO.setCfmFg(StringUtils.trimToEmpty((String)paramMap.get("cfmFg")));
		nEDMPRO0024VO.setProdCd(StringUtils.trimToEmpty((String)paramMap.get("prodCd")));
		nEDMPRO0024VO.setSrcmkCd(StringUtils.trimToEmpty((String)paramMap.get("srcmkCd")));
		//작업자정보 setting (로그인세션에서 추출)
		nEDMPRO0024VO.setRegId(workId);

		//----- 3.이미지 DB 저장
		nedmpro0130Dao.insertProdPogImgInfo(nEDMPRO0024VO);



		//----- 4. 이미지 RFC 전송 [2016.02.25 이후로 이미지 RFC 요청은 하지 않는다.]
		List<HashMap>	lsHmap			=	nedmpro0130Dao.selectProdImgInfoToRFC(paramMap);
		HashMap	 		reqCommonMap	=	new HashMap();												// RFC 응답

		//---- RFC로 전송할 데이터가 존재할 경우
		/*if (lsHmap.size() > 0) {

			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS", "");
			reqCommonMap.put("ZPOROWS", "");
			reqCommonMap.put("ZPODATE", "");
			reqCommonMap.put("ZPOTIME", "");

			JSONObject obj	=	new JSONObject();

			obj.put("BCD_IMG", 	lsHmap);			// HashMap에 담긴 데이터 JSONObject로 ...
			obj.put("REQCOMMON", reqCommonMap);				// RFC 응답 HashMap JsonObject로....

			logger.debug("obj.toString=" + obj.toString());

			//----- 1.RFC CALL("proxyNm", String, String);
			Map<String, Object> rfcMap;

			rfcMap = rfcCommonService.rfcCall(StringUtils.trimToEmpty((String)paramMap.get("proxyNm")), obj.toString(), StringUtils.trimToEmpty((String)paramMap.get("venCd")));

			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

			//성공이 아니면 실패로 간주한다.
			if (!rtnResult.equals("S")) {
				//result = "FAIL";
				//return result;
				throw new Exception();
			}
		}	*/

		result = "SUCCESS";

		return result;
	}

	public HashMap selectWgtInfo(Map<String, String> paramMap) throws Exception {
		HashMap resultMap = null;
		HashMap resInfoMap = null;

		try {
			String seqRes = "0";
			String seq = nedmpro0130Dao.selectWgtSeq(paramMap);
			paramMap.put("seq", seq);

			resultMap = nedmpro0130Dao.selectWgtInfo(paramMap);
			seqRes = nedmpro0130Dao.selectWgtSeqRes(paramMap);

			// 1.요청한 적 없거나, 2.응답 데이터만 있는 경우 SALE_MST 데이터 참고
			if (resultMap == null ||
					(seqRes != null && !seqRes.equals(seq))
				) {
				resultMap = nedmpro0130Dao.selectMstWgtInfo(paramMap);
			}

			if (seqRes != null) {
				resultMap.put("SEQ", seqRes);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			resultMap = null;
		}

		return resultMap;
	}

	public HashMap selectWgtResInfo(Map<String, String> paramMap) throws Exception {

		HashMap<String, String> resMap = null;

		try {
			resMap = nedmpro0130Dao.selectWgtResInfo(paramMap);
		} catch(Exception e) {
			logger.error(e.getMessage());
			resMap = null;
		}

		return resMap;
	}


	public JSONObject makeRfcParams(Map<String, String> paramMap) throws Exception {
		JSONObject rfcParams = new JSONObject();
		HashMap<String, String> rfcParamMap = new HashMap<>();

		rfcParamMap.put("SEQ", paramMap.get("seq"));
		rfcParamMap.put("PROD_CD", paramMap.get("prodCd"));
		rfcParamMap.put("SRCMK_CD", paramMap.get("srcmkCd"));
		rfcParamMap.put("APPLY_DY", paramMap.get("applyDy"));
		rfcParamMap.put("OLD_G_WGT", paramMap.get("oldGWgt"));
		rfcParamMap.put("OLD_N_WGT", paramMap.get("oldNWgt"));
		rfcParamMap.put("OLD_WGT_UNIT", paramMap.get("oldWgtUnit"));
		rfcParamMap.put("CHG_G_WGT", paramMap.get("newGWgt"));
		rfcParamMap.put("CHG_N_WGT", paramMap.get("newNWgt"));
		rfcParamMap.put("CHG_WGT_UNIT", paramMap.get("newWgtUnit"));

		rfcParams.put("TPC_WGT_REQ", rfcParamMap);

		LinkedHashMap reqCommonLhm = new LinkedHashMap();
		reqCommonLhm.put("ZPOSOURCE", "");
		reqCommonLhm.put("ZPOTARGET", "");
		reqCommonLhm.put("ZPONUMS", "");
		reqCommonLhm.put("ZPOROWS", "");
		reqCommonLhm.put("ZPODATE", "");
		reqCommonLhm.put("ZPOTIME", "");

		rfcParams.put("REQCOMMON", reqCommonLhm);

		return rfcParams;
	}

	public String getRtnRfcResult(Map<String, Object> resultMap) throws Exception {
		JSONObject mapObj = new JSONObject(resultMap.toString());
		JSONObject resultObj = mapObj.getJSONObject("result");
		String rtnResult = StringUtils.trimToEmpty(resultObj.getString("MSGTYP"));

		return rtnResult;
	}

	public String requestWgtRfcCall(Map<String, String> paramMap) throws Exception {
		String rtnCode = "1";

		try {
			JSONObject rfcParams = makeRfcParams(paramMap);

			Map<String, Object> resultMap = new HashMap<String, Object>();

			if (rfcParams.get("TPC_WGT_REQ") != null) {
				resultMap = rfcCommonService.rfcCall(paramMap.get("proxyNm"), rfcParams.toString(), "");
			}

			String rtnResult = getRtnRfcResult(resultMap);

			//성공이 아니면 실패로 간주한다.
			if (!rtnResult.equals("S")) {
				throw new IllegalArgumentException();
			}

		} catch(Exception e) {;
			logger.error(e.getMessage());
			rtnCode = "-3";
		} finally {

		}

		return rtnCode;
	}

	public HashMap<String, String> refineWgtParam(NEDMPRO0130VO nedmpro0130VO) throws Exception {
		HashMap<String, String> refinedParam = new HashMap<>();

		String wgtSeq = nedmpro0130VO.getWgtSeq();

		String prodCd = nedmpro0130VO.getProdCd();
		String srcmkCd = nedmpro0130VO.getSrcmkCd();
//		String regId = nedmpro0130VO.getVenCd();
		String regId = nedmpro0130VO.getRegId();
		String venCd = nedmpro0130VO.getVenCd();

		String wg = nedmpro0130VO.getWg();
		String pclWg = nedmpro0130VO.getPclWg();
		String pclWgUnit = nedmpro0130VO.getPclWgUnit();
		String reqGWgt = nedmpro0130VO.getReqGWgt();
		String reqNWgt = nedmpro0130VO.getReqNWgt();
		String reqWgtUnit = nedmpro0130VO.getReqWgtUnit();
		String proxyNm = nedmpro0130VO.getProxyNm();
		String applyDy = nedmpro0130VO.getApplyDy();

		if (wgtSeq == null ||
				wg == null ||
				pclWg == null||
				pclWgUnit == null ||
				reqGWgt == null ||
				reqNWgt == null ||
				reqWgtUnit == null ||
				proxyNm == null ||
				applyDy == null ||
//				regId == null
				venCd == null
			) {
			return null;
		}

		refinedParam.put("seq", wgtSeq);
		refinedParam.put("prodCd", prodCd);
		refinedParam.put("srcmkCd", srcmkCd);
		refinedParam.put("oldGWgt", wg);
		refinedParam.put("oldNWgt", pclWg);
		refinedParam.put("oldWgtUnit", pclWgUnit);
		refinedParam.put("newGWgt", reqGWgt);
		refinedParam.put("newNWgt", reqNWgt);
		refinedParam.put("newWgtUnit", reqWgtUnit);
		refinedParam.put("proxyNm", proxyNm);
		refinedParam.put("applyDy", applyDy);
		refinedParam.put("regId", regId);
		refinedParam.put("venCd", venCd);

		return refinedParam;
	}
	// 에러코드(-1: 파라미터 없음, -2: 중복시퀀스, -3: RFC 호출 에러, -4:전송이력 저장 에러)
	public String submitWgtInfo(NEDMPRO0130VO nedmpro0130VO) throws Exception {
		String rtnCode = "0";
		HashMap<String, String> paramMap = null;
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nedmpro0130VO.setRegId(workId);

		try {
			paramMap = refineWgtParam(nedmpro0130VO);
			if (paramMap == null) {
				rtnCode = "-1";
				throw new Exception();
			}

			String seq = nedmpro0130Dao.selectWgtSeq(paramMap);
			if (!paramMap.get("seq").equals("0") &&
					paramMap.get("seq").equals(seq)) {
				rtnCode = "-2";
				throw new Exception();
			}

			rtnCode = requestWgtRfcCall(paramMap);

			if (rtnCode.equals("1")) {
				nedmpro0130Dao.insertWgtInfo(paramMap);
			}
		} catch(Exception e) {
			if (!rtnCode.equals("-1") || !rtnCode.equals("-2")) {
				nedmpro0130Dao.deleteWgtInfo(nedmpro0130VO);
			}
			// -4 : 전송 이력 남기고 에러
			if (rtnCode.equals("1")) {
				rtnCode = "-4";
			}
		}

		return rtnCode;
	}
	
	/**
	 * 세션에서 작업자 아이디 추출
	 * @return String
	 */
	private String getLoginWorkId() {
		String workId = "";
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
			workId = epcLoginVO.getLoginWorkId();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return workId;
	}
}
