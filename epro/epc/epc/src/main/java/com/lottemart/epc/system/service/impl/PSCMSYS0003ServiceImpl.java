package com.lottemart.epc.system.service.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xlib.cmc.GridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.dao.PSCMSYS0003Dao;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.service.PSCMSYS0003Service;

/**
 * @Class Name : PSCMSYS0003ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmsys0003Service")
public class PSCMSYS0003ServiceImpl implements PSCMSYS0003Service
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0003ServiceImpl.class);
	@Autowired
	private PSCMSYS0003Dao pscmsys0003Dao;
	
	/**
	 * (non-Javadoc)
 	 * @see com.lottemart.epc.system.service.PSCMSYS0003Service#selectDeliveryAmtInfoTotalCnt(java.util.Map)
	 * @MethodName  : PSCMSYS0003ServiceImpl.java
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.service.impl
	 * @Description : 배송비정보 리스트의 갯수를 select 하여 리턴
     * @param 
	 * @return
	 */
	public int selectPartnerTotalCnt(DataMap dataMap) throws SQLException
	{
		return pscmsys0003Dao.selectPartnerTotalCnt(dataMap);
	}
	
	public List<PSCMSYS0003VO> selectPartnerList(DataMap dataMap) throws Exception
	{
		return pscmsys0003Dao.selectPartnerList(dataMap);
	}
	
	public void insertPartnerPopup(PSCMSYS0003VO vo) throws Exception {
		pscmsys0003Dao.insertPartnerPopup(vo);
	}
	
	public int updatePartnerList(DataMap dataMap) throws Exception {
		int resultCnt = 0;
		PSCMSYS0003VO vo = null;
		try {
			//데이터 세팅
			String[] vendorId = dataMap.getString("vendorId").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] vendorNm = dataMap.getString("vendorNm").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] islndDeliAmtYn = dataMap.getString("islndDeliAmtYn").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] repTelNo = dataMap.getString("repTelNo").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] zipCd = dataMap.getString("zipCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] addr_1_nm = dataMap.getString("addr_1_nm").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] addr_2_nm = dataMap.getString("addr_2_nm").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String cono = dataMap.getString("cono");
			
			for(int i = 0; i < vendorNm.length; i++){
				vo = new PSCMSYS0003VO();
				vo.setVendorId(vendorId[i]);
				vo.setVendorNm(vendorNm[i]);
				vo.setIslndDeliAmtYn(islndDeliAmtYn[i]);
				vo.setRepTelNo(repTelNo[i]);
				vo.setZipCd(zipCd[i]);
				vo.setAddr_1_nm(addr_1_nm[i]);
				vo.setAddr_2_nm(addr_2_nm[i]);
				vo.setCono(cono);
				
				resultCnt += pscmsys0003Dao.updatePartnerList(vo);
			}
		} catch (Exception e) {
			resultCnt = 0;
			logger.debug(e.getMessage());
		}
		return resultCnt;
	}
	
	public int deletePartnerList(DataMap dataMap) throws Exception {
		int resultCnt = 0;
		PSCMSYS0003VO vo = null;
		try{
			//데이터 세팅
			String[] vendorId = dataMap.getString("vendorId").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String cono = dataMap.getString("cono");
			
			for (int i = 0; i < vendorId.length; i++) {
				vo = new PSCMSYS0003VO();
				vo.setVendorId(vendorId[i]);
				vo.setCono(cono);
				resultCnt += pscmsys0003Dao.deletePartnerList(vo);
			}
			
		}catch (Exception e) {
			resultCnt = 0;
			logger.debug(e.getMessage());
		}
		
		return resultCnt;
	}

	
}
