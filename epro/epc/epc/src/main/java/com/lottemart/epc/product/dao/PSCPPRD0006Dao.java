package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0006VO;

/**
 * @Class Name : PSCPPRD0006Dao.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * &#64;Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 *               </pre>
 */
@Repository("pscpprd0006Dao")
public class PSCPPRD0006Dao extends AbstractDAO {

	/**
	 * 상품 이미지 목록 Desc :
	 * 
	 * @Method Name : selectPrdImageList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0006VO> selectPrdImageList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0006VO>) getSqlMapClientTemplate().queryForList("pscpprd0006.selectPrdImageList", paramMap);
	}

	/**
	 * 상품 이미지 추가 Desc :
	 * 
	 * @Method Name : updatePrdImageAdd
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdImageAdd(PSCPPRD0006VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updatePrdImageAdd", bean);
	}

	/**
	 * 상품 이미지 삭제 Desc :
	 * 
	 * @Method Name : updatePrdImageDel
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdImageDel(PSCPPRD0006VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updatePrdImageDel", bean);
	}

	/**
	 * 상품 이미지 히스토리 수정 Desc :
	 * 
	 * @Method Name : updatePrdImageDel
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdImageHist(PSCPPRD0006VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updatePrdImageHist", bean);
	}

	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectYoutubeLink(Map<String, String> paramMap) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0006.selectYoutubeLink", paramMap);
	}

	/**
	 * 
	 * @param dm
	 * @return
	 * @throws SQLException
	 */
	public int updateYoutubeSave(DataMap dm) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updateYoutubeSave", dm);
	}

	/**
	 * 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공 (2018-07-15)
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public List<DataMap> selectAprvList(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0006.selectAprvList", paramMap);
	}

	/**
	 * 상품 와이드이미지 목록 Desc :
	 * 
	 * @Method Name : selectWidePrdImageList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0006VO> selectWidePrdImageList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0006VO>) getSqlMapClientTemplate().queryForList("pscpprd0006.selectWidePrdImageList", paramMap);
	}

	/**
	 * 와이드 이미지 등록여부 업데이트 Desc :
	 * 
	 * @Method Name : updateWideImgYN
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	public int updateWideImgYN(DataMap map) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updateWideImgYN", map);
	}

	/**
	 * MD_SRCMK_CD 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectMdSrcmkCd(DataMap map) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0006.selectMdSrcmkCd", map);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public PSCPPRD0006VO selectVideoUrl(PSCPPRD0006VO bean) throws SQLException {
		return (PSCPPRD0006VO) getSqlMapClientTemplate().queryForObject("pscpprd0006.selectVideoUrl", bean);
	}

	/**
	 * 상품 동영상URL 등록/수정
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int updateVideoUrl(PSCPPRD0006VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.updateVideoUrl", bean);
	}

	/**
	 * 대표이미지 변경 이력 등록 (For OSP)
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int insertImgChgHist(DataMap dataMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0006.insertImgChgHist", dataMap);
	}

}
