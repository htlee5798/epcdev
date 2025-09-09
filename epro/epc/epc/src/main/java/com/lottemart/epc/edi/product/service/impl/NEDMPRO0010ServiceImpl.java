package com.lottemart.epc.edi.product.service.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.dao.NEDMPRO0010Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0020Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0010VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0010Service;

/**
 * @Class Name : NEDMPRO0010ServiceImpl
 * @Description : 신상품현황조회 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21 	SONG MIN KYO	최초생성
 * </pre>
 */
@Service("nEDMPRO0010Service")
public class NEDMPRO0010ServiceImpl implements NEDMPRO0010Service {

	@Resource(name="nEDMPRO0010Dao")
	private NEDMPRO0010Dao nEDMPRO0010Dao;

	@Resource(name="baseController")
	private BaseController baseController;

	@Resource(name="nEDMPRO0020Dao")
	private NEDMPRO0020Dao nEDMPRO0020Dao;

	/**
	 * 신상품 현황 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectNewProdFixList(Map<String, Object> paramMap) throws Exception {
		if (paramMap == null) {
			throw new IllegalArgumentException();
		}

		List<NEDMPRO0010VO> resultVO = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//-----온오프 상품
//		if ("0".equals(paramMap.get("srchOnOffDivnCd"))) {
			//resultVO = nEDMPRO0010Dao.selectNewProdFixList(paramMap); 2016.03.17 이전버전
			resultVO = nEDMPRO0010Dao.selectNewProdFixList_2(paramMap); //2016.03.17 이후버전

			//-----온라인 상품
//		} else {
//			resultVO = nEDMPRO0010Dao.selectNewProdOnlineFixList(paramMap);
//		}

		resultMap.put("resultList", resultVO);
		return resultMap;
	}

	/**
	 * 신상품 현황조회 POG 이미지 저장
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertNewProdImgPop(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new IllegalArgumentException();
		}
		//============
		// 작업자정보
		//============
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();

		String result			=	"FAIL";

		/**********************************************************************************************
		 * - 직매입이 아닌 상품만 MD FTP 로 전송하고 RFC CALL 한다. (2016.02.25일 이후로 이미지 RFC CALL, POG서버로 이미지 전송은 하지 않는다 )
		 * - 직매입 상품은 해당 디렉토리에 POG이미지 저장만 하고 온라인 BOS 시스템에서 직매입인 상품을 확정처리 할떄 이미지 전송, RFC CALL을 해준다.
		 *********************************************************************************************/
		/*FTPClient ftp = new FTPClient();

		ftp.connect( ConfigUtils.getString("edi.md.ftp.url") );  //AS-IS:10.52.1.163,	TO-BE:10.52.11.133
		ftp.login( ConfigUtils.getString("edi.md.ftp.userid"), ConfigUtils.getString("edi.md.ftp.passwd") );
		ftp.setFileType(FTP.BINARY_FILE_TYPE); */

		JSONObject obj	=	new JSONObject();

		String [] arrPgmId 			= 	paramMap.get("pgmId").toString().split("\\,");
		String [] arrtrdTypeDivnCd	=	paramMap.get("trdTypeDivnCd").toString().split("\\,");
		String [] arrProdImgId		=	paramMap.get("prodImgId").toString().split("\\,");
		String [] arrVariant		=	paramMap.get("variant").toString().split("\\,");
		String [] arrEntpCd			=	paramMap.get("entpCd").toString().split("\\,");
		String [] arrProdCd			=	paramMap.get("prodCd").toString().split(",");
		String [] arrSrcmkCd		=	paramMap.get("srcmkCd").toString().split("\\,");
		String [] arrFixSrcmkCd		=	paramMap.get("fixSrcmkCd").toString().split("\\,");

		List<HashMap>lsImgHmap 		= 	null;
		ArrayList<HashMap> arrHm	=	new ArrayList<HashMap>();

		for (int i = 0; i < arrPgmId.length; i++) {
			String uploadDir   	 	= 	baseController.makeSubFolderForOffline(arrPgmId[i]);

			MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
			Iterator fileIter = mptRequest.getFileNames();

			FileOutputStream frontImageStream =null;
			String newFileSource ="";
			String imgIDs= "";
			NEDMPRO0024VO paramVO = null;
			//HashMap	lsImgHmap = null;


			//-----직매입이 아닐경우
			if (!arrtrdTypeDivnCd[i].equals("1")) {
				while (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

					if(!mFile.isEmpty()) {
						//String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
						String postfix = mFile.getName();
						if(postfix.endsWith("front")) {
							 postfix = postfix.replaceAll("_front", ".1");
						}
						if(postfix.endsWith("side")) {
							 postfix = postfix.replaceAll("_side", ".2");
						}
						if(postfix.endsWith("top")) {
							 postfix = postfix.replaceAll("_top", ".3");
						}


						imgIDs = arrProdImgId[i] + arrVariant[i];

						newFileSource = uploadDir+"/"+imgIDs+postfix+".jpg";//+ext;
						
						try (FileOutputStream frontImageOutStream = new FileOutputStream(newFileSource)) {
                            FileCopyUtils.copy(mFile.getInputStream(), frontImageOutStream);
                        }
						//frontImageStream = new FileOutputStream(newFileSource);
						//FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
						//ftp.storeFile(imgIDs+postfix+".jpg", FileUtils.openInputStream(new File(newFileSource)));		//POG서버 이미지 전송
						//frontImageStream.close();
					 }
				}

				//----- 이미지  테이블에 저장
				paramVO = new NEDMPRO0024VO();
				paramVO.setPgmId(arrPgmId[i]);
				paramVO.setVariant(arrVariant[i]);
				paramVO.setProdImgId(imgIDs);
//				paramVO.setRegId(arrEntpCd[i]);
				///작업자정보 setting (로그인세션에서 추출)
				paramVO.setRegId(workId);
				paramVO.setEntpCd(arrEntpCd[i]);

				if (StringUtils.trimToEmpty(arrProdCd[i]).equals("")) {
					paramVO.setProdCd("");
				} else {
					paramVO.setProdCd(arrProdCd[i]);
				}

				if (StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {			//최초 상품등록시 입력한 판매코드가 없고 REG_SAP테이블에 상품확정 결과가 들어오기전에 POG이미지 등록시
					paramVO.setSrcmkCd("");
				} else if (!StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {	//최초 상품등록시에 입력한 판매코드는 있고 REG_SAP테이블에 상품확정 결과가 들어오기전에 POG이미지 등록시
					paramVO.setSrcmkCd(arrSrcmkCd[i]);
				} else if (StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && !StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {	//최초 상품등록시에 입력한 판매코드가 존재하고 REG_SAP테이블에 상품확정 결과가 들어오고 난 이후의 POG이미지 등록시에는 REG_SAP테이블의 판매코드를 사용
					paramVO.setSrcmkCd(arrFixSrcmkCd[i]);
				}

				paramVO.setCfmFg(StringUtils.trimToEmpty((String)paramMap.get("cfmFg")));	// [0:등록, 1:심사중.......jsp에서 1로 던짐 이미지 등록이 심사중으로 보여지기 떄문..]

				//logger.debug("variant----->" + postfix.substring(0, 14));
				//logger.debug("ProdImgId----->" + postfix.substring(11, 14));
				nEDMPRO0020Dao.insertSaleImg(paramVO);

				//----- RFC 넘길 데이터 조회
				paramMap.put("pgmId", 	arrPgmId[i]);
				paramMap.put("variant", arrVariant[i]);
				lsImgHmap	=	nEDMPRO0010Dao.selectNewProdImgInfoToRFC(paramMap);		// RFC CALL 넘길 데이터 조회

				//----- 조회된 데이터 List에 담는다.(직매입이 아닌 상품만 RFC 전송을 하므로 List에 담아두고 사용)
				Iterator itr = lsImgHmap.iterator();
				while(itr.hasNext()) {
					arrHm.add((HashMap) itr.next());
				}

			//----- 직매입인 상품
			}else {
				while (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

					if(!mFile.isEmpty()) {
						//String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
						String postfix = mFile.getName();
						if(postfix.endsWith("front")) {
							 postfix = postfix.replaceAll("_front", ".1");
						}
						if(postfix.endsWith("side")) {
							 postfix = postfix.replaceAll("_side", ".2");
						}
						if(postfix.endsWith("top")) {
							 postfix = postfix.replaceAll("_top", ".3");
						}


						imgIDs = arrProdImgId[i] + arrVariant[i];

						newFileSource = uploadDir+"/"+imgIDs+postfix+".jpg";//+ext;
						
						try (FileOutputStream frontImageOutStream = new FileOutputStream(newFileSource)) {
						    FileCopyUtils.copy(mFile.getInputStream(), frontImageOutStream);
						}
						//frontImageStream = new FileOutputStream(newFileSource);
						//FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
						//frontImageStream.close();
					 }
				}

				//----- 이미지  테이블에 저장
				paramVO = new NEDMPRO0024VO();
				paramVO.setPgmId(arrPgmId[i]);
				paramVO.setVariant(arrVariant[i]);
				paramVO.setProdImgId(imgIDs);
//				paramVO.setRegId(arrEntpCd[i]);
				///작업자정보 setting (로그인세션에서 추출)
				paramVO.setRegId(workId);
				paramVO.setEntpCd(arrEntpCd[i]);
				paramVO.setProdCd(arrProdCd[i]);

				if (StringUtils.trimToEmpty(arrProdCd[i]).equals("")) {
					paramVO.setProdCd("");
				} else {
					paramVO.setProdCd(arrProdCd[i]);
				}

				if (StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {			//최초 상품등록시 입력한 판매코드가 없고 REG_SAP테이블에 상품확정 결과가 들어오기전에 POG이미지 등록시
					paramVO.setSrcmkCd("");
				} else if (!StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {	//최초 상품등록시에 입력한 판매코드는 있고 REG_SAP테이블에 상품확정 결과가 들어오기전에 POG이미지 등록시
					paramVO.setSrcmkCd(arrSrcmkCd[i]);
				} else if (StringUtils.trimToEmpty(arrSrcmkCd[i]).equals("") && !StringUtils.trimToEmpty(arrFixSrcmkCd[i]).equals("")) {	//최초 상품등록시에 입력한 판매코드가 존재하고 REG_SAP테이블에 상품확정 결과가 들어오고 난 이후의 POG이미지 등록시에는 REG_SAP테이블의 판매코드를 사용
					paramVO.setSrcmkCd(arrFixSrcmkCd[i]);
				}

				paramVO.setCfmFg(StringUtils.trimToEmpty((String)paramMap.get("cfmFg")));

				//logger.debug("variant----->" + postfix.substring(0, 14));
				//logger.debug("ProdImgId----->" + postfix.substring(11, 14));
				nEDMPRO0020Dao.insertSaleImg(paramVO);
			}
		}

		//----- ftp disconnect
		//ftp.disconnect();


		/************************************************** RFC CALL [2016.02.25 이후로 이미지 RFC 요청은 하지 않는다] ***********************************************/
		//----- 직매입이 아닌 상품만 RFC 전송을 하므로 List에 담아둔 RFC로 전송할 데이터가 있을떄만
		/*if (arrHm.size() > 0) {
			HashMap	 		reqCommonMap	=	new HashMap();													// RFC 응답
			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS", "");
			reqCommonMap.put("ZPOROWS", "");
			reqCommonMap.put("ZPODATE", "");
			reqCommonMap.put("ZPOTIME", "");

			obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....
			obj.put("BCD_IMG", arrHm);				//

			logger.debug("obj.toString=" + obj.toString());

			//----- 1.RFC CALL("proxyNm", String, String);
			Map<String, Object>	rfcMap	=	rfcCommonService.rfcCall((String)paramMap.get("proxyNm"), obj.toString(), (String)paramMap.get("entpCd"));

			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

			//성공이 아니면 실패로 간주한다.
			if (!rtnResult.equals("S")) {
				result	=	"RFC_FAIL";
				return result;
			}
		}*/

		result	=	"SUCCESS";
		return result;
	}
}
