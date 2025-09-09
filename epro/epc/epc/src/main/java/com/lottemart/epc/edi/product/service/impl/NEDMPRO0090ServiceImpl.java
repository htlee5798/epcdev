package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.*;
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
import com.lottemart.epc.edi.product.dao.NEDMPRO0080Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0090Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0080Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0090Service;

import lcn.module.framework.property.ConfigurationService;

@Service
public class NEDMPRO0090ServiceImpl implements NEDMPRO0090Service{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0090ServiceImpl.class);
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired 
	NEDMPRO0090Dao nedmpro0090dao = new NEDMPRO0090Dao();
	
	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Override
	public List<HashMap> selectGrpAtt(String l3Cd) throws Exception {		
		return nedmpro0090dao.selectGrpAtt(l3Cd);
	}

	@Override
	public List<HashMap> selectGrpAttOpt(String l3Cd) throws Exception {
		return nedmpro0090dao.selectGrpAttOpt(l3Cd);
	}
	
	@Override
	public Integer selectCntGrpReq(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {
		return nedmpro0090dao.selectCntGrpReq(nEDMPRO0090VO);
	}
	
	/**
	 * 분석속성 등록 및 삭제
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveGrpAttOptInfoTmp(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		
		//해당 상품 코드에 등록된 분석속성 삭제
		deleteGrpAttWithProdCd(nEDMPRO0090VO.getProdCd());
		
		String rtnMsg = "S";
		HashMap<String,String> paramMap = new HashMap<String, String>();
		
		try {
		    for (int idx = 0; idx < nEDMPRO0090VO.getGrpAttrId().length; idx++) {
		    	paramMap.put("prodCd"  , nEDMPRO0090VO.getProdCd());
		    	paramMap.put("attId"   , nEDMPRO0090VO.getGrpAttrId()[idx]);
		    	paramMap.put("attValId", nEDMPRO0090VO.getGrpAttrValId()[idx]);
		    	paramMap.put("entpCd"  , nEDMPRO0090VO.getEntpCd());
		    	paramMap.put("workId"  , workId);	//작업자정보
		        nedmpro0090dao.mergeGrpAttOptTmp(paramMap);
		    }
		} catch(Exception e) {
			logger.error(e.getMessage());
		    rtnMsg = "F";
		}
		
		return rtnMsg;
	}
	
    /**
     * 분석속성 삭제
     * @param prodCd
     * @throws Exception
     */
	public void deleteGrpAttWithProdCd(String prodCd) throws Exception {
		nedmpro0090dao.deleteGrpAttWithProdCd(prodCd);
	}

	@Override
	public Integer getMaxSeqAttr(String prodCd) throws Exception {
		String maxSeqAttr = nedmpro0090dao.getMaxSeqAttr(prodCd);
		int maxSeq = 0;
		if (maxSeqAttr != null && !maxSeqAttr.isEmpty()) {
			maxSeq = Integer.parseInt(maxSeqAttr);
		}
		return maxSeq;
	}

	@Override
	public Integer getCntNotResponseAttr(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {
		Integer cntNotResponseAttr = nedmpro0090dao.getCntNotResponseAttr(nEDMPRO0090VO);
		return cntNotResponseAttr;
	}

	@Override
	public List<HashMap> selectGrpAttSelectedOpt(String prodCd) throws Exception {
		List<HashMap> grpAttrOptSaved = null;
		
		try {
		   grpAttrOptSaved = nedmpro0090dao.selectGrpAttrSelectedOptInfo(prodCd);
		
		   if (grpAttrOptSaved.size() < 1) {
			   // ECO에서 값 가져와서 세팅 해주기 
			   grpAttrOptSaved = nedmpro0090dao.selectGrpAttrEcoSavedOptInfo(prodCd);
		   }
		} catch(Exception e) {
			logger.error("selectGrpAttSelectedOpt Error Message : " + e.getMessage());
		}
		return grpAttrOptSaved;
	}
		
	public Boolean isPassValidateBeforeStatus(String msgCd) {
		if (msgCd.startsWith("-")) {
			return false;
		}
		return true;
	}
	
	public String validateBeforeStatus(NEDMPRO0090VO vo, List<HashMap> GrpAttList, List<HashMap> GrpAttrSelectedOptList) {
		String msgCd = "NEEDCHECK"; 
		String chgFg = vo.getChgFg();
		
		try {
			int cntNotResponseAttr = nedmpro0090dao.getCntNotResponseAttr(vo);		
			
			if (GrpAttList.size() != GrpAttrSelectedOptList.size()) {
				return "ERR_NEEDOPTINPUT";
			}
			
			if (cntNotResponseAttr > 0) {
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
	
	public String getResponseMsgModifyStatusAttr(NEDMPRO0090VO vo, List<HashMap> GrpAttList, List<HashMap> GrpAttrSelectedOptList) throws Exception{
		String msgCd = "-1"; 
		String validateCd = validateBeforeStatus(vo, GrpAttList, GrpAttrSelectedOptList);
		    
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

	/**
	 * 상품 분석속성값 저장 상태값 업데이트 (확정취소)
	 */
	@Override
	public String updateModifyStatusAttr(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0090VO.setRegId(workId);
		nEDMPRO0090VO.setModId(workId);
		
		String msgCd = "-1";
				
		try 
		{
			List<HashMap> GrpAttList = nedmpro0090dao.selectGrpAtt(nEDMPRO0090VO.getL3Cd());
			List<HashMap> GrpAttrSelectedOptList = nedmpro0090dao.selectGrpAttrSelectedOptInfo(nEDMPRO0090VO.getProdCd());		
			
			msgCd = getResponseMsgModifyStatusAttr(nEDMPRO0090VO, GrpAttList, GrpAttrSelectedOptList);
			
			if (!isPassValidateBeforeStatus(msgCd)) {
				return msgCd;
			}
			
			//분석속성 변경 가능구분 수정
		    nedmpro0090dao.updateModifyStatusAttr(nEDMPRO0090VO);
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
		
		obj.put("PROD_GRP_ATT_REQ", rfcAttrHMap);
		obj.put("REQCOMMON", reqCommonMap);

		rfcMap = rfcCommonService.rfcCall("MST1360", obj.toString(), "EDI");

		return rfcMap;
	}
	
	public String startRfcCallProcessByAttrOpt(NEDMPRO0090VO nEDMPRO0090VO, List<HashMap> rfcAttrHMap) throws Exception {
		String rtnMsg = "F";
		String rtnResult = "F";
		
		if (rfcAttrHMap.size() > 0) {			
			Map<String, Object> rfcMap = requestRfcCall(rfcAttrHMap);
			rtnResult = getRfcCallReturnMessage(rfcMap);
			rtnMsg = "S";
		}
		
		if (!rtnResult.equals("S")) {
			nedmpro0090dao.deleteGrpAttOptReq(nEDMPRO0090VO);
			rtnMsg = "GRP_ATTR INTERFACE FAIL";
		}	
		
		return rtnMsg;
	}
	// ECO 정합이슈있어 상품코드수 변환
	public String convertProdCd10CharTo18Char(String prodCd) {
		return "00000000" + prodCd;
	}
	
	/**
	 * 요청 분석속성 구성 및 저장 (SAP 전송 데이터와 동일)
	 */
	public List<HashMap> saveParamGrpAttReq(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {
		List<HashMap> rfcAttrHMap = new ArrayList<HashMap>();
		
		for (int idx = 0; idx < nEDMPRO0090VO.getGrpAttrValId().length; idx++) {
	    	HashMap<String,String> paramMap = new HashMap<String, String>();
	    	
	        paramMap.put("SEQ"       , nEDMPRO0090VO.getSeq());
	        paramMap.put("PROD_CD"   , nEDMPRO0090VO.getProdCd());
	        paramMap.put("ATT_ID"    , nEDMPRO0090VO.getGrpAttrId()[idx]);
	        paramMap.put("ATT_VAL_ID", nEDMPRO0090VO.getGrpAttrValId()[idx]);
	        paramMap.put("APRV_FG"   , nEDMPRO0090VO.getAprvFg());
	        paramMap.put("REG_DT"    , nEDMPRO0090VO.getRegDt());
	        paramMap.put("REG_TM"    , nEDMPRO0090VO.getRegTm());
//	        paramMap.put("REG_ID"    , nEDMPRO0090VO.getEntpCd());
	        //250903 로그인세션에 있는 작업자정보 가져오도록 변경
	        paramMap.put("REG_ID"    , nEDMPRO0090VO.getRegId());
	        paramMap.put("MOD_DT"    , "");
	        paramMap.put("MOD_TM"    , "");
	        paramMap.put("MOD_ID"    , "");
	        nedmpro0090dao.insertGrpAttOptReq(paramMap);

	        //인호C, 요청건 보낼때는 상품코드 18자리로 (SAP기준으로)
	        paramMap.put("PROD_CD"   , convertProdCd10CharTo18Char(nEDMPRO0090VO.getProdCd()));
	        
	        rfcAttrHMap.add(paramMap);
	    }

		return rfcAttrHMap;
	}

	/**
	 * 상품 분석속성값 ECO 전송
	 */
	@Override
	public String submitGrpAttrOptInfo(NEDMPRO0090VO nEDMPRO0090VO) throws Exception {		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0090VO.setRegId(workId);
		nEDMPRO0090VO.setModId(workId);
		
		String rtnMsg = "F";
		
		try {
			List<HashMap> rfcAttrHMap;
			//요청 분석속성 구성 및 저장
			rfcAttrHMap = saveParamGrpAttReq(nEDMPRO0090VO);			
			//ECO 전송
			rtnMsg = startRfcCallProcessByAttrOpt(nEDMPRO0090VO, rfcAttrHMap);
		} catch(Exception e) {
			logger.error(e.getMessage());
			nedmpro0090dao.deleteGrpAttOptReq(nEDMPRO0090VO);
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
