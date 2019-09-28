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

		// 1.��ȡServletPath:/edit.do,....�ȵ�
		String servletPath = request.getServletPath();

		// 2.ȥ��/��.do �õ�������edit�������ַ���
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length() - 3);

		// 3.���÷���
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}
	}

	// ��ѯȫ�����ݣ�����Ҫsession
	private void query(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Diary> diarys = diaryDao.getAll();
		request.setAttribute("diarys", diarys);
		request.getRequestDispatcher("/show.jsp").forward(request, response);
	}

	// ģ����ѯ������Ҫsession
	private void queryPartly(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String content = request.getParameter("content");

		CriteriaDiary criteriaDiary = new CriteriaDiary(title, author, content);

		List<Diary> diarys = diaryDao.getForListWithCriteriaDiary(criteriaDiary);
		request.setAttribute("diarys", diarys);
		request.getRequestDispatcher("/showconsequence.jsp").forward(request, response);

	}

	// �鿴�ı�,��������,������
	private void queryContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
		Diary diary = new Diary();
		diary = diaryDao.get(id);
		List<Comment> comments = diary.setComments(commentDao.getComments(id));

		long likeCount = (long) likeDao.collectLike(id);// ת��
		request.setAttribute("likeCount", likeCount);

		request.setAttribute("comments", comments);
		request.setAttribute("diary", diary);
		request.setAttribute("id", id);
		request.getRequestDispatcher("/showcontent.jsp").forward(request, response);

	}

	// ���շ����ȡ�ռ��б�
	private void queryByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Diary diary = new Diary();
		List<Category> category = diary.setCategory(categoryDao.getByCategory());
		request.setAttribute("category", category);
		request.getRequestDispatcher("/querybycategory.jsp").forward(request, response);
	}

	// ����
	private void addLike(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {

			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
			Diary diary = new Diary();
			diary = diaryDao.get(id);

			String userName = user.getUserName();
			String password = user.getPassword();
			int user_id = user.getId(userName, password);// ��д��getid����
			session.setAttribute("user_id", user_id);

			int diary_id = diary.getId();

			long count = likeDao.judgeLike(user_id, diary_id);// �ж��Ƿ��Ѿ�����
			if (count > 0) {
				request.getRequestDispatcher("/no.jsp").forward(request, response);
			} else {
				likeDao.addLike(user_id, diary_id);
				request.getRequestDispatcher("success.jsp").forward(request, response);
				return;
			}
		}
	}

	// ���ӣ�����ʵ��
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

	// �ȵõ�Ҫ������۵�����
	private void showAddCommentContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			String forwardPath = null;

			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
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

	// ��������
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

	// ��ʾ����
	private void showComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
		List<Comment> comments = commentDao.getComments(id);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("/showcontent.jsp").forward(request, response);

	}

	// ɾ��
	private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
			Diary diary = new Diary();
			diary = diaryDao.get(id);
			String author = diary.getAuthor();
			String userName = user.getUserName();

			if (!author.equals(userName)) {// �ж�author��username�Ƿ����
				request.getRequestDispatcher("/wrong.jsp").forward(request, response);
			} else {

				diaryDao.delete(id);
				response.sendRedirect("query.do");// ʹ��ת����show.jsp)�����ݿ�����ɾ����������show.jsp�ﻹ����ʾ��ʹ���ض���û�����ִ���.
				return;
			}
		}
	}
	// Cannot call sendRedirect() after the response has been committed

	// ����ʾҪ�޸ĵ�����
	private void showEditContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			String forwardPath = null;

			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
			Diary diary = new Diary();
			try {
				diary = diaryDao.get(id);
				String author = diary.getAuthor();
				String userName = user.getUserName();

				if (!author.equals(userName)) {// �ж�author��username�Ƿ����
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

	// �޸Ĳ�����ʵ��
	private void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {// edit���û�������ã�

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			// 1.��ȡ������
			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String content = request.getParameter("content");

			Diary diary = new Diary(title, author, content);
			diary.setId(id);
			request.setCharacterEncoding("UTF-8");
			diaryDao.edit(diary);

			// 5.�ض���query.do
			response.sendRedirect("query.do");
		}
	}

	// �����ļ�
	private void exportFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("warn.jsp");
		} else {
			// request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("utf-8");// ���д������Ҫ��
			int id = Integer.parseInt(request.getParameter("id"));// ��Stirngת��Ϊint
			Diary diary = new Diary();
			diary = diaryDao.get(id);

			PrintWriter out = response.getWriter();
			out.println(diary.toString());
			response.setContentType("application/msword;charset=utf-8");// ����word�ĵ�

		}

	}

	// ע��
	private void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String message = "";

		if (userName.equals("") || userName.trim().isEmpty()) {
			message = "�û�������Ϊ��";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password.equals("") || password.trim().isEmpty()) {
			message = "���벻��Ϊ��";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		User user = new User(userName, password);
		userDao.register(user);
		message = "ע��ɹ�";
		request.setAttribute("message", message);
		// request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
		response.sendRedirect("remindlogin.jsp");

	}

	// ��½
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
				message = "�û�������";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (passwordd != null && !passwordd.equals(password)) {
				message = "�������";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (inputCode != null) {
				if (!checkCode.equalsIgnoreCase(inputCode)) {
					request.setAttribute("messsage", "��֤�����");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("messsage", "��֤�벻��Ϊ�գ�");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			if (passwordd.equals(password) && checkCode.equalsIgnoreCase(inputCode)) {
				message = "��½�ɹ�";
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

	// ��֤��
	private void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String checkCode = CheckCode.getCheckCode();

		request.getSession().setAttribute("checkCode", checkCode); // ����֤�뱣�浽session�У������Ժ���֤

		try {
			// ����ͼƬ
			ImageIO.write(CheckCode.getCheckImg(checkCode), "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
