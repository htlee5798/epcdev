package com.lottemart.common.file.service;

import java.io.IOException;

/**
 * @Class Name : ImageFileMngService.java
 * @Description : Image Server(QS/QC) Manage Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 2. 20. 오후 5:04:33 조성옥
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface ImageFileMngService{
	/**
	 * Desc : ImageQC 서버 purge(캐쉬이미지 리로딩) 요청
	 * @Method Name : purgeImageQCServer
	 * @param type
	 * @param uniqueId
	 * @return
	 * @throws Exception
	 * @param 
	 * @exception Exception
	 */
	public void purgeImageQCServer(String type, String uniqueId) throws Exception; 

	/**
     * Desc : CDN 서버 purge(캐쉬이미지 리로딩) 요청
     * @Method Name : purgeImageQCServer
     * @param type
     * @param uniqueId
     * @return
     * @throws Exception
     * @param 
     * @exception Exception
     */
    public void purgeCDNServer(String type, String uniqueId) throws Exception;
    
	/**
	 * Desc : ImageQS 서버 PROD 상세 SNAPSHOT 요청
	 * @Method Name : requestImageQSServer
	 * @param type
	 * @param uniqueId
	 * @return
	 * @throws Exception
	 * @param 
	 * @exception Exception
	 */
	public boolean requestImageQSServer(String type, String uniqueId) throws Exception;
	
    /**
     * Desc : 이미지 리사이즈 파일을 생성하기 위한 Method
     * 
     * @Method Name : resize
     * @param source
     * @param destination
     * @param width
     * @param height
     * @throws IOException
     * @param
     * @return
     * @throws InterruptedException 
     * @exception Exception
     */
	public void resizeImage(String source, String destination, int width, int height) throws InterruptedException, IOException;
	
}