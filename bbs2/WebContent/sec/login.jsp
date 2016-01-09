<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="en_US" />
<fmt:bundle basename="com.fancige.locale.Login">
  <!DOCTYPE html>
  <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/global.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/css.js"></script>
<script type="text/javascript" src="../js/compat.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<title><fmt:message key="login" /></title>
</head>
<body>
  <div id="topPanel">
    <div style="position: absolute;">
      <a href="../forum.jsp" style="font-size: 60px;">fancige</a>
    </div>
    <div style="text-align: right;">
      <a href="user/home.jsp"><fmt:message key="home" /></a> <a href="register.html"><fmt:message key="register" /></a>
    </div>
  </div>
  <div id="panel">
    <img src="../images/login.jpg" id="bg">
    <div id="divForm">
      <form>
        <div id="title">
          <fmt:message key="login" />
        </div>
        <div>
          <input type="text" id="userkey" placeholder="<fmt:message key="userkey"/>">
        </div>
        <div>
          <input type="password" id="password" placeholder="<fmt:message key="password"/>">
        </div>
        <div id="divCode">
          <input type="text" id="verificode" placeholder="<fmt:message key="verificode"/>"> <img id="imgCode" src="../verificode">
        </div>
        <div style="margin-top: 10px;">
          <input type="checkbox" id="autoLogin"><label for="autoLogin" style="font-size: 13px;"><fmt:message key="checkbox" /></label>
        </div>
        <div>
          <button type="submit" id="submit">
            <fmt:message key="login" />
          </button>
        </div>
        <div id="tip" style="color: red;"></div>
      </form>
    </div>
  </div>
</body>
  </html>
</fmt:bundle>