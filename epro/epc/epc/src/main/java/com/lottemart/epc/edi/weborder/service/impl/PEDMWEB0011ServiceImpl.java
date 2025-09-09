package com.lottemart.epc.edi.weborder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0011Service;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0011Dao;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;



@Service("pedmweb0011Service")
public class PEDMWEB0011ServiceImpl implements PEDMWEB0011Service{


	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private PEDMWEB0011Dao PEDMWEB0011Dao;

	public Map<String,Object> selectVenOrdAllInfo(SearchWebOrder vo,HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub


		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> VenAllList=null;




		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = PEDMWEB0011Dao.selectVendOrdAllListTotCnt(vo);

		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);



		if (totalCount > 0){
			VenAllList = PEDMWEB0011Dao.selectVenOrdAllInfo(vo);
		}

		result.put("VenAllList", VenAllList);

		result.put("totalCount", totalCount);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));

		return result;
	}





}
