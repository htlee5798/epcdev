package com.lottemart.epc.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCMPRD0041Dao;
import com.lottemart.epc.product.model.PSCMPRD0041DtlVO;
import com.lottemart.epc.product.model.PSCMPRD0041VO;
import com.lottemart.epc.product.service.PSCMPRD0041Service;

@Service("pscmprd0041Service")
public class PSCMPRD0041ServiceImpl implements PSCMPRD0041Service {
	private static final Logger logger = LoggerFactory
			.getLogger(PSCMPRD0041ServiceImpl.class);

	@Autowired
	private PSCMPRD0041Dao pscmprd0041Dao;
	
	@Autowired
	private CommonDao commonDao;

	// 협력사공지 검색
	@Override
	public List<PSCMPRD0041VO> selectVendorMgr(DataMap paramMap)
			throws Exception {
		return pscmprd0041Dao.selectVendorMgr(paramMap);
	}

	// 협력사공지 총건수
	@Override
	public int selectVendorMgrCnt(Map<String, String> paramMap)
			throws Exception {
		return pscmprd0041Dao.selectVendorMgrCnt(paramMap);
	}
	

	// 협력사 공지 사용 중지 기능
	@Override
	public int updateVendorMgr(PSCMPRD0041VO pscmprd0041vo) throws Exception {
		int resultCnt = 0;
		resultCnt = pscmprd0041Dao.updateVendorMgr(pscmprd0041vo);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("announSeq", pscmprd0041vo.getAnnounSeq());
		paramMap.put("useFlag", "N");
		commonDao.commit();
		
		if(isNotiEcRegistered(paramMap)){
			RestAPIUtil rest = new RestAPIUtil();
			Map<String, Object> apiParam = new HashMap<String, Object>();	
			apiParam.put("bnrNo", pscmprd0041vo.getAnnounSeq());
			apiParam.put("notiEpsrStdCd", "TR");
			String result = rest.sendRestCall(RestConst.PD_API_0038, HttpMethod.POST, apiParam, 100000, true);
			logger.debug("API CALL RESULT = " + result);
		}
		
		return  resultCnt;
	}

	//협력사 공지 등록
	@Override
	public int saveVondorMgr(DataMap paramMap) throws Exception {
		int ecfailCnt = 0;
		boolean ecRegYn = false;
		boolean notiYn = false;
		ArrayList<String> ecApplyToCd = new ArrayList<String>();
		ArrayList<String> ecApplyToTypeCd = new ArrayList<String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		
		try {
			// 저장 수정 구분
			if (paramMap.getString("announSeq").equals("")) {
				//VENDOR_ANNOUN_MGR SEQ 추가
				String announSeq = pscmprd0041Dao.VondorMgrCnt();
				paramMap.put("announSeq", announSeq);
			
				// VENDOR_ANNOUN_MGR_DTL 추가
				String[] applyToCd = paramMap.getString("applyToCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
				String[] applyToTypeCd = paramMap.getString("applyToTypeCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
						
				for(int i=0; i<applyToCd.length;i++){
					if("10".equals(applyToTypeCd[i])){
						String prodCd = applyToCd[i];
						//EC 연동 여부 체크
						if( prodCd.substring(0, 1).equals("D")){
							ecRegYn = commonDao.selectEcPlanRegisteredYn(prodCd);
						}else{
							ecRegYn = commonDao.selectEcApprovedYn(prodCd);
						}
						
						if(ecRegYn){
							ecApplyToTypeCd.add(applyToTypeCd[i]);
							ecApplyToCd.add(applyToCd[i]);
						}else{
							ecfailCnt++;
						}
					}
				}
							
				dataMap.put("announStartDy", paramMap.getString("startDate"));
				dataMap.put("announEndDy",paramMap.getString("endDate"));
				dataMap.put("applyToCd", ecApplyToCd);
				dataMap.put("announSeq", paramMap.getString("announSeq"));
				dataMap.put("venderId", paramMap.getString("venderId"));
				
				//1. EC에 연동된 상품이 하나 이상일 경우  2. 같은기간에 같은 상품 공지사항 등록 불가능 처리  : 2가지 조건 충족해야 저장 가능
				if(ecApplyToCd.size() > 0 ){
					if(isNotiEcProductCheck(dataMap)){
						// VENDOR_ANNOUN_MGR 저장
						pscmprd0041Dao.saveVondorMgr(paramMap);	
						
						for(int i=0; i<ecApplyToCd.size();i++){
							if("10".equals(ecApplyToTypeCd.get(i))){ //연동 되어 있는 상품만 등록 가능
								paramMap.put("applyToCd", ecApplyToCd.get(i));
								paramMap.put("applyToTypeCd",ecApplyToTypeCd.get(i));
								pscmprd0041Dao.saveVondorMgrDtl(paramMap);
							}
						}
						
						notiYn = true;
					}
				}else{
					throw new Exception("EC에 연동된 상품이 없습니다. 다시 시도해주세요.");
				}							
			} else {
				
				// VENDOR_ANNOUN_MGR_DTL 추가
				String[] applyToCd = paramMap.getString("applyToCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
				String[] applyToTypeCd = paramMap.getString("applyToTypeCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
				
				for(int i=0; i<applyToCd.length;i++){
					if("10".equals(applyToTypeCd[i])){
						String prodCd = applyToCd[i];
						//EC 연동 여부 체크
						if( prodCd.substring(0, 1).equals("D")){
							ecRegYn = commonDao.selectEcPlanRegisteredYn(prodCd);
						}else{
							ecRegYn = commonDao.selectEcApprovedYn(prodCd);
						}
						
						if(ecRegYn){ //연동 되어 있는 상품만 등록 가능
							ecApplyToTypeCd.add(applyToTypeCd[i]);
							ecApplyToCd.add(applyToCd[i]);
						}else{
							ecfailCnt++;
						}
					}
				}
				
				dataMap.put("announStartDy", paramMap.getString("startDate")+paramMap.getString("startTime"));
				dataMap.put("announEndDy",paramMap.getString("endDate")+paramMap.getString("endTime"));
				dataMap.put("applyToCd", ecApplyToCd);
				dataMap.put("announSeq", paramMap.getString("announSeq"));
				dataMap.put("venderId", paramMap.getString("venderId"));
				
				//1. EC에 연동된 상품이 하나 이상일 경우  2. 같은기간에 같은 상품 공지사항 등록 불가능 처리  : 2가지 조건 충족해야 저장 가능
				if(ecApplyToCd.size() > 0 ){	
					if(isNotiEcProductCheck(dataMap)){
						//VENDOR_ANNOUN_MGR 마스터 수정
						pscmprd0041Dao.updateVondorMgr(paramMap);
						// VENDOR_ANNOUN_MGR 디테일 삭제
						pscmprd0041Dao.deleteVondorMgrDtl(paramMap);
						
						for(int i=0; i<ecApplyToCd.size();i++){
							if("10".equals(ecApplyToTypeCd.get(i))){
								paramMap.put("applyToCd", ecApplyToCd.get(i));
								paramMap.put("applyToTypeCd",ecApplyToTypeCd.get(i));
								pscmprd0041Dao.saveVondorMgrDtl(paramMap);
							}
						}
						notiYn = true;
					}
				}else{
					throw new Exception("EC에 연동된 상품이 없습니다. 다시 시도해주세요.");
				}
				
			}
			
			//EC 연동된 상품이 있을 경우에만 API 전송
			if(notiYn){
				commonDao.commit();
				String announSeq = paramMap.getString("announSeq");

				RestAPIUtil rest = new RestAPIUtil();
				Map<String, Object> apiParam = new HashMap<String, Object>();	
				apiParam.put("bnrNo", announSeq);
				apiParam.put("notiEpsrStdCd", "TR");
				
				String apiUrl = "";
				if(isNotiEcRegistered(dataMap) ){
					apiUrl = RestConst.PD_API_0038;	//수정						
				}else{					
					apiUrl = RestConst.PD_API_0036;	//등록								
				}
				
				String result = rest.sendRestCall(apiUrl, HttpMethod.POST, apiParam, 100000, true);
				logger.debug("API CALL RESULT = " + result);
			}	
		} catch (Exception e) {
			logger.debug("VondorMgr : "+ e.getMessage().toString() );
			throw new Exception(e.getMessage().toString());
		}
	
		return ecfailCnt;
	}
	
	//협력사 공지 상세 등록
	@Override
	public int saveVondorMgrDtl(DataMap paramMap) throws Exception {
		int cnt = 0;
		String[] applyToCd = paramMap.getString("applyToCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
		String[] applyToTypeCd = paramMap.getString("applyToTypeCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
	
		for(int i=0; i<applyToCd.length;i++){
			paramMap.put("applyToCd", applyToCd[i]);
			paramMap.put("applyToTypeCd", applyToTypeCd[i]);
			cnt =+ pscmprd0041Dao.saveVondorMgrDtl(paramMap);
		}
		return cnt;
	}
	
	

	//협력사 공지 게시판 총건수
	@Override
	public String VondorMgrCnt() throws Exception { 
		return pscmprd0041Dao.VondorMgrCnt();
	}

	//협력사 공지 게시판 디테일
	@Override
	public PSCMPRD0041VO selectVendorMgrView(String recommSeq) throws Exception {
		return pscmprd0041Dao.selectVendorMgrView(recommSeq);
	}

	//IBSHEET 세팅
	@Override
	public List<PSCMPRD0041DtlVO> selectSheet(DataMap paramMap) throws Exception {
		return pscmprd0041Dao.selectSheet(paramMap);
	}

	//수정
	@Override
	public int updateVondorMgr(DataMap paramMap) throws Exception {
		int cnt = 0;
		//디테일 삭제
		pscmprd0041Dao.deleteVondorMgrDtl(paramMap);
		//마스터 수정
		pscmprd0041Dao.updateVondorMgr(paramMap);
		cnt = 1;
		return cnt;
	}

	//엑셀
	@Override
	public List<Map<Object, Object>> selectPscmprd0041Export( Map<Object, Object> paramMap) throws Exception {
		return pscmprd0041Dao.selectPscmprd0041Export(paramMap);
	}
  
	//공지사항 ec연동여부 확인 
	@Override
	public boolean isNotiEcRegistered(Map<String, Object> paramMap) throws Exception {
		PSCMPRD0041VO vendorMgrInfo = new PSCMPRD0041VO();
		vendorMgrInfo.setAnnounSeq((String) paramMap.get("announSeq"));	
		vendorMgrInfo.setUseFlag((String) paramMap.get("useFlag"));
		vendorMgrInfo = (PSCMPRD0041VO) pscmprd0041Dao.selectNotiEcInfo(vendorMgrInfo); 
		
		boolean result = false;
		if(vendorMgrInfo != null) {
			if(vendorMgrInfo.getEcNotiNo()!=null && !vendorMgrInfo.getEcNotiNo().isEmpty()){
				result =  true;
			}
		}
		return result;
	}
	
	//같은 공지기간에 상품 중복 여부 체크
	@Override
	public boolean isNotiEcProductCheck(Map<String, Object> dataMap) throws Exception {
		int result;
		result = pscmprd0041Dao.selectNotiEcProductCheck(dataMap);
		if(result > 0){
			throw new Exception("해당 기간에 게시중인 공지가 존재합니다.");
		}else{
			return true;
		}
	}
	
}
