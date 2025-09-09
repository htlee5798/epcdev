package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMQNA0000VO;
import com.lottemart.epc.edi.srm.model.SRMQNA0010VO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface SRMQNA0010Service {

    /**
     * QNA 게시판 작성 화면 카테고리 목록 조회
     * @param SRMQNA0000VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaCategoryList(SRMQNA0000VO srmqna0000VO) throws Exception;

    /**
     * 점포 영역 조회
     * @param SRMQNA0000VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaStoreAreaList(SRMQNA0000VO srmqna0000VO) throws Exception;

    /**
     * 영역별 점포 조회
     * @param SRMQNA0000VO
     * @return List<SRMQNA0000VO>
     * @throws Exception
     */
    public List<SRMQNA0000VO> selectQnaStoreList(SRMQNA0000VO srmqna0000VO) throws Exception;

    /**
     * QNA 게시판 작성
     * @param SRMQNA0010VO
     * @return void
     * @throws Exception
     */
    public void insertQnaInfo(SRMQNA0010VO srmqna0010VO) throws Exception;

    /**
     * QNA 파트너사에 메일 전송
     * @param SRM0010VO
     * @return void
     * @throws Exception
     */
    public void sendEmailToSeller(SRMQNA0010VO srmqna0010VO) throws Exception;

    /**
     * 테넌트 입점문의 파일 업로드 (올릴 파일, 사업자번호)
     * @param MultipartFile
     * @param String
     * @return String
     * @throws Exception
     */
    public String uploadQnaFile(MultipartFile uploadfile, String SellerCode) throws Exception;
}
