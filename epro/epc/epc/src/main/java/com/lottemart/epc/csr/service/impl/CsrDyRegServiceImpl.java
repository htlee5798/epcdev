package com.lottemart.epc.csr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.csr.dao.CsrDyRegDAO;
import com.lottemart.epc.csr.model.CsrDyRegDefaultVO;
import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrEmpAllListVO;

import com.lottemart.epc.csr.model.CsrSearchParam;

import com.lottemart.epc.csr.service.CsrDyRegService;
// Vo 객체에 대한 import 구문
// import (vo package).CsrDyRegDefaultVO;
// import (vo package).CsrDyRegVO;
// Dao 객체에 대한 import 구문
// import (dao package).CsrDyRegDAO;
import com.lottemart.epc.edi.product.model.NewProduct;

/**
 * @Class Name : CsrDyRegServiceImpl.java
 * @Description : CsrDyReg Business Implement class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see Copyright (C) All right reserved.
 */

@Service("csrDyRegService")
public class CsrDyRegServiceImpl extends AbstractServiceImpl implements
		CsrDyRegService {

	@Resource(name = "csrDyRegDAO")
	private CsrDyRegDAO csrDyRegDAO;

	/** ID Generation */
	// @Resource(name="{egovCsrDyRegIdGnrService}")
	// private EgovIdGnrService egovIdGnrService;

	/**
	 * CSR_DY_REG을 등록한다.
	 *
	 * @param vo
	 *            - 등록할 정보가 담긴 CsrDyRegVO
	 * @return 등록 결과
	 * @exception Exception
	 */


	public void insertCsrDyRegs(String obj) throws Exception {

    	log.debug(obj);
//    	System.out.print("이것이 obj에 들어가는 값입니다." +obj);
    	String[] tmp = obj.split("@");

		HashMap<String, Object> hMap = new HashMap<String, Object>();

		int seq = 1;

		for (int i = 0; i < tmp.length; i++) {
			String[] tmpVal = tmp[i].split(";");
			hMap.put("seq", seq + i);
			hMap.put("empNo", tmpVal[0]);
			hMap.put("regDy", tmpVal[1]);
			hMap.put("l1CatCd", tmpVal[2]);
			hMap.put("l2CatCd", tmpVal[3]);
			hMap.put("l3CatCd", tmpVal[4]);
			hMap.put("l1Cd", tmpVal[5]);
			hMap.put("l2Cd", tmpVal[6]);
			hMap.put("hh", tmpVal[7]);

    	csrDyRegDAO.insertCsrDyRegs(hMap);

    	//TODO 해당 테이블 정보에 맞게 수정
		}
    }

	/**
	 * CSR_DY_REG을 수정한다.
	 *
	 * @param vo
	 *            - 수정할 정보가 담긴 CsrDyRegVO
	 * @return void형
	 * @exception Exception
	 */
	public void updateCsrDyReg(CsrDyRegVO vo) throws Exception {
		csrDyRegDAO.updateCsrDyReg(vo);
	}

	/**
	 * CSR_DY_REG을 삭제한다.
	 *
	 * @param vo
	 *            - 삭제할 정보가 담긴 CsrDyRegVO
	 * @return void형
	 * @exception Exception
	 */
	public void deleteCsrDyReg(CsrDyRegVO vo) throws Exception {
		csrDyRegDAO.deleteCsrDyReg(vo);
	}

	/**
	 * CSR_DY_REG을 조회한다.
	 *
	 * @param vo
	 *            - 조회할 정보가 담긴 CsrDyRegVO
	 * @return 조회한 CSR_DY_REG
	 * @exception Exception
	 */
	public CsrDyRegVO selectCsrDyReg(CsrDyRegVO vo) throws Exception {
		CsrDyRegVO resultVO = csrDyRegDAO.selectCsrDyReg(vo);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	/**
	 * CSR_DY_REG 목록을 조회한다.
	 *
	 * @param searchVO
	 *            - 조회할 정보가 담긴 VO
	 * @return CSR_DY_REG 목록
	 * @exception Exception
	 */
	public List selectCsrDyRegList(CsrDyRegDefaultVO searchVO) throws Exception {
		return csrDyRegDAO.selectCsrDyRegList(searchVO);
	}

	/**
	 * CSR_DY_REG 총 갯수를 조회한다.
	 *
	 * @param searchVO
	 *            - 조회할 정보가 담긴 VO
	 * @return CSR_DY_REG 총 갯수
	 * @exception
	 */
	public int selectCsrDyRegListTotCnt(CsrDyRegDefaultVO searchVO) {
		return csrDyRegDAO.selectCsrDyRegListTotCnt(searchVO);
	}

	public List<CsrDyRegVO> selectCsrRegEmpDayList(CsrSearchParam csrSearchParam) {
		// TODO Auto-generated method stub
		return csrDyRegDAO.selectCsrRegEmpDayList(csrSearchParam);
	}





}
