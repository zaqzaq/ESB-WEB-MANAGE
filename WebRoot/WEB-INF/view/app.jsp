<%@page import="com.zaq.esb.common.Constans"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.zaq.esb.common.BaseModel"%> 
<%@page import="java.util.List"%> 
<script src="resources/js/App.js" type="text/javascript"></script>	
<script>
	$(function() {
		//重要，需要更新控件的附加参数内容，以及图片初始化显示
		$("#uploadInputFile").fileinput('refresh',{
			language: 'zh', //设置语言
			showUpload:false,
			enctype: 'multipart/form-data',
            showUpload: true, //是否显示上传按钮
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式             
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
		    uploadUrl: "admin/app/upload?appId=${appId }",
		    allowedFileExtensions: ["xml"]
        });
	 });
</script>
<div class="btn-toolbar list-toolbar">
  <div class="btn-group">
  	<a class="btn btn-primary" href="#uploadFile" data-toggle="modal" class="padding-right-small"><i class="fa fa-plus"></i> 新部署xml </a>
  </div>
</div>
<input type="hidden" id="appId" value="${appId }" />
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
	      <% if(m.getInt("isDel").intValue()==Constans.DEL_Y){%>
	    	    <td style="text-decoration:line-through;color:red"><%=m.getStr("name") %></td>
    	  <% }else{%>	 
    		<td id="xmlName_<%=m.getLong("id") %>"><%=m.getStr("name") %></td>
    	  <%  } %>
	      
	      <td id="xmlStatus_<%=m.getLong("id") %>"><%=m.getInt("status")==1?"已部署":"未部署" %></td>
	      <td><%=m.getStr("flowFuns") %></td>
	      <td><%=m.getDate("timeStart") %></td>
	      <td id="xmlUserLastUpdate_<%=m.getLong("id") %>"><%=m.getStr("userLastUpdate")==null?"系统":m.getStr("userLastUpdate") %></td>
	      <td><%=m.getStr("remark")==null?"无":m.getStr("remark") %></td>
	     
	      <td>
		       <a href="#" onclick="showPage('admin/app/xml/<%=m.getLong("id") %>')"><i class="fa fa-pencil"></i></a>
		     <% if(m.getInt("isDel").intValue()==Constans.DEL_N){%>
		       <a href="javascript:void(0)" onclick="App.doDelXml(<%=m.getLong("id") %>)" role="button" data-toggle="modal"><i class="fa fa-trash-o"></i></a>
	    	 <% }%>	 
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

<div class="modal small fade" id="appModal" tabindex="-1" role="dialog" aria-labelledby="appModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="appModalLabel">Delete Confirmation</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="fa fa-warning modal-icon"></i>确定在删除此xml?<br>删除将会卸载些模块.</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <button class="btn btn-danger" id="delXml" data-dismiss="modal">Delete</button>
        </div>
      </div>
    </div>
</div>
