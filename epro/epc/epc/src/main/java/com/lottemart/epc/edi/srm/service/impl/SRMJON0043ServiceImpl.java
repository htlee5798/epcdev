package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.edi.srm.dao.SRMJON0040Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON004301Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON0043Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0040VO;
import com.lottemart.epc.edi.srm.model.SRMJON0042VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.service.SRMJON0042Service;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 [정보확인]
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
@Service("srmjon0043Service")
public class SRMJON0043ServiceImpl implements SRMJON0043Service {

	@Autowired
	private SRMJON0043Dao srmjon0043Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Autowired
	private SRMJON0040Dao srmjon0040Dao;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SRMJON0042Service srmjon0042Service;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 잠재업체 정보 조회
	 * @param SRMJON0043VO
	 * @return SRMJON0043VO
	 * @throws Exception
	 */
	public SRMJON0043VO selectHiddenComp(SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return srmjon0043Dao.selectHiddenComp(vo);
	}

	/**
	 * 잠재업체 입점상담신청 수정
	 * @param SRMJON0043VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public HashMap<String, String> updateHiddenCompReq(SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		SRMJON0043VO tmpVo = vo;
		HashMap<String, String> resultMap = new HashMap<String, String>();
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));
		//최종 VALIDATION 체크
		tmpVo = srmjon0043Dao.selectHiddenComp(tmpVo);


		//기존 채널, 대분류로 신청내역 상태 조회
		SRMJON0040VO srmjon0040vo = new SRMJON0040VO();
		srmjon0040vo.setHouseCode(tmpVo.getHouseCode());
		srmjon0040vo.setSellerCode(tmpVo.getSellerCode());
		srmjon0040vo.setChannelCode(tmpVo.getChannelCode());
		srmjon0040vo.setIrsNo(tmpVo.getSellerCode());
		srmjon0040vo.setCatLv1Code(tmpVo.getCatLv1Code());

		if(srmjon0043Dao.selectCatLv1CodeCheck(tmpVo) == 0){
			resultMap.put("message","FAIL-CAT_LV1_CODE_DEL");
			return resultMap;
		}

		List<String> statusList = srmjon0040Dao.selectConsultStatusList(srmjon0040vo);
		int statusCnt = 0;
		boolean status = true;
		for(int i=0; i<statusList.size(); i++) {
			if(i < 3) {
				if (statusList.get(i).equals("C02") || statusList.get(i).equals("D05") || statusList.get(i).equals("G06") || statusList.get(i).equals("H04")) {
					statusCnt++;
				}
			}
			if(statusList.get(i).equals("Z01")) {
				status = false;
			}
		}
		if(statusCnt >=3 && status) {
			resultMap.put("message","FAIL-REFUSE");
			return resultMap;
		}
		//채널코드
		if (StringUtil.isEmpty(tmpVo.getChannelCode())) {
			resultMap.put("message", "FAIL-CHANNEL_CODE");
			return resultMap;
		}
		//분류
		if (StringUtil.isEmpty(tmpVo.getCatLv1Code())) {
			resultMap.put("message","FAIL-CAT_LV1_CODE");
			return resultMap;
		}
		//해외업체구분
		if (StringUtil.isEmpty(tmpVo.getShipperType())) {
			resultMap.put("message","FAIL-SHIPPER_TYPE");
			return resultMap;
		}
		//비밀번호
		if (StringUtil.isEmpty(tmpVo.getTempPw())) {
			resultMap.put("message","FAIL-TEMP_PW");
			return resultMap;
		}
		//상호명
		if (StringUtil.isEmpty(tmpVo.getSellerNameLoc())) {
			resultMap.put("message","FAIL-SELLER_NAME_LOC");
			return resultMap;
		}
		//법인구분
		if (StringUtil.isEmpty(tmpVo.getCompanyType())) {
			resultMap.put("message","FAIL-COMPANY_TYPE");
			return resultMap;
		}

		//법인번호
		if (StringUtil.isEmpty(tmpVo.getCompanyRegNo()) && tmpVo.getCompanyType().equals("1")) {
			resultMap.put("message","FAIL-COMPANY_REG_NO");
			return resultMap;
		}

		//대표자명
		if (StringUtil.isEmpty(tmpVo.getSellerCeoName())) {
			resultMap.put("message","FAIL-SELLER_CEO_NAME");
			return resultMap;
		}
		//담당자명
		if (StringUtil.isEmpty(tmpVo.getvMainName())) {
			resultMap.put("message","FAIL-V_MAIN_NAME");
			return resultMap;
		}
		//대표전화
		if (StringUtil.isEmpty(tmpVo.getvPhone1())) {
			resultMap.put("message","FAIL-V_PHONE1");
			return resultMap;
		}
		//휴대전화
		if (StringUtil.isEmpty(tmpVo.getvMobilePhone())) {
			resultMap.put("message","FAIL-MOBILE_PHONE");
			return resultMap;
		}
		//E-mail
		if (StringUtil.isEmpty(tmpVo.getSellerCeoEmail())) {
			resultMap.put("message","FAIL-SELLER_CEO_EMAIL");
			return resultMap;
		}
		//E-mail
		if (StringUtil.isEmpty(tmpVo.getvEmail())) {
			resultMap.put("message","FAIL-V_EMAIL");
			return resultMap;
		}

		//국내 업체일경우
		if(tmpVo.getShipperType().equals("1")) {
			//주소
			if (StringUtil.isEmpty(tmpVo.getZipcode())) {
				resultMap.put("message","FAIL-ADDRESS");
				return resultMap;
			}
			if (StringUtil.isEmpty(tmpVo.getAddress1())) {
				resultMap.put("message","FAIL-ADDRESS");
				return resultMap;
			}
			if (StringUtil.isEmpty(tmpVo.getAddress2())) {
				resultMap.put("message","FAIL-ADDRESS");
				return resultMap;
			}
		}


		//국외 업체일경우
		if(tmpVo.getShipperType().equals("2")) {
			//주소
			if (StringUtil.isEmpty(tmpVo.getAddress1())) {
				resultMap.put("message","FAIL-ADDRESS");
				return resultMap;
			}
		}

		//업종
		if (StringUtil.isEmpty(tmpVo.getIndustryType())) {
			resultMap.put("message","FAIL-INDUSTRY_TYPE");
			return resultMap;
		}
		//업태
		if (StringUtil.isEmpty(tmpVo.getBusinessType())) {
			resultMap.put("message","FAIL-BUSINESS_TYPE");
			return resultMap;
		}
		//사업유형
		if (StringUtil.isEmpty(tmpVo.getSellerType())) {
			resultMap.put("message","FAIL-SELLER_TYPE");
			return resultMap;
		}
		//Global은 선택 Domestic은 필수
		if (tmpVo.getShipperType().equals("1") && !tmpVo.getChannelCode().equals("06") && !tmpVo.getChannelCode().equals("99")) {
			//평가번호(DNA)
//			if (StringUtil.isEmpty(tmpVo.getCreditNo())) {
//				resultMap.put("message","FAIL-CREDIT");
//				return resultMap;
//			}
			//신용평가사
			if (StringUtil.isEmpty(tmpVo.getCreditCompanyCode())) {
				resultMap.put("message","FAIL-CREDIT");
				return resultMap;
			}
			//신용등급
			if (StringUtil.isEmpty(tmpVo.getCreditRating())) {
				resultMap.put("message","FAIL-CREDIT");
				return resultMap;
			}
			//신용평가 기준일자
			if (StringUtil.isEmpty(tmpVo.getCreditBasicDate())) {
				resultMap.put("message","FAIL-CREDIT");
				return resultMap;
			}
			//신용평가사본
			if (StringUtil.isEmpty(tmpVo.getCreditAttachNo())) {
				resultMap.put("message","FAIL-CREDITE_ATTACH_NO");
				return resultMap;
			}

			//----- 신용평가기관 정보와 비교 ------------------------------
			SRMJON0042VO paramVO = new SRMJON0042VO();
			paramVO.setCreditCompanyCode(tmpVo.getCreditCompanyCode());
			paramVO.setCreditRating(tmpVo.getCreditRating());
			paramVO.setCreditBasicDate(tmpVo.getCreditBasicDate());

			paramVO.setCompanyType(tmpVo.getCompanyType());
			paramVO.setCompanyRegNo(tmpVo.getCompanyRegNo());

			String retMsg = srmjon0042Service.selectCreditInfo(paramVO, request);

			if (!retMsg.equals("success")) {
				resultMap.put("message",retMsg);
				return resultMap;
			}
			//--------------------------------------------------
		}


		//설립년도
		if (StringUtil.isEmpty(tmpVo.getFoundationDate())) {
			resultMap.put("message","FAIL-FOUNDATION_DATE");
			return resultMap;
		}
		//자본금
		if (StringUtil.isEmpty(tmpVo.getBasicAmt())) {
			resultMap.put("message","FAIL-BASIC_AMT");
			return resultMap;
		}
		//연간 매출액
		if (StringUtil.isEmpty(tmpVo.getSalesAmt())) {
			resultMap.put("message","FAIL-SALES_AMT");
			return resultMap;
		}
		//종업원수
		if (StringUtil.isEmpty(tmpVo.getEmpCount())) {
			resultMap.put("message","FAIL-EMP_COUNT");
			return resultMap;
		}
		//공장운영형태
		if ("X".equals(tmpVo.getPlantOwnType()) && StringUtil.isEmpty(tmpVo.getPlantRoleType())) {
			resultMap.put("message","FAIL-PLANT_ROLE_TYPE");
			return resultMap;
		}
		//동업계 입점현황
		if (StringUtil.isEmpty(tmpVo.getMainCustomer())) {
			resultMap.put("message","FAIL-MAIN_CUSTOMER");
			return resultMap;
		}
		//롯데마트 기 진출 채널
		if (StringUtil.isEmpty(tmpVo.getAboardChannelText())) {
			resultMap.put("message","FAIL-ABOARD_CHANNEL_TEXT");
			return resultMap;
		}
		//온라인몰 메일 발송대상
		if (StringUtil.isEmpty(tmpVo.getOnlineMailTarget()) && "06".equals(tmpVo.getChannelCode())) {
			resultMap.put("message","FAIL-ONLINE_MAIL_TARGET");
			return resultMap;
		}

		srmjon0043Dao.updateHiddenCompReq(tmpVo);

		//EMS 발송
		List<SRMJON0043VO> emailList = srmjon0043Dao.selectHiddenCompReqEMSList(tmpVo);
        //대분류 담당 MD
		if(serverType.equals("prd")) {
			if (emailList != null) {
				for (int i = 0; i < emailList.size(); i++) {
					SRMJON0043VO emailVo = emailList.get(i);
					emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title", null, Locale.getDefault()));
					emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents", new Object[]{tmpVo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
					emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
					srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
				}
			}
		} else {
			/****TEST****/
			SRMJON0043VO emailVo = new SRMJON0043VO();
			emailVo.setUserNameLoc(config.getString("email.test.userName"));
			emailVo.setEmail(config.getString("email.test.userEmail"));
			emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon0043.title", null, Locale.getDefault()));
			emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon0043.contents", new Object[]{tmpVo.getSellerNameLoc()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon0043.msgCd", null, Locale.getDefault()));
			srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
			/****TEST****/
		}
        //파트너사

        SRMJON0043VO emailPartnerVo = new SRMJON0043VO();
		if(serverType.equals("prd")) {
			//담당자
			emailPartnerVo.setUserNameLoc(tmpVo.getvMainName());
			emailPartnerVo.setEmail(tmpVo.getvEmail());
			//대표자
//			emailPartnerVo.setUserNameLoc(vo.getSellerCeoName());
//			emailPartnerVo.setEmail(vo.getSellerCeoEmail());
		} else {
			/****TEST****/
			emailPartnerVo.setUserNameLoc(config.getString("email.test.userName"));
			emailPartnerVo.setEmail(config.getString("email.test.userEmail"));
			/****TEST****/
		}

		emailPartnerVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmjon004301.title", null, Locale.getDefault()));
		emailPartnerVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmjon004301.contents", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
		emailPartnerVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmjon004301.msgCd", null, Locale.getDefault()));
		srmjon004301Dao.insertHiddenCompReqEMS(emailPartnerVo);

		resultMap.put("message","SUCCESS");
		return resultMap;
	}

	/**
	 * 입점상담신청 취소
	 * @param SRMJON0043VO
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	public HashMap<String, String> updateHiddenCompReqCancel(SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		//신청데이터(SINRQ) 삭제
		srmjon0043Dao.deleteHiddenCompReq(vo);
		srmjon0043Dao.deleteHiddenCompReqSSUPI(vo);

		//신청데이터가 존재하지 않을경우 잠재업체정보(SSUGL) 삭제
		if(srmjon0043Dao.selectHiddenCompReqCheck(vo) == 0) {
			srmjon0043Dao.deleteHiddenComp(vo);
			//세션 삭제(로그아웃)
			request.getSession().removeAttribute(config.getString("lottemart.srm.session.key"));
		}

		resultMap.put("message","SUCCESS");
		return resultMap;
	}
}
