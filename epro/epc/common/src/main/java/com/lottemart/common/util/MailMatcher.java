package com.lottemart.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailMatcher {

	private static final Map<String, String> solution = new HashMap<String, String>();

	public void addPattern(String pattern, String replacement) {
		solution.put(pattern, replacement);
	}
	
	public void addPattern(Map<String, String> pattern_replacement) {
		solution.putAll(pattern_replacement);
	}

	public String getBoundBody(String chunk) {
		String temp = chunk;
		for (Iterator<String> it = solution.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Pattern p = Pattern.compile(key);
			Matcher m = p.matcher(temp);

			if (m.find()) {
				String replacement = (String) solution.get(key);
				temp = m.replaceAll(replacement);
			}
		}
		return temp;
	}

	public String getMailBody(String htmlPath) throws IOException {
		return getMailBody(new File(htmlPath));
	}
	
	public String getMailBody(File templateFile) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(templateFile), "utf-8"));
		while(true) {
			line = br.readLine();
			if(line != null) {
				sb.append(line);
				sb.append("\n");				
			} else {
				break;
			}
		}
		br.close();
		return sb.toString();
	}
}
