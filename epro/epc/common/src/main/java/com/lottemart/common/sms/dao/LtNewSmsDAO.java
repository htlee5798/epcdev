package com.lottemart.common.sms.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;


/**
 * 문자메시지를 위한 데이터 접근 클래스
 * @author 고성훈
 * @since 2011.11.28
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.30  고성훈          최초 생성
 *
 * </pre>
 */
@Repository("LtNewSmsDAO")
public class LtNewSmsDAO extends SqlMapClientDaoSupport{
	
    /**
     * 문자메시지 목록을 조회한다.
     * 
     * @param LtnewsmsVO
     */
    @SuppressWarnings("unchecked")
    public List<LtnewsmsVO> selectNewSmsInfs(LtnewsmsVO vo) throws Exception {
	return getSqlMapClientTemplate().queryForList("NewSmsDAO.selectSmsInfs", vo);
    }

    /**
     * 문자메시지 목록 숫자를 조회한다
     * 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
     */
    public int selectNewSmsInfsCnt(LtnewsmsVO vo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsInfsCnt", vo);
    }
    
    /**
     * 문자메시지 정보를 등록한다.
     * 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
     */
	public String insertNewSmsInf(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.insertSmsInf", sms);
	}
    
	/**
	 * 
	 * 문자메시지(LMS, MMS) 전송
	 * @Method Name : insertLmsSend
	 * @param map
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public String insertMmsSend(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.insertMmsSend", sms);		
	}
	public String insertMmsInf(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.insertMmsInf", sms);		
	}
    /**
     * 문자메시지 수신정보 및 결과 정보를 등록한다.
     * @param smsRecptn
     * @return
     * @throws Exception
     */
    public String insertNewSmsRecptnInf(LtnewsmsVO smsRecptn) throws Exception {
	return (String)getSqlMapClientTemplate().insert("NewSmsDAO.insertSmsRecptnInf", smsRecptn);
    }
    
    /**
     * 문자메시지에 대한 상세정보를 조회한다.
     * 
     * @param searchVO
     * @return
     */
    public LtnewsmsVO selectNewSmsInf(LtnewsmsVO searchVO) {
	return (LtnewsmsVO)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsInf", searchVO);
    }
    
    /**
     * 문자메시지 수신 및 결과 목록을 조회한다.
     * 
     * @param SmsRecptn
     */
    @SuppressWarnings("unchecked")
    public List<LtsmsVO> selectNewSmsRecptnInfs(LtnewsmsVO vo) throws Exception {
	return getSqlMapClientTemplate().queryForList("NewSmsDAO.selectSmsRecptnInfs", vo);
    }
    
    /**
     * 문자메시지 전송 결과 수신을 처리한다.
     * SmsInfoReceiver(Schedule job)에 의해 호출된다.
     * 
     * @param smsRecptn
     * @return
     * @throws Exception
     */
    public String updateNewSmsRecptnInf(LtnewsmsVO smsRecptn) throws Exception {
	return (String)getSqlMapClientTemplate().insert("NewSmsDAO.updateSmsRecptnInf", smsRecptn);
    }
    
    @Resource(name = "sqlMapClient_NewSms")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
    
    
    /**
     * CC및 FRONT 문자메시지 목록을 조회한다.
     * 
     * @param LtnewsmsVO
     */
    @SuppressWarnings("unchecked")
    public List<LtnewsmsVO> selectNewSmsInfsCC(LtnewsmsVO vo) throws Exception {
	return getSqlMapClientTemplate().queryForList("NewSmsDAO.selectSmsInfsCC", vo);
    }

    /**
     * CC및 FRONT 문자메시지 목록 숫자를 조회한다
     * 
     * @param LtnewsmsVO
     * @return
     * @throws Exception
     */
    public int selectNewSmsInfsCntCC(LtnewsmsVO vo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsInfsCntCC", vo);
    }
    
    
    /**
     * 
     * @see selectSmsInfsCntBOS
     * @Locaton    : com.lottemart.common.sms.dao
     * @MethodName  : selectSmsInfsCntBOS
     * @author     : jyLim
     * @Description : BOS SMS전송내역조회 카운트
     * @param vo
     * @return
     * @throws SQLException
     */
    public int selectSmsInfsCntBOS(LtnewsmsVO vo) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsInfsCntBOS",vo);
	}
    /**
     * 
     * @see selectSmsInfsBOS
     * @Locaton    : com.lottemart.common.sms.dao
     * @MethodName  : selectSmsInfsBOS
     * @author     : jyLim
     * @Description : BOS SMS전송내역조회
     * @param vo
     * @return
     * @throws SQLException
     */
	@SuppressWarnings("unchecked")
	public List<LtnewsmsVO> selectSmsInfsBOS(LtnewsmsVO vo) throws SQLException{
		
		return getSqlMapClientTemplate().queryForList("NewSmsDAO.selectSmsInfsBOS",vo);
	}
    
    /**
     * 문자메시지(LMS) 정보를 등록한다.
     * 
     * @param LtnewsmsVO
     * @author 김학수 20160225 추가
     * @return
     * @throws Exception
     */
	public String lmsCh2Proc(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.lmsCh2Proc", sms);
	}
	
    /**
     * 문자메시지(LMS) 정보를 등록한다.
     * 
     * @param LtnewsmsVO
     * @author 이효근 20170515 추가
     * @return
     * @throws Exception
     */
	public String lmsCh3Proc(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.lmsCh3Proc", sms);
	}
	public String lmsCh4Proc(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.lmsCh4Proc", sms);
	}
	
    
    /**
     * 문자메시지(LMS) 정보를 등록한다.
     * 
     * @param LtnewsmsVO
     * @author pwj 20171222 추가
     * @return
     * @throws Exception
     */
	public String lmsCh2SmsProc(LtnewsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("NewSmsDAO.lmsCh2SmsProc", sms);
	}

    /**
     * 문자메시지(LMS)를 전송(등록)전 TRAN_PR 번호를 조회한다.
     * 
     * @since      : 2018. 08. 03.
     * @author     : 김연민
     */ 	
	public String getTranPr() {
		return (String)getSqlMapClientTemplate().queryForObject("NewSmsDAO.getTranPr");
	}
	/**
     * 문자메시지(LMS,MMS)를 등록 전 TRAN_MMS_SEQ 번호를 조회한다.
     * 
     * @since      : 2018. 08. 29.
     * @author     : 이종일
     */ 	
	public String selectMmsSeq() {
		return (String)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectMmsSeq");
	}
	
	/**
	 * CC 알림톡 발송현황
	 * @see searchSmsList
	 * @Method Name  : LtNewSmsDAO.java
	 * @since      : 2018. 9. 11.
	 * @author     : JinS
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.dao
	 * @Description : 
	 * @param 
	 * @return  List<DataMap>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<LtnewsmsVO> searchSmsList(LtnewsmsVO vo) {
		return getSqlMapClientTemplate().queryForList("NewSmsDAO.searchSmsList",vo);
	}
	
	/**
	 * CC 알림톡 발송현황 카운트
	 * @see selectSmsListCnt
	 * @Method Name  : LtNewSmsDAO.java
	 * @since      : 2018. 9. 14.
	 * @author     : JinS
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.dao
	 * @Description : 
	 * @param 
	 * @return  int
	 * @throws
	 */
	public int selectSmsListCnt(LtnewsmsVO vo) {
		return (Integer)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsListCnt", vo);
	}
	
	/**
	 * CC SMS 발송 이력 상세 팝업
	 * @see selectSmsDetail
	 * @Method Name  : LtNewSmsDAO.java
	 * @since      : 2018. 10. 4.
	 * @author     : JinS
	 * @version    :
	 * @Locaton    : com.lottemart.common.sms.dao
	 * @Description : 
	 * @param 
	 * @return  DataMap
	 * @throws
	 */
	public LtnewsmsVO selectSmsDetail(LtnewsmsVO vo) {
		return (LtnewsmsVO)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsDetail", vo);
	}
	
	/**
     * SMS이력 메시지 보기 팝업
     * 
     * @since      : 2022. 10. 04.
     * @author     : syd
     */
	public DataMap selectSmsReportDetailInfo(DataMap param) throws Exception {
		DataMap returnMap = new DataMap();

//		param.put("TABLE_FLAG", "NEWSMS");		// 테이블을 분리하여 조회하기 위한 FLAG(NEWSMS)
		
		returnMap = (DataMap)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsReportDetailInfo", param);
		
//		if(returnMap == null || returnMap.size() == 0) {
//			param.put("TABLE_FLAG", "SMS");		// 테이블을 분리하여 조회하기 위한 FLAG(SMS)
//			returnMap = (DataMap)getSqlMapClientTemplate().queryForObject("NewSmsDAO.selectSmsReportDetailInfo", param);
//		}
		
		return returnMap;
	}
}