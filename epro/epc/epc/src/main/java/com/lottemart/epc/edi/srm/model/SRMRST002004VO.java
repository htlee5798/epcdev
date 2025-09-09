package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.common.model.CommonFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SRMRST002004VO implements Serializable {
	
	private static final long serialVersionUID = -897615395126813700L;

	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/**업체코드*/
	private String sellerCode;
	/** 평가번호 */
	private String evNo;
	/** 평가요청번호 */
	private String seq;
	/** 시정조치순번 */
	private String impSeq;
	/** 시정조치 요청항목 */
	private String evItemType1Code;
	private String evItemType1CodeName;
	/** 시정조치요청내용 */
	private String impReqRemark;
	/** 시정조치 상태값 */
	private String impStatus;
	/** 시정조치조치내용 */
	private String impResRemark;
	/** 시정조치 첨부파일ID*/
	private String impAttachFileNo;
	/** 시정조치조치일 */
	private String impResDate;
	/**첨부파일*/
	ArrayList<MultipartFile> impAttachFileNoFile;
	/**첨부문서*/
	private ArrayList docNo;
	/**첨부문서 순번*/
	private ArrayList docSeq;

	private List<CommonFileVO> impAttachFileList;

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getEvNo() {
		return evNo;
	}

	public void setEvNo(String evNo) {
		this.evNo = evNo;
	}

	public String getImpSeq() {
		return impSeq;
	}

	public void setImpSeq(String impSeq) {
		this.impSeq = impSeq;
	}

	public String getEvItemType1Code() {
		return evItemType1Code;
	}

	public void setEvItemType1Code(String evItemType1Code) {
		this.evItemType1Code = evItemType1Code;
	}

	public String getEvItemType1CodeName() {
		return evItemType1CodeName;
	}

	public void setEvItemType1CodeName(String evItemType1CodeName) {
		this.evItemType1CodeName = evItemType1CodeName;
	}

	public String getImpReqRemark() {
		return impReqRemark;
	}

	public void setImpReqRemark(String impReqRemark) {
		this.impReqRemark = impReqRemark;
	}

	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}

	public String getImpResRemark() {
		return impResRemark;
	}

	public void setImpResRemark(String impResRemark) {
		this.impResRemark = impResRemark;
	}

	public String getImpAttachFileNo() {
		return impAttachFileNo;
	}

	public void setImpAttachFileNo(String impAttachFileNo) {
		this.impAttachFileNo = impAttachFileNo;
	}

	public String getImpResDate() {
		return impResDate;
	}

	public void setImpResDate(String impResDate) {
		this.impResDate = impResDate;
	}

	public List<CommonFileVO> getImpAttachFileList() {
		return impAttachFileList;
	}

	public void setImpAttachFileList(List<CommonFileVO> impAttachFileList) {
		this.impAttachFileList = impAttachFileList;
	}

	public ArrayList getDocSeq() {
		if (this.docSeq != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docSeq.size(); i++) {
				ret.add(i, this.docSeq.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setDocSeq(ArrayList docSeq) {
		if (docSeq != null) {
			this.docSeq = new ArrayList();
			for (int i = 0; i < docSeq.size();i++) {
				this.docSeq.add(i, docSeq.get(i));
			}
		} else {
			this.docSeq = null;
		}
		this.docSeq = docSeq;
	}

	public ArrayList getDocNo() {
		if (this.docNo != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docNo.size(); i++) {
				ret.add(i, this.docNo.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setDocNo(ArrayList docNo) {
		if (docNo != null) {
			this.docNo = new ArrayList();
			for (int i = 0; i < docNo.size();i++) {
				this.docNo.add(i, docNo.get(i));
			}
		} else {
			this.docNo = null;
		}
		this.docNo = docNo;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public ArrayList<MultipartFile> getImpAttachFileNoFile() {
		if (this.impAttachFileNoFile != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < impAttachFileNoFile.size(); i++) {
				ret.add(i, this.impAttachFileNoFile.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setImpAttachFileNoFile(ArrayList<MultipartFile> impAttachFileNoFile) {
		if (impAttachFileNoFile != null) {
			this.impAttachFileNoFile = new ArrayList();
			for (int i = 0; i < impAttachFileNoFile.size();i++) {
				this.impAttachFileNoFile.add(i, impAttachFileNoFile.get(i));
			}
		} else {
			this.impAttachFileNoFile = null;
		}
		this.impAttachFileNoFile = impAttachFileNoFile;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
