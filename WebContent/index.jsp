<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	RequestDispatcher dis = request.getRequestDispatcher("user?cmd=list");
	dis.forward(request, response);
%>
