package org.zhiqsyr.framework.demo.dao.mybatis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhiqsyr.framework.demo.entity.Dept;
import org.zhiqsyr.framework.model.page.Order;
import org.zhiqsyr.framework.model.page.OrderablePagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext_common.xml", "classpath:/spring/applicationContext_db_mybatis.xml"})
public class MyBatisTest {

	@Autowired
	public DeptDao deptDao;
	
	@Test
	public void insert() {
		Dept po = new Dept();
		po.setName(String.valueOf(System.currentTimeMillis()));
		
		deptDao.insert(po);
	}
	
	@Test
	public void update() {
		Dept po = new Dept();
		po.setId(5);
		po.setName("bz");
		
		deptDao.update(po);
	}
	
	@Test
	public void delete()	{
		Dept po = new Dept();
		po.setId(6);
		
		deptDao.delete(po);
	}
	
	@Test
	public void select() {
		Dept query = new Dept();
//		query.setId(5);
		query.setName("综合");
		
		OrderablePagination op = new OrderablePagination();
		op.setPageSize(1);
		op.addOrders(Order.asc("id"));
		
		List<Dept> result = deptDao.select(query, op);
		System.err.println(result.get(0).getName());
	}
	
}
