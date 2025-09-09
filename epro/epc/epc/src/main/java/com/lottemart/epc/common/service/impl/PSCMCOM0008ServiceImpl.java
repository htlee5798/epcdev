package com.lottemart.epc.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.dao.PSCMCOM0008Dao;
import com.lottemart.epc.common.service.PSCMCOM0008Service;
import com.lottemart.epc.common.model.PSCMCOM0008VO;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("PSCMCOM0008Service")
public class PSCMCOM0008ServiceImpl implements PSCMCOM0008Service {


	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "10";

	@Autowired
	private PSCMCOM0008Dao PSCMCOM0008Dao;

	@Override
	public Map<String,Object> selectVendCodeList(PSCMCOM0008VO vo,HttpServletRequest request)
	{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state","-1");
		List<PSCMCOM0008VO> vendCodeList=null;
		int totalCount = 0;

		//사번
		String loginId = vo.getEmpNo();

		vo.setEmpNo(loginId);   // BOS 로그인 사번


		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));


		totalCount = PSCMCOM0008Dao.selectVendCodeListCnt(vo);


		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);


		if (totalCount > 0){
			vendCodeList = (List<PSCMCOM0008VO>)PSCMCOM0008Dao.selectVendCodeList(vo);
		}

		result.put("vendCodeList", vendCodeList);

		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}



}

