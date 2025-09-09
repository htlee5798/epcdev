package com.lottemart.epc.edi.consult.service;
import com.lottemart.epc.edi.consult.model.Estimation;

public interface NEDMCST0210Service {

	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 등록
	 * @param Estimation
	 * @param pid
	 * @return
	 */
	public void estimationInsert(Estimation map, String pid ) throws Exception;
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 등록
	 * @param Estimation
	 * @param pid
	 * @return
	 */
	public void estimationSheetInsert(Estimation map, String pid) throws Exception;
	
	/**
	 * 견적서 저장
	 * @param map
	 * @param pid
	 * @throws Exception
	 */
	public void insertEstimation(Estimation map, String pid) throws Exception;

}
