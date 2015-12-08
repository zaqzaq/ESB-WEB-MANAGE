package com.zaq.esb.common;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BaseModel {
	private SqlRowSet rowSet;
	
	public BaseModel(){
		
	}
	public BaseModel(SqlRowSet rowSet){
		this.rowSet=rowSet;
	}
	public void setRowSet(SqlRowSet rowSet) {
		this.rowSet = rowSet;
	}

	public String get(String prop){
		return rowSet.getString(prop);
	}
}
