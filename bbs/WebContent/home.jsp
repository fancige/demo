<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="css/home.css">
<script type="text/javascript" src="js/query.js"></script>
</head>
<body>
  <div class="body">
    <a href="" class="logo">fancige社区</a>
    <p class="a">
      <a href="">退出</a>
    </p>
    <div class="panel">
      <br> <font class="panel"><%=session.getAttribute("username")%></font>
      <p class="panel">
        <a href="#">我的帖子</a> <a href="#">个人资料</a> <a href="#">安全中心</a> <a href="#">最近访客</a>
      </p>
    </div>
    <br> <br>
    <div class="posts">
      <div class="title">最近帖子</div>
    </div>
  </div>
</body>
</html>