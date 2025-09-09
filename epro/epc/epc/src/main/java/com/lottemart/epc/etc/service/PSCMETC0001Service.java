
package com.lottemart.epc.etc.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.etc.model.PSCMETC0001VO;


public interface PSCMETC0001Service {
	
	
	//public List<DataMap> getAsianaBalanceAccountList(DataMap paramMap) throws Exception;

	
	/**
	 * 관리자관리 리스트 조회
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCodeMainList(DataMap paramMap) throws Exception;
	
	
	/**
	 * 관리자관리 저장
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public void saveCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception;
	
	
	/**
	 * 관리자관리 등록
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public void insertCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception;
	
	
	/**
	 * 관리자관리 수정
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public void updateCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception;
	
	/**
	 * 관리자관리 삭제
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public void deleteCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception;	
}
