<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结果</title>
<link rel="stylesheet" type="text/css" href="votecss.css">
</head>
<body>
<%
	//在显示该页面前先判断是否需要调用页面访问处理类(Index)验证用户是否有权访问该页面。
	
	if(!"true".equals(session.getAttribute("validate")) || !"result".equals(session.getAttribute("validateType"))){
		session.setAttribute("validateType", "result");
		request.getRequestDispatcher("index").forward(request, response);
		return;
	}

	session.removeAttribute("validate");
	session.removeAttribute("validateType");
	
	String questionId = (String)session.getAttribute("questionId");
	String questionOptionsLength = (String)session.getAttribute("questionOptionsLength");
	String questionTitle = (String)session.getAttribute("questionTitle");
	
	String[] results = (String[])session.getAttribute("results" + questionId);
%>
	<h4>本题你所选择的选项是：
<% 
	for(int i=0;i<results.length;i++){
%>
	<%= (String)session.getAttribute("questionOption" + results[i]) %>&nbsp;&nbsp;
<%	
	} 
%>
	</h4>
	<p><%= questionTitle %></p>
<%
	for(int i=1;i<=Integer.parseInt(questionOptionsLength);i++){
		String questionOption = (String)session.getAttribute("questionOption" + i);
%>
	<p><%= questionOption %>&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;<%= (String)session.getAttribute("optionCount" + i) %>&nbsp;)</p>
<%
	}
%>
<%
// 设置下一题，上一题，首页三个按钮。
	String questionsLength = (String)session.getAttribute("questionsLength");
	int currentQuestionId = Integer.parseInt(questionId);
	int nextQuestionId = currentQuestionId + 1;
	int lastQuestionId = currentQuestionId - 1;
	if(nextQuestionId <= Integer.parseInt(questionsLength) && currentQuestionId < nextQuestionId){

%>
	<a href="question.jsp?questionId=<%= nextQuestionId %>">下一题</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%
	}
	if(lastQuestionId > 0 && currentQuestionId > lastQuestionId){
%>
<a href="question.jsp?questionId=<%= lastQuestionId %>" >上一题</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%	
}
%>
<a href="index.jsp">首页</a>

</body>
</html>