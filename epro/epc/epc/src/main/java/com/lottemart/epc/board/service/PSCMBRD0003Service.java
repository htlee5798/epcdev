/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 4:50:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0003SearchVO;

/**
 * @Class Name : PSCMBRD0003Service
 * @Description : 1:1문의 목록 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 4:51:38 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMBRD0003Service {
	
	/**
	 * Desc : 총처리건, 접수건, 완료건을 조회하는 메소드
	 * @Method Name : selectCounselCount
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectCounselCount(DataMap paramMap) throws Exception;

	
	/**
	 * Desc : 콜센터 1:1문의 목록을 조회하는 메소드
	 * @Method Name : selectinquireList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectinquireList(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc : 접수위치 select box
	 * @Method Name : agentLocation
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> agentLocation(PSCMBRD0003SearchVO searchVO) throws Exception;
	
	
	/**
	 * Desc : 고객문의 구분 select box
	 * @Method Name : custQstDivnCd
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> custQstDivnCd(PSCMBRD0003SearchVO searchVO) throws Exception;

}
