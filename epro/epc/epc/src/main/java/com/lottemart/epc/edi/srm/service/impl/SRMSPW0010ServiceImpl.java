package com.lottemart.epc.edi.srm.service.impl;


import java.util.List;
import java.util.Locale;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.srm.dao.SRMJON004301Dao;
import com.lottemart.epc.edi.srm.dao.SRMSPW0010Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMSPW0010VO;
import com.lottemart.epc.edi.srm.service.SRMSPW0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;

/**
 * 비밀번호 변경 ServiceImpl
 *
 * @author SHIN SE JIN
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmspw0010Service")
public class SRMSPW0010ServiceImpl implements SRMSPW0010Service {

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SRMSPW0010Dao srmspw0010Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	/**
	 * 비밀번호 변경전 확인
	 */
	public SRMSPW0010VO passwdChangeCheck(SRMSPW0010VO vo) throws Exception {
		return srmspw0010Dao.passwdChangeCheck(vo);
	}

	/**
	 * 비밀번호 변경
	 */
	public String updatePasswdChange(SRMSPW0010VO vo) throws Exception {
		// 임시 비밀번호 발송 일 경우
		if (!vo.getPwdGbn().equals("0")) {
			// 10자리 임시 비밀번호 생성
			String imsiPwd = SRMCommonUtils.getRandomAuth(10);

			// 임시 비밀번호 암호화
			vo.setTempPw(SRMCommonUtils.EncryptSHA256(imsiPwd));

			SRMJON0043VO emailVo = new SRMJON0043VO();

			// 메일발송
			if (serverType.equals("prd")) {
				SRMSPW0010VO sendMailVO = new SRMSPW0010VO();

				// 선택한 담당자 정보 가져오기
				sendMailVO = selectVEmail(vo);

				emailVo.setUserNameLoc(sendMailVO.getUserName());
				emailVo.setEmail(sendMailVO.getvEmail());
			} else {
				/****TEST****/
				emailVo.setUserNameLoc(config.getString("email.test.userName"));
				emailVo.setEmail(config.getString("email.test.userEmail"));
				/****TEST****/
			}

			emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmspw001001.title", null, Locale.getDefault()));
			emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmspw001001.contents", new Object[]{imsiPwd,vo.getSellerNameLoc(),vo.getIrsNo()}, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
			emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmspw001001.msgCd", null, Locale.getDefault()));

			// 메일 발송
			if (!serverType.equals("local")) {
			    srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
			}
		}

		// 비밀번호 Update
		srmspw0010Dao.updatePasswdChange(vo);

		return "success";
	}

	/**
	 * 담당자 이메일 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMSPW0010VO> selectVEmailList(SRMSPW0010VO vo) throws Exception {
		List<SRMSPW0010VO> list = srmspw0010Dao.selectVEmailList(vo);

		if (list != null && list.size() > 0) {
			SRMSPW0010VO tempVO = new SRMSPW0010VO();
			//String vEmail = "";
			String vEmailArr[] = null;
			String vEmailNew = "";

			for (int i = 0; i < list.size(); i++) {
				tempVO = list.get(i);

				//vEmail = tempVO.getvEmail();
				vEmailArr = (tempVO.getvEmail()).split("@");
				vEmailNew = "";
				for (int j = 0; j < vEmailArr[0].length(); j++) {
					if (j < 2) {
						vEmailNew = vEmailNew + vEmailArr[0].substring(j, j + 1);
					} else {
						vEmailNew = vEmailNew + "*";
					}
				}

				vEmailNew = vEmailNew + "@" + vEmailArr[1];

				list.get(i).setvEmail(vEmailNew);
			}
		}


		return list;
	}

	/**
	 * 담당자 이메일 정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public SRMSPW0010VO selectVEmail(SRMSPW0010VO vo) throws Exception {
		return srmspw0010Dao.selectVEmail(vo);
	}

	/**
	 *  임시비밀번호 발급 여부확인
	 */
	public String selectTempPwFlag(SRMSPW0010VO vo) throws Exception {
		return srmspw0010Dao.selectTempPwFlag(vo);
	}

	/**
	 * 임시비밀번호 발급후, 로그인시 비밀번호변경
	 */
	public void updateTempPasswdChange(SRMSPW0010VO vo) throws Exception {
		srmspw0010Dao.updateTempPasswdChange(vo);
	}

	/**
	 * 비밀번호 변경한지 90일 지났는지 확인
	 */
	public Integer selectIsNotChangePasswordOver90(SRMSPW0010VO vo) throws Exception {
		return srmspw0010Dao.selectIsNotChangePasswordOver90(vo);
	}

	/**
	 * 최근 접근 이력 90일 지났는지 확인
	 */
	public Integer selectIsNotAccessBefore90(SRMSPW0010VO vo) throws Exception {
		return srmspw0010Dao.selectIsNotAccessBefore90(vo);
	}

	/**
	 * 최근 접근 이력 90일 지났는지 확인
	 */
	public void updatePwdChgDateToday(SRMSPW0010VO vo) throws Exception {
		srmspw0010Dao.updatePwdChgDateToday(vo);
	}


}
