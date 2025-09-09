package com.lottemart.epc.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.main.dao.MainViewDao;
import com.lottemart.epc.main.service.MainViewService;

/**
 * @Class Name : PSCPPRD0005ServiceImpl.java
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
@Service("mainViewService")
public class MainViewServiceImpl implements MainViewService {

	@Autowired
	private MainViewDao mainViewDao;

	/**
	 * 추가설명 정보
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectBoardList() throws Exception {
		return mainViewDao.selectBoardList();
	}

	/**
	 * 추가설명 정보
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPopupBoardList() throws Exception {
		return mainViewDao.selectPopupBoardList();
	}

	public List<DataMap> selectIntroCountList(DataMap paramMap) throws Exception {
		return mainViewDao.selectIntroCountList(paramMap);
	}

	/**
	 * 위수탁 업체 로그인 시 미처리 문의건 확인
	 * @param paramMap
	 * @return 미처리 문의 건수
	 * @throws Exception
	 */
	public int selectTotalQnaChkCnt(DataMap paramMap) throws Exception {
		return mainViewDao.selectTotalQnaChkCnt(paramMap);
	}

	/**
	 * 업체 주문배송비관리
	 * @param paramMap
	 * @return 배송설정여부
	 * @throws Exception
	 */
	public String selectEdiNochDeliYn(DataMap paramMap) throws Exception {
		return mainViewDao.selectEdiNochDeliYn(paramMap);
	}

	/**
	 * 업체정보조회
	 * @param String
	 * @return 업체정보조회
	 * @throws Exception
	 */
	public List<DataMap> selectVendorList(String[] cono) throws Exception {
		return mainViewDao.selectVendorList(cono);
	}

	/**
	 * 담당자 휴대번호 조회
	 * @param dataMap
	 * @return 담당자 휴대번호 조회
	 * @throws Exception
	 */
	public List<DataMap> vendorUserTelList(DataMap dataMap) throws Exception {
		return mainViewDao.vendorUserTelList(dataMap);
	}

	/**
	 * SMS 인증코드 발송
	 * @param dataMap
	 * @return 인증코드 저장 여부
	 * @throws Exception
	 */
	public int smsAuthCodeInsert(DataMap dataMap) throws Exception {
		mainViewDao.smsCodeUpdate(dataMap);
		return mainViewDao.smsAuthCodeInsert(dataMap);
	}

	/**
	 * SMS 인증코드 체크
	 * @param dataMap
	 * @return 인증코드 저장 여부
	 * @throws Exception
	 */
	public DataMap smsCodeCheckSelect(DataMap dataMap) throws Exception {
		return mainViewDao.smsCodeCheckSelect(dataMap);
	}

	/**
	 * 인증 코드 저장
	 * @param dataMap
	 * @return 인증 코드 저장
	 * @throws Exception
	 */
	public void smsCodeUpdate(DataMap dataMap) throws Exception {
		mainViewDao.smsCodeUpdate(dataMap);
	}

	/**
	 * 개인정보 수집이용 동의여부 저장
	 * @param dataMap
	 * @throws Exception
	 */
	public void insertUserInfoApply(DataMap dataMap) throws Exception {
		mainViewDao.insertUserInfoApply(dataMap);
	}

	/**
	 * TIEMSTAMP (Milliseconds까지)
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getSystimestampMs() throws Exception {
		return mainViewDao.getSystimestampMs();
	}

}
