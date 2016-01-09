<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>fancige社区-首页</title>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/css.js"></script>
<script type="text/javascript" src="js/forum.js"></script>
<script type="text/javascript" src="js/compat.js"></script>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/forumHeader.css">
<link rel="stylesheet" type="text/css" href="css/forum.css">
</head>
<body>
  <div id="body">
    <tag:forumHeader />
    <common:post type="gets" nearIndexSize="10" filter="${param}" groupSize="10" />
    <div id="postPanel">
      <c:forEach items="${gets}" var="post">
        <div class="divPost" style="border-bottom: 1px dotted gray;">
          <a href="view.jsp?postid=${post.postid}">${post.title}</a>
        </div>
      </c:forEach>
    </div>
    <div id="pageMenu">
      <c:if test="${currentIndex > 1}">
        <a href="forum.jsp?page=${currentIndex - 1 }" title="上一页">&lt;</a>
      </c:if>
      <c:forEach var="index" items="${nearIndexs}">
        <c:choose>
          <c:when test="${currentIndex == index}">
            <span>${index}</span>
          </c:when>
          <c:otherwise>
            <a href="forum.jsp?page=${index}">${index}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <c:if test="${currentIndex < maxIndex}">
        <a href="forum.jsp?page=${currentIndex + 1}" title="下一页">&gt;</a>
      </c:if>
      <input type="text" id="inputPage" placeholder="${currentIndex}/${maxIndex}"><button id="btnPage">→</button>
    </div>
  </div>
  <script type="text/javascript">
		var maxPage = "${maxIndex}";
	</script>
</body>
</html>