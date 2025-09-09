package com.lottemart.epc.common.controller;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.PropertyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.epc.common.service.CommonFileService;
import com.lottemart.epc.common.util.EPCUtil;
import com.lottemart.epc.common.util.SecureUtil;

/**
 * @Class Name : CommonFileController.java
 * @Description : 게시판 공통 CommonFileController
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 11. 오후 02:02:02 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("commonFileController")
public class CommonFileController
{
    private static final Logger logger = LoggerFactory.getLogger(CommonFileController.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CommonFileService commonFileService;
    @Resource(name = "propertiesService")
    protected PropertyService propertyService;

    /**
     * Desc : 첨부파일 삭제 및 게시판 업데이트
     * @Method Name : fileDelete
     * @param request
     * @throws Exception
     * @return message
     */
    @RequestMapping("comm/fileDelete.do")
    public ModelAndView fileDelete(HttpServletRequest request) throws Exception
    {
        String message = "";
        String atchFileId    = request.getParameter("atchFileId");
        String streFileNm    = request.getParameter("streFileNm");
        String orignlFileNm  = request.getParameter("orignlFileNm");
        String fileStreCours = request.getParameter("fileStreCours");
        String fileSn        = request.getParameter("fileSn");
        String boardSeq      = request.getParameter("boardSeq");

        FileVO file = new FileVO();
        file.setAtchFileId   (atchFileId);
        file.setStreFileNm   (streFileNm);
        file.setOrignlFileNm (orignlFileNm);
        file.setFileStreCours(fileStreCours);
        file.setFileSn       (fileSn);

        try
        {
            int resultCnt = commonFileService.deleteFile(file,boardSeq);
            message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());

            if (resultCnt > 0)
            {
                return AjaxJsonModelHelper.create("");
            }
            else
            {
                return AjaxJsonModelHelper.create(message);
            }
        }
        catch (Exception e)
        {
            return AjaxJsonModelHelper.create(message);
        }
    }

    /**
     * 파일 다운로드(샘플)
     */
    @RequestMapping(value = "comm/sampleDowmLoad.do")
    public ModelAndView download(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO filevo) throws Exception
    {
		ServletRequestAttributes sra           = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpServletRequest       hsr           = sra.getRequest();
		StringBuffer             stordFilePath = new StringBuffer();
		
		stordFilePath.append(hsr.getSession().getServletContext().getRealPath("/"));
		stordFilePath.append(propertyService.getString("Globals.fileStorePath"));
        //stordFilePath.append("C:/Project");

        String filename      = filevo.getStreFileNm();
        String original      = filevo.getOrignlFileNm();
        String filestrecours = filevo.getFileStreCours();

        if ("".equals(filename))
        {
            request.setAttribute("message", "File not found.");
            return new ModelAndView("/test/fileDown");
        }

        if ("".equals(original))
        {
            original = filename;
        }

        stordFilePath.append(filestrecours).append("/").append(filename);
        model.put("file",     new File(stordFilePath.toString()));
        model.put("fileName", original                          );

        return new ModelAndView("downloadView", model);
    }

    /**
     * 나모 이지미 파일 다운로드(파일 경로 및 이름을 통한 다운로드)
     */
    @RequestMapping(value = "common/namo/namoFileDownload.do", method = RequestMethod.GET)
    public ModelAndView namoFileDownload(HttpServletRequest request, Map<String, Object> model) throws Exception
    {
        StringBuffer stordFilePath = new StringBuffer();
        String path                = SecureUtil.splittingFilter(request.getParameter("path"));
        String filename            = new EPCUtil().getIso8859ToUtf8(SecureUtil.splittingFilter(request.getParameter("filename")));

        if ("".equals(filename))
        {
            request.setAttribute("message", "File not found.");
            return new ModelAndView("/test/fileDown");
        }

        stordFilePath.append(path).append(filename);
        logger.debug("stordFilePath ==>" + stordFilePath.toString() + "<==");
        logger.debug("fileName      ==>" + filename                 + "<==");
        model.put("file", new File(stordFilePath.toString()));
        model.put("fileName", filename);

        return new ModelAndView("downloadView", model);
    }

}
