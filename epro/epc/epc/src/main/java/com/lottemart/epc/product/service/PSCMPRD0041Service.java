package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0041DtlVO;
import com.lottemart.epc.product.model.PSCMPRD0041VO;

public interface PSCMPRD0041Service {

	/**
	 * Desc : 협력사공지관리 검색
	 * 
	 * @Method Name : selectVendorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0041VO> selectVendorMgr(DataMap paramMap)
			throws Exception;

	/**
	 * Desc : 협력사공지관리 총건수
	 * 
	 * @Method Name : selectVendorMgrCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int selectVendorMgrCnt(Map<String, String> paramMap)
			throws Exception;

	/**
	 * Desc : 협사공지게시판 총건수
	 * 
	 * @Method Name : VondorMgrCnt
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public String VondorMgrCnt() throws Exception;

	/**
	 * Desc : : 협력사 공지 사용 승인여부 수정
	 * 
	 * @Method Name : updateVendorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updateVendorMgr(PSCMPRD0041VO pscmprd0041vo) throws Exception;

	/**
	 * Desc : 협력사 공지 등록
	 * 
	 * @Method Name : saveVondorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int saveVondorMgr(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc : 협력사 공지 수정
	 * @Method Name : updateVondorMgr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateVondorMgr(DataMap paramMap) throws Exception ;

	/**
	 * Desc : 협력사 공지 상세 등록
	 * 
	 * @Method Name : saveVondorMgrDtl
	 * @param paramMap
	 * @param mgrCnt
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int saveVondorMgrDtl(DataMap paramMap) throws Exception;

	/**
	 * Desc : 협력사 공지 VEIW
	 * 
	 * @Method Name : selectVendorMgrView
	 * @param recommSeq
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMPRD0041VO selectVendorMgrView(String recommSeq) throws Exception;

	/**
	 * Desc : IBSHEET 세팅
	 * 
	 * @Method Name : selectSheet
	 * @param seq
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0041DtlVO> selectSheet(DataMap paramMap) throws Exception;

	/**
	 * Desc : 엑셀 저장
	 * @Method Name : selectPscmprd0041Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<Object, Object>> selectPscmprd0041Export( Map<Object, Object> paramMap) throws Exception;
	
	/**
	 * Desc :  공지사항 EC 연동여부 확인
	 * @Method Name : isVendorMgrEcRegistered
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	boolean isNotiEcRegistered(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * Desc : 같은 공지기간에 상품 중복 여부 체크
	 * @Method Name : isNotiEcProductCheck
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	boolean isNotiEcProductCheck(Map<String, Object> dataMap) throws Exception;
	
}
