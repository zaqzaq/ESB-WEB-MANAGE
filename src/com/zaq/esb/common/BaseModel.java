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
		if(null!=propStore.get(prop)){
			return (String) propStore.get(prop);
		}else if(null!=rowSet){
			return rowSet.getString(prop);
		}else{
			return null;
		}
	}
	
	public Integer getInt(String prop){
		if(null!=propStore.get(prop)){
			return (Integer) propStore.get(prop);
		}else if(null!=rowSet){
			return rowSet.getInt(prop);
		}else{
			return null;
		}
	}
	
	public Long getLong(String prop){
		if(null!=propStore.get(prop)){
			return (Long) propStore.get(prop);
		}else if(null!=rowSet){
			return rowSet.getLong(prop);
		}else{
			return null;
		}
	}
	
	public Date getDate(String prop){
		if(null!=propStore.get(prop)){
			return (Date) propStore.get(prop);
		}else if(null!=rowSet){
			return rowSet.getDate(prop);
		}else{
			return null;
		}

	}
}
