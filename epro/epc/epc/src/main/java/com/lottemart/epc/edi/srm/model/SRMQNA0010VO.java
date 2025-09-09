package com.lottemart.epc.edi.srm.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SRMQNA0010VO implements Serializable  {

    // 요청 순번
    String reqSeq;
    // 업체 코드
    String sellerCode;
    // 제목
    String subject;
    // 카테고리
    String category;
    // 담당자 이름
    String mainName;
    // 업체 이름
    String sellerNameLoc;
    // 담당자 이메일
    String email;
    // 담당자 휴대전화
    String phone;
    // 희망점포
    String hopeStore;
    // 내용
    String content;
    // 등록자
    String regId;
    // 수정자
    String chgId;
    // 개인정보 동의서 파일 이름
    String agreeFileVer;
    // 문의내용 첨부파일
    String boardAttachNo;
    MultipartFile boardAttachFile;
    // 회사소개서 첨부파일
    String compInfoAttachNo;
    MultipartFile compInfoAttachFile;
    // 사업계획서 첨부파일
    String bizPlanAttachNo;
    MultipartFile bizPlanAttachFile;
    // 사업자등록증 첨부파일
    String bizRegCertAttachNo;
    MultipartFile bizRegCertAttachFile;


    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSellerNameLoc() {
        return sellerNameLoc;
    }

    public void setSellerNameLoc(String sellerNameLoc) {
        this.sellerNameLoc = sellerNameLoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHopeStore() {
        return hopeStore;
    }

    public void setHopeStore(String hopeStore) {
        this.hopeStore = hopeStore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getChgId() {
        return chgId;
    }

    public void setChgId(String chgId) {
        this.chgId = chgId;
    }

    public String getAgreeFileVer() {
        return agreeFileVer;
    }

    public void setAgreeFileVer(String agreeeFileVer) {
        this.agreeFileVer = agreeeFileVer;
    }

    public String getBoardAttachNo() {
        return boardAttachNo;
    }

    public void setBoardAttachNo(String boardAttachNo) {
        this.boardAttachNo = boardAttachNo;
    }

    public MultipartFile getBoardAttachFile() {
        return boardAttachFile;
    }

    public void setBoardAttachFile(MultipartFile boardAttachFile) {
        this.boardAttachFile = boardAttachFile;
    }

    public String getCompInfoAttachNo() {
        return compInfoAttachNo;
    }

    public void setCompInfoAttachNo(String compInfoAttachNo) {
        this.compInfoAttachNo = compInfoAttachNo;
    }

    public MultipartFile getCompInfoAttachFile() {
        return compInfoAttachFile;
    }

    public void setCompInfoAttachFile(MultipartFile compInfoAttachFile) {
        this.compInfoAttachFile = compInfoAttachFile;
    }

    public String getBizPlanAttachNo() {
        return bizPlanAttachNo;
    }

    public void setBizPlanAttachNo(String bizPlanAttachNo) {
        this.bizPlanAttachNo = bizPlanAttachNo;
    }

    public MultipartFile getBizPlanAttachFile() {
        return bizPlanAttachFile;
    }

    public void setBizPlanAttachFile(MultipartFile bizPlanAttachFile) {
        this.bizPlanAttachFile = bizPlanAttachFile;
    }

    public String getBizRegCertAttachNo() {
        return bizRegCertAttachNo;
    }

    public void setBizRegCertAttachNo(String bizRegCertAttachNo) {
        this.bizRegCertAttachNo = bizRegCertAttachNo;
    }

    public MultipartFile getBizRegCertAttachFile() {
        return bizRegCertAttachFile;
    }

    public void setBizRegCertAttachFile(MultipartFile bizRegCertAttachFile) {
        this.bizRegCertAttachFile = bizRegCertAttachFile;
    }
}
