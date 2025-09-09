package com.lottemart.epc.csr.service;

import java.util.List;
import java.util.Map;

import com.lottemart.epc.csr.model.CsrDyRegDefaultVO;
import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrSearchParam;


// Vo 객체에 대한 import 구문
// import (vo package).CsrDyRegDefaultVO;
// import (vo package).CsrDyRegVO;
//import com.lottemart.epc.edi.product.model.NewProduct;

/**
 * @Class Name : CsrDyRegService.java
 * @Description : CsrDyReg Business class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface CsrDyRegService {
	
	/**
	 * CSR_DY_REG을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrDyRegVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    void insertCsrDyRegs(String obj) throws Exception;
    
    
    
    
    
    /**
	 * CSR_DY_REG을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrDyRegVO
	 * @return void형
	 * @exception Exception
	 */
    void updateCsrDyReg(CsrDyRegVO vo) throws Exception;
    
    /**
	 * CSR_DY_REG을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrDyRegVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteCsrDyReg(CsrDyRegVO vo) throws Exception;
    
    /**
	 * CSR_DY_REG을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrDyRegVO
	 * @return 조회한 CSR_DY_REG
	 * @exception Exception
	 */
    CsrDyRegVO selectCsrDyReg(CsrDyRegVO vo) throws Exception;
    
    /**
	 * CSR_DY_REG 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_DY_REG 목록
	 * @exception Exception
	 */
    List selectCsrDyRegList(CsrDyRegDefaultVO searchVO) throws Exception;
    
    /**
	 * CSR_DY_REG 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_DY_REG 총 갯수
	 * @exception
	 */
    int selectCsrDyRegListTotCnt(CsrDyRegDefaultVO searchVO);
    
    
    List<CsrDyRegVO> selectCsrRegEmpDayList(CsrSearchParam csrSearchParamg);
    
    
    
}
