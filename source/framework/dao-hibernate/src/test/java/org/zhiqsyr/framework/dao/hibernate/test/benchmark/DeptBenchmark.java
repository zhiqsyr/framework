package org.zhiqsyr.framework.dao.hibernate.test.benchmark;

import org.apache.commons.lang.RandomStringUtils;
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
	
//	@Test
	public void getById() {
		Dept vo = deptService.getById(1);
		
		System.err.println(StringUtils.center(vo.getName(), 50, '-'));
	}
	
	@Test
	public void doCreate() throws Exception {
		System.err.println(StringUtils.center(String.valueOf(deptService.queryAll().size()), 50, '-'));
		
		Dept po = new Dept();
		po.setName(RandomStringUtils.randomAlphanumeric(10));
		
		deptService.doCreate(po);
		
//		Dept vo = deptService.getById(po.getId());
//		System.err.println(StringUtils.center(vo.getName(), 50, '-'));
		
		System.err.println(StringUtils.center(String.valueOf(deptService.queryAll().size()), 50, '-'));
	
//		throw new Exception("on purpose");		// 抛出受检查异常（非运行时异常），默认不会回滚
	}
	
//	@Test
	public void doModify() {
		Dept vo = deptService.getById(2);
		System.err.println(StringUtils.center(vo.getName(), 50, '-'));
		
		deptService.doModify(vo);
		
		throw new RuntimeException("on purpose");
	}
	
}
