package com.novel.service.spider;

public interface ParseSectionService {
	
	/**
	 * 解析一本书的所有章节信息
	 * @param sectionUrl
	 */
	void parseSection(String bookUrl);
}
