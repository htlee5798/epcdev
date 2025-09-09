package com.lottemart.epc.edi.srm.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.lottemart.epc.edi.srm.utils.SRMCommonUtils.*;

public class SRMDBConnDao extends SqlMapClientDaoSupport{

	@Resource(name = "sqlMapClient_Srm")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}



	/**
	 * 단일 Integer SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object queryForInteger(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		Object result = getSqlMapClientTemplate().queryForObject(query, paramVo);
		return result;
	}

	/**
	 * 단일 String SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object queryForString(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		Object result = getSqlMapClientTemplate().queryForObject(query, paramVo);
		if(result != null){
			result = changeNormalString(result.toString());
		}
		return result;
	}

	/**
	 * 단일 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object queryForObject(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		Object result = getSqlMapClientTemplate().queryForObject(query, paramVo);
		//result data 변환
		result = convertNormalObjectToObject(result);
		return result;
	}

	/**
	 * 단일 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object queryForObjectParamString(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = changeSpecialString((String) vo);
		Object result = getSqlMapClientTemplate().queryForObject(query, paramVo);
		//result data 변환
		result = convertNormalObjectToObject(result);
		return result;
	}


	/**
	 * 복수 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
		public List queryForList(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		List<Object> result = getSqlMapClientTemplate().queryForList(query, paramVo);
		//result data 변환
		for(int i=0; i<result.size(); i++){
			Object obj = result.get(i);
			result.set(i,convertNormalObjectToObject(obj));
		}
		return result;
	}



	/**
	 * 복수 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public List queryForListHashMap(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		List<HashMap> result = getSqlMapClientTemplate().queryForList(query, paramVo);
		//result data 변환
		for(int i=0; i<result.size(); i++){
			HashMap map = result.get(i);
			Iterator itr = map.keySet().iterator();
			String keyAttribute = null;
			while (itr.hasNext()) {
				keyAttribute = (String) itr.next();
				map.put(keyAttribute,changeNormalString((String) map.get(keyAttribute)));
			}
			result.set(i, map);
		}

		return result;
	}


	/**
	 * 복수 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public List queryForListParamString(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = changeSpecialString((String) vo);
		List<Object> result = getSqlMapClientTemplate().queryForList(query, paramVo);
		//result data 변환
		for(int i=0; i<result.size(); i++){
			Object obj = result.get(i);
			result.set(i,convertNormalObjectToObject(obj));
		}
		return result;
	}


	/**
	 * 복수 데이터 SELECT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public List queryForListString(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		List<Object> result = getSqlMapClientTemplate().queryForList(query, paramVo);
		//result data 변환
		for(int i=0; i<result.size(); i++){
			Object obj = result.get(i);
			if(obj != null){
				result.set(i,changeNormalString(obj.toString()));
			} else {
				result.set(i,obj);
			}

		}
		return result;
	}

	/**
	 * INSERT
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object insert(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		return getSqlMapClientTemplate().insert(query, paramVo);
	}

	/**
	 * DELETE
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object delete(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		return getSqlMapClientTemplate().delete(query, paramVo);
	}

	/**
	 * UPDATE
	 * @param Object
	 * @return Object
	 * @throws Exception
	 */
	public Object update(String query, Object vo) throws Exception {
		//param data 변환
		Object paramVo = convertSpecialObjectToObject(vo);
		return getSqlMapClientTemplate().update(query, paramVo);
	}



}

