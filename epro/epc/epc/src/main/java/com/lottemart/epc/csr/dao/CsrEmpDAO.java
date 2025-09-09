package com.lottemart.epc.csr.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.csr.model.CsrEmpDefaultVO;
import com.lottemart.epc.csr.model.CsrEmpVO;
import com.lottemart.epc.csr.model.CsrEmpAllListVO;



import com.lottemart.epc.csr.model.CsrSearchParam;


/**
 * @Class Name : CsrEmpDAO.java
 * @Description : CsrEmp DAO Class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("csrEmpDAO")
public class CsrEmpDAO extends AbstractDAO {

	/**
	 * CSR_EMP을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrEmpVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertCsrEmp(CsrEmpVO vo) throws Exception {
        return (String)insert("csrEmpDAO.insertCsrEmp_S", vo);
    }

    /**
	 * CSR_EMP을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrEmpVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateCsrEmp(CsrEmpVO vo) throws Exception {
        update("csrEmpDAO.updateCsrEmp_S", vo);
    }

    /**
	 * CSR_EMP을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrEmpVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteCsrEmp(CsrEmpVO vo) throws Exception {
        delete("csrEmpDAO.deleteCsrEmp_S", vo);
    }

    /**
	 * CSR_EMP을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrEmpVO
	 * @return 조회한 CSR_EMP
	 * @exception Exception
	 */
//    public CsrEmpVO selectCsrEmp(CsrEmpVO vo) throws Exception {
//        return (CsrEmpVO) selectByPk("csrEmpDAO.selectCsrEmp_S", vo);
//    }

    /**
	 * CSR_EMP 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_EMP 목록
	 * @exception Exception
	 */
    public List selectCsrEmpList(CsrEmpDefaultVO searchVO) throws Exception {
        return list("csrEmpDAO.selectCsrEmpList_D", searchVO);
    }

    /**
	 * CSR_EMP 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_EMP 총 갯수
	 * @exception
	 */
    public int selectCsrEmpListTotCnt(CsrEmpDefaultVO searchVO) {
        return (Integer)getSqlMapClientTemplate().queryForObject("csrEmpDAO.selectCsrEmpListTotCnt_S", searchVO);
    }

    
	public CsrEmpVO selectCsrEmp(CsrSearchParam csrSearchParam)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (CsrEmpVO) getSqlMapClientTemplate().queryForObject("csrEmpDAO.selectCsrEmp", csrSearchParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsrEmpAllListVO> selectCsrEmpAllList(CsrSearchParam csrSearchParam)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<CsrEmpAllListVO>) getSqlMapClientTemplate().queryForList("csrEmpDAO.selectCsrEmpAllList", csrSearchParam);
	}
	

	
}
