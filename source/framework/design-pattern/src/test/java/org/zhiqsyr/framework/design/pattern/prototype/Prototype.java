package org.zhiqsyr.framework.design.pattern.prototype;

import org.junit.Test;

public class Prototype implements Cloneable {

	String name = "原型模式";
	
	public Prototype clone() throws CloneNotSupportedException {
		return (Prototype) super.clone();
	}
	
	@Test
	public void test() throws CloneNotSupportedException {
		Prototype prototype = new Prototype();
		
		Prototype cloned = prototype.clone();
		System.out.println(cloned.name);
	}
	
}
