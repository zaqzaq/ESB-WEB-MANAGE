<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<title>Amaze UI</title>
<jsp:include page="/jsp/include/head.jsp"></jsp:include>
</head>
<body>
  <div class="header">
    <div class="am-g">
      <h1>
        <img alt="LJT" src="${ctx}/resources/assets/i/favicon.png">
      </h1>
      <p>智能家居更懂你</p>
    </div>
    <hr />
  </div>
  <div class="am-g">
    <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
      <form method="post" class="am-form" action="${ctx}/index">
        <input type="text" name="userName" value="" placeholder="账号"> <br /> <input type="password" name="password" value="" placeholder="密码"> <br /> <label for="remember-me"><input
          id="remember-me" name="rememberMe" type="checkbox"> 记住密码</label> <br /> <br />
        <div class="am-cf">
          <input type="submit" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl"> <input type="submit" name="" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr">
        </div>
      </form>
      <hr>
      <p>© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
    </div>
  </div>
  <script src="${ctx}/resources/assets/js/jquery-2.1.3.min.js"></script>
  <script src="${ctx}/resources/assets/js/amazeui.js"></script>
  <script src="${ctx}/resources/assets/js/app.js"></script>
  <script src="${ctx}/resources/js/login.js"></script>
  <script>
    if(App.isEmpty(document.cookie)){
      console.info("no cookie");
    }else{
      var cookieVal = document.cookie.split("=")[1].split("-");
      var username = cookieVal[0];
      var password = cookieVal[1];
      var rememberMe = cookieVal[2];
      console.info(cookieVal);
      console.info(username);
      console.info(password);
      console.info(rememberMe);
      $("input[name='userName']").val(username);
      if(password != 'null'){
        $("input[name='password']").val(password);
      }
      if(rememberMe == 'on'){
        $("input[name='rememberMe']").attr("checked", true);
      }
      
    }
  </script>
</body>
</html>
