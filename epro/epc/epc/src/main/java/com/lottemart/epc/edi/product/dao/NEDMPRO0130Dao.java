package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Component;

import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0130VO;

@Component("nedmpro0130Dao")
public class NEDMPRO0130Dao extends AbstractDAO {

	/**
	 * 일반 Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectStandardProductImageCount(NEDMPRO0130VO vo) throws Exception {
		return (Integer) this.selectByPk("NEDMPRO0130.selectStandardProductImageCount", vo);
	}

	/**
	 * 패션상품 Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectFashionProductImageCount(NEDMPRO0130VO vo) throws Exception {
		return (Integer) this.selectByPk("NEDMPRO0130.selectFashionProductImageCount", vo);
	}

	/**
	 * 일반 List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0130VO> selectStandardProductImageList(NEDMPRO0130VO vo) throws Exception {
		return this.list("NEDMPRO0130.selectStandardProductImageList", vo);
	}

	/**
	 * 패션상품 List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0130VO> selectFashionProductImageList(NEDMPRO0130VO vo) throws Exception {
		return this.list("NEDMPRO0130.selectFashionProductImageList", vo);
	}

	/**
	 * 이미지 사이즈 변경 예약 존재여부
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectMDSellCodeinReserv(NEDMPRO0130VO vo) throws Exception {
		return (Integer) this.selectByPk("NEDMPRO0130.selectMDSellCodeinReserv", vo);
	}

	/**
	 * 이미지 사이즈 변경 예약 Insert
	 * @param vo
	 * @throws Exception
	 */
	public void insertMDSizeReserv(NEDMPRO0130VO vo) throws Exception {
		this.insert("NEDMPRO0130.insertMDSizeReserv", vo);
	}

	/**
	 * 이미지 사이즈 변경 예약 Update
	 * @param vo
	 * @throws Exception
	 */
	public void updateMDSizeInReserv(NEDMPRO0130VO vo) throws Exception {
		this.update("NEDMPRO0130.updateMDSizeInReserv", vo);
	}

	/**
	 * RFC Call Data 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<LinkedHashMap> selectRfcCallData(NEDMPRO0130VO vo) throws Exception {
		return this.list("NEDMPRO0130.selectRfcCallData", vo);
	}

	/**
	 * 이미지 정보 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteProdPogImgInfo(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0130.deleteProdPogImgInfo", paramMap);
	}

	/**
	 * 이미지 저장
	 * @param nEDMPRO0024VO
	 * @throws Exception
	 */
	public void insertProdPogImgInfo(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0130.insertProdPogImgInfo", nEDMPRO0024VO);
	}

	/**
	 * RFC 전송 할 이미지 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectProdImgInfoToRFC(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0130.selectProdImgInfoToRFC", paramMap);
	}

	/**
	 * 중량 정보 최근 순번
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectWgtSeq(Map<String, String> paramMap) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectWgtSeq", paramMap);
	}

	/**
	 * 중량 정보 다음 차례 순번
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectWgtNextSeq(Map<String, String> paramMap) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectWgtNextSeq", paramMap);
	}

	/**
	 * 중량 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectWgtInfo(Map<String, String> paramMap) throws Exception {
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectWgtInfo", paramMap);
	}

	/**
	 * 상품마스터 중량 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectMstWgtInfo(Map<String, String> paramMap) throws Exception {
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectMstWgtInfo", paramMap);
	}

	/**
	 * 상품마스터 중량 정보 응답 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectWgtSeqRes(Map<String, String> paramMap) throws Exception {
		return (String)getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectWgtSeqRes", paramMap);
	}

	/**
	 * 중량 업데이트 요청 정보 입력
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertWgtInfo(HashMap<String, String> paramMap) throws Exception {
		this.insert("NEDMPRO0130.insertWgtInfo", paramMap);
	}

	/**
	 * 중량 업데이트 요청 정보 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteWgtInfo(NEDMPRO0130VO nedmpro0130vo) throws Exception {
		this.delete("NEDMPRO0130.deleteWgtInfo", nedmpro0130vo);
	}

	/**
	 * 중량 정보 응답 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectWgtResInfo(Map<String, String> paramMap) throws Exception {
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMPRO0130.selectWgtResInfo", paramMap);
	}
}
