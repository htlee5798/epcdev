package com.lottemart.epc.edi.comm.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.common.service.PSCMCOM0001Service;
import com.lottemart.epc.common.util.ParquetUtil;
import com.lottemart.epc.edi.comm.dao.ScheduledDao;
import com.lottemart.epc.edi.comm.model.ScheduledVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.comm.service.ScheduledService;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0040Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : CommonServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		  	  수정자           		수정내용
 *  -------    		---------------    ---------------------------
 * 2025. 04. 09. 	 park jong gyu				최초생성
 * 
 * </pre>
 */
@Service("scheduledServiceImpl")
public class ScheduledServiceImpl implements ScheduledService
{
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);
	
	@Autowired
	private ScheduledDao scheduledDao;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private NEDMPRO0040Service nEDMPRO0040Service;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	private static String path;
	private static String authKey;
	private static String bucketName;
	private static String task;
	private static String gcsFileName;
	private static String localCsvFile;
	
	@PostConstruct
	public void gcsInit() {
		path = config.getString("epc.gcs.parquet.path");
		authKey = config.getString("epc.gcs.parquet.authKey");
		bucketName = config.getString("epc.gcs.parquet.bucketName");
		task = config.getString("epc.gcs.parquet.task");
	}
	
	/**
	 * 행사 마스터 데이터 계약 진행상태(계약완료) update!
	 */
	@Override
	public void updateProdEvntDoc( ScheduledVO scheduledVO ) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String rtnResult = "F";
		// ECS <> EPC IF 진행 상태 조회
		List<ScheduledVO> list = scheduledDao.selectProEventIfStsList(scheduledVO);
		
		for(ScheduledVO vo : list) {
			boolean rfcCall  = false;
			try {
				//vo.setDocNo1("");
				//vo.setDocNo2("");
				//vo.setDocNo3("");
				vo.setDcChgIf("0");
				/*if( "1".equals(vo.getVkorgGbn()) ) {
					// 마트이면.. 1번
					if( "KR02".equals(vo.getVkorg()) ) vo.setDocNo1( vo.getDcNum() );
					else vo.setDocNo2( vo.getDcNum() ); // 슈퍼
				}else {
					
				}*/
				
				// 슈퍼이면
				if( "KR04".equals(vo.getVkorg()) ) {
					vo.setDocNo2( vo.getDcNum() ); // 슈퍼
					vo.setDocNo3( vo.getDcNum2() ); // 씨에스
				}else vo.setDocNo1( vo.getDcNum() ); // 마트
				
				// 슈퍼이면..
				/*if("KR04".equals( vo.getVkorg() )) {
					vo.setDcStat("03");
					if( "1".equals(vo.getVkorgGbn()) && vo.getContCode() != null && vo.getContCode2() != null ) {
						vo.setDcStat("05");
						rfcCall = true;
						// 행사 제안 Table 진행상태 update				
						scheduledDao.updateProdEvnt(vo);
					}
				}else {
					rfcCall = true;
					// 행사 제안 Table 진행상태 update				
					scheduledDao.updateProdEvnt(vo);
				}*/
				
				// 행사 제안 Table 진행상태 update				
				scheduledDao.updateProdEvnt(vo);
				logger.info("rfcCall : " +  rfcCall );
				
				// 행사 확정
				if( "07".equals(vo.getDcStat())) {
					List<LinkedHashMap> headerData = scheduledDao.selectEvntRfcCallData(vo);
					JSONObject jo = new JSONObject();
					jo.put("HEADER", headerData);
						
					logger.info("jo.toString() a : \n" + jo.toString() );
					returnMap = rfcCommonService.rfcCall("PMR2030", jo.toString(), "Batch");
					logger.info("returnMap : " + returnMap);
						
					JSONObject mapObj        = new JSONObject(returnMap.toString());  
					JSONObject resultObj     = mapObj.getJSONObject("result");        
					JSONObject respCommonObj = resultObj.getJSONObject("ES_RETURN");  
					rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("TYPE"));
					logger.info("rtnResult : " + rtnResult);
					
					// 성공이면
					if (rtnResult.equals("S")) {
						// if_return 상태값 update
						scheduledDao.updateIfReturn(vo);
					}
				}
				
			}catch (Exception e) {
				logger.info("e::::::::" + e.getMessage() );
			}
		}// End for
	}
	
	/**
	 * 행사 마스터 데이터 계약 진행상태(계약삭제 / 계약폐기) update!
	 */
	public void updateProdEvntDelDoc( ScheduledVO scheduledVO ) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String rtnResult = "F";
		// ECS <> EPC IF 진행 상태 조회
		List<ScheduledVO> list = scheduledDao.selectProEventIfDelStsList(scheduledVO);
		
		for(ScheduledVO vo : list) {
			
			try {
				//vo.setDocNo1("");
				//vo.setDocNo2("");
				//vo.setDocNo3("");
				//vo.setDcStat("04");
				vo.setDcChgIf("1");
				// ECS 계약 진행상태가 삭제면 행사 진행상태 MD 승인으로 변경해줌
				scheduledDao.updateProdEvnt(vo);
				
				// 행사 삭제
				if( "44".equals(vo.getDcStat()) || "98".equals(vo.getDcStat()) || "99".equals(vo.getDcStat()) ) {
					List<LinkedHashMap> headerData = scheduledDao.selectEvntRfcCallData(vo);
					JSONObject jo = new JSONObject();
					jo.put("HEADER", headerData);
					
					logger.info("jo.toString() a : \n" + jo.toString() );
					returnMap = rfcCommonService.rfcCall("PMR2030", jo.toString(), "Batch");
					logger.info("returnMap : " + returnMap);
					
					JSONObject mapObj        = new JSONObject(returnMap.toString());  
					JSONObject resultObj     = mapObj.getJSONObject("result");        
					JSONObject respCommonObj = resultObj.getJSONObject("ES_RETURN");  
					rtnResult = StringUtils.trimToEmpty(respCommonObj.getString("TYPE"));
					logger.info("rtnResult : " + rtnResult);
				}
				
				// 성공이면
				if (rtnResult.equals("S")) {
					// if_return 상태값 update
					//scheduledDao.updateIfReturn(vo);
				}
			}catch (Exception e) {
				logger.info("e::::::::" + e.getMessage() );
			}
		}// End for
	}
	
	/**
	 * 원가변경요청 계약번호 업데이트
	 * - 원가변경요청 테이블 내 epc 연계번호 (CONT_CODE)가 있으면서, ECS 계약번호(DC_NUM)가 없으면, ECS 테이블에서 찾아서 UPDATE 해준다.
	 *   (원가변경요청 > 협의요청 이후 상태값 수신 I/F에서, DC_NUM을 포함하여 대상을 찾아 UPDATE 하기 때문에,
	 *    협의 요청 전 원가변경요청 테이블에서 반드시 DC_NUM을 가지고 있어야 한다.) 
	 */
	public void updateProdChgCostDcNum(ScheduledVO scheduledVO) throws Exception {
		logger.info("scheduledService.updateProdChgCostDcNum =============== start");
		
		//원가변경요청 계약번호 업데이트
		int upd = scheduledDao.updateProdChgCostDcNum(scheduledVO);
		
		logger.info("scheduledService.updateProdChgCostDcNum =============== end");
	}
	
	/**
	 * 신상품확정요청_BOS 미처리 건 ERP 자동발송
	 */
	@Override
	public Map<String, Object> updateImsiProductListFixAutoSend(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		String sendGbn = nEDMPRO0040VO.getSendGbn();	//전송대상구분 (ATO: 자동발송)
		String batchYn = nEDMPRO0040VO.getBatchYn();	//배치여부 (Y: 배치, N: 수동호출)
		
		//배치일 경우, 휴일에는 발송하지 않음
		if("Y".equals(batchYn)) {
			//오늘이 휴일인지
			String isTodyHoliday = scheduledDao.selectTodayIsHolidayYn();
			if("Y".equals(isTodyHoliday)) {
				resultMap.put("msg", "NOT SEND HOLIDAY");
				return resultMap;
			}
		}
		
		// 자동발송 대상 신상품 list 추출
		List<NEDMPRO0040VO> prodList = nEDMPRO0040Service.selectNewTmpProductInfoSendGbn(nEDMPRO0040VO);
		if(prodList == null || prodList.isEmpty() || prodList.size() == 0) {
			resultMap.put("msg", "NO_DATA_LIST");
			return resultMap;
		}
		
//		//ERP 자동발송 대상 setting
//		nEDMPRO0040VO.setErpProdList(prodList);
		
		List<String> okPgmIdList = new ArrayList<String>();		//자동발송 성공 pgmId List
		
		Map<String,Object> sendResultMap = null;	//ERP 전송 결과 MAP
		int upd = 0;
		
		NEDMPRO0040VO autoVo = new NEDMPRO0040VO();
		try {
			
			List<NEDMPRO0040VO> sendProdTempList = new ArrayList<NEDMPRO0040VO>();
			
			String sendPgmId = "";
			for(NEDMPRO0040VO sendProdVo : prodList) {
				sendPgmId = StringUtils.defaultString(sendProdVo.getPgmId());		//전송 pgmId
				
				sendProdTempList.clear();
				
				sendProdVo.setSendGbn(sendGbn);
				sendProdVo.setBatchYn(batchYn);
				
				sendProdTempList.add(sendProdVo);
				sendProdVo.setErpProdList(sendProdTempList);
				
				//ERP Proxy 전송
				sendResultMap = nEDMPRO0040Service.updateImsiProductListFixSendErp(sendProdVo, request);
				
				//ERP Proxy 전송 성공 시, 자동발송여부 update
				if("SUCCESS".equals(MapUtils.getString(sendResultMap, "msg"))) {
					autoVo.setProdArr(sendProdTempList);
					upd += scheduledDao.updateTpcNewProdRegAutoSendFg(autoVo);
					
					//전송 성공 시, 성공 list에 pgmId 추가
					okPgmIdList.add(sendPgmId);
				}
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			resultMap.put("msg", "SEND_ERP_ERROR");
		}
		
		if(upd>0) {
			resultMap.put("msg", "SUCCESS");
			resultMap.put("pgmIdList", okPgmIdList);	//전송성공 pgmId list
		}
		
		return resultMap;
	}
	
	
	/**
     * google cloud storage 이용해서 Parquet 파일 가져오기(파트너사 실적)
     */
	public void updateGcsGetParquet(ScheduledVO scheduledVO) throws Exception {
		for(int j = 0; j < 3; j++) {
			if(j == 0) {
				gcsFileName = config.getString("epc.gcs.parquet.venPrfr");
				localCsvFile = config.getString("epc.gcs.parquet.localCsvFile") + "_prfr.csv";
			}else if(j == 1) {
				gcsFileName = config.getString("epc.gcs.parquet.venBuy");
				localCsvFile = config.getString("epc.gcs.parquet.localCsvFile") + "_buy.csv";
			}else if(j == 2) {
				gcsFileName = config.getString("epc.gcs.parquet.newProdPrfr");
				localCsvFile = config.getString("epc.gcs.parquet.localCsvFile") + "_prodPrfr.csv";
			}
			logger.info("path : " + path );
			logger.info("authKey : " + authKey );
			logger.info("bucketName : " + bucketName );
			logger.info("task : " + task );
			logger.info("fileName : " + gcsFileName );
			logger.info("localCsvFile : " + localCsvFile );
			
	    	List<Map<String, Object>> list = fileReadList();
	    	logger.info( "list size !!!!!!!!! : " + list.size() );
	    	
	        for(int i = 0 ; i < list.size(); i++) {
	        	//logger.info(" list : " +  list.get(i) );
	        	if(j == 0)	scheduledDao.updateMainVenPrfr(list.get(i));
				else if(j == 1)	scheduledDao.updateMainVenBuy(list.get(i));
				else if(j == 2)	scheduledDao.updateMainNewProdPrfr(list.get(i));
	        }
	        
	        File file = new File(path + gcsFileName);
		    // 파일이 존재할 경우에만 삭제
	        if (file.exists()) {
	        	synchronized (this) {
	        		try {
	        			if (file.delete()) {
			        		logger.error("파일 삭제 완료 : " + path + localCsvFile);
			        	} else {
			        		throw new IOException("파일 삭제 실패 : " + path + localCsvFile);
			        	}
	        		} catch(IOException e) {
	        			logger.error(e.getMessage());
	        			throw e;
	        		}
	        	}
	        }
	        
	        File file2 = new File(path + localCsvFile);
	        // 파일이 존재할 경우에만 삭제
	        if (file2.exists()) {
	        	synchronized (this) {
	        		try {
	        			if (file2.delete()) {
			        		logger.error("파일 삭제 완료 : " + path + localCsvFile);
			        	} else {
			        		throw new IOException("파일 삭제 실패 : " + path + localCsvFile);
			        	}
	        		} catch(IOException e) {
	        			logger.error(e.getMessage());
	        			throw e;
	        		}
	        	}
	        }
		}// end for
	}

	public static List<Map<String, Object>> fileReadList() throws Exception {
		//gcs Root 경로 생성
		File gcsFilePath = new File(path);
		if (!gcsFilePath.exists()) {
			gcsFilePath.mkdirs();
		}
		
		//gcs 업무 경로 생성
		File gcsPqtPath = new File(path+"EPC");
		if (!gcsPqtPath.exists()) {
			gcsPqtPath.mkdirs();
		}
		
		
		ParquetUtil reader = new ParquetUtil(path + authKey);
		reader.downloadParquet(bucketName, gcsFileName, path);
    	reader.convertParquetToCsv(path + gcsFileName, path + localCsvFile);
    	return reader.readCsvToData(path + localCsvFile);
	}

	/**
	 * 휴일 및 공휴일 등록
	 */
	@Override
	public Map<String, Object> insertTpcHoliday(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		String srchYear = MapUtils.getString(paramMap, "YYYY", "");		//조회연도
		
		if("".equals(srchYear)) {
			logger.error("scheduledService.insertTpcHoliday Exception : 조회 연도 누락");
			resultMap.put("errMsg", "조회 연도가 누락되었습니다.");
			return resultMap;
		}
		
		/*
		 * 1. 해당 연도에 등록된 휴일정보 삭제
		 */
		scheduledDao.deleteTpcHolidayYear(paramMap);
		
		/*
		 * 2. 해당 연도에 대한 휴일 정보 데이터 리스트 구성
		 */
		List<Map<String,String>> holidayList = getNationalHolidays(srchYear);
		
		//해당 연도 휴일정보 존재 시, 데이터 insert
		if(holidayList != null && !holidayList.isEmpty() && holidayList.size()>0) {
			try {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("YYYY", srchYear);			//연도
				dataMap.put("DATA_LIST", holidayList);	//
				scheduledDao.insertTpcHoliday(dataMap);
			}catch(Exception e) {
				logger.error("scheduledService.insertTpcHoliday Exception : DATA INSERT ERROR");
				resultMap.put("errMsg", "휴일정보 등록 중 오류가 발생했습니다.");
				return resultMap;
			}
		}
		
		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}
	
	/**
	 * 공휴일 Map Data 생성
	 * @param isLunar
	 * @param yyyy
	 * @param mm
	 * @param dd
	 * @param dyNm
	 * @return Map<String,String>
	 */
	private static Map<String,String> getHolidayMap(boolean isLunar, String yyyy, String mm, String dd, String dyNm){
		Map<String,String> holiMap = new HashMap<String,String>();
		
		//리턴데이터
		String to_yyyy = yyyy;	//연
		String to_mm = mm;		//월
		String to_dd = dd;		//일
		
		//음력 > 양력 변환 Start ----------------------------------------
		if(isLunar) {
			int year, month, day;
			try {
				//음력>양력변환을 위한 convert
				year = Integer.parseInt(yyyy);
				month = Integer.parseInt(mm);
				day = Integer.parseInt(dd);
				
				//양력일자 계산
				String solarDy = DateUtil.convertLunarToSolar(year, month, day);
				
				if(solarDy == null || "".equals(solarDy)) {
					System.out.println(String.format("음력 > 양력 번환 실패 ::: param {yyyy: %s, mm: %s, dd: %s, dyNm: %s}", yyyy, mm, dd, dyNm));
					return null;
				}
				
				solarDy = DateUtil.string2String(solarDy, DateUtil.DATE_PATTERN, "yyyyMMdd");
				
				to_yyyy = solarDy.substring(0, 4);
				to_mm = solarDy.substring(4, 6);
				to_dd = solarDy.substring(6, 8);
			}catch(Exception e) {
				logger.error(e.getMessage() + String.format("::: param {yyyy: %s, mm: %s, dd: %s, dyNm: %s}", yyyy, mm, dd, dyNm));
				return null;
			}
		}
		//음력 > 양력 변환 End ----------------------------------------
		
		//결과 맵 셋팅
		holiMap.put("YYYY", to_yyyy);					//연
		holiMap.put("MM", to_mm);						//월
		holiMap.put("DD", to_dd);						//일
		holiMap.put("DY", to_yyyy+to_mm+to_dd);			//연+월+일 (yyyyMMdd)
		holiMap.put("DY_NM", dyNm);						//휴일명
		
		return holiMap;
	}
	
	/**
	 * 국가 공휴일 list 생성
	 * @param year
	 * @return List<Map<String,String>>
	 */
	private List<Map<String,String>> getNationalHolidays(String year){
		/*
		 * [국가공휴일]
		 * 1. 양력
		 * 		0101 신정
		 * 		0301 삼일절
		 * 		0505 어린이날
		 * 		0606 현충일
		 * 		0815 광복절
		 * 		1003 개천절
		 * 		1009 한글날
		 * 		1225 크리스마스
		 * 
		 * 2. 음력
		 * 		12월말일 설날(D-1)
		 * 		0101 설날
		 * 		0102 설날(D+1)
		 * 		0408 석가탄신일
		 * 		0814 추석(D-1)
		 * 		0815 추석
		 * 		0816 추석(D+1)
		 */
		//공휴일 리스트
		List<Map<String,String>> holidayList = new ArrayList<Map<String,String>>();
		
		/* 1) 양력 ------------------------------------------------------------- */
		holidayList.add(getHolidayMap(false, year, "01", "01", "신정"));
		holidayList.add(getHolidayMap(false, year, "03", "01", "3.1절"));
		holidayList.add(getHolidayMap(false, year, "05", "05", "어린이날"));
		holidayList.add(getHolidayMap(false, year, "06", "06", "현충일"));
		holidayList.add(getHolidayMap(false, year, "08", "15", "광복절"));
		holidayList.add(getHolidayMap(false, year, "10", "03", "개천절"));
		holidayList.add(getHolidayMap(false, year, "10", "09", "한글날"));
		holidayList.add(getHolidayMap(false, year, "12", "29", "크리스마스"));
		
		/* 2) 음력 ------------------------------------------------------------- */
		holidayList.add(getHolidayMap(true, year, "04", "08", "부처님 오신 날"));
				
		/* 3) 음력/설날,추석 ------------------------------------------------------ */
		//3-1) 설날
		Map<String,String> seol = getHolidayMap(true, year, "01", "01", "설연휴");
		String seolDy = MapUtils.getString(seol, "DY", "");										//설날 양력 날짜 (yyyyMMdd)
		String seolDyFmt = DateUtil.string2String(seolDy, "yyyyMMdd", DateUtil.DATE_PATTERN);	//설날 연휴 계산을 위한 formatting
		String seol_bf = DateUtil.string2String(DateUtil.addDays(seolDyFmt, -1), DateUtil.DATE_PATTERN, "yyyyMMdd");	//설날 전날
		String seol_af = DateUtil.string2String(DateUtil.addDays(seolDyFmt, 1), DateUtil.DATE_PATTERN, "yyyyMMdd");		//설날 다음날
		
		holidayList.add(seol);	//설 당일
		holidayList.add(getHolidayMap(false, year, seol_bf.substring(4, 6), seol_bf.substring(6, 8), "설연휴"));	//설 D-1
		holidayList.add(getHolidayMap(false, year, seol_af.substring(4, 6), seol_af.substring(6, 8), "설연휴"));	//설 D+1
		
		//3-2) 추석
		Map<String,String> chuseok = getHolidayMap(true, year, "08", "15", "추석");
		String chuseokDy = MapUtils.getString(chuseok, "DY", "");									//추석 양력 날짜(yyyyMMdd)
		String chuseokDyFmt = DateUtil.string2String(chuseokDy, "yyyyMMdd", DateUtil.DATE_PATTERN);	//추석 연휴 계산을 위한 formatting
		String chu_bf = DateUtil.string2String(DateUtil.addDays(chuseokDyFmt, -1), DateUtil.DATE_PATTERN, "yyyyMMdd");		//추석 전날
		String chu_af = DateUtil.string2String(DateUtil.addDays(chuseokDyFmt, 1), DateUtil.DATE_PATTERN, "yyyyMMdd");		//추석 다음날
		
		holidayList.add(chuseok);
		holidayList.add(getHolidayMap(false, year, chu_bf.substring(4, 6), chu_bf.substring(6, 8), "추석"));		//추석 D-1
		holidayList.add(getHolidayMap(false, year, chu_af.substring(4, 6), chu_af.substring(6, 8), "추석"));		//추석 D+1
			
		
		//공휴일 리스트 null로 추가된 값 제거
		Iterator<Map<String,String>> itr = holidayList.iterator();
		while(itr.hasNext()) {
			if(itr.next() == null) {
				itr.remove();
			}
		}
		
		return holidayList;
	}

}
