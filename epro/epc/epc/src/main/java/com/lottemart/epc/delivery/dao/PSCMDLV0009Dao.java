package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0009VO;

@Repository("PSCMDLV0009Dao")
public class PSCMDLV0009Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> getTetCodeList(PSCMDLV0009VO vo) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMDLV0009.getTetCodeList", vo);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMDLV0009.selectPartnerReturnList", paramMap);
	}

	public int insertTsaBatchLog2(PSCMDLV0009VO vo) throws SQLException {
		return (Integer) getSqlMapClientTemplate().update("PSCMDLV0009.insertTsaBatchLog2", vo);
	}

	public int updateTorOrderItem(PSCMDLV0009VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMDLV0009.updateTorOrderItem", bean);
	}

	public void updateTdeDeliMst(PSCMDLV0009VO bean) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateTdeDeliMst", bean);
	}

	public void updateTorOrderOrdStsCd(PSCMDLV0009VO bean) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateTorOrderOrdStsCd", bean);
	}

	public void updateTorOrderOrdStsCd11(PSCMDLV0009VO bean) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateTorOrderOrdStsCd11", bean);
	}

	public int selectExchFnshChk(PSCMDLV0009VO bean) throws SQLException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PSCMDLV0009.selectExchFnshChk", bean);
	}

	public List<DataMap> selectRtnMap(String ecOrderId) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMDLV0009.selectRtnMap", ecOrderId);
	}

	public void updateDeliMstByEnter(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateDeliMstByEnter", paramMap);
	}

	public void updateInvoiceByEnter(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateInvoiceByEnter", paramMap);
	}

	//TOR_ORDER 변경 (반품주문 변경)
	public void updateReturnTorderStatus(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateReturnTorderStatus", paramMap);
	}

	//TOR_ORDER_ITEM 변경 (반품주문 변경)
	public void updateReturnTorderItemStatus(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateReturnTorderItemStatus", paramMap);
	}

	//결제목록 조회
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDemandList(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().queryForList("PSCMDLV0009.selectDemandList", paramMap);
	}

	//적립 쿠폰 조회
	@SuppressWarnings("unchecked")
	public List<DataMap> selectAddPointCouponList(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().queryForList("PSCMDLV0009.selectAddPointCouponList", paramMap);
	}

	//쿠폰id 조회
	public String selectRepCouponId(String repCouponId) throws SQLException {
		return (String) getSqlMapClientTemplate().queryForObject("PSCMDLV0009.selectRepCouponId", repCouponId);
	}

	//쿠폰 사용여부 변경
	public void updateCouponIssuedUsedYn(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateCouponIssuedUsedYn", paramMap);
	}

	//TOR_DEMAND 결제 취소 (반품접수주문)
	public void updateReturnConfirmTorDemandStatus(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateReturnConfirmTorDemandStatus", paramMap);
	}

	//상담접수 현황의 처리상태 Update
	public void updateTorCounselProcessStatus(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateTorCounselProcessStatus", paramMap);
	}

	//원주문 반품완료
	public void updateOrgTorderReturnCompleteStatus(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateOrgTorderReturnCompleteStatus", paramMap);
	}

	//tsa_batch_log_3 등록
	public void insertTsaBatchLog3(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().insert("PSCMDLV0009.insertTsaBatchLog3", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectSppDeliType(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().queryForList("PSCMDLV0009.selectSppDeliType", paramMap);
	}

	public void insertSppInfo(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.insertSppInfo", paramMap);
	}

	public void updateSppInfoForReturnEnd(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateSppInfoForReturnEnd", paramMap);
	}

	// 반품배송비의 주문번호, 매출확정여부 조회
	public DataMap selectRtnDeliAmtOrder(DataMap paramMap) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("PSCMDLV0009.selectRtnDeliAmtOrder", paramMap);
	}

	// 반품배송비 주문의  매출확정처리
	public void updateRtnDeliAmtOrder(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateRtnDeliAmtOrder", paramMap);
	}

	// 반품배송비 주문상품의  매출확정처리
	public void updateRtnDeliAmtOrderItem(DataMap paramMap) throws SQLException {
		getSqlMapClientTemplate().update("PSCMDLV0009.updateRtnDeliAmtOrderItem", paramMap);
	}

}