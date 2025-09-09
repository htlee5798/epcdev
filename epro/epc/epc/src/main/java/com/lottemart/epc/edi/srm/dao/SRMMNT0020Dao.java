package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMMNT002001VO;
import com.lottemart.epc.edi.srm.model.SRMMNT0020VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 대표자 SRM 모니터링 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Repository("SRMMNT0020Dao")
public class SRMMNT0020Dao extends SRMDBConnDao {



	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMMNT0020VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectCatLv1CodeList", vo);
	}

	/**
	 * SRM 모니터링 LIST count
	 * @param SRMMNT0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectSRMmoniteringListCount(SRMMNT0020VO vo) throws Exception {
		return (Integer)queryForInteger("SRMMNT0020.selectSRMmoniteringListCount", vo);
	}


	/**
	 * SRM 모니터링 LIST
	 * @param SRMMNT0020VO
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
     */
	public List<SRMMNT0020VO> selectSRMmoniteringList(SRMMNT0020VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringList", vo);
	}


	/**
	 * SRM 모니터링 상세조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetail(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetail", vo);
	}

	/**
	 * SRM 모니터링 차트
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailChart(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailChart", vo);
	}

	/**
	 * 현재까지 점수
	 * @param SRMMNT002001VO
	 * @return String
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailCurrentValue(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailCurrentValue", vo);
	}


	/**
	 * 고객컴플레인수
	 * @param SRMMNT002001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMMNT002001VO vo) throws Exception {
		return (String)queryForString("SRMMNT0020.selectSRMmoniteringDetailClaim", vo);
	}


	/**
	 * 등급조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailGrade(SRMMNT002001VO vo) throws Exception {
		return (SRMMNT002001VO)queryForObject("SRMMNT0020.selectSRMmoniteringDetailGrade", vo);
	}

	/**
	 * 비고, 특이사항 조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
     */
	public SRMMNT002001VO selectSRMmoniteringDetailRemarkEtc(SRMMNT002001VO vo) throws Exception {
		return (SRMMNT002001VO)queryForObject("SRMMNT0020.selectSRMmoniteringDetailRemarkEtc", vo);
	}

	/**
	 * 등급 예제 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailGradeExemple(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailGradeExemple", vo);
	}


	/**
	 * PLC등급 리스트 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailPlc(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailPlc", vo);
	}

	/**
	 * 불량등록건수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMMNT002001VO vo) throws Exception {
		return (String)queryForString("SRMMNT0020.selectSRMmoniteringDetailDefective", vo);
	}

	/**
	 * 불량등록건수 리스트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailDefectiveList(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailDefectiveList", vo);
	}

	/**
	 * 개선조치
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailImpReq(SRMMNT002001VO vo) throws Exception {
		return (SRMMNT002001VO)queryForObject("SRMMNT0020.selectSRMmoniteringDetailImpReq", vo);
	}

	/**
	 * 첨부파일 조회
	 * @param SRMVEN003001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMMNT002001VO vo) throws Exception {
		return queryForList("SRMMNT0020.selectSRMmoniteringDetailImpReqFileList", vo);
	}


}




