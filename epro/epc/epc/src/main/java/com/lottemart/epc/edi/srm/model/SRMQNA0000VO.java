package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMQNA0000VO implements Serializable  {

    String foodYnFg; // 식품,비식품 구분자
    String martSuperFg; // 마트,슈퍼 구분자
    String AreaCd; // 영역 코드 구분
    String Code;
    String Name;

    public String getFoodYnFg() {
        return foodYnFg;
    }

    public String getMartSuperFg() {
        return martSuperFg;
    }

    public String getAreaCd() {
        return AreaCd;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }

    public void setFoodYnFg(String foodYnFg) {
        this.foodYnFg = foodYnFg;
    }

    public void setMartSuperFg(String martSuperFg) {
        this.martSuperFg = martSuperFg;
    }

    public void setAreaCd(String areaCd) {
        AreaCd = areaCd;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setName(String name) {
        Name = name;
    }
}
