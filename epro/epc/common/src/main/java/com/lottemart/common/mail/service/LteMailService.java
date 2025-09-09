package com.lottemart.common.mail.service;

import java.io.File;
import java.util.List;

import com.lottemart.common.mail.model.LteMailVO;

public interface LteMailService {
	 /**
     * 이메일 목록을 조회 한다.
     * 
     * @param searchVO
     */
    public List<LteMailVO> selecteMailInfs(LteMailVO searchVO) throws Exception;
    
    /**
     * 이메일를 전송(등록)한다.
     * 
     * @param mail
     * @throws Exception
     */
    public String inserteMailSend(LteMailVO mail) throws Exception;
    
    /**
     * 여러건의 이메일를 전송(등록)한다.
     * 
     * @param mailg
     * @throws Exception
     */
    public String inserteMailListSend(List<LteMailVO> mailg) throws Exception;
    
    /**
     * 이메일에 대한 상세정보를 조회한다.
     * 
     * @param searchVO
     * @return
     * @throws Exception
     */
    public LteMailVO selecteMailInf(LteMailVO searchVO) throws Exception;
    
    /**
     * @see 이메일리스트에 대한 전체 카운트를 갖져온다.
     * @param mail
     * @return
     * @throws Exception
     */
    public int selectMailInfsCnt(LteMailVO mail) throws Exception;
    
    /**
     * 템플릿을 이용하여 메일을 발송한다.
     * Desc : 
     * @Method Name : sneMailByTemplate
     * @param mail
     * @param templateFile
     * @return
     * @throws Exception
     * @param 
     * @return 
     * @exception Exception
     */
    public boolean sendMailByTemplate(LteMailVO mail) throws Exception;
}
