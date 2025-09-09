package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lottemart.common.util.DataMap;

public class ColorSize implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8217038698476076150L;

	public ColorSize() {}

	//상품등록코드
	private String newProductCode;

	//시퀀스, 자동증가 값으로 세자리이며 0으로 leftpadding되서 생성됨.
	private String itemCode;

	//색상값
	private String colorCode;

	//사이즈 분류 값.
	private String sizeCategoryCode;

	//사이즈 코드
	private String sizeCode;

	//협력업체 코드
	private String enterpriseCode;

	//판매코드
	private String sellCode;

	//상품명
	private String productName;

	//상태구분 코드
	private String statusDivisionCode;

	//삭제 여부
	private String deleteFlag;

	//등록 일
	private Date regDate;

	//등록자
	private String regId;

	//수정일
	private Date modDate;

	//수정자
	private String modId;

	//오프라인 pog image id. 패션 상품인 경우 이 필드 값에 colorCode값을 추가 연결해서 pog image값으로 사용함.
	private String productImageId;

	//이하 두 필드는 colorCode, sizeCode필드 값의 세자리 0으로 leftpadding된 필드
//	private String colorLpaded;
//	private String sizeLpaded;

	//색상명
	private String colorName;

	//사이즈단위명
	private String szName;

	private String optnDesc;
	private String stkMgrYn;
	private String attrPiType;
	private int rservStkQty;
	private int optnAmt; //단품 가격

	//ec 상품속성(단품당 여러개있을수있음)
	private List<EcProductAttribute> ecProductAttrList;


	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSzName() {
		return szName;
	}
	public void setSzName(String szName) {
		this.szName = szName;
	}
	public String getColorLpaded() {
		return StringUtils.leftPad(getColorCode(), 3, "0");
	}
	public String getSizeLpaded() {
		return StringUtils.leftPad(getSizeCode(), 3, "0");
	}
	public String getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(String productImageId) {
		this.productImageId = productImageId;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getSizeCategoryCode() {
		return sizeCategoryCode;
	}
	public void setSizeCategoryCode(String sizeCategoryCode) {
		this.sizeCategoryCode = sizeCategoryCode;
	}
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatusDivisionCode() {
		return statusDivisionCode;
	}
	public void setStatusDivisionCode(String statusDivisionCode) {
		this.statusDivisionCode = statusDivisionCode;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}


	//이 메소드는  색상/사이즈를 입력하는 ui와 table구조가 달라서 동일한 색상의 여러 사이즈를 입력한 경우,
	//각 사이즈에 대한 색상 값 객체를 복사해주는 역할은 한다.
	public ColorSize copyField(String itemCode, String sizeCode, String sellCode) {
		// TODO Auto-generated method stub
		ColorSize tmpColorSize1 = new ColorSize();
		tmpColorSize1.setNewProductCode(getNewProductCode());
		tmpColorSize1.setItemCode(itemCode);
		tmpColorSize1.setColorCode(getColorCode());
		tmpColorSize1.setSizeCategoryCode(getSizeCategoryCode());
		tmpColorSize1.setSizeCode(sizeCode);
		tmpColorSize1.setStatusDivisionCode(getStatusDivisionCode());
		tmpColorSize1.setSellCode(sellCode);
		tmpColorSize1.setProductName(getProductName());
		tmpColorSize1.setEnterpriseCode(getEnterpriseCode());

		return tmpColorSize1;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
	public String getStkMgrYn() {
		return stkMgrYn;
	}
	public void setStkMgrYn(String stkMgrYn) {
		this.stkMgrYn = stkMgrYn;
	}
	public int getRservStkQty() {
		return rservStkQty;
	}
	public void setRservStkQty(int rservStkQty) {
		this.rservStkQty = rservStkQty;
	}
	public int getOptnAmt() {
		return optnAmt;
	}
	public void setOptnAmt(int optnAmt) {
		this.optnAmt = optnAmt;
	}
	public String getAttrPiType() {
		return attrPiType;
	}
	public void setAttrPiType(String attrPiType) {
		this.attrPiType = attrPiType;
	}
	public List<EcProductAttribute> getEcProductAttrList() {
		return ecProductAttrList;
	}
	public void setEcProductAttrList(List<EcProductAttribute> ecProductAttrList) {
		this.ecProductAttrList = ecProductAttrList;
	}

}
