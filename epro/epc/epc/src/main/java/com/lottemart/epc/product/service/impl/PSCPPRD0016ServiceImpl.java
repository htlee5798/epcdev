package com.lottemart.epc.product.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0016Dao;
import com.lottemart.epc.product.model.PSCPPRD0016VO;
import com.lottemart.epc.product.service.PSCPPRD0016Service;

/**
 * @Class Name : PSCPPRD0016ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscpprd0016Service")
public class PSCPPRD0016ServiceImpl implements PSCPPRD0016Service
{
	@Autowired
	private PSCPPRD0016Dao pscpprd0016Dao;

	/**
	 * 증정품 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdPresentList(Map<String, String> paramMap) throws Exception
	{
		return pscpprd0016Dao.selectPrdPresentList(paramMap);
	}

	/**
	 * 증정품 수정 처리
	 * @param List<VO>
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdPresentList(List<PSCPPRD0016VO> pscpprd0016VOList, String mode) throws Exception
	{
		int resultCnt = 0;
		String ICON_6 = "";

		try
		{
			PSCPPRD0016VO ListBean = null;
			PSCPPRD0016VO bean = null;
			int listSize = pscpprd0016VOList.size();

			// 채크된 항목에 대해 개별 증정품 처리
			for (int i = 0; i < listSize; i++)
			{
				ListBean = (PSCPPRD0016VO)pscpprd0016VOList.get(i);
				bean = new PSCPPRD0016VO();

				bean.setPrest(ListBean.getPrest());
				bean.setPrestType(ListBean.getPrestType());
				bean.setPrestStartDy(ListBean.getPrestStartDy());
				bean.setPrestEndDy(ListBean.getPrestEndDy());
				bean.setStrCd(ListBean.getStrCd());
				bean.setProdCd(ListBean.getProdCd());
				bean.setStrNm(ListBean.getStrNm());
				bean.setRegId(ListBean.getRegId());

				if ("update".equals(mode))
				{
					Map<String, String> paramMap = new HashMap<String, String>();

					paramMap.put("prodCd", bean.getProdCd());
					paramMap.put("strCd", bean.getStrCd());

					int check = pscpprd0016Dao.selectPrdPresentChek(paramMap);

					if(check > 0){
						// 증정품 업데이트 - 해당 값들을 업데이트한다
						resultCnt = pscpprd0016Dao.updatePrdPresent(bean);
					}else{
						// 증정품 등록 - 해당 값들을 등록한다
						resultCnt = pscpprd0016Dao.insertPrdPresent(bean);
					}

					if (resultCnt <= 0)
					{
						throw new TopLevelException("증정품 수정 작업중에 오류가 발생하였습니다. 채크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
					}

					ICON_6 = "1";
				}
				else
				{
					if(i == 0){
						// 증정품 삭제 - 실제로는 해당 값들을 null 로 업데이트한다
						//resultCnt = pscpprd0016Dao.updatePrdPresentNull(bean);
						resultCnt = pscpprd0016Dao.deletePrdPresent(bean);

						if (resultCnt <= 0)
						{
							throw new TopLevelException("증정품 삭제 작업중에 오류가 발생하였습니다. 채크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
						}

						ICON_6 = "0";
					}
				}

				// 증정품 아이콘 업데이트 - 증정품의 사용 여부에 따라 icon DP 여부를 변경한다
				bean.setIcon6(ICON_6);
				resultCnt = pscpprd0016Dao.updatePrdPresentIcon(bean);

				if (resultCnt <= 0)
				{
					throw new IllegalArgumentException("증정품 아이콘 수정 작업중에 오류가 발생하였습니다. 채크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
				}

			}

		}
		catch (Exception e)
		{
			throw e;
		}

		return resultCnt;
	}

	/**
	 * Desc : 증정품 수정 처리
	 * @Method Name : updatePrdPresentList
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updatePrdPresentList(HttpServletRequest request) throws Exception {

		int resultCnt = 0;
		String ICON_6 = "";

		DataMap param = new DataMap(request);
		String mode			= param.getString("mode");
		String prodCd		= param.getString("prodCd");
		String prest			= param.getString("prest");
		String prestStartDy	= param.getString("prestStartDy");
		String prestEndDy	= param.getString("prestEndDy");
		String prestType	= param.getString("prestType");
		String vendorId 		= param.getString("vendorId");

		String[] strCds		= request.getParameterValues("STRCD");

		for (int i=0; i<strCds.length; i++) {
			param.put("strCd", strCds[i]);

			PSCPPRD0016VO bean = new PSCPPRD0016VO();

			bean.setPrest(prest);
			bean.setPrestType(prestType);
			bean.setPrestStartDy(prestStartDy);
			bean.setPrestEndDy(prestEndDy);
			bean.setStrCd(strCds[i]);
			bean.setProdCd(prodCd);
			bean.setRegId(vendorId);

			if ("update".equals(mode)) { // 등록/수정
				int check = pscpprd0016Dao.selectPrdPresentChek((Map)param);
				if (check > 0){
					// 증정품 UPDATE - 해당 값들을 업데이트한다
					resultCnt = pscpprd0016Dao.updatePrdPresent(bean);
				} else {
					// 증정품 INSERT - 해당 값들을 등록한다
					resultCnt = pscpprd0016Dao.insertPrdPresent(bean);
				}

				if (resultCnt <= 0) {
					throw new TopLevelException("증정품 수정 작업중에 오류가 발생하였습니다. \n체크된 항목중 "+(i+1)+"번째 항목을 확인하세요.");
				}
				ICON_6 = "1";// Y
			} else { // 삭제
				resultCnt = pscpprd0016Dao.deletePrdPresent(bean);
				if (resultCnt <= 0) {
					throw new TopLevelException("증정품 삭제 작업중 오류가 발생하였습니다. \n체크된 항목중 "+(i+1)+"번째 항목을 확인하세요.");
				}
				ICON_6 = "0";// N
			}

			// 증정품 아이콘 업데이트 - 증정품의 사용 여부에 따라 icon DP 여부를 변경한다);
			bean.setIcon6(ICON_6);
			resultCnt = pscpprd0016Dao.updatePrdPresentIcon(bean);
			if (resultCnt <= 0) {
				throw new TopLevelException("증정품 아이콘 수정 작업중에 오류가 발생하였습니다. \n체크된 항목중 "+(i+1)+"번째 항목을 확인하세요.");
			}
		}
		return resultCnt;
	}
}
