/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 05. 31. 오후 2:36:30
 * @author      : kslee 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.system.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.oneapp.trader.domain.TraderCondition;
import com.lottemart.common.oneapp.trader.domain.TraderType;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.system.dao.PSCMSYS0004Dao;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.model.PSCMSYS0004VO;
import com.lottemart.epc.system.service.PSCMSYS0004Service;

import lcn.module.common.conts.APIUrlConstant;
/**
 * @Class Name : PSCMSYS0004ServiceImpl
 * @Description : 업체정보 목록조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:37:05 kslee
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMSYS0004Service")
public class PSCMSYS0004ServiceImpl implements PSCMSYS0004Service {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private PSCMSYS0004Dao pscmsys0004Dao;

	/**
	 * Desc : 업체정보
	 * @Method Name : selectVendorInfoView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	@Override
	public PSCMSYS0003VO selectVendorInfoView(String vendorId) throws Exception {
		PSCMSYS0003VO vo = pscmsys0004Dao.selectVendorInfoView(vendorId);
		return vo;
	}

	/**
	 * Desc : 업체정보 수정
	 * @Method Name : updateVendorInfo
	 * @param PSCMSYS0003VO
	 * @throws Exception
	 * @return 결과수
	 */
	@Override
	public int updateVendorInfo(PSCMSYS0003VO vo) throws Exception {
		return pscmsys0004Dao.updateVendorInfo(vo);
	}

	/**
	 * Desc : 업체담당자조회
	 * @Method Name : selectVendorUserList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0002VO
	 */
	@Override
	public List<PSCMSYS0002VO> selectVendorUserList(Map<String, String> paramMap) throws Exception {
		return pscmsys0004Dao.selectVendorUserList(paramMap);
	}

	/**
	 * Desc : 업체주소조회
	 * @Method Name : selectVendorAddrList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0004VO
	 */
	@Override
	public List<PSCMSYS0004VO> selectVendorAddrList(Map<String, String> paramMap) throws Exception {
		return pscmsys0004Dao.selectVendorAddrList(paramMap);
	}

	/**
	 * Desc : 업체기준배송비조회
	 * @Method Name : selectVendorDeliAmtList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0001VO
	 */
	@Override
	public List<PSCMSYS0001VO> selectVendorDeliAmtList(Map<String, String> paramMap) throws Exception {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date ecDate = simpledateformat.parse("2020-04-28");
		Date toDate = new Date();
		String deliDicnCd = paramMap.get("deliDivnCd");
		String nextDate = paramMap.get("nextDate");
		if(toDate.compareTo(ecDate) < 0 && "20".equals(deliDicnCd) && nextDate != null){
			paramMap.put("nextDate", "20200428");
		}
		return pscmsys0004Dao.selectVendorDeliAmtList(paramMap);
	}

	/**
	 * Desc : 업체담당자 저장
	 * @Method Name : vendorUserListSave
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0001VO
	 */
	@Override
	public int vendorUserListSave(HttpServletRequest request) throws Exception {

		int resultCnt = 0;

		String[] sStatus = request.getParameterValues("S_STATUS");
		String[] vendorId = request.getParameterValues("vendorId");
		String[] vendorSeq = request.getParameterValues("vendorSeq");
		String[] utakType = request.getParameterValues("utakType");
		String[] utakNm = request.getParameterValues("utakNm");
		String[] utakDeptNm = request.getParameterValues("utakDeptNm");
		String[] utakPositionNm = request.getParameterValues("utakPositionNm");
		String[] utakTelNo = request.getParameterValues("utakTelNo");
		String[] utakCellNo = request.getParameterValues("utakCellNo");
		String[] utakFaxNo = request.getParameterValues("utakFaxNo");
		String[] email = request.getParameterValues("email");
		String[] smsRecvYn = request.getParameterValues("smsRecvYn");
		String[] rmk = request.getParameterValues("rmk");
		String[] zipCd = request.getParameterValues("zipCd");
		String[] addr1Nm = request.getParameterValues("addr1Nm");
		String[] addr2Nm = request.getParameterValues("addr2Nm");
		String[] valiYN = request.getParameterValues("valiYN");

		PSCMSYS0002VO bean = new PSCMSYS0002VO();

		for (int i = 0; i < sStatus.length; i++) {

			if ("I".equals(sStatus[i]) || "U".equals(sStatus[i])) {
				bean.setVendorId(vendorId[i]);
				bean.setVendorSeq(vendorSeq[i]);
				bean.setUtakType(utakType[i]);
				bean.setUtakNm(utakNm[i]);
				bean.setUtakDeptNm(utakDeptNm[i]);
				bean.setUtakPositionNm(utakPositionNm[i]);
				bean.setUtakTelNo(utakTelNo[i]);
				bean.setUtakCellNo(utakCellNo[i]);
				bean.setUtakFaxNo(utakFaxNo[i]);
				bean.setEmail(email[i]);
				bean.setSmsRecvYn(smsRecvYn[i]);
				bean.setRmk(rmk[i]);
				bean.setZipCd(zipCd[i]);
				bean.setAddr1Nm(addr1Nm[i]);
				bean.setAddr2Nm(addr2Nm[i]);
				bean.setValiYN(valiYN[i]);
				bean.setModId(vendorId[i]);

				resultCnt += pscmsys0004Dao.updateVendorUser(bean);
				commonDao.commit();

			} else if ("D".equals(sStatus[i])) {
				bean.setVendorId(vendorId[i]);
				bean.setVendorSeq(vendorSeq[i]);

				resultCnt += pscmsys0004Dao.deleteVendorUser(bean);
			}
		}
		
		return resultCnt;
	}

	/**
	 * Desc : 업체주소 저장
	 * @Method Name : vendorAddrListSave
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0004VO
	 */
	@Override
	public int vendorAddrListSave(HttpServletRequest request) throws Exception {

		RestAPIUtil restAPIUtil = new RestAPIUtil();
		int resultCnt = 0;

		String[] sStatus = request.getParameterValues("S_STATUS");
		String[] vendorId = request.getParameterValues("vendorId");
		String[] addrSeq = request.getParameterValues("addrSeq");
		String[] addrKindCd = request.getParameterValues("addrKindCd");
		String[] zipCd = request.getParameterValues("zipCd");
		String[] addr_1_nm = request.getParameterValues("addr_1_nm");
		String[] addr_2_nm = request.getParameterValues("addr_2_nm");
		String[] cellNo = request.getParameterValues("cellNo");
		String[] useYn = request.getParameterValues("useYn");

		//oneapp open-api 호출
		List<String> addAddrSeqs = new ArrayList<String>();
		List<String> modifyAddrSeqs = new ArrayList<String>();
		String responseJsonString = "";
		TraderCondition traderCondition = new TraderCondition();
		traderCondition.setTraderType(TraderType.VENDOR);

		PSCMSYS0004VO bean = new PSCMSYS0004VO();

		for (int i = 0; i < sStatus.length; i++) {

			boolean sendYn = false; // addrKindCd 01,02 일때만 oneapp api 호출
			switch (Integer.parseInt(addrKindCd[i])) {
			case 1: sendYn = true;
				break;
			case 2: sendYn = true;
				break;
			case 3: sendYn = false;
				break;
			default: sendYn = false;
			}

			if ("I".equals(sStatus[i]) || "U".equals(sStatus[i])) {
				bean.setVendorId(vendorId[i]);
				bean.setAddrSeq(addrSeq[i]);
				bean.setAddrKindCd(addrKindCd[i]);
				bean.setZipCd(zipCd[i]);
				bean.setAddr_1_nm(addr_1_nm[i]);
				bean.setAddr_2_nm(addr_2_nm[i]);
				bean.setCellNo(cellNo[i]);
				bean.setUseYn(useYn[i]);
				bean.setModId(vendorId[i]);

				resultCnt += pscmsys0004Dao.updateVendorAddr(bean);
				if (sendYn) {
					traderCondition.setTraderCd(vendorId[i]);
				}
			} else if (sStatus[i].equals("D")) {
				bean.setVendorId(vendorId[i]);
				bean.setAddrSeq(addrSeq[i]);

				resultCnt += pscmsys0004Dao.deleteVendorAddr(bean);
			}
		}
		//oneapp open-api 호출
		if(traderCondition.getTraderCd() != null) {
			restAPIUtil.sendRestCall(APIUrlConstant.TRADER_ADDRESS_SAVE_API_URL, HttpMethod.POST, traderCondition, true);
		}
		return resultCnt;
	}

	/**
	 * Desc : 업체배송비기준 저장
	 * @Method Name : vendorDeliListSave
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0001VO
	 */
	@Override
	public int vendorDeliListSave(HttpServletRequest request) throws Exception {

		int resultCnt = 0;

		String[] sStatus = request.getParameterValues("S_STATUS");
		String[] vENDOR_ID = request.getParameterValues("VENDOR_ID");
		String[] aPPLY_START_DY = request.getParameterValues("APPLY_START_DY");
		String[] aPPLY_END_DY = request.getParameterValues("APPLY_END_DY");
		String[] dELI_BASE_MIN_AMT = request.getParameterValues("DELI_BASE_MIN_AMT");
		String[] dELI_BASE_MAX_AMT = request.getParameterValues("DELI_BASE_MAX_AMT");
		String[] dELIVERY_AMT = request.getParameterValues("DELIVERY_AMT");
		String[] dELIVERY_DIVN_CD = request.getParameterValues("DELI_DIVN_CD");

		PSCMSYS0001VO bean = new PSCMSYS0001VO();

		for (int i = 0; i < sStatus.length; i++) {

			if ("I".equals(sStatus[i]) || "U".equals(sStatus[i])) {

				bean.setVENDOR_ID(vENDOR_ID[i]);
				bean.setAPPLY_START_DY(aPPLY_START_DY[i]);
				bean.setAPPLY_END_DY(aPPLY_END_DY[i]);
				bean.setDELI_BASE_MIN_AMT(dELI_BASE_MIN_AMT[i]);
				bean.setDELI_BASE_MAX_AMT(dELI_BASE_MAX_AMT[i]);
				bean.setPROD_CD("0470000045576");
				bean.setDELIVERY_AMT(dELIVERY_AMT[i]);
				bean.setDELI_DIVN_CD(dELIVERY_DIVN_CD[i]); // 배송비 구분 [DE020 > 10:배송, 20:반품, 30:교환]
				bean.setMOD_ID(vENDOR_ID[i]);

				resultCnt += pscmsys0004Dao.updateVendorDeliAmt(bean);

				//반품배송비(20)만 수정 시
				if ("20".equals(bean.getDELI_DIVN_CD()) && "99991231".equals(bean.getAPPLY_END_DY())) {
					pscmsys0004Dao.updateVendorReturnAmt(bean);
				}
				//주문배송비(10)만 수정시
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("nextDate", "99991231");
				paramMap.put("vendorId", vENDOR_ID[i]);
				paramMap.put("deliDivnCd", "20");
				List<PSCMSYS0001VO> list = pscmsys0004Dao.selectVendorDeliAmtList(paramMap);
				if ("10".equals(bean.getDELI_DIVN_CD()) && !"99991231".equals(bean.getAPPLY_END_DY()) && list.size() != 0) {
					PSCMSYS0001VO reTurnBean = new PSCMSYS0001VO();
					reTurnBean.setVENDOR_ID(vENDOR_ID[i]);
					reTurnBean.setAPPLY_START_DY(list.get(0).getAPPLY_START_DY());
					reTurnBean.setAPPLY_END_DY(aPPLY_END_DY[i]);
					reTurnBean.setDELI_BASE_MIN_AMT("0");
					reTurnBean.setDELI_BASE_MAX_AMT("0");
					reTurnBean.setPROD_CD("0470000045576");
					reTurnBean.setDELIVERY_AMT(list.get(0).getDELIVERY_AMT());
					reTurnBean.setDELI_DIVN_CD("20"); // 배송비 구분 [DE020 > 10:배송, 20:반품, 30:교환]
					reTurnBean.setMOD_ID(vENDOR_ID[i]);
					pscmsys0004Dao.updateVendorDeliAmt(reTurnBean);

				} else if ("10".equals(bean.getDELI_DIVN_CD()) && "99991231".equals(bean.getAPPLY_END_DY()) && list.size() != 0) {
					PSCMSYS0001VO reTurnBean = new PSCMSYS0001VO();
					reTurnBean.setVENDOR_ID(vENDOR_ID[i]);
					reTurnBean.setAPPLY_START_DY(aPPLY_START_DY[i]);
					reTurnBean.setAPPLY_END_DY(aPPLY_END_DY[i]);
					reTurnBean.setDELI_BASE_MIN_AMT("0");
					reTurnBean.setDELI_BASE_MAX_AMT("0");
					reTurnBean.setPROD_CD("0470000045576");
					reTurnBean.setDELIVERY_AMT(list.get(0).getDELIVERY_AMT());
					reTurnBean.setDELI_DIVN_CD("20"); // 배송비 구분 [DE020 > 10:배송, 20:반품, 30:교환]
					reTurnBean.setMOD_ID(vENDOR_ID[i]);
					pscmsys0004Dao.updateVendorDeliAmt(reTurnBean);
				}
			}
		}

		return resultCnt;
	}

}
