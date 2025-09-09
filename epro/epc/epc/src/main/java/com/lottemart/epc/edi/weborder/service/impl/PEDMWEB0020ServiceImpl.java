package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0020Service;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0020Dao;



@Service("pedmweb0020Service")
public class PEDMWEB0020ServiceImpl implements PEDMWEB0020Service{

	@Autowired
	private PEDMWEB0020Dao PEDMWEB0020Dao;

	/**
	 * 사원목록 조회
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpPoolList(SearchWebOrder vo) throws Exception{
		return PEDMWEB0020Dao.selectEmpPoolList(vo);
	}



	/**
	 * 사원별 협력사 매핑목록 조회
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpVenList(SearchWebOrder vo) throws Exception{
		return PEDMWEB0020Dao.selectEmpVenList(vo);
	}

	/**
	 * 사원별 협력사 선택목록 삭제
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(SearchWebOrder vo ) throws Exception{
		PEDMWEB0020Dao.deleteEmpVenList(vo);
	}

	/**
	 * 사원별 협력사 저장
	 * @param EdiPoEmpPoolVO
	 * @return void
	 * @throws SQLException
	 */
	public HashMap<String,String>  insertEmpVenData(SearchWebOrder vo ) throws Exception{

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
		cnt = PEDMWEB0020Dao.selectEmpVenDataCnt(vo);
		if(cnt > 0) {
			result.put("state","1");
			result.put("message","No Data Found!");
			return result;
		}
		/*-----------------------------------------------------*/

		/*정상 저장 여부 확인 -----------------------------------*/
		cnt =  PEDMWEB0020Dao.insertEmpVenData(vo); // 저장
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
	public HashMap<String,String> insertEmpVenCopAndMove(EdiPoEmpPoolVO vo) throws Exception{
		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS         		 )
		 *  1  : 미등록 오류    ( insert 0 row    )
		 * -1  : 시스템 오류    (exception message)
		*/
		HashMap<String,String>  result	    = new HashMap<String,String>();
		result.put("state","-1");

		int cnt = PEDMWEB0020Dao.insertEmpVenCopy(vo);
		if(cnt <= 0) {
			result.put("state","1");
			result.put("message","0 row insert!");
			return result;
		}

		/*이동일경우 대상정보 삭제 (M 이동, C:복사)---*/
		if("M".equals(vo.getEventType()))
			PEDMWEB0020Dao.deleteEmpVenData(vo);
		/*-----------------------------------------*/

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}


}
