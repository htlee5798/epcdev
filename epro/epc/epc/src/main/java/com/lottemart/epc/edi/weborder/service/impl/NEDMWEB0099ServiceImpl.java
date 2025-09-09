package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0099Dao;
import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.EdiVendorVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0099VO;
import com.lottemart.epc.edi.weborder.model.TedStore;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0099Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;



@Service("nedmweb0099Service")
public class NEDMWEB0099ServiceImpl implements NEDMWEB0099Service{



	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "10";


	@Autowired
	private NEDMWEB0099Dao nEDMWEB0099Dao;

	/**
	 * 사원정보 찾기(empNo조건)
	 * @param NEDMWEB0099VO
	 * @return EdiPoEmpPoolVO
	 * @throws SQLException
	 */
	public EdiPoEmpPoolVO selectEmpPoolData(NEDMWEB0099VO vo) throws Exception{
		return nEDMWEB0099Dao.selectEmpPoolData(vo);
	}

	/**
	 * 담당자별 또는 특정1협력사 전체 조회
	 * @param NEDMWEB0099VO
	 * @return EdiVendorVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiVendorVO selectEmpVenData(NEDMWEB0099VO vo) throws Exception{
		return nEDMWEB0099Dao.selectEmpVenData(vo);
	}


	/**
     * 반품상품 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param NEDMWEB0099VO
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public EdiRtnProdVO selectStrCdProdSumList(NEDMWEB0099VO vo) throws Exception{
		return nEDMWEB0099Dao.selectEdiRtnProdData(vo);
	}


	/**
     * 반품상품 조회2  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param NEDMWEB0099VO
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public List<EdiRtnProdVO> selectStrCdProdSumList2(NEDMWEB0099VO vo) throws Exception{
		return nEDMWEB0099Dao.selectEdiRtnProdData2(vo);
	}


	/**
     * 반품상품 목록 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param NEDMWEB0099VO
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public Map<String,Object> selectProdCodeList(NEDMWEB0099VO vo) throws Exception {

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state","-1");
		List<EdiRtnProdVO> prodCodeList=null;
		int totalCount = 0;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));


		totalCount = nEDMWEB0099Dao.selectProdCodeListCnt(vo);


		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);


		if (totalCount > 0){
			prodCodeList = (List<EdiRtnProdVO>)nEDMWEB0099Dao.selectProdCodeList(vo);
		}


		result.put("prodCodeList", prodCodeList);

		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}

	/**
     * 업체 발주가능 점포 조회
     * @param NEDMWEB0099VO
     * @return List<TedStore>
     * @throws Exception
    */
	public List<TedStore> selectVenStoreCodeList(NEDMWEB0099VO vo) throws Exception{
		return nEDMWEB0099Dao.selectVenStoreCodeList(vo);
	}

}
