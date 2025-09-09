package com.lottemart.epc.edi.batch.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lcn.module.common.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.model.FuelSale001VO;
import com.lottemart.epc.edi.batch.service.PEDMBAT0001Service;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.product.controller.PSCMPRD0011Controller;
import com.lottemart.epc.util.Utils;

@Controller
public class PEDMBAT0001Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PEDMBAT0001Controller.class);


////////마장점 주유소 매출 4건발생 전송


	//임시보관함용 서비스
	private PEDMBAT0001Service pEDMBAT0001Service;


	@Autowired
	public void setpEDMBAT0001Service(PEDMBAT0001Service pEDMBAT0001Service) {
		this.pEDMBAT0001Service = pEDMBAT0001Service;
	}



	@RequestMapping(value="/edi/batch/fuelsale0001.do")
   // public void orderCompleteInquiry(String order_id, String order_item_seq, String status_cd) throws Exception{
	//public void orderCompleteInquiry(@RequestParam Map<String,Object> map,test3model tmptest3model ,SearchProduct searchParam, HttpServletRequest request, Model model) {
//	public void orderCompleteInquiry(@RequestParam Map<String,Object> map
//														, HttpServletRequest request
//														, Model model) {
	public String orderCompleteInquiry(@RequestParam Map<String,Object> map,ModelMap model) {


//		System.out.println("==========START OF SALE SEND TO RESTSTOP============");


		StringBuffer buffXml = null;
		String dateYYYYMMDD = null;
		String sDateYYYYMMDD = null;

		String eDateYYYYMMDD = null;
		//sDateYYYYMMDD="20130814";
		//eDateYYYYMMDD="20130811";
		sDateYYYYMMDD="";
		eDateYYYYMMDD="";



//		String today = DateUtil.getToday("yyyy-MM-dd");
//		 sDateYYYYMMDD= commonUtil.nowDateBack(today);
//		 eDateYYYYMMDD= commonUtil.nowDateBack(today);


		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, -1);
		NowDate2.add(Calendar.DATE, -1);


		if(	sDateYYYYMMDD.equals(""))
		{
			sDateYYYYMMDD= Utils.formatDate(NowDate.getTime(),"yyyyMMdd");
		}
		if(	eDateYYYYMMDD.equals(""))
		{
			eDateYYYYMMDD= Utils.formatDate(NowDate2.getTime(),"yyyyMMdd");
		}

		map.put("sDateYYYYMMDD", sDateYYYYMMDD);
		map.put("eDateYYYYMMDD", eDateYYYYMMDD);
		model.addAttribute("paramMap",map);

    	try{

    		List<DataMap> dateList = pEDMBAT0001Service.selectFuesSale001SendDistinctDate(map);


if(dateList != null && dateList.size() > 0 ){
	for(int y=0;y<dateList.size();y++)
	{

		map.put("dateYYYYMMDD", (dateList.get(y)).getString("DY"));
		//map.put("billnumber", (dateList.get(y)).getString("DY"));
		model.addAttribute("paramMap",map);



		//List<DataMap> billNoList = pEDMBAT0001Service.selectFuesSale001SendDistinctBill(dateYYYYMMDD);
		List<DataMap> billNoList = pEDMBAT0001Service.selectFuesSale001SendDistinctBill(map);
    		if(billNoList != null && billNoList.size() > 0 ){
		    		for(int u=0;u<billNoList.size();u++){


		    		URL url = new URL("http://skhiway.com/SvrApp/SL000.java"); //매출
		    				buffXml 		= null;
							buffXml 		= new StringBuffer(); 			//buffXml 객체 초기화
//			System.out.println("==========START BILL_NO============>"+(billNoList.get(u)).getString("BILL_NO"));
			//				FuelSale001VO tmpFuelSale001VO  = pEDMBAT0001Service.selectFuesSale001Send(dateYYYYMMDD,(billNoList.get(u)).getString("BILL_NO"));



							//map.put("dateYYYYMMDD", dateYYYYMMDD);
							map.put("dateYYYYMMDD", (dateList.get(y)).getString("DY"));
							map.put("billnumber", (billNoList.get(u)).getString("BILL_NO"));

							model.addAttribute("paramMap",map);




							FuelSale001VO tmpFuelSale001VO  = pEDMBAT0001Service.selectFuesSale001Send(map);

								try {
							buffXml.append("<?xml version='1.0'?>");
							buffXml.append("<EREST>");
							buffXml.append("<TXJM-FD SRID='"+tmpFuelSale001VO.getSendXmlSrid()+"'");
							 buffXml.append(" RETCD='"+tmpFuelSale001VO.getSendXmlRetcd()+"'");
							 buffXml.append(" TRHDR_CNT='"+tmpFuelSale001VO.getSendXmlTrhdrCnt()+"'");
							 buffXml.append(" TRDTL_CNT='"+tmpFuelSale001VO.getSendXmlTrdtlCnt()+"'");
							 buffXml.append(" TRCRD_CNT='"+tmpFuelSale001VO.getSendXmlTrcrdCnt()+"'");
							 buffXml.append(" TRCSH_CNT='"+tmpFuelSale001VO.getSendXmlTrcshCnt()+"'");
							 buffXml.append(" TRCST_CNT='"+tmpFuelSale001VO.getSendXmlTrcstCnt()+"'");
							 buffXml.append(" TRFOD_CNT='"+tmpFuelSale001VO.getSendXmlTrfodCnt()+"'");
							 buffXml.append(" TRGFT_CNT='"+tmpFuelSale001VO.getSendXmlTrgftCnt()+"'");
							 buffXml.append(" TRJCD_CNT='"+tmpFuelSale001VO.getSendXmlTrjcdCnt()+"'");
							 buffXml.append(" TRWES_CNT='"+tmpFuelSale001VO.getSendXmlTrwesCnt()+"'");
							 buffXml.append(" TRPSQ_CNT='"+tmpFuelSale001VO.getSendXmlTrpsqCnt()+"'");
							 buffXml.append(" TRCSA_CNT='"+tmpFuelSale001VO.getSendXmlTrcsaCnt()+"'");
							 buffXml.append(" TRGFA_CNT='"+tmpFuelSale001VO.getSendXmlTrgfaCnt()+"'");
							 buffXml.append(" TRFOA_CNT='"+tmpFuelSale001VO.getSendXmlTrfoaCnt()+"'");
							 buffXml.append(" TRRFC_CNT='"+tmpFuelSale001VO.getSendXmlTrrfcCnt()+"'");
							 buffXml.append(" RUN_MODE='"+tmpFuelSale001VO.getSendXmlRunMode()+"'");
							 buffXml.append("/>");

							buffXml.append("<TRHDR ");
							buffXml.append(" SHOP_CD='"+tmpFuelSale001VO.getSendXmlShopCd()+"'");
							buffXml.append(" SALE_DATE='"+tmpFuelSale001VO.getSendXmlSaleDate()+"'");
							buffXml.append(" POS_NO='"+tmpFuelSale001VO.getSendXmlPosNo()+"'");
							buffXml.append(" BILL_NO='"+tmpFuelSale001VO.getSendXmlBillNo()+"'");
							buffXml.append(" REGI_SEQ='"+tmpFuelSale001VO.getSendXmlRegiSeq()+"'");
							buffXml.append(" SALE_YN='"+tmpFuelSale001VO.getSendXmlSaleYn()+"'");
							buffXml.append(" TOT_SALE_AMT='"+tmpFuelSale001VO.getSendXmlTotSaleAmt()+"'");
							buffXml.append(" TOT_DC_AMT='"+tmpFuelSale001VO.getSendXmlTotDcAmt()+"'");
							buffXml.append(" SVC_TIP_AMT='"+tmpFuelSale001VO.getSendXmlSvcTipAmt()+"'");
							buffXml.append(" TOT_ETC_AMT='"+tmpFuelSale001VO.getSendXmlTotEtcAmt()+"'");
							buffXml.append(" DCM_SALE_AMT='"+tmpFuelSale001VO.getSendXmlDcmSaleAmt()+"'");
							buffXml.append(" VAT_SALE_AMT='"+tmpFuelSale001VO.getSendXmlVatSaleAmt()+"'");
							buffXml.append(" VAT_AMT='"+tmpFuelSale001VO.getSendXmlVatAmt()+"'");
							buffXml.append(" NO_VAT_SALE_AMT='"+tmpFuelSale001VO.getSendXmlNoVatSaleAmt()+"'");
							buffXml.append(" NO_TAX_SALE_AMT='"+tmpFuelSale001VO.getSendXmlNoTaxSaleAmt()+"'");
							buffXml.append(" EXP_PAY_AMT='"+tmpFuelSale001VO.getSendXmlExpPayAmt()+"'");
							buffXml.append(" GST_PAY_AMT='"+tmpFuelSale001VO.getSendXmlGstPayAmt()+"'");
							buffXml.append(" RET_PAY_AMT='"+tmpFuelSale001VO.getSendXmlRetPayAmt()+"'");
							buffXml.append(" CASH_AMT='"+tmpFuelSale001VO.getSendXmlCashAmt()+"'");
							buffXml.append(" CRD_CARD_AMT='"+tmpFuelSale001VO.getSendXmlCrdCardAmt()+"'");
							buffXml.append(" WES_AMT='"+tmpFuelSale001VO.getSendXmlWesAmt()+"'");
							buffXml.append(" TK_GFT_AMT='"+tmpFuelSale001VO.getSendXmlTkGftAmt()+"'");
							buffXml.append(" TK_FOD_AMT='"+tmpFuelSale001VO.getSendXmlTkFodAmt()+"'");
							buffXml.append(" CST_POINT_AMT='"+tmpFuelSale001VO.getSendXmlCstPointAmt()+"'");
							buffXml.append(" JCD_CARD_AMT='"+tmpFuelSale001VO.getSendXmlJcdCardAmt()+"'");
							buffXml.append(" DC_GEN_AMT='"+tmpFuelSale001VO.getSendXmlDcGenAmt()+"'");
							buffXml.append(" DC_SVC_AMT='"+tmpFuelSale001VO.getSendXmlDcSvcAmt()+"'");
							buffXml.append(" DC_PCD_AMT='"+tmpFuelSale001VO.getSendXmlDcPcdAmt()+"'");
							buffXml.append(" DC_CPN_AMT='"+tmpFuelSale001VO.getSendXmlDcCpnAmt()+"'");
							buffXml.append(" DC_CST_AMT='"+tmpFuelSale001VO.getSendXmlDcCstAmt()+"'");
							buffXml.append(" DC_TFD_AMT='"+tmpFuelSale001VO.getSendXmlDcTfdAmt()+"'");
							buffXml.append(" DC_PRM_AMT='"+tmpFuelSale001VO.getSendXmlDcPrmAmt()+"'");
							buffXml.append(" DC_CRD_AMT='"+tmpFuelSale001VO.getSendXmlDcCrdAmt()+"'");
							buffXml.append(" DC_PACK_AMT='"+tmpFuelSale001VO.getSendXmlDcPackAmt()+"'");
							buffXml.append(" REPAY_CASH_AMT='"+tmpFuelSale001VO.getSendXmlRepayCashAmt()+"'");
							buffXml.append(" REPAY_TK_GFT_AMT='"+tmpFuelSale001VO.getSendXmlRepayTkGftAmt()+"'");
							buffXml.append(" CASH_BILL_AMT='"+tmpFuelSale001VO.getSendXmlCashBillAmt()+"'");
							buffXml.append(" DLV_ORDER_FG='"+tmpFuelSale001VO.getSendXmlDlvOrderFg()+"'");
							buffXml.append(" FD_TBL_CD='"+tmpFuelSale001VO.getSendXmlFdTblCd()+"'");
						    buffXml.append(" FD_GST_CNT_T='"+tmpFuelSale001VO.getSendXmlFdGstCntT()+"'");
							buffXml.append(" FD_GST_CNT_1='"+tmpFuelSale001VO.getSendXmlFdGstCnt1()+"'");
							buffXml.append(" FD_GST_CNT_2='"+tmpFuelSale001VO.getSendXmlFdGstCnt2()+"'");
							buffXml.append(" FD_GST_CNT_3='"+tmpFuelSale001VO.getSendXmlFdGstCnt3()+"'");
							buffXml.append(" FD_GST_CNT_4='"+tmpFuelSale001VO.getSendXmlFdGstCnt4()+"'");
							buffXml.append(" ORG_BILL_NO='"+tmpFuelSale001VO.getSendXmlOrgBillNo()+"'");
							buffXml.append(" ORDER_NO='"+tmpFuelSale001VO.getSendXmlOrderNo()+"'");
							buffXml.append(" INS_DT='"+tmpFuelSale001VO.getSendXmlInsDt()+"'");
							buffXml.append(" EMP_NO='"+tmpFuelSale001VO.getSendXmlEmpNo()+"'");
							buffXml.append(" PAY_OUT_DT='"+tmpFuelSale001VO.getSendXmlPayOutDt()+"'");
							buffXml.append(" CST_NM='"+tmpFuelSale001VO.getSendXmlCstNm()+"'");
							buffXml.append(" DLV_EMP_NO='"+tmpFuelSale001VO.getSendXmlDlvEmpNo()+"'");
							buffXml.append(" DLV_START_DT='"+tmpFuelSale001VO.getSendXmlDlvStartDt()+"'");
							buffXml.append(" DLV_PAYIN_EMP_NO='"+tmpFuelSale001VO.getSendXmlDlvPayinEmpNo()+"'");
							buffXml.append(" DLV_PAYIN_DT='"+tmpFuelSale001VO.getSendXmlDlvPayinDt()+"'");
							buffXml.append(" NEW_DLV_ADDR_YN='"+tmpFuelSale001VO.getSendXmlNewDlvAddrYn()+"'");
							buffXml.append(" DLV_ADDR='"+tmpFuelSale001VO.getSendXmlDlvAddr()+"'");
							buffXml.append(" DLV_ADDR_DTL='"+tmpFuelSale001VO.getSendXmlDlvAddrDtl()+"'");
							buffXml.append(" NEW_DLV_TEL_NO_YN='"+tmpFuelSale001VO.getSendXmlNewDlvTelNoYn()+"'");
							buffXml.append(" DLV_TEL_NO='"+tmpFuelSale001VO.getSendXmlDlvTelNo()+"'");
							buffXml.append(" DLV_CL_CD='"+tmpFuelSale001VO.getSendXmlDlvClCd()+"'");
							buffXml.append(" DLV_CM_CD='"+tmpFuelSale001VO.getSendXmlDlvCmCd()+"'");
							buffXml.append(" TRAVEL_CD='"+tmpFuelSale001VO.getSendXmlTravelCd()+"'");
							buffXml.append(" RSV_NO='"+tmpFuelSale001VO.getSendXmlRsvNo()+"'");
							buffXml.append(" PRE_PAY_CASH='"+tmpFuelSale001VO.getSendXmlPrePayCash()+"'");
							buffXml.append(" PRE_PAY_CARD='"+tmpFuelSale001VO.getSendXmlPrePayCard()+"'");
							buffXml.append(" RSV_USER_NM='"+tmpFuelSale001VO.getSendXmlRsvUserNm()+"'");
							buffXml.append(" RSV_USER_TEL_NO='"+tmpFuelSale001VO.getSendXmlRsvUserTelNo()+"'");
							buffXml.append(" PREPAY_AMT='"+tmpFuelSale001VO.getSendXmlPrepayAmt()+"'");
							buffXml.append(" COM_AMT='"+tmpFuelSale001VO.getSendXmlComAmt()+"'");
							buffXml.append("/>");

							buffXml.append("<TRDTL>");
							buffXml.append("<DATA-DTL ");
							buffXml.append(" DTL_NO='"+tmpFuelSale001VO.getSendXmlDtlNo()+"'");
							buffXml.append(" REGI_SEQ='"+tmpFuelSale001VO.getSendXmlRegiSeq1()+"'");
							buffXml.append(" SALE_YN='"+tmpFuelSale001VO.getSendXmlSaleYn1()+"'");
							buffXml.append(" PROD_CD='"+tmpFuelSale001VO.getSendXmlProdCd()+"'");
							buffXml.append(" PROD_TYPE_FG='"+tmpFuelSale001VO.getSendXmlProdTypeFg()+"'");
							buffXml.append(" CORNER_CD='"+tmpFuelSale001VO.getSendXmlCornerCd()+"'");
							buffXml.append(" CHG_BILL_NO='"+tmpFuelSale001VO.getSendXmlChgBillNo()+"'");
							buffXml.append(" TAX_YN='"+tmpFuelSale001VO.getSendXmlTaxYn()+"'");
							buffXml.append(" DLV_PACK_FG='"+tmpFuelSale001VO.getSendXmlDlvPackFg()+"'");
							buffXml.append(" ORG_SALE_MG_CD='"+tmpFuelSale001VO.getSendXmlOrgSaleMgCd()+"'");
							buffXml.append(" ORG_SALE_UPRC='"+tmpFuelSale001VO.getSendXmlOrgSaleUprc()+"'");
							buffXml.append(" NORMAL_UPRC='"+tmpFuelSale001VO.getSendXmlNormalUprc()+"'");
							buffXml.append(" SALE_MG_CD='"+tmpFuelSale001VO.getSendXmlSaleMgCd()+"'");
							buffXml.append(" SALE_QTY='"+tmpFuelSale001VO.getSendXmlSaleQty()+"'");
							buffXml.append(" SALE_UPRC='"+tmpFuelSale001VO.getSendXmlSaleUprc()+"'");
							buffXml.append(" SALE_AMT='"+tmpFuelSale001VO.getSendXmlSaleAmt()+"'");
							buffXml.append(" DC_AMT='"+tmpFuelSale001VO.getSendXmlDcAmt()+"'");
							buffXml.append(" ETC_AMT='"+tmpFuelSale001VO.getSendXmlEtcAmt()+"'");
							buffXml.append(" SVC_TIP_AMT='"+tmpFuelSale001VO.getSendXmlSvcTipAmt1()+"'");
							buffXml.append(" DCM_SALE_AMT='"+tmpFuelSale001VO.getSendXmlDcmSaleAmt1()+"'");
							buffXml.append(" VAT_AMT='"+tmpFuelSale001VO.getSendXmlVatAmt1()+"'");
							buffXml.append(" SVC_CD='"+tmpFuelSale001VO.getSendXmlSvcCd()+"'");

							buffXml.append(" TK_CPN_CD='"+tmpFuelSale001VO.getSendXmlTkCpnCd()+"'");
							buffXml.append(" DC_AMT_GEN='"+tmpFuelSale001VO.getSendXmlDcAmtGen()+"'");
							buffXml.append(" DC_AMT_SVC='"+tmpFuelSale001VO.getSendXmlDcAmtSvc()+"'");
							buffXml.append(" DC_AMT_JCD='"+tmpFuelSale001VO.getSendXmlDcAmtJcd()+"'");
							buffXml.append(" DC_AMT_CPN='"+tmpFuelSale001VO.getSendXmlDcAmtCpn()+"'");
							buffXml.append(" DC_AMT_CST='"+tmpFuelSale001VO.getSendXmlDcAmtCst()+"'");
							buffXml.append(" DC_AMT_FOD='"+tmpFuelSale001VO.getSendXmlDcAmtFod()+"'");
							buffXml.append(" DC_AMT_PRM='"+tmpFuelSale001VO.getSendXmlDcAmtPrm()+"'");
							buffXml.append(" DC_AMT_CRD='"+tmpFuelSale001VO.getSendXmlDcAmtCrd()+"'");
							buffXml.append(" DC_AMT_PACK='"+tmpFuelSale001VO.getSendXmlDcAmtPack()+"'");
							buffXml.append(" CST_SALE_POINT='"+tmpFuelSale001VO.getSendXmlCstSalePoint()+"'");
							buffXml.append(" CST_USE_POINT='"+tmpFuelSale001VO.getSendXmlCstUsePoint()+"'");
							buffXml.append(" PRM_PROC_YN='"+tmpFuelSale001VO.getSendXmlPrmProcYn()+"'");
							buffXml.append(" PRM_CD='"+tmpFuelSale001VO.getSendXmlPrmCd()+"'");
							buffXml.append(" PRM_SEQ='"+tmpFuelSale001VO.getSendXmlPrmSeq()+"'");
							buffXml.append(" SDA_CD='"+tmpFuelSale001VO.getSendXmlSdaCd()+"'");
							buffXml.append(" SDS_ORG_DTL_NO='"+tmpFuelSale001VO.getSendXmlSdsOrgDtlNo()+"'");


							buffXml.append(" INS_DT='"+tmpFuelSale001VO.getSendXmlInsDtT()+"'");
							buffXml.append(" EMP_NO='"+tmpFuelSale001VO.getSendXmlEmpNoT()+"'");
							buffXml.append(" PREPAY_AMT='"+tmpFuelSale001VO.getSendXmlPrepayAmtT()+"'");
							buffXml.append(" COM_AMT='"+tmpFuelSale001VO.getSendXmlComAmtT()+"'");
							buffXml.append(" />");
							buffXml.append("</TRDTL>");
							buffXml.append("</EREST>");

									tmpFuelSale001VO.setSendXml(buffXml.toString());
									Element elementS = null;
									elementS = getAfcrRtnXmlInfo_euc_utf(tmpFuelSale001VO,url, buffXml.toString());
//									System.out.println("url=======>"+url);
//									System.out.println("SENDbuffXml========>"+buffXml.toString());
									String RcvXml = null;
									RcvXml = tmpFuelSale001VO.getRecvXml();
//									System.out.println("RcvXml=========>"+RcvXml);


									DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								    DocumentBuilder builder = factory.newDocumentBuilder();
		   							InputSource is = new InputSource(new StringReader(RcvXml)); //지금추가
								    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is); //지금추가
								    doc.getDocumentElement().normalize();
									NodeList headNodeList = doc.getChildNodes();

					 for(int i=0; i<headNodeList.getLength(); i++){
				      Node headNode = headNodeList.item(i);
					  //  System.out.println("Head Node Name = " + headNode.getNodeName());

					    //����� ��尡 ��� Ÿ���̶��
						if(headNode.getNodeType() == Node.ELEMENT_NODE){
						        Element element = (Element) headNode;
						        NodeList nodeList = element.getChildNodes();

								for(int k=0; k<nodeList.getLength(); k++){
									Node node = nodeList.item(k);

								     if(node.getNodeType() == Node.ELEMENT_NODE){
										 	 Element ele = (Element) node;
											 NodeList list = ele.getChildNodes();


								        //	 System.out.println("Head ele Name = " + ele.getNodeName());	//TXJM-FD
											 tmpFuelSale001VO.setRecvXmlRetcd(ele.getAttribute("RETCD"));
											 tmpFuelSale001VO.setRecvXmlSrid(ele.getAttribute("SRID"));

//								 System.out.println("RETCD======> : " + tmpFuelSale001VO.getRecvXmlRetcd());
//								 System.out.println("RETCD======> : " + tmpFuelSale001VO.getRecvXmlSrid());

										for(int j=0; j<list.getLength(); j++){
											Node n = list.item(j);

											if(n.getNodeType() == Node.ELEMENT_NODE){
											Element e = (Element) n;

											tmpFuelSale001VO.setRecvXmlRetcd(ele.getAttribute("RETCD"));
											tmpFuelSale001VO.setRecvXmlBillCd(e.getAttribute("BILL_NO"));
											tmpFuelSale001VO.setRecvXmlSaleDate(e.getAttribute("SALE_DATE"));
											tmpFuelSale001VO.setRecvXmlShopCd(e.getAttribute("SHOP_CD"));

//								System.out.println("RETCD========> : " + tmpFuelSale001VO.getRecvXmlRetcd());
//								System.out.println("BILL_NO========> : " + tmpFuelSale001VO.getRecvXmlBillCd());
//								System.out.println("SALE_DATE========> : " + tmpFuelSale001VO.getRecvXmlSaleDate());
//								System.out.println("SHOP_CD========> : " + tmpFuelSale001VO.getRecvXmlShopCd());
											}
										}
								    }
							}
				       }
					 }

						pEDMBAT0001Service.insertFuesSale001log(tmpFuelSale001VO);
						buffXml = null;
								} catch( Exception e ) {
									logger.error(e.getMessage());
								}

		    	}//if
    		} //for

    } //if
} //for

		}
    	catch(Exception e){
    		logger.error(e.getMessage());
    	}
//    	System.out.println("==========END OF SALE SEND TO RESTSTOP============");
    	//return "/edi/system/PEDMBAT0001";
    	return "system/PEDMBAT0001";



      }


	/**
     * URL 접속후 수신 결과 리턴한다.
	 */
	protected Element getAfcrRtnXmlInfo_euc_utf(FuelSale001VO tmpFuelSale001VO,URL url,String xmlData){
		Element root = null;

		try {
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true); 						//URL서버에 파일 Write 하기(post방식:true)
			conn.setDoInput(true);  						//URL서버에서 파일 받아오기
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type","text/xml; charset=utf-8");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(xmlData);
			bw.close();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

			String InputLine;
			StringBuffer buff = new StringBuffer();
			while (true)
			{
				InputLine = bufferedReader.readLine();
				if (InputLine != null) {
					buff.append(InputLine.trim());
				} else {
					break;
				}
			}
			bufferedReader.close();
		//	setLog("xml 연동 후   = "+buff.toString());
//			System.out.println("xml response   = "+buff.toString());
			String responseXml=buff.toString();

			tmpFuelSale001VO.setRecvXml(responseXml);


	        /*****************************************
				XML 데이타 파싱    80포트 utf-8 바이트
		    *****************************************/

			// XML Parsing 을 위한 형변환 하기(String -> InputStream)
//		    ByteArrayInputStream isXmlData 	= new ByteArrayInputStream(buff.toString().replaceAll("utf-8", "euc-kr").getBytes());
		    ByteArrayInputStream isXmlData 	= new ByteArrayInputStream(buff.toString().replaceAll("euc-kr", "utf-8").getBytes());

		    Document doc	= null;
		    // XML Parsing DocumentBuilder 생성
		    DocumentBuilderFactory factory	= DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder 		= factory.newDocumentBuilder();
			doc								= builder.parse(isXmlData);

			// 루트 엘리먼트 객체 얻기
			root  = doc.getDocumentElement();


		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return root;
	}


	/**
     * 해당 TagName의 값을 가져온다.
     * Root 하위 전체에서 검색한다.
     * @param root
     * @param tag_name
     * @return
     */
    public static String getTagValue(Element root, String tag_name)
    {
        String returnString = "";

        NodeList nodeList	= root.getElementsByTagName(tag_name);
        //NodeList nodeList	= root.getChildNodes(tag_name);

        for ( int i=0; i < nodeList.getLength(); i++ ) {
            Node nodeItem	= nodeList.item(i);

            if ( nodeItem != null ) {
                Node nodeChild	= nodeItem.getFirstChild();
                return nodeChild == null ? "" : nodeChild.getNodeValue().trim();
            }
        }
        return returnString;
    }



}
