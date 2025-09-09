package com.lottemart.epc.edi.consult.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0010Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0010Service;

@Service("pedmsct0010Service")
public class PEDMSCT0010ServiceImpl implements PEDMSCT0010Service{
	
	@Autowired
	private PEDMSCT0010Dao pedmsct0010Dao;
	
	
	public List orderStopResultSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0010Dao.orderStopResultSelect(map );
	}
	
	
}
