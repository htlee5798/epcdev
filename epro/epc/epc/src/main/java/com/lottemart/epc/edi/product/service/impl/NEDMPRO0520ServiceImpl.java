package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.EpcSftpUtil;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0520Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0520VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0520Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : NEDMPRO0520ServiceImpl.java
 * @Description : ESG 인증관리 ServiceImpl
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.28		yun				최초생성
 *               </pre>
 */
@Service("nEDMPRO0520Service")
public class NEDMPRO0520ServiceImpl implements NEDMPRO0520Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0520ServiceImpl.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0520Dao")
	private NEDMPRO0520Dao nEDMPRO0520Dao;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired
	EpcSftpUtil epcSftpUtil;

	/**
	 * ESG 인증리스트 조회
	 */
	@Override
	public Map<String, Object> selectProdEsgList(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		nEDMPRO0520VO.setVenCds(myVenCds);
		
		//ESG 인증리스트
		List<NEDMPRO0520VO> itemList = nEDMPRO0520Dao.selectProdEsgList(nEDMPRO0520VO);
		
		result.put("itemList", itemList);

		return result;
	}

	/**
	 * ESG 인증정보 변경 저장
	 */
	@Override
	public Map<String, Object> updateProdEsgInfo(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		/*
		 * 1. 필수 데이터 확인
		 */
		//변경대상 data
		List<NEDMPRO0520VO> prodDataArr = nEDMPRO0520VO.getProdDataArr();
		
		//변경대상 list 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "ESG 인증정보 변경 대상 상품을 1개 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. data validation
		 */
		String prodCd = "";			//상품코드
		String esgGbn = "";			//유형코드
		String esgAuth = "";		//인증유형
		String esgAuthDtl = "";		//인증유형상세
		String esgAfFrDt = "";		//인증시작일(After)
		String esgAfToDt = "";		//인증종료일(After)
		String esgDtFg = "";		//ESG 인증시작/종료일 필수 Flag
		String esgCfmFg = "";		//ESG 확정상태
		
		String errMsg = "";
		String chkEsgDate = "";
		
		
		NEDMPRO0520VO dtlVo = null;
		for(NEDMPRO0520VO vo : prodDataArr) {
			prodCd = StringUtils.defaultString(vo.getProdCd());			//상품코드
			esgGbn = StringUtils.defaultString(vo.getEsgGbn());			//유형코드
			esgAuth = StringUtils.defaultString(vo.getEsgAuth());		//인증유형
			esgAuthDtl = StringUtils.defaultString(vo.getEsgAuthDtl());	//인증유형상세
			esgAfFrDt = StringUtils.defaultString(vo.getEsgAfFrDt());	//인증시작일(After)
			esgAfToDt = StringUtils.defaultString(vo.getEsgAfToDt());	//인증종료일(After)
			
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드가 존재하지 않습니다.");
				return result;
			}
			//유형코드 누락
			if("".equals(esgGbn)) {
				result.put("errMsg", "ESG 유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형 누락
			if("".equals(esgAuth)) {
				result.put("errMsg", "ESG 인증유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형상세 누락
			if("".equals(esgAuthDtl)) {
				result.put("errMsg", "ESG 인증상세유형이 존재하지 않습니다.");
				return result;
			}
			
			//상세정보조회
			dtlVo = nEDMPRO0520Dao.selectProdEsgDetail(vo);
			if(dtlVo == null) {
				result.put("errMsg", "상품 ESG 인증정보(Before)가 존재하지 않아 저장이 불가능합니다.");
				return result;
			}
			
			//before 인증종료일정보 없음
//			if("".equals(StringUtils.defaultString(dtlVo.getEsgToDt()))) {
//				result.put("errMsg", "상품 ESG 인증종료일 정보(Before)가 존재하지 않아 인증 갱신이 불가능합니다.");
//				return result;
//			}
			
			//ESG 기간 입력 필수 flag
			esgDtFg = StringUtils.defaultString(dtlVo.getEsgDtFg());
			//ESG 확정상태
			esgCfmFg = StringUtils.defaultString(dtlVo.getEsgCfmFg(), "9");
			
			//승인요청 상태일 경우, 수정불가
			if("0".equals(esgCfmFg)) {
				result.put("errMsg", "ESG 인증정보 변경 승인대기 중입니다.");
				return result;
			}
			
			//ESG 기간 입력이 필수인 항목일 경우,
			if("X".equals(esgDtFg)) {
				//인증시작일(After) 누락
				if("".equals(esgAfFrDt)) {
					result.put("errMsg", "ESG 인증시작일(After)는 필수입력 항목입니다.");
					return result;
				}
				//인증종료일(After) 누락
				if("".equals(esgAfToDt)) {
					result.put("errMsg", "ESG 인증종료일(After)는 필수입력 항목입니다.");
					return result;
				}
				
				//ESG 날짜 확인
				chkEsgDate = getEsgDateChk(StringUtils.defaultString(dtlVo.getEsgToDt()), esgAfFrDt, esgAfToDt);
				switch(chkEsgDate) {
					case "OK": break;
					case "BF_TO_GREATER_THAN_AF_FR":
						result.put("errMsg", "인증시작일(After)은 인증종료일(Before) 이후여야 합니다.");
						return result;
					case "AF_FR_GREATER_THAN_AF_TO":
						result.put("errMsg", "인증시작일(After)이 인증종료일(After)보다 이후입니다.");
						return result;
					case "ERR":
					default:
						result.put("errMsg", "처리 중 오류가 발생했습니다.");
						return result;
				}
			}
			
		}
		
		/*
		 * 3. Update Data 
		 */
		int tot = prodDataArr.size();
		int ok = 0;
		
		Map<String,Object> fileResult = null;
		for(NEDMPRO0520VO saveVo : prodDataArr) {
			saveVo.setEsgCfmFg("9");	//임시저장상태
			saveVo.setModId(workId);
			
			//임시파일 삭제 후 파일저장
			fileResult = this.moveEsgFileTmpToReal(saveVo, request);
			if(!"success".equals(fileResult.get("msg"))) {
				logger.error("파일 복사 중 오류가 발생했습니다.\n첨부파일아이디:"+saveVo.getEsgFileId());
			}
			
			ok += nEDMPRO0520Dao.updateProdEsgInfo(saveVo);
		}
		
		result.put("msg", "success");
		return result;
	}
	
	/**
	 * ESG 인증일자 체크
	 * @param esgBfToDt
	 * @param esgAfFrDt
	 * @param esgAfToDt
	 * @return String
	 */
	private String getEsgDateChk(String esgBfToDt, String esgAfFrDt, String esgAfToDt) {
		try {
			
			Date esgBfToDate = ("".equals(StringUtils.defaultString(esgBfToDt)))?null:DateUtil.string2Date(esgBfToDt, "yyyyMMdd");	//before 인증종료일
			Date esgAfFrDate = ("".equals(StringUtils.defaultString(esgAfFrDt)))?null:DateUtil.string2Date(esgAfFrDt, "yyyyMMdd");	//after 인증시작일
			Date esgAfToDate = ("".equals(StringUtils.defaultString(esgAfToDt)))?null:DateUtil.string2Date(esgAfToDt, "yyyyMMdd"); //after 인증종료일
			
			//before 인증종료일이 있을 경우에만 체크
			if(esgBfToDate != null) {
				//after 인증 시작일은 before 인증 종료일 이후여야 함
				if(!esgAfFrDate.after(esgBfToDate)) {
					return "BF_TO_GREATER_THAN_AF_FR";
				}	
			}
			
			//after 인증 시작일이 after 인증 종료일 이후일 수 없음
			if(esgAfFrDate.after(esgAfToDate)) {
				return "AF_FR_GREATER_THAN_AF_TO";
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "ERR";
		}
		
		return "OK";
	}
	
	/**
	 * ESG 인증서첨부 (단건)
	 */
	public Map<String,Object> updateProdEsgFileInfo(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		//파일확장자 제한
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.product.esg");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit); 
		
		/*
		 * 1. 필수데이터 확인
		 */
		MultipartFile mFile = nEDMPRO0520VO.getEsgFile();		//ESG 첨부파일
		//첨부파일누락
		if(mFile == null) {
			result.put("errMsg", "업로드 파일 정보가 존재하지 않습니다.");
			return result;
		}
		
		//상품코드누락
		String prodCd = StringUtils.defaultString(nEDMPRO0520VO.getProdCd());	//상품코드
		if("".equals(prodCd)) {
			result.put("errMsg", "상품코드가 존재하지 않습니다.");
			return result;
		}
		
		String esgGbn = StringUtils.defaultString(nEDMPRO0520VO.getEsgGbn());	//ESG유형
		String esgAuth = StringUtils.defaultString(nEDMPRO0520VO.getEsgAuth());	//ESG인증유형
		String esgAuthDtl = StringUtils.defaultString(nEDMPRO0520VO.getEsgAuthDtl());	//ESG인증상세유형
		
		
		String tempYn = StringUtils.defaultString(nEDMPRO0520VO.getTempYn(), "N");	//임시파일여부
		
		/*
		 * 2. 파일업로드 진행
		 */
		String uploadFilePath = ConfigUtils.getString("edi.esg.file.path");		//ESG 파일업로드 ROOT PATH
		
		// 파일명이 중복되었을 경우, 사용할 스트링 객체
        String keyId = "";			// 첨부파일 ID
        String orgFileNm = ""; 		// 원본 파일명
        String fileExt = "";		// 확장자
        String saveFileNm = "";		// 저장될 파일명
        String saveFilePath = "";	// 저장될 경로
        String fullPath = "";		// 저장경로 + 파일명
        String fileSize = "";		// 파일 사이즈
        long fileSizeLong = 0;		// 첨부파일 사이즈 (long)
        
        keyId = StringUtils.defaultString(nEDMPRO0520VO.getEsgFileId());			//ESG 첨부파일 아이디
        
        /*** 서버에 등록된 파일 삭제 ***/
    	//기등록 첨부파일 조회
    	NEDMPRO0520VO oldFileVo = nEDMPRO0520Dao.selectEsgFileInfo(nEDMPRO0520VO);
    	
    	//기등록 첨부파일 정보 존재 시,
    	if(oldFileVo != null) {
    		String oldFilePath = oldFileVo.getFilePath();		//기등록 파일업로드경로
    		String oldSaveFileNm = oldFileVo.getSaveFileNm();	//기등록 파일저장명
    		
    		//기등록파일 존재시, 파일 삭제
    		synchronized (this) {
    		    try {
    		        File oldSaveFile = new File(oldFilePath+oldSaveFileNm);
                    if(oldSaveFile.exists()) {
                        if (!oldSaveFile.delete()) {
                            throw new IOException("파일 삭제 실패 : " + oldSaveFile.getPath());
                        }
                    }
    		    } catch (IOException e) {
    		        logger.error(e.getMessage());
    		        throw e;
    		    }
            }
    	}
    	/*************************/
        
		//기등록 파일아이디가 없거나, 임시파일일 경우, 새로생성
		if("".equals(keyId)||"Y".equals(tempYn)) {
			keyId = "ESG" + getTodayFormatYymmdd() + System.currentTimeMillis() + getRandomKey(2);
		}
		
		orgFileNm = StringUtils.defaultString(mFile.getOriginalFilename());		//원본파일명
		orgFileNm = orgFileNm.replaceAll("%00", "").replaceAll("%zz", "");		//원본파일명-종단문자제거
		
		fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".")+1);			//파일확장자명
		//임시파일 업로드경로 분기
		if("Y".equals(tempYn)) {
			saveFilePath = uploadFilePath + File.separator + prodCd + File.separator + "TEMP" + File.separator;
			saveFileNm = prodCd+"_"+esgGbn+"_"+esgAuth+"_"+esgAuthDtl+"."+fileExt;	//저장파일명
		}else {
			saveFilePath = uploadFilePath + File.separator + prodCd + File.separator;	//업로드 파일 path 
//			saveFileNm =  keyId+"."+fileExt;		//저장파일명
			saveFileNm = prodCd+"_"+esgGbn+"_"+esgAuth+"_"+esgAuthDtl+"."+fileExt;	//저장파일명
		}
		
		fileSizeLong = mFile.getSize();	 				//파일사이즈(long)
    	fileSize = Long.toString(fileSizeLong);			//파일사이즈(string)
    	
    	//파일 사이즈 없을 경우 exception
    	if(fileSizeLong == 0) {
    		throw new IllegalArgumentException("파일 내용이 없습니다.\n파일명:"+orgFileNm);
    	}
    	
    	// 확장자 체크
    	if(!extLimit.contains(fileExt.toLowerCase())) {
    		throw new IllegalArgumentException("업로드 불가능한 확장자입니다.\n파일명:"+orgFileNm);
    	}
		
		// filePath에 해당되는 파일의 File 객체를 생성한다.
        File fileFolder = new File(saveFilePath);

        if (!fileFolder.exists()) {
            // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
            if (fileFolder.mkdirs()) {
                logger.info("[file.mkdirs] : Success");
            } else {
                logger.error("[file.mkdirs] : Fail");
            }
        }
		
        //업로드 전체경로
        fullPath = saveFilePath + saveFileNm;
		
        //파일업로드
        FileOutputStream fileStream = null;
        try {
        	fileStream = new FileOutputStream(fullPath);
        	FileCopyUtils.copy(mFile.getInputStream(), fileStream);
        }catch(Exception e) {
        	File delFile	=	new File(fullPath);
        	if(delFile.exists()) delFile.delete();
        	throw new IllegalArgumentException("파일 업로드 중 오류가 발생했습니다.\n파일명:"+orgFileNm);
        }finally {
        	if(fileStream != null) fileStream.close();
        	logger.info("orgFileNm:"+orgFileNm+", fileSize:"+fileSize);
        }
        
        //파일정보 setting
        nEDMPRO0520VO.setEsgFileId(keyId);			//첨부파일아이디
        nEDMPRO0520VO.setOrgFileNm(orgFileNm);		//원본파일명
        nEDMPRO0520VO.setSaveFileNm(saveFileNm);	//저장파일명
        nEDMPRO0520VO.setFileSize(fileSize);		//파일사이즈
        nEDMPRO0520VO.setFileExt(fileExt);			//파일확장자명
        nEDMPRO0520VO.setFilePath(saveFilePath);	//파일경로
        
        //작업자 정보 setting
        nEDMPRO0520VO.setRegId(workId);
        nEDMPRO0520VO.setModId(workId);
    	
        //파일정보 merge
        nEDMPRO0520Dao.mergeProdEsgFile(nEDMPRO0520VO);
		
        result.put("msg", "success");
        result.put("esgFileId", keyId);		//첨부파일아이디 반환
		return result;
	}
	
	/**
	 * ESG 인증정보 변경 (파일 업로드 동시에 진행)
	 */
	@Override
	public Map<String, Object> updateProdEsgInfoWithFile(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		/*
		 * 1. 필수 데이터 확인
		 */
		//변경대상 data
		List<NEDMPRO0520VO> prodDataArr = nEDMPRO0520VO.getProdDataArr();
		
		//변경대상 list 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "ESG 인증정보 변경 대상 상품을 1개 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. data validation
		 */
		String prodCd = "";			//상품코드
		String esgGbn = "";			//유형코드
		String esgAuth = "";		//인증유형
		String esgAuthDtl = "";		//인증유형상세
		String esgAfFrDt = "";		//인증시작일(After)
		String esgAfToDt = "";		//인증종료일(After)
		String esgDtFg = "";		//ESG 인증시작/종료일 필수 Flag
		String esgCfmFg = "";		//ESG 확정상태
		
		String errMsg = "";
		String chkEsgDate = "";
		
		
		NEDMPRO0520VO dtlVo = null;
		for(NEDMPRO0520VO vo : prodDataArr) {
			prodCd = StringUtils.defaultString(vo.getProdCd());			//상품코드
			esgGbn = StringUtils.defaultString(vo.getEsgGbn());			//유형코드
			esgAuth = StringUtils.defaultString(vo.getEsgAuth());		//인증유형
			esgAuthDtl = StringUtils.defaultString(vo.getEsgAuthDtl());	//인증유형상세
			esgAfFrDt = StringUtils.defaultString(vo.getEsgAfFrDt());	//인증시작일(After)
			esgAfToDt = StringUtils.defaultString(vo.getEsgAfToDt());	//인증종료일(After)
			
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드가 존재하지 않습니다.");
				return result;
			}
			//유형코드 누락
			if("".equals(esgGbn)) {
				result.put("errMsg", "ESG 유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형 누락
			if("".equals(esgAuth)) {
				result.put("errMsg", "ESG 인증유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형상세 누락
			if("".equals(esgAuthDtl)) {
				result.put("errMsg", "ESG 인증상세유형이 존재하지 않습니다.");
				return result;
			}
			
			//상세정보조회
			dtlVo = nEDMPRO0520Dao.selectProdEsgDetail(vo);
			if(dtlVo == null) {
				result.put("errMsg", "상품 ESG 인증정보(Before)가 존재하지 않아 저장이 불가능합니다.");
				return result;
			}
			
			//before 인증종료일정보 없음
//			if("".equals(StringUtils.defaultString(dtlVo.getEsgToDt()))) {
//				result.put("errMsg", "상품 ESG 인증종료일 정보(Before)가 존재하지 않아 인증 갱신이 불가능합니다.");
//				return result;
//			}
			
			//ESG 기간 입력 필수 flag
			esgDtFg = StringUtils.defaultString(dtlVo.getEsgDtFg());
			//ESG 확정상태
			esgCfmFg = StringUtils.defaultString(dtlVo.getEsgCfmFg(), "9");
			
			//승인요청 상태일 경우, 수정불가
			if("0".equals(esgCfmFg)) {
				result.put("errMsg", "ESG 인증정보 변경 승인대기 중입니다.");
				return result;
			}
			
			//ESG 기간 입력이 필수인 항목일 경우,
			if("X".equals(esgDtFg)) {
				//인증시작일(After) 누락
				if("".equals(esgAfFrDt)) {
					result.put("errMsg", "ESG 인증시작일(After)는 필수입력 항목입니다.");
					return result;
				}
				//인증종료일(After) 누락
				if("".equals(esgAfToDt)) {
					result.put("errMsg", "ESG 인증종료일(After)는 필수입력 항목입니다.");
					return result;
				}
				
				//ESG 날짜 확인
				chkEsgDate = getEsgDateChk(StringUtils.defaultString(dtlVo.getEsgToDt()), esgAfFrDt, esgAfToDt);
				switch(chkEsgDate) {
					case "OK": break;
					case "BF_TO_GREATER_THAN_AF_FR":
						result.put("errMsg", "인증시작일(After)은 인증종료일(Before) 이후여야 합니다.");
						return result;
					case "AF_FR_GREATER_THAN_AF_TO":
						result.put("errMsg", "인증시작일(After)이 인증종료일(After)보다 이후입니다.");
						return result;
					case "ERR":
					default:
						result.put("errMsg", "처리 중 오류가 발생했습니다.");
						return result;
				}
			}
			
		}
		
		/*
		 * 3. Update Data 
		 */
		MultipartFile mFile = null;
		int tot = prodDataArr.size();
		int ok = 0;
		
		Map<String,Object> fileResult = null;
		for(NEDMPRO0520VO saveVo : prodDataArr) {
			saveVo.setEsgCfmFg("9");	//임시저장상태
			saveVo.setModId(workId);
			
			/** 첨부파일 저장 **/
			mFile = saveVo.getEsgFile();
			if(mFile != null) {
				fileResult = updateProdEsgFileInfo(saveVo, request);
				if(!"success".equals(fileResult.get("msg"))) {
					result.put("errMsg", "파일 업로드 중 오류가 발생했습니다.");
					return result;
				}
				
				//esgFileId 셋팅
				saveVo.setEsgFileId(MapUtils.getString(fileResult, "esgFileId"));
			}
			/***************/
			
			ok += nEDMPRO0520Dao.updateProdEsgInfo(saveVo);
		}
		
		result.put("msg", "success");
		return result;
	}
	
	private String getTodayFormatYymmdd() throws Exception {
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		dateFormat.format(now, sb, new FieldPosition(0));
		return sb.toString();
	}
	
	private static String getRandomKey(int length) {
		String strReturn = "";
		if (length > 0) {
			strReturn = RandomStringUtils.random(length, 48, 91, true, true);
		}

	    return strReturn;
	 }
	
	/**
	 * ESG 첨부파일 임시 파일 copy 후 삭제
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	private Map<String, Object> moveEsgFileTmpToReal(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String esgFileId = StringUtils.defaultString(nEDMPRO0520VO.getEsgFileId());	//첨부파일아이디
		String prodCd = StringUtils.defaultString(nEDMPRO0520VO.getProdCd());		//상품코드
		
		String esgGbn = StringUtils.defaultString(nEDMPRO0520VO.getEsgGbn());			//ESG유형
		String esgAuth = StringUtils.defaultString(nEDMPRO0520VO.getEsgAuth());			//ESG인증유형
		String esgAuthDtl = StringUtils.defaultString(nEDMPRO0520VO.getEsgAuthDtl());	//ESG인증상세유형
		
		//파일업로드 정보 조회
		NEDMPRO0520VO fileInfo = nEDMPRO0520Dao.selectEsgFileInfo(nEDMPRO0520VO);
		if(fileInfo == null) {
			result.put("errMsg", "파일 업로드 정보가 없습니다.");
			return result;
		}
		
		String fileExt = StringUtils.defaultString(fileInfo.getFileExt());			//파일확장자명
		String tmpSaveFileNm = StringUtils.defaultString(fileInfo.getSaveFileNm());	//임시저장파일명
		
//		String saveFileNm = esgFileId+"."+fileExt;	//확정저장파일명
		String saveFileNm = prodCd+"_"+esgGbn+"_"+esgAuth+"_"+esgAuthDtl+"."+fileExt;	//확정저장파일명
		
		String uploadFilePath = ConfigUtils.getString("edi.esg.file.path");		//ESG 파일업로드 ROOT PATH
		
		String tempFilePath = uploadFilePath + File.separator + prodCd + File.separator + "TEMP" + File.separator; 
		String tempFileFullPath = tempFilePath + tmpSaveFileNm;
		String realFilePath = uploadFilePath + File.separator+prodCd+File.separator;	//업로드 파일 path
		String realFileFullPath = realFilePath + saveFileNm;
		
		File tmpFile = new File(tempFileFullPath);
		File realFileDir = new File(realFilePath);
		File realFile = new File(realFileFullPath);
		
		if(tmpFile.exists()) {
			try {
				if(!realFileDir.exists()) {
					realFileDir.mkdirs();
				}
				
				FileCopyUtils.copy(tmpFile, realFile);
				if (tmpFile.isFile()) {
					tmpFile.delete();
				}
			}catch(Exception e) {
				logger.error(e.getMessage());
				result.put("errMsg", "임시파일 복사 실패");
				return result;
			}
		}
		
		long fileSizeLong = 0;
		
		if(realFile.exists()) {
			fileSizeLong =  realFile.length();
			fileInfo.setSaveFileNm(saveFileNm);
			fileInfo.setFileSize(Long.toString(fileSizeLong));
			fileInfo.setFilePath(realFilePath);
			
			fileInfo.setRegId(workId);
			fileInfo.setModId(workId);
			
			//파일정보갱신
			nEDMPRO0520Dao.mergeProdEsgFile(fileInfo);
			
			//ESG 인증정보 파일아이디 업데이트
			fileInfo.setProdCd(prodCd);			//상품코드
			fileInfo.setEsgGbn(esgGbn);			//ESG유형
			fileInfo.setEsgAuth(esgAuth);		//ESG인증유형
			fileInfo.setEsgAuthDtl(esgAuthDtl);	//ESG인증상세유형
			nEDMPRO0520Dao.updateProdEsgFileId(fileInfo);
		}
		
		result.put("msg", "success");
		return result;
	}

	/**
	 * ESG 인증정보 변경 요청 (Proxy 전송)
	 */
	@Override
	public Map<String, Object> updateProdEsgSendProxy(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		/*
		 * 1. 필수 데이터 확인
		 */
		//변경대상 data
		List<NEDMPRO0520VO> prodDataArr = nEDMPRO0520VO.getProdDataArr();
		
		//변경대상 list 없음
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "ESG 인증정보 변경 대상 상품을 1개 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. data validation
		 */
		String prodCd = "";			//상품코드
		String esgGbn = "";			//유형코드
		String esgAuth = "";		//인증유형
		String esgAuthDtl = "";		//인증유형상세
		String esgAfFrDt = "";		//인증시작일(After)
		String esgAfToDt = "";		//인증종료일(After)
		String esgDtFg = "";		//ESG 인증시작/종료일 필수 Flag
		String esgCfmFg = "";		//ESG 확정상태
		
		String errMsg = "";
		String chkEsgDate = "";
		
		
		NEDMPRO0520VO dtlVo = null;
		for(NEDMPRO0520VO vo : prodDataArr) {
			prodCd = StringUtils.defaultString(vo.getProdCd());			//상품코드
			esgGbn = StringUtils.defaultString(vo.getEsgGbn());			//유형코드
			esgAuth = StringUtils.defaultString(vo.getEsgAuth());		//인증유형
			esgAuthDtl = StringUtils.defaultString(vo.getEsgAuthDtl());	//인증유형상세
			
			//상품코드 누락
			if("".equals(prodCd)) {
				result.put("errMsg", "상품코드가 존재하지 않습니다.");
				return result;
			}
			//유형코드 누락
			if("".equals(esgGbn)) {
				result.put("errMsg", "ESG 유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형 누락
			if("".equals(esgAuth)) {
				result.put("errMsg", "ESG 인증유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형상세 누락
			if("".equals(esgAuthDtl)) {
				result.put("errMsg", "ESG 인증상세유형이 존재하지 않습니다.");
				return result;
			}
			
			//상세정보조회
			dtlVo = nEDMPRO0520Dao.selectProdEsgDetail(vo);
			if(dtlVo == null) {
				result.put("errMsg", "상품 ESG 인증정보가 존재하지 않아 인증정보 변경요청이 불가능합니다.");
				return result;
			}
			
			//ESG 기간 입력 필수 flag
			esgDtFg = StringUtils.defaultString(dtlVo.getEsgDtFg());
			//ESG 확정상태
			esgCfmFg = StringUtils.defaultString(dtlVo.getEsgCfmFg(), "9");
			
			//승인요청 상태일 경우, 재요청 불가
			if("0".equals(esgCfmFg)) {
				result.put("errMsg", "ESG 인증정보 변경 승인대기 중입니다.");
				return result;
			}
			
			//임시저장 상태에서만 저장가능 (승인, 반려상태면 이전 요청건의 상태이므로, 내용 변경해서 저장후 재요청해야됨)
			if(!"9".equals(esgCfmFg)) {
				result.put("errMsg", "ESG 인증정보 저장 후 요청이 가능합니다.");
				return result;
			}
			
			
			//ESG 기간 입력이 필수인 항목일 경우,
			if("X".equals(esgDtFg)) {
				esgAfFrDt = StringUtils.defaultString(dtlVo.getEsgAfFrDt());	//인증시작일(After)
				esgAfToDt = StringUtils.defaultString(dtlVo.getEsgAfToDt());	//인증종료일(After)
				
				//인증시작일(After) 누락
				if("".equals(esgAfFrDt)) {
					result.put("errMsg", "ESG 인증시작일(After)는 필수입력 항목입니다.");
					return result;
				}
				//인증종료일(After) 누락
				if("".equals(esgAfToDt)) {
					result.put("errMsg", "ESG 인증종료일(After)는 필수입력 항목입니다.");
					return result;
				}
				
				//ESG 날짜 확인
				chkEsgDate = getEsgDateChk(StringUtils.defaultString(dtlVo.getEsgToDt()), esgAfFrDt, esgAfToDt);
				switch(chkEsgDate) {
					case "OK": break;
					case "BF_TO_GREATER_THAN_AF_FR":
						result.put("errMsg", "인증시작일(After)은 인증종료일(Before) 이후여야 합니다.");
						return result;
					case "AF_FR_GREATER_THAN_AF_TO":
						result.put("errMsg", "인증시작일(After)이 인증종료일(After)보다 이후입니다.");
						return result;
					case "ERR":
					default:
						result.put("errMsg", "처리 중 오류가 발생했습니다.");
						return result;
				}
			}
			
		}
		
		/*
		 * 3. ESG 파일 있을 경우, PO 전송
		 */
		//TODO_JIA :::::: SFTP 서버 테스트 완료 후 주석 해제 처리 예정
//		Map<String,Object> esgFileSendMap = this.updateSendSftpEsgFile(nEDMPRO0520VO);
//		//전송실패 시,
//		if(!MapUtils.getString(esgFileSendMap, "msg").equals("SUCCESS")) {
//			result.put("msg", "ESG_FILE_SEND_FAIL");
//			result.put("errMsg", MapUtils.getString(esgFileSendMap, "errMsg"));
//			return result;
//		}
		
		/*
		 * 4. PROXY 전송
		 */
		logger.info("PO CALL Start ::: updateProdEsgSendProxy ---------------------------");
		String ifStDt = "", ifEndDt = "";		//인터페이스 시작,종료일시
		//-- 1. 전송대상 LIST 조회 (GRP_ID가 있을 경우, 동일한 GRP_ID 가진 건들도 일괄로 전송함)
		List<HashMap> sendList = nEDMPRO0520Dao.selectProdEsgRfcData(nEDMPRO0520VO); 
		
		if(sendList == null || sendList.size() == 0) {
			result.put("errMsg", "요청정보 구성에 실패하였습니다.");
			return result;
		}
		
		//-- 2. 요청정보 구성
		JSONObject obj = new JSONObject();
		obj.put("PRODUCT_ESG", sendList);		//요청 DATA
		logger.debug("obj.toString=" + obj.toString());
		
		//-- 3. call
		String proxyNm = "MST2240";		//인터페이스 Function 명
		ifStDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 시작일시
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, obj.toString(), workId);
		ifEndDt = DateUtil.getCurrentDay(DateUtil.DATE_HMS_FORMAT);	//인터페이스 종료일시
		
		//-- 4. 응답처리
		JSONObject mapObj = new JSONObject(rfcMap.toString());	//응답 message
		JSONObject resultObj = mapObj.getJSONObject("result");	//응답 메세지 key==result
		
		
		String rsltCd = resultObj.has("MSGTYP")?resultObj.getString("MSGTYP"):"";		//응답결과코드
		String rsltMsg = resultObj.has("MESSAGE")?resultObj.getString("MESSAGE"):"";	//응답결과메세지	- 응답결과코드 S일때는 반환되지 않음
		
		//전송 실패 시,
		if(!"S".equals(rsltCd)) {
			logger.error(rsltMsg);
			errMsg = "변경 요청에 실패하였습니다.(" + rsltMsg +")";
			result.put("errMsg", errMsg);
			return result;
		}
		
		logger.info("if Start : "+ifStDt);
		logger.info("if End : "+ifEndDt);
		logger.info("if ResultCode : "+rsltCd);
		logger.info("if Message : "+rsltMsg);
		logger.info("PO CALL End ::: updateProdEsgSendProxy ---------------------------");
		
		/*
		 * 5. 전송 상태 업데이트
		 */
		nEDMPRO0520VO.setIfDt(ifStDt);		//if 시작일시
		nEDMPRO0520VO.setEsgCfmFg("0");		//확정상태 - 0:요청
		nEDMPRO0520VO.setModId(workId);		//작업자정보
		
		int updOk = nEDMPRO0520Dao.updateProdEsgCfmFg(nEDMPRO0520VO);
		
		//성공 시 결과 메세지 셋팅
		if(updOk > 0) {
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
	
	/**
	 * ESG 파일 STFP 전송 (EPC to PO)
	 */
	public Map<String, Object> updateSendSftpEsgFile(NEDMPRO0520VO paramVo) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "SUCCESS");
		
		//등록된 ESG 파일 리스트 조회
		List<Map<String,Object>> filelist = nEDMPRO0520Dao.selectTpcProdEsgFileForSend(paramVo);
		
		//등록된 ESG 파일 리스트 없을 경우 pass
		if(filelist == null || filelist.isEmpty()) {
			return resultMap;
		}
		
		String filePath = "";			//파일업로드경로
		String saveFileNm = "";			//저장파일명
		String orgFileNm = "";			//원본파일명
		String fileFullPath = "";		//파일 Full 경로
		String esgFileId = "";			//ESG 파일아이디
		
		String prodCd = "";				//상품코드
		String esgGbn = "";				//ESG인증구분
		String esgAuth = "";			//ESG인증유형
		String esgAuthDtl = "";			//ESG인증상세유형
		String esgGbnNm = "";			//ESG인증구분명
		String esgAuthNm = "";			//ESG인증유형명
		String esgAuthDtlNm = "";		//ESG인증상세유형명
		
		//원격 SFTP 서버 파일업로드 경로 
		String remoteFilePath = ConfigUtils.getString("epc.sftp.po.path.esgUpd.ing");			//업로드 미완료 파일 SFTP 서버 경로
		String remoteFileMoveToPath = ConfigUtils.getString("epc.sftp.po.path.esgUpd.cfm");		//업로드 완료 파일 SFTP 서버 경로
		
		Map<String, Object> sftpMap = null;
		String sftpMsgCd = "";			//SFTP 전송결과 코드
		String sftpMessage = "";		//SFTP 전송결과 메세지
		
		String errMsg = "";
		
		//파일전송 결과 업데이트용 vo
		NEDMPRO0520VO fileSendVo = new NEDMPRO0520VO();
		
		//파일 SFTP 전송
		for(Map<String,Object> fileMap : filelist) {
			filePath = MapUtils.getString(fileMap, "FILE_PATH", "");		//파일업로드 경로
			saveFileNm = MapUtils.getString(fileMap, "SAVE_FILE_NM", "");	//저장파일명
			orgFileNm = MapUtils.getString(fileMap, "ORG_FILE_NM", "");		//원본파일명
			esgFileId = MapUtils.getString(fileMap, "ESG_FILE_ID", "");		//파일아이디
			
			esgGbn = MapUtils.getString(fileMap, "ESG_GBN", "");			//ESG인증구분
			esgAuth = MapUtils.getString(fileMap, "ESG_AUTH", "");			//ESG인증유형
			esgAuthDtl = MapUtils.getString(fileMap, "ESG_AUTH_DTL", "");	//ESG인증상세유형
			
			esgGbnNm = MapUtils.getString(fileMap, "ESG_GBN_NM", "");			//ESG인증구분명
			esgAuthNm = MapUtils.getString(fileMap, "ESG_AUTH_NM", "");			//ESG인증유형명
			esgAuthDtlNm = MapUtils.getString(fileMap, "ESG_AUTH_DTL_NM", "");	//ESG인증상세유형명
			
			//저장된 파일 Full 경로
			fileFullPath = filePath + "/" + saveFileNm;
			
			//SFTP 파일업로드 (성공 시 파일 경로 이동)
			sftpMap = epcSftpUtil.uploadMoveAf("PO", fileFullPath, remoteFilePath, saveFileNm, remoteFileMoveToPath);
			
			sftpMsgCd = MapUtils.getString(sftpMap, "msgCd");		//SFTP 전송결과 코드
			sftpMessage = MapUtils.getString(sftpMap, "message");	//SFTP 전송결과 메세지
			
			//업로드 실패 시, return
			if(!"S".equals(sftpMsgCd)) {
				errMsg = String.format("(prodCd:%s, esgGbn:%s, esgAuth:%s, esgAuthDtl:%s, esgFileId:%s", prodCd, esgGbn, esgAuth, esgAuthDtl, esgFileId);
				logger.error(sftpMessage+errMsg);
				
				errMsg = String.format("(상품코드:%s, 인증구분:%s, 인증유형:%s, 인증상세유형:%s, 파일명:%s", prodCd, esgGbnNm, esgAuthNm, esgAuthDtlNm, orgFileNm);
				
				resultMap.put("msg", "FAIL");
				resultMap.put("errMsg", "ESG 파일 전송에 실패하였습니다."+errMsg);
				return resultMap;
			}
			
			//파일 SFTP 전송 정보 UPDATE
			fileSendVo.setEsgFileId(esgFileId);	//ESG 파일아이디
			fileSendVo.setSendPath(MapUtils.getString(sftpMap, "uploadPath"));	//SFTP 서버 내 업로드 경루
			fileSendVo.setSendDate(MapUtils.getString(sftpMap, "sendDate"));	//SFTP 서버 업로드 일시
			nEDMPRO0520Dao.updateTpcProdEsgFileSendInfo(fileSendVo);
		}
		
		return resultMap;
	}

}
