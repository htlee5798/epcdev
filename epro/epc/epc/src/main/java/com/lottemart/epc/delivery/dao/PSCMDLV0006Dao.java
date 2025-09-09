package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0006VO;

@Repository("PSCMDLV0006Dao")
public class PSCMDLV0006Dao extends AbstractDAO{
	@Autowired
	private SqlMapClient sqlMapClient;
	public Object selectDeliHistList;

	public String selectDeliveryDlayBlockYn(String ordDy) throws SQLException{
		return (String)sqlMapClient.queryForObject("PSCMDLV0006.selectDeliveryDlayBlockYn", ordDy);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryDlaySMSTarget(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectDeliveryDlaySMSTarget", vo);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getTetCodeList(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.getTetCodeList", vo);
	}	

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAllOnlineStore() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectAllOnlineStore");
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsList(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectPartnerFirmsList", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsPopupList(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectPartnerFirmsPopupList", vo);
	}
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerPopupList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0006.selectPartnerPopupList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsListExcel(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectPartnerFirmsListExcel", vo);
	}
	
	
	public void updatePartnerFirmsOrderItem(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.updatePartnerFirmsOrderItem", vo);
	}
	
	public void updatePartnerFirmsOrderItem_holy(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.updatePartnerFirmsOrderItem_holy", vo);
	}
	
	public void updatePartnerFirmsDeliMst(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.updatePartnerFirmsDeliMst", vo);
	}
	
	public int selectPartnerFirmsHodecoInfoCnt(PSCMDLV0006VO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectPartnerFirmsHodecoInfoCnt", vo);
	}
	
	public void updatePartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.updatePartnerFirmsHodecoInfo", vo);
	}

	public void insertPartnerFirmsHodecoInfo(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.insertPartnerFirmsHodecoInfo", vo);
	}	
	
	public DataMap selectSendSMSInfo(PSCMDLV0006VO vo) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectSendSMSInfo", vo);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsSendSMSList(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectPartnerFirmsSendSMSList", vo);
	}

	public void insertSendSMS(LtsmsVO vo) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.insertSendSMS", vo);
	}	
	
	public void insertSendLMS(DataMap dm) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.insertSendLMS", dm);
	}
	
	public void updateSmsSendYn(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.updateSmsSendYn", vo);
	}	

	public DataMap selectOrderItemCount(PSCMDLV0006VO vo) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectOrderItemCount", vo);
	}	
	
	public DataMap selectHodecoInfoCount(PSCMDLV0006VO vo) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectHodecoInfoCount", vo);
	}		
	
	public void updateDeliveryStatusOk(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().update("PSCMDLV0006.updateDeliveryStatusOk", vo);
	}	
	
	public int selectSaleConfirmCount(PSCMDLV0006VO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectSaleConfirmCount", vo);
	}
	
	public int selectSaleConfirmCountPartOk(PSCMDLV0006VO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectSaleConfirmCountPartOk", vo);
	}
	
	public int selectSaleConfirmCountPartFalse(PSCMDLV0006VO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectSaleConfirmCountPartFalse", vo);
	}	
	
	public DataMap selectOrderInfo(PSCMDLV0006VO vo) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectOrderInfo", vo);
	}	
	
	public String selectConfirmSiteCd(PSCMDLV0006VO vo) throws SQLException{
		return (String)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectConfirmSiteCd", vo);
	}	
	
	
	public int insertTsaBatchLog2(PSCMDLV0006VO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().update("PSCMDLV0006.insertTsaBatchLog2", vo);
	}	
		
	public void updateOrderStatus(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.updateOrderStatus", vo);
	}		
	
	public void insertDoShippingGeneralLog(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.insertDoShippingGeneralLog", vo);
	}		
	
	public void insertTorCounsel(PSCMDLV0006VO vo) throws SQLException{
		getSqlMapClientTemplate().insert("PSCMDLV0006.insertTorCounsel", vo);
	}		
	public String selectCounselSeq() throws SQLException{
		return (String) getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectCounselSeq");
	}

	public List<DataMap> selectCounselContent(String counselSeq) throws SQLException {
		return getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectCounselContent", counselSeq);
	}
	
	public int selectHodecoNoChk(DataMap dm) throws SQLException {
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectHodecoNoChk", dm);
	}
 
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliHistList(PSCMDLV0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0006.selectDeliHistList", vo);
	}
		
	public String selectEcOrderYn(String deliNo) throws SQLException{
		return (String)sqlMapClient.queryForObject("PSCMDLV0006.selectEcOrderYn", deliNo);
	}

    public String selectDeliveryReverseCheck(PSCMDLV0006VO vo) {
		return (String)getSqlMapClientTemplate().queryForObject("PSCMDLV0006.selectDeliveryReverseCheck", vo);
	}
}
