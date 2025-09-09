package com.lottemart.epc.main.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCPPRD0005Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("mainViewDao")
public class MainViewDao extends AbstractDAO {
	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 추가설명 정보
	 * Desc : 
	 * @Method Name : selectBoardList
	 * @param
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectBoardList() throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList("mainView.selectBoardList");
	}

	/**
	 * 추가설명 정보
	 * Desc : 
	 * @Method Name : selectPopupBoardList
	 * @param
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPopupBoardList() throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList("mainView.selectPopupBoardList");
	}

	/**
	 * 추가설명 정보
	 * Desc : 
	 * @Method Name : selectIntroCountList
	 * @param
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectIntroCountList(DataMap paramMap) throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList("mainView.selectIntroCountList", paramMap);
	}

	/**
	 * 위수탁 업체 로그인 시 미처리 문의건 확인
	 * @Method Name : selectTotalQnaChkCnt
	 * @param paramMap
	 * @return 미처리 문의 건수
	 * @throws SQLException
	 */
	public int selectTotalQnaChkCnt(DataMap paramMap) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("mainView.selectTotalQnaChkCnt", paramMap);
	}

	/**
	 * 업체 주문배송비관리
	 * 
	 * @param paramMap
	 * @return 배송설정여부
	 * @throws Exception
	 */
	public String selectEdiNochDeliYn(DataMap paramMap) throws SQLException {
		return (String) sqlMapClient.queryForObject("mainView.selectEdiNochDeliYn", paramMap);
	}

	/**
	 * 업체 정보 조회
	 * 
	 * @param String
	 * @return 업체 정보 조회
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorList(String[] cono) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cono", cono);
		return (List<DataMap>) sqlMapClient.queryForList("mainView.selectVendorList", paramMap);
	}

	/**
	 * 담당자 전화번호 조회
	 * 
	 * @param dataMap
	 * @return 담당자 전화번호 조회
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> vendorUserTelList(DataMap dataMap) throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList("mainView.vendorUserTelList", dataMap);
	}

	/**
	 * SMS 인증코드 발송
	 * 
	 * @param dataMap
	 * @return 인증코드 저장 여부
	 * @throws Exception
	 */
	public int smsAuthCodeInsert(DataMap dataMap) throws SQLException {
		try {
			sqlMapClient.insert("mainView.smsAuthCodeInsert", dataMap);
		} catch (Exception e) {
			return 2;
		}
		return 1;
	}

	/**
	 * SMS 인증코드 체크
	 * 
	 * @param dataMap
	 * @return 인증코드 저장 여부
	 * @throws Exception
	 */
	public DataMap smsCodeCheckSelect(DataMap dataMap) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("mainView.smsCodeCheckSelect", dataMap);
	}

	/**
	 * 인증 코드 저장
	 * 
	 * @param dataMap
	 * @return 인증 코드 저장
	 * @throws Exception
	 */
	public void smsCodeUpdate(DataMap dataMap) throws SQLException {
		sqlMapClient.update("mainView.smsCodeUpdate", dataMap);
	}

	/**
	 * 개인정보 수집이용 동의여부 저장
	 * @param dataMap
	 * @throws SQLException
	 */
	public void insertUserInfoApply(DataMap dataMap) throws SQLException {
		sqlMapClient.update("mainView.insertUserInfoApply", dataMap);
	}

	/**
	 * TIEMSTAMP (Milliseconds까지)
	 * @return
	 * @throws SQLException
	 */
	public String getSystimestampMs() throws SQLException {
		return (String) sqlMapClient.queryForObject("mainView.getSystimestampMs");
	}

}
