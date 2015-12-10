package com.zaq.esb.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zaq.esb.common.BaseModel;
import com.zaq.esb.common.BaseService;

@Service("appInfoService")
public class AppInfoService extends BaseService {
	private static final String getByFilePath = "select id,parentId,filePath,name,remark,status,timeStart,userLastUpdate,flowFuns,isDel from app_info where filePath=?";
	private static final String listApp = "select id,parentId,filePath,name,remark,status,timeStart,userLastUpdate,flowFuns,isDel from app_info where parentId is null";
	private static final String getByApp= "select id,parentId,filePath,name,remark,status,timeStart,userLastUpdate,flowFuns,isDel from app_info where parentId = ?";
	private static final String getById= "select id,parentId,filePath,name,remark,status,timeStart,userLastUpdate,flowFuns,isDel from app_info where id = ?";

	private static final String sqlInsert = "INSERT INTO app_info (parentId,filePath,status,timeStart,flowFuns,userLastUpdate,name,isDel)" 
													+ " VALUES (?,?,?,?,?,?,?,0)";

	public static final String sqlUpdate="UPDATE app_info SET name=? ,isDel=?, remark=? ,status=?,timeStart=?,userLastUpdate=?,flowFuns=? where filePath=?  ";
	public static final String sqlDelete="UPDATE app_info SET isDel=1  WHERE filePath=?  ";

	public BaseModel getByFilePath(String filePath) {
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(getByFilePath, filePath);
		if (rowSet.next()) {
			return new BaseModel(rowSet);
		} else {
			return null;
		}
	}
	public BaseModel getById(Long id) {
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(getById, id);
		if (rowSet.next()) {
			return new BaseModel(rowSet);
		} else {
			return null;
		}
	}
	public List<BaseModel> getByApp(Long appId) {
		List<BaseModel> list=new ArrayList<>();
		
		List<Map<String,Object>>  listMap = jdbcTemplate.queryForList(getByApp,appId);
		for(Map<String,Object> map:listMap){
			
			list.add(new BaseModel(map));
		}
		
		return list;
	}
	
	
	public List<BaseModel> listApp(){
		List<BaseModel> list=new ArrayList<>();
		
		List<Map<String,Object>>  listMap = jdbcTemplate.queryForList(listApp);
		for(Map<String,Object> map:listMap){
			
			list.add(new BaseModel(map));
		}
		
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void update(final BaseModel appInfo) {
		jdbcTemplate.update(sqlUpdate, appInfo.getStr("name"), appInfo.getInt("isDel"), appInfo.getStr("remark"), appInfo.getInt("status"),
								appInfo.getDate("timeStart"), appInfo.getStr("userLastUpdate"), appInfo.getStr("flowFuns"), appInfo.getStr("filePath"));
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void del(final BaseModel appInfo) {
		jdbcTemplate.update(sqlDelete, appInfo.getStr("filePath"));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void add(final BaseModel appInfo) {
		// 创建一个主键持有者
		KeyHolder genKey = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
				if(null!=appInfo.getLong("parentId")){
					ps.setLong(1,appInfo.getLong("parentId") );
				}else{
					ps.setNull(1, Types.BIGINT);
				}
				
				ps.setString(2, appInfo.getStr("filePath"));
				ps.setInt(3, appInfo.getInt("status"));
				ps.setDate(4, new java.sql.Date(appInfo.getDate("timeStart").getTime()));
				
				if(null!=appInfo.getStr("flowFuns")){
					ps.setString(5, appInfo.getStr("flowFuns"));
				}else{
					ps.setNull(5, Types.VARCHAR);
				}
				if(null!=appInfo.getStr("userLastUpdate")){
					ps.setString(6, appInfo.getStr("userLastUpdate"));
				}else{
					ps.setNull(6, Types.VARCHAR);
				}
				if(null!=appInfo.getStr("name")){
					ps.setString(7, appInfo.getStr("name"));
				}else{
					ps.setString(7, "未命名");
				}
				return ps;
			}
		}, genKey);
		appInfo.set("id", genKey.getKey().longValue());
	}

}
