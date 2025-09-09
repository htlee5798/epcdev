package com.lottemart.epc.edi.srm.service.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.dao.SRMJON0020Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0020Service;

/**
 * 입점상담 / 입점상담신청  / 입점상당신청 로그인 ServiceImpl
 * 
 * @author SHIN SE JIN
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmjon0020Service")
public class SRMJON0020ServiceImpl implements SRMJON0020Service {

	@Autowired
	private SRMJON0020Dao srmjon0020Dao;
	
	@Autowired
	private ConfigurationService config;

	/**
	 * 기등록여부 확인
	 */
	public SRMJON0020VO selectSRMJONLogin(SRMJON0020VO vo) throws Exception {
		return srmjon0020Dao.selectSRMJONLogin(vo);
	}
	
	/**
	 * 입점상담 신청 로그인(비밀번호 포함)
	 */
	public SRMJON0020VO selectSRMJONReLogin(SRMJON0020VO vo) throws Exception {
		return srmjon0020Dao.selectSRMJONReLogin(vo);
	}
	
	/**
	 * 상담신청 리스트 조회 카운트
	 */
	public int selectCounselListCount(SRMJON0020VO vo) throws Exception {
		return srmjon0020Dao.selectCounselListCount(vo);
	}
	
	/**
	 * 상담신청 리스트 조회
	 */
	public List<SRMJON0020VO> selectCounselList(SRMJON0020VO vo) throws Exception {
		return srmjon0020Dao.selectCounselList(vo);
	}
	
	/**
	 * 비밀번호 틀린 경우 카운트 증가
	 */
	public void updatePassCheckCnt(SRMJON0020VO vo) throws Exception {
		srmjon0020Dao.updatePassCheckCnt(vo);
	}
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 */
	public void updatePassCheckCntReset(SRMJON0020VO vo) throws Exception {
		srmjon0020Dao.updatePassCheckCntReset(vo);
	}
	
	/**
	 * 로그인 성공 시 세션 생성
	 */
	public void setSession(SRMJON0020VO vo, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.srm.session.key");
		
		SRMSessionVO sessionVO = (SRMSessionVO)request.getSession().getAttribute(sessionKey);
		if (sessionVO != null) {
			//session.invalidate();
			request.getSession().setAttribute(sessionKey, "");
		}
		
		// 기 등록된 내역 확인
		SRMJON0020VO resultVO = selectSRMJONLogin(vo);
		
		//---------- 세션 생성 Start ------------------------------
		SRMSessionVO session = new SRMSessionVO();
		
		// 기 신청 내역이 없는 경우 넘어온 Parameter로 VO 재 구성
		if (resultVO == null) {
			resultVO = vo;
		}
		
		session.setHouseCode(StringUtil.null2str(StringUtil.null2str(resultVO.getHouseCode())));	// 하우스코드
		session.setShipperType(StringUtil.null2str(resultVO.getShipperType()));						// 업체 소싱 국가 구분
		session.setSellerCode(StringUtil.null2str(resultVO.getIrsNo()));							// 업체코드
		session.setIrsNo(StringUtil.null2str(resultVO.getIrsNo()));									// 사업자번호
		session.setSellerNameLoc(StringUtil.null2str(resultVO.getSellerNameLoc()));					// 상호명
		session.setCountry(StringUtil.null2str(resultVO.getCountry()));								// 국가코드
		
		request.getSession().setAttribute(sessionKey, session);
		//---------- 세션 생성 End ------------------------------
	}
	
	
	/**
	 * 사업자명/사업자번호 찾기
	 */
	public SRMJON0020VO selectSRMJONSearch(SRMJON0020VO vo) throws Exception {
		return srmjon0020Dao.selectSRMJONSearch(vo);
	}
	
}
