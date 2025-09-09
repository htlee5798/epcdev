/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-bos
 * @since    : 2016
 * @Description : BOS에서 공통으로 사용하는 엑셀파일 복호화 서비스 인터페이스
 * @author : ksh
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */package com.lottemart.epc.common.service;

 import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.util.DataMap;
 
public interface ExcelFileMngService {

	/**
	 * 엑셀파일 복호화 후 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일 컬럼수와 colNms의 갯수가 맞아야 함.
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadExcelFile
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수(기본=1)
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadExcelFile(HttpServletRequest request) throws Exception;
}
