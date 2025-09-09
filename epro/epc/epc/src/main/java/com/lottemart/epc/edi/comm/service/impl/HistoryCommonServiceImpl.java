package com.lottemart.epc.edi.comm.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lottemart.epc.edi.comm.dao.HistoryCommonDao;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

/**
 * 
 * @Class Name : HistoryCommonServiceImpl.java
 * @Description : 히스토리/로그 공통 서비스  
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.25		yun				최초생성
 *               </pre>
 */
@Service(value="historyCommonService")
public class HistoryCommonServiceImpl implements HistoryCommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryCommonServiceImpl.class);
	
	@Autowired
	HistoryCommonDao historyCommonDao;

	/**
	 * 인터페이스 히스토리 등록
	 */
	@Override
	public void insertTpcIfLog(HistoryVo param) {
		try {
			historyCommonDao.insertTpcIfLog(param);
		}catch(Exception e) {
			logger.error("[HISTORY INS/UPD ERROR]:::"+e.getMessage());
		}
	}

	/**
	 * BATCH JOB LOG ID생성
	 */
	@Override
	public String selectNewBatchJobLogId() {
		String newLogId = "";
		try {
			newLogId = historyCommonDao.selectNewBatchJobLogId();
		} catch (Exception e) {
			//오류발생시 빈값 반환
			logger.error(e.getMessage());
			return newLogId;
		}
		
		return newLogId;
	}

	/**
	 * 배치 히스토리 등록
	 */
	@Override
	public void insertTpcBatchJobLog(HistoryVo param) {
		try {
			historyCommonDao.insertTpcBatchJobLog(param);
		}catch(Exception e) {
			logger.error("[HISTORY INS/UPD ERROR]:::"+e.getMessage());
		}
	}

	/**
	 * 배치 히스토리 상태 변경
	 */
	@Override
	public void updateTpcBatchJobLog(HistoryVo param) {
		try {
			historyCommonDao.updateTpcBatchJobLog(param);
		}catch(Exception e) {
			logger.error("[HISTORY INS/UPD ERROR]:::"+e.getMessage());
		}
	}

	
	/**
	 * 클라이언트 IP 주소 추출
	 * @param request
	 * @return String
	 */
	public String getClientIpAddr(HttpServletRequest request) {
		String ip = null;
		try {
			
			ip = request.getHeader("X-Forwarded-For");
		    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("HTTP_CLIENT_IP");
		    }
		    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		    }
		    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getRemoteAddr();
		    }
		    if (ip != null && ip.contains(",")) {
		        ip = ip.split(",")[0].trim();
		    }
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	    return ip;
	}
	
	/**
	 * 클라이언트 IP 주소 추출 (NoParameter)
	 * @return String
	 */
	public String getClientIpAddr() {
		String ip = null;
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			ip = this.getClientIpAddr(request);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return ip;
	}
}
