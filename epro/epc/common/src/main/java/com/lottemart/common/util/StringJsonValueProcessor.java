package com.lottemart.common.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class StringJsonValueProcessor implements JsonValueProcessor {
       public StringJsonValueProcessor() {

       }

       @Override
       public Object processArrayValue(Object param,JsonConfig config) {
             return param == null? "":param;
       }

      @Override
       public Object processObjectValue(String pString,Object param, JsonConfig config) {
             return processArrayValue(param, config);
       }



}