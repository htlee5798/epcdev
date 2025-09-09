package com.lottemart.epc.delivery.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0012Dao;
import com.lottemart.epc.delivery.model.PSCMDLV0012VO;
import com.lottemart.epc.delivery.service.PSCMDLV0012Service;

import lcn.module.framework.idgen.IdGnrService;


@Service("PSCMDLV0012Service")
public class PSCMDLV0012ServiceImpl  implements PSCMDLV0012Service {

	@Autowired
	private PSCMDLV0012Dao pscmdlv0012Dao;

	@Override
	public List<DataMap> selectPartnerNoFirmsOrderList(DataMap param) throws Exception {
		return pscmdlv0012Dao.selectPartnerNoFirmsOrderList(param);
	}

}