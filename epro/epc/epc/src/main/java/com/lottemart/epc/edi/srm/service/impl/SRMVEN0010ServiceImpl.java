package com.lottemart.epc.edi.srm.service.impl;


import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.srm.dao.SRMJON004301Dao;
import com.lottemart.epc.edi.srm.dao.SRMVEN0010Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0010VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0010Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * SRM > SRM정보 > 파트너사정보변경 ServiceImpl
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           			수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.29  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
@Service("srmven0010Service")
public class SRMVEN0010ServiceImpl implements SRMVEN0010Service {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private SRMVEN0010Dao srmven0010Dao;

	@Autowired
	private SRMJON004301Dao srmjon004301Dao;

	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType = null;

	private static final Logger logger = LoggerFactory.getLogger(SRMVEN0010ServiceImpl.class);
	
	/**
	 * 협력업체정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMVEN0010VO> selectVendorList(SRMVEN0010VO vo) throws Exception {
		return srmven0010Dao.selectVendorList(vo);
	}
	
	/**
	 * 파트너사 수정정보 Insert/Update
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String insertVenInfo(SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		// 체크된 협력업체 가져오기
		ArrayList alVenCd = vo.getAlVenCd();
		
		// 체크된 협력업체가 있을 경우
		if (alVenCd != null && alVenCd.size() > 0) {
			HashMap hm = new HashMap();
			
			SRMVEN0010VO paramVO = null;
			
			for (int i = 0; i < alVenCd.size(); i++) {
				hm = (HashMap) alVenCd.get(i);
				
				paramVO = new SRMVEN0010VO();
				
				paramVO.setVenCd((String) hm.get("venCd"));
				paramVO.setSeq((String) hm.get("seq"));
				paramVO.setModPresidNm((String) hm.get("modPresidNm"));
				paramVO.setModPresidEmail((String) hm.get("modPresidEmail"));
				paramVO.setModRepTelNo((String) hm.get("modRepTelNo"));
				paramVO.setModDutyInf((String) hm.get("modDutyInf"));
				paramVO.setModHpNo1((String) hm.get("modHpNo1"));
				paramVO.setModEmail((String) hm.get("modEmail"));
				paramVO.setModFaxNo((String) hm.get("modFaxNo"));
				paramVO.setModZip((String) hm.get("modZip"));
				paramVO.setModAddr((String) hm.get("modAddr"));
				paramVO.setModAddr2((String) hm.get("modAddr2"));
				
				paramVO.setZzqcFg1((String) hm.get("zzqcFg1"));
				paramVO.setZzqcFg2((String) hm.get("zzqcFg2"));
				paramVO.setZzqcFg3((String) hm.get("zzqcFg3"));
				paramVO.setZzqcFg4((String) hm.get("zzqcFg4"));
				paramVO.setZzqcFg5((String) hm.get("zzqcFg5"));
				paramVO.setZzqcFg6((String) hm.get("zzqcFg6"));
				paramVO.setZzqcFg7((String) hm.get("zzqcFg7"));
				paramVO.setZzqcFg8((String) hm.get("zzqcFg8"));
				paramVO.setZzqcFg9((String) hm.get("zzqcFg9"));
				paramVO.setZzqcFg10((String) hm.get("zzqcFg10"));
				paramVO.setZzqcFg11((String) hm.get("zzqcFg11"));
				paramVO.setZzqcFg12((String) hm.get("zzqcFg12"));
				paramVO.setStatus((String) hm.get("status"));
				paramVO.setMdEmail((String) hm.get("mdEmail"));
				paramVO.setRegId(epcLoginVO.getRepVendorId());
				
				// 상태값이 null(Insert), 9(Update)
				
				if (paramVO.getStatus() != null && paramVO.getStatus().equals("9")) {	// Update
					srmven0010Dao.updateVenInfo(paramVO);
				} else {	// Insert
					paramVO.setStatus("9");
					srmven0010Dao.insertVenInfo(paramVO);
				}
			}
		}
		
		return "success";
	}
	
	/**
	 * 파트너사 수정정보 확정요청
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateVenInfoConfirm(SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		// 체크된 협력업체 가져오기
		ArrayList alVenCd = vo.getAlVenCd();

		//MD-EMAIL 이 SUSMT 테이블에 존재하지않음
		boolean mdInfo = true;
		List<SRMVEN0010VO> mdEmailList = srmven0010Dao.selectVenInfoConfirmMDEmailList(vo);
		for(int i=0; i<mdEmailList.size(); i++){
			if(StringUtil.isEmpty(mdEmailList.get(i).getMdName()) || StringUtil.isEmpty(mdEmailList.get(i).getMdEmail())){
				mdInfo = false;
				break;
			}
		}
		if(!mdInfo){
			return "fail_mdEmail";
		}

		
		//----- 체크된 협력업체가 있을 경우(상태값 Update) --------------------
		if (alVenCd != null && alVenCd.size() > 0) {
			HashMap hm = null;
			SRMVEN0010VO paramVO = null;
			
			for (int i = 0; i < alVenCd.size(); i++) {
				hm = (HashMap) alVenCd.get(i);
				
				paramVO = new SRMVEN0010VO();
				
				paramVO.setVenCd((String) hm.get("venCd"));
				paramVO.setSeq((String) hm.get("seq"));
				paramVO.setStatus((String) hm.get("status"));
				
				srmven0010Dao.updateVenInfoConfirm(paramVO);
			}
		}
		//--------------------------------------------------
		
		//----- RFC 호출  ----------------------------------------
		List<HashMap> rfcList = srmven0010Dao.selectVenInfoConfirmList(vo);
		
		HashMap reqCommonMap = new HashMap();	// RFC 응답
		
		if (rfcList.size() > 0) {
			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS", "");
			reqCommonMap.put("ZPOROWS", "");
			reqCommonMap.put("ZPODATE", "");
			reqCommonMap.put("ZPOTIME", "");
			
			JSONObject obj = new JSONObject();
			
			obj.put("TPC_SRM_VEN_INFO_SAP", rfcList);	// HashMap에 담긴 데이터 JSONObject로 ...
			obj.put("REQCOMMON", reqCommonMap);			// RFC 응답 HashMap JsonObject로....
			
			logger.debug("obj.toString----->" + obj.toString());
			
			// RFC 호출
			Map<String, Object> rfcMap;
			rfcMap = rfcCommonService.rfcCall(vo.getProxyNm(), obj.toString(), epcLoginVO.getRepVendorId());
			
			// 응답정보 확인
			JSONObject mapObj			= new JSONObject(rfcMap.toString());								// MAP에 담긴 응답메세지를 JSONObject로.................							
			JSONObject resultObj		= mapObj.getJSONObject("result");									// JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj	= resultObj.getJSONObject ("RESPCOMMON");							// RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		// RFC 응답 성공 / 실패 여부를 담는 Key다
			
			// 성공이 아니면 상태값 등록상태(9)로 되돌림
			if (!rtnResult.equals("S")) {
				// 체크된 협력업체가 있을 경우(상태값 Update)
				if (alVenCd != null && alVenCd.size() > 0) {
					HashMap hm = null;
					SRMVEN0010VO paramVO = null;
					
					for (int i = 0; i < alVenCd.size(); i++) {
						hm = (HashMap) alVenCd.get(i);
						
						paramVO = new SRMVEN0010VO();
						
						paramVO.setVenCd((String) hm.get("venCd"));
						paramVO.setSeq((String) hm.get("seq"));
						paramVO.setStatus("9");
						
						srmven0010Dao.updateVenInfoConfirm(paramVO);
					}
				}
			} else {
				//성공인경우 담당 MD 에게 메일 전송
				//SSUGL_PARTNER_H | MD_EMAIL
				//메일전송한 MD 체크후 전송내역있으면 패스
				mdEmailList = srmven0010Dao.selectVenInfoConfirmMDEmailList(vo);
				List<String> sendMdEmailLIst = new ArrayList<String>();				// 보낸 이메일 LIST
				boolean sendMail = true;
				for(int i=0; i<mdEmailList.size(); i++){
					sendMail = true;

					SRMVEN0010VO mdEmailVo = mdEmailList.get(i);

					SRMJON0043VO emailVo = new SRMJON0043VO();

					for(int j=0; j<sendMdEmailLIst.size(); j++){
						if(mdEmailVo.getMdEmail().equals(sendMdEmailLIst.get(j))){
							sendMail = false;
							break;
						}
					}
					if(sendMail){
						if(serverType.equals("prd")) {
							emailVo.setUserNameLoc(mdEmailVo.getMdName());
							emailVo.setEmail(mdEmailVo.getMdEmail());
							emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmven0010.contents", new Object[] { mdEmailVo.getVenNm() }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
						} else {
							/****TEST****/
							emailVo.setUserNameLoc("email.test.userName");
							emailVo.setEmail(config.getString("email.test.userEmail"));
							emailVo.setMsgContents(SRMCommonUtils.getMailContents(messageSource.getMessage("ems.srm.msg.srmven0010.contents", new Object[] { "테스트업체" }, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.juso", null, Locale.getDefault()), messageSource.getMessage("ems.srm.msg.lottemart.sendMsg", null, Locale.getDefault())));
							/****TEST****/
						}

						emailVo.setMsgTitle(messageSource.getMessage("ems.srm.msg.srmven0010.title", null, Locale.getDefault()));
						emailVo.setMsgCd(messageSource.getMessage("ems.srm.msg.srmven0010.msgCd", null, Locale.getDefault()));
						srmjon004301Dao.insertHiddenCompReqEMS(emailVo);
						sendMdEmailLIst.add(emailVo.getEmail());
					}
				}

			}
		}
		//------------------------------------------------------------
		
		return "success";
	}
	
	/**
	 * 파트너사 수정정보 Delete
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String deleteVenInfo(SRMVEN0010VO vo) throws Exception {
		// 체크된 협력업체 가져오기
		ArrayList alVenCd = vo.getAlVenCd();
		
		// 체크된 협력업체가 있을 경우
		if (alVenCd != null && alVenCd.size() > 0) {
			HashMap hm = null;
			SRMVEN0010VO paramVO = null;
			
			for (int i = 0; i < alVenCd.size(); i++) {
				hm = (HashMap) alVenCd.get(i);
				
				paramVO = new SRMVEN0010VO();
				
				paramVO.setVenCd((String) hm.get("venCd"));
				paramVO.setSeq((String) hm.get("seq"));
				
				srmven0010Dao.deleteVenInfo(paramVO);
			}
		}
		
		return "success";
	}
	
	/**
	 * 선택한 파트너사 리스트
	 */
	public List<SRMVEN0010VO> selectSrmVenList(SRMVEN0010VO vo) throws Exception {
		return srmven0010Dao.selectSrmVenList(vo);
	}
	
}
