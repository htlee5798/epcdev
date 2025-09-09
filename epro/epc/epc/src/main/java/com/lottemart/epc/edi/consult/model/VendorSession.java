package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;

public class VendorSession implements Serializable {

	private Vendor vendor;

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
	public VendorSession() {}
	
	public VendorSession(Vendor vendor){
		this.vendor = vendor;
	}
}
