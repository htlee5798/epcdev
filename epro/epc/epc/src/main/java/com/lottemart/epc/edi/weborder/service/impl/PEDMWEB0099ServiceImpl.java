package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0099Dao;
import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.EdiVendorVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedStore;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0099Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;



@Service("pedmweb0099Service")
public class PEDMWEB0099ServiceImpl implements PEDMWEB0099Service{



	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "10";


	@Autowired
	private PEDMWEB0099Dao PEDMWEB0099Dao;

	/**
	 * 사원정보 찾기(empNo조건)
	 * @param SearchWebOrder
	 * @return EdiPoEmpPoolVO
	 * @throws SQLException
	 */
	public EdiPoEmpPoolVO selectEmpPoolData(SearchWebOrder vo) throws Exception{
		return PEDMWEB0099Dao.selectEmpPoolData(vo);
	}

	/**
	 * 담당자별 또는 특정1협력사 전체 조회
	 * @param SearchWebOrder
	 * @return EdiVendorVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiVendorVO selectEmpVenData(SearchWebOrder vo) throws Exception{
		return PEDMWEB0099Dao.selectEmpVenData(vo);
	}


	/**
     * 반품상품 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public EdiRtnProdVO selectStrCdProdSumList(SearchWebOrder vo) throws Exception{
		return PEDMWEB0099Dao.selectEdiRtnProdData(vo);
	}


	/**
     * 반품상품 조회2  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public List<EdiRtnProdVO> selectStrCdProdSumList2(SearchWebOrder vo) throws Exception{
		return PEDMWEB0099Dao.selectEdiRtnProdData2(vo);
	}


	/**
     * 반품상품 목록 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return EdiRtnProdVO
     * @throws Exception
    */
	@Override
	public Map<String,Object> selectProdCodeList(SearchWebOrder vo) throws Exception {

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state","-1");
		List<EdiRtnProdVO> prodCodeList=null;
		int totalCount = 0;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));


		totalCount = PEDMWEB0099Dao.selectProdCodeListCnt(vo);


		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);


		if (totalCount > 0){
			prodCodeList = (List<EdiRtnProdVO>)PEDMWEB0099Dao.selectProdCodeList(vo);
		}


		result.put("prodCodeList", prodCodeList);

		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}

	/**
     * 업체 발주가능 점포 조회
     * @param SearchWebOrder
     * @return List<TedStore>
     * @throws Exception
    */
	public List<TedStore> selectVenStoreCodeList(SearchWebOrder vo) throws Exception{
		return PEDMWEB0099Dao.selectVenStoreCodeList(vo);
	}

}
