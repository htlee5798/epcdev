package com.lottemart.epc.common.tag.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.common.model.OptionTagVO;

/**
 * @Class Name : TagLibraryDao.java
 * @Description : Tag Library에서 쓰는 DAO.
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 11. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository
public class TagLibraryDao extends AbstractDAO
{
	/**
	 * Desc : 코드그룹에 속한 코드의 리스트를 select해온다.
	 * (html의 select box를 만들때 쓰려구)
	 * @param codeGroupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OptionTagVO> selectCodeList(String codeGroupId)
	{
		return (List<OptionTagVO>)this.list("TagLibraryDao.selectCodeList", codeGroupId);
	}

}
