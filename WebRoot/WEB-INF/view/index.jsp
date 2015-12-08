<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!doctype html>
<html class="no-js">
<head>
<base href="<%=basePath%>">
<title>Hello Amaze UI</title>
<jsp:include page="include/head.jsp"></jsp:include>
<jsp:include page="include/jsFoot.jsp"></jsp:include>
</head>
<body>
  <script type="text/x-handlebars-template" id="amz-tpl-header">
    {{>header}}
  </script>
  <script type="text/x-handlebars-template" id="amz-tpl-menu">
    {{>menu}}
  </script>
  <div id="page-wrapper"></div>
</body>
<script type="text/javascript" src="${ctx}/resources/js/header.js"></script>
<script type="text/javascript">
App.loadJsp("jsp/indexContent.jsp");
</script>
</html>
