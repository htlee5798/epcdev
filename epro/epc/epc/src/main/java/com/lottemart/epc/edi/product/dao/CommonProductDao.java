package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.CommonProductVO;

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

@Repository("commonProductDao")
public class CommonProductDao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	
	
	/**
	 * 신상품 장려금 대상 업체인지 체크
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Integer selectNcheckCountVendorNewPromoFg(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectNcheckCountVendorNewPromoFg", paramMap);
	}
	
	
	
	/**
	 * 거래중지된 업체인지 체크
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Integer selectNcheckCountVendorStopTrading(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectNcheckCountVendorStopTrading", paramMap);
	}
	
	/**
	 * 협력업체 거래유형조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectNVendorTradeType(Map<String, Object> paramMap) throws Exception {				
		return (HashMap) getSqlMapClientTemplate().queryForObject("CommonProduct.selectNVendorTradeType", paramMap);
	}
	
	/**
	 * 협력업체 과세구분조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectNVendorTaxType(Map<String, Object> paramMap) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("CommonProduct.selectNVendorTaxType", paramMap);
	}
	
	/**
	 * 팀 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNteamList(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNteamList", paramMap);
	}
	
	/**
	 * ECS 사업자별 팀 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectEcsNteamList(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectEcsNteamList", paramMap);
	}
	
	/**
	 * 해당팀의 대분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL1list(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNgetL1list", paramMap);
	}
	
	/**
	 * 대분류의 중분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL2list(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNgetL2list", paramMap);
	}
	
	/**
	 * 대분류 의 중분류 조회 (팀X)
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> NselectNoTeamL2List(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.NselectNoTeamL2List", paramMap);
	}
	
	/**
	 * 중분류의 소분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetL3list(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNgetL3list", paramMap);
	}
	
	/**
	 * 중분류의 소분류 조회 (팀X)
	 * @param paramMap
	 * @return List<CommonProductVO>
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetNoTeamL3List(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNgetNoTeamL3List", paramMap);
	}
	
	
	/**
	 * 소분류의 그룹소분류 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNgetGrplist(Map<String, Object> paramMap)	throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNgetGrplist", paramMap);
	}
	
	/**
	 * 계절 년도 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNseasonYear(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNseasonYear", paramMap);
	}
		
	/**
	 * 계절리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectNseasonList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectNseasonList", paramMap);
	}
	
	/**
	 * 전상법 콤보박스 변경시 해당그룹의 리스트 조회
	 * @param infoGrpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdAddTemplateDetailList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectProdAddTemplateDetailList", paramMap);
	}
	
	/**
	 * KC인증 콤보박스 변경시 해당그룹의 리스트 조회
	 * @param infoGrpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateDetailList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectProdCertTemplateDetailList", paramMap);
	}
	
	/**
	 * 대분류 변경으로 전자상거래 콤보박스 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdAddTemplateList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectProdAddTemplateList", paramMap);
	}
	
	/**
	 * 대분류 변경으로 KC인증 콤보박스 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectProdCertTemplateList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectProdCertTemplateList", paramMap);
	}
	
	/**
	 * 소분류에 매핑되어 있는 변형속성 카운트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectL3CdVarAttCnt(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectL3CdVarAttCnt", paramMap);
	}
	
	/**
	 * 2016.08.29 추가
	 * 001여부 확인
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectVarAttCnt001(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectVarAttCnt001", paramMap);
	}
	
	/**
	 * 판매코드 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectSrcmkCdList(CommonProductVO paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectSrcmkCdList", paramMap);
	}
	
	public int selectSrcmkCdListCount(CommonProductVO paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectSrcmkCdListCount", paramMap);
	}
	
	
	/**
	 * ECS 수신 담당자 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selEcsReceiverPopupInfo(CommonProductVO paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selEcsReceiverPopupInfo", paramMap);
	}
	
	public int selEcsReceiverPopupInfoCount(CommonProductVO paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selEcsReceiverPopupInfoCount", paramMap);
	}
	
	/**
	 * 점포코드 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectStrCdList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectStrCdList", paramMap);
	}
	
	public int selectStrCdListCount(Map<String, Object> paramMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommonProduct.selectStrCdListCount", paramMap);
	}

	/**
	 * 팀정보 및 대분류 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectTeamL1CdList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectTeamL1CdList", paramMap);
	}
	
	/**
	 *  통화구분 공통코드 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectWaersCdList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectWaersCdList", paramMap);
	}
	
	
	/**
	 * 공통코드 htmlcodeTag 한번에 조회 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> selectCodeTagList(Map<String, Object> paramMap)throws Exception{
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectCodeTagList", paramMap);
	}
	
	/**
	 * 파트너사 코드 별 거래조직 확인
	 * @param paramVo
	 * @return CommonProductVO
	 * @throws Exception
	 */
	public CommonProductVO selectVendorZzorgInfo(CommonProductVO paramVo) throws Exception {
		return (CommonProductVO) getSqlMapClientTemplate().queryForObject("CommonProduct.selectVendorZzorgInfo", paramVo);
	}
	
	/**
	 * 파트너사가 사용할 수 있는 구매조직 필터링
	 * @param paramVo
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> selectVenPurDeptInfo(CommonProductVO paramVo) throws Exception {
		return getSqlMapClientTemplate().queryForList("CommonProduct.selectVenPurDeptInfo", paramVo);
	}
	
	/**
	 * 구매조직별 ECS 수신 계열사 정보 조회
	 * @param paramVo
	 * @return CommonProductVO
	 * @throws Exception
	 */
	public CommonProductVO selectEcsRecvCompInfo(CommonProductVO paramVo) throws Exception {
		return (CommonProductVO) getSqlMapClientTemplate().queryForObject("CommonProduct.selectEcsRecvCompInfo", paramVo);
	}
	
	/**
	 * ECS 계약/공문 양식정보 조회
	 * @param paramVo
	 * @return Map<String,String>
	 * @throws Exception
	 */
	public Map<String,String> selectEcsDocInfo(Map<String,String> paramMap) throws Exception {
		return (Map<String,String>) getSqlMapClientTemplate().queryForObject("CommonProduct.selectEcsDocInfo", paramMap);
	}
}

