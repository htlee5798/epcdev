package com.lottemart.epc.edi.consult.service;
import java.util.List;

import com.lottemart.epc.edi.consult.model.NEDMCST0230VO;

public interface NEDMCST0230Service {
	public List<NEDMCST0230VO> asMainSelect(NEDMCST0230VO nEDMCST0230VO ) throws Exception;
	public int asMainSelectCount(NEDMCST0230VO nEDMCST0230VO ) throws Exception;
	public int asMainUpdate(NEDMCST0230VO nEDMCST0230VO ) throws Exception;
}
