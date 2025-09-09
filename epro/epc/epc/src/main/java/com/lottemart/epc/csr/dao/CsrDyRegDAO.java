package com.lottemart.epc.csr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.csr.model.CsrDyRegDefaultVO;
import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrSearchParam;

/**
 * @Class Name : CsrDyRegDAO.java
 * @Description : CsrDyReg DAO Class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("csrDyRegDAO")
public class CsrDyRegDAO extends AbstractDAO {

	/**
	 * CSR_DY_REG을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrDyRegVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertCsrDyRegs(HashMap<String,Object> map) throws Exception {
        return (String)insert("csrDyRegDAO.insertCsrDyReg_S",map);
    }

    /**
	 * CSR_DY_REG을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrDyRegVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateCsrDyReg(CsrDyRegVO vo) throws Exception {
        update("csrDyRegDAO.updateCsrDyReg_S", vo);
    }

    /**
	 * CSR_DY_REG을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrDyRegVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteCsrDyReg(CsrDyRegVO vo) throws Exception {
        delete("csrDyRegDAO.deleteCsrDyReg_S", vo);
    }

    /**
	 * CSR_DY_REG을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrDyRegVO
	 * @return 조회한 CSR_DY_REG
	 * @exception Exception
	 */
    public CsrDyRegVO selectCsrDyReg(CsrDyRegVO vo) throws Exception {
        return (CsrDyRegVO) selectByPk("csrDyRegDAO.selectCsrDyReg_S", vo);
    }

    /**
	 * CSR_DY_REG 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_DY_REG 목록
	 * @exception Exception
	 */
    public List selectCsrDyRegList(CsrDyRegDefaultVO searchVO) throws Exception {
        return list("csrDyRegDAO.selectCsrDyRegList_D", searchVO);
    }

    /**
	 * CSR_DY_REG 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_DY_REG 총 갯수
	 * @exception
	 */
    public int selectCsrDyRegListTotCnt(CsrDyRegDefaultVO searchVO) {
        return (Integer)getSqlMapClientTemplate().queryForObject("csrDyRegDAO.selectCsrDyRegListTotCnt_S", searchVO);
    }

    
    
    
	@SuppressWarnings("unchecked")
	public List<CsrDyRegVO> selectCsrRegEmpDayList(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrDyRegVO>)getSqlMapClientTemplate().queryForList("csrDyRegDAO.selectCsrRegEmpDayList", csrSearchParam);
	}

	
	
	//테이블 값 입력 하나
	public void insertCsrDyReg(Map<String, Object> map, String pid) {
		// TODO Auto-generated method stub
		map.put("pid", pid);
		getSqlMapClientTemplate().insert("PEDMSCT0003.TSC_ESTIMATION-INSERT01",map);
	}
	
    //테이블 값 입력 여러개

    
}
