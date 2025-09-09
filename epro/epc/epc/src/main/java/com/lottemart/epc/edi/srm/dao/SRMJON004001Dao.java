package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON004001VO;
import lcn.module.framework.base.AbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 기본정보 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.15  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon004001Dao")
public class SRMJON004001Dao extends AbstractDAO {

	/**
	 * 주소 검색(도로명) Count
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public int selectZipListCount(SRMJON004001VO paramVO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("SRMJON0040.selectZipListCount", paramVO);
	}

	/**
	 * 주소 검색(도로명)
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<SRMJON004001VO> selectZipList(SRMJON004001VO paramVO) throws Exception {
		return (List<SRMJON004001VO>) getSqlMapClientTemplate().queryForList("SRMJON0040.selectZipList", paramVO);
	}
	
	/**
	 * 주소 검색(읍면동) Count
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public int selectZipListJiBunCount(SRMJON004001VO paramVO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("SRMJON0040.selectZipListJiBunCount", paramVO);
	}

	/**
	 * 주소 검색(읍면동)
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<SRMJON004001VO> selectZipListJiBun(SRMJON004001VO paramVO) throws Exception {
		return (List<SRMJON004001VO>) getSqlMapClientTemplate().queryForList("SRMJON0040.selectZipListJiBun", paramVO);
	}

}




