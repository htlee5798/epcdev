/**
 *
 */
package com.lottemart.common.oneapp.trader.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 홍진옥
 * @@Description : 위수탁업체정보
 *
 */
//@JsonInclude(value = Include.NON_ABSENT, content = Include.NON_EMPTY)
public class VendorAddressInfo extends AddressInfoBase {

    @JsonProperty("dvpTypCd")
    private DeliveryType addrKindCd;       // 배송지유형코드 [출고지:B / 01, 반품지:A / 02]

    @JsonProperty("afflLrtrCd")
    private String vendorId;                   // 하위 거래처코드

    private String vendorNm;                   // 하위거래처명

    @JsonProperty("zipNo")
    private String zipCd;                      // 우편번호

    @JsonProperty("zipAddr")
    private String addr1Nm;                    // 우편주소

    @JsonProperty("dtlAddr")
    private String addr2Nm;                    // 상세주소

    private String cellNo;                     // 휴대폰번호
    
    @JsonProperty("telNo")
    private String telNo;					   // 전화번호 (출고지/반품지)


    public DeliveryType getAddrKindCd() {
        return addrKindCd;
    }

    public void setAddrKindCd(DeliveryType addrKindCd) {
        this.addrKindCd = addrKindCd;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorNm() {
        return vendorNm;
    }

    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public String getAddr1Nm() {
        return addr1Nm;
    }

    public void setAddr1Nm(String addr1Nm) {
        this.addr1Nm = addr1Nm;
    }

    public String getAddr2Nm() {
        return addr2Nm;
    }

    public void setAddr2Nm(String addr2Nm) {
        this.addr2Nm = addr2Nm;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}
