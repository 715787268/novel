package test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.novel.service.spider.ParseSectionService;

import test.BaseTest;

public class ParseSectionServiceTest  extends BaseTest{

	@Autowired
	private ParseSectionService service;
	
	@Test
	public void testParseSection(){
		service.parseSection("http://www.biquge.com.tw/0_69/");
	}
	
	
}
