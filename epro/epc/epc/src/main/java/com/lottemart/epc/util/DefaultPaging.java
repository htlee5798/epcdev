package com.lottemart.epc.util;

import java.util.ArrayList;
import java.util.List;

public class DefaultPaging implements Paging {

	private Integer last;				// 링크 목록의 마지막 페이지 번호 예)10
	private Integer start;			// 링크 목록의 첫번째 페이지 번호 예) 1
	private final Integer totalCount;	// 조회 전체 건 수
	private Integer next;				// 다음 링크 목록의 첫번째 번호 예)11
	private Integer current;			// 현재 페이지
	private List<StyleSheet> list;		// 화면에 출력될 링크 목록
	private Integer first;				// 모든 링크의  맨앞 페이지 예)1
	private Integer end;				// 모든 링크의 맨 마지막 페이지 번호
	private Integer prev;			// 이전 링크 목록의 첫페이지 번호
	private final Integer blockSize;		// 행 수
	private Integer pageCount;	// 페이지 총 수
	private final Integer listCount;		// 링크 목록의 갯수

	private Integer startRowNum;
	private Integer endRowNum;

	/**
	 *
	 * @param totalCount the count of total row
	 * @param blockSize the number of a row in the list
	 * @param listCount the number of a page number shown in the list
	 * @param current number of current page
	 */
	public DefaultPaging(int totalCount, int blockSize,int listCount, int current)
	{
		this.totalCount = totalCount;
		this.blockSize = blockSize;
		this.current = current;
		this.listCount = listCount;

		initPage();

		if(totalCount > 0 )	makePagingNumber();
		else makeDefaultPagingNumber();
	}

	private void initPage()
	{
		pageCount = totalCount / blockSize;
		if(totalCount % blockSize > 0) pageCount++;
		if(current > pageCount) current = pageCount;

		start = (current / listCount) * listCount + 1;

		if(start > current) start = start - listCount;

		end = start + listCount - 1;
		next = start + listCount;
		last = pageCount;
		prev = start - listCount;
		first = 1;

		if(end > pageCount) end = pageCount;
		if(next > pageCount) next = pageCount;

		startRowNum = (current - 1) * blockSize + 1;
		endRowNum = startRowNum + blockSize - 1;
	}


	private void makePagingNumber()
	{
		list = new ArrayList<StyleSheet>();

		StyleSheet sh = null;

		for(int pageNumber=start;pageNumber<=end;pageNumber++)
		{
			if(current == pageNumber) sh = new StyleSheet("on",String.valueOf(pageNumber),pageNumber);
			else sh = new StyleSheet("off",String.valueOf(pageNumber),pageNumber);
			list.add(sh);

		}

		if(end <pageCount)
		{
			sh = new StyleSheet("off",">",next);
			list.add(sh);
			sh = new StyleSheet("off",">>",pageCount);
			list.add(sh);

		}


		if(start > first){
			sh = new StyleSheet("off","<",prev);
			list.add(0,sh);
			sh = new StyleSheet("off","<<",1);
			list.add(0,sh);
		}

	}

	private void makeDefaultPagingNumber()
	{
		list = new ArrayList<StyleSheet>();
	}

	public Integer getLast() {
		return last;
	}
	public Integer getStart() {
		return start;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public Integer getNext() {
		return next;
	}
	public Integer getCurrent() {
		return current;
	}
	public StyleSheet [] getList() {
		return list.toArray(new StyleSheet[list.size()]);
	}
	public Integer getFirst() {
		return first;
	}
	public Integer getEnd() {
		return end;
	}
	public Integer getPrev() {
		return prev;
	}

	public int getStartRowNum() {
		return startRowNum.intValue();
	}
	public int getEndRowNum() {
		return endRowNum.intValue();
	}
}
