package com.lottemart.epc.edi.product.model;


import java.io.FileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;



/**
 * @Class Name : OfflineImage
 * @Description : 신상품 등록에 사용되는 offline pogimage  VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:32:38 kks
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class OfflineImage {

	public OfflineImage() {}

	//이하 multipart필드는 파일 업로드시 매핑되어 컨트롤러에서 사용된다.
	//이미지 파일 앞면
	private MultipartFile front;

	//이미지 파일 측면
	private MultipartFile side;

	//이미지 파일 윗면
	private MultipartFile top;


	//아래 세개의 string필드는 위의 3개 multipart필드의 파일 이름필드로 사용됨.
	private String frontImage;
	private String sideImage;
	private String topImage;

//	private String frontImageExt;
//	private String sideImageExt;
//	private String topImageExt;

	//오프라인 이미지 등록 폴더 prefix
	private String uploadDir;

	//상품등록코드
	private String newProductCode;

	//상품구분(규격, 패션)
	private String productDivnCode;



	public String getProductDivnCode() {
		return productDivnCode;
	}


	public void setProductDivnCode(String productDivnCode) {
		this.productDivnCode = productDivnCode;
	}

	//MD 상품이미지 등록 관련 필드
	private String imgSeq;
	private String colorCd;
	private String imgNm;
	private String prodImgId;
	private String entpCode;
	private String sellCode;



	public String getSellCode() {
		return sellCode;
	}


	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}


	public String getEntpCode() {
		return entpCode;
	}


	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}


	public String getImgSeq() {
		return imgSeq;
	}


	public void setImgSeq(String imgSeq) {
		this.imgSeq = imgSeq;
	}


	public String getColorCd() {
		return colorCd;
	}


	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}


	public String getImgNm() {
		return imgNm;
	}


	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}


	public String getProdImgId() {
		return prodImgId;
	}


	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}


	public String getNewProductCode() {
		return newProductCode;
	}


	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}


	public String getUploadDir() {
		return uploadDir;
	}


	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}



	public void saveOfflineImage(String uploadDir) throws Exception {


	      setFrontImage(getNewProductCode()+"_front."+getFrontImageExt());
		  setSideImage(getNewProductCode()+"_side."+getSideImageExt());
		  setTopImage(getNewProductCode()+"_top."+getTopImageExt());

		  setUploadDir(uploadDir);

		  FileOutputStream frontImageStream = new FileOutputStream(uploadDir+"/"+getFrontImage());
          FileCopyUtils.copy(getFront().getInputStream(), frontImageStream);

          FileOutputStream sideImageStream = new FileOutputStream(uploadDir+"/"+getSideImage());
          FileCopyUtils.copy(getSide().getInputStream(), sideImageStream);

          FileOutputStream topImageStream = new FileOutputStream(uploadDir+"/"+getTopImage());
          FileCopyUtils.copy(getTop().getInputStream(), topImageStream);
	}


	public String getFrontImageExt() {
		return FilenameUtils.getExtension(getFront().getOriginalFilename()).toLowerCase();
	}
	public String getSideImageExt() {
		return FilenameUtils.getExtension(getSide().getOriginalFilename()).toLowerCase();
	}
	public String getTopImageExt() {
		return FilenameUtils.getExtension(getTop().getOriginalFilename()).toLowerCase();

	}


	public String resultString() {

		return "[{ \"frontImage\" : \""+getFrontImage()+"\", \"sideImage\" : \""+getSideImage()+
			"\", \"topImage\": \""+getTopImage()+"\" }]";
	}


	public String getFrontImage() {
		return frontImage;
	}
	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}
	public String getSideImage() {
		return sideImage;
	}
	public void setSideImage(String sideImage) {
		this.sideImage = sideImage;
	}
	public String getTopImage() {
		return topImage;
	}
	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}
	public MultipartFile getFront() {
		return front;
	}
	public void setFront(MultipartFile front) {
		this.front = front;
	}
	public MultipartFile getSide() {
		return side;
	}
	public void setSide(MultipartFile side) {
		this.side = side;
	}
	public MultipartFile getTop() {
		return top;
	}
	public void setTop(MultipartFile top) {
		this.top = top;
	}


}
