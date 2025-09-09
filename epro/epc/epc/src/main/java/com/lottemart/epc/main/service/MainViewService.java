package com.lottemart.epc.main.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCPPRD0005Service.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface MainViewService {
	/**
	 * 추가설명 정보
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectBoardList() throws Exception;

	/**
	 * 추가설명 정보
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPopupBoardList() throws Exception;

	/**
	 * 추가설명 정보
	 * 
	 * @param
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectIntroCountList(DataMap paramMap) throws Exception;

	/**
	 * 위수탁 업체 로그인 시 미처리 문의건 확인
	 * 
	 * @param paramMap
	 * @return 미처리 문의 건수
	 * @throws Exception
	 */
	public int selectTotalQnaChkCnt(DataMap paramMap) throws Exception;

	/**
	 * 업체 주문배송비관리
	 * @param paramMap
	 * @return 배송설정여부
	 * @throws Exception
	 */
	public String selectEdiNochDeliYn(DataMap paramMap) throws Exception;
	
	/**
	 * 업체코드, 업체 명 조회
	 * @param String
	 * @return 업체정보
	 * @throws Exception
	 */
	public List<DataMap> selectVendorList(String[] cono) throws Exception;
	
	/**
	 * 협력업체 전화번호 조회
	 * @param String
	 * @return 업체 전화번호
	 * @throws Exception
	 */
	public List<DataMap> vendorUserTelList(DataMap dataMap) throws Exception;
	
	/**
	 * SMS 인증번호 발송
	 * @param String
	 * @return 인증코드 저장 여부
	 * @throws Exception
	 */
	public int smsAuthCodeInsert(DataMap dataMap) throws Exception;
	
	/**
	 * SMS 인증코드 체크
	 * @param String
	 * @return 인증코드 유효성 체크
	 * @throws Exception
	 */
	public DataMap smsCodeCheckSelect(DataMap dataMap) throws Exception;
	
	/**
	 * 담당자별 인증 코드 저장
	 * @param String
	 * @return 담당자별 인증 코드 저장
	 * @throws Exception
	 */
	public void smsCodeUpdate(DataMap dataMap) throws Exception;

	/**
	 * 개인정보 수집이용 동의여부 저장
	 * @param dataMap
	 * @throws Exception
	 */
	public void insertUserInfoApply(DataMap dataMap) throws Exception;

	/**
	 * TIEMSTAMP (Milliseconds까지)
	 * @return
	 * @throws Exception
	 */
	public String getSystimestampMs() throws Exception;
}
