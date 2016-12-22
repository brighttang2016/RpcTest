package com.pujjr.rpc.proxy;

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
	
	public static Object getProxy(Object target){
		Object proxyObj = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new MyInvocationHandler(target));
		return proxyObj;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		method.invoke(target, args);
		return null;
	}

}
