package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMKOR0010VO;

/**
 * @Class Name : NEDMKOR0010Dao
 * @Description : 코리안넷 POG이미지 Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 */

@Repository("nEDMKOR0010Dao")
public class NEDMKOR0010Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 선택한 협력업체코드의 사업자번호 가져오기
	 * @param entpCd
	 * @return
	 * @throws Exception
	 */
	public String selectBmanNo(String entpCd) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMKOR0010.selectBmanNo", entpCd);
	}
	
	/**
	 * 해당일 최근 토큰정보 조회
	 * @return
	 * @throws Exception
	 */
	public String selectAuthToken() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMKOR0010.selectAuthToken", null);
	}
	
	/**
	 * 인증토큰 저장
	 * @param paramHm
	 * @return
	 * @throws Exception
	 */
	public void insertAuthToken(HashMap authHm) throws Exception {
		getSqlMapClientTemplate().insert("NEDMKOR0010.insertAuthToken", authHm);
	}
	
	/**
	 * 변형속성 가져오기
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public List<NEDMKOR0010VO> selectVariant(String pgmId) throws Exception {
		return (List<NEDMKOR0010VO>) getSqlMapClientTemplate().queryForList("NEDMKOR0010.selectVariant", pgmId);
	}
	
	/**
	 * POG 이미지 정보 삭제(개별)
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteSaleImg(HashMap paramHm) throws Exception {
		getSqlMapClientTemplate().delete("NEDMKOR0010.deleteSaleImg", paramHm);
	}
	
	/**
	 * POG 이미지 정보 삭제(전체)
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteSaleImgAll(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMKOR0010.deleteSaleImgAll", pgmId);
	}
	
	/**
	 * POG 이미지 정보 추가
	 * @param pgmId
	 * @throws Exception
	 */
	public void insertSaleImg(NEDMKOR0010VO vo) throws Exception {
		getSqlMapClientTemplate().delete("NEDMKOR0010.insertSaleImg", vo);
	}

}
