package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class TeamVO implements Serializable{
	private static final long serialVersionUID = 2929814391989342271L;
	private String team_cd;
	private String team_nm;
	
	public String getTeam_cd() {
		return team_cd;
	}
	public void setTeam_cd(String team_cd) {
		this.team_cd = team_cd;
	}
	public String getTeam_nm() {
		return team_nm;
	}
	public void setTeam_nm(String team_nm) {
		this.team_nm = team_nm;
	}
}
