package com.lottemart.epc.edi.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0010VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nedmord0010Dao")
public class NEDMORD0010Dao extends AbstractDAO {

	/**
	 * 기간정보 - > 상품별 조회
	 * @param pedmord0001Vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0010VO> selectOrdPordList(NEDMORD0010VO vo) throws Exception{
		return (List<NEDMORD0010VO>)getSqlMapClientTemplate().queryForList("NEDMORD0010.selectOrdPordList", vo);
	}
	
}
