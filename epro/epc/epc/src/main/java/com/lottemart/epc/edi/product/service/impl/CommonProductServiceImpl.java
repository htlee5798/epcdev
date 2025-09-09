package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;
import com.lottemart.epc.edi.product.dao.CommonProductDao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.service.CommonProductService;

import lcn.module.common.util.HashBox;
import lcn.module.common.util.StringUtil;

/**
 * @Class Name : NEDMPRO0020ServiceImpl
 * @Description : 신상품등록(온오프) 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 */
@Service("commonProductService")
public class CommonProductServiceImpl implements CommonProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonProductService.class);
	
	/*  DAO */
	@Resource(name="commonProductDao")
	private CommonProductDao commonProductDao;
	
	@Resource(name="customTagDao")
	private CustomTagDao customTagDao;
	
	/*
	 * 신상품장려금 대상 업체인지 체크
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int selectNcheckCountVendorNewPromoFg(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNcheckCountVendorNewPromoFg(paramMap);
	}
	
	/**
	 * 거래중지된 업체인지 체크
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int selectNcheckCountVendorStopTrading(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNcheckCountVendorStopTrading(paramMap);
	}
	
	/**
	 * 협력업체 거래유형조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectNVendorTradeType(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {		
		return commonProductDao.selectNVendorTradeType(paramMap);
	}
	
	/**
	 * 협력업체 과세구분 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String selectNVendorTaxType(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNVendorTaxType(paramMap);
	}
	
	
	/**
	 * 팀 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNteamList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNteamList(paramMap);
	}
	
	/**
	 * ECS 사업자별 팀 정보 조회 
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectEcsNteamList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectEcsNteamList(paramMap);
	}
	
	/**
	 * 팀의 대분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL1list(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNgetL1list(paramMap);
	}
	
	/**
	 * 대붑류의 중분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL2list(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectNgetL2list(paramMap);
	}
	
	/**
	 * 대분류의 중분류 조회 (팀정보 X)
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> NselectNoTeamL2List(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.NselectNoTeamL2List(paramMap);
	}
	
	/**
	 * 중분류의 소분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL3list(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNgetL3list(paramMap);
	}
	
	/**
	 * 중분류의 소분류 조회 (팀X)
	 */
	public List<CommonProductVO> selectNgetNoTeamL3List(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectNgetNoTeamL3List(paramMap);
	}
	
	/**
	 * 소분류의 그룹분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetGrplist(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNgetGrplist(paramMap);
	}
	/**
	 * 계절년도 리스트
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNseasonYear(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectNseasonYear(paramMap);
	}
	
	/**
	 * 계절리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> NselectSeasonList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return commonProductDao.selectNseasonList(paramMap);
	}
	
	/**
	 * 전상법 콤보박스 변경시 해당그룹의 리스트 조회
	 */
	public List<CommonProductVO> selectProdAddTemplateDetailList(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectProdAddTemplateDetailList(paramMap);
	}
	
	/**
	 * KC인증 콤보박스 변경시 해당그룹의 KC인증 조회
	 * @param infoGrpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateDetailList(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectProdCertTemplateDetailList(paramMap);
	}
	
	/**
	 * 대분류 변경으로 전자상거래 콤보리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdAddTemplateList(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectProdAddTemplateList(paramMap);
	}
	
	/**
	 * 대분류 변경으로 KC인증 콤보리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateList(Map<String, Object> paramMap) throws Exception {
		return commonProductDao.selectProdCertTemplateList(paramMap);
	}
	
	/**
	 * 판매코드 조회 
	 */
	public List<CommonProductVO> selectSrcmkCdList(CommonProductVO paramMap) throws Exception {
		return commonProductDao.selectSrcmkCdList(paramMap);
	}
	
	public int selectSrcmkCdListCount(CommonProductVO paramMap) throws Exception {
		return commonProductDao.selectSrcmkCdListCount(paramMap);
	}
	
	/**
	 * ECS 수신 담당자 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selEcsReceiverPopupInfo(CommonProductVO paramMap) throws Exception {
		return commonProductDao.selEcsReceiverPopupInfo(paramMap);
	}
	
	public int selEcsReceiverPopupInfoCount(CommonProductVO paramMap) throws Exception {
		return commonProductDao.selEcsReceiverPopupInfoCount(paramMap);
	}
	
	/**
	 * 점포코드 조회 
	 */
	public List<CommonProductVO> selectStrCdList(Map<String, Object> paramMap) throws Exception{
		return commonProductDao.selectStrCdList(paramMap);
	}
	
	public int selectStrCdListCount(Map<String, Object> paramMap) throws Exception{
		return commonProductDao.selectStrCdListCount(paramMap);
	}
	
	/**
	 * 팀정보 및 대분류 조회 
	 */
	public List<CommonProductVO> selectTeamL1CdList(Map<String, Object> paramMap) throws Exception{
		return commonProductDao.selectTeamL1CdList(paramMap);
	}
	
	/**
	 *  통화구분 공통 코드 조회 
	 */
	public List<CommonProductVO> selectWaersCdList(Map<String, Object> paramMap) throws Exception{
		return commonProductDao.selectWaersCdList(paramMap);
	}
	
	/**
	 * HTML_CODE_TAG 문자대체까지가져오기 
	 */
	public List<HashMap<String, Object>> selectCodeTagList(Map<String, Object> paramMap)throws Exception{
		return commonProductDao.selectCodeTagList(paramMap);
	}
	
	/**
	 * 업체코드별 계열사 및 구매조직 정보
	 */
	public Map<String,Object> selectVendorZzorgInfo(CommonProductVO paramVo, HttpServletRequest request) throws Exception{
		//request parameter 없으면 직접 추출해서 셋팅
		if(request == null) {
			try {
				ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
				request = attrs.getRequest();
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		//해당회사 리스트만 조회 -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		//-----------------------------------------------
		
		Map<String,Object> vendorInfo = new HashMap<String,Object>();
		
		List<String> venZzorgs = null;			//파트너사 코드별 거래 계열사 (마트, 슈퍼, CS)
		List<String> venPurDepts = null;		//파트너사 코드별 선택가능한 구매조직
		String venZzorgNm = "";					//(화면표시or문구용) 거래 계열사명
		
		//파트너사 코드별 계열사 확인
		CommonProductVO zzorgVo = commonProductDao.selectVendorZzorgInfo(paramVo); 
		
		if(zzorgVo != null) {
			//거래 계열사명
			venZzorgNm = StringUtils.defaultString(zzorgVo.getZzorgNm());
			
			//거래 계열사 코드
			String zzorgArrStr = StringUtils.defaultString(zzorgVo.getZzorg());		//파트너사 거래계열사 ArrayString
			String[] zzorgArr = zzorgArrStr.split(",");						//파트너사 거래계열사 Array
			if(zzorgArr != null && zzorgArr.length > 0) {
				venZzorgs = Arrays.asList(zzorgArr);						//파트너사 거래계열사 List<String>
			}
			
			//파트너사가 사용가능한 구매조직 필터링
			paramVo.setZzorgArr(zzorgArr);
			venPurDepts = commonProductDao.selectVenPurDeptInfo(paramVo);
		}
		
		vendorInfo.put("venZzorgs", venZzorgs);		//파트너사 계열사		(List<String>)
		vendorInfo.put("venPurDepts", venPurDepts);	//파트너사 구매조직		(List<String>)
		
		vendorInfo.put("venZzorgNm", venZzorgNm);	//파트너사 계열사명		(String)
		
		return vendorInfo;
	}

	/**
	 * [공통코드] TPC_NEW_CODE 공통코드 조회
	 * - customTagService 내에 정의되지 않은 변수 사용금지 (에러발생함)
	 */
	@Override
	public List<HashBox> selectTpcNewCode(CommonProductVO paramVo) throws Exception {
		List<HashBox> codeList = null;
		
		/*
		 * 1) 넘어온 파라미터 데이터 전처리
		 * - null >> ""
		 * - 'notIn' >> 대문자로 변경
		 */
		String majorCd = StringUtils.defaultString(paramVo.getMajorCd());			//마스터코드
		String subExtent01 = StringUtils.defaultString(paramVo.getSubExtent01());	//확장코드1
		String subExtent02 = StringUtils.defaultString(paramVo.getSubExtent01());	//확장코드2
		String subExtent03 = StringUtils.defaultString(paramVo.getSubExtent01());	//확장코드3
		String subExtent04 = StringUtils.defaultString(paramVo.getSubExtent01());	//확장코드4
		
		String notIn = StringUtils.defaultString(paramVo.getNotIn()).toUpperCase();	//확장코드리스트 notIn 조건
		String[] subExtentList01 = paramVo.getSubExtentList01();	//확장코드01리스트
		String[] subExtentList02 = paramVo.getSubExtentList02();	//확장코드02리스트
		
		String subNmUseYn = StringUtils.defaultString(paramVo.getSubNmUseYn()).toUpperCase();	//확장이름 사용여부
		
		/*
		 * 2) 코드 조회용 파라미터 셋팅 (필수 제외하고는 값 있을 때만 셋팅함)
		 */
		HashBox paramMap = new HashBox();
		paramMap.put("parentCodeId", majorCd);			//마스터코드 (필수)
		paramMap.put("orderSeqYn", "Y");				//정렬순번으로 정렬처리
		
		if(!"".equals(subExtent01)) paramMap.put("childCodeId", subExtent01);	//확장코드1
		if(!"".equals(subExtent02)) paramMap.put("subCodeId02", subExtent02);	//확장코드2
		if(!"".equals(subExtent03)) paramMap.put("subCodeId03", subExtent03);	//확장코드3
		if(!"".equals(subExtent04)) paramMap.put("subCodeId", 	subExtent04);	//확장코드4
		
		if(!"".equals(notIn)) paramMap.put("notIn", notIn);	//확장코드 리스트 NotIn 조건 (default: In)
		if(subExtentList01 != null) paramMap.put("childCodeList", subExtentList01);			//확장코드 1 리스트
		if(subExtentList02 != null) paramMap.put("subCodeList02", subExtentList02);			//확장코드 2 리스트
		
		if(!"".equals(subNmUseYn)) paramMap.put("subNmUseYn", subNmUseYn);	//확장이름 사용여부
		
		
		/*
		 * 3) 코드리스트 조회
		 */
		codeList = customTagDao.getNewTcpCode(paramMap);
		
		return codeList;
	}
	
	/**
	 * 업체코드별 업무에 사용 가능한 구매조직 list 조회
	 * - 협력사코드 셋팅 (필수)
	 * - 업무별 조회조건 필요 시 셋팅 (선택) -- codeSrchInfo 변수에 setting
	 */
	public List<HashBox> selectVendorPurDeptsWorkUsable(CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		//반환 codelist
		List<HashBox> result = new ArrayList<HashBox>();
		List<HashBox> codeList = null;
		
		/*
		 * 1) 협력업체별 계열사 및 구매조직 정보 조회
		 */
		Map<String,Object> vendorZzorgInfo = this.selectVendorZzorgInfo(paramVo, request);
		
		//협력업체별 사용가능 구매조직 list
		List<String> venPurDepts = (List<String>) MapUtils.getObject(vendorZzorgInfo, "venPurDepts");
		
		//협력업체별 사용가능 구매조직 list가 없을 경우, 빈 codeList 반환
		if(venPurDepts == null || venPurDepts.isEmpty()) return result;
		
		/*
		 * 2) 업무별 구매조직 리스트 조회
		 * - 업무별 조회 조건 셋팅 필요~
		 */
		//구매조직 코드리스트 조회
		CommonProductVO srchVo = (paramVo.getCodeSrchInfo() != null)?paramVo.getCodeSrchInfo():new CommonProductVO();
		srchVo.setMajorCd("PURDE");
		codeList = this.selectTpcNewCode(srchVo); 
		
		if(codeList == null || codeList.isEmpty()) return result;
		
		/*
		 * 3) 업무에서 사용하는 구매조직 중, 선택한 협력사가 사용가능한 리스트만 추출
		 */
		String codeId = "";
		for(HashBox hb : codeList){
			codeId = (String) hb.getOrDefault("CODE_ID", "");
			
			//협력업체가 사용할 수 있는 구매조직만 반환 list에 추가
			if(!"".equals(codeId) && venPurDepts.contains(codeId)) {
				result.add(hb);
			}
		}
		
		return result;
	}

	/**
	 * 구매조직별 ECS 수신 계열사 정보 조회
	 */
	@Override
	public CommonProductVO selectEcsRecvCompInfo(CommonProductVO vo) throws Exception {
		//구매조직별 ECS 수신 계열사 정보 조회
		CommonProductVO ecsRcvCompVo = commonProductDao.selectEcsRecvCompInfo(vo);
		
		//조회 결과 없음
		if(ecsRcvCompVo == null) {
			//null exception 방지 위해 빈 vo setting 
			ecsRcvCompVo = new CommonProductVO();
		}
		
		return ecsRcvCompVo;
	}

	/**
	 * ECS 계약/공문 양식정보 조회
	 */
	@Override
	public Map<String, String> selectEcsDocInfo(String workGbn, String docGbn) throws Exception {
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("WORK_GBN", workGbn);	//업무구분
		paramMap.put("DOC_GBN", docGbn);	//양식구분
		
		//양식정보조회
		Map<String, String> docInfo = commonProductDao.selectEcsDocInfo(paramMap);
		
		return docInfo;
	}
	
	/**
	 * 나의 업체 리스트 조회 (로그인 세션에서 추출)
	 */
	public List<Map<String, Object>> selectMyVendorList(CommonProductVO paramVo, HttpServletRequest request) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (paramVo == null || request == null) {
			return list;
		}
		
		//조회대상 조직구분
		String[] srchZzorgs = paramVo.getZzorgArr();
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		
		String[] trdTypCds = epcLoginVO.getTradeType();		//거래유형코드
		String[] trdTypNms = epcLoginVO.getTradeTypeNm();	//거래유형명
		String[] zzorgs = epcLoginVO.getZzorg();			//거래조직구분
		String[] zzorgNms = epcLoginVO.getZzorgNm();		//거래조직구분명
		
		String[] venZzorgArr = null;
		String[] venZzorgNmArr = null;
		
		Map<String, Object> venInfo = null;
		for(int i = 0; i < myVenCds.length; i++){
			venInfo = new HashMap<String, Object>();
			
			venInfo.put("VEN_CD", myVenCds[i]);			//파트너사코드
			venInfo.put("TRD_TYP_FG", trdTypCds[i]);	//거래유형코드
			venInfo.put("TRD_TYP_NM", trdTypNms[i]);	//거래유형명
			
			venZzorgArr = zzorgs[i].split(",");
			venInfo.put("ZZORG_CDS", venZzorgArr);		//조직구분 코드 arrays
			
			venZzorgNmArr = zzorgNms[i].split(",");
			venInfo.put("ZZORG_NMS", venZzorgNmArr);	//조직구분 명 arrays
			
			if(this.checkArrayContainsAny(venZzorgArr, srchZzorgs)) {
				list.add(venInfo);
			}
		}
		
		return list;
	}
	
	/**
	 * array1에 array2 요소 중 한 개라도 포함되었는지 check
	 * - array2 값이 없으면 항상 true
	 * - array1 값이 없으면 항상 false
	 */
	private boolean checkArrayContainsAny(String[] array1, String[] array2) {
		if(array2 == null || array2.length == 0) return true;
		if(array1 == null || array1.length == 0) return false;
		
		try {
			List<String> list1 = Arrays.asList(array1);
	        for (String element : array2) {
	            if (list1.contains(element)) {
	                return true;
	            }
	        }
		}catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
        return false;
    }
}
