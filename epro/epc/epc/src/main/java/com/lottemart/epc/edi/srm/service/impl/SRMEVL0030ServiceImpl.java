package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.edi.srm.dao.SRMEVL0030Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON004301Dao;
import com.lottemart.epc.edi.srm.model.SRMEVL0030VO;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMEVL0030Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 대상 ServiceImpl
 *
 * @author SHIN SE JIN
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.11  	SHIN SE JIN		 최초 생성
 *   2016.07.26     LEE HYOUNG TAK   수정
 * </pre>
 */
@Service("srmevl0030Service")
public class SRMEVL0030ServiceImpl implements SRMEVL0030Service {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SRMEVL0030Dao srmevl0030Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	/**
	 * 품질평가 list
	 * @param SRMEVL0030VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectQualityEvaluationList(SRMEVL0030VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//세션에서 로그인한 사용자 업체코드 받아오기
		vo.setEvalSellerCode(srmSessionVO.getEvalSellerCode());
		vo.setEvUserId(srmSessionVO.getEvUserId());

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setRecordCountPerPage(10);					//페이지 LOW 수
		paginationInfo.setPageSize(vo.getPageSize());

		result.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = srmevl0030Dao.selectQualityEvaluationListCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMEVL0030VO>	resultList 	= 	srmevl0030Dao.selectQualityEvaluationList(vo);
		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		result.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return result;
	}

	/**
	 * 품질평가 list Excel
	 * @param HttpServletRequest
	 * @param SRMEVL0030VO
	 * @return List<SRMEVL0030VO>
	 * @throws Exception
	 */
	public List<SRMEVL0030VO> selectQualityEvaluationListExcel(SRMEVL0030VO vo, HttpServletRequest request) throws Exception {
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//세션에서 로그인한 사용자 업체코드 받아오기
		vo.setEvalSellerCode(srmSessionVO.getEvalSellerCode());
		vo.setEvUserId(srmSessionVO.getEvUserId());

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return srmevl0030Dao.selectQualityEvaluationListExcel(vo);
	}


	/**
	 * 파트너사 정보 조회
	 * @param SRMEVL0030VO
	 * @return SRMEVL0030VO
	 * @throws Exception
	 */
	public SRMEVL0030VO selectQualityEvaluationCompInfo(SRMEVL0030VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
		vo.setEvUserId(srmSessionVO.getEvUserId());

		return srmevl0030Dao.selectQualityEvaluationCompInfo(vo);
	}

	/**
	 * 현장점검일 등록/상태정보 수정
	 * @param SRMEVL0030VO
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateQualityEvaluationCheckDate(SRMEVL0030VO vo, HttpServletRequest request) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();

		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//로그인 사용자 ID
		vo.setEvUserId(srmSessionVO.getEvUserId());
		vo.setHouseCode(srmSessionVO.getHouseCode());
//		vo.setSellerCode(srmSessionVO.getEvalSellerCode());


		vo.setStatus("G06");
		//현장점검일 등록
		srmevl0030Dao.updateQualityEvaluationSITEVISITCheckDate(vo);

		//SSUGL_PROCESS_MAIN 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationMAINStatus(vo);

		//SSUGL_PROCESS_SITEVISIT 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationSITEVISITStatus(vo);

		vo.setStatus("200");
		//SEVEM 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationSEVEMStatus(vo);

		//SEVEU 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationSEVEUStatus(vo);

		//SEVUS 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationSEVUSStatus(vo);

		//SEVES 상태값 UPDATE
		srmevl0030Dao.updateQualityEvaluationSEVESStatus(vo);

		//EMS(평가 대상업체에게)
		vo.setLocale(SRMCommonUtils.getLocale(request));
		SRMEVL0030VO emailInfo = srmevl0030Dao.selectQualityEvaluationEMSInfo(vo);
		SRMJON0043VO emailVo = new SRMJON0043VO();

		String checkDate = vo.getCheckDate().substring(0,4) + "-" + vo.getCheckDate().substring(4,6) + "-" + vo.getCheckDate().substring(6,8) + " " +vo.getCheckTime() + ":00";
		String checkSellerName = emailInfo.getSellerNameLoc();

		if(serverType.equals("prd")){
			emailVo.setUserNameLoc(emailInfo.getvMainName());
			emailVo.setEmail(emailInfo.getvEmail());
		} else {
			/****TEST****/
			emailVo.setUserNameLoc(config.getString("email.test.userName"));
			emailVo.setEmail(config.getString("email.test.userEmail"));
			/****TEST****/
		}

		emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmevl0030.title", null, Locale.getDefault()));

		if(vo.getEvalFlag().equals("0")){
			emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0030.contents", new Object[] { checkDate, checkSellerName }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
		} else {
			emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmevl0030.contents2", new Object[] { checkSellerName }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
		}

		emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmevl0030.msgCd", null, Locale.getDefault()));
		srmjon004301Dao.insertHiddenCompReqEMS(emailVo);

		return resultMap;
	}
}
