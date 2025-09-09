package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.NEDMCST0030Dao;
import com.lottemart.epc.edi.consult.model.NEDMCST0030VO;
import com.lottemart.epc.edi.consult.service.NEDMCST0030Service;

@Service("nedmcst0030Service")
public class NEDMCST0030ServiceImpl implements NEDMCST0030Service{

	@Autowired
	private NEDMCST0030Dao nedmcst0030Dao;

	@Override
	public List selectVenCd(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.selectVenCd(map );
	}
	@Override
	public List<NEDMCST0030VO> alertPageInsertPageSelect(NEDMCST0030VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.alertPageInsertPageSelect(map );
	}
	@Override
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxEmailCk(map );
	}
	@Override
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxEmailCkUP(map );
	}
	@Override
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxCellCk(map );
	}
	@Override
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxCellCkUP(map );
	}
	@Override
	public List ajaxVendor(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxVendor(map );
	}
	@Override
	public List ajaxVendorCK(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.ajaxVendorCK(map );
	}

	/**
	 * 협업정보 - > 협업정보  - > 알리미 등록
	 */
	public String alertPageInsert(NEDMCST0030VO map) throws Exception {
		String[] ven_split = map.getVenCds();
		String svcSeq = "";

		//----- 서비스순번 Max Seq
		svcSeq = nedmcst0030Dao.selectPageInsertIDSeq(map);
		map.setSvcSeq(svcSeq);

		//----- 알리미 서비스 ID 등록
		nedmcst0030Dao.insertPageInsertID(map);

		for (int j = 0; j < ven_split.length; j++) {
			map.setVenCd(ven_split[j]);
			nedmcst0030Dao.insertPageInsertUser(map);
		}

		return svcSeq;
	}

	@Override
	public NEDMCST0030VO alertPageUpdatePage(NEDMCST0030VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0030Dao.alertPageUpdatePage(map );
	}
	@Override
	public void alertPageUpdate(NEDMCST0030VO map) throws Exception{

		String[] ven_split=map.getVenCds();

		nedmcst0030Dao.alertPageUpdateID(map );
		nedmcst0030Dao.alertPageDeleteUser(map);

		for(int j=0;j<ven_split.length;j++){
			map.setVenCd(ven_split[j]);
			nedmcst0030Dao.insertPageInsertUser(map);
		}

	}
	@Override
	public void alertPageDelete(NEDMCST0030VO map) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0030Dao.alertPageDeleteID(map );
		nedmcst0030Dao.alertPageDeleteUser(map );
	}


}



