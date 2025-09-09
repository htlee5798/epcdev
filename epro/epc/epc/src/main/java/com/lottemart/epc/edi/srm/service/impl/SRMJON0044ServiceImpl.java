package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.*;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMJON0044VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0030Service;
import com.lottemart.epc.edi.srm.service.SRMJON0044Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.file.FileUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 (해외)
 *
 * @author LEE HYOUNG TAK
 * @since 2016.11.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.11.16  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Service("srmjon0044Service")
public class SRMJON0044ServiceImpl implements SRMJON0044Service {

	@Autowired
	private SRMJON0040Dao srmjon0040Dao;

	@Autowired
	private SRMJON0043Dao srmjon0043Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Autowired
	private SRMJON0044Dao srmjon0044Dao;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;

	@Autowired
	private SRMRST0020Dao srmrst0020Dao;


	@Autowired
	private SRMJON0030Service srmjon0030Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(SRMJON0044ServiceImpl.class);

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 잠재업체 기본정보 조회
	 * @param HttpServletRequest
	 * @param SRMJON0044VO
	 * @return SRMJON0044VO
	 * @throws Exception
	 */
	public SRMJON0044VO selectGlobalHiddenCompInfo(SRMJON0044VO vo, HttpServletRequest request) throws Exception {
		SRMJON0044VO tmpVo = vo;
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);
		tmpVo.setHouseCode(session.getHouseCode());
		tmpVo.setSellerCode(session.getIrsNo());
		// Locale 설정
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));

		if(StringUtil.isEmpty(session.getCountry())){
			tmpVo.setCountry("KR");
		} else {
			tmpVo.setCountry(session.getCountry());
		}
		//잠재업체 기본정보 조회
		if (!StringUtil.isEmpty(tmpVo.getReqSeq())){
			tmpVo = srmjon0044Dao.selectGlobalHiddenCompInfo(tmpVo);
			tmpVo.setCompanyIntroAttachNoFileList(srmrst0020Dao.selectCompCounselFileList(tmpVo.getCompanyIntroAttachNo()));
			return tmpVo;
		} else {
			//기등록 정보가 있는 잠재업체
			SRMJON0044VO basicCompVO = srmjon0044Dao.selectGlobalHiddenCompBasicInfo(tmpVo);
			if (basicCompVO != null) {
				basicCompVO.setChannelCode(tmpVo.getChannelCode());
				basicCompVO.setCatLv1Code(tmpVo.getCatLv1Code());
				basicCompVO.setCatLv1CodeNm(tmpVo.getCatLv1CodeNm());
				basicCompVO.setCompanyIntroAttachNoFileList(srmrst0020Dao.selectCompCounselFileList(basicCompVO.getCompanyIntroAttachNo()));
				return basicCompVO;
			}

			//신규정보 잠재업체
			tmpVo.setShipperType(session.getShipperType());
			tmpVo.setSellerNameLoc(session.getSellerNameLoc());
			tmpVo.setCountry(session.getCountry());
//			vo.setCompanyIntroAttachNoFileList(srmrst0020Dao.selectCompCounselFileList(basicCompVO.getCompanyIntroAttachNo()));
			if (session.getShipperType().equals("1")) {
				tmpVo.setCountry("KR");
			}
			return tmpVo;
		}
	}


	/**
	 * 잠재업체 기본정보 체크
	 * @param SRMJON0044VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public Map<String, Object> selectGlobalHiddenCompInfoCheck(SRMJON0044VO vo, HttpServletRequest request) throws Exception {
		SRMJON0044VO tmpVo = vo;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		tmpVo.setSellerCode(session.getIrsNo());
		tmpVo.setHouseCode(session.getHouseCode());
		tmpVo.setCountry(session.getCountry());

		SRMJON0040VO srmjon0040vo = new SRMJON0040VO();
		srmjon0040vo.setHouseCode(tmpVo.getHouseCode());
		srmjon0040vo.setSellerCode(tmpVo.getSellerCode());
		srmjon0040vo.setChannelCode(tmpVo.getChannelCode());
		srmjon0040vo.setCatLv1Code(tmpVo.getCatLv1Code());
		srmjon0040vo.setReqSeq(tmpVo.getReqSeq());
		srmjon0040vo.setIrsNo(tmpVo.getSellerCode());


		SRMJON0043VO srmjon0043vo = new SRMJON0043VO();
		srmjon0043vo.setCatLv1Code(vo.getCatLv1Code());
		if(srmjon0043Dao.selectCatLv1CodeCheck(srmjon0043vo) == 0){
			resultMap.put("message","FAIL-CAT_LV1_CODE_DEL");
			return resultMap;
		}


		resultMap = srmjon0030Service.consultStatusCheck(srmjon0040vo);	// 채널, 대분류로 동일한 신청내역 체크
		if (!resultMap.get("message").equals("SUCCESS")) {
			return resultMap;
		}

		if(StringUtil.isNotEmpty(tmpVo.getTempPw())){
			//----- 비밀번호 유효성 체크 --------------------
			if (tmpVo.getTempPw().length() < 8) {
				resultMap.put("message", "NOT_LENGTH");
				return resultMap;
			}

			// 공백체크
			if (!SRMCommonUtils.isSpaceCheck(tmpVo.getTempPw())) {
				resultMap.put("message", "EXIST_SPACE");
				return resultMap;
			}

			// 허용된 문자열인지 체크
			if (!SRMCommonUtils.isPermitPasswordCharCheck(tmpVo.getTempPw())) {
				resultMap.put("message", "NOT_PERMIT");
				return resultMap;
			}

			// 중복된 3자 이상의 문자 또는 숫자 사용불가
			/*if (!SRMCommonUtils.isDuplicate3Character(vo.getTempPw())) {
				resultMap.put("message", "EXIST_DUPL");
				return resultMap;
			}*/

			// 문자, 숫자, 특수문자 혼용
			if (!SRMCommonUtils.isPasswordCharCheck(tmpVo.getTempPw())) {
				resultMap.put("message", "NOT_MATCH");
				return resultMap;
			}
			//---------------------------------------------

			tmpVo.setTempPw(SRMCommonUtils.EncryptSHA256(tmpVo.getTempPw()));
		}

		tmpVo = srmjon0044Dao.selectGlobalHiddenCompInfo(tmpVo);
		if(tmpVo != null){
			//채널코드
			if (StringUtil.isEmpty(tmpVo.getChannelCode())) {
				resultMap.put("message", "FAIL-CHANNEL_CODE");
				return resultMap;
			}
			//분류
			if (StringUtil.isEmpty(tmpVo.getCatLv1Code())) {
				resultMap.put("message","FAIL-CAT_LV1_CODE");
				return resultMap;
			}
			//해외업체구분
			if (StringUtil.isEmpty(tmpVo.getShipperType())) {
				resultMap.put("message","FAIL-SHIPPER_TYPE");
				return resultMap;
			}
			//비밀번호
			if (StringUtil.isEmpty(tmpVo.getTempPw())) {
				resultMap.put("message","FAIL-TEMP_PW");
				return resultMap;
			}
			//상호명
			if (StringUtil.isEmpty(tmpVo.getSellerNameLoc())) {
				resultMap.put("message","FAIL-SELLER_NAME_LOC");
				return resultMap;
			}
			//대표자명
			if (StringUtil.isEmpty(tmpVo.getSellerCeoName())) {
				resultMap.put("message","FAIL-SELLER_CEO_NAME");
				return resultMap;
			}
			//담당자명
			if (StringUtil.isEmpty(tmpVo.getvMainName())) {
				resultMap.put("message","FAIL-V_MAIN_NAME");
				return resultMap;
			}
			//대표전화
			if (StringUtil.isEmpty(tmpVo.getvPhone1())) {
				resultMap.put("message","FAIL-V_PHONE1");
				return resultMap;
			}
			//E-mail
			if (StringUtil.isEmpty(tmpVo.getvEmail())) {
				resultMap.put("message","FAIL-V_EMAIL");
				return resultMap;
			}

			//국외 업체일경우
			//주소
			if (StringUtil.isEmpty(tmpVo.getAddress1())) {
				resultMap.put("message","FAIL-ADDRESS");
				return resultMap;
			}

			//설립년도
			if (StringUtil.isEmpty(tmpVo.getFoundationDate())) {
				resultMap.put("message","FAIL-FOUNDATION_DATE");
				return resultMap;
			}
			//자본금
			if (StringUtil.isEmpty(tmpVo.getBasicAmt())) {
				resultMap.put("message","FAIL-BASIC_AMT");
				return resultMap;
			}
			//연간 매출액
			if (StringUtil.isEmpty(tmpVo.getSalesAmt())) {
				resultMap.put("message","FAIL-SALES_AMT");
				return resultMap;
			}
			//종업원수
			if (StringUtil.isEmpty(tmpVo.getEmpCount())) {
				resultMap.put("message","FAIL-EMP_COUNT");
				return resultMap;
			}

			//온라인몰 메일 발송대상
//			if (StringUtil.isEmpty(tmpVo.getOnlineMailTarget()) && "06".equals(tmpVo.getChannelCode())) {
//				resultMap.put("message","FAIL-ONLINE_MAIL_TARGET");
//				return resultMap;
//			}
			resultMap.put("reqSeq",tmpVo.getReqSeq());
		}

		resultMap.put("message","SUCCESS");


		return resultMap;
	}

	/**
	 * 잠재업체 기본정보 저장
	 * @param SRMJON0044VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public Map<String, Object> updateGlobalHiddenCompInfo(SRMJON0044VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String status = vo.getStatus();
		resultMap.put("status", status);

		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(sessionKey);

		vo.setSellerCode(session.getIrsNo());
		vo.setHouseCode(session.getHouseCode());
		vo.setCountry(session.getCountry());


		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		ArrayList<MultipartFile> arrayFile = vo.getCompanyIntroAttachNoFile();

		ArrayList docSeq = vo.getDocSeq();
		List<CommonFileVO> fileList = srmrst0020Dao.selectCompCounselFileList(vo.getCompanyIntroAttachNo());
		for(CommonFileVO fileVo: fileList) {
			if(docSeq != null && docSeq.size() > 0) {
				boolean fileDel = true;
				for(int j=0; j < docSeq.size(); j++){
					if(!StringUtil.isEmpty(fileVo.getFileSeq()) && fileVo.getFileSeq().equals(docSeq.get(j))){
						fileDel = false;
					}
				}
				if(fileDel) {
					//파일 삭제
					srmjon0041Dao.deleteHiddenCompFile(fileVo);

					//서버파일 삭제
					File file = new File(imageUploadPath + "/" + fileVo.getTempFileName());
					if (file.isFile()) {
						FileUtil.delete(file);
					}
				}
			} else {
				//파일 삭제
				srmjon0041Dao.deleteHiddenCompFile(fileVo);

				//서버파일 삭제
				File file = new File(imageUploadPath + "/" + fileVo.getTempFileName());
				if (file.isFile()) {
					FileUtil.delete(file);
				}
			}

		}

		if(arrayFile != null && arrayFile.size() != 0) {
			//파일 저장
			CommonFileVO fileVo = new CommonFileVO();

			String attchFileNo = "";
			if(StringUtil.isEmpty(vo.getCompanyIntroAttachNo())) {
				attchFileNo = String.valueOf(System.currentTimeMillis());
			} else {
				attchFileNo = vo.getCompanyIntroAttachNo();
			}

			for(int i=0; i < arrayFile.size(); i++){
				fileVo = new CommonFileVO();

				if(StringUtil.isNotEmpty(arrayFile.get(i).getOriginalFilename())) {
					String detailImageExt = FilenameUtils.getExtension(arrayFile.get(i).getOriginalFilename());
					String serverFileName = "1000_"+System.currentTimeMillis() + "." + detailImageExt;

					String originalFilename = arrayFile.get(i).getOriginalFilename();
					String filename = originalFilename.replace("."+detailImageExt, "");

					String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
					filename =filename.replaceAll(match, " ");


					originalFilename = filename + "." + detailImageExt;

					fileVo.setFileId(attchFileNo);
					fileVo.setTempFileName(serverFileName);
					fileVo.setFileNmae(originalFilename);
					fileVo.setFileSize(String.valueOf(arrayFile.get(i).getSize()));
					fileVo.setRegId(vo.getSellerCode());

					srmevl0050Dao.insertQualityEvaluationFile(fileVo);

					FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + serverFileName);

					try {
						FileCopyUtils.copy(arrayFile.get(i).getInputStream(),	listImageOs);
					} catch (Exception e) {
						logger.debug(e.toString());
					} finally {
						if (listImageOs != null) {
							try {
								listImageOs.close();
						} catch (Exception e) {
							logger.debug("error : " + e.getMessage());
							}
						}
					}
				}
			}
			vo.setCompanyIntroAttachNo(attchFileNo);
			if(srmevl0050Dao.selectQualityEvaluationFileList(vo.getCompanyIntroAttachNo()).size() == 0) {
				vo.setCompanyIntroAttachNo("");
			}
		}


		SRMJON0040VO srmjon0040vo = new SRMJON0040VO();
		srmjon0040vo.setHouseCode(vo.getHouseCode());
		srmjon0040vo.setSellerCode(vo.getSellerCode());
		srmjon0040vo.setChannelCode(vo.getChannelCode());
		srmjon0040vo.setCatLv1Code(vo.getCatLv1Code());
		srmjon0040vo.setReqSeq(vo.getReqSeq());
		srmjon0040vo.setIrsNo(vo.getSellerCode());

		if(StringUtil.isNotEmpty(vo.getTempPw())){
			vo.setTempPw(SRMCommonUtils.EncryptSHA256(vo.getTempPw()));
		}

		if (srmjon0044Dao.selectGlobalHiddenCompCheck(vo) > 0) {
			//잠재업체 수정
			srmjon0044Dao.updateGlobalHiddenCompInfo(vo);
		} else {
			//잠재업체 등록
			srmjon0044Dao.insertGlobalHiddenCompInfo(vo);
		}
		if (!StringUtil.isEmpty(vo.getReqSeq())){
			srmjon0044Dao.updateGlobalConsultReq(vo);
		} else {
			vo.setReqSeq(srmjon0040Dao.selectConsultReqSeq(srmjon0040vo));
			srmjon0044Dao.insertGlobalConsultReq(vo);
		}

		if(vo != null && srmjon0040Dao.selecthiddenCompSSUPICount(srmjon0040vo) > 0){
			//수정
			srmjon0044Dao.updateGlobalHiddenCompInfoSSUPI(vo);
		} else {
			//등록
			srmjon0044Dao.insertGlobalHiddenCompInfoSSUPI(vo);
		}


		if("S".equals(status)){
			SRMJON0043VO srmjon0043vo = new SRMJON0043VO();
			srmjon0043vo.setHouseCode(vo.getHouseCode());
			srmjon0043vo.setSellerCode(vo.getSellerCode());
			srmjon0043vo.setReqSeq(vo.getReqSeq());
			srmjon0043Dao.updateHiddenCompReq(srmjon0043vo);

			//EMS 발송
			srmjon0043vo.setChannelCode(vo.getChannelCode());
			srmjon0043vo.setLocalFoodYn(vo.getLocalFoodYn());
			srmjon0043vo.setOnlineMailTarget(vo.getOnlineMailTarget());
			srmjon0043vo.setCatLv1Code(vo.getCatLv1Code());

			List<SRMJON0043VO> emailList = srmjon0043Dao.selectHiddenCompReqEMSList(srmjon0043vo);
			//대분류 담당 MD
			if(serverType.equals("prd")) {
				if (emailList != null) {
					for (int i = 0; i < emailList.size(); i++) {
						SRMJON0043VO emailVo = emailList.get(i);
						emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title", null, Locale.getDefault()));
						emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents", new Object[]{vo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
						emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
						srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
					}
				}
			} else {
				/****TEST****/
				SRMJON0043VO emailVo = new SRMJON0043VO();
				emailVo.setUserNameLoc(config.getString("email.test.userName"));
				emailVo.setEmail(config.getString("email.test.userEmail"));
				emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title", null, Locale.getDefault()));
				emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents", new Object[]{vo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
				emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
				srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
				/****TEST****/
			}
			//파트너사

			SRMJON0043VO emailPartnerVo = new SRMJON0043VO();
			if(serverType.equals("prd")) {
				//담당자
				emailPartnerVo.setUserNameLoc(vo.getvMainName());
				emailPartnerVo.setEmail(vo.getvEmail());
				//대표자
//			emailPartnerVo.setUserNameLoc(vo.getSellerCeoName());
//			emailPartnerVo.setEmail(vo.getSellerCeoEmail());
			} else {
				/****TEST****/
				emailPartnerVo.setUserNameLoc(config.getString("email.test.userName"));
				emailPartnerVo.setEmail(config.getString("email.test.userEmail"));
				/****TEST****/
			}

			emailPartnerVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon004301.title", null, Locale.getDefault()));
			emailPartnerVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon004301.contents", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailPartnerVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon004301.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailPartnerVo);
		}

		resultMap.put("message", "SUCCESS");


		return resultMap;
	}

}
