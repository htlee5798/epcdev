package com.lottemart.epc.delivery.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0007Dao;
import com.lottemart.epc.delivery.model.PSCMDLV0007VO;
import com.lottemart.epc.delivery.service.PSCMDLV0007Service;


@Service("PSCMDLV0007Service")
public class PSCMDLV0007ServiceImpl  implements PSCMDLV0007Service {
	@Autowired
	private PSCMDLV0007Dao pscmdlv0007Dao;

	public List<DataMap> getTetCodeList(PSCMDLV0007VO vo) throws Exception {
		return pscmdlv0007Dao.getTetCodeList(vo);
	}	
	
	public DataMap selectPartherReturnStatusSum(PSCMDLV0007VO vo) throws Exception {
		return pscmdlv0007Dao.selectPartherReturnStatusSum(vo);
	}		
	
	
	public List<DataMap> selectPartherReturnStatusList(PSCMDLV0007VO vo) throws Exception {
		return pscmdlv0007Dao.selectPartherReturnStatusList(vo);
	}

	
}