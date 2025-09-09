package com.lottemart.epc.delivery.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PDCPHPC0001Dao;
import com.lottemart.epc.delivery.model.PDCPHPC0001VO;
import com.lottemart.epc.delivery.service.PDCPHPC0001Service;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 6.	binary27	
 * @version : 
 */
@Service("PDCPHPC0001Service")
public class PDCPHPC0001ServiceImpl implements PDCPHPC0001Service {
	
	@Autowired
	private PDCPHPC0001Dao pdcphpc0001Dao;


	/** 
	 * @see selectHappyCallList
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 6.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 해피콜 등록에 필요한 정보를 조회한다.
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public DataMap selectHpCallRegiInfo(String param) throws Exception {
		return pdcphpc0001Dao.selectHpCallRegiInfo(param);
	}
	
	
	/** 
	 * @see selectProdInfo
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2012. 1. 10.
	 * @author     : binary27
	 * @version    : 해피콜 배송 상품 정보 조회
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 
     * @param 
	 * @return  List<DataMap>
     * @throws 
	*/
	public List<DataMap> selectProdInfo(String param) throws SQLException{
		return pdcphpc0001Dao.selectProdInfo(param);
	}
	
	/** 
	 * @see selectHpcInfo
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 모든 해피콜 정보를 조회한다.
     * @param 
	 * @return  DataMap
     * @throws 
	*/
	public List<DataMap> selectHpcInfo(String param) throws Exception {
		return pdcphpc0001Dao.selectHpcInfo(param);
	}
	
	
	/** 
	 * @see getAdminId
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 8.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : adminId를 받아온다.
     * @param 
	 * @return  String
     * @throws 
	*/
	public String getAdminId(String param) throws Exception {
		return pdcphpc0001Dao.getAdminId(param);
	}
	
	
	/**
 	 * @see com.lottemart.epc.delivery.service.PDCPHPC0001Service#updateHappyCall(com.lottemart.epc.delivery.model.PDCPHPC0001VO)
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    : 
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 새로운 해피콜 정보를 저장한다.
     * @param 
	 * @return
	 */
	public int updateHappyCall(PDCPHPC0001VO pdcphpc0001VO) throws Exception {
		
		int check = 0;
		
		//새로운 해피콜 저장
		pdcphpc0001Dao.insertHappycall(pdcphpc0001VO);	
		
		//해피콜 등록에 따른 송장접수정보수정
		check = check + pdcphpc0001Dao.updateInvoiceAcceptHappycall(pdcphpc0001VO);	

		if(null!=pdcphpc0001VO.getSendNm() && !"".equals(pdcphpc0001VO.getSendNm())){
			//해피콜 등록에 따른 보내시는분 정보 수정
			check = check + pdcphpc0001Dao.updateSendInfo(pdcphpc0001VO);
		}

		//해피콜 등록에 따른 받으실분 정보 수정
		check = check + pdcphpc0001Dao.updateRecvInfo(pdcphpc0001VO);
		
		return check;
	}
	

	/** 
 	 * @see com.lottemart.epc.delivery.service.PDCPHPC0001Service#selectPostList(java.util.Map)
	 * @Method Name  : PDCPHPC0001ServiceImpl.java
	 * @since      : 2011. 12. 7.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.service.impl
	 * @Description : 우편번호를 조회한다.
     * @param 
	 * @return
	 */
	public List<DataMap> selectPostList(String addressDivn, DataMap paramMap) throws SQLException{
		
		//addressDivn 체크
		//addressDivn 별 param체크
		
		// 지번주소 조회
		if("J".equals(StringUtil.upperCase(addressDivn))){
			return pdcphpc0001Dao.selectPostList_JIBUN(paramMap);
			
		}
		// 도로명주소 조회
		else if("N".equals(StringUtil.upperCase(addressDivn))){
			return pdcphpc0001Dao.selectPostList_ROAD(paramMap);
		}
		else
			return null;
		
//		return pdcphpc0001Dao.selectPostList(paramMap);
	}
	
	public List<DataMap> selectNewZipCodeSigunguList(DataMap param) throws Exception{
		return pdcphpc0001Dao.selectNewZipCodeSigunguList(param);
	}
}
