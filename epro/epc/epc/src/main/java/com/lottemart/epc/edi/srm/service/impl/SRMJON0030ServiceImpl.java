package com.lottemart.epc.edi.srm.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.srm.dao.SRMJON0030Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON0040Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0030ListVO;
import com.lottemart.epc.edi.srm.model.SRMJON0030VO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0030Service;

/**
 * 입점상담 / 입점상담신청  / 1차 스크리닝 ServiceImpl
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.06  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Service("srmjon0030Service")
public class SRMJON0030ServiceImpl implements SRMJON0030Service {

	@Autowired
	private SRMJON0030Dao srmjon0030Dao;

	@Autowired
	private SRMJON0040Dao srmjon0040Dao;

	@Autowired
	private ConfigurationService config;

	/**
	 * 1차 스크리닝 LIST 조회
	 * @param SRMJON0030VO
	 * @return List<SRMJON0030VO>
	 * @throws Exception
	 */
	public List<SRMJON0030VO> selectScreeningList(SRMJON0030VO vo) throws Exception {
		return srmjon0030Dao.selectScreeningList(vo);
	}

	/**
	 * 1차 스크리닝 결과 등록
	 * @param SRMJON0030VO
	 * @throws Exception
	 */
	public Map<String,Object> insertScreeningList(SRMJON0030ListVO listVo, HttpServletRequest request) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		SRMJON0030VO tempVO = null;

		if(listVo.size() !=0) {
			tempVO = listVo.get(0);
		}

		if(tempVO != null) {
			SRMJON0040VO srmjon0040VO = new SRMJON0040VO();
			srmjon0040VO.setHouseCode(session.getHouseCode());
			srmjon0040VO.setSellerCode(session.getIrsNo());
			srmjon0040VO.setChannelCode(tempVO.getChannelCode());
			srmjon0040VO.setCatLv1Code(tempVO.getCatLv1Code());

			resultMap = consultStatusCheck(srmjon0040VO);	// 채널, 대분류로 동일한 신청내역 체크
			if (!resultMap.get("message").equals("SUCCESS")) {
				return resultMap;
			}
			
			for(SRMJON0030VO vo : listVo) {
				vo.setIrsNo(session.getIrsNo());
				vo.setShipperType(session.getShipperType());
				if(!StringUtil.isEmpty(vo.getEvTplNo())){
					srmjon0030Dao.insertScreeningList(vo);
				}
			}
		}

		resultMap.put("message","SUCCESS");
		return resultMap;
	}

	/**
	* 대분류 코드 조회
	* @param SRMJON0030VO
	* @return List<SRMJON0030VO>
	* @throws Exception
	*/
	public List<SRMJON0030VO> selectCatLv1CodeList(SRMJON0030VO vo) throws Exception {
		return srmjon0030Dao.selectCatLv1CodeList(vo);
	}
	
	/**
	 * 채널, 대분류로 동일한 신청내역 체크
	 * @param SRMJON0040VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> consultStatusCheck(SRMJON0040VO srmjon0040VO) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		List<String> statusList = srmjon0040Dao.selectConsultStatusList(srmjon0040VO);
		int statusCnt = 0;
		for(int i=0; i<statusList.size(); i++) {
			if(i < 3) {
				if (statusList.get(i).equals("C02") || statusList.get(i).equals("D05") || statusList.get(i).equals("G08") || statusList.get(i).equals("H04")) {
					statusCnt++;
				}
			}
		}
		if(statusCnt >=3) {
			resultMap.put("message","FAIL-REFUSE");
			return resultMap;
		}

		/*if(srmjon0040Dao.selectConsultStatus(srmjon0040VO) > 0) {
			resultMap.put("message","FAIL-SUCCESS");
			return resultMap;
		}*/

		//2019-05-08 오승현
		//같은 대분류로 입점 심사가 진행중일 때 같은 대분류 입점 금지 삭제
		/*if(srmjon0040Dao.selectConsultIngStatus(srmjon0040VO) > 0) {
			resultMap.put("message","FAIL-ING");
			return resultMap;
		}*/
		
		resultMap.put("message", "SUCCESS");
		return resultMap;
	}
}
