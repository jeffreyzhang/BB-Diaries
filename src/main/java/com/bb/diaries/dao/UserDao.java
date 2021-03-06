package com.bb.diaries.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.bb.diaries.database.DbManager;
import com.bb.diaries.model.User;

public class UserDao {
	
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);
	
	private final static UserDao DAO = new UserDao();

	public static UserDao getInstance() {
		return DAO;
	}
	
	public User get(int id) {
		String sql = "select * from " +getTableName() + " where id = ?";
		Object params[] = { id };
		QueryRunner run = DbManager.getQueryRunner();
		
		User user = new User();
		try {
			user = run.query(sql,new BeanHandler(User.class), params);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("查询用户异常.[id=" + id + "]");
		}
		return user;
	}
	
	public User get(String name) {
		String sql = "select * from " +getTableName() + " where name = ?";
		Object params[] = { name };
		QueryRunner run = DbManager.getQueryRunner();
		
		User user = new User();
		try {
			user = run.query(sql,new BeanHandler(User.class), params);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("查询用户异常.[name=" + name + "]");
		}
		return user;
	}
	
	public boolean save(User user) {
		String sql = "insert into " + getTableName() + " (id,name,password,email) values (?,?,?,?)";
		Object params[] = { user.getId(), user.getName(), user.getPassword(), user.getEmail() };
		QueryRunner run = DbManager.getQueryRunner();

		boolean flag = false;
		try {
			run.update(sql, params);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
			LOGGER.error("保存用户异常.");
		}
		return flag;
	}

	public boolean update(User user) {
		String sql = "update " + getTableName() + " set name=?,password=?,email=? where id=?";
		Object params[] = { user.getName(), user.getPassword(), user.getEmail(), user.getId() };
		QueryRunner run = DbManager.getQueryRunner();

		boolean flag = false;
		try {
			run.update(sql, params);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
			LOGGER.error("更新用户异常.");
		}
		return flag;
	}

	public boolean delete(int id) {
		String sql = "delete from " + getTableName() + " where id=?";
		Object params[] = { id };
		QueryRunner run = DbManager.getQueryRunner();
		boolean flag = false;
		try {
			run.update(sql, params);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
			LOGGER.error("删除用户异常.");
		}
		return flag;
	}

	public List<User> findAll() {
		String sql = "select * from " + getTableName();
		QueryRunner run = DbManager.getQueryRunner();
		ResultSetHandler<List<User>> handler = new BeanListHandler<User>(User.class);
		List<User> list = new ArrayList<User>();
		try {
			list = run.query(sql, handler);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("查找所有制用户异常.");
		}
		return list;
	}

	public String getTableName() {
		return "t_user";
	}
}
