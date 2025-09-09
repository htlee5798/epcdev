package com.lottemart.epc.edi.consult.dao;

import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.consult.model.PEDMSCT0099VO;

@Repository("PEDMSCT0099Dao")
public class PEDMSCT0099Dao extends AbstractDAO {
	
	/**
	 * 사업자  List
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PEDMSCT0099VO> selectBmanNoZipList(Map map) throws Exception {
//		System.out.println(map.get("cono"));
		return (List<PEDMSCT0099VO>)getSqlMapClientTemplate().queryForList("PEDMSCT0099.selectBmanNoZipList", map);
	}
	
	/**
	 * 주소 검색
	 * @param srchStreetNm
	 * @return
	 * @throws Exception
	 */
	public List<PEDMSCT0099VO> selectZipList(PEDMSCT0099VO paramVO) throws Exception{
		return (List<PEDMSCT0099VO>)getSqlMapClientTemplate().queryForList("PEDMSCT0099.selectZipList", paramVO);
	}
	
	/**
	 * 주소 삭제
	 * @param trVO
	 * @throws Exception
	 */
	public void deleteZip(PEDMSCT0099VO trVO) throws Exception {
		this.delete("PEDMSCT0099.deleteZip", trVO);
	}
	
	/**
	 * 주소 저장
	 * @param trVO
	 * @throws Exception
	 */
	public void insertZip(PEDMSCT0099VO trVO) throws Exception {
		this.insert("PEDMSCT0099.insertZip", trVO);
	}
	
}
