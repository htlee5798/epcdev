package com.lottemart.epc.edi.consult.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.dao.PEDMSCT0099Dao;
import com.lottemart.epc.edi.consult.model.PEDMSCT0099VO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0099Service;

@Service("PEDMSCT0099Service")
public class PEDMSCT0099ServiceImpl implements PEDMSCT0099Service {
	
	@Autowired
	private PEDMSCT0099Dao defDao;
	
	/**
	 * 사업자 List
	 */
	public List<PEDMSCT0099VO> selectBmanNoZipList(EpcLoginVO epcLoginVO) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		System.out.println("----->" + epcLoginVO.getCono());
		paramMap.put("cono", epcLoginVO.getCono());
		
		return defDao.selectBmanNoZipList(paramMap);
	}
	
	/**
	 * 주소 검색
	 */
	public List<PEDMSCT0099VO> selectZipList(PEDMSCT0099VO paramVO) throws Exception {
		return defDao.selectZipList(paramVO);
	}
	
	/**
	 * 주소 저장
	 */
	public void saveZip(PEDMSCT0099VO paramVO) throws Exception {
		if (paramVO == null) {
			return;
		}
		
		ArrayList zipInfoAl = paramVO.getZipInfoAl();
		
		HashMap infoHm = new HashMap();
		if (zipInfoAl != null) {
			String bmanNo 	= "";
			String zipCd 	= "";
			
			PEDMSCT0099VO trVO = null;
			
			for (int i = 0; i < zipInfoAl.size(); i++) {
				infoHm = (HashMap) zipInfoAl.get(i);
				
				bmanNo 	= (String) infoHm.get("bmanNo");
				zipCd 	= (String) infoHm.get("zipCd");
				
				if (bmanNo != null && !bmanNo.equals("") && zipCd != null && !zipCd.equals("")) {
					trVO = new PEDMSCT0099VO();
					
					trVO.setBmanNo((String) infoHm.get("bmanNo"));
					trVO.setZipCd((String) infoHm.get("zipCd"));
					trVO.setAddr1((String) infoHm.get("addr1"));
					trVO.setAddr2((String) infoHm.get("addr2"));
					
					// 삭제 후 저장실행
					defDao.deleteZip(trVO);
					
					// 저장
					defDao.insertZip(trVO);
				}
			}
		}
	}

}
