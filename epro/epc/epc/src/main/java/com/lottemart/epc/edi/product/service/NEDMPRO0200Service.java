package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;

import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0200VO;


public interface NEDMPRO0200Service {
	/*
	 * 소분류별 영양성분코드 조회
	 * 
	 * @param NEDMPRO0200VO
	 * @return
	 * @throws Exception
	 */
    public List<HashMap> selectNutAtt(String l3Cd) throws Exception;
	
    /*
	 * 소분류별 영양성분값 조회
	 * 
	 * @param NEDMPRO0200VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutAttInfo(String l3Cd) throws Exception;

    /*
	 * 상품 영양성분값 임시저장
	 * 
	 * @param NEDMPRO0200VO
	 * @return
	 * @throws Exception
	 */
	public String mergeNutAmtTmp(NEDMPRO0200VO vo) throws Exception;

    /*
	 * 상품 영양성분 저장할 순번 가져오기
	 * 
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public Integer getProdNutMaxSeq(String prodCd) throws Exception;	

    /*
	 * SAP에서 응답안온 영양성분 개수 
	 * 
	 * @param NEDMPRO0090VO
	 * @return
	 * @throws Exception
	 */
	public Integer getCntNotNutAttRes(NEDMPRO0200VO vo) throws Exception;
	
	/*
	 * 상품 저장되어 있는 영양성분값 가져오기 
	 * 
	 * @param l3Cd
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutAmtSaved(String prodCd) throws Exception;

	/*
	 * 기존 시퀀스로 요청한 영양성분속성값이 있는지 확인 (개수확인)
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	public Integer selectCntNutReq(NEDMPRO0200VO nEDMPRO0200VO) throws Exception ;
	
	/*
	 * 상품 영양성분 저장 상태값 업데이트 
	 * 
	 * @param NEDMPRO0090VO
	 * @return
	 * @throws Exception
	 */
	public String updateNutModifyStatus(NEDMPRO0200VO vo) throws Exception;
	
	/*
	 * 상품 영양성분값 ECO 전송 
	 * 
	 * @param l3Cd
	 * @return
	 * @throws Exception
	 */
	public String submitNutAmtInfo(NEDMPRO0200VO vo) throws Exception;
	
}

