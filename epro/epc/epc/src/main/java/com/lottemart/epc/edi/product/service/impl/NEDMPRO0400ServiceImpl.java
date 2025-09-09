package com.lottemart.epc.edi.product.service.impl;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.EpcSftpUtil;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.CommonProductDao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0400Dao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0400Service;

import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0400Service")
public class NEDMPRO0400ServiceImpl implements NEDMPRO0400Service {
	
	private static final String[] PROP_ZZORGS = {"MT", "SP"};
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0400ServiceImpl.class);
	
	@Autowired
	private NEDMPRO0400Dao nedmpro0400Dao;
	
	@Resource(name = "commonProductDao")
	private CommonProductDao commonProductDao;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired
	EpcSftpUtil epcSftpUtil;
	
	/**
	 * 입점제안 등록 총 갯수
	 */
	public int selectProposeStoreListCount(NEDMPRO0400VO vo, HttpServletRequest request) throws Exception{
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		vo.setVenCds(myVenCds);
		
		return nedmpro0400Dao.selectProposeStoreListCount(vo);
	}
	
	/**
	 * 입점제안 등록 리스트 
	 */
	public List<NEDMPRO0400VO> selectProposeStoreList(NEDMPRO0400VO vo, HttpServletRequest request) throws Exception{
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		vo.setVenCds(myVenCds);
		
		//소속회사의 업체코드별 사용 가능한 제안 채널정보 구성
		Map<String, Object> venMap = this.getMayVenZzorgsMap(request);
		
		//입점제안 list 조회
		List<NEDMPRO0400VO> resultList = nedmpro0400Dao.selectProposeStoreList(vo);
		
		//임점제안 list 있을 경우, 추가정보 셋팅
		if(resultList != null && resultList.size() > 0) {
			List<Map<String,Object>> venSellChanCds = null;	//업체코드별 사용가능한 제안 채널 코드 리스트
			
			for(NEDMPRO0400VO nedmproVo : resultList) {
				//업체코드별 사용가능한 제안 채널 코드 리스트 셋팅
				venSellChanCds = (List<Map<String, Object>>) venMap.getOrDefault(nedmproVo.getVenCd(), Collections.emptyList());
				nedmproVo.setSelBoxChanCds(venSellChanCds);
			}
		}
		
		return resultList;
	}
	
	/**
	 * 신규 입점 제안 정보 저장 
	 */
	public Map<String, Object> insertNewPropStore(List<NEDMPRO0400VO> paramVoList, HttpServletRequest request, MultipartFile[] files) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		//이미지 등록용 시퀀스
		int seq = 0;
		String tempPropRegNo = null;
		
		String chkVenCd = ""; 	//파트너사
		String chkTeamCd = ""; //팀
		
		
		  for (int i = 0; i < paramVoList.size(); i++) {
			  NEDMPRO0400VO paramVo = paramVoList.get(i);
			  
			  //필수값 검증 ------------- start -------------------
			  chkVenCd = StringUtils.defaultString(paramVo.getVenCd());		// 파트너사 코드 
			  chkTeamCd = StringUtils.defaultString(paramVo.getTeamCd());	// 팀 코드
			  
			  
			  if("".equals(chkVenCd)) {
				  resultMap.put("errMsg", "파트너사 코드가 존재하지 않습니다.");
				  return resultMap;
			  }
			  
			  if("".equals(chkTeamCd)) {
				  resultMap.put("errMsg", "팀 코드가 존재하지 않습니다.");
				  return resultMap;
			  }
			  
			  //필수갑 검증 -------------- end -------------------
			  
			//작업자 아이디 설정 
			paramVo.setRegId(workId);
			paramVo.setModId(workId);

			//입점 제안 번호가 존재하는지 확인 
			String propRegNo = paramVo.getPropRegNo();
			
			//요청 유무 (저장 상태이므로 요청 유무는 N으로 설정 (반려 후 새로운 요청도 N 으로 초기화???))
			if(paramVo.getPrdtReqYn() == "" || paramVo.getPrdtReqYn() == null) {
				paramVo.setPrdtReqYn("N");
			}
			
			//반려상태로 들어올경우 새로운 요청번호 채번해야하려면 기존에 있던 입점제안 건은 삭제여부를 Y로 변경  
			if(paramVo.getPrdtStatus().equals("1") && propRegNo != null) {
				//반려후 신규로 저장되는 상품같은경우 삭제여부를 Y로 업데이트
				nedmpro0400Dao.updateNewPropDelYn(paramVo);
				
				//입점제안번호 초기화 및 요청유무 초기화 
				paramVo.setPropRegNo("");
				paramVo.setPrdtReqYn("N");
				propRegNo = "";
			}
			
			
			//유사(타겟)상품코드가 없으면 X로 데이터 들어가게 세팅 
			if(paramVo.getSellCd() == "" || paramVo.getSellCd() == null) {
				paramVo.setSellCd("X");
			}
			
			
			//신규 데이터 저장 
			if(propRegNo == null || propRegNo == "") {
				//시퀀스 없이 요청번호만 키값으로 움직이는경우 
				String paramSeq = seq + "";
				propRegNo = nedmpro0400Dao.selectNewPropRegNo(paramSeq);
				
				//이미지 등록을 위한 시퀀스 
				paramVo.setSeq(seq);
				
				//신규 입점제안번호 세팅
				paramVo.setPropRegNo(propRegNo);
				
				//파트너사 등록 상태로 변경 
				paramVo.setPrdtStatus("0");
				
				
				// 이미지 저장=================================== start 
				if(paramVo.getOrgFileNm() != null && paramVo.getOrgFileNm() != "") {
				
			        if (files != null && files.length > i && files[i] != null && !files[i].isEmpty()) {
			        	
			        	if(paramVo.getImgId() == null || paramVo.getImgId() == "") {
							//신규 일경우 이미지 아이디 구하기 
							String imgId = nedmpro0400Dao.selectNewImgId(seq);
							paramVo.setImgId(imgId);	
						}
			        	
			            String originalFileName = files[i].getOriginalFilename();
			            String saveFileNm = "NewpropStore_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(originalFileName);
			     //       String filePath = "C:/Project-lottemart/NewPropStore/enc/";
						String filePath = ConfigUtils.getString("edi.propNewStore.image.path") + "/";
						String imgUrl = paramVo.getImgfileUrl();
			            
			            File saveFolder = new File(filePath);
			            if (!saveFolder.exists()) saveFolder.mkdirs();
			
			            File savedFile = new File(filePath + saveFileNm);
			            files[i].transferTo(savedFile);
			
			            String fileSize = String.valueOf(savedFile.length());
			            
			            paramVo.setOrgFileNm(originalFileName);
			            paramVo.setSaveFileNm(saveFileNm);
			            paramVo.setFilePath(filePath);
			            paramVo.setFileSize(Integer.parseInt(fileSize));
			            
			            String nweFileSource = filePath + saveFileNm;
			            
			            File fileCheck = new File(nweFileSource);
						
			            boolean isOk = imageFileSizeCheck(fileCheck);
			            
			            // 신규 입점 제안 정보 이미지 저장 
			            nedmpro0400Dao.insertNewPropStoreImg(paramVo);
			        	// 최종 확인 조건
			            /*
						if (isOk) {
							//이미지 존재할경우 이미지 저장 
							nedmpro0400Dao.insertNewPropStoreImg(paramVo);
						}*/
			        }
			 
				}
				// 이미지 저장===================================  end	
				
				//신규 입점제안 정보 저장 
				nedmpro0400Dao.insertNewPropStore(paramVo);		
				
				// 하나의 입점제안에 여러개 시퀀스 들어가도록 설정
				seq++;
			}else {
				
				//원래 이미지가 없었음
				if(paramVo.getOrgImgId() == null || paramVo.getOrgImgId().equals("")) {
					
					//이미지가 없음 -> 있음 
					if(paramVo.getOrgFileNm() != null && paramVo.getOrgFileNm() != "") {
						
						 if (files != null && files.length > i && !files[i].isEmpty()) {
								String imgId = nedmpro0400Dao.selectNewImgId(seq);
								paramVo.setImgId(imgId);	
								
							    String originalFileName = files[i].getOriginalFilename();
					            String saveFileNm = "NewpropStore_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(originalFileName);
					     //       String filePath = "C:/Project-lottemart/NewPropStore/enc/";
								String filePath = ConfigUtils.getString("edi.propNewStore.image.path") + "/";
								String imgUrl = paramVo.getImgfileUrl();
					            
					            File saveFolder = new File(filePath);
					            if (!saveFolder.exists()) saveFolder.mkdirs();
					
					            File savedFile = new File(filePath + saveFileNm);
					            files[i].transferTo(savedFile);
					
					            String fileSize = String.valueOf(savedFile.length());
					            
					            paramVo.setOrgFileNm(originalFileName);
					            paramVo.setSaveFileNm(saveFileNm);
					            paramVo.setFilePath(filePath);
					            paramVo.setFileSize(Integer.parseInt(fileSize));
					            
					            String nweFileSource = filePath + saveFileNm;
					            
					            File fileCheck = new File(nweFileSource);
								
					            boolean isOk = imageFileSizeCheck(fileCheck);
								
								
								// 신규 입점 제안 정보 이미지 저장 
					            nedmpro0400Dao.insertNewPropStoreImg(paramVo); 
					            
					            seq++;
						 }
					}
					
				}else { //원래 이미지가 있었음
					
					String orgFileNm = nedmpro0400Dao.selectOrgFileNm(paramVo.getImgId());
					//이미지가 변경되지 않은경우
					if(paramVo.getOrgFileNm().equals(orgFileNm)) {
						
					}else {
						//이미지가 변경된경우 
						if (files != null && files.length > i && !files[i].isEmpty()) {
							  String originalFileName = files[i].getOriginalFilename();
					            String saveFileNm = "NewpropStore_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(originalFileName);
					     //       String filePath = "C:/Project-lottemart/NewPropStore/enc/";
								String filePath = ConfigUtils.getString("edi.propNewStore.image.path") + "/";
								String imgUrl = paramVo.getImgfileUrl();
					            
					            File saveFolder = new File(filePath);
					            if (!saveFolder.exists()) saveFolder.mkdirs();
					
					            File savedFile = new File(filePath + saveFileNm);
					            files[i].transferTo(savedFile);
					
					            String fileSize = String.valueOf(savedFile.length());
					            
					            paramVo.setOrgFileNm(originalFileName);
					            paramVo.setSaveFileNm(saveFileNm);
					            paramVo.setFilePath(filePath);
					            paramVo.setFileSize(Integer.parseInt(fileSize));
					            
					            String nweFileSource = filePath + saveFileNm;
					            
					            File fileCheck = new File(nweFileSource);
								
					            boolean isOk = imageFileSizeCheck(fileCheck);
					            
					          	//이미지 존재할경우 이미지 업데이트 
								nedmpro0400Dao.updateNewPropStoreImg(paramVo);
						}
					}
					
				}
				
				//반려상태인 경우 새롭게 수정하면 반려상태 -> 등록 상태로 변경 요청유무도 N으로 리셋 
				paramVo.setPrdtStatus("0");
				paramVo.setPrdtReqYn("N");
				
				//신규 입점제안이아닌 수정일경우 업데이트 
				nedmpro0400Dao.updateNewPropStore(paramVo);
			}
		}
		
		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}
	
	
	/**
	 * 이미지 사이즈 체크 
	 * @param checkFile
	 * @return
	 * @throws Exception
	 */
	private boolean imageFileSizeCheck(File checkFile) throws Exception {
		
		long maxSize = 1024 * 600; // 이미지 파일 MaxSize;
		int minPixelSize = 500 * 500; // 최소 픽셀 사이즈
		int maxPixelSize = 1500 * 1500; // 최대 픽셀 사이즈

		long fileSize = checkFile.length(); // 파일용량
		
		Image chkImg = null;
		try {
			chkImg = ImageIO.read(checkFile);
		} catch (IOException ioe) {
			logger.error("ImageIO read ERROR");
			throw ioe;
		}

		int imgHeight = chkImg.getHeight(null);
		int imgWidth = chkImg.getWidth(null);

		int xMinusY = imgHeight - imgWidth; // 가로 세로 1:1 비율 체크
		int imgPixelSize = imgHeight * imgWidth; // 전체 픽셀 사이즈

		boolean isOk = fileSize <= maxSize && xMinusY == 0 && minPixelSize <= imgPixelSize && imgPixelSize <= maxPixelSize;

		return isOk;
	}
	
	//등록된 이미지 상세조회 
	public NEDMPRO0400VO selectDetailImgInfo(String param) throws Exception {
		NEDMPRO0400VO imgVo = nedmpro0400Dao.selectDetailImgInfo(param);
		//null exception 방지
		if(imgVo == null) imgVo = new NEDMPRO0400VO();
		return imgVo;
	}
	
	/**
	 * 신규 입점상품 삭제 
	 */
	public Map<String, Object> deleteNewPropStore(List<NEDMPRO0400VO> paramVoList) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		NEDMPRO0400VO paramVo = null;
		paramVo.setRegId(workId);
		paramVo.setModId(workId);
		
		for (int i = 0; i < paramVoList.size(); i++) {
			paramVo = paramVoList.get(i);
			
			//제안번호 없음
			if(paramVo.getPropRegNo() == null || paramVo.getPropRegNo() == "") {
				resultMap.put("errMsg", "선택된 값들중" + i+1 + "번째 필수 파라미터 누락");
				throw new AlertException("입정상품 삭제 중 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
			}
			
			//신규 입점 상품 삭제 
			nedmpro0400Dao.deleteNewPropStore(paramVo);	
			
			//선택된 상품이 이미지가 존재하는지 여부 
			if(paramVo.getImgId() != null && paramVo.getImgId() != "") {
				//신규 입점 상품 이미지 삭제 
				nedmpro0400Dao.deleteImgNewPropStore(paramVo);	
			}
		}
		
		resultMap.put("msg", "SUCCESS");
		return resultMap;
	}
	
	
	/**
	 * 신규입점 제안 제안 요청 
	 */
	public Map<String, Object> updateNewPropStoreRequest(List<NEDMPRO0400VO> paramVoList , HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("msg", "FAIL");
	    
	    //작업자정보 setting (로그인세션에서 추출)
	    EpcLoginVO epcLoginVO = this.getWorkSessionVo();
	    String workId = epcLoginVO.getLoginWorkId();
	    
	    
	    //요청리스트 없음
	    if (paramVoList == null || paramVoList.isEmpty()) {
	    	resultMap.put("errMsg", "입력된 데이터가 없습니다.");
	    	return resultMap;
	    }
	    
	    //상태변경용 vo setting
	    NEDMPRO0400VO stsVo = new NEDMPRO0400VO();
	    stsVo.setRegId(workId);
	    stsVo.setModId(workId);
	    stsVo.setProdArr(paramVoList);
	    
	    
		//RFC 보낼 데이터 리스트
		List<HashMap> totalRfcPropStoreList = new ArrayList<>();
		List<HashMap> datalist = null;
		
		/*
		 *  1. 모든 데이터를 한 번에 가져오기
		 */
		String propRegNo = "";
		for (NEDMPRO0400VO paramVo : paramVoList) {
			propRegNo = StringUtils.defaultString(paramVo.getPropRegNo());		//제안번호
			
			//제안번호 누락
			if ("".equals(propRegNo)) {
				throw new AlertException("입점제안 요청 중 오류가 발생했습니다.");
			}
			
			//작업자 세팅 
			paramVo.setModId(workId);
			paramVo.setRegId(workId);
		
			//rfc 전송할 데이터 조회 
			datalist = nedmpro0400Dao.selectTmpPropStoreInfo(paramVo);
			
			//전송할 data 없을 경우, pass
			if(datalist == null || datalist.isEmpty()) {
				logger.error("No Data For Send (제안번호 : "+propRegNo+")");
				continue;
			}
			
			//전송 data list에 추가
			totalRfcPropStoreList.addAll(datalist);
			
			//rfc 전송할데이터 성공 조회시 상태값 요청유무를 Y로 업데이트
			paramVo.setPrdtReqYn("Y");
			nedmpro0400Dao.updateNewPropStoreReqYn(paramVo);
		}
		
		//전송 가능 datalist 구성 실패
		if (totalRfcPropStoreList.isEmpty()) {
			throw new AlertException("요청 가능한 입점제안 정보가 없습니다.");
		}
		
		
		/*
		 * 2. 신상품 입점제안 이미지 전송 (파일 있을 경우, PO 전송)
		 */
		Map<String,Object> imgSendMap = new HashMap<String,Object>();
		imgSendMap.put("prodArr", totalRfcPropStoreList);
		
		//TODO_JIA :::::: SFTP 서버 테스트 완료 후 주석 해제 처리 예정
//		Map<String,Object> esgFileSendMap = this.selectTpcProdNewPropFileForSend(imgSendMap);
//		//전송실패 시,
//		if(!MapUtils.getString(esgFileSendMap, "msg").equals("SUCCESS")) {
//			// 요청 실패 시, 요청 여부 rollback 처리 -------------------------------
//			stsVo.setPrdtReqYn("N");
//			nedmpro0400Dao.updateNewPropStoreReqYn(stsVo);
//		    //---------------------------------------------------------------
//			resultMap.put("msg", "ESG_FILE_SEND_FAIL");
//			resultMap.put("errMsg", MapUtils.getString(esgFileSendMap, "errMsg"));
//			return resultMap;
//		}
		
		/*
		 * 3. RFC Call
		 */
		//1) rfc call 데이터 구성
		JSONObject obj = new JSONObject();
		obj.put("TPC_PROD_NEW_PROP", totalRfcPropStoreList);
		logger.debug("최종 RFC 요청 데이터: " + obj.toString());
		
		//인터페이스 구분 
		String proxyNm = "MST2130";
		logger.info("PO CALL Start :::  ---------------------------");
		
		//2) rfc call
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, obj.toString(), workId);
		
		//3) 응답 처리
		JSONObject mapObj = new JSONObject(rfcMap.toString());
		JSONObject resultObj = mapObj.getJSONObject("result");
		
		if(resultObj == null) {
			// 요청 실패 시, 요청 여부 rollback 처리 --------------------------------
			stsVo.setPrdtReqYn("N");
			nedmpro0400Dao.updateNewPropStoreReqYn(stsVo);
		    //---------------------------------------------------------------
		    
		    resultMap.put("msg", "NO_RESULT");
			resultMap.put("errMsg", "응답 결과가 없습니다.");
			return resultMap;
		}
		
		String resultCd = resultObj.has("MSGTYP")? resultObj.getString("MSGTYP"):"";
		String resultMsg = resultObj.has("MESSAGE")? resultObj.getString("MESSAGE"):"";
		
		logger.info("if resultCd : "+resultCd);
		logger.info("if resultMsg : "+resultMsg);
		
		//실패 시, 요청여부 rollback 처리
		if(!"S".equals(resultCd)) {
			// 요청 실패 시, 요청 여부 rollback 처리 --------------------------------
			stsVo.setPrdtReqYn("N");
			nedmpro0400Dao.updateNewPropStoreReqYn(stsVo);
//		    for (NEDMPRO0400VO paramVo : paramVoList) {
//		        paramVo.setModId(workId);
//		        paramVo.setPrdtReqYn("N");
//		        try {
//		            nedmpro0400Dao.updateNewPropStoreReqYn(paramVo);
//		        } catch (Exception rollbackEx) {
//		            logger.error("rollback 실패 --> " + rollbackEx.getMessage());
//		        }
//		    }
		    //---------------------------------------------------------------
		    
		    resultMap.put("msg", resultCd);
			resultMap.put("errMsg", resultMsg);
			return resultMap;
		}
		
		//성공 시, 결과값 셋팅
		resultMap.put("msg", "SUCCESS");
	    return resultMap;
	}
	
	/**
	 * 조회된 팀의 대분류 조회 
	 */
	public List<CommonProductVO> selectTempL1List(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return nedmpro0400Dao.selectTempL1List(paramMap);
	}
	
	
	
	/**
	 * 조회된 팀의 대분류조회 한번에 다건 조회
	 */
	public List<CommonProductVO> selectTempL1List2(CommonProductVO paramMap) throws Exception {
	    List<CommonProductVO> paramList = paramMap.getTeamL1CdList();
	    List<CommonProductVO> resultList = new ArrayList<>();

	    for (CommonProductVO singleParam : paramList) {
	        CommonProductVO newParam = new CommonProductVO();
	        newParam.setGroupCode(singleParam.getGroupCode());
	        newParam.setL1Cd(singleParam.getL1Cd());

	        List<CommonProductVO> partialResult = nedmpro0400Dao.selectTempL1List2(newParam);
	        if (partialResult != null) {
	            for (CommonProductVO vo : partialResult) {
	                vo.setRnum(singleParam.getRnum()); // rnum 유지
	                vo.setTeamCd(singleParam.getGroupCode());
	            }
	            resultList.addAll(partialResult);
	        }
	    }

	    return resultList;
	}
	
	/**
	 * 신상품 입점제안 엑셀 리스트 조회 
	 */
	public List<HashMap<String, String>> selectNewPropStoreExcelInfo(NEDMPRO0400VO paramVo) throws Exception {
		String fromDt = paramVo.getSrchFromDt(); // "2025-04-15"
		String endDt = paramVo.getSrchEndDt(); // "2025-04-15"
		if(fromDt != null) {
			String formattedDt = fromDt.replaceAll("-", ""); // "20250415"
			
			paramVo.setSrchFromDt(formattedDt);
		}
		if(endDt != null) {
			String formattedDt = endDt.replaceAll("-", ""); // "20250415"
			
			paramVo.setSrchEndDt(formattedDt);
		}
	
    List<HashMap<String, String>> returnList = nedmpro0400Dao.selectNewPropStoreExcelInfo(paramVo);
   
    return returnList;
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
	
	/**
	 * 상품제안 채널 조회 (selectbox 구성용)
	 */
	@Override
	public List<Map<String, Object>> selectTpcNewPropChanCodes(Map<String, Object> paramMap) throws Exception {
		return nedmpro0400Dao.selectTpcNewPropChanCodes(paramMap);
	}
	
	/**
	 * 소속회사의 업체코드별 사용 가능한 제안 채널정보 구성
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	private Map<String, Object> getMayVenZzorgsMap(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		String[] myVenCds = epcLoginVO.getVendorId();	//소속회사 업체코드 
		String[] myZzorgsAll = epcLoginVO.getZzorg();	//소속회사 거래계열사
		
		List<String> propZzorgList	= Arrays.asList(PROP_ZZORGS); 		//해당 업무에서 사용가능한 거래계열사
		boolean isAll = false;		//해당 업무에서 사용가능한 모든 거래계열사를 해당 파트너사가 사용할 수 있는지
		
		
		String[] venZzorgs = null;				//업체코드별 연관계열사 array
		List<String> venZzorgList = null;		//업체코드별 연관계열사 array -> list
		
		Map<String, Object> codeParamMap = null;	//협력사별 사용가능한 채널코드 추출을 위한 parameterMap
		
		//업체코드 없을 경우 빈 map 반환 
		if(myVenCds == null || myVenCds.length == 0) {
			return resultMap;
		}
		
		String venCd = "";		//소속회사의 업체코드
		String zzorg = "";		//업체코드의 연관계열사 (,로구분)
		
		List<Map<String,Object>> venSellCdCodeList = null;		//업체코드로 사용가능한 제안 채널 코드 리스트
		for(int i = 0; i < myVenCds.length; i ++) {
			venCd = StringUtils.deleteWhitespace(myVenCds[i]);		//소속회사의 업체코드
			zzorg = StringUtils.deleteWhitespace(myZzorgsAll[i]);	//업체코드의 연관계열사 (,로구분)
			
			if(venCd == null || "".equals(venCd)) continue;
			if(zzorg == null || "".equals(zzorg)) continue;
			
			//해당 업체코드의 연관계열사 array
			venZzorgs = zzorg.split(",");
			venZzorgList = new ArrayList<>(Arrays.asList(venZzorgs));
			
			//해당 업체코드에 업무단에서 사용할 수 있는 모든 계열사가 포함되어 있는지 확인
			isAll = venZzorgList.containsAll(propZzorgList);
			if(isAll) venZzorgList.add("ALL");	//모두 포함되어 있을 경우, ALL값 추가
			
			//해당 업체코드로 사용 가능한 제안 채널 코드 조회
			codeParamMap = new HashMap<String, Object>();
			codeParamMap.put("ZZORGS", venZzorgList); //사용가능한 계열사정보만 셋팅
			venSellCdCodeList = nedmpro0400Dao.selectTpcNewPropChanCodes(codeParamMap);
			
			//업체코드-채널코드리스트 구조로 map에 추가
			resultMap.put(venCd, venSellCdCodeList);	
		}
		
		return resultMap;			
	}
	
	/**
	 * 상품제안 파일 STFP 전송 (EPC to PO)
	 */
	public Map<String, Object> selectTpcProdNewPropFileForSend(Map<String,Object> paramMap) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "SUCCESS");
		
		//등록된 파일 리스트 조회
		List<Map<String,Object>> filelist = nedmpro0400Dao.selectTpcProdNewPropFileForSend(paramMap);
		
		//등록된 파일 리스트 없을 경우 pass
		if(filelist == null || filelist.isEmpty()) {
			return resultMap;
		}
		
		String filePath = "";			//파일업로드경로
		String saveFileNm = "";			//저장파일명
		String orgFileNm = "";			//원본파일명
		String fileFullPath = "";		//파일 Full 경로
		
		String propRegId = "";			//제안요청번호
		String imgId = "";				//이미지아이디
		
		String errMsg = "";				//에러메세지
		
		//원격 SFTP 서버 파일업로드 경로 
		String remoteFilePath = ConfigUtils.getString("epc.sftp.po.path.prodProp.ing");			//업로드 미완료 파일 SFTP 서버 경로
		String remoteFileMoveToPath = ConfigUtils.getString("epc.sftp.po.path.prodProp.cfm");	//업로드 완료 파일 SFTP 서버 경로
		
		Map<String, Object> sftpMap = null;
		String sftpMsgCd = "";			//SFTP 전송결과 코드
		String sftpMessage = "";		//SFTP 전송결과 메세지
		
		//파일전송 결과 업데이트용 vo
		NEDMPRO0400VO fileSendVo = new NEDMPRO0400VO();
		
		//파일 SFTP 전송
		for(Map<String,Object> fileMap : filelist) {
			filePath = MapUtils.getString(fileMap, "FILE_PATH", "");		//파일업로드 경로
			saveFileNm = MapUtils.getString(fileMap, "SAVE_FILE_NM", "");	//저장파일명
			orgFileNm = MapUtils.getString(fileMap, "ORG_FILE_NM", "");		//원본파일명
			filePath = MapUtils.getString(fileMap, "FILE_PATH", "");		//파일업로드 경로
			imgId = MapUtils.getString(fileMap, "IMG_ID", "");				//이미지아이디
			propRegId = MapUtils.getString(fileMap, "PROP_REG_NO", "");		//제안번호
			
			//저장된 파일 Full 경로
			fileFullPath = filePath + "/" + saveFileNm;
			
			//SFTP 파일업로드 (성공 시 파일 경로 이동)
			sftpMap = epcSftpUtil.uploadMoveAf("PO", fileFullPath, remoteFilePath, saveFileNm, remoteFileMoveToPath);
			
			sftpMsgCd = MapUtils.getString(sftpMap, "msgCd");		//SFTP 전송결과 코드
			sftpMessage = MapUtils.getString(sftpMap, "message");	//SFTP 전송결과 메세지
			
			//업로드 실패 시, return
			if(!"S".equals(sftpMsgCd)) {
				errMsg = String.format("(propRegId:%s, imgId:%s", propRegId, imgId);
				logger.error(sftpMessage+errMsg);
				
				errMsg = String.format("(제안번호:%s, 원본파일명:%s", propRegId, orgFileNm);
				
				resultMap.put("msg", "FAIL");
				resultMap.put("errMsg", "상품제안 이미지 전송에 실패하였습니다."+errMsg);
				return resultMap;
			}
			
			//파일 SFTP 전송 정보 UPDATE
			fileSendVo.setImgId(imgId);	//이미지아이디
			fileSendVo.setSendPath(MapUtils.getString(sftpMap, "uploadPath"));	//SFTP 서버 내 업로드 경루
			fileSendVo.setSendDate(MapUtils.getString(sftpMap, "sendDate"));	//SFTP 서버 업로드 일시
			nedmpro0400Dao.updateTpcProdNewProdImgSendInfo(fileSendVo);
		}
		
		return resultMap;
	}
	
}
