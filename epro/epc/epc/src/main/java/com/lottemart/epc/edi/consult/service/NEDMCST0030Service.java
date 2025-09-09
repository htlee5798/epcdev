package com.lottemart.epc.edi.consult.service;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.consult.model.NEDMCST0030VO;

public interface NEDMCST0030Service {
	public List selectVenCd(Map<String,Object> map ) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 등록 조회
	 * @param NEDMCST0030VO
	 * @return
	 */
	public List<NEDMCST0030VO> alertPageInsertPageSelect(NEDMCST0030VO map ) throws Exception;
	/**
	 *  협업정보 - > 협업정보  - > 이메일 체크  AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 이메일 수정 체크  AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 핸드폰 체크  AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 핸드폰 수정 체크  AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 협력업체 코드  AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public List ajaxVendor(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 협력업체 코드 checkd AJAX
	 * @param NEDMCST0030VO
	 * @return
	 */
	public List ajaxVendorCK(Map<String,Object> map) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 등록 
	 * @param NEDMCST0030VO
	 * @return
	 */
	public String alertPageInsert(NEDMCST0030VO map ) throws Exception;
	
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 수정  페이지
	 * @param NEDMCST0030VO
	 * @return
	 */
	public NEDMCST0030VO alertPageUpdatePage(NEDMCST0030VO map ) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 업데이트
	 * @param NEDMCST0030VO
	 * @return
	 */
	public void alertPageUpdate(NEDMCST0030VO map ) throws Exception;
	
	/**
	 *  협업정보 - > 협업정보  - > 알리미 삭제
	 * @param NEDMCST0030VO
	 * @return
	 */
	public void alertPageDelete(NEDMCST0030VO map ) throws Exception;
	
	
	
}
