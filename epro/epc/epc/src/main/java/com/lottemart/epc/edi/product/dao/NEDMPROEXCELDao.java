package com.lottemart.epc.edi.product.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.ExcelTempUploadVO;

@Component("nedmproExcelDao")
public class NEDMPROEXCELDao extends SqlMapClientDaoSupport {

	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	public void deleteExcelTemp(HashMap<String, String> map) throws Exception {
	    getSqlMapClientTemplate().delete("NEDMPROEXCEL.deleteExcelTemp", map);
	}
	
	public ExcelTempUploadVO selectExcel500Validation (ExcelTempUploadVO vo) throws Exception {
		return (ExcelTempUploadVO) getSqlMapClientTemplate().queryForObject("NEDMPROEXCEL.selectExcel500Validation", vo);
	}
	
	public void insertExcelTemp(HashMap<String, Object> map) throws Exception{
		getSqlMapClientTemplate().insert("NEDMPROEXCEL.insertExcelTemp", map);
	}
	
	public List<HashMap<String, Object>> selectExcelTempUploadList(HashMap<String, String> excelMap) throws Exception{
		return getSqlMapClientTemplate().queryForList("NEDMPROEXCEL.selectExcelTempUploadList", excelMap);
	}
	
	public List<HashMap<String, Object>> selectExcelTempUploadProdChgPrcList(Map<String, Object> excelObjMap) throws Exception{
		return getSqlMapClientTemplate().queryForList("NEDMPROEXCEL.selectExcelTempUploadProdChgPrcList", excelObjMap);
	}

	public String selectExcelOrdCostInfo(HashMap<String, String> excelMap) throws Exception{
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPROEXCEL.selectExcelOrdCostInfo", excelMap);
	}

	public List<HashMap<String, Object>> selectExcelTempUploadProdChk(HashMap<String, String> excelMap) throws Exception{
		return getSqlMapClientTemplate().queryForList("NEDMPROEXCEL.selectExcelTempUploadProdChk", excelMap);
	}
	
	public List<HashMap<String, Object>> selectExcelTempUploadRtnChk(HashMap<String, String> excelMap) throws Exception{
		return getSqlMapClientTemplate().queryForList("NEDMPROEXCEL.selectExcelTempUploadRtnChk", excelMap);
	}
	
	
}
