package com.lottemart.epc.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import lcn.module.framework.property.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.epc.common.dao.CommonFileDao;
import com.lottemart.epc.common.service.CommonFileService;

/**
 * @Class Name : CommonFileServiceImpl.java
 * @Description : 게시판 공통 CommonFileServiceImpl
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
@Service("commonFileService")
public class CommonFileServiceImpl implements CommonFileService
{
	@Autowired
	private CommonFileDao commonFileDao;
	// 첨부파일 관련 
	@Resource(name="FileMngService")
	private FileMngService fileMngService;	
	@Resource(name = "propertiesService")
	protected PropertyService propertyService;
	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * Desc : 첨부파일 삭제 및 게시판 파일아이디 업데이트
	 * @Method Name : deleteFile
	 * @param boardSeq
	 * @throws Exception
	 * @return 결과수
	 */
	public int deleteFile(FileVO filevo,String boardSeq) throws Exception
	{
		int resultCnt = 1;	
		try 
		{
			//하나의 파일삭제  데이터
			fileMngService.deleteFileInf(filevo);
			//삭제후 남은데이터가 있는지 검색...
			List<FileVO> fileList = fileMngService.selectFileList(filevo);
			//남은 데이터가 없다면....
			if (fileList.size() == 0)
			{
				//파일아이디 삭제수정
				commonFileDao.updateFileId(boardSeq);
			}
			//마지막 첨부파일삭제
			fileMngUtil.deleteRealFile(filevo, "");
		}
		catch (Exception e)
		{
			return 0;
		}
		
		return resultCnt;
	}
	
}
