package com.lottemart.epc.csr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractServiceImpl;

import org.springframework.stereotype.Service;

import com.lottemart.epc.csr.dao.CsrCodeDAO;
import com.lottemart.epc.csr.model.CsrCodeDefaultVO;
import com.lottemart.epc.csr.model.CsrCodeVO;

import com.lottemart.epc.csr.model.CsrCodeCat1VO;
import com.lottemart.epc.csr.model.CsrCodeCat2VO;
import com.lottemart.epc.csr.model.CsrCodeCat3VO;
import com.lottemart.epc.csr.model.CsrCodeL1VO;
import com.lottemart.epc.csr.model.CsrCodeL2VO;	



import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrSearchParam;
import com.lottemart.epc.csr.service.CsrCodeService;

/**
 * @Class Name : CsrCodeServiceImpl.java
 * @Description : CsrCode Business Implement class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("csrCodeService")
public class CsrCodeServiceImpl extends AbstractServiceImpl implements
        CsrCodeService {

    @Resource(name="csrCodeDAO")
    private CsrCodeDAO csrCodeDAO;
    
    /** ID Generation */
    //@Resource(name="{egovCsrCodeIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * CSR_CODE을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrCodeVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertCsrCode(CsrCodeVO vo) throws Exception {
    	log.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	log.debug(vo.toString());
    	
    	csrCodeDAO.insertCsrCode(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * CSR_CODE을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrCodeVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateCsrCode(CsrCodeVO vo) throws Exception {
        csrCodeDAO.updateCsrCode(vo);
    }

    /**
	 * CSR_CODE을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrCodeVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteCsrCode(CsrCodeVO vo) throws Exception {
        csrCodeDAO.deleteCsrCode(vo);
    }

    /**
	 * CSR_CODE을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrCodeVO
	 * @return 조회한 CSR_CODE
	 * @exception Exception
	 */
    public CsrCodeVO selectCsrCode(CsrCodeVO vo) throws Exception {
        CsrCodeVO resultVO = csrCodeDAO.selectCsrCode(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * CSR_CODE 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_CODE 목록
	 * @exception Exception
	 */
    public List selectCsrCodeList(CsrCodeDefaultVO searchVO) throws Exception {
        return csrCodeDAO.selectCsrCodeList(searchVO);
    }

    /**
	 * CSR_CODE 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_CODE 총 갯수
	 * @exception
	 */
    public int selectCsrCodeListTotCnt(CsrCodeDefaultVO searchVO) {
		return csrCodeDAO.selectCsrCodeListTotCnt(searchVO);
	}
    
        
    public List<CsrCodeCat1VO> selectCsrCodeCat1List(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrCodeDAO.selectCsrCodeCat1List(csrSearchParam);
	}
    public List<CsrCodeCat2VO> selectCsrCodeCat2List(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrCodeDAO.selectCsrCodeCat2List(csrSearchParam);
	}
    public List<CsrCodeCat3VO> selectCsrCodeCat3List(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrCodeDAO.selectCsrCodeCat3List(csrSearchParam);
	}
    public List<CsrCodeL1VO> selectCsrCodeL1List(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrCodeDAO.selectCsrCodeL1List(csrSearchParam);
	}
    public List<CsrCodeL2VO> selectCsrCodeL2List(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrCodeDAO.selectCsrCodeL2List(csrSearchParam);
	}
    
    
    
    
    
	
    
}
