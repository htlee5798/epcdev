package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.weborder.dao.NEDMWEB0240Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0240VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0240Service;

/**
 * @Class Name : NEDMWEB0240ServiceImpl
 * @Description : 협력업체설정 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nedmweb0240Service")
public class NEDMWEB0240ServiceImpl implements NEDMWEB0240Service{

	@Autowired
	private NEDMWEB0240Dao nEDMWEB0240Dao;

	/**
	 * 사원목록 조회
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpPoolList(NEDMWEB0240VO vo) throws Exception{
		return nEDMWEB0240Dao.selectEmpPoolList(vo);
	}



	/**
	 * 사원별 협력사 매핑목록 조회
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpVenList(NEDMWEB0240VO vo) throws Exception{
		return nEDMWEB0240Dao.selectEmpVenList(vo);
	}

	/**
	 * 사원별 협력사 선택목록 삭제
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(NEDMWEB0240VO vo ) throws Exception{
		nEDMWEB0240Dao.deleteEmpVenList(vo);
	}

	/**
	 * 사원별 협력사 저장
	 * @param EdiPoEmpPoolVO
	 * @return void
	 * @throws SQLException
	 */
	public HashMap<String,String>  insertEmpVenData(NEDMWEB0240VO vo ) throws Exception{

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS         		 )
		 *  1  : 중복 오류      ( TED_PO_EMP_VENDOR 중복    )
		 *  2  : 미등록 오류    ( insert 0 row    )
		 * -1  : 시스템 오류    (exception message)
		*/
		HashMap<String,String>  result	    = new HashMap<String,String>();
		result.put("state","-1");
		int cnt = 0;

		/*저장전 기등록 여부 확인(중복확인 ------------------------*/
		cnt = nEDMWEB0240Dao.selectEmpVenDataCnt(vo);
		if(cnt > 0) {
			result.put("state","1");
			result.put("message","No Data Found!");
			return result;
		}
		/*-----------------------------------------------------*/

		/*정상 저장 여부 확인 -----------------------------------*/
		cnt =  nEDMWEB0240Dao.insertEmpVenData(vo); // 저장
		if(cnt <= 0) {
			result.put("state","2");
			result.put("message","0 row insert!");
			return result;
		}
		/*-----------------------------------------------------*/

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}




	/**
	 * 사원별 협력사 복사,이동
	 * @param EdiPoEmpPoolVO
	 * @return int
	 * @throws SQLException
	 */
	public HashMap<String,String> insertEmpVenCopAndMove(NEDMWEB0240VO vo) throws Exception{
		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS         		 )
		 *  1  : 미등록 오류    ( insert 0 row    )
		 * -1  : 시스템 오류    (exception message)
		*/
		HashMap<String,String>  result	    = new HashMap<String,String>();
		result.put("state","-1");

		int cnt = nEDMWEB0240Dao.insertEmpVenCopy(vo);
		if(cnt <= 0) {
			result.put("state","1");
			result.put("message","0 row insert!");
			return result;
		}

		/*이동일경우 대상정보 삭제 (M 이동, C:복사)---*/
		if("M".equals(vo.getEventType()))
			nEDMWEB0240Dao.deleteEmpVenData(vo);
		/*-----------------------------------------*/

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}


}
