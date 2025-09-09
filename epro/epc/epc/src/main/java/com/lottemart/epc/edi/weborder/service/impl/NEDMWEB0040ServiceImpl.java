package com.lottemart.epc.edi.weborder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0040Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0040VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0040Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

/**
 * @Class Name : NEDMWEB0040ServiceImpl
 * @Description : 발주전체현황 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nEDMWEB0040Service")
public class NEDMWEB0040ServiceImpl implements NEDMWEB0040Service {
	
	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";
	
	@Autowired
	private NEDMWEB0040Dao nedmweb0040dao;
	
	public Map<String,Object> selectOrdTotList(NEDMWEB0040VO vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0040VO> list = null;
		NEDMWEB0040VO ordCnt = null;
		
		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;
		
		totalCount = nedmweb0040dao.selectOrdTotListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = nedmweb0040dao.selectOrdTotList(vo);
		}
		
		ordCnt = nedmweb0040dao.selectOrdTotCntSum(vo);
		
		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("ordCnt", ordCnt);
		
		return result;
	}
	
}
