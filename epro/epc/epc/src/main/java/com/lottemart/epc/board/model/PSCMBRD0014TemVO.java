package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0014VO.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 5:52:28 choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */

/**
 * @Class Name : PSCMBRD0014TemVO.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 4. 오후 1:34:31 Choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMBRD0014TemVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 템플릿 순번
	private String templateSeq = "";
	// 템플릿 제목
	private String templateTitle = "";
	// 템플릿 내용
	private String templateContent = "";
	// 등록일자
	private String regId = "";
	// 등록일시
	private String regDate = "";

	public String getTemplateSeq() {
		return templateSeq;
	}

	public void setTemplateSeq(String templateSeq) {
		this.templateSeq = templateSeq;
	}

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
