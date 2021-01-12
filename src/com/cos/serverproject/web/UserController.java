package com.cos.serverproject.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.serverproject.common.dto.CommonRespDto;
import com.cos.serverproject.domain.user.User;
import com.cos.serverproject.domain.user.dto.LoginReqDto;
import com.cos.serverproject.domain.user.dto.joinReqDto;
import com.cos.serverproject.service.UserService;
import com.cos.serverproject.util.Script;
import com.google.gson.Gson;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();

		if (cmd.equals("loginForm")) {
			// 서비스 호출
			RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
			dis.forward(request, response);

		} else if (cmd.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			LoginReqDto dto = new LoginReqDto();

			dto.setUsername(username);
			dto.setPassword(password);

			User userEntity = userService.로그인(dto);
			if (userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity); // 인증주체
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "로그인 실패");
			}
		} else if (cmd.equals("joinForm")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("join")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");

			joinReqDto dto = new joinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);

			int result = userService.회원가입(dto);
			if (result == 1) {
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "회원가입 실패");
			}
		} else if (cmd.equals("usernameCheck")) {

			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			int result = userService.유저네임중복체크(username);
			PrintWriter out = response.getWriter();

			if (result == 1) {
				out.print("ok");
			} else {
				if(username == null || username=="") {
					out.print("empty");
				} else {
					out.print("fail");
				}
				
			}
			out.flush();
		} else if (cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		} else if (cmd.equals("list")) {
			List<User> users = userService.회원목록보기();
			request.setAttribute("users", users);

			RequestDispatcher dis = request.getRequestDispatcher("user/userList.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("delete")) {
			// 1. 요청 받은 json 데이터를 자바 오브젝트로 파싱
			int id = Integer.parseInt(request.getParameter("id"));
			// 2. DB에서 받은 id값으로 글 삭제
			int result = userService.회원삭제(id);

			// 3.응답할 json 데이터를 생성
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");

			Gson gson = new Gson();
			
			String respData = gson.toJson(commonRespDto);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
			
			HttpSession session = request.getSession();
			session.invalidate();
			
		}
	}

}
