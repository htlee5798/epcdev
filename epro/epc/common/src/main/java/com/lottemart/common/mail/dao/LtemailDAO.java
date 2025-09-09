package com.lottemart.common.mail.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.mail.model.LteMailVO;

/**
 * 이메일을 위한 데이터 접근 클래스
 * 
 * @author 고성훈
 * @since 2011.09.06
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.09.06  고성훈          최초 생성
 * 
 * </pre>
 */
@Repository("LtemailDAO")
public class LtemailDAO extends SqlMapClientDaoSupport {
	/**
	 * Logger for this class
	 */
	final static Logger logger = LoggerFactory.getLogger(LtemailDAO.class);
			
	/**
	 * 이메일 목록을 조회한다.
	 * 
	 * @param MailVO
	 */
	@SuppressWarnings("unchecked")
	public List<LteMailVO> selectMailInfs(LteMailVO vo) throws Exception {
		List<LteMailVO> result = null;
		
		try {
			result = getSqlMapClientTemplate().queryForList("MailDAO.selectMailInfs",vo);
		}catch(Exception e) {
			logger.error("LtemailDAO.selectMailInfs 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일 목록 숫자를 조회한다
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectMailInfsCnt(LteMailVO vo) throws Exception {
		int result = 0; 
		
		try {
			result =  (Integer) getSqlMapClientTemplate().queryForObject("MailDAO.selectMailInfsCnt", vo);
		}catch(Exception e) {
			logger.error("LtemailDAO.selectMailInfsCnt 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일 수신 정보를 등록한다.
	 * 
	 * @param Mail
	 * @return
	 * @throws Exception
	 */
	public String insertMailReip(LteMailVO Mail) throws Exception {
		String result = "";
		
		try {
			result = (String) getSqlMapClientTemplate().insert("MailDAO.insertMailReip", Mail);
		}catch(Exception e) {
			logger.error("LtemailDAO.insertMailReip 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일 정보를 등록한다.
	 * 
	 * @param Mail
	 * @return
	 * @throws Exception
	 */
	public String insertMailQueue(LteMailVO Mail) throws Exception {
		String result = "";

		try {
			result = (String) getSqlMapClientTemplate().insert("MailDAO.insertMailQueue", Mail);
		}catch(Exception e) {
			logger.error("LtemailDAO.insertMailQueue 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 서비스 항목을 등록한다.
	 * 
	 * @param Mail
	 * @return
	 * @throws Exception
	 */
	public String insertMailSendtype(LteMailVO Mail) throws Exception {
		String result = "";

		try {
			result = (String) getSqlMapClientTemplate().insert("MailDAO.insertMailSendtype", Mail);
		}catch(Exception e) {
			logger.error("LtemailDAO.insertMailSendtype 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일에 대한 상세정보를 조회한다.
	 * 
	 * @param searchVO
	 * @return
	 */
	public LteMailVO selectMailInf(LteMailVO searchVO) {
		LteMailVO result = null;
		
		try {
			result = (LteMailVO) getSqlMapClientTemplate().queryForObject("MailDAO.selectMailInf", searchVO);
		}catch(Exception e) {
			logger.error("LtemailDAO.selectMailInf 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일 수신 및 결과 목록을 조회한다.
	 * 
	 * @param MailRecptn
	 */
	@SuppressWarnings("unchecked")
	public List<LteMailVO> selectMailRecptnInfs(LteMailVO vo) throws Exception {
		List<LteMailVO> result = null;
		
		try {
			result = getSqlMapClientTemplate().queryForList("MailDAO.selectMailRecptnInfs", vo);
		}catch(Exception e) {
			logger.error("LtemailDAO.selectMailRecptnInfs 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	/**
	 * 이메일 전송 결과 수신을 처리한다. MailInfoReceiver(Schedule job)에 의해 호출된다.
	 * 
	 * @param MailRecptn
	 * @return
	 * @throws Exception
	 */
	public String updateMailRecptnInf(LteMailVO MailRecptn) throws Exception {
		String result = "";
		
		try {
			result = (String) getSqlMapClientTemplate().insert("MailDAO.updateMailRecptnInf", MailRecptn);
		}catch(Exception e) {
			logger.error("LtemailDAO.updateMailRecptnInf 메소드 사용 시 오류 발생");
		}
		
		return result;
	}
	
	
	/** 
		 * @see selectIsMailSendType
		 * @Method Name  : LtemailDAO.java
		 * @since      : 2011. 9. 29.
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.dao
		 * @Description : 
	     * @param vo
	     * @return
	     * @throws Exception 
	*/
	public int selectIsMailSendType(LteMailVO vo) throws Exception {
		int result = 0;
		
		try {
			result = (Integer) getSqlMapClientTemplate().queryForObject("MailDAO.selectIsMainSendTypeCnt", vo);
		}catch(Exception e) {
			logger.error("LtemailDAO.selectIsMailSendType 메소드 사용 시 오류 발생");
		}
		
		return result;
	}

	@Resource(name = "sqlMapClient_Ems")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
}
