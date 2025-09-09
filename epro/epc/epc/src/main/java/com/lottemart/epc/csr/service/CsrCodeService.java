package com.lottemart.epc.csr.service;

import java.util.List;

import com.lottemart.epc.csr.model.CsrCodeDefaultVO;
import com.lottemart.epc.csr.model.CsrCodeVO;
import com.lottemart.epc.csr.model.CsrCodeCat1VO;
import com.lottemart.epc.csr.model.CsrCodeCat2VO;
import com.lottemart.epc.csr.model.CsrCodeCat3VO;
import com.lottemart.epc.csr.model.CsrCodeL1VO;
import com.lottemart.epc.csr.model.CsrCodeL2VO;

import com.lottemart.epc.csr.model.CsrSearchParam;


/**
 * @Class Name : CsrCodeService.java
 * @Description : CsrCode Business class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface CsrCodeService {
	
	/**
	 * CSR_CODE을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrCodeVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertCsrCode(CsrCodeVO vo) throws Exception;
    
    /**
	 * CSR_CODE을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrCodeVO
	 * @return void형
	 * @exception Exception
	 */
    void updateCsrCode(CsrCodeVO vo) throws Exception;
    
    /**
	 * CSR_CODE을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrCodeVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteCsrCode(CsrCodeVO vo) throws Exception;
    
    /**
	 * CSR_CODE을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrCodeVO
	 * @return 조회한 CSR_CODE
	 * @exception Exception
	 */
    CsrCodeVO selectCsrCode(CsrCodeVO vo) throws Exception;
    
    /**
	 * CSR_CODE 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_CODE 목록
	 * @exception Exception
	 */
    List selectCsrCodeList(CsrCodeDefaultVO searchVO) throws Exception;
    
    
    
    List<CsrCodeCat1VO> selectCsrCodeCat1List(CsrSearchParam csrSearchParam);    
    List<CsrCodeCat2VO> selectCsrCodeCat2List(CsrSearchParam csrSearchParam);    
    List<CsrCodeCat3VO> selectCsrCodeCat3List(CsrSearchParam csrSearchParam);     
    List<CsrCodeL1VO> selectCsrCodeL1List(CsrSearchParam csrSearchParam);      
    List<CsrCodeL2VO> selectCsrCodeL2List(CsrSearchParam csrSearchParam);
    

    
    /**
	 * CSR_CODE 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_CODE 총 갯수
	 * @exception
	 */
    int selectCsrCodeListTotCnt(CsrCodeDefaultVO searchVO);
    
}
