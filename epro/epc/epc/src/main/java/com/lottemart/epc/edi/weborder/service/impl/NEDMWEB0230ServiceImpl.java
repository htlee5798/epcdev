package com.lottemart.epc.edi.weborder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0230Dao;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0230VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0230Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;



@Service("nedmweb0230Service")
public class NEDMWEB0230ServiceImpl implements NEDMWEB0230Service{

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private NEDMWEB0230Dao NEDMWEB0230Dao;

	public Map<String,Object> selectVenRtnInfo(NEDMWEB0230VO vo,HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */


		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state","-1");



		List<EdiRtnProdVO> listData=null;


		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = NEDMWEB0230Dao.selectVendRtnListTotCnt(vo);

		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);


		if (totalCount > 0){
			listData = NEDMWEB0230Dao.selectVenRtnInfo(vo);
		}

		result.put("listData", listData);

		result.put("totalCount", totalCount);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지



		return result;
	}





}
