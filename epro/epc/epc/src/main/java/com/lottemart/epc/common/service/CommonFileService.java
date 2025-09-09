package com.lottemart.epc.common.service;

import com.lottemart.common.file.model.FileVO;

/**
 * @Class Name : CommonFileService.java
 * @Description : 게시판 공통 CommonFileService
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
public interface CommonFileService
{
	/**
	 * Desc : 첨부파일 삭제 및 게시판 파일아이디 업데이트
	 * @Method Name : deleteFile
	 * @param boardSeq
	 * @throws Exception
	 * @return 결과수
	 */
	public int deleteFile(FileVO filevo,String boardSeq) throws Exception;
	
}
