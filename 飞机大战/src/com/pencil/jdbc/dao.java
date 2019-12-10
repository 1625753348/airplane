package com.pencil.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dao {
	public void insert(String name,int password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
			System.out.println("加载jdbc驱动失败");
		}
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/飞机大战","root","123456");
			System.out.println("数据库连接成功");			
			pre = conn.prepareStatement("insert into user (name,password,airplane,bullet) value(?,?,?,?);");
			
			pre.setString(1,name);
			pre.setInt(2,password);
			pre.setString(3,"1212");
			pre.setString(4,"1212");
			
			int res = pre.executeUpdate();
			if(res == 1)
			System.out.println("一条记录插入数据库");
			//
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("建立连接失败");
		}finally
		{
			if(pre != null) {
				try {
					pre.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		

	}
public boolean select(String name,int password) {
	/*
	 * 查询
	 */
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		
		e1.printStackTrace();
		System.out.println("加载jdbc驱动失败");
	}
	Connection conn = null;
	PreparedStatement pre = null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/飞机大战","root","123456");
		System.out.println("数据库连接成功");			
		pre = conn.prepareStatement("select * from `user` where name=? and `password`=?");
		

		pre.setString(1, name);
		pre.setInt(2, password);
		ResultSet res = pre.executeQuery();
		while(res.next())
		{
			System.out.println(res.getString(1)+" "+res.getInt(2));	
			return true;
		}
			
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("建立连接失败");
	}finally
	{
		if(pre != null) {
			try {
				pre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	return false;
	
}
public boolean setuser(String name ,int grade)
{
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		
		e1.printStackTrace();
		System.out.println("加载jdbc驱动失败");
	}
	Connection conn = null;
	PreparedStatement pre = null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/飞机大战","root","123456");
		System.out.println("数据库连接成功");			
		pre = conn.prepareStatement("update `user` SET `user`.grade =? WHERE `user`.`name` =?;");
		
		pre.setInt(1,grade);
		pre.setString(2,name);
		
		int res = pre.executeUpdate();
		if(res == 1)
		System.out.println("一条记录插入数据库");
		//
		
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("建立连接失败");
	}finally
	{
		if(pre != null) {
			try {
				pre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	return false;
}
}
