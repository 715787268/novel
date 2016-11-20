package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.novel.beans.User;
import com.novel.dao.UserMapper;

//使用junit4进行测试  
//加载配置文件
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})   
public class DbConnTest {
	
	@Autowired
	private UserMapper dao;

	@Test
	public void dbConnTest(){
		User user = dao.selectByPrimaryKey(1);
		System.out.println(user);
	}
	
}
