package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.CommonProductVO;

import lcn.module.common.util.HashBox;

/**
 * @Class Name : CommonProductService
 * @Description : 삼풍관련 공통 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26		SONG MIN KYO	최초생성
 * </pre>
 */

public interface CommonProductService {

	
	/**
	 * 신상품 장려금 대상 업체 여부 확인 
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int selectNcheckCountVendorNewPromoFg(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 거래중지된 업체인지 체크
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int selectNcheckCountVendorStopTrading(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 협력업체 거래유형 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HashMap selectNVendorTradeType(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 협력업체 과세구분조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String selectNVendorTaxType(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 팀 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNteamList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * ecs 사업자별 팀 조회 
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectEcsNteamList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	
	/**
	 * 팀의 대분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL1list(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 팀의 중분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL2list(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 대분류의 중분류 조회 (팀X)
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> NselectNoTeamL2List(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 중분류의 소분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL3list(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 중분류의 소분류 조회 (팀X)
	 * @param paramMap
	 * @return List<CommonProductVO>
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetNoTeamL3List(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 소분류의 그룹분석속성 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetGrplist(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	/**
	 * 계절년도 리스트
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNseasonYear(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 계절리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> NselectSeasonList(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	/**
	 * 전상법 콤보박스 변경시 해당그룹의 리스트 조회
	 * @param infoGrpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdAddTemplateDetailList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * KC인증 콤보박스 변경시 해당그룹의 리스트 조회
	 * @param infoGrpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateDetailList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 대분류 변경으로 전자상거래 콤보리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdAddTemplateList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 대분류 변경으로 KC인증 콤보리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 판매코드 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectSrcmkCdList(CommonProductVO paramMap) throws Exception;
	public int selectSrcmkCdListCount(CommonProductVO paramMap) throws Exception;
	
	/**
	 * ECS 수신 담당자 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selEcsReceiverPopupInfo(CommonProductVO paramMap) throws Exception;
	public int selEcsReceiverPopupInfoCount(CommonProductVO paramMap) throws Exception; 
	
	/**
	 * 점포코드 조회 
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectStrCdList(Map<String, Object> paramMap) throws Exception;
	public int selectStrCdListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 팀,대분류 한번에 나오게 조회 
	 * @param paramMap
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectTeamL1CdList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 통화구분 공통 코드 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectWaersCdList(Map<String, Object> paramMap) throws Exception;
	
	
	/**
	 * htmlcodeTag 값 전부 조회해오기 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> selectCodeTagList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 업체코드별 계열사 및 구매조직 정보
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectVendorZzorgInfo(CommonProductVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * [공통코드] TPC_NEW_CODE 공통코드 조회
	 * @param paramVo
	 * @return List<HashBox>
	 * @throws Exception
	 */
	public List<HashBox> selectTpcNewCode(CommonProductVO paramVo) throws Exception;
	
	/**
	 * 업체코드별 업무에 사용 가능한 구매조직 list 조회
	 * @param paramVo
	 * @param request
	 * @return List<HashBox>
	 * @throws Exception
	 */
	public List<HashBox> selectVendorPurDeptsWorkUsable(CommonProductVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 구매조직별 ECS 수신 계열사 정보 조회
	 * @param vo
	 * @return CommonProductVO
	 * @throws Exception
	 */
	public CommonProductVO selectEcsRecvCompInfo(CommonProductVO vo) throws Exception;
	
	/**
	 * ECS 계약/공문 양식정보 조회
	 * @param workGbn
	 * @param docGbn
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> selectEcsDocInfo(String workGbn, String docGbn) throws Exception;
	
	/**
	 * 나의 업체 리스트 조회 (로그인 세션에서 추출)
	 * @param paramVo
	 * @param request
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectMyVendorList(CommonProductVO paramVo, HttpServletRequest request) throws Exception;
} 
