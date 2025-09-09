package com.lottemart.common.sms.service;

import java.util.List;

import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;

public interface LtsmsService {
	
	 /**
     * 문자메시지 목록을 조회를 위한 전채 갯수를 조회 한다.
     * 
     * @param SmsVO
     */
    public int selectSmsInfsCnt(LtsmsVO searchVO) throws Exception;
    
	 /**
     * 문자메시지 목록을 조회 한다.
     * 
     * @param SmsVO
     */
    public List<LtsmsVO> selectSmsInfs(LtsmsVO searchVO) throws Exception;
    
    /**
     * 문자메시지를 전송(등록)한다.
     * 
     * @param sms
     * @throws Exception
     */
    public String insertSmsSend(LtsmsVO sms) throws Exception;
    
    /**
     * 여러건의 문자메시지를 전송(등록)한다.
     * 
     * @param sms
     * @throws Exception
     */
    public String insertSmsListSend(List<LtsmsVO> smses) throws Exception;
    
    /**
     * 문자메시지에 대한 상세정보를 조회한다.
     * 
     * @param searchVO
     * @return
     * @throws Exception
     */
    public LtsmsVO selectSmsInf(LtsmsVO searchVO) throws Exception;
    
    
	 /**
     * 문자메시지 목록을 조회를 위한 전채 갯수를 조회 한다.
     * 
     * @param LtnewsmsVO
     */
    public int selectNewSmsInfsCnt(LtnewsmsVO searchVO) throws Exception;
    
	 /**
     * 문자메시지 목록을 조회 한다.
     * 
     * @param LtnewsmsVO
     */
    public List<LtnewsmsVO> selectNewSmsInfs(LtnewsmsVO searchVO) throws Exception;
    
    /**
     * 문자메시지를 전송(등록)한다. 예외는 무시한다.
     * 
     * @since      : 2013. 10. 24.
     * @author     : jyLim
     * @param 
     * @return  int
     * @throws
     */
    public String insertNewSmsSend_NoneException(LtnewsmsVO sms);
    
    /**
     * 문자메시지를 전송(등록)한다.
     * 
     * @param LtnewsmsVO
     * @throws Exception
     */
    public String insertNewSmsSend(LtnewsmsVO sms) throws Exception;
     
    /**
     * 여러건의 문자메시지를 전송(등록)한다.
     * 
     * @param LtnewsmsVO
     * @throws Exception
     */
    public List<String> insertNewSmsListSend(List<LtnewsmsVO> smses) throws Exception;
    
    /**
     * 문자메시지에 대한 상세정보를 조회한다.
     * 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
     */
    public LtnewsmsVO selectNewSmsInf(LtnewsmsVO searchVO) throws Exception;
    
    
    /**
     * CC및 FRONT 문자메시지 목록을 조회를 위한 전채 갯수를 조회 한다.
     * 
     * @param LtnewsmsVO
     */
    public int selectNewSmsInfsCntCC(LtnewsmsVO searchVO) throws Exception;
    
	 /**
     * CC및 FRONT 문자메시지 목록을 조회 한다.
     * 
     * @param LtnewsmsVO
     */
    public List<LtnewsmsVO> selectNewSmsInfsCC(LtnewsmsVO searchVO) throws Exception;
    
    /**
     * 문자메시지(LMS)를 전송(등록)한다.
     * 
     * @since      : 2016. 02. 25.
     * @author     : 김학수
     * @param sms
     * @throws Exception
     */
    public String lmsCh2Proc(LtnewsmsVO sms) throws Exception;
    
    /**
     * 문자메시지(LMS)를 전송(등록)한다.(SMS)
     * 
     * @since      : 2017. 12. 22.
     * @author     : pwj
     * @param sms
     * @throws Exception
     */
    public String lmsCh2SmsProc(LtnewsmsVO sms) throws Exception;
    
    
    /**
     * 문자메시지(LMS)를 전송(등록)한다.
     * 
     * @since      : 2017. 05. 15.
     * @author     : 이효근
     * @param sms
     * @throws Exception
     */
    public String lmsCh3Proc(LtnewsmsVO sms) throws Exception;
    public String lmsCh4Proc(LtnewsmsVO sms) throws Exception;
    
    /**
     * 여러건의 문자메시지(LMS)를 전송(등록)한다.
     * 
     * @since      : 2016. 02. 25.
     * @author     : 김학수
     * @param sms
     * @throws Exception
     */
    public List<String> lmsCh2ListProc(List<LtnewsmsVO> smses) throws Exception;

    /**
     * 문자메시지(LMS)를 전송(등록)전 TRAN_PR 번호를 조회한다.
     * 
     * @since      : 2018. 08. 03.
     * @author     : 김연민
     */    
	public String getTranPr();
	/**
     * 문자메시지(LMS, MMS)를 전송한다.
     * 
     * @since      : 2018. 08. 29.
     * @author     : 이종일
     */
	public String selectMmsSeq();
	public String insertMmsSend(LtnewsmsVO sms) throws Exception;
	public String insertMmsInf(LtnewsmsVO sms) throws Exception;
	
	/**
     * SMS이력 메시지 보기 팝업
     * 
     * @since      : 2022. 10. 04.
     * @author     : syd
     */
	DataMap selectSmsReportDetailInfo(DataMap param) throws Exception;

}
