package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0006VO;

public interface PSCMDLV0006Service {

	public String selectDeliveryDlayBlockYn(String ordDy) throws Exception;

	public List<DataMap> selectDeliveryDlaySMSTarget(PSCMDLV0006VO vo) throws Exception;
	
	public List<DataMap> getTetCodeList(PSCMDLV0006VO vo) throws Exception;
	
	public List<DataMap> selectAllOnlineStore() throws Exception;
	
	public List<DataMap> selectPartnerFirmsList(PSCMDLV0006VO vo) throws Exception;
	
	public List<DataMap> selectPartnerPopupList(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectPartnerFirmsPopupList(PSCMDLV0006VO vo) throws Exception;
	
	public List<DataMap> selectPartnerFirmsListExcel(PSCMDLV0006VO vo) throws Exception;
	
	public void updatePartnerFirmsOrderItem(PSCMDLV0006VO vo) throws Exception;
	
	public void updatePartnerFirmsOrderItem_holy(PSCMDLV0006VO vo) throws Exception;
	
	public void updatePartnerFirmsDeliMst(PSCMDLV0006VO vo) throws Exception;
	
	public int selectPartnerFirmsHodecoInfoCnt(PSCMDLV0006VO vo) throws Exception;
	
	public void updatePartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws Exception;
	
	public void insertPartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws Exception;

	public DataMap selectSendSMSInfo(PSCMDLV0006VO vo) throws Exception;
	
	public List<DataMap> selectPartnerFirmsSendSMSList(PSCMDLV0006VO vo) throws Exception;
	
	public void insertSendSMS(LtsmsVO vo) throws Exception;

	public void insertSendLMS(DataMap dm) throws Exception;
	
	public void updateSmsSendYn(PSCMDLV0006VO vo) throws Exception;
	
	public DataMap selectOrderItemCount(PSCMDLV0006VO vo) throws Exception;
	
	public DataMap selectHodecoInfoCount(PSCMDLV0006VO vo) throws Exception;
	
	public void updateDeliveryStatusOk(PSCMDLV0006VO vo) throws Exception;
	
	public int selectSaleConfirmCount(PSCMDLV0006VO vo) throws Exception;
	
	public int selectSaleConfirmCountPartOk(PSCMDLV0006VO vo) throws Exception;
	
	public int selectSaleConfirmCountPartFalse(PSCMDLV0006VO vo) throws Exception;
	
	public DataMap selectOrderInfo(PSCMDLV0006VO vo) throws Exception;
	
	public String selectConfirmSiteCd(PSCMDLV0006VO vo) throws Exception;
	
	public int insertTsaBatchLog2(PSCMDLV0006VO vo) throws Exception;
	
	public void updateOrderStatus(PSCMDLV0006VO vo) throws Exception;
	
	public void insertDoShippingGeneralLog(PSCMDLV0006VO vo) throws Exception;
	
	public void insertTorCounsel(PSCMDLV0006VO vo) throws Exception;
	
	public String selectCounselSeq() throws Exception;
	
	public int selectHodecoNoChk(DataMap dm) throws Exception;
	
	// CounselContent value
	public List< DataMap >selectCounselContent(String counselSeq) throws Exception;

	public List<DataMap> selectDeliHistList(PSCMDLV0006VO searchVO) throws Exception;
	
	public String selectEcOrderYn(String deliNo) throws Exception;

	public boolean selectDeliveryReverseCheck(PSCMDLV0006VO vo);
}
