package com.lottemart.epc.edi.product.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;

import signgate.provider.ec.arithmetic.curves.exceptions.ECException;

@Component("nedmproMgrCodeDao")
public class NEDMPROMgrCodeDao extends SqlMapClientDaoSupport{
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	
	/**
	 * 공통 코드 마스터 총 갯수
	 * @param vo
	 * @return
	 * @throws ECException
	 */
	public int selectMgrCodeListCount(CommonProductVO vo) throws ECException {
		return (Integer) getSqlMapClientTemplate().queryForObject("NedmproMgrCode.selectMgrCodeListCount", vo); 
	}

	/**
	 * 공통코드 마스터  리스트 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectMgrCodeList(CommonProductVO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NedmproMgrCode.selectMgrCodeList", vo);
	}	
	
	/**
	 * 상세 코드 총 갯수
	 * @param vo
	 * @return
	 * @throws ECException
	 */
	public int selectMgrCodeDtlListCount(CommonProductVO vo) throws ECException {
		return (Integer) getSqlMapClientTemplate().queryForObject("NedmproMgrCode.selectMgrCodeDtlListCount", vo); 
	}
	
	/**
	 * 상세 코드 리스트 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectMgrCodeDtlList(CommonProductVO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NedmproMgrCode.selectMgrCodeDtlList", vo);
	}
	
	/**
	 * 분류코드 마스터 코드 중복검사
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectMasterCodeCount (CommonProductVO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NedmproMgrCode.selectMasterCodeCount", vo); 
	}
	
	/**
	 * 마스터코드 변경시 세부코드 의 마스터코드 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void upateCodeSubAll (CommonProductVO vo) throws Exception {
		 getSqlMapClientTemplate().update("NedmproMgrCode.upateCodeSubAll", vo); 
	}
	
	/**
	 * 마스터 코드 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void updateCodeMaster (CommonProductVO vo) throws Exception {
		  getSqlMapClientTemplate().update("NedmproMgrCode.updateCodeMaster", vo); 
	}
	
	/**
	 * 마스터 코드 저장 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void insertCodeMaster (CommonProductVO vo) throws Exception {
		  getSqlMapClientTemplate().insert("NedmproMgrCode.insertCodeMaster", vo); 
	}

	
	/**
	 * 세부 코드 중복검사 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectSubCodeCount (CommonProductVO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NedmproMgrCode.selectSubCodeCount", vo); 
	}
	
	/**
	 * 세부코드 수정 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void updateCodeSub (CommonProductVO vo) throws Exception {
		  getSqlMapClientTemplate().update("NedmproMgrCode.updateCodeSub", vo); 
	}
	
	/**
	 * 세부코드 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void insertCodeSub (CommonProductVO vo) throws Exception {
		  getSqlMapClientTemplate().insert("NedmproMgrCode.insertCodeSub", vo); 
	}
	
	/**
	 * 세부코드 삭제 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void deleteMgrDtlCode (CommonProductVO vo) throws Exception {
		  getSqlMapClientTemplate().update("NedmproMgrCode.deleteMgrDtlCode", vo); 
	}
	
	/**
	 * 코드 저장시 중복 검사 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectCodeCount (CommonProductVO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NedmproMgrCode.selectCodeCount", vo); 
	}
	
}
