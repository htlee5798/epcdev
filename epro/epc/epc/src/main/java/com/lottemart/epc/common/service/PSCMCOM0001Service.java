package com.lottemart.epc.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

public interface PSCMCOM0001Service {

//	public void makeClientSessionInfo(HttpServletRequest request, String conoArrStr) throws Exception;

	public boolean makeClientSessionInfoSms(HttpServletRequest request, String conoArrStr, DataMap codeMap) throws Exception;

	public void makeAdminSessionInfo(HttpServletRequest request, String adminId, String cono) throws Exception;

	public void makeAdminSessionInfo(HttpServletRequest request, String adminId, String adminPwd, String cono) throws Exception;

	public void makeHappyCallSessionInfo(HttpServletRequest request, String adminId, String adminPwd) throws Exception;

	public void makeAllianceSessionInfo(HttpServletRequest request, String adminId, String adminPwd) throws Exception;

	public void logout(HttpServletRequest request) throws Exception;

	/**
	 * [관리자] 로그인 검증 및 세션 생성
	 * @param request
	 * @param coco
	 * @throws Exception
	 */
	public void makeAdminSessionInfoChg(HttpServletRequest request, String coco) throws Exception;
	
	/**
	 * HQ_VEN > TVE_VENDOR
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateMergeHqVenToTveVendor(Map<String,Object> paramMap) throws Exception;
	
	/**
	 * [협력사] 로그인 검증 및 세션 생성
	 * @param request
	 * @param paramMap
	 * @throws Exception
	 */
	public void makeClientSessionInfo(HttpServletRequest request, Map<String, Object> paramMap) throws Exception;
}
