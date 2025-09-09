package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
//import com.lottemart.epc.product.model.PSCMPRD0009VO;
import com.lottemart.epc.product.model.PSCMPRD0015VO;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;

/**
 * 
 * @author cwj
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.dao
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   
 * @version : 
 * </pre>
 */
@Repository("pscmprd0015Dao")
public class PSCMPRD0015Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectRepProdCdList
	 * @author     : cwj
	 * @Description : 대표상품코드 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
/*	@SuppressWarnings("unchecked")
	public List<DataMap> selectRepProdCdList(Map<String, String> paramMap) throws SQLException {
		return sqlMapClient.queryForList("pscmprd0015.selectRepProdCdList", paramMap);
	}*/
	
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0015VO> selectRepProdCdList(Map<Object, Object> paramMap) throws SQLException {
		return (List<PSCMPRD0015VO>)sqlMapClient.queryForList("pscmprd0015.selectRepProdCdList", paramMap);
	}

	/**
	 * Desc : 상품단품 정보 중복 카운트를 조회하는 메소드
	 * @Method Name : selectProductItemDupCount
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductItemDupCount(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("PSCMPRD0015.selectProductItemDupCount", paramMap);
	}
	
	/**
	 * Desc : 상품단품 정보를 조회하는 메소드
	 * @Method Name : selectProductItemList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0015VO> selectProductItemList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0015VO>)sqlMapClient.queryForList("PSCMPRD0015.selectProductItemList", paramMap);
	}
	
	/**
	 * 
	 * @see selectRepProdCdComboList
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectRepProdCdComboList
	 * @author     : cwj
	 * @Description : 협력사별 대표판매코드 목록 조회 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectRepProdCdComboList(Map<Object, Object> paramMap) throws SQLException {
		return (List<Map<String, Object>>)sqlMapClient.queryForList("pscmprd0015.selectRepProdCdComboList", paramMap);
	}
	
	/**
	 * 
	 * @see selectTprStoreItemPriceList
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectTprStoreItemPriceList
	 * @author     : cwj
	 * @Description : TPR_SOTRE_ITEM_PRICE 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTprStoreItemPriceList(Map<Object, Object> paramMap) throws SQLException {
		return (List<Map<String, Object>>)sqlMapClient.queryForList("pscmprd0015.selectTprStoreItemPriceList", paramMap);
	}

	/**
	 * 
	 * @see chkCurrDate
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : chkCurrDate
	 * @author     : cwj
	 * @Description : 적용시작일시를 현재날짜와 비교(적용여부 체크용)
	 * @param pscmprd0015vo
	 * @return
	 * @throws SQLException
	 */
	public DataMap chkCurrDate(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("pscmprd0015.chkCurrDate", pscmprd0015vo);
	}

	/**
	 * 
	 * @see chkProfitRate
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : chkProfitRate
	 * @author     : cwj
	 * @Description : 이익률 중복체크
	 * @param pscmprd0015vo
	 * @return
	 * @throws SQLException
	 */
	public DataMap chkProfitRate(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.chkProfitRate", pscmprd0015vo);
	}
	
	/**
	 * 
	 * @see chkRegisteredRepProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : chkRegisteredRepProdCd
	 * @author     : cwj
	 * @Description : 적용시작일시 중복체크
	 * @param pscmprd0015vo
	 * @return
	 * @throws SQLException
	 */
	public DataMap chkRegisteredRepProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("pscmprd0015.chkRegisteredRepProdCd", pscmprd0015vo);
	}
	
	/**
	 * 
	 * @see updateBeforeRepProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateBeforeRepProdCd
	 * @author     : cwj
	 * @Description : 등록시 이전 데이터의 정보를 수정
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void updateBeforeRepProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateBeforeRepProdCd", pscmprd0015vo);
	}

	/**
	 * 
	 * @see insertRepProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertRepProdCd
	 * @author     : cwj
	 * @Description : 대표상품코드 등록
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void insertRepProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertRepProdCd", pscmprd0015vo);
	}
	
	/**
	 * 
	 * @see updateRepProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateRepProdCd
	 * @author     : cwj
	 * @Description : 대표상품코드 수정
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void updateRepProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateRepProdCd", pscmprd0015vo);
	}

	/**
	 * 
	 * @see chkUpdateFlag
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : chkUpdateFlag
	 * @author     : cwj
	 * @Description : 삭제시 이전 데이터 적용끝일자 수정여부 체크
	 * @param pscmprd0015vo
	 * @return
	 * @throws SQLException
	 */
	public DataMap chkUpdateFlag(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.chkUpdateFlag", pscmprd0015vo);
	}

	/**
	 * 
	 * @see updateApplyEndDy
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateApplyEndDy
	 * @author     : cwj
	 * @Description : 삭제시 이전 데이터의 적용끝일자를 수정
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void updateApplyEndDy(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateApplyEndDy", pscmprd0015vo);
	}

	/**
	 * 
	 * @see deleteRepProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : deleteRepProdCd
	 * @author     : cwj
	 * @Description : 대표상품코드 삭제
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public int deleteRepProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return sqlMapClient.delete("pscmprd0015.deleteRepProdCd", pscmprd0015vo);
	}

	/**
	 * 
	 * @see selectRepProdCdInfo
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectRepProdCdInfo
	 * @author     : cwj
	 * @Description : 대표상품코드 상세 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public List<DataMap> selectRepProdCdInfoList(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscmprd0015.selectRepProdCdInfo", paramMap);
	}
	
	/**
	 * 
	 * @see insertRepProdCd_1_startDy_endDy
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertRepProdCd_1_startDy_endDy
	 * @author     : jyLim
	 * @Description : 대표상품코드변경이력을 등록한다. 
	 * 									  	가격정보: 사용자가 입력한 값
	 * 										시작일자: 사용자가 입력한 시작일자
	 * 									         종료일자: 사용자가 입력한 종료일자  
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void insertRepProdCd_startDy_endDy(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertRepProdCd_startDy_endDy", pscmprd0015vo);
	}
	/**
	 * 
	 * @see insertRepProdCd_2_endDy_plus_1_99991231
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertRepProdCd_2_endDy_plus_1_99991231
	 * @author     : jyLim
	 * @Description : 대표상품코드변경이력을 등록한다. 
	 * 										가격정보: 상품마스터의 값
	 * 										시작일자: 사용자가 입력한 종료일자 +1
	 * 										종료일자: 99991231
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void insertRepProdCd_1_endDy_plus_1_99991231(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertRepProdCd_1_endDy_plus_1_99991231", pscmprd0015vo);
	}
	
	/**
	 * 
	 * @see insertRepProdCd_2_endDy_plus_1_99991231
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertRepProdCd_2_endDy_plus_1_99991231
	 * @author     : jyLim
	 * @Description : 대표상품코드변경이력을 등록한다. 
	 * 										가격정보: 최근변경이력 값
	 * 										시작일자: 사용자가 입력한 종료일자 +1
	 * 										종료일자: 99991231
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void insertRepProdCd_2_endDy_plus_1_99991231(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertRepProdCd_2_endDy_plus_1_99991231", pscmprd0015vo);
	}
	/**
	 * 
	 * @see selectTprRepProdChgHst
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectTprRepProdChgHst
	 * @author     : jyLim
	 * @Description : 대표상품코드변경이력 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectTprRepProdChgHst(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.selectTprRepProdChgHst", paramMap);
	}
	public List<DataMap> selectTprRepProdChgHst1(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscmprd0015.selectTprRepProdChgHst", paramMap);
	}
	/**
	 * 
	 * @see selectTprProduct
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectTprProduct
	 * @author     : jyLim
	 * @Description : 상품마스터 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectTprProduct(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.selectTprProduct", paramMap);
	}
	public List<DataMap> selectTprProduct1(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscmprd0015.selectTprProduct", paramMap);
	}
	/**
	 * 
	 * @see selectProdInfo
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectProdInfo
	 * @author     : jyLim
	 * @Description : 상품정보를 가져온다
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectProdInfo(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.selectProdInfo", paramMap);
	}
	
	public DataMap chkProduct(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.chkProduct" , pscmprd0015vo);
	}
	
	/**
	 * 
	 * @see selectRepProdSellingPrc
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectRepProdSellingPrc
	 * @author     : dragon
	 * @Description : 긴급매가1 - 대상 상품정보 조회
	 * @param : paramMap
	 * @return : List<DataMap>
	 * @throws SQLException
	 */
	public List<DataMap> selectRepProdSellingPrc(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscmprd0015.selectRepProdSellingPrc", paramMap);
	}
	
	/**
	 * 
	 * @see updateTprStoreItemPriceRepProd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateTprStoreItemPriceRepProd
	 * @author     : dragon
	 * @Description : 긴급매가2 - 점포별가격정보 가격 update
	 * @param : paramMap
	 * @return : 
	 * @throws SQLException
	 */
	public void updateTprStoreItemPriceRepProd(Map<Object, Object>  paramMap) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateTprStoreItemPriceRepProd", paramMap);
	}

	/**
	 * 
	 * @see insertTprStoreItemPriceHistRepProd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertTprStoreItemPriceHistRepProd
	 * @author     : dragon
	 * @Description : 긴급매가3 - 점포별가격정보 이력 insert
	 * @param : paramMap
	 * @return : 
	 * @throws SQLException
	 */
	public void insertTprStoreItemPriceHistRepProd(Map<Object, Object>  paramMap) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertTprStoreItemPriceHistRepProd", paramMap);
	}

	/**
	 * 
	 * @see updateTcaRepProdChgHstRepProd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateTcaRepProdChgHstRepProd
	 * @author     : dragon
	 * @Description : 긴급매가4 - 대표판매코드변경이력 적용여부 update
	 * @param : paramMap
	 * @return : 
	 * @throws SQLException
	 */
	public void updateTcaRepProdChgHstRepProd(Map<Object, Object>  paramMap) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateTcaRepProdChgHstRepProd", paramMap);
	}
	
	/**
	 * 
	 * @see updateTprProductRepProd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateTprProductRepProd
	 * @author     : dragon
	 * @Description : 긴급매가5 - 상품마스터 가격 update
	 * @param : paramMap
	 * @return : 
	 * @throws SQLException
	 */
	public void updateTprProductRepProd(Map<Object, Object>  paramMap) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateTprProductRepProd", paramMap);
	}

	/**
	 * 
	 * @see updateTcaCategoryAssignRepProd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : updateTcaCategoryAssignRepProd
	 * @author     : dragon
	 * @Description : 긴급매가6 - 카테고리할당상품 가격 update
	 * @param : paramMap
	 * @return : 
	 * @throws SQLException
	 */
	public void updateTcaCategoryAssignRepProd(Map<Object, Object>  paramMap) throws SQLException {
		sqlMapClient.update("pscmprd0015.updateTcaCategoryAssignRepProd", paramMap);
	}
	
	/**
	 * 
	 * @see insertRepChgProdCd
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : insertRepChgProdCd
	 * @author     : cwj
	 * @Description : 대표상품코드 변경 요청 등록
	 * @param pscmprd0015vo
	 * @throws SQLException
	 */
	public void insertRepChgProdCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		sqlMapClient.insert("pscmprd0015.insertRepChgProdCd", pscmprd0015vo);
	}

	public DataMap selectRepProdCdTaxatDivnCd(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmprd0015.selectRepProdCdTaxatDivnCd", pscmprd0015vo);
	}

	/**
	 * 
	 * @see chkCurrChgSts
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : chkCurrDate
	 * @author     : cwj
	 * @Description : 적용시작일시를 현재날짜와 비교(적용여부 체크용)
	 * @param pscmprd0015vo
	 * @return
	 * @throws SQLException
	 */
	public DataMap chkCurrChgSts(PSCMPRD0015VO pscmprd0015vo) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("pscmprd0015.chkCurrChgSts", pscmprd0015vo);
	}
	
	/**
	 * Desc : 상품 정보를 조회한다.
	 * @Method Name : pscmprd0015List
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> pscmprd0015List(Map<Object, Object> paramMap)throws Exception{
		return (List<Map<String, Object>>) sqlMapClient.queryForList("pscmprd0015.pscmprd0015List", paramMap);
	}
	
	
}
