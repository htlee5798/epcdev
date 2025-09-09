/**

 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.common.util;

import java.io.File;

import lcn.module.common.namo.NamoMime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : MimeUtil
 * @Description : 에디터 NamoMime 객체에 대한 유틸리티 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 6:06:38 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class MimeUtil {

	/**
	 * Desc : Mime 객체에서 파일을 추출하여 저장하는 메소드
	 * @Method Name : save
	 * @param contents
	 * @param saveUrl
	 * @param savePath
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public static NamoMime save(String contents, String saveUrl, String savePath) throws Exception {

		makeDirectory(savePath);

		// MIME 디코딩 하기
		NamoMime mime = new NamoMime();
		mime.setSaveURL(saveUrl);
		mime.setSavePath(savePath);
		mime.decode(contents);
		mime.saveFile();

		return mime;
	}

	/**
	 * Desc : 파일을 저장하기 위한 디렉토리 생성 메소드
	 * @Method Name : makeDirectory
	 * @param savePath
	 * @param
	 * @return
	 * @exception Exception
	 */
	private static void makeDirectory(String savePath) {

		String path[] = savePath.split("/");
		int length = path.length;
		String appendDir = "";

		// 해당 디렉토리 없을 경우 생성 (상위 디렉토리 미존재시 대비)
		for(int i = 0; i < length; i++) {
			if(!"".equals(path[i].trim())) {
				if("c:".equals(path[i].toLowerCase())) {
					appendDir = path[i];
				} else {
					appendDir = appendDir + "/" + path[i];
				}
				File dir = new File(appendDir);
				if(!dir.exists()) {
					synchronized(dir) {
						dir.mkdir();
					}
				}
			}
		}
	}

	/**
	 * Desc : 문자 치환 메소드
	 * @Method Name : replaceAll
	 * @param str
	 * @param src
	 * @param des
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public static String replaceAll(String str, String src, String des) throws Exception {
		StringBuffer sb = new StringBuffer(str.length());
		int startIdx = 0;
		int oldIdx = 0;
		while(true) {
			startIdx = str.indexOf(src, startIdx);
			if(startIdx == -1) {
				sb.append(str.substring(oldIdx));
				break;
			}

			sb.append(str.substring(oldIdx, startIdx));
			sb.append(des);

			startIdx += src.length();
			oldIdx = startIdx;
		}

		return sb.toString();
	}

    /**
     * Desc : 스크립트 에러 방지를 위한 HTML 문자 변환 메소드
     * @Method Name : getHTMLCode
     * @param text
     * @return
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    public static String getHTMLCode(String text) throws Exception {

        if( text == null || text.equals("") )
            return "";

        StringBuffer sb = new StringBuffer(text);
        char ch;

        // 아래 for내 조건문은 sb의 길이가 변경됨 (sb.length() 밖으로 빼서 변수 선언 금지)
        for ( int i = 0; i < sb.length(); i++ ) {
            ch = sb.charAt(i);
            if ( ch == '<' ) {
                sb.replace(i, i+1, "&lt;");
                i += 3;
            } else if ( ch == '>' ) {
                sb.replace(i, i+1, "&gt;");
                i += 3;
            } else if ( ch == '&' ) {
                sb.replace(i, i+1, "&amp;");
                i += 4;
            } else if ( ch == '\'' ) {
                sb.replace(i, i+1, "&#39;");
                i += 4;
            } else if ( ch == '"' ) {
                sb.replace(i, i+1, "&quot;");
                i += 5;
            }
        }

        return replaceAll(sb.toString(), "&nbsp;", "&amp;nbsp;");
    }
}
