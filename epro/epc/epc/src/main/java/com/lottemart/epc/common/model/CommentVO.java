/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.model;

import java.io.Serializable;

/**
 * @Class Name : CommentVO
 * @Description : 코멘트 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:02:17 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class CommentVO implements Serializable {

	private static final long serialVersionUID = -8717159923669486475L;
	
	private int rankNum;
	private String boardSeq;
	private String commentSeq;
	private String lineComment;
	private String delYn;
	private String memberNo;
	private String evltCd;
	private String userIp;
	private String typeCd;
	private String regDate;
	private String regId;
	private String modDate;
	private String modId;
	
	
	public int getRankNum() {
		return rankNum;
	}
	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getCommentSeq() {
		return commentSeq;
	}
	public void setCommentSeq(String commentSeq) {
		this.commentSeq = commentSeq;
	}
	public String getLineComment() {
		return lineComment;
	}
	public void setLineComment(String lineComment) {
		this.lineComment = lineComment;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getEvltCd() {
		return evltCd;
	}
	public void setEvltCd(String evltCd) {
		this.evltCd = evltCd;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("rankNum :: ").append(rankNum);
		sb.append("boardSeq :: ").append(boardSeq);
		sb.append("commentSeq :: ").append(commentSeq);
		sb.append("lineComment :: ").append(lineComment);
		sb.append("delYn :: ").append(delYn);
		sb.append("memberNo :: ").append(memberNo);
		sb.append("evltCd :: ").append(evltCd);
		sb.append("userIp :: ").append(userIp);
		sb.append("typeCd :: ").append(typeCd);
		sb.append("regDate :: ").append(regDate);
		sb.append("regId :: ").append(regId);
		sb.append("modDate :: ").append(modDate);
		sb.append("modId :: ").append(modId);
		return sb.toString();
	}
}
