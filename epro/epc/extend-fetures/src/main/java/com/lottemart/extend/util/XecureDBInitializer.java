package com.lottemart.extend.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softforum.xdbe.XdspNative;

/**
 * XecureDB 초기화 유틸리티
 * Resorce에 존재하는 XecureDB 설정 파일을 실행 계정 home에 설치하고 classload에 적재시켜 XecureDB 초기화에 사용될 수 있도록 합니다.
 * XecureConfig Template Zip 파일들은 이 라이브러리 jar 내에 함께 제공 됩니다.
 * 템플릿 파일과 변수가 변경되지 않으면 새로 작성 하지 않습니다.
 * @author pat
 *
 */
public class XecureDBInitializer {
	
	private static final Pattern	PTN_REPLACE	= Pattern.compile("\\$\\{(.+?)\\}");
	
	/**
	 * 초기화 합니다.
	 * @param serverType 초기화 대상 서버 타입
	 * @param connectId XecureDB 연결 ID
	 * @throws IOException XecureDB 설정 파일 작성 실패시
	 */
	public static void init(String serverType, String connectId)
		throws IOException {
		File				xecureHomeDir;
		File				xecureConfigDir;
		File				xecureLogDir;
		File				xecureDataDir;
		File				configHashFile;
		Map<String, String>	valueMap;
		MessageDigest		hashDigest;
		URL					configTemplateUrl;
		String				hashValue;
		boolean				needUpdate;
		Logger				logger;
		
		logger	= LoggerFactory.getLogger(XecureDBInitializer.class);
		// xecure home 폴더 작성
		xecureHomeDir	= new File(System.getProperty("user.home"), "xecuredb/" + Integer.toHexString(connectId.hashCode()));
		xecureHomeDir.mkdirs();
		if (!xecureHomeDir.exists()) {
			throw new RuntimeException("can't XecureDB Home dir " + xecureHomeDir);
		}
		xecureConfigDir	= new File(xecureHomeDir, "conf");
		xecureConfigDir.mkdir();
		xecureLogDir	= new File(xecureHomeDir, "log");
		xecureLogDir.mkdir();
		xecureDataDir	= new File(xecureHomeDir, "data");
		xecureDataDir.mkdir();
		configHashFile	= new File(xecureHomeDir, "config-hash");
		
		// xecure 환경 설정 변수 
		valueMap	= new TreeMap<>();
		valueMap.put("$", "$");
		valueMap.put("connect.id", connectId);
		valueMap.put("file.config", new File(xecureConfigDir, "xdsp_plain.conf").getCanonicalPath().replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\")));
		valueMap.put("file.log", new File(xecureLogDir, "xdsp_apps.log").getCanonicalPath().replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\")));
		valueMap.put("path.log", xecureLogDir.getCanonicalPath().replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\")));
		valueMap.put("path.data", xecureDataDir.getCanonicalPath().replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\")));
		
		
		logger.info("XecureDB home path {}", xecureHomeDir);
		// 변경감지 hash 계산
		try {
			hashDigest	= MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			try {
				hashDigest	= MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ie) {
				// never thrown
				throw new RuntimeException("can't find Hash Algorithm", ie);
			}
		}
		for (Entry<String, String> valueEnt : valueMap.entrySet()) {
			try {
				hashDigest.update(valueEnt.getKey().getBytes("ISO-8859-1"));
				hashDigest.update(valueEnt.getValue().getBytes("ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				// NEVER throws
				throw new RuntimeException(e);
			}
		}
		
		configTemplateUrl	= Thread.currentThread().getContextClassLoader().getResource("xecuredb/config/" + serverType + ".zip");
		if (configTemplateUrl == null) {
			configTemplateUrl	= Thread.currentThread().getContextClassLoader().getResource("xecuredb/config/default.zip");
			logger.info("XecureDB home template file using default");
		} else {
			logger.info("XecureDB home template file using {}", serverType);
		}
		
		try (ZipInputStream	zis = new ZipInputStream(configTemplateUrl.openStream())) {
			ZipEntry 		zent;
			byte[]	buf;
			int		rsize;
			
			buf	= new byte[1024];
			while ((zent = zis.getNextEntry()) != null) {
				hashDigest.update(zent.getName().getBytes());
				while ((rsize = zis.read(buf)) != -1) {
					hashDigest.update(buf, 0, rsize);
				}
				
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		hashValue	= DatatypeConverter.printBase64Binary(hashDigest.digest());
		
		// 변경 필요 여부 확인
		needUpdate	= true;
		if (configHashFile.exists()) {
			try (BufferedReader rd	= new BufferedReader(new InputStreamReader(new FileInputStream(configHashFile)))) {
				String	storedHashValue;
				
				while ((storedHashValue	= rd.readLine()) != null && storedHashValue.trim().length() == 0);
				needUpdate	= !hashValue.equals(storedHashValue.trim());
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		if (needUpdate) {
			logger.info("XecureDB home will update(or create)");
			
			// xecure 환경 설정 파일 처리
			try (ZipInputStream	zis = new ZipInputStream(configTemplateUrl.openStream())) {
				ZipEntry 		zent;
				BufferedReader	rd;
				
				rd	= new BufferedReader(new InputStreamReader(zis));
				while ((zent = zis.getNextEntry()) != null) {
					File	writeFile;
					
					writeFile	= new File(xecureConfigDir, zent.getName());
					try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(writeFile)))) {
						String			line;
						StringBuffer	sb;
						
						sb	= new StringBuffer();
						while((line = rd.readLine()) != null) {
							Matcher			mch;
							
							mch	= PTN_REPLACE.matcher(line);
							if (!mch.find()) {
								pw.println(line);
								continue;
							}
							sb.setLength(0);
							do {
								String	replaceText;
								
								replaceText	= valueMap.get(mch.group(1));
								if (replaceText == null) {
									replaceText	= mch.group(0);
								}
								mch.appendReplacement(sb, Matcher.quoteReplacement(replaceText));
								
							} while (mch.find());
							mch.appendTail(sb);
							pw.println(sb.toString());
						}
						pw.flush();
					}catch(Exception e) {
						logger.error(e.getMessage());
					}
				}
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		// XdspNative  ClassLoader에 삽입
		URLClassLoader	xdspClassLoader;
		Method			addUrlMethod;			
		
		try {
			ClassLoader	classLoader;
			boolean		isAdded;
			
			isAdded			= false;
			classLoader		= XdspNative.class.getClassLoader();
			if (classLoader instanceof URLClassLoader) {
				xdspClassLoader	= (URLClassLoader)classLoader;
				addUrlMethod	= URLClassLoader.class.getDeclaredMethod("addURL", new Class<?>[] {URL.class});
				addUrlMethod.setAccessible(true);
				addUrlMethod.invoke(xdspClassLoader, xecureConfigDir.toURI().toURL());
				isAdded	= true;
			} else {
				for (Method method : classLoader.getClass().getDeclaredMethods()) {
					Class<?>[]	methodParameterTypes;
					
					methodParameterTypes	= method.getParameterTypes();
					if (methodParameterTypes == null || methodParameterTypes.length != 1 || methodParameterTypes[0] != URL.class) {
						continue;
					}
					method.setAccessible(true);
					method.invoke(classLoader, xecureConfigDir.toURI().toURL());
					isAdded	= true;
				}
			}
			if (isAdded) {
				logger.info("add XecureDB config dir {} to class path (ClassLoader {})", xecureConfigDir, classLoader);
			} else {
				throw new RuntimeException("there is no Method(URL) on classloader " + classLoader);
			}
		} catch (Exception e) {
			throw new RuntimeException("XecureDB 동적 환경 설정 실패.", e);
		}
		
		// Hash파일 작성
		try (PrintWriter pw = new PrintWriter(new FileWriter(configHashFile))) {
			pw.println(hashValue);
			pw.flush();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

}
