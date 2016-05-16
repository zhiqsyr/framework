package org.zhiqsyr.framework.demo.dao.hibernate;

import org.cloud.dao.framework.hibernate.DefaultHibernateExecution;
import org.cloud.demo.entity.Dept;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext_common.xml", "classpath:/spring/applicationContext_db_hibernate.xml"})
public class HibernateDaoTest {

//	@Autowired
	private DefaultHibernateExecution dbExec;
	
	@Test
	public void test() {
		Dept po = new Dept();
		po.setName("综合应用");
		
		dbExec.save(po);
	}
	
}
