package com.lottemart.epc.product.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0050Dao;
import com.lottemart.epc.product.dao.PSCPPRD0016Dao;
import com.lottemart.epc.product.model.PSCPPRD0016VO;
import com.lottemart.epc.product.service.PSCMPRD0050Service;

/**
 * @Class Name : PSCMPRD0050ServiceImpl
 * @Description : 증정품관리 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.06.07   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMPRD0050Service")
public class PSCMPRD0050ServiceImpl implements PSCMPRD0050Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0050ServiceImpl.class);
	
	@Autowired
	private PSCMPRD0050Dao pscmprd0050Dao;
	
	@Autowired
	private PSCPPRD0016Dao pscpprd0016Dao;

	/**
	 * Desc : 증정품관리 조회하는 메소드
	 * @Method Name : selectComponentList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectGiftList(DataMap paramMap) throws Exception {
		return pscmprd0050Dao.selectGiftList(paramMap);
	}
	
	/**
	 * Desc : 증정품관리 저장하는 메소드
	 * @Method Name : updateBatchGift
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateBatchGift(HttpServletRequest request) throws Exception {
		PSCPPRD0016VO bean = new PSCPPRD0016VO();
		
		int resultCnt = 0;
		
		String[] prodCds	= request.getParameterValues("PROD_CD");
		String[] strCds	= request.getParameterValues("STR_CD");
		String[] pests	= request.getParameterValues("PEST_DESC");
		String[] pestTypes	= request.getParameterValues("PEST_TYPE");
		String[] pestStartDys	= request.getParameterValues("PEST_START_DY");
		String[] pestEndDys	= request.getParameterValues("PEST_END_DY");
		
		try {
			for (int i=0; i<prodCds.length; i++) {
				bean.setProdCd(prodCds[i]);
				bean.setStrCd(strCds[i]);
				bean.setPrest(pests[i]);
				bean.setPrestType(pestTypes[i]);
				bean.setPrestStartDy(pestStartDys[i]);
				bean.setPrestEndDy(pestEndDys[i]);
				
				pscpprd0016Dao.deletePrdPresent(bean);
				
				resultCnt += pscpprd0016Dao.insertPrdPresent(bean);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
		return resultCnt;
	}
}
