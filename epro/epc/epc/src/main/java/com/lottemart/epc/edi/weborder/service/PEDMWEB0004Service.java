package com.lottemart.epc.edi.weborder.service;

import java.util.Map;


import com.lottemart.epc.edi.weborder.model.SearchWebOrder;


public interface PEDMWEB0004Service {


	Map<String,Object> selectOrdTotList(SearchWebOrder vo);
	
}
