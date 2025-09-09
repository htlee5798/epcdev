package com.lottemart.epc.edi.batch.controller;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URLConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.comm.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import com.lottemart.epc.edi.batch.model.FuelCalc001VO;
import com.lottemart.epc.edi.batch.service.PEDMBAT0002Service;
import com.lottemart.epc.util.Utils;



import org.xml.sax.InputSource;

@Controller
public class PEDMBAT0002Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PEDMBAT0002Controller.class);


////////마장점 주유소 정산 1건발생 전송


	//임시보관함용 서비스
	private PEDMBAT0002Service pEDMBAT0002Service;

	@Autowired
	public void setpEDMBAT0002Service(PEDMBAT0002Service pEDMBAT0002Service) {
		this.pEDMBAT0002Service = pEDMBAT0002Service;
	}


	@RequestMapping(value="/edi/batch/fuelsale0002.do")
   // public void orderCompleteInquiry(String order_id, String order_item_seq, String status_cd) throws Exception{
	//public void orderCompleteInquiry(@RequestParam Map<String,Object> map,test3model tmptest3model ,SearchProduct searchParam, HttpServletRequest request, Model model) {
	public String orderCompleteInquiry(@RequestParam Map<String,Object> map
														, HttpServletRequest request
														, Model model) {

//		System.out.println("==========START OF AVGSALE SEND TO RESTSTOP============");

		StringBuffer buffXml = null;
		//buffXml 		= null;
		//buffXml 		= new StringBuffer(); 			//buffXml 객체 초기화
		String dateYYYYMMDD= null;
		String sDateYYYYMMDD = null;
		String eDateYYYYMMDD = null;




//		sDateYYYYMMDD="";
//		eDateYYYYMMDD="";
		sDateYYYYMMDD="";
		eDateYYYYMMDD="";


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

    		List<DataMap> dateList = pEDMBAT0002Service.selectFuesSale002SendDistinctDate(map);

    		//URL url = new URL("http://skhiway.com/SvrApp/SL000.java"); //
    		URL url = new URL("http://skhiway.com/SvrApp/PS000.java"); //



    		if(dateList != null && dateList.size() > 0 ){
				for(int y=0;y<dateList.size();y++){

					buffXml 		= null;
					buffXml 		= new StringBuffer(); 			//buffXml 객체 초기화

					map.put("dateYYYYMMDD", (dateList.get(y)).getString("DY"));
					//map.put("billnumber", (dateList.get(y)).getString("DY"));
					model.addAttribute("paramMap",map);

					FuelCalc001VO tmpFuelCalc001VO  = pEDMBAT0002Service.selectFuesSale002Send(map);

    			try {
					//////////////시작
					buffXml.append("<?xml version='1.0'?>");
					buffXml.append("<EREST>");
					buffXml.append("<TXJM-FD SRID='"+tmpFuelCalc001VO.getSendXmlSrid()+"'");
					 buffXml.append(" RETCD='"+tmpFuelCalc001VO.getSendXmlRetcd()+"'");
					 buffXml.append("/>");
//					 System.out.println("getSendXmlSrid"+tmpFuelCalc001VO.getSendXmlSrid());


					buffXml.append("<DATA-HD ");
					buffXml.append(" SHOP_CD='"+tmpFuelCalc001VO.getSendXmlShopCd()+"'");
					buffXml.append(" SALE_DATE='"+tmpFuelCalc001VO.getSendXmlSaleDate()+"'");
					buffXml.append(" POS_NO='"+tmpFuelCalc001VO.getSendXmlPosNo()+"'");
					buffXml.append(" REGI_SEQ='"+tmpFuelCalc001VO.getSendXmlRegiSeq()+"'");
					buffXml.append(" INDEX_NO='"+tmpFuelCalc001VO.getSendXmlIndexNo()+"'"); //
					buffXml.append(" DT_CNT='"+tmpFuelCalc001VO.getSendXmlDtCnt()+"'"); //
					buffXml.append(">");

					buffXml.append("<DATA-DT");
					buffXml.append(" SHOP_CD='"+tmpFuelCalc001VO.getSendXmlShopCd1()+"'");
					buffXml.append(" SALE_DATE='"+tmpFuelCalc001VO.getSendXmlSaleDate1()+"'");
					buffXml.append(" POS_NO='"+tmpFuelCalc001VO.getSendXmlPosNo1()+"'");
					buffXml.append(" REGI_SEQ='"+tmpFuelCalc001VO.getSendXmlRegiSeq1()+"'");
					buffXml.append(" EMP_NO='"+tmpFuelCalc001VO.getSendXmlEmpNo()+"'");
					buffXml.append(" CLOSE_FG='"+tmpFuelCalc001VO.getSendXmlCloseFg()+"'");
					buffXml.append(" OPEN_DT='"+tmpFuelCalc001VO.getSendXmlOpenDt()+"'");
					buffXml.append(" CLOSE_DT='"+tmpFuelCalc001VO.getSendXmlCloseDt()+"'");
					buffXml.append(" TOT_BILL_CNT='"+tmpFuelCalc001VO.getSendXmlTotBillCnt()+"'");
					buffXml.append(" TOT_SALE_AMT='"+tmpFuelCalc001VO.getSendXmlTotSaleAmt()+"'");

					buffXml.append(" TOT_DC_AMT='"+tmpFuelCalc001VO.getSendXmlTotDcAmt()+"'");
					buffXml.append(" SVC_TIP_AMT='"+tmpFuelCalc001VO.getSendXmlSvcTipAmt()+"'");
					buffXml.append(" TOT_ETC_AMT='"+tmpFuelCalc001VO.getSendXmlTotEtcAmt()+"'");

					buffXml.append(" DCM_SALE_AMT='"+tmpFuelCalc001VO.getSendXmlDcmSaleAmt()+"'");
					buffXml.append(" VAT_SALE_AMT='"+tmpFuelCalc001VO.getSendXmlVatSaleAmt()+"'");
					buffXml.append(" VAT_AMT='"+tmpFuelCalc001VO.getSendXmlVatAmt()+"'");
					buffXml.append(" NO_VAT_SALE_AMT='"+tmpFuelCalc001VO.getSendXmlNoVatSaleAmt()+"'");
					buffXml.append(" NO_TAX_SALE_AMT='"+tmpFuelCalc001VO.getSendXmlNoTaxSaleAmt()+"'");
					buffXml.append(" RET_BILL_CNT='"+tmpFuelCalc001VO.getSendXmlRetBillCnt()+"'");
					buffXml.append(" RET_BILL_AMT='"+tmpFuelCalc001VO.getSendXmlRetBillAmt()+"'");
					buffXml.append(" VISIT_CST_CNT='"+tmpFuelCalc001VO.getSendXmlVisitCstCnt()+"'");
					buffXml.append(" POS_READY_AMT='"+tmpFuelCalc001VO.getSendXmlPosReadyAmt()+"'");
					buffXml.append(" POS_CSH_IN_AMT='"+tmpFuelCalc001VO.getSendXmlPosCshInAmt()+"'");
					buffXml.append(" POS_CSH_OUT_AMT='"+tmpFuelCalc001VO.getSendXmlPosCshOutAmt()+"'");
					buffXml.append(" WEA_IN_CSH_AMT='"+tmpFuelCalc001VO.getSendXmlWeaInCshAmt()+"'");
					buffXml.append(" WEA_IN_CRD_AMT='"+tmpFuelCalc001VO.getSendXmlWeaInCrdAmt()+"'");
					buffXml.append(" TK_GFT_SALE_CSH_AMT='"+tmpFuelCalc001VO.getSendXmlTkGftSaleCshAmt()+"'");
					buffXml.append(" TK_GFT_SALE_CRD_AMT='"+tmpFuelCalc001VO.getSendXmlTkGftSaleCrdAmt()+"'");
					buffXml.append(" TK_FOD_SALE_CSH_AMT='"+tmpFuelCalc001VO.getSendXmlTkFodSaleCshAmt()+"'");
					buffXml.append(" TK_FOD_SALE_CRD_AMT='"+tmpFuelCalc001VO.getSendXmlTkFodSaleCrdAmt()+"'");
					buffXml.append(" CASH_CNT='"+tmpFuelCalc001VO.getSendXmlCashCnt()+"'");
					buffXml.append(" CASH_AMT='"+tmpFuelCalc001VO.getSendXmlCashAmt()+"'");
					buffXml.append(" CASH_BILL_CNT='"+tmpFuelCalc001VO.getSendXmlCashBillCnt()+"'");
					buffXml.append(" CASH_BILL_AMT='"+tmpFuelCalc001VO.getSendXmlCashBillAmt()+"'");
					buffXml.append(" CRD_CARD_CNT='"+tmpFuelCalc001VO.getSendXmlCrdCardCnt()+"'");
					buffXml.append(" CRD_CARD_AMT='"+tmpFuelCalc001VO.getSendXmlCrdCardAmt()+"'");
					buffXml.append(" WES_CNT='"+tmpFuelCalc001VO.getSendXmlWesCnt()+"'");
					buffXml.append(" WES_AMT='"+tmpFuelCalc001VO.getSendXmlWesAmt()+"'");
					buffXml.append(" TK_GFT_CNT='"+tmpFuelCalc001VO.getSendXmlTkGftCnt()+"'");
					buffXml.append(" TK_GFT_AMT='"+tmpFuelCalc001VO.getSendXmlTkGftAmt()+"'");
					buffXml.append(" TK_FOD_CNT='"+tmpFuelCalc001VO.getSendXmlTkFodCnt()+"'");
					buffXml.append(" TK_FOD_AMT='"+tmpFuelCalc001VO.getSendXmlTkFodAmt()+"'");
					buffXml.append(" CST_POINT_CNT='"+tmpFuelCalc001VO.getSendXmlCstPointCnt()+"'");
					buffXml.append(" CST_POINT_AMT='"+tmpFuelCalc001VO.getSendXmlCstPointAmt()+"'");
					buffXml.append(" JCD_CARD_CNT='"+tmpFuelCalc001VO.getSendXmlJcdCardCnt()+"'");
					buffXml.append(" JCD_CARD_AMT='"+tmpFuelCalc001VO.getSendXmlJcdCardAmt()+"'");
					buffXml.append(" RFC_CNT='"+tmpFuelCalc001VO.getSendXmlRfcCnt()+"'");
					buffXml.append(" RFC_AMT='"+tmpFuelCalc001VO.getSendXmlRfcAmt()+"'");
					buffXml.append(" DC_GEN_CNT='"+tmpFuelCalc001VO.getSendXmlDcGenCnt()+"'");
					buffXml.append(" DC_GEN_AMT='"+tmpFuelCalc001VO.getSendXmlDcGenAmt()+"'");
					buffXml.append(" DC_SVC_CNT='"+tmpFuelCalc001VO.getSendXmlDcSvcCnt()+"'");
					buffXml.append(" DC_SVC_AMT='"+tmpFuelCalc001VO.getSendXmlDcSvcAmt()+"'");
					buffXml.append(" DC_JCD_CNT='"+tmpFuelCalc001VO.getSendXmlDcJcdCnt()+"'");
					buffXml.append(" DC_JCD_AMT='"+tmpFuelCalc001VO.getSendXmlDcJcdAmt()+"'");
					buffXml.append(" DC_CPN_CNT='"+tmpFuelCalc001VO.getSendXmlDcCpnCnt()+"'");
					buffXml.append(" DC_CPN_AMT='"+tmpFuelCalc001VO.getSendXmlDcCpnAmt()+"'");
					buffXml.append(" DC_CST_CNT='"+tmpFuelCalc001VO.getSendXmlDcCstCnt()+"'");
					buffXml.append(" DC_CST_AMT='"+tmpFuelCalc001VO.getSendXmlDcCstAmt()+"'");
					buffXml.append(" DC_TFD_CNT='"+tmpFuelCalc001VO.getSendXmlDcTfdCnt()+"'");
					buffXml.append(" DC_TFD_AMT='"+tmpFuelCalc001VO.getSendXmlDcTfdAmt()+"'");
					buffXml.append(" DC_PRM_CNT='"+tmpFuelCalc001VO.getSendXmlDcPrmCnt()+"'");
					buffXml.append(" DC_PRM_AMT='"+tmpFuelCalc001VO.getSendXmlDcPrmAmt()+"'");
					buffXml.append(" DC_CRD_CNT='"+tmpFuelCalc001VO.getSendXmlDcCrdCnt()+"'");
					buffXml.append(" DC_CRD_AMT='"+tmpFuelCalc001VO.getSendXmlDcCrdAmt()+"'");
					buffXml.append(" DC_PACK_CNT='"+tmpFuelCalc001VO.getSendXmlDcPackCnt()+"'");
					buffXml.append(" DC_PACK_AMT='"+tmpFuelCalc001VO.getSendXmlDcPackAmt()+"'");
					buffXml.append(" REM_CHECK_CNT='"+tmpFuelCalc001VO.getSendXmlRemCheckCnt()+"'");
					buffXml.append(" REM_CHECK_AMT='"+tmpFuelCalc001VO.getSendXmlRemCheckAmt()+"'");
					buffXml.append(" REM_W100000_CNT='"+tmpFuelCalc001VO.getSendXmlRemW100000Cnt()+"'");
					buffXml.append(" REM_W50000_CNT='"+tmpFuelCalc001VO.getSendXmlRemW50000Cnt()+"'");
					buffXml.append(" REM_W10000_CNT='"+tmpFuelCalc001VO.getSendXmlRemW10000Cnt()+"'");
					buffXml.append(" REM_W5000_CNT='"+tmpFuelCalc001VO.getSendXmlRemW5000Cnt()+"'");
					buffXml.append(" REM_W1000_CNT='"+tmpFuelCalc001VO.getSendXmlRemW1000Cnt()+"'");
					buffXml.append(" REM_W500_CNT='"+tmpFuelCalc001VO.getSendXmlRemW500Cnt()+"'");
					buffXml.append(" REM_W100_CNT='"+tmpFuelCalc001VO.getSendXmlRemW100Cnt()+"'");
					buffXml.append(" REM_W50_CNT='"+tmpFuelCalc001VO.getSendXmlRemW50Cnt()+"'");
					buffXml.append(" REM_W10_CNT='"+tmpFuelCalc001VO.getSendXmlRemW10Cnt()+"'");
					buffXml.append(" REM_CASH_AMT='"+tmpFuelCalc001VO.getSendXmlRemCashAmt()+"'");
					buffXml.append(" REM_TK_GFT_CNT='"+tmpFuelCalc001VO.getSendXmlRemTkGftCnt()+"'");
					buffXml.append(" REM_TK_GFT_AMT='"+tmpFuelCalc001VO.getSendXmlRemTkGftAmt()+"'");
					buffXml.append(" REM_TK_FOD_CNT='"+tmpFuelCalc001VO.getSendXmlRemTkFodCnt()+"'");
					buffXml.append(" REM_TK_FOD_AMT='"+tmpFuelCalc001VO.getSendXmlRemTkFodAmt()+"'");
					buffXml.append(" ETC_TK_FOD_AMT='"+tmpFuelCalc001VO.getSendXmlEtcTkFodAmt()+"'");
					buffXml.append(" LOSS_CASH_AMT='"+tmpFuelCalc001VO.getSendXmlLossCashAmt()+"'");
					buffXml.append(" LOSS_TK_GFT_AMT='"+tmpFuelCalc001VO.getSendXmlLossTkGftAmt()+"'");
					buffXml.append(" LOSS_TK_FOD_AMT='"+tmpFuelCalc001VO.getSendXmlLossTkFodAmt()+"'");
					buffXml.append(" REPAY_CASH_CNT='"+tmpFuelCalc001VO.getSendXmlRepayCashCnt()+"'");
					buffXml.append(" REPAY_CASH_AMT='"+tmpFuelCalc001VO.getSendXmlRepayCashAmt()+"'");
					buffXml.append(" REPAY_TK_GFT_CNT='"+tmpFuelCalc001VO.getSendXmlRepayTkGftCnt()+"'");
					buffXml.append(" REPAY_TK_GFT_AMT='"+tmpFuelCalc001VO.getSendXmlRepayTkGftAmt()+"'");
					buffXml.append(" COM_CNT='"+tmpFuelCalc001VO.getSendXmlComCnt()+"'");
					buffXml.append(" COM_AMT='"+tmpFuelCalc001VO.getSendXmlComAmt()+"'");
					buffXml.append(" PREPAY_CNT='"+tmpFuelCalc001VO.getSendXmlPrepayCnt()+"'");
					buffXml.append(" PREPAY_AMT='"+tmpFuelCalc001VO.getSendXmlPrepayAmt()+"'");
					buffXml.append(" INS_DT='"+tmpFuelCalc001VO.getSendXmlInsDt()+"'");
					buffXml.append(" BILL_NO_END='"+tmpFuelCalc001VO.getSendXmlBillNoEnd()+"'");
					buffXml.append("/>");
					buffXml.append("</DATA-HD>");
					buffXml.append("</EREST>");



							tmpFuelCalc001VO.setSendXml(buffXml.toString());

							Element elementS = null;
							elementS = getAfcrRtnXmlInfo_euc_utf(tmpFuelCalc001VO,url, buffXml.toString());

//							System.out.println("url=======>"+url);
//							System.out.println("buffXml========>"+buffXml.toString());

//							System.out.println("getRecvXml =>"+tmpFuelCalc001VO.getRecvXml());

							String RcvXml = null;
							RcvXml = tmpFuelCalc001VO.getRecvXml();
//							System.out.println("RcvXml=========>"+RcvXml);


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
									 tmpFuelCalc001VO.setRecvXmlRetcd(ele.getAttribute("RETCD"));
									 tmpFuelCalc001VO.setRecvXmlSrid(ele.getAttribute("SRID"));

//									 System.out.println("RETCD======> : " + tmpFuelCalc001VO.getRecvXmlRetcd());
//							     	 System.out.println("RETCD======> : " + tmpFuelCalc001VO.getRecvXmlSrid());

								for(int j=0; j<list.getLength(); j++){
									Node n = list.item(j);
									//	 System.out.println("Head N Name = " + n.getNodeName());	// DATA-HD

									if(n.getNodeType() == Node.ELEMENT_NODE){
									Element e = (Element) n;

									tmpFuelCalc001VO.setRecvXmlRetcd(ele.getAttribute("RETCD"));
									tmpFuelCalc001VO.setRecvXmlBillCd(e.getAttribute("BILL_NO"));
									tmpFuelCalc001VO.setRecvXmlSaleDate(e.getAttribute("SALE_DATE"));
									tmpFuelCalc001VO.setRecvXmlShopCd(e.getAttribute("SHOP_CD"));
									tmpFuelCalc001VO.setRecvXmlIndexNo(e.getAttribute("INDEX_NO"));

//									System.out.println("RETCD========> : " + tmpFuelCalc001VO.getRecvXmlRetcd());
//									System.out.println("BILL_NO========> : " + tmpFuelCalc001VO.getRecvXmlBillCd());
//									System.out.println("SALE_DATE========> : " + tmpFuelCalc001VO.getRecvXmlSaleDate());
//									System.out.println("SHOP_CD========> : " + tmpFuelCalc001VO.getRecvXmlShopCd());
//									System.out.println("INDEX_NO========> : " + tmpFuelCalc001VO.getRecvXmlIndexNo());
									}
//									pEDMBAT0002Service.insertFuesSale002log(tmpFuelCalc001VO);
//					    			buffXml = null;
								}
						    }
					}
		       }
			 }

		pEDMBAT0002Service.insertFuesSale002log(tmpFuelCalc001VO);
		buffXml = null;

						} catch( Exception e ) {
							logger.error(e.getMessage());
						}

				}  //if
			}  //for
    	}
    	catch(Exception e){
    		logger.error(e.getMessage());
    	}
    	//return "/edi/system/PEDMBAT0002";
    	return "system/PEDMBAT0002";


    }


	/**
     * URL 접속후 수신 결과 리턴한다.
	 */
	protected Element getAfcrRtnXmlInfo_euc_utf(FuelCalc001VO tmpFuelSale001VO,URL url,String xmlData){
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
