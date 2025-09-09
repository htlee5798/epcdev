package com.lottemart.epc.edi.product.service;

import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;

import java.util.List;
import java.util.HashMap;


public interface NEDMPRO0090Service {
	/*
	 * 상품 분석속성 가져오기
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
    public List<HashMap> selectGrpAtt(String l3Cd) throws Exception;
	
    /*
	 * 상품 분석속성값 가져오기
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttOpt(String l3Cd) throws Exception;

    /*
	 * 상품 분석속성값 임시저장
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public String saveGrpAttOptInfoTmp(NEDMPRO0090VO vo) throws Exception;

    /*
	 * 상품 분석속성관련 저장할 SEQ 가져오기
	 * 
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public Integer getMaxSeqAttr(String prodCd) throws Exception;	

    /*
	 * 요청한 분석속성 값중 응답오지 않은 속성 개수 
	 * 
	 * @param NEDMPRO0090VO
	 * @return
	 * @throws Exception
	 */
	public Integer getCntNotResponseAttr(NEDMPRO0090VO vo) throws Exception;
	
	/*
	 * 상품 저장되어 있는 분석속성값 가져오기 
	 * 
	 * @param l3Cd
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttSelectedOpt(String prodCd) throws Exception;

	/*
	 * 기존 시퀀스로 요청한 분석속성값이 있는지 확인 (개수확인)
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	public Integer selectCntGrpReq(NEDMPRO0090VO nEDMPRO0090VO) throws Exception ;
	
	/*
	 * 상품 분석속성값 저장 상태값 업데이트 
	 * 
	 * @param NEDMPRO0090VO
	 * @return
	 * @throws Exception
	 */
	public String updateModifyStatusAttr(NEDMPRO0090VO vo) throws Exception;
	
	/*
	 * 상품 분석속성값 ECO 전송 
	 * 
	 * @param l3Cd
	 * @return
	 * @throws Exception
	 */
	public String submitGrpAttrOptInfo(NEDMPRO0090VO vo) throws Exception;
	
}

