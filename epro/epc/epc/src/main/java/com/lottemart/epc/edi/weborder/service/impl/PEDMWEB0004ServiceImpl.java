package com.lottemart.epc.edi.weborder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0004Dao;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0004Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("pEDMWEB0004Service")
public class PEDMWEB0004ServiceImpl implements PEDMWEB0004Service {
	
	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";
	
	@Autowired
	private PEDMWEB0004Dao pedmweb0004dao;
	
	public Map<String,Object> selectOrdTotList(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;
		TedOrdList ordCnt = null;
		
		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;
		
		totalCount = pedmweb0004dao.selectOrdTotListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = pedmweb0004dao.selectOrdTotList(vo);
		}
		
		ordCnt = pedmweb0004dao.selectOrdTotCntSum(vo);
		
		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("ordCnt", ordCnt);
		
		return result;
	}
	
}
