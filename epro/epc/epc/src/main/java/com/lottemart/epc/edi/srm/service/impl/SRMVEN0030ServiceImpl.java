package com.lottemart.epc.edi.srm.service.impl;


import com.diquest.ir.util.common.StringUtil;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.srm.dao.SRMEVL0050Dao;
import com.lottemart.epc.edi.srm.dao.SRMJON0041Dao;
import com.lottemart.epc.edi.srm.dao.SRMRST0020Dao;
import com.lottemart.epc.edi.srm.dao.SRMVEN0030Dao;
import com.lottemart.epc.edi.srm.model.SRMVEN003001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0030VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0030Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.file.FileUtil;
import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * SRM 모니터링 ServiceImpl
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.18  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Service("srmven0030Service")
public class SRMVEN0030ServiceImpl implements SRMVEN0030Service {

	@Autowired
	private SRMVEN0030Dao srmven0030Dao;

	@Autowired
	private SRMRST0020Dao srmrst0020Dao;

	@Autowired
	private SRMJON0041Dao srmjon0041Dao;

	@Autowired
	private SRMEVL0050Dao srmevl0050Dao;



	@Autowired
	private ConfigurationService config;

	private static final Logger logger = LoggerFactory.getLogger(SRMVEN0030ServiceImpl.class);

	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMVEN0030VO vo) throws Exception {
		return srmven0030Dao.selectCatLv1CodeList(vo);
	}

	/**
	 * SRM 모니터링 LIST
	 * @param SRMVEN0030VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectSRMmoniteringList(SRMVEN0030VO vo, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//세션에서 로그인한 사용자 업체코드 받아오기
		if(epcLoginVO != null) {
			if (StringUtil.isEmpty(vo.getVenCd())) {
				ArrayList venCds = new ArrayList();
				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					venCds.add(epcLoginVO.getVendorId()[i]);
				}

				vo.setVenCds(venCds);
			} else {
				ArrayList venCds = new ArrayList();
				venCds.add(vo.getVenCd());
				vo.setVenCds(venCds);
			}
		}

		Map<String,Object> result = new HashMap<String,Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		result.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex()+1);
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = srmven0030Dao.selectSRMmoniteringListCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<SRMVEN0030VO>	resultList 	= 	srmven0030Dao.selectSRMmoniteringList(vo);
		result.put("listData", resultList);

		// 화면에 보여줄 게시물 리스트
		result.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			result.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}

		return result;
	}



	/**
	 * SRM 모니터링 상세조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetail(SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		//locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmven0030Dao.selectSRMmoniteringDetail(vo);
	}



	/**
	 * SRM 모니터링 상세조회 차트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailChart(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailChart(vo);
	}


	/**
	 * 현재까지 점수
	 * @param SRMVEN003001VO
	 * @return HashMap<String, List<SRMVEN003001VO>>
	 * @throws Exception
	 */
	public HashMap<String, List<SRMVEN003001VO>> selectSRMmoniteringDetailCurrentValue(SRMVEN003001VO vo) throws Exception {
		HashMap<String, List<SRMVEN003001VO>> resultMap = new HashMap();
		vo.setVendorYn("Y");
		resultMap.put("compCurrentValue", srmven0030Dao.selectSRMmoniteringDetailCurrentValue(vo));		//업체
		vo.setVendorYn("N");
		resultMap.put("allCurrentValue", srmven0030Dao.selectSRMmoniteringDetailCurrentValue(vo));		//전체

		return resultMap;
	}

	/**
	 * 고객컴플레인수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailClaim(vo);
	}

	/**
	 * 등급조회
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailGrade(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailGrade(vo);
	}

	/**
	 * 비고, 특이사항 조회
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailRemarkEtc(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailRemarkEtc(vo);
	}

	/**
	 * 등급 예제 조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailGradeExemple(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailGradeExemple(vo);
	}


	/**
	 * PLC등급 리스트 조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailPlc(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailPlc(vo);
	}

	/**
	 * 불량등록건수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailDefective(vo);
	}



	/**
	 * 불량등록건수 리스트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailDefectiveList(SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		//locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmven0030Dao.selectSRMmoniteringDetailDefectiveList(vo);
	}


	/**
	 * 개선조치
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailImpReq(SRMVEN003001VO vo, HttpServletRequest request) throws Exception {
		vo.setLocale(SRMCommonUtils.getLocale(request));
		return srmven0030Dao.selectSRMmoniteringDetailImpReq(vo);
	}


	/**
	 * 개선조치 등옥
	 * @param SRMVEN003001VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateSRMmoniteringDetailImpReq(SRMVEN003001VO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//파일 업로드
		String imageUploadPath = config.getString("edi.srm.file.path");

		ArrayList<MultipartFile> arrayFile = vo.getAttachFileNoFile();

		ArrayList docSeq = vo.getDocSeq();
		List<CommonFileVO> fileList = srmrst0020Dao.selectCompCounselFileList(vo.getAttachNo());
		for(CommonFileVO fileVo: fileList) {
			if(docSeq.size() > 0) {
				boolean fileDel = true;
				for(int j=0; j < docSeq.size(); j++){
					if(fileVo.getFileSeq().equals(docSeq.get(j))){
						fileDel = false;
					}
				}
				if(fileDel) {
					//파일 삭제
					srmjon0041Dao.deleteHiddenCompFile(fileVo);

					//서버파일 삭제
					File file = new File(imageUploadPath + "/" + fileVo.getTempFileName());
					if (file.isFile()) {
						FileUtil.delete(file);
					}
				}
			} else {
				//파일 삭제
				srmjon0041Dao.deleteHiddenCompFile(fileVo);

				//서버파일 삭제
				File file = new File(imageUploadPath + "/" + fileVo.getTempFileName());
				if (file.isFile()) {
					FileUtil.delete(file);
				}
			}

		}

		if(arrayFile.size() != 0) {
			//파일 저장
			CommonFileVO fileVo = new CommonFileVO();

			String attchFileNo = "";
			if(StringUtil.isEmpty(vo.getAttachNo())) {
				attchFileNo = String.valueOf(System.currentTimeMillis());
			} else {
				attchFileNo = vo.getAttachNo();
			}

			for(int i=0; i < arrayFile.size(); i++){
				fileVo = new CommonFileVO();

				if(lcn.module.common.util.StringUtil.isNotEmpty(arrayFile.get(i).getOriginalFilename())) {
					String detailImageExt = FilenameUtils.getExtension(arrayFile.get(i).getOriginalFilename());
					String serverFileName = "1000_"+System.currentTimeMillis() + "." + detailImageExt;

					String originalFilename = arrayFile.get(i).getOriginalFilename();
					String filename = originalFilename.replace("."+detailImageExt, "");

					String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
					filename =filename.replaceAll(match, " ");


					originalFilename = filename + "." + detailImageExt;

					fileVo.setFileId(attchFileNo);
					fileVo.setTempFileName(serverFileName);
					fileVo.setFileNmae(originalFilename);
					fileVo.setFileSize(String.valueOf(arrayFile.get(i).getSize()));
					fileVo.setRegId(vo.getVendorCode());

					srmevl0050Dao.insertQualityEvaluationFile(fileVo);

					FileOutputStream listImageOs = new FileOutputStream(imageUploadPath + "/" + serverFileName);

					try {
						FileCopyUtils.copy(arrayFile.get(i).getInputStream(),	listImageOs);
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
				}
			}
			vo.setAttachNo(attchFileNo);
			if(srmevl0050Dao.selectQualityEvaluationFileList(vo.getAttachNo()).size() == 0) {
				vo.setAttachNo("");
			}
		}

		vo.setImpPlanDate(vo.getImpPlanDate().replaceAll("-",""));
		vo.setImpPlanDueDate(vo.getImpPlanDueDate().replaceAll("-",""));
		srmven0030Dao.updateSRMmoniteringDetailImpReq(vo);
		resultMap.put("status",vo.getTempYn());
		resultMap.put("message","success");
		return resultMap;
	}


	/**
	 * 첨부파일 조회
	 * @param SRMVEN003001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMVEN003001VO vo) throws Exception {
		return srmven0030Dao.selectSRMmoniteringDetailImpReqFileList(vo);
	}
}
