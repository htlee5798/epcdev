package com.lottemart.epc.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0020VO;

/**
 * 
 * @author projectBOS32
 * @Description : 상품관리 - 전상법관리
 * @Class : com.lottemart.epc.product.service
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
public interface PSCMPRD0030Service {
	/**
	 * 
	 * @see selectElecCommColList
	 * @Locaton : com.lottemart.epc.product.service
	 * @MethodName : selectElecCommColList
	 * @author : projectBOS32
	 * @Description : 전상법 컬럼 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<DataMap> selectElecCommColList(DataMap paramMap) throws Exception;

	/**
	 * 
	 * @see selectInfoGrpCdList
	 * @Locaton : com.lottemart.epc.product.service
	 * @MethodName : selectInfoGrpCdList
	 * @author : projectBOS32
	 * @Description : 전상법 상품분류 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<DataMap> selectInfoGrpCdList(DataMap paramMap) throws Exception;

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
	List<PSCPPRD0020VO> selectInfoGrpDetList(DataMap paramMap) throws Exception;
	
	
	/**
	 * Desc : 전상법 상품분류 상세 Cnt
	 * @Method Name : cntInfoGrpDetList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	int cntInfoGrpDetList(DataMap paramMap)throws Exception;
	

	/**
	 * 
	 * @see selectElecCommList
	 * @Locaton : com.lottemart.epc.product.service
	 * @MethodName : selectElecCommList
	 * @author : projectBOS32
	 * @Description : 전상법 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<DataMap> selectElecCommList(DataMap paramMap) throws Exception;

	/**
	 * 
	 * @see updateElecComm
	 * @Locaton : com.lottemart.epc.product.service
	 * @MethodName : updateElecComm
	 * @author : projectBOS32
	 * @Description : 전상법 수정
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateElecComm(HttpServletRequest request) throws Exception;

	/**
	 * 
	 * @see updateElecCommExcel
	 * @Locaton : com.lottemart.epc.product.service
	 * @MethodName : updateElecCommExcel
	 * @author : projectBOS32
	 * @Description : 전상법 엑셀 업로드 수정
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateElecCommExcel(List<DataMap> mapList,
			HttpServletRequest request) throws Exception;
}
