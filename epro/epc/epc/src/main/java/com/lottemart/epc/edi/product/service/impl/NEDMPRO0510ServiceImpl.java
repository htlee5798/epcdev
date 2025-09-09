package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.dao.NEDMPRO0500Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0510Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0500Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0510Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0510ServiceImpl.java
 * @Description : 원가변경요청 조회
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.19		yun				최초생성
 *               </pre>
 */
@Service("nEDMPRO0510Service")
public class NEDMPRO0510ServiceImpl implements NEDMPRO0510Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0510ServiceImpl.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0510Dao")
	private NEDMPRO0510Dao nEDMPRO0510Dao;
	
	@Resource(name = "nEDMPRO0500Dao")
	private NEDMPRO0500Dao nEDMPRO0500Dao;
	
	@Resource(name = "nEDMPRO0500Service")
	private NEDMPRO0500Service nEDMPRO0500Service;
	
	
	/**
	 * 원가변경요청정보 현황 조회
	 */
	@Override
	public Map<String, Object> selectTpcProdChgCostDetailView(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		nEDMPRO0500VO.setVenCds(myVenCds);
		
		//원가변경요청상세 아이템리스트
		List<NEDMPRO0500VO> itemList = nEDMPRO0510Dao.selectTpcProdChgCostItemList(nEDMPRO0500VO);
		
		result.put("itemList", itemList);

		return result;
	}

	/**
	 * 원가변경요청정보 복사
	 */
	@Override
	public Map<String, Object> insertCopyTpcProdChgCost(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String reqNo 	= StringUtils.defaultString(nEDMPRO0500VO.getReqNo()); 		//요청번호     
		String srcmkCd 	= StringUtils.defaultString(nEDMPRO0500VO.getSrcmkCd());	//판매코드     
		String prodCd 	= StringUtils.defaultString(nEDMPRO0500VO.getProdCd());		//상품코드     
		String seq 		= StringUtils.defaultString(nEDMPRO0500VO.getSeq()); 		//순번
		String purDept	= StringUtils.defaultString(nEDMPRO0500VO.getPurDept());	//구매조직
		
		/*
		 * 1. 필수데이터 확인
		 */
		//요청번호 누락
		if("".equals(reqNo)) {
			result.put("errMsg", "요청번호가 존재하지 않습니다.");
			return result;
		}
		//seq 누락
		if("".equals(prodCd)) {
			result.put("errMsg", "요청순번이 존재하지 않습니다.");
			return result;
			
		}
		//판매코드 누락
		if("".equals(srcmkCd)) {
			result.put("errMsg", "판매코드 정보가 존재하지 않습니다.");
			return result;
		}
		//상품코드 누락
		if("".equals(prodCd)) {
			result.put("errMsg", "상품코드 정보가 존재하지 않습니다.");
			return result;
		}
		//구매조직 누락
		if("".equals(purDept)) {
			result.put("errMsg", "구매조직 정보가 존재하지 않습니다.");
			return result;
		}
		
		/*
		 * 2. 진행상태 확인
		 */
		NEDMPRO0500VO statusVo 		= new NEDMPRO0500VO();	//상태체크용 vo
		NEDMPRO0500VO statusGrpVo 	= new NEDMPRO0500VO();	//그룹상태체크용 vo
		statusVo.setReqNo(reqNo);			//요청번호
		statusVo.setSrcmkCd(srcmkCd);		//판매코드
		statusVo.setProdCd(prodCd);			//상품코드
		statusVo.setSeq(seq);				//순번
		//선택건 진행상태 조회
		statusVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatus(statusVo);
		//선택건 진행상태
		String curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";
		String grpId = (statusVo != null)?StringUtils.defaultString(statusVo.getGrpId()):"";		//요청그룹아이디
		nEDMPRO0500VO.setGrpId(grpId);
		
		
		//현재상태가 파트너사등록(03)일때만 복사 가능
		if(!"03".equals(curPrStat)) {
			result.put("errMsg", "반려된 요청건만 복사가 가능합니다.");
			return result;
		}
		
		//요청그룹아이디가 있을 경우, 해당 그룹의 상태 모두 체크
		if(!"".equals(grpId)) {
			statusGrpVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatusGrp(statusVo);
			String stsAllRtnYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getStsAllRtnYn()):"N";			//모든 그룹요청건 반려여부
			
			if(!"Y".equals(stsAllRtnYn)) {
				result.put("errMsg", "그룹으로 생성된 데이터 중 반려되지 않은 건이 존재합니다.(그룹아이디:"+grpId+")");
				return result;
			}
		}
		
		/*
		 * 3. 판매코드, 상품코드, 구매조직 동일 건의 최신 data 상태 확인
		 * (임시저장 건이 있는지 확인)
		 */
		statusVo.setPurDept(purDept);	//구매조직
		Map<String,Object> rcntStatusMap = nEDMPRO0500Service.selectCheckProdChgCostSelOkStatus(statusVo, request);
		String rsltCd = MapUtils.getString(rcntStatusMap, "msg");		//결과코드
		String errMsg = MapUtils.getString(rcntStatusMap, "errMsg");	//에러메세지
		if(!"success".equals(rsltCd)) {
			result.put("errMsg", errMsg);
			return result;
		}
		
		
		/*
		 * 4. 복사
		 */
		//복사대상 datalist
		boolean copyOk = false;
		int insOk = 0;
		String newGrpId = "";
		
		if(!"".equals(grpId)) {	//그룹생성대상인 경우
			List<NEDMPRO0500VO> grpList = nEDMPRO0500Dao.selectTpcProdChgCostItemsByGrpId(nEDMPRO0500VO);
			if(grpList == null || grpList.isEmpty() || grpList.size() == 0) {
				result.put("errMsg", "요청정보 일괄 복사에 실패하였습니다.");
				return result;
			}
			
			//슈퍼 or CS일 경우, 신규그룹아이디생성
			if("KR04".equals(purDept) || "KR05".equals(purDept)) {
				newGrpId = nEDMPRO0500Dao.selectTpcProdChgCostGrpId(nEDMPRO0500VO);
			}
			
			//복사
			for(NEDMPRO0500VO vo : grpList) {
				vo.setRegId(workId);
				vo.setModId(workId);
				vo.setGrpId(newGrpId);	//그룹아이디셋팅
				insOk += nEDMPRO0510Dao.insertCopyTpcProdChgCostItem(vo);
			}
			
			copyOk = (grpList.size() == insOk)?true:false;
		}else {					//그룹생성대상이 아닌 경우
			nEDMPRO0500VO.setRegId(workId);
			insOk += nEDMPRO0510Dao.insertCopyTpcProdChgCostItem(nEDMPRO0500VO);
			copyOk = (insOk>0)?true:false;
		}
		
		if(copyOk) {
			result.put("msg", "success");
		}
		
		return result;
	}
	
	
	/**
	 * 원가변경요청 엑셀다운로드 리스트 
	 */
	@Override
	public List<HashMap<String, String>> selectTpcProdChgCostDetailExcelInfo(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
			String chgReqCostDt = nEDMPRO0500VO.getSrchChgReqCostDt(); // "2025-04-15"
			if(chgReqCostDt != null) {
				String formattedDt = chgReqCostDt.replaceAll("-", ""); // "20250415"
				
				nEDMPRO0500VO.setSrchChgReqCostDt(formattedDt);
			}
		
	    List<HashMap<String, String>> returnList = nEDMPRO0510Dao.selectTpcProdChgCostDetailExcelInfo(nEDMPRO0500VO);
	   
	    return returnList;
	}
	
	/**
	 * 원가변경요청정보 수정 저장
	 */
	public Map<String, Object> updateTpcProdChgCostInfo(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		/*
		 * 1. 필수데이터 확인
		 */
		String venCd = StringUtils.defaultString(nEDMPRO0500VO.getVenCd());				//파트너사코드
		String nbPbGbn = StringUtils.defaultString(nEDMPRO0500VO.getNbPbGbn());			//NB,PB구분
		String purDept = StringUtils.defaultString(nEDMPRO0500VO.getPurDept());			//구매조직
		
		//대상상품 저장 list
		List<NEDMPRO0500VO> datalist = nEDMPRO0500VO.getProdDataArr();
		
		//구매조직 누락
		if("".equals(purDept)) {
			result.put("errMsg", "구매조직은 필수 선택 항목입니다.");
			return result;
		}
		
		//파트너사코드 누락
		if("".equals(venCd)) {
			result.put("errMsg", "파트너사는 필수 선택 항목입니다.");
			return result;
		}
				
		//NB,PB 구분 누락
		if("".equals(nbPbGbn)) {
			result.put("errMsg", "상품구분(NB/PB)는 필수 선택 항목입니다.");
			return result;
		}
		
		//저장대상 상품 누락
		if(datalist == null || datalist.size() == 0) {
			result.put("errMsg", "저장 대상 상품 리스트가 존재하지 않습니다.");
			return result;
		}
		
		
		/*
		 * 2. 상세데이터 check
		 */
		//data check
		String reqNo = "";			//요청번호
		String seq = "";			//순번
		String srcmkCd = "";		//판매코드
		String prodCd = "";			//상품코드
		String curPrStat = "";		//현재진행상태
		String contCode = "";		//연계공문아이디
		String grpId = "";			//그룹아이디
		
		String errMsg = "";
		
		String editAvailYn = "";	//수정가능여부
		
		NEDMPRO0500VO statusVo = null;
		NEDMPRO0500VO statusGrpVo = null;
		
		for(NEDMPRO0500VO chkVo : datalist) {
			//공통데이터 셋팅
			chkVo.setVenCd(venCd);		//파트너사코드
			chkVo.setPurDept(purDept);	//구매조직코드
			chkVo.setNbPbGbn(nbPbGbn);	//NB,PB구분
			
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			
			//판매코드 누락
			if("".equals(srcmkCd)) {
				result.put("errMsg", "판매코드는 필수 입력 항목입니다."+errMsg);
				return result;
			}
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			
			//상태정보 조회
			statusVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatus(chkVo);
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";		//진행상태
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//연계공문아이디
			grpId = (statusVo != null)?StringUtils.defaultString(statusVo.getGrpId()):"";			//요청그룹아이디
			
			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "이미 MD협의요청된 데이터는 수정이 불가능합니다."+errMsg);
				return result;
			}
			
			//공문 미생성건만 수정가능
			if(!"".equals(contCode)) {
				result.put("errMsg", "생성된 공문이 존재하여 데이터 수정이 불가능합니다."+errMsg);
				return result;
			}
			
			//요청그룹아이디가 있을 경우, 해당 그룹의 상태 모두 체크
			if(!"".equals(grpId)) {
				statusGrpVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatusGrp(statusVo);
				editAvailYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getEditAvailYn()):"N";	//수정가능여부
				
				if(!"Y".equals(editAvailYn)) {
					result.put("errMsg", "그룹으로 생성된 데이터 중 수정 불가 상태가 존재합니다.(그룹아이디:"+grpId+")"+errMsg);
					return result;
				}
			}
		}
		
		/*
		 * 3. 저장
		 */
		//저장
		int updItemOk = 0;
		for(NEDMPRO0500VO vo : datalist) {
			vo.setModId(workId);
			updItemOk += nEDMPRO0510Dao.updateTpcProdChgCostItem(vo);			
			
			//그룹아이디 존재 시, 동일한 그룹아이디 데이터 일괄수정
			if(!"".equals(StringUtils.defaultString(vo.getGrpId()))) {
				updItemOk += nEDMPRO0510Dao.updateTpcProdChgCostItemGrp(vo);
			}
		}
		
		//성공 시 결과값 셋팅
		if(updItemOk >= datalist.size()) {
			result.put("msg", "success");
		}
		
		return result;
	}

	
	/**
	 * 세션정보 추출
	 * @return EpcLoginVO
	 */
	private EpcLoginVO getWorkSessionVo() {
		EpcLoginVO epcLoginVO = null;
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			
			if(request != null) {
				epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return epcLoginVO;
	}
}
