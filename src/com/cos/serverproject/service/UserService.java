package com.cos.serverproject.service;

import java.util.List;

import com.cos.serverproject.domain.user.User;
import com.cos.serverproject.domain.user.UserDao;
import com.cos.serverproject.domain.user.dto.LoginReqDto;
import com.cos.serverproject.domain.user.dto.joinReqDto;

public class UserService {
	UserDao userDao;
	public UserService() {
		userDao = new UserDao();
	}
	public int 회원가입(joinReqDto dto) {
		int result = userDao.save(dto);
		return result;
	}
	public User 로그인(LoginReqDto dto) {
		return userDao.findByUsernameAndPassword(dto);
	}
	public int 유저네임중복체크(String username) {
		int result = userDao.findByUsername(username);
		return result;
	}
	public List<User> 회원목록보기(){
		return userDao.findAll();
	}
	
	public int 회원삭제(int id) {
		return userDao.deleteById(id);
	}
}
