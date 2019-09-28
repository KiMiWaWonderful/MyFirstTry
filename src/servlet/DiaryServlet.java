package servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.mysql.jdbc.StringUtils;

import dao.CategoryDao;
import dao.CommentDao;
import dao.DiaryDao;
import dao.LikeDao;
import dao.UserDao;
import daoImpl.CommentJdbcImpl;
import daoImpl.DiaryDaoJdbcImpl;
import daoImpl.LikeDaoJdbcImpl;
import daoImpl.UserDaoJdbcImpl;
import daoImpl.CategoryDaoJdbcImpl;
import domain.Category;
import domain.CheckCode;
import domain.Comment;
import domain.CriteriaDiary;
import domain.Diary;
import domain.Like;
import domain.User;
import net.sf.json.JSON;

public class DiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DiaryDao diaryDao = new DiaryDaoJdbcImpl();
	private UserDao userDao = new UserDaoJdbcImpl();
	private CommentDao commentDao = new CommentJdbcImpl();
	private CategoryDao categoryDao = new CategoryDaoJdbcImpl();
	private LikeDao likeDao = new LikeDaoJdbcImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.获取ServletPath:/edit.do,....等等
		String servletPath = request.getServletPath();

		// 2.去除/和.do 得到类似于edit这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length() - 3);

		// 3.利用反射
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}
	}

	// 查询全部内容，不需要session
	private void query(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Diary> diarys = diaryDao.getAll();
		request.setAttribute("diarys", diarys);
		request.getRequestDispatcher("/show.jsp").forward(request, response);
	}

	// 模糊查询，不需要session
	private void queryPartly(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String content = request.getParameter("content");

		CriteriaDiary criteriaDiary = new CriteriaDiary(title, author, content);

		List<Diary> diarys = diaryDao.getForListWithCriteriaDiary(criteriaDiary);
		request.setAttribute("diarys", diarys);
		request.getRequestDispatcher("/showconsequence.jsp").forward(request, response);

	}

	// 查看文本,评论内容,点赞数
	private void queryContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
		Diary diary = new Diary();
		diary = diaryDao.get(id);
		List<Comment> comments = diary.setComments(commentDao.getComments(id));

		long likeCount = (long) likeDao.collectLike(id);// 转换
		request.setAttribute("likeCount", likeCount);

		request.setAttribute("comments", comments);
		request.setAttribute("diary", diary);
		request.setAttribute("id", id);
		request.getRequestDispatcher("/showcontent.jsp").forward(request, response);

	}

	// 按照分类获取日记列表
	private void queryByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Diary diary = new Diary();
		List<Category> category = diary.setCategory(categoryDao.getByCategory());
		request.setAttribute("category", category);
		request.getRequestDispatcher("/querybycategory.jsp").forward(request, response);
	}

	// 点赞
	private void addLike(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {

			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			Diary diary = new Diary();
			diary = diaryDao.get(id);

			String userName = user.getUserName();
			String password = user.getPassword();
			int user_id = user.getId(userName, password);// 重写了getid方法
			session.setAttribute("user_id", user_id);

			int diary_id = diary.getId();

			long count = likeDao.judgeLike(user_id, diary_id);// 判断是否已经点赞
			if (count > 0) {
				request.getRequestDispatcher("/no.jsp").forward(request, response);
			} else {
				likeDao.addLike(user_id, diary_id);
				request.getRequestDispatcher("success.jsp").forward(request, response);
				return;
			}
		}
	}

	// 增加，可以实现
	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");

			String userName = user.getUserName();
			session.setAttribute("userName", userName);

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String category_name = request.getParameter("category_name");
			Date date = new Date();
			Diary diary = new Diary(title, userName, content, date, category_name);
			diaryDao.save(diary);

			response.sendRedirect("success.jsp");
		}
	}

	// 先得到要添加评论的内容
	private void showAddCommentContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			String forwardPath = null;

			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			Diary diary = new Diary();
			try {
				diary = diaryDao.get(id);
				if (diary != null) {
					forwardPath = "/addcomment.jsp";
				}
				request.setAttribute("diary", diary);
				request.getRequestDispatcher(forwardPath).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 增加评论
	private void addComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {

			request.setCharacterEncoding("UTF-8");

			String userName = user.getUserName();
			session.setAttribute("userName", userName);

			String title = request.getParameter("title");
			String commentt = request.getParameter("commentt");
			Date time = new Date();
			Comment comment = new Comment(userName, time, commentt, title);
			commentDao.save(comment);
			response.sendRedirect("success.jsp");
		}
	}

	// 显示评论
	private void showComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
		List<Comment> comments = commentDao.getComments(id);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("/showcontent.jsp").forward(request, response);

	}

	// 删除
	private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			Diary diary = new Diary();
			diary = diaryDao.get(id);
			String author = diary.getAuthor();
			String userName = user.getUserName();

			if (!author.equals(userName)) {// 判断author和username是否相等
				request.getRequestDispatcher("/wrong.jsp").forward(request, response);
			} else {

				diaryDao.delete(id);
				response.sendRedirect("query.do");// 使用转发（show.jsp)：数据库里已删除，但是在show.jsp里还有显示。使用重定向没有这种错误.
				return;
			}
		}
	}
	// Cannot call sendRedirect() after the response has been committed

	// 先显示要修改的内容
	private void showEditContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			String forwardPath = null;

			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			Diary diary = new Diary();
			try {
				diary = diaryDao.get(id);
				String author = diary.getAuthor();
				String userName = user.getUserName();

				if (!author.equals(userName)) {// 判断author和username是否相等
					request.getRequestDispatcher("/wrong.jsp").forward(request, response);
				} else {

					if (diary != null) {
						forwardPath = "/editdiary.jsp";
					}

					request.setAttribute("diary", diary);
					request.getRequestDispatcher(forwardPath).forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 修改操作，实现
	private void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {// edit语句没有起作用？

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			// 1.获取表单参数
			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String content = request.getParameter("content");

			Diary diary = new Diary(title, author, content);
			diary.setId(id);
			request.setCharacterEncoding("UTF-8");
			diaryDao.edit(diary);

			// 5.重定向到query.do
			response.sendRedirect("query.do");
		}
	}

	// 导出文件
	private void exportFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			// request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("utf-8");// 这行代码很重要！
			int id = Integer.parseInt(request.getParameter("id"));// 将Stirng转化为int
			Diary diary = new Diary();
			diary = diaryDao.get(id);

			PrintWriter out = response.getWriter();
			out.println(diary.toString());
			response.setContentType("application/msword;charset=utf-8");// 属于word文档

		}

	}

	// 注册
	private void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String message = "";

		if (userName.equals("") || userName.trim().isEmpty()) {
			message = "用户名不能为空";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password.equals("") || password.trim().isEmpty()) {
			message = "密码不能为空";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		User user = new User(userName, password);
		userDao.register(user);
		message = "注册成功";
		request.setAttribute("message", message);
		// request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
		response.sendRedirect("remindlogin.jsp");

	}

	// 登陆
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String inputCode = request.getParameter("inputCode");
		String checkCode = (String) request.getSession().getAttribute("checkCode");

		String message = "";

		String passwordd;

		User user = new User(userName, password);
		try {
			passwordd = userDao.find(user);
			if (passwordd == null) {
				message = "用户不存在";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (passwordd != null && !passwordd.equals(password)) {
				message = "密码错误";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (inputCode != null) {
				if (!checkCode.equalsIgnoreCase(inputCode)) {
					request.setAttribute("messsage", "验证码错误！");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("messsage", "验证码不能为空！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (passwordd.equals(password) && checkCode.equalsIgnoreCase(inputCode)) {
				message = "登陆成功";
				request.setAttribute("message", message);
				// request.getRequestDispatcher("/welcome.jsp").forward(request, response);

				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(3600);
				session.setAttribute("user", user);
				session.setAttribute("userName", userName);
				response.sendRedirect("success.jsp");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 验证码
	private void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String checkCode = CheckCode.getCheckCode();

		request.getSession().setAttribute("checkCode", checkCode); // 将验证码保存到session中，便于以后验证

		try {
			// 发送图片
			ImageIO.write(CheckCode.getCheckImg(checkCode), "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
