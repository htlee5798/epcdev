/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lottemart.common.exception.AlertException;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0011Dao;
import com.lottemart.epc.product.model.PSCMPRD0011VO;
import com.lottemart.epc.product.service.PSCMPRD0011Service;

/**
 * @Class Name : PSCMPRD0011ServiceImpl
 * @Description : 상품이미지촬영스케쥴목록 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:01:50 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMPRD0011Service")
public class PSCMPRD0011ServiceImpl implements PSCMPRD0011Service {
	
	@Autowired
	private PSCMPRD0011Dao pscmprd0011Dao;

	/**
	 * Desc : 상품이미지촬영스케쥴목록 조회 메소드
	 * @Method Name : selectScheduleList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0011VO> selectScheduleList(DataMap paramMap) throws Exception {
		return pscmprd0011Dao.selectScheduleList(paramMap);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 수정 메소드
	 * @Method Name : updateSchedule
	 * @param pscmprd0011VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateSchedule(HttpServletRequest request) throws Exception {
		
		int resultCnt = 0;
		
//		int size = pscmprd0011VO.size();

		try {
			DataMap param = new DataMap(request);
			
			String[] rowCount = request.getParameterValues("selected");

			String[] status = request.getParameterValues("s_status");
			String[] crud = request.getParameterValues("crud");
			
			String[] scdlSeqs = request.getParameterValues("scdlSeqs");
			String[] rservStartDy = request.getParameterValues("rservStartDy");
			String[] rservStartHour = request.getParameterValues("rservStartHour");
			String[] rservStartMin = request.getParameterValues("rservStartMin");
			String[] rservEndHour = request.getParameterValues("rservEndHour");
			String[] rservEndMin = request.getParameterValues("rservEndMin");
			String[] vendorId = request.getParameterValues("vendorId");
			String[] scdlMemo = request.getParameterValues("scdlMemo");
			
			PSCMPRD0011VO pscmprd0011VO = new PSCMPRD0011VO() ;
			
			
//			// header data VO객체에 셋팅 
			for(int i = 0; i < rowCount.length; i++){				

				pscmprd0011VO.setScdlSeqs(scdlSeqs[i]);
				pscmprd0011VO.setRservStartDy(rservStartDy[i]);
				pscmprd0011VO.setRservStartTm(rservStartHour[i]+rservStartMin[i] );
				pscmprd0011VO.setRservEndTm(rservEndHour[i]+rservEndMin[i]);
				pscmprd0011VO.setVendorId(vendorId[i]);				
				
				
				int cnt = pscmprd0011Dao.selectDupScheduleCount(pscmprd0011VO).get(0).getInt("CNT");
				
				if(cnt > 1) {
					throw new AlertException("촬영 스케쥴이 중복됩니다.");
				}
				
				resultCnt += pscmprd0011Dao.updateSchedule(pscmprd0011VO);
				
			}
			
		} catch (Exception e) {
			resultCnt = 0;
		}
			
		return resultCnt;	
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 삭제 메소드
	 * @Method Name : deleteSchedule
	 * @param pscmprd0011VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteSchedule(HttpServletRequest request) throws Exception {

//		int size = request.size();
		int resultCnt = 0;
		
		String[] rowCount = request.getParameterValues("selected");
		String[] scdlSeqs = request.getParameterValues("scdlSeqs");
		
		PSCMPRD0011VO pscmprd0011VO = new PSCMPRD0011VO() ;
		
		for(int i = 0; i < rowCount.length; i++) {
			pscmprd0011VO.setScdlSeqs(scdlSeqs[i]);
			resultCnt += pscmprd0011Dao.deleteSchedule(pscmprd0011VO);
		}
		
		return resultCnt;	
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 엑셀다운로드하는 메소드
	 * @Method Name : selectScheduleMgrListExcel
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0011VO> selectScheduleMgrListExcel(PSCMPRD0011VO pscmprd0011VO) throws Exception {
		return pscmprd0011Dao.selectScheduleMgrListExcel(pscmprd0011VO);
	}

}
