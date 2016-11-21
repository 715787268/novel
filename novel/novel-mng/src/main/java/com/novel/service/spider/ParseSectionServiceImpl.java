package com.novel.service.spider;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.beans.Section;
import com.novel.dao.SectionMapper;
import com.novel.utils.BiqugeConstants;

@Service
public class ParseSectionServiceImpl implements ParseSectionService {

	// 待解析url
	public static BlockingQueue<String> waitForParseUrl = new LinkedBlockingQueue<String>();

	// 已解析url
	public static HashSet<String> parsedUrlStrings = new HashSet<String>();

	@Autowired
	private SectionMapper sectionDao;

	@Override
	public void parseSection(String bookUrl) {
		// 书目id
		Integer bookId = Integer.parseInt(bookUrl.substring(bookUrl.lastIndexOf("_") + 1, bookUrl.lastIndexOf("/")));
		// 1.获取一本书的所有待解析的章节url
		if (bookUrl == null || bookUrl.trim() == "") {
			return;
		}
		Document doc = getDoc(bookUrl);
		// 所有章节
		Elements sectionDoms = doc.getElementsByTag("dd");
		for (Element e : sectionDoms) {
			String url = e.child(0).attr("href");
			// 待解析页面url
			url = new StringBuilder(BiqugeConstants.HOME_PAGE).append(url).toString();
			try {
				waitForParseUrl.put(url);
			} catch (InterruptedException e1) {
				System.out.println("线程阻塞：" + e1.getMessage());
			}
		}

		// 2.解析单章
		while (waitForParseUrl.size() > 0) {
			Section section = null;
			try {
				// 章节内容地址
				String contentPageUrl = waitForParseUrl.take();

				if (!parsedUrlStrings.contains(contentPageUrl)) {// 没有解析
					// 章节内容
					Document contentPageDoc = getDoc(contentPageUrl);

					section = new Section();
					section.setSectionurl(contentPageUrl);
					section.setSectionname(contentPageDoc.getElementsByClass("bookname").first().child(0).html());
					section.setContent(contentPageDoc.getElementById("content").html());
					section.setBookid(bookId);
					sectionDao.insert(section);

					// 解析完毕 加入已解析列表
					parsedUrlStrings.add(contentPageUrl);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e){
				System.out.println(e.getMessage());
			}

		}
	}

	// 获取页面文档
	public Document getDoc(String url) {
		// 从 URL 直接加载 HTML 文档
		try {
			Document doc = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
					.get();
			return doc;
		} catch (IOException e) {
			System.out.println("连接地址:" + url + "失败！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
