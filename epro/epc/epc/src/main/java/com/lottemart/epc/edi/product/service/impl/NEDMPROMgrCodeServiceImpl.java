package com.lottemart.epc.edi.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.dao.NEDMPROMgrCodeDao;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.service.NEDMPROMgrCodeService;

import lcn.module.framework.property.ConfigurationService;

@Service("nedmproMgrCodeService")
public class NEDMPROMgrCodeServiceImpl implements NEDMPROMgrCodeService{
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPROMgrCodeServiceImpl.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nedmproMgrCodeDao")
	private NEDMPROMgrCodeDao nedmproMgrCodeDao;
	
	/**
	 * 마스터 코드 총 갯수
	 */
	public int selectMgrCodeListCount(CommonProductVO vo) throws Exception{
		return nedmproMgrCodeDao.selectMgrCodeListCount(vo);
	}
	
	/**
	 * 마스터 코드 리스트 
	 */
	public List<CommonProductVO> selectMgrCodeList(CommonProductVO vo) throws Exception{
		List<CommonProductVO> resultList = nedmproMgrCodeDao.selectMgrCodeList(vo);
		return resultList;
	}

	/**
	 * 상세코드 총 갯수 
	 */
	public int selectMgrCodeDtlListCount(CommonProductVO vo) throws Exception{
		return nedmproMgrCodeDao.selectMgrCodeDtlListCount(vo);
	}
	
	
	/**
	 * 상세코드 리스트 
	 */
	public List<CommonProductVO> selectMgrCodeDtlList(CommonProductVO vo) throws Exception{
		List<CommonProductVO> resultList = nedmproMgrCodeDao.selectMgrCodeDtlList(vo);
		return resultList;
	}
	
	
	/**
	 * 코드 저장 
	 */
	public Map<String, Object> insertMgrDtlCode(CommonProductVO vo , HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "fail");
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String adminId = StringUtils.defaultString(epcLoginVO.getAdminId(), "EPC");
		
		//작업자 아이디 설정 
		vo.setWorkId(adminId);
		

		/*마스터 코드 및 세부 코드 저장*/
		//입력된 마스터코드가 존재하는지 확인 
		int cnt = nedmproMgrCodeDao.selectMasterCodeCount(vo);
		/*코드 저장(신규등록/수정)----------------------------*/
		Integer sortNo = vo.getSubSortNo();
		if(sortNo == null) {
			vo.setSubSortNo(0);
		}

		//------------------ 입력한 마스터 코드가 이미 있는경우 ----------------------
		if(cnt > 0 ) {
			
			//서브코드 검증
			int subCheck = nedmproMgrCodeDao.selectSubCodeCount(vo);
			
			//마스터코드가  존재하면서 마스터코드명 서브코드명 서브코드도 같은경우는 중복이므로 튕겨냄 
			if(subCheck > 0) {
				result.put("msgCd", "duple");
				return result;
			}else {
				// 신규 버큰 누르고 마스터코드와 상세코드가 같으면 튕겨냄 (신규는 오리지널코드를 못가져오므로 업데이트를 안타서 중복으로 인서트 방지)
				int doubleCheck = nedmproMgrCodeDao.selectCodeCount(vo);
				
				if(doubleCheck > 0 && StringUtils.isEmpty(vo.getSubDtlCdOld())) {
					result.put("msgCd", "duple");
					return result;
				}else {
					if(StringUtils.isEmpty(vo.getSubDtlCd()) && StringUtils.isEmpty(vo.getSubDtlNm())) {
						//세부코드 없을경우 분류코드 업데이트  (분류코드만 입력한경우)
						nedmproMgrCodeDao.updateCodeMaster(vo);
					}else {
						if(vo.getMstComNm() != vo.getMstComNmOld()) {
							if(StringUtils.isEmpty(vo.getMstComNm())) {
								//기존코드는 존재하나 코드명이 다른 코드명으로 입력할경우 
								
							}else {
								//분류코드명이 바뀔경우 분류코드명 변경 
								nedmproMgrCodeDao.updateCodeMaster(vo);
							}
						}
						
						//서브코드가 자체가 처음부터 없었으면 그 해당하는 마스터코드에 서브코드 인서트 
						if(StringUtils.isEmpty(vo.getSubDtlCdOld())) {
							nedmproMgrCodeDao.insertCodeSub(vo);
						}else if((vo.getSubDtlNm() != vo.getSubDtlNmOld()) || (vo.getSubDtlCd() != vo.getSubDtlCdOld())) {
							//서브코드랑 서브코드명 분류코드명이 세개중에 하나라도 변경이 되었으면 코드 전체 업데이트
							nedmproMgrCodeDao.updateCodeSub(vo);
						}
					}
				}
				
			}
	
		}else {
			//------------------ 마스터코드가 신규 인경우 ----------------------
			
			if(StringUtils.isEmpty(vo.getMstComCdOld())) {
				//기존에 없던경우 인서트 
				nedmproMgrCodeDao.insertCodeMaster(vo);
			}else {
				//기존에 없던 코드여도 등록되었던 코드면  업데이트 
				nedmproMgrCodeDao.updateCodeMaster(vo);
			}
			
		}
		
		result.put("msgCd", "success");
		
		return result;
	}
	
	
	
	

	
	/**
	 * 세부코드 삭제
	 */
	public Map<String, Object> deleteMgrDtlCode(CommonProductVO vo , HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "fail");
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String adminId = StringUtils.defaultString(epcLoginVO.getAdminId(), "EPC");
		
		//작업자 아이디 설정 
		vo.setWorkId(adminId);
		
		logger.info("삭제할 마스터코드 :" + vo.getMstComCd() + "삭제할 세부코드 :" + vo.getSubDtlCdOld());
		
		nedmproMgrCodeDao.deleteMgrDtlCode(vo);
		
		result.put("msgCd", "success");
		
		return result;
	}

}
