package com.lottemart.epc.csr.service;

import java.util.List;
// Vo 객체에 대한 import 구문
// import (vo package).CsrEmpDefaultVO;
// import (vo package).CsrEmpVO;

import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrEmpDefaultVO;
import com.lottemart.epc.csr.model.CsrEmpVO;

import com.lottemart.epc.csr.model.CsrEmpAllListVO;

import com.lottemart.epc.csr.model.CsrSearchParam;




/**
 * @Class Name : CsrEmpService.java
 * @Description : CsrEmp Business class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface CsrEmpService {
	
	/**
	 * CSR_EMP을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrEmpVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertCsrEmp(CsrEmpVO vo) throws Exception;
    
    /**
	 * CSR_EMP을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrEmpVO
	 * @return void형
	 * @exception Exception
	 */
    void updateCsrEmp(CsrEmpVO vo) throws Exception;
    
    /**
	 * CSR_EMP을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrEmpVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteCsrEmp(CsrEmpVO vo) throws Exception;
    
    /**
	 * CSR_EMP을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrEmpVO
	 * @return 조회한 CSR_EMP
	 * @exception Exception
	 */
    //CsrEmpVO selectCsrEmp(CsrEmpVO vo) throws Exception;
    CsrEmpVO selectCsrEmp(CsrSearchParam csrSearchParam);
    //전체 사원리스트
    List<CsrEmpAllListVO> selectCsrEmpAllList(CsrSearchParam csrSearchParam);
  
    
    
    
    /**
	 * CSR_EMP 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_EMP 목록
	 * @exception Exception
	 */
    List selectCsrEmpList(CsrEmpDefaultVO searchVO) throws Exception;
    
    /**
	 * CSR_EMP 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_EMP 총 갯수
	 * @exception
	 */
    int selectCsrEmpListTotCnt(CsrEmpDefaultVO searchVO);
    
}
