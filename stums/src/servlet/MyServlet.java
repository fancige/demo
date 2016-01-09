package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.MyOperator;

@WebServlet("/myservlet")
public class MyServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try
		{
			resp.setContentType("text/plain;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			// BufferedReader br = new BufferedReader(req.getReader());
			// String line = null;
			// while((line = br.readLine()) != null)
			// System.out.println(line);

			PrintWriter out = resp.getWriter();
			HttpSession session = req.getSession();
			String type = req.getParameter("type");
			if ("login".equals(type))
			{
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				String authority = MyOperator.login(username, password);
				if (!authority.isEmpty())
				{
					session.setAttribute("username", username);
					session.setAttribute("authority", authority);
					out.print("登陆成功");
				}
				else
				{
					out.print("用户名或密码错误");
				}
			}
			else if ("logout".equals(type))
			{
				session.invalidate();
			}
			else if ("page".equals(type))
			{
				String auth = (String) session.getAttribute("authority");
				out.print(auth == null ? "login" : auth);
			}
			else if (session.getAttribute("username") != null)
			{
				String tableName = req.getParameter("tableName");
				String username = (String) session.getAttribute("username");
				String authority = (String) session.getAttribute("authority");

				if ("tableNames".equals(type) && "admin".equals(authority))
				{
					out.print(MyOperator.getTableNames());
				}
				else if ("tableInfo".equals(type))
				{
					if ("admin".equals(authority))
						out.print(MyOperator.getTableInfo(tableName));
					else if ("student".equals(tableName)
							|| "teacher".equals(tableName)
							|| "sc".equals(tableName))
						out.print(MyOperator.getTableInfo(tableName));
				}
				else if ("query".equals(type))
				{
					if ("admin".equals(authority))
					{
						out.print(MyOperator.query(tableName, null, null, true));
					}
					else if ("student".equals(tableName)
							|| "teacher".equals(tableName))
					{
						String columnAlias = req.getParameter("columnAlias");
						String columnValue = req.getParameter("columnValue");
						out.print(MyOperator.query(tableName, columnAlias,
								columnValue, true));
					}
				}
				else if ("update".equals(type) && "admin".equals(authority))
				{
					String deleteData = req.getParameter("deleteData");
					String updateData = req.getParameter("updateData");
					int delete = MyOperator.delete(tableName, deleteData);
					int update = MyOperator.update(tableName, updateData);
					out.print("成功删除：" + delete + "、更新：" + update + " 条记录");
				}
				else if ("myInfo".equals(type))
				{
					String alias = "student".equals(authority) ? "学号" : "教师编号";
					out.print(MyOperator.query(authority, alias, username,
							false));
				}
				else if ("myGrade".equals(type) && "student".equals(authority))
				{
					out.print(MyOperator.getGrade(username,
							req.getParameter("courName")));
				}
				else if ("delete".equals(type) && "admin".equals(authority))
				{
					out.print(MyOperator.delete(tableName,
							req.getParameter("key")));
				}
			}
		}
		catch (Exception e)
		{
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().print("服务器错误！");
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().println("没有内容可显示");
	}
}
