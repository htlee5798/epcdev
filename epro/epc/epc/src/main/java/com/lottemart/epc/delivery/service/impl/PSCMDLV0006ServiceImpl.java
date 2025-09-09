package com.lottemart.epc.delivery.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import lcn.module.framework.idgen.IdGnrService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0006Dao;
import com.lottemart.epc.delivery.model.PSCMDLV0006VO;
import com.lottemart.epc.delivery.service.PSCMDLV0006Service;


@Service("PSCMDLV0006Service")
public class PSCMDLV0006ServiceImpl  implements PSCMDLV0006Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0006ServiceImpl.class);
	
	@Autowired
	private PSCMDLV0006Dao pscmdlv0006Dao;

	@Resource(name = "SmsIdGnrService")
	private IdGnrService idgenService;	

	public String selectDeliveryDlayBlockYn(String ordDy) throws Exception {
		return pscmdlv0006Dao.selectDeliveryDlayBlockYn(ordDy);
	}
	
	public List<DataMap> selectDeliveryDlaySMSTarget(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectDeliveryDlaySMSTarget(vo);
	}
	
	public List<DataMap> getTetCodeList(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.getTetCodeList(vo);
	}	
	
	public List<DataMap> selectAllOnlineStore() throws Exception {
		return pscmdlv0006Dao.selectAllOnlineStore();
	}	
	
	public List<DataMap> selectPartnerFirmsList(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectPartnerFirmsList(vo);
	}
	
	public List<DataMap> selectPartnerPopupList(DataMap paramMap) throws Exception {
		return pscmdlv0006Dao.selectPartnerPopupList(paramMap);
	}
	
	public List<DataMap> selectPartnerFirmsPopupList(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectPartnerFirmsPopupList(vo);
	}
	
	public List<DataMap> selectPartnerFirmsListExcel(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectPartnerFirmsListExcel(vo);
	}
	
	public void updatePartnerFirmsOrderItem(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updatePartnerFirmsOrderItem(vo);
	}	
	
	public void updatePartnerFirmsOrderItem_holy(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updatePartnerFirmsOrderItem_holy(vo);
	}	
	
	public void updatePartnerFirmsDeliMst(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updatePartnerFirmsDeliMst(vo);
	}	
	
	public int selectPartnerFirmsHodecoInfoCnt(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectPartnerFirmsHodecoInfoCnt(vo);
	}	
	
	public void updatePartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updatePartnerFirmsHodecoInfo(vo);
	}		

	public void insertPartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.insertPartnerFirmsHodecoInfo(vo);
	}		
	
	public DataMap selectSendSMSInfo(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectSendSMSInfo(vo);
	}	
	
	public List<DataMap> selectPartnerFirmsSendSMSList(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectPartnerFirmsSendSMSList(vo);
	}	
	
	public void insertSendSMS(LtsmsVO vo) throws Exception {
		String SERIALNO = idgenService.getNextStringId();
//		logger.debug("SERIALNO ==================" + SERIALNO);
		vo.setSERIALNO(SERIALNO);		
		pscmdlv0006Dao.insertSendSMS(vo);
	}		
	
	/**
	 * LMS 전송 
	 * 2016.04.27 추가 
	 */
	@Override
	public void insertSendLMS(DataMap dm) throws Exception {
		try {		
			pscmdlv0006Dao.insertSendLMS(dm);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}

	}
		
	public void updateSmsSendYn(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updateSmsSendYn(vo);
	}			
	
	public DataMap selectOrderItemCount(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectOrderItemCount(vo);
	}		
	
	public DataMap selectHodecoInfoCount(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectHodecoInfoCount(vo);
	}	
	
	public int selectSaleConfirmCount(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectSaleConfirmCount(vo);
	}		
	
	public int selectSaleConfirmCountPartOk(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectSaleConfirmCountPartOk(vo);
	}		
	
	public int selectSaleConfirmCountPartFalse(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectSaleConfirmCountPartFalse(vo);
	}	
	
	public void updateDeliveryStatusOk(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updateDeliveryStatusOk(vo);
	}			
		
	public DataMap selectOrderInfo(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectOrderInfo(vo);
	}		
	
	public String selectConfirmSiteCd(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.selectConfirmSiteCd(vo);
	}		
	
	public int insertTsaBatchLog2(PSCMDLV0006VO vo) throws Exception {
		return pscmdlv0006Dao.insertTsaBatchLog2(vo);
	}		
	
	public void updateOrderStatus(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.updateOrderStatus(vo);
	}		
	
	public void insertDoShippingGeneralLog(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.insertDoShippingGeneralLog(vo);
	}		
	
	public void insertTorCounsel(PSCMDLV0006VO vo) throws Exception {
		pscmdlv0006Dao.insertTorCounsel(vo);
	}

	@Override
	public String selectCounselSeq() throws Exception {
		return pscmdlv0006Dao.selectCounselSeq();
	}

	@Override
	public List< DataMap >selectCounselContent(String counselSeq) throws Exception {
		return pscmdlv0006Dao.selectCounselContent(counselSeq);
	}

	@Override
	public int selectHodecoNoChk(DataMap dm) throws Exception {
		return pscmdlv0006Dao.selectHodecoNoChk(dm);
	}

	@Override
	public List<DataMap> selectDeliHistList(PSCMDLV0006VO vo) throws Exception {
	  return pscmdlv0006Dao.selectDeliHistList(vo);
		
	}		
	
	@Override
	public String selectEcOrderYn(String deliNo) throws Exception {
		return pscmdlv0006Dao.selectEcOrderYn(deliNo);
	}

	@Override
	public boolean selectDeliveryReverseCheck(PSCMDLV0006VO vo) {

		String deliStatusCd = pscmdlv0006Dao.selectDeliveryReverseCheck(vo);
		String venDeliStatusCd = vo.getVenDeliStatusCd();
			switch (deliStatusCd){
				case "30"://상품준비중 (ven 미확인(01)/상품준비중(02)/배송지연(09))
					return true;
				case "31"://피킹중|배송준비중 (ven 발송예정(03))
					if("01".equals(venDeliStatusCd)){
						logger.error("배송 상태 역행 발생 venDeliStatusCd : 03(발송예정) ->" + venDeliStatusCd);
						return false;
					}
					return true;
				case "45"://상차완료확인|배송중 (ven 배송중(09))
					if("09".equals(venDeliStatusCd) || "05".equals(venDeliStatusCd)){
						return true;
					}
					else{
						logger.error("배송 상태 역행 발생 venDeliStatusCd : 09(배송중) ->" + venDeliStatusCd);
						return false;
					}
				case "51"://배송 완료 (ven 배송완료(05))
					logger.error("배송 상태 역행 발생 venDeliStatusCd : 05(배송완료) ->" + venDeliStatusCd);
					return false;
			}
		return true;
	}
}