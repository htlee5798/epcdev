package com.lottemart.epc.edi.payment.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.payment.dao.PEDMPAY0030Dao;
import com.lottemart.epc.edi.payment.service.PEDMPAY0030Service;

@Service("pedmpay0030Service")
public class PEDMPAY0030ServiceImpl implements PEDMPAY0030Service{
	
	@Autowired
	private PEDMPAY0030Dao pedmpay0030Dao;
	

	public List selectPayCountData(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0030Dao.selectPayCountData(map );
	}
	
	
	public Integer selectPamentStayCount(Map<String,Object> map) throws Exception{ 
		return pedmpay0030Dao.selectPamentStayCount(map);
	}
	
	
	public boolean sendExecuteCommand(Map<String,Object> map) throws Exception {
		 return pedmpay0030Dao.sendExecuteCommand(map);
	}

}
