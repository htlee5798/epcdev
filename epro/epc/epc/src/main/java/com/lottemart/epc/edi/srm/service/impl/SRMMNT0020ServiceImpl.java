package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.dao.SRMMNT002001Dao;
import com.lottemart.epc.edi.srm.dao.SRMMNT0020Dao;
import com.lottemart.epc.edi.srm.model.SRMMNT002001VO;
import com.lottemart.epc.edi.srm.model.SRMMNT0020VO;
import com.lottemart.epc.edi.srm.service.SRMMNT0020Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 대표자 SRM 모니터링 ServiceImpl
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Service("srmmnt0020Service")
public class SRMMNT0020ServiceImpl implements SRMMNT0020Service {

	@Autowired
	private SRMMNT0020Dao srmmnt0020Dao;

	@Autowired
	private SRMMNT002001Dao srmmnt002001Dao;

	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMMNT0020VO vo) throws Exception {
		return srmmnt0020Dao.selectCatLv1CodeList(vo);
	}

	/**
	 * SRM 모니터링 LIST
	 * @param SRMVEN0030VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectSRMmoniteringList(SRMMNT0020VO vo, HttpServletRequest request) throws Exception {

		//세션에서 로그인한 사용자 업체코드 받아오기
		if (StringUtil.isEmpty(vo.getVenCd())) {
			List<SRMMNT0020VO> venCdList = srmmnt002001Dao.selectCeoLoginVenCdLIst(vo.getIrsNo());
			ArrayList venCds = new ArrayList();
			for (int i = 0; i < venCdList.size(); i++) {
				venCds.add(venCdList.get(i).getVenCd());
			}
			vo.setVenCds(venCds);
		} else {
			ArrayList venCds = new ArrayList();
			venCds.add(vo.getVenCd());
			vo.setVenCds(venCds);
		}

		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setRecordCountPerPage(15);
		paginationInfo.setPageSize(vo.getPageSize());

		result.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = srmmnt0020Dao.selectSRMmoniteringListCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMMNT0020VO>	resultList 	= 	srmmnt0020Dao.selectSRMmoniteringList(vo);
		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		result.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return result;
	}



	/**
	 * SRM 모니터링 상세조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetail(SRMMNT002001VO vo, HttpServletRequest request) throws Exception {
		//locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmmnt0020Dao.selectSRMmoniteringDetail(vo);
	}



	/**
	 * SRM 모니터링 상세조회 차트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailChart(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailChart(vo);
	}


	/**
	 * 사업자 번호로 업체코드 조회
	 * @param String
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	public List<SRMMNT0020VO> selectCeoLoginVenCdLIst(String irsNo) throws Exception {
		return srmmnt002001Dao.selectCeoLoginVenCdLIst(irsNo);
	}


	/**
	 * 현재까지 점수
	 * @param SRMMNT002001VO
	 * @return HashMap<String, List<SRMMNT002001VO>>
	 * @throws Exception
	 */
	public HashMap<String, List<SRMMNT002001VO>> selectSRMmoniteringDetailCurrentValue(SRMMNT002001VO vo) throws Exception {
		HashMap<String, List<SRMMNT002001VO>> resultMap = new HashMap();
		vo.setVendorYn("Y");
		resultMap.put("compCurrentValue", srmmnt0020Dao.selectSRMmoniteringDetailCurrentValue(vo));		//업체
		vo.setVendorYn("N");
		resultMap.put("allCurrentValue", srmmnt0020Dao.selectSRMmoniteringDetailCurrentValue(vo));		//업체

		return resultMap;
	}

	/**
	 * 고객컴플레인수
	 * @param SRMMNT002001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailClaim(vo);
	}

	/**
	 * 등급조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailGrade(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailGrade(vo);
	}

	/**
	 * 비고, 특이사항 조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailRemarkEtc(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailRemarkEtc(vo);
	}

	/**
	 * 등급 예제 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailGradeExemple(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailGradeExemple(vo);
	}

	/**
	 * PLC등급 리스트 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailPlc(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailPlc(vo);
	}

	/**
	 * 불량등록건수
	 * @param SRMMNT002001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailDefective(vo);
	}



	/**
	 * 불량등록건수 리스트
	 * @param SRMVEN003001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailDefectiveList(SRMMNT002001VO vo, HttpServletRequest request) throws Exception {
		//locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmmnt0020Dao.selectSRMmoniteringDetailDefectiveList(vo);
	}

	/**
	 * 개선조치
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailImpReq(SRMMNT002001VO vo, HttpServletRequest request) throws Exception {
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmmnt0020Dao.selectSRMmoniteringDetailImpReq(vo);
	}

	/**
	 * 첨부파일 조회
	 * @param SRMVEN003001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMMNT002001VO vo) throws Exception {
		return srmmnt0020Dao.selectSRMmoniteringDetailImpReqFileList(vo);
	}
}
