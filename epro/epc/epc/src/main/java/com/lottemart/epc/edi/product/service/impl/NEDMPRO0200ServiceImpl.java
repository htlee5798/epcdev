package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0200Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0200VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0200Service;

import lcn.module.framework.property.ConfigurationService;

@Service
public class NEDMPRO0200ServiceImpl implements NEDMPRO0200Service{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0200ServiceImpl.class);
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired 
	NEDMPRO0200Dao nedmpro0200dao = new NEDMPRO0200Dao();
	
	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Override
	public List<HashMap> selectNutAtt(String l3Cd) throws Exception {		
		return nedmpro0200dao.selectNutAtt(l3Cd);
	}

	@Override
	public List<HashMap> selectNutAttInfo(String l3Cd) throws Exception {
		return nedmpro0200dao.selectNutAttInfo(l3Cd);
	}
	
    // 영양성분 삭제
	public void deleteNutAmtSaved(String prodCd) throws Exception {
		nedmpro0200dao.deleteNutAmtSaved(prodCd);
	}
	
	@Override
	public Integer selectCntNutReq(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {
		return nedmpro0200dao.selectCntNutReq(nEDMPRO0200VO);
	}

	/**
	 * 영양성분 등록 및 삭제
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String mergeNutAmtTmp(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0200VO.setRegId(workId);
		nEDMPRO0200VO.setModId(workId);
		
		deleteNutAmtSaved(nEDMPRO0200VO.getProdCd());
		
		String rtnMsg = "S";
		HashMap<String,String> paramMap = new HashMap<String, String>();
		
		try {
		    for (int idx = 0; idx < nEDMPRO0200VO.getNutCd().length; idx++) {
		    	paramMap.put("prodCd"  , nEDMPRO0200VO.getProdCd());
		    	paramMap.put("nutCd"   , nEDMPRO0200VO.getNutCd()[idx]);
		    	paramMap.put("nutAmt"  , nEDMPRO0200VO.getNutAmt()[idx]);
		    	paramMap.put("entpCd"  , nEDMPRO0200VO.getEntpCd());
		    	paramMap.put("workId"  , workId);
		    	
		        nedmpro0200dao.mergeNutAmtTmp(paramMap);
		    }
		} catch(Exception e) {
			logger.error(e.getMessage());
		    rtnMsg = "F";
		}
		
		return rtnMsg;
	}

	@Override
	public Integer getProdNutMaxSeq(String prodCd) throws Exception {
		String maxSeqAttr = nedmpro0200dao.getProdNutMaxSeq(prodCd);
		int maxSeq = 0;
		if (maxSeqAttr != null && !maxSeqAttr.isEmpty()) {
			maxSeq = Integer.parseInt(maxSeqAttr);
		}
		return maxSeq;
	}

	@Override
	public Integer getCntNotNutAttRes(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {
		Integer cntNotResponseAttr = nedmpro0200dao.getCntNotNutAttRes(nEDMPRO0200VO);
		return cntNotResponseAttr;
	}

	@Override
	public List<HashMap> selectNutAmtSaved(String prodCd) throws Exception {
		List<HashMap> nutAmtSaved = null;
		
		try {
			nutAmtSaved = nedmpro0200dao.selectNutAmtSaved(prodCd);
		
		   if (nutAmtSaved.size() < 1) {
			   // ECO에서 값 가져와서 세팅 해주기 
			   nutAmtSaved = nedmpro0200dao.selectNutAmtEcoSaved(prodCd);
		   }
		} catch(Exception e) {
			logger.error("selectGrpAttSelectedOpt Error Message : " + e.getMessage());
		}
		return nutAmtSaved;
	}
		
	public Boolean isPassValidateBeforeStatus(String msgCd) {
		if (msgCd.startsWith("-")) {
			return false;
		}
		return true;
	}
	
	public String validateBeforeStatus(NEDMPRO0200VO vo, List<HashMap> nutAttList, List<HashMap> nutAmtSavedList) {
		String msgCd = "NEEDCHECK"; 
		String chgFg = vo.getChgFg();
		
		try {
			int cntNotResNutAtt = nedmpro0200dao.getCntNotNutAttRes(vo);		
			
			if (nutAttList.size() != nutAmtSavedList.size()) {
				return "ERR_NEEDOPTINPUT";
			}
			
			if (cntNotResNutAtt > 0) {
				return "ERR_APRVING";
			}
			
			if (chgFg.equals("M")) {
				return "CANMODIFY";
			}
			
			if (chgFg.equals("C")) {
				return "CANCONFIRM";
			}	
			
		} catch(Exception e) {
			logger.error("validateBeforeStatus Error Message : " + e.getMessage());
		}
		return msgCd;
	}
	
	public String getResponseMsgModifyStatusAttr(NEDMPRO0200VO vo, List<HashMap> nutAttList, List<HashMap> nutAmtSavedList) throws Exception{
		String msgCd = "-1"; 
		String validateCd = validateBeforeStatus(vo, nutAttList, nutAmtSavedList);
		    
		if (validateCd.equals("ERR_NEEDOPTINPUT")) {
		    msgCd = "-2";
		}
		
		if (validateCd.equals("ERR_APRVING")) {
		    msgCd = "-3";
		}
		
		if (validateCd.equals("CANMODIFY")) {
			msgCd = "M";
		}
		
		if (validateCd.equals("CANCONFIRM")) {
			msgCd = "C";
		}
		
		if (validateCd.equals("NEEDCHECK")) {
			msgCd = "E";
		}

	    return msgCd;
	}

	@Override
	public String updateNutModifyStatus(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0200VO.setRegId(workId);
		nEDMPRO0200VO.setModId(workId);
		
		String msgCd = "-1";
		try 
		{
			List<HashMap> nutAttList = nedmpro0200dao.selectNutAtt(nEDMPRO0200VO.getL3Cd());
			List<HashMap> nutAmtList = nedmpro0200dao.selectNutAmtSaved(nEDMPRO0200VO.getProdCd());		
			
			msgCd = getResponseMsgModifyStatusAttr(nEDMPRO0200VO, nutAttList, nutAmtList);
			
			if (!isPassValidateBeforeStatus(msgCd)) {
				return msgCd;
			}
			
		    nedmpro0200dao.updateNutModifyStatus(nEDMPRO0200VO);
		}
		catch (Exception e) {
			logger.error("updateModifyStatusAttr Error Message : " + e.getMessage());
		}
		return msgCd;
	}
	
	public String getRfcCallReturnMessage(Map<String, Object> rfcMap) throws Exception{	
		String rtnResult = "";

		JSONObject mapObj        = new JSONObject(rfcMap.toString());  
		JSONObject resultObj     = mapObj.getJSONObject("result");        
		JSONObject respCommonObj = resultObj.getJSONObject("RESPCOMMON");  
		rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT")); 

		return rtnResult;
	}
	
	public HashMap getRfcReqCommonParam() {
		HashMap reqCommonMap = new HashMap(); // RFC 응답 객체
		
		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");
		
		return reqCommonMap;
	}
	
	public Map<String, Object> requestRfcCall(List<HashMap> rfcAttrHMap) throws Exception {
		JSONObject obj = new JSONObject();
		Map<String, Object> rfcMap = new HashMap();
		HashMap reqCommonMap = getRfcReqCommonParam(); // RFC 응답 객체	
		
		obj.put("PROD_NUT_ATT_REQ", rfcAttrHMap);
		obj.put("REQCOMMON", reqCommonMap);

		rfcMap = rfcCommonService.rfcCall("MST1381", obj.toString(), "EDI");

		return rfcMap;
	}
	
	public String startRfcCallProcessByAmt(NEDMPRO0200VO nEDMPRO0200VO, List<HashMap> rfcAttrHMap) throws Exception {
		String rtnMsg = "F";
		String rtnResult = "F";
		
		if (rfcAttrHMap.size() > 0) {			
			Map<String, Object> rfcMap = requestRfcCall(rfcAttrHMap);
			rtnResult = getRfcCallReturnMessage(rfcMap);
			rtnMsg = "S";
		}
		
		if (!rtnResult.equals("S")) {
			nedmpro0200dao.deleteNutAmtReq(nEDMPRO0200VO);
			rtnMsg = "GRP_ATTR INTERFACE FAIL";
		}	
		
		return rtnMsg;
	}
	// ECO 정합이슈있어 상품코드수 변환
	public String convertProdCd10CharTo18Char(String prodCd) {
		return "00000000" + prodCd;
	}
	
	public List<HashMap> saveParamNutAmtReq(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {
		List<HashMap> rfcAttrHMap = new ArrayList<HashMap>();
		
		for (int idx = 0; idx < nEDMPRO0200VO.getNutCd().length; idx++) {
	    	HashMap<String,String> paramMap = new HashMap<String, String>();
	    	
	        paramMap.put("SEQ"       , nEDMPRO0200VO.getSeq());
	        paramMap.put("PROD_CD"   , nEDMPRO0200VO.getProdCd());
	        paramMap.put("NUT_CD"    , nEDMPRO0200VO.getNutCd()[idx]);
	        paramMap.put("NUT_AMT"   , nEDMPRO0200VO.getNutAmt()[idx]);
	        paramMap.put("APRV_FG"   , nEDMPRO0200VO.getAprvFg());
	        paramMap.put("REG_DT"    , nEDMPRO0200VO.getRegDt());
	        paramMap.put("REG_TM"    , nEDMPRO0200VO.getRegTm());
//	        paramMap.put("REG_ID"    , nEDMPRO0200VO.getEntpCd());
	        //250903 로그인세션에 있는 작업자정보 가져오도록 변경
	        paramMap.put("REG_ID"    , nEDMPRO0200VO.getRegId());
	        paramMap.put("MOD_DT"    , "");
	        paramMap.put("MOD_TM"    , "");
	        paramMap.put("MOD_ID"    , "");
	        nedmpro0200dao.insertNutAmtReq(paramMap);

	        //인호C, 요청건 보낼때는 상품코드 18자리로 (SAP기준으로)
	        paramMap.put("PROD_CD"   , convertProdCd10CharTo18Char(nEDMPRO0200VO.getProdCd()));
	        
	        rfcAttrHMap.add(paramMap);
	    }

		return rfcAttrHMap;
	}

	/**
	 * 상품 영양성분값 ECO 전송
	 */
	@Override
	public String submitNutAmtInfo(NEDMPRO0200VO nEDMPRO0200VO) throws Exception {		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0200VO.setRegId(workId);
		nEDMPRO0200VO.setModId(workId);
		
		String rtnMsg = "F";
		
		try {
			List<HashMap> rfcAttrHMap;
			rfcAttrHMap = saveParamNutAmtReq(nEDMPRO0200VO);			
			rtnMsg = startRfcCallProcessByAmt(nEDMPRO0200VO, rfcAttrHMap);
		} catch(Exception e) {
			logger.error(e.getMessage());
			nedmpro0200dao.deleteNutAmtReq(nEDMPRO0200VO);
			rtnMsg = "F";
		}
		
		return rtnMsg;
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
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			workId = epcLoginVO.getLoginWorkId();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return workId;
	}
}
