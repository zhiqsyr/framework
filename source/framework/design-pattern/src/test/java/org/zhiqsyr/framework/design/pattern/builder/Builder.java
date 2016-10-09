package org.zhiqsyr.framework.design.pattern.builder;

import java.util.ArrayList;
import java.util.List;

import org.zhiqsyr.framework.design.pattern.factory.Mobile;
import org.zhiqsyr.framework.design.pattern.factory.SumSung;
import org.zhiqsyr.framework.design.pattern.factory.iPhone;

/**
 * Builder（建造者模式），重点在于批量建造；Factory（工厂模式），重点在于单个生产
 * 
 * @author dongbz 
 * @since  2016-08-18
 */
public class Builder {

	private List<Mobile> mobiles = new ArrayList<>();
	
	public void produceiPhone(int count) {
		for (int i = 0; i < count; i++) {
			mobiles.add(new iPhone());
		}
	}
	
	public void produceSumSung(int count) {
		for (int i = 0; i < count; i++) {
			mobiles.add(new SumSung());
		}
	}
	
}
