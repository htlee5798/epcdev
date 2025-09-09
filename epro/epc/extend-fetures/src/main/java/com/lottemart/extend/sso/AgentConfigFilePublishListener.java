package com.lottemart.extend.sso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 서블릿 컨테이너가 기동할때 SSO AgentConfig 파일을 작성하는 리스너 입니다.
 * WEB.xml에 Spring WebApplicationContext보다 뒤에 정의 되어야 합니다.
 * @author pat
 *
 */
public class AgentConfigFilePublishListener
	implements ServletContextListener {
	
	public static final String	PARAM_TEMPLATE_FILE_PATH	= "AGENT_TEMPLATE_FILE_PATH";
	public static final String	PARAM_CONFIG_PUBLISH_TO		= "AGENT_CONFIG_PUBLISH_TO";
	public static final Pattern	PTN_SERVER_IP				= Pattern.compile("#serverip#", Pattern.CASE_INSENSITIVE);
	
	
	private Logger				logger	= LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// DO notting
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext		svctx;
		String				templatePath;
		String				publishTo;
		File				contextRealPath;
		String				templateFilePath;
		ApplicationContext	springContext;
		Environment			springEnv;
		File				templateFile;
		File				publishToFile;
		
		// 설정 검사
		svctx				= sce.getServletContext();
		templatePath		= svctx.getInitParameter(PARAM_TEMPLATE_FILE_PATH);
		if (StringUtils.isEmpty(templatePath)) {
			throw new RuntimeException("AgentConfig Template file path required.(Servlet Init Param " + PARAM_TEMPLATE_FILE_PATH + ")");
		}
		publishTo			= svctx.getInitParameter(PARAM_CONFIG_PUBLISH_TO);
		if (StringUtils.isEmpty(publishTo)) {
			throw new RuntimeException("AgentConfig pubish to path required.(Servlet Init Param " + PARAM_CONFIG_PUBLISH_TO + ")");
		}
		springContext		= WebApplicationContextUtils.getWebApplicationContext(svctx);
		if (springContext == null) {
			throw new RuntimeException("can't find spring context. spring context required");
		}
		springEnv			= springContext.getEnvironment();
		contextRealPath		= new File(svctx.getRealPath("/"));
		templateFilePath	= springEnv.resolvePlaceholders(templatePath);
		templateFile		= new File(contextRealPath, templateFilePath);
		if (!templateFile.exists()) {
			this.logger.info("threre is no agent config template file ({})", templateFilePath);
			return;
		}
		publishTo			= springEnv.resolvePlaceholders(publishTo);
		publishToFile		= new File(contextRealPath, publishTo);
		try {
			this.doPublish(templateFile, publishToFile);
		} catch (IOException e) {
			throw new RuntimeException("AgentConfig file publish fail", e);
		}
	}
	
	private String getMaxIp()
		throws SocketException {
		Enumeration<NetworkInterface>	nifs;
		byte[]							rvAddr;
		StringBuilder					sb;
		
		rvAddr	= new byte[4];
		nifs	= NetworkInterface.getNetworkInterfaces();
		while (nifs.hasMoreElements()) {
			NetworkInterface			nif;
			Enumeration<InetAddress>	nifAddrs;
			
			nif	= nifs.nextElement();
			if (nif.isLoopback() || !nif.isUp() || nif.isVirtual()) {
				continue;
			}
			nifAddrs	= nif.getInetAddresses();
			while (nifAddrs.hasMoreElements()) {
				InetAddress	nifAddr;
				byte[]		bAddr;		
				
				nifAddr	= nifAddrs.nextElement();
				bAddr	= nifAddr.getAddress();
				if (bAddr.length != 4) {
					continue;
				}
				for (int i=0; i<4; i++) {
					if (Byte.toUnsignedInt(bAddr[i]) > Byte.toUnsignedInt(rvAddr[i])) {
						rvAddr	= bAddr;
						break;
					}
				}
			}
		}
		sb	= new StringBuilder();
		for (int i=0; i<4; i++) {
			if (i > 0) {
				sb.append(".");
			}
			sb.append(Byte.toUnsignedInt(rvAddr[i]));
		}
		return sb.toString();
	}
	
	private void doPublish(File templateFile, File publishFile)
		throws IOException {
		String				maxIp;

		try {
			maxIp				= this.getMaxIp();
		} catch (SocketException e) {
			throw new RuntimeException("fail to get IP for AgentConfig", e);
		}
		this.logger.info("server ip is {}", maxIp);
		
		try (BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(templateFile), "UTF-8"))) {
			try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(publishFile), "UTF-8"))) {
				String	line;
				
				while ((line = rd.readLine()) != null) {
					pw.println(PTN_SERVER_IP.matcher(line).replaceAll(maxIp));
				}
				pw.flush();
			}
		}
		this.logger.info("AgentConfig file {} published", publishFile);
	}
}
