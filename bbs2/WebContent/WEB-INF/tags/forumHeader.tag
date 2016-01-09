<%@tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="common" uri="/WEB-INF/common.tld"%>
<div id="forumLink">
  <a href="forum.jsp" id="logo">fancige</a>
  <p>
    <c:choose>
      <c:when test="${sessionScope.loginID != null}">
        <a href="sec/user/home.jsp"><common:info name="username" param="${sessionScope}" /></a>
        <a href="sec/user/logout">退出</a>
      </c:when>
      <c:otherwise>
        <a href="sec/register.html">注册</a>
        <a href="sec/login.jsp">登录</a>
      </c:otherwise>
    </c:choose>
  </p>
</div>
<div id="forumCatalog">
  <a href="forum.jsp" id="homepage">社区首页</a><a href="resource.jsp" id="resource">资源下载</a>
</div>