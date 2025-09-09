package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0220Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0220VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0221VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0220Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0220ServiceImpl
 * @Description : 물류바코드 등록 ServiceImpl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.30 	SONG MIN KYO	최초생성
 * </pre>
 */
@Service("nEDMPRO0220Service")
public class NEDMPRO0220ServiceImpl implements NEDMPRO0220Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0220ServiceImpl.class);

	@Resource(name="nEDMPRO0220Dao")
	private NEDMPRO0220Dao nEDMPRO0220Dao;

	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private BosOpenApiService bosApiService;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 *  임시보관함에서 바코드 등록을 위한 기본 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectNewBarcodeRegistTmp(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0220Dao.selectNewBarcodeRegistTmp(paramMap);
	}

	/**
	 * 임시보관함에서 바코드 등록을 위한 등록된 상품의 속성 리스트 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectImsiProdClassListCnt(String pgmId) throws Exception {
		return nEDMPRO0220Dao.selectImsiProdClassListCnt(pgmId);
	}


	/**
	 * [임시보관함 바코드 등록 팝업] 해당상품의 등록된 속성이 있을경우 등록된 속성의 물류바코드 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectImsiProdClassToLogiBcdInfo(Map<String, Object> paramMap) throws Exception {
		if (paramMap ==  null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	rsMap		=	new HashMap<String, Object>();
		NEDMPRO0220VO	resultVO		=	nEDMPRO0220Dao.selectImsiProdClassToLogiBcdInfo(paramMap);
		NEDMPRO0220VO	prodBasicInfo	=	new NEDMPRO0220VO();

		if (paramMap.get("variant").equals("000")) {
			prodBasicInfo	=	nEDMPRO0220Dao.selectLogiProdBasicInfo_1(paramMap);		//규격&&단일 상품 변형속성이 없다
		} else {
			prodBasicInfo	=	nEDMPRO0220Dao.selectLogiProdBasicInfo_2(paramMap);		//규격&&묶음 상품 변형속성 존재
		}

		rsMap.put("resultVO"		, resultVO);			//물류바코드 정보
		rsMap.put("prodBasicInfo"	, prodBasicInfo);		//상품의 기본정보

		return rsMap;
	}

	/**
	 * [임시보관함, 신상품등록현황조회 물류바코드 등록 팝업] 물류바코드 저장
	 * @param nEDMPRO0220VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertImsiProdLogiBcd(NEDMPRO0220VO nEDMPRO0220VO, HttpServletRequest request) throws Exception {

		if (nEDMPRO0220VO == null || request == null) {
			throw new IllegalArgumentException();
		}
		
		//============
		// 작업자정보
		//============
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();

		//코리안넷은 세션이 없기 때문에 주석처리
		//EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String[] arrProxyNm		=	nEDMPRO0220VO.getArrProxyNm();		//RFC CALL name..

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//-----1.[임시보관함] 신상품 속성별 물류바코드 등록  속성별 한건만 등록가능하다
		//String seq	=	nEDMPRO0220Dao.selectLogiBcdSeqInfo(nEDMPRO0220VO.getPgmId());
		//String seq = "001";

		//-----2.물류바코드 중복체크 [해당상품의 등록되어 있는 물류바코드 데이터가 있고 화면에서 입력한 물류바코드가 다를 경우 중복체크]
		Map<String, Object> paramMap	=	new HashMap<String, Object>();
		paramMap.put("pgmId", nEDMPRO0220VO.getPgmId());
		paramMap.put("venCd", nEDMPRO0220VO.getVenCd());
		//paramMap.put("seq", nEDMPRO0220VO.getSeq());		//속성별로 물류바코드 등록할떄는 seq가 variant랑 같으므로 주석처리
		paramMap.put("seq", nEDMPRO0220VO.getVariant());
		paramMap.put("variant", nEDMPRO0220VO.getVariant());

		//물류바코드 전체 중복체크 LOGI_BCD, LOGI_BCD_SAP 테이블 전체중복체크 (현재 사용안함 주석 처리)
		/*int duplCnt	=	nEDMPRO0220Dao.selectLogiBcdDuplChkCnt(nEDMPRO0220VO);
		if (duplCnt > 0) {
			resultMap.put("msg", "DUPL_LOGI_BCD");
			return resultMap;
		}*/



		/* 상품의 속성별로 중복체크를 하고 싶을 경우 주석 해제후 사용
		 * NEDMPRO0220VO	resultVO	=	nEDMPRO0220Dao.selectImsiProdClassToLogiBcdInfo(paramMap);

		//신규등록일때만 중복체크
		if (nEDMPRO0220VO.getCfgFg().equals("I")) {
			if (resultVO != null) {
				if (!StringUtils.trimToEmpty(resultVO.getLogiBcd()).equals(nEDMPRO0220VO.getLogiBcd())) {
					int duplCnt	=	nEDMPRO0220Dao.selectLogiBcdDuplChkCnt(nEDMPRO0220VO);
					if (duplCnt > 0) {
						resultMap.put("msg", "DUPL_LOGI_BCD");
						return resultMap;
					}
				}
			}
		}*/


		//----- 3.물류바코드 저장
		//nEDMPRO0220VO.setSeq(nEDMPRO0220VO.getSeq());					//상품별 SEQ
		nEDMPRO0220VO.setSeq(nEDMPRO0220VO.getVariant());				//SEQ에 변형속성 값을 준다.
//		nEDMPRO0220VO.setRegId(nEDMPRO0220VO.getVenCd());				//등록자는 협력업체코드로
		
		///작업자정보 setting (로그인세션에서 추출)
		nEDMPRO0220VO.setRegId(workId);
		nEDMPRO0220VO.setModId(workId);

		if (nEDMPRO0220VO.getCfgFg().equals("I")) {
			//-----INSERT
			nEDMPRO0220Dao.insertImsiProdLogiBcd(nEDMPRO0220VO);
		} else {
			//-----UPDATE
			nEDMPRO0220Dao.updateImsiProdLogiBcd(nEDMPRO0220VO);		//LOGI_BCD 테이블 UPDATE
			nEDMPRO0220Dao.updateImsiProdLogiBcdSAP(nEDMPRO0220VO);		//LOGI_BCD_SAP 테이블 확정상태 심사중으로 업데이트
		}


		//-----4.RFC CALL - 등록된 데이터를 다시 조회하여 RFC를 CALL한다.
		HashMap			lsHmap			=	nEDMPRO0220Dao.selectImsiProdInfoToRFC(nEDMPRO0220VO);		// RFC CALL 넘길 데이터 조회
		HashMap	 		reqCommonMap	=	new HashMap();												// RFC 응답

		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		JSONObject obj	=	new JSONObject();

		obj.put("LOGI_BCD", lsHmap);			// HashMap에 담긴 데이터 JSONObject로 ...
		obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....

		logger.debug("obj.toString=" + obj.toString());

		Map<String, Object>	rfcMap	=	rfcCommonService.rfcCall(arrProxyNm[0], obj.toString(), nEDMPRO0220VO.getVenCd());


		//----- 5.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
		String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		//성공이 아니면 실패로 간주한다.
		/*if (!rtnResult.equals("S")) {
			resultMap.put("msg", "RFC_FAIL");
			return resultMap;
		}*/
		if (!rtnResult.equals("S")) {
			throw new IllegalArgumentException();
		}

		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}

	/**
	 * 물류바코드 중복체크 카운트[TPC_LOGI_BCD TABLE 활용]
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public int selectLogiBcdDuplChkCnt(NEDMPRO0220VO nEDMPRO0220VO) throws Exception {
		return nEDMPRO0220Dao.selectLogiBcdDuplChkCnt(nEDMPRO0220VO);
	}


	///////////////////////////
	@Override
	public HashMap selectBarCodeProductInfo(NEDMPRO0221VO searchParam) {
		// TODO Auto-generated method stub
		return nEDMPRO0220Dao.selectBarCodeProductInfo(searchParam);
	}


	@Override
	public List selectBarcodeList(NEDMPRO0221VO searchParam) {
		// TODO Auto-generated method stub
		return nEDMPRO0220Dao.selectBarcodeList(searchParam);
	}

	/**
	 * 물류바코드 저장 및 RFC 호출
	 */
	/*public void insertNewBarcode(NEDMPRO0221VO nedmpro0221VO) throws Exception {
		NEDMPRO0222VO paramVO = null;

		String srcmkCd = nedmpro0221VO.getSrcmkCd();
		for (int i = 0; nedmpro0221VO.getChgFg().length > i; i++) {
			paramVO = new NEDMPRO0222VO();

			if (nedmpro0221VO.getPgmId() == null || nedmpro0221VO.getPgmId().length <= 0) {
				paramVO.setPgmId("");	// PGM ID  (KEY)
				paramVO.setSeq("");		// 순번 (KEY
			} else {
				paramVO.setPgmId(nedmpro0221VO.getPgmId()[i]);	// PGM ID  (KEY)
				paramVO.setSeq(nedmpro0221VO.getSeq()[i]);		// 순번 (KEY
			}

			paramVO.setSrcmkCd(srcmkCd);
			paramVO.setVenCd(nedmpro0221VO.getVenCd());				// 협력업체코드
			paramVO.setL1Cd(nedmpro0221VO.getL1Cd());				// 대분류코드
			paramVO.setSrcmkCd(nedmpro0221VO.getSrcmkCd());			// 판매(88)코드
			paramVO.setProdCd(nedmpro0221VO.getProdCd());			// 상품코드
			paramVO.setRegId(nedmpro0221VO.getRegId());				// 수정자

			paramVO.setLogiBcd(nedmpro0221VO.getLogiBcd()[i]);		// 물류바코드
			paramVO.setUseFg(nedmpro0221VO.getUseFg()[i]);			// 사용여부
			paramVO.setMixProdFg(nedmpro0221VO.getMixProdFg()[i]);	// 혼재여부 0:비혼재, 1:혼재
			paramVO.setWg(nedmpro0221VO.getWg()[i]);					// 총중량
			paramVO.setWidth(nedmpro0221VO.getWidth()[i]);			// 박스체적 (가로)
			paramVO.setLength(nedmpro0221VO.getLength()[i]);			// 박스체적 (세로)
			paramVO.setHeight(nedmpro0221VO.getHeight()[i]);			// 박스체적 (높이)
			paramVO.setConveyFg(nedmpro0221VO.getConveyFg()[i]);		// 소터에러사유
			paramVO.setSorterFg(nedmpro0221VO.getSorterFg()[i]);		// 소터구분
			paramVO.setwUseFg(nedmpro0221VO.getwUseFg()[i]);			// w사용여부

			if (nedmpro0221VO.getInnerIpsu() == null || nedmpro0221VO.getInnerIpsu().length <= 0) {
				paramVO.setInnerIpsu("0");	// 팔레트(가로박스수)
			} else {
				paramVO.setInnerIpsu(nedmpro0221VO.getInnerIpsu()[i]);	// 팔레트(가로박스수)
			}

			if (nedmpro0221VO.getPltLayerQty() == null || nedmpro0221VO.getPltLayerQty().length <= 0) {
				paramVO.setPltLayerQty("0");		// 팔레트(세로박스수)
			} else {
				paramVO.setPltLayerQty(nedmpro0221VO.getPltLayerQty()[i]);	// 팔레트(세로박스수)
			}

			if (nedmpro0221VO.getPltHeightQty() == null || nedmpro0221VO.getPltHeightQty().length <= 0) {
				paramVO.setPltHeightQty("0");	// 팔레트 (높이박스수)
			} else {
				paramVO.setPltHeightQty(nedmpro0221VO.getPltHeightQty()[i]);	// 팔레트 (높이박스수)
			}

			if (nedmpro0221VO.getwInnerIpsu() == null || nedmpro0221VO.getwInnerIpsu().length <= 0) {
				paramVO.setwInnerIpsu("0");	// w박스수
			} else {
				paramVO.setwInnerIpsu(nedmpro0221VO.getwInnerIpsu()[i]);	// w박스수
			}

			paramVO.setLogiBoxIpsu(nedmpro0221VO.getLogiBoxIpsu()[i]);		// 물류박스 입수
			paramVO.setChgFg(nedmpro0221VO.getChgFg()[i]);					// 변경구분 (I:등록, U:수정)
			paramVO.setCrsdkFg(nedmpro0221VO.getCrsdkFg()[i]);				// 클로스덕 물류바코드 flag 순서/값  0/1
			//paramVO.setSizeUnit(nedmpro0221VO.getSizeUnit()[i]);			// 변경구분 (I:등록, U:수정)
			//paramVO.setWgUnit(nedmpro0221VO.getWgUnit()[i]);				// 클로스덕 물류바코드 flag 순서/값  0/1

			//--[등록 및 수정 구분에 따른  PGM_ID,SEQ  생성 ] ---------------------------------------
			if (paramVO.getChgFg().equals("I") && paramVO.getPgmId().trim().length() <= 0) {
				paramVO.setPgmId(nEDMPRO0220Dao.selectBarcodeList()); // PGM_ID 생성
				paramVO.setSeq("000");								 // SEQ    생성  신규생성은 하나이상 일어나지 않는다.
			}

			//-- MERGE INTO  UPDATE or INSERT -----------------------------------------------
			if (paramVO.getChgFg().equals("I")) {
				nEDMPRO0220Dao.insertNewBarcode(paramVO);			//TPC_LOGI_BCD
			} else {
				//System.out.println(paramVO);
				nEDMPRO0220Dao.updateNewBarcode(paramVO);		//TPC_LOGI_BCD
				nEDMPRO0220Dao.updateNewBarcodeSap(paramVO);		//TPC_LOGI_BCD_SAP(수정요청 시 심사중으로 변경)
			}
			//----------------selectBarcodeListTmp--------------------------------------------------------------
		}

		//rfc 전송

		//-----2.RFC CALL - 등록된 데이터를 다시 조회하여 RFC를 CALL한다.
		nedmpro0221VO.setOrgLogiBcd(paramVO.getLogiBcd());
		List<HashMap> lsHmap = nEDMPRO0220Dao.selectProdInfoToRFC(nedmpro0221VO);	// RFC CALL 넘길 데이터 조회
		HashMap reqCommonMap = new HashMap();	// RFC 응답

		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		JSONObject obj	=	new JSONObject();

		obj.put("LOGI_BCD", lsHmap);			// HashMap에 담긴 데이터 JSONObject로 ...
		obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....

		//logger.debug("obj.toString=" + obj.toString());

		Map<String, Object>	rfcMap	=	rfcCommonService.rfcCall("MST0800", obj.toString(), "");


		//----- 3.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
		String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		//성공이 아니면 실패로 간주한다.
		if (!rtnResult.equals("S")) {
			//throw new Exception();
			throw new Exception();
		}
	}*/
	
	/**
	 * 물류바코드 등록 
	 */
	public String insertNewbarcode(NEDMPRO0220VO vo, HttpServletRequest request) throws Exception {
//		vo.setRegId(vo.getVenCd());
//		vo.setModId(vo.getVenCd());
		
		//============
		// 작업자정보
		//============
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();

		///작업자정보 setting (로그인세션에서 추출)
		vo.setRegId(workId);
		vo.setModId(workId);
		

		//----- 물류바코드 정보 Insert
		String pgmId = "";
		if (vo.getCfgFg().equals("I")) {
			// 신규 PGM_ID 생성
//			pgmId = nEDMPRO0220Dao.selectBarcodeList();
			//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
			pgmId = bosApiService.selectTpcNewProdRegKey();

			vo.setPgmId(pgmId);

			nEDMPRO0220Dao.insertNewBarcode(vo);
		} else {
			nEDMPRO0220Dao.updateNewBarcode(vo);
			nEDMPRO0220Dao.updateNewBarcodeSap(vo);
		}

		//----- RFC 호출
		HashMap rfcHm = nEDMPRO0220Dao.selectProdInfoToRFC(vo);	// RFC CALL 넘길 데이터 조회

		HashMap reqCommonMap = new HashMap();	// RFC 응답
		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		JSONObject obj	=	new JSONObject();

		obj.put("LOGI_BCD", rfcHm);			// HashMap에 담긴 데이터 JSONObject로 ...
		obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....

		//logger.debug("----->" + obj.toString());

		Map<String, Object>	rfcMap = rfcCommonService.rfcCall(vo.getProxyNm(), obj.toString(), "");

		//----- 3.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj 		= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj 	= resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
		String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		//성공이 아니면 실패로 간주한다.
		if (!rtnResult.equals("S")) {
			//throw new Exception();
			throw new IllegalArgumentException();
		}

		return "0";
	}

	/**
	 * 신상품등록현황 조회에서 해당 변형속성이 있는 상품의 물류바코드 등록시 해당 변형순번의 변형명칭 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectVarAttNmInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0220Dao.selectVarAttNmInfo(paramMap);
	}

}
