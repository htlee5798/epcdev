package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.weborder.controller.PEDMWEB0001Controller;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0110Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0110VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0110Service;
@Service("NEDMWEB0110Service")
public class NEDMWEB0110ServiceImpl implements NEDMWEB0110Service {

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0001Controller.class);


	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "15";

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private NEDMWEB0110Dao nedmweb0110dao;



	/**
     * 반품상품 저장
     * @param EdiRtnProdVO
     * @return HashMap<String, String>
     * @throws Exception
    */
	public HashMap<String, String> insertReturnProdData(NEDMWEB0110VO vo, HttpServletRequest request) throws Exception{

		/**
		 *  state
		 *  0  : 정상 등록         			( SUCCESS          )
		 *  1  : 중복 등록 오류    			( DUPLICATE DATA   )
		 *  2  : 반품불가 상품   			    ( EDI_RTN_PROD_V1@DL_MD_MARTNIS   0 row  )
		 *  3  : TED_PO_RRL_PROD 미등록      ( TED_PO_RRL_PROD  0 row inserted error )
		 * -1  : 시스템 오류    				(exception message)
		 */
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");

		/* ==[ 작업자 설정(로그인사용자 아이디 ] ============================== --*/
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		/* ================================================================ --*/




		/* ==[ 반품상품저장 ] ============================================== --*/

		/*-- 반품상품 점별 마스터 생성   (INSERT TED_PO_RRL_MST) --*/
		for(NEDMWEB0110VO insertParam : vo.getInsertParam()){
			String rrlReqNo= nedmweb0110dao.selectNewRtnReqNo();
			insertParam.setRrlReqNo(rrlReqNo);
			nedmweb0110dao.saveRtnProdMst(insertParam);
			/*-- ----------------------------------------------------*/

			/*-- 반품상품 점별 상품목록 생성 (INSERT TED_PO_RRL_PROD) ---*/
			int prodCnt = nedmweb0110dao.saveRtnProdList(insertParam);
			if(prodCnt <= 0){
				// 해당 EXCEPTION 발생할경우 참조 하고있는 TABLE DATA 이상함...
				throw new IllegalArgumentException("TPC_PO_RRL_PROD  0 row inserted error");
			}
		}

		/*-- ----------------------------------------------------*/

		/* 반품 등록 정상 Return Data set --------------------- --*/
		rtnData.put("state", 	"0");
		rtnData.put("message", 	"SUCCESS");
		/*-- ----------------------------------------------------*/

		/* ================================================================ --*/

		return rtnData;
	}

	/**
	 *  당일 반품등록 내역 조회(합계 / 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0110VO> NEDMWEB0110selectDayRtnProdList(NEDMWEB0110VO vo)  throws Exception {
		logger.debug("NEDMWEB0110selectDayRtnProdList");
		List<NEDMWEB0110VO> 	listData	= null;
		listData	= nedmweb0110dao.selectDayRtnProdList(vo);		// 목록
		return listData;
	}
}
