package org.zhiqsyr.framework.design.pattern.proxyanddecorator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SampleProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Class<T> clazz, final T proxied) {		// 使用泛型
		return (T) Proxy.newProxyInstance(
				proxied.getClass().getClassLoader(),						// proxied
				new Class[]{clazz},
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// SENSE also used as Decorator
						System.out.println("-- Dynamic Proxy pre handle  --");
						Object result = method.invoke(proxied, args);		// proxied
						System.out.println("-- Dynamic Proxy post handle --");
						
						return result;
					}
				});
	}
	
}
