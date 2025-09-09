package com.lottemart.epc.edi.product.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lcnjf.util.NumberUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.EcsUtil;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.controller.NEDMPRO0020Controller;
import com.lottemart.epc.edi.product.dao.NEDMPRO0310Dao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0310VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0310Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0310Service")
public class NEDMPRO0310ServiceImpl implements NEDMPRO0310Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);
	
	private static final String ECS_GB = "ET";
	
	@Autowired
	private NEDMPRO0310Dao nedmpro0310Dao;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private ConfigurationService config;
	
	@Resource(name="commonProductService")
	private CommonProductService commonProductService;
	
	@Autowired
	EcsUtil ecsUtil;
	
	/**
	 * 파트너사 행사 신청 상세 헤더 조회
	 */
	public NEDMPRO0310VO selectProEventAppDetail(NEDMPRO0310VO paramVo) throws Exception {
		return nedmpro0310Dao.selectProEventAppDetail(paramVo);
	}
	
	/**
	 * 파트너사 행사 신청 상세 아이템 조회
	 */
	public List<NEDMPRO0310VO> selectProEventAppItemList(NEDMPRO0310VO paramVo) throws Exception {
		return nedmpro0310Dao.selectProEventAppItemList(paramVo);
	}
	
	/**
	 * 파트너사 행사 정보 저장
	 */
	public HashMap<String,Object> insertProEventApp(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String reqOfrcdSeq = "";
		
		try {
			
			paramVo.setLifnr(epcLoginVO.getRepVendorId()); // 파트너사 코드
			paramVo.setWorkId( workId ); // 작업자 아이디
			
			// 신규 이면
			if("Y".equals( paramVo.getProdEvntNewYn() )) {
				reqOfrcdSeq = nedmpro0310Dao.selectReqOfrcdSeq(paramVo);	// 파트너사 행사 번호 순번 조회
				paramVo.setReqOfrcd( reqOfrcdSeq ); // 파트너사 행사 순번
				nedmpro0310Dao.insertProEventApp(paramVo);	// 행사 header 정보 저장
				returnMap.put("reqOfrcd", reqOfrcdSeq);
			}else {
				nedmpro0310Dao.updateProEventApp(paramVo);	// 행사 header 정보 수정
				returnMap.put("reqOfrcd", paramVo.getReqOfrcd());
			}
			
			if("01".equals( paramVo.getApprStatus() )) nedmpro0310Dao.deleteProEventAppItemList(paramVo); // 행사 itme 저장 전 정보 삭제
			
			// 행사 item 저장
			for(NEDMPRO0310VO vo : paramVo.getItemList()){
				vo.setWorkId( workId ); // 작업자 아이디
				vo.setDelYn( paramVo.getDelYn() );	// 삭제여부
				vo.setApprStatus(paramVo.getApprStatus());
				if("Y".equals( paramVo.getProdEvntNewYn() )) vo.setReqOfrcd( reqOfrcdSeq ); // 파트너사 행사 순번
				else vo.setReqOfrcd( paramVo.getReqOfrcd() ); // 파트너사 행사 순번
				
				// 진행상태가 "요청대기"만 insert!!!
				if("01".equals( paramVo.getApprStatus() )) nedmpro0310Dao.mergeProEvntItemList(vo); // 행사 item 저장(MERGE)
				
				// item 정보가 신규 추가면..
				//if( "new".equals(vo.getRowAttri()) ) nedmpro0310Dao.insertProEventAppItemList(vo); 	// 행사 item 저장
				//else nedmpro0310Dao.updateProEventAppItemList(vo);	// 행사 item 정보 수정
			}// end for
			
			//nedmpro0310Dao.deleteProEventAppItemList(paramVo);	// 행사 itme 저장 전 정보 삭제
			returnMap.put("msg", "정상적으로 저장되었습니다.");
			returnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("msg", "저장에 실패했습니다.\\n다시 확인해주세요.");
		}
		
		return returnMap;
	}
	
	/**
	 * 파트너사 행사 정보 삭제
	 */
	public HashMap<String,Object> deleteProEventApp(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		try {
			nedmpro0310Dao.updateProEventApp(paramVo);			// 행사 header 정보 삭제 처리
			nedmpro0310Dao.updateProEventAppItemList(paramVo);	// 행사 itme 정보 삭제 처리
			returnMap.put("result", true);
			returnMap.put("msg", "정상적으로 삭제되었습니다.");
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("msg", "저장에 실패했습니다.\\n다시 확인해주세요.");
		}
		
		return returnMap;
	}
	
	/**
	 * 파트너사 행사 item 정보 삭제
	 */
	public HashMap<String,Object> deleteProEventAppItem(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		try {
			nedmpro0310Dao.updateProEventAppItemList(paramVo);	// 행사 itme 정보 삭제 처리
			returnMap.put("result", true);
			returnMap.put("msg", "정상적으로 삭제되었습니다.");
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("msg", "저장에 실패했습니다.\\n다시 확인해주세요.");
		}
		
		return returnMap;
	}
	
	/**
	 * 파트너사 행사 정보 ECO 전송
	 */
	public Map<String, Object> insertProEventAppRfcCall(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String rtnResult = "F";
		returnMap.put("result", true);

		try {
			
			paramVo.setDelYn("N");
			
			//작업자 정보 세팅
			paramVo.setRegId(workId);
			paramVo.setModId(workId);
			
			nedmpro0310Dao.updateProEventApp(paramVo);
			nedmpro0310Dao.updateProEventAppItemList(paramVo);
			
			//----- RFC Call 처리
			List<LinkedHashMap> headerData = nedmpro0310Dao.selectEvntRfcCallData(paramVo);
			List<LinkedHashMap> itemData = nedmpro0310Dao.selectEvntItemRfcCallData(paramVo);
			JSONObject jo = new JSONObject();
			jo.put("ITEM", itemData);
			jo.put("HEADER", headerData);
			
			logger.info("sap send String ::::::::::: " + jo.toString() );
			returnMap = rfcCommonService.rfcCall("PMR2040", jo.toString(), workId);
			JSONObject mapObj        = new JSONObject(returnMap.toString());  
			JSONObject resultObj     = mapObj.getJSONObject("result");        
			JSONObject respCommonObj = resultObj.getJSONObject("ES_RETURN");  
			rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("TYPE")); 
			
			logger.info("return message ::::::::::::::: " + returnMap);
			// 성공이 아니면..
			if (!rtnResult.equals("S")) {
				returnMap.put("result", false);
				returnMap.put("msg", "행사 요청을 실패했습니다. \n잠시후 다시 시도해주세요.");
			}else {
				//paramVo.setTaskGbn("eventReq");
				returnMap.put("reqOfrcd", paramVo.getReqOfrcd());
				returnMap.put("result", true);
				returnMap.put("msg", "정상적으로 행사요청을 하였습니다.");
			}
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("reqOfrcd", paramVo.getReqOfrcd());
			returnMap.put("result", false);
			returnMap.put("msg", "저장에 실패했습니다. \n다시 확인해주세요.");
		}
		
		return returnMap;
	}
	
	
	/**
	 * 판매 바코드 조회
	 */
	public HashMap<String,Object> selectSaleBarcodeList(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		int totalCount = 0;
		
		// 판매 바코드 카운터 조회
		totalCount = nedmpro0310Dao.selectSaleBarcodeListCount(paramVo);

		if(totalCount > 0){	// 판매 바코드 리스트 조회
			list = nedmpro0310Dao.selectSaleBarcodeList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
	}
	
	/**
	 * 공문서 작성 요청
	 */
	public Map<String,Object> insertProdEcsIntgr(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		// 계약 생성 카운트
		int cnt = ("KR02".equals(prodEvntVo.getVkorg())) ? 1 : 2;
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		prodEvntVo.setContCode("");
		prodEvntVo.setContCode2("");
		paramVo.setContCode("");
		paramVo.setContCode2("");
		
		//ECS 수신 업체정보 조회용 vo
		CommonProductVO ecsRecvCompVo = new CommonProductVO();
		ecsRecvCompVo.setPurDept(prodEvntVo.getVkorg());	//계열사코드 setting
		//int z = 0;
		//for(int z = 0; z < cnt; z++) {
		
		//ECS 양식정보 START ==============================================================
		Map<String,String> ecsDocInfo = null;
		String docGbn = "";		//ECS 양식구분코드
		
		//계열사 별 양식구분코드 setting
		if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
			docGbn = "0"+prodEvntVo.getReqContyp();		//ECS 양식구분코드
			
			//CONT CODE 셋팅
			prodEvntVo.setContCode(ecsContCode);
			paramVo.setContCode(ecsContCode);
		}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
			// 슈퍼, 씨에스 계약 2번 생성
			// 슈퍼
			if( "ecsIntgrMd".equals( paramVo.getTaskGbn() )) {
				docGbn = "1"+prodEvntVo.getReqContyp();	//ECS 양식구분코드
				
				//CONT CODE 셋팅
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode(ecsContCode);
			}else {
				// 씨에스유통
				ecsRecvCompVo.setPurDept("KR05");
				docGbn = "2"+prodEvntVo.getReqContyp();	//ECS 양식구분코드
				
				//CONT CODE 셋팅
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode2(ecsContCode);
			}
		}
		
		//ECS 양식정보 조회
		ecsDocInfo = commonProductService.selectEcsDocInfo(ECS_GB, docGbn);
		String ecsDocGbn = ECS_GB+docGbn;
		
		//ECS 양식정보 없음
		if(ecsDocInfo == null) {
			returnMap.put("msg", "공문 양식정보가 존재하지 않습니다.");
			return returnMap;
		}
		
		String depCd = MapUtils.getString(ecsDocInfo, "DEP_CD", "");		//대분류코드_ECS 관리코드(FRM_LARGE_CD)
		String linCd = MapUtils.getString(ecsDocInfo, "LIN_CD", "");		//중분류코드_ECS 관리코드(FRM_MIDDLE_CD)
		String dcCd = MapUtils.getString(ecsDocInfo, "DC_CD", "");			//계약명코드(BUN_CD)
		String dcNmCd = MapUtils.getString(ecsDocInfo, "DC_NM_CD", "");		//계약서코드
		
		//양식정보 누락건 존재
		if("".equals(depCd) || "".equals(linCd) || "".equals(dcCd) || "".equals(dcNmCd)) {
			logger.error(String.format("ECS_DOC_INFO IS EMPTY ::: DEP_CD : %s / LIN_CD : %s / DC_CD : %s / DC_NM_CD : %s", depCd, linCd, dcCd, dcNmCd));
			returnMap.put("msg", "공문 양식정보가 올바르지 않습니다.");
			return returnMap;
		}
		
		//ECS 양식정보 setting
		prodEvntVo.setEcsDepCd(depCd);		//대분류코드_ECS 관리코드(FRM_LARGE_CD)
		prodEvntVo.setLinCd(linCd);			//중분류코드_ECS 관리코드(FRM_MIDDLE_CD)
		prodEvntVo.setDcCd(dcCd);			//계약명코드(BUN_CD)
		prodEvntVo.setDcNmCd(dcNmCd);		//계약서코드
		
//			if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
//				// 마트
//				prodEvntVo.setEcsDepCd("510");
//				
//				// 원매가 행사 공문
//				if( "00".equals( prodEvntVo.getReqContyp() )) {
//					prodEvntVo.setLinCd("007");
//					prodEvntVo.setDcCd("12653");
//					prodEvntVo.setDcNmCd("15104");
//				}else {
//					prodEvntVo.setLinCd("006");
//					//  판촉요청 합의서 (50:50)
//					if( "01".equals( prodEvntVo.getReqContyp() )) {
//						prodEvntVo.setDcCd("12651");
//						prodEvntVo.setDcNmCd("15099");
//					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
//						// 판촉요청 합의서 (자율분담)
//						prodEvntVo.setDcCd("12652");
//						prodEvntVo.setDcNmCd("15102");
//					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
//						// 판촉행사 시행 요청 공문 (100:0)
//						prodEvntVo.setDcCd("12651");
//						prodEvntVo.setDcNmCd("15100");
//					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
//						// 판촉행사 시행 요청 공문 (자율분담)
//						prodEvntVo.setDcCd("12652");
//						prodEvntVo.setDcNmCd("15103");
//					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
//						// 판촉행사 신청서_공개모집
//						prodEvntVo.setDcCd("12651");
//						prodEvntVo.setDcNmCd("15101");
//					}
//				}
//				prodEvntVo.setContCode(ecsContCode);
//				paramVo.setContCode(ecsContCode);
//			}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
//				// 슈퍼, 씨에스 계약 2번 생성
//				// 슈퍼
//				if( "ecsIntgrMd".equals( paramVo.getTaskGbn() )) {
//					prodEvntVo.setEcsDepCd("511");
//					
//					// 원매가 행사 공문
//					if( "00".equals( prodEvntVo.getReqContyp() )) {
//						prodEvntVo.setLinCd("001");
//						prodEvntVo.setDcCd("12657");
//						prodEvntVo.setDcNmCd("15108");
//					}else {
//						prodEvntVo.setLinCd("003");
//						//  판촉요청 합의서 (50:50)
//						if( "01".equals( prodEvntVo.getReqContyp() )) {
//							prodEvntVo.setDcCd("12659");
//							prodEvntVo.setDcNmCd("15110");
//						}else if( "02".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉요청 합의서 (자율분담)
//							prodEvntVo.setDcCd("12660");
//							prodEvntVo.setDcNmCd("15113");
//						}else if( "03".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 시행 요청 공문 (100:0)
//							prodEvntVo.setDcCd("12659");
//							prodEvntVo.setDcNmCd("15111");
//						}else if( "04".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 시행 요청 공문 (자율분담)
//							prodEvntVo.setDcCd("12660");
//							prodEvntVo.setDcNmCd("15114");
//						}else if( "05".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 신청서_공개모집
//							prodEvntVo.setDcCd("12659");
//							prodEvntVo.setDcNmCd("15112");
//						}
//					}
//					prodEvntVo.setContCode(ecsContCode);
//					paramVo.setContCode(ecsContCode);
//				}else {
//					// 씨에스유통
//					prodEvntVo.setEcsDepCd("512");
//					ecsRecvCompVo.setPurDept("KR05");
//					// 원매가 행사 공문
//					if( "00".equals( prodEvntVo.getReqContyp() )) {
//						prodEvntVo.setLinCd("001");
//						prodEvntVo.setDcCd("12658");
//						prodEvntVo.setDcNmCd("15109");
//					}else {
//						prodEvntVo.setLinCd("004");
//						//  판촉요청 합의서 (50:50)
//						if( "01".equals( prodEvntVo.getReqContyp() )) {
//							prodEvntVo.setDcCd("12665");
//							prodEvntVo.setDcNmCd("15118");
//						}else if( "02".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉요청 합의서 (자율분담)
//							prodEvntVo.setDcCd("12666");
//							prodEvntVo.setDcNmCd("15121");
//						}else if( "03".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 시행 요청 공문 (100:0)
//							prodEvntVo.setDcCd("12665");
//							prodEvntVo.setDcNmCd("15119");
//						}else if( "04".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 시행 요청 공문 (자율분담)
//							prodEvntVo.setDcCd("12666");
//							prodEvntVo.setDcNmCd("15122");
//						}else if( "05".equals( prodEvntVo.getReqContyp() )) {
//							// 판촉행사 신청서_공개모집
//							prodEvntVo.setDcCd("12665");
//							prodEvntVo.setDcNmCd("15120");
//						}
//					}
//					prodEvntVo.setContCode(ecsContCode);
//					paramVo.setContCode2(ecsContCode);
//				}
//			}
			//ECS 양식정보 END ==============================================================

			ecsRecvCompVo = commonProductService.selectEcsRecvCompInfo(ecsRecvCompVo);
			prodEvntVo.setCompNum(ecsRecvCompVo != null? ecsRecvCompVo.getEcsRecvCompNum() : "");
			prodEvntVo.setCompName(ecsRecvCompVo != null? ecsRecvCompVo.getEcsRecvCompName() : "");
			prodEvntVo.setTeamCd(prodEvntVo.getDepCd());
			prodEvntVo.setEmpSabun(prodEvntVo.getEmpNo());

			//공문 수신 업체 사업자번호 조회
			if("".equals(prodEvntVo.getCompNum())) {
				returnMap.put("msg", "공문 수신처 사업자정보가 존재하지 않아 공문 발송이 불가능합니다.");
				return returnMap;
			}
			
			//Header ------------------------------------------------------------------
			Map<String,Object> hMap = new HashMap<String,Object>();
			hMap.put("FAMILY_YN", "N");						//계열사구분
			hMap.put("CONT_CODE", prodEvntVo.getContCode()); //EPC 내부 관리번호
			hMap.put("DC_NAME", prodEvntVo.getReqOfrTxt());	//계약명
			hMap.put("DC_CONDATE", DateUtil.getToday("yyyyMMdd"));	//계약일자
			//hMap.put("DC_FROM", prodEvntVo.getOfsdt() );	//계약시작일자
			//hMap.put("DC_END", prodEvntVo.getOfedt());		//계약종료일자
			hMap.put("DEP_CD", prodEvntVo.getEcsDepCd());	//ECS관리코드(FRM_LARGE_CD)
			hMap.put("LIN_CD", prodEvntVo.getLinCd());		//ECS관리코드(FRM_MIDDLE_CD)
			hMap.put("DC_CD", prodEvntVo.getDcCd());		//계약명코드(BUN_CD)
			hMap.put("DC_NM_CD", prodEvntVo.getDcNmCd());	//계약서코드
			
			String hXml = EcsUtil.createHeader(hMap);	//header Xml 
			
			//body --------------------------------------------------------------------
			StringBuilder body = new StringBuilder();
			//공통 - 수신업체정보
			Map<String,Object> recvCompMap = new HashMap<String,Object>();
			recvCompMap.put("COMP_NUM", prodEvntVo.getCompNum());	//사업자번호
			recvCompMap.put("COMP_NAME", prodEvntVo.getCompName());	//사업자명
			recvCompMap.put("TEAM_CD", prodEvntVo.getTeamCd());		//팀코드
			recvCompMap.put("EMP_SABUN", prodEvntVo.getEmpSabun());	//사번
			
			String recvCompXml = EcsUtil.getMapData("COMP_LIST", recvCompMap);
			body.append(recvCompXml);
			
			Map<String,Object> itemData = new HashMap<String,Object>();
			List<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();
			
			if( "00".equals( prodEvntVo.getReqContyp() )) {
				// 원매가 행사 공문
				//itemData.put("DC_IF_1", "뭘까용???");
				itemData.put("DC_IF_2", prodEvntVo.getReqOfrTxt()); // 행사명
				itemData.put("DC_IF_3", prodEvntVo.getOfsdt() + " ~ " +prodEvntVo.getOfedt()); // 행사기간
				itemData.put("DC_IF_4", prodEvntVo.getPrsdt() + " ~ " +prodEvntVo.getPredt()); // 발주기간
			}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
				// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)						
				itemData.put("DC_IF_1", prodEvntVo.getReqOfrTxt()); // 행사명
				itemData.put("DC_IF_2", prodEvntVo.getOfsdt() + " ~ " +prodEvntVo.getOfedt()); // 행사기간
				// 증정 사은품이면..
				if("4002".equals(prodEvntVo.getReqType())) {
					itemData.put("DC_IF_15", ""); // 체크박스
					itemData.put("DC_IF_16", "0"); // 예상판촉비용
					itemData.put("DC_IF_17", prodEvntVo.getVkorgRate()); // 계열사 분담율
					itemData.put("DC_IF_18", prodEvntVo.getHdVenRate()); // 파트너사 분담율
					itemData.put("DC_IF_19", "V"); // 체크박스
					itemData.put("DC_IF_20", prodEvntVo.getOfrCost()); // 예상판촉비용
					itemData.put("DC_IF_21", prodEvntVo.getVkorgRate()); // 계열사 분담율
					itemData.put("DC_IF_22", prodEvntVo.getHdVenRate()); // 파트너사 분담율
				}else {
					itemData.put("DC_IF_15", "V"); // 체크박스
					itemData.put("DC_IF_16", prodEvntVo.getOfrCost()); // 예상판촉비용
					itemData.put("DC_IF_17", prodEvntVo.getVkorgRate()); // 계열사 분담율
					itemData.put("DC_IF_18", prodEvntVo.getHdVenRate()); // 파트너사 분담율
					itemData.put("DC_IF_19", ""); // 체크박스
					itemData.put("DC_IF_20", "0"); // 예상판촉비용
					itemData.put("DC_IF_21", prodEvntVo.getVkorgRate()); // 계열사 분담율
					itemData.put("DC_IF_22", prodEvntVo.getHdVenRate()); // 파트너사 분담율
				}
			}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
				// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
				itemData.put("DC_IF_1", prodEvntVo.getOfsdt() + " ~ " +prodEvntVo.getOfedt()); // 행사기간
				itemData.put("DC_IF_2", prodEvntVo.getOfrCost()); // 예상판촉비용
				//itemData.put("DC_IF_3", "뭘까용");
				//itemData.put("DC_IF_4", "뭘까용2"); 
			}else if( "05".equals( prodEvntVo.getReqContyp() )) {
				// 판촉행사 신청서 공개모집
				itemData.put("DC_IF_1", prodEvntVo.getReqOfrTxt()); // 행사명
				itemData.put("DC_IF_2", prodEvntVo.getOfsdt() + " ~ " +prodEvntVo.getOfedt()); // 행사기간
			}
			itemDataList.add(itemData);
			for(int i = 0; i < itemDataList.size(); i++) {
				body.append( EcsUtil.getMapOfficialItem(prodEvntVo.getDcNmCd(), itemDataList.get(i)) );
			}
			
			Map<String,Object> tableHeadData = new HashMap<String,Object>();
			List<Map<String,Object>> tableHeadDataList = new ArrayList<Map<String,Object>>();
			
			if( !"8001".equals( prodEvntVo.getReqType() ) ) {
				tableHeadData.put("ROW_NUM", "0"); // row num
				tableHeadData.put("COL1", "판매바코드"); // 헤더명1
				tableHeadData.put("COL2", "상품명"); // 헤더명2
				
				// 원가 인하
				if( "01".equals( prodEvntVo.getCostType() ) ) {
					// 번들이 아니면 변경금액, 정상원가 데이터 넣어주기
					if( !"1002".equals( prodEvntVo.getReqType() ) ) {
						tableHeadData.put("COL3", "변경금액(원)/율(%)"); // 헤더명3
						tableHeadData.put("COL4", "정상원가(원)"); // 헤더명4
						tableHeadData.put("COL5", "행사원가(원)"); // 헤더명5
					}else {
						tableHeadData.put("COL3", "행사원가(원)"); // 헤더명3
					}
					
					if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
						// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
						tableHeadData.put("COL6", "변경금액(원)/율(%)"); // 헤더명6
						tableHeadData.put("COL7", "정상판매가(원)"); // 헤더명7
						tableHeadData.put("COL8", "행사판매가(원)"); // 헤더명8
						tableHeadData.put("COL9", "세부사항"); // 헤더명9
						tableHeadData.put("COL10", "비고"); // 헤더명10
					}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
						// 번들
						tableHeadData.put("COL4", "기준1(해당상품N개구매시)"); // 헤더명4
						tableHeadData.put("COL6", "기준2"); // 헤더명6
						tableHeadData.put("COL8", "기준3"); // 헤더명8
						tableHeadData.put("COL10", "세부사항"); // 헤더명10
						if( "01".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정액판매가 서식 4번
							tableHeadData.put("COL5", "행사판매가1(원)"); // 헤더명5
							tableHeadData.put("COL7", "행사판매가2(원)"); // 헤더명7
							tableHeadData.put("COL9", "행사판매가3(원)"); // 헤더명9
						}else if( "03".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정액할인 서식 5번
							tableHeadData.put("COL5", "할인액1(원)"); // 헤더명5
							tableHeadData.put("COL7", "할인액2(원)"); // 헤더명7
							tableHeadData.put("COL9", "할인액3(원)"); // 헤더명9
						}else if( "04".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정율할인 서식 6번
							tableHeadData.put("COL5", "할인율1(%)"); // 헤더명5
							tableHeadData.put("COL7", "할인율2(%)"); // 헤더명7
							tableHeadData.put("COL9", "할인율3(%)"); // 헤더명9
						}
					}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
						// M+N
						tableHeadData.put("COL6", "M(개)"); // 헤더명6
						tableHeadData.put("COL7", "N(개)"); // 헤더명7
						tableHeadData.put("COL8", "세부사항"); // 헤더명8
						tableHeadData.put("COL9", "비고"); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
						// 연관
						tableHeadData.put("COL6", "행사기준"); // 헤더명6
						tableHeadData.put("COL7", "변경금액(원)/율(%)"); // 헤더명7
						tableHeadData.put("COL8", "정상판매가(원)"); // 헤더명8
						tableHeadData.put("COL9", "행사판매가(원)"); // 헤더명9
						tableHeadData.put("COL10", "세부사항"); // 헤더명10
					}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
						// 상품권
						tableHeadData.put("COL6", "결제금액기준(동일입력)"); // 헤더명6
						tableHeadData.put("COL7", "증정/사은금액"); // 헤더명7
						tableHeadData.put("COL8", "세부사항"); // 헤더명8
						tableHeadData.put("COL9", "비고"); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}/*else if( "8001".equals( prodEvntVo.getReqType() ) ) {
						// 원매가 인하
						tableHeadData.put("COL6", "세부사항"); // 헤더명6
						tableHeadData.put("COL7", "비고"); // 헤더명7
						tableHeadData.put("COL8", ""); // 헤더명8
						tableHeadData.put("COL9", ""); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}*/
				}else {
					// 사후 정산
					if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
						// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
						tableHeadData.put("COL3", "변경금액(원)/율(%)"); // 헤더명3
						tableHeadData.put("COL4", "정상판매가(원)"); // 헤더명4
						tableHeadData.put("COL5", "행사판매가(원)"); // 헤더명5
						tableHeadData.put("COL6", "분담율"); // 헤더명6
						tableHeadData.put("COL7", "세부사항"); // 헤더명7
						tableHeadData.put("COL8", "비고"); // 헤더명8
						tableHeadData.put("COL9", ""); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
						// 번들
						tableHeadData.put("COL3", "기준1(해당상품N개구매시)"); // 헤더명3
						tableHeadData.put("COL5", "기준2"); // 헤더명5
						tableHeadData.put("COL7", "기준3"); // 헤더명7
						tableHeadData.put("COL9", "상품별 분담율"); // 헤더명9
						tableHeadData.put("COL10", "세부사항"); // 헤더명10
						if( "01".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정액판매가 서식 4번
							tableHeadData.put("COL4", "행사판매가1(원)"); // 헤더명4
							tableHeadData.put("COL6", "행사판매가2(원)"); // 헤더명6
							tableHeadData.put("COL8", "행사판매가3(원)"); // 헤더명8
						}else if( "03".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정액할인 서식 5번
							tableHeadData.put("COL4", "할인액1(원)"); // 헤더명4
							tableHeadData.put("COL6", "할인액2(원)"); // 헤더명6
							tableHeadData.put("COL8", "할인액3(원)"); // 헤더명8
						}else if( "04".equals( prodEvntVo.getReqDisc() )){
							// 번들 + 정율할인 서식 6번
							tableHeadData.put("COL4", "할인율1(%)"); // 헤더명4
							tableHeadData.put("COL6", "할인율2(%)"); // 헤더명6
							tableHeadData.put("COL8", "할인율3(%)"); // 헤더명8
						}
					}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
						// M+N
						tableHeadData.put("COL3", "M(개)"); // 헤더명3
						tableHeadData.put("COL4", "N(개)"); // 헤더명4
						tableHeadData.put("COL5", "분담율"); // 헤더명5
						tableHeadData.put("COL6", "세부사항"); // 헤더명6
						tableHeadData.put("COL7", "비고"); // 헤더명7
						tableHeadData.put("COL8", ""); // 헤더명8
						tableHeadData.put("COL9", ""); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
						// 연관
						tableHeadData.put("COL3", "행사기준"); // 헤더명3
						tableHeadData.put("COL4", "변경금액(원)/율(%)"); // 헤더명4
						tableHeadData.put("COL5", "정상판매가(원)"); // 헤더명5
						tableHeadData.put("COL6", "행사판매가(원)"); // 헤더명6
						tableHeadData.put("COL7", "분담율"); // 헤더명7
						tableHeadData.put("COL8", "세부사항"); // 헤더명8
						tableHeadData.put("COL9", "비고"); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
						// 상품권
						tableHeadData.put("COL3", "결제금액기준(동일입력)"); // 헤더명3
						tableHeadData.put("COL4", "증정/사은금액"); // 헤더명4
						tableHeadData.put("COL5", "분담율"); // 헤더명5
						tableHeadData.put("COL6", "세부사항"); // 헤더명6
						tableHeadData.put("COL7", "비고"); // 헤더명7
						tableHeadData.put("COL8", ""); // 헤더명8
						tableHeadData.put("COL9", ""); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}/*else if( "8001".equals( prodEvntVo.getReqType() ) ) {
						// 원매가 인하
						tableHeadData.put("COL3", "변경금액(원)/율(%)"); // 헤더명3
						tableHeadData.put("COL4", "정상원가(원)"); // 헤더명4
						tableHeadData.put("COL5", "행사원가(원)"); // 헤더명5
						tableHeadData.put("COL6", "세부사항"); // 헤더명6
						tableHeadData.put("COL7", "비고"); // 헤더명7
						tableHeadData.put("COL8", ""); // 헤더명8
						tableHeadData.put("COL9", ""); // 헤더명9
						tableHeadData.put("COL10", ""); // 헤더명10
					}*/
				}
				tableHeadDataList.add(tableHeadData);
				body.append( EcsUtil.getMapTableData("IF_RECV_OFFICIAL_TABLE_ROW", prodEvntVo.getDcNmCd(), "1", tableHeadDataList) );
			}
			
			for(int i = 0; i < list.size(); i++) {
				Map<String,Object> tableRowData = new HashMap<String,Object>();
				List<Map<String,Object>> tableRowDataList = new ArrayList<Map<String,Object>>();
				
				tableRowData.put("ROW_NUM", (i+1)); // row num
				tableRowData.put("COL1", list.get(i).getEan11()); // 판매바코드
				tableRowData.put("COL2", list.get(i).getMaktx()); // 상품명
				
				// 원가 인하
				if( "01".equals( prodEvntVo.getCostType() ) ) {
					// 번들이 아니면 변경금액, 정상원가 데이터 넣어주기
					if( !"1002".equals( prodEvntVo.getReqType() ) ) {
						tableRowData.put("COL3", list.get(i).getCalPurPrc() + " / " + list.get(i).getCalComRate()); // 변경금액
						tableRowData.put("COL4", list.get(i).getPurPrc()); // 정상원가(원)
						tableRowData.put("COL5", list.get(i).getReqPurPrc()); // 행사원가
					}else {
						tableRowData.put("COL3", list.get(i).getReqPurPrc()); // 행사원가
					}
					
					
					if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
						// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
						tableRowData.put("COL6", list.get(i).getCalSalesPrc() + " / " + list.get(i).getCalSalesRate() ); // 변경금액
						tableRowData.put("COL7", list.get(i).getReqSalesPrc()); // 정상판매가
						tableRowData.put("COL8", list.get(i).getSalesPrc()); // 행사판매가
						tableRowData.put("COL9", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL10", list.get(i).getZbigo()); // 비고
					}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
						// 번들
						tableRowData.put("COL4", list.get(i).getOfrStd1()); // 기준1
						tableRowData.put("COL5", list.get(i).getOfrDisc1()); // 할인액1
						tableRowData.put("COL6", list.get(i).getOfrStd2()); // 기준2
						tableRowData.put("COL7", list.get(i).getOfrDisc2()); // 할인액2
						tableRowData.put("COL8", list.get(i).getOfrStd3()); // 기준3
						tableRowData.put("COL9", list.get(i).getOfrDisc3()); // 할인액3
						tableRowData.put("COL10", list.get(i).getReqItemDesc()); // 세부사항
					}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
						// M+N
						tableRowData.put("COL6", list.get(i).getOfrM()); // M
						tableRowData.put("COL7", list.get(i).getOfrN()); // N
						tableRowData.put("COL8", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL9", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL10", "");
					}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
						// 연관
						tableRowData.put("COL6", list.get(i).getOfrStd()); // 행사기준
						tableRowData.put("COL7", list.get(i).getCalSalesPrc() + " / " + list.get(i).getCalSalesRate()); // 변경금액(원)/율(%)
						tableRowData.put("COL8", list.get(i).getReqSalesPrc()); // 정상판매가
						tableRowData.put("COL9", list.get(i).getSalesPrc()); // 행사판매가
						tableRowData.put("COL10", list.get(i).getReqItemDesc()); // 세부사항
					}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
						// 상품권
						tableRowData.put("COL6", NumberUtil.formatNumber( Integer.parseInt(list.get(i).getOfrStd()), "###,###") ); // 행사기준
						tableRowData.put("COL7", list.get(i).getGiftAmt()); // 사은금액
						tableRowData.put("COL8", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL9", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL10", "");
					}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
						// 원매가 인하
						tableRowData.put("COL6", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL7", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL8", "");
						tableRowData.put("COL9", "");
						tableRowData.put("COL10", "");
					}
				}else {
					// 사후 정산
					if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
						// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
						tableRowData.put("COL3", list.get(i).getCalSalesPrc() + " / " + list.get(i).getCalSalesRate()); // 변경금액
						tableRowData.put("COL4", list.get(i).getReqSalesPrc()); // 정상판매가
						tableRowData.put("COL5", list.get(i).getSalesPrc()); // 행사판매가
						tableRowData.put("COL6", list.get(i).getVenRate()); // 분담율
						tableRowData.put("COL7", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL8", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL9", "");
						tableRowData.put("COL10", "");
					}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
						// 번들
						tableRowData.put("COL3", list.get(i).getOfrStd1()); // 기준1
						tableRowData.put("COL4", list.get(i).getOfrDisc1()); // 할인액1
						tableRowData.put("COL5", list.get(i).getOfrStd2()); // 기준2
						tableRowData.put("COL6", list.get(i).getOfrDisc2()); // 할인액2
						tableRowData.put("COL7", list.get(i).getOfrStd3()); // 기준3
						tableRowData.put("COL8", list.get(i).getOfrDisc3()); // 할인액3
						tableRowData.put("COL9", list.get(i).getVenRate()); // 분담율
						tableRowData.put("COL10", list.get(i).getReqItemDesc()); // 세부사항
					}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
						// M+N
						tableRowData.put("COL3", list.get(i).getOfrM()); // M
						tableRowData.put("COL4", list.get(i).getOfrN()); // N
						tableRowData.put("COL5", list.get(i).getVenRate()); // 분담율
						tableRowData.put("COL6", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL7", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL8", "");
						tableRowData.put("COL9", "");
						tableRowData.put("COL10", "");
					}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
						// 연관
						tableRowData.put("COL3", list.get(i).getOfrStd()); // 행사기준
						tableRowData.put("COL4", list.get(i).getCalSalesPrc() + " / " + list.get(i).getCalSalesRate()); // 변겸금액
						tableRowData.put("COL5", list.get(i).getReqSalesPrc()); // 정상판매가
						tableRowData.put("COL6", list.get(i).getSalesPrc()); // 행사판매가
						tableRowData.put("COL7", list.get(i).getVenRate()); // 분담율
						tableRowData.put("COL8", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL9", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL10", "");
					}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
						// 상품권
						tableRowData.put("COL3", NumberUtil.formatNumber( Integer.parseInt(list.get(i).getOfrStd()), "###,###") ); // 행사기준
						tableRowData.put("COL4", list.get(i).getGiftAmt()); // 사은금액
						tableRowData.put("COL5", list.get(i).getVenRate()); // 분담율
						tableRowData.put("COL6", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL7", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL8", "");
						tableRowData.put("COL9", "");
						tableRowData.put("COL10", "");
					}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
						// 원매가 인하
						tableRowData.put("COL3", list.get(i).getCalPurPrc() + " / " + list.get(i).getCalComRate()); // 변경금액
						tableRowData.put("COL4", list.get(i).getPurPrc()); // 정상원가
						tableRowData.put("COL5", list.get(i).getReqPurPrc()); // 행사원가
						tableRowData.put("COL6", list.get(i).getReqItemDesc()); // 세부사항
						tableRowData.put("COL7", list.get(i).getZbigo()); // 비고
						tableRowData.put("COL8", "");
						tableRowData.put("COL9", "");
						tableRowData.put("COL10", "");
					}
				}
				tableRowDataList.add(tableRowData);
				body.append( EcsUtil.getMapTableData("IF_RECV_OFFICIAL_TABLE_ROW", prodEvntVo.getDcNmCd(), "1", tableRowDataList) );
			}
			
			// 공문서 일때만
			if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
					|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
				//공통 - 파트너사정보(발신)
				Map<String,Object> vendorInfoMap = new HashMap<String,Object>();
				vendorInfoMap.put("USER_ID", paramVo.getEcsUserId());		//파트너사작성자ID
				vendorInfoMap.put("CONT_CODE", prodEvntVo.getContCode());	//연계계약번호(EPC내부관리번호)		
				vendorInfoMap.put("SYS_CODE", "PC");						//시스템구분
				
				String sendCompXml = EcsUtil.getMapData("IF_RECV_OFFICIAL", vendorInfoMap);
				body.append(sendCompXml);
			}
			
			//ECS 전송
			Map<String,Object> ecsResultMap = ecsUtil.sendEcsDcDoc(ecsDocGbn, hXml, body.toString());
			
			//ECS 전송 실패 시, 오류코드 반환
			if(!MapUtils.getBooleanValue(ecsResultMap, "result")) {
				returnMap.put("errCode", MapUtils.getString(ecsResultMap, "errCode"));
				returnMap.put("msg", MapUtils.getString(ecsResultMap, "errMsg"));
				return returnMap;
			}
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			returnMap.put("url", MapUtils.getString(ecsResultMap, "url"));	//ECS 반환 URL
			returnMap.put("result", true);
			
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr_20250417(NEDMPRO0310VO paramVo) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		// 계약 생성 카운트
		int cnt = ("KR02".equals(prodEvntVo.getVkorg())) ? 1 : 2;
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		prodEvntVo.setContCode("");
		prodEvntVo.setContCode2("");
		paramVo.setContCode("");
		paramVo.setContCode2("");
		//int z = 0;
		//for(int z = 0; z < cnt; z++) {
		if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
			// 마트
			prodEvntVo.setEcsDepCd("510");
			prodEvntVo.setCompNum("2158513569");
			prodEvntVo.setCompName("롯데마트사업부");
			
			// 원매가 행사 공문
			if( "00".equals( prodEvntVo.getReqContyp() )) {
				prodEvntVo.setLinCd("007");
				prodEvntVo.setDcCd("12653");
				prodEvntVo.setDcNmCd("15104");
			}else {
				prodEvntVo.setLinCd("006");
				//  판촉요청 합의서 (50:50)
				if( "01".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15099");
				}else if( "02".equals( prodEvntVo.getReqContyp() )) {
					// 판촉요청 합의서 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15102");
				}else if( "03".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (100:0)
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15100");
				}else if( "04".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15103");
				}else if( "05".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 신청서_공개모집
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15101");
				}
			}
			prodEvntVo.setContCode(ecsContCode);
			paramVo.setContCode(ecsContCode);
		}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
			// 슈퍼, 씨에스 계약 2번 생성
			// 슈퍼
			if( "ecsIntgrMd".equals( paramVo.getTaskGbn() )) {
				prodEvntVo.setEcsDepCd("511");
				prodEvntVo.setCompNum("2068511698");
				prodEvntVo.setCompName("롯데슈퍼사업부");
				
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12657");
					prodEvntVo.setDcNmCd("15108");
				}else {
					prodEvntVo.setLinCd("003");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15110");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15113");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15111");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15114");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15112");
					}
				}
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode(ecsContCode);
			}else {
				// 씨에스유통
				prodEvntVo.setEcsDepCd("512");
				prodEvntVo.setCompNum("2048136642");
				prodEvntVo.setCompName("씨에스유통");
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12658");
					prodEvntVo.setDcNmCd("15109");
				}else {
					prodEvntVo.setLinCd("004");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15118");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15121");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15119");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15122");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15120");
					}
				}
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode2(ecsContCode);
			}
		}
		
		String xmlData =
				"<root>\n"
						+ "<HEAD_INFO>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
						+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
						+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
						+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
						+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
						+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
						+ "<DC_END><![CDATA[]]></DC_END>\n"
						+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
						+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
						+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
						+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
						+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
						+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
						+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
						+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
						+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
						+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
						+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "</HEAD_INFO>\n"
						
					+ "<COMP_LIST>\n"
					+ "<COMP_NUM><![CDATA["+ prodEvntVo.getCompNum() +"]]></COMP_NUM>\n"
					+ "<COMP_NAME><![CDATA["+ prodEvntVo.getCompName() +"]]></COMP_NAME>\n"
					+ "<TEAM_CD><![CDATA[1234]]></TEAM_CD>\n"
					+ "<EMP_SABUN><![CDATA[1234]]></EMP_SABUN>\n"
					+ "</COMP_LIST>\n";
		
		if( "00".equals( prodEvntVo.getReqContyp() )) {
			// 원매가 행사 공문
			xmlData = xmlData
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[뭘까용???]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_3]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_4]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() + "~" +prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			
		}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
			// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)						
			xmlData = xmlData
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			
			// 증정 사은품이면..
			if("4002".equals(prodEvntVo.getReqType())) {
				xmlData = xmlData
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_19]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA[1]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_20]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n"
							
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_21]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getVkorgRate() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n"
							
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_22]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			}else {
				xmlData = xmlData
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_15]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA[1]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_16]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n"
							
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_17]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getVkorgRate() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n"
							
							+	"<IF_RECV_OFFICIAL_ITEMS>\n"
							+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
							+	"<ITEM_ID><![CDATA[DC_IF_18]]></ITEM_ID>\n"
							+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
							+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			}
		}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
			xmlData = xmlData
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_3]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA[뭘까용]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n"
						
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_4]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA[뭘까용2]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			
		}else if( "05".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 신청서 공개모집
			xmlData = xmlData
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
						+	"<IF_RECV_OFFICIAL_ITEMS>\n"
						+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
						+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
						+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
						+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		}
		xmlData = xmlData
				+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
				+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
				+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
				+ "<ROW_NUM><![CDATA[0]]></ROW_NUM>\n"
				+ "<COL1><![CDATA[판매바코드]]></COL1>\n"
				+ "<COL2><![CDATA[상품명]]></COL2>\n";
		
		// 원가 인하
		if( "01".equals( prodEvntVo.getCostType() ) ) {
			
			// 번들이 아니면 변경금액, 정상원가 데이터 넣어주기
			if( !"1002".equals( prodEvntVo.getReqType() ) ) {
				xmlData = xmlData
						+	"<COL3><![CDATA[변경금액(원)/율(%)]]></COL3>\n"
						+	"<COL4><![CDATA[정상원가(원)]]></COL4>\n"
						+	"<COL5><![CDATA[행사원가(원)]]></COL5>\n";
			}else {
				xmlData = xmlData
						+	"<COL3><![CDATA[행사원가(원)]]></COL3>\n";
			}
			
			if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
				// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
				xmlData = xmlData
						+	"<COL6><![CDATA[변경금액(원)/율(%)]]></COL6>\n"
						+	"<COL7><![CDATA[정상판매가(원)]]></COL7>\n"
						+	"<COL8><![CDATA[행사판매가(원)]]></COL8>\n"
						+	"<COL9><![CDATA[세부사항]]></COL9>\n"
						+	"<COL10><![CDATA[비고]]></COL10>\n";	
			}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
				// 번들
				xmlData = xmlData
						+	"<COL4><![CDATA[기준1(해당상품N개구매시)]]></COL4>\n"
						+	"<COL6><![CDATA[기준2]]></COL6>\n"
						+	"<COL8><![CDATA[기준3]]></COL8>\n"
						+	"<COL10><![CDATA[세부사항]]></COL10>\n";
				if( "01".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정액판매가 서식 4번
					xmlData = xmlData
							+	"<COL5><![CDATA[행사판매가1(원)]]></COL5>\n"
							+	"<COL7><![CDATA[행사판매가2(원)]]></COL7>\n"
							+	"<COL9><![CDATA[행사판매가3(원)]]></COL9>\n";
				}else if( "03".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정액할인 서식 5번
					xmlData = xmlData
							+	"<COL5><![CDATA[할인율1(%)]]></COL5>\n"
							+	"<COL7><![CDATA[할인율2(%)]]></COL7>\n"
							+	"<COL9><![CDATA[할인율3(%)]]></COL9>\n";
				}else if( "04".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정율할인 서식 6번
					xmlData = xmlData
							+	"<COL5><![CDATA[할인액1(원)]]></COL5>\n"
							+	"<COL7><![CDATA[할인액2(원)]]></COL7>\n"
							+	"<COL9><![CDATA[할인액3(원)]]></COL9>\n";
				}
			}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
				// M+N
				xmlData = xmlData
						+	"<COL6><![CDATA[M(개)]]></COL6>\n"
						+	"<COL7><![CDATA[N(개)]]></COL7>\n"
						+	"<COL8><![CDATA[세부사항]]></COL8>\n"
						+	"<COL9><![CDATA[비고]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
				// 연관
				xmlData = xmlData
						+	"<COL6><![CDATA[행사기준]]></COL6>\n"
						+	"<COL7><![CDATA[변경금액(원)/율(%)]]></COL7>\n"
						+	"<COL8><![CDATA[정상판매가(원)]]></COL8>\n"
						+	"<COL9><![CDATA[행사판매가(원)]]></COL9>\n"
						+	"<COL10><![CDATA[세부사항]]></COL10>\n";
			}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
				// 상품권
				xmlData = xmlData
						+	"<COL6><![CDATA[결제금액기준(동일입력)]]></COL6>\n"
						+	"<COL7><![CDATA[증정/사은금액]]></COL7>\n"
						+	"<COL8><![CDATA[세부사항]]></COL8>\n"
						+	"<COL9><![CDATA[비고]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
				// 원매가 인하
				xmlData = xmlData
						+	"<COL6><![CDATA[세부사항]]></COL6>\n"
						+	"<COL7><![CDATA[비고]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}
		}else {
			// 사후 정산
			if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
				// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
				xmlData = xmlData
						+	"<COL3><![CDATA[변경금액(원)/율(%)]]></COL3>\n"
						+	"<COL4><![CDATA[정상판매가(원)]]></COL4>\n"
						+	"<COL5><![CDATA[행사판매가(원)]]></COL5>\n"
						+	"<COL6><![CDATA[분담율]]></COL6>\n"
						+	"<COL7><![CDATA[세부사항]]></COL7>\n"
						+	"<COL8><![CDATA[비고]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
				// 번들
				xmlData = xmlData
						+	"<COL3><![CDATA[기준1(해당상품N개구매시)]]></COL3>\n"
						+	"<COL5><![CDATA[기준2]]></COL5>\n"
						+	"<COL7><![CDATA[기준3]]></COL7>\n"
						+	"<COL9><![CDATA[상품별 분담율]]></COL9>\n"
						+	"<COL10><![CDATA[세부사항]]></COL10>\n";
				if( "01".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정액판매가 서식 4번
					xmlData = xmlData
							+	"<COL4><![CDATA[행사판매가1(원)]]></COL4>\n"
							+	"<COL6><![CDATA[행사판매가2(원)]]></COL6>\n"
							+	"<COL8><![CDATA[행사판매가3(원)]]></COL8>\n";
				}else if( "03".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정액할인 서식 5번
					xmlData = xmlData
							+	"<COL4><![CDATA[할인율1(%)]]></COL4>\n"
							+	"<COL6><![CDATA[할인율2(%)]]></COL6>\n"
							+	"<COL8><![CDATA[할인율3(%)]]></COL8>\n";
				}else if( "04".equals( prodEvntVo.getReqDisc() )){
					// 번들 + 정율할인 서식 6번
					xmlData = xmlData
							+	"<COL4><![CDATA[할인액1(원)]]></COL4>\n"
							+	"<COL6><![CDATA[할인액2(원)]]></COL6>\n"
							+	"<COL8><![CDATA[할인액3(원)]]></COL8>\n";
				}
			}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
				// M+N
				xmlData = xmlData
						+	"<COL3><![CDATA[M(개)]]></COL3>\n"
						+	"<COL4><![CDATA[N(개)]]></COL4>\n"
						+	"<COL5><![CDATA[분담율]]></COL5>\n"
						+	"<COL6><![CDATA[세부사항]]></COL6>\n"
						+	"<COL7><![CDATA[비고]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
				// 연관
				xmlData = xmlData
						+	"<COL3><![CDATA[행사기준]]></COL3>\n"
						+	"<COL4><![CDATA[변경금액(원)/율(%)]]></COL4>\n"
						+	"<COL5><![CDATA[정상판매가(원)]]></COL5>\n"
						+	"<COL6><![CDATA[행사판매가(원)]]></COL6>\n"
						+	"<COL7><![CDATA[분담율]]></COL7>\n"
						+	"<COL8><![CDATA[세부사항]]></COL8>\n"
						+	"<COL9><![CDATA[비고]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
				// 상품권
				xmlData = xmlData
						+	"<COL3><![CDATA[결제금액기준(동일입력)]]></COL3>\n"
						+	"<COL4><![CDATA[증정/사은금액]]></COL4>\n"
						+	"<COL5><![CDATA[분담율]]></COL5>\n"
						+	"<COL6><![CDATA[세부사항]]></COL6>\n"
						+	"<COL7><![CDATA[비고]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
				// 원매가 인하
				xmlData = xmlData
						+	"<COL3><![CDATA[변경금액(원)/율(%)]]></COL3>\n"
						+	"<COL4><![CDATA[정상원가(원)]]></COL4>\n"
						+	"<COL5><![CDATA[행사원가(원)]]></COL5>\n"
						+	"<COL6><![CDATA[세부사항]]></COL6>\n"
						+	"<COL7><![CDATA[비고]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}
		}
		
		xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		
		for(int i = 0; i < list.size(); i++) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
					+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
					+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
					+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
					+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
					+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
			// 원가 인하
			if( "01".equals( prodEvntVo.getCostType() ) ) {
				
				// 번들이 아니면 변경금액, 정상원가 데이터 넣어주기
				if( !"1002".equals( prodEvntVo.getReqType() ) ) {
					xmlData = xmlData
							+	"<COL3><![CDATA[" + list.get(i).getPurPrc() + ")]]></COL3>\n"
							+	"<COL4><![CDATA[" + list.get(i).getCalPurPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA[" + list.get(i).getCalComRate() + "]]></COL5>\n";
				}else {
					xmlData = xmlData
							+	"<COL3><![CDATA[" + list.get(i).getCalComRate() + "]]></COL3>\n";
				}
				
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";	
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							+	"<COL4><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrM() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrN() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL10>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
			}else {
				// 사후 정산
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getVenRate() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getZbigo() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getVenRate() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getZbigo() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrM() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrN() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getVenRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrStd() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getVenRate() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrStd() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getVenRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
			}
			xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		}
		
		// 공문서 일때만
		if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
				|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL>\n"
					+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
					+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
					+ "<USER_ID><![CDATA["+ paramVo.getEcsUserId() +"]]></USER_ID>\n"
					+ "</IF_RECV_OFFICIAL>\n";
		}
		xmlData = xmlData
				+ "</root>\n";
		
		try {
			
			// 노인코딩
			String postData = "data= " + xmlData;
			postData = postData.replaceAll("%", "%25");
			logger.info(" postData === " +  postData );
			
			URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			// 데이터 전송
			OutputStream os = connection.getOutputStream();
			
			byte[] input = postData.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			// 서버 응답 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			logger.info("response ::::::" + response);
			
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			if(!"".equals( response.toString() )) {
				String[] resultCode = response.toString().split("-");
				
				if(!"00".equals( resultCode[0] )) {
					returnMap.put("msg", resultCode[1]);
					returnMap.put("result", false);
				}else {
					returnMap.put("url", resultCode[1]);
					returnMap.put("result", true);
				}
			}else {
				returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				returnMap.put("result", false);
			}
			connection.disconnect();
			os.close();
		}catch (Exception e) {
			logger.info("e::::::::" + e.getMessage() );
			returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
			returnMap.put("result", false);
		}		
		//}// end for
		
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr_20250415(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		// 계약 생성 카운트
		int cnt = ("KR02".equals(prodEvntVo.getVkorg())) ? 1 : 2;
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		prodEvntVo.setContCode("");
		prodEvntVo.setContCode2("");
		paramVo.setContCode("");
		paramVo.setContCode2("");
		//int z = 0;
		//for(int z = 0; z < cnt; z++) {
		if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
			// 마트
			prodEvntVo.setEcsDepCd("510");
			prodEvntVo.setCompNum("2158513569");
			prodEvntVo.setCompName("롯데마트사업부");
			
			// 원매가 행사 공문
			if( "00".equals( prodEvntVo.getReqContyp() )) {
				prodEvntVo.setLinCd("007");
				prodEvntVo.setDcCd("12653");
				prodEvntVo.setDcNmCd("15104");
			}else {
				prodEvntVo.setLinCd("006");
				//  판촉요청 합의서 (50:50)
				if( "01".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15099");
				}else if( "02".equals( prodEvntVo.getReqContyp() )) {
					// 판촉요청 합의서 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15102");
				}else if( "03".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (100:0)
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15100");
				}else if( "04".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15103");
				}else if( "05".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 신청서_공개모집
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15101");
				}
			}
			prodEvntVo.setContCode(ecsContCode);
			paramVo.setContCode(ecsContCode);
		}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
			// 슈퍼, 씨에스 계약 2번 생성
			// 슈퍼
			if( "ecsIntgrMd".equals( paramVo.getTaskGbn() )) {
				prodEvntVo.setEcsDepCd("511");
				prodEvntVo.setCompNum("2068511698");
				prodEvntVo.setCompName("롯데슈퍼사업부");
				
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12657");
					prodEvntVo.setDcNmCd("15108");
				}else {
					prodEvntVo.setLinCd("003");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15110");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15113");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15111");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15114");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15112");
					}
				}
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode(ecsContCode);
			}else {
				// 씨에스유통
				prodEvntVo.setEcsDepCd("512");
				prodEvntVo.setCompNum("2048136642");
				prodEvntVo.setCompName("씨에스유통");
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12658");
					prodEvntVo.setDcNmCd("15109");
				}else {
					prodEvntVo.setLinCd("004");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15118");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15121");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15119");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15122");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15120");
					}
				}
				prodEvntVo.setContCode(ecsContCode);
				paramVo.setContCode2(ecsContCode);
			}
		}
		
		String xmlData =
				"<root>\n"
						+ "<HEAD_INFO>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
						+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
						+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
						+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
						+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
						+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
						+ "<DC_END><![CDATA[]]></DC_END>\n"
						+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
						+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
						+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
						+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
						+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
						+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
						+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
						+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
						+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
						+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
						+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "</HEAD_INFO>\n"
						
					+ "<COMP_LIST>\n"
					+ "<COMP_NUM><![CDATA["+ prodEvntVo.getCompNum() +"]]></COMP_NUM>\n"
					+ "<COMP_NAME><![CDATA["+ prodEvntVo.getCompName() +"]]></COMP_NAME>\n"
					+ "<TEAM_CD><![CDATA[1234]]></TEAM_CD>\n"
					+ "<EMP_SABUN><![CDATA[1234]]></EMP_SABUN>\n"
					+ "</COMP_LIST>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[REQ_OFRCD]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrcd() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[LIFNR]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getLifnr() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[TEVT_NM]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		if( "00".equals( prodEvntVo.getReqContyp() )) {
			// 원매가 행사 공문
		}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
			// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)
			
		}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
			
		}else if( "05".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 신청서 공개모집
			
		}
		xmlData = xmlData
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_3]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[뭘까용?]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_4]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_5]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_6]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		for(int i = 0; i < list.size(); i++) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
					+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
					+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
					+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
					+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
					+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
			// 원가 인하
			if( "01".equals( prodEvntVo.getCostType() ) ) {
				xmlData = xmlData
						+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n";
				
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";	
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL6><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL6><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL6>\n"
							//+	"<COL8><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL3>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL7>\n"
							//+	"<COL10><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL3>\n"
							+	"<COL8><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrM() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrN() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL10>\n"
							+	"<COL11><![CDATA["+ list.get(i).getZbigo() +"]]></COL11>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL6><![CDATA[결제금액기준(동일입력) 애 없음]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}else {
				// 사후 정산
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL3><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL3><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL3>\n"
							//+	"<COL4><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL4>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL4>\n"
							//+	"<COL5><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL5>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrM() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrN() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrStd() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getZbigo() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL3><![CDATA[결제금액기준(동일입력) 애 없음]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}
			xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		}
		
		// 공문서 일때만
		if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
				|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL>\n"
					+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
					+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
					+ "<USER_ID><![CDATA["+ paramVo.getEcsUserId() +"]]></USER_ID>\n"
					+ "</IF_RECV_OFFICIAL>\n";
		}
		xmlData = xmlData
				+ "</root>\n";
		
		try {
			
			// 노인코딩
			String postData = "data= " + xmlData;
			postData = postData.replaceAll("%", "%25");
			logger.info(" postData === " +  postData );
			
			URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			// 데이터 전송
			OutputStream os = connection.getOutputStream();
			
			byte[] input = postData.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			// 서버 응답 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			logger.info("response ::::::" + response);
			
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			if(!"".equals( response.toString() )) {
				String[] resultCode = response.toString().split("-");
				
				if(!"00".equals( resultCode[0] )) {
					returnMap.put("msg", resultCode[1]);
					returnMap.put("result", false);
				}else {
					/*if(z == 0) {
							returnMap.put("url1", resultCode[1]);
							returnMap.put("result", true);
						}else {
							returnMap.put("url2", resultCode[1]);
							returnMap.put("result", true);
						}*/
					returnMap.put("url", resultCode[1]);
					returnMap.put("result", true);
				}
			}else {
				returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				returnMap.put("result", false);
			}
			
			connection.disconnect();
			os.close();
			
		}catch (Exception e) {
			logger.info("e::::::::" + e.getMessage() );
			returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
			returnMap.put("result", false);
		}		
		//}// end for
		
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr_20250410(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		
		// 계약 생성 카운트
		int cnt = ("KR02".equals(prodEvntVo.getVkorg())) ? 1 : 2;
		//int z = 0;
		//for(int z = 0; z < cnt; z++) {
		if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
			// 마트
			prodEvntVo.setEcsDepCd("510");
			prodEvntVo.setCompNum("2158513569");
			prodEvntVo.setCompName("롯데마트사업부");
			
			// 원매가 행사 공문
			if( "00".equals( prodEvntVo.getReqContyp() )) {
				prodEvntVo.setLinCd("007");
				prodEvntVo.setDcCd("12653");
				prodEvntVo.setDcNmCd("15104");
			}else {
				prodEvntVo.setLinCd("006");
				//  판촉요청 합의서 (50:50)
				if( "01".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15099");
				}else if( "02".equals( prodEvntVo.getReqContyp() )) {
					// 판촉요청 합의서 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15102");
				}else if( "03".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (100:0)
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15100");
				}else if( "04".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행 요청 공문 (자율분담)
					prodEvntVo.setDcCd("12652");
					prodEvntVo.setDcNmCd("15103");
				}else if( "05".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 신청서_공개모집
					prodEvntVo.setDcCd("12651");
					prodEvntVo.setDcNmCd("15101");
				}
			}
		}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
			// 슈퍼, 씨에스 계약 2번 생성
			// 슈퍼
			if( "ecsIntgrMd".equals( paramVo.getTaskGbn() )) {
				prodEvntVo.setEcsDepCd("511");
				prodEvntVo.setCompNum("2068511698");
				prodEvntVo.setCompName("롯데슈퍼사업부");
				
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12657");
					prodEvntVo.setDcNmCd("15108");
				}else {
					prodEvntVo.setLinCd("003");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15110");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15113");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15111");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12660");
						prodEvntVo.setDcNmCd("15114");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12659");
						prodEvntVo.setDcNmCd("15112");
					}
				}
			}else {
				// 씨에스유통
				prodEvntVo.setEcsDepCd("512");
				prodEvntVo.setCompNum("2048136642");
				prodEvntVo.setCompName("씨에스유통");
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("001");
					prodEvntVo.setDcCd("12658");
					prodEvntVo.setDcNmCd("15109");
				}else {
					prodEvntVo.setLinCd("004");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15118");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15121");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15119");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12666");
						prodEvntVo.setDcNmCd("15122");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12665");
						prodEvntVo.setDcNmCd("15120");
					}
				}
			}
		}
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		prodEvntVo.setContCode(ecsContCode);
		paramVo.setContCode(ecsContCode);
		
		String xmlData =
				"<root>\n"
						+ "<HEAD_INFO>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
						+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
						+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
						+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
						+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
						+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
						+ "<DC_END><![CDATA[]]></DC_END>\n"
						+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
						+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
						+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
						+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
						+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
						+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
						+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
						+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
						+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
						+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
						+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "</HEAD_INFO>\n"
						
					+ "<COMP_LIST>\n"
					+ "<COMP_NUM><![CDATA["+ prodEvntVo.getCompNum() +"]]></COMP_NUM>\n"
					+ "<COMP_NAME><![CDATA["+ prodEvntVo.getCompName() +"]]></COMP_NAME>\n"
					+ "<TEAM_CD><![CDATA[]]></TEAM_CD>\n"
					+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
					+ "</COMP_LIST>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[REQ_OFRCD]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrcd() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[LIFNR]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getLifnr() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[TEVT_NM]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		if( "00".equals( prodEvntVo.getReqContyp() )) {
			// 원매가 행사 공문
		}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
			// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)
			
		}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
			
		}else if( "05".equals( prodEvntVo.getReqContyp() )) {
			// 판촉행사 신청서 공개모집
			
		}
		xmlData = xmlData
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_3]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[뭘까용?]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_4]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_5]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_6]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		for(int i = 0; i < list.size(); i++) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
					+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
					+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
					+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
					+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
					+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
			
			// 원매가 행사 공문
			if( "00".equals( prodEvntVo.getReqContyp() )) {
				xmlData = xmlData
						+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ prodEvntVo.getZbigo() +"]]></COL5>\n"
						+	"<COL6><![CDATA[]]></COL6>\n"
						+	"<COL7><![CDATA[]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
				// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)
				xmlData = xmlData
						//+	"<COL3><![CDATA["+ prodEvntVo.getPrsdt() +"]]></COL3>\n"
						//+	"<COL4><![CDATA["+ prodEvntVo.getPredt() +"]]></COL4>\n"
						+	"<COL3><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ prodEvntVo.getItmeApprProdCnt() +"]]></COL5>\n"
						+	"<COL6><![CDATA["+ prodEvntVo.getCostTypeTxt() +"]]></COL6>\n"
						+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
				// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
				xmlData = xmlData
						+	"<COL3><![CDATA["+ prodEvntVo.getReqTypeTxt() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ prodEvntVo.getZbigo() +"]]></COL4>\n"
						+	"<COL5><![CDATA[]]></COL5>\n"
						+	"<COL6><![CDATA[]]></COL6>\n"
						+	"<COL7><![CDATA[]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}else if( "05".equals( prodEvntVo.getReqContyp() )) {
				// 판촉행사 신청서 공개모집
				xmlData = xmlData
						+	"<COL3><![CDATA["+ prodEvntVo.getReqTypeTxt() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ prodEvntVo.getReqOfrDesc() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ prodEvntVo.getCostTypeTxt() +"]]></COL5>\n"
						+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
						+	"<COL7><![CDATA[]]></COL7>\n"
						+	"<COL8><![CDATA[]]></COL8>\n"
						+	"<COL9><![CDATA[]]></COL9>\n"
						+	"<COL10><![CDATA[]]></COL10>\n";
			}
			xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		}
		
		// 공문서 일때만
		if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
				|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL>\n"
					+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
					+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
					+ "<USER_ID><![CDATA["+ paramVo.getEcsUserId() +"]]></USER_ID>\n"
					+ "</IF_RECV_OFFICIAL>\n";
		}
		xmlData = xmlData
				+ "</root>\n";
		
		try {
			
			// 노인코딩
			String postData = "data= " + xmlData;
			logger.info(" postData === " +  postData );
			
			URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			// 데이터 전송
			OutputStream os = connection.getOutputStream();
			
			byte[] input = postData.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			// 서버 응답 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			logger.info("response ::::::" + response);
			
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			if(!"".equals( response.toString() )) {
				String[] resultCode = response.toString().split("-");
				
				if(!"00".equals( resultCode[0] )) {
					returnMap.put("msg", resultCode[1]);
					returnMap.put("result", false);
				}else {
					/*if(z == 0) {
							returnMap.put("url1", resultCode[1]);
							returnMap.put("result", true);
						}else {
							returnMap.put("url2", resultCode[1]);
							returnMap.put("result", true);
						}*/
					returnMap.put("url", resultCode[1]);
					returnMap.put("result", true);
				}
			}else {
				returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				returnMap.put("result", false);
			}
			
			connection.disconnect();
			os.close();
			
		}catch (Exception e) {
			logger.info("e::::::::" + e.getMessage() );
			returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
			returnMap.put("result", false);
		}		
		//}// end for
		
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr33333(NEDMPRO0310VO paramVo) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setDoOutput(true);
		// 데이터 전송
		OutputStream os = connection.getOutputStream();
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		
		// 계약 생성 카운트
		int cnt = ("KR02".equals(prodEvntVo.getVkorg())) ? 1 : 2;
		//int z = 0;
		for(int z = 0; z < cnt; z++) {
			if( "KR02".equals( prodEvntVo.getVkorg() ) ) {
				// 마트
				prodEvntVo.setEcsDepCd("510");
				prodEvntVo.setCompNum("2158513569");
				prodEvntVo.setCompName("롯데마트사업부");
				
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					prodEvntVo.setLinCd("007");
					prodEvntVo.setDcCd("12653");
					prodEvntVo.setDcNmCd("15104");
				}else {
					prodEvntVo.setLinCd("006");
					//  판촉요청 합의서 (50:50)
					if( "01".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setDcCd("12651");
						prodEvntVo.setDcNmCd("15099");
					}else if( "02".equals( prodEvntVo.getReqContyp() )) {
						// 판촉요청 합의서 (자율분담)
						prodEvntVo.setDcCd("12652");
						prodEvntVo.setDcNmCd("15102");
					}else if( "03".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (100:0)
						prodEvntVo.setDcCd("12651");
						prodEvntVo.setDcNmCd("15100");
					}else if( "04".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 시행 요청 공문 (자율분담)
						prodEvntVo.setDcCd("12652");
						prodEvntVo.setDcNmCd("15103");
					}else if( "05".equals( prodEvntVo.getReqContyp() )) {
						// 판촉행사 신청서_공개모집
						prodEvntVo.setDcCd("12651");
						prodEvntVo.setDcNmCd("15101");
					}
				}
			}else if( "KR04".equals( prodEvntVo.getVkorg() ) ) {
				// 슈퍼, 씨에스 계약 2번 생성
				// 슈퍼
				if(z == 0) {
					prodEvntVo.setEcsDepCd("511");
					prodEvntVo.setCompNum("2068511698");
					prodEvntVo.setCompName("롯데슈퍼사업부");
					
					// 원매가 행사 공문
					if( "00".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setLinCd("001");
						prodEvntVo.setDcCd("12657");
						prodEvntVo.setDcNmCd("15108");
					}else {
						prodEvntVo.setLinCd("003");
						//  판촉요청 합의서 (50:50)
						if( "01".equals( prodEvntVo.getReqContyp() )) {
							prodEvntVo.setDcCd("12659");
							prodEvntVo.setDcNmCd("15110");
						}else if( "02".equals( prodEvntVo.getReqContyp() )) {
							// 판촉요청 합의서 (자율분담)
							prodEvntVo.setDcCd("12660");
							prodEvntVo.setDcNmCd("15113");
						}else if( "03".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 시행 요청 공문 (100:0)
							prodEvntVo.setDcCd("12659");
							prodEvntVo.setDcNmCd("15111");
						}else if( "04".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 시행 요청 공문 (자율분담)
							prodEvntVo.setDcCd("12660");
							prodEvntVo.setDcNmCd("15114");
						}else if( "05".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 신청서_공개모집
							prodEvntVo.setDcCd("12659");
							prodEvntVo.setDcNmCd("15112");
						}
					}
				}else {
					// 씨에스유통
					prodEvntVo.setEcsDepCd("512");
					prodEvntVo.setCompNum("2048136642");
					prodEvntVo.setCompName("씨에스유통");
					// 원매가 행사 공문
					if( "00".equals( prodEvntVo.getReqContyp() )) {
						prodEvntVo.setLinCd("001");
						prodEvntVo.setDcCd("12658");
						prodEvntVo.setDcNmCd("15109");
					}else {
						prodEvntVo.setLinCd("004");
						//  판촉요청 합의서 (50:50)
						if( "01".equals( prodEvntVo.getReqContyp() )) {
							prodEvntVo.setDcCd("12665");
							prodEvntVo.setDcNmCd("15118");
						}else if( "02".equals( prodEvntVo.getReqContyp() )) {
							// 판촉요청 합의서 (자율분담)
							prodEvntVo.setDcCd("12666");
							prodEvntVo.setDcNmCd("15121");
						}else if( "03".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 시행 요청 공문 (100:0)
							prodEvntVo.setDcCd("12665");
							prodEvntVo.setDcNmCd("15119");
						}else if( "04".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 시행 요청 공문 (자율분담)
							prodEvntVo.setDcCd("12666");
							prodEvntVo.setDcNmCd("15122");
						}else if( "05".equals( prodEvntVo.getReqContyp() )) {
							// 판촉행사 신청서_공개모집
							prodEvntVo.setDcCd("12665");
							prodEvntVo.setDcNmCd("15120");
						}
					}
				}
			}
			
			String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
			prodEvntVo.setContCode(ecsContCode);
			paramVo.setContCode(ecsContCode);
			
			String xmlData =
					"<root>\n"
							+ "<HEAD_INFO>\n"
							+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
							+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
							+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
							+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
							+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
							+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
							+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
							+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
							+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
							+ "<DC_END><![CDATA[]]></DC_END>\n"
							+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
							+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
							+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
							+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
							+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
							+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
							+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
							+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
							+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
							+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
							+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
							+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
							+ "</HEAD_INFO>\n"
							
					+ "<COMP_LIST>\n"
					+ "<COMP_NUM><![CDATA["+ prodEvntVo.getCompNum() +"]]></COMP_NUM>\n"
					+ "<COMP_NAME><![CDATA["+ prodEvntVo.getCompName() +"]]></COMP_NAME>\n"
					+ "<TEAM_CD><![CDATA[]]></TEAM_CD>\n"
					+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
					+ "</COMP_LIST>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[REQ_OFRCD]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrcd() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[LIFNR]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getLifnr() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[TEVT_NM]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[E_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_START_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[B_END_DY]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_1]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_2]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() + "~" +prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_3]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[뭘까용?]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_4]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getOfrCost() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_5]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n"
					
					+	"<IF_RECV_OFFICIAL_ITEMS>\n"
					+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
					+	"<ITEM_ID><![CDATA[DC_IF_6]]></ITEM_ID>\n"
					+	"<ITEM_DATA><![CDATA[" + prodEvntVo.getHdVenRate() + "]]></ITEM_DATA>\n"
					+	"</IF_RECV_OFFICIAL_ITEMS>\n";
			
			for(int i = 0; i < list.size(); i++) {
				xmlData = xmlData
						+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
						+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
						+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
						+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
				
				// 원매가 행사 공문
				if( "00".equals( prodEvntVo.getReqContyp() )) {
					xmlData = xmlData
							+	"<COL3><![CDATA["+ prodEvntVo.getPrsdt() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ prodEvntVo.getPredt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getPurPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ prodEvntVo.getOfsdt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ prodEvntVo.getOfedt() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "01".equals( prodEvntVo.getReqContyp() ) || "02".equals( prodEvntVo.getReqContyp() )) {
					// 판촉요청 합의서(50:50), 판촉요청 합의서(자율분담)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ prodEvntVo.getPrsdt() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ prodEvntVo.getPredt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ prodEvntVo.getItmeApprProdCnt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ prodEvntVo.getCostTypeTxt() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "03".equals( prodEvntVo.getReqContyp() ) || "04".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 시행공문(100:0), 판촉행사 시행공문(자율분담)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ prodEvntVo.getReqTypeTxt() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ prodEvntVo.getZbigo() +"]]></COL4>\n"
							+	"<COL5><![CDATA[]]></COL5>\n"
							+	"<COL6><![CDATA[]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "05".equals( prodEvntVo.getReqContyp() )) {
					// 판촉행사 신청서 공개모집
					xmlData = xmlData
							+	"<COL3><![CDATA["+ prodEvntVo.getReqTypeTxt() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ prodEvntVo.getReqOfrDesc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ prodEvntVo.getCostTypeTxt() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
			}
			
			// 공문서 일때만
			if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
					|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
				xmlData = xmlData
						+ "<IF_RECV_OFFICIAL>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<USER_ID><![CDATA["+ paramVo.getEcsUserId() +"]]></USER_ID>\n"
						+ "</IF_RECV_OFFICIAL>\n";
			}
			
			xmlData = xmlData
					+ "</root>\n";
			
			try {
				
				// 노인코딩
				String postData = "data= " + xmlData;
				logger.info(" postData === " +  postData );
				
				
				byte[] input = postData.getBytes("UTF-8");
				os.write(input, 0, input.length);
				os.flush();
				
				int responseCode = connection.getResponseCode();
				logger.info(" Response Code ::::: " + responseCode );
				
				// 서버 응답 읽기
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				StringBuilder response = new StringBuilder();
				String responseLine;
				while( (responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				logger.info("response ::::::" + response);
				
				// CONT CODE Update!!!
				paramVo.setTaskGbn("contCode");
				paramVo.setWorkId(workId);
				nedmpro0310Dao.updateProEventApp(paramVo);
				
				if(!"".equals( response.toString() )) {
					String[] resultCode = response.toString().split("-");
					
					if(!"00".equals( resultCode[0] )) {
						returnMap.put("msg", resultCode[1]);
						returnMap.put("result", false);
					}else {
						if(z == 0) {
							returnMap.put("url1", resultCode[1]);
							returnMap.put("result", true);
						}else {
							returnMap.put("url2", resultCode[1]);
							returnMap.put("result", true);
						}
					}
				}else {
					returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
					returnMap.put("result", false);
				}
				
			}catch (Exception e) {
				logger.info("e::::::::" + e.getMessage() );
				returnMap.put("result", false);
			}		
		}// end for
		
		connection.disconnect();
		os.close();
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr2(NEDMPRO0310VO paramVo) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setDoOutput(true);
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		
		// 공문
		prodEvntVo.setEcsDepCd("507");
		prodEvntVo.setLinCd("001");
		prodEvntVo.setDcCd("11470");
		prodEvntVo.setDcNmCd("12457");
		
		//prodEvntVo.setEcsDepCd("96");
		//prodEvntVo.setLinCd("007");
		//prodEvntVo.setDcCd("12503");
		//prodEvntVo.setDcNmCd("14526");
		
		prodEvntVo.setContCode(ecsContCode);
		paramVo.setContCode(ecsContCode);
		
		String xmlData =
				"<root>\n"
						+ "<HEAD_INFO>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
						+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
						+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
						+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
						+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
						+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
						+ "<DC_END><![CDATA[]]></DC_END>\n"
						+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
						+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
						+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
						+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
						+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
						+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
						+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
						+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
						+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
						+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
						+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "</HEAD_INFO>\n"
						
				+ "<COMP_LIST>\n"
				+ "<COMP_NUM><![CDATA[2068511698]]></COMP_NUM>\n"
				+ "<COMP_NAME><![CDATA[롯데슈퍼사업부]]></COMP_NAME>\n"
				+ "<TEAM_CD><![CDATA[]]></TEAM_CD>\n"
				+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
				+ "</COMP_LIST>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFRCD]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrcd() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[LIFNR]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getLifnr() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[VKORG]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getVkorg() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFR_TXT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_PUR_TXT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqPurTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[OFSDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[OFEDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[PRSDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[PREDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFR_DESC]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrDesc() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		for(int i = 0; i < list.size(); i++) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
					+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
					+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
					+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
					+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
					+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
			
			// 원가 인하
			if( "01".equals( prodEvntVo.getCostType() ) ) {
				xmlData = xmlData
						+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n";
				
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";	
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL6><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL6><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL6>\n"
							//+	"<COL8><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL3>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL7>\n"
							//+	"<COL10><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL3>\n"
							+	"<COL8><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrM() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrN() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL10>\n"
							+	"<COL11><![CDATA["+ list.get(i).getZbigo() +"]]></COL11>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL6><![CDATA[결제금액기준(동일입력) 애 없음]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}else {
				// 사후 정산
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL3><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL3><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL3>\n"
							//+	"<COL4><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL4>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL4>\n"
							//+	"<COL5><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL5>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrM() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrN() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrStd() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getZbigo() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL3><![CDATA[결제금액기준(동일입력) 애 없음]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}
			xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		}
		
		// 공문서 일때만
		if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
				|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL>\n"
					+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
					+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
					+ "<USER_ID><![CDATA["+ paramVo.getEcsUserId() +"]]></USER_ID>\n"
					+ "</IF_RECV_OFFICIAL>\n";
		}
		
		xmlData = xmlData
				+ "</root>\n";
		try {
			
			// 노인코딩
			String postData2 = "data= " + xmlData;
			logger.info(" postData2 === " +  postData2 );
			
			// 데이터 전송
			OutputStream os = connection.getOutputStream();
			byte[] input = postData2.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			os.close();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			// 서버 응답 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			logger.info("response ::::::" + response);
			
			connection.disconnect();
			
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			if(!"".equals( response.toString() )) {
				String[] resultCode = response.toString().split("-");
				
				if(!"00".equals( resultCode[0] )) {
					returnMap.put("msg", resultCode[1]);
					returnMap.put("result", false);
				}else {
					returnMap.put("url", resultCode[1]);
					returnMap.put("result", true);
				}
			}else {
				returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				returnMap.put("result", false);
			}
			
		}catch (Exception e) {
			logger.info("e::::::::" + e.getMessage() );
			returnMap.put("result", false);
		}
		
		return returnMap;
	}
	
	public Map<String,Object> insertProdEcsIntgr3(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		URL url = new URL("https://devedocu.lcn.co.kr/httpReceive.do?");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setDoOutput(true);
		
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		// 행사 제안 정보 상세 조회
		NEDMPRO0310VO prodEvntVo = nedmpro0310Dao.selectProEventAppDetail(paramVo);
		list = nedmpro0310Dao.selectProEventAppItemList(paramVo);
		
		String ecsContCode = nedmpro0310Dao.selectEcsContCode(paramVo);	// ECS 연계 계약번호 생성
		
		// 공문
		prodEvntVo.setEcsDepCd("507");
		prodEvntVo.setLinCd("001");
		prodEvntVo.setDcCd("11470");
		prodEvntVo.setDcNmCd("12457");
		
		prodEvntVo.setEcsDepCd("96");
		prodEvntVo.setLinCd("007");
		prodEvntVo.setDcCd("12503");
		prodEvntVo.setDcNmCd("14526");
		
		prodEvntVo.setContCode(ecsContCode);
		paramVo.setContCode(ecsContCode);
		
		String xmlData =
				"<root>\n"
						+ "<HEAD_INFO>\n"
						+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
						+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
						+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
						+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
						+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
						+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
						+ "<DC_NAME><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></DC_NAME>\n"
						+ "<DC_CONDATE><![CDATA["+ DateUtil.getToday("yyyyMMdd")+"]]></DC_CONDATE>\n"
						+ "<DC_FROM><![CDATA[]]></DC_FROM>\n"
						+ "<DC_END><![CDATA[]]></DC_END>\n"
						+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
						+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
						+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
						+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
						+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
						+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
						+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
						+ "<EMP_NAME><![CDATA[]]></EMP_NAME>\n"
						+ "<DEP_CD><![CDATA["+ prodEvntVo.getEcsDepCd() +"]]></DEP_CD>\n"
						+ "<LIN_CD><![CDATA["+ prodEvntVo.getLinCd() +"]]></LIN_CD>\n"
						+ "<DC_CD><![CDATA["+ prodEvntVo.getDcCd() +"]]></DC_CD>\n"
						+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
						+ "</HEAD_INFO>\n"
						
				+ "<COMP_LIST>\n"
				+ "<COMP_NUM><![CDATA[2068511698]]></COMP_NUM>\n"
				+ "<COMP_NAME><![CDATA[롯데슈퍼사업부]]></COMP_NAME>\n"
				+ "<TEAM_CD><![CDATA[]]></TEAM_CD>\n"
				+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
				+ "</COMP_LIST>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFRCD]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrcd() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[LIFNR]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getLifnr() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[VKORG]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getVkorg() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFR_TXT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_PUR_TXT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqPurTxt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[OFSDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfsdt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[OFEDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getOfedt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[PRSDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPrsdt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[PREDT]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getPredt() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n"
				
				+	"<IF_RECV_OFFICIAL_ITEMS>\n"
				+	"<DC_NM_CD><![CDATA[" + prodEvntVo.getDcNmCd() + "]]></DC_NM_CD>\n"
				+	"<ITEM_ID><![CDATA[REQ_OFR_DESC]]></ITEM_ID>\n"
				+	"<ITEM_DATA><![CDATA["+ prodEvntVo.getReqOfrDesc() +"]]></ITEM_DATA>\n"
				+	"</IF_RECV_OFFICIAL_ITEMS>\n";
		
		for(int i = 0; i < list.size(); i++) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
					+ "<DC_NM_CD><![CDATA["+ prodEvntVo.getDcNmCd() +"]]></DC_NM_CD>\n"
					+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
					+ "<ROW_NUM><![CDATA["+ (i+1) +"]]></ROW_NUM>\n"
					+ "<COL1><![CDATA["+ list.get(i).getEan11() +"]]></COL1>\n"
					+ "<COL2><![CDATA["+ list.get(i).getMaktx() +"]]></COL2>\n";
			
			// 원가 인하
			if( "01".equals( prodEvntVo.getCostType() ) ) {
				xmlData = xmlData
						+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
						+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
						+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n";
				
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";	
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL6><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL6><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL6>\n"
							//+	"<COL8><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL3>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL7>\n"
							//+	"<COL10><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL3>\n"
							+	"<COL8><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getZbigo() +"]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrM() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getOfrN() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getOfrStd() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL9>\n"
							+	"<COL10><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL10>\n"
							+	"<COL11><![CDATA["+ list.get(i).getZbigo() +"]]></COL11>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL6><![CDATA[결제금액기준(동일입력) 애 없음]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL8>\n"
							+	"<COL9><![CDATA["+ list.get(i).getZbigo() +"]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}else {
				// 사후 정산
				if( "1001".equals( prodEvntVo.getReqType() ) || "1301".equals( prodEvntVo.getReqType() ) || "1501".equals( prodEvntVo.getReqType() ) ) {
					// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1002".equals( prodEvntVo.getReqType() ) ) {
					// 번들
					xmlData = xmlData
							//+	"<COL3><![CDATA["+ list.get(i).getOfrStd1() +"]]></COL3>\n"
							+	"<COL3><![CDATA["+ list.get(i).getOfrDisc1() +"]]></COL3>\n"
							//+	"<COL4><![CDATA["+ list.get(i).getOfrStd2() +"]]></COL4>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrDisc2() +"]]></COL4>\n"
							//+	"<COL5><![CDATA["+ list.get(i).getOfrStd3() +"]]></COL5>\n"
							+	"<COL5><![CDATA["+ list.get(i).getOfrDisc3() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1003".equals( prodEvntVo.getReqType() ) ) {
					// M+N
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrM() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getOfrN() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "1004".equals( prodEvntVo.getReqType() ) ) {
					// 연관
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getOfrStd() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalSalesPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqSalesPrc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getSalesPrc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL7>\n"
							+	"<COL8><![CDATA["+ list.get(i).getZbigo() +"]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "4002".equals( prodEvntVo.getReqType() ) ) {
					// 상품권
					xmlData = xmlData
							+	"<COL3><![CDATA[결제금액기준(동일입력) 애 없음]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getGiftAmt() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getZbigo() +"]]></COL6>\n"
							+	"<COL7><![CDATA[]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}else if( "8001".equals( prodEvntVo.getReqType() ) ) {
					// 원매가 인하
					xmlData = xmlData
							+	"<COL3><![CDATA["+ list.get(i).getPurPrc() +"]]></COL3>\n"
							+	"<COL4><![CDATA["+ list.get(i).getCalPurPrc() +"]]></COL4>\n"
							+	"<COL5><![CDATA["+ list.get(i).getCalComRate() +"]]></COL5>\n"
							+	"<COL6><![CDATA["+ list.get(i).getReqItemDesc() +"]]></COL6>\n"
							+	"<COL7><![CDATA["+ list.get(i).getZbigo() +"]]></COL7>\n"
							+	"<COL8><![CDATA[]]></COL8>\n"
							+	"<COL9><![CDATA[]]></COL9>\n"
							+	"<COL10><![CDATA[]]></COL10>\n";
				}
				
			}
			xmlData = xmlData + "</IF_RECV_OFFICIAL_TABLE_ROW>\n";
		}
		
		// 공문서 일때만
		if( "00".equals( prodEvntVo.getReqContyp() ) ||  "03".equals( prodEvntVo.getReqContyp() ) 
				|| "04".equals( prodEvntVo.getReqContyp() ) || "05".equals( prodEvntVo.getReqContyp() )) {
			xmlData = xmlData
					+ "<IF_RECV_OFFICIAL>\n"
					+ "<CONT_CODE><![CDATA["+ prodEvntVo.getContCode() +"]]></CONT_CODE>\n"
					+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
					+ "<USER_ID><![CDATA[testuserB]]></USER_ID>\n"
					+ "</IF_RECV_OFFICIAL>\n";
		}
		
		xmlData = xmlData
				+ "</root>\n";
		
		/*
		String xmlData =
				"<root>\n"
				+ "<HEAD_INFO>\n"
				+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
				+ "<MASTER_ID><![CDATA[]]></MASTER_ID>\n"
				+ "<FAMILY_YN><![CDATA[N]]></FAMILY_YN>\n"
				+ "<COMP_SEQ><![CDATA[]]></COMP_SEQ>\n"
				+ "<CONT_CODE><![CDATA[PCtest000005]]></CONT_CODE>\n"
				+ "<CONT_CODE_OLD><![CDATA[]]></CONT_CODE_OLD>\n"
				+ "<DC_NAME><![CDATA[파트너사_PC공문서]]></DC_NAME>\n"
				+ "<DC_CONDATE><![CDATA[20250402]]></DC_CONDATE>\n"
				+ "<DC_FROM><![CDATA[20250420]]></DC_FROM>\n"
				+ "<DC_END><![CDATA[29991231]]></DC_END>\n"
				+ "<DC_GONAMT><![CDATA[0]]></DC_GONAMT>\n"
				+ "<DC_VAT><![CDATA[0]]></DC_VAT>\n"
				+ "<DC_PREAMT><![CDATA[0]]></DC_PREAMT>\n"
				+ "<DC_MIDAMT><![CDATA[0]]></DC_MIDAMT>\n"
				+ "<DC_JANAMT><![CDATA[0]]></DC_JANAMT>\n"
				+ "<PAY_TERMS><![CDATA[]]></PAY_TERMS>\n"
				+ "<EMP_SABUN><![CDATA[]]></EMP_SABUN>\n"
				+ "<EMP_NAME><![CDATA[11테스트담당자]]></EMP_NAME>\n"
				+ "<DEP_CD><![CDATA[96]]></DEP_CD>\n"
				+ "<LIN_CD><![CDATA[007]]></LIN_CD>\n"
				+ "<DC_CD><![CDATA[12505]]></DC_CD>\n"
				+ "<DC_NM_CD><![CDATA[14529]]></DC_NM_CD>\n"
				+ "</HEAD_INFO>\n"
				+ "<COMP_LIST>\n"
				+ "<COMP_NUM><![CDATA[2068511698]]></COMP_NUM>\n"
				+ "<COMP_NAME><![CDATA[롯데슈퍼사업부]]></COMP_NAME>\n"
				+ "<TEAM_CD><![CDATA[test]]></TEAM_CD>\n"
				+ "<EMP_SABUN><![CDATA[5678]]></EMP_SABUN>\n"
				+ "</COMP_LIST>\n"
				+ "<IF_RECV_OFFICIAL_ITEMS>\n"
				+ "<DC_NM_CD><![CDATA[14529]]></DC_NM_CD>\n"
				+ "<ITEM_ID><![CDATA[EVENT_DT_ST]]></ITEM_ID>\n"
				+ "<ITEM_DATA><![CDATA[2025]]></ITEM_DATA>\n"
				+ "</IF_RECV_OFFICIAL_ITEMS>\n"
				+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
				+ "<DC_NM_CD><![CDATA[14529]]></DC_NM_CD>\n"
				+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
				+ "<ROW_NUM><![CDATA[001]]></ROW_NUM>\n"
				+ "<COL1><![CDATA[8888888888888]]></COL1>\n"
				+ "<COL2><![CDATA[테스트 / 행사상품]]></COL2>\n"
				+ "<COL3><![CDATA[금액]]></COL3>\n"
				+ "<COL4><![CDATA[]]></COL4>\n"
				+ "<COL5><![CDATA[]]></COL5>\n"
				+ "<COL6><![CDATA[]]></COL6>\n"
				+ "<COL7><![CDATA[]]></COL7>\n"
				+ "<COL8><![CDATA[]]></COL8>\n"
				+ "<COL9><![CDATA[]]></COL9>\n"
				+ "<COL10><![CDATA[]]></COL10>\n"
				+ "</IF_RECV_OFFICIAL_TABLE_ROW>\n"
				+ "<IF_RECV_OFFICIAL_TABLE_ROW>\n"
				+ "<DC_NM_CD><![CDATA[14529]]></DC_NM_CD>\n"
				+ "<LIST_NUM><![CDATA[1]]></LIST_NUM>\n"
				+ "<ROW_NUM><![CDATA[002]]></ROW_NUM>\n"
				+ "<COL1><![CDATA[4902430311625]]></COL1>\n"
				+ "<COL2><![CDATA[다우니핑크1L]]></COL2>\n"
				+ "<COL3><![CDATA[금액]]></COL3>\n"
				+ "<COL4><![CDATA[]]></COL4>\n"
				+ "<COL5><![CDATA[]]></COL5>\n"
				+ "<COL6><![CDATA[]]></COL6>\n"
				+ "<COL7><![CDATA[]]></COL7>\n"
				+ "<COL8><![CDATA[]]></COL8>\n"
				+ "<COL9><![CDATA[]]></COL9>\n"
				+ "<COL10><![CDATA[]]></COL10>\n"
				+ "</IF_RECV_OFFICIAL_TABLE_ROW>\n"
				+ "<IF_RECV_OFFICIAL>\n"
				+ "<CONT_CODE><![CDATA[PCtest000005]]></CONT_CODE>\n"
				+ "<SYS_CODE><![CDATA[PC]]></SYS_CODE>\n"
				+ "<USER_ID><![CDATA[testuserB]]></USER_ID>\n"
				+ "</IF_RECV_OFFICIAL>\n"
				+ "</root>\n";
		 */
		
		try {
			
			// 노인코딩
			String postData2 = "data= " + xmlData;
			
			logger.info(" postData2 === " +  postData2 );
			
			// 데이터 전송
			OutputStream os = connection.getOutputStream();
			byte[] input = postData2.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			os.close();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			// 서버 응답 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			logger.info("response ::::::" + response);
			
			connection.disconnect();
			
			// CONT CODE Update!!!
			paramVo.setTaskGbn("contCode");
			paramVo.setWorkId(workId);
			nedmpro0310Dao.updateProEventApp(paramVo);
			
			if(!"".equals( response.toString() )) {
				String[] resultCode = response.toString().split("-");
				
				if(!"00".equals( resultCode[0] )) {
					returnMap.put("msg", resultCode[1]);
					returnMap.put("result", false);
				}else {
					returnMap.put("url", resultCode[1]);
					returnMap.put("result", true);
				}
			}else {
				returnMap.put("msg", "ECS 연동중 오류가 발생하였습니다.\n잠시후 다시 시도해주세요.");
				returnMap.put("result", false);
			}
			
		}catch (Exception e) {
			logger.info("e::::::::" + e.getMessage() );
			returnMap.put("result", false);
		}
		
		return returnMap;
	}
	
	
	/**
	 * 행사 테마 번호 조회
	 */
	public HashMap<String,Object> selectProdEvntThmList(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		int totalCount = 0;
		
		// 행사 테마 번호 카운터 조회
		totalCount = nedmpro0310Dao.selectProdEvntThmCount(paramVo);

		if(totalCount > 0){	// 행사 테마 번호 리스트 조회
			list = nedmpro0310Dao.selectProdEvntThmList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
	}
	
	/**
	 * 팀 정보 조회
	 */
	public HashMap<String,Object> selectDepCdList(NEDMPRO0310VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0310VO> list = new ArrayList<NEDMPRO0310VO>();				//리스트 조회 결과 Vo
		
		int totalCount = 0;
		
		// 팀 정보 카운트 조회
		totalCount = nedmpro0310Dao.selectDepCdCount(paramVo);
		
		if(totalCount > 0){	// 팀 정보 리스트 조회
			list = nedmpro0310Dao.selectDepCdList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
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
