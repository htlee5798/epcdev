/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-epc
 * @since    : 2016.05
 * @Description : SCM 기획전
 * @author : ljh
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.epc.exhibition.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.idgen.IdGnrService;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
//import com.lcnjf.util.StringUtil;
import com.lottemart.epc.exhibition.controller.PSCMEXH0100Controller;
import com.lottemart.epc.exhibition.dao.PSCMEXH0100Dao;
import com.lottemart.epc.exhibition.model.PSCMEXH010050VO;
import com.lottemart.epc.exhibition.service.PSCMEXH0100Service;

@Service("pscmexh0100Service")
public class PSCMEXH0100ServiceImpl implements PSCMEXH0100Service{

	private static final Logger logger = LoggerFactory.getLogger(PSCMEXH0100Controller.class);

	@Autowired
	private PSCMEXH0100Dao pscmexh0100Dao;

	@Autowired
	private ConfigurationService config;

	@Autowired
	MessageSource messageSource;

	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;

	@Autowired
	private ImageFileMngService imageFileMngService;

	@Resource(name = "FileIdGnrService")
	private IdGnrService idgenService;

	/**
	 * 기획전관리 조회
	 * Desc :
	 * @Method Name : selectExhibitionList
	 * @param selectExhibitionList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectExhibitionList(DataMap paramMap) throws Exception {
		return pscmexh0100Dao.selectExhibitionList(paramMap);
	}


	/**
	 * 통합기획전관리 조회
	 * Desc :
	 * @Method Name : selectExhibitionList
	 * @param selectExhibitionList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectExhibitionIntList(DataMap paramMap) throws Exception {
		return pscmexh0100Dao.selectExhibitionIntList(paramMap);
	}

	/**
	 * 기획전카테고리 조회
	 * Desc :
	 * @Method Name : getCategoryList
	 * @param getCategoryList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectCategoryList() throws Exception {
		return pscmexh0100Dao.selectCategoryList();
	}

	/**
	 * 승인처리
	 * Desc :
	 * @Method Name : updateApply
	 * @param updateApply
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int updateApply(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);

			String gbn           = request.getParameter("gbn");
			String retnReason = StringUtil.null2str(request.getParameter("retnReason"));

			//카테고리번호, 점포코드, 기획전번호
			String[] categoryId  = request.getParameterValues("CATEGORY_ID");
			String[] strCd         = request.getParameterValues("STR_CD");
			String[] mkdpSeq    = request.getParameterValues("MKDP_SEQ");

			//String[] status         = request.getParameterValues("STATUS");
			for (int i=0; i<categoryId.length; i++) {
				//logger.debug("status=>"+status[i]);

				DataMap map = new DataMap();

				map.put("gbn", gbn);
				map.put("retnReason", retnReason);      //반려사유

				map.put("categoryId", categoryId[i]);
				map.put("strCd"       , strCd[i]);
				map.put("mkdpSeq"  , mkdpSeq[i]);

				map.put("modId", loginSession.getAdminId());

				resultCnt += pscmexh0100Dao.updateApply(map);

/*				if( resultCnt > 0 ){

				}*/

			}

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}

	/**
	 * 기획전 등록팝업 조회
	 * Desc :
	 * @Method Name : selectExhibitionInfo
	 * @param selectExhibitionInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public DataMap selectExhibitionInfo(DataMap param) throws Exception {
		List<DataMap> list = pscmexh0100Dao.selectExhibitionInfo(param);
		DataMap paramMap = new DataMap();

		if(list.size() > 0){

			paramMap.put("CATEGORY_PARENT_ID", StringUtil.null2str((String)list.get(0).getString("CATEGORY_PARENT_ID")));
			paramMap.put("CATEGORY_CHILD_ID", StringUtil.null2str((String)list.get(0).getString("CATEGORY_CHILD_ID")));
			paramMap.put("CATEGORY_ID", StringUtil.null2str((String)list.get(0).getString("CATEGORY_ID")));
			paramMap.put("MKDP_SEQ", StringUtil.null2str((String)list.get(0).getString("MKDP_SEQ")));
			paramMap.put("APRV_STS_CD", StringUtil.null2str((String)list.get(0).getString("APRV_STS_CD")));
			paramMap.put("MKDP_NM", StringUtil.null2str((String)list.get(0).getString("MKDP_NM")));
			paramMap.put("MKDP_SCND_TITLE", StringUtil.null2str((String)list.get(0).getString("MKDP_SCND_TITLE")));
			paramMap.put("MKDP_START_DATE", StringUtil.null2str((String)list.get(0).getString("MKDP_START_DATE")));
			paramMap.put("MKDP_START_HH", StringUtil.null2str((String)list.get(0).getString("MKDP_START_HH")));
			paramMap.put("MKDP_START_MM", StringUtil.null2str((String)list.get(0).getString("MKDP_START_MM")));
			paramMap.put("MKDP_END_DATE", StringUtil.null2str((String)list.get(0).getString("MKDP_END_DATE")));
			paramMap.put("MKDP_END_HH", StringUtil.null2str((String)list.get(0).getString("MKDP_END_HH")));
			paramMap.put("MKDP_END_MM", StringUtil.null2str((String)list.get(0).getString("MKDP_END_MM")));
			paramMap.put("MKDP_CAT_CD", StringUtil.null2str((String)list.get(0).getString("MKDP_CAT_CD")));
			paramMap.put("DISP_TYPE_CD", StringUtil.null2str((String)list.get(0).getString("DISP_TYPE_CD")));
			paramMap.put("UTAK_MD", StringUtil.null2str((String)list.get(0).getString("UTAK_MD")));
			paramMap.put("UTAK_MD_NM", StringUtil.null2str((String)list.get(0).getString("UTAK_MD_NM")));
			paramMap.put("DISP_YN", StringUtil.null2str((String)list.get(0).getString("DISP_YN")));
			paramMap.put("KYWRD", StringUtil.null2str((String)list.get(0).getString("KYWRD")));
			paramMap.put("PROGRESS_STATUS", StringUtil.null2str((String)list.get(0).getString("PROGRESS_STATUS")));
			paramMap.put("VIEW_MKDP_START_DATE", StringUtil.null2str((String)list.get(0).getString("VIEW_MKDP_START_DATE")));
			paramMap.put("VIEW_MKDP_END_DATE", StringUtil.null2str((String)list.get(0).getString("VIEW_MKDP_END_DATE")));
			paramMap.put("STR_NM", StringUtil.null2str((String)list.get(0).getString("STR_NM")));
			paramMap.put("STR_CD", StringUtil.null2str((String)list.get(0).getString("STR_CD")));
			paramMap.put("VIEW_MKDP_START_YYMMDD", StringUtil.null2str((String)list.get(0).getString("VIEW_MKDP_START_YYMMDD")));
			paramMap.put("VIEW_MKDP_END_YYMMDD", StringUtil.null2str((String)list.get(0).getString("VIEW_MKDP_END_YYMMDD")));
			paramMap.put("REG_METHOD_CD", StringUtil.null2str((String)list.get(0).getString("REG_METHOD_CD")));
			paramMap.put("VENDOR_ID", StringUtil.null2str((String)list.get(0).getString("VENDOR_ID")));
			paramMap.put("CATEGORY_PARENT_ID", StringUtil.null2str((String)list.get(0).getString("CATEGORY_PARENT_ID")));
			paramMap.put("CATEGORY_CHILD_ID", StringUtil.null2str((String)list.get(0).getString("CATEGORY_CHILD_ID")));
			// mz_storm1927 2018/12/14 작업 start - 기획전명(MO) 추가(기획전명, 전단/코너 타이틀, 혜택/할인정보, 마케팅행사정보)
			paramMap.put("MOBILE_MKDP_NM", StringUtil.null2str((String)list.get(0).getString("MOBILE_MKDP_NM")));
			paramMap.put("MOBILE_MKDP_SCND_TITLE", StringUtil.null2str((String)list.get(0).getString("MOBILE_MKDP_SCND_TITLE")));
			paramMap.put("MOBILE_MKDP_SCND_TITLE2", StringUtil.null2str((String)list.get(0).getString("MOBILE_MKDP_SCND_TITLE2")));
			paramMap.put("DC_INFO", StringUtil.null2str((String)list.get(0).getString("DC_INFO")));
			paramMap.put("DC_INFO2", StringUtil.null2str((String)list.get(0).getString("DC_INFO2")));
			paramMap.put("EVT_INFO", StringUtil.null2str((String)list.get(0).getString("EVT_INFO")));
			// mz_storm1927 2018/12/14 작업 end

			for(int i=0; i<list.size(); i++){
				if( "01".equals( StringUtil.null2str((String)list.get(i).getString("CONTENTS_DIVN_CD"))) ){ // SM313 : 컨텐츠메인

					if("01".equals( StringUtil.null2str((String)list.get(i).getString("CH_CD")) )){ // SM310 : PC - 기획전소개(PC)
//						if( "02".equals( StringUtil.null2str((String)list.get(i).getString("REG_METHOD_CD")) )){
//							paramMap.put("INDT_HTML", StringUtil.null2str((String)list.get(i).getString("INDT_HTML")));
//						}
						paramMap.put("CONTENTS_SEQ", StringUtil.null2str((String)list.get(i).getString("CONTENTS_SEQ")));
						paramMap.put("IMG_PATH", StringUtil.null2str((String)list.get(0).getString("IMG_PATH")));
						paramMap.put("CH_CD", StringUtil.null2str((String)list.get(i).getString("CH_CD")));
					}else{ // SM310 : 모바일 - 기획전소개(MOBILE)
//						if( "02".equals( StringUtil.null2str((String)list.get(i).getString("REG_METHOD_CD")) )){
//							paramMap.put("INDT_HTML2", StringUtil.null2str((String)list.get(i).getString("INDT_HTML")));
//						}
						paramMap.put("CONTENTS_SEQ2", StringUtil.null2str((String)list.get(i).getString("CONTENTS_SEQ")));
						paramMap.put("IMG_PATH2", StringUtil.null2str((String)list.get(1).getString("IMG_PATH")));
						paramMap.put("CH_CD2", StringUtil.null2str((String)list.get(i).getString("CH_CD")));
					}

				}else if( "03".equals( StringUtil.null2str((String)list.get(i).getString("CONTENTS_DIVN_CD"))) ){ // SM313 : 배너
//					paramMap.put("CONTENTS_SEQ3", StringUtil.null2str((String)list.get(i).getString("CONTENTS_SEQ")));
					if("01".equals( StringUtil.null2str((String)list.get(i).getString("CH_CD")) )){ // SM310 : PC - 기획전 배너(PC)
						paramMap.put("IMG_PATH3", StringUtil.null2str((String)list.get(i).getString("IMG_PATH")));
					}else{ // SM310 : 모바일 - 기획전 배너(MOBILE)
						// mz_storm1927 2018/12/14 작업 start - 기획전 배너(MOBILE) 추가됨(000000000005) 로직 추가
						if( "000000000004".equals( StringUtil.null2str((String)list.get(i).getString("CONTENTS_SEQ")))) // 기획전 배너(MOBILE 검색결과)
							paramMap.put("IMG_PATH4", StringUtil.null2str((String)list.get(i).getString("IMG_PATH")));
						else  // 기획전 배너(MOBILE)
							paramMap.put("IMG_PATH5", StringUtil.null2str((String)list.get(i).getString("IMG_PATH")));
						// mz_storm1927 2018/12/14 작업 end
					}
				}

			}


//			// PC
//			if( "00002".equals(StringUtil.null2str((String)list.get(0).getString("DISP_TYPE_CD"))) ){
//				paramMap.put("CONTENTS_SEQ", StringUtil.null2str((String)list.get(0).getString("CONTENTS_SEQ")));
//				paramMap.put("IMG_PATH", StringUtil.null2str((String)list.get(0).getString("IMG_PATH")));
//				paramMap.put("INDT_HTML", StringUtil.null2str((String)list.get(0).getString("INDT_HTML")));
//				paramMap.put("CH_CD", StringUtil.null2str((String)list.get(0).getString("CH_CD")));
//
//			// Mobile
//			}else if( "00003".equals(StringUtil.null2str((String)list.get(0).getString("DISP_TYPE_CD"))) ){
//				paramMap.put("CONTENTS_SEQ2", StringUtil.null2str((String)list.get(0).getString("CONTENTS_SEQ")));
//				paramMap.put("IMG_PATH2", StringUtil.null2str((String)list.get(0).getString("IMG_PATH")));
//				paramMap.put("INDT_HTML2", StringUtil.null2str((String)list.get(0).getString("INDT_HTML")));
//				paramMap.put("CH_CD2", StringUtil.null2str((String)list.get(0).getString("CH_CD")));
//
//			// 공통
//			}else{
//				paramMap.put("CONTENTS_SEQ", StringUtil.null2str((String)list.get(0).getString("CONTENTS_SEQ")));
//				paramMap.put("IMG_PATH", StringUtil.null2str((String)list.get(0).getString("IMG_PATH")));
//				paramMap.put("INDT_HTML", StringUtil.null2str((String)list.get(0).getString("INDT_HTML")));
//				paramMap.put("CH_CD", StringUtil.null2str((String)list.get(0).getString("CH_CD")));
//
//				paramMap.put("CONTENTS_SEQ2", StringUtil.null2str((String)list.get(1).getString("CONTENTS_SEQ")));
//				paramMap.put("IMG_PATH2", StringUtil.null2str((String)list.get(1).getString("IMG_PATH")));
//				paramMap.put("CONTENTS_DIVN_CD2", StringUtil.null2str((String)list.get(1).getString("CONTENTS_DIVN_CD")));
//				paramMap.put("INDT_HTML2", StringUtil.null2str((String)list.get(1).getString("INDT_HTML")));
//				paramMap.put("CH_CD2", StringUtil.null2str((String)list.get(1).getString("CH_CD")));
//			}


		}

		return paramMap;
	}

	/**
	 * 기획전 등록/수정
	 * Desc : 기획전 등록/수정
	 * @Method Name : insertExhibition
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int insertExhibition(DataMap param, HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		String pageDiv = param.getString("pageDiv");
		String mkdpSeq = "";

		String copyYn = StringUtil.null2str((String) param.get("copyYn"));   //기획전 복사 처리여부(Y:복사)
		String fileUrl = "";
		String gudnImg = "";
		String ContentSeq1 = "";
		String ContentSeq2 = "";
		String ContentSeq3 =request.getParameter("ContentSeq3");
		String ContentSeq4 =request.getParameter("ContentSeq4");
		String ContentSeq5 =request.getParameter("ContentSeq5");

		int storeResultCnt = 0;

			if(pageDiv.equals("insert")) { // 기획전 등록

				//예전 기획전 번호 셋팅(기획전 복사용)
				String oldMkdpSeq = StringUtil.null2str((String) param.get("mkdpSeq"));
				param.put("oldMkdpSeq", oldMkdpSeq);

				mkdpSeq = pscmexh0100Dao.getMkdpSeq(param); // TCA_MKDP_MST 테이블 조회 - 기획전 소카테고리에 등록된 기획전번호 조회
				param.put("mkdpSeq", mkdpSeq);

				resultCnt = pscmexh0100Dao.insertExhibitionInfo(param); // TCA_MKDP_MST 테이블 등록
//				resultCnt = 1;

				//기획전 복사일 경우
				/*if("Y".equals(copyYn)){
					pscmexh0100Dao.insertCopyExhibition(param);
				}*/

				//점포할당
				//String strCdList = "981"; // 981고정
//				param.put("strCd", "981");
//				pscmexh0100Dao.insertStrAssignInfo(param); // TCA_MKDP_MST_STR 테이블 등록

				//점포할당
				String strCdList = StringUtil.null2str((String) param.get("strCd"));
				String[] arrStrCdList = strCdList.split(",");
				for (int i = 0; i < arrStrCdList.length; i++) {
					param.put("strCd", arrStrCdList[i]);
					pscmexh0100Dao.insertStrAssignInfo(param);
				}

				param.put("startDate", param.get("mkdpStartDate"));
				param.put("endDate", param.get("mkdpEndDate"));
				param.put("regMethodCd", param.get("regMethodCd"));

				// 1. 소개PC,
				// 2. 소개모바일
				// 3. PC 배너
				// 4. 모바일 배너
				// 5. 에디터PC
				// 6. 에디터 모바일
//				for(int i=1; i<7; i++){	//	에디터영역 삭제
				for(int i=1; i<6; i++){ // 기획전 배너(PC/MOBILE) 로 되어 있던 부분이 PC, MOBILE 로 나뉘어져서 하나 추가됨(000000000005)
//					if(i != 4){
						String contentSeqSum = "00000000000"+i;
						logger.debug("contentSeqSum   =    "+contentSeqSum);

						if( i == 1){
							ContentSeq1 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "01");
							param.put("contentsDivnCd", "01");
						}else if(i == 2){
							ContentSeq2 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "02");
							param.put("contentsDivnCd", "01");
						}else if(i == 3){
							ContentSeq3 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "01");
							param.put("contentsDivnCd", "03");
						}else if(i == 4){
							ContentSeq4 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "02");
							param.put("contentsDivnCd", "03");
						}else if(i == 5){
							ContentSeq5 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "03"); // 모바일인데, 03으로 등록함
							param.put("contentsDivnCd", "03");
						}
//						else if(i == 5){
//							param.put("contentSeq5", contentSeqSum);
//							param.put("contentSeq", contentSeqSum);
//							param.put("chCd", "01");
//							param.put("contentsDivnCd", "01");
//							param.put("regMethodCd", "02");
//							param.put("content", param.getString("content1"));  // 에디터
//						}else if(i == 6){
//							param.put("contentSeq6", contentSeqSum);
//							param.put("contentSeq", contentSeqSum);
//							param.put("chCd", "02");
//							param.put("contentsDivnCd", "01");
//							param.put("regMethodCd", "02");
//							param.put("content", param.getString("content2"));  // 에디터
//						}
						String imageHtmlCnt1 = pscmexh0100Dao.insertExhiImageHtml(param); // TCA_MKDP_CONTENTS 테이블 등록
						pscmexh0100Dao.insertConStrAssignInfo(param); // TCA_MKDP_CON_STR 테이블 등록
//					} // i가 4가 아닐때 if

				}



				// 00001
				// 전시유형 ==> 공통
//				if( "00001".equals(param.getString("dispTypeCd")) ){
//					// pc
//					ContentSeq1 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq1", ContentSeq1);
//					param.put("contentSeq", ContentSeq1);
//					param.put("chCd", "01");
//					param.put("content", param.getString("content1"));
//					param.put("contentsDivnCd", "01");
//					param.put("regMethodCd", "02");
//					String imageHtmlCnt1 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//					// mobile
//					ContentSeq2 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq2", ContentSeq2);
//					param.put("contentSeq", ContentSeq2);
//					param.put("chCd", "02");
//					param.put("content", param.getString("content2"));
//					param.put("contentsDivnCd", "01");
//					param.put("regMethodCd", "02");
//					String imageHtmlCnt2 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//					// 배너
//					ContentSeq3 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq", ContentSeq3);
//					param.put("chCd", "01");
//					param.put("contentsDivnCd", "03");
//					String imageHtmlCnt3 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//				//	전시유형 ==> 모바일
//				}else if ( "00003".equals(param.getString("dispTypeCd")) ){
//					ContentSeq2 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq2", ContentSeq2);
//					param.put("contentSeq", ContentSeq2);
//					param.put("chCd", "02");
//					param.put("content", param.getString("content2"));
//					param.put("contentsDivnCd", "01");
//					param.put("regMethodCd", "02");
//					String imageHtmlCnt2 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//					// 배너
//					ContentSeq3 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq", ContentSeq3);
//					param.put("chCd", "01");
//					param.put("contentsDivnCd", "03");
//					String imageHtmlCnt3 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//				// pc이거나 그외
//				}else{
//					ContentSeq1 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq1", ContentSeq1);
//					param.put("contentSeq", ContentSeq1);
//					param.put("chCd", "01");
//					param.put("content", param.getString("content1"));
//					param.put("contentsDivnCd", "01");
//					param.put("regMethodCd", "02");
//					String imageHtmlCnt1 = pscmexh0100Dao.insertExhiImageHtml(param);
//
//					// 배너
//					ContentSeq3 = pscmexh0100Dao.getMkdpConSeqKey(param);
//					param.put("contentSeq", ContentSeq3);
//					param.put("chCd", "01");
//					param.put("contentsDivnCd", "03");
//					String imageHtmlCnt3 = pscmexh0100Dao.insertExhiImageHtml(param);
//				}


			}else if(pageDiv.equals("insertCopy")) { // 기획전 복사


				//예전 기획전 번호 셋팅(기획전 복사용)
				String oldMkdpSeq = StringUtil.null2str((String) param.get("mkdpSeq"));
				param.put("oldMkdpSeq", oldMkdpSeq);
			//	param.put("categoryChildId", param.get("categoryId"));

				mkdpSeq = pscmexh0100Dao.getMkdpSeq(param); // TCA_MKDP_MST 테이블 조회 - 기획전 소카테고리에 등록된 기획전번호 조회
				param.put("mkdpSeq", mkdpSeq);

				resultCnt +=  pscmexh0100Dao.insertCopyExhibitionInfo(param); // TCA_MKDP_MST 테이블 등록
//				resultCnt = 1;

				//기획전 복사일 경우
				/*if("Y".equals(copyYn)){
					pscmexh0100Dao.insertCopyExhibition(param);
				}*/

				//점포할당
				//String strCdList = "981"; // 981고정
//				param.put("strCd", "981");
				//점포할당
				String strCdList = StringUtil.null2str((String) param.get("strCd"));
				String[] arrStrCdList = strCdList.split(",");
				for (int i = 0; i < arrStrCdList.length; i++) {
					param.put("strCd", arrStrCdList[i]);
					pscmexh0100Dao.insertStrAssignInfo(param);
				}

				resultCnt += pscmexh0100Dao.insertCopyStrAssignInfo(param); // TCA_MKDP_MST_STR 테이블 등록

				resultCnt += pscmexh0100Dao.insertCopyConStrAssignInfo(param); // TCA_MKDP_CON_STR 테이블 등록
				param.put("startDate", param.get("mkdpStartDate"));
				param.put("endDate", param.get("mkdpEndDate"));
				param.put("regMethodCd", param.get("regMethodCd"));

				/*for(int i=1; i<5; ++i){
//					if(i != 4){
						String contentSeqSum = "00000000000"+i;
						logger.debug("contenCopytSeqSum   =    "+contentSeqSum);

						if( i == 1){
							ContentSeq1 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "01");
							param.put("contentsDivnCd", "01");
						}else if(i == 2){
							ContentSeq2 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "02");
							param.put("contentsDivnCd", "01");
						}else if(i == 3){
							ContentSeq3 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "01");
							param.put("contentsDivnCd", "03");
						}else if(i == 4){
							ContentSeq4 = contentSeqSum;
							param.put("contentSeq", contentSeqSum);
							param.put("chCd", "02");
							param.put("contentsDivnCd", "03");
						}
						String imageHtmlCnt1 = pscmexh0100Dao.insertCopyExhiImageHtml(param);
						pscmexh0100Dao.insertCopyConStrAssignInfo(param);
//					} // i가 4가 아닐때 if
				}*/
				/* 주석 처리
				위의 for 문이 주석 처리되어 있으므로 contentSeq 값이 안 들어감 : 아래의 insertCopyExhiImageHtml 쿼리는 contentSeq 값이 있어야 함
				TCA_MKDP_CONTENTS 테이블에 insert 하는 부분이 아래 insertCopyContentsInfo 있으므로 주석처리함
				resultCnt += pscmexh0100Dao.insertCopyExhiImageHtml(param); // TCA_MKDP_CON_STR 테이블 등록  - 여기서는 contents_seq 가 없어서 실제적으로 DB 등록 안됨.
				*/
				//resultCnt += pscmexh0100Dao.insertCopyConStrAssignInfo(param);

				//int size = pscmexh0100Dao.selectDivnInfoLength(param);

				//for (int i = 1; i <size; i++) {

				//  구분자 번호

					//param.put("divnSeq"        , "00"+i);
				resultCnt += pscmexh0100Dao.insertCopyDivnInfo(param); // TCA_MKDP_DTL 테이블 등록

				resultCnt += pscmexh0100Dao.insertCopyDtlStrAssignInfo(param); // TCA_MKDP_DTL_STR 테이블 등록

				//}

				// TCA_MKDP_CONTENTS 테이블에 넣는 쿼리에 START_DATE, END_DATE 하는 부분이 빠져 있어 쿼리에 추가함
				resultCnt +=  pscmexh0100Dao.insertCopyContentsInfo(param); // TCA_MKDP_CONTENTS 테이블 등록
				resultCnt +=  pscmexh0100Dao.insertCopyExhibitionProdInfo(param); // TCA_MKDP_DTL_ASSIGN 테이블 등록
				resultCnt +=  pscmexh0100Dao.updateCopyAprv(param); // TCA_MKDP_MST 테이블 수정

				logger.debug("resultCntresultCntresultCntresultCnt="+resultCnt);

			}else if(pageDiv.equals("update")) { // 수정
				resultCnt = pscmexh0100Dao.updateExhibitionInfo(param); // TCA_MKDP_MST 테이블 수정

				//* 점포수정 시작===
				String storeModifyYn = StringUtil.null2str((String) param.get("storeModifyYn"));
				if("Y".equals(storeModifyYn)){

					String strCdList = StringUtil.null2str((String) param.get("strCd"));
					String[] arrStrCdList = strCdList.split(",");
					param.put("arrStrCd", arrStrCdList);

					//전점이 아닐경우만 해당 점포의 컨텐츠, 구분자 삭제
					if(!"999".equals(strCdList)){

						//2016-08-11 작업시작========================================================================
						// 0.구분자 - 점포 삭제시 점포할당 정보가 1건도 존재하지 않을 경우 경고 메세지 뛰우고 Exception처리
						DataMap resultIsDivnStrMap = new DataMap();
						resultIsDivnStrMap = pscmexh0100Dao.selectCheckRemoveIsStrMkdp(param);
						String checkMessage = "";
						if(!"".equals(resultIsDivnStrMap.get("DIVN_NM")) && resultIsDivnStrMap.get("DIVN_NM") != null ){
							checkMessage = "기획전 하위 상품그룹정보의 " +resultIsDivnStrMap.get("DIVN_NM") + " 점포를 확인하세요.";
						}

						// 1.컨텐츠 - 점포 삭제시 점포할당 정보가 1건도 존재하지 않을 경우 경고 메세지 뛰우고 Exception처리
						DataMap resultIsConStrMap = new DataMap();
						resultIsConStrMap = pscmexh0100Dao.selectCheckRemoveIsConStrMkdp(param);
						if(!"".equals(resultIsConStrMap.get("CONTENTS_SEQ")) && resultIsConStrMap.get("CONTENTS_SEQ") != null ){
							if(!"".equals(checkMessage))checkMessage += "\n";
							checkMessage += "기획전 소개 이미지정보의 "+resultIsConStrMap.get("CONTENTS_SEQ") + " 컨텐츠번호의 점포를 확인하세요.";
						}
						if(!"".equals(checkMessage)){
							throw new IllegalArgumentException(checkMessage);
						}
						//2016-08-11 작업종료========================================================================

						// 2.컨텐츠 - 점포 삭제
						storeResultCnt = pscmexh0100Dao.deleteMkdpModifyConStr(param);

						// 3.구분자 - 점포 삭제
						storeResultCnt = pscmexh0100Dao.deleteMkdpModifyDtlStr(param);
					}

					// 3.기획전 - 전체점포 삭제후   해당 점포등록
					storeResultCnt = pscmexh0100Dao.deleteMkdpMstStr(param);
					for (int i = 0; i < arrStrCdList.length; i++) {
						param.put("strCd", arrStrCdList[i]);
						pscmexh0100Dao.insertStrAssignInfo(param);
					}

				}
				//* 점포수정 종료===

				// 전시유형 ==> 공통
//				if( "00001".equals(param.getString("dispTypeCd")) ){

					// pc
//					int imageHtmlCnt1 = pscmexh0100Dao.updateContentsHtml1(param);

					// mobile
//					int imageHtmlCnt2 = pscmexh0100Dao.updateContentsHtml2(param);

				//	전시유형 ==> 모바일
//				}
//				else if ( "00003".equals(param.getString("dispTypeCd")) ){
					// pc
//					int imageHtmlCnt2 = pscmexh0100Dao.updateContentsHtml2(param);

				// pc이거나 그외
//				}else{
					// mobile
//					int imageHtmlCnt1 = pscmexh0100Dao.updateContentsHtml1(param);
//				}


			}

			if(resultCnt>0){

					//  이미지 물리 업로드
					List<FileVO> _result = new ArrayList<FileVO>();
					MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
					final Map<String, MultipartFile> files = mptRequest.getFileMap();
					MultipartFile tmpFile = files.get("tmpFile1");
					MultipartFile tmpFile2 = files.get("tmpFile2");
					MultipartFile tmpFile3 = files.get("tmpFile3");
					MultipartFile tmpFile4 = files.get("tmpFile4");
					MultipartFile tmpFile5 = files.get("tmpFile5");


					if( "1".equals(param.getString("tmpFile1Chk")) ){

						if(tmpFile!=null){
//							logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★   tmpFile Info  " +tmpFile.getOriginalFilename().equals("")+"    =    "+tmpFile.getSize()      );
							if(!tmpFile.getOriginalFilename().equals("") || tmpFile.getSize() > 0) {

								String strSubPath = fileMngUtil.getSubpath("Y");
								//_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");
//								_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path", strSubPath);
								_result = fn_parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path", strSubPath);

								logger.debug("================== img1");
								if(tmpFile.getSize() > 0 && _result==null) {
									throw new IllegalArgumentException("같은 이미지명이 있거나 파일용량을 초과했습니다");

								}else {
									//exhibition.image.url + "/년도"
									//fileUrl = config.getString("exhibition.image.url")+strYear+"/";

	//								fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									gudnImg = fileUrl  +_result.get(0).getStreFileNm();

									//업로드 성공후 Dynamic Image 처리  시작========
//									String strType = "";
									// 기획전 소개 pc
//									strType = "03";
//									imageFileMngService.purgeImageQCServer(strType, _result.get(0).getStreFileNm());
//									imageFileMngService.purgeCDNServer(strType, _result.get(0).getStreFileNm());
									//업로드 성공후 Dynamic Image 처리  종료========

									//관련 파일 경로만 DB업데이트
									param.put("imgPath", gudnImg);
									param.put("contentSeq", param.getString("ContentSeq1"));
									resultCnt = pscmexh0100Dao.updateContentsImage(param); // TCA_MKDP_CONTENTS 테이블 수정
								}
							}
						}
					}

					if( "1".equals(param.getString("tmpFile2Chk")) ){

						if(tmpFile2!=null){
							if(!tmpFile2.getOriginalFilename().equals("") || tmpFile2.getSize() > 0) {

								String strSubPath = fileMngUtil.getSubpath("Y");
								//_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");
//								_result = fileMngUtil.parseFileInfSingle(tmpFile2, "", "image", "exhibition.image.path", strSubPath);
								_result = fn_parseFileInfSingle(tmpFile2, "", "image", "exhibition.image.path", strSubPath);

								logger.debug("================== img2");
								if(tmpFile2.getSize() > 0 && _result==null) {
									throw new IllegalArgumentException("같은 이미지명이 있거나 파일용량을 초과했습니다");
								} else {
									fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									gudnImg = fileUrl  +_result.get(0).getStreFileNm();

									//업로드 성공후 Dynamic Image 처리  시작========
//									String strType = "";
									// 기획전 소개 mobile
//									strType = "04";
//									imageFileMngService.purgeImageQCServer(strType, _result.get(0).getStreFileNm());
//									imageFileMngService.purgeCDNServer(strType, _result.get(0).getStreFileNm());
									//업로드 성공후 Dynamic Image 처리  종료========

									//관련 파일 경로만 DB업데이트
									param.put("imgPath", gudnImg);
									param.put("contentSeq", param.getString("ContentSeq2"));
									resultCnt = pscmexh0100Dao.updateContentsImage(param); // TCA_MKDP_CONTENTS 테이블 수정
								}
							}
						}
					}

					if( "1".equals(param.getString("tmpFile3Chk")) ){

						if(tmpFile3!=null){
							if(!tmpFile3.getOriginalFilename().equals("") || tmpFile3.getSize() > 0) {

								String strSubPath = fileMngUtil.getSubpath("Y");
								//_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");
//								_result = fileMngUtil.parseFileInfSingle(tmpFile2, "", "image", "exhibition.image.path", strSubPath);
								_result = fn_parseFileInfSingle(tmpFile3, "", "image", "exhibition.image.path", strSubPath);

								logger.debug("================== img3");

								if(tmpFile3.getSize() > 0 && _result==null) {
									throw new IllegalArgumentException("같은 이미지명이 있거나 파일용량을 초과했습니다");
								} else {
									fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									gudnImg = fileUrl  +_result.get(0).getStreFileNm();

									//업로드 성공후 Dynamic Image 처리  시작=======
									String strType = "03";
									imageFileMngService.purgeImageQCServer(strType, _result.get(0).getStreFileNm());
									imageFileMngService.purgeCDNServer(strType, _result.get(0).getStreFileNm());
									//업로드 성공후 Dynamic Image 처리  종료========

									//관련 파일 경로만 DB업데이트
									param.put("imgPath", gudnImg);
									param.put("contentSeq",  "000000000003");
									resultCnt = pscmexh0100Dao.updateContentsImage(param); // TCA_MKDP_CONTENTS 테이블 수정
								}
							}
						}
					}

					if( "1".equals(param.getString("tmpFile4Chk")) ){

						if(tmpFile4!=null){
							if(!tmpFile4.getOriginalFilename().equals("") || tmpFile4.getSize() > 0) {

								String strSubPath = fileMngUtil.getSubpath("Y");
								//_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");
//								_result = fileMngUtil.parseFileInfSingle(tmpFile2, "", "image", "exhibition.image.path", strSubPath);
								_result = fn_parseFileInfSingle(tmpFile4, "", "image", "exhibition.image.path", strSubPath);

								logger.debug("================== img4");

								if(tmpFile4.getSize() > 0 && _result==null) {
									throw new IllegalArgumentException("같은 이미지명이 있거나 파일용량을 초과했습니다");
								} else {
									fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									gudnImg = fileUrl  +_result.get(0).getStreFileNm();

									//업로드 성공후 Dynamic Image 처리  시작=======
//									String strType = "";
//									imageFileMngService.purgeImageQCServer(strType, _result.get(0).getStreFileNm());
//									imageFileMngService.purgeCDNServer(strType, _result.get(0).getStreFileNm());
									//업로드 성공후 Dynamic Image 처리  종료========

									//관련 파일 경로만 DB업데이트
									param.put("imgPath", gudnImg);
									param.put("contentSeq",  "000000000004");
									resultCnt = pscmexh0100Dao.updateContentsImage(param); // TCA_MKDP_CONTENTS 테이블 수정
								}
							}
						}
					}

					if( "1".equals(param.getString("tmpFile5Chk")) ){

						if(tmpFile5!=null){
							if(!tmpFile5.getOriginalFilename().equals("") || tmpFile5.getSize() > 0) {

								String strSubPath = fileMngUtil.getSubpath("Y");
								//_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");
//								_result = fileMngUtil.parseFileInfSingle(tmpFile2, "", "image", "exhibition.image.path", strSubPath);
								_result = fn_parseFileInfSingle(tmpFile5, "", "image", "exhibition.image.path", strSubPath);

								logger.debug("================== img5");

								if(tmpFile5.getSize() > 0 && _result==null) {
									throw new IllegalArgumentException("같은 이미지명이 있거나 파일용량을 초과했습니다");
								} else {
									fileUrl = config.getString("exhibition.image.url")+strSubPath+"/";
									gudnImg = fileUrl  +_result.get(0).getStreFileNm();

									//업로드 성공후 Dynamic Image 처리  시작=======
//									String strType = "";
//									imageFileMngService.purgeImageQCServer(strType, _result.get(0).getStreFileNm());
//									imageFileMngService.purgeCDNServer(strType, _result.get(0).getStreFileNm());
									//업로드 성공후 Dynamic Image 처리  종료========

									//관련 파일 경로만 DB업데이트
									param.put("imgPath", gudnImg);
									param.put("contentSeq",  "000000000005");
									resultCnt = pscmexh0100Dao.updateContentsImage(param); // TCA_MKDP_CONTENTS 테이블 수정
								}
							}
						}
					}

			}


		return resultCnt;
	}

	/**
	 *
	 * 기획전 소카테고리 콤보 조회
	 * Desc : 기획전 소카테고리 콤보 조회
	 * @Method Name : selectCategoryChildIdList
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectCategoryChildIdList(DataMap param) {
		return pscmexh0100Dao.selectCategoryChildIdList(param);
	}

	/**
	 * 기획전 구분자정보 조회
	 * Desc : 기획전 구분자정보 조회
	 * @Method Name : selectDivnInfoList
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectDivnInfoList(DataMap param) throws Exception {
		return pscmexh0100Dao.selectDivnInfoList(param);
	}

	/**
	 * 기획전 구분자정보 등록
	 * Desc : 기획전 구분자정보 등록
	 * @Method Name : insertDivnInfo
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int insertDivnInfo(DataMap param) throws Exception{

		String idxList = StringUtil.null2str(param.getString("idxList"));

		String[] arrIdxList = idxList.split("T");
		int idxCnt = arrIdxList.length;

		int resultCnt = 0;
		int isCnt     = 0;


		int applyCnt         = 0;
		String createDivnseq = "";
		String divnSeq = null;

		//* 삭제
		int deleteCnt = 0;
		String strDeleteItem = StringUtil.null2str(param.getString("deleteItemDivnSeq"));
		logger.debug("strDeleteItem   =   "+strDeleteItem);
		String[] arrDivnSeq = strDeleteItem.split("T");
		deleteCnt = arrDivnSeq.length;

		Map<String, Object> pMap = new HashMap<String, Object>();
		if(!"".equals(strDeleteItem)){
			pMap.put("categoryId"      , param.getString("categoryId"));
			pMap.put("mkdpSeq"         , param.getString("mkdpSeq"));
			pMap.put("arrDivnSeq"      , arrDivnSeq);
			logger.debug("");
			applyCnt = pscmexh0100Dao.deleteDivnInfo(pMap);

			// TCA_MKDP_DTL_STR 테이블 지우는 로직이 빠져 있는 이슈 수정 : TCA_MKDP_DTL_STR 지우는 로직 추가
			param.put(divnSeq, arrDivnSeq);
			applyCnt = pscmexh0100Dao.deleteDivnStrInfo(pMap);

			// 2.관련 상품 삭제
			applyCnt = pscmexh0100Dao.deleteDivnAssignProd(pMap);
			//* 구분자정보 삭제시  2016-05-17 추가 시작============================================

		}


			//* 등록/수정
			String idx = "";
			String storeModifyYn = "";
			int storeResultCnt = 0;

			DataMap dmapParam = null;

			for (int i = 0; i < idxCnt; i++) {
				idx = arrIdxList[i];

				pMap = new HashMap<String, Object>();

				//1.생성여부 확인
				pMap.put("categoryId"     , param.getString("categoryId"));
				pMap.put("mkdpSeq"        , param.getString("mkdpSeq"));

				//  구분자 번호
				divnSeq = StringUtil.null2str(param.getString("divnSeq"     +idx));
				pMap.put("divnSeq"        , divnSeq);

				pMap.put("divnNm"         , param.getString("divnNm"        +idx));      //구분자명
				pMap.put("dispSeq"        , param.getString("dispSeq"       +idx));      //전시순위

				pMap.put("divnType"       , "01");      //노출유형
				pMap.put("dispYn"         , param.getString("dispYn"        +idx));      //전시여부

				pMap.put("dispToCd"       , "01");
				pMap.put("dispProdCnt"    , "999");
				pMap.put("prodTemplateCd" , param.getString("prodTemplateCd"+idx));

				pMap.put("mkdpStartDate"  , param.getString("mkdpStartDate").replaceAll("-", ""));
				pMap.put("mkdpStartHh"    , param.getString("mkdpStartHh"));
				pMap.put("mkdpStartMm"    , param.getString("mkdpStartMm"));

				pMap.put("mkdpEndDate"    , param.getString("mkdpEndDate").replaceAll("-", ""));
				pMap.put("mkdpEndHh"      , param.getString("mkdpEndHh"));
				pMap.put("mkdpEndMm"      , param.getString("mkdpEndMm"));

				//regId 안 들어가는 이슈 수정 - regId 추가함
				pMap.put("regId"          , param.getString("regId"));
				pMap.put("modId"          , param.getString("modId"));

				String strCdList      = StringUtil.null2str((String) param.get("subStrCd"+idx));
				String[] arrStrCdList = strCdList.split(",");
//				String startDate      = (String)pMap.get("mkdpStartDate") + (String)pMap.get("mkdpStartHh") + (String)pMap.get("mkdpStartMm");
//				String endDate        = (String)pMap.get("mkdpEndDate")   + (String)pMap.get("mkdpEndHh")   + (String)pMap.get("mkdpEndMm");

				//2.미존재시 순번생성후 insert, 존재시 update
				if("".equals(divnSeq)){

					createDivnseq = pscmexh0100Dao.getDivnSeq(pMap);
					pMap.put("divnSeq", createDivnseq);

					// 1.구분자 기간범위 체크
					dmapParam = new DataMap();
					dmapParam.put("categoryId"  , pMap.get("categoryId"));
					dmapParam.put("mkdpSeq"     , pMap.get("mkdpSeq"));
	//
	//				dmapParam.put("startDate"   , startDate);
	//				dmapParam.put("endDate"     , endDate);
	//				String isDateYn = pscmexh0100Dao.selectMkdpDateCheck(dmapParam);
	//				if("N".equals(isDateYn)){
	//					throw new Exception(idx+"번째 설정된 기획전 기간범위에서 입력하세요");
	//				}

					// 2.구분자 점포범위 체크
					dmapParam.put("strCd"    , "999");
					String existStoreYn = pscmexh0100Dao.selectExistMkdpStoreCheck(dmapParam);   // 999존재시 점포범위 체크X
					if("N".equals(existStoreYn)){
						// 구분자 점포범위 체크
						dmapParam.put("storeCount"  , arrStrCdList.length);
						dmapParam.put("arrStrCd"    , arrStrCdList);
						String isStoreYn = pscmexh0100Dao.selectMkdpStoreCheck(dmapParam);
						if("N".equals(isStoreYn)){
							throw new IllegalArgumentException(idx+"번째 할당된 기획전 점포범위에서 입력하세요");
						}
					}

					//등록
					applyCnt = pscmexh0100Dao.insertDivnInfo(pMap);

					//* 점포할당 시작===
					param.put("divnSeq", createDivnseq);
					for (int j = 0; j < arrStrCdList.length; j++) {
						param.put("strCd", arrStrCdList[j]);
						pscmexh0100Dao.insertDtlStrAssignInfo(param);

					}
					//* 점포할당 종료===

				}else{

					//수정
					applyCnt = pscmexh0100Dao.updateDivnInfo(pMap);

	//				// 1.구분자 기간범위 체크
					dmapParam = new DataMap();
					dmapParam.put("categoryId"  , pMap.get("categoryId"));
					dmapParam.put("mkdpSeq"     , pMap.get("mkdpSeq"));
	//
	//				dmapParam.put("startDate"   , startDate);
	//				dmapParam.put("endDate"     , endDate);
	//				String isDateYn = pscmexh0100Dao.selectMkdpDateCheck(dmapParam);
	//				if("N".equals(isDateYn)){
	//					throw new Exception(idx+"번째 기획전 설정된 기간범위에서 입력하세요");
//					}

					//* 점포수정 시작===
					storeModifyYn = (String) param.getString("storeModifyYn"+idx);
					if("Y".equals(storeModifyYn)){

						// 2.구분자 점포범위 체크
//						dmapParam.put("strCd"    , "999");

						dmapParam.put("storeCount"  , arrStrCdList.length);	//selectExistMkdpStoreCheck쿼리 1line 변경필요시 넣을예정
						dmapParam.put("arrStrCd"    , arrStrCdList);
						String existStoreYn = pscmexh0100Dao.selectExistMkdpStoreCheck(dmapParam);    // 999존재시 점포범위 체크X
						if("N".equals(existStoreYn)){
							// 구분자 점포범위 체크
							dmapParam.put("storeCount"  , arrStrCdList.length);
							dmapParam.put("arrStrCd"    , arrStrCdList);
							String isStoreYn = pscmexh0100Dao.selectMkdpStoreCheck(dmapParam);
							if("N".equals(isStoreYn)){
								throw new IllegalArgumentException(idx+"번째 기획전 할당된 점포범위에서 입력하세요");
							}
						}

						// 3.구분자 - 전체 점포 삭제후 점포등록
						param.put("divnSeq", divnSeq);
						storeResultCnt = pscmexh0100Dao.deleteDtlStrAssignInfo(param);
						for (int j = 0; j < arrStrCdList.length; j++) {
							param.put("strCd", arrStrCdList[j]);
							pscmexh0100Dao.insertDtlStrAssignInfo(param);
						}

					}
					//* 점포수정 종료===

				}

				resultCnt += applyCnt;
			}

		return resultCnt;
	}

	/**
	 * 이미지/HTML 정보 - 기획전 내용(소개) 조회
	 * Desc :
	 * @Method Name : selectContentsList
	 * @param selectContentsList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectContentsList(DataMap param) throws Exception {
		return pscmexh0100Dao.selectContentsList(param);
	}


	/**
	 * 이미지/HTML 정보 - 이미지 / HTML 저장
	 * Desc :
	 * @Method Name : insertContentsImage
	 * @param insertContentsImage
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int insertContentsImage(DataMap param, HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		String pageDiv = param.getString("pageDiv");
		String createContentsSeq = "";
		int storeResultCnt = 0;

		String fileUrl = "";
		String gudnImg = "";

		//점포범위 체크용
		DataMap dmapParam = new DataMap();
		dmapParam.put("categoryId"    , param.get("categoryId"));
		dmapParam.put("mkdpSeq"       , param.get("mkdpSeq"));
		dmapParam.put("contentsSeq"   , param.get("contentsSeq"));

		dmapParam.put("contentsDivnCd", param.get("contentsDivnCd"));
		dmapParam.put("chCd"          , param.get("chCd"));
		dmapParam.put("divnSeq"       , param.get("divnSeq"));

		DataMap contentsCheckInfo = new DataMap();

		//param2 = new BOSUtil().unEscapeHtml(param);
		String contentsDivnCd = param.getString("contentsDivnCd");
		if(pageDiv.equals("insert")) {

			if("01".equals(contentsDivnCd)){                       //기획전 내용(소개) HTML, 이미지
				//1.컨텐츠번호 발번 및 컨텐츠 등록
				createContentsSeq = pscmexh0100Dao.getMkdpConSeqKey(param);
				param.put("contentsSeq", createContentsSeq);
				resultCnt = pscmexh0100Dao.insertContentsImage(param);

				//2.점포할당
				String subStrCdList = StringUtil.null2str((String) param.get("subStrCd"));
				if(!"".equals(subStrCdList)){
					String[] arrStrCdList = subStrCdList.split(",");

					// 기획전 점포범위 체크 시작=====
					dmapParam.put("strCd"    , "999");
					String existStoreYn = pscmexh0100Dao.selectExistMkdpStoreCheck(dmapParam);   // 999존재시 점포범위 체크X
					if("N".equals(existStoreYn)){
						// 기획전 점포범위 체크
						dmapParam.put("storeCount"  , arrStrCdList.length);
						dmapParam.put("arrStrCd"    , arrStrCdList);
						String isStoreYn = pscmexh0100Dao.selectMkdpStoreCheck(dmapParam);
						if("N".equals(isStoreYn)){
							throw new IllegalArgumentException("할당된 기획전 점포범위에서 입력하세요");
						}
					}
					// 기획전 점포범위 체크 종료=====

					// 컨텐츠   채널, 점포범위 체크
					contentsCheckInfo = getContentsChStoreCheck(dmapParam);

					for (int i = 0; i < arrStrCdList.length; i++) {
						param.put("strCd", arrStrCdList[i]);
						pscmexh0100Dao.insertConStrAssignInfo(param);
					}
				}

			}else if("03".equals(contentsDivnCd) || "02".equals(contentsDivnCd)){                 //기획전 배너 이미지, 구분자 HTML
				// 컨텐츠   채널, 점포범위 체크
				contentsCheckInfo = getContentsChStoreCheck(dmapParam);

				//1.컨텐츠번호 발번 및 컨텐츠 등록
				createContentsSeq = pscmexh0100Dao.getMkdpConSeqKey(param);
				param.put("contentsSeq", createContentsSeq);
				resultCnt = pscmexh0100Dao.insertContentsImage(param);
			}

		} else if(pageDiv.equals("update")) {
			resultCnt = pscmexh0100Dao.updateContentsImage(param);

			if("01".equals(contentsDivnCd)){                       //기획전 내용(소개) HTML, 이미지

				//* 점포수정 시작===
				String storeModifyYn = StringUtil.null2str((String) param.get("storeModifyYn"));
				if("Y".equals(storeModifyYn)){
					String subStrCdList = StringUtil.null2str((String) param.get("subStrCd"));
					String[] arrStrCdList = subStrCdList.split(",");
					if(!"".equals(subStrCdList)){

						// 기획전 점포범위 체크 시작=====
						dmapParam.put("strCd"    , "999");
						String existStoreYn = pscmexh0100Dao.selectExistMkdpStoreCheck(dmapParam);   // 999존재시 점포범위 체크X
						if("N".equals(existStoreYn)){
							// 기획전 점포범위 체크
							dmapParam.put("storeCount"  , arrStrCdList.length);
							dmapParam.put("arrStrCd"    , arrStrCdList);
							String isStoreYn = pscmexh0100Dao.selectMkdpStoreCheck(dmapParam);
							if("N".equals(isStoreYn)){
								throw new IllegalArgumentException("할당된 기획전 점포범위에서 입력하세요");
							}
						}
						// 기획전 점포범위 체크 종료=====

						// 컨텐츠   채널, 점포범위 체크
						contentsCheckInfo = getContentsChStoreCheck(dmapParam);

						// 전체점포 삭제후   해당 점포등록
						storeResultCnt = pscmexh0100Dao.deleteConStrAssignInfo(param);
						for (int i = 0; i < arrStrCdList.length; i++) {
							param.put("strCd", arrStrCdList[i]);
							pscmexh0100Dao.insertConStrAssignInfo(param);
						}
					}

				}
				//* 점포수정 종료===
		}


		//* 수정 및 등록 성공시 파일업로드
		if(resultCnt>0){
			//  이미지 물리 업로드
			List<FileVO> _result = new ArrayList<FileVO>();
			MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
			final Map<String, MultipartFile> files = mptRequest.getFileMap();
			MultipartFile tmpFile = files.get("tmpFile");
			if(tmpFile!=null){
				if(!tmpFile.getOriginalFilename().equals("") || tmpFile.getSize() > 0) {
					_result = fileMngUtil.parseFileInfSingle(tmpFile, "", "image", "exhibition.image.path");

					if(tmpFile.getSize() > 0 && _result==null) {
						/*message = " 파일용량을 초과했습니다";
						resultMap.put("saveCode","-1");
						resultMap.put("saveMsg", message);
						return "exhibition/PSCMEXH010041";*/
						throw new IllegalArgumentException("파일용량을 초과했습니다");

					} else {
						fileUrl = config.getString("exhibition.image.url");
						gudnImg = fileUrl  +_result.get(0).getStreFileNm();

						//관련 파일 경로만 DB업데이트
						param.put("imgPath", gudnImg);
						resultCnt = pscmexh0100Dao.updateContentsImage(param);
					}
				}
			}
		}
		}

		return resultCnt;

	}

	/**
	 * 컨텐츠   채널, 점포범위 체크
	 * Desc :
	 * @Method Name : getContentsChStoreCheck
	 * @param getContentsChStoreCheck
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public DataMap getContentsChStoreCheck(DataMap dmapParam) throws Exception {
		DataMap resultInfo  = pscmexh0100Dao.selectContentsChStoreCheck(dmapParam);
		if(resultInfo == null){
			return resultInfo;
		}

		String  subStrCd    = StringUtil.null2str((String) resultInfo.get("SUB_STR_CD"));
		String  chCd        = StringUtil.null2str((String) resultInfo.get("CH_CD"));

		String  contentsDivnCd = StringUtil.null2str((String) dmapParam.get("contentsDivnCd"));

		//기획적 내용(소개) 이미지, HTML만 체크
		if("01".equals(contentsDivnCd)){
			if(!"".equals(subStrCd)){
				throw new IllegalArgumentException("해당 채널에는 중복 할당된 점포 정보가 있습니다.");
			}

		}else if("03".equals(contentsDivnCd) || "02".equals(contentsDivnCd)){
			if(!"".equals(chCd)){
				throw new IllegalArgumentException("해당 채널에는 중복 등록된 채널 정보가 있습니다.");
			}
		}

		return resultInfo;
	}


	/**
	 * 이미지/HTML 정보 - 기획전 내용(소개) 정보 조회
	 * Desc :
	 * @Method Name : selectContentsInfo
	 * @param selectContentsInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public DataMap selectContentsInfo(DataMap param) throws Exception {
		return pscmexh0100Dao.selectContentsInfo(param);
	}

	/**
	 * 이미지/HTML 정보 - 기획전내용(소개) 이미지 삭제처리
	 * Desc :
	 * @Method Name : deleteContentsImage
	 * @param deleteContentsImage
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int deleteContentsImage(HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		int resultStoreCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);

			//KEY
			String[] categoryId       = request.getParameterValues("CATEGORY_ID");
			String[] mkdpSeq          = request.getParameterValues("MKDP_SEQ");
			String[] contentsSeq      = request.getParameterValues("CONTENTS_SEQ");

			for (int i=0; i<categoryId.length; i++) {
				DataMap map = new DataMap();
				map.put("categoryId"      , categoryId[i]    );
				map.put("mkdpSeq"         , mkdpSeq[i]       );
				map.put("contentsSeq"     , contentsSeq[i]   );
				resultCnt += pscmexh0100Dao.deleteContentsImage(map);

				//점포 삭제
				resultStoreCnt += pscmexh0100Dao.deleteConStrAssignInfo(map);
			}

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}

	/**
	 * 기획전 구분자정보 콤보 가져오기
	 * Desc : 기획전 구분자정보 콤보 가져오기
	 * @Method Name : selectDivnSeqList
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectDivnSeqList(DataMap param) throws Exception {
		return pscmexh0100Dao.selectDivnSeqList(param);
	}

	/**
	 * 기획전 승인요청
	 * Desc :
	 * @Method Name : insertExhibitionProdInfo
	 * @param insertExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int updateAprv(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap map = new DataMap(request);

			String categoryId  = request.getParameter("categoryId");
			String mkdpSeq     = request.getParameter("mkdpSeq");
			String vendorId     = request.getParameter("vendorId");

			map.put("categoryId" , categoryId);
			map.put("mkdpSeq" , mkdpSeq);

			String aprvStsCd = pscmexh0100Dao.selectAprvStsCdCheck(map);

			//APRV_STS_CD 승인대기 or 재승인대기
			if("00".equals(aprvStsCd)){
				aprvStsCd = "01";
			}else if("03".equals(aprvStsCd)) {
				aprvStsCd = "04";
			}

			map.put("aprvStsCd" , aprvStsCd);
			map.put("categoryId" , categoryId);
			map.put("mkdpSeq" , mkdpSeq);
			map.put("modId" , vendorId);

			resultCnt += pscmexh0100Dao.updateAprv(map);

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}


	/**
	 * 기획전 상품등록
	 * Desc :
	 * @Method Name : insertExhibitionProdInfo
	 * @param insertExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int insertExhibitionProdInfo(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);

			//카테고리번호, 점포코드, 기획전번호

//			String   SearchstrCd = StringUtil.null2str(request.getParameter("strCd"),"");

			String[] prodCd      = request.getParameterValues("PROD_CD");
			String[] divnSeq     = request.getParameterValues("DIVN_SEQ");
			String categoryId  = request.getParameter("categoryId");
			String mkdpSeq     = request.getParameter("mkdpSeq");
			String vendorId     = request.getParameter("vendorId");

			String[] status      = request.getParameterValues("S_STATUS");

			String[] dispYn      = request.getParameterValues("DISP_YN");
			String[] dispSeq     = request.getParameterValues("DISP_SEQ");
			//logger.debug(status.length);
			if(divnSeq != null){
				for (int i=0; i<divnSeq.length; i++) {
					logger.debug("status=>"+status[i]);
					DataMap map = new DataMap();

					map.put("prodCd"    , prodCd[i]);
					map.put("divnSeq"   , divnSeq[i]);
					map.put("categoryId", categoryId);
					map.put("mkdpSeq"   , mkdpSeq);

					map.put("strCd"     , "981");

					//전시순서
					map.put("dispSeq"   , dispSeq[i]);

					//전시여부
					map.put("dispYn"    , dispYn[i]);

					map.put("regId" , vendorId);
					map.put("modId" , vendorId);

					if("I".equals(status[i])){
						resultCnt += pscmexh0100Dao.insertExhibitionProdInfo(map);
					}else if("U".equals(status[i])){
						resultCnt += pscmexh0100Dao.updateExhibitionProdInfo(map);
					}

				}
			}
			logger.debug("resultCnt3    =   "+resultCnt);

			//상품정렬방식 저장
//			param.put("modId" , loginSession.getAdminId());
//			resultCnt += pscmexh0100Dao.updateDivnProdSortInfo(param);

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}


	/**
	 * 통합기획전 상품등록
	 * Desc :
	 * @Method Name : insertExhibitionProdInfo
	 * @param insertExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int insertIntExhibitionProdInfo(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);
			DataMap paramMap = new DataMap(request);

			//카테고리번호, 점포코드, 기획전번호

//			String   SearchstrCd = StringUtil.null2str(request.getParameter("strCd"),"");

			String[] prodCd      = request.getParameterValues("PROD_CD");
			String[] divnSeq     = request.getParameterValues("DIVN_SEQ");
			String[] regId     = request.getParameterValues("REG_ID");
			String categoryId  = request.getParameter("categoryId");
			String mkdpSeq     = request.getParameter("mkdpSeq");
			String vendorId     = request.getParameter("vendorId");

			String[] status      = request.getParameterValues("S_STATUS");
			String[] dispYn      = request.getParameterValues("DISP_YN");
			String[] stsCd      = request.getParameterValues("STS_CD");

			ArrayList<String> aryList = new ArrayList<String>();

			for (int i = 0; i < prodCd.length; i++)
			{
				aryList.add(prodCd[i]);
			}

			paramMap.put("prodCd", aryList);
			paramMap.put("categoryId", categoryId);
			paramMap.put("mkdpSeq", mkdpSeq);

			List<DataMap> list = pscmexh0100Dao.selectAssignChk(paramMap);

			int assignCnt = list.size();
			if( assignCnt > 0 ){
				throw new IllegalArgumentException("같은 제품이 존재합니다.");
			}

			if(divnSeq != null){
				for (int i=0; i<divnSeq.length; i++) {
					DataMap map = new DataMap();

					map.put("prodCd"    , prodCd[i]);
					map.put("divnSeq"   , divnSeq[i]);
					map.put("regId"   , regId[i]);
					map.put("modId"   , regId[i]);
					map.put("categoryId", categoryId);
					map.put("mkdpSeq"   , mkdpSeq);

					// 승인상태코드
					map.put("stsCd"     , stsCd[i]);

					//전시여부
					map.put("dispYn"    , dispYn[i]);

					if("I".equals(status[i])){
						resultCnt += pscmexh0100Dao.insertIntExhibitionProdInfo(map);
					}
//					else if("U".equals(status[i])){
//						resultCnt += pscmexh0100Dao.updateExhibitionProdInfo(map);
//					}

				}
			}
			logger.debug("resultCnt3    =   "+resultCnt);

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}



	/**
	 * 통합기획전 할당 요청
	 * Desc :
	 * @Method Name : insertExhibitionProdInfo
	 * @param insertExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int updateStsCd(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);
			DataMap paramMap = new DataMap(request);

			//카테고리번호, 점포코드, 기획전번호


			String[] prodCd      = request.getParameterValues("PROD_CD");
			String[] divnSeq     = request.getParameterValues("DIVN_SEQ");
			String categoryId  = request.getParameter("categoryId");
			String mkdpSeq     = request.getParameter("mkdpSeq");
			String vendorId     = request.getParameter("vendorId");

			String[] status      = request.getParameterValues("S_STATUS");

			if(divnSeq != null){
				for (int i=0; i<divnSeq.length; i++) {
					DataMap map = new DataMap();

					map.put("prodCd"    , prodCd[i]);
					map.put("divnSeq"   , divnSeq[i]);
					map.put("categoryId", categoryId);
					map.put("mkdpSeq"   , mkdpSeq);

					map.put("modId" , vendorId);

					resultCnt += pscmexh0100Dao.updateStsCd(map);

				}
			}

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}



	/**
	 * 통합기획전 할당 요청
	 * Desc :
	 * @Method Name : insertExhibitionProdInfo
	 * @param insertExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int deleteProdCd(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);
			DataMap paramMap = new DataMap(request);

			//카테고리번호, 점포코드, 기획전번호


			String[] prodCd      = request.getParameterValues("PROD_CD");
			String[] divnSeq     = request.getParameterValues("DIVN_SEQ");
			String categoryId  = request.getParameter("categoryId");
			String mkdpSeq     = request.getParameter("mkdpSeq");

			String[] status      = request.getParameterValues("S_STATUS");

			if(divnSeq != null){
				for (int i=0; i<divnSeq.length; i++) {
					DataMap map = new DataMap();

					map.put("prodCd"    , prodCd[i]);
					map.put("divnSeq"   , divnSeq[i]);
					map.put("categoryId", categoryId);
					map.put("mkdpSeq"   , mkdpSeq);


					resultCnt += pscmexh0100Dao.deleteProdCd(map);

				}
			}

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}



	/**
	 * 기획전 상품조회
	 * Desc :
	 * @Method Name : selectExhibitionProdInfoList
	 * @param selectExhibitionProdInfoList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectExhibitionProdInfoList(DataMap param) throws Exception {
		return pscmexh0100Dao.selectExhibitionProdInfoList(param);
	}

	/**
	 * 기획전 상품조회
	 * Desc :
	 * @Method Name : selectExhibitionProdInfoList
	 * @param selectExhibitionProdInfoList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectDivnSeqlist(DataMap param) throws Exception {
		return pscmexh0100Dao.selectDivnSeqlist(param);
	}

	/**
	 * 기획전 상품조회
	 * Desc :
	 * @Method Name : selectExhibitionProdInfoList
	 * @param selectExhibitionProdInfoList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectExhibitionProdList1(DataMap param) throws Exception {
		return pscmexh0100Dao.selectExhibitionProdList1(param);
	}


	/**
	 * 기획전 상품조회
	 * Desc :
	 * @Method Name : selectExhibitionProdInfoList
	 * @param selectExhibitionProdInfoList
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectExhibitionProdList2(DataMap param) throws Exception {
		return pscmexh0100Dao.selectExhibitionProdList2(param);
	}

	/**
	 * 기획전 상품삭제
	 * Desc :
	 * @Method Name : deleteExhibitionProdInfo
	 * @param deleteExhibitionProdInfo
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public int deleteExhibitionProdInfo(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		LoginSession loginSession = LoginSession.getLoginSession(request);

		try {
			DataMap param = new DataMap(request);

			//카테고리번호, 점포코드, 기획전번호
			String[] categoryId  = request.getParameterValues("CATEGORY_ID");
			String[] strCd       = request.getParameterValues("STR_CD");
			String[] mkdpSeq     = request.getParameterValues("MKDP_SEQ");

			String   prodOrdSetupVal = request.getParameter("prodOrdSetupVal");

			String[] prodCd        = request.getParameterValues("PROD_CD");
			String[] status        = request.getParameterValues("STATUS");

			String[] divnSeq       = request.getParameterValues("DIVN_SEQ");

			for (int i=0; i<categoryId.length; i++) {
				DataMap map = new DataMap();

				map.put("categoryId", categoryId[i]);
				map.put("strCd"     , strCd[i]);
				map.put("mkdpSeq"   , mkdpSeq[i]);
				map.put("divnSeq"   , divnSeq[i]);
				map.put("prodCd"    , prodCd[i]);

				if("U".equals(status[i])){
					resultCnt += pscmexh0100Dao.deleteExhibitionProdInfo(map);
				}

			}

		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}

	/**
	 * 기획전 상품등록 상품정렬방식 조회
	 * Desc : 기획전 상품등록 상품정렬방식 조회
	 * @Method Name : selectDivnProdSortInfo
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public DataMap selectDivnProdSortInfo(DataMap param) throws Exception {
		return pscmexh0100Dao.selectDivnProdSortInfo(param);
	}


	/**
	 * 기획전 상품등록 - 점포별 상품정보 조회
	 * Desc : 기획전 상품등록 - 점포별 상품정보 조회
	 * @Method Name : selectDivnInfoList
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public List<DataMap> selectProdItemList(DataMap param) throws Exception {

		//전점여부 체크
		//String exhibitionStrCd = pscmexh0100Dao.selectExhibitionStrCd(param);
		//param.put("exhibitionStrCd", exhibitionStrCd);
		return pscmexh0100Dao.selectProdItemList(param);
	}

	/**
	 * Desc : MD정보  리스트 가져오기
	 * @Method Name : selectMdList
	 * @param paramMap
	 * @throws Exception
	 * @return List<PSCMEXH010050VO>
	 */
	@Override
	public List<PSCMEXH010050VO> selectMdList(Map<String, String> paramMap) throws Exception {
		return pscmexh0100Dao.selectMdList(paramMap);
	}

	/**
	 * Desc : MD정보 총건수
	 * @Method Name : selectMdTotalCnt
	 * @param paramMap
	 * @throws Exception
	 * @return iTotCnt
	 */
	@Override
	public int selectMdTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscmexh0100Dao.selectMdTotalCnt(paramMap);
	}







	/**
	 * 첨부파일에 대한 목록 정보를 취득한다. (단건)
	 * @param files
	 * @param atchFileId파일 아이디 지정
	 * @param fileDiv 	파일구분 (이미지, 파일 등)
	 * @param storePath 저장경로
	 * @return
	 * @throws Exception
	 *
	 */
	public List<FileVO> fn_parseFileInfSingle(MultipartFile file,
																 String atchFileId
																,String fileDiv
																,String storePath
																,String subpath)
																throws Exception {
			long posblAtchFileSize = 0;

			// 파일 사이즈 지정
			if(fileDiv != null) {
				logger.debug("atchFileSize ==============   "+config.getString("fileCheck.atchFile.size"));
				posblAtchFileSize = Long.valueOf(config.getString("fileCheck.atchFile.size"));
			}

			String fileStorePath = config.getString(storePath)+subpath;
	    	File saveFolder       = new File(fileStorePath);

	    	if (!saveFolder.exists() || saveFolder.isFile()) {
	    		saveFolder.mkdirs();
	    	}

	    	String filePath = "";
	    	List<FileVO> result  = new ArrayList<FileVO>();
	    	FileVO fileVO;

	    	String fileSizeError = "N";

    		if(file.getSize() > 0) {

		    		long _size = file.getSize();
		    		if(posblAtchFileSize < _size) {
		    			fileSizeError = "Y";
		    			return result;
		    		}

		    		// 파일명이 없는 경우 처리
		    		String orginFileName = file.getOriginalFilename();
		    		if ("".equals(orginFileName)) {
		    			return result;
		    		}

		    		// 이미지파일 받아야 하는데 이미지파일이 아닐경우
		    		int index = orginFileName.lastIndexOf(".");
		    		String fileExt = orginFileName.substring(index + 1);
		    		String filePyscName = StringUtil.getTimeStamp();

		    		// 이미지 일경우 확장자를 붙인다.
		    		if(fileDiv.equals("image")){
		    			filePyscName += "."+fileExt;
		    		}

		    		if (!"".equals(orginFileName)) {
		    			filePath = fileStorePath + filePyscName;

//		    			String imageChk = pscmexh0100Dao.selectExistMkdpStoreCheck(dmapParam);
		    			int imageChk = pscmexh0100Dao.selectImageNm(filePath);   // 이미지명이 같은게 있는지 체크

		    			if(imageChk > 0){
		    				result = null;
		    			}else{
		    				file.transferTo(new File(filePath));
		    			}

		    		}

		    		String atchFileIdString = "";
		    		if ("".equals(atchFileId) || atchFileId == null) {
		    			atchFileIdString = idgenService.getNextStringId();
		    		} else {
		    			atchFileIdString = atchFileId;
		    		}

		    		fileVO = new FileVO();
		    		fileVO.setAtchFileId(atchFileIdString);
		    		fileVO.setOrignlFileNm(orginFileName);
		    		fileVO.setStreFileNm(filePyscName);
		    		fileVO.setFileStreCours(fileStorePath);
		    		fileVO.setFileMg(Long.toString(_size));
		    		fileVO.setFileExtsn(fileExt);
		    		result.add(fileVO);
		    	}

	    	// 파일사이즈 초과시 첨부한 전체파일 삭제
	    	if("Y".equals(fileSizeError)){
	    		fileVO = null;

	    		for(int i = 0;i<result.size();i++){
	    			fileVO = result.get(i);

	    			fileMngUtil.deleteFile(fileVO.getFileStreCours(), fileVO.getStreFileNm());
	    		}
	    		result = null;
	    	}

			return result;
	}

	@Override
	public int selectAdminInfoTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscmexh0100Dao.selectAdminInfoTotalCnt(paramMap);
	}

	@Override
	public List<DataMap> selectAdminInfoList(Map<String, String> paramMap) throws Exception {
		return pscmexh0100Dao.selectAdminInfoList(paramMap);
	}

	@Override
	public String mkdpNm(DataMap paramMap) throws Exception {
		return pscmexh0100Dao.mkdpNm(paramMap);
	}

}

