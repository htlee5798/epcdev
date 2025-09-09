package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0006VO;

/**
 * @Class Name : PSCPPRD0006Service.java
 * @Description :
 * @Modification Information
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
public interface PSCPPRD0006Service {
	/**
	 * 상품 이미지 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0006VO> selectPrdImageList(Map<String, String> paramMap) throws Exception;

	/**
	 * 상품 이미지 추가 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageAdd(PSCPPRD0006VO bean) throws Exception;

	/**
	 * 상품 이미지 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageDel(PSCPPRD0006VO bean) throws Exception;

	/**
	 * 상품 이미지 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageHist(PSCPPRD0006VO bean) throws Exception;

	public DataMap selectYoutubeLink(Map<String, String> paramMap) throws Exception;

	public int updateYoutubeSave(DataMap dm) throws Exception;

	//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
	public List<DataMap> selectAprvList(Map<String, String> paramMap) throws Exception;

	/**
	 * 상품 와이드이미지 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0006VO> selectWidePrdImageList(Map<String, String> paramMap) throws Exception;

	/**
	 * 와이드이미지 등록여부 업데이트
	 * @param DataMap
	 * @return int
	 * @throws Exception
	 */
	public int updateWideImgYN(DataMap map) throws Exception;

	public DataMap selectMdSrcmkCd(DataMap map) throws Exception;

	public PSCPPRD0006VO selectVideoUrl(PSCPPRD0006VO bean) throws Exception;

	/**
	 * 동영상 URL 수정
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateVideoUrl(PSCPPRD0006VO bean) throws Exception;
	
	/**
	 * 대표이미지 변경 이력 등록 (For OSP)
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public int insertImgChgHist(DataMap dataMap) throws Exception;
}
