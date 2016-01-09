<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="votecss.css">
</head>
<body>
<h3>欢迎参与此次问卷调查，以下是各题目链接，请点击进入。</h3>
<%
	if(session.getAttribute("questionId") == null){
		session.removeAttribute("validateType");
		response.sendRedirect("index");
		return;
	}
	String questionsLength = (String)session.getAttribute("questionsLength");
	for(int i=1;i<=Integer.parseInt(questionsLength);i++){

%>
	<p><a href="question.jsp?questionId=<%= i %>" ><%= session.getAttribute("questionTitle" + i) %></a></p>
<%
	}
%>
</body>
</html>