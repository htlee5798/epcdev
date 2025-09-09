package com.lottemart.epc.edi.srm.service.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.common.file.FileUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.SRMEVL0050Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON0041Dao;
import com.lottemart.epc.edi.srm.dao.SRMRST0020Dao;
import com.lottemart.epc.edi.srm.dao.SRMVEN0040Dao;
import com.lottemart.epc.edi.srm.model.SRMVEN004001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0040VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0040Service;


/**
 * 품질경영평가조치
 *
 * @author SHIN SE JIN
 * @since 2016.10.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmven0040Service")
public class SRMVEN0040ServiceImpl implements SRMVEN0040Service {

	private static final Logger logger = LoggerFactory.getLogger(SRMVEN0040ServiceImpl.class);

	@Autowired
	private SRMVEN0040Dao srmven0040Dao;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	@Autowired
	private SRMRST0020Dao srmrst0020Dao;

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 품질경영평가조치리스트 카운트
	 */
	public int selectEvalInfoListCount(SRMVEN0040VO vo) throws Exception {
		return srmven0040Dao.selectEvalInfoListCount(vo);
	}

	/**
	 * 품질경영평가조치리스트 조회
	 */
	public List<SRMVEN0040VO> selectEvalInfoList(SRMVEN0040VO vo) throws Exception {
		return srmven0040Dao.selectEvalInfoList(vo);
	}

	/**
	 * 품질경영평가조치 상세정보 조회
	 */
	public List<SRMVEN004001VO> selectCorrectiveActionList(SRMVEN004001VO vo) throws Exception {
		return srmven0040Dao.selectCorrectiveActionList(vo);
	}

	/**
	 * 시정조치 상세 조회
	 */
	public SRMVEN004001VO selectCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception {
		SRMVEN004001VO resultVO = srmven0040Dao.selectCorrectiveActionDetail(vo);
		if(StringUtil.isNotEmpty(resultVO.getImpAttachFileNo())) {
			resultVO.setImpAttachFileList(srmrst0020Dao.selectCompCounselFileList(resultVO.getImpAttachFileNo()));
		}
		return resultVO;

	}

	/**
	 * 품질경영평가조치 정보 수정
	 */
	public Map<String, Object> updateCorrectiveActionDetail(SRMVEN004001VO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();

		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		ArrayList<MultipartFile> arrayFile = vo.getImpAttachFileNoFile();

		ArrayList docSeq = vo.getDocSeq();
		List<CommonFileVO> fileList = srmrst0020Dao.selectCompCounselFileList(vo.getImpAttachFileNo());
		for(CommonFileVO fileVo: fileList) {
			if(docSeq.size() > 0) {
				boolean fileDel = true;
				for(int j=0; j < docSeq.size(); j++){
					if(fileVo.getFileSeq().equals(docSeq.get(j))){
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

		if(arrayFile.size() != 0) {
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

					srmevl0050Dao.insertQualityEvaluationFile(fileVo);	//파일정보 등록

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
		srmven0040Dao.updateCorrectiveActionDetail(vo);

		resultMap.put("message", "SUCCESS");

		return resultMap;

	}

	/**
	 * 품질경영평가조치 정보 삭제
	 */
	public Map<String, Object> updateCorrectiveActionDetaildel(SRMVEN004001VO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();

		String imageUploadPath = config.getString("edi.srm.file.path"); //파일 업로드 경로

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

		srmven0040Dao.updateCorrectiveActionDetaildel(vo);

		resultMap.put("message", "SUCCESS");

		return resultMap;
	}
}
