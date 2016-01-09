<%@tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="common" uri="/WEB-INF/common.tld"%>
<div style="background: green;">
  <div id="topPanel">
    <div style="position: absolute;">
      <a href="../../forum.jsp" style="font-size: 28px; font-weight: bold;">fancige</a>
    </div>
    <div style="text-align: right;">
      <a href="home.jsp"><common:info name="username" param="${sessionScope}" /></a> <a href="publish.jsp">发帖</a> <a href="logout">退出</a>
    </div>
  </div>
</div>