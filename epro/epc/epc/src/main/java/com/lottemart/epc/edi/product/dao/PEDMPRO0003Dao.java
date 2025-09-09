package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;



import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.EcomAddInfo;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.SearchProduct;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : PEDMPRO0003Dao.java
 * @Description : 임시 보관함 관련 dao
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 03. 오후 2:42:02 kks
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository
public class PEDMPRO0003Dao extends AbstractDAO {

	/**
	 * 상품테이블(TED_NEW_PROD_REG)에 NEW_PROD_CD칼럼(varchar 11)에 사용될 시퀀스 값 조회. 
	 * @Method Name : selectNewProductInfoInTemp
	 * @param newProductCode
	 * @return NewProduct
	 * @throws DataAccessException
	 */
	public NewProduct selectNewProductInfoInTemp(String newProductCode)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (NewProduct) getSqlMapClientTemplate().queryForObject("PEDMPRO0003.selectNewProductInfoInTemp", newProductCode);
	}

	/**
	 * 구 EDI 임시보관함 & 신규상품등록현황 조회 에서 상품상세정보 조회
	 * @param newProductCode
	 * @return
	 * @throws DataAccessException
	 */
	public NewProduct selectNewProductInfoInTempOld(String newProductCode)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (NewProduct) getSqlMapClientTemplate().queryForObject("PEDMPRO0003.selectNewProductInfoInTempOld", newProductCode);
	}

	/**
	 * 임시 보관된 로그인 한 업체의 협력사 등록 상품 조회 
	 * @Method Name : selectNewProductInfoInTemp
	 * @param SearchProduct
	 * @return List<NewProduct>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<NewProduct> selectNewProductListInTemp(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductListInTemp", searchParam);
	}

	/**
	 * 온오프겸용 상품 정보 수정
	 * @Method Name : updateOnoffNewProductInfoInTemp
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void updateOnoffNewProductInfoInTemp(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("PEDMPRO0003.updateOnoffNewProductInfoInTemp", newProductInTemp);
	}

	/**
	 * 온라인 전용, 소셜 상품 정보 수정
	 * @Method Name : updateOnlineSocialNewProductInfoInTemp
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void updateOnlineSocialNewProductInfoInTemp(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("PEDMPRO0003.updateOnlineSocialNewProductInfoInTemp", newProductInTemp);
	}
	/**
	 * 상품 상세 테이블(TED_NEW_PROD_DESCR) 상세 정보(편집기를 통해 입력된 상품 상세정보)  수정
	 * @Method Name : updateNewProductDescriptionInTemp
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void updateNewProductDescriptionInTemp(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("PEDMPRO0003.updateProductDescription", newProductInTemp);
	}
	
	/**
	 * MD 테이블( FASH_COLOR_SZ_EDI@DL_MD_MARTNIS ) 에 패션 상품의 색상 정보 저장(insert)
	 * @Method Name : insertNewProductImageData
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertNewProductImageData(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewFashionProductColorSize", newProductInTemp);
	}

	/**
	 * MD 테이블(  SALE_MST_IMG@DL_MD_MARTNIS ) 에 패션 상품의 색상 정보 저장(insert)
	 * @Method Name : insertNewProductChangedImageData
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertNewProductChangedImageData(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewFashionProductImage", newProductInTemp);
	}

	/**
	 * MD 테이블에 온오프 상품 정보 저장(insert)
	 * @Method Name : fixSubmitNewProductInTemp
	 * @param NewProduct
	 * @param productType
	 * @return void
	 * @throws DataAccessException
	 */
	public void fixSubmitNewProductInTemp(String productCode, String productType, List fileList)  throws Exception  {
		// TODO Auto-generated method stub
		
		//officialOrder.recomendedAmountDivnCode 장려금구분
		String colorSizeImages="";
		String productImageId = "";
		String trdTypFg = "";
	
		
		if(productType.equals(Constants.ONLINE_OFFLINE_PRODUCT_CD)) {
			NewProduct tmpNewProduct = selectNewProductInfoInTemp(productCode);
			
			
			trdTypFg = tmpNewProduct.getTradeTypeCode();
			
			
			
			/*협력사 별 장려금 구분코드 정의 (NEW_PROD_EDI@DL_MD_MARTNIS.PROMO_AMT_FG)------*/
			HashBox hpromo = (HashBox)getSqlMapClientTemplate().queryForObject("PEDMPRO0003.selectPromoAmtRtStatus", tmpNewProduct.getEntpCode());
			String promo_amt_str = (String)hpromo.get("PROMO_AMT_RT");
			
			/* TRD_TYP_FG =0  or 상품유형이 PB일때 장려금 미적용 =2*/
			if("0".equals(promo_amt_str)  ||  "2".equals(tmpNewProduct.getProductTypeDivnCode()) )
				tmpNewProduct.setPromoAmtStr("0");  //officialOrder.recomendedAmountDivnCode
			else tmpNewProduct.setPromoAmtStr("1");
			/*----------------------------------------------------------------------*/
			
			
			if(StringUtils.isEmpty(tmpNewProduct.getProfitRate())) {
				tmpNewProduct.setProfitRate("0");
			}
			
			if(StringUtils.isEmpty(tmpNewProduct.getVicprofitRate())) {
				tmpNewProduct.setVicprofitRate("0");
			}
			tmpNewProduct.setProfitRate(SecureUtil.sqlValid(tmpNewProduct.getProfitRate()));
			tmpNewProduct.setProductHorizontalLength(SecureUtil.sqlValid(tmpNewProduct.getProductHorizontalLength()));
			tmpNewProduct.setProductVerticalLength(SecureUtil.sqlValid(tmpNewProduct.getProductVerticalLength()));
			tmpNewProduct.setProductHeight(SecureUtil.sqlValid(tmpNewProduct.getProductHeight()));
			tmpNewProduct.setVicprofitRate(SecureUtil.sqlValid(tmpNewProduct.getVicprofitRate()));
			tmpNewProduct.getOfficialOrder().setNewProductFirstPublishedDivnCode(SecureUtil.sqlValid(tmpNewProduct.getOfficialOrder().getNewProductFirstPublishedDivnCode()));
			tmpNewProduct.getOfficialOrder().setMixYn(SecureUtil.sqlValid(tmpNewProduct.getOfficialOrder().getMixYn()));
			//tmpNewProduct.setProductDescription(MimeUtil.getHTMLCode(tmpNewProduct.getProductDescription()));
			
			/*쇼카드명 서브스트링 제거*/
			//tmpNewProduct.setProductShortName(StringUtils.substring(tmpNewProduct.getProductShortName(), 0, 6));
			
			getSqlMapClientTemplate().delete("PEDMPRO0003.deleteFixedNewProductinTemp", tmpNewProduct);
			
			
			//온오프일때 거래형태가 1.직매입일경우 A 가등록심사중으로 넣는다.
			 if("1".equals(trdTypFg)) {
				 getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewProductinTempA", tmpNewProduct);
			 	}
			 else{
				 getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewProductinTemp", tmpNewProduct);
			 	}
			
			//getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewProductinTemp", tmpNewProduct);
			
			productImageId = tmpNewProduct.getProductImageId();
			
			if(Constants.FASHION_PRODUCT_CD.equals(tmpNewProduct.getProductDivnCode())) {

				//상품속성 :  패션(5)
				getSqlMapClientTemplate().delete("PEDMPRO0003.deleteFixedNewFashionProductColorSize", tmpNewProduct);
				getSqlMapClientTemplate().delete("PEDMPRO0003.deleteFixedNewFashionProductImage", tmpNewProduct);
				
				List<ColorSize> fixProductColorSizeList = selectProductColorListInTemp(tmpNewProduct.getNewProductCode());
				for(ColorSize colorSizeMD : fixProductColorSizeList) {
					colorSizeMD.setEnterpriseCode(tmpNewProduct.getEntpCode());
					colorSizeMD.setProductName(tmpNewProduct.getProductName());
					colorSizeMD.setNewProductCode(tmpNewProduct.getNewProductCode());

					
					getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewFashionProductColorSize", colorSizeMD);
				
					//tmpNewProduct.setNewProductCode(tmpNewProduct.getNewProductCode().substring(0,19));
					//tmpNewProduct.setProductImageId(tmpNewProduct.getNewProductCode().substring(0,14));
					tmpNewProduct.setSellCode(colorSizeMD.getSellCode());
					
					
					/*2012.01.02 test md 시스템에서는 키값이 아니어서 오류가 없었느아  운영md에서는 키값임 필수로 입력받아야함.*/
					tmpNewProduct.setColorCodeCd(colorSizeMD.getColorCode());					// COLOR_CD
					tmpNewProduct.setSizeCategoryCodeCd(colorSizeMD.getSizeCategoryCode());  	// SZ_CAT_CD
					tmpNewProduct.setSizeCodeCd(colorSizeMD.getSizeCode());						// SZ_CD
					
					
					colorSizeImages = productImageId+"0"+colorSizeMD.getColorCode();
					
					if(!getFileDataCheck(colorSizeImages, fileList))
					{
						tmpNewProduct.setProductImageId("");				// IMG_NM
						tmpNewProduct.setImageConfirmFlag("");				// 이미지등록여부
					}
					else
					{
						tmpNewProduct.setProductImageId(colorSizeImages);	// IMG_NM
						tmpNewProduct.setImageConfirmFlag("0");				// 이미지등록여부
					}
					
				
					
					getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewFashionProductImage", tmpNewProduct);
					
				}
			}
			else 
			{
				// 상품속성 : 규격(1)  규격상품일경우 POG IMG 가없을경우 
				// SALE_MST_IMG@DL_MD_MARTNIS.IMG_NM null 처리해서 저장한다.
				tmpNewProduct.setColorCodeCd("");					// COLOR_CD   (규격일때는 DEFAULT 값으로 들어가도한다.
				tmpNewProduct.setSizeCategoryCodeCd("");  			// SZ_CAT_CD  (규격일때는 DEFAULT 값으로 들어가도한다.
				tmpNewProduct.setSizeCodeCd("");					// SZ_CD      (규격일때는 DEFAULT 값으로 들어가도한다.
				
				if(fileList == null || fileList.size() <= 0  ) {
					// IMG_CFM_FG // null 처리
					tmpNewProduct.setProductImageId("");				// IMG_NM
					tmpNewProduct.setImageConfirmFlag("");				// 이미지등록여부
				}
				else tmpNewProduct.setImageConfirmFlag("0");				// 이미지등록여부
				
				getSqlMapClientTemplate().insert("PEDMPRO0003.fixedNewFashionProductImage", tmpNewProduct);
			}
		}
	//	getSqlMapClientTemplate().update("PEDMPRO0003.updateNewProductMDSendStatus", productCode);
		
		
		
		
//		System.out.println("trdTypFg:"+trdTypFg); 
		//온오프상품이면서 거래형태가 직매입일경우만 md_send_fg= A(가등록심사중)로 입력
		 if(productType.equals(Constants.ONLINE_OFFLINE_PRODUCT_CD) && "1".equals(trdTypFg)) {
			 //온오프상품
			 getSqlMapClientTemplate().update("PEDMPRO0003.updateNewProductMDSendStatusA", productCode);
			 }
			 	else 
			{
			 //온라인전용 외 기타
			getSqlMapClientTemplate().update("PEDMPRO0003.updateNewProductMDSendStatus", productCode);						
			}
		 
	}
	
	
	
	private boolean getFileDataCheck(String colorSizeImages, List fileList) {
		
		
		String fileNames = "";
		String fileIDs = "";
		if(fileList == null || fileList.size() <=0) return false;
		for(int i=0; fileList.size() > i; i++)
		{
			fileNames = (String)fileList.get(i);
			fileIDs = fileNames.substring(0, fileNames.indexOf("."));
			
			if(fileIDs.equals(colorSizeImages)) return true;
		}
		
		return false;
	}

	/**
	 * Desc : 사용자가 입력한 색상/사이즈 값 조회. 색상 값 중복 가능
	 * @Method Name : selectProductColorListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */
	@SuppressWarnings("unchecked")
	public List<ColorSize> selectProductColorListInTemp(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductColorListInTemp", newProductCode);
	}
	
	@SuppressWarnings("unchecked")
	public List<ColorSize> selectProductColorListInTempOld(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductColorListInTemp", newProductCode);
	}
	
	/**

	 */
	@SuppressWarnings("unchecked")
	public List<EcomAddInfo> selectProductEcomAddInfoListInTemp(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<EcomAddInfo>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductEcomAddInfoListInTemp", newProductCode);
	}
	
	/**
	 * 패션상품 컬러사이즈 정보 조회
	 * @Method Name : sellCodeView
	 * @param SearchProduct
	 * @return List<NewProduct>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ColorSize> sellCodeView(Map<String,Object> map) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectColoSizeView", map);
	}
	
	public List selctPogList() throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.SELECT_POG_LIST");
	}
	public void updatePogList(HashBox hData) throws Exception{
		 getSqlMapClientTemplate().update("PEDMPRO0003.UPDATE_POG_LIST", hData);
	}
	
	/**
	 * Desc : 온라인 전용 상품 단품 리스트
	 * @Method Name : selectProductItemListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */
	@SuppressWarnings("unchecked")
	public List<ColorSize> selectProductItemListInTemp(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductItemListInTemp", newProductCode);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ColorSize> selectProductItemListInTempOld(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO0003.selectProductItemListInTempOld", newProductCode);
	}
	
	/**
	 * 상품 상세 테이블(TPC_NEW_PROD_DESCR) 상세 정보(편집기를 통해 입력된 상품 상세정보)  수정
	 * @Method Name : updateTitleProductDescription
	 * @param NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void updateTitleProductDescription(NewProduct newProductInTemp) throws DataAccessException  {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("PEDMPRO0003.updateTitleProductDescription", newProductInTemp);
	}
	/**
	 * 온라인가등록상품 승인여부 체크
	 * @Method Name : selectMdSendDivnCdCheck
	 * @param pgmID
	 * @return int
	 * @throws DataAccessException
	 */
	public int selectMdSendDivnCdCheck(String pgmId) throws Exception {
		Integer resultCnt = new Integer(0);
		resultCnt = (Integer) getSqlMapClientTemplate().queryForObject( "PEDMPRO0003.selectMdSendDivnCdCheck", pgmId);
		return resultCnt;
	}
}
