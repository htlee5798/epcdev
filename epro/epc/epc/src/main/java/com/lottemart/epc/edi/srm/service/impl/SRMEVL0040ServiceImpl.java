package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.SRMEVL0040Dao;
import com.lottemart.epc.edi.srm.dao.SRMEVL0050Dao;
import com.lottemart.epc.edi.srm.model.SRMEVL0040ListVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0040VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMEVL0040Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 품질경영평가  > 품질경영평가  ServiceImpl
 *
 * @author AN TAE KYUNG
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일 				수정자				수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	AN TAE KYUNG	최초 생성
 *
 */

@Service("srmevl0040Service")
public class SRMEVL0040ServiceImpl implements SRMEVL0040Service {

	@Autowired
	private SRMEVL0040Dao srmevl0040Dao;

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(SRMEVL0050ServiceImpl.class);

	/**
	 * 품질경영평가 Tab List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlTabList(SRMEVL0040VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return srmevl0040Dao.selectEvlTabList(vo);
	}

	/**
	 * 품질경영평가 Item
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlItem(SRMEVL0040VO vo, HttpServletRequest request) throws Exception {
		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		return srmevl0040Dao.selectEvlItem(vo);
	}

	/**
	 * 품질경영평가 등록
	 * @param SRMEVL0040ListVO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertQualityEvaluation(SRMEVL0040ListVO listVo, HttpServletRequest request) throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//세션정보 set
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

		//최초 평가등록 여부
		if(listVo.size() != 0) {
			SRMEVL0040VO vo = listVo.get(0);
			if (StringUtil.isEmpty(srmevl0040Dao.selectQualityEvaluationEvalStartDateCheck(vo))) {
				vo.setEvUserId(srmSessionVO.getEvUserId());
				srmevl0040Dao.updateQualityEvaluationEvalStartDate(vo);
			}

			for(int i=0; i<listVo.size(); i++ ) {
				vo = listVo.get(i);
				vo.setEvUserId(srmSessionVO.getEvUserId());

				if (srmevl0040Dao.selectQualityEvaluationCheck(vo) > 0) {
					//수정
					srmevl0040Dao.updateQualityEvaluation(vo);
				} else {
					//등록
					srmevl0040Dao.insertQualityEvaluation(vo);
				}
			}
		}

		resultMap.put("message","SUCCESS");
		return resultMap;
	}


	/**
	 * 품질평가 모두 입력 여부 Check
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public int selectQualityEvaluationEvalCheck(SRMEVL0040VO vo, HttpServletRequest request) throws Exception {
		int cnt = 0;
		if(!"300".equals(vo.getProgressCode())){
			SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
			vo.setEvUserId(srmSessionVO.getEvUserId());
			cnt = srmevl0040Dao.selectQualityEvaluationEvalCheck(vo);
			if (cnt == 0) {
				srmevl0040Dao.updateQualityEvaluationEvScore(vo);
			}
		}
		return cnt;
	}


	/**
	 * 엑셀파일 업로드
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public HashMap<String,Object> selectQualityEvaluationItemListExcelUpload(SRMEVL0040VO vo, HttpServletRequest request) throws Exception {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		SRMSessionVO srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));
		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		MultipartFile arrayFile = vo.getExcelFile();


		if(arrayFile.getSize() != 0) {
			//파일 저장
			CommonFileVO fileVo = new CommonFileVO();

			String attchFileNo = "";
			if(StringUtil.isEmpty(vo.getAttachFileNo())) {
				attchFileNo = String.valueOf(System.currentTimeMillis());
			} else {
				attchFileNo = vo.getAttachFileNo();
			}

			fileVo = new CommonFileVO();

			if(StringUtil.isNotEmpty(arrayFile.getOriginalFilename())) {
				String detailImageExt = FilenameUtils.getExtension(arrayFile.getOriginalFilename());
//				String serverFileName = "1000_"+String.valueOf(System.currentTimeMillis()) + "." + detailImageExt;
				String serverFileName = vo.getEvalNoResult() + "_" + vo.getVisitSeq() + "." + detailImageExt;

				String originalFilename = arrayFile.getOriginalFilename();
				String filename = originalFilename.replace("."+detailImageExt, "");

				String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				filename =filename.replaceAll(match, " ");

				originalFilename = filename + "." + detailImageExt;

				fileVo.setFileId(attchFileNo);
				fileVo.setTempFileName(serverFileName);
				fileVo.setFileNmae(originalFilename);
				fileVo.setFileSize(String.valueOf(arrayFile.getSize()));
				fileVo.setRegId(vo.getEvUserId());

				srmevl0050Dao.insertQualityEvaluationFile(fileVo);

				FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + serverFileName);
				try {
					FileCopyUtils.copy(arrayFile.getInputStream(),	listImageOs);
				} catch (Exception e) {
					logger.debug(e.toString());
				} finally {
					if (listImageOs != null) {
						try {
							listImageOs.close();
							} catch (Exception e) {
								logger.debug("error : " + e.getMessage());
							}
						}
				}

				//파일읽기

				FileInputStream fis = new FileInputStream(imageUploadPath + "/" + serverFileName);
//				FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\품질경영평가_정상_4\\품질경영평가_정상_1.xls");
				HSSFWorkbook workbook = null;

				try {
					workbook = new HSSFWorkbook(fis);
				} catch (Exception e) {
					logger.debug(e.toString());
				} finally {
					if (fis != null) {
						try {
							fis.close();
					} catch (Exception e) {
						logger.debug("error : " + e.getMessage());
						}
					}
				}

				HSSFRow row;
				HSSFSheet sheet;

				SRMEVL0040ListVO listVo = new SRMEVL0040ListVO();
				String beforeEvItemNo = "";
				//시트수
				for(int i=0; i < workbook.getNumberOfSheets(); i++){
					sheet = workbook.getSheetAt(i);

					//헤더 검사
					row = sheet.getRow(1); // row 가져오기
					HSSFCell header1 = row.getCell(8);								//No
					HSSFCell header2 = row.getCell(9);								//점검기준
					HSSFCell header3 = row.getCell(10);								//점수
					HSSFCell header4 = row.getCell(11);								//가이드라인
					HSSFCell header5 = row.getCell(12);								//평가
					HSSFCell header6 = row.getCell(13);								//선택점수

					if(!header1.getStringCellValue().equals(messageSource.getMessage("table.srm.colum.title.no", null, Locale.getDefault()))
							|| !header2.getStringCellValue().equals(messageSource.getMessage("table.srm.srmevl0040.colum.title2", null, Locale.getDefault()))
							|| !header3.getStringCellValue().equals(messageSource.getMessage("table.srm.srmevl0040.colum.title3", null, Locale.getDefault()))
							|| !header4.getStringCellValue().equals(messageSource.getMessage("table.srm.srmevl0040.colum.title4", null, Locale.getDefault()))
							|| !header5.getStringCellValue().equals(messageSource.getMessage("table.srm.srmevl0040.colum.title5", null, Locale.getDefault()))
							|| !header6.getStringCellValue().equals(messageSource.getMessage("table.srm.srmevl0040.colum.title6", null, Locale.getDefault()))){
//						FileUtil.delete(new File(imageUploadPath + "/" + serverFileName));
						resultMap.put("message","FAIL-HEADER");			//헤더 변경됨
						return resultMap;
					}
					// 로우수
					for(int j=2; j < sheet.getPhysicalNumberOfRows(); j++){

						SRMEVL0040VO excelVo = new SRMEVL0040VO();
						row = sheet.getRow(j); // row 가져오기
						HSSFCell houseCode = row.getCell(0);								//houseCode
						HSSFCell sgNo = row.getCell(1);										//sgNo
						HSSFCell evNo = row.getCell(2);										//evNo
						HSSFCell vendorCode = row.getCell(3);								//vendorCode
						HSSFCell evTplNo = row.getCell(4);									//evTplNo
						HSSFCell evItemNo = row.getCell(5);									//evItemNo
						HSSFCell evIdSeq = row.getCell(6);									//evIdSeq
						HSSFCell evIdScoreVal = row.getCell(7);								//evIdScoreVal

						HSSFCell evIdScoreSelect = row.getCell(13);							//선택점수

						excelVo.setHouseCode(houseCode.getStringCellValue());
						excelVo.setSgNo(sgNo.getStringCellValue());
						excelVo.setEvNo(evNo.getStringCellValue());
						excelVo.setVendorCode(vendorCode.getStringCellValue());
						excelVo.setEvTplNo(evTplNo.getStringCellValue());
						excelVo.setEvItemNo(evItemNo.getStringCellValue());
						excelVo.setEvIdSeq(evIdSeq.getStringCellValue());
						excelVo.setEvIdScoreVal(evIdScoreVal.getStringCellValue());

						if(!vo.getEvTplNo().equals(excelVo.getEvTplNo())){
//							FileUtil.delete(new File(imageUploadPath + "/" + serverFileName));
							resultMap.put("message","FAIL-EV_TPL_NO");			//해당 평가 템플릿이 아님
							return resultMap;
						} else {
							if(StringUtil.upperCase(evIdScoreSelect.getStringCellValue()).equals("O")){
								if(beforeEvItemNo.equals(excelVo.getEvItemNo())){
									resultMap.put("message","FAIL-EV_ITEM_NO");			//같은항목 중복체크됨
//									FileUtil.delete(new File(imageUploadPath + "/" + serverFileName));
									return resultMap;
								}
								//삭제 및 빈값 수정 여부
								if(StringUtil.isEmpty(excelVo.getHouseCode())
										|| StringUtil.isEmpty(excelVo.getSgNo())
										|| StringUtil.isEmpty(excelVo.getEvNo())
										|| StringUtil.isEmpty(excelVo.getVendorCode())
										|| StringUtil.isEmpty(excelVo.getEvTplNo())
										|| StringUtil.isEmpty(excelVo.getEvItemNo())
										|| StringUtil.isEmpty(excelVo.getEvIdSeq())
										|| StringUtil.isEmpty(excelVo.getEvIdScoreVal())){
//									FileUtil.delete(new File(imageUploadPath + "/" + serverFileName));
									resultMap.put("message","FAIL-NULL");
									return resultMap;
								}

								listVo.add(excelVo);
								beforeEvItemNo= excelVo.getEvItemNo();
							}
						}
					}

				}
				if(listVo != null){
					SRMEVL0040VO srmevl0040Vo = listVo.get(0);
					if (StringUtil.isEmpty(srmevl0040Dao.selectQualityEvaluationEvalStartDateCheck(srmevl0040Vo))) {
						srmevl0040Vo.setEvUserId(srmSessionVO.getEvUserId());
						srmevl0040Dao.updateQualityEvaluationEvalStartDate(srmevl0040Vo);
					}

					for(int i=0; i<listVo.size(); i++ ){
						srmevl0040Vo = listVo.get(i);
						srmevl0040Vo.setEvUserId(srmSessionVO.getEvUserId());

						if (srmevl0040Dao.selectQualityEvaluationCheck(srmevl0040Vo) > 0) {
							//수정
							srmevl0040Dao.updateQualityEvaluation(srmevl0040Vo);
						} else {
							//등록
							srmevl0040Dao.insertQualityEvaluation(srmevl0040Vo);
						}
					}
				}
			}
			vo.setAttachFileNo(attchFileNo);
			if(srmevl0050Dao.selectQualityEvaluationFileList(vo.getAttachFileNo()).size() == 0) {
				vo.setAttachFileNo("");
			}
//			srmevl0050Dao.updateQualityEvaluationFile(vo);
		}

		resultMap.put("message","success");
		return resultMap;
	}

}
