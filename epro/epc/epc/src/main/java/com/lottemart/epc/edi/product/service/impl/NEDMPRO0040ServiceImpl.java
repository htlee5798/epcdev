package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.util.BosOpenApiCaller;
import com.lottemart.epc.common.util.EpcSftpUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
//import com.lottemart.epc.edi.product.dao.CommonProductDao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0040Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0043VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0044VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0045VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0040Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;
import com.lottemart.epc.system.model.PSCMSYS0003VO;

//import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0040ServiceImpl
 * @Description : 임시보관함 SERVICE Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.27 	SONG MIN KYO	최초생성
 * </pre>
 */
@Service("nEDMPRO0040Service")
public class NEDMPRO0040ServiceImpl extends BaseController implements NEDMPRO0040Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0040ServiceImpl.class);
	
	@Resource(name="nEDMPRO0040Dao")
	private NEDMPRO0040Dao nEDMPRO0040Dao;
	
	// @Resource(name="commonProductDao")
	// private CommonProductDao commonProductDao;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	// @Resource(name = "configurationService")
	// private ConfigurationService config;
	
	// 신상품 등록 관련 서비스
	@Resource(name = "ediProductService")
	private PEDMPRO000Service ediProductService;
	
	@Resource(name="imageFileMngService")
	private ImageFileMngService imageFileMngService;
	
	@Autowired
	BosOpenApiService bosOpenApiService;
	
	@Autowired
	BosOpenApiCaller bosOpenApiCaller;
	
	@Autowired
	EpcSftpUtil epcSftpUtil;
	
	/**
	 * 임시보관함 LIST
	 * @param nEDMPRO0040VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0040VO> selectImsiProductList(Map<String, Object> paramMap) throws Exception {
		// return nEDMPRO0040Dao.selectImsiProductList(paramMap);

		List<NEDMPRO0040VO> rsList = nEDMPRO0040Dao.selectImsiProductList(paramMap);

		// 대표이미지 여부 체크
		/*int prodDescCnt = 0;

		if (rsList.size() > 0) {
			for (int j = 0; j < rsList.size(); j++) {
				prodDescCnt = nEDMPRO0040Dao.selectProductDescChk(rsList.get(j).getPgmId());

				if (prodDescCnt > 0) {
					rsList.get(j).setProdDescYn("Y");
				} else {
					rsList.get(j).setProdDescYn("N");
				}
			}
		}*/

		return rsList;
	}

	/**
	 * 상품의 변형속성 리스트 조회
	 */
	public List<NEDMPRO0042VO> selectNewSrcmkCd(Map<String, Object> paramMap, HttpServletRequest request)
			throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		String cfmFg = StringUtils.trimToEmpty((String) paramMap.get("cfmFg"));
		if (cfmFg.equals("3")) {
			return nEDMPRO0040Dao.selectNewSrcmkCdFix(paramMap); //신상품의 확정된 상품에 대한 데이터 조회 추가 2016.03.17 by song min kyo
		} else {
			return nEDMPRO0040Dao.selectNewSrcmkCd(paramMap);
		}
	}

	/**
	 * 임시보관함 선택상품 삭제
	 * @param nEDMPRO0040VO
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteImsiProductList(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0040VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		String[] arrPgmId = nEDMPRO0040VO.getArrPgmId();
		String[] arrOnOffGubun = nEDMPRO0040VO.getArrOnOffGubun();
		String[] arrImgId = nEDMPRO0040VO.getArrImgId();
		
		String esgUploadFilePath = ConfigUtils.getString("edi.prod.file.path");	//ESG파일 업로드패스 Root
		String esgFilePath = "";		//ESG파일패스
		File esgDir;					//ESG파일 디렉토리

		for (int i = 0; i < arrPgmId.length; i++) {

			String onLineFullPath = makeSubFolderForOnline(StringUtils.trimToEmpty(arrPgmId[i]));
			String offLinefullPath = makeSubFolderForOffline(StringUtils.trimToEmpty(arrPgmId[i]));
			String wideOnLineFullPath = makeSubFolderForWide(StringUtils.trimToEmpty(arrPgmId[i]));
			//선택한 상품의 온라인 이미지  파일을 삭제한다.
			File dir = new File(onLineFullPath);
			FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(arrPgmId[i]) + "*");
			File[] files = dir.listFiles(fileFilter);
			for (int j = 0; j < files.length; j++) {
				FileUtils.deleteQuietly(files[j]);
			}

			//선택한 상품의 온라인 와이드 이미지 파일을 삭제한다.
			File wideDir = new File(wideOnLineFullPath);
			FileFilter wideFileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(arrPgmId[i]) + "*");
			File[] wideFiles = wideDir.listFiles(wideFileFilter);
			for (int j = 0; j < wideFiles.length; j++) {
				FileUtils.deleteQuietly(wideFiles[j]);
			}
			//온오프 겸용 상품인 경우, 해당 오프라인 pog image를 삭제 [온오프상품:0, 온라인상품:1]
			if(StringUtils.trimToEmpty(arrOnOffGubun[i]).equals("0")) {
				
				String targetFile = StringUtils.substring(StringUtils.trimToEmpty(arrPgmId[i]), 2, 8)+ StringUtils.right(StringUtils.trimToEmpty(arrPgmId[i]), 5);
				
				File dirOff = new File(offLinefullPath);	
				FileFilter fileFilterOff = new WildcardFileFilter(targetFile+"*");
				File[] filesOff = dirOff.listFiles(fileFilterOff);
				for (int jOff = 0; jOff < filesOff.length; jOff++) {
				  FileUtils.deleteQuietly(filesOff[jOff]);
				}
			}

			//delete 조건문 Parameter..
			nEDMPRO0040VO.setPgmId(StringUtils.trimToEmpty(arrPgmId[i]));
			
			//1.해당상품 변형속성 삭제
			nEDMPRO0040Dao.deleteImsiNewProdVarAtt(nEDMPRO0040VO);
			
			//2.해당상품 분석속성 삭제
			nEDMPRO0040Dao.deleteImsiNewProdCatAtt(nEDMPRO0040VO);
			
			//3.해당상품 오프라인 이미지 삭제
			nEDMPRO0040Dao.deleteImsiNewProdOfflineImg(nEDMPRO0040VO);
			
			//3.전상거래법 삭제
			nEDMPRO0040Dao.deleteImsiNewProdAddInfoVal(nEDMPRO0040VO);
			
			//4.KC인증 삭제
			nEDMPRO0040Dao.deleteImsiNewProdCertInfoVal(nEDMPRO0040VO);
			
			//5.상품설명 삭제
			nEDMPRO0040Dao.deleteImsiNewProdDescr(nEDMPRO0040VO);
			
			//------ 추가분 ------//
			
			//배송정책 삭제
			nEDMPRO0040Dao.deleteImsiNewProdDelivery(nEDMPRO0040VO);
			
			//------ 추가분 ------//
			
			//5.상품삭제
			nEDMPRO0040Dao.deleteImsiNewProd(nEDMPRO0040VO);	
			
			//20180904 상품키워드 입력 기능 추가
			//온라인상품등록(일괄) 상품키워드 삭제
			nEDMPRO0040Dao.deleteAllTpcPrdTotalKeyword(nEDMPRO0040VO);

			nEDMPRO0040Dao.deleteEcProductCategory(StringUtils.trimToEmpty(arrPgmId[i]));	// EC전시카테고리 삭제
			
			nEDMPRO0040Dao.deleteEcProductAttribute(StringUtils.trimToEmpty(arrPgmId[i]));	// EC상품(단품별)속성 삭제
			
			nEDMPRO0040Dao.deleteNutAttWithPgmId(StringUtils.trimToEmpty(arrPgmId[i])); // 영양성분속성 삭제

			nEDMPRO0040Dao.deleteOspDispCategory(StringUtils.trimToEmpty(arrPgmId[i])); // 오카도카테고리 삭제
			
			//------ 추가분 ------//
			//6.ESG 인증정보 삭제
			//ESG 파일 삭제
			esgFilePath = esgUploadFilePath + "/" + arrPgmId[i] + "/";	//ESG파일 업로드 경로
			esgDir = new File(esgFilePath);								//ESG 파일 업로드 디렉토리 객체
			if (esgDir.exists()) {
				//ESG 파일삭제
				File[] filesEsg = esgDir.listFiles();
				for (int k = 0; k < filesEsg.length; k++) {
				  FileUtils.deleteQuietly(filesEsg[k]);
				}
			}
			//ESG 파일정보 삭제
			nEDMPRO0040Dao.deleteTpcNewProdEsgFile(StringUtils.trimToEmpty(arrPgmId[i]));
			//ESG 인증항목 삭제 (순서주의!! 파일정보 삭제 이전에 인증항목부터 삭제하면 파일정보 삭제불가함)
			nEDMPRO0040Dao.deleteTpcNewProdEsg(StringUtils.trimToEmpty(arrPgmId[i]));
			
		}

		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}

	/**
	 * 임시보관함 신규상품 확정
	 */
	private Map<String, Object> updateImsiProductList_old250423(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0040VO == null || request == null) {
			throw new TopLevelException("");
		}
		
		//코리안넷은 세션이 없으므로 주석처리..
		//EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		/* 선택된 확정 지어야 될 상품 배열 */
		String[] arrPgmId = nEDMPRO0040VO.getArrPgmId(); // 판매코드
		String[] arrOnOffGubun = nEDMPRO0040VO.getArrOnOffGubun(); // 온오프구분
		// String[] arrImgId = nEDMPRO0040VO.getArrImgId(); //이미지아이디
		// String[] arrTrdTypeDivnCd = nEDMPRO0040VO.getArrImgId(); //거래형태구분[1:직매입, 2:특약1, 4:특약2]
		String[] arrProxyNm = nEDMPRO0040VO.getArrProxyNm(); //RFC CALL name..
		int arrPgmIdLength = 0;
		if (arrPgmId != null && arrPgmId.length > 0) {
			arrPgmIdLength = arrPgmId.length;
		}

		String[] arrStaffSellYn = nEDMPRO0040VO.getArrStaffSellYn();
		//---- 온라인전용이면서 임직원몰판매가능 한 상품 임직원용상품으로 복사
		for (int j = 0; j < arrPgmIdLength; j++) {
			if ("1".equals(arrOnOffGubun[j]) && "1".equals(arrStaffSellYn[j])) {

				DataMap paramMap = new DataMap();
				paramMap.put("pgmId", arrPgmId[j]);

				try {
					//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
					//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
					String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
					//신상품코드 생성 실패 시, exception
					if("".equals(newPgmId)) {
						throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
					}
//					String newPgmId = ediProductService.selectNewProductCode();
					paramMap.put("newPgmId", newPgmId); // 신규임시상품코드
					nEDMPRO0040Dao.insertStaffSellProduct(paramMap); // 상품복사
					nEDMPRO0040Dao.insertStaffSellProdAddDetail(paramMap); // 전상법 복사
					nEDMPRO0040Dao.insertStaffSellProdCertDetail(paramMap); // KC인증 복사
					nEDMPRO0040Dao.insertStaffSellProdDelivery(paramMap); // 배송정책 복사
					nEDMPRO0040Dao.insertStaffSellProdVendorAddr(paramMap); // 배송정책 출고지,반품/교환지 주소 복사
					nEDMPRO0040Dao.insertStaffSellProductItemInfo(paramMap); // 옵션단품 복사
					nEDMPRO0040Dao.insertStaffSellProductDescription(paramMap); // 추가설명 복사

					// 이미지복사
					String orgFileNm = "";
					String orgUploadDirPath = makeSubFolderForOnline(arrPgmId[j]);
					String newUploadDirPath = makeSubFolderForOnline(newPgmId);

					File files = new File(orgUploadDirPath);

					for (File file : files.listFiles()) {
						if (file.isDirectory()) {
							continue;
						}

						if (file.isFile()) {
							if (file.getName().indexOf(".") == -1) {
								orgFileNm = file.getName();
								String newFileNm = newPgmId + orgFileNm.substring(orgFileNm.lastIndexOf("_"), orgFileNm.length());

								FileInputStream inputStream = new FileInputStream(orgUploadDirPath+"/"+orgFileNm);
								FileOutputStream outputStream = new FileOutputStream(newUploadDirPath+"/"+newFileNm);

								FileChannel fcin =  inputStream.getChannel();
								FileChannel fcout = outputStream.getChannel();

								long size = fcin.size();
								fcin.transferTo(0, size, fcout);

								fcout.close();
								fcin.close();

								outputStream.close();
								inputStream.close();

								imageFileMngService.purgeImageQCServer("01",newFileNm);
								imageFileMngService.purgeCDNServer("01", newFileNm);
							}
						}
					}

				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new TopLevelException("");
				}
			}
		}

		/*
		 * 온라인 상품일경우 이미지 등록 카운트를 확인하여 Null이나 0 이 있는 경우 전부 거부처리한다.
		 * 화면에서 요청되는 경우엔 스크립트에서 제어하여 요청 상품수 20개 이내 이미지 카운트가 있는것들만 처리하도록 하지만
		 * 사방넷등의 연동 솔루션등을 통해 접근하는 경우 스크립트가 무시될 수 있기때문에 필수 요소인 이미지를 체크하지 않으면
		 * 대표이미지 없이 상품승인 단계로 진입할 수 있다.
		 * 2019-08-01 한주형
		 */
		boolean isOnlineProd = false;
		for (int i = 0; i < arrPgmIdLength; i++) {
			if ("1".equals(arrOnOffGubun[i])) {
				isOnlineProd = true;
			}
		}

		if (isOnlineProd) {
			int noImgCnt = nEDMPRO0040Dao.selectNewProdNoImgCnt(nEDMPRO0040VO);
			if (noImgCnt > 0) { // 온라인 상품일경우 확정요청 상품중 IMG_NCNT 가 Null or 0 일경우 전체거부 2019-08-01 한주형
				resultMap.put("msg", "FAIL-PRODUCT_WITHOUT_IMAGE");
				return resultMap;
			}
		}

		//----- 3.MD전송 구분 업데이트 온오프상품 (온오프 && 거래형태가 직매입 'A'&&관리자 1 이면 W 관리자가 아니면 A 그외 아닐경우 'S' SQL에서 처리되어 있음)
		nEDMPRO0040Dao.updateImsiProductList(nEDMPRO0040VO);
		//----- STS테이블에 오프라인확정을 넘어가게 처리
		//matcd가 2이면 eco->바로 지

		// for(int s=0; s<arrPgmIdLength; s++){
		// 	if("X".equals(arrVicOnlineCd[s])){
		// 		DataMap paramMap = new DataMap();
		// 		paramMap.put("pgmId", arrPgmId[s]);
		// 		nEDMPRO0040Dao.updateImsiProductStsVicOn(arrPgmId[s]);
		// 	}else {
		// 		nEDMPRO0040Dao.updateImsiProductStsVicOff(arrPgmId[s]);
		// 		}
		// }
		// nEDMPRO0040Dao.updateImsiProductSts(nEDMPRO0040VO);

		String[] arrVicOnlineCd = nEDMPRO0040VO.getArrVicOnlineCd();
		String[] arrMatCd = nEDMPRO0040VO.getArrMatCd();
		String[] arrAdmFg = nEDMPRO0040VO.getArrAdmFg();

		for (int s = 0; s < arrPgmIdLength; s++) {

			//온라인 일괄확정시  vic마켓 구분 값이 없을때 null로 초기화 되어 nullPointException 발생.
			//null 예외처리 대신 상품 유형[온오프: 0, 온라인: 1, 소셜: 2]에 따라 해당 index에 접근하여 데이터 업데이트 할 수 있도록  분기 처리.  
			//소셜 유형은 현재 사용되지 않고 있지만 추후 vic마켓 데이터 여부에 따라 처리되는 분기로직 확인이 필요함.
			if (StringUtils.trimToEmpty(arrOnOffGubun[s]).equals("0")) { // 온오프

				if ("2".equals(arrMatCd[s])) {	//VIC 전용
					if ("X".equals(arrVicOnlineCd[s])) {
						nEDMPRO0040Dao.updateImsiProductStsVicOn(arrPgmId[s]);
					} else {
						nEDMPRO0040Dao.updateImsiProductStsVicOff(arrPgmId[s]);
					}
				} else {
					if ("3".equals(arrAdmFg[s])) {
						nEDMPRO0040Dao.updateImsiProductStsNorAdm(arrPgmId[s]);
					}/* else {
						// nEDMPRO0040Dao.updateImsiProductStsNor(arrPgmId[s]);
					}*/
				}

			} else { //온라인
				if (arrAdmFg != null) {
					if ("3".equals(arrAdmFg[s])) {
						nEDMPRO0040Dao.updateImsiProductStsNorAdm(arrPgmId[s]);
					} /*else {
						// nEDMPRO0040Dao.updateImsiProductStsNor(arrPgmId[s]);
					}*/
				}
			}

			// if("2".equals(arrMatCd[s])&& "X".equals(arrVicOnlineCd[s])){ //vic여부가 vic마켓전용이면 온라인여부면 
			// 	nEDMPRO0040Dao.updateImsiProductStsVicOn(arrPgmId[s]);
			// }else if("2".equals(arrMatCd[s]) && ("".equals(arrVicOnlineCd[s]) || arrVicOnlineCd[s]==null ) ) {
			// 	nEDMPRO0040Dao.updateImsiProductStsVicOff(arrPgmId[s]);	
			// }else if(!"2".equals(arrMatCd[s]) && "3".equals(arrAdmFg[s])) {
			// 	//nEDMPRO0040Dao.updateImsiProductStsAdm(arrPgmId[s])
			// }else{
			// 	//nEDMPRO0040Dao.updateImsiProductStsNor(arrPgmId[s])
			// }
		}

		//----- RFC Call 요청 이전 해당 상품의 소분류의 그룹분석속정정보를 다시 한번 체크하여 해당 소분류의 그룹분석속성이 아닌값이 있을경우에는 해당 데이터는 삭제한다. 2016.03.14 추가 by songminkyo
		for (int i = 0; i < arrPgmIdLength; i++) {
			nEDMPRO0040Dao.deleteNotGrpAtt(arrPgmId[i]);
		}

		//----- 1. 상품확정처리 RFC CALL START
		List<HashMap> lsHmap = nEDMPRO0040Dao.selectNewTmpProductInfo(nEDMPRO0040VO); // RFC CALL 넘길 데이터 조회(온오프전용이면서 직매입이 아닌 상품만 조회함)	
		HashMap reqCommonMap = new HashMap(); // RFC 응답

		//---- RFC로 넘길 데이터는 온오프 상품만 조회하므로 온오프 상품만 RFC 콜 한다.
		if (lsHmap.size() > 0) {
			//----- 2. 상품확정처리 RFC 그룹분석속성
			List<HashMap> lsHmapGrpAttr = nEDMPRO0040Dao.selectRfcDataGrpAttr(nEDMPRO0040VO); // RFC CALL 넘길 그룹분석속성 데이터 조회

			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS", "");
			reqCommonMap.put("ZPOROWS", "");
			reqCommonMap.put("ZPODATE", "");
			reqCommonMap.put("ZPOTIME", "");

			JSONObject obj = new JSONObject();
			obj.put("NEW_PROD", lsHmap); // HashMap에 담긴 데이터 JSONObject로 ...
			if (lsHmapGrpAttr != null && lsHmapGrpAttr.size() > 0) {
				obj.put("NEW_PROD_ANALYSIS", lsHmapGrpAttr);
			}
			
			List<HashMap> lsHmapNutAttr = nEDMPRO0040Dao.selectRfcDataNutAtt(nEDMPRO0040VO); // RFC CALL 넘길 영양성분속성 데이터 조회
			if (lsHmapNutAttr != null && lsHmapNutAttr.size() > 0) {
				obj.put("NEW_PROD_NUT", lsHmapNutAttr);
			}
			
			List<HashMap> lsHmapEsgInfo = nEDMPRO0040Dao.selectRfcDataEsgInfo(nEDMPRO0040VO); // RFC CALL 넘길 ESG 인증정보 데이터 조회
			if (lsHmapEsgInfo != null && lsHmapEsgInfo.size() > 0) {
				obj.put("NEW_PROD_ESG", lsHmapEsgInfo);
			}
			
			obj.put("REQCOMMON", reqCommonMap); // RFC 응답 HashMap JsonObject로....
			logger.debug("obj.toString=" + obj.toString());

			// ----- 1.RFC CALL("proxyNm", String, String);
			Map<String, Object> rfcMap;

			rfcMap = rfcCommonService.rfcCall(arrProxyNm[0], obj.toString(), nEDMPRO0040VO.getGbn());

			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject mapObj = new JSONObject(rfcMap.toString()); //MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject resultObj = mapObj.getJSONObject("result"); //JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj = resultObj.getJSONObject("RESPCOMMON"); //<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT")); //RFC 응답 성공 / 실패 여부를 담는 Key다

			//성공이 아니면 실패로 간주한다.
			if (!rtnResult.equals("S")) {
				// -----RFC 요청이 실패이면 MD_SEND_DIVN_CD 컬럼을 ''값으로 다시 Update한다.
				nEDMPRO0040Dao.updateImsiProductClear(nEDMPRO0040VO);

				resultMap.put("msg", "PRODUCT_FIX_FAIL");
				return resultMap;
				// throw new Exception();
			}

			// //----- 2. 상품확정처리 이미지 RFC CALL START [상품 마스터 정보 RFC CALL 요청시에 포함해서 하므로 주석처리 by song min kyo]
			// /*List<HashMap>	lsImgHmap = nEDMPRO0040Dao.selectNewTmpProductImgInfo(nEDMPRO0040VO); // RFC CALL 넘길 데이터 조회
			// JSONObject JsonImgObj = new JSONObject();
			// JsonImgObj.put("BCD_IMG", lsImgHmap); // HashMap에 담긴 데이터 JSONObject로 ...
			// JsonImgObj.put("REQCOMMON", reqCommonMap); // RFC 응답 HashMap JsonObject로....
			// logger.debug("JsonImgObj.toString=" + JsonImgObj.toString());*/
			// //RFC CALL("proxyNm", String, String);
			// //rfcCommonService.rfcCall(arrProxyNm[1], JsonImgObj.toString(), epcLoginVO.getAdminId());
		}
		// logger.debug("proxyNm ==" + arrProxyNm[0]);
		// logger.debug("proxyNm1 ==" + arrProxyNm[1]);

		resultMap.put("msg", "SUCCESS");
		return resultMap;
	
	}

	/**
	 * 임시보관함 상품 변형속성 정보
	 */
	public List<NEDMPRO0043VO> selectNewProductDetailVarAttInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0040Dao.selectNewProductDetailVarAttInfo(paramMap);
	}

	/**
	 * 임시보관함 상품 상세보기 온라인전용 88코드, 이익률 조회
	 * @param entpCode
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0044VO> selectOnlineRepresentProdctList(String entpCode) throws Exception {
		return nEDMPRO0040Dao.selectOnlineRepresentProdctList(entpCode);
	}

	/**
	 * 임시보관함 상세보기 [온라인전용] 단품정보 리스트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0045VO> selectOnlineItemListIntemp(String pgmId) throws Exception {
		return nEDMPRO0040Dao.selectOnlineItemListIntemp(pgmId);
	}

	/**
	 * 기본출고지/반품지 주소 조회
	 */
	@Override
	public Map<String, Object> checkAddressInfo(String[] vendorIds) throws Exception {
		List<PSCMSYS0003VO> addressInfoList = nEDMPRO0040Dao.selectAddressInfo(vendorIds);

		Map<String, Object> result = new HashMap<String, Object>();
		String info = "";
		for(PSCMSYS0003VO addressInfo : addressInfoList) {
			if("KM05".equals(addressInfo.getVendorKindCd()) && addressInfo.getCount() < 2) {
				info += addressInfo.getVendorId() + " ";
			}
		}
		result.put("vendorIds", info);
		return result;
	}
	
	/**
	 * 임시보관함 상품 확정
	 */
	public Map<String, Object> updateImsiProductList(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0040VO == null || request == null) {
			throw new TopLevelException("");
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		/*
		 * 1. 필수데이터 체크
		 */
		//상품 list
		List<NEDMPRO0040VO> prodList = nEDMPRO0040VO.getProdArr();
		
		//상품 데이터리스트 없음
		if(prodList == null || prodList.isEmpty() || prodList.size() == 0) {
			resultMap.put("msg", "NO_DATA_LIST");
			return resultMap;
		}
		
		/*
		 * 2. RFC Call 요청 이전, 유효하지 않은 데이터 전처리 (삭제OR전송불가하도록 처리)
		 */
		//2-1) 해당 상품의 소분류의 그룹분석속정정보를 다시 한번 체크하여 해당 소분류의 그룹분석속성이 아닌값이 있을경우에는 해당 데이터는 삭제한다. 2016.03.14 추가 by songminkyo
		for(NEDMPRO0040VO vo : prodList) {
			nEDMPRO0040Dao.deleteNotGrpAtt(vo.getPgmId());
		}
		
		//2-2) 항목삭제된 ESG 정보 제거
		int delUnusedCtg = nEDMPRO0040Dao.updateCtgDelEsgInfo(nEDMPRO0040VO);
		logger.info("Remove information about deleted esg category - delCnt:"+delUnusedCtg);
		
		//TODO_JIA :::::: SFTP 서버 테스트 완료 후 주석 해제 처리 예정
//		//2-3) ESG 파일 있을 경우, PO 전송
//		Map<String,Object> esgFileSendMap = this.updateSendSftpEsgFile(nEDMPRO0040VO);
//		//전송실패 시,
//		if(!MapUtils.getString(esgFileSendMap, "msg").equals("SUCCESS")) {
//			resultMap.put("msg", "ESG_FILE_SEND_FAIL");
//			resultMap.put("errMsg", MapUtils.getString(esgFileSendMap, "errMsg"));
//			return resultMap;
//		}
		
		/*
		 * 3. MD 전송 구분 업데이트
		 */
		nEDMPRO0040Dao.updateImsiProductList(nEDMPRO0040VO);
		
		/*
		 * 4. 전송대상별 Data 구분
		 */
		NEDMPRO0040VO gbnVo = new NEDMPRO0040VO();
		gbnVo.setProdArr(prodList);
		gbnVo.setSendGbn("BOS");
		List<NEDMPRO0040VO> bosSendList = nEDMPRO0040Dao.selectNewTmpProductInfoSendGbn(gbnVo);
		
		gbnVo.setSendGbn("ERP");
		List<NEDMPRO0040VO> erpSendList = nEDMPRO0040Dao.selectNewTmpProductInfoSendGbn(gbnVo);
		
		gbnVo.setSendGbn("");
		
		
		Map<String,Object> bosRsltMap = null;	//BOS 전송결과맵
		Map<String,Object> erpRsltMap = null;	//ERP 전송결과맵
		
		/*
		 * 5. BOS API SEND
		 */
		//BOS 전송대상 존재 시, 실행
		if(bosSendList != null && !bosSendList.isEmpty() && bosSendList.size() > 0) {
			nEDMPRO0040VO.setBosProdList(bosSendList);
			bosRsltMap = this.updateImsiProductListFixSendBos(nEDMPRO0040VO, request);
		}
		
		/*
		 * 6. ERP PROXY SEND
		 */
		//ERP 전송대상 존재 시, 실행
		if(erpSendList != null && !erpSendList.isEmpty() && erpSendList.size() > 0) {
			nEDMPRO0040VO.setErpProdList(erpSendList);
			erpRsltMap = this.updateImsiProductListFixSendErp(nEDMPRO0040VO, request);
		}
		
		/*
		 * 결과셋팅
		 */
		resultMap.put("toBos", bosSendList.size());		//bos전송대상수
		resultMap.put("toErp", erpSendList.size());		//erp전송대상수
		
		boolean chkSendBos = false;
		boolean chkSendErp = false;
		if(bosSendList.size()>0) {
			String bosMsg = MapUtils.getString(bosRsltMap, "msg");
			if("SUCCESS".equals(bosMsg)) chkSendBos = true;
			resultMap.put("bosMsg", bosMsg);
		}else{
			chkSendBos = true;
		}
		
		if(erpSendList.size()>0) {
			String erpMsg = MapUtils.getString(erpRsltMap, "msg");
			if("SUCCESS".equals(erpMsg)) chkSendErp = true;
			resultMap.put("erpMsg", erpMsg);
		}else {
			chkSendErp = true;
		}
		
		if(chkSendBos || chkSendErp) {
			resultMap.put("msg", "SUCCESS");
		}
		return resultMap;
		
	}
	
	/**
	 * 임시보관함 신규상품 확정요청 > BOS API 전송
	 */
	public Map<String,Object> updateImsiProductListFixSendBos(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		/*
		 * 0. 데이터 전처리
		 */
		//BOS 전송대상 상품 List
		List<NEDMPRO0040VO> bosProdList = nEDMPRO0040VO.getBosProdList();
		if(bosProdList == null || bosProdList.isEmpty() || bosProdList.size() == 0) {
			resultMap.put("msg", "NO_DATA");
			return resultMap;
		}
		
		//BOS 전송대상 상품정보 setting
		NEDMPRO0040VO bosProdVo = new NEDMPRO0040VO();
		bosProdVo.setSendGbn("BOS");			//전송구분 - BOS
		bosProdVo.setProdArr(bosProdList);		//BOS 전송대상상품 LIST
		
		/*
		 * 1. BOS 온오프 확정상태 등록(직매입상품만) 
		 */
		String maxxOnlyYn = "";	//MAXX 단일채널 여부
		String vicOnlineCd = "";//온라인적용여부
		String admFg = "";		//관리자구분
		String pgmId = "";		//신상품아이디
		
		for(NEDMPRO0040VO bosVo:bosProdList) {
			maxxOnlyYn = StringUtils.defaultString(bosVo.getMaxxOnlyYn());		//MAXX 단일채널여부
			vicOnlineCd = StringUtils.defaultString(bosVo.getVicOnlineCd());	//온라인적용여부 (MAXX 단일채널 선택시에만 적용됨)
			admFg = StringUtils.defaultString(bosVo.getAdmFg());				//관리자구분
			pgmId = StringUtils.defaultString(bosVo.getPgmId());				//신상품아이디
			
			if("Y".equals(maxxOnlyYn)) {	//MAXX 단일채널
				if("X".equals(vicOnlineCd)) {	//온라인적용일 경우 오프라인확정처리(80) 되어서 넘어감 (직매입상품일 경우에만)
					nEDMPRO0040Dao.updateImsiProductStsVicOn(pgmId);
				} else {							//온라인미적용일 경우 온라인확정처리(81) 되어서 넘어감 (직매입상품일 경우에만)
					nEDMPRO0040Dao.updateImsiProductStsVicOff(pgmId);
				}
			}else {
				//관리자 승인 시, BOS 전송 전 오프라인 확정처리(80) 되어서 넘어감 (직매입상품일 경우에만)
				if ("3".equals(admFg)) {
					nEDMPRO0040Dao.updateImsiProductStsNorAdm(pgmId);
				}
			}
			
		}
		
		/*
		 * 2. BOS 승인마감일자 UPDATE 
		 */
		nEDMPRO0040Dao.updateProdBosSendDy(bosProdVo);
		
		/*
		 * 3. BOS 전송 데이터 구성
		 */
		Map<String,Object> sendParamMap = new HashMap<String,Object>();
		Map<String,Object> sendProdMst = null;
		JSONObject sendObj = null;
		
		List<HashMap<String,Object>> sendProdSts = null;		//신상품등록상태
		List<HashMap<String,Object>> sendImport = null;			//수입정보
		List<HashMap<String,Object>> sendProdCertInfo = null;	//KC인증
		List<HashMap<String,Object>> sendVarAtt = null;			//변형속성
		List<HashMap<String,Object>> sendProdDescr = null;		//상품추가설명
		List<HashMap<String,Object>> sendGrpAtt = null;			//그룹분석속성
		List<HashMap<String,Object>> sendNutAtt = null;			//영양성분속성
		List<HashMap<String,Object>> sendKeyword = null;		//상품키워드
		List<HashMap<String,Object>> sendEcCatProdMapp = null;	//EC 전시카테고리
		List<HashMap<String,Object>> sendEcAttrProdMapp = null;	//EC 상품속성
		List<HashMap<String,Object>> sendProdAddInfo = null;	//전상법
		List<HashMap<String,Object>> sendProdEsg = null;		//ESG 인증항목
		List<HashMap<String,Object>> sendProdEsgFile = null;	//ESG 첨부파일
		List<HashMap<String,Object>> sendSaleImgInfo = null;	//POG 이미지정보
		List<HashMap<String,Object>> sendOspCatProdMapp = null;	//오카도 전시카테고리
		
		String sendPgmId = "";
		
		//BOS 상품전송 API 호출결과
		Map<String,Object> apiResult = null;
		String apiRsltCd = "";		//호출결과 msgCd
		String apiRsltMsg = "";		//호출결과 message
		//BOS 상품전송 응답결과
		Map<String,Object> apiRtnMap = null;
		String apiRtnRslt = "";		//상품전송결과코드
		String apiRtnRsltMsg = "";	//상품전송결과메세지
		
		int okCnt = 0;
		//상품 건별 data 전송
		for(NEDMPRO0040VO sendVo:bosProdList) {
			sendPgmId = StringUtils.defaultString(sendVo.getPgmId());	//신상품코드
			logger.info("SEND TO BOS - PGM_ID : "+sendPgmId);
			
			//상품 마스터 조회
			sendProdMst = nEDMPRO0040Dao.selectTpcNewProdRegBosFix(sendVo);
			
			//상품데이터 있을 때에만 전송
			if(sendProdMst != null && !sendProdMst.isEmpty()) {
				
				sendParamMap.put("TPC_NEW_PROD_REG", sendProdMst);	//상품마스터
				
				//신상품 등록상태
				sendProdSts = nEDMPRO0040Dao.selectTpcNewProdStsBosFix(sendVo);
				sendParamMap.put("TPC_NEW_PROD_STS", sendProdSts);
				
				//수입정보
				sendImport = nEDMPRO0040Dao.selectTpcImportAttBosFix(sendVo);
				sendParamMap.put("TPC_IMPORT_ATT", sendImport);
				
				//KC인증
				sendProdCertInfo = nEDMPRO0040Dao.selectTprProdCertInfoValBosFix(sendVo);
				sendParamMap.put("TPR_PROD_CERT_INFO_VAL", sendProdCertInfo);
				
				//변형속성
				sendVarAtt = nEDMPRO0040Dao.selectTpcVarAttBosFix(sendVo);
				sendParamMap.put("TPC_VAR_ATT", sendVarAtt);
				
				//상품추가설명
				sendProdDescr = nEDMPRO0040Dao.selectTpcNewProdDescrBosFix(sendVo);
				sendParamMap.put("TPC_NEW_PROD_DESCR", sendProdDescr);
				
				//그룹분석속성
				sendGrpAtt = nEDMPRO0040Dao.selectTpcGrpAttBosFix(sendVo);
				sendParamMap.put("TPC_GRP_ATT", sendGrpAtt);
				
				//영양성분속성
				sendNutAtt = nEDMPRO0040Dao.selectTpcNutAttBosFix(sendVo);
				sendParamMap.put("TPC_NUT_ATT", sendNutAtt);
				
				//상품키워드
				sendKeyword = nEDMPRO0040Dao.selectTpcProductKeywordBosFix(sendVo);
				sendParamMap.put("TPC_PRODUCT_KEYWORD", sendKeyword);
				
				//EC 전시카테고리
				sendEcCatProdMapp = nEDMPRO0040Dao.selectTecCatProdMappingBosFix(sendVo);
				sendParamMap.put("TEC_CAT_PROD_MAPPING", sendEcCatProdMapp);
				
				//EC 상품속성
				sendEcAttrProdMapp = nEDMPRO0040Dao.selectTecAttrProdMappingBosFix(sendVo);
				sendParamMap.put("TEC_ATTR_PROD_MAPPING", sendEcAttrProdMapp);
				
				//전상법
				sendProdAddInfo = nEDMPRO0040Dao.selectTprProdAddInfoValBosFix(sendVo);
				sendParamMap.put("TPR_PROD_ADD_INFO_VAL", sendProdAddInfo);
				
				//ESG 인증항목
				sendProdEsg = nEDMPRO0040Dao.selectTpcNewProdEsgBosFix(sendVo);
				sendParamMap.put("TPC_NEW_PROD_ESG", sendProdEsg);
				
				//ESG 첨부파일
				sendProdEsgFile = nEDMPRO0040Dao.selectTpcNewProdEsgFileBosFix(sendVo);
				sendParamMap.put("TPC_PROD_ESG_FILE", sendProdEsgFile);
				
				//POG 이미지정보
				sendSaleImgInfo = nEDMPRO0040Dao.selectTpcSaleImgBosFix(sendVo);
				sendParamMap.put("TPC_SALE_IMG", sendSaleImgInfo);
				
				//오카도 전시카테고리
				sendOspCatProdMapp = nEDMPRO0040Dao.selectTecOspCatProdMappingBosFix(sendVo);
				sendParamMap.put("TEC_OSP_CAT_PROD_MAPPING", sendOspCatProdMapp);
				
				sendObj = new JSONObject(sendParamMap);
				logger.debug("sendObj.toString=" + sendObj.toString());
				
				//API 호출
				apiResult = bosOpenApiCaller.call("101", sendParamMap);
				
				//api 응답결과 코드
				apiRsltCd = StringUtils.defaultString(MapUtils.getString(apiResult, "msgCd"));		//API 호출 결과코드
				apiRsltMsg = StringUtils.defaultString(MapUtils.getString(apiResult, "message"));	//API 호출 결과메세지
				
				//api call 실패
				if(!"S".equals(apiRsltCd)) {
					// -----API 요청이 실패이면 MD_SEND_DIVN_CD 컬럼을 ''값으로 다시 Update한다.
					nEDMPRO0040Dao.updateImsiProductClear(sendVo);
					//전송일자 clear (원복)
					nEDMPRO0040Dao.updateProdBosSendDyClear(sendVo);
					logger.error("[API ERR] BOS 상품확정요청 API 응답이 없습니다 - PGM_ID :"+sendPgmId);
					resultMap.put("msg", "PRODUCT_FIX_FAIL");
					continue;
				}
				
				//상품전송결과 확인
				apiRtnMap = MapUtils.getMap(apiResult, "resultData");
				apiRtnRslt = MapUtils.getString(apiRtnMap, "RESULT");	//전송결과코드
				apiRtnRsltMsg = MapUtils.getString(apiRtnMap, "MSG");	//전송결과메세지
				
				if(!"S".equals(apiRtnRslt)) {
					// -----API 요청이 실패이면 MD_SEND_DIVN_CD 컬럼을 ''값으로 다시 Update한다.
					nEDMPRO0040Dao.updateImsiProductClear(sendVo);
					//전송일자 clear (원복)
					nEDMPRO0040Dao.updateProdBosSendDyClear(sendVo);
					logger.error("[API ERR] BOS 상품확정요청에 실패하였습니다.("+apiRtnRsltMsg+")- PGM_ID :"+sendPgmId);
					resultMap.put("msg", "PRODUCT_FIX_FAIL");
					continue;
				}
				
				okCnt++;
			}
			
		}
		
		if(okCnt>0) {
			resultMap.put("msg", "SUCCESS");
		}
		return resultMap;
	}
	
	/**
	 * 임시보관함 신규상품 확정요청 > ERP Proxy 전송
	 */
	public Map<String,Object> updateImsiProductListFixSendErp(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		/*
		 * 0. 데이터 전처리
		 */
		//ERP 전송대상 상품 List
		List<NEDMPRO0040VO> erpProdList = nEDMPRO0040VO.getErpProdList();
		if(erpProdList == null || erpProdList.isEmpty() || erpProdList.size() == 0) {
			resultMap.put("msg", "NO_DATA");
			return resultMap;
		}
		
		//proxy name
		String[] arrProxyNm = {"MST2120"};
//		String[] arrProxyNm = nEDMPRO0040VO.getArrProxyNm(); //RFC CALL name..
//		if(arrProxyNm == null || arrProxyNm.length == 0) {
//			resultMap.put("msg", "NO_PROXY_NM");
//			return resultMap;
//		}
		
		String gbn = StringUtils.defaultString(nEDMPRO0040VO.getGbn(), "EDI");				//ERP 전송 시스템구분 (EDI)	
		String sendGbn = StringUtils.defaultString(nEDMPRO0040VO.getSendGbn(), "ERP");		//전송대상구분 (default:ERP, 자동발송:ATO)
		String batchYn = StringUtils.defaultString(nEDMPRO0040VO.getBatchYn(), "N");		//스케쥴러여부
		
		//ERP 전송대상 상품정보 setting
		NEDMPRO0040VO erpProdVo = new NEDMPRO0040VO();	
		erpProdVo.setSendGbn(sendGbn);			//전송구분-ERP			
		erpProdVo.setProdArr(erpProdList);		//ERP 전송대상상품 LIST
		erpProdVo.setBatchYn(batchYn);
		
		/**
		 * [EPC to ERP 전송대상]
		 * 1) 직매입x 상품
		 * 2) 직매입 & MAXX 단일 & 온라인미적용 상품
		 */
		
		//----- 1. 상품확정처리 RFC CALL START
		List<HashMap> lsHmap = nEDMPRO0040Dao.selectNewTmpProductInfo(erpProdVo); // RFC CALL 넘길 데이터 조회(온오프전용이면서 직매입이 아닌 상품만 조회함)	
		HashMap reqCommonMap = new HashMap(); // RFC 응답
	
		//---- RFC로 넘길 데이터는 온오프 상품만 조회하므로 온오프 상품만 RFC 콜 한다.
		if (lsHmap != null && !lsHmap.isEmpty() && lsHmap.size() > 0) {
			//----- 2. 상품확정처리 RFC 그룹분석속성
			List<HashMap> lsHmapGrpAttr = nEDMPRO0040Dao.selectRfcDataGrpAttr(erpProdVo); // RFC CALL 넘길 그룹분석속성 데이터 조회
	
			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS", "");
			reqCommonMap.put("ZPOROWS", "");
			reqCommonMap.put("ZPODATE", "");
			reqCommonMap.put("ZPOTIME", "");
	
			JSONObject obj = new JSONObject();
			obj.put("NEW_PROD", lsHmap); // HashMap에 담긴 데이터 JSONObject로 ...
			if (lsHmapGrpAttr != null && lsHmapGrpAttr.size() > 0) {
				obj.put("NEW_PROD_ANALYSIS", lsHmapGrpAttr);
			}
			
			List<HashMap> lsHmapNutAttr = nEDMPRO0040Dao.selectRfcDataNutAtt(erpProdVo); // RFC CALL 넘길 영양성분속성 데이터 조회
			if (lsHmapNutAttr != null && lsHmapNutAttr.size() > 0) {
				obj.put("NEW_PROD_NUT", lsHmapNutAttr);
			}
			
			List<HashMap> lsHmapEsgInfo = nEDMPRO0040Dao.selectRfcDataEsgInfo(erpProdVo); // RFC CALL 넘길 ESG 인증정보 데이터 조회
			if (lsHmapEsgInfo != null && lsHmapEsgInfo.size() > 0) {
				obj.put("NEW_PROD_ESG", lsHmapEsgInfo);
			}
			
			obj.put("REQCOMMON", reqCommonMap); // RFC 응답 HashMap JsonObject로....
			logger.debug("obj.toString=" + obj.toString());
	
			// ----- 1.RFC CALL("proxyNm", String, String);
			Map<String, Object> rfcMap;
	
			rfcMap = rfcCommonService.rfcCall(arrProxyNm[0], obj.toString(), gbn);
	
			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject mapObj = new JSONObject(rfcMap.toString()); //MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject resultObj = mapObj.getJSONObject("result"); //JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj = resultObj.getJSONObject("RESPCOMMON"); //<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT")); //RFC 응답 성공 / 실패 여부를 담는 Key다
//			String rtnResult = "F";
	
			//성공이 아니면 실패로 간주한다.
			if (!rtnResult.equals("S")) {
				//ERP 즉시 전송 대상일 경우에만, 실패 시 MD_SEND_DIVN_CD 값을 빈값으로 초기화.
				//BOS 승인 대상일 경우에는, BOS에 전송된 MD_SEND_DIVN_CD 유지를 위해 별도로 빈값 초기화 하지 않는다. (AUTO SEND 시)
				if("ERP".equals(sendGbn)) {
					nEDMPRO0040Dao.updateImsiProductClear(erpProdVo);
				}
	
				resultMap.put("msg", "PRODUCT_FIX_FAIL");
				return resultMap;
				// throw new Exception();
			}
	
		}
		//===========================================================================================================================================================
		
		
		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}
	
	/**
	 * 임시보관함 신규상품 확정요청 > 전송대상별 신상품 LIST 추출
	 */
	public List<NEDMPRO0040VO> selectNewTmpProductInfoSendGbn(NEDMPRO0040VO nEDMPRO0040VO) throws Exception{
		List<NEDMPRO0040VO> list = nEDMPRO0040Dao.selectNewTmpProductInfoSendGbn(nEDMPRO0040VO);
		if(list == null || list.isEmpty() || list.size() == 0) list = new ArrayList<NEDMPRO0040VO>();
		return list;
	}
	
	/**
	 * ESG 파일 STFP 전송 (EPC to PO)
	 */
	public Map<String, Object> updateSendSftpEsgFile(NEDMPRO0040VO paramVo) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "SUCCESS");
		
		//등록된 ESG 파일 리스트 조회
		List<Map<String,Object>> filelist = nEDMPRO0040Dao.selectTpcProdEsgFileForSend(paramVo);
		
		//등록된 ESG 파일 리스트 없을 경우 pass
		if(filelist == null || filelist.isEmpty()) {
			return resultMap;
		}
		
		String filePath = "";			//파일업로드경로
		String saveFileNm = "";			//저장파일명
		String orgFileNm = "";			//원본파일명
		String fileFullPath = "";		//파일 Full 경로
		String esgFileId = "";			//ESG 파일아이디
		
		String pgmId = "";				//EPC 문서번호
		
		//원격 SFTP 서버 파일업로드 경로 
		String remoteFilePath = ConfigUtils.getString("epc.sftp.po.path.esgNew.ing");			//업로드 미완료 파일 SFTP 서버 경로
		String remoteFileMoveToPath = ConfigUtils.getString("epc.sftp.po.path.esgNew.cfm");		//업로드 완료 파일 SFTP 서버 경로
		
		Map<String, Object> sftpMap = null;
		String sftpMsgCd = "";			//SFTP 전송결과 코드
		String sftpMessage = "";		//SFTP 전송결과 메세지
		
		//파일전송 결과 업데이트용 vo
		NEDMPRO0028VO fileSendVo = new NEDMPRO0028VO();
		
		//파일 SFTP 전송
		for(Map<String,Object> fileMap : filelist) {
			filePath = MapUtils.getString(fileMap, "FILE_PATH", "");		//파일업로드 경로
			saveFileNm = MapUtils.getString(fileMap, "SAVE_FILE_NM", "");	//저장파일명
			orgFileNm = MapUtils.getString(fileMap, "ORG_FILE_NM", "");		//원본파일명
			esgFileId = MapUtils.getString(fileMap, "ESG_FILE_ID", "");		//파일아이디
			pgmId = MapUtils.getString(fileMap, "PGM_ID", "");		//문서번호
			
			//저장된 파일 Full 경로
			fileFullPath = filePath + "/" + saveFileNm;
			
			//SFTP 파일업로드 (성공 시 파일 경로 이동)
			sftpMap = epcSftpUtil.uploadMoveAf("PO", fileFullPath, remoteFilePath, saveFileNm, remoteFileMoveToPath);
			
			sftpMsgCd = MapUtils.getString(sftpMap, "msgCd");		//SFTP 전송결과 코드
			sftpMessage = MapUtils.getString(sftpMap, "message");	//SFTP 전송결과 메세지
			
			//업로드 실패 시, return
			if(!"S".equals(sftpMsgCd)) {
				logger.error(sftpMessage+"(pgmId::"+pgmId+")");
				
				resultMap.put("msg", "FAIL");
				resultMap.put("errMsg", "ESG 파일 전송에 실패하였습니다.(문서번호:"+pgmId+"/파일명:"+orgFileNm+")");
				return resultMap;
			}
			
			//파일 SFTP 전송 정보 UPDATE
			fileSendVo.setEsgFileId(esgFileId);	//ESG 파일아이디
			fileSendVo.setSendPath(MapUtils.getString(sftpMap, "uploadPath"));	//SFTP 서버 내 업로드 경루
			fileSendVo.setSendDate(MapUtils.getString(sftpMap, "sendDate"));	//SFTP 서버 업로드 일시
			nEDMPRO0040Dao.updateTpcProdEsgFileSendInfo(fileSendVo);
		}
		
		return resultMap;
	}
	
}
