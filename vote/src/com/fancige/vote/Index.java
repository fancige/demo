package com.fancige.vote;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Index
 * 该类负责处理所有访问页面的请求，无论访问哪个页面都会先转到此。
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		index(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void index(HttpServletRequest request, HttpServletResponse response){
		
		// 用户首次访问时将所有问题标题以及问题总数保存在session中，同时还要在sessson中设置用户访问问题页面以及结果页面的初始题号。
		HttpSession session = request.getSession();
		if(session.getAttribute("questionId") == null){

			session.setAttribute("questionId", "1");
			List<String> list = QuestionManager.getTitles();
			session.setAttribute("questionsLength", String.valueOf(list.size()));
			for(int i=1;i<=list.size();i++){
				
				session.setAttribute("questionTitle" + i, list.get(i - 1));
			}
		}
		if(session.getAttribute("validateType") != null){

			validate(request, response);
			
		}else{
			
			try {
				response.sendRedirect("index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 验证页面访问的题号是否合法。
	
	private void validate(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();

		List<String> questionIdList = QuestionManager.getQuestionIdList();

		// 尝试从request中获得合法题号，然后保存到session中。
		if(questionIdList.contains(request.getParameter("questionId"))){
			session.setAttribute("questionId", request.getParameter("questionId"));
		}

		dataReady(request, response);
		
		if("question".equals(session.getAttribute("validateType"))){
			
			question(request, response);
			
		}else if("result".equals(session.getAttribute("validateType"))){
			
			result(request, response);
		}
			
	}
	
	// 根据题号获取相应题库数据，暂时保存在session中。
	private void dataReady(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		String questionId = (String)session.getAttribute("questionId");
		
		String questionTitle = QuestionManager.getTitleById(questionId);
		List<String> questionOptions = QuestionManager.getOptionsById(questionId);
		
		session.setAttribute("questionTitle", questionTitle);
		session.setAttribute("questionOptionsLength", String.valueOf(questionOptions.size()));
		for(int i=1;i<=questionOptions.size();i++){
			
			session.setAttribute("questionOption" + i, questionOptions.get(i - 1));
		}
		
	}
	
	// 执行该方法前必须先执行validate方法。
	private void question(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		String questionId = (String)session.getAttribute("questionId");
		
		if(session.getAttribute("results" + questionId) != null){
			
			session.setAttribute("validateType", "result");
			result(request,response);
			return;
		}
		
		String questionType = QuestionManager.getTypeById(questionId);
		session.setAttribute("questionType",questionType);
		
		try {
			
			session.setAttribute("validate","true");
			response.sendRedirect("question.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 该方法用于设置问题结果，访问结果页面(result.jsp)时会先调用validate方法进行访问验证，通过后会调用此方法。
	// 执行该方法前必须先执行validate方法。
	private void result(HttpServletRequest request, HttpServletResponse response){
		
		
		HttpSession session = request.getSession();
		String questionId = (String)session.getAttribute("questionId");
		
		// 判断结果是否已经存在。如果已经存在，直接跳转到结果页面。如果不存在，分以下两种情况处理。
		// 如果从request中不能获得合法的结果参数，将跳转到问题页面。
		// 如果能，则将结果录入，并跳转到结果页面。
		if(session.getAttribute("results" + questionId) == null){
			
			List<String> optionIdList = QuestionManager.getOptionIdList(questionId);
			boolean tip = true;
			// 下面将结果录入，如果从request中未能获得合法结果,将返回到问题页面做答,并在session中设置该题用户并未作答的提示。
			if(request.getParameter("results" + questionId) != null){
			
				String[] optionIds = request.getParameterValues("results" + questionId);
				
				for(int i=0;i<optionIds.length;i++){
				
					if(optionIdList.contains(optionIds[i])){
						
						QuestionManager.setOptionCountById(questionId,optionIds[i]);
						session.setAttribute("results" + questionId, optionIds);
						tip = false;
					}
				}
			}
			
			if(tip){
				session.setAttribute("tip", "提示：你未做出任何选择！");
				session.setAttribute("validateType", "question");
				question(request, response);
				return;
			}
		}

		// 获得每个选项已选数，然后保存在session中。
		int optionsLength = QuestionManager.getOptionsById(questionId).size();
		for(int i=1;i<=optionsLength;i++){
			
			String optionCount = QuestionManager.getOptionCountById(questionId, String.valueOf(i));
			session.setAttribute("optionCount" + i,optionCount);
		}

		try {
			
			session.setAttribute("validate","true");
			response.sendRedirect("result.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
