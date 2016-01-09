<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问题</title>
<link rel="stylesheet" type="text/css" href="votecss.css">

<script type="text/javascript">
	function validate(){
		
		var result = document.getElementsByName("result1");
		if(result.length == 0){

			alert("结果不能为空");
			return false;
		}
		return true;
	}

</script>

</head>
<body>
<%
	// 在显示该页面前先判断是否需要调用页面访问处理类(Index)验证用户是否有权访问该页面。
	
	if(!"true".equals(session.getAttribute("validate")) || !"question".equals(session.getAttribute("validateType"))){
		
		session.setAttribute("validateType", "question");
		request.getRequestDispatcher("index").forward(request, response);
		return;
	}

	session.removeAttribute("validate");
	session.removeAttribute("validateType");
	
	String questionId = (String)session.getAttribute("questionId");
	String questionOptionsLength = (String)session.getAttribute("questionOptionsLength");
	String questionTitle = (String)session.getAttribute("questionTitle");
	
	String questionType = (String)session.getAttribute("questionType");
	String inputType = "radio";
	if("more".equals(questionType)){
		inputType = "checkbox";
	}
	
	if(session.getAttribute("tip") != null){
		out.print("<h4>" +session.getAttribute("tip") + "</h4>");
		session.removeAttribute("tip");
	}
%>
	<p><%= questionTitle %></p>
	<form action="result.jsp">
<%
	for(int i=1;i<=Integer.parseInt(questionOptionsLength);i++){
		String questionOption = (String)session.getAttribute("questionOption" + i);
%>

	<p>
	<input type=<%= inputType %> name=<%= "results" + questionId %> value=<%= String.valueOf(i) %> >
	<%= questionOption %>	
	</p>		
<%
	}
%>
	<p><input type="submit" value="提交"></p>
	</form>
	
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