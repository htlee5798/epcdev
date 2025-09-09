package com.lottemart.common.oneapp.trader.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author name
 * @Description :
 *
 * @param <T>
 */
public class TraderAPIBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("returnCode")
    private String returnCode;      // 응답 코드

    @JsonProperty("message")
    private String message;         // 응답 메세지

    @JsonProperty("subMessages")
    private List<T> subMessages;    // 응답 상세 하위 메세지 배열

    @JsonProperty("dataCount")
    private int dataCount;          // 응답 데이터 객체 크기

    @JsonProperty("data")
    private List<T> data;           // 응답 데이터

    private T t;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getSubMessages() {
        return subMessages;
    }

    public void setSubMessages(List<T> subMessages) {
        this.subMessages = subMessages;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


}
