package org.zhiqsyr.framework.design.pattern.singleton;

public class Singleton {

	private static Singleton singleton;
	
	private Singleton() {	// 私有 constructor

	}
	
	public static Singleton getInstance() {
		if (singleton == null) {
			createInstance();
		}
		
		return singleton;
	}

	private static synchronized void createInstance() {	// synchronized 避免实例化过程同步问题
		singleton = new Singleton();
	}
	
}
