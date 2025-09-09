/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 05. 31. 오후 2:38:50
 * @author      : kslee 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.system.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.model.PSCMSYS0004VO;

/**
 * @Class Name : PSCMBRD0013Dao
 * @Description : 상품평 목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:39:01 kslee
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSYS0004Dao")
public class PSCMSYS0004Dao extends AbstractDAO {
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0004Dao.class);


	/**
	 * Desc : 업체정보 조회
	 * @Method Name : selectVendorInfoView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	public PSCMSYS0003VO selectVendorInfoView(String vendorId) throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vendorId", vendorId);			
		return (PSCMSYS0003VO)getSqlMapClientTemplate().queryForObject("pscmsys0004.selectVendorInfoView", vendorId);
	}

	/**
	 * Desc : 업체정보 수정
	 * @Method Name : updateVendorInfo
	 * @param PSCMSYS0003VO
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateVendorInfo(PSCMSYS0003VO vo) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.updateVendorInfo", vo);
	}

	/**
	 * Desc : 업체담당자정보 조회
	 * @Method Name : selectVendorUserList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0002VO
	 */
	public List<PSCMSYS0002VO> selectVendorUserList(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmsys0004.selectVendorUserList", paramMap);
	}

	/**
	 * Desc : 업체주소정보 조회
	 * @Method Name : selectVendorAddrList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0004VO
	 */
	public List<PSCMSYS0004VO> selectVendorAddrList(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmsys0004.selectVendorAddrList", paramMap);
	}

	/**
	 * Desc : 업체기준배송비정보 조회
	 * @Method Name : selectVendorDeliAmtList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0001VO
	 */
	public List<PSCMSYS0001VO> selectVendorDeliAmtList(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmsys0004.selectVendorDeliAmtList", paramMap);
	}

	/**
	 * 업체담당자 수정
	 * Desc : 
	 * @Method Name : updateVendorUser
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateVendorUser(PSCMSYS0002VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.updateVendorUser", bean);
	}	

	/**
	 * 업체담당자 삭제
	 * Desc : 
	 * @Method Name : deleteVendorUser
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deleteVendorUser(PSCMSYS0002VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.deleteVendorUser", bean);
	}
	
	/**
	 * 업체주소 수정
	 * Desc : 
	 * @Method Name : updateVendorAddr
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateVendorAddr(PSCMSYS0004VO bean) throws SQLException{
		logger.debug("bean===>"+bean+"<============");
		
		return getSqlMapClientTemplate().update("pscmsys0004.updateVendorAddr", bean);
	}	

	/**
	 * 업체주소 삭제
	 * Desc : 
	 * @Method Name : deleteVendorAddr
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deleteVendorAddr(PSCMSYS0004VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.deleteVendorAddr", bean);
	}	

	/**
	 * 업체기준배송비 등록
	 * Desc : 
	 * @Method Name : insertVendorDeliAmt
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertVendorDeliAmt(PSCMSYS0001VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.insertVendorDeliAmt", bean);
	}	

	/**
	 * 업체기준배송비 수정
	 * Desc : 
	 * @Method Name : updateVendorDeliAmt
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateVendorDeliAmt(PSCMSYS0001VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.updateVendorDeliAmt", bean);
	}
	
	/**
	 * 반품배송비 수정시, 주문배송비 수정
	 * Desc : 
	 * @Method Name : updateVendorReturnAmt
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateVendorReturnAmt(PSCMSYS0001VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.updateVendorReturnAmt", bean);
	}		
	
	/**
	 * 업체기준배송비 삭제
	 * Desc : 
	 * @Method Name : deleteVendorDeliAmt
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deleteVendorDeliAmt(PSCMSYS0001VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmsys0004.deleteVendorDeliAmt", bean);
	}	
}
