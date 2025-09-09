package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0001Dao;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0003Dao;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0003Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("pEDMWEB0003Service")
public class PEDMWEB0003ServiceImpl implements PEDMWEB0003Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private PEDMWEB0001Dao pedmweb0001dao;

	@Autowired
	private PEDMWEB0003Dao pedmweb0003dao;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0003ServiceImpl.class);


	/**
	 * 발주일괄등록 정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectOrdPackInfo(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;
		TedOrdList ordCntVo = null;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = pedmweb0003dao.selectOrdPackListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = pedmweb0003dao.selectOrdPackList(vo);
		}
		// 전점 합계 조회
		ordCntVo = pedmweb0003dao.selectOrdPackCntSum(vo);

		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("ordCnt", ordCntVo);

		return result;
	}

	/**
	 * 발주 일괄등록 정보 저장
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String insertOrdPackInfo(TedPoOrdPackList001VO vo, HttpServletRequest request, String ordVenCd) throws SQLException, Exception{
		String result = null;
		String packDivnCd = null;
		String fileGrpCd = null;

		TedPoOrdPackVO pakVO = new TedPoOrdPackVO();

		// 파일 구분 코드 생성
		packDivnCd = pedmweb0003dao.selectNewPackDivnCd();

		// 파일 그룹 코드 생성
		fileGrpCd = pedmweb0003dao.selectNewFileGrpCd(ordVenCd);

		// 업체발주요청 마스터 저장
		for ( TedPoOrdPackVO pack : vo ) {
			pack.setFileGrpCd(fileGrpCd);
			pack.setPackDivnCd(packDivnCd);
			pack.setRegId(pack.getVenCd());
			pack.setModId(pack.getVenCd());
		}

		// 업체발주요청상품정보 저장
		pedmweb0003dao.insertOrdPackInfo(vo);

		// 업체발주요청상품정보 수정
		pakVO.setPackDivnCd(packDivnCd);
		pedmweb0003dao.updateOrdPackInfo(pakVO);

		// 오류 코드 업데이트 ( TED_ORD_SUPP_INFO와 Excel 정보가 일치 하지 않은 경우)
		// 상품코드 오류 시 상태 업데이트
		//pedmweb0003dao.updateOrdPackProdState(packDivnCd);
		// 점포코드 오류 시 상태 업데이트
		pedmweb0003dao.updateOrdPackStoreState(packDivnCd);

		result = "suc";

		return result;
	}

	/**
	 * 엑셀 정보 삭제
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public String deleteExcelOrdInfo(TedPoOrdPackVO vo) throws Exception{

		String result = null;

		// 업체발주요청상품정보 삭제
		pedmweb0003dao.deleteExcelOrdInfo(vo);

		result = "suc";

		return result;

	}

	/**
	 * Excel 정보 저장
	 * @param TedPoOrdPackVO
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String insertExcelOrdInfo(TedPoOrdPackVO vo, HttpServletRequest request) throws Exception{
		List<TedPoOrdPackVO> packList = new TedPoOrdPackList001VO();
		List<TedPoOrdPackVO> ordList = new TedPoOrdPackList001VO();
		TedPoOrdMstVO  mst = new TedPoOrdMstVO();

		int excelErrorCnt = 0;
		int excelDuplCnt = 0;
		int ordDuplCnt = 0;

		// 엑셀 데이터 중 오류건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-001
		excelErrorCnt = pedmweb0003dao.selectExcelErrorCnt(vo);
		if(excelErrorCnt > 0){
			return "fail-001";
		}

		// 엑셀 데이터중 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-002
		excelDuplCnt = pedmweb0003dao.selectExcelDuplCnt(vo);
		if(excelDuplCnt > 0){
			return "fail-002";
		}

		// 기존에 저장된 데이터와 비교하여 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-003
		ordDuplCnt = pedmweb0003dao.selectOrdDuplCnt(vo);
		if(ordDuplCnt > 0){
			return "fail-003";
		}

		// 오류건과 중복건이 없을 경우 점포 코드 조회 후( ven_cd, ord_dy ) TED_PO_ORD_MST 점포별로 생성
		packList = pedmweb0003dao.selectPackStrInfo(vo);
		//ordList = pedmweb0003dao.selectPackInfoList(vo);

		// 처리할 데이터가 없을 경우 리턴
		if(packList == null || packList.size() == 0 ){
			return "fail-004";
		}

		for ( TedPoOrdPackVO  ord : packList ) {
			if("".equals(ord.getOrdReqNo()) || ord.getOrdReqNo() == null) ord.setOrdReqNo(pedmweb0001dao.selectNewOrdReqNo());
		}

		// 업체발주요청상품정보 저장 TED_PO_ORD_PROD [ executeBatch.insert/update ]
		pedmweb0003dao.insertExcelProdInfo(packList);
		//-----------------------------------------

		// 일괄작업용 KEY 조건 설정 ------------------
		mst.setVenCd(packList.get(0).getVenCd());
		mst.setOrdDy(packList.get(0).getOrdDy());
		//-----------------------------------------

		// 업체발주정보마스터 생성 -------------------
		pedmweb0003dao.insertExcelMstInfo(mst);
		//-----------------------------------------

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 점포별 일괄 업데이트
		pedmweb0003dao.updateExcelOrdMstSum(mst);
		//-----------------------------------------

		// 저장이 성공하면 TED_PO_ORD_PACK 정보 삭제.
		pedmweb0003dao.deleteOrdPackInfo(vo);

		return  "suc";

	}
}
