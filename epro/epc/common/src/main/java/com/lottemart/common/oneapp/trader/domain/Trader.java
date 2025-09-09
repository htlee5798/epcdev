package com.lottemart.common.oneapp.trader.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lottemart.common.util.RestConst;

/**
 * @author DELL
 *
 */
public class Trader {

    @JsonProperty("afflCd")
    private String affiliateCd = RestConst.AFFILIATE_CD;                // 계열사코드

    @JsonProperty("afflTrCd")
    private String traderCd = RestConst.AFFILIATE_TRADE_NO;            // 상위거래처번호

    private boolean isNew = false;                                    // 신규등록여부

    public String getAffiliateCd() {
        return affiliateCd;
    }

    public void setAffiliateCd(String affiliateCd) {
        this.affiliateCd = affiliateCd;
    }

    public String getTraderCd() {
        return traderCd;
    }

    public void setTraderCd(String traderCd) {
        this.traderCd = traderCd;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    };                   // 상위거래처코드



}
