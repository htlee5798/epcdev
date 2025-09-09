package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.dao.SRMJON004001Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON0040Dao;
import com.lottemart.epc.edi.srm.model.SRMJON004001VO;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0030Service;
import com.lottemart.epc.edi.srm.service.SRMJON0040Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 [기본정보]
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
@Service("srmjon0040Service")
public class SRMJON0040ServiceImpl implements SRMJON0040Service {

	@Autowired
	private SRMJON0040Dao srmjon0040Dao;

	@Autowired
	private SRMJON004001Dao srmjon004001Dao;

	@Autowired
	private SRMJON0030Service srmjon0030Service;

	@Autowired
	private ConfigurationService config;

	/**
	 * 잠재업체 기본정보 조회
	 * @param HttpServletRequest
	 * @param SRMJON0040VO
	 * @return SRMJON0040VO
	 * @throws Exception
	 */
	public SRMJON0040VO selectHiddenCompInfo(HttpServletRequest request, SRMJON0040VO vo) throws Exception {
		SRMJON0040VO tmpVo = vo ;
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		tmpVo.setSellerCode(session.getIrsNo());
		tmpVo.setHouseCode(session.getHouseCode());

		// Locale 설정
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));

		if(StringUtil.isEmpty(session.getCountry())){
			tmpVo.setCountry("KR");
		} else {
			tmpVo.setCountry(session.getCountry());
		}

//		vo.setReqSeq(session.getReqSeq());

		if (StringUtil.isEmpty(tmpVo.getReqSeq())){

			//기등록 정보가 있는 잠재업체
			SRMJON0040VO basicCompVO = srmjon0040Dao.selectHiddenCompBasicInfo(tmpVo);
			if (basicCompVO != null) {
				basicCompVO.setChannelCode(vo.getChannelCode());
				basicCompVO.setCatLv1Code(vo.getCatLv1Code());
				basicCompVO.setCatLv1CodeNm(vo.getCatLv1CodeNm());
				return basicCompVO;
			}
			//SSUGL(잠재업체)는 없지만 SSUGL_PARTNER(파트너사)정보는 존재 하는 경우
//			SRMJON0040VO partnerCompVO = srmjon0040Dao.selectHiddenCompPartnerInfo(vo);
//			if (partnerCompVO != null) {
//				partnerCompVO.setChannelCode(vo.getChannelCode());
//				partnerCompVO.setCatLv1Code(vo.getCatLv1Code());
//				partnerCompVO.setCatLv1CodeName(vo.getCatLv1CodeName());
//				partnerCompVO.setShipperType(session.getShipperType());
//				return partnerCompVO;
//			}


			//신규정보 잠재업체
			tmpVo.setShipperType(session.getShipperType());
			tmpVo.setSellerNameLoc(session.getSellerNameLoc());
			tmpVo.setCountry(session.getCountry());
			if (session.getShipperType().equals("1")) {
				tmpVo.setCountry("KR");
			}
			return tmpVo;
		}

		tmpVo = srmjon0040Dao.selectHiddenCompInfo(vo);
		return tmpVo;
	}

	/**
	 * 잠재업체 기본정보 수정
	 * @param SRMJON0040VO
	 * @return HashMap<String, String>
	 * @throws Exception
     */
	public Map<String, Object> updateHiddenCompInfo(SRMJON0040VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(sessionKey);

		vo.setSellerCode(session.getIrsNo());
		vo.setHouseCode(session.getHouseCode());
		vo.setCountry(session.getCountry());

		String agreeType = selecthiddenCompAgreeType(vo, request);
		if (!agreeType.equals("1")) {
			vo.setFaxFhone("");
		}
		
		resultMap = srmjon0030Service.consultStatusCheck(vo);	// 채널, 대분류로 동일한 신청내역 체크
		if (!resultMap.get("message").equals("SUCCESS")) {
			return resultMap;
		}

		if(StringUtil.isNotEmpty(vo.getTempPw())){
			//----- 비밀번호 유효성 체크 --------------------
			if (vo.getTempPw().length() < 8) {
				resultMap.put("message", "NOT_LENGTH");
				return resultMap;
			}

			// 공백체크
			if (!SRMCommonUtils.isSpaceCheck(vo.getTempPw())) {
				resultMap.put("message", "EXIST_SPACE");
				return resultMap;
			}

			// 허용된 문자열인지 체크
			if (!SRMCommonUtils.isPermitPasswordCharCheck(vo.getTempPw())) {
				resultMap.put("message", "NOT_PERMIT");
				return resultMap;
			}

			// 중복된 3자 이상의 문자 또는 숫자 사용불가
			/*if (!SRMCommonUtils.isDuplicate3Character(vo.getTempPw())) {
				resultMap.put("message", "EXIST_DUPL");
				return resultMap;
			}*/

			// 문자, 숫자, 특수문자 혼용
			if (!SRMCommonUtils.isPasswordCharCheck(vo.getTempPw())) {
				resultMap.put("message", "NOT_MATCH");
				return resultMap;
			}
			//---------------------------------------------

			vo.setTempPw(SRMCommonUtils.EncryptSHA256(vo.getTempPw()));
		}

		if (srmjon0040Dao.selectHiddenCompCheck(vo) > 0) {
			//잠재업체 수정
			srmjon0040Dao.updateHiddenCompInfo(vo);
		} else {
			//잠재업체 등록
			srmjon0040Dao.insertHiddenCompInfo(vo);
		}
		if (!StringUtil.isEmpty(vo.getReqSeq())){
			srmjon0040Dao.updateConsultReqRequest(vo);
		} else {
			vo.setReqSeq(srmjon0040Dao.selectConsultReqSeq(vo));
			srmjon0040Dao.insertConsultReqRequest(vo);
		}

		if(vo != null && srmjon0040Dao.selecthiddenCompSSUPICount(vo) > 0){
			//수정
			srmjon0040Dao.updateHiddenCompInfoSSUPI(vo);
		} else {
		 	//등록
			srmjon0040Dao.insertHiddenCompInfoSSUPI(vo);
		}

		resultMap.put("message","SUCCESS");
		resultMap.put("reqSeq",vo.getReqSeq());

		return resultMap;
	}

	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList() throws Exception {
		return srmjon0040Dao.selectCatLv1CodeList();
	}

	/**
	 * 주소 검색
	 */
	public Map<String,Object> selectZipList(SRMJON004001VO paramVO, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(paramVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(paramVO.getRecordCountPerPage());
		paginationInfo.setPageSize(paramVO.getPageSize());

		result.put("paginationInfo", paginationInfo);

		paramVO.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		paramVO.setLastIndex(paginationInfo.getLastRecordIndex());
		paramVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = 0;

		if (paramVO.getSrchGbn().equals("0")) {
			totCnt = srmjon004001Dao.selectZipListJiBunCount(paramVO);
		} else {
			totCnt = srmjon004001Dao.selectZipListCount(paramVO);
		}
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMJON004001VO>	resultList 	= 	null;

		if (paramVO.getSrchGbn().equals("0")) {
			resultList = srmjon004001Dao.selectZipListJiBun(paramVO);
		} else {
			resultList = srmjon004001Dao.selectZipList(paramVO);
		}

		result.put("totCnt", totCnt);

		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", paramVO.getPageIndex());

		// 화면에 보여줄 페이징 생성
		result.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return result;
	}

	@Override
	public String selecthiddenCompAgreeType(SRMJON0040VO paramVO, HttpServletRequest request) throws Exception {
		String AgreeType = srmjon0040Dao.selecthiddenCompAgreeType(paramVO);
		if (AgreeType == null || AgreeType.isEmpty()) {
			AgreeType = "1";
		}
		return AgreeType;
	}

}
