package com.lottemart.common.product.video.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import lcn.module.framework.base.AbstractDAO;
/**
 *  
 * @Class Name : TPRMPICDao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일          수정자	     수정내용
 *  -------         --------    ---------------------------
 * 2016. 6. 15. 오후 3:25:31   hyunjin
 * 
 * @Copyright (C) 2016 ~ 2016 lottemart All right reserved.
 */
@Repository("tprmpicDao")
public class TPRMPICDao extends AbstractDAO {
	
	@Autowired
	private SqlMapClient sqlMapClient;
	/**
	 * Desc : 상품 동영상 목록 조회
	 * @Method Name : selectTPRMPICList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<Object, Object>> selectTPRMPICList(Map<Object, Object> paramMap) throws Exception{
		return (List<Map<Object, Object>>) sqlMapClient.queryForList("tprMpic.selectTprMpicList", paramMap);
	}
	
	
	
	/**
	 * Desc : 상품 동영상 수정
	 * @Method Name : updateTPRMPICinfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateTPRMPICinfo(Map<Object, Object> paramMap) throws Exception{
		return (Integer) sqlMapClient.update("tprMpic.updateTprMpicInfo", paramMap);
	}
	
	/**
	 * Desc : 상품 동영상 등록
	 * @Method Name : insertTPRMPICinfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int insertTPRMPICinfo(Map<Object, Object> paramMap) throws Exception{
		return (Integer) sqlMapClient.update("tprMpic.insertTPRMPICinfo", paramMap);
	}
	
	/**
	 * Desc : 상품 동영상 삭제
	 * @Method Name : deleteTPRMPICinfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteTPRMPICinfo(Map<Object, Object> paramMap) throws Exception{
		return (Integer) sqlMapClient.delete("tprMpic.deleteTprMpicInfo", paramMap);
	}
	
	
	public Map<Object, Object> selectTPRMPICinfo(Map<Object, Object> paramMap) throws Exception{
		return (Map<Object, Object>) sqlMapClient.queryForObject("tprMpic.selectTprMpicInfo", paramMap);
	}
	
	public Map<Object, Object> selectTPRMPICInfo2(Map<Object, Object> paramMap) throws Exception{
		return (Map<Object, Object>) sqlMapClient.queryForObject("tprMpic.selectTPRMPICInfo2", paramMap);
	}
	
	

}
