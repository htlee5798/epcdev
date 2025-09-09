package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0007Service;






@Controller
public class PEDMPRO0007Controller extends BaseController{

//	private PEDMPRO000Service ediProductService;

	private PEDMPRO0007Service pEDMPRO0007Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


//	@Autowired
//	public void setEdiProductService(PEDMPRO000Service ediProductService) {
//		this.ediProductService = ediProductService;
//	}

	@Autowired
	public void setpEDMPRO0007Service(PEDMPRO0007Service pEDMPRO0007Service) {
		this.pEDMPRO0007Service = pEDMPRO0007Service;
	}


	//전체상품 현황
	@RequestMapping(value="/edi/product/PEDMPRO0007")
    public String wholeProductList(SearchProduct searchParam, HttpServletRequest request, Model model) {
		/** paging setting */


		return "edi/product/PEDMPRO0007";
	}

	//전체 현황 다운로드 체크
	@RequestMapping(value="/edi/product/PEDMPRO0007DownloadCheck")
	public ModelAndView wholeProductListDownload(SearchProduct searchParam)
		 {
		return null;
	}

	//전체 상품현황 다운로드
	@RequestMapping(value="/edi/product/PEDMPRO0007Download")
	public void wholeProductListDownload(SearchProduct searchParam, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
		/** paging setting */


	}


	//이미지 사이즈 수정 배너 페이지에서 이동시 url
	@RequestMapping(value = "/edi/product/PEDMPRO0008Baner.do", method = RequestMethod.GET)
	public String outSideUrl(@RequestParam Map<String,Object> map, Locale locale,  ModelMap model) {

		map.put("defPgmID", "/edi/product/PEDMPRO0008.do");
		map.put("pgm_code", "PRO");
		map.put("pgm_sub",  "2");
		model.addAttribute("paramMap",map);

		return "/edi/main/ediIndex";
	}




	//이미지 사이즈 수정 목록 보기
	@RequestMapping(value="/edi/product/PEDMPRO0008")
    public String productImageList(SearchProduct searchParam, HttpServletRequest request, Model model) {

		String todayString = DateUtil.getToday("yyyy-MM-dd");
		String applyDay = DateUtil.addDay(todayString, 1);

		if(StringUtils.isEmpty(searchParam.getStartDate()))
			searchParam.setStartDate(DateUtil.getToday("yyyy-MM-dd"));


		if(StringUtils.isEmpty(searchParam.getEndDate())) {
			searchParam.setEndDate(DateUtil.formatDate(DateUtil.addDay(todayString, 1), "-"));
		}

		model.addAttribute("applyDay", applyDay);
		model.addAttribute("searchParam", searchParam);

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		if(StringUtils.isBlank(searchParam.getEntpCode())) {
			return "edi/product/PEDMPRO0008";
		}

		List<PEDMPRO0005VO> productImageList = pEDMPRO0007Service.selectProductImageList(searchParam);
		model.addAttribute("productImageList", productImageList);

		 model.addAttribute("imagePath" ,			ConfigUtils.getString("system.cdn.static.path"));
		return "edi/product/PEDMPRO0008";
	}


	//이미지 수정 팝업페이지
	@RequestMapping(value="/edi/product/PEDMPRO000801")
    public String productImagePage(SearchProduct searchParam, HttpServletRequest request, Model model) {
		String currentTime  = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss", Locale.KOREA);
		model.addAttribute("currentTime", currentTime);

		return "edi/product/PEDMPRO000801";
	}

	//규격, 이미지 변경처리 - MD자료 delete 후 insert
	@RequestMapping(value="/edi/product/PEDMPRO000802")
    public String onSubmitProductImage(SearchProduct searchParam, HttpServletRequest request, Model model) throws IOException {
		String uploadDir    = makeSubFolderForOffline(searchParam.getNewProductCode());

		 // offlineImage.saveOfflineImage(uploadDir);

		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		  Iterator fileIter = mptRequest.getFileNames();

		  FTPClient ftp = new FTPClient();

		  ftp.connect( ConfigUtils.getString("edi.md.ftp.url") );  //10.52.1.163
		  ftp.login( ConfigUtils.getString("edi.md.ftp.userid"), ConfigUtils.getString("edi.md.ftp.passwd") );
		  ftp.setFileType(FTP.BINARY_FILE_TYPE);
		  FileOutputStream frontImageStream =null;
		  String currentTime=DateFormatUtils.format(new Date(), "yyyyMMddHHmmss", Locale.KOREA);



		  while (fileIter.hasNext()) {
			 MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			 if(!mFile.isEmpty()) {
				 //String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
				 String postfix = mFile.getName();

				 // 이미지아이디가 없는경우----------------------------
				 if(searchParam.getImageName() == null || searchParam.getImageName().length() <=0) {
					 postfix =  currentTime+postfix;
				}
				 //------------------------------------------------


				 if(postfix.endsWith("front")) {
					 postfix = postfix.replaceAll("_front", ".1");
				 }
				 if(postfix.endsWith("side")) {
					 postfix = postfix.replaceAll("_side", ".2");
				 }
				 if(postfix.endsWith("top")) {
					 postfix = postfix.replaceAll("_top", ".3");
				 }
				 if(postfix.endsWith("back")) {
					 postfix = postfix.replaceAll("_back", ".4");
				 }
				// String baseFileSource = uploadDir+"/"+offlineImage.getNewProductCode()+"_"+postfix;





				 String newFileSource = uploadDir+"/"+postfix+".jpg";//+ext;

				 frontImageStream = new FileOutputStream(newFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

				 ftp.storeFile(postfix+".jpg", FileUtils.openInputStream(new File(newFileSource)));
				 /*
				 FileOutputStream baseFileStream = new FileOutputStream(baseFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), baseFileStream);
				 */

				 frontImageStream.close();

			 }

		  }
		  ftp.disconnect();

		  if(searchParam.getImageName() == null || searchParam.getImageName().length() <=0)  searchParam.setImageName(currentTime); // 이미지 이름이 없을경우
		  //TODO 이미지 교체일 경우, changeState.. 메소드 실행할것.

		  pEDMPRO0007Service.deleteMDLegacyImageData(searchParam);
		  model.addAttribute("result", "insert");
		return "edi/product/PEDMPRO000801";
	}



	//사이즈 수정 페이지
	@RequestMapping(value="/edi/product/PEDMPRO000810")
    public String productSizePage(SearchProduct searchParam, HttpServletRequest request, Model model) {

		return "edi/product/PEDMPRO000810";
	}

	//사이즈 처리
	@RequestMapping(value="/edi/product/PEDMPRO000811")
    public String onSubmitproductSizes(SearchProduct searchParam, HttpServletRequest request, Model model) {
		pEDMPRO0007Service.updateMDPOGImageSize(searchParam);
		 model.addAttribute("result", "success");
		return "edi/product/PEDMPRO000810";
	}
}
