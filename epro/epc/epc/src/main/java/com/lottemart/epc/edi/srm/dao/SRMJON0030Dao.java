package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMJON0030VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 1차 스크리닝 Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.06  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Repository("srmjon0030Dao")
public class SRMJON0030Dao  extends SRMDBConnDao {

    /**
     * 1차 스크리닝 LIST 조회
     * @param SRMJON0030VO
     * @return List<SRMJON0030VO>
     * @throws Exception
     */
    public List<SRMJON0030VO> selectScreeningList(SRMJON0030VO vo) throws Exception {
        return (List<SRMJON0030VO>)queryForList("SRMJON0030.selectScreeningList", vo);
    }

    /**
     * 1차 스크리닝 결과 등록
     * @param SRMJON0030VO
     * @throws Exception
     */
    public void insertScreeningList(SRMJON0030VO vo) throws Exception {
        insert("SRMJON0030.insertScreeningList", vo);
    }

    /**
     * 대분류 코드 조회
     * @param SRMJON0030VO
     * @return List<SRMJON0030VO>
     * @throws Exception
     */
    public List<SRMJON0030VO> selectCatLv1CodeList(SRMJON0030VO vo) throws Exception {
        return (List<SRMJON0030VO>)queryForList("SRMJON0030.selectCatLv1CodeList", vo);
    }

}




