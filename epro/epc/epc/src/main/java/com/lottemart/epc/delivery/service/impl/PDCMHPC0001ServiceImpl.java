package com.lottemart.epc.delivery.service.impl;

import java.sql.SQLException;
import java.util.List;

import lcn.module.common.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PDCMHPC0001Dao;
import com.lottemart.epc.delivery.service.PDCMHPC0001Service;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 5.	binary27	
 * @version : 
 */
@Service("PDCMHPC0001Service")
public class PDCMHPC0001ServiceImpl implements PDCMHPC0001Service {
	
	@Autowired
	private PDCMHPC0001Dao pdcmhpc0001Dao;

	/** 
 	 * @see com.lottemart.epc.delivery.service.PDCMHPC0001Service#selectHappyCallList(com.lottemart.common.util.DataMap)
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 5.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 해피콜 정보 목록을 조회한다.
     * @param 
	 * @return
	 */
	public List<DataMap> selectHappyCallList(DataMap paramMap) throws Exception {
		return pdcmhpc0001Dao.selectHappyCallList(paramMap);
	}
	
	/** 
 	 * @see com.lottemart.epc.delivery.service.PDCMHPC0001Service#selectHappyCallCount(com.lottemart.common.util.DataMap)
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2019. 4. 11.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 해피콜 실적을 조회한다.
     * @param 
	 * @return
	 */
	public List<DataMap> selectHappyCallCount(DataMap paramMap) throws Exception {
		return pdcmhpc0001Dao.selectHappyCallCount(paramMap);
	}
	
	
	/** 
	 * @see selectStoreList
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 점포 전체 목록 조회
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectStoreList() throws SQLException {
		return pdcmhpc0001Dao.selectStoreList();
	}
	
	
	/** 
	 * @see selectConfInfo
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 조회일자차수 정보조회
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public DataMap selectConfInfo() throws Exception {
		return selectConfInfo( DateUtil.getCurrentYearAsString());
	}
	
	
	/**
	 * Desc : 현재일자 및 조회일자차수를 적용한 일자를 조회(날짜는 포멧을 포함한다)
	 * @Method Name : getConfInfo
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectConfInfo(String year) throws Exception{
		DataMap resultMap = new DataMap();
		
		//조회일자차수 정보 조회
		DataMap confMap = (DataMap)pdcmhpc0001Dao.selectConfInfo(year);
		String qryDateFg = confMap == null ? "0" : confMap.getString("QRY_DATE_DG");
		
		String searchToDate   = "";
		String searchFromDate = "";
		
		//조회일자차수
		resultMap.put("qryDateDg", qryDateFg);
		
		//조회일자차수를 적용한 From Date, To Date 조회
		DataMap paramMap = new DataMap();
		paramMap.put("qryDateFg", qryDateFg);
		DataMap dateMap = (DataMap)pdcmhpc0001Dao.selectFromToDate(paramMap);
		if ( dateMap != null && dateMap.size() > 0) {
			searchToDate = dateMap.getString("TO_DATE");
			searchFromDate = dateMap.getString("FROM_DATE");
		}
		
		//현재일자(포멧포함)
		resultMap.put("currentDate",   searchToDate);
		//조회일자차수가 적용된 일자
		resultMap.put("qrydateFgDate", searchFromDate);
		
		return resultMap;
	}


	@Override
	public List<DataMap> selectActiveIdList() throws Exception {
		
		return pdcmhpc0001Dao.selectActiveIdList();
	}


	@Override
	public void updatdUtakmnId(DataMap paramMap) throws Exception {
		
		pdcmhpc0001Dao.updatdUtakmnId(paramMap);
	}
	
	/** 
 	 * @see com.lottemart.epc.delivery.service.PDCMHPC0001Service#selectHappyCallCountTotal(com.lottemart.common.util.DataMap)
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return
	 */
	public List<DataMap> selectHappyCallCountTotal(DataMap paramMap) throws Exception {
		return pdcmhpc0001Dao.selectHappyCallCountTotal(paramMap);
	}
	
	/** 
 	 * @see com.lottemart.epc.delivery.service.PDCMHPC0001Service#selectHappyCallCountDeliDy(com.lottemart.common.util.DataMap)
	 * @Method Name  : PDCMHPC0001ServiceImpl.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return
	 */
	public List<DataMap> selectHappyCallCountDeliDy(DataMap paramMap) throws Exception {
		return pdcmhpc0001Dao.selectHappyCallCountDeliDy(paramMap);
	}
}
