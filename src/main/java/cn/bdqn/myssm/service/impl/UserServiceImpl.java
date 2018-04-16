package cn.bdqn.myssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bdqn.myssm.dao.UserMapper;
import cn.bdqn.myssm.entity.User;
import cn.bdqn.myssm.service.UserService;

/**
 * @description
 * @author 盛毅欣
 * @address 北大青鸟沈阳三好中心
 * @created 2018年4月11日 上午11:37:20
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> userAllSelect() {
		return userMapper.userAllSelect();
	}

}
