package com.zaq.esb.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.zaq.esb.common.BaseModel;
import com.zaq.esb.common.BaseService;

@Service
@Scope("prototype")
public class AdminUserService extends BaseService{
	private static final String getByUserName="select username,password,fullname from admin_user where username=?";
	
	public BaseModel getByUserName(String username){
		SqlRowSet rowSet= jdbcTemplate.queryForRowSet(getByUserName, username);
		if(rowSet.next()){
			return new BaseModel(rowSet);
		}else{
			return null;
		}
	}
	
}
