package com.lottemart.epc.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0041DtlVO;
import com.lottemart.epc.system.model.PSCMSYS0005DtlVO;
import com.lottemart.epc.system.model.PSCMSYS0005VO;

/**
 * @Class Name : PSCMSYS0005Service.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 28. 오후 4:59:01 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMSYS0005Service {

	/**
	 * Desc : 전상법템플릿 검색
	 * 
	 * @Method Name : searchEscTem
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMSYS0005VO> searchEscTem(Map<String, String> paramMap)
			throws Exception;

	/**
	 * Desc : 전상법 템플릿 Cnt
	 * 
	 * @Method Name : searchEscTemCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int searchEscTemCnt(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 전상법 템플릿 삭제
	 * 
	 * @Method Name : deleteEscTem
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int deleteEscTem(DataMap dataMap) throws Exception;

	/**
	 * Desc : 전상법템플릿 저장
	 * 
	 * @Method Name : insertMasEscTem
	 * @param pscmsys0005vo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int insertMasEscTem(HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 전상법 템플릿 상세보기
	 * @Method Name : dtlEscTem
	 * @param norProdSeq
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMSYS0005VO dtlEscTem(String norProdSeq ) throws Exception;
	
	
	/**
	 * Desc : 전상법 템플릿 디테일  상세보기
	 * @Method Name : dtlEscTemDtl
	 * @param norProdSeq
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMSYS0005DtlVO dtlEscTemDtl(String norProdSeq ) throws Exception;
	
	/**
	 * Desc : 전상법 상세 시트 조회
	 * @Method Name : selectSheet
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMSYS0005DtlVO> selectSheet(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 전상법 상세 수정
	 * @Method Name : updateMasEscTem
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateMasEscTem(HttpServletRequest request) throws Exception ;

}