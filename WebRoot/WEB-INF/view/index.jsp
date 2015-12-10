<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ESB管理界面</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href='http://fonts.useso.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

<script src="lib/jquery-1.11.1.min.js" type="text/javascript"></script>

<script src="lib/jQuery-Knob/js/jquery.knob.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".knob").knob();
	});
</script>


<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">

</head>
<body class=" theme-blue">
	<script type="text/javascript">
		$(function() {
			var match = document.cookie.match(new RegExp('color=([^;]+)'));
			if (match)
				var color = match[1];
			if (color) {
				$('body').removeClass(function(index, css) {
					return (css.match(/\btheme-\S+/g) || []).join(' ')
				})
				$('body').addClass('theme-' + color);
			}

			$('[data-popover="true"]').popover({
				html : true
			});

		});
	</script>
	<style type="text/css">
		#line-chart {
			height: 300px;
			width: 800px;
			margin: 0px auto;
			margin-top: 1em;
		}
		
		.navbar-default .navbar-brand,.navbar-default .navbar-brand:hover {
			color: #fff;
		}
	</style>	

	<script type="text/javascript">
		$(function() {
			var uls = $('.sidebar-nav > ul > *').clone();
			uls.addClass('visible-xs');
			$('#main-menu').append(uls.clone());
		});
	</script>
	<script type="text/javascript">
	  function showPage(url){
	      $('#page-main').html('页面加载中，请稍后...'); // 设置页面加载时的loading图片
	      $('#page-main').load(url); // ajax加载页面
	  }
	</script> 

	<div class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="" href="index.html"><span class="navbar-brand"><span
					class="fa fa-paper-plane"></span> ESB WEB管理</span></a>
		</div>

		<div class="navbar-collapse collapse" style="height: 1px;">
			<ul id="main-menu" class="nav navbar-nav navbar-right">
				<li class="dropdown hidden-xs"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <span
						class="glyphicon glyphicon-user padding-right-small"
						style="position:relative;top: 3px;"></span> ${fullname} <i
						class="fa fa-caret-down"></i>
				</a>

					<ul class="dropdown-menu">
						<li><a href="./">My Account</a></li>
						<li class="divider"></li>
						<li class="divider"></li>
						<li><a tabindex="-1" href="logout">Logout</a></li>
					</ul></li>
			</ul>

		</div>
	</div>


	<div class="sidebar-nav">
		<ul>
			<li><a href="#" data-target=".dashboard-menu" class="nav-header"
				data-toggle="collapse"><i class="fa fa-fw fa-dashboard"></i>
					APP应用<i class="fa fa-collapse"></i></a></li>
			<li>
				<ul class="dashboard-menu nav nav-list collapse in">
					${listGroup }
					
					<li>
						<a href="#" class="panel-heading" onclick='showPage("admin/app/0")'>
							<span class="fa fa-caret-right"></span>测试页</a>
					</li>
					<li>
						<a href="#page-main" class="panel-heading" data-toggle="collapse">
							<span class="fa fa-caret-right"></span> 折叠</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>

	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<span class="label label-info">${total }</span> 总数
				</p>
				<p class="stat">
					<span class="label label-success">${runing }</span> 运行数
				</p>
				<p class="stat">
					<span class="label label-danger">${del }</span> 刪除数
				</p>
			</div>

			<h1 class="page-title">ESB 应用</h1>
			<ul class="breadcrumb">
				<li><a href="index">首页</a></li>
				<li class="active">APP</li>
			</ul>

		</div>
		<div class="main-content">
			<div class="panel">
        		<div id="page-main" class="panel-collapse panel-body collapse in">
        			xxxx主面板
				</div>		
			</div>
		
			<footer>
				<hr>
				<p class="pull-right">
					信息中心 
				</p>
				<p>
					© 2015 <a href="http://www.ljttech.com" target="_blank">朗捷通</a>
				</p>
			</footer>
		</div>
	</div>

	<script src="lib/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
		});
	</script>

</body>
</html>
