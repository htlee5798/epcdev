package com.lottemart.common.sms.dao;

import java.util.List;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.sms.model.LtsmsVO;


/**
 * 문자메시지를 위한 데이터 접근 클래스
 * @author 고성훈
 * @since 2011.08.30
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
@Repository("LtsmsDAO")
public class LtsmsDAO extends SqlMapClientDaoSupport{
	
    /**
     * 문자메시지 목록을 조회한다.
     * 
     * @param SmsVO
     */
    @SuppressWarnings("unchecked")
    public List<LtsmsVO> selectSmsInfs(LtsmsVO vo) throws Exception {
	return getSqlMapClientTemplate().queryForList("SmsDAO.selectSmsInfs", vo);
    }

    /**
     * 문자메시지 목록 숫자를 조회한다
     * 
     * @param SmsVO
     * @return
     * @throws Exception
     */
    public int selectSmsInfsCnt(LtsmsVO vo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("SmsDAO.selectSmsInfsCnt", vo);
    }
    
    /**
     * 문자메시지 정보를 등록한다.
     * 
     * @param notification
     * @return
     * @throws Exception
     */
	public String insertSmsInf(LtsmsVO sms) throws Exception {
		return (String)getSqlMapClientTemplate().insert("SmsDAO.insertSmsInf", sms);
	}
    
    /**
     * 문자메시지 수신정보 및 결과 정보를 등록한다.
     * @param smsRecptn
     * @return
     * @throws Exception
     */
    public String insertSmsRecptnInf(LtsmsVO smsRecptn) throws Exception {
	return (String)getSqlMapClientTemplate().insert("SmsDAO.insertSmsRecptnInf", smsRecptn);
    }
    
    /**
     * 문자메시지에 대한 상세정보를 조회한다.
     * 
     * @param searchVO
     * @return
     */
    public LtsmsVO selectSmsInf(LtsmsVO searchVO) {
	return (LtsmsVO)getSqlMapClientTemplate().queryForObject("SmsDAO.selectSmsInf", searchVO);
    }
    
    /**
     * 문자메시지 수신 및 결과 목록을 조회한다.
     * 
     * @param SmsRecptn
     */
    @SuppressWarnings("unchecked")
    public List<LtsmsVO> selectSmsRecptnInfs(LtsmsVO vo) throws Exception {
	return getSqlMapClientTemplate().queryForList("SmsDAO.selectSmsRecptnInfs", vo);
    }
    
    /**
     * 문자메시지 전송 결과 수신을 처리한다.
     * SmsInfoReceiver(Schedule job)에 의해 호출된다.
     * 
     * @param smsRecptn
     * @return
     * @throws Exception
     */
    public String updateSmsRecptnInf(LtsmsVO smsRecptn) throws Exception {
	return (String)getSqlMapClientTemplate().insert("SmsDAO.updateSmsRecptnInf", smsRecptn);
    }
    
    @Resource(name = "sqlMapClient_Sms")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
}
