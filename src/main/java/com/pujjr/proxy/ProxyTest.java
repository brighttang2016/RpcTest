package com.pujjr.proxy;

public class ProxyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IUserService userService = new UserServiceImpl();
		MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);
		IUserService userServiceProxy = (IUserService) myInvocationHandler.getProxy();
		System.out.println("ProxyTest->main 1");
		userServiceProxy.add();
		System.out.println("ProxyTest->main 2");
	}

}
