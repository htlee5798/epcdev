package com.lottemart.epc.edi.main.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.main.dao.MainDashBoardDao;
import com.lottemart.epc.edi.main.model.MainDashBoardVO;
import com.lottemart.epc.edi.main.service.MainDashBoardService;
import com.lottemart.epc.edi.product.controller.NEDMPRO0020Controller;

import lcn.module.framework.property.ConfigurationService;

@Service("mainDashBoardService")
public class MainDashBoardServiceImpl implements MainDashBoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Autowired
	private MainDashBoardDao mainDashBoardDao;
	
	/**
	 * Main → 파트너사 실적 조회
	 */
	@Override
	public Map<String, Object> selectVenPrfrList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("msg", "FAIL");
		
		Map<String, Object> barChartMap = new HashMap<>();
		 
		//X축 데이터
		//String[] xAxisData = {"1월", "2월", "3월", "4월", "5월", "6월", "7월"};
		
		List<Map<String, Object>> list = new ArrayList<>();
		list = mainDashBoardDao.selectVenPrfrList(paramVo);
		
		//seriesList.add(createBarData("매출액", new int[]{10, 20, 15, 30, 15, 10, 30} ,"bar"));
		//seriesList.add(createBarData("상품이익", new int[]{12, 18, 14, 28, 13, 22, 28} ,"bar"));
		//seriesList.add(createBarData("순이익", new int[]{10, 9, 7, 14, 6, 12, 14} ,"bar"));
		//seriesList.add(createBarData("평가이익", new int[]{15, 14, 12, 7, 11, 17, 21} ,"bar"));
		
		int i = 0;
		for( Map<String, Object> vo : list ) {
			String data[] = vo.get("data").toString().split(",");
			
			//int[] nums = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
			//vo.put("data", nums );
			double[] nums = Arrays.stream(data).mapToDouble(Double::parseDouble).toArray();
			vo.put("data", nums );
			/*if(i > 1) {
				double[] nums = Arrays.stream(data).mapToDouble(Double::parseDouble).toArray();
				vo.put("data", nums );
			}else {
				int[] nums = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
				vo.put("data", nums );
			}*/
			//int[] nums = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
			//double[] nums = Arrays.stream(data).mapToDouble(Double::parseDouble).toArray();
			
			//System.out.println("asdfasfasd : " +  nums.toString() );
			i++;
		}
		
		String xAxisData[] = list.get(0).get("month").toString().split(",");
		barChartMap.put("xAxisData", xAxisData );
		barChartMap.put("series", list);
		 
		result.put("result", true);
		result.put("barChartMap", barChartMap);
		return result;
	}
	
	
	/**
	 * Main → 파트너사 매입액 TOP 10 조회
	 */
	@Override
	public Map<String, Object> selectVenBuyList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectVenBuyCount(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectVenBuyList(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
	}
	
	/**
	 * Main → 파트너사 매입액 TOP 10 조회_old250820 (table 버전)
	 */
	public Map<String, Object> selectVenBuyList_old250820(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<MainDashBoardVO> list = new ArrayList<MainDashBoardVO>();		//리스트 조회 결과 Vo
		
		//합계 행 구성 변수
		float totBuyAmt = 0;			//매입금액 총계
		float totBuyQty = 0;			//매입량 총계
		float totSalesAmt = 0;			//매출액 총계
		float totSalesQty = 0;			//매출량 총계

		int totalCount = 0;				//데이터 전체 카운트
		// 파트너사 매입액 TOP 10 카운터 조회
		totalCount = mainDashBoardDao.selectVenBuyCount(paramVo);

		// 데이터 있을 경우, 파트너사 매입액 TOP 10 리스트 조회
		if(totalCount > 0){	
			list = mainDashBoardDao.selectVenBuyList(paramVo);
			
			//------------------------ 리스트 데이터 존재 시, 합계 행 계산 start
			if(list != null && !list.isEmpty()) {
				//매입금액 총계
				totBuyAmt = list.stream().map(MainDashBoardVO::getBuyAmt)
							.map(strBuyAmt -> {
								try {
						            return Float.parseFloat(strBuyAmt.replace(",", ""));
						        } catch (NumberFormatException e) {
						        	logger.error("strBuyAmt Type Parsing Error :"+e.getMessage());
						            return 0f; // 변환 실패 시 0 처리
						        }
							}).reduce(0f, Float::sum);
				
				//매입량 총계
				totBuyQty = list.stream().map(MainDashBoardVO::getBuyQty)
							.map(strBuyQty -> {
								try {
						            return Float.parseFloat(strBuyQty.replace(",", ""));
						        } catch (NumberFormatException e) {
						        	logger.error("strBuyQty Type Parsing Error :"+e.getMessage());
						            return 0f; // 변환 실패 시 0 처리
						        }
							}).reduce(0f, Float::sum);
				
				//매출액 총계
				totSalesAmt = list.stream().map(MainDashBoardVO::getSalesAmt)
						.map(strSalesAmt -> {
							try {
					            return Float.parseFloat(strSalesAmt.replace(",", ""));
					        } catch (NumberFormatException e) {
					        	logger.error("strSalesAmt Type Parsing Error :"+e.getMessage());
					            return 0f; // 변환 실패 시 0 처리
					        }
						}).reduce(0f, Float::sum);
				
				//매출량 총계
				totSalesQty = list.stream().map(MainDashBoardVO::getSalesQty)
						.map(strSalesQty -> {
							try {
					            return Float.parseFloat(strSalesQty.replace(",", ""));
					        } catch (NumberFormatException e) {
					        	logger.error("strSalesQty Type Parsing Error :"+e.getMessage());
					            return 0f; // 변환 실패 시 0 처리
					        }
						}).reduce(0f, Float::sum);
			}
			//------------------------ 리스트 데이터 존재 시, 합계 행 계산 end
			
		}
		
		//합계 행 setting
		MainDashBoardVO summaryVo = new MainDashBoardVO();
		summaryVo.setBuyAmt(String.format("%.0f", totBuyAmt));		//매입액 총계
		summaryVo.setBuyQty(String.format("%.0f", totBuyQty));		//매입량 총계
		summaryVo.setSalesAmt(String.format("%.0f", totSalesAmt));	//매출액 총계
		summaryVo.setSalesQty(String.format("%.0f", totSalesQty));	//매출량 총계
		
		result.put("list", list);							//리스트 데이터
		result.put("summary", summaryVo);					//합계 데이터
		result.put("result", true);
		return result;
	}

	/**
	 * Main → 신상품 실적 조회
	 */
	@Override
	public Map<String, Object> selectNewProdPrfrList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectNewProdPrfrCount(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectNewProdPrfrList(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
	}
	
	/**
	 * Main → 신상품 입점 제안 조회
	 */
	@Override
	public Map<String, Object> selectProdNewPropList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectProdNewPropCount(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectProdNewPropList(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
	}

	/**
	 * Main 원가변경요청 내역 및 상태 조회
	 */
	@Override
	public Map<String, Object> selectMainTpcProdChgCostItem(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectMainTpcProdChgCostItemCnt(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectMainTpcProdChgCostItem(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
	}

	/**
	 * Main 데이터 일괄 조회
	 */
	@Override
	public Map<String, Object> selectMainDashBoardAll(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		Map<String, Object> dataMap = new HashMap<String,Object>();
		
		//1. 파트너사 실적조회
		Map<String, Object> venPrfrMap = this.selectVenPrfrList(paramVo, request); 
		dataMap.put("VEN_PRFR", venPrfrMap);
		
		//2. 파트너사 매입액 TOP 10 리스트 조회
		Map<String, Object> venBuyMap = this.selectVenBuyList(paramVo, request);
		dataMap.put("VEN_BUY", venBuyMap);
		
		//3. 파트너사 SKU 현황
		Map<String, Object> venSkuMap = this.selectMainVenSku(paramVo, request);
		dataMap.put("VEN_SKU", venSkuMap);
		
		//4. 기간별 미납율
		// --- chart
		
		//5. 미납 내역
		Map<String,Object> nonPayItemMap = this.selectMainNonPayItems(paramVo, request);
		dataMap.put("NON_PAY_ITEM", nonPayItemMap);
		
		//6. 입고 거부 상품 미조치 내역
		Map<String,Object> inboundRejectsMap = this.selectMainInboundRejects(paramVo, request);
		dataMap.put("INB_RJT_ITEM", inboundRejectsMap);
		
		//7. 신상품 입점 제안 조회
		Map<String,Object> prodNewPropMap = this.selectProdNewPropList(paramVo, request);
		dataMap.put("PROD_NEW_PROP", prodNewPropMap);
		
		//8. 신상품 실적조회
		Map<String, Object> newProdPrfrMap = this.selectNewProdPrfrList(paramVo, request);
		dataMap.put("NEW_PROD_PRFR", newProdPrfrMap);
		
		//9. 원가변경요청 내역 조회
		Map<String,Object> prodChgCostItemMap = this.selectMainTpcProdChgCostItem(paramVo, request);
		dataMap.put("PROD_CHG_COST_ITEM", prodChgCostItemMap);
		
		return dataMap;
	}

	/**
	 * Main 미납내역 조회
	 */
	@Override
	public Map<String, Object> selectMainNonPayItems(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectMainNonPayItemsCnt(paramVo);
		String nonPayRate = "0";		//미납율
		
		//data list exist
		if(totalCount > 0) {
			//데이터리스트 조회
			list = mainDashBoardDao.selectMainNonPayItems(paramVo);
			
			//미납율 조회
			nonPayRate = mainDashBoardDao.selectMainNonPayRate(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		//추가 DATA
		gridMap.put("rate", nonPayRate);		//미납율
		
		return gridMap;
	}

	/**
	 * Main 파트너사 SKU 현황 조회
	 */
	@Override
	public Map<String, Object> selectMainVenSku(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectMainVenSkuCnt(paramVo);
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectMainVenSku(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
	}

	/**
	 * Main 입고 거부 상품 미조치 내역 조회
	 */
	@Override
	public Map<String, Object> selectMainInboundRejects(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);
		
		//====== 페이징 정보
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		int currentPage = paramVo.getPage();	//현재 페이지
		int rowPerPage = paramVo.getRows();		//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		paramVo.setStartRowNo(startRow);
		paramVo.setEndRowNo(endRow);
		
		//data list
		List<MainDashBoardVO> list = null;
		
		totalCount = mainDashBoardDao.selectMainInboundRejectsCnt(paramVo);
		String rjtRate = "0";		//거부율
		
		//data list exist
		if(totalCount > 0) {
			list = mainDashBoardDao.selectMainInboundRejects(paramVo);
			
			//거부율 조회
			rjtRate = mainDashBoardDao.selectMainInboundRejectsRate(paramVo);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		//추가 DATA
		gridMap.put("rate", rjtRate);			//거부율
		
		return gridMap;
	}
	
	
}
