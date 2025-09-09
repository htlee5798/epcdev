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
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0140Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0140VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0140Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;
@Service("NEDMWEB0140Service")
public class NEDMWEB0140ServiceImpl implements NEDMWEB0140Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "15";

	@Autowired
	private NEDMWEB0140Dao nedmweb0140dao;



	/**
	 *  당일 반품등록 내역 조회(합계 / 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnList(NEDMWEB0140VO vo, HttpServletRequest request)  throws Exception {

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */


		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");




			int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
			int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
			int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));


			NEDMWEB0140VO		sumData     = null;
			List<NEDMWEB0140VO> 	listData	= null;
			int totalCount = 0;


			totalCount = nedmweb0140dao.selectDayRtnListTotCnt(vo);


			Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
			int startRowNum = paging.getStartRowNum();
			int endRowNum = paging.getEndRowNum();
			vo.setStartRowNo(startRowNum);
			vo.setEndRowNo(endRowNum);


			if (totalCount > 0){
				listData	= nedmweb0140dao.selectDayRtnList(vo);		// 목록
			}

			sumData 	= nedmweb0140dao.selectDayRtnSum(vo); 		// 합계

			result.put("listData", 		listData);
			result.put("sumData", 		sumData);
			result.put("totalCount", totalCount);

			result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
			result.put("state",		"0");		// 처리상태
			result.put("message",	"SUCCESS");	// 처리메시지


		return result;
	}

}
