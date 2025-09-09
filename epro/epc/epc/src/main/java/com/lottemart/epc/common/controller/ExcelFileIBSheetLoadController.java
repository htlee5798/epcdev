package com.lottemart.epc.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.service.ExcelFileMngService;

/**
 * @Class Name :
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 19. 오후 2:02:11 sjKim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class ExcelFileIBSheetLoadController {

	protected final Log logger = LogFactory.getLog(getClass());

	// 첨부파일 관련
	@Resource(name = "ExcelFileMngService")
	private ExcelFileMngService excelFileMngService;

	@RequestMapping("excelLoad/IBSheetExcelLoad.do")
	public String doSearchShopingListIbs(HttpServletRequest request)
			throws Exception {

		try {
			// String[] colNms =
			// "siteCd^intCd^item^itemTp^begin^end".split("\\^");
			// int hdRow = 1;

			logger.debug("==============================================");

			List<DataMap> list = excelFileMngService
					.readUploadExcelFile(request);

			logger.debug("excelData length===>" + list.size());
			request.setAttribute("excelData", list);
		} catch (Exception e) {
			logger.error("IBSheetExcelLoad.do 실행 시 에러 발생", e);
			request.setAttribute("error", e.getMessage());
		}
		return "common/ibsheetExcelData2";
	}
}
