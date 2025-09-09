package com.lottemart.epc.edi.product.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.CommonProductDao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0020Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0530Dao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0530VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0530Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0530ServiceImpl.java
 * @Description : 채널확장 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.07.31		yun				최초생성
 *               </pre>
 */
@Service("nEDMPRO0530Service")
public class NEDMPRO0530ServiceImpl implements NEDMPRO0530Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0530ServiceImpl.class);
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0530Dao nEDMPRO0530Dao;
	
	@Autowired
	private BosOpenApiService bosApiService;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private CommonProductDao commonProductDao;
	
	@Autowired
	private NEDMPRO0020Dao nEDMPRO0020Dao;
	
	@Autowired
	private CommonProductService commonProductService;
	
	/**
	 * 확장가능 상품내역 리스트 조회 jqGrid
	 */
	@Override
	public Map<String, Object> selectExtAvailProdList(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();
		
		//====== 소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		
		//data list
		List<NEDMPRO0530VO> list = null;
		
		//data list count
		totalCount = nEDMPRO0530Dao.selectExtAvailProdListCount(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = nEDMPRO0530Dao.selectExtAvailProdList(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}

		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트

		return gridMap;
	}
	
	/**
	 * 등록된 확장요청 정보 조회 (TAB 구성용)
	 */
	@Override
	public List<NEDMPRO0530VO> selectExtTabList(NEDMPRO0530VO paramVo) throws Exception {
		return nEDMPRO0530Dao.selectExtTabList(paramVo);
	}

	/**
	 * 채널확장 상세정보 조회
	 */
	@Override
	public Map<String, Object> selectTpcProdChanExtendDetailInfo(NEDMPRO0530VO paramVo) throws Exception {
		String prodCd = StringUtils.defaultString(paramVo.getProdCd());			//상품코드
//		String srcmkCd = StringUtils.defaultString(paramVo.getSrcmkCd());		//판매코드
		String pgmId = StringUtils.defaultString(paramVo.getPgmId());			//문서번호
		
		Map<String,Object> detailMap = new HashMap<String,Object> ();
		
		//선택한 확장 요청정보 조회
		NEDMPRO0530VO dtlVo = null;
		
		//문서번호 있을 때만 상세정보 조회
		if(!"".equals(pgmId)) {
			dtlVo = nEDMPRO0530Dao.selectTpcProdChanExtendDetailInfo(paramVo);
		}
		
		//채널 확장 가능 상태 확인용 list
		List<NEDMPRO0530VO> extAbleChkList = nEDMPRO0530Dao.selectChkExtAbleProdChan(paramVo);
		
		//반환 데이터
		detailMap.put("dtlInfo", dtlVo);					//선택한 확장요청 상세정보
		detailMap.put("extAbleChkList", extAbleChkList);	//채널 확장 가능 상태 확인용 list
		
		return detailMap;
	}

	/**
	 * 채널확장정보 등록
	 */
	@Override
	public Map<String, Object> updateTpcProdChanExtendDetailInfo(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		
		/*
		 * 1. 필수 데이터 확인
		 */
		String prodCd = StringUtils.defaultString(paramVo.getProdCd());			//상품코드
//		String srcmkCd = StringUtils.defaultString(paramVo.getSrcmkCd());		//판매코드
		String pgmId = StringUtils.defaultString(paramVo.getPgmId());			//문서번호
		String venCd = StringUtils.defaultString(paramVo.getVenCd());			//파트너사코드
		String extChanCd = StringUtils.defaultString(paramVo.getExtChanCd());	//확장채널코드
		String[] extChanCds;			//확장채널 코드 array
		

		//상품코드 누락
		if("".equals(prodCd)) {
			result.put("errMsg", "상품코드가 존재하지 않습니다.");
			return result;
		}
		
//		//판매코드 누락
//		if("".equals(srcmkCd)) {
//			result.put("errMsg", "판매코드가 존재하지 않습니다.");
//			return result;
//		}
		
		//협력업체코드 코드 누락
		if("".equals(venCd)) {
			result.put("errMsg", "협력업체 정보가 존재하지 않습니다.");
			return result;
		}
		
		//확장채널 코드 누락
		if("".equals(extChanCd)) {
			result.put("errMsg", "확장대상 채널 정보가 존재하지 않습니다.");
			return result;
		}
		
		extChanCds = extChanCd.split(",");		//확장채널코드 Array
		paramVo.setChanCds(extChanCds);			//확장채널코드 Array Setting
		
		
		/*
		 * 2. Data Validation
		 */
		//상품마스터의 속성 조회
		NEDMPRO0530VO prodInfo = nEDMPRO0530Dao.selectProductDetails(paramVo);
		if(prodInfo == null) {
			result.put("errMsg", "등록된 상품 정보가 없습니다.");
			return result;
		}
		
		//상품마스터에서 면과세코드 조회
		String taxFg = StringUtils.defaultString(prodInfo.getTaxFg());
		if("".equals(taxFg)) {
			result.put("errMsg", "등록된 상품의 과세구분이 없습니다.\n관리자에게 문의하세요.");
			return result;
		}
		
		//선택한 업체의 선택 가능 구매조직 체크
		CommonProductVO venChanChkVo = new CommonProductVO();
		venChanChkVo.setVenCd(venCd);
		Map<String,Object> venChanMap = commonProductService.selectVendorZzorgInfo(venChanChkVo, null);
		
		//해당 업체코드로 선택가능한 구매조직(채널)
		List<String> venPurDept = (List<String>) MapUtils.getObject(venChanMap, "venPurDepts");
		
		//선택한 채널이 선택 가능한 구매조직(채널)에 모두 포함되는지 확인 
		List<String> extChanCdList = Arrays.asList(extChanCds);
		if(!venPurDept.containsAll(extChanCdList)) {
			result.put("msg", "CHAN_NOT_SELECTABLE");
			result.put("errMsg", "해당 업체코드로 선택 불가능한 채널이 존재하여, 저장이 불가능합니다.");
			return result;
		}
		
		//협력업체코드 거래유형조회
		Map<String, Object> venParamMap	=	new HashMap<String, Object>();
		venParamMap.put("selectedVendor", venCd);
		HashMap	vendorHsMap	=	commonProductDao.selectNVendorTradeType(venParamMap);
		
		//----- 협력업체 거래유형이 없을경우
		if (vendorHsMap == null) {
			result.put("msg", "NOT_PERMISSION_TRD_TYP_FG");
			result.put("errMsg", "상품을 등록 할 수 없는 거래유형 타입입니다.");
			return result;
		}
		
		//협력업체거래유형
		String vendorTrdTypFg = MapUtils.getString(vendorHsMap, "TRD_TYP_FG", ""); 
		
		//직매입, 특약1, 특약2 업체일 경우만 상품 확장요청 가능 (상품등록 조건과 동일)
		if(!"1".equals(vendorTrdTypFg) && !"2".equals(vendorTrdTypFg) && !"4".equals(vendorTrdTypFg)) {
			result.put("msg", "NOT_PERMISSION_TRD_TYP_FG");
			result.put("errMsg", "상품 확장이 불가능한 거래유형 타입입니다.");
			return result;
		}
				
		//=============================
		// 확장가능한 채널인지 확인
		//=============================
		List<NEDMPRO0530VO> extAbleChkList = nEDMPRO0530Dao.selectChkExtAbleProdChan(paramVo);
		
		//확장가능 채널인지 체크 불가
		if(extAbleChkList == null || extAbleChkList.isEmpty() || extAbleChkList.size() != extChanCds.length) {
			result.put("errMsg", "확장 가능 여부를 확인할 수 없습니다.");
			return result;
		}
		
		String extAbleSts = "";		//확장가능상태 체크
		String chkChanCd = "";		//체크할 채널코드
		String chkChanNm = "";		//체크할 채널명
		
		int chkErrCnt = 0;
		String chk_aldy = "";		//이미 적용된 채널
		String chk_save = "";		//다른 문서에 임시저장된 채널
		String chk_prgs = "";		//확장 요청중인 채널
		
		//확장 가능 채널인지 체크
		for(NEDMPRO0530VO chkVo : extAbleChkList) {
			chkChanCd = StringUtils.defaultString(chkVo.getChanCd());	//체크할 채널코드
			chkChanNm = StringUtils.defaultString(chkVo.getChanNm());	//체크할 채널명
			extAbleSts = StringUtils.defaultString(chkVo.getExtAbleSts(), "A");	//확장가능상태 체크값
			
			switch(extAbleSts) {
				case "A":	//확장요청 가능
					break;
				case "C":	//이미 적용된 채널
					chk_aldy += ","+chkChanNm;
					chkErrCnt ++;
					break;
				case "T":	//다른 문서에 임시저장된 채널
					chk_save += ","+chkChanNm;
					chkErrCnt ++;
					break;
				case "P":	//확장 요청중인 채널
					chk_prgs += ","+chkChanNm;
					chkErrCnt ++;
					break;
				default: break;
			}
		}
		
		//확장 불가 채널 있을 경우,
		if(chkErrCnt > 0) {
			chk_aldy = chk_aldy.replaceAll("^,", "");	//이미 적용된 채널 메세지
			chk_save = chk_save.replaceAll("^,", "");	//다른 문서에 임시저장된 채널 메세지
			chk_prgs = chk_prgs.replaceAll("^,", "");	//확장 요청중인 채널 메세지
			
			chk_aldy = ("".equals(chk_aldy.trim()))?"":"\n[이미 적용된 채널] "+chk_aldy;
			chk_save = ("".equals(chk_save.trim()))?"":"\n[임시저장건 존재] "+chk_save;
			chk_prgs = ("".equals(chk_prgs.trim()))?"":"\n[이미 요청된 채널]"+chk_prgs;
			
			result.put("errMsg", String.format("확장 불가능한 채널입니다. %s %s %s", chk_aldy, chk_save, chk_prgs));
			return result;
		}
		//=======================
		
		//문서번호 있을 경우, 해당 문서의 진행상태 체크
		if(!"".equals(pgmId)) {
			String prgsSts = "";		//현재 문서의 진행상태 코드
			
			//상세정보 조회
			NEDMPRO0530VO dtlVo = nEDMPRO0530Dao.selectTpcProdChanExtendDetailInfo(paramVo);
			if(dtlVo == null) {
				result.put("errMsg", "확장요청 상태 확인에 실패하였습니다.");
				return result;
			}
			
			prgsSts = StringUtils.defaultString(dtlVo.getPrgsSts());	//진행상태코드
			
			//확정
			if("02".equals(prgsSts)) {
				result.put("errMsg", "이미 확장 승인된 건입니다.");
				return result;
			}
			
			//거절, 반려
			if("03".equals(prgsSts)) {
				result.put("errMsg", "거절 또는 반려 처리된 건입니다.\n신규 요청으로 진행해주세요.");
				return result;
			}
			
			//이미 요청된 건
			if("01".equals(prgsSts)) {
				result.put("errMsg", "확장 요청 심사중인 건입니다.");
				return result;
			}
		}
		
		/*
		 * 3. 데이터 전처리
		 */
		//3-1) 문서번호 없을 경우 생성 ---------------------------------------------------------
		if("".equals(pgmId)) {
			//key 채번
			pgmId = bosApiService.selectTpcNewProdRegKey();
			
			//key 채번 실패
			if("".equals(pgmId)) {
				result.put("errMsg", "채널확장 정보 생성 중 오류가 발생했습니다.");
				return result;
			}
			
			//채번된 key setting
			paramVo.setPgmId(pgmId);
		}
		
		//3-2) 슈퍼 채널이 미포함되었을 경우, 슈퍼 장려금 값 초기화 ------------------------------------
		if(!extChanCd.contains("KR04")) {
			paramVo.setsNewProdPromoFg("");
			paramVo.setsNewProdStDy("");
			paramVo.setsOverPromoFg("");
		}
		
		//3-3) 면과세 구분에 따른 이익률 및 원가 계산 ----------------------------------------------
		NEDMPRO0020VO prodCostVo = new NEDMPRO0020VO();
		prodCostVo.setTaxatDivnCd(taxFg);	//면과세구분코드 setting
		
		//paramVo에서 원매가 정보 조회해서 셋팅
		prodCostVo.setNorProdPcost(paramVo.getNorProdPcost());		//마트_원가
		prodCostVo.setNorProdSalePrc(paramVo.getNorProdSalePrc());	//마트_매가
		prodCostVo.setPrftRate(paramVo.getPrftRate());				//마트_이익률
		
		prodCostVo.setWnorProdPcost(paramVo.getWnorProdPcost());	//MAXX_원가
		prodCostVo.setWnorProdSalePrc(paramVo.getWnorProdSalePrc());//MAXX_매가
		prodCostVo.setWprftRate(paramVo.getWprftRate());			//MAXX_이익률
		
		prodCostVo.setSnorProdPcost(paramVo.getSnorProdPcost());	//슈퍼_원가
		prodCostVo.setSnorProdSalePrc(paramVo.getSnorProdSalePrc());//슈퍼_매가
		prodCostVo.setSprftRate(paramVo.getSprftRate());			//슈퍼_이익률
		
		prodCostVo.setOnorProdPcost(paramVo.getOnorProdPcost());	//CFC_원가
		prodCostVo.setOnorProdSalePrc(paramVo.getOnorProdSalePrc());//CFC_매가
		prodCostVo.setOprftRate(paramVo.getOprftRate());			//CFC_이익률
		
		//3-3-1) 거래형태가 직매입일 경우
		if("1".equals(vendorTrdTypFg)) {
			//----- 채널별 면/과세 구분에 따른 이익률 계산 start----------------- 
			String commPrftRate = "";	//이익률 환산결과
			for(String selChanCd : extChanCds) {
				switch(selChanCd) {
					case "KR02":	//마트
						commPrftRate = nEDMPRO0020Dao.selectnewProdPrftRate(prodCostVo);
						paramVo.setPrftRate(commPrftRate);	//마트_이익률 셋팅
						break;
					case "KR03":	//MAXX
						commPrftRate = nEDMPRO0020Dao.selectnewProdWprftRate(prodCostVo);
						paramVo.setWprftRate(commPrftRate);	//MAXX_이익률 셋팅
						break;
					case "KR04":	//슈퍼
						commPrftRate = nEDMPRO0020Dao.selectnewProdSprftRate(prodCostVo);
						paramVo.setSprftRate(commPrftRate);	//슈퍼_이익률 셋팅
						break;
					case "KR09":	//오카도
						commPrftRate = nEDMPRO0020Dao.selectnewProdOprftRate(prodCostVo);
						paramVo.setOprftRate(commPrftRate);	//오카도_이익률 셋팅
						break;
					default:
						break;
					
				}
			}
			//----- 채널별 면/과세 구분에 따른 이익률 계산 end ----------------- 
		}else {
			//----- 채널별 면/과세 구분에 따른 원가 계산 start----------------- 
			String commNorProdPcost = "";	//원가 환산결과
			for(String selChanCd : extChanCds) {
				switch(selChanCd) {
					case "KR02":	//마트
						commNorProdPcost = nEDMPRO0020Dao.selectNorProdPcost(prodCostVo);
						paramVo.setNorProdPcost(commNorProdPcost);	//마트_원가 셋팅
						break;
					case "KR03":	//MAXX
						commNorProdPcost = nEDMPRO0020Dao.selectWnorProdPcost(prodCostVo);
						paramVo.setWnorProdPcost(commNorProdPcost);	//MAXX_원가 셋팅
						break;
					case "KR04":	//슈퍼
						commNorProdPcost = nEDMPRO0020Dao.selectSnorProdPcost(prodCostVo);
						paramVo.setSnorProdPcost(commNorProdPcost);	//슈퍼_원가 셋팅
						break;
					case "KR09":	//오카도
						commNorProdPcost = nEDMPRO0020Dao.selectOnorProdPcost(prodCostVo);
						paramVo.setOnorProdPcost(commNorProdPcost);	//오카도_원가 셋팅
						break;
					default:
						break;
					
				}
			}
			//----- 채널별 면/과세 구분에 따른 원가 계산 end-----------------
		}
		
		/*
		 * 4. 확장정보 저장
		 */
		int insOk = nEDMPRO0530Dao.updateTpcProdChanExtendDetailInfo(paramVo);
		
		//성공 시 결과 값 셋팅
		if(insOk > 0) {
			result.put("msg", "success");
		}
		
		return result;
	}

	/**
	 * 채널확장 요청 전송 (Proxy 전송)
	 */
	@Override
	public Map<String, Object> insertRequestProdChanExtendInfo(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");

		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		/*
		 * 1. 필수 데이터 확인
		 */
		List<NEDMPRO0530VO> prodArr = paramVo.getProdArr();						//확장요청 채널 list
		
		//확장채널 정보 없음
		if(prodArr == null || prodArr.isEmpty()) {
			result.put("errMsg", "확장 요청할 채널정보가 없습니다.");
			return result;
		}
		
		/*
		 * 2. data validation
		 */
		String errMsg = "";
		
		String pgmId = "";		//문서번호
		String prodCd = "";		//상품코드
//		String srcmkCd = "";	//판매코드
		String extChanCd = "";	//확장채널코드
		String[] extChanCds;
		
		NEDMPRO0530VO dtlVo = null;	//확장요청상세정보
		String alreadyYn = "";		//이미 적용된 채널
		String prgsSts = "";		//확장요청진행상태
		
		for(NEDMPRO0530VO vo: prodArr) {
			pgmId = StringUtils.defaultString(vo.getPgmId());		//문서번호
			prodCd = StringUtils.defaultString(vo.getProdCd());		//상품코드
//			srcmkCd = StringUtils.defaultString(vo.getSrcmkCd());	//판매코드
			
			//문서번호 없음
			if("".equals(pgmId)) {
				result.put("errMsg", "문서번호가 존재하지 않습니다.");
				return result;
			}
			
			//상품코드 없음
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드가 존재하지 않습니다. (문서번호:"+pgmId+")");
				return result;
			}
			
//			//판매코드 없음
//			if("".equals(srcmkCd)) {
//				result.put("errMsg", "판매코드가 존재하지 않습니다. (문서번호:"+pgmId+")");
//				return result;
//			}
			
			//-- 해당 문서의 진행상태 체크
			//상세정보 조회
			dtlVo = nEDMPRO0530Dao.selectTpcProdChanExtendDetailInfo(vo);
			
			//저장된 확장요청 정보 없음
			if(dtlVo == null) {
				result.put("errMsg", "등록된 확장요청 정보가 없습니다. (문서번호:"+pgmId+")");
				return result;
			}
			
			//진행상태 체크
			prgsSts = StringUtils.defaultString(dtlVo.getPrgsSts());
			
			//확정
			if("02".equals(prgsSts)) {
				result.put("errMsg", "이미 확장 승인된 건입니다.");
				return result;
			}
			
			//거절, 반려
			if("03".equals(prgsSts)) {
				result.put("errMsg", "거절 또는 반려 처리된 건입니다.\n재요청은 불가능하며, 신규요청으로 진행해주세요.");
				return result;
			}
			
			//이미 요청된 건
			if("01".equals(prgsSts)) {
				result.put("errMsg", "확장 요청 심사중인 건입니다.");
				return result;
			}
			
			
			//확장채널 정보 체크
			extChanCd = StringUtils.defaultString(dtlVo.getExtChanCd());	//확장채널코드
			
			//확장채널정보 없음
			if("".equals(extChanCd)) {
				result.put("errMsg", "확장요청 대상 채널 정보가 존재하지 않습니다. (문서번호:"+pgmId+")");
				return result;
			}
			
			extChanCds = extChanCd.split(",");		//확장채널코드 Array
			vo.setChanCds(extChanCds);			//확장채널코드 Array Setting
			
			//=============================
			// 확장 가능 채널인지 확인
			//=============================
			List<NEDMPRO0530VO> extAbleChkList;	
			String extAbleSts = "";		//확장가능상태 체크
			String chkChanCd = "";		//체크할 채널코드
			String chkChanNm = "";		//체크할 채널명
			
			int chkErrCnt = 0;			//적용불가 count
			String chk_aldy = "";		//이미 적용된 채널
			String chk_save = "";		//다른 문서에 임시저장된 채널
			String chk_prgs = "";		//확장 요청중인 채널
			
			extAbleChkList = nEDMPRO0530Dao.selectChkExtAbleProdChan(vo);
			
			//확장가능 채널인지 체크 불가
			if(extAbleChkList == null || extAbleChkList.isEmpty() || extAbleChkList.size() != extChanCds.length) {
				result.put("errMsg", "확장 가능 여부를 확인할 수 없습니다.");
				return result;
			}
			
			//확장 가능 채널인지 체크
			for(NEDMPRO0530VO chkVo : extAbleChkList) {
				chkChanCd = StringUtils.defaultString(chkVo.getChanCd());	//체크할 채널코드
				chkChanNm = StringUtils.defaultString(chkVo.getChanNm());	//체크할 채널명
				extAbleSts = StringUtils.defaultString(chkVo.getExtAbleSts(), "A");	//확장가능상태 체크값
				
				switch(extAbleSts) {
					case "A":	//확장요청 가능
						break;
					case "C":	//이미 적용된 채널
						chk_aldy += ","+chkChanNm;
						chkErrCnt ++;
						break;
					case "T":	//다른 문서에 임시저장된 채널
						chk_save += ","+chkChanNm;
						chkErrCnt ++;
						break;
					case "P":	//확장 요청중인 채널
						chk_prgs += ","+chkChanNm;
						chkErrCnt ++;
						break;
					default: break;
				}
			}
			
			//확장 불가 채널 있을 경우,
			if(chkErrCnt > 0) {
				chk_aldy = chk_aldy.replaceAll("^,", "");	//이미 적용된 채널 메세지
				chk_save = chk_save.replaceAll("^,", "");	//다른 문서에 임시저장된 채널 메세지
				chk_prgs = chk_prgs.replaceAll("^,", "");	//확장 요청중인 채널 메세지
				
				chk_aldy = ("".equals(chk_aldy.trim()))?"":"\n이미 적용된 채널( "+chk_aldy+")";
				chk_save = ("".equals(chk_save.trim()))?"":"\n임시저장건 존재( "+chk_save+")";
				chk_prgs = ("".equals(chk_prgs.trim()))?"":"\n이미 요청된 채널("+chk_prgs+")";
				
				result.put("errMsg", String.format("확장 불가능한 채널입니다.(문서번호:%s)\n %s %s %s", pgmId, chk_aldy, chk_save, chk_prgs));
				return result;
			}
			//=================================
		}
			
		/*
		 * 3. PROXY 전송
		 */
		logger.info("PO CALL Start ::: insertRequestProdChanExtendInfo ---------------------------");
		//-- 1. 전송대상 LIST 조회 
		List<HashMap> sendList = nEDMPRO0530Dao.selectTpcProdChanExtendProxyData(paramVo);
		
		if(sendList == null || sendList.size() == 0) {
			result.put("errMsg", "요청정보 구성에 실패하였습니다.");
			return result;
		}
		
		//-- 2. 요청정보 구성
		JSONObject obj = new JSONObject();
		obj.put("EXT_PROD", sendList);		//요청 DATA
		
		//-- 3. call
		String proxyNm = "MST2410";		//인터페이스 Function 명
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, obj.toString(), workId);
		
		//-- 4. 응답처리
		JSONObject mapObj = new JSONObject(rfcMap.toString());	//응답 message
		JSONObject resultObj = mapObj.getJSONObject("result");	//응답 메세지 key==result
		
		
		String rsltCd = resultObj.has("MSGTYP")?resultObj.getString("MSGTYP"):"";		//응답결과코드
		String rsltMsg = resultObj.has("MESSAGE")?resultObj.getString("MESSAGE"):"";	//응답결과메세지	- 응답결과코드 S일때는 반환되지 않음
		
		//전송 실패 시,
		if(!"S".equals(rsltCd)) {
			logger.error(rsltMsg);
			errMsg = "확장 요청에 실패하였습니다.(" + rsltMsg +")";
			result.put("errMsg", errMsg);
			return result;
		}
		logger.info("PO CALL End ::: insertRequestProdChanExtendInfo ---------------------------");
		
		/*
		 * 4. 전송상태 업데이트
		 */
		paramVo.setPrgsSts("01");			//진행상태 변경 - 01: 요청
		int updSts = nEDMPRO0530Dao.updateTpcProdExtendSts(paramVo);
		
		//성공 시 결과 메세지 셋팅
		if(updSts > 0) {
			result.put("msg", "success");
		}
		
		return result;
	}

	/**
	 * 채널 확장 정보 삭제
	 */
	@Override
	public Map<String, Object> deleteTpcExtProdReg(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		/*
		 * 1. 필수 데이터 확인
		 */
		String prodCd = StringUtils.defaultString(paramVo.getProdCd());			//상품코드
//		String srcmkCd = StringUtils.defaultString(paramVo.getSrcmkCd());		//판매코드
		String pgmId = StringUtils.defaultString(paramVo.getPgmId());			//문서번호
		
		//문서번호 없음
		if("".equals(pgmId)) {
			result.put("errMsg", "문서번호가 존재하지 않습니다.");
			return result;
		}

//		//상품코드 누락
//		if("".equals(prodCd)) {
//			result.put("errMsg", "상품코드가 존재하지 않습니다.");
//			return result;
//		}
//		
//		//판매코드 누락
//		if("".equals(srcmkCd)) {
//			result.put("errMsg", "판매코드가 존재하지 않습니다.");
//			return result;
//		}
		
		String prgsSts = "";		//현재 문서의 진행상태 코드
		
		/*
		 * 2. data Validation
		 */
		//상세정보 조회
		NEDMPRO0530VO dtlVo = nEDMPRO0530Dao.selectTpcProdChanExtendDetailInfo(paramVo);
		if(dtlVo == null) {
			result.put("errMsg", "삭제할 확장정보가 존재하지 않습니다.");
			return result;
		}

		prgsSts = StringUtils.defaultString(dtlVo.getPrgsSts());	//진행상태코드

		//확정
		if("02".equals(prgsSts)) {
			result.put("errMsg", "이미 확장 승인된 건입니다.");
			return result;
		}
		
		//거절, 반려
		if("03".equals(prgsSts)) {
			result.put("errMsg", "거절 또는 반려 처리된 건입니다.");
			return result;
		}
		
		//이미 요청된 건
		if("01".equals(prgsSts)) {
			result.put("errMsg", "확장 요청 심사중인 건입니다.");
			return result;
		}
		
		/*
		 * 3. 삭제
		 */
		//삭제
		int delOk = nEDMPRO0530Dao.deleteTpcExtProdReg(paramVo);
		
		//성공 시 결과 메세지 셋팅
		if(delOk > 0) {
			result.put("msg", "success");
		}

		return result;
	}
}
