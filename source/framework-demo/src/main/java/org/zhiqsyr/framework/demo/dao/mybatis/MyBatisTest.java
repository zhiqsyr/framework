package org.zhiqsyr.framework.demo.dao.mybatis;

import java.util.List;

import org.cloud.demo.dao.mybatis.DeptDao;
import org.cloud.demo.entity.Dept;
import org.cloud.model.page.OrderablePagination;
import org.cloud.model.page.Sortor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext_common.xml", "classpath:/spring/applicationContext_db_mybatis.xml"})
public class MyBatisTest {

	@Autowired
	public DeptDao deptDao;
	
//	@Test
	public void save() {
		Dept po = new Dept();
		po.setName("质量监控");
		
		deptDao.save(po);
	}
	
	@Test
	public void find() {
		Dept query = new Dept();
		query.setName("综合");
		
		OrderablePagination op = new OrderablePagination();
		op.setPageSize(1);
		op.addSortors(Sortor.desc("name"));
		
		List<Dept> result = deptDao.find(query, op);
		System.out.println(result.get(0).getName());
	}
	
}
