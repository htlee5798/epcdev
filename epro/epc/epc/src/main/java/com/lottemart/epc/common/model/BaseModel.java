package com.lottemart.epc.common.model;


import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.LogFactory;

public class BaseModel implements Serializable 
{
	private static final long serialVersionUID = 2852157186337943649L;
	/**
     * 각 속성을 보기쉽게 찍어줌..
     */
    @Override
    public String toString() {
        try {
            return BeanUtils.describe(this).toString();
        } catch (Throwable e) {
            LogFactory.getLog(this.getClass()).error("Object에서 String으로 문자열 변환 에러", e);
        }
        return super.toString();
    }

}
