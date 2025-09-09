package com.lottemart.epc.product.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0015Dao;
import com.lottemart.epc.product.model.PSCPPRD0015VO;
import com.lottemart.epc.product.service.PSCPPRD0015Service;

/**
 * @Class Name : PSCPPRD0015ServiceImpl.java
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
@Service("pscpprd0015Service")
public class PSCPPRD0015ServiceImpl implements PSCPPRD0015Service
{
	@Autowired
	private PSCPPRD0015Dao pscpprd0015Dao;

	/**
	 * 점포 목록
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdStoreList(String strGubun) throws Exception 
	{
		return pscpprd0015Dao.selectPrdStoreList(strGubun);
	}
	
	/**
	 * 상품 아이콘 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0015VO> selectPrdIconList(Map<String, String> paramMap) throws Exception 
	{
		return pscpprd0015Dao.selectPrdIconList(paramMap);
	}
	
	/**
	 * 상품 아이콘 수정 처리
	 * @param List<VO>
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdIconList(List<PSCPPRD0015VO> pscpprd0015VOList) throws Exception 
	{
		int resultCnt = 0;

		try 
		{
			PSCPPRD0015VO ListBean = null;
			PSCPPRD0015VO bean = null;
			int listSize = pscpprd0015VOList.size();
			
			for (int i = 0; i < listSize; i++) 
			{
				ListBean = (PSCPPRD0015VO)pscpprd0015VOList.get(i);
				bean = new PSCPPRD0015VO();
				
				bean.setProdCd(ListBean.getProdCd());
				bean.setRegId(ListBean.getRegId());
				bean.setStrCd(ListBean.getStrCd());
				bean.setDelYn(ListBean.getDelYn());
				bean.setIcon1(ListBean.getIcon1());
				bean.setIcon2(ListBean.getIcon2());
				bean.setIcon3(ListBean.getIcon3());
				bean.setIcon4(ListBean.getIcon4());
				bean.setIcon5(ListBean.getIcon5());
				bean.setIcon6(ListBean.getIcon6());
				bean.setIcon7(ListBean.getIcon7());
				bean.setIcon8(ListBean.getIcon8());
				bean.setIcon9(ListBean.getIcon9());
				bean.setIcon10(ListBean.getIcon10());
				bean.setIcon11(ListBean.getIcon11());
				bean.setIcon12(ListBean.getIcon12());
				bean.setIcon13(ListBean.getIcon13());
				bean.setIcon14(ListBean.getIcon14());
				
				resultCnt = pscpprd0015Dao.updatePrdIconList(bean);
				
				if (resultCnt <= 0) 
				{
					throw new TopLevelException("아이콘 수정 작업중에 오류가 발생하였습니다. 수정 요청된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
				}
			}
		} 
		catch (Exception e) 
		{
			throw e;
		}
		
		return resultCnt;
	}

}
