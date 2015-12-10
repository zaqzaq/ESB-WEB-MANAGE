<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.zaq.esb.common.BaseModel"%> 
<%@page import="java.util.List"%> 
<div class="btn-toolbar list-toolbar">
    <button class="btn btn-primary"><i class="fa fa-plus"></i> 新部署一个xml</button>
  <div class="btn-group">
  </div>
</div>
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>名称</th>
      <th>运行状态</th>
      <th>功能</th>
      <th>开启时间</th>
      <th>最后更新者</th>
      <th>备注</th>
      <th style="width: 3.5em;"></th>
    </tr>
  </thead>
  <tbody>
   <%
  	 	List<BaseModel> list=(List<BaseModel>)request.getAttribute("list");
   		int i=0;
   		for(BaseModel m:list){
   	%>
   		<tr>
	      <td><%=++i %></td>
	      <td><%=m.getStr("name") %></td>
	      <td><%=m.getInt("status")==1?"已部署":"未部署" %></td>
	      <td><%=m.getStr("flowFuns") %></td>
	      <td><%=m.getDate("timeStart") %></td>
	      <td><%=m.getStr("userLastUpdate")==null?"系统":m.getStr("userLastUpdate") %></td>
	      <td><%=m.getStr("remark")==null?"无":m.getStr("remark") %></td>
	      <td>
	          <a href="#" onclick="showPage('admin/app/xml/<%=m.getLong("id") %>')"><i class="fa fa-pencil"></i></a>
	          <a href="#myModal" role="button" data-toggle="modal"><i class="fa fa-trash-o"></i></a>
	      </td>
	    </tr>
   	<%		
   		}
   %>
  </tbody>
</table>

<!-- <ul class="pagination"> -->
<!--   <li><a href="#">&laquo;</a></li> -->
<!--   <li><a href="#">1</a></li> -->
<!--   <li><a href="#">2</a></li> -->
<!--   <li><a href="#">3</a></li> -->
<!--   <li><a href="#">4</a></li> -->
<!--   <li><a href="#">5</a></li> -->
<!--   <li><a href="#">&raquo;</a></li> -->
<!-- </ul> -->

<div class="modal small fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">Delete Confirmation</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="fa fa-warning modal-icon"></i>Are you sure you want to delete the user?<br>This cannot be undone.</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <button class="btn btn-danger" data-dismiss="modal">Delete</button>
        </div>
      </div>
    </div>
</div>