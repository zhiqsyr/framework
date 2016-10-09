package org.zhiqsyr.framework.design.pattern.abstractfactory;

import org.zhiqsyr.framework.design.pattern.factory.Mobile;
import org.zhiqsyr.framework.design.pattern.factory.SumSung;

public class SumSungFactory implements Producer {

	@Override
	public Mobile produce() {
		return new SumSung();
	}

}
