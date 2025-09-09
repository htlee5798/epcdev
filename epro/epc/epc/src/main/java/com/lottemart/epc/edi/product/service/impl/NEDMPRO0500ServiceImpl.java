package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.EcsUtil;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0500Dao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0500Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0500ServiceImpl.java
 * @Description : 원가변경요청 
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
@Service("nEDMPRO0500Service")
public class NEDMPRO0500ServiceImpl implements NEDMPRO0500Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0500ServiceImpl.class);
	
	private static final String ECS_GB = "PR";

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0500Dao")
	private NEDMPRO0500Dao nEDMPRO0500Dao;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Resource(name="commonProductService")
	private CommonProductService commonProductService;
	
	@Autowired
	EcsUtil ecsUtil;
	
	/**
	 * 원가변경 상세정보 조회
	 */
	@Override
	public Map<String, Object> selectTpcProdChgCostDetail(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		nEDMPRO0500VO.setVenCds(myVenCds);
		
		//원가변경요청상세 아이템리스트
		List<NEDMPRO0500VO> itemList = nEDMPRO0500Dao.selectTpcProdChgCostItemList(nEDMPRO0500VO);
		
		result.put("itemList", itemList);

		return result;
	}

	/**
	 * 원가변경요청정보 저장
	 */
	public Map<String, Object> insertTpcProdChgCostInfo(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		/*
		 * 1. 필수데이터 확인
		 */
		String venCd = StringUtils.defaultString(nEDMPRO0500VO.getVenCd());			//파트너사코드
		String nbPbGbn = StringUtils.defaultString(nEDMPRO0500VO.getNbPbGbn());		//NB,PB구분
		String[] purDepts = nEDMPRO0500VO.getPurDepts();							//구매조직 array
		
		//대상상품 저장 list
		List<NEDMPRO0500VO> datalist = nEDMPRO0500VO.getProdDataArr();
		
		//구매조직 누락
		if(purDepts == null || purDepts.length == 0) {
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
		
		/**
		 * 구매조직 filtering
		 * 1. 중복이 있을 경우, distinct 처리
		 * 2. 구매조직에 따른 data 일괄생성
		 * 	- 슈퍼 선택	===> CS 동시 생성
		 * 	- CS  선택	===> 슈퍼 동시 생성
		 */
//		String groupId = "";			//동시 생성되는 groupId
		//=================================
		List<String> purDeptList = new ArrayList<>(Arrays.asList(purDepts));			//구매조직 array to list
		
		if(purDeptList.contains("KR04") || purDeptList.contains("KR05")) {				//구매조직 = 슈퍼, CS 는 항상 같이 생성 
			purDeptList.add("KR04");	//구매조직추가 - 슈퍼
			purDeptList.add("KR05");	//구매조직추가 - CS
		}
		
		//구매조직 중복 제거
		purDeptList = purDeptList.stream().distinct().collect(Collectors.toList());
		//=================================
		
		
		/*
		 * 2. 상세데이터 check
		 */
		//data check
		String reqNo = "";			//요청번호
		String seq = "";			//순번
		String srcmkCd = "";		//판매코드
		String prodCd = "";			//상품코드
		
		String errMsg = "";
		
		for(NEDMPRO0500VO chkVo : datalist) {
			//공통데이터 셋팅
			chkVo.setVenCd(venCd);		//파트너사코드
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
		}
		
		
		/*
		 * 3. 저장
		 */
		
		String grpId = "";
		Map<String,String> deptGrpMap = null;
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		//저장
		for(NEDMPRO0500VO vo : datalist) {
			deptGrpMap = new HashMap<String,String>();
			
			//구매조직 = 슈퍼 OR CS포함일 경우, 그룹아이디 생성
			if(purDeptList.contains("KR04") || purDeptList.contains("KR05")) {
				grpId = nEDMPRO0500Dao.selectTpcProdChgCostGrpId(vo);
				deptGrpMap.put("KR04", grpId);
				deptGrpMap.put("KR05", grpId);
			}
			
			//구매조직 별 n건 생성
			for(String purDept : purDeptList) {
				//해당 구매조직에 대한 그룹아이디 있을 경우 setting
				if(deptGrpMap != null && !deptGrpMap.isEmpty() && deptGrpMap.containsKey(purDept)) {
					vo.setGrpId(MapUtils.getString(deptGrpMap, purDept));
				}else {
					vo.setGrpId("");
				}
				vo.setPurDept(purDept);
				
				//작업자 세팅
				vo.setRegId(workId);
				
				//저장
				nEDMPRO0500Dao.insertTpcProdChgCostItem(vo);
			}
		}
		
		//성공 시 결과값 셋팅
		result.put("msg", "success");
		return result;
	}
	
	/**
	 * 원가변경요청 아이템 정보 삭제
	 */
	@Override
	public Map<String, Object> deleteTpcProdChgCostItem(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		List<NEDMPRO0500VO> prodDataArr = nEDMPRO0500VO.getProdDataArr();			//삭제대상 list
		
		/*
		 * 1. 필수데이터 확인
		 */
		//삭제대상 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "삭제 대상 상품을 최소 1건 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. 진행상태 확인
		 */
		String reqNo = ""; //요청번호     
		String srcmkCd = ""; //판매코드     
		String prodCd = ""; //상품코드     
		String seq = ""; //순번
		String contCode = "";		//연계공문아이디
		String grpId = "";			//그룹아이디

		String errMsg = "";
		
		String editAvailYn = "";	//수정가능여부
		
		NEDMPRO0500VO statusVo = null;	//상태체크용 vo
		NEDMPRO0500VO statusGrpVo = null;
		
		String curPrStat = "";			//현재진행상태
		for(NEDMPRO0500VO chkVo : prodDataArr) {
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			
			//요청번호 누락
			if("".equals(reqNo)) {
				result.put("errMsg", "요청번호가 존재하지 않습니다."+errMsg);
				return result;
			}
			//seq 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "요청순번이 존재하지 않습니다."+errMsg);
				return result;
			}
			//판매코드 누락
			if("".equals(srcmkCd)) {
				result.put("errMsg", "판매코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			
			//상태정보 조회
			statusVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatus(chkVo);
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//연계공문아이디
			grpId = (statusVo != null)?StringUtils.defaultString(statusVo.getGrpId()):"";			//요청그룹아이디
			
			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "승인 대기중이거나 이미 승인/반려된 건은 삭제가 불가능합니다."+errMsg);
				return result;
			}
			
			//공문 미생성건만 삭제가능
			if(!"".equals(contCode)) {
				result.put("errMsg", "생성된 공문이 존재하여 데이터 삭제가 불가능합니다."+errMsg);
				return result;
			}
			
			//요청그룹아이디가 있을 경우, 해당 그룹의 상태 모두 체크
			if(!"".equals(grpId)) {
				statusGrpVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatusGrp(statusVo);
				editAvailYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getEditAvailYn()):"N";	//수정가능여부
				
				if(!"Y".equals(editAvailYn)) {
					result.put("errMsg", "그룹으로 생성된 데이터 중 삭제 불가 상태가 존재합니다.(그룹아이디:"+grpId+")"+errMsg);
					return result;
				}
			}
		}
		
		/*
		 * 3. 삭제
		 */
		int delOk = 0;
		for(NEDMPRO0500VO vo : prodDataArr) {
			//그룹아이디 동일 건 모두 삭제
			delOk += nEDMPRO0500Dao.deleteTpcProdChgCostItemGrp(vo);
			
			//선택한 data 삭제
			delOk += nEDMPRO0500Dao.deleteTpcProdChgCostItem(vo); 
		}
		
		if(delOk > 0) {
			result.put("msg", "success");
		}
		return result;
	}

	/**
	 * 원가변경요청 판매코드 선택가능 여부 확인
	 */
	@Override
	public Map<String, Object> selectCheckProdChgCostSelOkStatus(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		result.put("errMsg", "등록 불가 상태입니다.");
		
		String srcmkCd = StringUtils.defaultString(nEDMPRO0500VO.getSrcmkCd());		//판매코드     
		String prodCd = StringUtils.defaultString(nEDMPRO0500VO.getProdCd());		//상품코드
		String purDept = StringUtils.defaultString(nEDMPRO0500VO.getPurDept());		//구매조직 single
		String[] purDepts = nEDMPRO0500VO.getPurDepts();							//구매조직 array
		
		//상품코드, 판매코드, 구매조직 별 최신 상태 조회
		NEDMPRO0500VO rcntVo = nEDMPRO0500Dao.selectTpcProdChgCostItemRcntStatus(nEDMPRO0500VO);
		
		int prStsCnt00 = Integer.parseInt(rcntVo.getPrStsCnt00());		//임시저장상태 요청건수
		int prStsCnt01 = Integer.parseInt(rcntVo.getPrStsCnt01());		//승인대기상태 요청건수
		
		String prStsDept00 = StringUtils.defaultString(rcntVo.getPrStsDept00());	//임시저장상태 구매조직
		String prStsDept01 = StringUtils.defaultString(rcntVo.getPrStsDept01());	//승인대기상태 구매조직
		
		//임시저장 건이 있을 경우, 추가불가
		if(prStsCnt00 > 0) {
			result.put("errMsg", "동일한 판매/상품코드에 대한 임시저장 건이 존재합니다.\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+"/구매조직:"+prStsDept00+")");
			return result;
		}
		
		//승인대기 건이 있을 경우, 추가불가
		if(prStsCnt01 > 0) {
			result.put("errMsg", "원가변경요청 승인 대기중입니다.\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+"/구매조직:"+prStsDept01+")");
			return result;
		}
		
		
		/*
		//상품코드/판매코드 별 최신 상태 정보조회 
		NEDMPRO0500VO rcntVo = nEDMPRO0500Dao.selectTpcProdChgCostItemRcntStatus(nEDMPRO0500VO);
		String prStat = (rcntVo == null)?"":StringUtils.defaultString(rcntVo.getPrStat());	//진행상태
		
		switch(prStat) {
			case "00":	//임시저장
				result.put("errMsg", "동일한 판매/상품코드에 대한 임시저장 건이 존재합니다.\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")");
				return result;
			case "01":	//승인대기
				result.put("errMsg", "원가변경요청 승인 대기중입니다.\n동일한 판매/상품코드에 대한 요청이 불가능합니다.\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")");
				return result;
			case "02":	//승인
			case "03":	//반려
			default:
				break;
		}
		*/
		
		result.put("msg", "success");
		return result;
	}
	
	/**
	 * 원가변경요청 공문생성
	 */
	public Map<String,Object> insertCreDcDocProChgCost(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		List<NEDMPRO0500VO> prodDataArr = nEDMPRO0500VO.getProdDataArr();			//공문생성대상 list

		/*
		 * 1. 필수데이터 확인
		 */
		//공문생성대상 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "공문생성 대상 상품을 최소 1건 이상 선택해주세요.");
			return result;
		}

		String ecsWrtId = StringUtils.defaultString(nEDMPRO0500VO.getEcsWrtId());
		String purDept = StringUtils.defaultString(nEDMPRO0500VO.getPurDept());			//구매조직
		String freshStdYn = StringUtils.defaultString(nEDMPRO0500VO.getFreshStdYn());	//신선규격상품여부
		String taxFg = StringUtils.defaultString(nEDMPRO0500VO.getTaxFg());				//면과세구분
		String dcCreGbn = StringUtils.defaultString(nEDMPRO0500VO.getDcCreGbn());		//공문생성구분
		String ecsReceiverTeamCd = StringUtils.defaultString(nEDMPRO0500VO.getEcsReceiverTeamCd());	//수신담당자 팀코드
		String ecsReceiverEmpNo = StringUtils.defaultString(nEDMPRO0500VO.getEcsReceiverEmpNo());	//수신담당자 사번
		
		//ECS 아이디 미입력
		if("".equals(ecsWrtId)) {
			result.put("errMsg", "공문발송을 위해 ECS 아이디를 입력하셔야 합니다.");
			return result;
		}
		
		//구매조직 누락
		if("".equals(purDept)) {
			result.put("errMsg", "공문발송을 위해 구매조직은 필수 선택 항목입니다.");
			return result;
		}
		
		//신선규격구분 누락
		if("".equals(freshStdYn)) {
			result.put("errMsg", "공문발송을 위해 신선상품여부는 필수 선택 항목입니다.");
			return result;
		}
		
		//신선규격구분 누락
		if("Y".equals(freshStdYn) && "".equals(taxFg)) {
			result.put("errMsg", "신선 상품일 경우, 과세 유형은 필수 선택 항목입니다.");
			return result;
		}
		
		//공문생성 수신처 구분
		if("".equals(dcCreGbn)) {
			result.put("errMsg", "공문발송을 위한 수신처는 필수 선택 항목입니다.");
			return result;
		}
		
		//공문생성 수신담당자 사번
		if("".equals(ecsReceiverEmpNo)) {
			result.put("errMsg", "공문발송을 위한 수신담당자 사번이 존재하지 않습니다.");
			return result;
		}
		
		//공문생성 수신담당자 팀코드
		if("".equals(ecsReceiverTeamCd)) {
			result.put("errMsg", "공문발송을 위한 수신담당자 팀 정보가 존재하지 않습니다.");
			return result;
		}
		
		/*
		 * 2. 상태 확인
		 */
		List<NEDMPRO0500VO> dcCreProdDataArr = null;			//공문생성대상의 요청정보
		String dcCreGbnDef = nEDMPRO0500Dao.selectDcCreDefGbnForPurDept(purDept); 		//구매조직 별 기본 공문 구분
		//그룹화된 다른 구매조직의 공문생성 여부 flag
		boolean dcGrpCreYn = false;
		
		//공문생성유형이 현재 구매조직의 공문과 동일한지 체크
		if(!(dcCreGbn).equals(StringUtils.defaultString(dcCreGbnDef))) {
			dcGrpCreYn = true;
			
			//그룹화된 다른 구매조직의 요청 list 조회
			List<NEDMPRO0500VO> grpProdDataArr = nEDMPRO0500Dao.selectTpcProdChgCostItemsToDcCreGrp(nEDMPRO0500VO);
			//그룹화된 다른 구매조직의 요청 list가 조회되지 않았거나, 선택한 건보다 적을 경우.....
			if(grpProdDataArr == null || grpProdDataArr.isEmpty() || grpProdDataArr.size() == 0 || prodDataArr.size() < grpProdDataArr.size()) {
				result.put("errMsg", "그룹화된 요청 건의 요청 정보가 존재하지 않습니다.");
				return result;
			}
			
			//ECS 최대 생성가능건수 (100row) 넘어가면 생성 불가
			if(grpProdDataArr.size() > 100) {
				result.put("errMsg", "그룹화된 요청 건의 요청 정보가 "+grpProdDataArr.size()+"건입니다.\n공문 생성은 최대 100건에 한하여 일괄 생성이 가능합니다.\n일부를 선택해제 후 다시 시도해주세요.");
				return result;
			}
			
			//공문생성대상 = 현재 선택한 건과 그룹화된 요청건
			dcCreProdDataArr = grpProdDataArr;
		}else {
			//공문생성대상 = 현재 선택한 요청건
			dcCreProdDataArr = prodDataArr;
		}
		
		String reqNo 	= ""; 	//요청번호     
		String srcmkCd	= ""; 	//판매코드     
		String prodCd	= ""; 	//상품코드     
		String seq		= "";	//순번
		String dcNum	= "";	//공문번호
		String dcStat	= "";	//공문진행상태
		String contCode = "";	//공문연계번호
		String grpId	= "";	//요청그룹아이디
		String dcCreAllYn = "";	//모든 공문생성 완료여부
		
		NEDMPRO0500VO statusVo = null;		//상태체크용 vo
		NEDMPRO0500VO statusGrpVo = null;	//그룹상태체크용 vo
		String curPrStat = "";				//현재진행상태
		String errMsg = "";
		
		for(NEDMPRO0500VO chkVo : dcCreProdDataArr) {
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			//요청번호 누락
			if("".equals(reqNo)) {
				result.put("errMsg", "요청번호가 존재하지 않습니다."+errMsg);
				return result;
			}
			//seq 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "요청순번이 존재하지 않습니다."+errMsg);
				return result;
			}
			//판매코드 누락
			if("".equals(srcmkCd)) {
				result.put("errMsg", "판매코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			
			//상태정보 조회
			statusVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatus(chkVo);
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//연계공문아이디
			grpId = (statusVo != null)?StringUtils.defaultString(statusVo.getGrpId()):"";			//요청그룹아이디

			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "승인 대기중이거나 이미 승인/반려된 건은 공문생성 요청이 불가능합니다."+errMsg);
				return result;
			}
			
			//기등록 공문이 있을 경우
			if(!"".equals(dcNum) || !"".equals(contCode)) {
				result.put("errMsg", "이미 생성된 공문이 존재합니다."+errMsg);
				return result;
			}
			
			//요청그룹아이디가 있을 경우, 해당 그룹의 상태 모두 체크
			if(!"".equals(grpId)) {
				statusGrpVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatusGrp(statusVo);
				dcCreAllYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getDcCreAllYn()):"N";	//모든공문생성완료여부
				
				//모든 공문이 생성된 건일 경우, 추가생성불가
				if("Y".equals(dcCreAllYn)) {
					result.put("errMsg", "이미 모든 공문이 존재합니다."+errMsg);
					return result;
				}
			}
		}
		
		/*
		 * 3. 공문생성
		 */
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		NEDMPRO0500VO dcVo = new NEDMPRO0500VO();
		dcVo.setRegId(workId);
		dcVo.setModId(workId);
		dcVo.setProdDataArr(dcCreProdDataArr);		//공문생성을 위한 요청정보 셋팅
		
		//공문생성 전, 현재 원가정보를 원가변경요청정보테이블(TPC_PROD_CHG_COST_ITEM)에 업데이트
		int updRcntOrgCost = nEDMPRO0500Dao.updateTpcProdChgCostItemRcntOrgCost(dcVo);
		
		/*************************** 공문 양식 정보 셋팅 *************************/
		String sysCode = "PC";	//시스템구분(EPC- PC로 고정)
		String depCd = "";		//ECS 관리코드(FRM_LARGE_CD)
		String linCd = "";		//ECS 관리코드(FRM_MIDDLE_CD)
		String dcCd = "";		//계약명코드(BUN_CD)
		String dcNmCd = "";		//계약서코드
		
		String recvCompNum	= "";	//수신처사업자번호(마트/슈퍼/씨에스)
		String recvCompName = "";	//수신처사업자명(마트/슈퍼/씨에스)
		
		String bXml = "";			//계약서 양식 상세내용구성 (body)
		
		//ECS 수신 업체정보 조회용 vo
		CommonProductVO ecsRecvCompVo = new CommonProductVO();
		ecsRecvCompVo.setZzorg(dcCreGbn);	//계열사코드 setting
		ecsRecvCompVo = commonProductService.selectEcsRecvCompInfo(ecsRecvCompVo);
		
		recvCompNum = (ecsRecvCompVo != null)?StringUtils.defaultString(ecsRecvCompVo.getEcsRecvCompNum(), ""):"";		//ECS 수신업체 사업자번호
		recvCompName = (ecsRecvCompVo != null)?StringUtils.defaultString(ecsRecvCompVo.getEcsRecvCompName(), ""):"";	//ECS 수신업체명
		
		//공문 수신 업체 사업자번호 조회
		if("".equals(recvCompNum)) {
			result.put("errMsg", "공문 수신처 사업자정보가 존재하지 않아 공문 발송이 불가능합니다.");
			return result;
		}
		
		//ECS 양식정보 START ==============================================================
		Map<String,String> ecsDocInfo = null;
		String docGbn = "";		//ECS 양식구분코드
		
		switch(dcCreGbn) {
			case "MT":	//MT
				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
					docGbn = "002";
				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
					docGbn = "003";
				}else { //--------------------------------------------------일반
					docGbn = "001";
				}
				break;
			case "SP":	//슈퍼
				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
					docGbn = "102";
				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
					docGbn = "103";
				}else { //--------------------------------------------------일반
					docGbn = "101";
				}
				break;
			case "CS":	//CS
				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
					docGbn = "202";
				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
					docGbn = "203";
				}else { //--------------------------------------------------일반
					docGbn = "201";
				}
				break;
			default:
				result.put("errMsg", "유효하지 않은 공문 생성 유형입니다.");
				return result;
		}
		
		//ECS 양식정보 조회
		ecsDocInfo = commonProductService.selectEcsDocInfo(ECS_GB, docGbn);
		String ecsDocGbn = ECS_GB+docGbn;
		
		//ECS 양식정보 없음
		if(ecsDocInfo == null) {
			result.put("errMsg", "공문 양식정보가 존재하지 않습니다.");
			return result;
		}
		
		depCd = MapUtils.getString(ecsDocInfo, "DEP_CD", "");		//대분류코드_ECS 관리코드(FRM_LARGE_CD)
		linCd = MapUtils.getString(ecsDocInfo, "LIN_CD", "");		//중분류코드_ECS 관리코드(FRM_MIDDLE_CD)
		dcCd = MapUtils.getString(ecsDocInfo, "DC_CD", "");			//계약명코드(BUN_CD)
		dcNmCd = MapUtils.getString(ecsDocInfo, "DC_NM_CD", "");	//계약서코드
		
		//양식정보 누락건 존재
		if("".equals(depCd) || "".equals(linCd) || "".equals(dcCd) || "".equals(dcNmCd)) {
			logger.error(String.format("ECS_DOC_INFO IS EMPTY ::: DEP_CD : %s / LIN_CD : %s / DC_CD : %s / DC_NM_CD : %s", depCd, linCd, dcCd, dcNmCd));
			result.put("errMsg", "공문 양식정보가 올바르지 않습니다.");
			return result;
		}

		
//		switch(dcCreGbn) {
//			case "SP":	//슈퍼
//				//양식정보
//				depCd = "511";		//ECS 관리코드(FRM_LARGE_CD)
//				linCd = "001";		//ECS 관리코드(FRM_MIDDLE_CD)
//				
//				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
//					dcCd = "12663";		//계약명코드(BUN_CD)
//					dcNmCd = "15116";	//계약서코드
//				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
//					dcCd = "12664";		//계약명코드(BUN_CD)
//					dcNmCd = "15117";	//계약서코드
//				}else { //--------------------------------------------------일반
//					dcCd = "12662";		//계약명코드(BUN_CD)
//					dcNmCd = "15115";	//계약서코드
//				}
//				break;
//			case "CS":
//				//사업자정보
//				//양식정보
//				depCd = "512";		//ECS 관리코드(FRM_LARGE_CD)
//				linCd = "001";		//ECS 관리코드(FRM_MIDDLE_CD)
//				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
//					dcCd = "12668";		//계약명코드(BUN_CD)
//					dcNmCd = "15124";	//계약서코드
//				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
//					dcCd = "12669";		//계약명코드(BUN_CD)
//					dcNmCd = "15125";	//계약서코드
//				}else { //--------------------------------------------------일반
//					dcCd = "12667";		//계약명코드(BUN_CD)
//					dcNmCd = "15123";	//계약서코드
//				}
//				break;
//			case "MT":
//				//사업자정보
//				//양식정보
//				depCd = "510";		//ECS 관리코드(FRM_LARGE_CD)
//				linCd = "007";		//ECS 관리코드(FRM_MIDDLE_CD)
//				
//				if("Y".equals(freshStdYn) && "0".equals(taxFg)) { //---------신선,면세
//					dcCd = "12655";		//계약명코드(BUN_CD)
//					dcNmCd = "15106";	//계약서코드
//				}else if("Y".equals(freshStdYn) && "1".equals(taxFg)) { //---신선,과세
//					dcCd = "12656";		//계약명코드(BUN_CD)
//					dcNmCd = "15107";	//계약서코드
//				}else { //--------------------------------------------------일반
//					dcCd = "12654";		//계약명코드(BUN_CD)
//					dcNmCd = "15105";	//계약서코드
//				}
//				break;
//			default:
//				result.put("errMsg", "유효하지 않은 공문 생성 유형입니다.");
//				return result;
//		}
		//ECS 양식정보 END ==============================================================
		
		//TODO_JIA 추후 분기처리필요
		//마트 양식
		bXml =  this.getProdChgCostDcDocMartNormal(dcVo, dcNmCd);
		
		//계약서 양식 상세내용구성 (body)실패시
		if("".equals(bXml)) {
			result.put("errMsg", "공문내용 구성 중 오류가 발생하였습니다.");
			return result;
		}
		
		/*************************** 공문 생성 *************************/
		//EPC 내부관리코드생성
		contCode = nEDMPRO0500Dao.selectEcsContCode(dcVo);
		dcVo.setContCode(contCode);
		
		//Header ------------------------------------------------------------------
		Map<String,Object> hMap = new HashMap<String,Object>();
		hMap.put("FAMILY_YN", "N");					//계열사구분
		hMap.put("CONT_CODE", contCode);			//EPC 내부 관리번호
		hMap.put("DC_NAME", "원가계약변경요청 공문");		//계약명
		hMap.put("DC_CONDATE", DateUtil.getCurrentDay("yyyyMMdd"));		//계약일자
		hMap.put("DEP_CD", depCd);					//ECS관리코드(FRM_LARGE_CD)
		hMap.put("LIN_CD", linCd);					//ECS관리코드(FRM_MIDDLE_CD)
		hMap.put("DC_CD", dcCd);					//계약명코드(BUN_CD)
		hMap.put("DC_NM_CD", dcNmCd);				//계약서코드
		
		String hXml = EcsUtil.createHeader(hMap);	//header Xml 
		
		//body --------------------------------------------------------------------
		StringBuilder body = new StringBuilder();
		//공통 - 수신업체정보
		Map<String,Object> recvCompMap = new HashMap<String,Object>();
		recvCompMap.put("COMP_NUM", recvCompNum);		//사업자번호
		recvCompMap.put("COMP_NAME", recvCompName);		//사업자명
		recvCompMap.put("TEAM_CD", ecsReceiverTeamCd);	//팀코드
		recvCompMap.put("EMP_SABUN", ecsReceiverEmpNo);	//사번
		
		String recvCompXml = EcsUtil.getMapData("COMP_LIST", recvCompMap);
		body.append(recvCompXml);
		
		//공통 - 파트너사정보(발신)
		Map<String,Object> vendorInfoMap = new HashMap<String,Object>();
		vendorInfoMap.put("USER_ID", ecsWrtId);			//파트너사작성자ID
		vendorInfoMap.put("CONT_CODE", contCode);		//연계계약번호(EPC내부관리번호)		
		vendorInfoMap.put("SYS_CODE", sysCode);			//시스템구분
		
		String sendCompXml = EcsUtil.getMapData("IF_RECV_OFFICIAL", vendorInfoMap);
		body.append(sendCompXml);
		body.append(bXml);
		
		//ECS 전송
		Map<String,Object> ecsResultMap = ecsUtil.sendEcsDcDoc(ecsDocGbn, hXml, body.toString());
		
		//ECS 전송 실패 시, 오류코드 반환
		if(!MapUtils.getBooleanValue(ecsResultMap, "result")) {
			result.put("errCode", MapUtils.getString(ecsResultMap, "errCode"));
			result.put("errMsg", MapUtils.getString(ecsResultMap, "errMsg"));
			
			
			//공문생성 실패 시, 원가변경요청정보테이블(TPC_PROD_CHG_COST_ITEM)의 원가 원복
			int updOrgCostClr = nEDMPRO0500Dao.updateTpcProdChgCostItemOrgCostClear(dcVo);
			
			return result;
		}
		
		/*
		 * 4. 공문번호 업데이트
		 */
		int updOk = nEDMPRO0500Dao.updateDcContCode(dcVo);
		
		if(updOk > 0) {
			result.put("url", MapUtils.getString(ecsResultMap, "url"));	//ECS 반환 URL
			result.put("msg", "success");
		}
		
		return result;
	}
	
	private Map<String,Object> insertCreDcDocProChgCost_old250513(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		List<NEDMPRO0500VO> prodDataArr = nEDMPRO0500VO.getProdDataArr();			//공문생성대상 list
		
		/*
		 * 1. 필수데이터 확인
		 */
		//공문생성대상 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "공문생성 대상 상품을 최소 1건 이상 선택해주세요.");
			return result;
		}
		
		//ECS 아이디 미입력
		String ecsWrtId = StringUtils.defaultString(nEDMPRO0500VO.getEcsWrtId());
		if("".equals(ecsWrtId)) {
			result.put("errMsg", "공문발송을 위해 ECS 아이디를 입력하셔야 합니다.");
			return result;
		}
		
		/*
		 * 2. 진행상태 확인
		 */
		String reqNo = ""; //요청번호     
		String srcmkCd = ""; //판매코드     
		String prodCd = ""; //상품코드     
		String seq = ""; //순번
		String dcNum = "";	//공문번호
		String dcStat = "";	//공문진행상태
		String contCode="";	//공문연계번호
		
		NEDMPRO0500VO statusVo = null;	//상태체크용 vo
		String curPrStat = "";			//현재진행상태
		String errMsg = "";
		
		for(NEDMPRO0500VO chkVo : prodDataArr) {
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			
			//요청번호 누락
			if("".equals(reqNo)) {
				result.put("errMsg", "요청번호가 존재하지 않습니다."+errMsg);
				return result;
			}
			//seq 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "요청순번이 존재하지 않습니다."+errMsg);
				return result;
			}
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
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";	//현재진행상태
			dcNum = (statusVo != null)?StringUtils.defaultString(statusVo.getDcNum()):"";		//공문번호
			dcStat = (statusVo != null)?StringUtils.defaultString(statusVo.getDcStat()):"";		//공문진행상태
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//공문연계번호
			
			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "승인 대기중이거나 이미 승인/반려된 건은 공문생성 요청이 불가능합니다."+errMsg);
				return result;
			}
			
			//기등록 공문이 있을 경우 (공문이 임시저장 상태인 건은 새로 생성가능)
//			if(!"".equals(dcNum) && !"".equals(dcStat) && !"10".equals(dcStat)) {
			if(!"".equals(dcNum) || !"".equals(contCode)) {
				result.put("errMsg", "이미 생성된 공문이 존재합니다."+errMsg);
				return result;
			}
		}
		
		/*
		 * 3. 공문생성
		 */
		/*************************** 공문 양식 정보 셋팅 *************************/
		String purDept = StringUtils.defaultString(nEDMPRO0500VO.getPurDept());		//구매조직
		String prodPatFg = StringUtils.defaultString(nEDMPRO0500VO.getProdPatFg());	//상품유형구분
		String taxFg = StringUtils.defaultString(nEDMPRO0500VO.getTaxFg());			//면과세구분
		
		String dcCreGbn = StringUtils.defaultString(nEDMPRO0500VO.getDcCreGbn());	//공문생성구분
		
		//신선일경우, 면과세구분 선택 필수
		if("0".equals(prodPatFg) && !"".equals(taxFg)) {
			result.put("errMsg", "신선 상품은 면과세 구분을 반드시 선택하여야 합니다.");
			return result;
		}
		
		String sysCode = "PC";	//시스템구분(EPC- PC로 고정)
		String depCd = "";		//ECS 관리코드(FRM_LARGE_CD)
		String linCd = "";		//ECS 관리코드(FRM_MIDDLE_CD)
		String dcCd = "";		//계약명코드(BUN_CD)
		String dcNmCd = "";		//계약서코드
		
		String recvCompNum	= "";	//수신처사업자번호(마트/슈퍼/씨에스)
		String recvCompName = "";	//수신처사업자명(마트/슈퍼/씨에스)
		
		String bXml = "";			//계약서 양식 상세내용구성 (body)
		
		switch(purDept) {
			case "KR04":	//슈퍼,CS
				dcCreGbn = "SP";
				recvCompNum = "2068511698";	//수신처사업자번호(슈퍼)
				recvCompName = "롯데슈퍼사업부";	//수신처사업자명(슈퍼)
				/* 슈퍼양식 */
				depCd = "511";		//ECS 관리코드(FRM_LARGE_CD)
				linCd = "001";		//ECS 관리코드(FRM_MIDDLE_CD)
				if("0".equals(prodPatFg) && "0".equals(taxFg)) { //---------신선비규격,면세
					dcCd = "12663";		//계약명코드(BUN_CD)
					dcNmCd = "15116";	//계약서코드
				}else if("0".equals(prodPatFg) && "1".equals(taxFg)) { //---신선비규격,과세
					dcCd = "12664";		//계약명코드(BUN_CD)
					dcNmCd = "15117";	//계약서코드
				}else { //--------------------------------------------------일반
					dcCd = "12662";		//계약명코드(BUN_CD)
					dcNmCd = "15115";	//계약서코드
				}
				break;
			case "KR05":	//CS
				dcCreGbn = "CS";
				recvCompNum = "2048136642";	//수신처사업자번호(씨에스)
				recvCompName = "씨에스유통";	//수신처사업자명(씨에스)
				/* 씨에스양식 */
				depCd = "512";		//ECS 관리코드(FRM_LARGE_CD)
				linCd = "001";		//ECS 관리코드(FRM_MIDDLE_CD)
				if("0".equals(prodPatFg) && "0".equals(taxFg)) { //---------신선비규격,면세
					dcCd = "12668";		//계약명코드(BUN_CD)
					dcNmCd = "15124";	//계약서코드
				}else if("0".equals(prodPatFg) && "1".equals(taxFg)) { //---신선비규격,과세
					dcCd = "12669";		//계약명코드(BUN_CD)
					dcNmCd = "15125";	//계약서코드
				}else { //--------------------------------------------------일반
					dcCd = "12667";		//계약명코드(BUN_CD)
					dcNmCd = "15123";	//계약서코드
				}
				break;
			case "KR02":	//마트
			case "KR03":	//MAXX
			case "KR09":	//오카도
				default:
					recvCompNum = "2158513569";	//수신처사업자번호(마트)
					recvCompName = "롯데마트사업본부";	//수신처사업자명(마트)
					/* 마트양식 */
					dcCreGbn = "MT";
					depCd = "510";		//ECS 관리코드(FRM_LARGE_CD)
					linCd = "007";		//ECS 관리코드(FRM_MIDDLE_CD)
					if("0".equals(prodPatFg) && "0".equals(taxFg)) { //---------신선비규격,면세
						dcCd = "12655";		//계약명코드(BUN_CD)
						dcNmCd = "15106";	//계약서코드
					}else if("0".equals(prodPatFg) && "1".equals(taxFg)) { //---신선비규격,과세
						dcCd = "12656";		//계약명코드(BUN_CD)
						dcNmCd = "15107";	//계약서코드
					}else { //--------------------------------------------------일반
						dcCd = "12654";		//계약명코드(BUN_CD)
						dcNmCd = "15105";	//계약서코드
					}
				break;
		}
		
		//TODO_JIA 추후 분기처리필요
		//마트 양식
		bXml =  this.getProdChgCostDcDocMartNormal(nEDMPRO0500VO, dcNmCd);
		
		//계약서 양식 상세내용구성 (body)실패시
		if("".equals(bXml)) {
			result.put("errMsg", "공문내용 구성 중 오류가 발생하였습니다.");
			return result;
		}
		
		/*************************** 공문 생성 *************************/
		//EPC 내부관리코드생성
		contCode = nEDMPRO0500Dao.selectEcsContCode(nEDMPRO0500VO);
		nEDMPRO0500VO.setContCode(contCode);
		
		//Header ------------------------------------------------------------------
		Map<String,Object> hMap = new HashMap<String,Object>();
		hMap.put("FAMILY_YN", "N");					//계열사구분
		hMap.put("CONT_CODE", contCode);			//EPC 내부 관리번호
		hMap.put("DC_NAME", "원가계약변경요청 공문");		//계약명
		hMap.put("DC_CONDATE", DateUtil.getCurrentDay("yyyyMMdd"));		//계약일자
		hMap.put("DEP_CD", depCd);					//ECS관리코드(FRM_LARGE_CD)
		hMap.put("LIN_CD", linCd);					//ECS관리코드(FRM_MIDDLE_CD)
		hMap.put("DC_CD", dcCd);					//계약명코드(BUN_CD)
		hMap.put("DC_NM_CD", dcNmCd);				//계약서코드
		
		String hXml = EcsUtil.createHeader(hMap);	//header Xml 
		
		//body --------------------------------------------------------------------
		StringBuilder body = new StringBuilder();
		//공통 - 수신업체정보
		Map<String,Object> recvCompMap = new HashMap<String,Object>();
		recvCompMap.put("COMP_NUM", recvCompNum);		//사업자번호
		recvCompMap.put("COMP_NAME", recvCompName);		//사업자명
		recvCompMap.put("TEAM_CD", "1000000000000000");		//팀코드
		recvCompMap.put("EMP_SABUN", "1234");				//사번
		
		String recvCompXml = EcsUtil.getMapData("COMP_LIST", recvCompMap);
		body.append(recvCompXml);
		
		//공통 - 파트너사정보(발신)
		Map<String,Object> vendorInfoMap = new HashMap<String,Object>();
		vendorInfoMap.put("USER_ID", ecsWrtId);			//파트너사작성자ID
		vendorInfoMap.put("CONT_CODE", contCode);		//연계계약번호(EPC내부관리번호)		
		vendorInfoMap.put("SYS_CODE", sysCode);			//시스템구분
		
		String sendCompXml = EcsUtil.getMapData("IF_RECV_OFFICIAL", vendorInfoMap);
		body.append(sendCompXml);
		body.append(bXml);
		
		//ECS 전송
		Map<String,Object> ecsResultMap = ecsUtil.sendEcsDcDoc("", hXml, body.toString());
		
		//ECS 전송 실패 시, 오류코드 반환
		if(!MapUtils.getBooleanValue(ecsResultMap, "result")) {
			result.put("errCode", MapUtils.getString(ecsResultMap, "errCode"));
			result.put("errMsg", MapUtils.getString(ecsResultMap, "errMsg"));
			return result;
		}
		
		/*
		 * 4. 공문번호 업데이트
		 */
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		nEDMPRO0500VO.setModId(workId);
		nEDMPRO0500VO.setRegId(workId);
		
		int updOk = nEDMPRO0500Dao.updateDcContCode(nEDMPRO0500VO);
		
		if(updOk > 0) {
			result.put("url", MapUtils.getString(ecsResultMap, "url"));	//ECS 반환 URL
			result.put("msg", "success");
		}
		return result;
	}
	
	/**
	 * [ECS] 본문생성_마트>일반상품
	 * @param nEDMPRO0500VO
	 * @param dcNmCd
	 * @return String
	 * @throws Exception
	 */
	private String getProdChgCostDcDocMartNormal(NEDMPRO0500VO nEDMPRO0500VO, String dcNmCd) throws Exception{
		StringBuilder contents = new StringBuilder();
		
		//마스터 정보 셋팅
		Map<String,Object> mstData = new HashMap<String,Object>();
//		String chgReqCostDt = nEDMPRO0500Dao.selectMinTpcChgReqCostDt(nEDMPRO0500VO);
//		mstData.put("DC_IF_1", chgReqCostDt);		//원가변경요청일
		String mstInfo = EcsUtil.getMapOfficialItem(dcNmCd,mstData);
		contents.append(mstInfo);
		
		List<Map<String,Object>> itemAllList = new ArrayList<Map<String,Object>>();
		
		//아이템 다건 (테이블)
		String itemData = "";
		List<Map<String,Object>> itemList = nEDMPRO0500Dao.selectTpcChgCostItemEcsSendData(nEDMPRO0500VO); 
		
		//조회된 아이템 있을 경우, 
		if(itemList != null && itemList.size() > 0) {
			//table title 셋팅
//			Map<String,Object> title01 = new LinkedHashMap<String,Object>();
//			title01.put("ROW_NUM", "1");		//표의 로우
//			title01.put("COL1", "판매코드");
//			title01.put("COL2", "상품명");
//			title01.put("COL3", "납품가");
//			title01.put("COL4", "사유");
//			title01.put("COL5", "상세사유");
//			title01.put("COL6", "비고");
//			itemAllList.add(title01);
//			
//			Map<String,Object> title02 = new LinkedHashMap<String,Object>();
//			title02.put("ROW_NUM", "2");		//표의 로우
//			title02.put("COL1", "기존");
//			title02.put("COL2", "변경");
//			title02.put("COL3", "증감율");
//			itemAllList.add(title02);
			
			//table datalist 셋팅
			itemAllList.addAll(itemList);
			
			//xml생성
			itemData = EcsUtil.getMapOfficialTable(dcNmCd, "1", itemAllList);
			contents.append(itemData);
		}
		
		return contents.toString();
	}
	
	
	/**
	 * 원가변경요청 MD협의요청
	 */
	@Override
	public Map<String, Object> insertReqMdProChgCost(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();

		List<NEDMPRO0500VO> prodDataArr = nEDMPRO0500VO.getProdDataArr();			//협의요청대상 list
		
		/*
		 * 1. 필수데이터 확인
		 */
		//MD협의요청대상 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "협의요청 대상 상품을 최소 1건 이상 선택해주세요.");
			return result;
		}
		
		String reqNo 	= ""; 	//요청번호     
		String srcmkCd	= ""; 	//판매코드     
		String prodCd	= ""; 	//상품코드     
		String seq		= "";	//순번
		String dcNum	= "";	//공문번호
		String dcStat	= "";	//공문진행상태
		String contCode = "";	//공문연계번호
		String grpId	= "";	//요청그룹아이디
		String dcCreAllYn = "";	//모든 공문생성 완료여부
		String dcSendAllYn = "";//모든 공문발송 완료여부
		
		NEDMPRO0500VO statusVo = null;		//상태체크용 vo
		NEDMPRO0500VO statusGrpVo = null;	//그룹상태체크용 vo
		String curPrStat = "";				//현재진행상태
		String errMsg = "";
		
		for(NEDMPRO0500VO chkVo : prodDataArr) {
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			//요청번호 누락
			if("".equals(reqNo)) {
				result.put("errMsg", "요청번호가 존재하지 않습니다."+errMsg);
				return result;
			}
			//seq 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "요청순번이 존재하지 않습니다."+errMsg);
				return result;
			}
			//판매코드 누락
			if("".equals(srcmkCd)) {
				result.put("errMsg", "판매코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드는 필수 선택 항목입니다."+errMsg);
				return result;
			}
			
			//상태정보 조회
			statusVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatus(chkVo);
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";
			dcNum = (statusVo != null)?StringUtils.defaultString(statusVo.getDcNum()):"";		//공문번호
			dcStat = (statusVo != null)?StringUtils.defaultString(statusVo.getDcStat()):"";		//공문진행상태
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//연계공문아이디
			grpId = (statusVo != null)?StringUtils.defaultString(statusVo.getGrpId()):"";			//요청그룹아이디

			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "승인 대기중이거나 이미 승인/반려된 건은 협의요청이 불가능합니다."+errMsg);
				return result;
			}
			
			//공문번호 없음
			if("".equals(dcNum) || "".equals(contCode)) {
				result.put("errMsg", "공문 발송 이후 MD협의요청이 가능합니다."+errMsg);
				return result;
			}
			
			//공문발송되지 않음
			if(!"40".equals(dcStat) && !"50".equals(dcStat)) {
				result.put("errMsg", "공문 발송 이후 MD 협의요청이 가능합니다."+errMsg);
				return result;
			}
			
			//요청그룹아이디가 있을 경우, 해당 그룹의 상태 모두 체크
			if(!"".equals(grpId)) {
				statusGrpVo = nEDMPRO0500Dao.selectTpcProdChgCostItemStatusGrp(statusVo);
				dcCreAllYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getDcCreAllYn()):"N";		//모든공문생성완료여부
				dcSendAllYn = (statusGrpVo != null)?StringUtils.defaultString(statusGrpVo.getDcSendAllYn()):"N";	//모든공문발송완료여부
				
				//모든 공문이 발송되었는지 체크
				if("N".equals(dcSendAllYn)) {
					result.put("errMsg", "모든 공문 발송 이후 MD 협의요청이 가능합니다. (일괄생성 건 중 공문 미발송 건 존재)"+errMsg);
					return result;
				}
			}
		}

		
		/*
		 * 3. PO 전송
		 */
		logger.info("PO CALL Start ::: insertReqMdProChgCost ---------------------------");
		String ifStDt = "", ifEndDt = "";		//인터페이스 시작,종료일시
		//-- 1. 전송대상 LIST 조회 (GRP_ID가 있을 경우, 동일한 GRP_ID 가진 건들도 일괄로 전송함)
		List<HashMap> sendList = nEDMPRO0500Dao.selectTpcChgCostItemSendData(nEDMPRO0500VO); 
		
		if(sendList == null || sendList.size() == 0) {
			result.put("errMsg", "요청정보 구성에 실패하였습니다."+errMsg);
			return result;
		}
		
		//-- 2. 요청정보 구성
		JSONObject obj = new JSONObject();
		obj.put("ITEM", sendList);		//요청 DATA
		logger.debug("obj.toString=" + obj.toString());
		
		//-- 3. call
		String proxyNm = "PDM2400";		//인터페이스 Function 명
		ifStDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 시작일시
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, obj.toString(), workId);
		ifEndDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 종료일시
		
		//-- 4. 응답처리
		JSONObject mapObj = new JSONObject(rfcMap.toString());	//응답 message
		JSONObject resultObj = mapObj.has("result")? mapObj.getJSONObject("result"):null;	//응답 메세지 key==result
		
		if(resultObj == null) {
			result.put("msg", "NO_RESULT");
			result.put("errMsg", "응답 결과가 없습니다.");
			return result;
		}
		
		JSONArray resultList = null;
		Object itemObj = resultObj.get("ITEM");
		if(itemObj instanceof JSONArray) {		//응답 == json array
			resultList = resultObj.getJSONArray("ITEM");	//응답 결과 list
		}else {									//응답 == json object
			JSONObject resultData = resultObj.getJSONObject("ITEM");	//응답 결과 data
			resultList = new JSONArray();
			resultList.put(resultData);
		}
		
		logger.info("PO CALL End ::: insertReqMdProChgCost ---------------------------");
		logger.info("if Start : "+ifStDt);
		logger.info("if End : "+ifEndDt);
		
		//응답 결과 list 없음
		if(resultList == null || resultList.length() == 0) {
			result.put("msg", "NO_RESULT");
			result.put("errMsg", "응답 결과가 없습니다.");
			return result;
		}
		
		
		JSONObject resultData = null;
		NEDMPRO0500VO stsVo = null;
		
		
		int ifOk = 0;
		int updOk = 0;
		//응답결과
		String rPurDept, rDcNum, rProdCd, rVenCd, rPrStat, rSrcmkCd, rRtnMsg, rRtnCode;
		for(int i = 0; i < resultList.length(); i++) {
			resultData = resultList.getJSONObject(i);
			
			rPurDept = StringUtils.trimToEmpty(resultData.getString("PUR_DEPT"));			//응답_구매조직
			rDcNum = StringUtils.trimToEmpty(resultData.getString("DC_NUM"));				//응답_공문서번호
			rProdCd = StringUtils.trimToEmpty(resultData.getString("PROD_CD"));				//응답_상품코드
			rVenCd = StringUtils.trimToEmpty(resultData.getString("VEN_CD"));				//응답_업체코드
			rSrcmkCd = StringUtils.trimToEmpty(resultData.getString("SRCMK_CD"));			//응답_판매코드
			rPrStat = StringUtils.trimToEmpty(resultData.getString("PR_STAT"));				//응답_응답상태코드
			rRtnMsg = StringUtils.trimToEmpty(resultData.getString("RTN_MESSAGE"));			//응답_결과메세지
			
			//응답결과코드 확인
			rRtnCode = ("E".equals(rPrStat))? "E":rPrStat;
			
			stsVo = new NEDMPRO0500VO();
			stsVo.setPurDept(rPurDept);		//구매조직
			stsVo.setDcNum(rDcNum);			//공문번호
			stsVo.setProdCd(rProdCd);		//상품코드
			stsVo.setSrcmkCd(rSrcmkCd);		//판매코드
			stsVo.setIfReqDt(ifStDt);		//인터페이스 송신일시
			stsVo.setIfRtnCode(rRtnCode);	//응답결과코드 셋팅
			stsVo.setIfRtnMsg(rRtnMsg);		//응답결과메세지 셋팅
			stsVo.setModId(workId);			//수정자 아이디
			
			//응답 성공 시에만 상태값 갱신
			if(!"E".equals(rRtnCode)) {
				stsVo.setPrStat("01");		//01-MD 승인대기
				ifOk ++;
			}
			
			//인터페이스 응답 결과 및 진행상태 갱신
			updOk += nEDMPRO0500Dao.updateTpcProdRfcStatus(stsVo); 
		}
		
		//인터페이스 응답 결과 모두 실패일 경우,
		if(ifOk == 0) {
			result.put("msg", "REQ_PROD_CHG_COST_FAIL");
			result.put("errMsg", "요청에 실패하였습니다.");
			return result;
		}
		
		//성공 시 결과 메세지
		String resultMsg = (ifOk == updOk)?"": String.format("요청성공 %d건/요청실패 %d건", ifOk, resultList.length()-ifOk);
		result.put("msg", "success");
		result.put("resultMsg", resultMsg);
		
		return result;
		
		
	}
	
	private Map<String, Object> insertReqMdProChgCost_old250513(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();

		List<NEDMPRO0500VO> prodDataArr = nEDMPRO0500VO.getProdDataArr();			//협의요청대상 list
		
		/*
		 * 1. 필수데이터 확인
		 */
		//MD협의요청대상 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "협의요청 대상 상품을 최소 1건 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. 진행상태 확인
		 */
		String reqNo = ""; //요청번호     
		String srcmkCd = ""; //판매코드     
		String prodCd = ""; //상품코드     
		String seq = ""; //순번
		String dcNum = "";	//공문(계약)번호
		String dcStat = "";	//공문진행상태
		String contCode= "";	//공문연계번호
		NEDMPRO0500VO statusVo = null;	//상태체크용 vo
		String curPrStat = "";			//현재진행상태
		String errMsg = "";
		for(NEDMPRO0500VO chkVo : prodDataArr) {
			//필수데이터 체크
			reqNo = StringUtils.defaultString(chkVo.getReqNo());			//요청번호     
			srcmkCd = StringUtils.defaultString(chkVo.getSrcmkCd());		//판매코드     
			prodCd = StringUtils.defaultString(chkVo.getProdCd());			//상품코드     
			seq = StringUtils.defaultString(chkVo.getSeq());				//순번     
			
			errMsg = "\n(판매코드:"+srcmkCd+"/상품코드:"+prodCd+")";
			
			
			//요청번호 누락
			if("".equals(reqNo)) {
				result.put("errMsg", "요청번호가 존재하지 않습니다."+errMsg);
				return result;
			}
			//seq 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "요청순번이 존재하지 않습니다."+errMsg);
				return result;
			}
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
			curPrStat = (statusVo != null)?StringUtils.defaultString(statusVo.getPrStat()):"";	//진행상태
			dcNum = (statusVo != null)?StringUtils.defaultString(statusVo.getDcNum()):"";		//공문번호
			dcStat = (statusVo != null)?StringUtils.defaultString(statusVo.getDcStat()):"";		//공문진행상태
			contCode = (statusVo != null)?StringUtils.defaultString(statusVo.getContCode()):"";		//공문연계번호
			
			//현재상태가 파트너사등록(00)일때만 데이터 등록 가능
			if(!"00".equals(curPrStat)) {
				result.put("errMsg", "승인 대기중이거나 이미 승인/반려된 건은 협의요청이 불가능합니다."+errMsg);
				return result;
			}
			
			//공문번호 없음
//			if("".equals(dcNum) && ("".equals(dcStat) || "10".equals(dcStat))) {
			if("".equals(dcNum) || "".equals(contCode)) {
				result.put("errMsg", "공문 발송 이후 MD협의요청이 가능합니다."+errMsg);
				return result;
			}
		}
		
		/*
		 * 3. PO 전송
		 */
		logger.info("PO CALL Start ::: insertReqMdProChgCost ---------------------------");
		String ifStDt = "", ifEndDt = "";		//인터페이스 시작,종료일시
		//-- 1. 전송대상 LIST 조회
		List<HashMap> sendList = nEDMPRO0500Dao.selectTpcChgCostItemSendData(nEDMPRO0500VO); 
		
		if(sendList == null || sendList.size() == 0) {
			result.put("errMsg", "요청정보 구성에 실패하였습니다."+errMsg);
			return result;
		}
		
		//-- 2. 요청정보 구성
		JSONObject obj = new JSONObject();
		obj.put("ITEM", sendList);		//요청 DATA
		logger.debug("obj.toString=" + obj.toString());
		
		//-- 3. call
		String proxyNm = "PDM2400";		//인터페이스 Function 명
		ifStDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 시작일시
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, obj.toString(), workId);
		ifEndDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 종료일시
		
		//-- 4. 응답처리
		JSONObject mapObj = new JSONObject(rfcMap.toString());	//응답 message
		JSONObject resultObj = mapObj.getJSONObject("result");	//응답 메세지 key==result
//			JSONArray resultList = resultObj.getJSONArray("ITEM");	//응답 결과 list
		JSONArray resultList = null;
		Object itemObj = resultObj.get("ITEM");
		if(itemObj instanceof JSONArray) {		//응답 == json array
			resultList = resultObj.getJSONArray("ITEM");	//응답 결과 list
		}else {									//응답 == json object
			JSONObject resultData = resultObj.getJSONObject("ITEM");	//응답 결과 data
			resultList = new JSONArray();
			resultList.put(resultData);
		}
		
		logger.info("PO CALL End ::: insertReqMdProChgCost ---------------------------");
		logger.info("if Start : "+ifStDt);
		logger.info("if End : "+ifEndDt);
		
		//응답 결과 list 없음
		if(resultList == null || resultList.length() == 0) {
			result.put("msg", "NO_RESULT");
			result.put("errMsg", "응답 결과가 없습니다.");
			return result;
		}
		
		
		JSONObject resultData = null;
		NEDMPRO0500VO stsVo = null;
		
		int ifOk = 0;
		int updOk = 0;
		//응답결과
		String rPurDept, rDcNum, rProdCd, rVenCd, rPrStat, rSrcmkCd, rRtnMsg, rRtnCode;
		for(int i = 0; i < resultList.length(); i++) {
			resultData = resultList.getJSONObject(i);
			
			rPurDept = StringUtils.trimToEmpty(resultData.getString("PUR_DEPT"));			//응답_구매조직
			rDcNum = StringUtils.trimToEmpty(resultData.getString("DC_NUM"));				//응답_공문서번호
			rProdCd = StringUtils.trimToEmpty(resultData.getString("PROD_CD"));				//응답_상품코드
			rVenCd = StringUtils.trimToEmpty(resultData.getString("VEN_CD"));				//응답_업체코드
			rSrcmkCd = StringUtils.trimToEmpty(resultData.getString("SRCMK_CD"));			//응답_판매코드
			rPrStat = StringUtils.trimToEmpty(resultData.getString("PR_STAT"));				//응답_응답상태코드
			rRtnMsg = StringUtils.trimToEmpty(resultData.getString("RTN_MESSAGE"));			//응답_결과메세지
			
			//응답결과코드 확인
			rRtnCode = ("E".equals(rPrStat))? "E":rPrStat;
			
			stsVo = new NEDMPRO0500VO();
			stsVo.setPurDept(rPurDept);		//구매조직
			stsVo.setDcNum(rDcNum);			//공문번호
			stsVo.setProdCd(rProdCd);		//상품코드
			stsVo.setSrcmkCd(rSrcmkCd);		//판매코드
			stsVo.setIfReqDt(ifStDt);		//인터페이스 송신일시
			stsVo.setIfRtnCode(rRtnCode);	//응답결과코드 셋팅
			stsVo.setIfRtnMsg(rRtnMsg);		//응답결과메세지 셋팅
			stsVo.setModId(workId);			//작업자 세팅
			
			//응답 성공 시에만 상태값 갱신
			if(!"E".equals(rRtnCode)) {
				stsVo.setPrStat("01");		//01-MD 승인대기
				ifOk ++;
			}
			
			//인터페이스 응답 결과 및 진행상태 갱신
			updOk += nEDMPRO0500Dao.updateTpcProdRfcStatus(stsVo); 
		}
		
		//인터페이스 응답 결과 모두 실패일 경우,
		if(ifOk == 0) {
			result.put("msg", "REQ_PROD_CHG_COST_FAIL");
			result.put("errMsg", "요청에 실패하였습니다.");
			return result;
		}
		
		//성공 시 결과 메세지
		String resultMsg = (ifOk == updOk)?"": String.format("요청성공 %d건/요청실패 %d건", ifOk, resultList.length()-ifOk);
		result.put("msg", "success");
		result.put("resultMsg", resultMsg);
		
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
