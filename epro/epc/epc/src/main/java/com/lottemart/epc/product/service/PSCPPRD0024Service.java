package com.lottemart.epc.product.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

public interface PSCPPRD0024Service {
	
	/**
	 * 배송정보
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDeliveryList(DataMap paramMap) throws Exception;
	
	/**
	 * 배송정보2
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectDeliveryInfo(DataMap paramMap) throws Exception;
	
	/**
	 * 공통배송비정보1
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(DataMap paramMap) throws Exception;
	
	/**
	 * 공통배송비정보2
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDeliInfo(DataMap paramMap) throws Exception;
	/**
	 * 묶음배송 정보
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectBdlDelYn(DataMap paramMap) throws Exception;
	/**
	 * 온라인 상품 유형 조회
	 * @param DataMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public DataMap selectOnlineProdInfo(DataMap paramMap) throws SQLException, Exception;
	/**
	 * 배송정보 저장
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public int deliverySave(HttpServletRequest request) throws Exception;

	/**
	 * 상품 배송비정보
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDInfoList(DataMap paramMap) throws Exception;
	
	/**
	 * 상품 배송비 저장
	 * @param DataMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public void updatePrdEvtCopy(DataMap paramMap) throws SQLException;
	
	//20181211 - 배송지 설정 수정
	/**
	 * 업체 배송지 정보
	 * @param DataMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public String selectVendorAddrInfoCnt(Map<String, Object> paramMap) throws Exception;	
	
	/**
	 * 상품 배송지 저장
	 * @param DataMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public void updateDeliveryAddressInfo(DataMap paramMap) throws SQLException;
	//20181211 - 배송지 설정 수정
	
}

