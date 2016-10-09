package org.zhiqsyr.framework.design.pattern.factory;

public class MobileFactory {

	public static Mobile produceiPhone() {
		return new iPhone();
	}
	
	public static Mobile produceSumSung() {
		return new SumSung();
	}
	
}
