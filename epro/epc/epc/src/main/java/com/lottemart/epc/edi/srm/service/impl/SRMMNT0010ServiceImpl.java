package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.edi.srm.dao.SRMMNT0010Dao;
import com.lottemart.epc.edi.srm.model.SRMMNT0010VO;
import com.lottemart.epc.edi.srm.service.SRMMNT0010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * 대표자 SRM 모니터링 ServiceImpl
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Service("srmmnt0010Service")
public class SRMMNT0010ServiceImpl implements SRMMNT0010Service {

	@Autowired
	private SRMMNT0010Dao srmmnt0010Dao;

	/**
	 * CEO로그인 및 MAX로그인수 조회
	 * @param  vo
	 * @return SRMMNT0010VO
	 * @throws Exception
	 */
	public HashMap<String, Object> selectCEOSRMmoniteringLogin(SRMMNT0010VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		SRMMNT0010VO resultVo = srmmnt0010Dao.selectCEOSRMmoniteringLogin(vo);
		if(resultVo == null){
			resultMap.put("message", "FAIL-NULL");
			return resultMap;
		}
		//최대 접속 횟수 초과
		if(resultVo.getAuthMaxCnt().equals(resultVo.getAuthCnt())){
			resultMap.put("message", "FAIL-MAX_COUNT");
			return resultMap;
		}

		//인증번호 체크
		if(!resultVo.getAuthCd().equals(vo.getAuthCd())){
			resultMap.put("message", "FAIL-AUTH_CD");
			return resultMap;
		}

		//접속count
		srmmnt0010Dao.updateCEOSRMmoniteringLogin(vo);

		//접속로그
		vo.setAuthMaxCnt(resultVo.getAuthMaxCnt());
		vo.setIpAddress(request.getRemoteAddr());
		srmmnt0010Dao.insertCEOSRMmoniteringLoginLog(vo);

		resultMap.put("message", "SUCCESS");
		return resultMap;
	}

}
