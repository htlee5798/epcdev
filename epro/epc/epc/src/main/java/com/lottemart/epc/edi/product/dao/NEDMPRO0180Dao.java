package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0043VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0180VO;

@Component("nedmpro0180Dao")
public class NEDMPRO0180Dao extends SqlMapClientDaoSupport {
 
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * PB 상품 성적서 정보 불러오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0180VO> selectPbProdList(NEDMPRO0180VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0180.selectPbProdList", vo);
	}

	/**
	 * PB 상품 성적서 상세 정보 불러오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0180VO selectPbProdReportInfo(NEDMPRO0180VO vo) throws Exception {
		return (NEDMPRO0180VO)getSqlMapClientTemplate().queryForObject("NEDMPRO0180.selectPbProdReportInfo", vo);
	}
	
	/**
	 * 한 상품의 가장 오래된 성적서 정보 식별자 값 가져오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0180VO selectOldSeqReportFile(NEDMPRO0180VO vo) throws Exception {
		return (NEDMPRO0180VO)getSqlMapClientTemplate().queryForObject("NEDMPRO0180.selectOldSeqReportFile", vo);
	}
	
	/**
	 * 한 상품의 저장될 성적서 정보 식별자 값 가져오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0180VO selectSeqRecentReportFile(NEDMPRO0180VO vo) throws Exception {
		return (NEDMPRO0180VO)getSqlMapClientTemplate().queryForObject("NEDMPRO0180.selectSeqRecentReportFile", vo);
	}
	
	/**
	 * PB 상품 성적서 정보 업데이트
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public void updateReportFile(NEDMPRO0180VO vo) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0180.updateReportFile", vo);
	}
	
	/**
	 * PB 상품 성적서 정보 삭제하기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public void deleteReportFile(NEDMPRO0180VO vo) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0180.deleteReportFile", vo);
	}

	/**
	 * 한 상품의 저장된 성적서 정보 불러오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0180VO> selectReportFileListForAProd(String prodCd) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0180.selectReportFileListForAProd", prodCd);
	}
	
	/**
	 * 유효하지 않은 성적서 개수 가져오기
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public Integer countNotValidPbProdReport(NEDMPRO0180VO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0180.countNotValidPbProdReport", vo);
	}
	
	
	/**
	 * 한 상품의 저장된 성적서 개수
	 * @Pparam vo
	 * @return
	 * @throws Exception
	 */
	public Integer countReportFileForAProd(NEDMPRO0180VO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0180.countReportFileForAProd", vo);
	}
	
	/**
	 * 업체별 저장해야할 성적서 개수 가져오기
	 * @Pparam admFG
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectEntpCdForNotValidReport(String admFg) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0180.selectEntpCdForNotValidReport",admFg);
	}
	
	/**
	 * PB상품 성적서 관리 부서 조회
	 * @Pparam adminId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAdminDeptPbReport(String adminId) throws Exception {
		return (List<String>) getSqlMapClientTemplate().queryForList("NEDMPRO0180.selectAdminDeptPbReport",adminId);
	}
}
