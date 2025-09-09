package com.lottemart.common.sms.service.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottemart.common.sms.dao.LtNewSmsDAO;
import com.lottemart.common.sms.dao.LtsmsDAO;
import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.sms.service.LtsmsService;
import com.lottemart.common.util.DataMap;

import lcn.module.framework.idgen.IdGnrService;

/**
 * @author sunghoon
 * @Class : com.lottemart.common.sms.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * @version :  
 */
@Service("LtsmsService")
public class LtsmsServiceImpl implements LtsmsService {
	/**
	 * Logger for this class
	 */
	final static Logger logger = LoggerFactory.getLogger(LtsmsServiceImpl.class);
	
	 @Resource(name = "LtsmsDAO")
	 private LtsmsDAO ltsmsDAO;
	 
	 @Resource(name = "LtNewSmsDAO")
	 private LtNewSmsDAO LtNewSmsDAO;
	 
	@Resource(name = "SmsIdGnrService")
	private IdGnrService idgenService;

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.sms.service.LtsmsService#selectSmsInfs(com.lottemart.common.sms.model.LtsmsVO)
		 * @Method Name  : LtsmsServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.sms.service.impl
		 * @Description : SMS의 목록을 조회
	     * @param searchVO
	     * @return
	     * @throws Exception
	 */
	@Override
	public List<LtsmsVO> selectSmsInfs(LtsmsVO searchVO)
			throws Exception {
		return ltsmsDAO.selectSmsInfs(searchVO);
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.sms.service.LtsmsService#insertSmsSend(com.lottemart.common.sms.model.LtsmsVO)
		 * @Method Name  : LtsmsServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.sms.service.impl
		 * @Description : SMS 목록의 갯수 
	     * @param sms
	     * @return
	     * @throws Exception
	 */
	@Override
	public String insertSmsSend(LtsmsVO sms) throws Exception {
		String SERIALNO = idgenService.getNextStringId();
		
		sms.setSERIALNO(SERIALNO);
		
		try {
		
			ltsmsDAO.insertSmsInf(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return "1";
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.sms.service.LtsmsService#insertSmsListSend(java.util.List)
		 * @Method Name  : LtsmsServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.sms.service.impl
		 * @Description : 
	     * @param smses
	     * @return
	     * @throws Exception
	 */
	@Override
	public String insertSmsListSend(List<LtsmsVO> smses) throws Exception {
		Iterator<LtsmsVO> iter = smses.iterator();
		
		while(iter.hasNext()) {
			LtsmsVO ltsmsvo = (LtsmsVO) iter.next();
			this.insertSmsSend(ltsmsvo);
		}
		
		return "1";
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.sms.service.LtsmsService#selectSmsInf(com.lottemart.common.sms.model.LtsmsVO)
		 * @Method Name  : LtsmsServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.sms.service.impl
		 * @Description : SMS의 상세 내역을 조회
	     * @param searchVO
	     * @return
	     * @throws Exception
	 */
	@Override
	public LtsmsVO selectSmsInf(LtsmsVO searchVO) throws Exception {
		return ltsmsDAO.selectSmsInf(searchVO);
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.sms.service.LtsmsService#selectSmsInfsCnt(com.lottemart.common.sms.model.LtsmsVO)
		 * @Method Name  : LtsmsServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.sms.service.impl
		 * @Description : 
	     * @param searchVO
	     * @return
	     * @throws Exception
	 */
	@Override
	public int selectSmsInfsCnt(LtsmsVO searchVO) throws Exception {
		return ltsmsDAO.selectSmsInfsCnt(searchVO);
	}

	@Override
	public int selectNewSmsInfsCnt(LtnewsmsVO searchVO) throws Exception {
		return LtNewSmsDAO.selectNewSmsInfsCnt(searchVO);
	}

	@Override
	public List<LtnewsmsVO> selectNewSmsInfs(LtnewsmsVO searchVO) throws Exception {
		return LtNewSmsDAO.selectNewSmsInfs(searchVO);
	}
	
	@Override
	public String insertNewSmsSend_NoneException(LtnewsmsVO sms) {
		String tRAN_ETC1 = "";
		try {
			tRAN_ETC1 = idgenService.getNextStringId();
			
			sms.setTRAN_ETC1(tRAN_ETC1);
			LtNewSmsDAO.insertNewSmsInf(sms);
		}catch (Exception e) {
			logger.error("tRAN_ETC1["+tRAN_ETC1+"]"+sms.getTRAN_MSG()+"\n"+e.toString());
			return null;
		}
		return tRAN_ETC1;
	}

	@Override
	public String insertNewSmsSend(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		
		sms.setTRAN_ETC1(tRAN_ETC1);
		if(sms.getTRAN_CALLBACK() == null || "".equals(sms.getTRAN_CALLBACK())) {
    		sms.setTRAN_CALLBACK("15772500");
    	}				
		
		try {
	    	logger.debug("IMPL---TRAN_PHONE::::"+sms.getTRAN_PHONE());
	    	logger.debug("IMPL---CALLBACK::::"+sms.getTRAN_CALLBACK());
	    	logger.debug("IMPL---TRAN_STATUS::::"+sms.getTRAN_STATUS());
	    	logger.debug("IMPL---TRAN_TYPE::::"+sms.getTRAN_TYPE());
	    	logger.debug("IMPL---TRAN_ETC1::::"+sms.getTRAN_ETC1());
	    	logger.debug("IMPL---TRAN_ETC2::::"+sms.getTRAN_ETC2());
	    	logger.debug("IMPL---TRAN_ETC3::::"+sms.getTRAN_ETC3());
	    	logger.debug("IMPL---HOUR10::::"+sms.getHOUR10());
		
			LtNewSmsDAO.insertNewSmsInf(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return tRAN_ETC1;
	}
	
	@Override
	public List<String> insertNewSmsListSend(List<LtnewsmsVO> smses) throws Exception {
		Iterator<LtnewsmsVO> iter = smses.iterator();
		
		List<String> vos = new LinkedList<String>();
		
		while(iter.hasNext()) {
			LtnewsmsVO ltsmsvo = (LtnewsmsVO) iter.next();
			vos.add(this.insertNewSmsSend(ltsmsvo));
		}
		
		return vos;
	}

	@Override
	public LtnewsmsVO selectNewSmsInf(LtnewsmsVO searchVO) throws Exception {
		return LtNewSmsDAO.selectNewSmsInf(searchVO);
	}
	
	
	@Override
	public int selectNewSmsInfsCntCC(LtnewsmsVO searchVO) throws Exception {
		return LtNewSmsDAO.selectNewSmsInfsCntCC(searchVO);
	}

	@Override
	public List<LtnewsmsVO> selectNewSmsInfsCC(LtnewsmsVO searchVO) throws Exception {
		return LtNewSmsDAO.selectNewSmsInfsCC(searchVO);
	}

	/** (non-Javadoc)
 	 * @see com.lottemart.common.sms.service.LtsmsService#lmsCh2Proc(com.lottemart.common.sms.model.LtnewsmsVO)
	 * @Method Name  : LtsmsServiceImpl.java
	 * @since      : 2016
	 * @author     : 김학수
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.service.impl
	 * @Description : 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
	 */
	@Override
	public String lmsCh2Proc(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		
		sms.setTRAN_ETC1(tRAN_ETC1);
		
		
		try {
			
			LtNewSmsDAO.lmsCh2Proc(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return tRAN_ETC1;
	}
	
	/** (non-Javadoc)
	 * @see com.lottemart.common.sms.service.LtsmsService#lmsCh2Proc(com.lottemart.common.sms.model.LtnewsmsVO)
	 * @Method Name  : LtsmsServiceImpl.java
	 * @since      : 2017
	 * @author     : pwj
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.service.impl
	 * @Description : 
	 * @param LtsmsVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public String lmsCh2SmsProc(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		
		sms.setTRAN_ETC1(tRAN_ETC1);
		
		
		try {
			
			LtNewSmsDAO.lmsCh2SmsProc(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return tRAN_ETC1;
	}
	
	/** (non-Javadoc)
 	 * @see com.lottemart.common.sms.service.LtsmsService#lmsCh2Proc(com.lottemart.common.sms.model.LtnewsmsVO)
	 * @Method Name  : LtsmsServiceImpl.java
	 * @since      : 2017
	 * @author     : 이효근
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.service.impl
	 * @Description : 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
	 */
	@Override
	public String lmsCh3Proc(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		
		sms.setTRAN_ETC1(tRAN_ETC1);
		
		try {
			LtNewSmsDAO.lmsCh3Proc(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return tRAN_ETC1;
	}
	@Override
	public String lmsCh4Proc(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		
		sms.setTRAN_ETC1(tRAN_ETC1);
		
		
		try {
			
			LtNewSmsDAO.lmsCh4Proc(sms);
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return tRAN_ETC1;
	}
	
	/** (non-Javadoc)
		 * @see com.lottemart.common.sms.service.LtsmsService#lmsCh2ListProc(com.lottemart.common.sms.model.LtnewsmsVO)
	 * @Method Name  : LtsmsServiceImpl.java
	 * @since      : 2016
	 * @author     : 김학수
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.service.impl
	 * @Description : 
	 * @param LtnewsmsVO
	 * @return
	 * @throws Exception
	*/
	@Override
	public List<String> lmsCh2ListProc(List<LtnewsmsVO> smses) throws Exception {
		Iterator<LtnewsmsVO> iter = smses.iterator();
		
		List<String> vos = new LinkedList<String>();
		
		while(iter.hasNext()) {
			LtnewsmsVO ltsmsvo = (LtnewsmsVO) iter.next();
			vos.add(this.lmsCh2Proc(ltsmsvo));
		}
		
		return vos;
	}

    /**
     * 문자메시지(LMS)를 전송(등록)전 TRAN_PR 번호를 조회한다.
     * 
     * @since      : 2018. 08. 03.
     * @author     : 김연민
     */ 
	@Override
	public String getTranPr() {
		return LtNewSmsDAO.getTranPr();
		
	}
	/**
     * 문자메시지(LMS,MMS)를 등록 전 TRAN_MMS_SEQ 번호를 조회한다.
     * 
     * @since      : 2018. 08. 29.
     * @author     : 이종일
     */
	@Override
	public String selectMmsSeq() {
		return LtNewSmsDAO.selectMmsSeq();
	}
	@Override
	public String insertMmsSend(LtnewsmsVO sms) throws Exception {
		String mmsSeq = String.valueOf(LtNewSmsDAO.selectMmsSeq());
		sms.setMMS_SEQ(mmsSeq);
		LtNewSmsDAO.insertMmsSend(sms);
		return mmsSeq;
	}

	@Override
	public String insertMmsInf(LtnewsmsVO sms) throws Exception {
		String tRAN_ETC1 = idgenService.getNextStringId();
		sms.setTRAN_ETC1(tRAN_ETC1);
    	if(sms.getTRAN_CALLBACK() == null || "".equals(sms.getTRAN_CALLBACK())) {
    		sms.setTRAN_CALLBACK("15772500");
    	}		
		
		try {
			LtNewSmsDAO.insertMmsInf(sms);
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		return tRAN_ETC1;
	}
	
	/**
     * SMS이력 메시지 보기 팝업
     * 
     * @since      : 2022. 10. 04.
     * @author     : syd
     */
	public DataMap selectSmsReportDetailInfo(DataMap param) throws Exception {
		return LtNewSmsDAO.selectSmsReportDetailInfo(param);
	}
}