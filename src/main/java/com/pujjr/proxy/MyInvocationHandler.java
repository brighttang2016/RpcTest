package com.pujjr.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author tom
 *
 */
public class MyInvocationHandler implements InvocationHandler{
	private Object target;
	public MyInvocationHandler(Object obj){
		this.target = obj;
	}
	
	public Object getProxy(){
//		Object proxyObj = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new MyInvocationHandler(target));
		Object proxyObj = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), this.target.getClass().getInterfaces(), this);
		return proxyObj;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("MyInvocationHandler->invoke");
		method.invoke(target, args);
		return null;
	}

}
