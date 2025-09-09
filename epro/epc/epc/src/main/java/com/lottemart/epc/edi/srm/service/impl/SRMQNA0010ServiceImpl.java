package com.lottemart.epc.edi.srm.service.impl;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.SRMJON0041Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON004301Dao;
import com.lottemart.epc.edi.srm.dao.SRMQNA0010Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMQNA0000VO;
import com.lottemart.epc.edi.srm.model.SRMQNA0010VO;
import com.lottemart.epc.edi.srm.service.SRMQNA0010Service;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;

@Service("srmqna0010Service")
public class SRMQNA0010ServiceImpl implements SRMQNA0010Service
{
    private static final Logger logger = LoggerFactory.getLogger(SRMQNA0010ServiceImpl.class);

    @Autowired
    private SRMQNA0010Dao srmqna0010Dao;

    @Autowired
    private ConfigurationService config;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SRMJON004301Dao srmjon004301Dao;

    @Autowired
    private SRMJON0041Dao srmjon0041Dao;


    @Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
    private String serverType = null;

    /**
     * 테넌트 입점문의 희망점포 지역 구분 데이터 가져오기
     */
    public List<SRMQNA0000VO> selectQnaCategoryList(SRMQNA0000VO srmqna0000VO) throws Exception {

        return srmqna0010Dao.selectQnaCategoryList(srmqna0000VO);
    }

    /**
     * 테넌트 입점문의 희망점포 지역 데이터 가져오기
     */
    public List<SRMQNA0000VO> selectQnaStoreAreaList(SRMQNA0000VO srmqna0000VO) throws Exception {

        return srmqna0010Dao.selectQnaStoreAreaList(srmqna0000VO);
    }

    /**
     * 테넌트 입점문의 지역별 점포 데이터 가져오기
     */
    public List<SRMQNA0000VO> selectQnaStoreList(SRMQNA0000VO srmqna0000VO) throws Exception {

        return srmqna0010Dao.selectQnaStoreList(srmqna0000VO);
    }

    /**
     * 테넌트 입점문의 작성
     */
    public void insertQnaInfo(SRMQNA0010VO srmqna0010VO) throws Exception {

        // 등록 순번 가져오기
        String nextReqSeq = srmqna0010Dao.selectNextQnaReqSeq(srmqna0010VO);
        if (nextReqSeq != null) {
            srmqna0010VO.setReqSeq(nextReqSeq);
        }

        srmqna0010Dao.insertQnaInfo(srmqna0010VO);
    }

    /**
     * 테넌트 입접문의 접수시 메일 전송
     */
    public void sendEmailToSeller(SRMQNA0010VO srmqna0010VO) throws Exception {

        try {
            //파트너사
            SRMJON0043VO emailPartnerVo = new SRMJON0043VO();

            emailPartnerVo.setUserNameLoc(srmqna0010VO.getMainName());
            emailPartnerVo.setEmail(srmqna0010VO.getEmail());
            
            emailPartnerVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmqna0010.title", null, Locale.getDefault()));
            emailPartnerVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmqna0010.contents", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
            emailPartnerVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmqna0010.msgCd", null, Locale.getDefault()));
            srmjon004301Dao.insertHiddenCompReqEMS(emailPartnerVo);
        }
        catch (Exception e) {
            logger.debug("SRMQNA0010>sendEmailToSeller : " + e.getMessage());
        }
    }

    /**
     * 테넌트 입점문의시 파일 전송
     * @param MultipartFile
     * @param String
     * @return
     * @throws Exception
     */
    public String uploadQnaFile(MultipartFile uploadfile, String SellerCode) throws Exception {
        if (uploadfile.isEmpty()) {
            return null;
        }

        CommonFileVO fileVo = new CommonFileVO();
        String imageUploadPath = config.getString("edi.srm.file.path");
        String detailImageExt = "";
        String serverFileName = "";
        String tmpFileId = "";

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

        //파일등록
        tmpFileId = String.valueOf(System.currentTimeMillis());
        fileVo.setFileId(tmpFileId);														//파일ID
        fileVo.setRegId(SellerCode);												//등록자

        detailImageExt = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
        serverFileName = "2001_"+System.currentTimeMillis() + "." + detailImageExt;

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
                    logger.debug("SRMQNA0010>uploadQnaFile : " + e.getMessage());
                }
            }
        }

        return tmpFileId;
    }
}
