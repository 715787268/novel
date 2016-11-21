package test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.novel.service.spider.ParseBookService;

import test.BaseTest;

public class ParseBookServiceTest extends BaseTest{
	
	@Autowired
	private ParseBookService service;
	
	@Test
	public void testParseBooks(){
		service.parseBooks(null);
	}
	
	

}
