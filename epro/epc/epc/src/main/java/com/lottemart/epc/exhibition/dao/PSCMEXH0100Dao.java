/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-epc
 * @since    : 2016.05
 * @Description : SCM 기획전
 * @author : ljh
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.epc.exhibition.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.exhibition.model.PSCMEXH010050VO;
import com.lottemart.common.util.DataMap;

/**
 * @author ljh
 * @Class : com.lottemart.epc.exhibition.dao
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2016.05.21	 ljh
 * @version :
 */

@Repository("pscmexh0100Dao")
public class PSCMEXH0100Dao extends AbstractDAO{

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 기획전관리 조회 
	 * Desc : 기획전관리 조회
	 * @Method Name : selectExhibitionList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectExhibitionList(DataMap paramMap) throws SQLException{
		return sqlMapClient.queryForList("pscmexh0100.selectExhibitionList", paramMap);
	}
	
	
	/**
	 * 통합기획전관리 조회 
	 * Desc : 통합기획전관리 조회
	 * @Method Name : selectExhibitionList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectExhibitionIntList(DataMap paramMap) throws SQLException{
		return sqlMapClient.queryForList("pscmexh0100.selectExhibitionIntList", paramMap);
	}

	/**
	 * 기획전카테고리 조회 
	 * Desc : 기획전카테고리 조회
	 * @Method Name : selectCategoryList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCategoryList() {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectCategoryList", null);
	}
	
	/**
	 * 승인처리
	 * Desc : 승인처리
	 * @Method Name : updateApply
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateApply(DataMap map) {
		int resultCnt = 0;
		
		if("1".equals(map.get("gbn")))
				resultCnt = getSqlMapClientTemplate().update("pscmexh0100.updateApply",map);
		else
				resultCnt = getSqlMapClientTemplate().update("pscmexh0100.updateApplyRetn",map);
		
		 
		return resultCnt;		
	}
	
	/**
	 * 반려처리
	 * Desc : 반려처리
	 * @Method Name : updateApplyRetn
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateApplyRetn(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateApplyRetn",map);
	}
	
	/**
	 * 기획전등록 - 정보 조회
	 * Desc : 기획전등록 - 정보 조회
	 * @Method Name : selectExhibitionInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public List<DataMap> selectExhibitionInfo(DataMap param) throws SQLException {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectExhibitionInfo", param);
	}

	/**
	 * 기획전등록 - 기획전번호 발번
	 * Desc : 기획전등록 - 기획전번호 발번
	 * @Method Name : getMkdpSeq
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String getMkdpSeq(DataMap param) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.getMkdpSeq", param);
	}

	/**
	 *기획전 등록
	 * Desc : 기획전 등록
	 * @Method Name : insertExhibitionInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int insertExhibitionInfo(DataMap param) throws SQLException {
		return sqlMapClient.update("pscmexh0100.insertExhibitionInfo", param);
	}

	/**
	 *기획전 수정
	 * Desc : 기획전 수정
	 * @Method Name : updateExhibitionInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */	
	public int updateExhibitionInfo(DataMap param) throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateExhibitionInfo", param);
	}
	
	/**
	 * 기획전 소카테고리 조회 
	 * Desc : 기획전 소카테고리 조회
	 * @Method Name : selectCategoryList
	 * @param
	 * @return
	 * @throws SQLException
	 */	
	public List<DataMap> selectCategoryChildIdList(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectCategoryChildIdList", param);
	}
	
	/**
	 * 기획전 구분자정보 조회
	 * Desc :  기획전 구분자정보 조회
	 * @Method Name : selectDivnInfoList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public List<DataMap> selectDivnInfoList(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectDivnInfoList", param);
	}
	
	/**
	 * 기획전 구분자정보 순번 발번
	 * Desc :  기획전 구분자정보 순번 발번
	 * @Method Name : getDivnSeq
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String getDivnSeq(Map<String, Object> pMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.getDivnSeq", pMap);
	}
	
	/**
	 *기획전 구분자정보 등록
	 * Desc : 기획전 구분자정보 등록
	 * @Method Name : insertDivnInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int insertDivnInfo(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.update("pscmexh0100.insertDivnInfo", pMap);
	}

	/**
	 *기획전 구분자정보 수정
	 * Desc : 기획전 구분자정보 수정
	 * @Method Name : updateDivnInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateDivnInfo(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateDivnInfo", pMap);
	}

	/**
	 *기획전 구분자정보 삭제
	 * Desc : 기획전 구분자정보 삭제
	 * @Method Name : deleteDivnInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDivnInfo(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDivnInfo", pMap);
	}
	
	/**
	 *기획전 구분자 점포 정보 삭제
	 * Desc : 기획전 구분자 점포 정보 삭제
	 * @Method Name : deleteDivnStrInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDivnStrInfo(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDivnStrInfo", pMap);
	}

	/**
	 *이미지/HTML 정보 - 기획전 내용(소개) 목록 조회
	 * Desc : 이미지/HTML 정보 - 기획전 내용(소개) 목록 조회
	 * @Method Name : selectContentsList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public List<DataMap> selectContentsList(DataMap param) throws SQLException {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectContentsList", param);
	}
	
	/**
	 *이미지/HTML 정보 - 기획전 내용(소개) 등록
	 * Desc : 이미지/HTML 정보 - 기획전 내용(소개) 등록
	 * @Method Name : insertContentsImage
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int insertContentsImage(DataMap param) throws SQLException {
		return sqlMapClient.update("pscmexh0100.insertContentsInfo", param);
	}

	/**
	 *이미지/HTML 정보 - 기획전 내용(소개) 수정
	 * Desc : 이미지/HTML 정보 - 기획전 내용(소개) 수정
	 * @Method Name : updateContentsImage
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateContentsImage(DataMap param)  throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateContentsInfo", param);
	}
	
	/**
	 *이미지/HTML 정보 - 기획전 내용(소개) 정보 조회
	 * Desc : 이미지/HTML 정보 - 기획전 내용(소개) 정보 조회
	 * @Method Name : selectContentsInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public DataMap selectContentsInfo(DataMap param) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmexh0100.selectContentsInfo", param);
	}

	/**
	 *이미지/HTML 정보 - 기획전 내용(소개) 정보 삭제
	 * Desc : 이미지/HTML 정보 - 기획전 내용(소개) 정보 삭제
	 * @Method Name : deleteContentsImage
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int deleteContentsImage(DataMap map) {
		int resultCnt = 0;
		resultCnt = getSqlMapClientTemplate().delete("pscmexh0100.deleteContentsImage",map);
		
		return resultCnt;
	}

	/**
	 * 구분자 정보 가져오기
	 * Desc : 구분자 정보 가져오기
	 * @Method Name : selectDivnSeqList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectDivnSeqList(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectDivnSeqList", param);
	}

	/**
	 * 기획전 상품 등록
	 * Desc : 기획전 상품 등록
	 * @Method Name : insertExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int insertExhibitionProdInfo(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertExhibitionProdInfo",map);
	}
	
	
	/**
	 * 통합기획전 상품 등록
	 * Desc : 통합기획전 상품 등록
	 * @Method Name : insertIntExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int insertIntExhibitionProdInfo(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertIntExhibitionProdInfo",map);
	}
	
	/**
	 * 기획전 승인요청
	 * Desc : 기획전 승인요청
	 * @Method Name : updateAprv
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int updateAprv(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateAprv",map);
//		return getSqlMapClientTemplate().update("pscmexh0100.updateExhibitionProdInfo",map);
	}
	
	
	/**
	 * 기획전 상품 수정
	 * Desc : 기획전 상품 수정
	 * @Method Name : updateExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int updateExhibitionProdInfo(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateExhibitionProdInfo",map);
	}
	
	/**
	 * 기획전 할당 요청
	 * Desc : 기획전 할당 요청
	 * @Method Name : updateExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int updateStsCd(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateStsCd",map);
	}
	
	
	/**
	 * 기획전 상품 삭제
	 * Desc : 기획전 상품 삭제
	 * @Method Name : updateExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int deleteProdCd(DataMap map) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteProdCd",map);
	}
	
	
	/**
	 * 기획전 상품 수정 - 조회조건 점포 선택후 수정시
	 * Desc : 기획전 상품 수정 - 조회조건 점포 선택후 수정시
	 * @Method Name : updateAllStoreExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int updateAllStoreExhibitionProdInfo(DataMap map) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateAllStoreExhibitionProdInfo",map);
	}

	/**
	 * 기획전 상품 등록 팝업 조회
	 * Desc : 기획전 상품 등록 팝업 조회
	 * @Method Name : selectExhibitionProdInfoList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectExhibitionProdInfoList(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectExhibitionProdInfoList", param);
	}
	
	
	/**
	 * 기획전 상품 등록 팝업 조회
	 * Desc : 기획전 상품 등록 팝업 조회 (구분자 조회)
	 * @Method Name : selectExhibitionProdInfoList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectDivnSeqlist(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectDivnSeqlist", param);
	}
	
	
	/**
	 * 기획전 상품 등록 팝업 조회
	 * Desc : 기획전 상품 등록 팝업 조회 (구분자 조회)
	 * @Method Name : selectExhibitionProdInfoList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectExhibitionProdList1(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectExhibitionProdList1", param);
	}
	
	/**
	 * 기획전 상품 등록 팝업 조회
	 * Desc : 기획전 상품 등록 팝업 조회 (구분자 조회)
	 * @Method Name : selectExhibitionProdInfoList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectExhibitionProdList2(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectExhibitionProdList2", param);
	}

	/**
	 * 기획전 상품 등록 팝업 삭제
	 * Desc : 기획전 상품 등록 팝업 삭제
	 * @Method Name : deleteExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int deleteExhibitionProdInfo(DataMap map) {
		return getSqlMapClientTemplate().delete("pscmexh0100.deleteExhibitionProdInfo",map);
	}

	/**
	 * 기획전 상품 상품정렬방식 저장
	 * Desc : 기획전 상품 상품정렬방식 저장
	 * @Method Name : updateExhibitionProdInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public int updateDivnProdSortInfo(DataMap param) {
		return getSqlMapClientTemplate().delete("pscmexh0100.updateDivnProdSortInfo",param);
	}

	/**
	 * 기획전 상품등록 상품정렬방식 조회
	 * Desc : 기획전 상품등록 상품정렬방식 조회
	 * @Method Name : selectDivnProdSortInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public DataMap selectDivnProdSortInfo(DataMap param) {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscmexh0100.selectDivnProdSortInfo", param);
	}

	/**
	 * 기획전 복사 - 구분자 정보, 이미지/HTML 정보 복사
	 * Desc :  기획전 복사 - 구분자 정보, 이미지/HTML 정보 복사
	 * @Method Name : insertCopyExhibition
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public void insertCopyExhibition(DataMap param) {
		int resultCnt = 0;
		
		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhibitionProdInfoList", param);
		//기획전 복사  selectExhibitionProdInfoList
//		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhiDivnInfo", param);
//		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhiImageHtmlInfo", param);
		
		//구분자정보, 이미지/HTML 점포 복사
//		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhiDivnSubStr", param);
//		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhiImageHtmlSubStr", param);
				
	}
	
	/**
	 * 기획전 상품등록 상품정렬방식 조회
	 * Desc : 기획전 상품등록 상품정렬방식 조회
	 * @Method Name : selectDivnProdSortInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectProdItemList(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectProdItemList", param);
	}
	
	/**
	 * 기획전 등록 할당점포 등록
	 * Desc : 기획전 등록 할당점포 등록
	 * @Method Name : selectDivnProdSortInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public void insertStrAssignInfo(DataMap param) {
		int resultCnt = 0;
		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertStrAssignInfo", param);
	}
	
	/**
	 * 구분자 등록 - 할당점포 등록
	 * Desc : 기획전 등록 할당점포 등록
	 * @Method Name : insertDtlStrAssignInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public void insertDtlStrAssignInfo(DataMap param) {
		int resultCnt = 0;
		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertDtlStrAssignInfo", param);
	}

	/**
	 * 컨텐츠 등록 - 할당점포 등록
	 * Desc : 컨텐츠 등록 할당점포 등록
	 * @Method Name : insertConStrAssignInfo
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public void insertConStrAssignInfo(DataMap param) {
		int resultCnt = 0;
		resultCnt += getSqlMapClientTemplate().update("pscmexh0100.insertConStrAssignInfo", param);
	}

	/**
	 * 컨텐츠 등록 - 컨텐츠 순번 발번
	 * Desc : 컨텐츠 등록 - 컨텐츠 순번 발번
	 * @Method Name : getMkdpConSeqKey
	 * @param
	 * @return
	 * @throws SQLException   
	 */	
	public String getMkdpConSeqKey(DataMap param) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.getMkdpConSeqKey", param);
	}
	
	/**
	 *기획전 점포수정 - 컨텐츠 점포 삭제
	 * Desc : 기획전 점포수정 컨텐츠 점포 삭제
	 * @Method Name : deleteMkdpModifyConStr
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteMkdpModifyConStr(DataMap pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteMkdpModifyConStr", pMap);
	}
	
	/**
	 *기획전 점포수정 - 구분자 점포 삭제
	 * Desc : 기획전 점포수정 - 구분자 점포 삭제
	 * @Method Name : deleteMkdpModifyDtlStr
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteMkdpModifyDtlStr(DataMap pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteMkdpModifyDtlStr", pMap);
	}
	
	/**
	 *기획전 점포수정 - 기획전 전체점포 삭제
	 * Desc : 기획전 점포수정 - 기획전 전체점포 삭제
	 * @Method Name : deleteMkdpMstStr
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteMkdpMstStr(DataMap pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteMkdpMstStr", pMap);
	}
	
	/**
	 *구분자 점포수정 - 구분자 전체점포 삭제
	 * Desc : 구분자 점포수정 - 구분자 전체점포 삭제
	 * @Method Name : deleteDtlStrAssignInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDtlStrAssignInfo(DataMap pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDtlStrAssignInfo", pMap);
	}
	
	/**
	 *컨텐츠 점포수정 - 컨텐츠 전체점포 삭제
	 * Desc : 컨텐츠 점포수정 - 컨텐츠 전체점포 삭제
	 * @Method Name : deleteConStrAssignInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteConStrAssignInfo(DataMap pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteConStrAssignInfo", pMap);
	}	
	
	/**
	 *구분자목록 점포 삭제
	 * Desc : 구분자목록 점포 삭제
	 * @Method Name : deleteDtlStrAssignInfoList
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDtlStrAssignInfoList(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDtlStrAssignInfoList", pMap);
	}
	
	/**
	 *구분자 기간범위 체크
	 * Desc : 구분자 기간범위 체크
	 * @Method Name : selectMkdpDateCheck
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String selectMkdpDateCheck(DataMap pMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.selectMkdpDateCheck", pMap);
	}
	
	/**
	 *구분자 점포범위 체크
	 * Desc : 구분자 점포범위 체크
	 * @Method Name : selectMkdpStoreCheck
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String selectMkdpStoreCheck(DataMap pMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.selectMkdpStoreCheck", pMap);
	}
	
	/**
	 *구분자 점포 존재여부 체크
	 * Desc : 구분자 존재여부 체크
	 * @Method Name : selectExistMkdpStoreCheck
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String selectExistMkdpStoreCheck(DataMap pMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.selectExistMkdpStoreCheck", pMap);
	}
	
	/**
	 *이미지/HTML 채널, 점포별 중복 체크
	 * Desc : 이미지/HTML 채널, 점포별 중복 체크
	 * @Method Name : selectContentsChStoreCheck
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectContentsChStoreCheck(DataMap param) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmexh0100.selectContentsChStoreCheck", param);
	}
	
	
	
	
	
	
	
	
	/** 2016-05-17 추가 ============================================
	 *구분자정보 삭제시 - 관련 구분자 Html 삭제
	 * Desc : 구분자정보 삭제시 - 관련 구분자 Html 삭제
	 * @Method Name : deleteDivnHtmlContents
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDivnHtmlContents(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDivnHtmlContents", pMap);
	}

	/** 2016-05-17 추가 ============================================
	 *구분자정보 삭제시 - 관련 상품 삭제
	 * Desc : 구분자정보 삭제시 - 관련 상품 삭제
	 * @Method Name : deleteDivnAssignProd
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int deleteDivnAssignProd(Map<String, Object> pMap) throws SQLException {
		return sqlMapClient.delete("pscmexh0100.deleteDivnAssignProd", pMap);
	}
	
	/**
	 *이미지 정보 - 이미지 경로 수정
	 * Desc : 이미지 정보 - 이미지 경로 수정
	 * @Method Name : updateContentsImgPathInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateContentsImgPathInfo(DataMap param)  throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateContentsImgPathInfo", param);
	}
	
	/**
	 *PC 에디터 정보 - PC 에디터 수정
	 * Desc : PC 에디터 정보 - PC 에디터 수정
	 * @Method Name : updateContentsImgPathInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateContentsHtml1(DataMap param)  throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateContentsHtml1", param);
	}
	
	/**
	 *MOBILE 에디터 정보 - MOBILE 에디터 수정
	 * Desc : MOBILE 에디터 정보 - MOBILE 에디터 수정
	 * @Method Name : updateContentsImgPathInfo
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int updateContentsHtml2(DataMap param)  throws SQLException {
		return sqlMapClient.update("pscmexh0100.updateContentsHtml2", param);
	}
	
	/**
	 *기획전 전점여부 체크
	 * Desc : 기획전 전점여부 체크
	 * @Method Name : selectExhibitionStrCd
	 * @param
	 * @return
	 * @throws SQLException
	 */
/*	public String selectExhibitionStrCd(DataMap pMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.selectExhibitionStrCd", pMap);
	}*/
	
	/**
	 * Desc : MD정보 리스트 가져오기
	 * @Method Name : selectMdList
	 * @param paramMap
	 * @throws SQLException
	 * @return List<PSCMEXH010050VO>
	 */
	public List<PSCMEXH010050VO> selectMdList(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectMdList",paramMap);
	}
	
	/**
	 * Desc : MD정보 총건수
	 * @Method Name : selectMdTotalCnt
	 * @param paramMap
	 * @throws SQLException
	 * @return iTotCnt
	 */
	public int selectMdTotalCnt(Map<String, String> paramMap) throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscmexh0100.selectMdTotalCnt",paramMap);
		return iTotCnt.intValue();
	}	
	
	
	/**
	 * Desc : scm 이미지 및 에디트 편집 저장
	 * @Method Name : selectMdTotalCnt
	 * @param paramMap
	 * @throws SQLException
	 * @return iTotCnt
	 */
	public String insertExhiImageHtml(DataMap param) throws SQLException {
		return (String)sqlMapClient.insert("pscmexh0100.insertExhiImageHtml", param);
	}
	
	
	/**
	 * 기획전 상품 등록 팝업 조회
	 * Desc : assign chk
	 * @Method Name : selectExhibitionProdInfoList
	 * @param
	 * @return
	 * @throws SQLException   
	 */
	public List<DataMap> selectAssignChk(DataMap param) {
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectAssignChk", param);
	}
	
	/**
	 *구분자 점포 존재여부 체크
	 * Desc : 구분자 존재여부 체크
	 * @Method Name : selectExistMkdpStoreCheck
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public int selectImageNm(String filePath) throws SQLException {
		return (Integer)sqlMapClient.queryForObject("pscmexh0100.selectImageNm", filePath);
	}
	
	/**
	 *기획전 구분자 점포 삭제하면 예상되는 점포 미존재 구분자 조회
	 * Desc : 기획전 구분자 점포 삭제하면 예상되는 점포 미존재 구분자 조회
	 * @Method Name : selectCheckRemoveIsStrMkdp
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectCheckRemoveIsStrMkdp(DataMap param) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmexh0100.selectCheckRemoveIsStrMkdp", param);
	}

	/**
	 *기획전 컨텐츠 점포 삭제하면 예상되는 점포 미존재 구분자 조회
	 * Desc : 기획전 컨텐츠 점포 삭제하면 예상되는 점포 미존재 구분자 조회
	 * @Method Name : selectCheckRemoveIsStrMkdp
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectCheckRemoveIsConStrMkdp(DataMap param) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscmexh0100.selectCheckRemoveIsConStrMkdp", param);
	}
	
	
	
	public int selectAdminInfoTotalCnt(Map<String, String> paramMap) throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscmexh0100.selectAdminInfoTotalCnt",paramMap);
		return iTotCnt.intValue();
	}
	
	public List<DataMap> selectAdminInfoList(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmexh0100.selectAdminInfoList",paramMap);
	}
	
	public String mkdpNm(DataMap paramMap) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.mkdpNm", paramMap);
	}


	public String selectAprvStsCdCheck(DataMap map) throws SQLException {
		return (String)sqlMapClient.queryForObject("pscmexh0100.selectAprvStsCdCheck", map);
	}


	public int insertCopyExhibitionInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhibitionInfo", param);
	}


	public int insertCopyStrAssignInfo(DataMap param) throws SQLException {
		return sqlMapClient.update("pscmexh0100.insertCopyStrAssignInfo", param);
	}


	public int insertCopyExhiImageHtml(DataMap param) throws SQLException {
		return sqlMapClient.update("pscmexh0100.insertCopyExhiImageHtml", param);
	}


	public int insertCopyConStrAssignInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyConStrAssignInfo", param);	
	}


	public int selectDivnInfoLength(DataMap param) throws SQLException {
		return (Integer)sqlMapClient.queryForObject("pscmexh0100.selectDivnInfoLength", param);
	}


	public int insertCopyDivnInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyDivnInfo", param);
	}


	public int insertCopyDtlStrAssignInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyDtlStrAssignInfo", param);		
	}


	public int insertCopyExhibitionProdInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyExhibitionProdInfo", param);
	}


	public int updateCopyAprv(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.updateCopyAprv", param);
		
	}
	public int insertCopyContentsInfo(DataMap param) {
		return getSqlMapClientTemplate().update("pscmexh0100.insertCopyContentsInfo", param);
	}
	
	
	
}
