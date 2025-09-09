package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMVEN003001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0030VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SRM 모니터링 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.18  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Repository("srmven0030Dao")
public class SRMVEN0030Dao extends SRMDBConnDao {



	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMVEN0030VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectCatLv1CodeList", vo);
	}

	/**
	 * SRM 모니터링 LIST count
	 * @param SRMVEN0030VO
	 * @return int
	 * @throws Exception
	 */
	public int selectSRMmoniteringListCount(SRMVEN0030VO vo) throws Exception {
		return (Integer)queryForInteger("SRMVEN0030.selectSRMmoniteringListCount", vo);
	}


	/**
	 * SRM 모니터링 LIST
	 * @param SRMVEN0030VO
	 * @return List<SRMVEN0030VO>
	 * @throws Exception
     */
	public List<SRMVEN0030VO> selectSRMmoniteringList(SRMVEN0030VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringList", vo);
	}


	/**
	 * SRM 모니터링 상세조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetail(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetail", vo);
	}

	/**
	 * SRM 모니터링 차트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailChart(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailChart", vo);
	}


	/**
	 * 현재까지 점수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailCurrentValue(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailCurrentValue", vo);
	}


	/**
	 * 고객컴플레인수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMVEN003001VO vo) throws Exception {
		return (String)queryForString("SRMVEN0030.selectSRMmoniteringDetailClaim", vo);
	}


	/**
	 * 등급조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailGrade(SRMVEN003001VO vo) throws Exception {
		return (SRMVEN003001VO)queryForObject("SRMVEN0030.selectSRMmoniteringDetailGrade", vo);
	}

	/**
	 * 비고, 특이사항 조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
     */
	public SRMVEN003001VO selectSRMmoniteringDetailRemarkEtc(SRMVEN003001VO vo) throws Exception {
		return (SRMVEN003001VO)queryForObject("SRMVEN0030.selectSRMmoniteringDetailRemarkEtc", vo);
	}

	/**
	 * 등급 예제 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailGradeExemple(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailGradeExemple", vo);
	}


	/**
	 * PLC등급 리스트 조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailPlc(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailPlc", vo);
	}

	/**
	 * 불량등록건수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMVEN003001VO vo) throws Exception {
		return (String)queryForString("SRMVEN0030.selectSRMmoniteringDetailDefective", vo);
	}

	/**
	 * 불량등록건수 리스트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailDefectiveList(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailDefectiveList", vo);
	}

	/**
	 * 개선조치
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailImpReq(SRMVEN003001VO vo) throws Exception {
		return (SRMVEN003001VO)queryForObject("SRMVEN0030.selectSRMmoniteringDetailImpReq", vo);
	}


	/**
	 * 개선조치 등옥
	 * @param SRMVEN003001VO
	 * @throws Exception
	 */
	public void updateSRMmoniteringDetailImpReq(SRMVEN003001VO vo) throws Exception {
		update("SRMVEN0030.updateSRMmoniteringDetailImpReq", vo);
	}


	/**
	 * 첨부파일 조회
	 * @param SRMVEN003001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMVEN003001VO vo) throws Exception {
		return queryForList("SRMVEN0030.selectSRMmoniteringDetailImpReqFileList", vo);
	}

}




