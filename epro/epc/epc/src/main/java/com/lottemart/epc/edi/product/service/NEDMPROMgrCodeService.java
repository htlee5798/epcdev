package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.CommonProductVO;

public interface NEDMPROMgrCodeService {

	/**
	 * 공통 코드 카운트 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectMgrCodeListCount(CommonProductVO vo) throws Exception;
	
	/**
	 * 공통 코드 마스터 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectMgrCodeList(CommonProductVO vo) throws Exception;
	
	/**
	 * 공통 코드 디테일 카운트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectMgrCodeDtlListCount(CommonProductVO vo) throws Exception;
	
	/**
	 * 공통 코드 디테일 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectMgrCodeDtlList(CommonProductVO vo) throws Exception;
	
	
	/**
	 * 공통 코드 저장 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> insertMgrDtlCode(CommonProductVO vo , HttpServletRequest request) throws Exception;
	
	/**
	 * 공통코드 삭제 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> deleteMgrDtlCode(CommonProductVO vo , HttpServletRequest request) throws Exception;
}
