package com.lottemart.epc.edi.product.controller;


import java.util.ArrayList;
import java.util.Locale;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.AlertException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.Constants;

import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.OfficialOrder;
import com.lottemart.epc.edi.product.service.PEDMPRO0004Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;



@Controller
public class PEDMPRO0004Controller extends BaseController {



	@Resource(name = "configurationService")
	private ConfigurationService config;


	//신상품 등록 관련 서비스
	private PEDMPRO000Service ediProductService;

	//패션 일괄등록 서비스
	private PEDMPRO0004Service pEDMPRO0004Service;


	private MessageSource messageSource;


	@Autowired
	public void setEdiProductService(PEDMPRO000Service ediProductService) {
		this.ediProductService = ediProductService;
	}


	@Autowired
	public void setpEDMPRO0004Service(PEDMPRO0004Service pEDMPRO0004Service) {
		this.pEDMPRO0004Service = pEDMPRO0004Service;
	}


	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 일괄등록 안내 페이지
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edi/product/PEDMPRO0004")
    public String batchUploadPage( Model model) {
		return "edi/product/PEDMPRO0004";
	}

	/**
	 * 패션 상품 업로드 페이지
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edi/product/PEDMPRO000401")
    public String productDataUploadPage( Model model) {
		return "edi/product/PEDMPRO000401";
	}


	/**
	 * 패션 상품 color/ size 업로드 페이지
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edi/product/PEDMPRO000402")
    public String productColorSizeUploadPage( Model model) {
		return "edi/product/PEDMPRO000402";
	}

	/**
	 * 패션 상품 파일업로드
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edi/product/PEDMPRO000410")
    public String onSubmitProductDataUpload( 	HttpServletRequest request, Model model ) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
 		  MultipartFile mFile  					 = mptRequest.getFile("productData");
 		  Workbook wb 		   					 = WorkbookFactory.create(mFile.getInputStream());

 		  ArrayList<NewProduct> newProductArrayList = new ArrayList<NewProduct>();
		  Sheet sheet = wb.getSheetAt(0);
		  int rows 	  = sheet.getPhysicalNumberOfRows();
		  boolean resultFlag = true;

		  for ( int i=1; i < rows; i++) {
			  Row row = sheet.getRow(i);
			  if( row == null) {
				  continue;
			  }
				//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
				//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
				String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
				//신상품코드 생성 실패 시, exception
				if("".equals(newPgmId)) {
					throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
				}
			  
		      NewProduct tmpProduct = new NewProduct();
		      tmpProduct.setOfficialOrder(new OfficialOrder());
		      tmpProduct.setNewProductCode(newPgmId);
		      tmpProduct.setOnOffDivnCode(Constants.ONLINE_OFFLINE_PRODUCT_CD);
		      tmpProduct.setProductDivnCode(Constants.FASHION_PRODUCT_CD);


			  //1.팀코드
		      String teamCode = returnCellContent(row.getCell(0));
		      if(pEDMPRO0004Service.isNotValidTeamcode(teamCode)) {
		    	  resultFlag = false;
		    	  String teamCodeTextKR = messageSource.getMessage("text.field.team", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), teamCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

		    	  break;
		      } else {
		    	  tmpProduct.getOfficialOrder().setTeamCode(teamCode);
		      }


			  //2.대분류코드
		      String l1GroupCode = returnCellContent(row.getCell(1));
		      if(pEDMPRO0004Service.isNotValidL1Code(teamCode, l1GroupCode)) {
		    	  resultFlag = false;
		    	  String l1GroupCodeTextKR = messageSource.getMessage("text.field.l1group", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), l1GroupCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

		    	  break;
		      } else {
		    	  tmpProduct.getOfficialOrder().setlGroupCode( l1GroupCode );
		      }

			  //3.상품속성명
			  tmpProduct.setProductName(returnCellContent(row.getCell(2)));

			  //4.단축상품속성명
			  tmpProduct.setProductShortName(returnCellContent(row.getCell(3)));

			  //5.상품규격
			  tmpProduct.setProductStandardName(returnCellContent(row.getCell(4)));


			  //6.면과세코드
			  String taxDivnCode =  returnCellContent(row.getCell(5));
			  if( pEDMPRO0004Service.isNotValidCommonCode(taxDivnCode, Constants.TAX_TYPE_GROUP_CD)) {
		    	  resultFlag = false;
		    	  String taxDivnCodeTextKR = messageSource.getMessage("text.field.taxDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), taxDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.setTaxatDivCode( taxDivnCode );
			  }

			  //7.표시총량
			  tmpProduct.setDisplayTotalQuantity( Integer.parseInt(returnCellContent(row.getCell(6))) );

			  //8.표시기준
			  tmpProduct.setDisplayBaseQuantity( Integer.parseInt(returnCellContent(row.getCell(7))) );

			  //9.표시단위코드
			  String displayUnitCode = returnCellContent(row.getCell(8));
			  if( pEDMPRO0004Service.isNotValidCommonCode(displayUnitCode, Constants.DISPLAY_UNIT_GROUP_CD)) {
		    	  resultFlag = false;
		    	  String displayUnitCodeTextKR = messageSource.getMessage("text.field.displayUnitCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), displayUnitCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.setDisplayUnitCode( displayUnitCode );
			  }


			  //10.협력업체코드
			  String entpCode = returnCellContent(row.getCell(9));
			  if(pEDMPRO0004Service.isNotValidEntpcode(entpCode, epcLoginVO.getVendorId())) {
				  resultFlag = false;
				  String entpCodeTextKR = messageSource.getMessage("text.field.entpCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), entpCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

		    	  break;
			  } else {
				  tmpProduct.setEntpCode( entpCode );
			  }

			  //11.정상원가
			  tmpProduct.setNormalProductCost( returnCellContent(row.getCell(10)) );

			  //12.정상매가
			  tmpProduct.setNormalProductSalePrice( returnCellContent(row.getCell(11)) );

			  //13.정상이익률
			  String profitRate = Double.toString(row.getCell(12).getNumericCellValue());
			  tmpProduct.setProfitRate( profitRate );

			  //14.상품유형코드
			  String productTypeDivnCode = returnCellContent(row.getCell(13));
			  if( pEDMPRO0004Service.isNotValidCommonCode(productTypeDivnCode, Constants.PRODUCT_PATTERN_GROUP_CD)) {
		    	  resultFlag = false;
		    	  String productTypeDivnCodeTextKR = messageSource.getMessage("text.field.productTypeDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), productTypeDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.setProductTypeDivnCode( productTypeDivnCode );
			  }


			  //15.생산국가
			  String productContryCode = returnCellContent(row.getCell(14));
			  if( pEDMPRO0004Service.isNotValidCommonCode(productContryCode, Constants.COUNTRY_GROUP_CD)) {
		    	  resultFlag = false;
		    	  String productContryCodeTextKR = messageSource.getMessage("text.field.productContryCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), productContryCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.setProductCountryCode( productContryCode );
			  }


			  //16.센터유형코드
			  String centerTypeDivnCode = returnCellContent(row.getCell(15));
			  if( pEDMPRO0004Service.isNotValidCommonCode(centerTypeDivnCode, Constants.CENTER_GROUP_CD)) {
		    	  resultFlag = false;
		    	  String centerTypeDivnCodeTextKR = messageSource.getMessage("text.field.centerTypeDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), centerTypeDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.setCenterTypeDivnCode( centerTypeDivnCode );
			  }


			  //17.신규상품초도구분
			  String newProductFirstPublishedDivnCode =  returnCellContent(row.getCell(16));
			  if( !"0".equals(newProductFirstPublishedDivnCode) &
					  !"1".equals(newProductFirstPublishedDivnCode)  ) {
				  resultFlag = false;
		    	  String newProductFirstPublishedDivnCodeTextKR = messageSource.getMessage("text.field.newProductFirstPublishedDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), newProductFirstPublishedDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setNewProductFirstPublishedDivnCode( SecureUtil.sqlValid(newProductFirstPublishedDivnCode ));
			  }

			  //18.무발주매입
			  String noPublishedBuyPossibleDivnCode = returnCellContent(row.getCell(17));
			  if( !"0".equals(noPublishedBuyPossibleDivnCode) &
					  !"1".equals(noPublishedBuyPossibleDivnCode)  ) {
				  resultFlag = false;
		    	  String noPublishedBuyPossibleDivnCodeTextKR = messageSource.getMessage("text.field.noPublishedBuyPossibleDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), noPublishedBuyPossibleDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setNoPublishedBuyPossibleDivnCode( noPublishedBuyPossibleDivnCode );
			  }

			  //19.계절구분코드
			  String styleCode = returnCellContent(row.getCell(18));
			  if( pEDMPRO0004Service.isNotValidStyleCode(styleCode) ) {
		    	  resultFlag = false;
		    	  String centerTypeDivnCodeTextKR = messageSource.getMessage("text.field.styleCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), centerTypeDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setSeasonDivnCode( styleCode );
			  }


			  //20.도난방지코드
			  String protectTagDivnCode = returnCellContent(row.getCell(19));
			  if( pEDMPRO0004Service.isNotValidCommonCode(protectTagDivnCode, Constants.PROTECT_TAG_GROUP_CD) ) {
		    	  resultFlag = false;
		    	  String protectTagDivnCodeTextKR = messageSource.getMessage("text.field.protectTagDivnCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), protectTagDivnCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setProtectTagDivnCode( protectTagDivnCode );
			  }


			  //21.도난방지 태그유형코드
			  String protectTagTypeCode = returnCellContent(row.getCell(20));
			  if( pEDMPRO0004Service.isNotValidCommonCode(protectTagTypeCode, Constants.PROTECT_TAG_TYPE_GROUP_CD) ) {
		    	  resultFlag = false;
		    	  String protectTagTypeCodeTextKR = messageSource.getMessage("text.field.protectTagTypeCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), protectTagTypeCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setProtectTagTypeCode( protectTagTypeCode );
			  }


			  //22.발주입수
			  tmpProduct.getOfficialOrder().setPublishIncrementQuantity( Integer.parseInt( returnCellContent(row.getCell(21))  ) );

			  //23.발주단위코드
			  String publishedUnitCode = returnCellContent(row.getCell(22));
			  if( pEDMPRO0004Service.isNotValidCommonCode(publishedUnitCode, Constants.ORD_UNIT_GROUP_CD) ) {
		    	  resultFlag = false;
		    	  String publishedUnitCodeTextKR = messageSource.getMessage("text.field.publishedUnitCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), publishedUnitCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setPublishedUnitCode( publishedUnitCode );
			  }


			  //24.세분류코드
			  String subGroupCode = returnCellContent(row.getCell(23));
			  if( pEDMPRO0004Service.isNotValidSubGroupCode(teamCode, l1GroupCode, subGroupCode) ) {
		    	  resultFlag = false;
		    	  String subGroupCodeTextKR = messageSource.getMessage("text.field.subGroupCode", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), subGroupCodeTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setSubGroupCode( subGroupCode    );
			  }


			  //25.혼재여부
			  String mixYn = returnCellContent(row.getCell(24));
			  if( !"0".equals(mixYn) &
					  !"1".equals(mixYn)  ) {
				  resultFlag = false;
		    	  String mixYnTextKR = messageSource.getMessage("text.field.mixYn", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), mixYnTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setMixYn(  SecureUtil.sqlValid(mixYn) );
			  }


			  //26.전수검사여부
			  String totalInspectYn = returnCellContent(row.getCell(25));
			  if( !"0".equals(totalInspectYn) &
					  !"1".equals(totalInspectYn)  ) {
				  resultFlag = false;
		    	  String totalInspectYnTextKR = messageSource.getMessage("text.field.totalInspectYn", null, Locale.getDefault());
		    	  Object[] args = new String[]{Integer.toString(i), totalInspectYnTextKR};
		    	  model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch.invalid", args, Locale.getDefault()));

				  break;
			  } else {
				  tmpProduct.getOfficialOrder().setTotalInspectYn( SecureUtil.sqlValid(totalInspectYn   ));
			  }


			  //27.가로
			  tmpProduct.setProductHorizontalLength(  returnCellContent(row.getCell(26))  );

			  //28.깊이
			  tmpProduct.setProductVerticalLength(  returnCellContent(row.getCell(27)) );

			  //29.높이
			  tmpProduct.setProductHeight(  returnCellContent(row.getCell(28))  );


			  tmpProduct.getOfficialOrder().setMdSendDivnCode("E");
			  tmpProduct.setRegId(epcLoginVO.getVendorId()[0]);
		      newProductArrayList.add(tmpProduct);
	      }
		if(resultFlag) {
			model.addAttribute("message", messageSource.getMessage("msg.product.excel.batch", null, Locale.getDefault()));
			model.addAttribute("success", "y");
			pEDMPRO0004Service.insertProductInfoList(newProductArrayList);
		}
		return "edi/product/PEDMPRO000401";
	}

	public String returnCellContent(Cell cell) {
		String value ="";
		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_FORMULA:
			value = "FORMULA value=" + cell.getCellFormula();
			break;

		case HSSFCell.CELL_TYPE_NUMERIC:
			double tmpValue = cell.getNumericCellValue();
			value = "" + new Double(tmpValue).intValue();
			break;

		case HSSFCell.CELL_TYPE_STRING:
			value =  cell.getStringCellValue();
			break;

		default:
	}
		return value;
	}


	/**
	 * 색상 / 사이즈 파일 업로드
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edi/product/PEDMPRO000420")
    public String onSubmitColorSizeDataUpload( 	HttpServletRequest request, Model model ) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		ArrayList<ColorSize> newColorSizeArrayList = new ArrayList<ColorSize>();
		  String newProductCode = request.getParameter("newProductCode");
		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		  MultipartFile mFile = mptRequest.getFile("productColorSize");
		  Workbook wb = WorkbookFactory.create(mFile.getInputStream());


		  NewProduct colorProduct = new NewProduct();
		  colorProduct.setNewProductCode(newProductCode);
		  colorProduct.setProductImageId(makeProductImageId(newProductCode));


		  Sheet sheet = wb.getSheetAt(0);
		  int rows = sheet.getPhysicalNumberOfRows();
		  for ( int i=1; i < rows; i++) {
			  Row row = sheet.getRow(i);

			  if( row == null ) {
		    	  continue;
		      }

			  ColorSize tmpColorSize = new ColorSize();
			  String seqString = StringUtils.leftPad(String.valueOf(i), 3, '0');

			  //PID( 가등록 상품코드
			  tmpColorSize.setNewProductCode(colorProduct.getNewProductCode());

			  //업체코드
			  tmpColorSize.setEnterpriseCode(returnCellContent(row.getCell(0)));

			  //상품속성명(상품명)
			  tmpColorSize.setProductName(returnCellContent(row.getCell(1)));

			  //판매코드
			  tmpColorSize.setSellCode(returnCellContent(row.getCell(2)));

			  //Color코드
			  String leftPadedColorCode = StringUtils.leftPad(returnCellContent(row.getCell(3)), 3, "0");
			  tmpColorSize.setColorCode(returnCellContent(row.getCell(3)));

			  //사이즈 구분코드
			  tmpColorSize.setSizeCategoryCode(returnCellContent(row.getCell(4)));

			  //사이즈코드
			  tmpColorSize.setSizeCode(returnCellContent(row.getCell(5))) ;

			  //itemCode
			  tmpColorSize.setItemCode(seqString);

			  //pog image id
			  tmpColorSize.setProductImageId(colorProduct.getProductImageId()+leftPadedColorCode);

			  //regid
			  tmpColorSize.setRegId(epcLoginVO.getCono()[0]);
			  newColorSizeArrayList.add(tmpColorSize);
	      }

		  pEDMPRO0004Service.insertColorSizeFromExcelBatch(newColorSizeArrayList);
		  model.addAttribute("newColorSizeArrayList", newColorSizeArrayList);

		return "edi/product/PEDMPRO000402";
	}





}
