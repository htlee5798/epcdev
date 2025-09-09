package com.lottemart.epc.common.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0005VO;

public interface PSCMCOM0005Service {

	public List<DataMap> getTetCodeList(PSCMCOM0005VO vo) throws Exception;

	public void updatePartnerFirmsOrderItem(PSCMCOM0005VO vo) throws Exception;

	public void updatePartnerFirmsDeliMst(PSCMCOM0005VO vo) throws Exception;

	public int selectPartnerFirmsHodecoInfoCnt(PSCMCOM0005VO vo) throws Exception;

	public void updatePartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws Exception;

	public void insertPartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws Exception;

	public DataMap selectOrderItemCount(PSCMCOM0005VO vo) throws Exception;

	public DataMap selectHodecoInfoCount(PSCMCOM0005VO vo) throws Exception;

	public String deliveryCheck(PSCMCOM0005VO vo) throws Exception;

	public Integer selectDeliveryStatusReverceCnt(PSCMCOM0005VO vo) throws Exception;

	public DataMap selectDeliveryVendor(PSCMCOM0005VO vo) throws Exception;
}
