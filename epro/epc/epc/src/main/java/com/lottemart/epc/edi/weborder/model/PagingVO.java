package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
/**
 * @author ljy
 * @Description : 
 * @Class : Result
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * </pre>
 * @version : 1.0
 */
@Alias("weborder.PagingVO")	// @4UP 추가 동일 이름의 VO가 존재하여 alias 추가
public class PagingVO implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1618031000635063037L;
	/**페이지 숫자 */
	private String page;
	/**페이지당 리스트 갯수  */
	private String pageRowCount;
	/**페이징 갯수  */
	private String pagePerCount;
	/**페이지 전체 카운트  */
	private int totalCount;
	/**페이지 시작 로우 넘버  */
	private int startRowNo;
	/**페이지 끝 로우 넘버 */
	private int endRowNo;
	/**리스트 로우 넘버  */
	private int rnum;
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPageRowCount() {
		return pageRowCount;
	}
	public void setPageRowCount(String pageRowCount) {
		this.pageRowCount = pageRowCount;
	}
	public String getPagePerCount() {
		return pagePerCount;
	}
	public void setPagePerCount(String pagePerCount) {
		this.pagePerCount = pagePerCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStartRowNo() {
		return startRowNo;
	}
	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}
	public int getEndRowNo() {
		return endRowNo;
	}
	public void setEndRowNo(int endRowNo) {
		this.endRowNo = endRowNo;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	
	
}
