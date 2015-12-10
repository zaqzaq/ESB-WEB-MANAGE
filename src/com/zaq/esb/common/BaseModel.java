package com.zaq.esb.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BaseModel {
	private SqlRowSet rowSet;
	private Map<String, Object> propStore=new HashMap<String, Object>();
	public BaseModel(){
	}
	public BaseModel(SqlRowSet rowSet){
		this.rowSet=rowSet;
	}
	
	public BaseModel(Map<String, Object> propStore){
		this.propStore=propStore;
	}
	
	public void set(String name,Object value){
		propStore.put(name, value);
	}
	
	public void setRowSet(SqlRowSet rowSet) {
		this.rowSet = rowSet;
	}

	public String getStr(String prop){
		if(null==rowSet){
			return null==propStore.get(prop)?null:(String)propStore.get(prop);
		}else{
			return rowSet.getString(prop);
		}
	}
	
	public Integer getInt(String prop){
		if(null==rowSet){
			return null==propStore.get(prop)?null:(Integer) propStore.get(prop);
		}else{
			return rowSet.getInt(prop);
		}
	}
	
	public Long getLong(String prop){
		if(null==rowSet){
			return null==propStore.get(prop)?null:(Long) propStore.get(prop);
		}else{
			return rowSet.getLong(prop);
		}
	}
	
	public Date getDate(String prop){
		if(null==rowSet){
			return null==propStore.get(prop)?null:(Date) propStore.get(prop);
		}else{
			return rowSet.getDate(prop);
		}
	}
}
