package com.lottemart.epc.common.model;

import java.io.Serializable;


public class OptionTagVO extends BaseModel implements Serializable
{
	private static final long serialVersionUID = -3190209150233653535L;
	
	public String name;
	public String code;
	
	public OptionTagVO()
	{
		super();
	}
	public OptionTagVO(String name, String code)
	{
		super();
		this.name = name;
		this.code = code;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Override
	public String toString()
	{
		return "OptionTagVO [name=" + name + ", code=" + code + "]";
	}
		
}
