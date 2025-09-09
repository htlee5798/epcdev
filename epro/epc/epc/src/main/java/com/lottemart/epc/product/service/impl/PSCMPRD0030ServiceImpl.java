package com.lottemart.epc.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.product.dao.PSCMPRD0030Dao;
import com.lottemart.epc.product.dao.PSCPPRD0005Dao;
import com.lottemart.epc.product.dao.PSCPPRD0020Dao;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCMPRD0030Service;

import lcn.module.common.util.StringUtil;

/**
 * 
 * @author projectBOS32
 * @Description : 상품관리 - 전상법관리
 * @Class : com.lottemart.epc.product.service.impl
 * 
 *        <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016.03.31  projectBOS32
 * @version :
 * </pre>
 */
@Service("pscmprd0030Service")
public class PSCMPRD0030ServiceImpl implements PSCMPRD0030Service {

	@Autowired
	private PSCMPRD0030Dao pscmprd0030Dao;

	@Autowired
	private PSCPPRD0020Dao PSCPPRD0020Dao;

	@Autowired
	private PSCPPRD0005Dao pscpprd0005Dao;

	/**
	 * 
	 * @see selectElecCommColList
	 * @Locaton : com.lottemart.epc.product.service.impl
	 * @MethodName : selectElecCommColList
	 * @author : projectBOS32
	 * @Description : 전상법 컬럼 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectElecCommColList(DataMap paramMap) throws Exception {
		return pscmprd0030Dao.selectElecCommColList(paramMap);
	}

	/**
	 * 
	 * @see selectInfoGrpCdList
	 * @Locaton : com.lottemart.epc.product.service.impl
	 * @MethodName : selectInfoGrpCdList
	 * @author : projectBOS32
	 * @Description : 전상법 상품분류 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectInfoGrpCdList(DataMap paramMap) throws Exception {
		return pscmprd0030Dao.selectInfoGrpCdList(paramMap);
	}

	/**
	 * Desc : 전상법 상품분류 상세 목록 조회
	 * 
	 * @Method Name : selectInfoGrpDescList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCPPRD0020VO> selectInfoGrpDetList(DataMap paramMap) throws Exception {
		return pscmprd0030Dao.selectInfoGrpDetList(paramMap);
	}
	
	/**
	 * Desc : 전상법 상품분류 cnt
	 * @Method Name : cntInfoGrpDetList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int cntInfoGrpDetList(DataMap paramMap)throws Exception{
		return pscmprd0030Dao.cntInfoGrpDetList(paramMap);
	}

	/**
	 * 
	 * @see selectElecCommList
	 * @Locaton : com.lottemart.epc.product.service.impl
	 * @MethodName : selectElecCommList
	 * @author : projectBOS32
	 * @Description : 전상법 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectElecCommList(DataMap paramMap) throws Exception {
		List<DataMap> colList = pscmprd0030Dao.selectElecCommColList(paramMap);

		String sql = "";

		for (int i = 0; i < colList.size(); i++) {
			DataMap colMap = colList.get(i);

			sql += ",MAX(DECODE(INFO_COL_CD," + colMap.getString("INFO_COL_CD")
					+ ",COL_VAL,'')) AS COL_" + (i + 1)
					+ ",MAX(DECODE(INFO_COL_CD,"
					+ colMap.getString("INFO_COL_CD")
					+ ",INFO_COL_CD,"
					+ "'"+ colMap.getString("INFO_COL_CD") + "'"
					+ ")) AS CODE_" + (i + 1);
		}

		paramMap.put("sqlVal", SecureUtil.sqlValid(sql));
		List<DataMap> valList = pscmprd0030Dao.selectElecCommValList(paramMap);

		return valList;
	}

	/**
	 * 
	 * @see updateElecComm
	 * @Locaton : com.lottemart.epc.product.service.impl
	 * @MethodName : updateElecComm
	 * @author : projectBOS32
	 * @Description : 전상법 수정
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateElecComm(HttpServletRequest request) throws Exception {

		DataMap paramMap = new DataMap(request);

		int resultCnt = 0;

		String[] prodCds = request.getParameterValues("PROD_CD");
		String[] infoGrpCds = request.getParameterValues("INFO_GRP_CD");
		String vendorId = request.getParameter("vendorId");

		String seq = "";

		//20180719 - 전상법관리 한건이상 저장할 경우 오류나는 문제 수정
		if (prodCds != null) {
			for (int i = 0; i < prodCds.length; i++) {

				DataMap mapVal = new DataMap();
				mapVal.put("prodCd", prodCds[i]);
				mapVal.put("typeCd", "003");
				mapVal.put("vendorId", vendorId);

				Map<String, String> prmMap = new HashMap<String, String>();
				prmMap.put("prodCd", prodCds[i]);
				prmMap.put("typeCd", "003");
				prmMap.put("vendorId", vendorId);

				int iCnt = pscpprd0005Dao.selectPrdMdAprvMstCnt(mapVal);

				if (iCnt > 0) {
					PSCPPRD0005VO prdMdAprInfo = pscpprd0005Dao.selectPrdMdAprvMst(prmMap);
					seq = prdMdAprInfo.getSeq();
				} else {
					PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
					bean2.setProdCd(prodCds[i]);

					bean2.setRegId(vendorId);
					bean2.setAprvCd("001");
					bean2.setTypeCd("003");
					bean2.setVendorId(vendorId);

					seq = pscpprd0005Dao.insertPrdMdAprvMst(bean2);
				}

				prmMap.put("seq", seq);
				PSCPPRD0020Dao.deletePrdCommerceHist(prmMap);

				paramMap.put("infoGrpCd", infoGrpCds[i]);
				List<DataMap> colList = pscmprd0030Dao.selectElecCommColList(paramMap);

				String mode = paramMap.getString("mode"); // 저장

				PSCPPRD0020VO bean = new PSCPPRD0020VO();

				bean.setSeq(seq);
				bean.setNewProdCd(prodCds[i]); // 상품코드
				bean.setInfoGrpCd(infoGrpCds[i]); // 그룹코드
				bean.setRegId(request.getAttribute("modId").toString()); // 등록자

				for (int j = 0; j < colList.size(); j++) {
					DataMap colMap = colList.get(j);
					String[] codes = request.getParameterValues("CODE_" + (j + 1));
					String[] colVals = request.getParameterValues("COL_" + (j + 1));

					if (colMap.get("INFO_COL_CD").equals(codes[i])) {
						bean.setInfoColCd(codes[i]); // 컬럼코드
						bean.setColVal(colVals[i]); // 컬럼값
						if (StringUtil.getByteLength(bean.getColVal()) > 2000) {
							throw new IllegalArgumentException("입력값이 2000 Byte 초과 입니다. 2000 Byte 이하로 입력해주세요.");
						}

						if ("update".equals(mode)) { // 수정(저장)
							resultCnt += PSCPPRD0020Dao.insertPrdCommerceHist(bean);
						}
					}
				}
			}
		}

		return resultCnt;
	}

	/**
	 * 
	 * @see updateElecComm
	 * @Locaton : com.lottemart.epc.product.service.impl
	 * @MethodName : updateElecComm
	 * @author : projectBOS32
	 * @Description : 전상법 수정
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateElecCommExcel(List<DataMap> mapList, HttpServletRequest request) throws Exception {

		DataMap paramMap = new DataMap(request);

		int resultCnt = 0;

		String seq = "";

		//20180719 - 전상법관리 한건이상 저장할 경우 오류나는 문제 수정
		if (mapList != null) {
			for (int i = 0; i < mapList.size(); i++) {

				String prodCd = mapList.get(i).getString("PROD_CD");
				String vendorId = request.getParameter("vendorId");

				DataMap mapVal = new DataMap();
				mapVal.put("prodCd", prodCd);
				mapVal.put("typeCd", "003");
				mapVal.put("vendorId", vendorId);

				Map<String, String> prmMap = new HashMap<String, String>();
				prmMap.put("prodCd", prodCd);
				prmMap.put("typeCd", "003");
				prmMap.put("vendorId", vendorId);

				int iCnt = pscpprd0005Dao.selectPrdMdAprvMstCnt(mapVal);

				if (iCnt > 0) {
					PSCPPRD0005VO prdMdAprInfo = pscpprd0005Dao.selectPrdMdAprvMst(prmMap);
					seq = prdMdAprInfo.getSeq();
				} else {
					PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
					bean2.setProdCd(prodCd);

					bean2.setRegId(vendorId);
					bean2.setAprvCd("001");
					bean2.setTypeCd("003");
					bean2.setVendorId(vendorId);

					seq = pscpprd0005Dao.insertPrdMdAprvMst(bean2);
				}

				prmMap.put("seq", seq);
				PSCPPRD0020Dao.deletePrdCommerceHist(prmMap);

				List<DataMap> colList = pscmprd0030Dao.selectElecCommColList(paramMap);

				PSCPPRD0020VO bean = new PSCPPRD0020VO();

				DataMap inputMap = mapList.get(i);

				bean.setSeq(seq);
				bean.setNewProdCd(inputMap.getString("PROD_CD")); // 상품코드
				bean.setInfoGrpCd(inputMap.getString("INFO_GRP_CD")); // 그룹코드
				bean.setRegId(request.getAttribute("modId").toString()); // 등록자

				if (inputMap.getString("PROD_CD") != null && inputMap.getString("INFO_GRP_CD") != null
						&& !"".equals(inputMap.getString("PROD_CD")) && !"".equals(inputMap.getString("INFO_GRP_CD"))) {

					for (int j = 0; j < colList.size(); j++) {
						DataMap colMap = colList.get(j);

						if (colMap.get("INFO_COL_CD").equals(inputMap.getString("CODE_" + (j + 1)))) {
							bean.setInfoColCd(inputMap.getString("CODE_" + (j + 1))); // 컬럼코드
							bean.setColVal(inputMap.getString("COL_" + (j + 1))); // 컬럼값
							if (StringUtil.getByteLength(bean.getColVal()) > 2000) {
								throw new IllegalArgumentException("입력값이 2000 Byte 초과 입니다. 2000 Byte 이하로 입력해주세요.");
							}

							resultCnt += PSCPPRD0020Dao.insertPrdCommerceHist(bean);
						}
					}

				}
			}
		}

		return resultCnt;
	}

}
