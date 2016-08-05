package org.zhiqsyr.framework.model.page.springboot;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

/**
 * 自定义 Order，适于传入排序信息
 * 
 * @author dongbz 2016-08-03
 */
@SuppressWarnings("serial")
public class Order extends Sort.Order {

	static final Order DEFAULT_ORDER = new Order(Direction.DESC, "id");
	
	@JsonCreator
	public Order(@JsonProperty("direction") Direction direction, @JsonProperty("property") String property) {
		super(direction, property);
	}

	public static List<Sort.Order> convert(List<Order> orders) {
		if (orders == null || orders.size() == 0) {
			return ImmutableList.of((Sort.Order) DEFAULT_ORDER);
		}
		
		Sort.Order[] a = new Order[orders.size()];
		return ImmutableList.copyOf(orders.toArray(a));
	}
	
}
