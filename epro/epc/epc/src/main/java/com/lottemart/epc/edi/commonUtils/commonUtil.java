package com.lottemart.epc.edi.commonUtils;

import java.io.BufferedWriter;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lcn.module.common.util.DateUtil;

public class commonUtil {

	private static final Logger logger = LoggerFactory.getLogger(commonUtil.class);

	public static void createTextFile(HttpServletRequest request, HttpServletResponse response, StringBuffer sb)		throws Exception {

		response.setContentType("application/x-msdownload;charset=euc-kr");
		String fileName = "default.txt";

		try{
			if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
			       response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, "UTF-8") + ";");
			    } else if (request.getHeader("User-Agent").indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
			       response.setHeader("Content-Disposition", "attachment; filename="+ java.net.URLEncoder.encode(fileName, "UTF-8") + ";");
			    } else { // 모질라나 오페라
			       response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("UTF-8"), "latin1") + ";");
			    }

				BufferedWriter bw = new BufferedWriter(response.getWriter());
				bw.write(sb.toString());
				bw.close();

		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static String firstDate(String date){

		String firstDate="";
		String[] tmp = date.split("-");
		firstDate = tmp[0]+"-"+tmp[1]+"-01";

		return firstDate;
	}

	public static String nowDateBack(String date){
		String tmpDate = date;
		String nowDateBack="";

		long chStart = 0;
	    DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
	    if(tmpDate != ""){
	    	tmpDate = tmpDate.replaceAll("-","");        		 //사이사이의 -를 없애고 다 붙인다
	      try {
	        chStart = df.parse(tmpDate).getTime();           //스트링형 date를 long형의 함수로 컨버트하고
	        chStart -= 86400000;    						//24*60*60*1000 하루치의 숫자를 빼준다

	        Date aa = new Date(chStart);    		 	    //이것을 다시 날짜형태로 바꿔주고

	        tmpDate = df.format(aa);                 		    //바꿔준 날짜를 yyyyMMdd형으로 바꾼후

	                                                        //스트링으로 다시 형변환을해서 date에 대입
	      } catch (ParseException e) {
	    	  logger.error(e.getMessage());
	      }
	     }

	    nowDateBack=tmpDate.substring(0,4)+"-"+tmpDate.substring(4,6)+"-"+tmpDate.substring(6,8);

	    return nowDateBack;
	}


}
