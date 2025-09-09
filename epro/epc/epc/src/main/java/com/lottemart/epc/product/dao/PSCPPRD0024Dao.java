package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;

import org.springframework.stereotype.Repository;

@Repository("PSCPPRD0024Dao")
public class PSCPPRD0024Dao extends AbstractDAO {

	/**
	 * 배송정보 조회
	 * @MethodName  : selectDeliveryList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectDeliveryList(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscpprd0024.selectDeliveryList",paramMap);
	}
	
	/**
	 * 배송정보 조회2
	 * @MethodName  : selectDeliveryInfo
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public DataMap selectDeliveryInfo(DataMap paramMap) throws Exception{ // 인터넷상품   배송정보
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectDeliveryInfo", paramMap);
	}
	
	/**
	 * 공통배송비정보2
	 * @MethodName  : selectVendorDeliInfo
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(DataMap paramMap) throws Exception{ 
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectVendorDlvInfo", paramMap);
	}
	
	/**
	 * 공통배송비정보2
	 * @MethodName  : selectVendorDeliInfo
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public DataMap selectVendorDeliInfo(DataMap paramMap) throws Exception{
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectVendorDeliInfo", paramMap);
	}
	
	/**
	 * 묶음배송정보
	 * @MethodName  : selectBdlDelYn
	 * @param String
	 * @return Int
	 * @throws Exception
	 */
	public DataMap selectBdlDelYn(DataMap paramMap) throws Exception{	
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectBdlDelYn", paramMap);
	}
	
	/**
	 * 온라인배송유형 조회
	 * @MethodName  : selectProdDetailInfo
	 * @param String
	 * @return Int
	 * @throws Exception
	 */
	public DataMap selectOnlineProdInfo(DataMap paramMap) {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectOnlineProdInfo", paramMap);
	}
	/**
	 * 상품 배송비정보
	 * @MethodName  : selectDInfoList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectDInfoList(DataMap paramMap) throws Exception {
		
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0024.selectDInfoList", paramMap);
	}
	
	/**
	 * 배송정보 저장1
	 * @MethodName  : updateDelivery
	 * @param String
	 * @return Int
	 * @throws Exception
	 */
	public int updateDelivery(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0024.updateDelivery", paramMap);
	}
	
	/**
	 * 배송정보 저장2
	 * @MethodName  : insertDelivery
	 * @param String
	 * @return Int
	 * @throws Exception
	 */
	public int insertDelivery(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().insert("pscpprd0024.insertDelivery", paramMap);
		return 1;
	}

	/**
	 * 배송정보 저장3
	 * @MethodName  : updateDeliveryInfo
	 * @param String
	 * @return Int
	 * @throws Exception
	 */
	public int updateDeliveryInfo(DataMap paramMap) {
		return getSqlMapClientTemplate().update("pscpprd0024.updateDeliveryInfo", paramMap);
	}

	/**
	 * 배송정보 저장4
	 * @MethodName  : updateDeliveryBld
	 * @param String
	 * @return 
	 * @return Int
	 * @throws Exception
	 */
	public int updateDeliveryBld(DataMap paramMap) {
		return getSqlMapClientTemplate().update("pscpprd0024.updateDeliveryBld", paramMap);
	}

	//20181211 - 배송지 설정 수정
	/**
	 * 업체 배송지 정보
	 * @MethodName  : selectVendorAddrInfoCnt
	 * @param Map
	 * @return 
	 * @return String
	 */
	public String selectVendorAddrInfoCnt(Map<String, Object> paramMap) {
		return (String) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectVendorAddrInfoCnt", paramMap);
	}

	/**
	 * 상품 배송가능지역
	 * @MethodName  : updateDeliveryAddressInfo
	 * @param DataMap
	 * @return 
	 * @return int
	 */
	public int updatePsbtRegnCd(DataMap paramMap) {
		return getSqlMapClientTemplate().update("pscpprd0024.updatePsbtRegnCd", paramMap);
	}
	
	/**
	 * 상품 출고지주소&반품교환주소
	 * @MethodName  : updateDeliveryAddressInfo
	 * @param DataMap
	 * @return 
	 * @return int
	 */
	public int updateDlvAddr(DataMap paramMap) {
		return getSqlMapClientTemplate().update("pscpprd0024.updateDlvAddr", paramMap);
	}

	/**
	 * 배송비 시작적용일자 체크
	 * @MethodName  : selectDeliStartDy
	 * @param DataMap
	 * @return 
	 * @return int
	 */
	public int selectDeliStartDy(DataMap paramMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0024.selectDeliStartDy", paramMap);
	}

	/**
	 * 당일적용 배송비 삭제
	 * @MethodName  : deleteDayDelivery
	 * @param DataMap
	 * @return 
	 * @return int
	 */
	public int deleteDayDelivery(DataMap paramMap) {
		return getSqlMapClientTemplate().delete("pscpprd0024.deleteDayDelivery", paramMap);
	}

	/**
	 * TEC_DELIVERY_AMT_POLICE 데이터 업데이트
	 * @MethodName  : updatePoliceDelivery
	 * @param DataMap
	 * @return 
	 * @return int
	 */
	public int updatePoliceDelivery(DataMap paramMap) {
		return getSqlMapClientTemplate().update("pscpprd0024.updatePoliceDelivery", paramMap);
	}
}
