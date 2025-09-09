package com.lottemart.common.file.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : FileMngService.java
 * @Description : 파일정보의 관리를 위한 서비스 인터페이스
 * @Modification Information
 * 
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *
 * @author 
 * @since 
 * @version 1.0
 * @see 
 * 
 *  Copyright (C) 2011 by lottemart  All right reserved.
 */
public interface FileMngService{

    /** 
    	 * @see deleteFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 여러 개의 파일을 삭제한다.
         * @param fvoList
         * @throws Exception 
    */
    public void deleteFileInfs(List fvoList) throws Exception;

    /** 
    	 * @see insertFileInf
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
         * @param fvo
         * @return
         * @throws Exception 
    */
    public String insertFileInf(FileVO fvo) throws Exception;

    /** 
    	 * @see insertFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
         * @param fvoList
         * @return
         * @throws Exception 
    */
    public String insertFileInfs(List fvoList) throws Exception;

    
    /** 
    	 * @see selectFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 파일에 대한 목록을 조회한다.
         * @param fvo
         * @return
         * @throws Exception 
    */
    public List<FileVO> selectFileInfs(FileVO fvo) throws Exception;

    /** 
    	 * @see updateFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    : 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 
         * @param fvoList
         * @throws Exception 
         * @deprecated
    */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fvoList) throws Exception;
    
    /** 
    	 * @see insertAppendFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 동일 아이디를 갖는 파일을 추가 한다.
         * @param fvo
         * @throws Exception 
    */
    @SuppressWarnings("unchecked")
    public void insertAppendFileInfs(FileVO fvo) throws Exception;
    
    /** 
    	 * @see insertAppendFileInfs
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 동일 아이디를 갖는 파일을 추가 한다.
         * @param fvoList
         * @throws Exception 
    */
    @SuppressWarnings("unchecked")
    public void insertAppendFileInfs(List<FileVO> fvoList) throws Exception;

    /** 
    	 * @see deleteFileInf
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 하나의 파일을 삭제한다.
         * @param fvo
         * @throws Exception 
    */
    public void deleteFileInf(FileVO fvo) throws Exception;

    /** 
    	 * @see selectFileInf
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 파일에 대한 상세정보를 조회한다.
         * @param fvo
         * @return
         * @throws Exception 
    */
    public FileVO selectFileInf(FileVO fvo) throws Exception;

    /** 
    	 * @see getMaxFileSN
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 파일 구분자에 대한 최대값을 구한다.
         * @param fvo
         * @return
         * @throws Exception 
    */
    public int getMaxFileSN(FileVO fvo) throws Exception;
    
    /** 
    	 * @see deleteAllFileInf
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 전체 파일을 삭제한다.
         * @param fvo
         * @throws Exception 
    */
    public void deleteAllFileInf(FileVO fvo) throws Exception;

    /** 
    	 * @see selectFileListByFileNm
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 파일명 검색에 대한 목록을 조회한다.
         * @param fvo
         * @return
         * @throws Exception 
    */
    public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws Exception;

    /** 
    	 * @see selectImageFileList
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 이미지 파일에 대한 목록을 조회한다.
         * @param vo
         * @return
         * @throws Exception 
    */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception;
    
    
    /** 
    	 * @see selectFileList
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 파일에 대한 목록을 조회한다.
         * @param vo
         * @return
         * @throws Exception 
    */
    public List<FileVO> selectFileList(FileVO vo) throws Exception;
    
    
    /** 
    	 * @see deleteRealFileInf
    	 * @Method Name  : FileMngService.java
    	 * @since      : 2011. 10. 6.
    	 * @author     : sunghoon
    	 * @version    :
    	 * @Locaton    : com.lottemart.common.file.service
    	 * @Description : 실제 파일도 함께 삭제한다.(파일 정보 중 atchFileId 또는  atchFileId ,fileSn를 필요로 함.)
         * @param vo
         * @param path
         * @return
         * @throws Exception 
    */
    public int deleteRealFileInf(FileVO vo, String path) throws Exception;
    
    
    /** 
      * @see deleteNFileSN
      * @Location : com.lottemart.common.file.service
      * @Method Name  : deleteNFileSN
      * @Description : 파일 전체 삯제시 TB_NFile 사용 정보 N로 변경 
      * @param vo
      * @return
      * @throws Exception 
    */
    public int deleteNFileSN(FileVO vo) throws Exception;
    
    /**
     * Desc : DRM File 을 복화화
     * @Method Name : decryptDrmFile
     * @param sourcePath DRM 대상 파일
     * @param targetPath DRM 복호화 저장 파일
     * @return 복호화 성공 여부
     * @throws Exception
     * @param 
     * @return 
     * @exception Exception
     */
    public DataMap decryptDrmFile(String sourcePath, String targetPath) throws Exception;

	/**
	 * 엑셀파일 복호화 후 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일 컬럼수와 colNms의 갯수가 맞아야 함.
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadExcelFile
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수(기본=1)
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int delFlag) throws Exception;

	public List<DataMap> readUploadExcelFile(HttpServletRequest request, String[] colNms, int hdRow) throws Exception;
	
	public List<DataMap> readUploadNullColExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int sheetIndex) throws Exception;

	public List<DataMap> readUploadExcelFileRequirement(HttpServletRequest request, String[] colNms, String[] reqCol, int hdRow, int delFlag) throws Exception;

	public List<DataMap> readUploadChangeColExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int sheetIndex) throws Exception;
}
