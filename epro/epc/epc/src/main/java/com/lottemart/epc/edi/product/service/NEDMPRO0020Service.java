package com.lottemart.epc.edi.product.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import org.springframework.dao.DataAccessException;

/**
 * @Class Name : NEDMPRO0020Service
 * @Description : 신상품등록(온오프) Service
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26		SONG MIN KYO	최초생성
 *               </pre>
 */

public interface NEDMPRO0020Service {

	/**
	 * 신상품 등록 PGM_ID구해오기
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String selectNewProductPgmId(String str) throws Exception;

	/**
	 * 신규상품 등록
	 * 
	 * @param nEDMPRO0020VO
	 * @throws Exception
	 */
	public Map<String, Object> insertNewProdMst(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 신상품 마스터 테이블 온라인 이미지 개수 수정
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCnt(Map<String, Object> paramMap) throws Exception;

	/**
	 * 신상품 마스터 동영상URL 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdMovieUrl(Map<String, Object> paramMap) throws Exception;

	/**
	 * 신상품 마스터 동영상URL 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectNewProdMovieUrl(Map<String, Object> paramMap) throws Exception;

	/**
	 * 신상품 마스터 테이블 온라인 이미지 삭제 후 이미지 카운트 -1
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateNewProdImgCntMinus(Map<String, Object> paramMap) throws Exception;

	/**
	 * 상품의 변형속성 카운트
	 * 
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdVarAttCnt(String pgmId, String cfmFg) throws Exception;

	/**
	 * 상품정보 가져오기
	 * 
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectProdInfo(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 소분류별 변형속성 그룹(2015.12.30 이전 버전)
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	// public List<NEDMPRO0024VO> selectAttGrp(NEDMPRO0024VO nEDMPRO0024VO) throws
	// Exception;

	/**
	 * 소분류별 변형속성 그룹 옵션(2015.12.30 이전 버전)
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	// public List<NEDMPRO0024VO> selectAttGrpOpt(NEDMPRO0024VO nEDMPRO0024VO)
	// throws Exception;

	/**
	 * 변형속성 그룹별 상세 속성(2015.12.30 이전 버전)
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	// public List<NEDMPRO0024VO> selectAttGrpOptVar(NEDMPRO0024VO nEDMPRO0024VO)
	// throws Exception;

	/**
	 * 소분류별 PROFILE 가져오기(Class)
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * PROFILE별 변형속성 가져오기
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAtt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 변형속성 Option 가져오기
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectClassVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 88코드 중복 체크
	 * 
	 * @param sellCd
	 * @return
	 * @throws Exception
	 */
	public int selectSellCdCount(String sellCd) throws Exception;

	/**
	 * 판매코드로 위해상품으로 등록된 판매코드인지 조회 [현재 사용안함 2016.04.08 쿼리로 변경됨]
	 * 
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public int selectChkDangerProdCnt(String sellCode) throws Exception;

	/**
	 * 판매코드가 위해상품으로 등록된 판매코드인지 조회 추가 [IF_DT 가 최근일자 기준으로 CFM_FG 컬럼값이 '2' 일 경우 위해상품의
	 * 판매코드로 결정] 2016.04.08 추가 by song min kyo
	 * 
	 * @param sellCode
	 * @return
	 * @throws Exception
	 */
	public String selectChkDangerProdCnt_3(String sellCode) throws Exception;

	/**
	 * 변형속성 저장
	 * 
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveVarAtt(NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception;

	/**
	 * 등록된 변형속성의 CLASS 조회
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public String selectProdVarAttClass(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 등록된 변형속성의 전체 속성정보 조회
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 등록된 상품변형속성 조회
	 * 
	 * @param nEDMPRO0024VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectProdVarAtt(String pgmId, String cfmFg) throws Exception;

	/**
	 * 2060804 사용안함 selectGrpAtt->selectGrpAttOne 소분류별 그룹분석속성 조회
	 * 
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAtt(String prodRange) throws Exception;

	/**
	 * 소분류별 그룹분석속성 조회
	 * 
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * 
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOpt(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 소분류별 그룹분석속성 속성값 Option 조회
	 * 
	 * @param prodRange
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0024VO> selectGrpAttOptOne(NEDMPRO0024VO nEDMPRO0024VO) throws Exception;

	/**
	 * 그룹분석속성 저장
	 * 
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveGrpAtt(NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception;

	/**
	 * 상품 마스터 정보 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpProductDetailInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 온라인전용 상품 마스터 정보 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0020VO selectNewTmpOnlineProductDetailInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 상품수정
	 * 
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateNewProductMST(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 오프라인 이미지 저장
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void saveSaleImg(HttpServletRequest request) throws Exception;

	/**
	 * 오프라인 이미지 일괄저장
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveSaleImgAllApply(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;

	/**
	 * 상품정보 복사
	 * 
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertNewProductCopyMST(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 코리안넷을 통해 상품등록시 사업자번호로 협력사 존재유무 체크
	 * 
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public int selectExistxKoreanNetVendor(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 상품소분류에 변형속성 유무 체크
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectVarAttCnt(Map<String, Object> paramMap) throws Exception;

	/**
	 * 코리안넷 협력사 리스트
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectKoreanNetEntpCdList(Map<String, Object> paramMap) throws Exception;

	/**
	 * 소분류에 따른 이미지등록제한관리 정보 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectProdEssentialInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 등록된 신상품의 소분류의 표시단위, 표시기준수량 default 정보 존재 여부 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectNewProdEssentialExistInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 기본카테고리 목록(IN 조건)
	 * 
	 * @Method Name : selectCategoryInList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws Exception;

	/* ****************************** */
	// 20180821 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * 
	 * @param newProductCode
	 * @return List<PEDMPRO0003VO>
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode) throws Exception;

	/**
	 * 신규 상품키워드 등록
	 * 
	 * @return int
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception;

	/**
	 * 신규 상품키워드 삭제
	 * 
	 * @return INT
	 * @throws Exception
	 */
	public Map<String, Object> deleteTpcPrdKeyword(Map<String, Object> map) throws Exception;
	// 20180821 - 상품키워드 입력 기능 추가
	/* ****************************** */

	/**
	 * 신상품 마스터 테이블 온라인 이미지 정보 수정
	 * 
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateNewProdImgInfo(NewProduct newProduct) throws Exception;

	/**
	 * 상품 상세설명 중복 체크
	 * 
	 * @param NewProduct
	 * @return
	 * @throws Exception
	 */
	public int selectProductDescription(NewProduct newProduct) throws Exception;

	/**
	 * 상품 상세설명 수정
	 * 
	 * @param NewProduct
	 * @throws Exception
	 */
	public void updateProductDescription(NewProduct newProduct) throws Exception;

	/**
	 * EC 표준 카테고리 매핑
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public DataMap selectEcStandardCategoryMapping(String martCatCd) throws Exception;

	/**
	 * EC 전시 카테고리 정보
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcCategoryByProduct(String pgmId) throws Exception;

	/**
	 * 와이드 이미지 개수 조회
	 * 
	 * @param String
	 * @throws Exception
	 */
	public String selectOnlineWideImageCnt(String pgmId) throws Exception;

	/**
	 * 와이드 이미지 개수 업데이트
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateOnlineWideImageCnt(Map<String, Object> paramMap) throws Exception;

	/**
	 * EC 속성 정보
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectEcProductAttr(DataMap paramMap) throws Exception;

	/**
	 * 등록한 EC 속성 정보
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<ColorSize> selectProductItemListInTemp(String newProductCode);

	/**
	 * EC속성 저장
	 * 
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveEcAtt(NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception;

	/**
	 * 소분류값에 의한 EC 속성의 개수
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectEcProductAttrCnt(Map<String, Object> paramMap) throws Exception;

	/**
	 * 입력한 EC 속성의 개수
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectInputEcProductAttrCnt(String pgmId) throws Exception;
	
	/**
	 * 대분류에 해당하는 영양성분 속성값 조회
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutInfoByL3Cd(String l3cd) throws Exception;
	
	/**
	 * 가등록상품정보에서 등록한 영양성분속성값 조회
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutInfoByPgmId(String pgmId) throws Exception;
	
	/**
	 * 가등록상품정보에서 등록한 영양성분속성값 삭제
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public void deleteNutAttWithPgmId(String pgmId) throws Exception;
	
	/**
	 * 가등록상품정보에서 등록한 영양성분속성값 등록 및 수정
	 * 
	 * @param NEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public void mergeNutAttWithPgmId(NEDMPRO0020VO nEDMPRO0020VO) throws Exception;

	/**
	 * 선택한 협력업체가 PB업체인지 확인
	 *
	 * @param NEDMPRO0020VO
	 * @return String
	 * @throws Exception
	 */
	public String checkPbPartner(NEDMPRO0020VO nedmpro0020VO) throws Exception;

	/**
	 * EC패션 속성값 페이지 전시 유무
	 *
	 * @param String
	 * @return List<HashMap>
	 * @throws Exception
	 */
	public List<HashMap> displayEcFashionAttr(String stdCatCd) throws Exception;

	/**
	 * OSP 카테고리 매핑
	 *
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectOspStandardCategoryMapping(String l3Cd) throws Exception;

	/**
	 * OSP 저장된 카테고리
	 *
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectOspCategorySaved(String pgmId) throws Exception;

	/**
	 * 오카도 카테고리 입력
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertOspDispCategory(Map paramMap) throws Exception;

	/**
	 * 오카도 카테고리 삭제
	 */
	public int deleteOspDispCategory(String pgmId) throws Exception;;
}
