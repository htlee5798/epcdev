package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.dao.NEDMPRO0030Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0040Dao;
import com.lottemart.epc.edi.product.service.NEDMPRO0030Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

/**
 * @Class Name : NEDMPRO0030ServiceImpl
 * @Description : 온라인신상품등록(배송정보) 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */
@Service("nEDMPRO0030Service")
public class NEDMPRO0030ServiceImpl extends BaseController implements NEDMPRO0030Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0030ServiceImpl.class);

	@Resource(name="nEDMPRO0030Dao")
	private NEDMPRO0030Dao	nEDMPRO0030Dao;

	@Resource(name="nEDMPRO0040Dao")
	private NEDMPRO0040Dao nEDMPRO0040Dao;

	// 신상품 등록 관련 서비스
	@Resource(name = "ediProductService")
	private PEDMPRO000Service ediProductService;

	@Resource(name="imageFileMngService")
	private ImageFileMngService imageFileMngService;

	/**
	 * 배송정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNewOnlineDeliveryList(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0030Dao.selectNewOnlineDeliveryList(paramMap);
	}

	/**
	 * 업체 주소정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectAddrMgrList(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0030Dao.selectAddrMgrList(paramMap);
	}

	/**
	 * 공통배송비정보1 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0030Dao.selectVendorDlvInfo(paramMap);
	}

	/**
	 * 공통배송비정보2 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectVendorDeliInfo(Map<String, Object> paramMap) throws Exception {
		return nEDMPRO0030Dao.selectVendorDeliInfo(paramMap);
	}

	/**
	 * 배송정보 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void newOnlineDeliverySave(DataMap paramMap) throws Exception {
		String deliKindCd = paramMap.getString("deliKindCd");           //배송비 종류
		String condUseYn =paramMap.getString("condUseYn"); 				//업체 공통조건 사용여부

		if("Y".equals(condUseYn)){
			paramMap.put("bdlDeliYn", "Y");

			nEDMPRO0030Dao.deleteDeliveryAmt(paramMap);    //배송비 삭제
			nEDMPRO0030Dao.updateNewProdReg(paramMap); //배송가능지역, 묶음배송여부 등록
		}else{
			nEDMPRO0030Dao.deleteDeliveryAmt(paramMap);  // 저장전 배송비 삭제

			/* 2020.03.03 정책 변경 : 수량별 차등 삭제
			 * if("02".equals(deliKindCd)){	//수량별 차등
				paramMap.put("bdlDeliYn", paramMap.getString("bdlDeliYn"+deliKindCd));

				for(int i=1; i<6; i++){
					String deliAmt = paramMap.getString("deliAmt02_"+i);
					String minSetQty = paramMap.getString("minSetQty_"+i);
					String maxSetQty = paramMap.getString("maxSetQty_"+i);

					if(!"".equals(deliAmt)){
						paramMap.put("deliAmt", deliAmt);
						paramMap.put("minSetQty", minSetQty);
						paramMap.put("maxSetQty", maxSetQty);

						nEDMPRO0030Dao.insertDeliveryAmt(paramMap);
					}
				}
			} */	
			
			if("01".equals(deliKindCd) || "03".equals(deliKindCd)){
				paramMap.put("bdlDeliYn", paramMap.getString("bdlDeliYn"+deliKindCd));
				
				String deliAmt = paramMap.getString("deliAmt"+deliKindCd);

				paramMap.put("deliAmt", deliAmt);
				if(!"01".equals(deliKindCd)){
					paramMap.put("deliCondAmt", "");
				}

				nEDMPRO0030Dao.insertDeliveryAmt(paramMap);
			}

			if("Y".equals(paramMap.getString("psbtChkYn"))){  // 제주/도서산간 추가 배송비
				for(int i=1; i<3; i++){
					deliKindCd = "0"+(3+i);
					String deliAmt = paramMap.getString("deliAmt0"+(3+i));

					paramMap.put("deliAmt", deliAmt);
					paramMap.put("deliKindCd", deliKindCd);
					paramMap.put("minSetQty", "");
					paramMap.put("maxSetQty", "");
					paramMap.put("deliCondAmt", "");

					nEDMPRO0030Dao.insertDeliveryAmt(paramMap);
				}
			}

			// 반품 배송비
			String deliAmt = paramMap.getString("deliAmt06");
			if(!"".equals(deliKindCd) && !"".equals(deliAmt)){
				paramMap.put("deliKindCd", "06");
				paramMap.put("deliAmt", deliAmt);
				paramMap.put("minSetQty", "");
				paramMap.put("maxSetQty", "");
				paramMap.put("deliCondAmt", "");
	
				nEDMPRO0030Dao.insertDeliveryAmt(paramMap);
			}

			nEDMPRO0030Dao.updateNewProdReg(paramMap); //배송가능지역, 묶음배송여부 등록
		}

		nEDMPRO0030Dao.deleteAddrMgr(paramMap);        //업체 주소정보 삭제

		for(int i=1; i<3; i++){ //업제 주소정보 등록
			paramMap.put("addrSeq", paramMap.getString("addr"+i));
			nEDMPRO0030Dao.insertAddrMgr(paramMap);
		}
	}

	/**
	 * 전상법 템플릿 selectBox 데이터
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempList(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectNorProdTempList(paramMap);
	}

	/**
	 * 전상법 템플릿 VALUES
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectNorProdTempValList(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectNorProdTempValList(paramMap);
	}

	public DataMap newMakeCopycatProduct(HttpServletRequest request) throws Exception {
		DataMap paramMap = new DataMap(request);
		String pgmId = paramMap.getString("newProductCode");

		paramMap.put("pgmId", pgmId);

		try{
			//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
			//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
			String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
			//신상품코드 생성 실패 시, exception
			if("".equals(newPgmId)) {
				throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
			}
//			String newPgmId = ediProductService.selectNewProductCode();

			paramMap.put("newPgmId", newPgmId); //신규임시상품코드

			/* 임직원몰 전용 상품 로직 같이 씀 (상품등록 제외 하고)*/
			nEDMPRO0030Dao.insertCopyProduct(paramMap); //상품복사

			nEDMPRO0040Dao.insertStaffSellProdAddDetail(paramMap); //전상법 복사

			nEDMPRO0040Dao.insertStaffSellProdCertDetail(paramMap); //KC인증 복사

			nEDMPRO0040Dao.insertStaffSellProdDelivery(paramMap); //배송정책 복사

			nEDMPRO0040Dao.insertStaffSellProdVendorAddr(paramMap); //배송정책 출고지,반품/교환지 주소 복사

			nEDMPRO0040Dao.insertStaffSellProductItemInfo(paramMap); //옵션단품 복사

			nEDMPRO0040Dao.insertStaffSellProductDescription(paramMap); //추가설명 복사

			//20180904 상품키워드 입력 기능 추가
			nEDMPRO0040Dao.insertStaffSellTpcPrdKeyword(paramMap);

			nEDMPRO0040Dao.insertEcProductCategory(paramMap); //EC전시카테고리 복사

			nEDMPRO0040Dao.insertEcProductAttribute(paramMap); //EC상품(단품별)속성 복사

			//이미지복사
			String orgFileNm = "";
			String orgUploadDirPath = makeSubFolderForOnline(pgmId);
			String newUploadDirPath = makeSubFolderForOnline(newPgmId);

			File files	= new File(orgUploadDirPath);

			for(File file : files.listFiles()){
				if( file.isDirectory() ){
					continue;
				}

				if( file.isFile() ) {
					if(file.getName().indexOf(".") == -1){
						orgFileNm = file.getName();

						if(orgFileNm.indexOf(pgmId) > -1){
							String newFileNm = newPgmId+orgFileNm.substring(orgFileNm.lastIndexOf("_"),orgFileNm.length());

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
						}
					}
				}
			}


		}catch(Exception e){
			logger.error(e.getMessage());
		}

		return paramMap;
	}

	/**
	 * 업체 거래형태 정보
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selecttrdTypFgSel(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selecttrdTypFgSel(paramMap);
	}

	/**
	 * EC 표준 카테고리 조회
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectEcStandardCategory(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectEcStandardCategory(paramMap);
	}

	/**
	 * EC 표준 카테고리 매핑
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception {
		return nEDMPRO0030Dao.selectEcStandardCategoryMapping(martCatCd);
	}

	/**
	 * EC 전시 카테고리 조회
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectEcDisplayCategory(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectEcDisplayCategory(paramMap);
	}

	/**
	 * EC 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectEcProductAttr(paramMap);
	}

	/**
	 * EC 상품 표준/전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception {
		return nEDMPRO0030Dao.selectEcCategoryByProduct(pgmId);
	}

	/**
	 * EC 롯데ON 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public int selectCountDispLton(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectCountDispLton(paramMap);
	}

	/**
	 * EC 롯데마트몰 전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public int selectCountDispMart(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectCountDispMart(paramMap);
	}


	/**
	 * EC 표준카테고리 - 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public int selectCountEcProductAttrId(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectCountEcProductAttrId(paramMap);
	}
	
	/**
	 * EC 표준카테고리 - 상품속성값 조회
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public int selectCountEcProductAttrValId(DataMap paramMap) throws Exception {
		return nEDMPRO0030Dao.selectCountEcProductAttrValId(paramMap);
	}
	
}
