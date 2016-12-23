package com.pujjr.proxy;

import java.util.Objects;

/**
 * @author tom
 *
 */
public class TestMain {

	/**
	 * tom 2016年12月23日
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IUserService ius = new UserServiceImpl();
		System.out.println(ius);
		MyInvocationHandler mi = new MyInvocationHandler(ius);
		Class<?>[] classes = mi.getClass().getInterfaces();
		for (int i = 0; i < classes.length; i++) {
			System.out.println(i+"|"+classes[i].getName());
		}
		System.out.println(ius.getClass().getClassLoader());
		System.out.println(ius.getClass().getClassLoader().getParent());
		System.out.println(ius.getClass().getClassLoader().getParent().getParent());
		System.out.println(ClassLoader.getSystemClassLoader());
	}

}
