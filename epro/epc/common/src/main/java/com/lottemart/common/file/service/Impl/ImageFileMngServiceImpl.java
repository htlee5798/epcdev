package com.lottemart.common.file.service.Impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.CdnOpenApiUtil;

import lcn.module.framework.base.AbstractServiceImpl;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : ImageFileMngServiceImpl.java
 * @Description : Image Server(QS/QC) Manage Service
 * @Modification Information
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 2. 20. 오후 5:04:15 조성옥
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("imageFileMngService")
public class ImageFileMngServiceImpl extends AbstractServiceImpl implements ImageFileMngService {

	final static Logger logger = LoggerFactory.getLogger(ImageFileMngServiceImpl.class);

	@Autowired
	private ConfigurationService config;

	/**
	 * Desc : ImageQC 서버 purge(캐쉬이미지 리로딩) 요청
	 * imageqc.use.yn = y
	 * imageqc.server.ip = 124.243.53.33
	 * imageqc.server.purge.port = 8011
	 * image.purge.01.scale = 60x60|75x75|100x100|160x160|250x250|500x500
	 * image.purge.02.scale = 60x60|75x75|100x100|160x160|250x250|500x500
	 * 
	 * 2016.5.23 조성옥 QC서버 Purge URL 패턴 변경
	 * 직사각형 이미지 처리를 위한 서버 설정 변경에 따른 Purge URL 패턴 변경 ( LK -> LB )
	 * (변경전)
	 * http://{HOSTNAME}:8011/erase?/cmd/LK_{사이즈}x{사이즈}/src/http://127.0.0.1/{OriginalImagePath}/{OriginalFileName}
	 * (변경후)
	 * http://{HOSTNAME}:8011/erase?/cmd/LB_{사이즈}x{사이즈}/src/http://127.0.0.1/{OriginalImagePath}/{OriginalFileName}
	 * 
	 * @Method Name : purgeImageQCServer
	 * @param type
	 * @param uniqueId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public void purgeImageQCServer(String type, String uniqueId) {

		try {
			boolean isImageQCUsed = false;
			String qcUsed = StringUtils.defaultString(config.getString("imageqc.use.yn"), "n");
//			logger.debug("qcUsed^^^^^" + qcUsed + "^^^^^");
			if ("Y".equalsIgnoreCase(qcUsed)) isImageQCUsed = true;
			if (isImageQCUsed) {
				String qcServerIp = config.getString("imageqc.server.ip");
				String qcServerIp2 = config.getString("imageqc.server.ip2");
				logger.debug("qcServerIp^^^^^" + qcServerIp + "^^^^^");
				logger.debug("qcServerIp2^^^^^" + qcServerIp2 + "^^^^^");
				String qcServerPurgePort = config.getString("imageqc.server.purge.port");
//				logger.debug("qcServerPurgePort^^^^^" + qcServerPurgePort + "^^^^^");

				// imageqc.save.root.dir = /nas_web/webhomes/lim_stg/static_root/images
				String imageqcRootPath = config.getString("imageqc.save.root.dir");
				imageqcRootPath = StringUtils.replace(imageqcRootPath, "/nas_web/webhomes", "");
//				logger.debug("imageqcRootPath^^^^^" + imageqcRootPath + "^^^^^");

//imageqc.sub.url.product.edi   = /edi/online
//imageqc.sub.url.product.bos   = /prodimg
//imageqc.sub.url.exhibition    = /exhibition
//imageqc.sub.url.event.pc      = /front/event/bn/pc
//imageqc.sub.url.event.mobile  = /front/event/bn/mobile
//imageqc.sub.url.trend.pc      = /front/trend/bn/pc
//imageqc.sub.url.trend.mobile  = /front/trend/bn/mobile
//imageqc.sub.url.recipe        = /front/recipe
//imageqc.sub.url.productreview = /files/boardfile/productreview

//imageqc.cache.purge.size.product.edi   = 80x80|90x90|94x94|120x120|140x140|158x158|185x185|204x204|208x208|210x210|220x220|250x250|272x272|308x308|320x320|375x375|450x450|464x464|500x500|584x584|640x640
//imageqc.cache.purge.size.product.bos   = 80x80|90x90|94x94|120x120|140x140|158x158|185x185|204x204|208x208|210x210|220x220|250x250|272x272|308x308|320x320|375x375|450x450|464x464|500x500|584x584|640x640
//imageqc.cache.purge.size.exhibition    = 80x80|120x120|216x216|240x240|584x460
//imageqc.cache.purge.size.event.pc      = 80x63|294x230
//imageqc.cache.purge.size.event.mobile  = 120x95|180x140|240x188
//imageqc.cache.purge.size.trend.pc      = 80x63|294x230|312x244
//imageqc.cache.purge.size.trend.mobile  = 120x95|210x165|584x460
//imageqc.cache.purge.size.recipe        = 210x210|240x240|250x250|278x278|415x415|515x515|584x584
//imageqc.cache.purge.size.productreview = 138x138
//imageqc.cache.purge.size.product.wide	 = 720x405

				String subFolerName = "";
				String imagePath = "";
				String imageScaleStr = "";
				if ("01".equals(type)) {
					// EDI 상품이미지(EPC)
					subFolerName = uniqueId.substring(0, 4) + "/" + uniqueId.substring(4, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.edi"), "");
				} else if ("02".equals(type)) {
					// BOS 상품이미지(BOS)
					subFolerName = uniqueId.substring(0, 5);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.bos"), "");
				} else if ("03".equals(type)) {
					// 기획전 이미지 Scale(PC용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.exhibition"), "");
				} else if ("04".equals(type)) {
					// 기획전 이미지 Scale(모바일용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.exhibition"), "");
				} else if ("05".equals(type)) {
					// 이벤트 이미지 Scale(PC용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.event.pc") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.event.pc"), "");
				} else if ("06".equals(type)) {
					// 이벤트 이미지 Scale(모바일용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.event.mobile") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.event.mobile"), "");
				} else if ("07".equals(type)) {
					// 트렌드 이미지 Scale(PC용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.trend.pc") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.trend.pc"), "");
				} else if ("08".equals(type)) {
					// 트렌드 이미지 Scale(모바일용)
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.trend.mobile") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.trend.mobile"), "");
				} else if ("09".equals(type)) {
					// 요리왕장보고 이미지 Scale
					subFolerName = uniqueId.substring(10, 12);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.recipe") + "/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.recipe"), "");
				} else if ("10".equals(type)) {
					// 상품평
					subFolerName = uniqueId.substring(0, 4);
					String subFolerName2 = uniqueId.substring(4, 6);
					String subFolerName3 = uniqueId.substring(6, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.productreview") + "/" + subFolerName + "/" + subFolerName2 + "/" + subFolerName3;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.productreview"), "");
				} else if ("11".equals(type)) {
					// 와이드 이미지
					subFolerName = uniqueId.substring(0, 5) + "/" + uniqueId.substring(5, 9);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/wide/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.wide"), "");
				} else if ("12".equals(type)) {
					// EDI WIDE 상품이미지
					subFolerName = uniqueId.substring(0, 4) + "/" + uniqueId.substring(4, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/wide/" + subFolerName;
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.wide"), "");
				}
//				logger.debug("imagePath^^^^^" + imagePath + "^^^^^");
//				logger.debug("imageScaleStr^^^^^" + imageScaleStr + "^^^^^");

				StringTokenizer imageScaleSt = new StringTokenizer(imageScaleStr, "|");
				StringBuilder reqSb = null;
				StringBuilder reqSb2 = null;
				String tempImageScaleSt = "";
				while (imageScaleSt.hasMoreElements()) {
					reqSb = new StringBuilder();
					reqSb2 = new StringBuilder();
					tempImageScaleSt = imageScaleSt.nextToken();

					reqSb.append("http://").append(qcServerIp).append(":").append(qcServerPurgePort);
					reqSb2.append("http://").append(qcServerIp2).append(":").append(qcServerPurgePort);

					if ("09".equals(type)) {
						// 요리왕 장보고인 경우
						// 기획팀 요청으로 이미지 외곡이 발생하더라도 정해진 사이즈로 늘리도록 옵션 적용
						// 이미지가 가로로 늘어나거나 세로로 늘어날 수 있음
						// 이미지가 이상하면 원본을 수정해서 다시 올린다고 기획팀에서 얘기함
						reqSb.append("/erase?/cmd/L_").append(tempImageScaleSt); // 사이즈 적용
						reqSb2.append("/erase?/cmd/L_").append(tempImageScaleSt); // 사이즈 적용
					} else {
						// 정해진 사이즈 만큼 이미지 크기 조정하고 여백은 하얀색으로 채움
						reqSb.append("/erase?/cmd/LB_").append(tempImageScaleSt); // 사이즈 적용
						reqSb2.append("/erase?/cmd/LB_").append(tempImageScaleSt); // 사이즈 적용
					}

					reqSb.append("/Q_100/src/").append("http://127.0.0.1").append(imagePath).append("/").append(uniqueId);
					reqSb2.append("/Q_100/src/").append("http://127.0.0.1").append(imagePath).append("/").append(uniqueId);
					requestImageServer(reqSb.toString());
					requestImageServer(reqSb2.toString());
					//logger.debug("Call url^^^^^" + reqSb.toString());
					reqSb.delete(0, reqSb.length());
					reqSb2.delete(0, reqSb2.length());
				}
			}
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		}
	}

	/**
	 * Desc : CDN 서버 purge(캐쉬이미지 리로딩) 요청
	 * cdn.purge.req.url = ocsp.kr.cdnetworks.com
	 * cdn.purge.user.id = jhheo@sys4u.co.kr
	 * cdn.purge.user.pw = changeme!
	 * image.purge.01.scale = 60x60|75x75|100x100|160x160|250x250|500x500
	 * image.purge.02.scale = 60x60|75x75|100x100|160x160|250x250|500x500
	 * 
	 * https://도메인/purge/rest/doPurge?user=&pass=&pad=image.lottemart.com&type=item&path=/lim/static_root/images/prodimg/88092/8809268452123_1_500.jpg
	 * https://도메인/purge/rest/doPurge?user=&pass=&pad=simage.lottemart.com&type=wildcard&path=/lim/static_root/images/prodimg/88092/8809268452123_1*
	 * 
	 * @param type
	 * @param uniqueId
	 * @return
	 * @throws Exception
	 * @param
	 * @exception Exception
	 */
	public void purgeCDNServer(String type, String uniqueId) throws Exception {

		try {
			boolean cdnPurgeYn = false;
			String cdnPurgeYnStr = StringUtils.defaultString(config.getString("cdn.purge.yn"), "n");
			if ("Y".equalsIgnoreCase(cdnPurgeYnStr)) cdnPurgeYn = true;
			if (cdnPurgeYn) {
				String cdnPurgeReqUrl = config.getString("cdn.purge.req.newUrl");
				String cdnUserId = config.getString("cdn.purge.user.id");
				String cdnApiKey = config.getString("cdn.purge.user.apiKey");

				// imageqc.save.root.dir = /nas_web/webhomes/lim_stg/static_root/images
				String imageqcRootPath = config.getString("imageqc.save.root.dir");
				imageqcRootPath = StringUtils.replace(imageqcRootPath, "/nas_web/webhomes", "");
				// logger.debug("imageqcRootPath^^^^^" + imageqcRootPath);
				
				String subFolerName = "";
				String imagePath = "";
				String imageDir = "";
				String imageScaleStr = "";
				if ("01".equals(type)) { // EDI 상품이미지(EPC)
					// edi.online.image.path=/dev_web/lim/static_root/images/edi/online
					subFolerName = uniqueId.substring(0, 4) + "/" + uniqueId.substring(4, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/" + subFolerName + "/" + uniqueId;
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.edi"), "");
				} else if ("02".equals(type)) { // BOS 상품이미지(BOS)
					// online.product.image.path=/dev_web/lim/static_root/images/prodimg
					subFolerName = uniqueId.substring(0, 5);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/" + subFolerName + "/" + uniqueId;
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.bos"), "");
				} else if ("03".equals(type)) { // 기획전 이미지 Scale(PC용)
					// exhibition.image.path=C:/Project-lottemart/workspace/lottemart-image/images/exhibition
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.exhibition"), "");
				} else if ("04".equals(type)) { // 기획전 이미지 Scale(모바일용)
					// exhibition.image.path=C:/Project-lottemart/workspace/lottemart-image/images/exhibition
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.exhibition") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.exhibition"), "");
				} else if ("05".equals(type)) { // 이벤트 이미지 Scale(PC용)
					// online.event.pc.image.path=C:/Project-lottemart/workspace/lottemart-image/images/front/event/bn/pc
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.event.pc") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.event.pc") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.event.pc"), "");
				} else if ("06".equals(type)) { // 이벤트 이미지 Scale(모바일용)
					// online.event.mobile.image.path=C:/Project-lottemart/workspace/lottemart-image/images/front/event/bn/mobile
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.event.mobile") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.event.mobile") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.event.mobile"), "");
				} else if ("07".equals(type)) { // 트렌드 이미지 Scale(PC용)
					// trend.image.bn.pc.path=C:/Project-lottemart/workspace/lottemart-image/images/front/trend/bn/pc
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.trend.pc") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.trend.pc") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.trend.pc"), "");
				} else if ("08".equals(type)) { // 트렌드 이미지 Scale(모바일용)
					// trend.image.bn.mobile.path=C:/Project-lottemart/workspace/lottemart-image/images/front/trend/bn/mobile
					subFolerName = uniqueId.substring(0, 4);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.trend.mobile") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.trend.mobile") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.trend.mobile"), "");
				} else if ("09".equals(type)) { // 요리왕장보고 이미지 Scale
					// recipe.image.path = C:/Project-lottemart/workspace2/lottemart-common/src/main/webroot/images/front/recipe
					subFolerName = uniqueId.substring(10, 12);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.recipe") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.recipe") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.recipe"), "");
				} else if ("10".equals(type)) { // 상품평
					// imageqc.sub.url.productreview = /files/boardfile/productreview
					subFolerName = uniqueId.substring(0, 4) + "/" + uniqueId.substring(4, 6) + "/" + uniqueId.substring(6, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.productreview") + "/" + subFolerName + "/" + uniqueId.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.productreview") + "/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.productreview"), "");  // mdSrcmkCd+"_00_720_405.jpg"+"?"+timeStamp);
				} else if ("11".equals(type)) { // 와이드 이미지
					 // online.product.wide.image.path=/nas_web02/lim/static_root/images/prodimg/wide
					subFolerName = uniqueId.substring(0, 5) + "/" + uniqueId.substring(5, 9);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/wide/" + subFolerName + "/" + uniqueId; //.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.product.bos") + "/wide/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.wide"), "");
				} else if ("12".equals(type)) { // EDI 와이드 이미지
					// EDI WIDE 상품이미지
					// edi.wide.image.path=/nas_web02/lim/static_root/images/edi/online/wide
					subFolerName = uniqueId.substring(0, 4) + "/" + uniqueId.substring(4, 8);
					imagePath = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/wide/" + subFolerName + "/" + uniqueId; //.substring(0, uniqueId.indexOf("_"));
					imageDir = imageqcRootPath + config.getString("imageqc.sub.url.product.edi") + "/wide/" + subFolerName + "/";
					imageScaleStr = StringUtils.defaultString(config.getString("imageqc.cache.purge.size.product.wide"), "");
				}

				List<String> purgeUrlList = new LinkedList<String>();
				List<String> purgeDirList = new LinkedList<String>();

				logger.debug("subFolerName : " + subFolerName);
				logger.debug("imagePath : " + imagePath);
				logger.debug("imageDir : " + imageDir);
				logger.debug("imageScaleStr : " + imageScaleStr);

				String[] imageScaleArr = imageScaleStr.split("[|]");

				String imgCallUrl = "";
				if (imageScaleArr != null && imageScaleArr.length > 0) {
					String imageDomain = config.getString("system.cdn.staticssl.path"); //  online.product.image.url
					int imgScleLen = imageScaleArr.length;
					for (int i = 0; i < imgScleLen; i++) {
						if ("01".equals(type) || "02".equals(type)) {
							logger.debug("imageScaleArr[" + i + "] : " + imageScaleArr[i]);
							String[] scaleStr = imageScaleArr[i].split("[x]");
							String tmpUrl = imagePath + "_" +scaleStr[0] + ".jpg";
							imgCallUrl = tmpUrl;
							logger.debug("tmpUrl[" + i + "] : " + tmpUrl);
							purgeUrlList.add(imageDomain + tmpUrl);
							purgeDirList.add(imageDomain + imageDir);
							imgCallUrl = imageDomain + tmpUrl;
						} else if ("11".equals(type) || "12".equals(type)) {
							String tmpUrl = imagePath + "_720_405" + ".jpg";
							purgeUrlList.add(imageDomain + tmpUrl);
							purgeDirList.add(imageDomain + imageDir);
						}
					}
				}

				if (purgeUrlList != null && purgeUrlList.size() > 0) {
					CdnOpenApiUtil cdnOpenApiUtil = new CdnOpenApiUtil();
					String resultMsg = cdnOpenApiUtil.cdnPurge(cdnPurgeReqUrl, cdnUserId, cdnApiKey, purgeUrlList, purgeDirList);

					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, Object> value = mapper.readValue(resultMsg, new TypeReference<HashMap<String, Object>>() { });

					if (value != null && value.containsKey("Code")) {
						if ("1".equals(String.valueOf(value.get("Code"))))
							if (!"".equals(imgCallUrl)) {
								requestImageServer(imgCallUrl);
							}
					}
				}

			}
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		}
	}

	/**
	 * Desc : ImageQS 서버 PROD 상세 SNAPSHOT 요청
	 * 사용하지 않음!!!
	 * 
	 * @Method Name : requestImageQSServer
	 * @param type
	 * @param uniqueId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@Override
	public boolean requestImageQSServer(String type, String uniqueId) {
		return true;
	}

    /**
     * Desc : ImageQC/QS 서버 이미지 작업 요청(HTTP 통신)(purge/slice)
     * 
     * @Method Name : requestImageServer
     * @param requestUrl
     * @return
     * @param
     * @return
     * @exception Exception
     */
	private boolean requestImageServer(String requestUrl) {
		logger.debug("requestImageServer(Request) : " + requestUrl);
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		try {
			URL url = new URL(requestUrl);

			// Send data
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setReadTimeout(3 * 1000);

			osw = new OutputStreamWriter(httpConn.getOutputStream());
			osw.flush();

			// 운영 소스 반영 - 2016.07.25 추가 - 김운주 AA
			br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			httpConn.disconnect();
			return true;
		} catch (Exception e) {
			log.error("Exception : " + requestUrl + " : " + e.getMessage());
			return false;
		} finally {
			try {
				if (br != null) { br.close(); }
			} catch (IOException e) {
				logger.error("error --> " + e.getMessage());
			}
			try {
				if (osw != null) { osw.close(); }
			} catch (IOException e) {
				logger.error("error --> " + e.getMessage());
			}
		}
	}

    /**
     * HTTPS Request
     * @param urlString
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
	public boolean requestCDNServer(String requestUrl) {
		logger.debug("(Request) : " + requestUrl);

		InputStream in = null;
		BufferedReader reader = null;
		try {
			logger.debug("requestCDNServer(Request) : " + requestUrl);

			// Get HTTPS URL connection
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			// Set Hostname verification
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					// Ignore host name verification. It always returns true.
					return true;
				}

			});

			// SSL setting
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, null, null); // No validation for now
			conn.setSSLSocketFactory(context.getSocketFactory());

			// Connect to host
			conn.connect();
			conn.setInstanceFollowRedirects(true);

			// Print response from host
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while (true) {//(line = reader.readLine()) != null
				line = reader.readLine();
				if(line != null) {
				logger.debug("requestCDNServer(Response) : " + line);
				}else {
					break;
				}
			}
			conn.disconnect();
			return true;
		} catch (Exception e) {
			logger.error("Exception : " + requestUrl + " : " + e.getMessage());
			return false;
		} finally {
			try {
				if (reader != null) { reader.close(); }
			} catch (IOException e) {
				logger.error("error --> " + e.getMessage());
			}
			try {
				if (in != null) { in.close(); }
			} catch (IOException e) {
				logger.error("error --> " + e.getMessage());
			}
		}
	}
	
    /**
     * Desc : 이미지 리사이즈 파일을 생성하기 위한 Method
     * 
     * @Method Name : resize
     * @param source
     * @param destination
     * @param width
     * @param height
     * @throws IOException
     * @param
     * @return
     * @throws InterruptedException 
     * @exception Exception
     */
    public void resizeImage(String source, String destination, int width, int height) throws InterruptedException, IOException {
        try {
	        File newFile = new File(destination);
	        RenderedOp renderedOp = JAI.create("fileload", source);
	        BufferedImage srcImg = renderedOp.getAsBufferedImage();

	        Image imgTarget = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);

	        int pixels[] = new int[width * height];
	        PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, width, height, pixels, 0, width);

	        pg.grabPixels();

	        BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        destImg.setRGB(0, 0, width, height, pixels, 0, width);

	        ImageIO.write(destImg, "jpg", newFile);
        } catch (InterruptedException e) {
			logger.error("error --> " + e.getMessage());
	        throw e;
        } catch (IOException e) {
			logger.error("error --> " + e.getMessage());
	        throw e;
        } 
    }
	
}
