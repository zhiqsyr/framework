package org.zhiqsyr.framework.design.pattern.proxyanddecorator;

public class BenchMark {

	public static void main(String[] args) {
		SampleInterface sampleInterface = SampleProxyFactory.createProxy(SampleInterface.class, new SampleInterfaceImpl());	// 具体实现
		sampleInterface.sayHello();
	}
	
}
