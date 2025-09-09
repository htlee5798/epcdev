package com.lottemart.epc.edi.comm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.dao.ImageLoadDao;
import com.lottemart.epc.edi.comm.service.ImageLoadService;

/**
 * 
 * @Class Name : ImageLoadServiceImpl.java
 * @Description :  이미지 공통 load 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.09.01		yun				최초생성
 *               </pre>
 */
@Service(value="imageLoadService")
public class ImageLoadServiceImpl implements ImageLoadService {
	
	@Autowired
	private ImageLoadDao imageLoadDao;

	/**
	 * ESG 이미지 정보 조회
	 */
	@Override
	public Map<String, Object> selectImageInfoProductEsg(Map<String, Object> paramMap) throws Exception {
		return imageLoadDao.selectImageInfoProductEsg(paramMap);
	}

	/**
	 * 신상품 입점제안 이미지 정보 조회
	 */
	@Override
	public Map<String, Object> selectImageInfoNewProdProp(Map<String, Object> paramMap) throws Exception {
		return imageLoadDao.selectImageInfoNewProdProp(paramMap);
	}

}
