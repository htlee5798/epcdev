package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.*;
import com.lottemart.epc.edi.srm.model.*;
import com.lottemart.epc.edi.srm.service.SRMEVL0050Service;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 품질경영평가  > 품질경영평가 평가완료  ServiceImpl
 *
 * @author LEE HYOUNG TAK
 * @since 2016.07.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일 				수정자				수정내용
 *  -----------    	------------    ------------------
 *   2016.07.29  	LEE HYOUNG TAK	최초 생성
 *   2019.01.09     OH SEUNG HYUN   품질경영평가 100분위 비율 계산 삭제
 * </pre>
 */
@Service("srmevl0050Service")
public class SRMEVL0050ServiceImpl implements SRMEVL0050Service {
	private static final Logger logger = LoggerFactory.getLogger(SRMEVL0050ServiceImpl.class);

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	@Autowired
	private SRMEVL0030Dao srmevl0030Dao;

	@Autowired
	private SRMEVL0040Dao srmevl0040Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Autowired
	private MessageSource messageSource;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;


	/**
	 * 첨부파일 조회
	 * @param SRMEVL0050VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectQualityEvaluationAttachFileList(SRMEVL0050VO vo) throws Exception {
		return srmevl0050Dao.selectQualityEvaluationAttachFileList(vo);
	}

	/**
	 * 점검요약 조회
	 * @param SRMEVL0050VO
	 * @return SRMEVL0050VO
	 * @throws Exception
	 */
	public SRMEVL0050VO selectQualityEvaluationSiteVisit1(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		SRMEVL0050VO resultVo = srmevl0050Dao.selectQualityEvaluationSiteVisit1(vo);
		/* 20190103 오승현
		 * 100분위 환산 점수를 데이터 조회할 때 계산해서 적용하기 때문에 더이상 서비스 단에서 계산하지 않는다
		 * if(resultVo != null) {
			if(StringUtil.isNotEmpty(resultVo.getEvScore()) && StringUtil.isNotEmpty(resultVo.getEvTotScore())){
				resultVo.setEvScorePer(String.valueOf(Math.round(Double.valueOf(resultVo.getEvScore())/Double.valueOf(resultVo.getEvTotScore())*100)));
			}
			if(StringUtil.isEmpty(resultVo.getEvScorePer()))resultVo.setEvScorePer("0");
		}*/

		return resultVo;
	}

	/**
	 * 참석자 조회
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit2(SRMEVL0050VO vo) throws Exception {
		return srmevl0050Dao.selectQualityEvaluationSiteVisit2(vo);
	}

	/**
	 * 조치내역 조회
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit3(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return srmevl0050Dao.selectQualityEvaluationSiteVisit3(vo);
	}


	/**
	 * 품질경영평가 평가완료
	 * @param SRMEVL0050VO
	 * @return
	 * @throws Exception
	 */
	public void updateQualityEvaluationComplete(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
//		vo.setHouseCode(srmSessionVO.getHouseCode());
		vo.setEvUserId(srmSessionVO.getEvUserId());

		//점검요약
		//삭제
		srmevl0050Dao.deleteQualityEvaluationSiteVisit1(vo);
		//등록
		if(StringUtil.isNotEmpty(vo.getEvCtrlDate())) {
			vo.setEvCtrlDate(vo.getEvCtrlDate().replace("-",""));
		}
		srmevl0050Dao.insertQualityEvaluationSiteVisit1(vo);

		//참석자
		//삭제
		srmevl0050Dao.deleteQualityEvaluationSiteVisit2(vo);
		//등록

		if(vo != null && vo.getEvPartNames() != null && vo.getEvPartNames().size() != 0) {
			for (int i = 0; i < vo.getEvPartNames().size(); i++) {
				if(!StringUtil.isEmpty((String)vo.getEvPartNames().get(i))){
					vo.setEvPartName((String) vo.getEvPartNames().get(i));
				} else {
					vo.setEvPartName("");
				}

				if(!StringUtil.isEmpty((String)vo.getEvPartDepts().get(i))){
					vo.setEvPartDept((String) vo.getEvPartDepts().get(i));
				} else {
					vo.setEvPartDept("");
				}

				if(!StringUtil.isEmpty((String)vo.getEvPartPostions().get(i))){
					vo.setEvPartPostion((String) vo.getEvPartPostions().get(i));
				} else {
					vo.setEvPartPostion("");
				}

				if(vo.getEvPartEmails().size() != 0 && !StringUtil.isEmpty((String)vo.getEvPartEmails().get(i))){
					vo.setEvPartEmail((String) vo.getEvPartEmails().get(i));
				} else {
					vo.setEvPartEmail("");
				}

				if(vo.getEvPartPhones().size() != 0 && !StringUtil.isEmpty((String)vo.getEvPartPhones().get(i))){
					vo.setEvPartPhone((String) vo.getEvPartPhones().get(i));
				} else {
					vo.setEvPartPhone("");
				}

				if (!StringUtil.isEmpty(vo.getEvPartName())) {
					srmevl0050Dao.insertQualityEvaluationSiteVisit2(vo);
				}
			}
		}

		//조치내역
		//삭제
		srmevl0050Dao.deleteQualityEvaluationSiteVisit3(vo);
		//등록
		if(vo != null && vo.getEvItemType1Codes() != null && vo.getEvItemType1Codes().size() != 0){
			for(int i=0; i< vo.getEvItemType1Codes().size(); i++) {
				if(vo.getTempYn().equals("Y")) {
					vo.setImpStatus("1");
				} else {
					vo.setImpStatus("0");
				}

				if(vo.getImpReqRemarks().size() != 0 && !StringUtil.isEmpty((String)vo.getImpReqRemarks().get(i))){
					vo.setImpReqRemark((String)vo.getImpReqRemarks().get(i));
				} else {
					vo.setImpReqRemark("");
				}

				if(vo.getEvItemType1Codes().size() != 0 && !StringUtil.isEmpty((String)vo.getEvItemType1Codes().get(i))){
					vo.setEvItemType1Code((String)vo.getEvItemType1Codes().get(i));
				} else {
					vo.setEvItemType1Code("");
				}

//				vo.setImpReqSubject((String)vo.getImpReqSubjects().get(i));
				if(!StringUtil.isEmpty(vo.getImpReqRemark())){
					srmevl0050Dao.insertQualityEvaluationSiteVisit3(vo);
				}
			}
		}

		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		ArrayList<MultipartFile> arrayFile = vo.getAttachFile();

		ArrayList docSeq = vo.getDocSeq();
		List<CommonFileVO> fileList = srmevl0050Dao.selectQualityEvaluationFileList(vo.getAttachFileNo());
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
			if(StringUtil.isEmpty(vo.getAttachFileNo())) {
				attchFileNo = String.valueOf(System.currentTimeMillis());
			} else {
				attchFileNo = vo.getAttachFileNo();
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
					fileVo.setRegId(vo.getEvUserId());

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
			vo.setAttachFileNo(attchFileNo);
			if(srmevl0050Dao.selectQualityEvaluationFileList(vo.getAttachFileNo()).size() == 0) {
				vo.setAttachFileNo("");
			}
			srmevl0050Dao.updateQualityEvaluationFile(vo);
		}

		//상태값 UPDATE
		if(vo.getTempYn().equals("Y")) {
			SRMEVL0030VO statusVo = new SRMEVL0030VO();

			statusVo.setHouseCode(vo.getHouseCode());
			statusVo.setEvNo(vo.getEvNo());
			statusVo.setSgNo(vo.getSgNo());
			statusVo.setEvUserId(vo.getEvUserId());
			statusVo.setSellerCode(vo.getVendorCode());
			statusVo.setSeq(vo.getSeq());
			statusVo.setVisitSeq(vo.getVisitSeq());

			statusVo.setStatus("G07");

			//SSUGL_PROCESS_MAIN 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationMAINStatus(statusVo);

			//SSUGL_PROCESS_SITEVISIT 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationSITEVISITStatus(statusVo);

			statusVo.setStatus("300");

			//SEVEM 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationSEVEMStatus(statusVo);

			//SEVEU 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationSEVEUStatus(statusVo);

			//SEVUS 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationSEVUSStatus(statusVo);

			//SEVES 상태값 UPDATE
			srmevl0030Dao.updateQualityEvaluationSEVESStatus(statusVo);

			//EMS 전송
			//담당MD
			vo.setLocale(SRMCommonUtils.getLocale(request));
			SRMEVL0050VO emailVo = srmevl0050Dao.selectQualityEvaluationSiteVisitEmailList(vo);
			SRMJON0043VO emailSendVo = new SRMJON0043VO();
			if(serverType.equals("prd")){
				emailSendVo.setUserNameLoc(emailVo.getUserNameLoc());
				emailSendVo.setEmail(emailVo.getEmail());
				emailSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0050.contents", new Object[] { emailVo.getSellerNameLoc() }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			} else {
				/****TEST****/
				emailSendVo.setUserNameLoc(config.getString("email.test.userName"));
				emailSendVo.setEmail(config.getString("email.test.userEmail"));
				emailSendVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0050.contents", new Object[] { "테스트업체" }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
				/****TEST****/
			}

			emailSendVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmevl0050.title", null, Locale.getDefault()));
			emailSendVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmevl0050.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailSendVo);
		}

	}



	/**
	 * 상세내역 보기 팝업
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitDetailPopup(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		List<SRMEVL0050VO> list = srmevl0050Dao.selectQualityEvaluationSiteVisitDetailPopup(vo);
		for(SRMEVL0050VO resultVo: list) {
			resultVo.setEvScorePer(String.valueOf(Math.round(Double.valueOf(resultVo.getEvIdScoreVal())/Double.valueOf(resultVo.getEvIdScore())*100)));
		}
		return list;
	}

	/**
	 * 품질경영평가 Tab List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlTabList(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		SRMEVL0040VO paramVo= new SRMEVL0040VO();
		paramVo.setLocale(SRMCommonUtils.getLocale(request));
		paramVo.setEvTplNo(vo.getEvTplNo());
		return srmevl0040Dao.selectEvlTabList(paramVo);
	}



	/**
	 * 결과보고서 평가결과
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitResult(SRMEVL0050VO vo, HttpServletRequest request) throws Exception {
		return srmevl0050Dao.selectQualityEvaluationSiteVisitResult(vo);
	}
}
