package com.lottemart.epc.system.service.impl;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.dao.PSCMSYS0005Dao;
import com.lottemart.epc.system.model.PSCMSYS0005DtlVO;
import com.lottemart.epc.system.model.PSCMSYS0005VO;
import com.lottemart.epc.system.service.PSCMSYS0005Service;

/**
 * @Class Name : PSCMSYS0005ServiceImpl.java
 * @Description : 전상법 템플릿 Service 구현체
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 28. 오후 5:15:55 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmsys0005Service")
public class PSCMSYS0005ServiceImpl implements PSCMSYS0005Service {
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0005ServiceImpl.class);

	@Autowired
	private PSCMSYS0005Dao pscmsys0005Dao;

	// 전상법 템플릿 검색
	@Override
	public List<PSCMSYS0005VO> searchEscTem(Map<String, String> paramMap)
			throws Exception {
		return pscmsys0005Dao.searchEscTem(paramMap);
	}

	// 저상법 템플릿 Cnt
	@Override
	public int searchEscTemCnt(Map<String, String> paramMap) throws Exception {
		return pscmsys0005Dao.searchEscTemCnt(paramMap);
	}

	// 전상법 템플릿 삭제
	@Override
	public int deleteEscTem(DataMap dataMap) throws Exception {
		int resultCnt = 0;
		PSCMSYS0005VO pscmsys0005vo = null;
		try {
			String[] norProdSeq = dataMap.getString("norProdSeq").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			for (int i = 0; i < norProdSeq.length; i++) {
				pscmsys0005vo = new PSCMSYS0005VO();
				// SEQ
				pscmsys0005vo.setNorProdSeq(norProdSeq[i]);
				logger.debug("norProdSeq[" + i + "] :: " + norProdSeq[i]);
				resultCnt += pscmsys0005Dao.deleteEscTem(pscmsys0005vo);
			}
		} catch (Exception e) {
			resultCnt = 0;
			logger.debug(e.getMessage());
		}
		logger.debug("resultCnt :: " + resultCnt);
		return resultCnt;
	}

	//전상법 저장
	@Override
	public int insertMasEscTem(HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		try {	
		//저장할 데이터
		String title = URLDecoder.decode(request.getParameter("title"), "UTF-8");
		String useYn = request.getParameter("useYn");
		String vendorId = (String) request.getAttribute("vendorId");
		String regId = (String) request.getAttribute("regId");
		String infoGrpCd = request.getParameter("infoGrpCd");
		
		String[] infoColCd =  request.getParameterValues("INFOCOLCD");
		String[] colVal  = request.getParameterValues("colVal");
		
		//전상법 SEQ 추가
		String norProdSeq =  pscmsys0005Dao.selectEscTemSql();
		logger.debug(norProdSeq);

		//master table 데이터 가공
		PSCMSYS0005VO pscmsys0005vo = new PSCMSYS0005VO();
		pscmsys0005vo.setNorProdSeq(norProdSeq);
		pscmsys0005vo.setTitle(title);
		pscmsys0005vo.setUseYn(useYn);
		pscmsys0005vo.setRegId(regId);
		pscmsys0005vo.setVendorId(vendorId);
		pscmsys0005vo.setInfoGrpCd(infoGrpCd);
		
		pscmsys0005Dao.insertMasEscTem(pscmsys0005vo);
		
		//logger.debug(infoColCd.length);
		//master table insert
		for(int i =0; i<infoColCd.length;i++){
		/*	if(!colVal[i].equals("")){
				logger.debug(!colVal[i].equals(""));
				//colVal 있을 경우
*/				PSCMSYS0005DtlVO pscmsys0005DtlVO = new PSCMSYS0005DtlVO();
				pscmsys0005DtlVO.setNorProdSeq(norProdSeq);
				pscmsys0005DtlVO.setInfoGrpCd(infoGrpCd);
				pscmsys0005DtlVO.setInfoColCd(infoColCd[i]);
				pscmsys0005DtlVO.setColVal(colVal[i]);
				pscmsys0005DtlVO.setRegId(regId);
				//sub table insert
				pscmsys0005Dao.insertSubEscTem(pscmsys0005DtlVO);
			/*}else{
				logger.debug(!colVal[i].equals(""));
				//colVal 없을 경우
				colVal[i]=null;
				PSCMSYS0005DtlVO pscmsys0005DtlVO = new PSCMSYS0005DtlVO();
				pscmsys0005DtlVO.setNorProdSeq(norProdSeq);
				pscmsys0005DtlVO.setInfoGrpCd(infoGrpCd);
				pscmsys0005DtlVO.setInfoColCd(infoColCd[i]);
				pscmsys0005DtlVO.setColVal(colVal[i]);
				pscmsys0005DtlVO.setRegId(regId);
				//sub table insert
				pscmsys0005Dao.insertSubEscTem(pscmsys0005DtlVO);
			}*/
		}
		resultCnt=1;		
		
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		return resultCnt;
	}

	//
	@Override
	public PSCMSYS0005VO dtlEscTem(String norProdSeq) throws Exception {
		return pscmsys0005Dao.dtlEscTem(norProdSeq) ;
	}

	//
	@Override
	public PSCMSYS0005DtlVO dtlEscTemDtl(String norProdSeq) throws Exception {
		return pscmsys0005Dao.dtlEscTemDtl(norProdSeq);
	}

	//IBSHEET 세팅
	@Override
	public List<PSCMSYS0005DtlVO> selectSheet(DataMap paramMap)
			throws Exception {
		return pscmsys0005Dao.selectSheet(paramMap);
	}

	//전상법 수정
	@Override
	public int updateMasEscTem(HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		try {	
		//저장할 데이터
		String title = URLDecoder.decode(request.getParameter("title"), "UTF-8");
		String useYn = request.getParameter("useYn");
		String modId = (String) request.getAttribute("modId");
		String norProdSeq = request.getParameter("norProdSeq");
		String[] infoGrpCd = request.getParameterValues("infoGrpCd");
		String[] infoColCd =  request.getParameterValues("infoColCd");
		String[] colVal  = request.getParameterValues("colVal");
		

		//master table 데이터 가공
		PSCMSYS0005VO pscmsys0005vo = new PSCMSYS0005VO();
		pscmsys0005vo.setNorProdSeq(norProdSeq);
		pscmsys0005vo.setTitle(title);
		pscmsys0005vo.setUseYn(useYn);
		pscmsys0005vo.setModId(modId);
		
		pscmsys0005Dao.updateMasEscTem(pscmsys0005vo);
		if(colVal.length!=0){
		//master table insert
		for(int i =0; i<infoColCd.length;i++){		
			PSCMSYS0005DtlVO pscmsys0005DtlVO = new PSCMSYS0005DtlVO();
			pscmsys0005DtlVO.setNorProdSeq(norProdSeq);
			pscmsys0005DtlVO.setColVal(colVal[i]);
			pscmsys0005DtlVO.setInfoColCd(infoColCd[i]);
			pscmsys0005DtlVO.setInfoGrpCd(infoGrpCd[i]);
			pscmsys0005DtlVO.setModId(modId);
			//sub table insert
			pscmsys0005Dao.updateSubEscTem(pscmsys0005DtlVO);
		}
		resultCnt=1;		
		}
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		return resultCnt;
	}

}
