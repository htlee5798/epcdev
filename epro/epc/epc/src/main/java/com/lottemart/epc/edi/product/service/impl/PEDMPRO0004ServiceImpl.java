package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lcn.module.common.util.HashBox;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.dao.PEDPCOM0001Dao;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;
import com.lottemart.epc.edi.product.dao.PEDMPRO0004Dao;
import com.lottemart.epc.edi.product.dao.PEDMPRO000Dao;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0004Service;
@Service("pEDMPRO0004Service")
public class PEDMPRO0004ServiceImpl implements PEDMPRO0004Service {
	//신상품 등록 관련 객체. 임시 보관함 삭제나 상품 정보 입력 및 수정.
	@Autowired
	private PEDMPRO0004Dao pEDMPRO0004Dao;

	//코드값 조회
	@Autowired
	private PEDPCOM0001Dao pedpcom001Dao;
	

	@Autowired
	private CustomTagDao customTagDao;

	
	public void insertColorSizeFromExcelBatch(List<ColorSize> colorSizeList)
			throws Exception {
		// TODO Auto-generated method stub
		pEDMPRO0004Dao.insertColorSizeFromExcelBatch(colorSizeList);
	}


	
	public void insertProductInfoList(ArrayList<NewProduct> newProductArrayList)
			throws Exception {
		// TODO Auto-generated method stub
		pEDMPRO0004Dao.insertProductInfoList(newProductArrayList);
	}

	

	public boolean isNotValidTeamcode(String teamCode) throws Exception  {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setDetailCode(teamCode);
		
		List<EdiCommonCode> resultList = pedpcom001Dao.selectDistinctTeamList(searchParam);
		return resultList.size() == 0 ? true: false;
	}




	public boolean isNotValidEntpcode(String entpCode, String[] sessionUserVendorArray) throws Exception {
		// TODO Auto-generated method stub
		int i=0;
		for(String vendorId : sessionUserVendorArray) {
			if(vendorId.equals(entpCode)) {
				i = 1;
				break;
			}
		}
		return i == 0 ? true : false;
	}



	@Override
	public boolean isNotValidL1Code(String teamCode, String l1GroupCode) throws Exception {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(teamCode);
		searchParam.setDetailCode(l1GroupCode);
		
		List<EdiCommonCode> resultList = pedpcom001Dao.selectL1List(searchParam);
		return resultList.size() == 0 ? true: false;
	}



	@Override
	public boolean isNotValidCommonCode(String taxDivnCode, String parentCode) throws Exception {
		// TODO Auto-generated method stub
		int resultNum =0;
		HashBox map = new HashBox();
		map.put("parentCodeId", parentCode);
		List<HashBox> resultHashBox = customTagDao.getCode(map);
		Iterator resultItr = resultHashBox.iterator();
		while( resultItr.hasNext()) {
			HashBox tmpMap = (HashBox)resultItr.next();
			String resultCode = tmpMap.get("CODE_ID").toString().trim();
			if(resultCode.equals(taxDivnCode)) {
				resultNum = 1;
				break;
			}
		}
		return resultNum == 0 ? true : false;
	}



	@Override
	public boolean isNotValidStyleCode(String mdStyleCode) throws Exception {
		int result=0;
		List<EdiCommonCode> resultList = pedpcom001Dao.selectStyleCodeList();
		Iterator resultItr = resultList.iterator();
		while(resultItr.hasNext()) {
			EdiCommonCode ediCommonCode = (EdiCommonCode)resultItr.next();
			if(ediCommonCode.getCategoryCode().
					equals(mdStyleCode)) {
				result = 1;
				break;
			}
		}
		return result == 0 ? true: false;
	}



	@Override
	public boolean isNotValidSubGroupCode(String teamCode, String l1GroupCode,
			String subGroupCode) throws Exception {
		// TODO Auto-generated method stub
		int result=0;
		
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(teamCode);
		searchParam.setDetailCode(l1GroupCode);
		
		List<EdiCommonCode> resultList = pedpcom001Dao.selectL4ListAjax(searchParam);
		Iterator resultItr = resultList.iterator();
		while(resultItr.hasNext()) {
			EdiCommonCode ediCommonCode = (EdiCommonCode)resultItr.next();
			if(ediCommonCode.getCategoryCode().
					equals(subGroupCode)) {
				result = 1;
				break;
			}
		}
		return result == 0 ? true: false;
	}

}
