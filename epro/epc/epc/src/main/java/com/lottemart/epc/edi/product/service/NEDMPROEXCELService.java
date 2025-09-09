package com.lottemart.epc.edi.product.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.lottemart.epc.edi.product.model.ExcelTempUploadVO;
import com.lottemart.epc.edi.product.model.Result;

public interface NEDMPROEXCELService {

	/**
	 * NEW 엑셀 업로드 
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	public Result insertExcelUpload(ExcelTempUploadVO paramVo , HttpServletRequest request, MultipartFile file) throws Exception;
}
