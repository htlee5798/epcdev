/**
 *
 */
package com.lottemart.common.oneapp.trader.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DELL
 *
 */
@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class AddressInfoBase extends Trader {

    @JsonProperty("addrSeq")
    private String addrSeq;             // 주소순번

    @JsonProperty("dvpNo")
    private String deliveryNo; // 배송지번호

    @JsonProperty("dvpNm")
    private String dvpNm;               // 배송지명

    @JsonProperty("stnmZipNo")
    private String stnmZipNo;           // 도로명우편번호

    @JsonProperty("stnmZipAddr")
    private String stnmZipAddr;         // 도로명우편주소

    @JsonProperty("stnmDtlAddr")
    private String stnmDtlAddr;         // 도로명상세주소

    @JsonProperty("rpbtrNm")
    private String rpbtrNm;             // 담당자명

    @JsonProperty("mphnNatnNoCd")
    private String mphnNatnNoCd;        // 휴대폰국가코드 [default:'82']

    @JsonProperty("mphnNo")
    private String mphnNo;              // 휴대폰번호

    @JsonProperty("telNatnNoCd")        // 연락처국가코드 [default:'82'] TODO 홍진옥 : 통합 EC API 오표기 추후 확인후 삭제
    private String telNatnNoCd;

    @JsonProperty("telNatnBoCd")        // 연락처국가코드 [default:'82'] TODO 홍진옥 : 통합 EC API 오표기 추후 확인후 삭제
    private String telNatnBoCd;

    @JsonProperty("useYn")
    private String useYn;

    public String getAddrSeq() {
        return addrSeq;
    }

    public void setAddrSeq(String addrSeq) {
        this.addrSeq = addrSeq;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDvpNm() {
        return dvpNm;
    }

    public void setDvpNm(String dvpNm) {
        this.dvpNm = dvpNm;
    }

    public String getStnmZipNo() {
        return stnmZipNo;
    }

    public void setStnmZipNo(String stnmZipNo) {
        this.stnmZipNo = stnmZipNo;
    }

    public String getStnmZipAddr() {
        return stnmZipAddr;
    }

    public void setStnmZipAddr(String stnmZipAddr) {
        this.stnmZipAddr = stnmZipAddr;
    }

    public String getStnmDtlAddr() {
        return stnmDtlAddr;
    }

    public void setStnmDtlAddr(String stnmDtlAddr) {
        this.stnmDtlAddr = stnmDtlAddr;
    }

    public String getRpbtrNm() {
        return rpbtrNm;
    }

    public void setRpbtrNm(String rpbtrNm) {
        this.rpbtrNm = rpbtrNm;
    }

    public String getMphnNatnNoCd() {
        return mphnNatnNoCd;
    }

    public void setMphnNatnNoCd(String mphnNatnNoCd) {
        this.mphnNatnNoCd = mphnNatnNoCd;
    }

    public String getMphnNo() {
        return mphnNo;
    }

    public void setMphnNo(String mphnNo) {
        this.mphnNo = mphnNo;
    }

    public String getTelNatnNoCd() {
        return telNatnNoCd;
    }

    public void setTelNatnNoCd(String telNatnNoCd) {
        this.telNatnNoCd = telNatnNoCd;
    }

    public String getTelNatnBoCd() {
        return telNatnBoCd;
    }

    public void setTelNatnBoCd(String telNatnBoCd) {
        this.telNatnBoCd = telNatnBoCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
