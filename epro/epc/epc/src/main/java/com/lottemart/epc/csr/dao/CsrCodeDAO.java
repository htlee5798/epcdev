package com.lottemart.epc.csr.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.csr.model.CsrCodeDefaultVO;
import com.lottemart.epc.csr.model.CsrCodeVO;
import com.lottemart.epc.csr.model.CsrCodeCat1VO;
import com.lottemart.epc.csr.model.CsrCodeCat2VO;
import com.lottemart.epc.csr.model.CsrCodeCat3VO;



import com.lottemart.epc.csr.model.CsrCodeL1VO;
import com.lottemart.epc.csr.model.CsrCodeL2VO;


import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrSearchParam;

/**
 * @Class Name : CsrCodeDAO.java
 * @Description : CsrCode DAO Class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("csrCodeDAO")
public class CsrCodeDAO extends AbstractDAO {

	/**
	 * CSR_CODE을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrCodeVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertCsrCode(CsrCodeVO vo) throws Exception {
        return (String)insert("csrCodeDAO.insertCsrCode_S", vo);
    }

    /**
	 * CSR_CODE을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrCodeVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateCsrCode(CsrCodeVO vo) throws Exception {
        update("csrCodeDAO.updateCsrCode_S", vo);
    }

    /**
	 * CSR_CODE을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrCodeVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteCsrCode(CsrCodeVO vo) throws Exception {
        delete("csrCodeDAO.deleteCsrCode_S", vo);
    }

    /**
	 * CSR_CODE을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrCodeVO
	 * @return 조회한 CSR_CODE
	 * @exception Exception
	 */
    public CsrCodeVO selectCsrCode(CsrCodeVO vo) throws Exception {
        return (CsrCodeVO) selectByPk("csrCodeDAO.selectCsrCode_S", vo);
    }

    /**
	 * CSR_CODE 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_CODE 목록
	 * @exception Exception
	 */
    public List selectCsrCodeList(CsrCodeDefaultVO searchVO) throws Exception {
        return list("csrCodeDAO.selectCsrCodeList_D", searchVO);
    }

    /**
	 * CSR_CODE 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return CSR_CODE 총 갯수
	 * @exception
	 */
    public int selectCsrCodeListTotCnt(CsrCodeDefaultVO searchVO) {
        return (Integer)getSqlMapClientTemplate().queryForObject("csrCodeDAO.selectCsrCodeListTotCnt_S", searchVO);
    }
   
    
	@SuppressWarnings("unchecked")
	public List<CsrCodeCat1VO> selectCsrCodeCat1List(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrCodeCat1VO>)getSqlMapClientTemplate().queryForList("csrCodeDAO.selectCsrCodeCat1List", csrSearchParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsrCodeCat2VO> selectCsrCodeCat2List(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrCodeCat2VO>)getSqlMapClientTemplate().queryForList("csrCodeDAO.selectCsrCodeCat2List", csrSearchParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsrCodeCat3VO> selectCsrCodeCat3List(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrCodeCat3VO>)getSqlMapClientTemplate().queryForList("csrCodeDAO.selectCsrCodeCat3List", csrSearchParam);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CsrCodeL1VO> selectCsrCodeL1List(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrCodeL1VO>)getSqlMapClientTemplate().queryForList("csrCodeDAO.selectCsrCodeL1List", csrSearchParam);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CsrCodeL2VO> selectCsrCodeL2List(CsrSearchParam csrSearchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<CsrCodeL2VO>)getSqlMapClientTemplate().queryForList("csrCodeDAO.selectCsrCodeL2List", csrSearchParam);
	}
	
	
	
	
	
	
	
	
}
