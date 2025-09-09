package com.lottemart.common.file.service;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

public interface FileMngForOperatingTeamService{
	
	public DataMap fileUploadAndDecrypt(HttpServletRequest request) throws Exception;
	
    public DataMap decryptDrmFile(String sourcePath, String sourceName, String targetPath, String targetName) throws Exception;
    
}
