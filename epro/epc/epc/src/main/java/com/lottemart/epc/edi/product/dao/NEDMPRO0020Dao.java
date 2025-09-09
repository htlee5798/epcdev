package com.lottemart.epc.edi.product.dao;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.EcProductAttribute;
import com.lottemart.epc.edi.product.model.EcProductCategory;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0025VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0026VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;

/**
 * @Class Name : NEDMPRO0020Dao
 * @Description : 신상품등록(온오프) Dao
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 */

@Repository("nEDMPRO0020Dao")
public class NEDMPRO0020Dao extends SqlMapClientDaoSupport {

	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

	/**
	 * 신상품등록 PGM_ID 구해오기
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String selectNewProductPgmId(String str) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewProductPgmId", str);
	}

	/**
	 * 직매입시 면세/과세 별 이익율 계산
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public String selectnewProdPrftRate(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectnewProdPrftRate", nEDMPRO0020VO);
	}

	/**
	 * VIC 직매입시 면세/과세 별 이익율 계산
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public String selectnewProdWprftRate(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectnewProdWprftRate", nEDMPRO0020VO);
	}

	/**
	 * 과세 및 면세,영세 일경우 원가계산
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public String selectNorProdPcost(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNorProdPcost", nEDMPRO0020VO);
	}

	/**
	 * VIC 과세 및 면세,영세  일경우 원가계산
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public String selectWnorProdPcost(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectWnorProdPcost", nEDMPRO0020VO);
	}

	/**
	 * 신규상품 등록
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdMst(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdMst", nEDMPRO0020VO);
	}

	/**
	 * KC인증 정보 저장
	 * @param nEDMPRO0021VO
	 * @throws Exception
	 */
	public void insertNewProdCertInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdCertInfo", nEDMPRO0020VO);
	}

	/**
	 * 전자상거래법 저장
	 * @param nEDMPRO0022VO
	 * @throws Exception
	 */
	public void insertNewProdAddInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdAddInfo", nEDMPRO0020VO);
	}

	/**
	 * 상품상세설명 저장
	 * @param nEDMPRO0023VO
	 * @throws Exception
	 */
	public void insertNewProdDescrInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdDescrInfo", nEDMPRO0020VO);
	}

	/**
	 * 신규상품 수입정보 등록
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewImportInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewImportInfo", nEDMPRO0020VO);
	}

	/**
	 * 신규상품 삭제정보 등록
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void deleteNewImportInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.deleteNewImportInfo", nEDMPRO0020VO);
	}

	/**
	 * 전상법 삭제[저장전]
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void deleteNewProdTprProdAddInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteNewProdTprProdAddInfo", nEDMPRO0020VO);
	}

	/**
	 * KC인증 삭제[저장전]
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void deleteNewProdTprProdCertInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteNewProdTprProdCertInfo", nEDMPRO0020VO);
	}

	/**
	 * 신상품 마스터 테이블 온라인 이미지 개수 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCnt(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProdImgCnt", paramMap);
	}

	/**
	 * 신상품 마스터 동영상URL 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdMovieUrl(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProdMovieUrl", paramMap);
	}
	
	/**
	 * 신상품 마스터 동영상URL 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectNewProdMovieUrl(Map<String, Object> paramMap) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewProdMovieUrl", paramMap);
	}

	/**
	 * 신상품 마스터 테이블 온라인 이미지 삭제 후 이미지 카운트 -1
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCntMinus(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProdImgCntMinus", paramMap);
	}

	/**
	 * 상품의 등록된 변형속성 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdVarAttCnt(String pgmId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewProdVarAttCnt", pgmId);
	}

	/**
	 * 신상품의 확정된 상품의 등록된  변형속성 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdVarAttCntFix(String pgmId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewProdVarAttCntFix", pgmId);
	}

	/**
	 * 소분류에 변형속성 유무 카운트
	 * @param l3Cd
	 * @return
	 * @throws Exception
	 */
	public int selectVarAttCnt(Map<String, Object> paramMap)throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectVarAttCnt", paramMap);
	}

	/**
	 * 상품정보 가져오기
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectProdInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (NEDMPRO0020VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectProdInfo", nEDMPRO0020VO);
	}

	/**
	 * 소분류별 변형속성 그룹(2015.12.30 이전 버전)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrp(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectAttGrp", nEDMPRO0024VO);
	}*/

	/**
	 * 소분류별 변형속성 그룹 옵션(2015.12.30 이전 버전)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrpOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectAttGrpOpt", nEDMPRO0024VO);
	}*/

	/**
	 * 변형속성 그룹별 상세 속성(2015.12.30 이전 버전)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	/*public List<NEDMPRO0024VO> selectAttGrpOptVar(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectAttGrpOptVar", nEDMPRO0024VO);
	}*/

	/**
	 * 소분류별 PROFILE 가져오기(Class)
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectClass", nEDMPRO0024VO);
	}

	/**
	 * PROFILE별 변형속성 가져오기
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectClassVarAtt", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 Option 가져오기
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectClassVarAttOpt", nEDMPRO0024VO);
	}

	/**
	 * 88코드 중복 체크
	 * @param sellCd
	 * @return
	 * @throws Exception
	 */
	public int selectSellCdCount(String sellCd) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectSellCdCount", sellCd);
	}

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 추가 2016.04.06 by song min kyo [현재 사용안함  2016.04.08 쿼리로 변경됨]
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public int selectChkDangerProdCnt(String sellCode) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectChkDangerProdCnt", sellCode);
	}

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 추가 [IF_DT 가 최근일자 기준으로 CFM_FG 컬럼값이  '2' 일 경우 위해상품의 판매코드로 결정] 2016.04.08 추가  by song min kyo
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public String selectChkDangerProdCnt_3(String sellCode) throws Exception {
		return (String)getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectChkDangerProdCnt_3", sellCode);
	}

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 [상품속성탭에서 변형속성 저장할때 사용 ] 추가 2016.04.06 by song min kyo
	 * 현재 사용안함 2016.04.08 쿼리로 변경
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public int selectChkDangerProdCnt_2(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectChkDangerProdCnt_2", nEDMPRO0024VO);
	}

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 [상품속성탭에서 변형속성 저장할때 사용] 추가 2016.04.08 by song min kyo
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public int selectChkDangerProdCnt_4(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectChkDangerProdCnt_4", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteVarAtt", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 Insert
	 * @param nEDMPRO0024VO
	 * @throws Exception
	 */
	public void insertVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertVarAtt", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 Update
	 * @param nEDMPRO0024VO
	 * @throws Exception
	 */
	public void updateVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.updateVarAtt", nEDMPRO0024VO);
	}

	/**
	 * 등록된 변형속성의 CLASS 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public String selectProdVarAttClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectProdVarAttClass", nEDMPRO0024VO);
	}

	/**
	 * 확정된 신상품은 VAR_ATT_SAP테이블에서 데이터 가져오기 위해 추출
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public String selectProdVarAttClassFix(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectProdVarAttClassFix", nEDMPRO0024VO);
	}

	/**
	 * 등록된 변형속성의 전체 속성정보 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectProdVarAttOpt", nEDMPRO0024VO);
	}

	/**
	 * 등록된 신상품의 확정된 상품의 변형속성의 전체 속성정보 조회 추가 2016.03.17 by song min kyo
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAttOptFix(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectProdVarAttOptFix", nEDMPRO0024VO);
	}

	/**
	 * 등록된 상품변형속성 조회
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAtt(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectProdVarAtt", pgmId);
	}

	/**
	 * 등록된 신상품의 확정된 상품의 변형속성 조회 추가 2016.03.17 추가 by song min kyo
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAttFix(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectProdVarAttFix", pgmId);
	}

	/**
	 * 소분류별 그룹분석속성 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAtt(String prodRange) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectGrpAtt", prodRange);
	}


	/**
	 * 소분류별 그룹분석속성 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectGrpAttOne", nEDMPRO0024VO);
	}
	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectGrpAttOpt", nEDMPRO0024VO);
	}


	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOptOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectGrpAttOptOne", nEDMPRO0024VO);
	}

	/**
	 * 그룹분석속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void deleteGrpAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteGrpAtt", nEDMPRO0024VO);
	}
	/**
	 * 그룹분석속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void saveGrpAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.saveGrpAtt", nEDMPRO0024VO);
	}

	/**
	 * 상품 마스터 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpProductDetailInfo(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0020VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewTmpProductDetailInfo", paramMap);
	}

	/**
	 * 온라인전용 상품 마스터 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpOnlineProductDetailInfo(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0020VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewTmpOnlineProductDetailInfo", paramMap);
	}

	/**
	 * 상품마스터 정보 조회
	 * 신상품 등록현황 조회에서 확정된 상품은 REG_SAP테이블의 정보를 보여주기 위해 추가 2016.03.17 by song min kyo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpProductDetailInfoFix(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0020VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewTmpProductDetailInfoFix", paramMap);
	}

	/**
	 * 상품수정
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateNewProductMST(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProductMST", nEDMPRO0020VO);
	}

	/**
	 * 상품추가설명 수정
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateNewProductDesc(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProductDesc", nEDMPRO0020VO);
	}

	/**
	 * 상품수입정보 수정
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateNewImportInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewImportInfo", nEDMPRO0020VO);
	}

	/**
	 * 변형속성 이미지 정보 Delete
	 * @param nEDMPRO0024VO
	 * @throws Exception
	 */
	public void deleteSaleImg(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteSaleImg", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 이미지 정보 Insert
	 * @param nEDMPRO0024VO
	 * @throws Exception
	 */
	public void insertSaleImg(NEDMPRO0024VO nEDMPRO0024VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertSaleImg", nEDMPRO0024VO);
	}

	/**
	 * 변형속성 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteVarAttInfo(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteVarAttInfo", pgmId);
	}

	/**
	 * 분석속성 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void  deleteGrpAttInfo(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteGrpAttInfo", pgmId);
	}

	/**
	 * 오프라인 이미지 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteOffImgInfo(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteOffImgInfo", pgmId);
	}

	/**
	 * 상품의 오프라인 이미지 정보
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0025VO> selectNewProdOffImgInfo(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectNewProdOffImgInfo", pgmId);
	}

	/**
	 * 상품 변형속성 정보 복사
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdVarAttCopy(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdVarAttCopy", nEDMPRO0020VO);
	}

	/**
	 * 상품 변형속성 정보 복사
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdVarAttCopySellNotNull(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdVarAttCopySellNotNull", nEDMPRO0020VO);
	}

	/**
	 * 상품 분석속정 정보 복사
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdGrpAttCopy(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdGrpAttCopy", nEDMPRO0020VO);
	}

	/**
	 * 상품 오프라인 이미지 복사
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdOffImgCopy(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdOffImgCopy", nEDMPRO0020VO);
	}

	/**
	 * 이미지 테이블 협력업체 코드 수정
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateEntpCdOfSaleImg(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateEntpCdOfSaleImg", nEDMPRO0020VO);
	}

	/**
	 * 변형속성 테이블 협력업체 코드 수정
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateEntpCdOfVarAtt(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateEntpCdOfVarAtt", nEDMPRO0020VO);
	}

	/**
	 * 코리안넷을 통해 상품등록시 사업자번호로 협력사 존재유무 체크
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public int selectExistxKoreanNetVendor(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectExistxKoreanNetVendor", nEDMPRO0020VO);
	}

	/**
	 * 코리안넷 협력사 리스트
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectKoreanNetEntpCdList(Map<String, Object> paramMap) throws Exception {
		return (List<CommonProductVO>) getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectKoreanNetEntpCdList", paramMap);
	}

	/**
	 * 소분류에 따른 이미지등록제한관리 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0026VO selectProdEssentialInfo(Map<String, Object> paramMap) throws Exception {
		return (NEDMPRO0026VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectProdEssentialInfo", paramMap);
	}

	/**
	 * 등록된 신상품의 소분류의 표시단위, 표시기준수량 default 정보 존재 여부 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdEssentialExistInfo(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectNewProdEssentialExistInfo", paramMap);
	}

	/**
	 * 기본카테고리 목록(IN 조건)
	 * @MethodName  : selectCategoryInList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectCategoryInList",paramMap);
	}



	/* ****************************** */
	//20180821 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * @param newProductCode
	 * @return LIST
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode)  throws Exception {
		return (List<PEDMPRO0011VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectTpcPrdKeywordList", newProductCode);
	}

	/**
	 * 신규 상품키워드 등록/수정
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertUpdateTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.insertUpdateTpcPrdKeyword", bean);
	}

	/**
	 * 신규 상품키워드 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.insertTpcPrdKeyword", bean);
	}

	/**
	 * 신규 상품키워드 삭제
	 * @param MAP
	 * @return INT
	 * @throws Exception
	 */
	public int deleteTpcPrdKeyword(Map<String,Object> map) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.deleteTpcPrdKeyword", map);
	}

	/**
	 * 신규 상품키워드 전체 삭제
	 * @param String
	 * @return INT
	 * @throws Exception
	 */
	public int deleteAllTpcPrdKeyword(String pgmId) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.deleteAllTpcPrdKeyword", pgmId);
	}

	/**
	 * 신규 상품키워드 전체 조회
	 * @param VO
	 * @return LIST
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectChkTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return (List<PEDMPRO0011VO>) getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectChkTpcPrdTotalKeyword",bean);
	}

	/**
	 * 신규 상품키워드 전체 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.insertTpcPrdTotalKeyword", bean);
	}

	/**
	 * 신규 상품키워드 전체 수정
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int updateTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.updateTpcPrdTotalKeyword",bean);
	}

	/**
	 * 신규 상품키워드 등록/수정 및 상품키워드 전체(SEQ:000) 등록/수정/삭제
	 * @Method Name : mergeTpcPrdKeyword
	 * @param  newProduct
	 * @return void
	 */
	@SuppressWarnings("rawtypes")
	public void mergeTpcPrdKeyword(NEDMPRO0020VO nEDMPRO0020VO) {

		int resultCnt = 0;

		String pgmId = "";
		String regId = "";

		try {
			Iterator kewordIter = nEDMPRO0020VO.getKeywordList().iterator();

			while(kewordIter.hasNext()) {
				PEDMPRO0011VO pedmpro0011VO = (PEDMPRO0011VO)kewordIter.next();

				pgmId = pedmpro0011VO.getPgmId();
				regId = pedmpro0011VO.getRegId();

				// 키워드 입력
				resultCnt = this.insertUpdateTpcPrdKeyword(pedmpro0011VO);
			}

			if (resultCnt <= 0) {
				throw new TopLevelException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			PEDMPRO0011VO bean = new PEDMPRO0011VO();

			if(!pgmId.equals("") && !regId.equals("")) {
				bean.setPgmId(pgmId);
				bean.setRegId(regId);
			}

			// 전체 키워드 셋팅
			List<PEDMPRO0011VO> prdList = this.selectChkTpcPrdTotalKeyword(bean);

			if(!(prdList == null || prdList.size() == 0)) {
				PEDMPRO0011VO resultBean = (PEDMPRO0011VO)prdList.get(0);
	        	String totalKywrd = resultBean.getTotalKywrd();
	        	String seq = resultBean.getSeq();

	        	bean.setTotalKywrd(totalKywrd);

	        	if("000".equals(seq)) {
					//전체 키워드 업데이트
					resultCnt = this.updateTpcPrdTotalKeyword(bean);

					if(resultCnt <= 0) {
						throw new TopLevelException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else {
					//전체 키워드 입력
					resultCnt = this.insertTpcPrdTotalKeyword(bean);

					if(resultCnt <= 0) {
						throw new TopLevelException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}

	}
	//20180821 - 상품키워드 입력 기능 추가
	/* ****************************** */

	/**
	 * 온라인상품등록(일반) 상품키워드 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertStaffSellTpcPrdKeyword(Map<String, Object> keywordMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertStaffSellTpcPrdKeyword", keywordMap);
	}

	/**
	 * 상품 상세 이미지 처리 화면 변경
	 * 신상품 마스터 테이블 온라인 이미지 정보 수정
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateNewProdImgInfo(NewProduct newProduct) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateNewProdImgInfo", newProduct);
	}

	/**
	 * 상품 상세설명 중복 체크
	 * @param NewProduct
	 * @return
	 * @throws Exception
	 */
	public int selectProductDescription(NewProduct newProduct) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectProductDescription", newProduct);
	}

	/**
	 * 상품 상세 이미지 처리 화면 변경
	 * 상품 상세설명 수정
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateProductDescription(NewProduct newProduct) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.updateProductDescription", newProduct);
	}


	/**
	 * 상품별 EC 카테고리 정보 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public void insertEcProductCategory(EcProductCategory bean) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertEcProductCategory", bean);
	}

	/**
	 * EC 표준 카테고리 매핑
	 * @param paramMap
	 * @throws Exception
	 */
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception {
		return (DataMap) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectEcStandardCategoryMapping", martCatCd);
	}

	/**
	 * EC 상품 표준/전시카테고리 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectEcCategoryByProduct", pgmId);
	}

	/**
	 * EC전시카테고리 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertEcProductCategory(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertEcProductCategory", paramMap);
	}

	/**
	 * 상품별 EC 카테고리 정보 삭제
	 */

	public int deleteEcProductCategory(String pgmId) throws DataAccessException {
		return getSqlMapClientTemplate().delete("NEDMPRO0020.deleteEcProductCategory", pgmId);
	}

	/**
	 * 와이드 이미지 개수 구해오기
	 * @param String
	 * @throws Exception
	 */

	public String selectOnlineWideImageCnt(String pgmId) throws Exception {
		return (String)getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectOnlineWideImageCnt", pgmId);
	}

	/**
	 * 와이드 이미지 개수 업데이트
	 * @param DataMap
	 * @throws Exception
	 */

	public void updateOnlineWideImageCnt(Map<String, Object> paramMap) throws Exception {
		getSqlMapClientTemplate().queryForObject("NEDMPRO0020.updateOnlineWideImageCnt", paramMap);
	}


	/**
	 * EC 상품속성 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectEcProductAttr", paramMap);
	}

	/**
	 * EC상품(단품별)속성 삭제
	 * @param pgmId
	 * @throws Exception
	 */
	public void deleteEcProductAttribute(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteEcProductAttribute", pgmId);
	}

	/**
	 * EC상품(단품별)속성 복사
	 * @param DataMap
	 * @throws Exception
	 */
	public void insertEcProductAttribute(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertEcProductAttribute", paramMap);
	}


	/**
	 * 상품/단품별 EC 속성매핑
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public void insertEcProductAttribute(NewProduct newProduct) throws DataAccessException {

		Iterator itemIter = newProduct.getAttrbuteList().iterator();
		while(itemIter.hasNext()) {
			EcProductAttribute ecProdAttr = (EcProductAttribute)itemIter.next();

			ecProdAttr.setRegId(newProduct.getEntpCode());
			ecProdAttr.setProdCd(newProduct.getNewProductCode());
			ecProdAttr.setPgmId(newProduct.getNewProductCode());

			getSqlMapClientTemplate().insert("NEDMPRO0020.insertEcProductAttribute", ecProdAttr);
		}
	}

	/**
	 * Desc : 온라인 전용 상품 단품 리스트
	 * @Method Name : selectProductItemListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */
	@SuppressWarnings("unchecked")
	public List<ColorSize> selectProductItemListInTemp(String newProductCode)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectProductItemListInTemp", newProductCode);
	}

	/**
	 * 온라인전용 단품 등록
	 * @Method Name : insertProductItemInfo
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public void insertProductItemInfo(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator itemIter = newProduct.getItemList().iterator();
		while(itemIter.hasNext()) {
			ColorSize argItem = (ColorSize)itemIter.next();

			//협력업체 코드
			argItem.setEnterpriseCode(newProduct.getEntpCode());
			//상품명 varchar(50)
			argItem.setProductName(newProduct.getProductName());
			//상품 코드 varchar(19(
			argItem.setNewProductCode(newProduct.getNewProductCode());

			getSqlMapClientTemplate().insert("NEDMPRO0020.insertProductItemInfo", argItem);
		}

	}

	/**
	 * 온라인전용상품 단품 정보 전부 삭제
	 * @Method Name : deleteTempProductItemAll
	 * @param  newProductCode
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteTempProductItemAll(String newProductCode) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteTempProductItemAll", newProductCode);
	}

	/**
	 * 단푼 속성 정보(옵션별 가격 동일 유무 정보) Update
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void updateProdMgrYn(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.updateProdMgrYn", nEDMPRO0020VO);
	}

	/**
	 * 상품 EC속성 복사값 Insert
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertEcProductAttributeCopy(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertEcProductAttributeCopy", nEDMPRO0020VO);
	}

	/**
	 * 상품 EC속성 재고,가격 복사값 Insert
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public void insertNewProdVarEcAttrCopy(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertNewProdVarEcAttrCopy", nEDMPRO0020VO);
	}

	/**
	 * EC카테고리 값에 의한 EC속성의 개수
	 * @param paramMAp
	 * @return
	 * @throws Exception
	 */
	public int selectEcProductAttrCnt(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectEcProductAttrCnt", paramMap);
	}

	/**
	 * 입력한 EC속성의 개수
	 * @param paramMAp
	 * @return
	 * @throws Exception
	 */
	public int selectInputEcProductAttrCnt(String pgmId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectInputEcProductAttrCnt", pgmId);
	}
	
	public List<HashMap> selectNutInfoByL3Cd(String l1Cd) throws Exception {
	    return (List<HashMap>)getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectNutInfoByL3Cd", l1Cd);
	}
	
	public List<HashMap> selectNutInfoByPgmId(String pgmId) throws Exception {
	    return (List<HashMap>)getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectNutInfoByPgmId", pgmId);
	}
	
	public void deleteNutAttWithPgmId(String pgmId) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0020.deleteNutAttWithPgmId", pgmId);
	}
	
	public void mergeNutAttWithPgmId(HashMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0020.mergeNutAttWithPgmId", paramMap);
	}

	public String checkPbPartner(NEDMPRO0020VO vo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.checkPbPartner", vo);
	}

	public List<HashMap> displayEcFashionAttr(String stdCatCd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0020.displayEcFashionAttr", stdCatCd);
	}

	public List<HashMap> selectOspStandardCategoryMapping(String l3Cd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectOspStandardCategoryMapping", l3Cd);
	}

	public List<HashMap> selectOspCategorySaved(String pgmId) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0020.selectOspCategorySaved", pgmId);
	}

	/**
	 * 오카도 카테고리 입력
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertOspDispCategory(Map paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0020.insertOspDispCategory", paramMap);
	}

	/**
	 * 오카도 카테고리 삭제
	 */

	public int deleteOspDispCategory(String pgmId) throws DataAccessException {
		return getSqlMapClientTemplate().delete("NEDMPRO0020.deleteOspDispCategory", pgmId);
	}
	
	/**
	 * 슈퍼 직매입시 면세/과세 별 이익율 계산
	 * @param nEDMPRO0020VO
	 * @return String
	 * @throws Exception
	 */
	public String selectnewProdSprftRate(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectnewProdSprftRate", nEDMPRO0020VO);
	}

	/**
	 * 오카도 직매입시 면세/과세 별 이익율 계산
	 * @param nEDMPRO0020VO
	 * @return String
	 * @throws Exception
	 */
	public String selectnewProdOprftRate(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectnewProdOprftRate", nEDMPRO0020VO);
	}

	/**
	 * 슈퍼 과세 및 면세,영세 일경우 원가계산
	 * @param nEDMPRO0020VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSnorProdPcost(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectSnorProdPcost", nEDMPRO0020VO);
	}

	/**
	 * 오카도 과세 및 면세,영세 일경우 원가계산
	 * @param nEDMPRO0020VO
	 * @return String
	 * @throws Exception
	 */
	public String selectOnorProdPcost(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0020.selectOnorProdPcost", nEDMPRO0020VO);
	}
	
	/**
	 * 등록된 ESG 정보 마스터 Copy
	 * @param nEDMPRO0020VO
	 * @return int
	 * @throws Exception
	 */
	public int updateCopyProdEsgInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception {
		return getSqlMapClientTemplate().update("NEDMPRO0020.updateCopyProdEsgInfo", nEDMPRO0020VO);
	}
	
}
