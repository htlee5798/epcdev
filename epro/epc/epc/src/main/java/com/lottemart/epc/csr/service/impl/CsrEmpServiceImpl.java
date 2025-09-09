package com.lottemart.epc.csr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractServiceImpl;

import org.springframework.stereotype.Service;

import com.lottemart.epc.csr.dao.CsrEmpDAO;
import com.lottemart.epc.csr.model.CsrEmpAllListVO;
import com.lottemart.epc.csr.model.CsrEmpDefaultVO;
import com.lottemart.epc.csr.model.CsrEmpVO;

import com.lottemart.epc.csr.model.CsrSearchParam;

import com.lottemart.epc.csr.service.CsrEmpService;
// Vo 객체에 대한 import 구문
// import (vo package).CsrEmpDefaultVO;
// import (vo package).CsrEmpVO;
// Dao 객체에 대한 import 구문
// import (dao package).CsrEmpDAO;

/**
 * @Class Name : CsrEmpServiceImpl.java
 * @Description : CsrEmp Business Implement class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *
 *  Copyright (C)  All right reserved.
 */

@Service("csrEmpService")
public class CsrEmpServiceImpl extends AbstractServiceImpl implements
        CsrEmpService {

    @Resource(name="csrEmpDAO")
    private CsrEmpDAO csrEmpDAO;

    /** ID Generation */
    //@Resource(name="{egovCsrEmpIdGnrService}")
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * CSR_EMP을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CsrEmpVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertCsrEmp(CsrEmpVO vo) throws Exception {
    	log.debug(vo.toString());

    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	log.debug(vo.toString());

    	csrEmpDAO.insertCsrEmp(vo);
    	//TODO 해당 테이블 정보에 맞게 수정
        return null;
    }

    /**
	 * CSR_EMP을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CsrEmpVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateCsrEmp(CsrEmpVO vo) throws Exception {
        csrEmpDAO.updateCsrEmp(vo);
    }

    /**
	 * CSR_EMP을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CsrEmpVO
	 * @return void형
	 * @exception Exception
	 */
    public void deleteCsrEmp(CsrEmpVO vo) throws Exception {
        csrEmpDAO.deleteCsrEmp(vo);
    }

    /**
	 * CSR_EMP을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CsrEmpVO
	 * @return 조회한 CSR_EMP
	 * @exception Exception
	 */
//    public CsrEmpVO selectCsrEmp(CsrEmpVO vo) throws Exception {
//        CsrEmpVO resultVO = csrEmpDAO.selectCsrEmp(vo);
//        if (resultVO == null)
//            throw processException("info.nodata.msg");
//        return resultVO;
//    }

    /**
	 * CSR_EMP 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_EMP 목록
	 * @exception Exception
	 */
    public List selectCsrEmpList(CsrEmpDefaultVO searchVO) throws Exception {
        return csrEmpDAO.selectCsrEmpList(searchVO);
    }

    /**
	 * CSR_EMP 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return CSR_EMP 총 갯수
	 * @exception
	 */
    public int selectCsrEmpListTotCnt(CsrEmpDefaultVO searchVO) {
		return csrEmpDAO.selectCsrEmpListTotCnt(searchVO);
	}

//	@Override
//	public CsrEmpVO selectCsrEmp(String sEmpNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}


	public CsrEmpVO selectCsrEmp(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrEmpDAO.selectCsrEmp(csrSearchParam);
	}


	//전체 사원정보리스트
		public List<CsrEmpAllListVO> selectCsrEmpAllList(CsrSearchParam csrSearchParam) {
			// TODO Auto-generated method stub
			return csrEmpDAO.selectCsrEmpAllList(csrSearchParam);
		}




}
