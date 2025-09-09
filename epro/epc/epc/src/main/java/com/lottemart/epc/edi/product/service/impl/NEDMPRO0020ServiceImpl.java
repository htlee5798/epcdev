package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.DateUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
//import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
//import com.lottemart.epc.edi.delivery.dao.PEDMDLY0000Dao;
import com.lottemart.epc.edi.product.dao.CommonProductDao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0020Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0028Dao;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.EcProductCategory;
//import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
//import com.lottemart.epc.edi.product.model.NEDMPRO0026VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0020ServiceImpl
 * @Description : 신상품등록(온오프) 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 *
 */
@Service("nEDMPRO0020Service")
public class NEDMPRO0020ServiceImpl implements NEDMPRO0020Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020ServiceImpl.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "nEDMPRO0020Dao")
	private NEDMPRO0020Dao nEDMPRO0020Dao;
	
	@Resource(name = "nEDMPRO0028Dao")
	private NEDMPRO0028Dao nEDMPRO0028Dao;

	@Resource(name = "commonProductDao")
	private CommonProductDao commonProductDao;

	@Resource(name = "baseController")
	private BaseController baseController;

	@Resource(name = "imageFileMngService")
	private ImageFileMngService imageFileMngService;
	
	@Autowired
	private BosOpenApiService bosApiService;
	
	@Autowired
	private CommonProductService commonProductService;
	

	/**
	 * 신상품 등록 PGM_ID 구해오기
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String selectNewProductPgmId(String str) throws Exception {
//		return nEDMPRO0020Dao.selectNewProductPgmId(str);
		//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
		return bosApiService.selectTpcNewProdRegKey(); 
	}

	/**
	 * 신상품 등록
	 */
	public Map<String, Object> insertNewProdMst(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {

		if (nEDMPRO0020VO == null) {
			throw new TopLevelException("");
		}
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0020VO.setRegId(workId); //등록자
		nEDMPRO0020VO.setModId(workId);	//수정자
		
		
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		Map<String, Object> paramMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");
		
		//선택한 협력업체
		String selectedVendor = StringUtils.trimToEmpty(nEDMPRO0020VO.getEntpCd());
		
		//채널코드
		String chanCd = StringUtils.trimToEmpty(nEDMPRO0020VO.getChanCd());			//채널코드 (콤마로 구분된 string)
		List<String> chanList = Arrays.asList(chanCd.split(","));					//채널코드 list
		
		//채널 정보 없음
		if(chanList == null || chanList.isEmpty()) {
			resultMap.put("msg", "NO_CHAN_CD");
			return resultMap;
		}
		
		//선택한 업체의 선택가능 구매조직 체크 ----------
		CommonProductVO venChanChkVo = new CommonProductVO();
		venChanChkVo.setVenCd(selectedVendor);
		Map<String,Object> venChanMap = commonProductService.selectVendorZzorgInfo(venChanChkVo, null);
		
		//해당 업체코드로 선택가능한 구매조직(채널)
		List<String> venPurDept = (List<String>) MapUtils.getObject(venChanMap, "venPurDepts");
		
		//선택한 채널이 선택 가능한 구매조직(채널)에 모두 포함되는지 확인 
		if(!venPurDept.containsAll(chanList)) {
			//선택 불가능한 채널
			resultMap.put("msg", "CHAN_NOT_SELECTABLE");
			return resultMap;
		}

		//----- 협력업체 거래유형 조회
		paramMap.put("selectedVendor", selectedVendor);
		HashMap	vendorHsMap	=	commonProductDao.selectNVendorTradeType(paramMap);

		//----- 협력업체 거래유형이 없을경우
		if (vendorHsMap == null) {
			resultMap.put("msg", "NOT_PERMISSION_TRD_TYP_FG");
			return resultMap;
		}


		//-----직매입, 특약1, 특약2가 아니면 상품등록 불가
		if (!StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("1") && !StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("2") && !StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("4")) {
			resultMap.put("msg", "NOT_PERMISSION_TRD_TYP_FG");
			return resultMap;
		}

		//-----판매코드가 있을 경우 위해상품 판매코드 일 경우 상품등록 불가능[현재 사용안함 2016.04.08 일자로 변경됨]
		/*if (this.selectChkDangerProdCnt(StringUtils.trimToEmpty(nEDMPRO0020VO.getSellCd())) > 0) {
			resultMap.put("msg", "DANGER_PROD");
			return resultMap;
		}*/

		//-----판매코드가 있을 경우 위해상품 판매코드 일 경우 상품등록 불가능[현재 사용중 2016.04.08 추가 by song min kyo]
		if (StringUtils.trimToEmpty(this.selectChkDangerProdCnt_3(StringUtils.trimToEmpty(nEDMPRO0020VO.getSellCd()))).equals("2")) {
			resultMap.put("msg", "DANGER_PROD");
			return resultMap;
		}

		//----- 소분류에 매핑되어 있는 변형속성 카운트 조회
		paramMap.put("l3Cd", nEDMPRO0020VO.getL3Cd());
		int varAttCnt	=	commonProductDao.selectL3CdVarAttCnt(paramMap);

		String pgmId = "";

		if (nEDMPRO0020VO.getIuGbn().equals("I")) {
			//----- 신상품 등록 PGM_ID 구해오기
//			pgmId	=	nEDMPRO0020Dao.selectNewProductPgmId("");
			//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
			pgmId	= 	bosApiService.selectTpcNewProdRegKey();
		} else {
			pgmId 	= 	nEDMPRO0020VO.getPgmId();
		}
		
		//PGM 생성되지 않음
		if("".equals(pgmId)) {
			resultMap.put("msg", "NO_PGM_ID");
			return resultMap;
		}

		//----- POG 이미지 ID값 설정 [PGM_ID값 이용]
		String prodImgId	=	StringUtils.substring(pgmId, 2, 8) + StringUtils.right(pgmId,  5);

		//----- 신상품 PGM_ID, 이미지 아이디 설정
		nEDMPRO0020VO.setPgmId(pgmId);
		nEDMPRO0020VO.setProdImgId(prodImgId);

		//----- AS-IS 에 적용되어 있던 소스 사용하는 부분이 없어서 제외
		/*if(!"0".equals(tmpProduct.getPromotionAmountFlag())) {
			newProduct.setPromotionAmountFlag("1");
		} else {
			newProduct.setPromotionAmountFlag(tmpProduct.getPromotionAmountFlag());
		}

		//상품 유형구분이 PB(2)인 경우
		if("2".equals(newProduct.getProductTypeDivnCode())) {
			newProduct.setPromotionAmountFlag("0");
		}*/
		
		//	logger.debug("leedbvendorHsMap----->" + vendorHsMap);
		//----- 해당 협력 업체 코드로 조회한 거래 유형값 설정
		if (vendorHsMap != null) {
			String vendorTrdTypFg	=	(String)vendorHsMap.get("TRD_TYP_FG");
			nEDMPRO0020VO.setTradeType(vendorTrdTypFg);

			/*if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM01")) {
				nEDMPRO0020VO.setTradeType("1");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM02")) {
				nEDMPRO0020VO.setTradeType("2");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM07")) {
				nEDMPRO0020VO.setTradeType("3");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM03")) {
				nEDMPRO0020VO.setTradeType("4");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM06")) {
				nEDMPRO0020VO.setTradeType("5");

			//----- KM04, KM05, KM08, KM09는 tradeTypeCd (업체들은 현재 온오프 상품 등록 할 수 없음)를 어떻게 바꿔야 되는지 확인 필요함 현재는 잉시로 그냥 끝자리 지정해놓음.
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM04")) {
				nEDMPRO0020VO.setTradeType("9");
				resultMap.put("MSG", "NOT_PERMISSION_TRD_TYP_FG");
				return resultMap;

			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM05")) {
				nEDMPRO0020VO.setTradeType("9");

				resultMap.put("MSG", "NOT_PERMISSION_TRD_TYP_FG");
				return resultMap;
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM08")) {
				nEDMPRO0020VO.setTradeType("9");

				resultMap.put("MSG", "NOT_PERMISSION_TRD_TYP_FG");
				return resultMap;
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM09")) {
				nEDMPRO0020VO.setTradeType("9");

				resultMap.put("MSG", "NOT_PERMISSION_TRD_TYP_FG");
				return resultMap;
			} */

//			logger.debug("leedbvendorTrdTypFg----->" + vendorTrdTypFg);
//			logger.debug("leedbvarAttCnt----->" + varAttCnt);

			//-----거래형태가 직매입이면
			if (vendorTrdTypFg.equals("1")) {
				
				//----- 채널별 면/과세 구분에 따른 이익률 계산 start----------------- 
				String commPrftRate = "";	//이익률 환산결과
				for(String selChanCd : chanList) {
					switch(selChanCd) {
						case "KR02":	//마트
							commPrftRate = nEDMPRO0020Dao.selectnewProdPrftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setPrftRate(commPrftRate);	//마트_이익률 셋팅
							break;
						case "KR03":	//MAXX
							commPrftRate = nEDMPRO0020Dao.selectnewProdWprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setWprftRate(commPrftRate);	//MAXX_이익률 셋팅
							break;
						case "KR04":	//슈퍼
							commPrftRate = nEDMPRO0020Dao.selectnewProdSprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setSprftRate(commPrftRate);	//슈퍼_이익률 셋팅
							break;
						case "KR09":	//오카도
							commPrftRate = nEDMPRO0020Dao.selectnewProdOprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setOprftRate(commPrftRate);	//오카도_이익률 셋팅
							break;
						default:
							break;
						
					}
				}
				//----- 채널별 면/과세 구분에 따른 이익률 계산 end ----------------- 

//				//------ [직매입시 면세/과세 별 이익율 계산 ]
//				String prftRate		=	nEDMPRO0020Dao.selectnewProdPrftRate(nEDMPRO0020VO);
//				nEDMPRO0020VO.setPrftRate(prftRate);
//
//				String norProdCurr = nEDMPRO0020VO.getNorProdCurr();
//				String norProdSaleCurr = nEDMPRO0020VO.getNorProdSaleCurr();
//				String wnorProdCurr = nEDMPRO0020VO.getWnorProdCurr();
//				String wnorProdSaleCurr = nEDMPRO0020VO.getWnorProdSaleCurr();
//
//				logger.debug("leedbnorProdCurr:"+norProdCurr);
//				logger.debug("leedbnorProdSaleCurr:"+norProdSaleCurr);
//
//			//	if(norProdCurr != norProdSaleCurr ) {
//				if(!norProdCurr.equals(norProdSaleCurr)  ) {
//					nEDMPRO0020VO.setPrftRate("0");
//					logger.debug("leedb000000000000");
//				}
//
//
//				//------ [VIC 직매입시 면세/과세 별 이익율 계산  ]
//				String wPrftRate	=	nEDMPRO0020Dao.selectnewProdWprftRate(nEDMPRO0020VO);
//				nEDMPRO0020VO.setWprftRate(wPrftRate);
//				logger.debug("leedbwnorProdCurr:"+wnorProdCurr);
//				logger.debug("leedbwnorProdSaleCurr:"+wnorProdSaleCurr);
//
//				if(!wnorProdCurr.equals(wnorProdSaleCurr)  ) {
//					nEDMPRO0020VO.setWprftRate("0");
//					logger.debug("leedb111111111111");
//				}
//				
//				
//				
				//----- 변형 속성이 존재하면
				if (varAttCnt > 0) {
					if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
						nEDMPRO0020VO.setNoprodFg("ZAW1");
					} else {	// 묶음상품일 경우
						nEDMPRO0020VO.setNoprodFg("ZAW2");
					}
				} else {
					nEDMPRO0020VO.setNoprodFg("ZAW1");
				}

				//-----무발주매입구분은 미적용으로 해준다
				nEDMPRO0020VO.setNpurBuyPsbtDivnCd("");

			//-----거래형태가 직매입 경우 제외한 나머지
			} else {
//				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getPrftRate()).equals("")) {
//					nEDMPRO0020VO.setPrftRate("0");
//				}
//
//				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getWprftRate()).equals("")) {
//					nEDMPRO0020VO.setPrftRate("0");
//				}
				
				//----- 채널별 면/과세 구분에 따른 원가 계산 start----------------- 
				String commNorProdPcost = "";	//원가 환산결과
				for(String selChanCd : chanList) {
					switch(selChanCd) {
						case "KR02":	//마트
							commNorProdPcost = nEDMPRO0020Dao.selectNorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setNorProdPcost(commNorProdPcost);	//마트_원가 셋팅
							break;
						case "KR03":	//MAXX
							commNorProdPcost = nEDMPRO0020Dao.selectWnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setWnorProdPcost(commNorProdPcost);	//MAXX_원가 셋팅
							break;
						case "KR04":	//슈퍼
							commNorProdPcost = nEDMPRO0020Dao.selectSnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setSnorProdPcost(commNorProdPcost);	//슈퍼_원가 셋팅
							break;
						case "KR09":	//오카도
							commNorProdPcost = nEDMPRO0020Dao.selectOnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setOnorProdPcost(commNorProdPcost);	//오카도_원가 셋팅
							break;
						default:
							break;
						
					}
				}
				//----- 채널별 면/과세 구분에 따른 원가 계산 end-----------------

//				//-------[과세 및 면세,영세 일경우 원가계산 ]
//				String taxFreeProductCosts = nEDMPRO0020Dao.selectNorProdPcost(nEDMPRO0020VO);
//				nEDMPRO0020VO.setNorProdPcost(taxFreeProductCosts);
//
//				//--[VIC 과세 및 면세,영세  일경우 원가계산 ]-----------------------------------
//				String victaxFreeProductCosts = nEDMPRO0020Dao.selectWnorProdPcost(nEDMPRO0020VO);
//				nEDMPRO0020VO.setWnorProdPcost(victaxFreeProductCosts);

				//----- 특약1[KM02]
				if (vendorTrdTypFg.equals("2")) {

					//----- 변형 속성이 존재하면
					if (varAttCnt > 0) {
						if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
							nEDMPRO0020VO.setNoprodFg("ZAW3");
						} else {
							nEDMPRO0020VO.setNoprodFg("ZAW4");
						}
					} else {
						nEDMPRO0020VO.setNoprodFg("ZAW3");
					}

				//----- 특약2[KM03]
				} else if (vendorTrdTypFg.equals("4")) {

					//----- 변형 속성이 존재하면
					if (varAttCnt > 0) {
						if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
							nEDMPRO0020VO.setNoprodFg("ZAW5");
						} else {
							nEDMPRO0020VO.setNoprodFg("ZAW6");
						}
					} else {
						nEDMPRO0020VO.setNoprodFg("ZAW5");
					}

					//-----무발주매입구분 미적용으로 해준다.
					nEDMPRO0020VO.setNpurBuyPsbtDivnCd("");
				}
			}
		}

//		if(StringUtils.isEmpty(nEDMPRO0020VO.getPrftRate())) {
//			nEDMPRO0020VO.setPrftRate("0");
//		}

		//200306 EC전용 카테고르 추가 START

		nEDMPRO0020Dao.deleteEcProductCategory(pgmId);

		logger.debug("----->" + nEDMPRO0020VO.getDispCatCd() + "/" + nEDMPRO0020VO.getStdCatCd()  + " / " + pgmId + " / " + nEDMPRO0020VO.getEntpCd() );
		if(!StringUtils.isEmpty(nEDMPRO0020VO.getDispCatCd())) {
			EcProductCategory ecProductCategory = new EcProductCategory();
			ecProductCategory.setProdCd(pgmId);
			ecProductCategory.setPgmId(pgmId);
//			ecProductCategory.setRegId(nEDMPRO0020VO.getEntpCd());
			//작업자정보 setting (로그인세션에서 추출)
			ecProductCategory.setRegId(workId);
			ecProductCategory.setStdCatCd(nEDMPRO0020VO.getStdCatCd());
			String[] dispCatArr = nEDMPRO0020VO.getDispCatCd().split(",");
			for (int i = 0; i < dispCatArr.length; i++) {
				ecProductCategory.setDispCatCd(dispCatArr[i]);

				nEDMPRO0020Dao.insertEcProductCategory(ecProductCategory);
			}
		}
		//200306 EC전용 카테고리 추가 END

		//24NBM_OSP_CAT S
		saveOspDispCd(nEDMPRO0020VO);
		//24NBM_OSP_CAT E

		// [EC조립12]S

		// EC 묶음상품 내에 옵션 값 (가격 동일 유무) 설정 값으로 값 받도록 설정_piy
		/*
		if(nEDMPRO0020VO.getProdPrcMgrYn()!=null && "".equals(nEDMPRO0020VO.getProdPrcMgrYn())) {
			nEDMPRO0020Dao.updateProdMgrYn(nEDMPRO0020VO);
		}
		*/

		//EC분석속성 등록되어 있으면서, EC카테고리 변경 시,
		if( "false".equals( nEDMPRO0020VO.getEcCategoryKeepYn() ) && "Y".equals(nEDMPRO0020VO.getEcAttrRegYn()) ) {
			//기등록된 EC분석속성 삭제
			nEDMPRO0020Dao.deleteEcProductAttribute(pgmId);
		}

		// [EC조립12]E
		//200326 EC전용 속성 추가 END


		//----- 1.전상법 저장
		nEDMPRO0020Dao.deleteNewProdTprProdAddInfo(nEDMPRO0020VO);		//저장전 삭제
		String[] arrAddInfoCd	=	nEDMPRO0020VO.getArrAddInfoCd();
		String[] arrAddInfoVal	=	nEDMPRO0020VO.getArrAddInfoVal();
		for (int i = 0; i < arrAddInfoCd.length; i++) {
			nEDMPRO0020VO.setProdAddCd(arrAddInfoCd[i]);
			nEDMPRO0020VO.setProdAddVal(arrAddInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdAddInfo(nEDMPRO0020VO);				//저장
		}


		//----- 2.KC인증 저장
		nEDMPRO0020Dao.deleteNewProdTprProdCertInfo(nEDMPRO0020VO);		//저장전 삭제
		String[] arrCertInfoCd	=	nEDMPRO0020VO.getArrCertInfoCd();
		String[] arrCertInfoVal	=	nEDMPRO0020VO.getArrCertInfoVal();
		for (int i = 0; i < arrCertInfoCd.length; i++) {
			nEDMPRO0020VO.setProdCertCd(arrCertInfoCd[i]);
			nEDMPRO0020VO.setProdCertVal(arrCertInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdCertInfo(nEDMPRO0020VO);
		}


		// ----- 2018-08-17 전산정보팀 이상구 추가
		// 상품 키워드 입력
		//20180821 - 상품키워드 입력 기능 추가
		//상품키워드
		nEDMPRO0020VO.makeKeywordObject();
		//상품키워드

		if(nEDMPRO0020VO.getKeywordList().size() > 0) {
			nEDMPRO0020Dao.mergeTpcPrdKeyword(nEDMPRO0020VO);
		}
		else {
			nEDMPRO0020Dao.deleteAllTpcPrdKeyword(pgmId);
		}
		//20180821 - 상품키워드 입력 기능 추가
		/* ****************************** */

		//----- 3.상품마스터 정보 저장, 상품상세설명 저장
		if (nEDMPRO0020VO.getIuGbn().equals("I")) {
			// 상품 Mst Insert
			nEDMPRO0020Dao.insertNewProdMst(nEDMPRO0020VO);

			// 상품 상세설명 Insert
			nEDMPRO0020Dao.insertNewProdDescrInfo(nEDMPRO0020VO);

			//상품 수입정보 Insert
			if(nEDMPRO0020VO.getHscd().isEmpty() && nEDMPRO0020VO.getDecno().isEmpty() && nEDMPRO0020VO.getTarrate().isEmpty()){
				logger.debug("");
			}
			else {
				nEDMPRO0020Dao.insertNewImportInfo(nEDMPRO0020VO);
			}

		} else {
			// 상품 Mst Update
			nEDMPRO0020Dao.updateNewProductMST(nEDMPRO0020VO);

			// 상품 상세설명 Update
			nEDMPRO0020Dao.updateNewProductDesc(nEDMPRO0020VO);

			//상품 수입정보 Insert
			nEDMPRO0020Dao.deleteNewImportInfo(nEDMPRO0020VO);
			if(nEDMPRO0020VO.getHscd().isEmpty() && nEDMPRO0020VO.getDecno().isEmpty() && nEDMPRO0020VO.getTarrate().isEmpty()){
				logger.debug("");
			}
			else {
				nEDMPRO0020Dao.insertNewImportInfo(nEDMPRO0020VO);
			}

			List<HashMap> nutAttInfoList = nEDMPRO0020Dao.selectNutInfoByPgmId(pgmId);
			if (nutAttInfoList.size() !=0 && nEDMPRO0020VO.getDelNutAmtYn().equals("Y")) {
				nEDMPRO0020Dao.deleteNutAttWithPgmId(pgmId);
			}


			// 속성정보/이미지 삭제[상품수정시 묶음에서 단일로 변경하거나 묶음상품이면서 소분류를 변경하게 되면]
			if (StringUtils.trimToEmpty(nEDMPRO0020VO.getDelGbn()).equals("Y")) {

				//변형속성 삭제
				nEDMPRO0020Dao.deleteVarAttInfo(pgmId);

				//분석속성 삭제
				nEDMPRO0020Dao.deleteGrpAttInfo(pgmId);
				
				/*
				 * 250421 차세대 추가
				 * - 단일 > 묶음 변경 시, EC 분석속성 삭제되지 않아 EC분석속성 영역 create 시 계속 단일로 표시되는 오류 존재 
				 *   --> 등록된 EC 분석속성이 있을경우, EC 분석속성을 삭제함
				 */
				if("Y".equals(nEDMPRO0020VO.getEcAttrRegYn())) {
					nEDMPRO0020Dao.deleteEcProductAttribute(pgmId);
				}
				

				//오프라인 이미지 삭제 하기전 업로드 디렉토리에서 해당 이미지 삭제 (물리적인경로에서 실제로 이미지 파일이 삭제됨 .. 현재로는 이미지 삭제 안하는걸로 주석처리함.>>>> 이미지 삭제 해달라고 하면 주석 해제 하면 됨)
				/*String onOffImgFolder			=	baseController.makeSubFolderForOffline(pgmId);		//오프라인 이미지 업로드 폴더
				List<NEDMPRO0025VO> rsOffImgVO	=	nEDMPRO0020Dao.selectNewProdOffImgInfo(pgmId);		//해당 상품의 오프라인 이미지 정보

				for (int i = 0; i < rsOffImgVO.size(); i++) {
					String filePath	=	onOffImgFolder + "/" + rsOffImgVO.get(i).getImgNm() + "." + (i+1) + ".jpg";	// (i+1) 이유는 오프라인 이미지가 파일이름뒤에 .1(정면), .2(측면), .3(후면) 으로 등록되기 때문이다.
					File delFile	=	new File(filePath);

					delFile.delete();	//오프라인 이미지 실제 파일 삭제
				}*/

				//오프라인 이미지정보 삭제
				nEDMPRO0020Dao.deleteOffImgInfo(pgmId);
			}

			//협력업체 코드가 변경이 되면 단일상품일 경우는 변형속성정보가 없기 때문에 이미지 정보 테이블의 협력업체 코드만 업데이트 , 묶음상품일 경우에는 변형속성이 있기 때문에 변형속성 테이블의 협력업체 코드도 업데이트해준다.
			if (StringUtils.trimToEmpty(nEDMPRO0020VO.getUpEntpGbn()).equals("Y")) {

				//단일 상품일 경우
				if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {
					nEDMPRO0020Dao.updateEntpCdOfSaleImg(nEDMPRO0020VO);
				//묶음 상품일경우
				} else {
					nEDMPRO0020Dao.updateEntpCdOfSaleImg(nEDMPRO0020VO);
					nEDMPRO0020Dao.updateEntpCdOfVarAtt(nEDMPRO0020VO);
				}
			}

		}

		resultMap.put("pgmId", pgmId); //등록된 신상품 PGM_ID
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	/**
	 * 신상품 마스터 테이블 온라인 이미지 개수 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCnt(Map<String, Object> paramMap) throws Exception {
		nEDMPRO0020Dao.updateNewProdImgCnt(paramMap);
	}

	/**
	 * 신상품 마스터 동영상URL 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdMovieUrl(Map<String, Object> paramMap) throws Exception {
		nEDMPRO0020Dao.updateNewProdMovieUrl(paramMap);
	}

	/**
	 * 신상품 마스터 동영상URL 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public String selectNewProdMovieUrl(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0020Dao.selectNewProdMovieUrl(paramMap);
	}

	/**
	 * 신상품 마스터 테이블 온라인 이미지 삭제 후 이미지 카운트 -1
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCntMinus(Map<String, Object> paramMap) throws Exception {
		nEDMPRO0020Dao.updateNewProdImgCntMinus(paramMap);
	}

	/**
	 * 상품의 변형속성 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdVarAttCnt(String pgmId, String cfmFg) throws Exception {
		if (StringUtils.trimToEmpty(cfmFg).equals("3")) {
			return nEDMPRO0020Dao.selectNewProdVarAttCntFix(pgmId);
		} else {
			return nEDMPRO0020Dao.selectNewProdVarAttCnt(pgmId);
		}

	}

	/**
	 * 상품정보 가져오기
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectProdInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return nEDMPRO0020Dao.selectProdInfo(nEDMPRO0020VO);
	}

	/**
	 * 소분류별 변형속성 그룹(2015.12.30 이전 버전)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrp(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectAttGrp(nEDMPRO0024VO);
	}*/

	/**
	 * 소분류별 변형속성 그룹 옵션(2015.12.30 이전 버전)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrpOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectAttGrpOpt(nEDMPRO0024VO);
	}*/

	/**(2015.12.30 이전 버전)
	 * 변형속성 그룹별 상세 속성
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrpOptVar(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectAttGrpOptVar(nEDMPRO0024VO);
	}*/

	/**
	 * 소분류별 PROFILE 가져오기(Class)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectClass(nEDMPRO0024VO);
	}

	/**
	 * PROFILE별 변형속성 가져오기
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectClassVarAtt(nEDMPRO0024VO);
	}

	/**
	 * 변형속성 Option 가져오기
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectClassVarAttOpt(nEDMPRO0024VO);
	}

	/**
	 * 88코드 중복 체크
	 * @param sellCd
	 * @return
	 * @throws Exception
	 */
	public int selectSellCdCount(String sellCd) throws Exception {
		return nEDMPRO0020Dao.selectSellCdCount(sellCd);
	}

	/**
	 * 판매코드로 위해상품으로 등록된 판매코드인지 조회 [현재 사용안함 2016.04.08 쿼리로 변경됨]
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public int selectChkDangerProdCnt(String sellCode) throws Exception {
		return nEDMPRO0020Dao.selectChkDangerProdCnt(sellCode);
	}

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 추가 [IF_DT 가 최근일자 기준으로 CFM_FG 컬럼값이  '2' 일 경우 위해상품의 판매코드로 결정] 2016.04.08 추가  by song min kyo
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public String selectChkDangerProdCnt_3(String sellCode) throws Exception {
		return nEDMPRO0020Dao.selectChkDangerProdCnt_3(sellCode);
	}

	/**
	 * 변형속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveVarAtt(NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		
		//EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		// 기존 변형속성 삭제
		//nEDMPRO0020Dao.deleteVarAtt(nEDMPRO0024VO.getPgmId());
		ArrayList attInfoAl = nEDMPRO0024VO.getAttInfoAl();
		String[] arrSrcmkCd	=	nEDMPRO0024VO.getArrSrcmkCd();

		// 상품정보 가져오기
		Map<String, Object>	paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", nEDMPRO0024VO.getPgmId());

		//-----입력된 판매코드로 위해상품의 판매코드인지 체크
		if (nEDMPRO0024VO.getArrSrcmkCd().length > 0) {		// 판매코드가 있을때만

			//int dangerProdCnt = nEDMPRO0020Dao.selectChkDangerProdCnt_2(nEDMPRO0024VO); 2016.04.06 쿼리는 현재 사용안함
			/*int dangerProdCnt = nEDMPRO0020Dao.selectChkDangerProdCnt_4(nEDMPRO0024VO); //2016.04.08 추가 [현재 사용]
			if (dangerProdCnt > 0) {
				return "DANGER";
			}*/
			for (int i = 0; i < arrSrcmkCd.length; i++) {
				if (StringUtils.trimToEmpty(this.selectChkDangerProdCnt_3(StringUtils.trimToEmpty(arrSrcmkCd[i]))).equals("2")) {
					return "DANGER";
				}
			}
		}

		NEDMPRO0020VO newProdDetailInfo = this.selectNewTmpProductDetailInfo(paramMap);

		String uploadDir = baseController.makeSubFolderForOffline(nEDMPRO0024VO.getPgmId());

		NEDMPRO0024VO paramVO = new NEDMPRO0024VO();

		NewProduct newProduct = new NewProduct();

		// 속성 Insert
		if (attInfoAl != null && attInfoAl.size() > 0) {
			HashMap hm = new HashMap();

			for (int i = 0; i < attInfoAl.size(); i++) {
				hm = (HashMap) attInfoAl.get(i);

				String rowDel 		= (String) hm.get("rowDel");
				String orgVariant 	= (String) hm.get("orgVariant");

				paramVO.setPgmId(nEDMPRO0024VO.getPgmId());
				paramVO.setClassCd((String) hm.get("classCd"));
				paramVO.setAttGrpCd((String) hm.get("attGrpCd"));
				paramVO.setAttDetailCd((String) hm.get("attDetailCd"));
				paramVO.setAttGrpCd((String) hm.get("attGrpCd"));
				paramVO.setAttDetailCd((String) hm.get("attDetailCd"));
				paramVO.setEntpCd(nEDMPRO0024VO.getEntpCd());
				paramVO.setSellCd((String) hm.get("sellCd"));
				paramVO.setL3Cd(nEDMPRO0024VO.getL3Cd());
//				paramVO.setRegId(nEDMPRO0024VO.getEntpCd());
				//작업자정보 setting (로그인세션에서 추출)
				paramVO.setRegId(workId);
				

				String variant = StringUtils.leftPad((String) hm.get("variant"), 3, "0");

				if (rowDel.equals("1") && orgVariant != null && !orgVariant.equals("")) {	// Delete
					paramVO.setVariant(orgVariant);

					// 변형속성 삭제
					nEDMPRO0020Dao.deleteVarAtt(paramVO);

					// 이미지정보 삭제
					nEDMPRO0020Dao.deleteSaleImg(paramVO);

					//logger.debug("----->" + uploadDir + "/" + newProdDetailInfo.getProdImgId() + "" + variant + ".1.jpg");
					FileUtils.deleteQuietly(new File(uploadDir + "/" + newProdDetailInfo.getProdImgId() + "" + variant + ".1.jpg"));
					FileUtils.deleteQuietly(new File(uploadDir + "/" + newProdDetailInfo.getProdImgId() + "" + variant + ".2.jpg"));
					FileUtils.deleteQuietly(new File(uploadDir + "/" + newProdDetailInfo.getProdImgId() + "" + variant + ".3.jpg"));

				} else {
					if (!rowDel.equals("1")) {	// 신규이면서 삭제가 아닌 대상만 저장
						if (orgVariant != null && !orgVariant.equals("")) {		// Update
							paramVO.setVariant(orgVariant);

							nEDMPRO0020Dao.updateVarAtt(paramVO);
						} else {	// Insert
							paramVO.setVariant(variant);
							paramVO.setProdImgId(newProdDetailInfo.getProdImgId() + "" + variant);

							nEDMPRO0020Dao.insertVarAtt(paramVO);
						}
					}
				}


				//paramVO.setPgmId(nEDMPRO0024VO.getPgmId());
				//paramVO.setClassCd((String) hm.get("classCd"));


				// prodImgId(pog이미지 id값 설정, pgmId 값 이용)
				//String prodImgId = StringUtils.substring(nEDMPRO0024VO.getPgmId(), 2, 8) + StringUtils.right(nEDMPRO0024VO.getPgmId(), 5);
				//prodImgId = prodImgId + "" + variant;

				//paramVO.setProdImgId(prodImgId);


				//paramVO.setItemCd(variant);
			}
		}

		return "S";
	}


	/**
	 * 등록된 변형속성의 CLASS 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public String selectProdVarAttClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {

		if (StringUtils.trimToEmpty(nEDMPRO0024VO.getCfmFg()).equals("3")) {
			return nEDMPRO0020Dao.selectProdVarAttClassFix(nEDMPRO0024VO);	//확정된 상품이면 VAR_ATT_SAP 테이블 에서 데이터 추출하기 위해 추가 2016.03.17 by song min kyo
		} else {
			return nEDMPRO0020Dao.selectProdVarAttClass(nEDMPRO0024VO);
		}


	}

	/**
	 * 등록된 변형속성의 전체 속성정보 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {

		if (StringUtils.trimToEmpty(nEDMPRO0024VO.getCfmFg()).equals("3")) {
			return nEDMPRO0020Dao.selectProdVarAttOptFix(nEDMPRO0024VO);	//확정된 상품이면 VAR_ATT_SAP 테이블 에서 데이터 추출하기 위해 추가 2016.03.17 by song min kyo
		} else {
			return nEDMPRO0020Dao.selectProdVarAttOpt(nEDMPRO0024VO);
		}

	}

	/**
	 * 등록된 상품변형속성 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAtt(String pgmId, String cfmFg) throws Exception {
		if (StringUtils.trimToEmpty(cfmFg).equals("3")) {
			return nEDMPRO0020Dao.selectProdVarAttFix(pgmId);
		} else {
			return nEDMPRO0020Dao.selectProdVarAtt(pgmId);
		}

	}

	/**
	 * 소분류별 그룹분석속성 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAtt(String prodRange) throws Exception {
		return nEDMPRO0020Dao.selectGrpAtt(prodRange);
	}

	/**
	 * 소분류별 그룹분석속성 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectGrpAttOne(nEDMPRO0024VO);
	}

	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectGrpAttOpt(nEDMPRO0024VO);
	}
	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOptOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return nEDMPRO0020Dao.selectGrpAttOptOne(nEDMPRO0024VO);
	}

	/**
	 * 그룹분석속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveGrpAtt(NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0024VO.setRegId(workId); //등록자
		nEDMPRO0024VO.setModId(workId);	//수정자
				
		

		NEDMPRO0024VO paramVO = new NEDMPRO0024VO();

		paramVO.setPgmId(nEDMPRO0024VO.getPgmId());
		nEDMPRO0020Dao.deleteGrpAtt(paramVO);
	//	logger.debug("leedbgetgetPgmId:"+paramVO.getPgmId());

		// 그룹분석속성 Insert/Update
		ArrayList attInfoAl = nEDMPRO0024VO.getAttInfoAl();
		if (attInfoAl != null && attInfoAl.size() > 0) {
			HashMap hm = new HashMap();

			for (int i = 0; i < attInfoAl.size(); i++) {
				hm = (HashMap) attInfoAl.get(i);

				String attId 		= (String) hm.get("attId");
				String attValId 	= (String) hm.get("attValId");

				paramVO.setPgmId(nEDMPRO0024VO.getPgmId());
				paramVO.setAttId(attId);
				paramVO.setAttValId(attValId);
//				paramVO.setRegId(nEDMPRO0024VO.getEntpCd());
				//작업자정보 setting (로그인세션에서 추출)
				paramVO.setRegId(workId);

				nEDMPRO0020Dao.saveGrpAtt(paramVO);
			}
		}

		return "S";
	}
	// ---[210125 EC조립6]S
	/**
	 * EC속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */

	  public String saveEcAtt(NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception {
		  
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0020VO.setRegId(workId); //등록자
		nEDMPRO0020VO.setModId(workId);	//수정자
			

	  //[200417 상품속성추가] START

		  nEDMPRO0020Dao.deleteEcProductAttribute(nEDMPRO0020VO.getPgmId());

		  NewProduct newProduct = new NewProduct();
		  newProduct.setEntpCode(nEDMPRO0020VO.getEntpCd());
		  newProduct.setNewProductCode(nEDMPRO0020VO.getPgmId());
		  newProduct.setStdCatCd(nEDMPRO0020VO.getStdCatCd());
		  newProduct.setEcProductAttr(nEDMPRO0020VO.getEcProductAttr());
		  //작업자정보 setting (로그인세션에서 추출)
		  newProduct.setRegId(workId);

		  if(nEDMPRO0020VO.getAttrPiType().equals("P") && !"".equals(nEDMPRO0020VO.getProdDirectYn())&& nEDMPRO0020VO.getProdDirectYn().equals("Y"))
		  {
			  newProduct.setProdDirectYn(nEDMPRO0020VO.getProdDirectYn());
			  newProduct.setEcProductAttrId(nEDMPRO0020VO.getEcProductAttrId());
		  }
		  if(nEDMPRO0020VO.getAttrPiType().equals("I") && !"".equals(nEDMPRO0020VO.getOptnDirectYn()) && nEDMPRO0020VO.getOptnDirectYn().equals("Y"))
		  {
			  newProduct.setOptnDirectYn(nEDMPRO0020VO.getOptnDirectYn());
			  newProduct.setEcProductAttrId(nEDMPRO0020VO.getEcProductAttrId());
		  }

		  newProduct.setAttrPiType(nEDMPRO0020VO.getAttrPiType());
		  newProduct.setItemCd(nEDMPRO0020VO.getItemCd());
		  newProduct.setStkMgrYn(nEDMPRO0020VO.getStkMgrYn());
		  newProduct.setRservStkQty(nEDMPRO0020VO.getRservStkQty());
		  newProduct.setOptnAmt(nEDMPRO0020VO.getOptnAmt());
		  newProduct.setProductName(nEDMPRO0020VO.getProdNm());
		  newProduct.makeItemObject();
		  
		  /*
		  if(newProduct.getAttrPiType().equals("I") && newProduct.getItemList().size() > 0){
			  nEDMPRO0020Dao.insertProductItemInfo(newProduct); // I유형일 경우 옵션 값들 추가.
		  }
		  */
		  newProduct.makeEcAttrbuteObject();
		  nEDMPRO0020Dao.insertEcProductAttribute(newProduct);
		 // nEDMPRO0020Dao.updateProdMgrYn(nEDMPRO0020VO); // 상품 옵션별 가격차이유무 정보 //[200417상품속성추가] END

		  return "S";
	  }

	// ---[210125 EC조립6]E


	/**
	 * 상품마스터 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpProductDetailInfo(Map<String, Object> paramMap) throws Exception {

		//----- 신상품 등록현황 조회에서 확정된 상품은 REG_SAP테이블의 정보를 보여주기 위해 분기 2016.03.07 by song min kyo
		if (StringUtils.trimToEmpty((String)paramMap.get("cfmFg")).equals("3")) {
			return nEDMPRO0020Dao.selectNewTmpProductDetailInfoFix(paramMap);

		} else {
			return nEDMPRO0020Dao.selectNewTmpProductDetailInfo(paramMap);

		}
	}

	/**
	 * 온라인전용 상품마스터 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpOnlineProductDetailInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0020Dao.selectNewTmpOnlineProductDetailInfo(paramMap);
	}

	/**
	 * 상품수정 (250416 미사용하는 것으로 확인 / this.insertNewProdMst와 동일한 로직사용)
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */

	public Map<String, Object> updateNewProductMST(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {

		if (nEDMPRO0020VO == null) {
			throw new TopLevelException("");
		}

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		Map<String, Object> paramMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//----- 협력업체 거래유형 조회
		paramMap.put("selectedVendor", StringUtils.trimToEmpty(nEDMPRO0020VO.getEntpCd()));
		HashMap	vendorHsMap	=	commonProductDao.selectNVendorTradeType(paramMap);

		//----- 해당 협력 업체 코드로 조회한 거래 유형값 설정
		if (vendorHsMap != null) {
			String vendorTrdTypFg	=	(String)vendorHsMap.get("TRD_TYP_FG");

			nEDMPRO0020VO.setTradeType(vendorTrdTypFg);

			/*if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM01")) {
				nEDMPRO0020VO.setTradeType("1");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM02")) {
				nEDMPRO0020VO.setTradeType("2");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM07")) {
				nEDMPRO0020VO.setTradeType("3");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM03")) {
				nEDMPRO0020VO.setTradeType("4");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM06")) {
				nEDMPRO0020VO.setTradeType("5");

			//----- KM04, KM05, KM08, KM09는 tradeTypeCd를 어떻게 바꿔야 되는지 확인 필요함 현재는 잉시로 그냥 끝자리 지정해놓음.
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM04")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM05")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM08")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM09")) {
				nEDMPRO0020VO.setTradeType("9");
			} */

			//-----거래형태가 직매입이면
			if (vendorTrdTypFg.equals("1")) {

				//------ [직매입시 면세/과세 별 이익율 계산 ]
				String prftRate		=	nEDMPRO0020Dao.selectnewProdPrftRate(nEDMPRO0020VO);
				nEDMPRO0020VO.setPrftRate(prftRate);

				//------ [VIC 직매입시 면세/과세 별 이익율 계산  ]
				String wPrftRate	=	nEDMPRO0020Dao.selectnewProdWprftRate(nEDMPRO0020VO);
				nEDMPRO0020VO.setWprftRate(wPrftRate);

			//-----거래형태가 직매입 경우 제외한 나머지
			} else {
				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getPrftRate()).equals("")) {
					nEDMPRO0020VO.setPrftRate("0");
				}

				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getWprftRate()).equals("")) {
					nEDMPRO0020VO.setPrftRate("0");
				}

				//-------[과세 및 면세,영세 일경우 원가계산 ]
				String taxFreeProductCosts = nEDMPRO0020Dao.selectNorProdPcost(nEDMPRO0020VO);
				nEDMPRO0020VO.setNorProdPcost(taxFreeProductCosts);

				//--[VIC 과세 및 면세,영세  일경우 원가계산 ]-----------------------------------
				String victaxFreeProductCosts = nEDMPRO0020Dao.selectWnorProdPcost(nEDMPRO0020VO);
				nEDMPRO0020VO.setWnorProdPcost(victaxFreeProductCosts);
			}
		}


		//----- 1.상품상세설명 수정
		nEDMPRO0020Dao.updateNewProductDesc(nEDMPRO0020VO);

		if(StringUtils.isEmpty(nEDMPRO0020VO.getPrftRate())) {
			nEDMPRO0020VO.setPrftRate("0");
		}

		//----- 2.전상법 저장
		nEDMPRO0020Dao.deleteNewProdTprProdAddInfo(nEDMPRO0020VO);		//저장전 삭제
		String[] arrAddInfoCd	=	nEDMPRO0020VO.getArrAddInfoCd();
		String[] arrAddInfoVal	=	nEDMPRO0020VO.getArrAddInfoVal();
		for (int i = 0; i < arrAddInfoCd.length; i++) {
			nEDMPRO0020VO.setProdAddCd(arrAddInfoCd[i]);
			nEDMPRO0020VO.setProdAddVal(arrAddInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdAddInfo(nEDMPRO0020VO);				//저장
		}


		//----- 3.KC인증 저장
		nEDMPRO0020Dao.deleteNewProdTprProdCertInfo(nEDMPRO0020VO);		//저장전 삭제
		String[] arrCertInfoCd	=	nEDMPRO0020VO.getArrCertInfoCd();
		String[] arrCertInfoVal	=	nEDMPRO0020VO.getArrCertInfoVal();
		for (int i = 0; i < arrCertInfoCd.length; i++) {
			nEDMPRO0020VO.setProdCertCd(arrCertInfoCd[i]);
			nEDMPRO0020VO.setProdCertVal(arrCertInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdCertInfo(nEDMPRO0020VO);
		}


		//----- 4.상품마스터 정보 저장
		nEDMPRO0020Dao.updateNewProductMST(nEDMPRO0020VO);


		resultMap.put("pgmId",	nEDMPRO0020VO.getPgmId());					//등록된 신상품 PGM_ID
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	/**
	 * 오프라인 이미지 저장
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void saveSaleImg(HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();

		NEDMPRO0024VO paramVO = null;

		String pgmId		= StringUtils.trimToEmpty((String)request.getParameter("pgmId"));
		String newProdGenDivnCd	= StringUtils.trimToEmpty((String)request.getParameter("newProdGenDivnCd"));
		String prodAttTypFg		= StringUtils.trimToEmpty((String)request.getParameter("prodAttTypFg"));
		String uploadDir    = baseController.makeSubFolderForOffline(pgmId);

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		Iterator fileIter = mptRequest.getFileNames();

		String[] prodImgIdAl 	= null;
		String[] variantAl 		= null;

		prodImgIdAl = request.getParameterValues("prodImgIdAl");
		variantAl 	= request.getParameterValues("variantAl");

		//logger.debug("prodImgIdAl----->" + prodImgIdAl.length);
		//logger.debug("variantAl----->" + variantAl.length);

		int i = 0;
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			if (!mFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
				String postfix = mFile.getName();
				
				// postfix replace 처리
				postfix = postfix.replaceAll("[\\\\/\"&]", "").replaceAll("\\.\\.", "");

				//-----코리안넷에서 등록한 상품이 아닐경우
				/*if (!newProdGenDivnCd.equals("KOR")) {
					if(postfix.endsWith("front")) {
						postfix = postfix.replaceAll("_front", ".1");
					}
					if(postfix.endsWith("side")) {
						postfix = postfix.replaceAll("_side", ".2");
					}
					if(postfix.endsWith("top")) {
						postfix = postfix.replaceAll("_top", ".3");
					}
					if(postfix.endsWith("back")) {
						postfix = postfix.replaceAll("_back", ".4");
					}

				//----- 코리안넷에서 등록한 상품일 경우
				} else {*/

					//logger.debug("postfix----->" + postfix);
					//-----단일 상품일 경우
					if (prodAttTypFg.equals("00")) {
						if(postfix.endsWith("front")) {
							postfix = postfix.replaceAll("_front", variantAl[i] + ".1");
						}
						if(postfix.endsWith("side")) {
							postfix = postfix.replaceAll("_side", variantAl[i] + ".2");
						}
						if(postfix.endsWith("top")) {
							postfix = postfix.replaceAll("_top",  variantAl[i] + ".3");
						}
						if(postfix.endsWith("back")) {
							postfix = postfix.replaceAll("_back", variantAl[i] + ".4");
						}

					//-----묶음 상품일 경우
					} else {
						if(postfix.endsWith("front")) {
							postfix = postfix.replaceAll("_front", ".1");
						}
						if(postfix.endsWith("side")) {
							postfix = postfix.replaceAll("_side", ".2");
						}
						if(postfix.endsWith("top")) {
							postfix = postfix.replaceAll("_top", ".3");
						}
						if(postfix.endsWith("back")) {
							postfix = postfix.replaceAll("_back", ".4");
						}
					}
				//}

				// String baseFileSource = uploadDir+"/"+offlineImage.getNewProductCode()+"_"+postfix;
				String newFileSource = uploadDir+"/"+postfix+".jpg";//+ext;

				FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
				FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

				//----- 이미지 정보 Insert, Update
				logger.debug("----->" + prodImgIdAl[i]);
				logger.debug("----->" + variantAl[i]);

				paramVO = new NEDMPRO0024VO();
				paramVO.setPgmId(pgmId);
				paramVO.setVariant(variantAl[i]);
				paramVO.setProdImgId(prodImgIdAl[i] + "" + variantAl[i]);
//				paramVO.setRegId(epcLoginVO.getAdminId());
				//작업자정보 setting (로그인세션에서 추출)
				paramVO.setRegId(workId);
				paramVO.setCfmFg(StringUtils.trimToEmpty(StringUtils.trimToEmpty(request.getParameter("cfmFg"))));
				paramVO.setEntpCd(StringUtils.trimToEmpty(request.getParameter("entpCd")));


				//logger.debug("variant----->" + postfix.substring(0, 14));
				//logger.debug("ProdImgId----->" + postfix.substring(11, 14));

				nEDMPRO0020Dao.insertSaleImg(paramVO);
			}

			i++;
		}
	}

	/**
	 * 오프라인 이미지 일괄저장
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveSaleImgAllApply(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}
		
		//============
		// 작업자정보
		//============
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		
		//파일확장자 제한
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.product.offline");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit); 

		String result =	"FAIL";

		String uploadDir = 	baseController.makeSubFolderForOffline((String)paramMap.get("pgmId"));

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		Iterator fileIter = mptRequest.getFileNames();

		FileOutputStream frontImageStream = null;
		String newFileSource = "";
		String imgIDs = "";

		NEDMPRO0024VO paramVO = null;

		// 넘어온 variant 배열 추출
		String[] chkVariantAl = null;
		String chkVariant = (String) paramMap.get("chkVariant");

		if (chkVariant == null || chkVariant.equals("")) {
			throw new TopLevelException("");
		}
		chkVariantAl = chkVariant.split("\\|");

		String orgFileNm = "";	//원본파일명
		String fileExt = "";	//파일확장자
		// 넘어온 Variant가 있는경우
		if (chkVariantAl.length > 0) {
			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

				if (!mFile.isEmpty()) {
					//String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
					String postfix = mFile.getName();
					//전체경로 경로순회 문자열 및 종단문자 제거
					postfix = StringUtil.getCleanPath(postfix, true);
					
					/***************************************/
					//원본파일명 (확장자추출용)
					orgFileNm = StringUtil.getCleanPath(mFile.getOriginalFilename(), true); 
					//파일확장자
					fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
    				if(!extLimit.contains(fileExt.toLowerCase())) {
    					logger.error("업로드 불가능 확장자 - 파일명 : "+orgFileNm);
    					result = "EXT_ERR";
    					return result;
    				}
    				/***************************************/
					
					if(postfix.endsWith("front")) {
						 postfix = postfix.replaceAll("_front", ".1");
					}
					if(postfix.endsWith("side")) {
						 postfix = postfix.replaceAll("_side", ".2");
					}
					if(postfix.endsWith("top")) {
						 postfix = postfix.replaceAll("_top", ".3");
					}

					for (int i = 0; i < chkVariantAl.length; i++) {
						String variant = chkVariantAl[i];

						if (variant != null && !variant.equals("")) {
							imgIDs = (String)paramMap.get("prodImgId") + variant;

							//logger.debug("imgIDs----->" + imgIDs);
							//logger.debug("newFileSource----->" + newFileSource);
							//logger.debug("----------------------");

							newFileSource = uploadDir + "/" + imgIDs + postfix + ".jpg";//+ext;
							//전체경로 경로순회 문자열 및 종단문자 제거
							StringUtil.getCleanPath(newFileSource, true);
							frontImageStream = new FileOutputStream(newFileSource);
							FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
							frontImageStream.close();

							//----- 이미지  테이블에 저장
							paramVO = new NEDMPRO0024VO();
							paramVO.setPgmId((String)paramMap.get("pgmId"));
							paramVO.setVariant(variant);
							paramVO.setProdImgId(imgIDs);
//							paramVO.setRegId((String)paramMap.get("entpCd"));
							///작업자정보 setting (로그인세션에서 추출)
							paramVO.setRegId(workId);
							paramVO.setEntpCd((String)paramMap.get("entpCd"));
							paramVO.setCfmFg(StringUtils.trimToEmpty((String)paramMap.get("cfmFg")));

							//logger.debug("variant----->" + postfix.substring(0, 14));
							//logger.debug("ProdImgId----->" + postfix.substring(11, 14));

							nEDMPRO0020Dao.insertSaleImg(paramVO);
						}
					}
				}
			}
		}

		result = "SUCCESS";
		return result;
	}



	/**
	 * 상품정보 복사
	 */
	public Map<String, Object> insertNewProductCopyMST(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {

		if (nEDMPRO0020VO == null) {
			throw new TopLevelException("");
		}
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0020VO.setRegId(workId); //등록자
		nEDMPRO0020VO.setModId(workId);	//수정자

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		Map<String, Object> paramMap	=	new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		//----- 기존 등록되어있던 이미지 아이디
		String oldImgId	=	StringUtils.trimToEmpty(nEDMPRO0020VO.getProdImgId());

		//----- 기존 PGM_ID
		String oldPgmId = nEDMPRO0020VO.getOldPgmId();
		
		//선택한 협력업체
		String selectedVendor = StringUtils.trimToEmpty(nEDMPRO0020VO.getEntpCd());
		
		//채널코드
		String chanCd = StringUtils.defaultString(nEDMPRO0020VO.getChanCd());		//채널코드 (콤마로 구분된 string)
		List<String> chanList = Arrays.asList(chanCd.split(","));
		
		//채널 정보 없음
		if(chanList == null || chanList.isEmpty()) {
			resultMap.put("msg", "NO_CHAN_CD");
			return resultMap;
		}
		
		//선택한 업체의 선택가능 구매조직 체크 ----------
		CommonProductVO venChanChkVo = new CommonProductVO();
		venChanChkVo.setVenCd(selectedVendor);
		Map<String,Object> venChanMap = commonProductService.selectVendorZzorgInfo(venChanChkVo, null);
		
		//해당 업체코드로 선택가능한 구매조직(채널)
		List<String> venPurDept = (List<String>) MapUtils.getObject(venChanMap, "venPurDepts");
		
		//선택한 채널이 선택 가능한 구매조직(채널)에 모두 포함되는지 확인 
		if(!venPurDept.containsAll(chanList)) {
			//선택 불가능한 채널
			resultMap.put("msg", "CHAN_NOT_SELECTABLE");
			return resultMap;
		}

		//----- 협력업체 거래유형 조회
		paramMap.put("selectedVendor", selectedVendor);
		HashMap	vendorHsMap	=	commonProductDao.selectNVendorTradeType(paramMap);

		//-----직매입, 특약1, 특약2가 아니면 상품등록 불가
		if (!StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("1") && !StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("2") && !StringUtils.trimToEmpty((String)vendorHsMap.get("TRD_TYP_FG")).equals("4")) {
			resultMap.put("msg", "NOT_PERMISSION_TRD_TYP_FG");
			return resultMap;
		}

		//-----판매코드가 있을 경우 위해상품 판매코드 일 경우 상품등록 불가능[현재 사용중 2016.04.08 추가 by song min kyo]
		if (StringUtils.trimToEmpty(this.selectChkDangerProdCnt_3(StringUtils.trimToEmpty(nEDMPRO0020VO.getSellCd()))).equals("2")) {
			resultMap.put("msg", "DANGER_PROD");
			return resultMap;
		}

		//----- 소분류에 매핑되어 있는 변형속성 카운트 조회
		paramMap.put("l3Cd", nEDMPRO0020VO.getL3Cd());
		int varAttCnt	=	commonProductDao.selectL3CdVarAttCnt(paramMap);

		String pgmId = "";
		if (nEDMPRO0020VO.getIuGbn().equals("I")) {
			//----- 신상품 등록 PGM_ID 구해오기
//			pgmId	=	nEDMPRO0020Dao.selectNewProductPgmId("");
			//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
			pgmId	= 	bosApiService.selectTpcNewProdRegKey();
		} else {
			pgmId 	= 	nEDMPRO0020VO.getPgmId();
		}
		
		//PGM 생성되지 않음
		if("".equals(pgmId)) {
			resultMap.put("msg", "NO_PGM_ID");
			return resultMap;
		}

		//----- POG 이미지 ID값 설정 [PGM_ID값 이용]
		String prodImgId	=	StringUtils.substring(pgmId, 2, 8) + StringUtils.right(pgmId,  5);

		//----- 신상품 PGM_ID, 이미지 아이디 설정
		nEDMPRO0020VO.setPgmId(pgmId);
		nEDMPRO0020VO.setProdImgId(prodImgId);

		//----- AS-IS 에 적용되어 있던 소스 사용하는 부분이 없어서 제외
		/*if(!"0".equals(tmpProduct.getPromotionAmountFlag())) {
			newProduct.setPromotionAmountFlag("1");
		} else {
			newProduct.setPromotionAmountFlag(tmpProduct.getPromotionAmountFlag());
		}

		//상품 유형구분이 PB(2)인 경우
		if("2".equals(newProduct.getProductTypeDivnCode())) {
			newProduct.setPromotionAmountFlag("0");
		}*/

		logger.debug("vendorHsMap----->" + vendorHsMap);
		//----- 해당 협력 업체 코드로 조회한 거래 유형값 설정
		if (vendorHsMap != null) {
			String vendorTrdTypFg	=	(String)vendorHsMap.get("TRD_TYP_FG");

			nEDMPRO0020VO.setTradeType(vendorTrdTypFg);

			/*if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM01")) {
				nEDMPRO0020VO.setTradeType("1");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM02")) {
				nEDMPRO0020VO.setTradeType("2");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM07")) {
				nEDMPRO0020VO.setTradeType("3");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM03")) {
				nEDMPRO0020VO.setTradeType("4");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM06")) {
				nEDMPRO0020VO.setTradeType("5");

			//----- KM04, KM05, KM08, KM09는 tradeTypeCd를 어떻게 바꿔야 되는지 확인 필요함 현재는 잉시로 그냥 끝자리 지정해놓음.
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM04")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM05")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM08")) {
				nEDMPRO0020VO.setTradeType("9");
			} else if (StringUtils.trimToEmpty(vendorTrdTypFg).equals("KM09")) {
				nEDMPRO0020VO.setTradeType("9");
			} */

			logger.debug("vendorTrdTypFg----->" + vendorTrdTypFg);
			logger.debug("varAttCnt----->" + varAttCnt);

			//-----거래형태가 직매입이면
			if (vendorTrdTypFg.equals("1")) {
				
				//----- 채널별 면/과세 구분에 따른 이익률 계산 start----------------- 
				String commPrftRate = "";	//이익률 환산결과
				for(String selChanCd : chanList) {
					switch(selChanCd) {
						case "KR02":	//마트
							commPrftRate = nEDMPRO0020Dao.selectnewProdPrftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setPrftRate(commPrftRate);	//마트_이익률 셋팅
							break;
						case "KR03":	//MAXX
							commPrftRate = nEDMPRO0020Dao.selectnewProdWprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setWprftRate(commPrftRate);	//MAXX_이익률 셋팅
							break;
						case "KR04":	//슈퍼
							commPrftRate = nEDMPRO0020Dao.selectnewProdSprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setSprftRate(commPrftRate);	//슈퍼_이익률 셋팅
							break;
						case "KR09":	//오카도
							commPrftRate = nEDMPRO0020Dao.selectnewProdOprftRate(nEDMPRO0020VO);
							nEDMPRO0020VO.setOprftRate(commPrftRate);	//오카도_이익률 셋팅
							break;
						default:
							break;
						
					}
				}
				//----- 채널별 면/과세 구분에 따른 이익률 계산 end ----------------- 

//
//				//------ [직매입시 면세/과세 별 이익율 계산 ]
//				String prftRate		=	nEDMPRO0020Dao.selectnewProdPrftRate(nEDMPRO0020VO);
//				nEDMPRO0020VO.setPrftRate(prftRate);
//
//				//------ [VIC 직매입시 면세/과세 별 이익율 계산  ]
//				String wPrftRate	=	nEDMPRO0020Dao.selectnewProdWprftRate(nEDMPRO0020VO);
//				nEDMPRO0020VO.setWprftRate(wPrftRate);


				//----- 변형 속성이 존재하면
				if (varAttCnt > 0) {
					if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
						nEDMPRO0020VO.setNoprodFg("ZAW1");
					} else {	// 묶음상품일 경우
						nEDMPRO0020VO.setNoprodFg("ZAW2");
					}
				} else {
					nEDMPRO0020VO.setNoprodFg("ZAW1");
				}

				//-----무발주 매입구분 미적용으로 해준다.
				nEDMPRO0020VO.setNpurBuyPsbtDivnCd("");

			//-----거래형태가 직매입 경우 제외한 나머지
			} else {
//				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getPrftRate()).equals("")) {
//					nEDMPRO0020VO.setPrftRate("0");
//				}
//
//				if (StringUtils.trimToEmpty(nEDMPRO0020VO.getWprftRate()).equals("")) {
//					nEDMPRO0020VO.setPrftRate("0");
//				}
				
				//----- 채널별 면/과세 구분에 따른 원가 계산 start----------------- 
				String commNorProdPcost = "";	//원가 환산결과
				for(String selChanCd : chanList) {
					switch(selChanCd) {
						case "KR02":	//마트
							commNorProdPcost = nEDMPRO0020Dao.selectNorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setNorProdPcost(commNorProdPcost);	//마트_원가 셋팅
							break;
						case "KR03":	//MAXX
							commNorProdPcost = nEDMPRO0020Dao.selectWnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setWnorProdPcost(commNorProdPcost);	//MAXX_원가 셋팅
							break;
						case "KR04":	//슈퍼
							commNorProdPcost = nEDMPRO0020Dao.selectSnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setSnorProdPcost(commNorProdPcost);	//슈퍼_원가 셋팅
							break;
						case "KR09":	//오카도
							commNorProdPcost = nEDMPRO0020Dao.selectOnorProdPcost(nEDMPRO0020VO);
							nEDMPRO0020VO.setOnorProdPcost(commNorProdPcost);	//오카도_원가 셋팅
							break;
						default:
							break;
						
					}
				}
				//----- 채널별 면/과세 구분에 따른 원가 계산 end-----------------

//				//-------[과세 및 면세,영세 일경우 원가계산 ]
//				String taxFreeProductCosts = nEDMPRO0020Dao.selectNorProdPcost(nEDMPRO0020VO);
//				nEDMPRO0020VO.setNorProdPcost(taxFreeProductCosts);
//
//				//--[VIC 과세 및 면세,영세  일경우 원가계산 ]-----------------------------------
//				String victaxFreeProductCosts = nEDMPRO0020Dao.selectWnorProdPcost(nEDMPRO0020VO);
//				nEDMPRO0020VO.setWnorProdPcost(victaxFreeProductCosts);

				//----- 특약1[KM02]
				if (vendorTrdTypFg.equals("2")) {

					//----- 변형 속성이 존재하면
					if (varAttCnt > 0) {
						if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
							nEDMPRO0020VO.setNoprodFg("ZAW3");
						} else {
							nEDMPRO0020VO.setNoprodFg("ZAW4");
						}
					} else {
						nEDMPRO0020VO.setNoprodFg("ZAW3");
					}

				//----- 특약2[KM03]
				} else if (vendorTrdTypFg.equals("4")) {

					//----- 변형 속성이 존재하면
					if (varAttCnt > 0) {
						if (nEDMPRO0020VO.getProdAttTypFg().equals("00")) {	// 단일상품일 경우
							nEDMPRO0020VO.setNoprodFg("ZAW5");
						} else {
							nEDMPRO0020VO.setNoprodFg("ZAW6");
						}
					} else {
						nEDMPRO0020VO.setNoprodFg("ZAW5");
					}

					//-----무발주 매입구분을 미적용으로 해준다.
					nEDMPRO0020VO.setNpurBuyPsbtDivnCd("");
				}
			}
		}

//		if(StringUtils.isEmpty(nEDMPRO0020VO.getPrftRate())) {
//			nEDMPRO0020VO.setPrftRate("0");
//		}

		//----- 1.온라인 이미지 복사(DB정보가 따로 없기 떄문에 실제 파일만 복사)
		/*
		 String onlineUploadFolder	 				= baseController.makeSubFolderForOnline(nEDMPRO0020VO.getOldPgmId());
		 String onlineUploadFolderForNewCopyProduct	= baseController.makeSubFolderForOnline(nEDMPRO0020VO.getPgmId());
		 int onlineImgCnt = 0;

		 File dir = new File(onlineUploadFolder);
		 FileFilter fileFilter = new WildcardFileFilter(nEDMPRO0020VO.getOldPgmId()+"*");
		 File[] files = dir.listFiles(fileFilter);
		 for (int j = 0; j < files.length; j++) {

			 String currnetOnlineImageFileName = files[j].getName();
			 String newProductOnlineImageFileName = onlineUploadFolderForNewCopyProduct + "/" + StringUtils.replace(currnetOnlineImageFileName, nEDMPRO0020VO.getOldPgmId(), nEDMPRO0020VO.getPgmId());

			 FileOutputStream copiedProductOnlineImageStream = new FileOutputStream(newProductOnlineImageFileName);
			 FileUtils.copyFile(files[j], copiedProductOnlineImageStream);
			 onlineImgCnt++;
		 }*/

		//이미지복사 ( 와이드 이미지 포함 )
		/* --배포시 반드시 주석풀것*/
		int onlineImgCnt = 0;
		int wideOnlineImgCnt =0;

		String orgFileNm = "";
		String wideOrgFileNm = "";

		String orgUploadDirPath = baseController.makeSubFolderForOnline(nEDMPRO0020VO.getOldPgmId());
		String newUploadDirPath = baseController.makeSubFolderForOnline(nEDMPRO0020VO.getPgmId());

		String wideOrgUploadDirPath = baseController.makeSubFolderForWide(nEDMPRO0020VO.getOldPgmId());
		String wideNewUploadDirPath = baseController.makeSubFolderForWide(nEDMPRO0020VO.getPgmId());

		File files	= new File(orgUploadDirPath);
		File wideFiles = new File(wideOrgUploadDirPath);

		for(File file : files.listFiles()){
			if( file.isDirectory() ){
				continue;
			}

			if( file.isFile() ) {
				if(file.getName().indexOf(".") == -1){
					orgFileNm = file.getName();

					if(orgFileNm.indexOf(nEDMPRO0020VO.getOldPgmId()) > -1){
						String newFileNm = nEDMPRO0020VO.getPgmId()+orgFileNm.substring(orgFileNm.lastIndexOf("_"),orgFileNm.length());

						FileInputStream inputStream = new FileInputStream(orgUploadDirPath+"/"+orgFileNm);
						FileOutputStream outputStream = new FileOutputStream(newUploadDirPath+"/"+newFileNm);

						FileChannel fcin =  inputStream.getChannel();
						FileChannel fcout = outputStream.getChannel();

						long size = fcin.size();
						fcin.transferTo(0, size, fcout);

						fcout.close();
						fcin.close();

						outputStream.close();
						inputStream.close();

						imageFileMngService.purgeImageQCServer("01",newFileNm);
						imageFileMngService.purgeCDNServer("01", newFileNm);

						onlineImgCnt++;
					}
				}
			}
		}
		// 와이드 이미지 업로드
		for(File file : wideFiles.listFiles()){
			if( file.isDirectory() ){
				continue;
			}

			if( file.isFile() ) {
				if(file.getName().indexOf(".") == -1){
					wideOrgFileNm = file.getName();

					if(wideOrgFileNm.indexOf(nEDMPRO0020VO.getOldPgmId()) > -1){
						String wideNewFileNm = nEDMPRO0020VO.getPgmId()+wideOrgFileNm.substring(wideOrgFileNm.lastIndexOf("_"),wideOrgFileNm.length());

						FileInputStream inputStream = new FileInputStream(wideOrgUploadDirPath+"/"+wideOrgFileNm);
						FileOutputStream outputStream = new FileOutputStream(wideNewUploadDirPath+"/"+wideNewFileNm);

						FileChannel fcin =  inputStream.getChannel();
						FileChannel fcout = outputStream.getChannel();

						long size = fcin.size();
						fcin.transferTo(0, size, fcout);

						fcout.close();
						fcin.close();

						outputStream.close();
						inputStream.close();

						imageFileMngService.purgeImageQCServer("13",wideOrgFileNm);
						imageFileMngService.purgeCDNServer("13", wideOrgFileNm);

						wideOnlineImgCnt++;
					}
				}
			}
		}
			/*여기까지 배포시 반드시 주석풀것 */

		//----- 2.전상법 저장
		nEDMPRO0020Dao.deleteNewProdTprProdAddInfo(nEDMPRO0020VO);			//저장전 삭제
		String[] arrAddInfoCd	=	nEDMPRO0020VO.getArrAddInfoCd();
		String[] arrAddInfoVal	=	nEDMPRO0020VO.getArrAddInfoVal();
		for (int i = 0; i < arrAddInfoCd.length; i++) {
			nEDMPRO0020VO.setProdAddCd(arrAddInfoCd[i]);
			nEDMPRO0020VO.setProdAddVal(arrAddInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdAddInfo(nEDMPRO0020VO);				//저장
		}


		//----- 3.KC인증 저장
		nEDMPRO0020Dao.deleteNewProdTprProdCertInfo(nEDMPRO0020VO);		//저장전 삭제
		String[] arrCertInfoCd	=	nEDMPRO0020VO.getArrCertInfoCd();
		String[] arrCertInfoVal	=	nEDMPRO0020VO.getArrCertInfoVal();
		for (int i = 0; i < arrCertInfoCd.length; i++) {
			nEDMPRO0020VO.setProdCertCd(arrCertInfoCd[i]);
			nEDMPRO0020VO.setProdCertVal(arrCertInfoVal[i]);

			nEDMPRO0020Dao.insertNewProdCertInfo(nEDMPRO0020VO);
		}


		//----- 4.상품마스터 정보 저장, 상품상세설명 저장
		// 상품 Mst Insert
		nEDMPRO0020VO.setImgNcnt(String.valueOf(onlineImgCnt));			//온라인 이미지가 복사되면  온라인 이미지 갯수를 설정해준다. 배포시 주석풀것!!!!!!!!!!
		nEDMPRO0020VO.setWideImgNcnt(String.valueOf(onlineImgCnt));		// 와이드 온라인 이미지 개수 복사
		nEDMPRO0020Dao.insertNewProdMst(nEDMPRO0020VO);

		// 상품 상세설명 Insert
		nEDMPRO0020Dao.insertNewProdDescrInfo(nEDMPRO0020VO);

		//상품 수입정보 Insert
		if(nEDMPRO0020VO.getHscd().isEmpty() && nEDMPRO0020VO.getDecno().isEmpty() && nEDMPRO0020VO.getTarrate().isEmpty()){
			logger.debug("");
		}
		else {
			nEDMPRO0020Dao.insertNewImportInfo(nEDMPRO0020VO);
		}

/*		if (nEDMPRO0020VO.getIuGbn().equals("I")) {
			// 상품 Mst Insert
			nEDMPRO0020Dao.insertNewProdMst(nEDMPRO0020VO);

			// 상품 상세설명 Insert
			nEDMPRO0020Dao.insertNewProdDescrInfo(nEDMPRO0020VO);
		} else {
			// 상품 Mst Update
			nEDMPRO0020Dao.updateNewProductMST(nEDMPRO0020VO);

			// 상품 상세설명 Update
			nEDMPRO0020Dao.updateNewProductDesc(nEDMPRO0020VO);
		}
*/

		//----- 5.20180821 상품키워드 입력 기능 추가
		// 전산정보팀 이상구


		Map<String, Object>	keyWordMap	=	new HashMap<String, Object>();
		keyWordMap.put("pgmId", nEDMPRO0020VO.getOldPgmId());
		keyWordMap.put("newPgmId", nEDMPRO0020VO.getPgmId());
		keyWordMap.put("regId", workId);	//작업자_등록아이디

		nEDMPRO0020Dao.insertStaffSellTpcPrdKeyword(keyWordMap);
		/* ***************************** */

		//200306 EC전용 카테고르 추가 START
		logger.debug("----->" + nEDMPRO0020VO.getDispCatCd() + "/" + nEDMPRO0020VO.getStdCatCd()  + " / " + pgmId + " / " + nEDMPRO0020VO.getEntpCd() );
		if(!StringUtils.isEmpty(nEDMPRO0020VO.getDispCatCd())) {
			EcProductCategory ecProductCategory = new EcProductCategory();
			ecProductCategory.setProdCd(pgmId);
			ecProductCategory.setPgmId(pgmId);
//			ecProductCategory.setRegId(nEDMPRO0020VO.getEntpCd());
			//작업자정보 setting (로그인세션에서 추출)
			ecProductCategory.setRegId(workId);
			ecProductCategory.setStdCatCd(nEDMPRO0020VO.getStdCatCd());
			String[] dispCatArr = nEDMPRO0020VO.getDispCatCd().split(",");
			for (int i = 0; i < dispCatArr.length; i++) {
				ecProductCategory.setDispCatCd(dispCatArr[i]);

				nEDMPRO0020Dao.insertEcProductCategory(ecProductCategory);
			}
		}
		//200306 EC전용 카테고리 추가 END

		//24NBM_OSP_CAT S
		saveOspDispCd(nEDMPRO0020VO);
		//24NBM_OSP_CAT E

		/************************* 상품범주와 소분류가 변경되지 않았을 경우에는 변형/분석속성 정보, 이미지 정보 모두 복사 *****************************/
		if (StringUtils.trimToEmpty(nEDMPRO0020VO.getCopyGbn()).equals("Y")) {
			//이동빈 수정 온오프이고 묶음상품일때 변형속성은 copy안됨 20160816
			paramMap.put("oldPgmId", nEDMPRO0020VO.getOldPgmId());
			if (nEDMPRO0020VO.getOnOffDivnCd().equals("0") && nEDMPRO0020VO.getProdAttTypFg().equals("01")) {   // 온오프이고 묶음일때
						int varAttCnt001	=	commonProductDao.selectVarAttCnt001(paramMap);
						if (varAttCnt001 > 0) {
							                   //관리자일경우 모두 복사 아니면 판매코드 없는 변형은 복사 안한다.
												if (nEDMPRO0020VO.getAdmFg().equals("3")){
				 									nEDMPRO0020Dao.insertNewProdVarAttCopy(nEDMPRO0020VO);
													}
												else {
													nEDMPRO0020Dao.insertNewProdVarAttCopy(nEDMPRO0020VO);
													//판매코드 없는건 복사 안한다
													//nEDMPRO0020Dao.insertNewProdVarAttCopySellNotNull(nEDMPRO0020VO);
													}
												}
						//	varAttCnt001갯수가  0이면 복사 안한다.
			}
			else {
				nEDMPRO0020Dao.insertNewProdVarAttCopy(nEDMPRO0020VO);
			}
			//----- 변형속성 정보 복사
			//nEDMPRO0020Dao.insertNewProdVarAttCopy(nEDMPRO0020VO);

			//----- 분석속성 정보 복사
			nEDMPRO0020Dao.insertNewProdGrpAttCopy(nEDMPRO0020VO);

			//----- 오프라인 이미지 정보 복사하기전 오프라인 이미지 실제 파일 물리적 경로에 복사
			String offlineUploadFolder	 				= baseController.makeSubFolderForOffline(nEDMPRO0020VO.getOldPgmId());
			String offlineUploadFolderForNewCopyProduct	= baseController.makeSubFolderForOffline(nEDMPRO0020VO.getPgmId());

			File offlineDir = new File(offlineUploadFolder);
			FileFilter offlineFileFilter = new WildcardFileFilter(oldImgId+"*");
			File[] offlineFiles = offlineDir.listFiles(offlineFileFilter);

			for (int jj = 0; jj < offlineFiles.length; jj++) {
				String currnetPogFileName = offlineFiles[jj].getName();

				//logger.debug("oldImgId ========" + oldImgId);
				//logger.debug("prodImgId ========" + prodImgId);

				String newProductImageFileName = offlineUploadFolderForNewCopyProduct + "/" + StringUtils.replace(currnetPogFileName, oldImgId, prodImgId);

				//logger.debug("newProductImageFileName==========================" + newProductImageFileName);

				try (FileOutputStream copiedProductPogImageStream = new FileOutputStream(newProductImageFileName)) {
				    FileUtils.copyFile(offlineFiles[jj], copiedProductPogImageStream);    
				}

			 }

			//----- 오프라인 이미지 정보 복사(DB)
			nEDMPRO0020Dao.insertNewProdOffImgCopy(nEDMPRO0020VO);
		}
		/*********************************************************************************************************************/


		if(StringUtils.trimToEmpty(nEDMPRO0020VO.getEcCategoryKeepYn()).equals("true") && nEDMPRO0020VO.getEcAttrRegYn().equals("Y")) {
	        nEDMPRO0020Dao.insertEcProductAttributeCopy(nEDMPRO0020VO);
		}

		// PIY_영양성분속성_복사값 추가하기
		List<HashMap> nutAttInfoList = nEDMPRO0020Dao.selectNutInfoByPgmId(oldPgmId);
		if (nutAttInfoList.size() !=0 && nEDMPRO0020VO.getDelNutAmtYn().equals("N")) {

			for (int idx=0; idx<nutAttInfoList.size(); idx++) {
				HashMap<String,String> nutInfoMap = nutAttInfoList.get(idx);
				nutInfoMap.put("pgmId", pgmId);
				nutInfoMap.put("entpCd",nEDMPRO0020VO.getEntpCd());
				nutInfoMap.put("regId", workId);
				nEDMPRO0020Dao.mergeNutAttWithPgmId(nutInfoMap);
			}
		}
		
		/*
		 * ESG 인증정보 복사
		 */
		//ESG 인증 마스터정보 복사
		int updEsgMsg = nEDMPRO0020Dao.updateCopyProdEsgInfo(nEDMPRO0020VO);
		
		NEDMPRO0028VO esgMstInfoVo = new NEDMPRO0028VO();
		esgMstInfoVo.setPgmId(oldPgmId);	//원본 pgmId
		//원본 ESG 인증정보 중, 수정 가능한 경우에 표시되는 데이터들만 복사처리함 (ex. ESG 항목이 삭제되었거나 존재하지 않는 건들은 복사하지 않도록 하기위함)
		esgMstInfoVo.setEditYn("Y");
		
		//ESG 인증 정보 리스트 (등록/수정 가능한 건들만 추출한 정보 list)
		List<NEDMPRO0028VO> esgList = nEDMPRO0028Dao.selectNewProdEsgList(esgMstInfoVo);
		
		//ESG 인증 정보 리스트 존재 시,
		if(esgList != null && !esgList.isEmpty() && esgList.size()>0) {
			String esgFileId, esgOrgFileNm, esgSaveFileNm, oldEsgFilePath;
			String esgUploadFilePath = ConfigUtils.getString("edi.prod.file.path");
			String newEsgFileId = "", newEsgFilePath = "", newEsgExt = "", newEsgSaveFileNm, newEsgFileSize;
			NEDMPRO0028VO esgInfoVo;
			
			File oldEsgFile;
			File newEsgFile;
			File newEsgFileDir;
			
			NEDMPRO0028VO esgFileVo;
			
			String esgGbn = "";		//ESG 인증구분
			String esgAuth = "";	//ESG 인증유형
			String esgAuthDtl = "";	//ESG 인증상세유형
			for(NEDMPRO0028VO esgVo : esgList) {
				//ESG인증정보 저장 VO 생성
				esgInfoVo = new NEDMPRO0028VO();
				
				/***************** 등록된 ESG 첨부파일 존재시, 첨부파일 copy ***************/
				esgFileId = StringUtils.defaultString(esgVo.getEsgFileId());	//원본파일아이디
				if(!"".equals(esgFileId)) {
					//원본파일 존재여부 확인
					oldEsgFilePath = esgUploadFilePath + "/" + oldPgmId + "/";	//원본파일업로드경로
					esgSaveFileNm = StringUtils.defaultString(esgVo.getSaveFileNm());	//원본파일 저장명
					oldEsgFile = new File(oldEsgFilePath+esgSaveFileNm);				//원본파일 객체
					
					//원본 파일존재 시,
					if(oldEsgFile.exists()) {
						esgOrgFileNm = StringUtils.defaultString(esgVo.getOrgFileNm());		//원본파일명
						esgGbn = StringUtils.defaultString(esgVo.getEsgGbn());				//ESG 인증구분
						esgAuth = StringUtils.defaultString(esgVo.getEsgAuth());			//ESG 인증유형
						esgAuthDtl = StringUtils.defaultString(esgVo.getEsgAuthDtl());		//ESG 인증상세유형
						
						//새 파일정보 생성
						newEsgFileId = "ESG" + DateUtil.getCurrentTime("yyMMdd") + System.currentTimeMillis() + StringUtil.getRandomKey(2); //복사파일아이디
						newEsgFilePath = esgUploadFilePath + "/" + pgmId + "/";	//복사파일 경로
						newEsgExt = esgOrgFileNm.substring(esgOrgFileNm.lastIndexOf(".")+1);			//복사파일	확장자
//						newEsgSaveFileNm = newEsgFileId+"."+newEsgExt;	//복사파일 저장명
						newEsgSaveFileNm = pgmId + "_" + esgGbn + "_" + esgAuth + "_" + esgAuthDtl + "." + newEsgExt;		//저장파일명 = 프로그램아이디_ESG유형_ESG인증유형_ESG인증유형상세
						
						//디렉토리 경로 파일객체 생성
						newEsgFileDir = new File(newEsgFilePath);
						if(!newEsgFileDir.exists()) {
							// 부모 폴더까지 포함하여 경로에 폴더를 만든다.
	    	                if (newEsgFileDir.mkdirs()) {
	    	                    logger.info("[file.mkdirs] : Success");
	    	                } else {
	    	                    logger.error("[file.mkdirs] : Fail");
	    	                }
						}
						
						//저장 파일 객체 생성
						newEsgFile = new File(newEsgFilePath+newEsgSaveFileNm);
						
						//물리파일 복사
						FileCopyUtils.copy(oldEsgFile, newEsgFile);
						
						newEsgFileSize = Long.toString(newEsgFile.length());	//복사파일 사이즈
						
						esgFileVo = new NEDMPRO0028VO();
						esgFileVo.setEsgFileId(newEsgFileId);			//ESG파일아이디
						esgFileVo.setOrgFileNm(esgOrgFileNm);			//ESG원본파일명
						esgFileVo.setSaveFileNm(newEsgSaveFileNm);		//ESG저장파일명
						esgFileVo.setFileSize(newEsgFileSize);			//ESG저장파일사이즈
						esgFileVo.setFilePath(newEsgFilePath);			//ESG저장파일경로
						esgFileVo.setFileExt(newEsgExt);				//ESG저장파일 확장자명
						esgFileVo.setWorkId(workId);					//작업자아이디
						
						//파일정보 등록
						nEDMPRO0028Dao.mergeProdEsgFile(esgFileVo);
						
						esgInfoVo.setEsgFileId(newEsgFileId);		//ESG인증정보-파일아이디 셋팅
					}else {
					//파일 미존재 시,
						logger.error("ESG 원본 파일이 존재하지 않습니다.\nFilePath:::"+oldEsgFilePath+esgSaveFileNm);
					}
				}
				/***************** 등록된 ESG 첨부파일 존재시, 첨부파일 copy ***************/
				
				//인증정보 insert
				esgInfoVo.setPgmId(pgmId);						//프로그램아이디
				esgInfoVo.setEsgGbn(esgVo.getEsgGbn());			//ESG인증구분
				esgInfoVo.setEsgAuth(esgVo.getEsgAuth());		//ESG인증유형
				esgInfoVo.setEsgAuthDtl(esgVo.getEsgAuthDtl());	//ESG인증유형상세
				esgInfoVo.setEsgFrDt(esgVo.getEsgFrDt());		//ESG인증시작일
				esgInfoVo.setEsgToDt(esgVo.getEsgToDt());		//ESG인증종료일
				esgInfoVo.setWorkId(workId);					//작업자아이디
				
				nEDMPRO0028Dao.mergeNewProdEsg(esgInfoVo);
			}
		}
		
		resultMap.put("pgmId",	pgmId);					//등록된 신상품 PGM_ID
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}


	/**
	 * 코리안넷을 통해 상품등록시 사업자번호로 협력사 존재유무 체크
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public int selectExistxKoreanNetVendor(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {

		//-----코리안넷을 통해 넘어오는 사업자등록번호가 ','를 활용하여 문자열로 넘어올수도 있기 때문에 10자리가 넘어서 올경우 배열로 구성
		if (StringUtils.trimToEmpty(nEDMPRO0020VO.getBman_no()).length() > 10) {
			nEDMPRO0020VO.setArrBmanNo(nEDMPRO0020VO.getBman_no().split(","));
		}

		return nEDMPRO0020Dao.selectExistxKoreanNetVendor(nEDMPRO0020VO);
	}

	/**
	 * 상품소분류에 변형속성 유무 체크
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectVarAttCnt(Map<String, Object> paramMap)throws Exception {
		Map<String, Object> resultMap	=	new HashMap<String, Object>();

		resultMap.put("varAttCnt", nEDMPRO0020Dao.selectVarAttCnt(paramMap));

		return resultMap;
	}

	/**
	 * 코리안넷 협력사 리스트
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectKoreanNetEntpCdList(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> resultMap	=	 new HashMap<String, Object>();

		if (StringUtils.trimToEmpty((String)paramMap.get("bmanNo")).length() > 10) {
			String[] arrBmanNo	=	paramMap.get("bmanNo").toString().split(",");
			paramMap.put("arrBmanNo", arrBmanNo);
		}

		resultMap.put("entpCdList", nEDMPRO0020Dao.selectKoreanNetEntpCdList(paramMap));
		return resultMap;
	}

	/**
	 * 소분류에 따른 이미지등록 제한관리 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectProdEssentialInfo(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> resultMap	=	new HashMap<String, Object>();
		resultMap.put("result", nEDMPRO0020Dao.selectProdEssentialInfo(paramMap));
		return resultMap;
	}

	/**
	 * 등록된 신상품의 소분류의 표시단위, 표시기준수량 default 정보 존재 여부 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdEssentialExistInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0020Dao.selectNewProdEssentialExistInfo(paramMap);
	}

	/**
	 * 기본카테고리 목록(IN 조건)
	 * @Method Name : selectCategoryInList
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws Exception
	{
		return nEDMPRO0020Dao.selectCategoryInList(paramMap);
	}


	/* ****************************** */
	//20180821 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * @param newProductCode
	 * @return List<PEDMPRO0003VO>
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode) throws Exception {
		return nEDMPRO0020Dao.selectTpcPrdKeywordList(newProductCode);
	}

	/**
	 * 신규 상품키워드 등록
	 * @return int
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		int resultCnt = 0;
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		bean.setRegId(workId);

		try {
			//--10개 이상 채크
			// 키워드 입력
			resultCnt = nEDMPRO0020Dao.insertTpcPrdKeyword(bean);

			if (resultCnt <= 0) {
				throw new TopLevelException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			// 전체 키워드 셋팅
			List<PEDMPRO0011VO> prdList = nEDMPRO0020Dao.selectChkTpcPrdTotalKeyword(bean);

			if (!(prdList == null || prdList.size() == 0)) {
				PEDMPRO0011VO resultBean = (PEDMPRO0011VO)prdList.get(0);
	        	String totalKywrd = resultBean.getTotalKywrd();
	        	String seq = resultBean.getSeq();

	        	bean.setTotalKywrd(totalKywrd);

	        	if ("000".equals(seq)) {
					//전체키워드 업데이트
					resultCnt = nEDMPRO0020Dao.updateTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						throw new TopLevelException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else {
					//전체키워드 입력
					resultCnt = nEDMPRO0020Dao.insertTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						throw new TopLevelException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 신규 상품키워드 삭제
	 * @return MAP
	 * @throws Exception
	 */
	public Map<String, Object> deleteTpcPrdKeyword(Map<String, Object> map) throws Exception {
		Map<String, Object>	resultMap =	new HashMap<String, Object>();
		
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();

		int resultCnt = 0;

		String pgmId = "";
		String regId = "";

		// entpCd 로 바꿈
		if(map.get("pgmId") != null && map.get("entpCd") != null) {
			pgmId = (String) map.get("pgmId");
//			regId = (String) map.get("entpCd");
			regId = workId;		//작업자정보 setting (로그인세션에서 추출)
		}

		nEDMPRO0020Dao.deleteTpcPrdKeyword(map);

		PEDMPRO0011VO bean = new PEDMPRO0011VO();

		bean.setPgmId(pgmId);
		bean.setRegId(regId);

		// 전체 키워드 셋팅
		List<PEDMPRO0011VO> prdList = nEDMPRO0020Dao.selectChkTpcPrdTotalKeyword(bean);

		if (!(prdList == null || prdList.size() == 0)) {
			PEDMPRO0011VO resultBean = (PEDMPRO0011VO)prdList.get(0);
        	String totalKywrd = resultBean.getTotalKywrd();
        	String seq = resultBean.getSeq();

        	bean.setTotalKywrd(totalKywrd);

        	if("000".equals(seq)) {
				//전체 키워드 업데이트
				resultCnt = nEDMPRO0020Dao.updateTpcPrdTotalKeyword(bean);

				if(resultCnt <= 0) {
					resultMap.put("msg", "FAIL");
					throw new TopLevelException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
				}
			} else {
        		if(!("".equals(totalKywrd) || totalKywrd == null)) {
        			//전체키워드 입력
					resultCnt = nEDMPRO0020Dao.insertTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						resultMap.put("msg", "FAIL");
						throw new TopLevelException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
        		}
			}
		}

		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}


	/**
	 * 상품 상세 이미지 처리 화면 변경
	 * 신상품 마스터 테이블 온라인 이미지 정보 수정
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateNewProdImgInfo(NewProduct newProduct) throws Exception{
		nEDMPRO0020Dao.updateNewProdImgInfo(newProduct);
	}


	/**
	 * 상품 상세설명 중복 체크
	 * @param NewProduct
	 * @return
	 * @throws Exception
	 */
	public int selectProductDescription(NewProduct newProduct) throws Exception {
		return nEDMPRO0020Dao.selectProductDescription(newProduct);
	}

	/**
	 * 상품 상세 이미지 처리 화면 변경
	 * 상품 상세설명 수정
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateProductDescription(NewProduct newProduct) throws Exception{
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		newProduct.setRegId(workId); //등록자
		nEDMPRO0020Dao.updateProductDescription(newProduct);
	}

	/**
	 * EC 표준 카테고리 매핑
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception {
		return nEDMPRO0020Dao.selectEcStandardCategoryMapping(martCatCd);
	}

	/**
	 * EC 상품 표준/전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception {
		return nEDMPRO0020Dao.selectEcCategoryByProduct(pgmId);
	}

	/**
	 * 와이드 이미지 개수 조회
	 * @param pgmId
	 * @throws Exception
	 */
	@Override
	public String selectOnlineWideImageCnt(String pgmId) throws Exception {
		return nEDMPRO0020Dao.selectOnlineWideImageCnt(pgmId);
	}

	/**
	 * 와이드 이미지 개수 업데이트
	 * @param pgmId
	 * @throws Exception
	 */
	@Override
	public void updateOnlineWideImageCnt(Map<String, Object> paramMap) throws Exception {
		nEDMPRO0020Dao.updateOnlineWideImageCnt(paramMap);
	}


	/**
	 * EC 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */

	@Override
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception {
		return nEDMPRO0020Dao.selectEcProductAttr(paramMap);
	}

	/**
	 * Desc : 온라인 전용 상품 단품 정보 리스트
	 * @Method Name : selectProductItemListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */

	public List<ColorSize> selectProductItemListInTemp(String newProductCode) {
		// TODO Auto-generated method stub
		return nEDMPRO0020Dao.selectProductItemListInTemp(newProductCode);
	}

	/**
	 * EC카테고리 값에 의한 EC속성의 개수
	 * @param
	 * @return
	 * @throws Exception
	 */

	public int selectEcProductAttrCnt(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0020Dao.selectEcProductAttrCnt(paramMap);
	}

	/**
	 * 입력한 EC속성의 개수
	 * @param
	 * @return
	 * @throws Exception
	 */

	public int selectInputEcProductAttrCnt(String pgmId) throws Exception {
		return nEDMPRO0020Dao.selectInputEcProductAttrCnt(pgmId);
	}

	/**
	 * 입력한 EC속성의 개수
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutInfoByL3Cd(String l3Cd) throws Exception {
		return nEDMPRO0020Dao.selectNutInfoByL3Cd(l3Cd);
	}

	/**
	 * 등록한 영양성분 속성
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutInfoByPgmId(String pgmId) throws Exception {
		return nEDMPRO0020Dao.selectNutInfoByPgmId(pgmId);
	}

	/**
	 * 영양성분 코드 삭제
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void deleteNutAttWithPgmId(String pgmId) throws Exception {
		nEDMPRO0020Dao.deleteNutAttWithPgmId(pgmId);
	}

	/**
	 * 영양성분코드 등록 및 삭제
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void mergeNutAttWithPgmId(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0020VO.setRegId(workId); //등록자
		nEDMPRO0020VO.setModId(workId);	//수정자

		deleteNutAttWithPgmId(nEDMPRO0020VO.getPgmId());

		HashMap<String,String> paramMap = new HashMap<String, String>();

		for(int idx=0; idx<nEDMPRO0020VO.getNutCd().length; idx++) {

			if( nEDMPRO0020VO.getNutAmt()[idx] == null ||
					nEDMPRO0020VO.getNutAmt()[idx].isEmpty() ) continue;

            paramMap.put("pgmId", nEDMPRO0020VO.getPgmId());
            paramMap.put("entpCd", nEDMPRO0020VO.getEntpCd());
            paramMap.put("nutCd", nEDMPRO0020VO.getNutCd()[idx]);
            paramMap.put("nutAmt", nEDMPRO0020VO.getNutAmt()[idx]);
            paramMap.put("regId", workId);

            nEDMPRO0020Dao.mergeNutAttWithPgmId(paramMap);
		}
	}

	/**
	 * PB 파트너사인지 확인
	 * @param NEDMPROO020VO
	 * @return String
	 * @throws Exception
	 */
	public String checkPbPartner(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return nEDMPRO0020Dao.checkPbPartner(nEDMPRO0020VO);
	}

	/**
	 * EC패션 속성값 페이지 전시 유무
	 *
	 * @param String
	 * @return List<HashMap>
	 * @throws Exception
	 */
	public List<HashMap> displayEcFashionAttr(String stdCatCd) throws Exception {
		return nEDMPRO0020Dao.displayEcFashionAttr(stdCatCd);
	}

	/**
	 * Osp 표준 카테고리 매핑
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HashMap> selectOspStandardCategoryMapping(String l3Cd) throws Exception {
		return nEDMPRO0020Dao.selectOspStandardCategoryMapping(l3Cd);
	}

	/**
	 * 저장된 OSP 카테고리 가져오기
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HashMap> selectOspCategorySaved(String pgmId) throws Exception {
		return nEDMPRO0020Dao.selectOspCategorySaved(pgmId);
	}

	/**
	 * 오카도 카테고리 입력
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertOspDispCategory(Map paramMap) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		paramMap.put("regId", workId);
		nEDMPRO0020Dao.insertOspDispCategory(paramMap);
	}

	/**
	 * 오카도 카테고리 삭제
	 */
	public int deleteOspDispCategory(String pgmId) throws Exception {
		return nEDMPRO0020Dao.deleteOspDispCategory(pgmId);
	}

	/**
	 * 오카도 커테고리 값 담아 저장
	 */
	public boolean saveOspDispCd(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		String workId = this.getLoginWorkId();
		nEDMPRO0020VO.setRegId(workId); //등록자
		nEDMPRO0020VO.setModId(workId);	//수정자

		try {
			if(!StringUtils.isEmpty(nEDMPRO0020VO.getOspDispCatCd())) {
				// 저장하기전 삭제
				nEDMPRO0020Dao.deleteOspDispCategory(nEDMPRO0020VO.getPgmId());

				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("prodCd", nEDMPRO0020VO.getPgmId());
				paramMap.put("pgmId", nEDMPRO0020VO.getPgmId());
				paramMap.put("regId", nEDMPRO0020VO.getRegId());
				paramMap.put("stdCatCd", nEDMPRO0020VO.getStdCatCd());

				String[] ospDispCatArr = nEDMPRO0020VO.getOspDispCatCd().split(",");
				String ospDispCatCd = "";
				String isRepOspCat = "";

				for (int i = 0; i < ospDispCatArr.length; i++) {
					isRepOspCat = ospDispCatArr[i].contains("^REP") ? "Y" : "";
					ospDispCatCd = isRepOspCat.equals("Y") ? ospDispCatArr[i].replace("^REP","") : ospDispCatArr[i];

					paramMap.put("ospDispCatCd", ospDispCatCd);
					paramMap.put("rprtDcatYn", isRepOspCat);


					nEDMPRO0020Dao.insertOspDispCategory(paramMap);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}

		return true;
	}
	
	
	/**
	 * 세션에서 작업자 아이디 추출
	 * @return String
	 */
	private String getLoginWorkId() {
		String workId = "";
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			workId = epcLoginVO.getLoginWorkId();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return workId;
	}

}
