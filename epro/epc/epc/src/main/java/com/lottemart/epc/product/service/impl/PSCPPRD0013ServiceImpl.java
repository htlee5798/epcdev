/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.product.dao.PSCMPRD0011Dao;
import com.lottemart.epc.product.dao.PSCPPRD0013Dao;
import com.lottemart.epc.product.model.PSCMPRD0011VO;
import com.lottemart.epc.product.service.PSCPPRD0013Service;
import java.io.IOException;

/**
 * @Class Name : PSCPPRD0013ServiceImpl
 * @Description : 상품이미지촬영스케쥴상세 조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:15:30 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCPPRD0013Service")
public class PSCPPRD0013ServiceImpl implements PSCPPRD0013Service {

	@Autowired
	private PSCPPRD0013Dao pscpprd0013Dao;
	@Autowired
	private PSCMPRD0011Dao pscmprd0011Dao;

	/**
	 * Desc : 상품이미지촬영스케쥴상세 조회 메소드
	 * @Method Name : selectSchedulePopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public PSCMPRD0011VO selectSchedulePopup(PSCMPRD0011VO vo) throws Exception {
		return pscpprd0013Dao.selectSchedulePopup(vo);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 등록 메소드
	 * @Method Name : insertSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void insertSchedulePopup(PSCMPRD0011VO vo) throws Exception {
		vo.setCrud("I");
		vo.setRservStartDy(vo.getRservStartDy().replaceAll("-", ""));
		vo.setRservEndDy(vo.getRservStartDy());
		vo.setRservStartTm(vo.getRservStartHour()+vo.getRservStartMin());
		vo.setRservEndTm(vo.getRservEndHour()+vo.getRservEndMin());
		int cnt = pscmprd0011Dao.selectDupScheduleCount(vo).get(0).getInt("CNT");

		if(cnt > 0) {
			throw new IllegalArgumentException("촬영 스케쥴이 중복됩니다.");
		}

		pscpprd0013Dao.insertSchedulePopup(vo);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 수정 메소드
	 * @Method Name : updateSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void updateSchedulePopup(PSCMPRD0011VO vo) throws Exception {
		vo.setCrud("U");
		vo.setRservStartDy(vo.getRservStartDy().replaceAll("-", ""));
		vo.setRservEndDy(vo.getRservStartDy());
		vo.setRservStartTm(vo.getRservStartHour()+vo.getRservStartMin());
		vo.setRservEndTm(vo.getRservEndHour()+vo.getRservEndMin());
		int cnt = pscmprd0011Dao.selectDupScheduleCount(vo).get(0).getInt("CNT");

		if(cnt > 0) {
			throw new IllegalArgumentException("촬영 스케쥴이 중복됩니다.");
		}

		pscpprd0013Dao.updateSchedulePopup(vo);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴상세 삭제 메소드
	 * @Method Name : deleteSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void deleteSchedulePopup(PSCMPRD0011VO vo) throws Exception {
		pscpprd0013Dao.deleteSchedulePopup(vo);
	}
}
