package com.lottemart.epc.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0023Dao;
import com.lottemart.epc.product.service.PSCPPRD0023Service;



          
@Service("PSCPPRD0023Service")                 
public class PSCPPRD0023ServiceImpl implements PSCPPRD0023Service{
	
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0002ServiceImpl.class);
	
	@Autowired
	private PSCPPRD0023Dao PSCPPRD0023Dao;
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 추가구성품 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectProdComponentList(DataMap paramMap) throws Exception {
		return PSCPPRD0023Dao.selectProdComponentList(paramMap);
	}
	
	/**
	 * 추가구성품,연관상품 등록
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveProdComponent(HttpServletRequest request) throws Exception {
		
		DataMap paramMap = new DataMap(request);
		
		int resultCnt = 0;
		
		String mode = paramMap.getString("mode");
		String[] selecteds	= request.getParameterValues("SELECTED");
		String[] assoCds	= request.getParameterValues("ASSO_CD");
		String[] dispYns	= request.getParameterValues("DISP_YN");
		
		if("update".equals(mode)){
			PSCPPRD0023Dao.deleteProdComponent(paramMap);
			
			for(int i=0; i<assoCds.length; i++){
				paramMap.put("assoCd", assoCds[i]);
				paramMap.put("dispYn", dispYns[i]);
				
				resultCnt += PSCPPRD0023Dao.insertProdComponent(paramMap);
			}
		}else if("delete".equals(mode)){
			for(int i=0; i<selecteds.length; i++){
				paramMap.put("assoCd", assoCds[i]);
				resultCnt += PSCPPRD0023Dao.deleteProdComponent(paramMap);
			}
		}
		
		if("06".equals(paramMap.getString("prodLinkKindCd"))){
			
			if("update".equals(mode)){
				//본상품 CAT_CD를 추가구성품에 update
				for(int j=0; j<selecteds.length; j++){
					paramMap.put("assoCd", assoCds[j]);
					PSCPPRD0023Dao.updateProductCatCd(paramMap);
				}	
			}
			
			String cnt = PSCPPRD0023Dao.selectProdCrossSellingCount(paramMap);
			String ctpdDealYn = "N";
			
			if(Integer.parseInt(cnt) > 0){
				ctpdDealYn = "Y";
			}
			
			paramMap.put("ctpdDealYn", ctpdDealYn);
			
			//추가구성품 일때 TPR_PRODUCT.CTPD_DEAL_YN[구성품취급여부] 처리
			PSCPPRD0023Dao.updateCtpdDealYn(paramMap);
			
			// API 연동 (EPC -> 통합BO API)
			String prodCd = paramMap.getString("prodCd").substring(0,1);
			String result = "";
			if(!prodCd.equals("D")){
				try {
					commonDao.commit();
					RestAPIUtil rest = new RestAPIUtil();
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("spdNo", paramMap.getString("prodCd"));
					if(commonDao.selectEcApprovedYn(paramMap.getString("prodCd"))){
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						logger.debug("API CALL RESULT = " + result);
					}
					logger.debug("API CALL RESULT = " + result);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
				
		return resultCnt;
	}
}
