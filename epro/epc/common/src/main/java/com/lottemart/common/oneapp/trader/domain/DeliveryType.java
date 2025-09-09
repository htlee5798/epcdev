package com.lottemart.common.oneapp.trader.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author DELL
 *
 */
//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DeliveryType {

    PICK(1, "02", "01", "출고지"),              //출고지
    BASIS_RETURN(2, "01", "02", "회수지");      //반품지

    private int num;

    private String apiCode;

    private String code;

    private String codeName;

    private DeliveryType(int num, String apiCode, String code, String codeName) {
        this.num = num;
        this.apiCode = apiCode;
        this.code = code;
        this.codeName = codeName;
    }

    public int getNum() {
        return num;
    }

    public String getApiCode() {
        return apiCode;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    @JsonCreator
    public static DeliveryType getEnumFromApiValue(String value) {
        for(DeliveryType deliveryType : values()) {
            if(deliveryType.getApiCode().equals(value)) {
                return deliveryType;
            }
        }
        throw new IllegalArgumentException();
    }

    public static DeliveryType getEnumFromValue(String value) {
        for(DeliveryType deliveryType : values()) {
            if(deliveryType.getCode().equals(value)) {
                return deliveryType;
            }
        }
        throw new IllegalArgumentException();
    }

    public static DeliveryType getEnumFromNum(int value) {
        for(DeliveryType deliveryType : values()) {
            if(deliveryType.getNum() == value) {
                return deliveryType;
            }
        }
        throw new IllegalArgumentException();
    }

}