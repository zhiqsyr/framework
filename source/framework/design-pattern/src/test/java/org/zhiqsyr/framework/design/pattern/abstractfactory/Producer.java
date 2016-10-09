package org.zhiqsyr.framework.design.pattern.abstractfactory;

import org.zhiqsyr.framework.design.pattern.factory.Mobile;

// SENSE 相比工厂模式，出现新的生产种类，不用更改原有工厂，利于扩展
public interface Producer {

	Mobile produce();
	
}
