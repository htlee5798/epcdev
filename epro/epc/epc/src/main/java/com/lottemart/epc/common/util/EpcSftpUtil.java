package com.lottemart.epc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.api.client.util.Value;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.lcnjf.util.DateUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

/**
 * 
 * @Class Name : EpcSftpUtil.java
 * @Description : SFTP 공통 Util 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.09.01		yun				최초생성
 *               </pre>
 */
@Component
public class EpcSftpUtil {
	
	@Autowired
	HistoryCommonService historyCommonService;
	
	private static final Logger logger = LoggerFactory.getLogger(EpcSftpUtil.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	private static final int SESSION_TIME_OUT = 30000;
	
	private String serverType = System.getProperty("server.type") == null?"local":System.getProperty("server.type");
	
	//===============================================
	// SFTP 접속 정보 공통 변수
	//===============================================
	private static String userId;			//sftp 접속 아이디
	private static String password;			//sftp 접속 비밀번호
	private static String host;				//sftp host 주소
	private static int port = 22;			//sftp port
	
	// SFTP 인증된 호스트키 검증용 파일 Full경로
	private static String knownHosts = ConfigUtils.getString("epc.sftp.knownHosts");
	
	//===============================================
	// History 생성용 공통 변수
	//===============================================
	private static final String IF_GBN = "FTPIF";
	private static String IF_ST_DT = "";
	private static String IF_END_DT = "";
	private static String IF_SYS_GBN = "";
	private static String IF_RSLT_CD = NO_CODE;
	private static String IF_RSLT_MSG = "";
	private static String IF_REQ_PAYLOAD = "";
	private static String IF_CD = "";
	
	
	/**
	 * 접속정보셋팅_PO
	 */
	private static void setConnSftpPo() {
		host = ConfigUtils.getString("epc.sftp.po.conn.host");
		userId = ConfigUtils.getString("epc.sftp.po.conn.user");
		password = ConfigUtils.getString("epc.sftp.po.conn.passwd");
	}
	
	/**
	 * 접속정보셋팅_상공회의소
	 */
	private static void setConnSftpSangong() {
		host = ConfigUtils.getString("epc.sftp.sangong.conn.host");
		userId = ConfigUtils.getString("epc.sftp.sangong.conn.user");
		password = ConfigUtils.getString("epc.sftp.sangong.conn.passwd");
	}
	
	/**
	 * SFTP 서버 파일 업로드
	 */
	public Map<String, Object> upload(String tgGbn, String epcFullPath, String uploadPath, String uploadFileNm) throws Exception {
		return this.upload(tgGbn, epcFullPath, uploadPath, uploadFileNm, false, null, SESSION_TIME_OUT);
	}
	
	/**
	 * SFTP 서버 파일 업로드 (업로드 완료 후 파일위치 이동)
	 */
	public Map<String, Object> uploadMoveAf(String tgGbn, String epcFullPath, String uploadPath, String uploadFileNm, String moveToPath) throws Exception {
		return this.upload(tgGbn, epcFullPath, uploadPath, uploadFileNm, true, moveToPath, SESSION_TIME_OUT);
	}
	
	/**
	 * SFTP 서버 파일 업로드
	 * @param tgGbn
	 * @param epcFullPath
	 * @param uploadPath
	 * @param uploadFileNm
	 * @param moveAfUpload
	 * @param moveToPath
	 * @param connTimeout
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> upload(String tgGbn, String epcFullPath, String uploadPath, String uploadFileNm, boolean moveAfUpload, String moveToPath, int connTimeout) throws Exception {
		logger.info("---------------- [ START - SFTP UPLOAD ] ------------------- ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msgCd", NO_CODE);
		resultMap.put("message", "SFTP 파일 전송에 실패하였습니다.");
		
		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		ChannelSftp sftpChannel = null;
		
		//인터페이스 유형
		IF_CD = "001";
		//인터페이스 시작일시
		IF_ST_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			//요청 파라미터 셋팅 ----> 히스토리생성용
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("tgGbn", tgGbn);
			paramMap.put("epcFullPath", epcFullPath);
			paramMap.put("uploadPath", uploadPath);
			paramMap.put("uploadFileNm", uploadFileNm);
			paramMap.put("moveAfUpload", moveAfUpload);
			paramMap.put("moveToPath", moveToPath);
			
			//요청 파라미터 string 생성
			JSONObject reqJsonObj = new JSONObject(paramMap);
			IF_REQ_PAYLOAD = reqJsonObj.toString();
			
			//=========================================
			// 접속정보 셋팅
			//=========================================
			IF_SYS_GBN = tgGbn;
			
			switch(tgGbn) {
				case "PO":		//PO
					setConnSftpPo();
					break;
				case "SG":		//상공회의소
					setConnSftpSangong();
					break;
				default:
					resultMap.put("message", "SFTP 접속 정보가 없습니다.");
					return resultMap;
			}
			
			//원본 파일 경로 CLEAN PATH 적용
			epcFullPath = StringUtil.getCleanPath(epcFullPath, false);
			
			logger.info("접속정보::::"+userId+"@"+host);
			logger.info("원본파일 ::::"+epcFullPath);
			logger.info("업로드경로 ::::"+uploadPath);
			logger.info("업로드파일명 ::::"+uploadFileNm);
			//===========================================
			
			//EPC 파일 존재 여부 확인
			if(!new File(epcFullPath).exists()) {
				logger.error("원본 파일을 찾을 수 없습니다. (원본파일:"+epcFullPath+")");
				resultMap.put("message", "원본 파일을 찾을 수 없습니다.");
				return resultMap;
			}
			
			//호스트키 검증용 파일 셋팅
        	jsch.setKnownHosts(knownHosts);
			
			session = jsch.getSession(userId, host, port);
	        session.setPassword(password);

	        // 로컬일 경우, 호스트 키 검증 생략
	        if("local".equals(serverType)) {
	        	session.setConfig("StrictHostKeyChecking", "no");
	        }else {
	        	session.setConfig("StrictHostKeyChecking", "yes");
	        }
	        session.connect();
	        
	      	// 연결실패
	        if(!session.isConnected()) {
	        	resultMap.put("message", "SSH 연결에 실패하였습니다.");
	        	return resultMap;
	        }

	        channel = session.openChannel("sftp");
	        channel.connect(connTimeout);
	        
	        sftpChannel = (ChannelSftp) channel;
	        
	        // SFTP 서버에 파일 업로드 경로 없으면 생성
	        ensureRemoteDirectoryExists(sftpChannel, uploadPath);
	        
	      	// SFTP 서버 파일 업로드 full 경로
	        String uploadFullPath = uploadPath + "/" + uploadFileNm;
	        uploadFullPath = StringUtil.getCleanPath(uploadFullPath, false);
	        
	     	// 파일 업로드
	        sftpChannel.put(new FileInputStream(epcFullPath), uploadFullPath);
	        
	     	// 업로드 성공 상태 확인
	        SftpATTRS attrs = sftpChannel.lstat(uploadFullPath);
	        if(attrs == null || attrs.getSize() == 0) {
	        	logger.error("SFTP 서버 내 파일 업로드 실패");
	        	return resultMap;
	        }
	        
	        //파일 업로드 패스 반환용 Data setting
	        resultMap.put("uploadPath", StringUtil.getCleanPath(uploadPath, false));
	        
	        // 업로드 성공 시, 파일 이동하는 경우,
	        if(moveAfUpload) {
	        	// SFTP 서버에 파일 이동 경로 없으면 생성
	        	ensureRemoteDirectoryExists(sftpChannel, moveToPath);
	        	
	        	//이동 파일 전체경로 생성
	        	String moveToFullPath = moveToPath + "/" + uploadFileNm;
		        moveToFullPath = StringUtil.getCleanPath(moveToFullPath, false);	        	
		        
		        logger.info("SFTP 서버 내 파일 경로 이동 :::: "+moveToFullPath);
		        sftpChannel.rename(uploadFullPath, moveToFullPath);
		        logger.info("SFTP 서버 내 파일 이동 완료 :::: ");
		        
		        //파일 업로드 패스 반환용 Data setting (이동후)
		        resultMap.put("uploadPath", StringUtil.getCleanPath(moveToPath, false));
	        }
	        
	        resultMap.put("msgCd", OK_CODE);
			resultMap.put("message", "SFTP 파일 전송이 완료되었습니다.");
		}catch(Exception e) {
			logger.error(e.getMessage());
			resultMap.put("message", e.getMessage());
		}finally {
	        sftpChannel.exit();
	        session.disconnect();
	        
	        IF_END_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);	//인터페이스 종료일시
	        IF_RSLT_CD = MapUtils.getString(resultMap, "msgCd");		//인터페이스결과코드
	        IF_RSLT_MSG = MapUtils.getString(resultMap, "message");		//인터페이스결과메세지
	        
	        // 인터페이스 히스토리 등록
	        insertIfHistory();
	        
	        // 반환 data 추가
	        resultMap.put("sendDate", IF_END_DT);	//I/F 종료일시
	        logger.info("---------------- [ END - SFTP UPLOAD ] ------------------- ");
		}
		
		return resultMap;
	}
	
	/**
	 * SFTP 서버에 디렉토리가 없을 경우, 생성
	 * @param sftp
	 * @param path
	 * @throws SftpException
	 */
	private static void ensureRemoteDirectoryExists(ChannelSftp sftp, String path) throws SftpException {
        String[] folders = path.split("/");
        String currentPath = "";
        for (String folder : folders) {
            if (folder.length() == 0) continue;
            currentPath += "/" + folder;
            try {
                sftp.cd(currentPath);
            } catch (SftpException e) {
                // 디렉토리가 없으면 생성
                sftp.mkdir(currentPath);
                sftp.cd(currentPath);
            }
        }
    }
	
	/**
	 * SFTP 서버 파일 다운로드
	 */
	public Map<String, Object> download(String tgGbn, String remoteFullPath, String epcPath, String epcFileNm) throws Exception {
		return this.download(tgGbn, remoteFullPath, epcPath, epcFileNm, SESSION_TIME_OUT);
	}
	
	/**
	 * SFTP 서버 파일 다운로드
	 * @param tgGbn
	 * @param remoteFullPath
	 * @param epcPath
	 * @param epcFileNm
	 * @param connTimeout
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String, Object> download(String tgGbn, String remoteFullPath, String epcPath, String epcFileNm, int connTimeout) throws Exception {
		logger.info("---------------- [ START - SFTP DOWNLOAD ] ------------------- ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msgCd", NO_CODE);
		resultMap.put("message", "SFTP 파일 복사에 실패하였습니다.");
		
		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		ChannelSftp sftpChannel = null;
		InputStream is = null;
		FileOutputStream os = null;
		
		//인터페이스 시작일시
		IF_ST_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			//요청 파라미터 셋팅 ----> 히스토리생성용
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("tgGbn", tgGbn);
			paramMap.put("remoteFullPath", remoteFullPath);
			paramMap.put("epcPath", epcPath);
			paramMap.put("epcFileNm", epcFileNm);
			
			//요청 파라미터 string 생성
			JSONObject reqJsonObj = new JSONObject(paramMap);
			IF_REQ_PAYLOAD = reqJsonObj.toString();
			
			//=========================================
			// 접속정보 셋팅
			//=========================================
			IF_SYS_GBN = tgGbn;
			
			switch(tgGbn) {
				case "PO":		//PO
					setConnSftpPo();
					break;
				case "SG":		//상공회의소
					setConnSftpSangong();
					break;
				default:
					resultMap.put("message", "SFTP 접속 정보가 없습니다.");
					return resultMap;
			}
			
			//원본 파일 경로 CLEAN PATH 적용
			remoteFullPath = StringUtil.getCleanPath(remoteFullPath, false);
			
			logger.info("접속정보::::"+userId+"@"+host);
			logger.info("원본파일 ::::"+remoteFullPath);
			logger.info("업로드경로 ::::"+epcPath);
			logger.info("업로드파일명 ::::"+epcFileNm);
			//===========================================
			
			//호스트키 검증용 파일 셋팅
        	jsch.setKnownHosts(knownHosts);
			
			session = jsch.getSession(userId, host, port);
	        session.setPassword(password);
	        
	        // 로컬일 경우, 호스트 키 검증 생략
	        if("local".equals(serverType)) {
	        	session.setConfig("StrictHostKeyChecking", "no");
	        }else {
	        	//호스트키 검증용 파일 셋팅
	        	session.setConfig("StrictHostKeyChecking", "yes");
	        }
	        session.connect();
	        
	      	// 연결실패
	        if(!session.isConnected()) {
	        	resultMap.put("message", "SSH 연결에 실패하였습니다.");
	        	return resultMap;
	        }

	        channel = session.openChannel("sftp");
	        channel.connect(connTimeout);
	        
	        sftpChannel = (ChannelSftp) channel;
	        
	        // SFTP 서버에 Copy 대상 파일 존재하는지 체크
	        try {
	        	SftpATTRS attrs = sftpChannel.stat(remoteFullPath);
	        }catch(SftpException e) {
	        	if(e.id == sftpChannel.SSH_FX_NO_SUCH_FILE) {
	        		logger.error("원본 파일이 존재하지 않습니다. (원본파일:"+remoteFullPath+")");
					resultMap.put("message", "원본 파일이 존재하지 않습니다.");
					return resultMap;
	        	}else {
	        		throw e;
	        	}
	        }
	        
	        // EPC 파일 업로드 패스 확인 후, 없을 경우 경로 생성
	        File saveFolder = new File(epcPath);
            if (!saveFolder.exists()) saveFolder.mkdirs();
            
            // EPC 파일 업로드 full 경로
            String epcFullPath = epcPath + "/" + epcFileNm;
            epcFullPath = StringUtil.getCleanPath(epcFullPath, false);
	        
	        // 파일 다운로드
	        is = sftpChannel.get(remoteFullPath);
	        os = new FileOutputStream(epcFullPath);

	        byte[] buffer = new byte[1024*2];
	        int readCount;
	        while ((readCount = is.read(buffer)) > 0) {
	            os.write(buffer, 0, readCount);
	        }

	        resultMap.put("msgCd", OK_CODE);
			resultMap.put("message", "SFTP 파일 복사가 완료되었습니다.");
		}catch(Exception e) {
			logger.error(e.getMessage());
			resultMap.put("message", e.getMessage());
		}finally {
			if(is != null)try {is.close();} catch (IOException e) {System.out.println(e.getMessage());}
			if(os != null)try {os.close();} catch (IOException e) {System.out.println(e.getMessage());}
	        sftpChannel.exit();
	        session.disconnect();
	        
	        IF_END_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);	//인터페이스 종료일시
	        IF_RSLT_CD = MapUtils.getString(resultMap, "msgCd");		//인터페이스결과코드
	        IF_RSLT_MSG = MapUtils.getString(resultMap, "message");		//인터페이스결과메세지
	        
	        // 인터페이스 히스토리 등록
	        insertIfHistory();
	        logger.info("---------------- [ END - SFTP DOWNLOAD ] ------------------- ");
		}
		
		return resultMap;
	}
	
	/**
	 * IF 로그생성
	 * @param reqPayload
	 * @throws Exception
	 */
	private void insertIfHistory() throws Exception {
		try {
			/*
			 * 1) 작업자정보
			 */
			//로그인세션
			EpcLoginVO epcLoginVO = this.getWorkSessionVo();
			
			//작업자 정보
			String workId = "";	//작업자아이디
			String workNm = "";	//작업자명
			
			//로그인 세션 존재 시, 작업자 정보 setting
			if(epcLoginVO != null) {
				workId = epcLoginVO.getLoginWorkId();
				workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
			}
			
			//클라이언트 IP
			String clientIp = historyCommonService.getClientIpAddr();
			
			/*
			 * 2) 히스토리 데이터
			 */
			//소요시간
			String ifDur = DateUtil.getDurationMillis(IF_ST_DT, IF_END_DT, DateUtil.TIMESTAMP_FORMAT);
			//접속 URL
			String connUrl = host+":"+Integer.toString(port);
			
			/*
			 * 3) 히스토리 생성
			 */
			HistoryVo pLogVo = new HistoryVo();
			pLogVo.setIfGbn(IF_GBN);				//인터페이스구분코드-FTP
			pLogVo.setIfCd(IF_CD);					//인터페이스코드
//			pLogVo.setIfNm("");						//인터페이스명
			pLogVo.setReqPayLoad(IF_REQ_PAYLOAD);	//요청 파라미터
//			pLogVo.setResPayLoad(resPayload);		//응답 데이터
			pLogVo.setResultCd(IF_RSLT_CD);			//결과 코드
			pLogVo.setResultMsg(IF_RSLT_MSG);		//결과 메세지
			pLogVo.setIfStartDt(IF_ST_DT);			//인터페이스 시작일시
			pLogVo.setIfEndDt(IF_END_DT);			//인터페이스 종료일시
			pLogVo.setDuration(ifDur);				//인터페이스 소요시간
			pLogVo.setIfType("FPT");				//인터페이스 유형 (FPT)
			pLogVo.setIfUrl(connUrl);				//인터페이스 호출 URL
			pLogVo.setIfDirection("O");				//인터페이스 방향 (O:아웃바운드)
			pLogVo.setSysGbn(IF_SYS_GBN);			//시스템구분
			pLogVo.setCallUserId(workId);			//호출아이디
			pLogVo.setCallUserNm(workNm);			//호출자명
			pLogVo.setCallIp(clientIp);				//호출IP
			
			historyCommonService.insertTpcIfLog(pLogVo);
		}catch(Exception e) {
			logger.error("SFTP I/F History Insert Error ::::: "+e.getMessage());
		}
	}
	
	/**
	 * 세션정보 추출
	 * @return EpcLoginVO
	 */
	private EpcLoginVO getWorkSessionVo() {
		EpcLoginVO epcLoginVO = null;
		String sessionKey = ConfigUtils.getString("lottemart.epc.session.key");
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			
			if(request != null) {
				epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return epcLoginVO;
	}
}
