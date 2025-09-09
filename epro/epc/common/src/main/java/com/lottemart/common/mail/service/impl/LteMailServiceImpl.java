package com.lottemart.common.mail.service.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottemart.common.mail.dao.LtemailDAO;
import com.lottemart.common.mail.model.LteMailVO;
import com.lottemart.common.mail.service.LteMailService;
import com.lottemart.common.util.MailMatcher;

import lcn.module.framework.idgen.IdGnrService;

/**
 * @author sunghoon
 * @Class : com.lottemart.common.mail.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * @version : 
 */
@Service("LteMailService")
public class LteMailServiceImpl implements LteMailService {
	/**
	 * Logger for this class
	 */
	final static Logger logger = LoggerFactory.getLogger(LteMailServiceImpl.class);
	
	@Resource(name = "LtemailDAO")
	private LtemailDAO ltemailDAO;

	@Resource(name = "EmailIdGnrService")
	private IdGnrService idgenService;

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.mail.service.LteMailService#selecteMailInfs(com.lottemart.common.mail.model.LteMailVO)
		 * @Method Name  : LteMailServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.service.impl
		 * @Description : 메일 리스트 가져오기 
	     * @param searchVO
	     * @return
	     * @throws Exception
	 */
	@Override
	public List<LteMailVO>  selecteMailInfs(LteMailVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		
		
		return ltemailDAO.selectMailInfs(searchVO);
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.mail.service.LteMailService#inserteMailSend(com.lottemart.common.mail.model.LteMailVO)
		 * @Method Name  : LteMailServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.service.impl
		 * @Description : 단건의 메일 보내기 
	     * @param mail
	     * @return
	     * @throws Exception
	 */
	@Override
	public String inserteMailSend(LteMailVO mail) throws Exception {
		// TODO Auto-generated method stub
		String SERIALNO = idgenService.getNextStringId();
		
		mail.setMID(SERIALNO);
		
		try {
			int tid = ltemailDAO.selectIsMailSendType(mail);
			if(tid < 1) {
				ltemailDAO.insertMailSendtype(mail);
			}
			ltemailDAO.insertMailReip(mail);
			ltemailDAO.insertMailQueue(mail);
			
		}catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		
		return "1";
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.mail.service.LteMailService#inserteMailListSend(java.util.List)
		 * @Method Name  : LteMailServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.service.impl
		 * @Description : 메일 발송 요청 리스트 처리 
	     * @param mailg
	     * @return
	     * @throws Exception
	 */
	@Override
	public String inserteMailListSend(List<LteMailVO> mailg) throws Exception {
		// TODO Auto-generated method stub
		Iterator it = mailg.iterator();
		
		while(it.hasNext()) {
			LteMailVO vo = (LteMailVO) it.next();
			this.inserteMailSend(vo);
		}
		
		return "1";
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.mail.service.LteMailService#selecteMailInf(com.lottemart.common.mail.model.LteMailVO)
		 * @Method Name  : LteMailServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.service.impl
		 * @Description : 메일 상세 
	     * @param searchVO
	     * @return
	     * @throws Exception
	 */
	@Override
	public LteMailVO selecteMailInf(LteMailVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return ltemailDAO.selectMailInf(searchVO);
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.mail.service.LteMailService#selectMailInfsCnt(com.lottemart.common.mail.model.LteMailVO)
		 * @Method Name  : LteMailServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.mail.service.impl
		 * @Description : 메일 리스트 갯수 
	     * @param mail
	     * @return
	     * @throws Exception
	 */
	@Override
	public int selectMailInfsCnt(LteMailVO mail) throws Exception {
		// TODO Auto-generated method stub
		return ltemailDAO.selectMailInfsCnt(mail);
	}

	@Override
	public boolean sendMailByTemplate(LteMailVO mail) throws Exception {
		
		MailMatcher mailMatcher = new MailMatcher();

		boolean result = true;

		try {
			mailMatcher.addPattern(mail.getBindMap());
			/* 템플릿 html 를 통한 전송과 message 를 통한 전송을 모두 수용한 로직 */
			String chunk = null;
			if (mail.getMailTemplate() != null) {
				chunk = mailMatcher.getMailBody(mail.getMailTemplate());
			} else if (mail.getTemplateFile() != null && mail.getTemplateFile().exists()) {
				chunk = mailMatcher.getMailBody(mail.getTemplateFile());
			} else {
				chunk = mail.getCONTENTS();
			}
			if(chunk!=null) chunk = mailMatcher.getBoundBody(chunk);
			mail.setCONTENTS(chunk);
			this.inserteMailSend(mail);

			result = true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			result = false;
		}
		return result;
	}
}
