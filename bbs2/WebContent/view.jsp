<%@page import="com.fancige.bean.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@	taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/forumHeader.css">
<link rel="stylesheet" type="text/css" href="css/view.css">
<title>查看</title>
</head>
<body>
  <div id="body">
    <tag:forumHeader />
    <div id="contentBody">
      <common:post type="get" filter="${param}" var="post" />
      <c:choose>
        <c:when test="${null == post}">
				指定的帖子不存在或已被删除！
			</c:when>
        <c:otherwise>
          <p id="title">${post.title}</p>
          <p id="publish">
            <common:info name="username" param="userid=${post.userid}" />
            <span>${post.publish}</span>
          </p>
          <div id="content">
            <common:string string="${post.content}" name="split" var="lines" param="1=\n" />
            <c:forEach items="${lines}" var="line">
              <p>${line}</p>
            </c:forEach>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</body>
</html>