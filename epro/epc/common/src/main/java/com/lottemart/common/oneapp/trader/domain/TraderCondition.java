package com.lottemart.common.oneapp.trader.domain;

import java.util.List;

public class TraderCondition {

	private TraderType traderType;
	
	private String traderCd;
	
	private List<String> addrSeqs;
	
	private List<String> removeInfos;
	
	public TraderType getTraderType() {
		return traderType;
	}

	public void setTraderType(TraderType traderType) {
		this.traderType = traderType;
	}

	public String getTraderCd() {
		return traderCd;
	}

	public void setTraderCd(String traderCd) {
		this.traderCd = traderCd;
	}

	public List<String> getAddrSeqs() {
		return addrSeqs;
	}

	public void setAddrSeqs(List<String> addrSeqs) {
		this.addrSeqs = addrSeqs;
	}

	public List<String> getRemoveInfos() {
		return removeInfos;
	}

	public void setRemoveInfos(List<String> removeInfos) {
		this.removeInfos = removeInfos;
	}

}
