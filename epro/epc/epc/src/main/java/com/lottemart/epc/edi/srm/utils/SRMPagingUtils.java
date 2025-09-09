package com.lottemart.epc.edi.srm.utils;

import lcn.module.common.paging.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class SRMPagingUtils {

	/**
	 *
	 * @param paginationInfo
	 * @param function(페이지 넘버 Click Event명으로 JSP에 function으로 생성해서 사용)
	 * @return
	 */

	public static String makingPagingContents(PaginationInfo paginationInfo, String function){
		StringBuffer strBuff = new StringBuffer();

		int firstPageNo = paginationInfo.getFirstPageNo();
		int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
		int totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getPageSize();
		int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		int currentPageNo = paginationInfo.getCurrentPageNo();
		int lastPageNo = paginationInfo.getLastPageNo();

		String firstPageLabel = "<button type='button' onclick=\"{0}({1}); return false;\"><img src='/images/epc/edi/srm/board_img/arrow_l_end.gif'></button>\n";
		String previousPageLabel = "<button type='button' onclick=\"{0}({1}); return false;\"><img src='/images/epc/edi/srm/board_img/arrow_l.gif'></button>\n";
		String currentPageLabel = "<a href=\"#\" onclick=\"\" class=\"on\">{0}</a>\n";
		String otherPageLabel = "<a href=\"#\" onclick=\"{0}({1});\">{2}</a>\n";
		String nextPageLabel = "<button type='button' onclick=\"{0}({1}); return false;\"><img src='/images/epc/edi/srm/board_img/arrow_r.gif'></button>\n";
		String lastPageLabel = "<button type='button' onclick=\"{0}({1}); return false;\"><img src='/images/epc/edi/srm/board_img/arrow_r_end.gif'></button>\n";

		if (totalPageCount > pageSize) {
			if (firstPageNoOnPageList > pageSize){
				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { function, Integer.toString(firstPageNo) }));
				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { function, Integer.toString(firstPageNoOnPageList - 1) }));
			} else {
				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { function, Integer.toString(firstPageNo) }));
				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { function, Integer.toString(firstPageNo) }));
			}
		}

		for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				strBuff.append(MessageFormat.format(currentPageLabel, new Object[] { Integer.toString(i) }));
			} else {
				strBuff.append(MessageFormat.format(otherPageLabel, new Object[] { function, Integer.toString(i), Integer.toString(i) }));
			}
		}

		if (totalPageCount > pageSize) {
			if (lastPageNoOnPageList < totalPageCount) {
				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { function, Integer.toString(firstPageNoOnPageList + pageSize) }));
				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { function, Integer.toString(lastPageNo) }));
			} else {
				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { function, Integer.toString(lastPageNo) }));
				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { function, Integer.toString(lastPageNo) }));
			}
		}
		return strBuff.toString();
	}
}
