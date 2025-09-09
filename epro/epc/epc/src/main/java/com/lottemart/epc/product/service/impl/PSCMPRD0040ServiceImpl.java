package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0040Dao;
import com.lottemart.epc.product.model.PSCMPRD0040DTLVO;
import com.lottemart.epc.product.model.PSCMPRD0040VO;
import com.lottemart.epc.product.service.PSCMPRD0040Service;

/**
 * 
 * @author khKim
 * @Description :
 * @Class : com.lottemart.epc.product.service.impl
 * 
 *        <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  
 * @version :
 * </pre>
 */
@Service("pscmprd0040Service")
public class PSCMPRD0040ServiceImpl implements PSCMPRD0040Service {
	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0040ServiceImpl.class);

	@Autowired
	private PSCMPRD0040Dao pscmprd0040Dao;

	// 셀링포인트 검색
	@Override
	public List<PSCMPRD0040VO> selectSelPoint(DataMap paramMap)
			throws Exception {
		return pscmprd0040Dao.selectSelPoint(paramMap);
	}

	// 셀링포인트 등록
	@Override
	public int selSave(DataMap paramMap) throws Exception {
		return pscmprd0040Dao.selSave(paramMap);
	}

	// 셀링포인트 총건수
	@Override
	public int selectSelPointCnt(Map<String, String> paramMap) throws Exception {
		return pscmprd0040Dao.selectSelPointCnt(paramMap);
	}

	// 셀링포인트 수정
	@Override
	public PSCMPRD0040VO selectSelPointView(String recommSeq) throws Exception {
		return pscmprd0040Dao.selectSelPointView(recommSeq);
	}

	// 셀링포인트 시트에서 수정
	@Override
	public int updateSelPont(DataMap paramMap) throws Exception {
		// 처리 수행
		int resultCnt = 0;
		PSCMPRD0040VO pscmprd0040vo = null;
		try {
			
			String[] mdTalkSeq = paramMap.getString("mdTalkSeq").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] useYn = paramMap.getString("useYn").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");

			String regId = paramMap.getString("regId");
			for (int i = 0; i < mdTalkSeq.length; i++) {
				pscmprd0040vo = new PSCMPRD0040VO();
				// SEQ
				pscmprd0040vo.setMdTalkSeq(mdTalkSeq[i]);
				logger.debug("mdTalkSeq[" + i + "] :: " + mdTalkSeq[i]);
				// USEYN
				pscmprd0040vo.setUseYn(useYn[i]);
				logger.debug("useYn[" + i + "] :: " + useYn[i]);
				// MODID
				pscmprd0040vo.setModId(regId);
				logger.debug("ModId :: " +regId);

				logger.debug("pscmprd0040vo");
				resultCnt += pscmprd0040Dao.updateSelPont(pscmprd0040vo);
			}
		} catch (Exception e) {
			resultCnt = 0;
			logger.debug(e.getMessage());
		}
		logger.debug("resultCnt :: " + resultCnt);
		return resultCnt;
	}

	// 셀링포인트  삭제
	@Override
	public void deleteSelPont(String mdTalkSeq) throws Exception {
		try {
			pscmprd0040Dao.deleteSelPont(mdTalkSeq);
		} catch (Exception e) {
			new Exception(" 셀링포인트  삭제중 에러");
		}
				
		
	}

	// 셀링포인트 수정
	@Override
	public int editSelPont(DataMap paramMap) throws Exception {
		int resultCnt = 0;
		try {
			// 마스터 저장
			pscmprd0040Dao.editSelPont(paramMap);
			
			//디테일 삭제
			pscmprd0040Dao.deleteDtlSelPoint(paramMap);
			
			//디테일 저장
			String[] applyToCd = paramMap.getString("applyToCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			String[] applyToTypeCd = paramMap.getString("applyToTypeCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
			for(int i=0; i<applyToCd.length;i++){
				paramMap.put("applyToCd", applyToCd[i]);
				paramMap.put("applyToTypeCd", applyToTypeCd[i]);
				resultCnt = pscmprd0040Dao.insertAllPoint(paramMap);
			}
			
		} catch (Exception e) {
			logger.debug("editSelPont error : "+e.getMessage());
		}
		return resultCnt;
	}
	

	// 저장 수정
	@Override
	public int insertAllPoint(DataMap paramMap) throws Exception {
		int cnt = 0;
		String[] applyToCd = paramMap.getString("applyToCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
		String[] applyToTypeCd = paramMap.getString("applyToTypeCd").replace("[", "").replace("]", "").replaceAll("\\p{Z}", "").split(",");
		for(int i=0; i<applyToCd.length;i++){
			paramMap.put("applyToCd", applyToCd[i]);
			paramMap.put("applyToTypeCd", applyToTypeCd[i]);
			cnt =+ pscmprd0040Dao.insertAllPoint(paramMap);
		}
		return cnt;
	}

	@Override
	public List<PSCMPRD0040DTLVO> selectSelSheet(DataMap paramMap)
			throws Exception {
		return pscmprd0040Dao.selectSelSheet(paramMap);
	}

	//셀링포인트 디테일 삭제
	@Override
	public int deleteDtlSelPoint(DataMap paramMap) throws Exception {
		return pscmprd0040Dao.deleteDtlSelPoint(paramMap);
	}

}
