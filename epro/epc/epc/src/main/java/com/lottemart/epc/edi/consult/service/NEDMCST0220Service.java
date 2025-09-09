package com.lottemart.epc.edi.consult.service;
import java.util.List;

import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.model.EstimationSheet;

public interface NEDMCST0220Service {


	/**
	 * 협업정보 - > 견적서 관리  - > 견적문서 조회
	 * @param Estimation
	 * @return
	 */
	public List<Estimation> estimationMainSelect(Estimation map ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 세부 조회
	 * @param pid
	 * @return
	 */
	public Estimation estimationMainSelectDetailTop(String pid ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 세부 조회
	 * @param pid
	 * @return
	 */
	public List<EstimationSheet> estimationMainSelectDetailBottom(String pid ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 삭제
	 * @param pid
	 * @return
	 */
	public void estimationMainDelete(String pid ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정
	 * @param pid
	 * @return
	 */
	public void estimationMainDetailDelete(String pid ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정
	 * @param pid
	 * @return
	 */
	public void estimationMainDetailDelete(Estimation map ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정
	 * @param Estimation
	 * @return
	 */
	public void estimationSheetInsert(Estimation map ) throws Exception;
	
	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정
	 * @param Estimation
	 * @return
	 */
	public void estimationUpdate(Estimation map) throws Exception;
}
