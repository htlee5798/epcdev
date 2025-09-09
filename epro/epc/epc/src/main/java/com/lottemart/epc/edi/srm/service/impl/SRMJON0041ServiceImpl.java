package com.lottemart.epc.edi.srm.service.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

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
import com.lottemart.epc.edi.srm.dao.SRMJON0041Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0041VO;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 [상세정보]
 *
 * @author LEE HYOUNG TAK
 * @since 2016.07.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.15  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Service("srmjon0041Service")
public class SRMJON0041ServiceImpl implements SRMJON0041Service {

	private static final Logger logger = LoggerFactory.getLogger(SRMJON0041ServiceImpl.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	/**
	 * 잠재업체 상세정보 조회
	 * @param SRMJON0041VO
	 * @return SRMJON0041VO
	 * @throws Exception
	 */
	public SRMJON0041VO selectHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception {
		return srmjon0041Dao.selectHiddenCompDetailInfo(vo);
	}

	/**
	 * 잠재업체 상세정보 수정
	 * @param SRMJON0041VO
	 * @return HashMap<String, String>
	 * @throws Exception
     */
	public HashMap<String, String> updateHiddenCompDetailInfo(SRMJON0041VO vo) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();

		vo.setProductImgPath(fileSave(vo.getProductImgPath(), vo.getProductImgPathFile(), vo.getSellerCode(), srmjon0041Dao.selectHiddenCompProductImgPathFileId(vo)));
		vo.setProductIntroAttachNo(fileSave(vo.getProductIntroAttachNo(), vo.getProductIntroAttachNoFile(), vo.getSellerCode(), srmjon0041Dao.selectHiddenCompProductIntroAttachNoFileId(vo)));
		vo.setCompanyIntroAttachNo(fileSave(vo.getCompanyIntroAttachNo(), vo.getCompanyIntroAttachNoFile(), vo.getSellerCode(), srmjon0041Dao.selectHiddenCompCompanyIntroAttachNoFileId(vo)));
		vo.setsmAttachNo(fileSave(vo.getsmAttachNo(), vo.getsmAttachNoFile(), vo.getSellerCode(), srmjon0041Dao.selectHiddenCompSmAttachNoFileId(vo)));

		//통화,제거
		vo.setProductPrice(vo.getProductPrice().replace(",",""));
		vo.setBasicAmt(vo.getBasicAmt().replace(",",""));
		vo.setSalesAmt(vo.getSalesAmt().replace(",",""));
		vo.setEmpCount(vo.getEmpCount().replace(",",""));

		srmjon0041Dao.updateHiddenCompDetailInfo(vo);
		srmjon0041Dao.updateHiddenCompDetailInfoSSUPI(vo);

		resultMap.put("message","SUCCESS");
		return resultMap;
	}

	/**
	 * 파일정보 조회
	 * @param CommonFileVO
	 * @throws Exception
	 */
	public CommonFileVO selectHiddenCompFile(CommonFileVO vo) throws Exception {
		return srmjon0041Dao.selectHiddenCompFile(vo);
	}

	/**
	 * 파일저장/수정/삭제[파일 1개만 업로드일 경우만 사용가능]
	 * @param String
	 * @param MultipartFile
	 * @param String
	 * @param String
	 * @return
     * @throws Exception
     */
	public String fileSave(String fileId, MultipartFile uploadfile, String SellerCode, String deleteFileNo)throws Exception{
		CommonFileVO fileVo = new CommonFileVO();
		String imageUploadPath = config.getString("edi.srm.file.path");
		String detailImageExt = "";
		String serverFileName = "";
		String tmpFileId = fileId;

		//파일 특수문자 제거
		String originalFilename = "";
		String filename;
		if (!uploadfile.isEmpty()) {
			originalFilename = uploadfile.getOriginalFilename();
			detailImageExt = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			filename = originalFilename.replace("."+detailImageExt, "");

//			String regex[] = config.getString("dir.srm.file.filenm").split("-");

			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			filename =filename.replaceAll(match, "");
			originalFilename = filename + "." + detailImageExt;

		}

		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory())
			folderDir.mkdirs();

		if (StringUtil.isEmpty(tmpFileId) && !uploadfile.isEmpty()) {
			//파일등록
			tmpFileId = String.valueOf(System.currentTimeMillis());
			fileVo.setFileId(tmpFileId);														//파일ID
			fileVo.setRegId(SellerCode);												//등록자

			detailImageExt = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			serverFileName = "1000_"+System.currentTimeMillis() + "." + detailImageExt;

			fileVo.setTempFileName(serverFileName);											//서버저장 파일명
			fileVo.setFileNmae(originalFilename);							//원본파일명
			fileVo.setFileSize(String.valueOf(uploadfile.getSize()));			//파일크기
			srmjon0041Dao.insertHiddenCompFile(fileVo);

			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + serverFileName);

			try {
				FileCopyUtils.copy(uploadfile.getInputStream(),	listImageOs);
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


		} else if (!StringUtil.isEmpty(fileId) && !uploadfile.isEmpty()) {
			//파일수정
			fileVo.setFileId(deleteFileNo);
			fileVo.setFileSeq("1");

			fileVo = srmjon0041Dao.selectHiddenCompFile(fileVo);
			CommonFileVO deleteFileVo = new CommonFileVO();
			deleteFileVo.setFileId(fileVo.getFileId());
			deleteFileVo.setFileSeq(fileVo.getFileSeq());

			serverFileName = fileVo.getTempFileName();
			String deleteServerFileName = serverFileName;

			tmpFileId = String.valueOf(System.currentTimeMillis());
			fileVo.setFileId(tmpFileId);															//파일ID
			fileVo.setRegId(SellerCode);												//등록자

			detailImageExt = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			serverFileName = "1000_"+System.currentTimeMillis() + "." + detailImageExt;

			fileVo.setTempFileName(serverFileName);												//서버저장 파일명
			fileVo.setFileNmae(originalFilename);				//원본파일명
			fileVo.setFileSize(String.valueOf(uploadfile.getSize()));			//파일크기

			//파일 DB 삭제
			srmjon0041Dao.deleteHiddenCompFile(deleteFileVo);
			srmjon0041Dao.insertHiddenCompFile(fileVo);

			//서버파일 삭제
			File file = new File(imageUploadPath + "/" + deleteServerFileName);
			if (file.isFile()) {
				FileUtil.delete(file);
			}

			FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + serverFileName);

			try {
				FileCopyUtils.copy(uploadfile.getInputStream(),	listImageOs);
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
		} else if (StringUtil.isEmpty(tmpFileId) && uploadfile.isEmpty()) {

			fileVo.setFileId(deleteFileNo);
			fileVo.setFileSeq("1");
			//파일 DB 삭제
			fileVo = srmjon0041Dao.selectHiddenCompFile(fileVo);
			if (fileVo == null ) return "";

			fileVo.setFileId(fileVo.getFileId());
			srmjon0041Dao.deleteHiddenCompFile(fileVo);

			//서버파일 삭제
			serverFileName = fileVo.getTempFileName();
			File file = new File(imageUploadPath + "/" + serverFileName);
			if (file.isFile()) {
				FileUtil.delete(file);
			}
			tmpFileId = "";
		}

		return tmpFileId;
	}
}
