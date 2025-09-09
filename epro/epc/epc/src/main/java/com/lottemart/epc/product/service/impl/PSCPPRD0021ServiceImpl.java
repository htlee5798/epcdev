package com.lottemart.epc.product.service.impl;

import java.io.File;
import java.util.List;

import lcn.module.common.image.ImageUtilsThumbnail;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0021Dao;
import com.lottemart.epc.product.model.PSCPPRD0021VO;
import com.lottemart.epc.product.service.PSCPPRD0021Service;


/**
 * 
 * @author jyLim
 * @Class : com.lottemart.bos.product.service.impl
 * @Description : 통계 > 업체상품 수정요청
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *     수정일      	 	    수정자                                         수정내용
 *  -----------    	--------    ---------------------------
 * 2012. 3. 9.	 	jyLim
 * @version : 1.0
 * </pre>
 */
@Service("PSCPPRD0021Service")
public  class PSCPPRD0021ServiceImpl implements PSCPPRD0021Service{

	@Autowired
	private PSCPPRD0021Dao pscpprd0021dao;
	@Autowired
	private ConfigurationService config;
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.bos.product.service.PSCPPRD0021Service#selectProdImgChgHist(java.util.Map)
	 * @Locaton    : com.lottemart.bos.product.service.impl
	 * @MethodName  : selectProdImgChgHist
	 * @author     : jyLim
	 * @Description : 업체상품 수정요청 상세 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectProdImgChgHist(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdImgChgHist(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdDescChgHist(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdDescChgHist(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdAddChgHist(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdAddChgHist(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdMstChgHist(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdMstChgHist(paramMap);
	}
	
	/**
	 * Desc : 업체상품 반려
	 * @Method Name : displayBatchDelete
	 * @param PBOMPRD0041VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int prodAprvStatus(PSCPPRD0021VO pscpprd0021vo) throws Exception {
		
		int resultCnt = 0;

		try {
			//카테고리할당 비전시
			resultCnt = pscpprd0021dao.prodAprvStatus(pscpprd0021vo);
					
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}
	
	/**
	 * Desc : 업체상품 승인으로 인한 업데이트
	 * @Method Name : displayBatchDelete
	 * @param PBOMPRD0041VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int prodStatusUpdate(PSCPPRD0021VO pscpprd0021vo) throws Exception {
		int resultCnt = 0;

		try {
			if("001".equals(pscpprd0021vo.getTypeCd() )){

				//이미지 복사작업
				resultCnt = imageAprvInsert(pscpprd0021vo);
				
			}else if("002".equals(pscpprd0021vo.getTypeCd() )){
				
				resultCnt = pscpprd0021dao.prodDescStatusAprv(pscpprd0021vo);
			
			}else if("003".equals(pscpprd0021vo.getTypeCd() )){
			
				//전상법 삭제
				pscpprd0021dao.prodAddInfoStatusDelete(pscpprd0021vo);
				
				resultCnt = pscpprd0021dao.prodAddInfoStatusAprv(pscpprd0021vo);
			}else{
				resultCnt = 1;
			}
			
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}
	
	public int imageAprvInsert(PSCPPRD0021VO pscpprd0021vo)throws IllegalArgumentException {

		try{
		
		//Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		//FileInputStream file = new FileInputStream(config.getString("online.product.image.url")+pscpprd0021vo.getBosUrl());
		
		fileDeleteFnc(pscpprd0021vo.getProdCd(),pscpprd0021vo.getProdImgNo());
		String bosUrl = config.getString("online.product.imageTemp.url")+ "/" + pscpprd0021vo.getProdCd() + "/" + pscpprd0021vo.getSeq() + "_" + pscpprd0021vo.getProdImgNo() + ".jpg";
			
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"500"), 400,400);
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"250"), 220,220);
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"160"), 154,154);
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"100"), 100,100);
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"75"), 75,75);
		ImageUtilsThumbnail.resize(bosUrl, config.getString("online.product.image.url")+"/"+subFolderName(pscpprd0021vo.getProdCd(), pscpprd0021vo.getProdImgNo(),"60"), 60,60);
		
		}catch(Exception e){
			return 0;
		}
		
		return 1;
	}
	
	public String subFolderName(String imgName, String prodImgNo, String size) {
		return imgName.substring(0, 5) + "/" + imgName + "_" + prodImgNo + "_" + size + ".jpg";
	}
	
	public void fileDeleteFnc(String prodCd, String prodImgNo){
		File fileDeleted;
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"500"));
		fileDeleted.delete();
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"250"));
		fileDeleted.delete();
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"160"));
		fileDeleted.delete();
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"100"));
		fileDeleted.delete();
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"75"));
		fileDeleted.delete();
		
		fileDeleted = new File(config.getString("online.product.image.url")+"/"+ subFolderName(prodCd,prodImgNo,"60"));
		fileDeleted.delete();
	}
	
	@Override
	public DataMap selectProdDesc(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdDesc(paramMap);
	}
	
	@Override
	public DataMap selectProdInfoView(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdInfoView(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdAddBefore(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdAddBefore(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdAddAfter(DataMap paramMap) throws Exception {
		
		return pscpprd0021dao.selectProdAddAfter(paramMap);
	}
	
	//20180911 상품키워드 입력 기능 추가	
	@Override
	public List<DataMap> selectProdKeywordChgHist(DataMap paramMap) throws Exception {
		return pscpprd0021dao.selectProdKeywordChgHist(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdKeywordChgBefore(DataMap paramMap) throws Exception {
		return pscpprd0021dao.selectProdKeywordChgBefore(paramMap);
	}
	
	@Override
	public List<DataMap> selectProdKeywordChgAfter(DataMap paramMap) throws Exception {
		return pscpprd0021dao.selectProdKeywordChgAfter(paramMap);
	}	
	//20180911 상품키워드 입력 기능 추가

}
