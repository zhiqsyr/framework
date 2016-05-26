package org.zhiqsyr.framework.dao.hibernate.test.benchmark;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhiqsyr.framework.dao.hibernate.test.entity.Dept;
import org.zhiqsyr.framework.dao.hibernate.test.service.DeptService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext_common.xml", 
		"classpath:/spring/applicationContext_db.xml"})
public class DeptBenchmark {

	@Autowired
	private DeptService deptService;
	
	@Test
	public void getById() {
		Dept vo = deptService.getById(1);
		
		System.err.println(StringUtils.center(vo.getName(), 50, '-'));
	}
	
//	@Test
	public void doCreate() {
		deptService.doCreate();
	}
	
//	@Test
	public void doModify() {
		Dept vo = deptService.getById(2);
		System.err.println(StringUtils.center(vo.getName(), 50, '-'));
		
		deptService.doModify();
		
		throw new RuntimeException("on purpose");
	}
	
}
