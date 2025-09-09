package com.lottemart.epc.edi.product.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.dao.NEDMKOR0010Dao;
import com.lottemart.epc.edi.product.model.NEDMKOR0010VO;
import com.lottemart.epc.edi.product.service.NEDMKOR0010Service;

/**
 * @Class Name : NEDMKOR0010ServiceImpl
 * @Description : 코리안넷 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21 	SONG MIN KYO	최초생성
 * </pre>
 */

@Service("nEDMKOR0010Service")
public class NEDMKOR0010ServiceImpl implements NEDMKOR0010Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMKOR0010ServiceImpl.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name="baseController")
	private BaseController baseController;

	@Resource(name="nEDMKOR0010Dao")
	private NEDMKOR0010Dao	nEDMKOR0010Dao;

	public static final String DEFALUT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	public static final String DEFALUT_HTTP_METHOD = "POST";
	public static final String DEFALUT_ACCEPT_ENCODING = "gzip,deflate";
	public static final int DEFALUT_CONNECT_TIMEOUNT = 10000; // 10초
	public static final int DEFALUT_READ_TIMEOUT = 600000; // 10분
	public static final String DEFALUT_CHARSET = "UTF-8";
	public static final int DEFALUT_BUFFER_SIZE = 2048;

	/**
	 * 선택한 협력업체코드의 사업자번호 가져오기
	 * @param entpCd
	 * @return
	 * @throws Exception
	 */
	public String selectBmanNo(String entpCd) throws Exception {
		return nEDMKOR0010Dao.selectBmanNo(entpCd);
	}

	/**
	 * 해당일 최근 토큰정보 조회
	 * @return
	 * @throws Exception
	 */
	public String selectAuthToken() throws Exception {
		return nEDMKOR0010Dao.selectAuthToken();
	}

	/**
	 * 인증토큰 저장
	 * @param paramHm
	 * @return
	 * @throws Exception
	 */
	public void insertAuthToken(HashMap authHm) throws Exception {
		nEDMKOR0010Dao.insertAuthToken(authHm);
	}

	/**
	 * 코리안넷 인증
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAuthToken() throws Exception {
		String baseUrl 	= config.getString("korean.net.authUrl");
		String id 		= config.getString("korean.net.id");
		String pw 		= config.getString("korean.net.pw");

		//---------- Argument Setting --------------------
		HashMap paramMap = new HashMap();
		paramMap.put("id", id);
		paramMap.put("pw", pw);
		//--------------------------------------------------

		String rtn = getServiceResult(baseUrl, paramMap);
		//logger.debug("getAuthToken rtn----->" + rtn);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap = getXmlReadValue(rtn, "auth", "authToken");


		String status = getXmlReadNodeAttribute(rtn, "resp", "code");
		resultMap.put("retCd", status);

		if (!status.equals("200")) {
			resultMap.put("retMsg", getXmlReadValue(rtn, "koreannet-dist", "resp"));
		} else {
			resultMap.put("authStartDt", getXmlReadValue(rtn, "auth", "requestDate"));
			resultMap.put("authEndDt", getXmlReadValue(rtn, "auth", "expirationDate"));
			resultMap.put("authToken", getXmlReadValue(rtn, "auth", "authToken"));
		}

		return resultMap;
	}

	/**
	 * 다운로드할 이미지 정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDownloadZipFileInfo(NEDMKOR0010VO vo) throws Exception {
		if (vo == null) {
			return null;
		}

		String baseUrl 	= config.getString("korean.net.imgUrl");
		String glnOrKbn = vo.getBmanNo();
		String gtin 	= vo.getSrcmkCd();

		baseUrl = baseUrl + "/" + glnOrKbn + "/" + gtin;
		//logger.debug("test baseUrl----->" + baseUrl);

		//---------- Argument Setting --------------------
		HashMap paramMap = new HashMap();
		paramMap.put("authToken", vo.getAuthToken());
		//--------------------------------------------------

		String rtn = getServiceResult(baseUrl, paramMap);
		//logger.debug("getDownloadImageInfo rtn----->" + rtn);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap = getXmlReadValue(rtn, "product-image", "downloadURL");

		String status = getXmlReadNodeAttribute(rtn, "resp", "code");
		resultMap.put("retCd", status);

		if (!status.equals("200")) {	// 실패
			resultMap.put("retMsg", getXmlReadValue(rtn, "koreannet-dist", "resp"));
		} else {
			resultMap.put("retMsg", getXmlReadValue(rtn, "product-image", "downloadURL"));
		}

		return resultMap;

		//return getXmlReadValue(rtn, "product-image", "downloadURL");
	}

	/**
	 * 코리안넷 이미지 다운로드
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String getDownloadZipFile(NEDMKOR0010VO vo) throws Exception {
		if (vo == null) {
			return null;
		}

		//---------- Argument Setting --------------------
		HashMap paramMap = new HashMap();
		paramMap.put("authToken", vo.getAuthToken());
		//--------------------------------------------------

		// 파라미터 설정
		String parameters = setHttpParameters(paramMap);

		InputStream in 			= null;
		FileOutputStream fos 	= null;
		OutputStream out 		= null;

		String downloadDir = config.getString("korean.net.imgPath");

		// 폴더생성
		File isDir = new File(downloadDir);
		if (!isDir.isDirectory()) {
			isDir.mkdirs();
		}

		String localFileName = vo.getBmanNo() + "_" + vo.getSrcmkCd() + ".zip";

		try {
			URL targetURL = new URL(vo.getRealImgPath());
			URLConnection urlConn = targetURL.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConn;

			httpConnection.setRequestProperty("Content-Type", DEFALUT_CONTENT_TYPE);
			httpConnection.setRequestMethod(DEFALUT_HTTP_METHOD);
			httpConnection.setRequestProperty("Accept-Encoding", DEFALUT_ACCEPT_ENCODING);	// HTTP 압축(GZip) 추가
			httpConnection.setConnectTimeout(DEFALUT_CONNECT_TIMEOUNT);	// 30초
			httpConnection.setReadTimeout(DEFALUT_READ_TIMEOUT);		// 5분
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			httpConnection.setUseCaches(false);
			httpConnection.setDefaultUseCaches(false);

			out = httpConnection.getOutputStream();
			out.write(parameters.getBytes("UTF-8"));
			out.flush();
			out.close();

			in = httpConnection.getInputStream();

			fos = new FileOutputStream(downloadDir + "/" + localFileName);

			byte[] buffer = new byte[512];
			int readcount = 0;
			while(true) {
				readcount = in.read(buffer);
				if(readcount != -1) {
				fos.write(buffer,0,readcount);
				}else {
					break;
				}
			}
		} catch(Exception e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if (in != null) {
				try {
					in.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if (fos != null) {
				try {
					fos.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
		}

		return "success";
	}

	/**
	 *  서비스 URL로 접속하여 XML 결과를 받아 Document 객체를 반환
	 * @param paramMap
	 * @param baseUrl
	 * @return
	 * @throws Exception
	 */
	private String getServiceResult(String baseUrl, HashMap paramMap) throws Exception {
		StringBuffer result = new StringBuffer();

		// 파라미터 설정
		String parameters = setHttpParameters(paramMap);

		OutputStream out 	= null;
		BufferedReader br 	= null;

		try {
			URL targetURL = new URL(baseUrl);
			URLConnection urlConn = targetURL.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConn;

			httpConnection.setRequestProperty("Content-Type", DEFALUT_CONTENT_TYPE);
			httpConnection.setRequestMethod(DEFALUT_HTTP_METHOD);
			httpConnection.setRequestProperty("Accept-Encoding", DEFALUT_ACCEPT_ENCODING);	// HTTP 압축(GZip) 추가
			httpConnection.setConnectTimeout(DEFALUT_CONNECT_TIMEOUNT);	// 30초
			httpConnection.setReadTimeout(DEFALUT_READ_TIMEOUT);		// 5분
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			httpConnection.setUseCaches(false);
			httpConnection.setDefaultUseCaches(false);

			out = httpConnection.getOutputStream();
			out.write(parameters.getBytes("UTF-8"));
			out.flush();
			out.close();

			br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

			String buffer = null;
			while (true) {// (buffer = br.readLine()) != null
				buffer = br.readLine();
				if (buffer != null) {
					result.append(buffer).append("\r\n");
				} else {
					break;
				}
			}
		} finally {
			if (out != null) {
				try {out.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
		}

		return result.toString();
	}

	/**
	 * 파라미터 설정
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private String setHttpParameters(HashMap paramMap) throws Exception {
		String parameters = "";

		Set<String> keySet = paramMap.keySet();
		Iterator keys = keySet.iterator();

		while (keys.hasNext()) {
			String paramKey = (String) keys.next();
			String paramValue = (String) paramMap.get(paramKey);

			if (parameters.equals("")) {
				parameters += paramKey + "=" + URLEncoder.encode(paramValue, "UTF-8");
			} else {
				parameters += "&" + paramKey + "=" + URLEncoder.encode(paramValue, "UTF-8");
			}
		}

		//logger.debug("parameters----->" + parameters);
		return parameters;
	}

	/**
	 * XML 읽기
	 * @param xmlStr
	 * @param retValue
	 * @return
	 * @throws Exception
	 */
	//private Map<String, Object> getXmlReadValue(String xmlStr, String tagName, String retValue) throws Exception {
	private String getXmlReadNodeAttribute(String xmlStr, String tagName, String retValue) throws Exception {
		String ret = "";

		Document doc = convertStringToDocument(xmlStr);
		doc.getDocumentElement().normalize();
		//logger.debug("Root element :" + doc.getDocumentElement().getNodeName());

		return getNodeAttribute(doc, tagName, retValue);
	}

	/**
	 * XML 읽기
	 * @param xmlStr
	 * @param retValue
	 * @return
	 * @throws Exception
	 */
	//private Map<String, Object> getXmlReadValue(String xmlStr, String tagName, String retValue) throws Exception {
	private String getXmlReadValue(String xmlStr, String tagName, String retValue) throws Exception {
		String ret = "";

		Document doc = convertStringToDocument(xmlStr);

		doc.getDocumentElement().normalize();
		//logger.debug("Root element :" + doc.getDocumentElement().getNodeName());

		//String retCd = getNodeAttribute(doc, "resp", "code");

		//----- 성공(200)이 아니면 return
		/*if (!retCd.equals("200")) {
			resultMap.put("retCd", retCd);
			resultMap.put("retMsg", getTagValue(doc, "koreannet-dist", "resp"));

			return resultMap;
		}*/

		//resultMap.put("retCd", retCd);
		//resultMap.put("retMsg", getTagValue(doc, tagName, retValue));

		/*NodeList nList = doc.getElementsByTagName(tagName);
		logger.debug("----->" + nList.getLength());
		//logger.debug("-----------------------");

		if (nList.getLength() > 0) {
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					resultMap.put("retCd", "0");
					resultMap.put("retMsg", getTagValue(retValue, eElement));
					//retValue = getTagValue(retValue, eElement);
					//logger.debug("id : " + getTagValue("id", eElement));
					//logger.debug("authToken : " + getTagValue("authToken", eElement));
					//logger.debug("requestDate : " + getTagValue("requestDate", eElement));
					//logger.debug("expirationDate : " + getTagValue("expirationDate", eElement));
				}
			}
		}*/

		//logger.debug("retMsg----->" + resultMap.get("retMsg"));
		return getTagValue(doc, tagName, retValue);
	}

	/**
	 * String To Document(String 문자열을 XML로 변환)
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	private Document convertStringToDocument(String xmlStr) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
		return doc;
	}

	/**
	 * 해당 Element에서 속성값 가져오기
	 * @param doc
	 * @param sTag
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	private String getNodeAttribute(Document doc, String sTag, String attr) throws Exception {
		NodeList nList = doc.getElementsByTagName(sTag);
		Node nNode = nList.item(0);
		Element nElement = (Element) nNode;
		//logger.debug("code----->" + nElement.getAttribute(attr));

		return nElement.getAttribute(attr);
	}

	/**
	 * XML Node에서 해당 값 가져오기
	 * @param sTag
	 * @param eElement
	 * @return
	 */
	private String getTagValue(Document doc, String tagName, String attr) {
		String retValue = "";

		NodeList nList = doc.getElementsByTagName(tagName);
		//logger.debug("----->" + nList.getLength());
		//logger.debug("-----------------------");

		if (nList.getLength() > 0) {
			/*
			배열로 구성되었을 경우 처리하는 로직
			List 형태로 return 해야 함
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element nElement = (Element) nNode;

					NodeList cnlList = nElement.getElementsByTagName(attr).item(0).getChildNodes();
					Node nValue = (Node) cnlList.item(0);

					retValue = nValue.getNodeValue();
					//retValue = getTagValue(retValue, eElement);
					//logger.debug("id : " + getTagValue("id", eElement));
					//logger.debug("authToken : " + getTagValue("authToken", eElement));
					//logger.debug("requestDate : " + getTagValue("requestDate", eElement));
					//logger.debug("expirationDate : " + getTagValue("expirationDate", eElement));
				}
			}
			*/

			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element nElement = (Element) nNode;

				NodeList cnlList = nElement.getElementsByTagName(attr).item(0).getChildNodes();
				Node nValue = (Node) cnlList.item(0);

				retValue = nValue.getNodeValue();
			}
		}

		//NodeList nlList = eElement.getElementsByTagName(attr).item(0).getChildNodes();
		//Node nValue = (Node) nlList.item(0);

		//return nValue.getNodeValue();

		return retValue;
	}

	/**
	 * ZIP 파일 압축풀기
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	public String unZipFile(NEDMKOR0010VO vo) throws Exception {
		if (vo == null) {
			return null;
		}

		String downloadDir = config.getString("korean.net.imgPath");
		String localFileName = SecureUtil.splittingFilter(vo.getBmanNo()) + "_" + SecureUtil.splittingFilter(vo.getSrcmkCd()) + ".zip";
		String zipFileName = downloadDir + "/" + localFileName;

		File zipFile 		= new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis 	= null;
		ZipEntry zipentry 	= null;

		try {
			// 파일 스트림
			fis = new FileInputStream(zipFile);

			// Zip 파일 스트림
			zis = new ZipInputStream(fis);

			// entry가 없을때까지 뽑기
			while (true) {//(zipentry = zis.getNextEntry()) != null
				zipentry = zis.getNextEntry();
				if (zipentry != null) {
					String filename = zipentry.getName();
					// System.out.println("filename----->" + filename);
					// File file = new File(directory, filename);
					File file = new File(downloadDir + "/" + SecureUtil.splittingFilter(vo.getBmanNo()) + "_"
							+ SecureUtil.splittingFilter(vo.getSrcmkCd()), filename);

					// entiry가 폴더면 폴더 생성
					if (zipentry.isDirectory()) {
						file.mkdirs();
					} else {
						// 파일이면 파일 만들기
						createFile(file, zis);
					}
				}else{
					break;
					}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (zis != null) {
				try {
					zis.close();
			} catch(Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
			} catch(Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
		}

		return "success";
	}

	/**
	 * 파일 만들기 메소드
	 * @param file
	 * @param zis
	 * @throws Exception
	 */
	private void createFile(File file, ZipInputStream zis) throws Exception {
		if (file == null || zis == null) {
			return;
		}

		// 디렉토리 확인
		File parentDir = new File(file.getParent());
		//System.out.println("parentDir----->" + parentDir);
		// 디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}

		FileOutputStream fos = null;

		// 파일 스트림 선언
		try {
			fos = new FileOutputStream(file);

			byte[] buffer = new byte[256];
			int size = 0;

			// Zip스트림으로부터 byte뽑아내기
			while (true) {//(size = zis.read(buffer)) > 0
				// byte로 파일 만들기
				size = zis.read(buffer);
				if (size > 0) {
					fos.write(buffer, 0, size);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
			} catch(Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 해당파일 변형속성 규칙에 맞게 파일명 변경하여 이동
	 * @param vo
	 * @param request
	 * @throws Exception
	 */
	public String moveFile(NEDMKOR0010VO vo) throws Exception {
		if (vo == null) {
			return null;
		}

		String downloadDir = config.getString("korean.net.imgPath");

		// 압출풀 폴더 정보
		String zipDir = downloadDir + "/" + SecureUtil.splittingFilter(vo.getBmanNo()) + "_" + SecureUtil.splittingFilter(vo.getSrcmkCd());

		File dirFile = new File(zipDir);
		File[] fileList = dirFile.listFiles();

		// POG이미지 폴더
		String uploadDir = baseController.makeSubFolderForOffline(SecureUtil.splittingFilter(vo.getPgmId()));

		String fileGbn = "";

		FileInputStream fis 	= null;
		FileOutputStream fos 	= null;

		// variant 없는 경우 '000'으로 설정
		if (vo.getVariant() == null || vo.getVariant().equals("")) {
			vo.setVariant("000");
		}

		//----- POG 이미지 정보 삭제 --------------------
		HashMap delImgHm = new HashMap();
		delImgHm.put("pgmId", vo.getPgmId());
		delImgHm.put("variant", vo.getVariant());

		nEDMKOR0010Dao.deleteSaleImg(delImgHm);		// 개별삭제
		//nEDMKOR0010Dao.deleteSaleImgAll(vo.getPgmId());	// 전체삭제(일괄생성 기능)
		//--------------------------------------------------

		//----- 등록된 상품변형속성 조회
		//List<NEDMKOR0010VO> varAtt = nEDMKOR0010Dao.selectVariant(vo.getPgmId());		// 등록된 변형속성별 이미지 생성(일괄생성 기능)

		// 개별생성하도록 설정
		List<NEDMKOR0010VO> varAtt = new ArrayList<NEDMKOR0010VO>();
		NEDMKOR0010VO listVO = new NEDMKOR0010VO();
		listVO.setVariant(vo.getVariant());
		varAtt.add(listVO);
		//--------------------------------------------------

		String tempPath = "";
		String tempFileName = "";

		for (File tempFile : fileList) {
			if (tempFile.isFile()) {
				tempPath = SecureUtil.splittingFilter(tempFile.getParent());
				tempFileName = SecureUtil.splittingFilter(tempFile.getName());
				//logger.debug("Path="+tempPath);
				//logger.debug("FileName="+tempFileName);

				// 다운받은 이미지
				//oFile = new File(tempPath + "/" + tempFileName);

				// 파일구분(뒤 6자리)
				fileGbn = tempFileName.substring(tempFileName.length() - 6, tempFileName.length());
				//logger.debug("fileGbn=" + fileGbn);

				// 새로지정할 파일명 설정
				String prodImgId = StringUtils.substring(SecureUtil.splittingFilter(vo.getPgmId()), 2, 8) + StringUtils.right(SecureUtil.splittingFilter(vo.getPgmId()), 5);

				if (fileGbn.equals("_6.jpg") || fileGbn.equals("_7.jpg") || fileGbn.equals("_8.jpg")) {
					try {
						if (varAtt.size() == 0) {	// 변형속성이 없는 경우
							try {
								fis = new FileInputStream(tempPath + "/" + tempFileName);

								// 파일명 변경
								if (fileGbn.equals("_6.jpg")) {		// 정면
									fos = new FileOutputStream(uploadDir + "/" + prodImgId + "000.1.jpg");
								} else if (fileGbn.equals("_7.jpg")) {	// 측면
									fos = new FileOutputStream(uploadDir + "/" + prodImgId + "000.2.jpg");
								} else if (fileGbn.equals("_8.jpg")) {	// 윗면
									fos = new FileOutputStream(uploadDir + "/" + prodImgId + "000.3.jpg");
								}

								int data = 0;
								while (true) {//(data = fis.read()) != -1
									data = fis.read();
									if(data != -1) {
										fos.write(data);
									}else {
										break;
									}
								}
							} catch (Exception e) {
								throw e;
							} finally {
								if (fis != null) {
									try {
										fis.close();
								} catch (Exception e) {
									logger.debug("error : " + e.getMessage());
									}
								}
								if (fos != null) {
									try {
										fos.close();
								} catch (Exception e) {
									logger.debug("error : " + e.getMessage());
									}
								}
							}

							//---------- POG 이미지정보 추가 --------------------
							vo.setVariant("000");
							vo.setImgNm(prodImgId + "000");
							nEDMKOR0010Dao.insertSaleImg(vo);
							//---------------------------------------------
						} else {	// 변형속성이 있는 경우
							String newFileNm = "";
							String variant = "";

							NEDMKOR0010VO varAttVO = new NEDMKOR0010VO();

							// 변형속성만큼 파일 생성
							for (int i = 0; i < varAtt.size(); i++) {
								try {
									fis = new FileInputStream(tempPath + "/" + tempFileName);

									varAttVO = varAtt.get(i);
									variant = SecureUtil.splittingFilter(varAttVO.getVariant());	// 변형속성

									newFileNm = uploadDir + "/" + prodImgId + variant;

									// 파일명 변경
									if (fileGbn.equals("_6.jpg")) {		// 정면
										fos = new FileOutputStream(newFileNm + ".1.jpg");
									} else if (fileGbn.equals("_7.jpg")) {	// 측면
										fos = new FileOutputStream(newFileNm + ".2.jpg");
									} else if (fileGbn.equals("_8.jpg")) {	// 윗면
										fos = new FileOutputStream(newFileNm + ".3.jpg");
									}

									int data = 0;
									while (true) {// (data = fis.read()) != -1
										data = fis.read();
										if (data != -1) {
											fos.write(data);
										} else {
											break;
										}
									}
									//---------- POG 이미지정보 추가 --------------------
									vo.setVariant(variant);
									vo.setImgNm(prodImgId + variant);
									nEDMKOR0010Dao.insertSaleImg(vo);
									//---------------------------------------------
								} catch (Exception e) {
									throw e;
								} finally {
									if (fis != null) {
										try {
											fis.close();
									} catch (Exception e) {
										logger.debug("error : " + e.getMessage());
										}
									}
									if (fos != null) {
										try {
											fos.close();
									} catch (Exception e) {
										logger.debug("error : " + e.getMessage());
										}
									}
								}
							}
						}

						try {	// 복사 완료된 파일 삭제
							File delFile = new File(tempPath + "/" + tempFileName);
							delFile.delete();
						} catch(Exception e) {
							throw e;
						}
					} catch(Exception e) {
						throw e;
					} finally {
						if (fis != null) try {
							fis.close();
					} catch(Exception e) {
						logger.debug("error : " + e.getMessage());
					}
						if (fos != null) try {
							fos.close();
					} catch(Exception e) {
						logger.debug("error : " + e.getMessage());
					}
					}
				} else {	// 사용하는 이외의 파일은 삭제
					try {
						File delFile = new File(tempPath + "/" + tempFileName);
						delFile.delete();
					} catch(Exception e) {
						throw e;
					}
				}
			}
		}

		//---------- ZIP 파일 삭제 Start
		try {
			File zipFile = new File(zipDir + ".zip");
			zipFile.delete();
		} catch(Exception e) {
			throw e;
		}
		//---------- ZIP 파일 삭제 End

		//---------- 압축푼 폴더 삭제 Start
		try {
			File zipFileDir = new File(zipDir);
			zipFileDir.delete();
		} catch(Exception e) {
			throw e;
		}
		//---------- 압축푼 폴더 삭제 Start

		return "success";
	}

}
