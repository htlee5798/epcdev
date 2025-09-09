package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.edi.srm.dao.SRMJON0042Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0042VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;
import com.lottemart.epc.edi.srm.service.SRMJON0042Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 [신용/인증정보]
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.19  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Service("srmjon0042Service")
public class SRMJON0042ServiceImpl implements SRMJON0042Service {
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private SRMJON0042Dao srmjon0042Dao;

	@Autowired
	private SRMJON0041Service srmjon0041Service;

	/**
	 * 잠재업체 신용정보 조회
	 * @param SRMJON0042VO
	 * @return SRMJON0042VO
	 * @throws Exception
	 */
	public SRMJON0042VO selectHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception {
		return srmjon0042Dao.selectHiddenCompCreditInfo(vo);
	}

	/**
	 * 잠재업체 신용정보 수정
	 * @param SRMJON0042VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public HashMap<String, String> updateHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();

		vo.setCreditAttachNo(srmjon0041Service.fileSave(vo.getCreditAttachNo(), vo.getCreditAttachNoFile(), vo.getSellerCode(), srmjon0042Dao.selectHiddenCompCreditAttachNoFileId(vo)));
		//통화,제거
		vo.setCreditBasicDate(vo.getCreditBasicDate().replace("-",""));

		srmjon0042Dao.updateHiddenCompCreditInfo(vo);
		srmjon0042Dao.updateHiddenCompCreditInfoReq(vo);

		resultMap.put("message","SUCCESS");
		return resultMap;
	}
	
	/**
	 * 신용평가기관 정보 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String selectCreditInfo(SRMJON0042VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
		String sellerCode = session.getSellerCode();
		vo.setSellerCode(sellerCode);
		// 신용평가 기관별 데이터 조회
		SRMJON0042VO dataInfo = new SRMJON0042VO();
		if (vo.getCreditCompanyCode().equals("1")) {			// 이크레더블
			dataInfo = srmjon0042Dao.selectEcredibleCreditInfo(vo);
		} else if (vo.getCreditCompanyCode().equals("2")) {		// 한국기업데이터
			dataInfo = srmjon0042Dao.selectIntrCreditInfo(vo);
		} else if (vo.getCreditCompanyCode().equals("3")) {		// 나이스디앤비
			dataInfo = srmjon0042Dao.selectNicednbCreditInfo(vo);
		} else {
			return "NO_COMPANY_INFO";	// 신용평가기관 정보가 없는 경우
		}
		
		if (dataInfo != null) {
			if (!dataInfo.getCreditBasicDate().equals(vo.getCreditBasicDate()) || !dataInfo.getCreditRating().equals(vo.getCreditRating())) {
				return "MISS_MATCH_INFO";	// 정보가 일치하지 않는 경우
			}

			int diffDay = DateUtil.getDaysDiff(DateUtil.getCurrentDateAsString(), dataInfo.getCreditBasicEndDate());

			if(diffDay < 0) {
				return "END_CREDIT_DATE";			//만료일이 1달미만으로 남은경우
			}

		} else {
			return "NO_CREDIT_INFO";	// 신용평가 정보가 없는 경우
		}
		
		return "success";
	}
	
	/**
     * 신용평가기관별 정보조회
     * @param SRMJON0042VO
     * @param HttpServletRequest
     * @return List<SRMJON0042VO>
     * @throws Exception
     */
    public List<SRMJON0042VO> selectCreditAllInfo(SRMJON0042VO vo, HttpServletRequest request) throws Exception {
    	
    	SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
    	
    	vo.setLocale(SRMCommonUtils.getLocale(request)); // Locale 설정
    	vo.setSellerCode(session.getSellerCode());			
    			
    	return srmjon0042Dao.selectCreditAllInfo(vo);
    }

}
