package com.lottemart.epc.edi.weborder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0220Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0220VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0220Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

/**
 * @Class Name : NEDMWEB0220ServiceImpl
 * @Description : 발주전체현황 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Service("nedmweb0220Service")
public class NEDMWEB0220ServiceImpl implements NEDMWEB0220Service{


	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private NEDMWEB0220Dao nEDMWEB0220Dao;

	public Map<String,Object> selectVenOrdAllInfo(NEDMWEB0220VO vo,HttpServletRequest request) throws Exception{

		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0220VO> VenAllList=null;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = nEDMWEB0220Dao.selectVendOrdAllListTotCnt(vo);

		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);



		if (totalCount > 0){
			VenAllList = nEDMWEB0220Dao.selectVenOrdAllInfo(vo);
		}

		result.put("VenAllList", VenAllList);

		result.put("totalCount", totalCount);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));

		return result;
	}





}
