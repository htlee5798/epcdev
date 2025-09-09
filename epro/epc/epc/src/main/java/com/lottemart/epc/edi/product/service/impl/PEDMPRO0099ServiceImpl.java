package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.product.dao.PEDMPRO0099Dao;
import com.lottemart.epc.edi.product.model.EdiCdListCodeVO;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0095VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0096VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0097VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0098VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0099VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0099Service;

/**
 * @Class Name : PEDMPRO0099ServiceImpl
 * @Description : 기존상품 수정 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자           	수정내용
 *  -------    --------    ---------------------------
 * 2015.11.10	SONG MIN KYO	최초 생성
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("pEDMPRO0099Service")
public class PEDMPRO0099ServiceImpl implements PEDMPRO0099Service {

	@Resource(name="pEDMPRO0099Dao")
	private PEDMPRO0099Dao pEDMPRO0099Dao;

	/**
	 * List count
	 */
	public Integer selectTotalProductCount(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectTotalProductCount(searchParam);	2016.03.07 이전 버전

		//-----2016.03.07 이후버전 (쿼리에서 count가 속도가 늦어 리스트 형태로 가지고 와서 리스트의 size를 return)
		//List<PEDMPRO0099VO>	resultList	=	pEDMPRO0099Dao.selectTotalProductCount_3(searchParam);
		//return resultList.size();

		//-----2015.03.10 이후 버전
		return pEDMPRO0099Dao.selectTotalProductCount_4(searchParam);
	}

	/**
	 * List
	 */
	public List<PEDMPRO0099VO> selectWholeProductList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectWholeProductList(searchProduct);	//2016.03.07이전 버전
		//return pEDMPRO0099Dao.selectWholeProductList_3(searchProduct);	//2016.03.07이후 버전

		//-----2016.03.10 이후 버전
		return pEDMPRO0099Dao.selectNewWholeProductList_4(searchProduct);
	}

	/**
	 * 기존상품에 입력된 팀, 대분류, 세분류 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public PEDMPRO0098VO selectNewWholeProductOldTeamInfo (PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectNewWholeProductOldTeamInfo(pEDMPRO0098VO);
	}


	/**
	 * 새로 입력된 속성 List
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0097VO> selectNewWholeProductAttrList(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectNewWholeProductAttrList(pEDMPRO0098VO);
	}

	/**
	 * 새로입력될 속성의 타입이 콤보일경우  콤보박스 구성
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0096VO> selectNewWholeProductAttComboList(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectNewWholeProductAttComboList(pEDMPRO0098VO);
	}

	/**
	 * 그룹분석속성 삭제
	 */
	public Map<String, Object> deleteGroupAttr(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//삭제전 완료 여부 체크
		int completCnt	=	pEDMPRO0099Dao.selectProductCompleChk(pEDMPRO0096VO);


		if (completCnt > 0) {
			resultMap.put("msg", "COMPLETE_CNT");
			return resultMap;
		}

		int delCnt = pEDMPRO0099Dao.selectDelWholeProductAttCnt(pEDMPRO0096VO);

		if (delCnt > 0) {

			/*//해당상품의 판매코드 리스트 조회
			List<PEDMPRO0099VO>	sellCdList	=	pEDMPRO0099Dao.selectSrcmkCdList(pEDMPRO0096VO.getNewProductCode());

			for (int i = 0; i < sellCdList.size(); i++) {

				pEDMPRO0096VO.setSellCode(sellCdList.get(i).getSellCode());
				//삭제
				pEDMPRO0099Dao.deletetWholeProductAtt(pEDMPRO0096VO);
			}*/

			//삭제
			pEDMPRO0099Dao.deletetWholeProductAtt(pEDMPRO0096VO);


			//완료 되셤 SUCCESS
			resultMap.put("msg", "SUCCESS");
		} else {
			resultMap.put("msg", "NO_DATA");
		}

		return resultMap;
	}


	/**
	 * 그룹분석속성 저장
	 * @param pEDMPRO0096VO
	 * @return
	 */
	public Map<String, Object> insertWholeProductAtt(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {
		Map<String, Object>	resultMap		=	new HashMap<String, Object>();
		PEDMPRO0098VO 		pEDMPRO0098VO 	=	new PEDMPRO0098VO();

		resultMap.put("msg", "FAIL");

		//저장전 완료 여부 체크
		int completCnt	=	pEDMPRO0099Dao.selectProductCompleChk(pEDMPRO0096VO);

		//상품이 완료 되었으면 수정이 불가능하다.
		if (completCnt > 0) {
			resultMap.put("msg", "COMPLETE_CNT");
			return resultMap;
		}

		//해당상품의 판매코드 리스트 조회
		/*List<PEDMPRO0099VO>	sellCdList	=	pEDMPRO0099Dao.selectSrcmkCdList(pEDMPRO0096VO.getSellCode());

		//저장전 삭제
		for (int i = 0; i < sellCdList.size(); i++) {
			pEDMPRO0096VO.setSellCode(sellCdList.get(i).getSellCode());
			pEDMPRO0099Dao.deletetWholeProductAtt(pEDMPRO0096VO);
		}*/

		//저장전 삭제
		pEDMPRO0099Dao.deletetWholeProductAtt(pEDMPRO0096VO);

		//배열로 넘어온 Parameter
		String[] arrGrpAttr		=	pEDMPRO0096VO.getArrGrpAttr();		// 그룹분석속성 붑류코드
		String[] arrGrpAttrTyp	=	pEDMPRO0096VO.getArrGrpAttrTyp();	// 그룹분석속성 붑류코드 타입
		String[] arrVal			=	pEDMPRO0096VO.getArrVal();			// 그룹분석속성 붑류값

		for (int x = 0; x < arrGrpAttr.length; x++) {

			// 그룹속성분류코드 값이 있을경우에만 insert 한다.
			if (!StringUtils.trimToEmpty(arrVal[x]).equals("")) {
				pEDMPRO0096VO.setSellCode(pEDMPRO0096VO.getSellCode());
				pEDMPRO0096VO.setSapL3Cd(pEDMPRO0096VO.getSapL3Cd());
				pEDMPRO0096VO.setAttId(StringUtils.trimToEmpty(arrGrpAttr[x]));
				pEDMPRO0096VO.setAttValId(StringUtils.trimToEmpty(arrVal[x]));
				pEDMPRO0096VO.setAttIdTyp(StringUtils.trimToEmpty(arrGrpAttrTyp[x]));

				//저장
				pEDMPRO0099Dao.insertWholeProductAtt(pEDMPRO0096VO);
			}
		}


		/*//해당 상품의 매핑된 sap 소분류 리스트
		pEDMPRO0098VO.setSrchL4Cd(StringUtils.trimToEmpty(pEDMPRO0096VO.getSrchL4Cd()));
		List<PEDMPRO0099VO> sapL3List	=	pEDMPRO0099Dao.selectSapMapAttrList(pEDMPRO0098VO);



		for (int idx = 0; idx < sellCdList.size(); idx++) {
			//해당상품의 매핑된 sqp 소분류 리스트 돌면서 insert
			if (sapL3List.size() > 0) {

				for (int i = 0; i < sapL3List.size(); i++) {

					for (int x = 0; x < arrGrpAttr.length; x++) {

						// 그룹속성분류코드 값이 있을경우에만 insert 한다.
						if (!StringUtils.trimToEmpty(arrVal[x]).equals("")) {
							pEDMPRO0096VO.setSellCode(sellCdList.get(idx).getSellCode());
							pEDMPRO0096VO.setSapL3Cd(sapL3List.get(i).getSapL3Cd());
							pEDMPRO0096VO.setAttId(StringUtils.trimToEmpty(arrGrpAttr[x]));
							pEDMPRO0096VO.setAttValId(StringUtils.trimToEmpty(arrVal[x]));
							pEDMPRO0096VO.setAttIdTyp(StringUtils.trimToEmpty(arrGrpAttrTyp[x]));

							//저장
							pEDMPRO0099Dao.insertWholeProductAtt(pEDMPRO0096VO);
						}
					}

				}

			}
		}*/


		//완료 되셤 SUCCESS
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	/**
	 * 입력된 그룹속성분류코드값 리스트 조회
	 * @param pEDMPRO0095VO
	 * @return
	 */
	public List<PEDMPRO0095VO> selectGrpAttrValList(PEDMPRO0098VO pEDMPRO0098VO, HttpServletRequest request) {
		return pEDMPRO0099Dao.selectGrpAttrValList(pEDMPRO0098VO);
	}

	/**
	 * 기존 세분류에 매핑되어 있는 속성 카운트
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public Integer selectWholeGrpArrCnt(PEDMPRO0098VO pEDMPRO0098VO){
		return pEDMPRO0099Dao.selectWholeGrpArrCnt(pEDMPRO0098VO);
	}

	/**
	 * 기존 세분류에 매핑되어 있는 SAP 소분류 콤보리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0099VO> selectSapMapAttrList(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectSapMapAttrList(pEDMPRO0098VO);
	}

	/**
	 * 단위규격 콤보리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0095VO> selectkyekeokList(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectkyekeokList(pEDMPRO0098VO);
	}

	/**
	 * 상품 분석속성관리(일괄) 리스트 카운트
	 */
	public Integer selectNewWholeProductCountBatch(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectNewWholeProductCountBatch(searchParam); 2016.03.07 이전버전

		//----- 2016.03.07 이후 버전
		//List<PEDMPRO0099VO> resultList	=	pEDMPRO0099Dao.selectNewWholeProductCountBatch_3(searchParam);
		//return resultList.size();

		//----- 2016.03.10 이후 버전
		return pEDMPRO0099Dao.selectNewWholeProductCountBatch_4(searchParam);
	}

	/**
	 * 상품 분석속성관리(일괄) 리스트
	 * @param searchProduct
	 * @return
	 */
	public List<PEDMPRO0099VO> selectNewWholeProductListBatch(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectNewWholeProductListBatch(searchProduct); 2016.03.07 이전 버전
		//return pEDMPRO0099Dao.selectNewWholeProductListBatch_3(searchProduct); //2016.03.07 이후 버전

		//-----2016.03.10이후 버전
		return pEDMPRO0099Dao.selectNewWholeProductListBatch_4(searchProduct); //2016.03.07 이후 버전
	}

	/**
	 * 팀에 해당하는 대분류 콤보박스 리스트 구성
	 */
	public List<EdiCommonCode> selectWholeProductAttrBatchTeamCombo(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return pEDMPRO0099Dao.selectWholeProductAttrBatchTeamCombo(searchProduct);
	}

	/**
	 * 대분류에 해당하는 세분류 콤보리스트 구성
	 */
	public List<EdiCommonCode> selectProductBatchL4CdList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return pEDMPRO0099Dao.selectProductBatchL4CdList(searchProduct);
	}

	/**
	 * 상품속성관리 (일괄) 저장
	 */
	public Map<String, Object> insertWholeProductAttBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {
		PEDMPRO0098VO 		pEDMPRO0098VO 	=	new PEDMPRO0098VO();
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");


		//해당 상품의 매핑된 sap 소분류 리스트
		pEDMPRO0098VO.setSrchL4Cd(StringUtils.trimToEmpty(pEDMPRO0096VO.getSrchL4Cd()));
		pEDMPRO0098VO.setL1Cd(StringUtils.trimToEmpty(pEDMPRO0096VO.getL1Cd()));
		List<PEDMPRO0099VO> sapL3List	=	pEDMPRO0099Dao.selectSapMapAttrList(pEDMPRO0098VO);


		//배열로 넘어온 Parameter
		String[] arrProdCd		=	pEDMPRO0096VO.getArrProdCd();		// 선택된 상품코드
		String[] arrSrcmkCd		=	pEDMPRO0096VO.getArrSrcmkCd();		// 선택된 판매코드
		String[] arrSapL3Cd		=	pEDMPRO0096VO.getArrSapL3Cd();		// 선택된 SAP 소분류 코드
		String[] arrGrpAttr		=	pEDMPRO0096VO.getArrGrpAttr();		// 그룹분석속성 붑류코드
		String[] arrGrpAttrTyp	=	pEDMPRO0096VO.getArrGrpAttrTyp();	// 그룹분석속성 붑류코드 타입
		String[] arrVal			=	pEDMPRO0096VO.getArrVal();			// 그룹분석속성 붑류값
		String[] arrL4Cd		=	pEDMPRO0096VO.getArrL4Cd();			// 선택된 이전 L4Cd


		String[] delArr = new String[arrVal.length];
		for (int i = 0 ;i < arrVal.length; i++) {
			if (!StringUtils.trimToEmpty(arrVal[i]).equals("")) {
				delArr[i]	=	arrGrpAttr[i];
			} else {
				delArr[i]	=	"";
			}
		}


		//저장 전 삭제
		for (int i = 0; i < arrProdCd.length; i++) {

			//해당상품의 판매코드 조회
			/*List<PEDMPRO0099VO>	sellCdList	=	pEDMPRO0099Dao.selectSrcmkCdList(arrSrcmkCd[i]);

			for (int j = 0; j < sellCdList.size(); j++) {

				for (int x = 0; x < delArr.length; x++) {

					if (!delArr[x].equals("")) {
						pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
						pEDMPRO0096VO.setSellCode(sellCdList.get(j).getSellCode());
						pEDMPRO0096VO.setAttId(delArr[x]);

						pEDMPRO0099Dao.deletetWholeProductAttBatch(pEDMPRO0096VO);
					}

				}
			}*/

			for (int x = 0; x < delArr.length; x++) {

				if (!delArr[x].equals("")) {
					pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
					pEDMPRO0096VO.setSellCode(arrSrcmkCd[i]);
					pEDMPRO0096VO.setL4Cd(arrL4Cd[i]);
					pEDMPRO0096VO.setAttId(delArr[x]);

					pEDMPRO0099Dao.deletetWholeProductAttBatch(pEDMPRO0096VO);
				}

			}

		}

		//pEDMPRO0096VO.setDelGrpAttr(delArr);
		//pEDMPRO0099Dao.deletetWholeProductAttBatch(pEDMPRO0096VO);


		// 선택된 상품코드들이 있고
		if (arrProdCd.length > 0) {

			//선택된 상품들의 length 만큼 돌면서
			for (int i = 0; i < arrProdCd.length; i++) {

				/*//해당상품의 판매코드 조회
				List<PEDMPRO0099VO>	sellCdList	=	pEDMPRO0099Dao.selectSrcmkCdList(arrProdCd[i]);

				for (int idx = 0; idx < sellCdList.size(); idx++) {

					// 해당 세분류의 sap 소분류 리스트가 있으면
					if (sapL3List.size() > 0) {

						// sap 소분류 리스트 만큼 돌면서
						for (int x = 0; x < sapL3List.size(); x++) {

							// 입력된 그룹분석속성 length 만큼 돌면서
							for (int z = 0; z < arrGrpAttr.length; z++) {

								// 그룹속성분류코드 값이 있을경우에만 insert 한다.
								if (!StringUtils.trimToEmpty(arrVal[z]).equals("")) {
									pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
									pEDMPRO0096VO.setSellCode(sellCdList.get(idx).getSellCode());
									pEDMPRO0096VO.setSapL3Cd(sapL3List.get(x).getSapL3Cd());
									pEDMPRO0096VO.setAttId(StringUtils.trimToEmpty(arrGrpAttr[z]));
									pEDMPRO0096VO.setAttValId(StringUtils.trimToEmpty(arrVal[z]));
									pEDMPRO0096VO.setAttIdTyp(StringUtils.trimToEmpty(arrGrpAttrTyp[z]));

									//저장
									pEDMPRO0099Dao.insertWholeProductAtt(pEDMPRO0096VO);
								}

							}

						}
					}

				}*/


				// 입력된 그룹분석속성 length 만큼 돌면서
				for (int z = 0; z < arrGrpAttr.length; z++) {

					// 그룹속성분류코드 값이 있을경우에만 insert 한다.
					if (!StringUtils.trimToEmpty(arrVal[z]).equals("")) {
						pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
						pEDMPRO0096VO.setSellCode(arrSrcmkCd[i]);
						pEDMPRO0096VO.setSapL3Cd(arrSapL3Cd[i]);
						pEDMPRO0096VO.setL4Cd(arrL4Cd[i]);
						pEDMPRO0096VO.setAttId(StringUtils.trimToEmpty(arrGrpAttr[z]));
						pEDMPRO0096VO.setAttValId(StringUtils.trimToEmpty(arrVal[z]));
						pEDMPRO0096VO.setAttIdTyp(StringUtils.trimToEmpty(arrGrpAttrTyp[z]));

						//저장
						pEDMPRO0099Dao.insertWholeProductAtt(pEDMPRO0096VO);
					}

				}

			}
		}

		//완료 되셤 SUCCESS
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	/**
	 * 상품속성관리 (일괄) 삭제
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> delWholeProductAttBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//배열로 넘어온 Parameter
		String[] arrProdCd		=	pEDMPRO0096VO.getArrProdCd();		// 선택된 상품코드
		String[] arrSrcmkCd		=	pEDMPRO0096VO.getArrSrcmkCd();		// 선택된 판매코드
		String[] arrL4Cd		=	pEDMPRO0096VO.getArrL4Cd();		// 선택된 판매코드

		//저장 전 삭제
		for (int i = 0; i < arrProdCd.length; i++) {

			/*//해당상품의 판매코드 조회
			List<PEDMPRO0099VO>	sellCdList	=	pEDMPRO0099Dao.selectSrcmkCdList(arrProdCd[i]);

			for (int j = 0; j < sellCdList.size(); j++) {

				pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
				pEDMPRO0096VO.setSellCode(sellCdList.get(j).getSellCode());

				pEDMPRO0099Dao.deletetWholeProductAttBatch(pEDMPRO0096VO);

			}*/

			pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);
			pEDMPRO0096VO.setSellCode(arrSrcmkCd[i]);
			pEDMPRO0096VO.setL4Cd(arrL4Cd[i]);

			pEDMPRO0099Dao.deletetWholeProductAttBatch(pEDMPRO0096VO);

		}


		//완료 되셤 SUCCESS
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}


	/**
	 * 상품속성관리(일괄) sap 소분류에 따른 그룹분석속성리스트 조회
	 */
	public List<PEDMPRO0097VO> selectNewWholeProductAttrListBatch(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectNewWholeProductAttrListBatch(pEDMPRO0098VO);
	}











	/**
	 * 분석속성관리(일괄) 에서 사용할 입력할 대상 상품들의 분석속성&마트단독속성 리스트
	 * @param spEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	public List<PEDMPRO0097VO> selectProductBatchGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO) {
		//return pEDMPRO0099Dao.selectProductBatchGrpAttrList(pEDMPRO0098VO); 2016.03.07 이전 버전
		return pEDMPRO0099Dao.selectProductBatchGrpAttrList_3(pEDMPRO0098VO); //2016.03.07 이후 버전
	}





	/**
	 * 새로 입력된 속성 List
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0097VO> selectProductGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO) {
		//return pEDMPRO0099Dao.selectProductGrpAttrList(pEDMPRO0098VO); 2016.03.07 이전 버전
		return pEDMPRO0099Dao.selectProductGrpAttrList_3(pEDMPRO0098VO); //2016.03.07 이후 버전
	}

	/**
	 * 새로 입력된 속성 List의 콤보박스 리스트
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0096VO> selectProductGrpAttrComboList(PEDMPRO0098VO pEDMPRO0098VO) {
		//return pEDMPRO0099Dao.selectProductGrpAttrComboList(pEDMPRO0098VO); 2016.03.07 이전 버전
		return pEDMPRO0099Dao.selectProductGrpAttrComboList_3(pEDMPRO0098VO); //2016.03.07 이후 버전
	}


	/**
	 * 새로 입력된 속성 List의 콤보박스 리스트 [일괄에서 사용]
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0096VO> selectProductGrpAttrComboListBatch(PEDMPRO0098VO pEDMPRO0098VO) {
		return pEDMPRO0099Dao.selectProductGrpAttrComboListBatch(pEDMPRO0098VO);
	}

	/**
	 * 상품 분석속성관리(일괄) 대분류 리스트 조회
	 */
	public List<EdiCdListCodeVO> selectProductL1CdListBatch(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectProductL1CdListBatch(searchProduct); 2016.03.07 이전 버전
		return pEDMPRO0099Dao.selectProductL1CdListBatch_3(searchProduct); //2016.03.07 이후 버전
	}

	/**
	 * 상품 분석속성관리(일괄) 세분류 리스트 조회
	 */
	public List<EdiCdListCodeVO> selectProductL4CdListBatch(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		//return pEDMPRO0099Dao.selectProductL4CdListBatch(searchProduct);	2016.03.07 이전 버전
		return pEDMPRO0099Dao.selectProductL4CdListBatch_3(searchProduct);	//2016.03.07 이후 버전
	}


	/**
	 * 상품속성관리 (일괄) 완료처리
	 */
	public Map<String, Object> updateCompleteGroupAttrBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//배열로 넘어온 Parameter
		String[] arrProdCd		=	pEDMPRO0096VO.getArrProdCd();		// 선택된 상품코드
		String[] arrSrcmkCd		=	pEDMPRO0096VO.getArrSrcmkCd();		// 선택된 판매코드

		// 선택된 상품코드들이 있고
		if (arrProdCd.length > 0) {

			//선택된 상품들의 length 만큼 돌면서
			for (int i = 0; i < arrProdCd.length; i++) {
				pEDMPRO0096VO.setNewProductCode(arrProdCd[i]);

				//저장
				pEDMPRO0099Dao.updateCompleteGroupAttrBatch(pEDMPRO0096VO);
			}
		}

		//완료 되셤 SUCCESS
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}


	/**
	 * 상품속성관리 완료, 취소 처리
	 */
	public Map<String, Object> updateCompleteGroupAttr(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request) {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//저장
		pEDMPRO0099Dao.updateCompleteGroupAttr(pEDMPRO0096VO);

		//완료 되셤 SUCCESS
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}


	/**
	 * SAP소분류에 매핑되어 있는 그룹소분류 호출
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> selectSrchGrpCd(PEDMPRO0098VO pEDMPRO0098VO){
		Map<String, Object> resultMap	=	new HashMap<String, Object>();
		List<PEDMPRO0099VO>	resultList	=	pEDMPRO0099Dao.selectSrchGrpCd(pEDMPRO0098VO);

		resultMap.put("resultList", resultList);

		return resultMap;
	}

}
