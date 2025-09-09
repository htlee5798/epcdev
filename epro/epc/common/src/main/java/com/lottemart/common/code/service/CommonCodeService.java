package com.lottemart.common.code.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.code.dao.CommonCodeDao;
import com.lottemart.common.util.DataMap;

@Service("commonCodeService")
public class CommonCodeService {

	@Autowired
	private CommonCodeDao commonCodeDao;

	public List<DataMap> getCodeList(String majorCd) throws SQLException {
		return commonCodeDao.getCodeList(majorCd);
	}
	
	public List<DataMap> getCodeList(String majorCd, String[] minorCds) throws SQLException {
		return commonCodeDao.getCodeList(majorCd, minorCds);
	}

	public DataMap getCode(String majorCd, String minorCd) throws SQLException {
		return commonCodeDao.getCode(majorCd, minorCd);
	}

	public String selectAuthYn(DataMap paramMap) throws SQLException {
		return commonCodeDao.selectAuthYn(paramMap);
	}

	public DataMap getEcCode(DataMap paramMap) throws SQLException {
		return commonCodeDao.getEcCode(paramMap);
	}
	
	public List<DataMap> getEcCodeList(String ecMajorCd) throws SQLException {
		return commonCodeDao.getEcCodeList(ecMajorCd);
	}

}
