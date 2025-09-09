package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0021VO;
/**
 * 
 * @author jyLim
 * @Class : com.lottemart.bos.product.service
 * @Description : 통계 > 업체상품 수정요청
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *     수정일      	 	    수정자                                         수정내용
 *  -----------    	--------    ---------------------------
 * 2012. 3. 9.	 	jyLim
 * @version : 1.0
 * </pre>
 */
public interface PSCPPRD0021Service {

	public List<DataMap> selectProdImgChgHist(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdDescChgHist(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdAddChgHist(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdMstChgHist(DataMap paramMap) throws Exception;
	
	public int prodAprvStatus(PSCPPRD0021VO pbomPRD0046vo) throws Exception;
	
	public int prodStatusUpdate(PSCPPRD0021VO pbomPRD0046vo) throws Exception;

	public DataMap selectProdDesc(DataMap paramMap) throws Exception;
	
	public DataMap selectProdInfoView(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdAddBefore(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdAddAfter(DataMap paramMap) throws Exception;
	
	//20180911 상품키워드 입력 기능 추가		
	public List<DataMap> selectProdKeywordChgHist(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdKeywordChgBefore(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectProdKeywordChgAfter(DataMap paramMap) throws Exception;
	//20180911 상품키워드 입력 기능 추가
	
}
