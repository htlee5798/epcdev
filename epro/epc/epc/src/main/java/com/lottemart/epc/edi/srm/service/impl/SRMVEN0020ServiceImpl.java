package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.dao.SRMVEN0020Dao;
import com.lottemart.epc.edi.srm.model.SRMVEN002001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0020VO;
import com.lottemart.epc.edi.srm.service.SRMJON0041Service;
import com.lottemart.epc.edi.srm.service.SRMVEN0020Service;

import lcn.module.common.file.FileUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SRM정보 / 인증정보 등록 ServiceImpl
 *
 * @author SHIN SE JIN
 * @since 2016.07.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.26  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmven0020Service")
public class SRMVEN0020ServiceImpl implements SRMVEN0020Service {

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private SRMVEN0020Dao srmven0020Dao;

	@Autowired
	private SRMJON0041Service srmjon0041Service;

	/**
	 * 인증등록 정보 내역 카운트
	 */
	public int selectCertiInfoAddListCount(SRMVEN0020VO vo) throws Exception {
		return srmven0020Dao.selectCertiInfoAddListCount(vo);
	}

	/**
	 * 인증등록정보 내역
	 */
	public List<SRMVEN0020VO> selectCertiInfoAddList(SRMVEN0020VO vo) throws Exception {
		return srmven0020Dao.selectCertiInfoAddList(vo);
	}

	/**
	 * 인증정보 내역
	 */
	public List<SRMVEN0020VO> selectCertiInfoList(SRMVEN0020VO vo) throws Exception {
		return srmven0020Dao.selectCertiInfoList(vo);
	}

	/**
	 * 상품검색 카운드
	 */
	public int selectProdInfoCount(SRMVEN002001VO vo) throws Exception {
		return srmven0020Dao.selectProdInfoCount(vo);
	}

	/**
	 * 상품검색
	 */
	public List<SRMVEN002001VO> selectProdInfo(SRMVEN002001VO vo) throws Exception {
		return srmven0020Dao.selectProdInfo(vo);
	}

	/**
	 * 파트너사 인증정보 등록 및 수정
	 */
	public String updateCertiInfo(SRMVEN0020VO vo) throws Exception {
		String msg = "fail";

		String detailImageExt = FilenameUtils.getExtension(vo.getCertiAttachNoFile().getOriginalFilename());
		if (detailImageExt != null) {
			detailImageExt = detailImageExt.toUpperCase();
		}

		if (detailImageExt != null &&
				!detailImageExt.equals("JPG") && !detailImageExt.equals("GIF") && !detailImageExt.equals("PNG") && !detailImageExt.equals("BMP")) {
			msg = "fail_invalid_ext";
			return msg;
		}

		/* 첨부파일 이름 (fileSave(첨부파일 이름, 첨부파일, 사업자등록번호, 삭제할 파일명))  */
		vo.setCertiAttachNo(srmjon0041Service.fileSave(vo.getCertiAttachNo(), vo.getCertiAttachNoFile(), vo.getVendorCode(), vo.getCertiAttachNo()));

		if (!StringUtil.isEmpty(vo.getSeq())) {
			srmven0020Dao.updateProdInfo(vo);	// 파트너사 인증정보가 있을 경우 UPDATE
			msg = "update";
		} else {
			srmven0020Dao.insertProdInfo(vo);	// 파트너사 인증정보가 없을 경우 INSERT
			msg = "insert";
		}
		return msg;
	}

	/**
	 * 파트너사 인증정보 삭제
	 */
	public String deleteCertiInfo(SRMVEN0020VO vo) throws Exception {

		ArrayList delList = vo.getDelList();

		HashMap<String, String> delDatas = null;

		if(delList != null) {
			for (int i = 0; i < delList.size(); i++) {
				delDatas = (HashMap<String, String>) delList.get(i);
				if(delDatas != null) {
					vo.setCertiTarget(delDatas.get("certiTarget"));
					vo.setCertiType(delDatas.get("certiType"));
					vo.setVenCd(delDatas.get("venCd"));
					vo.setSeq(delDatas.get("seq"));
					String fileId = srmven0020Dao.selectCertiAttachFileId(vo);	// 등록된 첨부파일 조회
					if (fileId != "") {
						fileDelete(fileId);
						srmven0020Dao.deleteCertiInfo(vo);
					}
				}
			}
		}

		return "success";
	}

	/**
	 * 파일 정보조회
	 */
	public CommonFileVO selectFileInfo(CommonFileVO vo) throws Exception {
		return srmven0020Dao.selectFileInfo(vo);
	}

	/**
	 * 파일삭제
	 */
	public void fileDelete(String fileId)throws Exception{
		CommonFileVO fileVo = new CommonFileVO();
		String fileUploadPath = config.getString("edi.srm.file.path");	//파일 업로드 경로

		if (!StringUtil.isEmpty(fileId)) {

			fileVo.setFileId(fileId);	//첨부파일 이름
			fileVo.setFileSeq("1");		//파일순번

			fileVo = srmven0020Dao.selectFileInfo(fileVo); //파일 DB 조회(문서번호, 문서항번, DEST파일명, SRC파일명)

			srmven0020Dao.deleteFile(fileVo);	//파일 DB 삭제

			String detailImageExt = FilenameUtils.getExtension(fileVo.getFileNmae());	//파일확장자
			String serverFileName = "1000_"+System.currentTimeMillis() + "." + detailImageExt;	//서버파일명

			File file = new File(fileUploadPath + "/" + serverFileName);	//파일업로드경로+서버파일명
			if (file.isFile()) {
				FileUtil.delete(file); //서버파일 삭제
			}
		}
	}

	/**
	 * 인증정보 정보 중복체크
	 */
	public int selectCertiInfoCheck(SRMVEN0020VO vo) throws Exception{
		return srmven0020Dao.selectCertiInfoCheck(vo);
	}

}
