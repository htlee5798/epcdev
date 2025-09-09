package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMQNA0000VO;
import com.lottemart.epc.edi.srm.model.SRMQNA0010VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 테넌트 입점문의
 *
 * @author PARK IL YOUNG
 * @since 2024.08.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.25  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmqna0010Dao")
public class SRMQNA0010Dao extends SRMDBConnDao {

    /**
     * 테넌트 입점문의 카테고리 항목 데이터 가져오기
     * @param SRMQNA00VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaCategoryList(SRMQNA0000VO srmqna0000VO) throws Exception {
        return (List<SRMQNA0000VO>) queryForList("SRMQNA0010.selectQnaCategoryList", srmqna0000VO);
    }
    /**
     * 테넌트 입점문의 희망점포 지역 데이터 가져오기
     * @param SRMQNA00VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaStoreAreaList(SRMQNA0000VO srmqna0000VO) throws Exception {
        return (List<SRMQNA0000VO>) queryForList("SRMQNA0010.selectQnaStoreAreaList", srmqna0000VO);
    }
    /**
     * 테넌트 입점문의 지역별 점포 데이터 가져오기
     * @param SRMQNA00VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaStoreList(SRMQNA0000VO srmqna0000VO) throws Exception {
        return (List<SRMQNA0000VO>) queryForList("SRMQNA0010.selectQnaStoreList", srmqna0000VO);
    }
    /**
     * 테넌트 입점문의 작성시 사업자번호별 등록할 순번 가져오기
     * @param srmqna0010VO
     * @return String
     * @throws Exception
     */
    public String selectNextQnaReqSeq(SRMQNA0010VO srmqna0010VO) throws Exception {
        return (String) queryForString("SRMQNA0010.selectNextQnaReqSeq", srmqna0010VO);
    }
    /**
     * 테넌트 입점문의 작성
     * @param srmqna0010VO
     * @return void
     * @throws Exception
     */
    public void insertQnaInfo(SRMQNA0010VO srmqna0010VO) throws Exception {
        insert("SRMQNA0010.insertQnaInfo", srmqna0010VO);
    }

}
