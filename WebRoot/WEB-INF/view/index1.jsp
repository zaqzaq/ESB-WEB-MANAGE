<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html class="no-js">
<head>
<base href="<%=basePath%>">
<title>ESB管理界面</title>

    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/bootstrap.js" type="text/javascript"></script>
    <style type="text/css">
    .span4 { background-color: #EEEEEE; }
    .span8 { background-color: #EEEEEE; }
    
    </style>
<body>
	<div class="panel" >
		欢迎 【${fullname}】 来到ESB后台管理界面
	</div>
    <div class="row">
		 <div class="panel panel-default col-md-2">
		  <!-- Default panel contents -->
		  <div class="panel-heading">APP应用列表</div>
<!-- 		  <div class="panel-body"> -->
<!-- 		    <p>...</p> -->
<!-- 		  </div> -->
		  <!-- List group -->
		  <ul class="list-group">
		  	${listGroup}
		  </ul>
		</div>
		
		<div id="main" class="panel panel-default col-md-10">
			欢迎 【${fullname}】 来到ESB后台管理界面
		</div>
    </div>
</body>
</html>
