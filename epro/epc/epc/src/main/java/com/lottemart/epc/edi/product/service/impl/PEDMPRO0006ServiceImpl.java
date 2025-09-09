package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.product.controller.PEDMPRO0006Controller;
import com.lottemart.epc.edi.product.dao.PEDMPRO0006Dao;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO00061VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0006VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0006Service;

@Service("pEDMPRO0006Service")
public class PEDMPRO0006ServiceImpl implements PEDMPRO0006Service {

	@Autowired
	private PEDMPRO0006Dao pEDMPRO0006Dao;
	
	@Autowired
	private BosOpenApiService bosApiService;

	@Override
	public HashMap selectBarCodeProductInfo(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return pEDMPRO0006Dao.selectBarCodeProductInfo(searchParam);
	}

	@Override
	public HashMap selectNewprodregInfo(PEDMPRO0006VO searchParam) {
		return pEDMPRO0006Dao.selectBarCodeProductInfo(searchParam);
	}

	@Override
	public List selectBarcodeList(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return pEDMPRO0006Dao.selectBarcodeList(searchParam);
	}


	@Override
	public void insertNewBarcode(PEDMPRO0006VO pedmpro0006vo) {
		// TODO Auto-generated method stub


		PEDMPRO00061VO pedmpro00061vo = null;
		String newPgmId = "";		//신규 PGM_ID(KEY)

		for(int i=0; pedmpro0006vo.getChgFg().length > i; i++)
		{
			pedmpro00061vo = new PEDMPRO00061VO();

			if(pedmpro0006vo.getPgmId() == null || pedmpro0006vo.getPgmId().length <= 0)
			{
				pedmpro00061vo.setPgmId("")    	 		; 	// PGM ID  (KEY)
				pedmpro00061vo.setSeq("")	      	 	;  	// 순번 (KEY
			}
			else
			{
				pedmpro00061vo.setPgmId(pedmpro0006vo.getPgmId()[i])    	 		; 	// PGM ID  (KEY)
				pedmpro00061vo.setSeq(pedmpro0006vo.getSeq()[i])	      	 		;  	// 순번 (KEY
			}

			pedmpro00061vo.setVenCd(pedmpro0006vo.getVenCd())	      	 		;	// 협력업체코드
			pedmpro00061vo.setL1Cd(pedmpro0006vo.getL1Cd())	          	 		;	// 대분류코드
			pedmpro00061vo.setSrcmkCd(pedmpro0006vo.getSrcmkCd())	  	 		;	// 판매(88)코드
			pedmpro00061vo.setProdCd(pedmpro0006vo.getProdCd())		  	 		;	// 상품코드
			pedmpro00061vo.setLstChgEmpNo(pedmpro0006vo.getLstChgEmpNo())		;	// 수정자

			pedmpro00061vo.setLogiBcd(pedmpro0006vo.getLogiBcd()[i])	 		;	// 물류바코드
			pedmpro00061vo.setUseFg(pedmpro0006vo.getUseFg()[i])	     		;	// 사용여부
			pedmpro00061vo.setMixProdFg(pedmpro0006vo.getMixProdFg()[i]) 		;	// 혼재여부 0:비혼재, 1:혼재
			pedmpro00061vo.setWg(pedmpro0006vo.getWg()[i])	             		;	// 총중량
			pedmpro00061vo.setWidth(pedmpro0006vo.getWidth()[i])	     		;	// 박스체적 (가로)
			pedmpro00061vo.setLength(pedmpro0006vo.getLength()[i])	     		;	// 박스체적 (세로)
			pedmpro00061vo.setHeight(pedmpro0006vo.getHeight()[i])	     		;	// 박스체적 (높이)
			pedmpro00061vo.setConveyFg(pedmpro0006vo.getConveyFg()[i])	 		;	// 소터에러사유
			pedmpro00061vo.setSorterFg(pedmpro0006vo.getSorterFg()[i])	 		;	// 소터구분

			pedmpro00061vo.setwUseFg(pedmpro0006vo.getwUseFg()[i])	     		;	// w사용여부


			if(pedmpro0006vo.getInnerIpsu() == null || pedmpro0006vo.getInnerIpsu().length <= 0)
				pedmpro00061vo.setInnerIpsu("0")		;	// 팔레트(가로박스수)
			else pedmpro00061vo.setInnerIpsu(pedmpro0006vo.getInnerIpsu()[i])		;	// 팔레트(가로박스수)

			if(pedmpro0006vo.getPltLayerQty() == null || pedmpro0006vo.getPltLayerQty().length <= 0)
				pedmpro00061vo.setPltLayerQty("0")    ;	// 팔레트(세로박스수)
			else pedmpro00061vo.setPltLayerQty(pedmpro0006vo.getPltLayerQty()[i])    ;	// 팔레트(세로박스수)

			if(pedmpro0006vo.getPltHeightQty() == null || pedmpro0006vo.getPltHeightQty().length <= 0)
				pedmpro00061vo.setPltHeightQty("0")  ;	// 팔레트 (높이박스수)
			else pedmpro00061vo.setPltHeightQty(pedmpro0006vo.getPltHeightQty()[i])  ;	// 팔레트 (높이박스수)


			if(pedmpro0006vo.getwInnerIpsu() == null || pedmpro0006vo.getwInnerIpsu().length <= 0)
				pedmpro00061vo.setwInnerIpsu("0")  ;	// w박스수
			else pedmpro00061vo.setwInnerIpsu(pedmpro0006vo.getwInnerIpsu()[i])  ;	// w박스수






			pedmpro00061vo.setLogiBoxIpsu(pedmpro0006vo.getLogiBoxIpsu()[i])    ;	// 물류박스 입수
			pedmpro00061vo.setChgFg(pedmpro0006vo.getChgFg()[i])          		;	// 변경구분 (I:등록, U:수정)
			pedmpro00061vo.setCrsdkFg(pedmpro0006vo.getCrsdkFg()[i])        	;	// 클로스덕 물류바코드 flag 순서/값  0/1



			//--[등록 및 수정 구분에 따른  PGM_ID,SEQ  생성 ] ---------------------------------------
			if(pedmpro00061vo.getChgFg().equals("I") && pedmpro00061vo.getPgmId().trim().length() <=0)
			{
				
				//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
				newPgmId = bosApiService.selectTpcNewProdRegKey();
				pedmpro00061vo.setPgmId(newPgmId);
//				pedmpro00061vo.setPgmId(pEDMPRO0006Dao.selectBarcodeList()); // PGM_ID 생성
				pedmpro00061vo.setSeq("001");								 // SEQ    생성  신규생성은 하나이상 일어나지 않는다.
			}

			//-- MERGE INTO  UPDATE or INSERT -----------------------------------------------

			if(pedmpro00061vo.getChgFg().equals("I")){
				pEDMPRO0006Dao.insertNewLogiBcdEdi(pedmpro00061vo);			//NEW_LOGI_BCDEDI
				pEDMPRO0006Dao.insertNewLogiCdEdi(pedmpro00061vo);			//NEW_LOGI_CD_EDI
				pEDMPRO0006Dao.insertNewSaleLogiEdi(pedmpro00061vo);		//NEW_SALE_LOGI_EDI
			}else{
				pEDMPRO0006Dao.updateNewLogiBcdEdi(pedmpro00061vo);			//NEW_LOGI_BCDEDI
				pEDMPRO0006Dao.updateNewLogiCdEdi(pedmpro00061vo);			//NEW_LOGI_CD_EDI
				pEDMPRO0006Dao.updateNewSaleLogiEdi(pedmpro00061vo);		//NEW_SALE_LOGI_EDI
			}
			//----------------selectBarcodeListTmp--------------------------------------------------------------
		}
	}

	@Override
	public HashMap newBarcodeRegistTmp(String val) {
		// TODO Auto-generated method stub

		return pEDMPRO0006Dao.newBarcodeRegistTmp(val);
	}


	@Override
	public HashMap selectBarcodeListTmp(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return pEDMPRO0006Dao.selectBarcodeListTmp(searchParam);
	}

	@Override
	public void insertNewBarcodeTmp(PEDMPRO0006VO pedmpro0006vo) {
		// TODO Auto-generated method stub


		PEDMPRO00061VO pedmpro00061vo = null;

		for(int i=0; pedmpro0006vo.getChgFg().length > i; i++)
		{
			pedmpro00061vo = new PEDMPRO00061VO();

			if(pedmpro0006vo.getPgmId() == null || pedmpro0006vo.getPgmId().length <= 0)
			{
				pedmpro00061vo.setPgmId("")    	 		; 	// PGM ID  (KEY)
				pedmpro00061vo.setSeq("")	      	 	;  	// 순번 (KEY
			}
			else
			{
				pedmpro00061vo.setPgmId(pedmpro0006vo.getPgmId()[i])    	 		; 	// PGM ID  (KEY)
				pedmpro00061vo.setSeq(pedmpro0006vo.getSeq()[i])	      	 		;  	// 순번 (KEY
			}

			pedmpro00061vo.setVenCd(pedmpro0006vo.getVenCd())	      	 		;	// 협력업체코드
			pedmpro00061vo.setL1Cd(pedmpro0006vo.getL1Cd())	          	 		;	// 대분류코드
			pedmpro00061vo.setSrcmkCd(pedmpro0006vo.getSrcmkCd())	  	 		;	// 판매(88)코드
			pedmpro00061vo.setProdCd(pedmpro0006vo.getProdCd())		  	 		;	// 상품코드
			pedmpro00061vo.setLstChgEmpNo(pedmpro0006vo.getLstChgEmpNo())		;	// 수정자

			pedmpro00061vo.setLogiBcd(pedmpro0006vo.getLogiBcd()[i])	 		;	// 물류바코드
			pedmpro00061vo.setUseFg(pedmpro0006vo.getUseFg()[i])	     		;	// 사용여부
			pedmpro00061vo.setMixProdFg(pedmpro0006vo.getMixProdFg()[i]) 		;	// 혼재여부 0:비혼재, 1:혼재
			pedmpro00061vo.setWg(pedmpro0006vo.getWg()[i])	             		;	// 총중량
			pedmpro00061vo.setWidth(pedmpro0006vo.getWidth()[i])	     		;	// 박스체적 (가로)
			pedmpro00061vo.setLength(pedmpro0006vo.getLength()[i])	     		;	// 박스체적 (세로)
			pedmpro00061vo.setHeight(pedmpro0006vo.getHeight()[i])	     		;	// 박스체적 (높이)
			pedmpro00061vo.setConveyFg(pedmpro0006vo.getConveyFg()[i])	 		;	// 소터에러사유
			pedmpro00061vo.setSorterFg(pedmpro0006vo.getSorterFg()[i])	 		;	// 소터구분
			pedmpro00061vo.setwUseFg(pedmpro0006vo.getwUseFg()[i])	     		;	// w사용여부

			if(pedmpro0006vo.getInnerIpsu() == null || pedmpro0006vo.getInnerIpsu().length <= 0)
				pedmpro00061vo.setInnerIpsu("0")		;	// 팔레트(가로박스수)
			else pedmpro00061vo.setInnerIpsu(pedmpro0006vo.getInnerIpsu()[i])		;	// 팔레트(가로박스수)

			if(pedmpro0006vo.getPltLayerQty() == null || pedmpro0006vo.getPltLayerQty().length <= 0)
				pedmpro00061vo.setPltLayerQty("0")    ;	// 팔레트(세로박스수)
			else pedmpro00061vo.setPltLayerQty(pedmpro0006vo.getPltLayerQty()[i])    ;	// 팔레트(세로박스수)

			if(pedmpro0006vo.getPltHeightQty() == null || pedmpro0006vo.getPltHeightQty().length <= 0)
				pedmpro00061vo.setPltHeightQty("0")  ;	// 팔레트 (높이박스수)
			else pedmpro00061vo.setPltHeightQty(pedmpro0006vo.getPltHeightQty()[i])  ;	// 팔레트 (높이박스수)



			if(pedmpro0006vo.getwInnerIpsu() == null || pedmpro0006vo.getwInnerIpsu().length <= 0)
				pedmpro00061vo.setwInnerIpsu("0")  ;	// w박스수
			else pedmpro00061vo.setwInnerIpsu(pedmpro0006vo.getwInnerIpsu()[i])  ;	// w박스수


			pedmpro00061vo.setLogiBoxIpsu(pedmpro0006vo.getLogiBoxIpsu()[i])    ;	// 물류박스 입수
			pedmpro00061vo.setChgFg(pedmpro0006vo.getChgFg()[i])          		;	// 변경구분 (I:등록, U:수정)
			pedmpro00061vo.setCrsdkFg(pedmpro0006vo.getCrsdkFg()[i])        	;	// 클로스덕 물류바코드 flag 순서/값  0/1



			//--[등록 및 수정 구분에 따른  PGM_ID,SEQ  생성 ] ---------------------------------------
			if(pedmpro00061vo.getChgFg().equals("I") && pedmpro00061vo.getPgmId().trim().length() <=0)
			{

				pedmpro00061vo.setPgmId(pedmpro0006vo.getNew_prod_id()); // PGM_ID 생성
				pedmpro00061vo.setSeq("001");								 // SEQ    생성  신규생성은 하나이상 일어나지 않는다.
			}

			//-- MERGE INTO  UPDATE or INSERT -----------------------------------------------

			if(pedmpro00061vo.getChgFg().equals("I")){
				pEDMPRO0006Dao.insertNewLogiBcdEdi(pedmpro00061vo);			//NEW_LOGI_BCDEDI
				pEDMPRO0006Dao.insertNewLogiCdEdi(pedmpro00061vo);			//NEW_LOGI_CD_EDI
				pEDMPRO0006Dao.insertNewSaleLogiEdi(pedmpro00061vo);		//NEW_SALE_LOGI_EDI
			}else{
				pEDMPRO0006Dao.updateNewLogiBcdEdi(pedmpro00061vo);			//NEW_LOGI_BCDEDI
				pEDMPRO0006Dao.updateNewLogiCdEdi(pedmpro00061vo);			//NEW_LOGI_CD_EDI
				pEDMPRO0006Dao.updateNewSaleLogiEdi(pedmpro00061vo);		//NEW_SALE_LOGI_EDI
			}
			//----------------selectBarcodeListTmp--------------------------------------------------------------
		}
	}


}
