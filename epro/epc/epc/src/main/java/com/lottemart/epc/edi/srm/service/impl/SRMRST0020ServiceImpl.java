package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.*;
import com.lottemart.epc.edi.srm.model.*;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;
import com.lottemart.epc.edi.srm.service.SRMRST0020Service;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * 입점상담 > 입점상담결과확인 ServiceImpl
 *
 * @author SHIN SE JIN
 * @since 2016.07.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.21  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmrst0020Service")
public class SRMRST0020ServiceImpl implements SRMRST0020Service {

	private static final Logger logger = LoggerFactory.getLogger(SRMRST0020ServiceImpl.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private SRMRST0020Dao srmrst0020Dao;

	@Autowired
	private SRMJON0041Service srmjon0041Service;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Autowired
	private SRMJON0043Dao srmjon0043Dao;


	@Autowired
	private MessageSource messageSource;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;


	/**
	 * 파트너사 상세 정보
	 */
	public SRMRST0020VO selectPartnerInfo(SRMSessionVO vo) throws Exception {
		return srmrst0020Dao.selectPartnerInfo(vo);
	}

	/**
	 * 입점상담 내역별 상태 조회 카운트
	 */
	public int selectStatusListCount(SRMRST0020VO vo) throws Exception {
		return srmrst0020Dao.selectStatusListCount(vo);
	}

	/**
	 * 입점상담 내역별 상태 조회
	 */
	public List<SRMRST0020VO> selectStatusList(SRMRST0020VO vo) throws Exception {
		return srmrst0020Dao.selectStatusList(vo);
	}

	/**
	 * 상담 조회
	 * @param SRMRST002001VO
	 * @return SRMRST002001VO
	 * @throws Exception
	 */
	public SRMRST002001VO selectCompCounselInfo(SRMRST002001VO vo) throws Exception {
		return srmrst0020Dao.selectCompCounselInfo(vo);
	}


	/**
	 * 품평회 조회
	 * @param SRMRST002002VO
	 * @return SRMRST002002VO
	 * @throws Exception
	 */
	public SRMRST002002VO selectCompFairInfo(SRMRST002002VO vo) throws Exception {
		return srmrst0020Dao.selectCompFairInfo(vo);
	}

	/**
	 * 파일정보 조회
	 * @param String
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectCompCounselFileList(String fileId) throws Exception {
		return srmrst0020Dao.selectCompCounselFileList(fileId);
	}

	/**
	 * 이행보증증권 조회
	 * @param SRMRST002003VO
	 * @return SRMRST002003VO
	 * @throws Exception
	 */
	public SRMRST002003VO selectCompInsInfo(SRMRST002003VO vo) throws Exception {
		return srmrst0020Dao.selectCompInsInfo(vo);
	}

	/**
	 * 이행보증증권 조회
	 * @param SRMRST002003VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public HashMap<String, String> updateCompInsInfo(SRMRST002003VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();

		vo.setInsAttachNo(srmjon0041Service.fileSave(vo.getInsAttachNo(),vo.getInsAttachNoFile(), vo.getSellerCode(), srmrst0020Dao.selectCompInsAttachNoFileId(vo)));

		srmrst0020Dao.updateCompInsInfo(vo);
		if(vo.getTempYn().equals("Y")){
			srmrst0020Dao.updateCompInsInfoMainStatus(vo);
			//EMS 전송
			vo.setLocale(SRMCommonUtils.getLocale(request));
			vo.setDeptCd(messageSource.getMessage("ems.srm.msg.srmrst002003.deptCd", null, Locale.getDefault()));
			//경리팀
			List<SRMRST002003VO> emailList = srmrst0020Dao.selectCompInsInfoDept(vo);

			//담당MD
			SRMRST002003VO mdEmail = srmrst0020Dao.selectCompInsInfoMD(vo);
			if(serverType.equals("prd")) {
				//경리팀
//				for (int i = 0; i < emailList.size(); i++) {
//					SRMRST002003VO emailVo = emailList.get(i);
//
//					SRMJON0043VO emailSendVo = new SRMJON0043VO();
//					emailSendVo.setUserNameLoc(emailVo.getUserNameLoc());
//					emailSendVo.setEmail(emailVo.getEmail());
//					emailSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmrst002003.title", null, Locale.getDefault()));
//					emailSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmrst002003.contents", new Object[]{emailVo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
//					emailSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmrst002003.msgCd", null, Locale.getDefault()));
//					srmjon004301Dao.insertHiddenCompReqEMS(emailSendVo);
//				}
				//담당MD 발송
				SRMJON0043VO emailMdSendVo = new SRMJON0043VO();
				emailMdSendVo.setUserNameLoc(mdEmail.getUserNameLoc());
				emailMdSendVo.setEmail(mdEmail.getEmail());
				emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmrst002003md.title", null, Locale.getDefault()));
				emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmrst002003md.contents", new Object[]{mdEmail.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
				emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmrst002003.msgCd", null, Locale.getDefault()));
				srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);
			} else {
				//경리팀
//				SRMJON0043VO emailSendVo = new SRMJON0043VO();
//				emailSendVo.setUserNameLoc(config.getString("email.test.userName"));
//				emailSendVo.setEmail(config.getString("email.test.userEmail"));
//				emailSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmrst002003.title", null, Locale.getDefault()));
//				emailSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmrst002003.contents", new Object[] { "테스트업체" }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
//				emailSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmrst002003.msgCd", null, Locale.getDefault()));
//				srmjon004301Dao.insertHiddenCompReqEMS(emailSendVo);

				//담당MD 발송
				SRMJON0043VO emailMdSendVo = new SRMJON0043VO();
				emailMdSendVo.setUserNameLoc(config.getString("email.test.userName"));
				emailMdSendVo.setEmail(config.getString("email.test.userEmail"));
				emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmrst002003md.title", null, Locale.getDefault()));
				emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmrst002003md.contents", new Object[] { "테스트업체" }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
				emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmrst002003md.msgCd", null, Locale.getDefault()));
				srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);
			}

		}
		resultMap.put("message","SUCCESS");

		return resultMap;
	}

	/**
	 * 선택한 입점상담 내역 삭제
	 */
	public String deleteCounselInfo(SRMRST0020VO vo) throws Exception {

		String msg = "success";

		ArrayList seqList = vo.getSeqList();

		if(seqList != null){
			for (int i = 0; i < seqList.size(); i++) {
				if(!StringUtil.isEmpty((String)seqList.get(i))){
					vo.setSeq((String) seqList.get(i));
					//삭제시 취소 메일
					insertSendCanelMail(vo);

					srmrst0020Dao.deleteCounselInfo(vo);	// 입점상담 내역 삭제
				}
			}

			int cnt = srmrst0020Dao.selectAllCounselInfo(vo);

			if (cnt == 0) {	// 입점상담 내역이 없을 경우 잠재업체 정보 삭제
				srmrst0020Dao.deleteHiddenCompInfo(vo);
				msg = "logOut";
			}
		}

		return msg;
	}


	/**
	 * 시정조치 리스트 조회
	 * @param SRMRST002004VO
	 * @return List<SRMRST002004VO>
	 * @throws Exception
	 */
	public List<SRMRST002004VO> selectCompSiteVisitCover3List(SRMRST002004VO vo) throws Exception {
		return srmrst0020Dao.selectCompSiteVisitCover3List(vo);
	}

	/**
	 * 시정조치 상세 조회
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public SRMRST002004VO selectCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception {
		SRMRST002004VO tmpVo = vo;
		tmpVo = srmrst0020Dao.selectCompSiteVisitCover3Detail(tmpVo);
		if(StringUtil.isNotEmpty(tmpVo.getImpAttachFileNo())) {
			tmpVo.setImpAttachFileList(srmrst0020Dao.selectCompCounselFileList(tmpVo.getImpAttachFileNo()));
		}
		return tmpVo;
	}



	/**
	 * 시정조치 상세 수정
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public Map<String, Object> updateCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception {

		Map<String, Object> resultMap = new HashMap<String,Object>();

		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		ArrayList<MultipartFile> arrayFile = vo.getImpAttachFileNoFile();

		ArrayList docSeq = vo.getDocSeq();
		List<CommonFileVO> fileList = srmrst0020Dao.selectCompCounselFileList(vo.getImpAttachFileNo());
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
			if(StringUtil.isEmpty(vo.getImpAttachFileNo())) {
				attchFileNo = String.valueOf(System.currentTimeMillis());
			} else {
				attchFileNo = vo.getImpAttachFileNo();
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
			vo.setImpAttachFileNo(attchFileNo);
			if(srmevl0050Dao.selectQualityEvaluationFileList(vo.getImpAttachFileNo()).size() == 0) {
				vo.setImpAttachFileNo("");
			}
		}

		if(StringUtil.isNotEmpty(vo.getImpResDate())) {
			vo.setImpResDate(vo.getImpResDate().replaceAll("-",""));
		}
		srmrst0020Dao.updateCompSiteVisitCover3Detail(vo);

		resultMap.put("message", "SUCCESS");

		return resultMap;
	}


	/**
	 * 시정 조치 내역 삭제
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public Map<String, Object> updateCompSiteVisitCover3Detaildel(SRMRST002004VO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		//파일 유무 체크
		if(StringUtil.isNotEmpty(vo.getImpAttachFileNo())) {
			//파일이 있는경우 파일삭제
			List<CommonFileVO> fileList = srmrst0020Dao.selectCompCounselFileList(vo.getImpAttachFileNo());
			for(CommonFileVO fileVo: fileList) {
				//파일 삭제
				srmjon0041Dao.deleteHiddenCompFile(fileVo);

				//서버파일 삭제
				File file = new File(imageUploadPath + "/" + fileVo.getTempFileName());
				if (file.isFile()) {
					FileUtil.delete(file);
				}
			}
		}

		srmrst0020Dao.updateCompSiteVisitCover3Detaildel(vo);

		resultMap.put("message", "SUCCESS");

		return resultMap;
	}


	/**
	 * 품질경영평가 기관정보
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public SRMRST002005VO selectCompSiteVisitComp(SRMRST002005VO vo, HttpServletRequest request) throws Exception {
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmrst0020Dao.selectCompSiteVisitComp(vo);
	}


	/**
	 * 품질경영평가 기관 선택 팝업
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public List<SRMRST002005VO> selectCompSiteVisitCompList(SRMRST002005VO vo) throws Exception {
		return srmrst0020Dao.selectCompSiteVisitCompList(vo);
	}


	/**
	 * 업체가 선택한  품질경영평가 기관 확인
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String selectedEvalSellerCode(SRMRST002005VO vo) throws Exception {
		// TODO Auto-generated method stub
		return srmrst0020Dao.selectedEvalSellerCode(vo);
	}


	/**
	 * 품질경영평가 기관 선택 후 저장
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception	 */
	public Map<String, Object> UpdateCompSiteVisitComp(SRMRST002005VO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		srmrst0020Dao.UpdateCompSiteVisitComp(vo); 	 // SSUGL_PROCESS_SITEVISIT 업데이트
		srmrst0020Dao.UpdateCompProcessMainComp(vo); // SSUGL_PROCESS_MAIN 업데이트

		// 담당 MD에세 메일 및 SMS 보내기
		// 담당자 정보 가져오기.
		SRMRST0020051VO emailvo = srmrst0020Dao.SelectChargeMdInfo(vo);

		// 운영일때
		if(serverType.equals("prd")) {
			//담당MD 발송
			SRMJON0043VO emailMdSendVo = new SRMJON0043VO();
			emailMdSendVo.setUserNameLoc(emailvo.getUserNameLoc());
			emailMdSendVo.setEmail(emailvo.getEmail());
			emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmevl0060.title", null, Locale.getDefault()));
			emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0060.contents", new Object[]{emailvo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmevl0060.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);

		}else {
			//담당MD 발송
			SRMJON0043VO emailMdSendVo = new SRMJON0043VO();
			emailMdSendVo.setUserNameLoc(config.getString("email.test.userName"));
			emailMdSendVo.setEmail(config.getString("email.test.userEmail"));
			emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmevl0060.title", null, Locale.getDefault()));
			emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0060.contents", new Object[] { emailvo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmevl0060.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);
		}

		resultMap.put("message", "SUCCESS");
		return resultMap;
	}

	/**
	 * 선택한 입점상담 내역 임시저장상태로 되돌림
	 * @param SRMRST0020VO
	 * @return
	 * @throws Exception
     */
	public String updateCounselInfoCancel(SRMRST0020VO vo) throws Exception {

		String msg = "success";

		ArrayList seqList = vo.getSeqList();

		if(seqList != null){
			for (int i = 0; i < seqList.size(); i++) {
				if(!StringUtil.isEmpty((String) seqList.get(i))){
					vo.setSeq((String) seqList.get(i));

					//취소시 이메일
					insertSendCanelMail(vo);

					srmrst0020Dao.updateCounselInfoCancel(vo);	// 입점상담 내역 삭제
				}
			}
		}
		return msg;
	}

	public void insertSendCanelMail(SRMRST0020VO vo) throws Exception {
		SRMJON0043VO mdVo = srmrst0020Dao.selectCatLv1CodeInfo(vo);
		if(mdVo == null) return;
		//EMS 발송
		List<SRMJON0043VO> emailList = srmjon0043Dao.selectHiddenCompReqEMSList(mdVo);

		// 취소시 취소 EMAIL
		if(serverType.equals("prd")) {
			//담당MD 발송
			if (emailList != null) {
				for (int j = 0; j < emailList.size(); j++) {
					SRMJON0043VO emailMdSendVo = emailList.get(j);
					emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title2", null, Locale.getDefault()));
					emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents2", new Object[]{emailMdSendVo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
					emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
					srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);
				}
			}
		} else {
			//담당MD 발송
			SRMJON0043VO emailMdSendVo = new SRMJON0043VO();
			emailMdSendVo.setUserNameLoc(config.getString("email.test.userName"));
			emailMdSendVo.setEmail(config.getString("email.test.userEmail"));
			emailMdSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title2", null, Locale.getDefault()));
			emailMdSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents2", new Object[] { "테스트업체" }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailMdSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailMdSendVo);
		}
	}

	/**
	 * MD거절 사유 조회
	 */
	public SRMRST002008VO selectRejectReasonInfo(SRMRST002008VO vo) throws Exception {
		return srmrst0020Dao.selectRejectReasonInfo(vo);
	}
}
