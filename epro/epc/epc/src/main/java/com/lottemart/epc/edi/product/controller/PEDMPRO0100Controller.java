package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.file.FileUploadProperty;
import lcn.module.common.file.FormBasedFileVo;
import lcn.module.common.image.ImageUtilsThumbnail;
import lcn.module.common.namo.NamoMime;
import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.impl.ConfigurationReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.util.MimeUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;


import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.OfflineImage;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;
import java.io.PrintWriter;

@Controller
public class PEDMPRO0100Controller extends BaseController{

	private PEDPCOM0001Service commService;
	private FileUploadProperty imageUploadProperty;

	@Autowired
	public void setCommService(PEDPCOM0001Service commService) {
		this.commService = commService;
	}

	@Resource(name="imageUploadProperty")
	public void setImageUploadProperty(FileUploadProperty imageUploadProperty) {
		this.imageUploadProperty = imageUploadProperty;
		this.imageUploadProperty.setMaximumSize(500000L);// 크기가 500k 미만인 이미지만 업로드 가능
		this.imageUploadProperty.setMinimumSize(300000L);
		this.imageUploadProperty.getUploadAllowedExtensions().remove("gif");
		this.imageUploadProperty.getUploadAllowedExtensions().remove("bmp");
		this.imageUploadProperty.getUploadAllowedExtensions().remove("png");

		this.imageUploadProperty.getUploadAllowedTypes().remove("image/png");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/tiff");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/bmp");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/gif");
	}

	//코리안넷 상품등록 페이지(규격)
	@RequestMapping(value="/edi/product/PEDMPRO0100")
    public String korStandardProduct(SearchParam searchParam,
    		HttpServletRequest request, Model model) throws Exception {
		String viewPage = "edi/product/PEDMPRO0100";

		//사업자 번호가 쉼표(,)로 연결되어 전송된 경우
		String bman_no = request.getParameter("bman_no");

		if( bman_no.length() > 10 ) {
			bman_no = "'"+bman_no.replaceAll(",", "','")+"'";
		}

		else{
			bman_no = "'"+bman_no+"'";
		}


		//TODO 테스트 코드로 이하 조건문 반드시 제거
		if("1048203590".equals(bman_no)) {
			bman_no="1320699554";
		}
		model.addAttribute("bman_no", 	    bman_no);
		model.addAttribute("vendorList",  	commService.selectVendorListByBusinessNo(bman_no));
		model.addAttribute("l1GroupList", 	commService.selectL1List(searchParam));
		model.addAttribute("todayDate", 	DateFormatUtils.format(new Date(), "yyyyMMdd"));
		model.addAttribute("teamList", 	  	commService.selectDistinctTeamList());
		model.addAttribute("l1GroupList", 	commService.selectL1List(searchParam));

		return viewPage;
	}



	//코리안넷 상품등록 (규격, 패션)
	@RequestMapping(value="/edi/product/PEDMPRO0110")
   public String onSubmitNewProduct(NewProduct newProduct,
			HttpServletRequest request, Model model ) throws Exception {

//		logger.debug("입력된 신상품 필드값 :"+newProduct);

		//System.out.println("---> "+newProduct.getOnOffDivnCode());

//		newProduct.setNewProductCode(ediProductService.selectNewProductCode());
//		/* 쇼카드명 강제로 자르지 않는다.
//		String poductShortCardName = newProduct.getProductShortName().length() > 14 ? StringUtils.substring(newProduct.getProductShortName(), 0, 14)
//				: newProduct.getProductShortName();
//		newProduct.setProductShortName(poductShortCardName);
//		*/
//		String editorHtmlContent = saveFileAndEditorHtmlContent(newProduct.getNewProductCode(), request);
//		newProduct.setProductDescription(StringUtils.defaultIfEmpty(editorHtmlContent, "&nbsp;"));
//
//		String newProductImageId = StringUtils.substring(newProduct.getNewProductCode(), 2, 8)
//		+ StringUtils.right(newProduct.getNewProductCode(), 5);
//
//	newProduct.setProductImageId(newProductImageId);
//
//	//System.out.println("---> "+newProduct.getOnOffDivnCode());
//
//		ediProductService.insertProductInfo(newProduct);
//
//
//
//
//		//전자상거래 입력 데이터
//		//String[] tempArrayCd = newProduct.getProdAddCd().split("//");
//		//String[] tempArrayNm = newProduct.getProdAddNm().split("//");
//		String[] tempArrayCd = newProduct.getProdAddCd().split("#//#");
//		String[] tempArrayNm = newProduct.getProdAddNm().split("#//#");
//
//		//INSERT 하기전 모두 삭제
//		ediProductService.deleteProdAddDetail(newProduct);
//
////		for(int i=0;i<tempArrayCd.length;i++){
////			if(tempArrayCd[i] != null && tempArrayCd[i].length() > 0){
////				newProduct.setProdAddMasterCd(newProduct.getProdAddMasterCd());
////				newProduct.setProdAddCd(tempArrayCd[i]);
////				newProduct.setProdAddNm(tempArrayNm[i]);
////				newProduct.setEntpCode(newProduct.getEntpCode());
////
////				ediProductService.insertProdAddDetail(newProduct);
////			}
////		}
////
//
//
//		for(int i=0;i<tempArrayCd.length;i++){
//			if(tempArrayCd[i] != null && tempArrayCd[i].length() > 0){
//			//	System.out.println("i "+i);
//			//	System.out.println("tempArrayNm[i]=========> "+tempArrayNm[i]);
//
//				if(tempArrayNm[i] != null && !"".equals(tempArrayNm[i].trim())){
//
//					newProduct.setProdAddMasterCd(newProduct.getProdAddMasterCd());
//					newProduct.setProdAddCd(tempArrayCd[i].trim());
//					newProduct.setProdAddNm(tempArrayNm[i].trim());
//					newProduct.setEntpCode(newProduct.getEntpCode());
//
//					ediProductService.insertProdAddDetail(newProduct);
//				}
//			}
//		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		//KC 인증마크 입력 데이터
//		String[] tempArrayCertCd = newProduct.getProdCertCd().split("#//#");
//		String[] tempArrayCertNm = newProduct.getProdCertNm().split("#//#");
//
//		//INSERT 하기전 모두 삭제
//		ediProductService.deleteProdCertDetail(newProduct);
//
//		for(int i=0;i<tempArrayCertCd.length;i++){
//			if(tempArrayCertCd[i] != null && tempArrayCertCd[i].length() > 0){
//			//	System.out.println("i "+i);
//			//	System.out.println("tempArrayNm[i]=========> "+tempArrayNm[i]);
//
//				if(tempArrayCertNm[i] != null && !"".equals(tempArrayCertNm[i].trim())){
//
//					newProduct.setProdCertMasterCd(newProduct.getProdCertMasterCd());
//					newProduct.setProdCertCd(tempArrayCertCd[i].trim());
//					newProduct.setProdCertNm(tempArrayCertNm[i].trim());
//					newProduct.setEntpCode(newProduct.getEntpCode());
//
//					ediProductService.insertProdCertDetail(newProduct);
//				}
//			}
//		}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		if(isThisFashionProduct(newProduct.getProductDivnCode())) {
//			newProduct.makeColorSizeObject();
//			if(newProduct.getColorSizeList().size() > 0) {
//				ediProductService.insertProductColorSizeInfo(newProduct);
//				model.addAttribute("colorSizeList", newProduct.getColorSizeList());
//			}
//		}
//
//
//		/**
//		 * 온라인 이미지 업로드 시작
//		 *
//		 *
//		 */
//
//		 String newProductCode = newProduct.getNewProductCode();
//		  String subFolderName  = subFolderName(newProductCode);
//		  String uploadDir		= makeSubFolderForOnline(newProductCode);
//		  String onOffDivnCode  = newProduct.getOnOffDivnCode();
//		  String productDivnCode = newProduct.getProductDivnCode();
//		  String fashionProductFlag = "n";
//		  //ArrayList<String> productColorList = new ArrayList<String>();
//		  List<String> productColorList = new ArrayList<String>();
//
////System.out.println("newProductCode---> "+newProductCode);
////System.out.println("subFolderName---> "+subFolderName);
////System.out.println("uploadDir---> "+uploadDir);
////System.out.println("onOffDivnCode---> "+onOffDivnCode);
////System.out.println("productDivnCode---> "+productDivnCode);
////System.out.println("fashionProductFlag---> "+fashionProductFlag);
//
//	int oNimgCnt=0;
//		  if(Constants.FASHION_PRODUCT_CD.equals(productDivnCode)) {
//			  fashionProductFlag = "y";
//			  productColorList = ediProductService.selectProductColorList(newProductCode);
//		  }
//		  Integer newSeq = 0;
//
//		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
//		  Iterator fileIter = mptRequest.getFileNames();
//		  while (fileIter.hasNext()) {
//			 MultipartFile mFile = mptRequest.getFile((String)fileIter.next());
//			 if(!mFile.isEmpty()) {
//				 String newFileSource = uploadDir+"/"+newProductCode+"_"+newSeq;
//
//				 String fileFieldName = mFile.getName();
//				 if(!fileFieldName.startsWith("front")) {
//					 newFileSource = uploadDir+"/"+fileFieldName;
//				 }
//
//				 FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
//				 FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
//				 ImageUtilsThumbnail.resizeAutoForEPC(newFileSource);
//			//	 System.out.println("frontImageStream---> "+frontImageStream);
//			//	 System.out.println("newFileSource---> "+newFileSource);
//			//	 System.out.println("uploadDir---> "+uploadDir);
//				 newSeq++;
//				 oNimgCnt++;
//
//				 //System.out.println("oNimgCnt---> "+oNimgCnt);
//
//			 }
//
//		  }
/////////////////////////////////////////이동빈/////////////////////////////////////////////////////////////////////////////////////////////////
//		//System.out.println("oNimgCnt---> "+oNimgCnt);
//		String onCnt = String.valueOf(oNimgCnt); //파일 개수
//		HashMap<String, Object> hmap = new HashMap();
//		  if(onCnt != null || !"".equals(onCnt)){
//			  hmap.put("newProd", newProductCode);
//			  hmap.put("fileCount", onCnt);
//			  ediProductService.updateNewprodOnimgCnt(hmap);
//		  }
//////////////////////////////////////////////////////////////////////////////////////////
//		/**
//		 * 온라인 이미지 업로드 끝
//		 *
//		 *
//		 */
//
//		  //확정처리
//		  pEDMPRO0003Service.fixNewProductInTemp(newProductCode, Constants.ONLINE_OFFLINE_PRODUCT_CD, null);
		model.addAttribute("newProduct", newProduct);
		return "edi/product/PEDMPRO0300";
		//return "redirect:/edi/product/PEDMPRO010301.do?newProductCode="+newProduct.getNewProductCode()+"&mode=basicInfo";
	}

	//코리안넷 상품등록 페이지 (패션)
	@RequestMapping(value="/edi/product/PEDMPRO0200")
    public String korFashionProduct(SearchParam searchParam,
    		HttpServletRequest request, Model model) {
		String fashionSellCode = request.getParameter("srcmk_cd");
		String[] sellCodeArray =fashionSellCode.split("\\|");
		List<String> fashionSellCodeList = Arrays.asList(fashionSellCode.split("\\|"));
		String mainProductSellCode = sellCodeArray[0];

		String bman_no = request.getParameter("bman_no");
		if( bman_no.length() > 10 ) {
			bman_no = "'"+bman_no.replaceAll(",", "','")+"'";
		}
		else {
			bman_no = "'"+bman_no+"'";
		}

		//TODO 테스트 코드로 이하 조건문 반드시 제거
		if("1048203590".equals(bman_no)) {
			bman_no="1320699554";
		}

		model.addAttribute("vendorList",  	commService.selectVendorListByBusinessNo(bman_no));
		model.addAttribute("bman_no", 	   bman_no);
		model.addAttribute("seasonList", commService.selectSeasonTypeList(Constants.FASHION_PRODUCT_CD));
		model.addAttribute("teamList", 	  	   commService.selectDistinctTeamList());
		model.addAttribute("l1GroupList", 	   commService.selectL1List(searchParam));
		model.addAttribute("colorList", 	   commService.selectColorList());
		model.addAttribute("sizeCategoryList", commService.selectSizeCategoryList());
		model.addAttribute("fashionSellCodeList", 	  	   fashionSellCodeList);
		model.addAttribute("mainProductSellCode", 	  	   mainProductSellCode);
		return "edi/product/PEDMPRO0200";
	}


	/**
	 * Desc : 오프라인 이미지 업로드 처리.
	 * @Method Name : onSubmitOfflineImageUpload
	 * @param OfflineImage
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return
	 * @return String
	 */
	@RequestMapping(value="/edi/product/checkEdiUploadImage", method=RequestMethod.POST)
	public void onSubmitOnlineImageCheckFromKoreannet( HttpServletRequest request,
			HttpServletResponse response ) throws Exception   {


		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		  Iterator fileIter = mptRequest.getFileNames();
		  boolean isValidFile = true;
		  String resultMessage="ok";

		  while (fileIter.hasNext()) {
			 MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			 if(!mFile.isEmpty()) {
				 //file type check
				 isValidFile =
						 this.imageUploadProperty.getUploadAllowedTypes().contains(
						 mFile.getContentType().toLowerCase());
				 if( !isValidFile )	{
					 resultMessage="type";
					break;
				 }

				 isValidFile =
					 this.imageUploadProperty.getUploadAllowedExtensions().contains(
							 FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase());
				 if( !isValidFile )	{
					 resultMessage="extension";
					break;
				 }

				 if( this.imageUploadProperty.getMaximumSize() < mFile.getSize()) {
					 resultMessage="size";
					break;
				 }
			 }

		  }


		  PrintWriter pw = response.getWriter();
		  pw.print("<textarea>{'resultMessage':'"+resultMessage+"' }</textarea>");
		  pw.flush();
		  pw.close();

		  //return AjaxJsonModelHelper.create("<textarea>{ 'resultMessage':'"+resultMessage+"' }</textarea>");
	}
}
