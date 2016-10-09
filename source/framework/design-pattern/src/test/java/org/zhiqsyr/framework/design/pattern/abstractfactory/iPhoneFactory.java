package org.zhiqsyr.framework.design.pattern.abstractfactory;

import org.zhiqsyr.framework.design.pattern.factory.Mobile;
import org.zhiqsyr.framework.design.pattern.factory.iPhone;

public class iPhoneFactory implements Producer {

	@Override
	public Mobile produce() {
		return new iPhone();
	}

}
